package com.integrosys.cms.app.creditriskparam.bus;

import java.rmi.RemoteException;
import java.util.List;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * CreditRiskParamBusDelegate
 * 
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class CreditRiskParamBusDelegate {

	public ICreditRiskParamGroup createCreditRiskParameters(ICreditRiskParamGroup paramGroup)
			throws CreditRiskParamGroupException, RemoteException {
		SBCreditRiskParamBusManager busmgr = getSBCreditRiskParamBusManager();
		ICreditRiskParamGroup ppOb = busmgr.createCreditRiskParameters(paramGroup);
		return ppOb;
	}

	public ICreditRiskParamGroup getCreditRiskParameters(long groupFeedId) throws CreditRiskParamGroupException,
			RemoteException {
		SBCreditRiskParamBusManager busmgr = getSBCreditRiskParamBusManager();
		ICreditRiskParamGroup ppOb = busmgr.getCreditRiskParameters(groupFeedId);
		return ppOb;
	}

	public ICreditRiskParamGroup updateCreditRiskParameters(ICreditRiskParamGroup paramGroup)
			throws ConcurrentUpdateException, CreditRiskParamGroupException, RemoteException {
		SBCreditRiskParamBusManager busmgr = getSBCreditRiskParamBusManager();
		ICreditRiskParamGroup ppOb = busmgr.updateCreditRiskParameters(paramGroup);
		return ppOb;
	}

	public ICreditRiskParamGroup createStgCreditRiskParameters(ICreditRiskParamGroup paramGroup)
			throws ConcurrentUpdateException, CreditRiskParamGroupException, RemoteException {
		SBCreditRiskParamBusManager busmgr = getSBStagingCreditRiskParamBusManager();
		ICreditRiskParamGroup ppOb = busmgr.createCreditRiskParameters(paramGroup);
		return ppOb;
	}

	public ICreditRiskParamGroup getStgCreditRiskParameters(long groupFeedId) throws CreditRiskParamGroupException,
			RemoteException {
		SBCreditRiskParamBusManager busmgr = getSBStagingCreditRiskParamBusManager();
		ICreditRiskParamGroup ppOb = busmgr.getCreditRiskParameters(groupFeedId);
		return ppOb;
	}

	public ICreditRiskParamGroup updateStgCreditRiskParameters(ICreditRiskParamGroup paramGroup)
			throws ConcurrentUpdateException, CreditRiskParamGroupException, RemoteException {
		SBCreditRiskParamBusManager busmgr = getSBStagingCreditRiskParamBusManager();
		ICreditRiskParamGroup ppOb = busmgr.updateCreditRiskParameters(paramGroup);
		return ppOb;
	}

	public List getCreditRiskParamGroup(String groupType, String groupSubType, String groupStockType)
			throws RemoteException, CreditRiskParamGroupException {
		return getSBCreditRiskParamBusManager().getCreditRiskParamGroup(groupType, groupSubType, groupStockType);
	}

	/**
	 * Helper method to get the bus manager remote interface. Will be overridden
	 * in subclass.
	 * @return The bus manager remote interface.
	 */
	private SBCreditRiskParamBusManager getSBCreditRiskParamBusManager() {
		return (SBCreditRiskParamBusManager) BeanController.getEJB("SBCreditRiskParamBusManagerHome",
				SBCreditRiskParamBusManagerHome.class.getName());
	}

	/**
	 * Helper method to get the bus manager remote interface. Will be overridden
	 * in subclass.
	 * @return The bus manager remote interface.
	 */
	private SBCreditRiskParamBusManager getSBStagingCreditRiskParamBusManager() {
		return (SBCreditRiskParamBusManager) BeanController.getEJB("SBCreditRiskParamBusManagerHomeStaging",
				SBCreditRiskParamBusManagerHome.class.getName());
	}

}
