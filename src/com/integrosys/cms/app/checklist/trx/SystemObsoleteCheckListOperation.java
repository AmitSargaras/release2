/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/SystemObsoleteCheckListOperation.java,v 1.7 2006/03/23 08:14:28 hshii Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateException;
import com.integrosys.cms.app.cccertificate.proxy.CCCertificateProxyManagerFactory;
import com.integrosys.cms.app.cccertificate.proxy.ICCCertificateProxyManager;
import com.integrosys.cms.app.checklist.bus.ICCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows system to delete checklsit
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.7 $
 * @since $Date: 2006/03/23 08:14:28 $ Tag: $Name: $
 */
public class SystemObsoleteCheckListOperation extends AbstractCheckListTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public SystemObsoleteCheckListOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_OBSOLETE_CHECKLIST;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance To approve
	 * custodian doc trxs spawned at the checklist item level
	 * @param anITrxValue is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
		anITrxValue = super.preProcess(anITrxValue);
		ICheckListTrxValue trxValue = getCheckListTrxValue(anITrxValue);
		ICheckList checkList = trxValue.getStagingCheckList();
		checkList.setCheckListStatus(ICMSConstant.STATE_CHECKLIST_OBSOLETE);
		trxValue.setStagingCheckList(checkList);
		return trxValue;
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICheckListTrxValue trxValue = getCheckListTrxValue(anITrxValue);
		trxValue = createStagingCheckList(trxValue);
		trxValue = updateActualCheckList(trxValue);
		trxValue = super.updateCheckListTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Post Processing after the trx operation 1. To reset the customer CCC
	 * status for non borrower
	 * @param result - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult postProcess(ITrxResult result) throws TrxOperationException {
		result = super.postProcess(result);

		ICheckListTrxValue trxValue = (ICheckListTrxValue) result.getTrxValue();
		ICheckList staging = trxValue.getStagingCheckList();
		ICheckList actual = trxValue.getCheckList();
		trxValue.setSendNotificationInd(true);
		if (actual.getCheckListOwner() instanceof ICCCheckListOwner) {
			ICCCheckListOwner owner = (ICCCheckListOwner) actual.getCheckListOwner();
			try {
				DefaultLogger.debug(this, "status1: " + staging.getCheckListStatus());
				DefaultLogger.debug(this, "status2: " + actual.getCheckListStatus());
				//sendCCNotification(trxValue, true);       //@see CollaborationTaskSystemCloseNotificationInterceptor
				ICCCertificateProxyManager proxy = CCCertificateProxyManagerFactory.getCCCertificateProxyManager();
				proxy.systemCloseCCC(owner.getSubOwnerType(), owner.getLimitProfileID(), owner.getSubOwnerID());
				return result;
			}
			catch (CCCertificateException ex) {
				throw new TrxOperationException("Caught CCCertificateException in postProcess", ex);
			}
		}
		else {
			// CMS-1980 : Nofication to IN COUNTRY required for both DELETED and
			// OBSOLETE checklist.
			//sendCollateralNotification(trxValue, true);    //@see CollaborationTaskSystemCloseNotificationInterceptor
		}
		return result;
	}
	/*
	 * private void sendReadyDeleteCCNotification(ICheckListTrxValue
	 * anICheckListTrxValue) throws TrxOperationException { try {
	 * ICCCheckListOwner owner =
	 * (ICCCheckListOwner)anICheckListTrxValue.getCheckList
	 * ().getCheckListOwner(); ILimitProfile limitProfile = null; String[]
	 * countryList = new String[1]; String orgCode = null; String segment =
	 * null; ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
	 * ICMSCustomer customer = null; // for main borrower/ coborrower/ pledgor
	 * if (owner.getLimitProfileID() != ICMSConstant.LONG_INVALID_VALUE) {
	 * limitProfile = anICheckListTrxValue.getTrxContext().getLimitProfile();
	 * countryList[0] = limitProfile.getOriginatingLocation().getCountryCode();
	 * orgCode = limitProfile.getOriginatingLocation().getOrganisationCode();
	 * customer = custProxy.getCustomer(limitProfile.getCustomerID()); segment =
	 * customer.getCMSLegalEntity().getCustomerSegment(); } else { // for non
	 * borrower customer = custProxy.getCustomer(owner.getSubOwnerID()); segment
	 * = customer.getCMSLegalEntity().getCustomerSegment(); countryList[0] =
	 * customer.getOriginatingLocation().getCountryCode(); }
	 * OBCCCollaborationTaskInfo info = new OBCCCollaborationTaskInfo();
	 * info.setLeID(customer.getCMSLegalEntity().getLEReference());
	 * info.setLeName(customer.getCMSLegalEntity().getLegalName());
	 * info.setCustomerCategory(owner.getSubOwnerType());
	 * info.setCustomerID(String.valueOf(owner.getSubOwnerID()));
	 * info.setCCRemarks("");
	 * info.setOrgCode(anICheckListTrxValue.getCheckList()
	 * .getCheckListLocation().getOrganisationCode()); String checklistCountry =
	 * anICheckListTrxValue
	 * .getCheckList().getCheckListLocation().getCountryCode(); if
	 * (!countryList[0].equals(checklistCountry)) { if
	 * (ICMSConstant.CHECKLIST_CO_BORROWER.equals(owner.getSubOwnerType()) ||
	 * ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(owner.getSubOwnerType()) ||
	 * ICMSConstant.CHECKLIST_PLEDGER.equals(owner.getSubOwnerType())) {
	 * info.setOriginatingOrganisation(orgCode);
	 * info.setBcaBkgCountry(limitProfile
	 * .getOriginatingLocation().getCountryCode());
	 * 
	 * if (ICMSConstant.CHECKLIST_CO_BORROWER.equals(owner.getSubOwnerType())) {
	 * customer = custProxy.getCustomer(owner.getSubOwnerID());
	 * info.setCoborrowerPledgorLeName
	 * (customer.getCMSLegalEntity().getLegalName());
	 * info.setCoborrowerPledgorID(customer.getLegalEntity().getLegalName()); }
	 * else if (ICMSConstant.CHECKLIST_PLEDGER.equals(owner.getSubOwnerType()))
	 * { ICollateralProxy colProxy = CollateralProxyFactory.getProxy(); IPledgor
	 * pledgor = colProxy.getPledgor(owner.getSubOwnerID());
	 * info.setCoborrowerPledgorLeName(pledgor.getPledgorName());
	 * info.setCoborrowerPledgorID
	 * (String.valueOf(pledgor.getSysGenPledgorID())); } } else { //non borrower
	 * //customer = custProxy.getCustomer(owner.getSubOwnerID());
	 * info.setDomicileCountry(checklistCountry);
	 * info.setGovernLaw(getSBCheckListTemplateBusManager().getLaw(checklistCountry));
	 * info.setOriginatingOrganisation(info.getOrgCode()); }
	 * 
	 * info.setDomicileCountry(checklistCountry);
	 * info.setGovernLaw(getSBCheckListTemplateBusManager().getLaw(checklistCountry));
	 * //
	 * info.setGovernLaw(getSBCheckListTemplateBusManager().getLaw(info.getDomicileCountry
	 * ())); info.setOriginatingCountry(countryList[0]);
	 * info.setSegment(segment); ArrayList list = new ArrayList();
	 * list.add(info); list.add(""); list.add(constructRuleParam());
	 * CCCollaborationTaskListener listener = new CCCollaborationTaskListener();
	 * listener.fireEvent(EVENT_CC_CHECKLIST_DELETION_READY, list); } }
	 * catch(CustomerException ex) { throw new TrxOperationException(ex); }
	 * catch(Exception ex) { ex.printStackTrace(); throw new
	 * TrxOperationException("General Exception in sendCollateralNotification" +
	 * ex.toString()); } }
	 * 
	 * private void sendReadyDeleteCollateralNotification(ICheckListTrxValue
	 * anICheckListTrxValue) throws TrxOperationException { try {
	 * DefaultLogger.debug(this, "Enter"); ICollateralProxy colProxy =
	 * CollateralProxyFactory.getProxy(); ICollateralCheckListOwner owner =
	 * (ICollateralCheckListOwner
	 * )anICheckListTrxValue.getCheckList().getCheckListOwner();
	 * //DefaultLogger.debug(this, "LimitProfileID: " +
	 * owner.getLimitProfileID()); String segment = null; String[] countryList =
	 * new String[1]; ILimitProfile limitProfile =
	 * anICheckListTrxValue.getTrxContext().getLimitProfile(); ICollateral
	 * collateral = colProxy.getCollateral(owner.getCollateralID(), false);
	 * countryList[0] = limitProfile.getOriginatingLocation().getCountryCode();
	 * if (!countryList[0].equals(collateral.getCollateralLocation())) {
	 * 
	 * ICustomerProxy custProxy = CustomerProxyFactory.getProxy(); ICMSCustomer
	 * customer = custProxy.getCustomer(limitProfile.getCustomerID()); segment =
	 * customer.getCMSLegalEntity().getCustomerSegment();
	 * OBCollaborationTaskInfo info = new OBCollaborationTaskInfo();
	 * info.setLeID(customer.getCMSLegalEntity().getLEReference());
	 * info.setLeName(customer.getCMSLegalEntity().getLegalName());
	 * info.setSecurityID(String.valueOf(collateral.getSCISecurityID()));
	 * info.setSecuritySubTypeID
	 * (collateral.getCollateralSubType().getSubTypeCode());
	 * info.setSecurityLocation(collateral.getCollateralLocation());
	 * info.setOriginatingCountry(countryList[0]); info.setSegment(segment);
	 * info.setSecurityRemarks("");
	 * info.setBcaBkgCountry(limitProfile.getOriginatingLocation
	 * ().getCountryCode()); ArrayList list = new ArrayList(); list.add(info);
	 * list.add(""); list.add(constructRuleParam()); CollaborationTaskListener
	 * listener = new CollaborationTaskListener();
	 * listener.fireEvent(EVENT_COL_CHECKLIST_DELETION_READY, list); } }
	 * catch(CustomerException ex) { throw new TrxOperationException(ex); }
	 * catch(CollateralException ex) { throw new TrxOperationException(ex); }
	 * catch(Exception ex) { ex.printStackTrace(); throw new
	 * TrxOperationException("General Exception in sendCollateralNotification" +
	 * ex.toString()); } }
	 */

	/**
	 * This method constructs the Rule Param based on the input bussiness object
	 * passed in..
	 */
	/*
	 * public IRuleParam constructRuleParam() { OBDateRuleParam param = new
	 * OBDateRuleParam(); param.setRuleID("R_CHECKLIST_DELETION");
	 * param.setSysDate(DateUtil.getDate()); param.setRuleNum(-1); return param;
	 * }
	 */
}
