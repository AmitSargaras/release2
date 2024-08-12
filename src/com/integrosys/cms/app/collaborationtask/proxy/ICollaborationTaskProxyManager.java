/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/proxy/ICollaborationTaskProxyManager.java,v 1.11 2006/04/13 05:18:22 jzhai Exp $
 */
package com.integrosys.cms.app.collaborationtask.proxy;

//java
import java.util.HashMap;

import com.integrosys.cms.app.collaborationtask.bus.CCTaskSearchCriteria;
import com.integrosys.cms.app.collaborationtask.bus.CCTaskSearchResult;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskException;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskNotAllowedException;
import com.integrosys.cms.app.collaborationtask.bus.ICCTask;
import com.integrosys.cms.app.collaborationtask.bus.ICollateralTask;
import com.integrosys.cms.app.collaborationtask.trx.ICCTaskTrxValue;
import com.integrosys.cms.app.collaborationtask.trx.ICollateralTaskTrxValue;
import com.integrosys.cms.app.common.IContext;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This interface defines the list of attributes that will be available to the
 * generation of a collaboration task
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2006/04/13 05:18:22 $ Tag: $Name: $
 */
public interface ICollaborationTaskProxyManager {
	/**
	 * Check if there is any pending collateral task trx
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @param aCollateralLocation of String type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws CollaborationTaskException on errors
	 */
	public boolean hasPendingCollateralTaskTrx(long aLimitProfileID, long aCollateralID, String aCollateralLocation)
			throws CollaborationTaskException;

	/**
	 * Check if there is any pending CC task trx
	 * @param aLimitProfileID of long type
	 * @param aCustomerCategory of String type
	 * @param aCustomerID of long type
	 * @param aDomicileCtry of String type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws CollaborationTaskException on errors
	 */
	public boolean hasPendingCCTaskTrx(long aLimitProfileID, String aCustomerCategory, long aCustomerID,
			String aDomicileCtry) throws CollaborationTaskException;

	/**
	 * Get the collateral summary list
	 * @param anIContext of IContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @return HashMap - the will contain the collateral summary list and the
	 *         whether collaboration is allowed or not
	 * @throws CollaborationTaskNotAllowedException, CollaborationTaskException
	 */
	public HashMap getCollateralSummaryList(IContext anIContext, ILimitProfile anILimitProfile)
			throws CollaborationTaskNotAllowedException, CollaborationTaskException;

	/**
	 * Get the CC summary list
	 * @param anIContext of IContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @return HashMap - the will contain the CC summary list and the whether
	 *         collaboration is allowed or not
	 * @throws CollaborationTaskNotAllowedException, CollaborationTaskException
	 */
	public HashMap getCCSummaryList(IContext anIContext, ILimitProfile anILimitProfile)
			throws CollaborationTaskNotAllowedException, CollaborationTaskException;

	/**
	 * Get the CC summary list for non borrower
	 * @param anIContext of IContext type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return HashMap - the will contain the CC summary list and the whether
	 *         collaboration is allowed or not
	 * @throws CollaborationTaskNotAllowedException, CollaborationTaskException
	 */
	public HashMap getCCSummaryList(IContext anIContext, ICMSCustomer anICMSCustomer)
			throws CollaborationTaskNotAllowedException, CollaborationTaskException;

