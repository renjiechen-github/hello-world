/**
 * WSRoom1KBizFacadeSrvProxyServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pccom.web.job.jd._132._60._168._192.ormrpc.services.WSRoom1kBizFacade;

public class WSRoom1KBizFacadeSrvProxyServiceLocator extends org.apache.axis.client.Service implements pccom.web.job.jd._132._60._168._192.ormrpc.services.WSRoom1kBizFacade.WSRoom1KBizFacadeSrvProxyService {

    public WSRoom1KBizFacadeSrvProxyServiceLocator() {
    }


    public WSRoom1KBizFacadeSrvProxyServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSRoom1KBizFacadeSrvProxyServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSRoom1kBizFacade
    private java.lang.String WSRoom1kBizFacade_address = "http://192.168.60.132:6888/ormrpc/services/WSRoom1kBizFacade";

    public java.lang.String getWSRoom1kBizFacadeAddress() {
        return WSRoom1kBizFacade_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSRoom1kBizFacadeWSDDServiceName = "WSRoom1kBizFacade";

    public java.lang.String getWSRoom1kBizFacadeWSDDServiceName() {
        return WSRoom1kBizFacadeWSDDServiceName;
    }

    public void setWSRoom1kBizFacadeWSDDServiceName(java.lang.String name) {
        WSRoom1kBizFacadeWSDDServiceName = name;
    }

    public pccom.web.job.jd._132._60._168._192.ormrpc.services.WSRoom1kBizFacade.WSRoom1KBizFacadeSrvProxy getWSRoom1kBizFacade() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSRoom1kBizFacade_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSRoom1kBizFacade(endpoint);
    }

    public pccom.web.job.jd._132._60._168._192.ormrpc.services.WSRoom1kBizFacade.WSRoom1KBizFacadeSrvProxy getWSRoom1kBizFacade(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	pccom.web.job.jd._132._60._168._192.ormrpc.services.WSRoom1kBizFacade.WSRoom1KBizFacadeSoapBindingStub _stub = new pccom.web.job.jd._132._60._168._192.ormrpc.services.WSRoom1kBizFacade.WSRoom1KBizFacadeSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSRoom1kBizFacadeWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSRoom1kBizFacadeEndpointAddress(java.lang.String address) {
        WSRoom1kBizFacade_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (pccom.web.job.jd._132._60._168._192.ormrpc.services.WSRoom1kBizFacade.WSRoom1KBizFacadeSrvProxy.class.isAssignableFrom(serviceEndpointInterface)) {
            	pccom.web.job.jd._132._60._168._192.ormrpc.services.WSRoom1kBizFacade.WSRoom1KBizFacadeSoapBindingStub _stub = new pccom.web.job.jd._132._60._168._192.ormrpc.services.WSRoom1kBizFacade.WSRoom1KBizFacadeSoapBindingStub(new java.net.URL(WSRoom1kBizFacade_address), this);
                _stub.setPortName(getWSRoom1kBizFacadeWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WSRoom1kBizFacade".equals(inputPortName)) {
            return getWSRoom1kBizFacade();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://192.168.60.132:6888/ormrpc/services/WSRoom1kBizFacade", "WSRoom1kBizFacadeSrvProxyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://192.168.60.132:6888/ormrpc/services/WSRoom1kBizFacade", "WSRoom1kBizFacade"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSRoom1kBizFacade".equals(portName)) {
            setWSRoom1kBizFacadeEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
