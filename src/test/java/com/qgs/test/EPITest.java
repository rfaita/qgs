package com.qgs.test;

import com.qgs.test.infra.AbstractTest;
import com.qgs.test.infra.TestHelper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.arquillian.CreateSwarm;

/**
 *
 * @author rafael
 */
@RunWith(Arquillian.class)
public class EPITest extends AbstractTest {

    @CreateSwarm
    public static Swarm createContainer() throws Exception {
        return AbstractTest.createContainer();
    }

    @Deployment(testable = false)
    public static Archive createDeployment() throws Exception {
        return AbstractTest.createDeployment();
    }

    @Drone
    private static WebDriver browser;

    @AfterClass
    public static void afterAll() {
        TestHelper.quit(browser);
    }

    @Test
    @RunAsClient
    public void testSalvarSucesso() throws InterruptedException {

        TestHelper.loginAndNavigate(browser, "http://localhost:8080/#!epi", "admin", "admin");

        TestHelper.elementById(browser, "add").click();

        TestHelper.elementById(browser, "epi").sendKeys("testeautomatico1");
        TestHelper.elementById(browser, "descricao").sendKeys("descrição testeautomatico1");
        TestHelper.checkBox(browser, "ativo").click();

        TestHelper.elementById(browser, "salvar").click();

        Assert.assertTrue("Problemas durante a persistência do EPI", TestHelper.elementByClass(browser, "infoNotification").getText().contains("Dados salvos com sucesso."));

        WebElement row = TestHelper.tableRow(browser, "dados", "testeautomatico1");

        Assert.assertNotNull("EPI cadastrado nao encontrado.", row);

        TestHelper.clickOnElement(browser, row);

        TestHelper.elementById(browser, "edit").click();

        Assert.assertNotEquals("Código não esta preenchido", "", TestHelper.elementById(browser, "id").getAttribute("value"));
        Assert.assertEquals("EPI não é igual", "testeautomatico1", TestHelper.elementById(browser, "epi").getAttribute("value"));
        Assert.assertEquals("Descrição não é igual", "descrição testeautomatico1", TestHelper.elementById(browser, "descricao").getAttribute("value"));
        Assert.assertEquals("Ativo não é igual", "on", TestHelper.checkBox(browser, "ativo").getAttribute("value"));

    }

    @Test
    @RunAsClient
    public void testSalvarValidacoes() throws InterruptedException {

        TestHelper.loginAndNavigate(browser, "http://localhost:8080/#!epi", "admin", "admin");

        TestHelper.elementById(browser, "add").click();

        TestHelper.elementById(browser, "salvar").click();
        Assert.assertTrue("Problemas validação EPI", TestHelper.elementByClass(browser, "warnNotification").getText().contains("O EPI deve estar preenchido e possuir no máximo 50 caractéres."));

        TestHelper.elementById(browser, "epi").sendKeys("testeautomatico1");
        TestHelper.elementById(browser, "descricao").sendKeys("descrição testeautomatico1");
        TestHelper.checkBox(browser, "ativo").click();

        TestHelper.elementById(browser, "salvar").click();

        Assert.assertTrue("Problemas durante a persistência do EPI", TestHelper.elementByClass(browser, "infoNotification").getText().contains("Dados salvos com sucesso."));

    }

    @Test
    @RunAsClient
    public void testSelecionarEditar() throws InterruptedException {

        TestHelper.loginAndNavigate(browser, "http://localhost:8080/#!epi", "admin", "admin");

        TestHelper.elementById(browser, "edit").click();

        Assert.assertTrue("Problemas durante a seleção do EPI", TestHelper.elementByClass(browser, "warnNotification").getText().contains("Selecione o que deseja editar."));

    }

}
