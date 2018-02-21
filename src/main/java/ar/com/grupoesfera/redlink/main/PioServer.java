package ar.com.grupoesfera.redlink.main;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;

public class PioServer {

    public static void main(final String[] args) {

        UndertowJaxrsServer server = new UndertowJaxrsServer();

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setApplication(App.instancia());
        deployment.setInjectorFactoryClass("org.jboss.resteasy.cdi.CdiInjectorFactory");

        DeploymentInfo deploymentInfo = server.undertowDeployment(deployment, "/");
        deploymentInfo.setClassLoader(PioServer.class.getClassLoader());
        deploymentInfo.setDeploymentName("Undertow + Resteasy example");
        deploymentInfo.setContextPath("/api");
        deploymentInfo.addListener(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));
        
        server.deploy(deploymentInfo);

        Undertow.Builder builder = Undertow.builder()
            .addHttpListener(8080, "localhost");

        server.start(builder);
    }
}
