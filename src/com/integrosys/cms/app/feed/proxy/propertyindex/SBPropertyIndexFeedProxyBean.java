/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/propertyindex/SBPropertyIndexFeedProxyBean.java,v 1.5 2005/01/12 07:44:39 hshii Exp $
 */
package com.integrosys.cms.app.feed.proxy.propertyindex;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedBusManager;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedGroup;
import com.integrosys.cms.app.feed.bus.propertyindex.PropertyIndexFeedBusManagerFactory;
import com.integrosys.cms.app.feed.bus.propertyindex.PropertyIndexFeedGroupException;
import com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.propertyindex.OBPropertyIndexFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.app.transaction.SBCMSTrxManagerHome;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/01/12 07:44:39 $ Tag: $Name: $
 * 
 * @ejb.bean name="SBPropertyIndexFeedProxy"
 *           jndi-name="SBPropertyIndexFeedProxyHome"
 *           local-jndi-name="SBPropertyIndexFeedProxyLocalHome"
 *           view-type="remote" type="Stateless"
 * @ejb.transaction type="Required"
 */
public class SBPropertyIndexFeedProxyBean extends AbstractPropertyIndexFeedProxy implements SessionBean {

	public void setSessionContext(SessionContext context) throws EJBException, RemoteException {
		_context = context;
	}

	public void ejbRemove() throws EJBException, RemoteException {
		// To change body of implemented methods use Options | File Templates.
	}

	public void ejbActivate() throws EJBException, RemoteException {
		// To change body of implemented methods use Options | File Templates.
	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// To change body of implemented methods use Options | File Templates.
	}

	protected void rollback() {
		_context.setRollbackOnly();
	}

	/**
	 * @ejb.create-method view-type="remote"
	 */
	public void ejbCreate() {

	}

