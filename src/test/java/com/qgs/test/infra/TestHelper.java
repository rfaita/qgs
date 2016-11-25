package com.qgs.test.infra;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author rafael
 */
public class TestHelper {

    public static void waitForElementById(WebDriver webDriver, String id, Integer waitTime) {
        WebDriverWait wait = new WebDriverWait(webDriver, waitTime);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
    }

    public static void waitForElementByClass(WebDriver webDriver, String clazz, Integer waitTime) {
        WebDriverWait wait = new WebDriverWait(webDriver, waitTime);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(clazz)));
    }

    public static void navigate(WebDriver webDriver, String url) {
        webDriver.manage().window().setSize(new Dimension(1360, 768));
        webDriver.navigate().to(url);
    }

    public static WebElement elementById(WebDriver webDriver, String id) {
        waitForElementById(webDriver, id, 2);
        return webDriver.findElement(By.id(id));
    }

    public static WebElement elementByClass(WebDriver webDriver, String clazz) {
        waitForElementByClass(webDriver, clazz, 2);
        return webDriver.findElement(By.className(clazz));
    }

    public static WebElement checkBox(WebDriver webDriver, String id) {
        return elementById(webDriver, id).findElement(By.cssSelector("input[type=checkbox]"));
    }

    public static WebElement tableRow(WebDriver webDriver, String table, String key) {
        List<WebElement> trs = elementById(webDriver, table).findElements(By.cssSelector(".v-table-body tr"));

        for (WebElement tr : trs) {
            List<WebElement> tds = tr.findElements(By.tagName("td"));
            for (WebElement td : tds) {
                if (key.toUpperCase().equals(td.getText().toUpperCase())) {

                    return td.findElement(By.className("v-table-cell-wrapper"));
                }
            }
        }
        return null;
    }

    public static void clickOnElement(WebDriver webDriver, WebElement we) {

        Mouse mouse = ((HasInputDevices) webDriver).getMouse();

        mouse.click(((Locatable) we).getCoordinates());

    }

    public static void loginAndNavigate(WebDriver webDriver, String url, String usuario, String senha) {
        webDriver.manage().deleteAllCookies();
        navigate(webDriver, url);
        if (TestHelper.elementById(webDriver, "usuario") != null) {
            TestHelper.elementById(webDriver, "usuario").sendKeys(usuario);
            TestHelper.elementById(webDriver, "senha").sendKeys(senha);
            TestHelper.elementById(webDriver, "entrar").click();
        }
    }

    public static void quit(WebDriver browser) {
        browser.quit();
    }

}
