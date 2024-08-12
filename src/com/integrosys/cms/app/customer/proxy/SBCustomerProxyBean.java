/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/proxy/SBCustomerProxyBean.java,v 1.13 2006/09/07 10:23:28 jychong Exp $
 */
package com.integrosys.cms.app.customer.proxy;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxControllerException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.MakerCheckerUserUtil;
import com.integrosys.cms.app.customer.bus.CustomerDAO;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.GroupSearchCriteria;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.SBCustomerManager;
import com.integrosys.cms.app.customer.bus.SBCustomerManagerHome;
import com.integrosys.cms.app.customer.trx.CustomerTrxControllerFactory;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.customer.trx.OBCMSCustomerTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * This session bean provides the implementation of the AbstractCAProxy, wrapped
 * in an EJB mechanism.
 * 
 * @author $Author: jychong $
 * @version $Revision: 1.13 $
 * @since $Date: 2006/09/07 10:23:28 $ Tag: $Name: $
 */
public class SBCustomerProxyBean implements ICustomerProxy, javax.ejb.SessionBean {

	private static final long serialVersionUID = -5750560125890700715L;

	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default Constructor
	 */
	
	private ITrxControllerFactory trxControllerFactory;

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public SBCustomerProxyBean() {
	}

	/**
	 * Create a customer record, without dual control.
	 * 
	 * @param value is of type ICMSCustomer
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	public ICMSTrxResult createCustomer(ICMSCustomer value) throws CustomerException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CREATE_CUSTOMER);

			OBCMSCustomerTrxValue trxValue = new OBCMSCustomerTrxValue();
			trxValue.setStagingCustomer(value);
			

			ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to create customer via workflow", e);
		}
	}
	
	/**
	 * Create a customer record, without dual control.
	 * 
	 * @param value is of type ICMSCustomerTrxValue
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	public ICMSCustomerTrxValue createCustomer(ICMSCustomerTrxValue value) throws CustomerException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			
				param.setAction(ICMSConstant.ACTION_CREATE_CUSTOMER);
			
			ITrxController controller = (new CustomerTrxControllerFactory()).getController(value, param);
			ITrxResult result = controller.operate(value, param);
			return (ICMSCustomerTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to create customer via workflows", e);
		}
	}
	public ICMSCustomerTrxValue createCustomerBrmaker(ICMSCustomerTrxValue value, String loginUser) throws CustomerException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
		
				param.setAction(ICMSConstant.ACTION_CREATE_CUSTOMER_BRMAKER);
		
			ITrxController controller = (new CustomerTrxControllerFactory()).getController(value, param);
			ITrxResult result = controller.operate(value, param);
			return (ICMSCustomerTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to create customer via workflows", e);
		}
	}
	public ICMSCustomerTrxValue createDraftCustomer(ICMSCustomerTrxValue value) throws CustomerException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_CREATE_DRAFT_CUSTOMER);

			ITrxController controller = (new CustomerTrxControllerFactory()).getController(value, param);
			ITrxResult result = controller.operate(value, param);
			return (ICMSCustomerTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to create customer via workflows", e);
		}
	}
	public ICMSCustomerTrxValue createDraftCustomerBrmaker(ICMSCustomerTrxValue value) throws CustomerException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_CREATE_DRAFT_CUSTOMER_BRMAKER);

			ITrxController controller = (new CustomerTrxControllerFactory()).getController(value, param);
			ITrxResult result = controller.operate(value, param);
			return (ICMSCustomerTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to create customer via workflows", e);
		}
	}
	
	public ICMSCustomerTrxValue saveCustomer(ICMSCustomerTrxValue value) throws CustomerException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SAVE_CUSTOMER);

			ITrxController controller = (new CustomerTrxControllerFactory()).getController(value, param);
			ITrxResult result = controller.operate(value, param);
			return (ICMSCustomerTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to create customer via workflows", e);
		}
	}
	public ICMSCustomerTrxValue saveCustomerBrmaker(ICMSCustomerTrxValue value) throws CustomerException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SAVE_CUSTOMER_BRMAKER);

			ITrxController controller = (new CustomerTrxControllerFactory()).getController(value, param);
			ITrxResult result = controller.operate(value, param);
			return (ICMSCustomerTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to create customer via workflows", e);
		}
	}
	public ICMSCustomerTrxValue saveEditCustomer(ICMSCustomerTrxValue value) throws CustomerException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_SAVE_CUSTOMER_IN_EDIT);

			ITrxController controller = (new CustomerTrxControllerFactory()).getController(value, param);
			ITrxResult result = controller.operate(value, param);
			return (ICMSCustomerTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to create customer via workflows", e);
		}
	}
	
	
	public ICMSCustomerTrxValue makerResubmitCreateCustomer(ICMSCustomerTrxValue value) throws CustomerException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_RESUBMIT_CREATE_CUSTOMER);

			ITrxController controller = (new CustomerTrxControllerFactory()).getController(value, param);
			ITrxResult result = controller.operate(value, param);
			return (ICMSCustomerTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to create customer via workflows", e);
		}
	}
	
	public ICMSCustomerTrxValue makerResubmitCreateCustomerBrmaker(ICMSCustomerTrxValue value) throws CustomerException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_RESUBMIT_CREATE_CUSTOMER_BRMAKER);

			ITrxController controller = (new CustomerTrxControllerFactory()).getController(value, param);
			ITrxResult result = controller.operate(value, param);
			return (ICMSCustomerTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to create customer via workflows", e);
		}
	}
	/**
	 * Update a Customer given a customer' ob
	 *
	 * @return ICMSCustomer if it can be found, or null if the customer does not
	 *         exist.
	 * @throws CustomerException on errors
	 */
	public ICMSCustomer updateCustomer(ICMSCustomer aCMSCustomer) throws CustomerException {
		try {
			SBCustomerManager mgr = getCustomerManager();
			return mgr.updateCustomer(aCMSCustomer);
		}
		catch (CustomerException e) {
			throw e;
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to update customer, id [" + aCMSCustomer.getCustomerID()
					+ "], throwing root cause", e.getCause());
		}
	}
	 //Sandeep Shinde added 
	 public ICMSCustomerTrxValue updateCustomer(ICMSCustomerTrxValue value) throws CustomerException {
		 try {
				OBCMSTrxParameter param = new OBCMSTrxParameter();
				param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_CUSTOMER);

				ITrxController controller = (new CustomerTrxControllerFactory()).getController(value, param);
				ITrxResult result = controller.operate(value, param);
				return (ICMSCustomerTrxValue) result.getTrxValue();
			}
			catch (TransactionException e) {
				_context.setRollbackOnly();
				throw new CustomerException("Failed to create customer via workflows", e);
			}
		}

	/**
	 * Get a Customer given a customer's ID
	 * 
	 * @param customerID is of type long
	 * @return ICMSCustomer if it can be found, or null if the customer does not
	 *         exist.
	 * @throws CustomerException on errors
	 */
	public ICMSCustomer getCustomer(long customerID) throws CustomerException {
		try {
			SBCustomerManager mgr = getCustomerManager();
			return mgr.getCustomer(customerID);
		}
		catch (CustomerException e) {
			throw e;
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to retrieve customer using customer id [" + customerID
					+ "], throwing root cause", e.getCause());
		}
	}

	/**
	 * Get a customer transaction give a transaction ID
	 * 
	 * @param trxID is of type String
	 * @return ICMSCustomerTrxValue
	 * @throws CustomerException on errors
	 */
	public ICMSCustomerTrxValue getTrxCustomer(String trxID) throws CustomerException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_READ_CUSTOMER);

			ICMSCustomerTrxValue trxValue = new OBCMSCustomerTrxValue();
			trxValue.setTransactionID(trxID);
			trxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTOMER);

			ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSCustomerTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			throw new CustomerException("Failed to retrieve customer trx value, trx id [" + trxID + "]", e);
		}
	}

	/**
	 * Get a Customer Transaction value given a customer's ID
	 * 
	 * @param customerID is of type long
	 * @return ICMSCustomerTrxValue if it can be found, or null if the customer
	 *         does not exist.
	 * @throws CustomerException on errors
	 */
	public ICMSCustomerTrxValue getTrxCustomer(long customerID) throws CustomerException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_READ_CUSTOMER_ID);

			ICMSCustomerTrxValue trxValue = new OBCMSCustomerTrxValue();
			trxValue.setReferenceID(String.valueOf(customerID));

			ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSCustomerTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			throw new CustomerException("Failed to retrieve customer trx value, customer id [" + customerID + "]", e);
		}
	}

	/**
	 * Search customer
	 * 
	 * @param criteria is of type CustomerSearchCriteria
	 * @return SearchResult containing ICustomerSearchResult objects
	 * @throws SearchDAOException on errors
	 */
	public SearchResult searchCustomer(CustomerSearchCriteria criteria) throws SearchDAOException {
		try {
			SBCustomerManager mgr = getCustomerManager();
			return mgr.searchCustomer(criteria);
		}
		catch (RemoteException e) {
			throw new SearchDAOException("Failed to search customer, throwing root cause", e.getCause());
		}
	}
	
	public SearchResult searchCustomerImageUpload(CustomerSearchCriteria criteria) throws SearchDAOException {
		try {
			SBCustomerManager mgr = getCustomerManager();
			return mgr.searchCustomerImageUpload(criteria);
		}
		catch (RemoteException e) {
			throw new SearchDAOException("Failed to search customer, throwing root cause", e.getCause());
		}
	}

	/**
	 * Search group
	 * 
	 * @param criteria is of type GroupSearchCriteria
	 * @return SearchResult containing IGroupSearchResult objects
	 * @throws SearchDAOException on errors
	 */
	public SearchResult searchGroup(GroupSearchCriteria criteria) throws SearchDAOException {
		try {
			SBCustomerManager mgr = getCustomerManager();
			return mgr.searchGroup(criteria);
		}
		catch (RemoteException e) {
			throw new SearchDAOException("Failed to search group, throwing root cause", e.getCause());
		}
	}

	/**
	 * Get map of mailing details for a list of limit profile IDs. One official
	 * address per limit profile ID if available.
	 * 
	 * @param sciLimitProfileIDList - List
	 * @return Map - (limitProfileID, OBCustomerMailingDetails)
	 * @throws SearchDAOException if errors
	 */
	public Map getCustomerMailingDetails(List sciLimitProfileIDList) throws SearchDAOException {
		try {
			SBCustomerManager mgr = getCustomerManager();
			return mgr.getCustomerMailingDetails(sciLimitProfileIDList);
		}
		catch (RemoteException e) {
			throw new SearchDAOException("Failed to retrieve customer mailing details, throwing root cause", e
					.getCause());
		}
	}

	public Map getFamcodeCustNameByCustomer(List sciLimitProfileIDList) throws SearchDAOException {
		try {
			SBCustomerManager mgr = getCustomerManager();
			return mgr.getFamcodeCustNameByCustomer(sciLimitProfileIDList);
		}
		catch (RemoteException e) {
			throw new SearchDAOException("Failed to retrieve fam code, customer name using limit profile id, "
					+ "throwing root cause", e.getCause());
		}
	}

	/**
	 * Allows the host to update the customer details
	 * 
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	public ICMSTrxResult hostUpdateCustomer(ICMSCustomerTrxValue trxValue) throws CustomerException {
		try {
			if (null == trxValue) {
				throw new CustomerException("ICMSCustomerTrxValue is null!");
			}
			trxValue.setTrxContext(new OBTrxContext());

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_HOST_UPDATE_CUSTOMER);

			ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to update customer trx value", e);
		}
	}

	/**
	 * Allows the host to delete the customer details
	 * 
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	public ICMSTrxResult hostDeleteCustomer(ICMSCustomerTrxValue trxValue) throws CustomerException {
		try {
			if (null == trxValue) {
				throw new CustomerException("ICMSCustomerTrxValue is null!");
			}
//			trxValue.setTrxContext(new OBTrxContext());

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_HOST_DELETE_CUSTOMER);

			ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to delete customer trx value", e);
		}
	}

	/**
	 * Maker update customer details
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @param customer is the ICMSCustomer object to be updated
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	public ICMSTrxResult makerUpdateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue, ICMSCustomer customer)
			throws CustomerException {
		try {
			if (null == trxValue) {
				throw new CustomerException("ICMSCustomerTrxValue is null!");
			}			
			trxValue.setTrxContext(context);
			String loginUser = String.valueOf(context.getUser().getTeamTypeMembership().getMembershipID());
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			if(loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_MAKER))||loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_MAKER_WFH)))
			{
				param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_CUSTOMER_BRMAKER);
			}
			else{
				param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_CUSTOMER);
			}
			

			//customer.setStatus("ACTIVE");	/*	Sandeep Shinde have set status so as not to alter it as it gets set to null	*/
			trxValue.setStagingCustomer(customer);
			trxValue.setCustomerName(customer.getCustomerName());
		     trxValue.setLegalName(customer.getCustomerName());

		     ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to update customer via workflow", e);
		}
	}

	
	public ICMSTrxResult makerUpdateDraftCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue, ICMSCustomer customer)
	throws CustomerException {
try {
	if (null == trxValue) {
		throw new CustomerException("ICMSCustomerTrxValue is null!");
	}			
	trxValue.setTrxContext(context);
	String loginUser = String.valueOf(context.getUser().getTeamTypeMembership().getMembershipID());
	OBCMSTrxParameter param = new OBCMSTrxParameter();
	
	if(loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_MAKER))||loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_MAKER_WFH)))
	{
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_DRAFT_CUSTOMER_BRMAKER);
	}
	else{
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_DRAFT_CUSTOMER);
	}
	

		/*	Sandeep Shinde have set status so as not to alter it as it gets set to null	*/
	trxValue.setStagingCustomer(customer);
	trxValue.setCustomerName(customer.getCustomerName());
    trxValue.setLegalName(customer.getCustomerName());

	ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
	ITrxResult result = controller.operate(trxValue, param);
	return (ICMSTrxResult) result;
}
catch (TransactionException e) {
	_context.setRollbackOnly();
	throw new CustomerException("Failed to update customer via workflow", e);
}
}
	
	public ICMSTrxResult makerEditDraftCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue, ICMSCustomer customer)
	throws CustomerException {
try {
	if (null == trxValue) {
		throw new CustomerException("ICMSCustomerTrxValue is null!");
	}			
	trxValue.setTrxContext(context);
	String loginUser = String.valueOf(context.getUser().getTeamTypeMembership().getMembershipID());
	OBCMSTrxParameter param = new OBCMSTrxParameter();
	if(loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_MAKER))||loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_MAKER_WFH)))
	{
		param.setAction(ICMSConstant.ACTION_SAVE_CUSTOMER_IN_EDIT_BRMAKER);
	}
	
	//Start:Uma Khot:Valid Rating CR
	else if(loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_PARTY_MAKER)))
	{
		param.setAction(ICMSConstant.ACTION_SAVE_CUSTOMER_IN_EDIT_PARTY);
	}
	//End:Uma Khot:Valid Rating CR
	
	else
	{
		param.setAction(ICMSConstant.ACTION_SAVE_CUSTOMER_IN_EDIT);
	}
	

		/*	Sandeep Shinde have set status so as not to alter it as it gets set to null	*/
	trxValue.setStagingCustomer(customer);

	ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
	ITrxResult result = controller.operate(trxValue, param);
	return (ICMSTrxResult) result;
}
catch (TransactionException e) {
	_context.setRollbackOnly();
	throw new CustomerException("Failed to update customer via workflow", e);
}
}
	/**
	 * Maker Resubmit customer update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @param customer is the ICMSCustomer object to be updated
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	public ICMSTrxResult makerResubmitUpdateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue,
			ICMSCustomer customer) throws CustomerException {
		try {
			if (null == trxValue) {
				throw new CustomerException("ICMSCustomerTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_RESUBMIT_UPDATE_CUSTOMER);

			trxValue.setStagingCustomer(customer);

			ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to re submit customer update via workflow", e);
		}
	}

	/**
	 * Maker Cancel customer update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	public ICMSTrxResult makerCancelUpdateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)
			throws CustomerException {
		try {
			if (null == trxValue) {
				throw new CustomerException("ICMSCustomerTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_CANCEL_UPDATE);

			ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to cancel customer update via workflow", e);
		}
	}

	/**
	 * Checker Approve customer update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	
	/*	@return ICMSTrxResult have been changed to ICMSCustomerTrxValue by Sandeep Shinde on 30-11-2011	*/
	
	public ICMSCustomerTrxValue checkerApproveUpdateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)
			throws CustomerException {
		try {
			if (null == trxValue) {
				throw new CustomerException("ICMSCustomerTrxValue is null!");
			}
			trxValue.setTrxContext(context);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			String loginUser = "";
			if(context.getUser()!=null && !"".equals(context.getUser()) 
					&& context.getUser().getTeamTypeMembership()!=null && !"".equals(context.getUser().getTeamTypeMembership())
					&& !"".equals(context.getUser().getTeamTypeMembership().getMembershipID())
					&& context.getUser().getTeamTypeMembership().getMembershipID()!=0L){
				
				loginUser = String.valueOf(context.getUser().getTeamTypeMembership().getMembershipID());
			}

			if(loginUser!=null && (String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_CHECKER).equals(loginUser)||String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_CHECKER_WFH).equals(loginUser)))
			{
				param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CUSTOMER_BRMAKER);
			}
			//Start:Uma Khot:Valid Rating CR
			else if(loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_PARTY_MAKER)))
			{
				param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CUSTOMER_PARTY);
			}
			//End:Uma Khot:Valid Rating CR
			else
			{
				param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CUSTOMER);
			}
			
			ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
