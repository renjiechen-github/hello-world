package pccom.web.job.jd._132._60._168._192.ormrpc.services.WSRoom1kBizFacade;

public class WSRoom1KBizFacadeSrvProxyProxy implements pccom.web.job.jd._132._60._168._192.ormrpc.services.WSRoom1kBizFacade.WSRoom1KBizFacadeSrvProxy {
  private String _endpoint = null;
  private pccom.web.job.jd._132._60._168._192.ormrpc.services.WSRoom1kBizFacade.WSRoom1KBizFacadeSrvProxy wSRoom1KBizFacadeSrvProxy = null;
  
  public WSRoom1KBizFacadeSrvProxyProxy() {
    _initWSRoom1KBizFacadeSrvProxyProxy();
  }
  
  public WSRoom1KBizFacadeSrvProxyProxy(String endpoint) {
    _endpoint = endpoint;
    _initWSRoom1KBizFacadeSrvProxyProxy();
  }
  
  private void _initWSRoom1KBizFacadeSrvProxyProxy() {
    try {
      wSRoom1KBizFacadeSrvProxy = (new pccom.web.job.jd._132._60._168._192.ormrpc.services.WSRoom1kBizFacade.WSRoom1KBizFacadeSrvProxyServiceLocator()).getWSRoom1kBizFacade();
      if (wSRoom1KBizFacadeSrvProxy != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wSRoom1KBizFacadeSrvProxy)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wSRoom1KBizFacadeSrvProxy)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wSRoom1KBizFacadeSrvProxy != null)
      ((javax.xml.rpc.Stub)wSRoom1KBizFacadeSrvProxy)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public pccom.web.job.jd._132._60._168._192.ormrpc.services.WSRoom1kBizFacade.WSRoom1KBizFacadeSrvProxy getWSRoom1KBizFacadeSrvProxy() {
    if (wSRoom1KBizFacadeSrvProxy == null)
      _initWSRoom1KBizFacadeSrvProxyProxy();
    return wSRoom1KBizFacadeSrvProxy;
  }
  
  public java.lang.String addGather(java.lang.String jsonData) throws java.rmi.RemoteException, pccom.web.job.jd.room1kbizfacade.client.WSInvokeException{
    if (wSRoom1KBizFacadeSrvProxy == null)
      _initWSRoom1KBizFacadeSrvProxyProxy();
    return wSRoom1KBizFacadeSrvProxy.addGather(jsonData);
  }
  
  public java.lang.String addRent(java.lang.String jsonData) throws java.rmi.RemoteException, pccom.web.job.jd.room1kbizfacade.client.WSInvokeException{
    if (wSRoom1KBizFacadeSrvProxy == null)
      _initWSRoom1KBizFacadeSrvProxyProxy();
    return wSRoom1KBizFacadeSrvProxy.addRent(jsonData);
  }
  
  public java.lang.String addRentRec(java.lang.String jsonData) throws java.rmi.RemoteException, pccom.web.job.jd.room1kbizfacade.client.WSInvokeException{
    if (wSRoom1KBizFacadeSrvProxy == null)
      _initWSRoom1KBizFacadeSrvProxyProxy();
    return wSRoom1KBizFacadeSrvProxy.addRentRec(jsonData);
  }
  
  public java.lang.String addGatherPay(java.lang.String jsonData) throws java.rmi.RemoteException, pccom.web.job.jd.room1kbizfacade.client.WSInvokeException{
    if (wSRoom1KBizFacadeSrvProxy == null)
      _initWSRoom1KBizFacadeSrvProxyProxy();
    return wSRoom1KBizFacadeSrvProxy.addGatherPay(jsonData);
  }
  
  public java.lang.String cancelContract(java.lang.String jsonData) throws java.rmi.RemoteException, pccom.web.job.jd.room1kbizfacade.client.WSInvokeException{
    if (wSRoom1KBizFacadeSrvProxy == null)
      _initWSRoom1KBizFacadeSrvProxyProxy();
    return wSRoom1KBizFacadeSrvProxy.cancelContract(jsonData);
  }
  
  
}