	/**
	 * Get the collateral task trx value using the limitprofile id, collateral
	 * ID and collateral location
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @param aCollateralLocation of String type
	 * @return ICollateralTaskTrxValue - the trx value of the collateral task
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue getCollateralTaskTrxValue(long aLimitProfileID, long aCollateralID,
			String aCollateralLocation) throws CollaborationTaskException;

	/**
	 * Get the cc task trx value using the limitprofile id, customer type,
	 * customer id and the country
	 * @param aLimitProfileID of long type
	 * @param aCustomerCategory of String type
	 * @param aCustomerID of long type
	 * @param aDomicileCtry of String type
	 * @return ICCTaskTrxValue - the trx value of the CC task
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue getCCTaskTrxValue(long aLimitProfileID, String aCustomerCategory, long aCustomerID,
			String aDomicileCtry) throws CollaborationTaskException;

	/**
	 * Get the collateral task trx value using the trx ID
	 * @param aTrxID of String type
	 * @return ICollateralTaskTrxValue - the collateral task trx value
	 * @throws CollaborationTaskException
	 */
	public ICollateralTaskTrxValue getCollateralTaskByTrxID(String aTrxID) throws CollaborationTaskException;

	/**
	 * Get the CC task trx value using the trx ID
	 * @param aTrxID of String type
	 * @return ICCTaskTrxValue - the CC task trx value
	 * @throws CollaborationTaskException
	 */
	public ICCTaskTrxValue getCCTaskByTrxID(String aTrxID) throws CollaborationTaskException;

	/**
	 * Maker create the collaboration task
	 * @param anITrxContext of ITrxContext type
	 * @param anICollateralTask of ICollateralTask type
	 * @return ICollateralTaskTrxValue - the generates Collateral Task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue makerCreateCollaborationTask(ITrxContext anITrxContext,
			ICollateralTask anICollateralTask) throws CollaborationTaskException;

	/**
	 * Maker update the collaboration task
	 * @param anITrxContext of ITrxContext type
	 * @param anICollateralTaskTrxValue of ICollateralTaskTrxValue type
	 * @param anICollateralTask of ICollateralTask type
	 * @return ICollateralTaskTrxValue - the generates Collateral Task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue makerUpdateCollaborationTask(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue, ICollateralTask anICollateralTask)
			throws CollaborationTaskException;

	/**
	 * Maker create the collaboration task
	 * @param anITrxContext of ITrxContext type
	 * @param anICCTask of ICCTask type
	 * @return ICCTaskTrxValue - the generates CC Task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue makerCreateCollaborationTask(ITrxContext anITrxContext, ICCTask anICCTask)
			throws CollaborationTaskException;

	/**
	 * Maker update the collaboration task
	 * @param anITrxContext of ITrxContext type
	 * @param anICCTaskTrxValue of ICCTaskTrxValue type
	 * @param anICCTask of ICCTask type
	 * @return ICCTaskTrxValue - the generates CC Task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue makerUpdateCollaborationTask(ITrxContext anITrxContext, ICCTaskTrxValue anICCTaskTrxValue,
			ICCTask anICCTask) throws CollaborationTaskException;

	public ICCTaskTrxValue makerRejectCollaborationTask(ITrxContext anITrxContext, ICCTaskTrxValue anICCTaskTrxValue,
			ICCTask anICCTask) throws CollaborationTaskException;

	public ICollateralTaskTrxValue makerRejectCollaborationTask(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue, ICollateralTask anICollateralTask)
			throws CollaborationTaskException;

	/**
	 * Checker approve the collaboration Task
	 * @param anITrxContext of ITrxContext type
	 * @param ICollateralTaskTrxValue of ICollateralTaskTrxValue type
	 * @return ICollateralTaskTrxValue - the generated Collateral Task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue checkerApproveCollaborationTask(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue) throws CollaborationTaskException;

	/**
	 * Checker approve the collaboration Task
	 * @param anITrxContext of ITrxContext type
	 * @param ICCTaskTrxValue of ICCTaskTrxValue type
	 * @return ICCTaskTrxValue - the generated CC Task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue checkerApproveCollaborationTask(ITrxContext anITrxContext, ICCTaskTrxValue anICCTaskTrxValue)
			throws CollaborationTaskException;

	/**
	 * Checker reject the collaboration Task
	 * @param anITrxContext of ITrxContext type
	 * @param anICollateralTaskTrxValue of ICollateralTaskTrxValue type
	 * @return ICollateralTaskTrxValue - the Collateral Task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue checkerRejectCollaborationTask(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue) throws CollaborationTaskException;

	/**
	 * Checker reject the collaboration Task
	 * @param anITrxContext of ITrxContext type
	 * @param anICCTaskTrxValue of ICCTaskTrxValue type
	 * @return ICCTaskTrxValue - the CC Task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue checkerRejectCollaborationTask(ITrxContext anITrxContext, ICCTaskTrxValue anICCTaskTrxValue)
			throws CollaborationTaskException;

	/**
	 * Maker edit the rejected collaboration Task
	 * @param anITrxContext of ITrxContext type
	 * @param anICollateralTaskTrxValue of ICollateralTaskTrxValue
	 * @param anICollateralTask of ICollateralTask
	 * @return ICollateralTaskTrxValue - the Collateral task trx
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue makerEditRejectedCollaborationTask(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue, ICollateralTask anICollateralTask)
			throws CollaborationTaskException;

	/**
	 * Maker edit the rejected collaboration Task
	 * @param anITrxContext of ITrxContext type
	 * @param anICCTaskTrxValue of ICCTaskTrxValue
	 * @param anICCTask of ICCTask
	 * @return ICCTaskTrxValue - the CC task trx
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue makerEditRejectedCollaborationTask(ITrxContext anITrxContext,
			ICCTaskTrxValue anICCTaskTrxValue, ICCTask anICCTask) throws CollaborationTaskException;

	/**
	 * Make close the rejected collaboration Task
	 * @param anITrxContext of ITrxContext type
	 * @param anICollateralTaskTrxValue of ICollateralTaskTrxValue type
	 * @return ICollateralTaskTrxValue - the Collateral task trx
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue makerCloseRejectedCollaborationTask(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue) throws CollaborationTaskException;

	/**
	 * Make close the rejected collaboration Task
	 * @param anITrxContext of ITrxContext type
	 * @param anICCTaskTrxValue of ICCTaskTrxValue type
	 * @return ICCTaskTrxValue - the CC task trx
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue makerCloseRejectedCollaborationTask(ITrxContext anITrxContext,
			ICCTaskTrxValue anICCTaskTrxValue) throws CollaborationTaskException;

	/**
	 * System close the collateral task for a collateral
	 * @param anITrxContext of ITrxContext type
	 * @param aCollateralID of long type
	 * @throws CollaborationTaskException
	 */
	public void systemCloseCollateralCollaborationTaskTrx(ITrxContext anITrxContext, long aCollateralID)
			throws CollaborationTaskException;

