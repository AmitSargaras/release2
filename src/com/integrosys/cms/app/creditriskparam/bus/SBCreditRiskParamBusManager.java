package com.integrosys.cms.app.creditriskparam.bus;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * SBCreditRiskParamBusManager Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface SBCreditRiskParamBusManager extends EJBObject {

	public ICreditRiskParamGroup createCreditRiskParameters(ICreditRiskParamGroup paramGroup)
			throws CreditRiskParamGroupException, RemoteException;

	public ICreditRiskParamGroup getStagingCreditRiskParameters(long groupFeedId) throws CreditRiskParamGroupException,
			RemoteException;

	public ICreditRiskParamGroup getCreditRiskParameters(long groupFeedId) throws CreditRiskParamGroupException,
			RemoteException;

	public ICreditRiskParamGroup updateCreditRiskParameters(ICreditRiskParamGroup paramGroup)
			throws ConcurrentUpdateException, CreditRiskParamGroupException, RemoteException;

	public ArrayList getCreditRiskParamGroup(String groupType, String groupSubType, String groupStockType)
			throws RemoteException, CreditRiskParamGroupException;

}
