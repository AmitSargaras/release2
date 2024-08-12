package com.integrosys.cms.app.propertyparameters.bus;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Jan 30, 2007 Time: 12:37:00 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SBPropertyParameters extends EJBObject {

	public IPropertyParameters createPropertyParameters(IPropertyParameters anIDDN) throws PropertyParametersException,
			RemoteException;

	public IPropertyParameters getPropertyParameters(long aDDNID) throws PropertyParametersException, RemoteException;

	public IPropertyParameters updatePropertyParameters(IPropertyParameters anIDDN) throws ConcurrentUpdateException,
			PropertyParametersException, RemoteException;

	public List getAllProParameters() throws RemoteException, PropertyParametersException;

	public boolean allowAutoValParamTrx(String referenceId) throws RemoteException, PropertyParametersException;

	/**
	 * Get a list of active MF Template.
	 * 
	 * @return a list of MF Template
	 * @throws RemoteException, PropertyParametersException on error finding the
	 *         MF Template
	 */
	public List listMFTemplate() throws RemoteException, PropertyParametersException;

	/**
	 * Gets the MF Template by MF Template ID.
	 * 
	 * @param mFTemplateID MF Template ID
	 * @return IMFTemplate
	 * @throws RemoteException, PropertyParametersException on errors
	 *         encountered
	 */
	public IMFTemplate getMFTemplate(long mFTemplateID) throws RemoteException, PropertyParametersException;

	/**
	 * Creates MF Template.
	 * 
	 * @param value the MF Template of type IMFTemplate
	 * @return IMFTemplate
	 * @throws RemoteException, PropertyParametersException on erros creating
	 *         the MF Template
	 */
	public IMFTemplate createMFTemplate(IMFTemplate value) throws RemoteException, PropertyParametersException;

	/**
	 * Updates the input of MF Template.
	 * 
	 * @param value the MF Template of type IMFTemplate
	 * @return IMFTemplate
	 * @throws RemoteException, PropertyParametersException on error updating
	 *         the MF Template
	 */
	public IMFTemplate updateMFTemplate(IMFTemplate value) throws RemoteException, PropertyParametersException;

	/**
	 * Delete the input of MF Template.
	 * 
	 * @param value the MF Template of type IMFTemplate
	 * @return IMFTemplate
	 * @throws RemoteException, PropertyParametersException on error deleting
	 *         the MF Template
	 */
	public IMFTemplate deleteMFTemplate(IMFTemplate value) throws RemoteException, PropertyParametersException;

}
