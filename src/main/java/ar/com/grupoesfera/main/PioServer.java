package ar.com.grupoesfera.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;

public class PioServer {

    private static final Log log = LogFactory.getLog(PioServer.class);
    private static final PioServer instancia = new PioServer();

    private final UndertowJaxrsServer server;
    private Estado estado;

    private PioServer() {

        server = new UndertowJaxrsServer();
        estado = Estado.CREADO;
        prepararDespliegue();
    }

    public static PioServer instancia() {

        return instancia;
    }

    public static void main(final String[] args) {

        PioServer.instancia().iniciarServer();
        log.info("PioServer iniciado. Presione Ctrl+C para salir.");
    }

    private void prepararDespliegue() {

        if (estado.equals(Estado.CREADO)) {

            ResteasyDeployment deployment = new ResteasyDeployment();
            deployment.setApplication(App.instancia());
            deployment.setInjectorFactoryClass("org.jboss.resteasy.cdi.CdiInjectorFactory");

            DeploymentInfo deploymentInfo = server.undertowDeployment(deployment, "/");
            deploymentInfo.setClassLoader(PioServer.class.getClassLoader());
            deploymentInfo.setDeploymentName("Undertow + Resteasy example");
            deploymentInfo.setContextPath("/api");
            deploymentInfo.addListener(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));

            server.deploy(deploymentInfo);

            estado = Estado.DESPLEGADO;
        }
    }

    public void iniciarServer() {

        if (estado.equals(Estado.DESPLEGADO)) {

            Undertow.Builder builder = Undertow.builder().addHttpListener(8080, "localhost");
            server.start(builder);

            Fixture.initData();

            estado = Estado.INICIADO;

        } else {
            
            log.warn("PioServer ya estaba iniciado. Usando la instancia existente...");
        }
    }

    public void detenerServer() {

        if (estado.equals(Estado.INICIADO)) {

            Fixture.dropData();
            server.stop();
            estado = Estado.DESPLEGADO;
        }
    }

    private enum Estado {

        CREADO, DESPLEGADO, INICIADO;
    }
}
