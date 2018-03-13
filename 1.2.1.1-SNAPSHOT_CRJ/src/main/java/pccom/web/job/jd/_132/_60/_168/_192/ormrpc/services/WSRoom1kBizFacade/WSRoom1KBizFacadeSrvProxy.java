/**
 * WSRoom1KBizFacadeSrvProxy.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pccom.web.job.jd._132._60._168._192.ormrpc.services.WSRoom1kBizFacade;

public interface WSRoom1KBizFacadeSrvProxy extends java.rmi.Remote {
	/**
	 * 收房单新增接口
	 * @param jsonData
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws pccom.web.job.jd.room1kbizfacade.client.WSInvokeException
	 */
    public java.lang.String addGather(java.lang.String jsonData) throws java.rmi.RemoteException, pccom.web.job.jd.room1kbizfacade.client.WSInvokeException;
   
    /**
	 * 租房单新增接口
	 * @param jsonData
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws pccom.web.job.jd.room1kbizfacade.client.WSInvokeException
	 */
    public java.lang.String addRent(java.lang.String jsonData) throws java.rmi.RemoteException, pccom.web.job.jd.room1kbizfacade.client.WSInvokeException;
    
    /**
     * 租房实收单新增接口
     * @param jsonData
     * @return
     * @throws java.rmi.RemoteException
     * @throws pccom.web.job.jd.room1kbizfacade.client.WSInvokeException
     */
    public java.lang.String addRentRec(java.lang.String jsonData) throws java.rmi.RemoteException, pccom.web.job.jd.room1kbizfacade.client.WSInvokeException;
    /**
	 * 收房实付单新增接口
	 * @param jsonData
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws pccom.web.job.jd.room1kbizfacade.client.WSInvokeException
	 */
    public java.lang.String addGatherPay(java.lang.String jsonData) throws java.rmi.RemoteException, pccom.web.job.jd.room1kbizfacade.client.WSInvokeException;
    /**
     * 合约作废接口
     * @param jsonData
     * @return
     * @throws java.rmi.RemoteException
     * @throws pccom.web.job.jd.room1kbizfacade.client.WSInvokeException
     */
    public java.lang.String cancelContract(java.lang.String jsonData) throws java.rmi.RemoteException, pccom.web.job.jd.room1kbizfacade.client.WSInvokeException;
}