	/**
	 * Get the list of CC Task that satisfy the search criteria
	 * @param aCriteria of CCTaskSearchCriteria type
	 * @return CCTaskSearchResult[] - the list of cc task result that satisfy
	 *         the criteria
	 * @throws CollaborationTaskException
	 */
	public CCTaskSearchResult[] getCCTask(CCTaskSearchCriteria aCriteria) throws CollaborationTaskException;

	/**
	 * System update the CC Collaboration task transaction
	 * @param aLimitProfileID of long type
	 * @param aCustomerCategory of String type
	 * @param aCustomerID of long type
	 * @throws CollaborationTaskException on errors
	 */
	public void systemUpdateCCCollaborationTask(long aLimitProfileID, String aCustomerCategory, long aCustomerID)
			throws CollaborationTaskException;

	/**
	 * System update the Collateral Collaboration Task transaction
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @throws CollaborationTaskException on errors
	 */
	public void systemUpdateCollateralCollaborationTask(long aLimitProfileID, long aCollateralID)
			throws CollaborationTaskException;

	/**
	 * System update the Collateral Collaboration Task transaction
	 * @param aCollateralID of long type
	 * @throws CollaborationTaskException on errors
	 */
	public void systemUpdateCollateralCollaborationTask(long aCollateralID) throws CollaborationTaskException;
}
