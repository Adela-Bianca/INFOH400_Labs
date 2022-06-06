/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.fhirserver2022;

import ca.uhn.fhir.rest.api.EncodingEnum;
import ca.uhn.fhir.rest.server.HardcodedServerAddressStrategy;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Adrien Foucart
 */
@WebServlet(name = "FHIRServer", urlPatterns = {"/FHIRServer/*"})
public class FHIRServer extends RestfulServer {

    private static final long serialVersionUID = 1L;

    /**
     * The initialize method is automatically called when the servlet is
     * starting up, so it can be used to configure the servlet to define
     * resource providers, or set up configuration, interceptors, etc.
     *
     * @throws javax.servlet.ServletException
     */
    @Override
    protected void initialize() throws ServletException {
        /*
       * The servlet defines any number of resource providers, and
       * configures itself to use them by calling
       * setResourceProviders()
         */
        this.setDefaultResponseEncoding(EncodingEnum.JSON);

        String serverBaseUrl = "http://localhost:8080/FHIRServer2022/";
        setServerAddressStrategy(new HardcodedServerAddressStrategy(serverBaseUrl));

        List<IResourceProvider> resourceProviders = new ArrayList();
        resourceProviders.add(new PatientResourceProvider());
        setResourceProviders(resourceProviders);
    }

}
