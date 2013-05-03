
/*
 * 
 */

package org.bridgedb.webservice.cronos;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.6
<<<<<<< HEAD
 * Tue Jan 15 15:58:41 GMT 2013
=======
 * Fri May 03 11:04:16 BST 2013
>>>>>>> master
 * Generated source version: 2.2.6
 * 
 */


@WebServiceClient(name = "CronosWSService", 
                  wsdlLocation = "http://mips.helmholtz-muenchen.de/genre/proj/cronos/CronosWSService.wsdl",
                  targetNamespace = "http://webservice.cronos/") 
public class CronosWSService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://webservice.cronos/", "CronosWSService");
    public final static QName CronosWSPort = new QName("http://webservice.cronos/", "CronosWSPort");
    static {
        URL url = null;
        try {
            url = new URL("http://mips.helmholtz-muenchen.de/genre/proj/cronos/CronosWSService.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://mips.helmholtz-muenchen.de/genre/proj/cronos/CronosWSService.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public CronosWSService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public CronosWSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CronosWSService() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns CronosWS
     */
    @WebEndpoint(name = "CronosWSPort")
    public CronosWS getCronosWSPort() {
        return super.getPort(CronosWSPort, CronosWS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CronosWS
     */
    @WebEndpoint(name = "CronosWSPort")
    public CronosWS getCronosWSPort(WebServiceFeature... features) {
        return super.getPort(CronosWSPort, CronosWS.class, features);
    }

}
