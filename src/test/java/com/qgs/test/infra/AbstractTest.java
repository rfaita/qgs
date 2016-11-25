package com.qgs.test.infra;

import java.util.zip.ZipFile;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.config.logging.Level;
import org.wildfly.swarm.config.logging.Logger;
import org.wildfly.swarm.config.security.Flag;
import org.wildfly.swarm.config.security.SecurityDomain;
import org.wildfly.swarm.config.security.security_domain.ClassicAuthentication;
import org.wildfly.swarm.config.security.security_domain.authentication.LoginModule;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jpa.JPAFraction;
import org.wildfly.swarm.logging.LoggingFraction;
import org.wildfly.swarm.security.SecurityFraction;
import org.wildfly.swarm.undertow.WARArchive;
import org.wildfly.swarm.undertow.descriptors.WebXmlAsset;

/**
 *
 * @author rafael
 */
public class AbstractTest {

    public static Archive createDeployment() throws Exception {

        WARArchive deployment = ShrinkWrap.create(ZipImporter.class, "qgs.war").importFrom(new ZipFile("target/project.war")).as(WARArchive.class);

        WebXmlAsset webXmlAsset = deployment.findWebXmlAsset();
        webXmlAsset.setLoginConfig("BASIC", "QGSdomain");
        webXmlAsset.protect("/api/secure/*").withRole("*");

        webXmlAsset.setContextParam("productionMode", "false");

        deployment.setSecurityDomain("QGSdomain");

        return deployment;
    }

    public static Swarm createContainer() throws Exception {
        Swarm container = new Swarm();

        container.fraction(new DatasourcesFraction()
                .dataSource("QGSDS", (ds) -> {
                    ds.driverName("h2");
                    ds.connectionUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
                    ds.userName("sa");
                    ds.password("sa");
                })
        );

        container.fraction(new JPAFraction()
                .defaultDatasource("jboss/datasources/QGSDS")
        );

        container.fraction(new LoggingFraction()
                .logger(new Logger("org.hibernate.SQL").level(Level.TRACE))
                .logger(new Logger("org.hibernate.type.descriptor.sql.BasicBinder").level(Level.TRACE))
        );

        container.fraction(SecurityFraction.defaultSecurityFraction()
                .securityDomain(new SecurityDomain("QGSdomain")
                        .cacheType(SecurityDomain.CacheType.DEFAULT)
                        .classicAuthentication(new ClassicAuthentication()
                                .loginModule(new LoginModule("QGSLoginModule")
                                        .code("com.qgs.util.security.QGSLoginModule")
                                        .flag(Flag.REQUIRED)
                                )))
        );

        return container;
    }
}