//			return (ICMSTrxResult) result;				/* 	Commented by Sandeep Shinde on 30-11-2011	*/
			return (ICMSCustomerTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to approve customer update via workflow", e);
		}
	}

	/**
	 * Checker Reject customer update
	 * 
	 * @param context is the ITrxContext that represents a transaction context
	 *        object
	 * @param trxValue is ICMSCustomerTrxValue transaction object
	 * @return ICMSTrxResult
	 * @throws CustomerException on errors
	 */
	
	/*	@return ICMSTrxResult have been changed to ICMSCustomerTrxValue by Sandeep Shinde on 30-11-2011	*/
	
	public ICMSCustomerTrxValue checkerRejectUpdateCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)
			throws CustomerException {
		try {
			if (null == trxValue) {
				throw new CustomerException("ICMSCustomerTrxValue is null!");
			}
			trxValue.setTrxContext(context);
			String loginUser = String.valueOf(context.getUser().getTeamTypeMembership().getMembershipID());
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			if(loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_CHECKER))||loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_CHECKER_WFH)))
			{
				param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_CUSTOMER_BRMAKER);
			}
			//Start:Uma Khot:Valid Rating CR
			else if(loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_PARTY_MAKER)))
			{
				param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_CUSTOMER_PARTY);
			}
			//End:Uma Khot:Valid Rating CR
			else
			{
			param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_CUSTOMER);
			}
			ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
