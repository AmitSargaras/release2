package com.integrosys.cms.app.propertyparameters.bus;

import java.rmi.RemoteException;
import java.util.List;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.JNDIConstants;
import com.integrosys.cms.app.ddn.bus.DDNException;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Jan 31, 2007 Time: 12:29:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyParametersBusDelegate {

	public IPropertyParameters createPropertyParameters(IPropertyParameters anIDDN) throws PropertyParametersException,
			RemoteException {
		SBPropertyParameters busmgr = getSBPropertyParameters();
		IPropertyParameters ppOb = busmgr.createPropertyParameters(anIDDN);
		return ppOb;
	}

	public IPropertyParameters getPropertyParameters(long aDDNID) throws PropertyParametersException, RemoteException {
		SBPropertyParameters busmgr = getSBPropertyParameters();
		IPropertyParameters ppOb = busmgr.getPropertyParameters(aDDNID);
		return ppOb;
	}

	public IPropertyParameters updatePropertyParameters(IPropertyParameters anIDDN) throws ConcurrentUpdateException,
			PropertyParametersException, RemoteException {
		SBPropertyParameters busmgr = getSBPropertyParameters();
		IPropertyParameters ppOb = busmgr.updatePropertyParameters(anIDDN);
		return ppOb;
	}

	public IPropertyParameters createStgPropertyParameters(IPropertyParameters anIDDN)
			throws PropertyParametersException, RemoteException

	{
		SBPropertyParameters busmgr = getSBStagingPropertyParameters();
		DefaultLogger.debug(this, "anIDDN : " + anIDDN);
		IPropertyParameters ppOb = busmgr.createPropertyParameters(anIDDN);
		return ppOb;
	}

	public IPropertyParameters getStgPropertyParameters(long aDDNID) throws PropertyParametersException,
			RemoteException {
		SBPropertyParameters busmgr = getSBStagingPropertyParameters();
		IPropertyParameters ppOb = busmgr.getPropertyParameters(aDDNID);
		return ppOb;
	}

	public IPropertyParameters updateStgPropertyParameters(IPropertyParameters anIDDN)
			throws ConcurrentUpdateException, PropertyParametersException, RemoteException {
		SBPropertyParameters busmgr = getSBStagingPropertyParameters();
		IPropertyParameters ppOb = busmgr.updatePropertyParameters(anIDDN);
		return ppOb;
	}

	public List getAllProParameters() throws RemoteException, PropertyParametersException {
		return getSBPropertyParameters().getAllProParameters();
	}

	private SBPropertyParameters getSBPropertyParameters() throws PropertyParametersException {
		SBPropertyParameters busmgr = (SBPropertyParameters) BeanController.getEJB(
				JNDIConstants.SB_PROPERTY_PARAMETERS_HOME, SBPropertyParametersHome.class.getName());
		if (busmgr == null) {
			throw new PropertyParametersException("SBPropertyParameters is null!");
		}
		return busmgr;
	}

	/**
	 * Helper method to return the staging ddn bus session bean
	 * 
	 * @return SBDDNBusManager - the remote handler for the ddn bus manager
	 *         session bean
	 * @throws DDNException for any errors encountered
	 */
	private SBPropertyParameters getSBStagingPropertyParameters() throws PropertyParametersException {
		SBPropertyParameters busmgr = (SBPropertyParameters) BeanController.getEJB(
				JNDIConstants.SB_STG_PROPERTY_PARAMETERS_HOME, SBPropertyParametersHome.class.getName());
		if (busmgr == null) {
			throw new PropertyParametersException("SBStgPropertyParameters is null!");
		}
		return busmgr;
	}

	public boolean allowAutoValParamTrx(String referenceId) throws RemoteException, PropertyParametersException {
		return getSBPropertyParameters().allowAutoValParamTrx(referenceId);
	}

}