	/**
	 * This is essentially the same as makerUpdatePropertyIndexFeedGroup except
	 * that it triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the PropertyIndexFeedGroup
	 *        object
	 * 
	 * @ejb.interface-method view-type="remote"
	 */
	public IPropertyIndexFeedGroupTrxValue makerUpdateRejectedPropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue) throws PropertyIndexFeedGroupException {

		checkParameters(anITrxContext, aTrxValue);

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_PROPERTY_INDEX_FEED_GROUP);
		return operate(aTrxValue, param);

	}

	/**
	 * Cancels an initiated transaction on a PropertyIndexFeedGroup to return it
	 * to last 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the PropertyIndexFeedGroup
	 *        object
	 * 
	 * @ejb.interface-method view-type="remote"
	 */
	public IPropertyIndexFeedGroupTrxValue makerCloseRejectedPropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue) throws PropertyIndexFeedGroupException {

		checkParameters(anITrxContext, aTrxValue);

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_PROPERTY_INDEX_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Cancels an initiated transaction on a PropertyIndexFeedGroup to return it
	 * to last 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the PropertyIndexFeedGroup
	 *        object
	 * 
	 * @ejb.interface-method view-type="remote"
	 */
	public IPropertyIndexFeedGroupTrxValue makerCloseDraftPropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue) throws PropertyIndexFeedGroupException {

		checkParameters(anITrxContext, aTrxValue);

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_PROPERTY_INDEX_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Gets the one and only property index feed group.
	 * @return The property index feed group.
	 * @throws PropertyIndexFeedGroupException on errors.
	 * 
	 * @ejb.interface-method view-type="remote"
	 */
	public IPropertyIndexFeedGroupTrxValue getPropertyIndexFeedGroup(String subType)
			throws PropertyIndexFeedGroupException {

		DefaultLogger.debug(this, "subType = " + subType);

		IPropertyIndexFeedBusManager mgr = PropertyIndexFeedBusManagerFactory.getActualPropertyIndexFeedBusManager();

		IPropertyIndexFeedGroup group = mgr.getPropertyIndexFeedGroup(ICMSConstant.PROPERTY_INDEX_FEED_GROUP_TYPE,
				subType);

		if (group == null) {
			// Cannot find the property index feed group.
			PropertyIndexFeedGroupException e = new PropertyIndexFeedGroupException(
					"Cannot find the property index feed group.");
			e.setErrorCode(IPropertyIndexFeedProxy.NO_FEED_GROUP);
			throw e;
		}

		IPropertyIndexFeedGroupTrxValue vv = new OBPropertyIndexFeedGroupTrxValue();
		vv.setReferenceID(String.valueOf(group.getPropertyIndexFeedGroupID()));
		vv.setPropertyIndexFeedGroup(group); // important to set!

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_PROPERTY_INDEX_FEED_GROUP);

		return operate(vv, param);
	}

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the
	 *        PropertyIndexFeedGroup object
	 * 
	 * @ejb.interface-method view-type="remote"
	 */
	public IPropertyIndexFeedGroupTrxValue makerSubmitPropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aFeedGroupTrxValue, IPropertyIndexFeedGroup aFeedGroup)
			throws PropertyIndexFeedGroupException {

		checkParameters(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_PROPERTY_INDEX_FEED_GROUP);

		return operate(aFeedGroupTrxValue, param);
	}

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws PropertyIndexFeedGroupException
	 * 
	 * @ejb.interface-method view-type="remote"
	 */
	public IPropertyIndexFeedGroupTrxValue makerSubmitRejectedPropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue) throws PropertyIndexFeedGroupException {
		checkParameters(anITrxContext, aTrxValue);

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_PROPERTY_INDEX_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the
	 *        PropertyIndexFeedGroup object
	 * 
	 * @ejb.interface-method view-type="remote"
	 */
	public IPropertyIndexFeedGroupTrxValue makerUpdatePropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aFeedGroupTrxValue, IPropertyIndexFeedGroup aFeedGroup)
			throws PropertyIndexFeedGroupException {

		checkParameters(anITrxContext, aFeedGroupTrxValue, aFeedGroup);

		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_PROPERTY_INDEX_FEED_GROUP);
		return operate(aFeedGroupTrxValue, param);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the PropertyIndexFeedGroup
	 *        object
	 * 
	 * @ejb.interface-method view-type="remote"
	 */
	public IPropertyIndexFeedGroupTrxValue checkerApprovePropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue) throws PropertyIndexFeedGroupException {

		checkParameters(anITrxContext, aTrxValue);

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_PROPERTY_INDEX_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the PropertyIndexFeedGroup
	 *        object
	 * 
	 * @ejb.interface-method view-type="remote"
	 */

	public IPropertyIndexFeedGroupTrxValue checkerRejectPropertyIndexFeedGroup(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue) throws PropertyIndexFeedGroupException {

		checkParameters(anITrxContext, aTrxValue);

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_PROPERTY_INDEX_FEED_GROUP);
		return operate(aTrxValue, param);

	}

	/**
	 * Helper method to perform the document item transactions.
	 * @param aTrxValue - IDocumentItemTrxValue
	 * @param anOBCMSTrxParameter - OBCMSTrxParameter
	 * @return IPropertyIndexFeedGroupTrxValue - the trx interface
	 */
	protected IPropertyIndexFeedGroupTrxValue operate(IPropertyIndexFeedGroupTrxValue aTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter) throws PropertyIndexFeedGroupException {

		ICMSTrxResult result = operateForResult(aTrxValue, anOBCMSTrxParameter);
		return (IPropertyIndexFeedGroupTrxValue) result.getTrxValue();
	}

	// helper method
	protected SBCMSTrxManager getTrxManager() throws TrxOperationException {
		SBCMSTrxManager mgr = (SBCMSTrxManager) BeanController.getEJB(ICMSJNDIConstant.SB_CMS_TRX_MGR_HOME,
				SBCMSTrxManagerHome.class.getName());
		if (null == mgr) {
			throw new TrxOperationException("SBCMSTrxManager is null!");
		}
		else {
			return mgr;
		}
	}

	private void checkParameters(ITrxContext anITrxContext, IPropertyIndexFeedGroupTrxValue aFeedGroupTrxValue,
			IPropertyIndexFeedGroup aFeedGroup) throws PropertyIndexFeedGroupException {

		// Do not check for trx context.

		// if (anITrxContext == null) {
		// throw new
		// PropertyIndexFeedGroupException("The anITrxContext is null!!!");
		// }
		if (aFeedGroupTrxValue == null) {
			throw new PropertyIndexFeedGroupException("The anIPropertyIndexFeedGroupTrxValue to be updated is null!!!");
		}
		if (aFeedGroup == null) {
			throw new PropertyIndexFeedGroupException("The IFeedGroup to be updated is null !!!");
		}
	}

	private void checkParameters(ITrxContext anITrxContext, IPropertyIndexFeedGroupTrxValue aFeedGroupTrxValue)
			throws PropertyIndexFeedGroupException {

		// Do not check for trx context.

		// if (anITrxContext == null) {
		// throw new
		// PropertyIndexFeedGroupException("The anITrxContext is null!!!");
		// }
		if (aFeedGroupTrxValue == null) {
			throw new PropertyIndexFeedGroupException("The anIPropertyIndexFeedGroupTrxValue to be updated is null!!!");
		}
	}

	/**
	 * Get the transaction value containing PropertyIndexFeedGroup This method
	 * will create a transaction if it is not already present, when this module
	 * is first used by user and system is first setup.
	 * 
	 * @ejb.interface-method view-type="remote"
	 */
	public IPropertyIndexFeedGroupTrxValue getPropertyIndexFeedGroup(long groupID)
			throws PropertyIndexFeedGroupException {
		return null; // To change body of implemented methods use Options | File
						// Templates.
	}

	/**
	 * Get the transaction value containing PropertyIndexFeedGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IPropertyIndexFeedGroupTrxValue
	 * 
	 * @ejb.interface-method view-type="remote"
	 */
	public IPropertyIndexFeedGroupTrxValue getPropertyIndexFeedGroupByTrxID(long trxID)
			throws PropertyIndexFeedGroupException {
		IPropertyIndexFeedGroupTrxValue trxValue = new OBPropertyIndexFeedGroupTrxValue();
		trxValue.setTransactionID(String.valueOf(trxID));
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_PROPERTY_INDEX_FEED_GROUP);
		return operate(trxValue, param);
	}

	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;
}