//			return (ICMSTrxResult) result;				/* 	Commented by Sandeep Shinde on 30-11-2011	*/
			return (ICMSCustomerTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to reject customer update via workflow", e);
		}
	}

	/**
	 * Retrieve the CMS Customer ID, given the SCI LE ID and SCI SubProfile ID
	 * 
	 * @return long
	 * @throws SearchDAOException if no records found
	 * @throws CustomerException on errors
	 */
	public long searchCustomerID(long leid, long subProfileID) throws CustomerException, SearchDAOException {
		try {
			SBCustomerManager mgr = getCustomerManager();
			return mgr.searchCustomerID(leid, subProfileID);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to search customer id, using le id [" + leid + "], sub profile id ["
					+ subProfileID + "]; throwing root cause", e.getCause());
		}
	}

	/**
	 * Retrieve the CMS Customer ID, given the CIF and Source system id If not
	 * found in local DB, will trigger search to host.
	 * 
	 * @return long
	 * @throws SearchDAOException if no records found
	 * @throws CustomerException on errors
	 */
	public ICMSCustomer getCustomerByCIFSource(String cifNumber, String sourceSystemId) throws CustomerException,
			SearchDAOException {
		try {
			SBCustomerManager mgr = getCustomerManager();
			return mgr.getCustomerByCIFNumber(cifNumber, sourceSystemId);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to retrieve customer using cif [" + cifNumber
					+ "], and source system id [" + sourceSystemId + "] via search to host; throwing root cause", e
					.getCause());
		}
	}

	
	public List getCustomerByCIFSource(String cifNumber, String sourceSystemId,String partyName,String partyId) throws CustomerException,
	SearchDAOException {
try {
	SBCustomerManager mgr = getCustomerManager();
	List cust = mgr.getCustomerByCIFNumber(cifNumber, sourceSystemId, partyName, partyId);
	return cust;
}
catch (RemoteException e) {
	throw new CustomerException("Failed to retrieve customer using cif [" + cifNumber
			+ "], and source system id [" + sourceSystemId + "] via search to host; throwing root cause", e
			.getCause());
}
}
	/**
	 * Retrieve the CMS Customer ID, given the CIF and Source system id
	 * 
	 * @return long
	 * @throws SearchDAOException if no records found
	 * @throws CustomerException on errors
	 */
	public ICMSCustomer getCustomerByCIFSourceFromDB(String cifNumber, String sourceSystemId) throws CustomerException,
			SearchDAOException {
		try {
			SBCustomerManager mgr = getCustomerManager();
			return mgr.getCustomerByCIFNumberFromDB(cifNumber, sourceSystemId);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to retrieve customer using cif [" + cifNumber
					+ "], and source system id [" + sourceSystemId + "] from local; throwing root cause", e.getCause());
		}
	}

	public ArrayList getMBlistByCBleId(long leid) throws CustomerException {
		try {
			SBCustomerManager mgr = getCustomerManager();
			return mgr.getMBlistByCBleId(leid);
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to retrieve main borrower list using co borrower cif [" + leid
					+ "]; throwing root cause", e.getCause());
		}
	}

	public List getJointBorrowerList(long limitProfileId) throws Exception {
		return new CustomerDAO().getJointBorrowerList(limitProfileId);
	}


    public List getOnlyJointBorrowerList(long limitProfileId) throws Exception {
        return new CustomerDAO().getOnlyJointBorrowerList(limitProfileId);
    }

    // ***************** Private Methods
	/**
	 * Get the SB for the actual storage of Customer
	 * 
	 * @return SBCustomerManager
	 * @throws Exception on errors
	 */
	private SBCustomerManager getCustomerManager() {
		SBCustomerManager home = (SBCustomerManager) BeanController.getEJB(ICMSJNDIConstant.SB_CUSTOMER_MGR_JNDI,
				SBCustomerManagerHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new IllegalArgumentException("Failed to retrieve customer remote home using jndi name ["
					+ ICMSJNDIConstant.SB_CUSTOMER_MGR_JNDI + "]");
		}
	}

	public SBCustomerManager getSBCustomerManager() throws Exception {
	    return getCustomerManager();
	}

	// ************* EJB Methods *****************

	/* EJB Methods */
	/**
	 * Called by the container to create a session bean instance. Its parameters
	 * typically contain the information the client uses to customize the bean
	 * instance for its use. It requires a matching pair in the bean class and
	 * its home interface.
	 */
	public void ejbCreate() {
	}

	/**
	 * A container invokes this method before it ends the life of the session
	 * object. This happens as a result of a client's invoking a remove
	 * operation, or when a container decides to terminate the session object
	 * after a timeout. This method is called with no transaction context.
	 */
	public void ejbRemove() {

	}

	/**
	 * The activate method is called when the instance is activated from its
	 * 'passive' state. The instance should acquire any resource that it has
	 * released earlier in the ejbPassivate() method. This method is called with
	 * no transaction context.
	 */
	public void ejbActivate() {

	}

	/**
	 * The passivate method is called before the instance enters the 'passive'
	 * state. The instance should release any resources that it can re-acquire
	 * later in the ejbActivate() method. After the passivate method completes,
	 * the instance must be in a state that allows the container to use the Java
	 * Serialization protocol to externalize and store away the instance's
	 * state. This method is called with no transaction context.
	 */
	public void ejbPassivate() {

	}

	/**
	 * Set the associated session context. The container calls this method after
	 * the instance creation. The enterprise Bean instance should store the
	 * reference to the context object in an instance variable. This method is
	 * called with no transaction context.
	 */
	public void setSessionContext(javax.ejb.SessionContext sc) {
		_context = sc;
	}

    /**
     * Search Customer Information Only
     *
     * @param criteria is of type CustomerSearchCriteria
     * @return SearchResult containing ICustomerSearchResult objects
     * @throws SearchDAOException on errors
     */
     public SearchResult searchCustomerInfoOnly(CustomerSearchCriteria criteria) throws SearchDAOException {
         try {
             SBCustomerManager mgr = getCustomerManager();
             return mgr.searchCustomerInfoOnly(criteria);
         }
         catch(SearchDAOException e) {
             throw e;
         }
         catch(Exception e) {
             e.printStackTrace();
             throw new SearchDAOException("Caught Exception!", e);
         }
     }

     /**
      * Method Added by Sandeep Shinde on 17-03-2011 so as to avoid Maker-Checker Operation
      * @param aCMSCustomer
      * @return ICMSCustomer
      * @throws CustomerException
      * @throws RemoteException
      */
     
	public ICMSCustomer createCustomerInfo(ICMSCustomer aCMSCustomer)throws CustomerException, RemoteException {
		DefaultLogger.debug(this,"Inside SBCustomerProxyBean");
		try {
			SBCustomerManager mgr = getCustomerManager();
			return mgr.createCustomer(aCMSCustomer);
		}
		catch (CustomerException e) {
			throw e;
		}
		catch (RemoteException e) {
			throw new CustomerException("Failed to update customer, id [" + aCMSCustomer.getCustomerID()
					+ "], throwing root cause", e.getCause());
		}
	}

	public void deleteCustomerInfo(ICMSCustomer aCMSCustomer)throws CustomerException,RemoteException {		
		try {
			long id = aCMSCustomer.getCustomerID();
			DefaultLogger.debug(this,"Inside SBCustomerProxyBean "+id);
			SBCustomerManager mgr = getCustomerManager();
			mgr.deleteCustomer(id);
		}
		catch (CustomerException e) {
			throw e;
		}	
		catch (RemoteException e) {
			throw e;
		}
	}

	 /**
     * Following Method Added by Sandeep Shinde 
     * 1.) checkerApproveCreateCustomer(ITrxContext context,ICMSCustomerTrxValue trxValue)
     * 2.) checkerRejectCreateCustomer(ITrxContext context,ICMSCustomerTrxValue trxValue)
     * 3.) getCustomerTrxValue(long id)
     * @since 29-03-2011 for purpose of Maker-Checker Operation
     * @param aCMSCustomer
     * @return ICMSCustomer
     * @throws CustomerException
     * @throws RemoteException
     */
	
	public ICMSCustomerTrxValue checkerApproveCreateCustomer(ITrxContext context,ICMSCustomerTrxValue trxValue) throws CustomerException,RemoteException {
		try {
			if (null == trxValue) {
				throw new CustomerException("ICMSCustomerTrxValue is null!");
			}
			trxValue.setTrxContext(context);
			String loginUser = "";
			
			if(context.getUser()!=null && !"".equals(context.getUser()) 
					&& context.getUser().getTeamTypeMembership()!=null && !"".equals(context.getUser().getTeamTypeMembership())
					&& !"".equals(context.getUser().getTeamTypeMembership().getMembershipID())
					&& context.getUser().getTeamTypeMembership().getMembershipID()!=0L){
				
				loginUser = String.valueOf(context.getUser().getTeamTypeMembership().getMembershipID());
			}
			
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			if(loginUser!=null && (String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_CHECKER).equals(loginUser)||String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_CHECKER_WFH).equals(loginUser)))
			{
				param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CUSTOMER_BRCHECKER);
			}
			//Start:Uma Khot:Valid Rating CR
			else if(loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_PARTY_MAKER)))
			{
				param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CUSTOMER_PARTY);
			}
			//End:Uma Khot:Valid Rating CR
			
			else{
				param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CUSTOMER);
			}
			ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSCustomerTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to approve customer Create via workflow", e);
		}
	}

	public ICMSCustomerTrxValue checkerRejectCreateCustomer(ITrxContext context,ICMSCustomerTrxValue trxValue) throws CustomerException,RemoteException { 
		try {
			if (null == trxValue) {
				throw new CustomerException("ICMSCustomerTrxValue is null!");
			}
			trxValue.setTrxContext(context);
			String loginUser = String.valueOf(context.getUser().getTeamTypeMembership().getMembershipID());
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			if(loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_CHECKER))||loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_CHECKER_WFH)))
			{
				param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_CREATE_CUSTOMER_BRCHECKER);
			}			
			//Start:Uma Khot:Valid Rating CR
			else if(loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_PARTY_MAKER)))
			{
				param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_CREATE_CUSTOMER_PARTY);
			}
			//End:Uma Khot:Valid Rating CR
			else
			{
				param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_CREATE_CUSTOMER);
			}
		

			ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSCustomerTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to reject customer Create via workflow", e);
		}
	}

	// shinde
	public ICMSCustomerTrxValue getCustomerTrxValue(long id) throws CustomerException, RemoteException {	
	    try {
				if (id == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					throw new CustomerException("Invalid CustomerId");
				}
		ICMSCustomerTrxValue trxValue = new OBCMSCustomerTrxValue();
        trxValue.setReferenceID(String.valueOf(id));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTOMER);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_CUSTOMER_ID);

		ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
		ITrxResult result = controller.operate(trxValue, param);
		return (ICMSCustomerTrxValue) result.getTrxValue();
		} catch (TrxParameterException e) {			
			e.printStackTrace();
		} catch (TrxControllerException e) {			
			e.printStackTrace();
		} catch (TransactionException e) {			
			e.printStackTrace();
		}
		return null;        
	}

	public ICMSCustomerTrxValue checkerApproveDeleteCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue) throws CustomerException,RemoteException  {
		
		try {
			if (null == trxValue) {
				throw new CustomerException("ICMSCustomerTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_DELETE_CUSTOMER);

			ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
//			return (ICMSTrxResult) result;				/* 	Commented by Sandeep Shinde on 30-11-2011	*/
			return (ICMSCustomerTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to approve customer update via workflow", e);
		}
		
	}

	public ICMSCustomerTrxValue checkerRejectDeleteCustomer(ITrxContext context, ICMSCustomerTrxValue trxValue)throws CustomerException,RemoteException  {
		try {
			if (null == trxValue) {
				throw new CustomerException("ICMSCustomerTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_DELETE_CUSTOMER);

			ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
//			return (ICMSTrxResult) result;				/* 	Commented by Sandeep Shinde on 30-11-2011	*/
			return (ICMSCustomerTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to reject customer update via workflow", e);
		}
	}

	public ICMSTrxResult prepareDeleteCustomer(ITrxContext context,ICMSCustomerTrxValue trxValue, ICMSCustomer customer)throws CustomerException,RemoteException  {	
		try {
			if (null == trxValue) {
				throw new CustomerException("ICMSCustomerTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_HOST_DELETE_CUSTOMER);

			//customer.setStatus("INACTIVE");
			trxValue.setStagingCustomer(customer);

			ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to update customer via workflow", e);
		}
	}

	public ICMSTrxResult makerResubmitDeleteCustomer(ITrxContext context,ICMSCustomerTrxValue trxValue, ICMSCustomer customer)throws CustomerException,RemoteException  {	
		try {
			if (null == trxValue) {
				throw new CustomerException("ICMSCustomerTrxValue is null!");
			}
			trxValue.setTrxContext(context);

			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_RESUBMIT_DELETE_CUSTOMER);

			//customer.setStatus("INACTIVE");
			trxValue.setStagingCustomer(customer);

			ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to update customer via workflow", e);
		}
	}
	public ICMSCustomerTrxValue makerCloseRejectedCustomer(ITrxContext context,ICMSCustomerTrxValue trxValue) throws CustomerException,RemoteException {		
		try {
			if (context == null) {
			    throw new SystemBankException("The ITrxContext is null!!!");
			}
			if (trxValue == null) {
			    throw new SystemBankException("The ICMSCustomerTrxValue to be updated is null!!!");
			}
			trxValue.setTrxContext(context);
			String loginUser = String.valueOf(context.getUser().getTeamTypeMembership().getMembershipID());
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			if(loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_MAKER))||loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_MAKER_WFH)))
			{
			param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_CUSTOMER_BRMAKER);
			}
			else{
				param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_CUSTOMER);
			}
			ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSCustomerTrxValue) result.getTrxValue();
			
		} catch (TrxParameterException e) {
			e.printStackTrace();
		} catch (TrxControllerException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	public ICMSCustomerTrxValue makerCloseDraftCustomer(ITrxContext context,ICMSCustomerTrxValue trxValue) throws CustomerException,RemoteException {		
		try {
			if (context == null) {
			    throw new SystemBankException("The ITrxContext is null!!!");
			}
			if (trxValue == null) {
			    throw new SystemBankException("The ICMSCustomerTrxValue to be updated is null!!!");
			}
			String loginUser = String.valueOf(context.getUser().getTeamTypeMembership().getMembershipID());
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			if(loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_MAKER))||loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_MAKER_WFH)))
			{
				param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_CUSTOMER_BRMAKER);
			}
			else
			{
			param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_CUSTOMER);
			}
			ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
			ITrxResult result = controller.operate(trxValue, param);
			return (ICMSCustomerTrxValue) result.getTrxValue();
			
		} catch (TrxParameterException e) {
			e.printStackTrace();
		} catch (TrxControllerException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	public ICMSCustomer getCustomerByLimitProfileId(long limitProfileId) throws CustomerException {
		return new CustomerDAO().getCustomerByLimitProfileId(limitProfileId);
	}
	
	public ICMSCustomerTrxValue createCustomerWithApprovalThroughWsdl(ITrxContext context,ICMSCustomerTrxValue trxValue) throws CustomerException {
		try {
			ICMSCustomerTrxValue trxResult = new OBCMSCustomerTrxValue();
			
			if (null == trxValue) {
				throw new CustomerException("ICMSCustomerTrxValue is null!");
			}

			MakerCheckerUserUtil mcUtil = (MakerCheckerUserUtil) BeanHouse.get("makerCheckerUserUtil");
			context = mcUtil.setContextForMaker();
			trxValue.setTrxContext(context);

			trxValue = createCustomer(trxValue);
			trxValue = getTrxCustomer(trxValue.getTransactionID());
			
			context = mcUtil.setContextForChecker();
						
			trxResult = checkerApproveCreateCustomer(context, trxValue);
			
			return trxResult;
		}
		catch (CustomerException e) {
			rollback();
			throw e;
		}
		catch (Exception e) {
			rollback();
			throw new CustomerException("Exception caught! " + e.toString(), e);
		}
	}
	
	public ICMSCustomerTrxValue updateCustomerWithApprovalThroughWsdl(ITrxContext context,ICMSCustomerTrxValue trxValue,ICMSCustomer obCMSCustomer) throws CustomerException {
		try {
			ICMSCustomerTrxValue trxResult = new OBCMSCustomerTrxValue();
			
			if (null == trxValue) {
				throw new CustomerException("ICMSCustomerTrxValue is null!");
			}
			
			ICMSTrxResult trxValueOut = new OBCMSTrxResult();
			
			MakerCheckerUserUtil mcUtil = (MakerCheckerUserUtil) BeanHouse.get("makerCheckerUserUtil");
			context = mcUtil.setContextForMaker();
			
			trxValueOut = makerUpdateCustomer(context,trxValue,obCMSCustomer);
			trxValue = (ICMSCustomerTrxValue) trxValueOut.getTrxValue();
			
			context = mcUtil.setContextForChecker();
			trxResult = checkerApproveUpdateCustomer(context,trxValue);
			
			return trxResult;
		}
		catch (CustomerException e) {
			rollback();
			throw e;
		}
		catch (Exception e) {
			rollback();
			throw new CustomerException("Exception caught! " + e.toString(), e);
		}
	}
	
	protected void rollback() throws CustomerException {
		try {
			_context.setRollbackOnly();
		}
		catch (Exception e) {
			throw new CustomerException(e.toString());
		}
	}
}