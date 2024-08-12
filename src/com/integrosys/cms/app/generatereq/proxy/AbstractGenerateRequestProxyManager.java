/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/proxy/AbstractGenerateRequestProxyManager.java,v 1.15 2003/12/18 06:12:20 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.proxy;

//java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.common.bus.BaseCurrency;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.generatereq.bus.DeferralRequestSearchCriteria;
import com.integrosys.cms.app.generatereq.bus.GenerateRequestException;
import com.integrosys.cms.app.generatereq.bus.IDeferralRequest;
import com.integrosys.cms.app.generatereq.bus.IDeferralRequestItem;
import com.integrosys.cms.app.generatereq.bus.IRequest;
import com.integrosys.cms.app.generatereq.bus.IRequestCollateralInfo;
import com.integrosys.cms.app.generatereq.bus.IRequestDescription;
import com.integrosys.cms.app.generatereq.bus.IRequestHeader;
import com.integrosys.cms.app.generatereq.bus.IRequestItem;
import com.integrosys.cms.app.generatereq.bus.IRequestLimitInfo;
import com.integrosys.cms.app.generatereq.bus.IRequestSubject;
import com.integrosys.cms.app.generatereq.bus.IWaiverRequest;
import com.integrosys.cms.app.generatereq.bus.IWaiverRequestItem;
import com.integrosys.cms.app.generatereq.bus.OBDeferralRequest;
import com.integrosys.cms.app.generatereq.bus.OBDeferralRequestItem;
import com.integrosys.cms.app.generatereq.bus.OBRequestCollateralInfo;
import com.integrosys.cms.app.generatereq.bus.OBRequestDescription;
import com.integrosys.cms.app.generatereq.bus.OBRequestHeader;
import com.integrosys.cms.app.generatereq.bus.OBRequestLimitInfo;
import com.integrosys.cms.app.generatereq.bus.OBRequestSubject;
import com.integrosys.cms.app.generatereq.bus.OBWaiverRequest;
import com.integrosys.cms.app.generatereq.bus.OBWaiverRequestItem;
import com.integrosys.cms.app.generatereq.bus.WaiverRequestSearchCriteria;
import com.integrosys.cms.app.generatereq.trx.GenerateRequestTrxControllerFactory;
import com.integrosys.cms.app.generatereq.trx.IDeferralRequestTrxValue;
import com.integrosys.cms.app.generatereq.trx.IWaiverRequestTrxValue;
import com.integrosys.cms.app.generatereq.trx.OBDeferralRequestTrxValue;
import com.integrosys.cms.app.generatereq.trx.OBWaiverRequestTrxValue;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * This abstract class will contains all the biz related logic that is
 * independent of any technology implementation such as EJB
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2003/12/18 06:12:20 $ Tag: $Name: $
 */
public abstract class AbstractGenerateRequestProxyManager implements IGenerateRequestProxyManager {
	public static final String WAIVER_REQ_SUBJECT_KEY = "waiver.subject";

	public static final String DEFERRAL_REQ_SUBJECT_KEY = "deferral.subject";

	/**
	 * Get a new waiver request for a limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @return IWaiverRequestTrxValue - the waiver request trx value
	 * @throws GenerateRequestException on errors
	 */
	public IWaiverRequestTrxValue getNewWaiverRequestTrxValue(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer)
			throws GenerateRequestException {
		if (hasPendingWaiverRequestTrxValue(anILimitProfile, anICMSCustomer)) {
			GenerateRequestException exp = new GenerateRequestException();
			exp.setErrorCode(ICMSErrorCodes.WAIVER_PENDING_TRX_EXIST);
			throw exp;
		}

		IRequestItem[] reqItemList = getWaiverRequestItemList(anILimitProfile);
		if ((reqItemList == null) || (reqItemList.length == 0)) {
			GenerateRequestException exp = new GenerateRequestException();
			exp.setErrorCode(ICMSErrorCodes.WAIVER_NOT_REQUIRED);
			throw exp;
		}
		IWaiverRequest waiverRequest = new OBWaiverRequest();
		processRequest(anILimitProfile, anICMSCustomer, waiverRequest, true);
		waiverRequest.setRequestItemList(reqItemList);
		IWaiverRequestTrxValue trxValue = new OBWaiverRequestTrxValue();
		trxValue.setStagingWaiverRequest(waiverRequest);
		return trxValue;
	}

	/**
	 * Get a new waiver request for a non borrower
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return IWaiverRequestTrxValue - the waiver request trx value
	 * @throws GenerateRequestException on errors
	 */
	public IWaiverRequestTrxValue getNewWaiverRequestTrxValue(ICMSCustomer anICMSCustomer)
			throws GenerateRequestException {
		if (hasPendingWaiverRequestTrxValue(anICMSCustomer)) {
			GenerateRequestException exp = new GenerateRequestException();
			exp.setErrorCode(ICMSErrorCodes.WAIVER_PENDING_TRX_EXIST);
			throw exp;
		}

		IRequestItem[] reqItemList = getWaiverRequestItemList(anICMSCustomer);
		if ((reqItemList == null) || (reqItemList.length == 0)) {
			GenerateRequestException exp = new GenerateRequestException();
			exp.setErrorCode(ICMSErrorCodes.WAIVER_NOT_REQUIRED);
			throw exp;
		}
		IWaiverRequest waiverRequest = new OBWaiverRequest();
		processRequest(anICMSCustomer, waiverRequest, true);
		waiverRequest.setRequestItemList(reqItemList);
		IWaiverRequestTrxValue trxValue = new OBWaiverRequestTrxValue();
		trxValue.setStagingWaiverRequest(waiverRequest);
		return trxValue;
	}

	/**
	 * Get a new deferral request for a limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @return IDeferralRequestTrxValue - the deferral request trx value
	 * @throws GenerateRequestException on errors
	 */
	public IDeferralRequestTrxValue getNewDeferralRequestTrxValue(ILimitProfile anILimitProfile,
			ICMSCustomer anICMSCustomer) throws GenerateRequestException {
		if (hasPendingDeferralRequestTrxValue(anILimitProfile, anICMSCustomer)) {
			GenerateRequestException exp = new GenerateRequestException();
			exp.setErrorCode(ICMSErrorCodes.DEFERRAL_PENDING_TRX_EXIST);
			throw exp;
		}

		IRequestItem[] reqItemList = getDeferralRequestItemList(anILimitProfile);
		if ((reqItemList == null) || (reqItemList.length == 0)) {
			GenerateRequestException exp = new GenerateRequestException();
			exp.setErrorCode(ICMSErrorCodes.DEFERRAL_NOT_REQUIRED);
			throw exp;
		}
		IDeferralRequest deferralRequest = new OBDeferralRequest();
		processRequest(anILimitProfile, anICMSCustomer, deferralRequest, false);
		deferralRequest.setRequestItemList(reqItemList);
		IDeferralRequestTrxValue trxValue = new OBDeferralRequestTrxValue();
		trxValue.setStagingDeferralRequest(deferralRequest);
		return trxValue;
	}

	/**
	 * Get a new deferral request for a limit profile
	 * @param anILimitProfile of ILimitProfile type
	 * @return IDeferralRequestTrxValue - the deferral request trx value
	 * @throws GenerateRequestException on errors
	 */
	public IDeferralRequestTrxValue getNewDeferralRequestTrxValue(ICMSCustomer anICMSCustomer)
			throws GenerateRequestException {
		if (hasPendingDeferralRequestTrxValue(anICMSCustomer)) {
			GenerateRequestException exp = new GenerateRequestException();
			exp.setErrorCode(ICMSErrorCodes.DEFERRAL_PENDING_TRX_EXIST);
			throw exp;
		}

		IRequestItem[] reqItemList = getDeferralRequestItemList(anICMSCustomer);
		if ((reqItemList == null) || (reqItemList.length == 0)) {
			GenerateRequestException exp = new GenerateRequestException();
			exp.setErrorCode(ICMSErrorCodes.DEFERRAL_NOT_REQUIRED);
			throw exp;
		}
		IDeferralRequest deferralRequest = new OBDeferralRequest();
		processRequest(anICMSCustomer, deferralRequest, false);
		deferralRequest.setRequestItemList(reqItemList);
		IDeferralRequestTrxValue trxValue = new OBDeferralRequestTrxValue();
		trxValue.setStagingDeferralRequest(deferralRequest);
		return trxValue;
	}

	/**
	 * Get the waiver trx value using the trx ID
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aTrxID of String type
	 * @return IWaiverRequestTrxValue - the trx value of the waiver request
	 * @throws GenerateRequestException on errors
	 */
	public IWaiverRequestTrxValue getWaiverRequestTrxValue(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer,
			String aTrxID) throws GenerateRequestException {
		IWaiverRequestTrxValue trxValue = getWaiverRequestTrxValue(aTrxID);
		IWaiverRequest stageWaiverReq = trxValue.getStagingWaiverRequest();
		IWaiverRequest actWaiverReq = trxValue.getWaiverRequest();
		if (stageWaiverReq != null) {
			processRequest(anILimitProfile, anICMSCustomer, stageWaiverReq, true);
		}
		if (actWaiverReq != null) {
			processRequest(anILimitProfile, anICMSCustomer, actWaiverReq, true);
		}
		trxValue.setWaiverRequest(actWaiverReq);
		trxValue.setStagingWaiverRequest(stageWaiverReq);
		return trxValue;
	}

	/**
	 * Get the waiver trx value using the trx ID for non borrower
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aTrxID of String type
	 * @return IWaiverRequestTrxValue - the trx value of the waiver request
	 * @throws GenerateRequestException on errors
	 */
	public IWaiverRequestTrxValue getWaiverRequestTrxValue(ICMSCustomer anICMSCustomer, String aTrxID)
			throws GenerateRequestException {
		IWaiverRequestTrxValue trxValue = getWaiverRequestTrxValue(aTrxID);
		IWaiverRequest stageWaiverReq = trxValue.getStagingWaiverRequest();
		IWaiverRequest actWaiverReq = trxValue.getWaiverRequest();
		if (stageWaiverReq != null) {
			processRequest(anICMSCustomer, stageWaiverReq, true);
		}
		if (actWaiverReq != null) {
			processRequest(anICMSCustomer, actWaiverReq, true);
		}
		trxValue.setWaiverRequest(actWaiverReq);
		trxValue.setStagingWaiverRequest(stageWaiverReq);
		return trxValue;
	}

	/**
	 * Get the deferral trx value using the trx ID
	 * @param anILimitProfile of ILimitProfile type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aTrxID of String type
	 * @return IDeferralRequestTrxValue - the trx value of the deferral request
	 * @throws GenerateRequestException on errors
	 */
	public IDeferralRequestTrxValue getDeferralRequestTrxValue(ILimitProfile anILimitProfile,
			ICMSCustomer anICMSCustomer, String aTrxID) throws GenerateRequestException {
		IDeferralRequestTrxValue trxValue = getDeferralRequestTrxValue(aTrxID);
		IDeferralRequest stageDeferralReq = trxValue.getStagingDeferralRequest();
		IDeferralRequest actDeferralReq = trxValue.getDeferralRequest();
		if (stageDeferralReq != null) {
			processRequest(anILimitProfile, anICMSCustomer, stageDeferralReq, false);
		}
		if (actDeferralReq != null) {
			processRequest(anILimitProfile, anICMSCustomer, actDeferralReq, false);
		}
		trxValue.setDeferralRequest(actDeferralReq);
		trxValue.setStagingDeferralRequest(stageDeferralReq);
		return trxValue;
	}

	/**
	 * Get the deferral trx value using the trx ID for non borrower
	 * @param anICMSCustomer of ICMSCustomer type
	 * @param aTrxID of String type
	 * @return IDeferralRequestTrxValue - the trx value of the deferral request
	 * @throws GenerateRequestException on errors
	 */
	public IDeferralRequestTrxValue getDeferralRequestTrxValue(ICMSCustomer anICMSCustomer, String aTrxID)
			throws GenerateRequestException {
		IDeferralRequestTrxValue trxValue = getDeferralRequestTrxValue(aTrxID);
		IDeferralRequest stageDeferralReq = trxValue.getStagingDeferralRequest();
		IDeferralRequest actDeferralReq = trxValue.getDeferralRequest();
		if (stageDeferralReq != null) {
			processRequest(anICMSCustomer, stageDeferralReq, false);
		}
		if (actDeferralReq != null) {
			processRequest(anICMSCustomer, actDeferralReq, false);
		}
		trxValue.setDeferralRequest(actDeferralReq);
		trxValue.setStagingDeferralRequest(stageDeferralReq);
		return trxValue;
	}

	private void processRequest(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer, IRequest anIRequest,
			boolean isWaiverInd) throws GenerateRequestException {
		IRequestHeader reqHeader = getRequestHeader(anICMSCustomer);
		IRequestSubject reqSubject = null;
		if (isWaiverInd) {
			reqSubject = getRequestSubject(anICMSCustomer, WAIVER_REQ_SUBJECT_KEY);
		}
		else {
			reqSubject = getRequestSubject(anICMSCustomer, DEFERRAL_REQ_SUBJECT_KEY);
		}
		IRequestDescription reqDesc = getRequestDescription(anILimitProfile);
		anIRequest.setLimitProfileID(anILimitProfile.getLimitProfileID());
		anIRequest.setRequestHeader(reqHeader);
		anIRequest.setRequestSubject(reqSubject);
		anIRequest.setRequestDescription(reqDesc);
	}

	private void processRequest(ICMSCustomer anICMSCustomer, IRequest anIRequest, boolean isWaiverInd)
			throws GenerateRequestException {
		IRequestHeader reqHeader = getRequestHeader(anICMSCustomer);
		IRequestSubject reqSubject = null;
		if (isWaiverInd) {
			reqSubject = getRequestSubject(anICMSCustomer, WAIVER_REQ_SUBJECT_KEY);
		}
		else {
			reqSubject = getRequestSubject(anICMSCustomer, DEFERRAL_REQ_SUBJECT_KEY);
		}
		// IRequestDescription reqDesc = getRequestDescription(anICMSCustomer);
		anIRequest.setCustomerID(anICMSCustomer.getCustomerID());
		anIRequest.setRequestHeader(reqHeader);
		anIRequest.setRequestSubject(reqSubject);
		// anIRequest.setRequestDescription(reqDesc);
	}

	private boolean hasPendingWaiverRequestTrxValue(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer)
			throws GenerateRequestException {
		if (anILimitProfile == null) {
			throw new GenerateRequestException("The ILimitProfile is null !!!");
		}
		if (anICMSCustomer == null) {
			throw new GenerateRequestException("The ICMSCustomer is null");
		}

		WaiverRequestSearchCriteria criteria = new WaiverRequestSearchCriteria();
		criteria.setLimitProfileID(anILimitProfile.getLimitProfileID());
		String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_REJECTED,
				ICMSConstant.STATE_PENDING_RM_VERIFY };
		criteria.setTrxStatusList(statusList);
		int numOfWaivers = getNoOfWaiverRequest(criteria);
		if (numOfWaivers == 0) {
			return false;
		}
		return true;
	}

	private boolean hasPendingWaiverRequestTrxValue(ICMSCustomer anICMSCustomer) throws GenerateRequestException {
		if (anICMSCustomer == null) {
			throw new GenerateRequestException("The ICMSCustomer is null");
		}

		WaiverRequestSearchCriteria criteria = new WaiverRequestSearchCriteria();
		// criteria.setLimitProfileID(anILimitProfile.getLimitProfileID());
		criteria.setCustomerID(anICMSCustomer.getCustomerID());
		String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_REJECTED,
				ICMSConstant.STATE_PENDING_RM_VERIFY };
		criteria.setTrxStatusList(statusList);
		int numOfWaivers = getNoOfWaiverRequest(criteria);
		if (numOfWaivers == 0) {
			return false;
		}
		return true;
	}

	private boolean hasPendingDeferralRequestTrxValue(ILimitProfile anILimitProfile, ICMSCustomer anICMSCustomer)
			throws GenerateRequestException {
		if (anILimitProfile == null) {
			throw new GenerateRequestException("The ILimitProfile is null !!!");
		}
		if (anICMSCustomer == null) {
			throw new GenerateRequestException("The ICMSCustomer is null");
		}

		DeferralRequestSearchCriteria criteria = new DeferralRequestSearchCriteria();
		criteria.setLimitProfileID(anILimitProfile.getLimitProfileID());
		String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_REJECTED,
				ICMSConstant.STATE_PENDING_RM_VERIFY };
		criteria.setTrxStatusList(statusList);
		int numOfDeferrals = getNoOfDeferralRequest(criteria);
		if (numOfDeferrals == 0) {
			return false;
		}
		return true;
	}

	private boolean hasPendingDeferralRequestTrxValue(ICMSCustomer anICMSCustomer) throws GenerateRequestException {
		if (anICMSCustomer == null) {
			throw new GenerateRequestException("The ICMSCustomer is null");
		}

		DeferralRequestSearchCriteria criteria = new DeferralRequestSearchCriteria();
		criteria.setCustomerID(anICMSCustomer.getCustomerID());
		String[] statusList = { ICMSConstant.STATE_PENDING_CREATE, ICMSConstant.STATE_REJECTED,
				ICMSConstant.STATE_PENDING_RM_VERIFY };
		criteria.setTrxStatusList(statusList);
		int numOfDeferrals = getNoOfDeferralRequest(criteria);
		if (numOfDeferrals == 0) {
			return false;
		}
		return true;
	}

	private IRequestHeader getRequestHeader(ICMSCustomer anICMSCustomer) {
		IRequestHeader reqHeader = new OBRequestHeader();
		// TODO
		return reqHeader;
	}

	private IRequestSubject getRequestSubject(ICMSCustomer anICMSCustomer, String aSubjectKey) {
		IRequestSubject reqSubject = new OBRequestSubject();
		reqSubject.setCustomerID(anICMSCustomer.getCustomerID());
		reqSubject.setCustomerName(anICMSCustomer.getCustomerName());
		reqSubject.setSubject(PropertyManager.getValue(aSubjectKey));
		return reqSubject;
	}

	private IRequestDescription getRequestDescription(ILimitProfile anILimitProfile) throws GenerateRequestException {
		IRequestDescription reqDesc = new OBRequestDescription();
		populateLimitInfo(anILimitProfile, reqDesc);
		populateCollateralInfo(anILimitProfile, reqDesc);
		return reqDesc;
	}

	private void populateLimitInfo(ILimitProfile anILimitProfile, IRequestDescription anIReqDesc)
			throws GenerateRequestException {
		ILimit[] limitList = anILimitProfile.getNonDeletedLimits();
		if ((limitList == null) && (limitList.length == 0)) {
			return;
		}
		IRequestLimitInfo limitInfo = null;
		long totalApprovalAmt = 0;
		long totalActivatedAmt = 0;
		CurrencyCode baseCurrency = BaseCurrency.getCurrencyCode();
		IRequestLimitInfo[] limitInfoList = new OBRequestLimitInfo[limitList.length];
		for (int ii = 0; ii < limitList.length; ii++) {
			limitInfoList[ii] = new OBRequestLimitInfo();
			limitInfoList[ii].setLimitID(limitList[ii].getLimitID());
			limitInfoList[ii].setLimitType(limitList[ii].getProductDesc());
			limitInfoList[ii].setApprovedLimitAmt(limitList[ii].getApprovedLimitAmount());
			limitInfoList[ii].setActivatedLimitAmt(limitList[ii].getActivatedLimitAmount());
			try {
				if (totalApprovalAmt != -1) {
					totalApprovalAmt = totalApprovalAmt
							+ convertAmount(limitList[ii].getApprovedLimitAmount(), baseCurrency);
					totalActivatedAmt = totalActivatedAmt
							+ convertAmount(limitList[ii].getActivatedLimitAmount(), baseCurrency);
				}
			}
			catch (GenerateRequestException ex) {
				totalApprovalAmt = -1;
				totalActivatedAmt = -1;
			}
		}
		anIReqDesc.setRequestLimitInfoList(limitInfoList);
		if (totalApprovalAmt != -1) {
			anIReqDesc.setTotalApprovedLimitAmt(new Amount(totalApprovalAmt, baseCurrency));
			anIReqDesc.setTotalActivatedLimitAmt(new Amount(totalActivatedAmt, baseCurrency));
		}
	}

	private void populateCollateralInfo(ILimitProfile anILimitProfile, IRequestDescription anIReqDesc)
			throws GenerateRequestException {
		ILimit[] limitList = anILimitProfile.getNonDeletedLimits();
		if ((limitList == null) && (limitList.length == 0)) {
			return;
		}
		try {
			CurrencyCode baseCurrency = BaseCurrency.getCurrencyCode();
			ArrayList colList = new ArrayList();
			IRequestCollateralInfo colInfo = null;
			ICollateral col = null;
			long totalCMVAmt = 0;
			ArrayList collateralIDList = new ArrayList();
			ICollateralAllocation[] colAllocationList = null;
			ICollateralProxy proxy = CollateralProxyFactory.getProxy();
			for (int ii = 0; ii < limitList.length; ii++) {
				colAllocationList = limitList[ii].getCollateralAllocations();
				if ((colAllocationList != null) && (colAllocationList.length > 0)) {
					ICheckListProxyManager checkListProxy = CheckListProxyManagerFactory.getCheckListProxyManager();
					HashMap checkListMap = checkListProxy.getCollateralCheckListStatus(anILimitProfile
							.getLimitProfileID());
					for (int jj = 0; jj < colAllocationList.length; jj++) {
						if (!collateralIDList
								.contains(new Long(colAllocationList[jj].getCollateral().getCollateralID()))) {
							collateralIDList.add(new Long(colAllocationList[jj].getCollateral().getCollateralID()));
							col = proxy.getCollateral(colAllocationList[jj].getCollateral().getCollateralID(), true);
							colInfo = new OBRequestCollateralInfo();
							colInfo.setCollateralID(col.getCollateralID());
							colInfo.setCollateralType(col.getCollateralType());
							colInfo.setCollateralSubType(col.getCollateralSubType());
							ILimitCharge[] chargeList = col.getLimitCharges();
							if ((chargeList != null) && (chargeList.length > 0)) {
								String[] natureOfChargeList = new String[chargeList.length];
								for (int kk = 0; kk < chargeList.length; kk++) {
									natureOfChargeList[kk] = chargeList[kk].getNatureOfCharge();
								}
								colInfo.setNatureOfChargeList(natureOfChargeList);
							}
							colInfo.setCollateralCMVAmt(col.getCMV());
							CheckListSearchResult checkList = (CheckListSearchResult) checkListMap.get(new Long(col
									.getCollateralID()));
							if (checkList != null) {
								colInfo.setCheckListStatus(checkList.getCheckListStatus());
							}
							Amount cmv = col.getCMV();
							if ((cmv != null) && (cmv.getCurrencyCode() != null)) {
								cmv.setCurrencyCode(col.getCMVCcyCode());
								try {
									if (totalCMVAmt != -1) {
										totalCMVAmt = totalCMVAmt + convertAmount(col.getCMV(), baseCurrency);
									}
								}
								catch (GenerateRequestException ex) {
									totalCMVAmt = -1;
								}
							}
							colList.add(colInfo);
						}
					}
				}
			}
			IRequestCollateralInfo[] infoList = (OBRequestCollateralInfo[]) colList
					.toArray(new OBRequestCollateralInfo[colList.size()]);
			anIReqDesc.setRequestCollateralInfoList(infoList);
			anIReqDesc.setTotalCollateralCMVAmt(new Amount(totalCMVAmt, baseCurrency));
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new GenerateRequestException("Exception in populateCollateralInfo", ex);
		}
		catch (CheckListException ex) {
			rollback();
			throw new GenerateRequestException("Exception in populateCollateralInfo", ex);
		}
		catch (CollateralException ex) {
			rollback();
			throw new GenerateRequestException("Exception in populateCollateralInfo", ex);
		}
	}

	private IRequestItem[] getWaiverRequestItemList(ILimitProfile anILimitProfile) throws GenerateRequestException {
		ILimit[] limitList = anILimitProfile.getNonDeletedLimits();
		if ((limitList == null) && (limitList.length == 0)) {
			return null;
		}
		HashMap itemMap = getCheckListItemListbyStatus(anILimitProfile.getLimitProfileID(),
				ICMSConstant.STATE_ITEM_WAIVER_REQ);
		if ((itemMap == null) || (itemMap.size() == 0)) {
			return null;
		}
		IWaiverRequestItem[] reqItemList = new OBWaiverRequestItem[itemMap.size()];
		Iterator iter = itemMap.keySet().iterator();

		int ctr = 0;
		while (iter.hasNext()) {
			reqItemList[ctr] = new OBWaiverRequestItem();
			String key = (String) iter.next();
			int position = key.indexOf("_");
			String checkListIDStr = key.substring(position + 1);
			Long checkListID = new Long(checkListIDStr);
			DefaultLogger.debug(this, "CheckListID: " + checkListID);
			if (checkListID != null) {
				reqItemList[ctr].setCheckListID(checkListID.longValue());
			}
			ICheckListItem item = (ICheckListItem) itemMap.get(key);
			reqItemList[ctr].setCheckListItem(item);
			ctr++;
		}
		return reqItemList;
	}

	private IRequestItem[] getWaiverRequestItemList(ICMSCustomer anICMSCustomer) throws GenerateRequestException {

		HashMap itemMap = getCheckListItemListbyStatusForNonBorrower(anICMSCustomer.getCustomerID(),
				ICMSConstant.STATE_ITEM_WAIVER_REQ);
		if ((itemMap == null) || (itemMap.size() == 0)) {
			return null;
		}
		IWaiverRequestItem[] reqItemList = new OBWaiverRequestItem[itemMap.size()];
		Iterator iter = itemMap.keySet().iterator();

		int ctr = 0;
		while (iter.hasNext()) {
			reqItemList[ctr] = new OBWaiverRequestItem();
			String key = (String) iter.next();
			int position = key.indexOf("_");
			String checkListIDStr = key.substring(position + 1);
			Long checkListID = new Long(checkListIDStr);
			DefaultLogger.debug(this, "CheckListID: " + checkListID);
			if (checkListID != null) {
				reqItemList[ctr].setCheckListID(checkListID.longValue());
			}
			ICheckListItem item = (ICheckListItem) itemMap.get(key);
			reqItemList[ctr].setCheckListItem(item);
			ctr++;
		}
		return reqItemList;
	}

	private IRequestItem[] getDeferralRequestItemList(ILimitProfile anILimitProfile) throws GenerateRequestException {
		ILimit[] limitList = anILimitProfile.getNonDeletedLimits();
		if ((limitList == null) && (limitList.length == 0)) {
			return null;
		}
		HashMap itemMap = getCheckListItemListbyStatus(anILimitProfile.getLimitProfileID(),
				ICMSConstant.STATE_ITEM_DEFER_REQ);
		if ((itemMap == null) || (itemMap.size() == 0)) {
			return null;
		}
		IDeferralRequestItem[] reqItemList = new OBDeferralRequestItem[itemMap.size()];
		Iterator iter = itemMap.keySet().iterator();

		int ctr = 0;
		while (iter.hasNext()) {
			reqItemList[ctr] = new OBDeferralRequestItem();
			String key = (String) iter.next();
			int position = key.indexOf("_");
			String checkListIDStr = key.substring(position + 1);
			Long checkListID = new Long(checkListIDStr);
			DefaultLogger.debug(this, "CheckListID: " + checkListID);
			if (checkListID != null) {
				reqItemList[ctr].setCheckListID(checkListID.longValue());
			}
			ICheckListItem item = (ICheckListItem) itemMap.get(key);
			reqItemList[ctr].setCheckListItem(item);
			ctr++;
		}
		return reqItemList;
	}

	private IRequestItem[] getDeferralRequestItemList(ICMSCustomer anICMSCustomer) throws GenerateRequestException {
		HashMap itemMap = getCheckListItemListbyStatusForNonBorrower(anICMSCustomer.getCustomerID(),
				ICMSConstant.STATE_ITEM_DEFER_REQ);
		if ((itemMap == null) || (itemMap.size() == 0)) {
			return null;
		}
		IDeferralRequestItem[] reqItemList = new OBDeferralRequestItem[itemMap.size()];
		Iterator iter = itemMap.keySet().iterator();

		int ctr = 0;
		while (iter.hasNext()) {
			reqItemList[ctr] = new OBDeferralRequestItem();
			String key = (String) iter.next();
			int position = key.indexOf("_");
			String checkListIDStr = key.substring(position + 1);
			Long checkListID = new Long(checkListIDStr);
			DefaultLogger.debug(this, "CheckListID: " + checkListID);
			if (checkListID != null) {
				reqItemList[ctr].setCheckListID(checkListID.longValue());
			}
			ICheckListItem item = (ICheckListItem) itemMap.get(key);
			reqItemList[ctr].setCheckListItem(item);
			ctr++;
		}
		return reqItemList;
	}

	/**
	 * Get the waiver request trx value based on the trx ID
	 * @param aTrxID of String type
	 * @return IWaiverRequestTrxValue - the scc trx value
	 * @throws GenerateRequestException
	 */
	private IWaiverRequestTrxValue getWaiverRequestTrxValue(String aTrxID) throws GenerateRequestException {
		if (aTrxID == null) {
			throw new GenerateRequestException("The TrxID is null !!!");
		}
		IWaiverRequestTrxValue trxValue = new OBWaiverRequestTrxValue();
		trxValue.setTransactionID(aTrxID);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_WAIVER_REQ);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_WAIVER_REQ);
		return operate(trxValue, param);
	}

	/**
	 * Get the deferral request trx value based on the trx ID
	 * @param aTrxID of String type
	 * @return IDeferralRequestTrxValue - the scc trx value
	 * @throws GenerateRequestException
	 */
	private IDeferralRequestTrxValue getDeferralRequestTrxValue(String aTrxID) throws GenerateRequestException {
		if (aTrxID == null) {
			throw new GenerateRequestException("The TrxID is null !!!");
		}
		IDeferralRequestTrxValue trxValue = new OBDeferralRequestTrxValue();
		trxValue.setTransactionID(aTrxID);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_DEFERRAL_REQ);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_DEFERRAL_REQ);
		return operate(trxValue, param);
	}

	/**
	 * Maker generate the waiver request
	 * @param anITrxContext of ITrxContext type
	 * @param anIWaiverRequestTrxValue of IWaiverRequestTrxValue type
	 * @param anIWaiverRequets of IWaiverRequest type
	 * @return IWaiverRequestTrxValue - the generates waiver request trx value
	 * @throws GenerateRequestException on errors
	 */
	public IWaiverRequestTrxValue makerGenerateRequest(ITrxContext anITrxContext,
			IWaiverRequestTrxValue anIWaiverRequestTrxValue, IWaiverRequest anIWaiverRequest)
			throws GenerateRequestException {
		if (anITrxContext == null) {
			throw new GenerateRequestException("The ITrxContext is null!!!");
		}
		if (anIWaiverRequestTrxValue == null) {
			throw new GenerateRequestException("The IWaiverRequestTrxValue to be updated is null!!!");
		}
		if (anIWaiverRequest == null) {
			throw new GenerateRequestException("The IWaiverRequest to be updated is null !!!");
		}
		anIWaiverRequestTrxValue = formulateTrxValue(anITrxContext, anIWaiverRequestTrxValue, anIWaiverRequest);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_GENERATE_WAIVER_REQ);
		return operate(anIWaiverRequestTrxValue, param);
	}

	/**
	 * Maker generate the deferral request
	 * @param anITrxContext of ITrxContext type
	 * @param anIDeferralRequestTrxValue of IDeferralRequestTrxValue type
	 * @param anIDeferralRequets of IDeferralRequest type
	 * @return IDeferralRequestTrxValue - the generates deferral request trx
	 *         value
	 * @throws GenerateRequestException on errors
	 */
	public IDeferralRequestTrxValue makerGenerateRequest(ITrxContext anITrxContext,
			IDeferralRequestTrxValue anIDeferralRequestTrxValue, IDeferralRequest anIDeferralRequest)
			throws GenerateRequestException {
		if (anITrxContext == null) {
			throw new GenerateRequestException("The ITrxContext is null!!!");
		}
		if (anIDeferralRequestTrxValue == null) {
			throw new GenerateRequestException("The IDeferralRequestTrxValue to be updated is null!!!");
		}
		if (anIDeferralRequest == null) {
			throw new GenerateRequestException("The IDeferralRequest to be updated is null !!!");
		}
		anIDeferralRequestTrxValue = formulateTrxValue(anITrxContext, anIDeferralRequestTrxValue, anIDeferralRequest);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_GENERATE_DEFERRAL_REQ);
		return operate(anIDeferralRequestTrxValue, param);
	}

	/**
	 * Checker approve the waiver request
	 * @param anITrxContext of ITrxContext type
	 * @param IWaiverRequestTrxValue of IWaiverRequestTrxValue type
	 * @return IWaiverRequestTrxValue - the generated waiver request trx value
	 * @throws GenerateRequestException on errors
	 */
	public IWaiverRequestTrxValue checkerApproveGenerateRequest(ITrxContext anITrxContext,
			IWaiverRequestTrxValue anIWaiverRequestTrxValue) throws GenerateRequestException {
		if (anITrxContext == null) {
			throw new GenerateRequestException("The ITrxContext is null!!!");
		}
		if (anIWaiverRequestTrxValue == null) {
			throw new GenerateRequestException("The IWaiverRequestTrxValue to be updated is null!!!");
		}
		anIWaiverRequestTrxValue = formulateTrxValue(anITrxContext, anIWaiverRequestTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_WAIVER_REQ);
		return operate(anIWaiverRequestTrxValue, param);
	}

	/**
	 * Checker approve the deferral request
	 * @param anITrxContext of ITrxContext type
	 * @param IDeferralRequestTrxValue of IDeferralRequestTrxValue type
	 * @return IDeferralRequestTrxValue - the generated deferral request trx
	 *         value
	 * @throws GenerateRequestException on errors
	 */
	public IDeferralRequestTrxValue checkerApproveGenerateRequest(ITrxContext anITrxContext,
			IDeferralRequestTrxValue anIDeferralRequestTrxValue) throws GenerateRequestException {
		if (anITrxContext == null) {
			throw new GenerateRequestException("The ITrxContext is null!!!");
		}
		if (anIDeferralRequestTrxValue == null) {
			throw new GenerateRequestException("The IDeferralRequestTrxValue to be updated is null!!!");
		}
		anIDeferralRequestTrxValue = formulateTrxValue(anITrxContext, anIDeferralRequestTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_DEFERRAL_REQ);
		return operate(anIDeferralRequestTrxValue, param);
	}

	/**
	 * RM approve the waiver request
	 * @param anITrxContext of ITrxContext type
	 * @param IWaiverRequestTrxValue of IWaiverRequestTrxValue type
	 * @return IWaiverRequestTrxValue - the generated waiver request trx value
	 * @throws GenerateRequestException on errors
	 */
	public IWaiverRequestTrxValue rmApproveGenerateRequest(ITrxContext anITrxContext,
			IWaiverRequestTrxValue anIWaiverRequestTrxValue) throws GenerateRequestException {
		if (anITrxContext == null) {
			throw new GenerateRequestException("The ITrxContext is null!!!");
		}
		if (anIWaiverRequestTrxValue == null) {
			throw new GenerateRequestException("The IWaiverRequestTrxValue to be updated is null!!!");
		}
		anIWaiverRequestTrxValue = formulateTrxValue(anITrxContext, anIWaiverRequestTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_RM_APPROVE_GENERATE_WAIVER_REQ);
		return operate(anIWaiverRequestTrxValue, param);
	}

	/**
	 * RM approve the deferral request
	 * @param anITrxContext of ITrxContext type
	 * @param IDeferralRequestTrxValue of IDeferralRequestTrxValue type
	 * @return IDeferralRequestTrxValue - the generated deferral request trx
	 *         value
	 * @throws GenerateRequestException on errors
	 */
	public IDeferralRequestTrxValue rmApproveGenerateRequest(ITrxContext anITrxContext,
			IDeferralRequestTrxValue anIDeferralRequestTrxValue) throws GenerateRequestException {
		if (anITrxContext == null) {
			throw new GenerateRequestException("The ITrxContext is null!!!");
		}
		if (anIDeferralRequestTrxValue == null) {
			throw new GenerateRequestException("The IDeferralRequestTrxValue to be updated is null!!!");
		}
		anIDeferralRequestTrxValue = formulateTrxValue(anITrxContext, anIDeferralRequestTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_RM_APPROVE_GENERATE_DEFERRAL_REQ);
		return operate(anIDeferralRequestTrxValue, param);
	}

	/**
	 * Checker reject the waiver request
	 * @param anITrxContext of ITrxContext type
	 * @param anIWaiverRequestTrxValue of IWaiverRequestTrxValue type
	 * @return IWaiverRequestTrxValue - the waiver request trx value
	 * @throws GenerateRequestException on errors
	 */
	public IWaiverRequestTrxValue checkerRejectGenerateRequest(ITrxContext anITrxContext,
			IWaiverRequestTrxValue anIWaiverRequestTrxValue) throws GenerateRequestException {
		if (anITrxContext == null) {
			throw new GenerateRequestException("The ITrxContext is null!!!");
		}
		if (anIWaiverRequestTrxValue == null) {
			throw new GenerateRequestException("The IWaiverRequestTrxValue to be updated is null!!!");
		}
		anIWaiverRequestTrxValue = formulateTrxValue(anITrxContext, anIWaiverRequestTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_GENERATE_WAIVER_REQ);
		return operate(anIWaiverRequestTrxValue, param);
	}

	/**
	 * Checker reject the deferral request
	 * @param anITrxContext of ITrxContext type
	 * @param anIDeferralRequestTrxValue of IDeferralRequestTrxValue type
	 * @return IDeferralRequestTrxValue - the deferral request trx value
	 * @throws GenerateRequestException on errors
	 */
	public IDeferralRequestTrxValue checkerRejectGenerateRequest(ITrxContext anITrxContext,
			IDeferralRequestTrxValue anIDeferralRequestTrxValue) throws GenerateRequestException {
		if (anITrxContext == null) {
			throw new GenerateRequestException("The ITrxContext is null!!!");
		}
		if (anIDeferralRequestTrxValue == null) {
			throw new GenerateRequestException("The IDeferralRequestTrxValue to be updated is null!!!");
		}
		anIDeferralRequestTrxValue = formulateTrxValue(anITrxContext, anIDeferralRequestTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_GENERATE_DEFERRAL_REQ);
		return operate(anIDeferralRequestTrxValue, param);
	}

	/**
	 * RM reject the waiver request
	 * @param anITrxContext of ITrxContext type
	 * @param anIWaiverRequestTrxValue of IWaiverRequestTrxValue type
	 * @return IWaiverRequestTrxValue - the waiver request trx value
	 * @throws GenerateRequestException on errors
	 */
	public IWaiverRequestTrxValue rmRejectGenerateRequest(ITrxContext anITrxContext,
			IWaiverRequestTrxValue anIWaiverRequestTrxValue) throws GenerateRequestException {
		if (anITrxContext == null) {
			throw new GenerateRequestException("The ITrxContext is null!!!");
		}
		if (anIWaiverRequestTrxValue == null) {
			throw new GenerateRequestException("The IWaiverRequestTrxValue to be updated is null!!!");
		}
		anIWaiverRequestTrxValue = formulateTrxValue(anITrxContext, anIWaiverRequestTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_RM_REJECT_GENERATE_WAIVER_REQ);
		return operate(anIWaiverRequestTrxValue, param);
	}

	/**
	 * RM reject the deferral request
	 * @param anITrxContext of ITrxContext type
	 * @param anIDeferralRequestTrxValue of IDeferralRequestTrxValue type
	 * @return IDeferralRequestTrxValue - the deferral request trx value
	 * @throws GenerateRequestException on errors
	 */
	public IDeferralRequestTrxValue rmRejectGenerateRequest(ITrxContext anITrxContext,
			IDeferralRequestTrxValue anIDeferralRequestTrxValue) throws GenerateRequestException {
		if (anITrxContext == null) {
			throw new GenerateRequestException("The ITrxContext is null!!!");
		}
		if (anIDeferralRequestTrxValue == null) {
			throw new GenerateRequestException("The IDeferralRequestTrxValue to be updated is null!!!");
		}
		anIDeferralRequestTrxValue = formulateTrxValue(anITrxContext, anIDeferralRequestTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_RM_REJECT_GENERATE_DEFERRAL_REQ);
		return operate(anIDeferralRequestTrxValue, param);
	}

	/**
	 * Maker edit the rejected waiver request
	 * @param anITrxContext of ITrxContext type
	 * @param anIWaiverRequestTrxValue of IWaiverRequestTrxValue
	 * @param anIWaiverRequest of IWaiverRequest
	 * @return IWaiverRequestTrxValue - the waiver request trx
	 * @throws GenerateRequestException on errors
	 */
	public IWaiverRequestTrxValue makerEditRejectedGenerateRequest(ITrxContext anITrxContext,
			IWaiverRequestTrxValue anIWaiverRequestTrxValue, IWaiverRequest anIWaiverRequest)
			throws GenerateRequestException {
		if (anITrxContext == null) {
			throw new GenerateRequestException("The ITrxContext is null!!!");
		}
		if (anIWaiverRequestTrxValue == null) {
			throw new GenerateRequestException("The IWaiverRequestTrxValue to be updated is null!!!");
		}
		if (anIWaiverRequest == null) {
			throw new GenerateRequestException("The IWaiverRequest to be updated is null !!!");
		}
		anIWaiverRequestTrxValue = formulateTrxValue(anITrxContext, anIWaiverRequestTrxValue, anIWaiverRequest);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_GENERATE_WAIVER_REQ);
		return operate(anIWaiverRequestTrxValue, param);
	}

	/**
	 * Maker edit the rejected deferral request
	 * @param anITrxContext of ITrxContext type
	 * @param anIDeferralRequestTrxValue of IDeferralRequestTrxValue
	 * @param anIDeferralRequest of IDeferralRequest
	 * @return IDeferralRequestTrxValue - the deferral request trx
	 * @throws GenerateRequestException on errors
	 */
	public IDeferralRequestTrxValue makerEditRejectedGenerateRequest(ITrxContext anITrxContext,
			IDeferralRequestTrxValue anIDeferralRequestTrxValue, IDeferralRequest anIDeferralRequest)
			throws GenerateRequestException {
		if (anITrxContext == null) {
			throw new GenerateRequestException("The ITrxContext is null!!!");
		}
		if (anIDeferralRequestTrxValue == null) {
			throw new GenerateRequestException("The IDeferralRequestTrxValue to be updated is null!!!");
		}
		if (anIDeferralRequest == null) {
			throw new GenerateRequestException("The IDeferralRequest to be updated is null !!!");
		}
		anIDeferralRequestTrxValue = formulateTrxValue(anITrxContext, anIDeferralRequestTrxValue, anIDeferralRequest);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_GENERATE_DEFERRAL_REQ);
		return operate(anIDeferralRequestTrxValue, param);
	}

	/**
	 * Make close the rejected waiver request
	 * @param anITrxContext of ITrxContext type
	 * @param anIWaiverRequestTrxValue of IWaiverRequestTrxValue type
	 * @return IWaiverRequestTrxValue - the waiver request trx
	 * @throws GenerateRequestException on errors
	 */
	public IWaiverRequestTrxValue makerCloseRejectedGenerateRequest(ITrxContext anITrxContext,
			IWaiverRequestTrxValue anIWaiverRequestTrxValue) throws GenerateRequestException {
		if (anITrxContext == null) {
			throw new GenerateRequestException("The ITrxContext is null!!!");
		}
		if (anIWaiverRequestTrxValue == null) {
			throw new GenerateRequestException("The IWaiverRequestTrxValue to be updated is null!!!");
		}
		anIWaiverRequestTrxValue = formulateTrxValue(anITrxContext, anIWaiverRequestTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_GENERATE_WAIVER_REQ);
		return operate(anIWaiverRequestTrxValue, param);
	}

	/**
	 * Make close the rejected deferral request
	 * @param anITrxContext of ITrxContext type
	 * @param anIDeferralRequestTrxValue of IDeferralRequestTrxValue type
	 * @return IDeferralRequestTrxValue - the deferral request trx
	 * @throws GenerateRequestException on errors
	 */
	public IDeferralRequestTrxValue makerCloseRejectedGenerateRequest(ITrxContext anITrxContext,
			IDeferralRequestTrxValue anIDeferralRequestTrxValue) throws GenerateRequestException {
		if (anITrxContext == null) {
			throw new GenerateRequestException("The ITrxContext is null!!!");
		}
		if (anIDeferralRequestTrxValue == null) {
			throw new GenerateRequestException("The IDeferralRequestTrxValue to be updated is null!!!");
		}
		anIDeferralRequestTrxValue = formulateTrxValue(anITrxContext, anIDeferralRequestTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_GENERATE_DEFERRAL_REQ);
		return operate(anIDeferralRequestTrxValue, param);
	}

	/**
	 * Formulate the waiver request Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anIWaiverRequest - IWaiverRequest
	 * @return IWaiverRequestTrxValue - the waiver request trx interface
	 *         formulated
	 */
	private IWaiverRequestTrxValue formulateTrxValue(ITrxContext anITrxContext, IWaiverRequest anIWaiverRequest) {
		return formulateTrxValue(anITrxContext, null, anIWaiverRequest);
	}

	/**
	 * Formulate the deferral request Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anIDeferralRequest - IDeferralRequest
	 * @return IDeferralRequestTrxValue - the deferral request trx interface
	 *         formulated
	 */
	private IDeferralRequestTrxValue formulateTrxValue(ITrxContext anITrxContext, IDeferralRequest anIDeferralRequest) {
		return formulateTrxValue(anITrxContext, null, anIDeferralRequest);
	}

	/**
	 * Formulate the waiver request Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anICMSTrxValue - ICMSTrxValue
	 * @param anIWaiverRequest - IWaiverRequest
	 * @return IWaiverRequestTrxValue - the waiver request trx interface
	 *         formulated
	 */
	private IWaiverRequestTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IWaiverRequest anIWaiverRequest) {
		IWaiverRequestTrxValue waiverRequestTrxValue = null;
		if (anICMSTrxValue != null) {
			waiverRequestTrxValue = new OBWaiverRequestTrxValue(anICMSTrxValue);
		}
		else {
			waiverRequestTrxValue = new OBWaiverRequestTrxValue();
		}
		waiverRequestTrxValue = formulateTrxValue(anITrxContext, (IWaiverRequestTrxValue) waiverRequestTrxValue);
		waiverRequestTrxValue.setStagingWaiverRequest(anIWaiverRequest);
		return waiverRequestTrxValue;
	}

	/**
	 * Formulate the deferral request Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anICMSTrxValue - ICMSTrxValue
	 * @param anIDeferralRequest - IDeferralRequest
	 * @return IDeferralRequestTrxValue - the deferral request trx interface
	 *         formulated
	 */
	private IDeferralRequestTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IDeferralRequest anIDeferralRequest) {
		IDeferralRequestTrxValue deferralRequestTrxValue = null;
		if (anICMSTrxValue != null) {
			deferralRequestTrxValue = new OBDeferralRequestTrxValue(anICMSTrxValue);
		}
		else {
			deferralRequestTrxValue = new OBDeferralRequestTrxValue();
		}
		deferralRequestTrxValue = formulateTrxValue(anITrxContext, (IDeferralRequestTrxValue) deferralRequestTrxValue);
		deferralRequestTrxValue.setStagingDeferralRequest(anIDeferralRequest);
		return deferralRequestTrxValue;
	}

	/**
	 * Formulate the waiver request trx object
	 * @param anITrxContext - ITrxContext
	 * @param anIWaiverRequestTrxValue - IWaiverRequestTrxValue
	 * @return IWaiverRequestTrxValue - the waiver request trx interface
	 *         formulated
	 */
	private IWaiverRequestTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IWaiverRequestTrxValue anIWaiverRequestTrxValue) {
		anIWaiverRequestTrxValue.setTrxContext(anITrxContext);
		anIWaiverRequestTrxValue.setTransactionType(ICMSConstant.INSTANCE_WAIVER_REQ);
		return anIWaiverRequestTrxValue;
	}

	/**
	 * Formulate the deferral request trx object
	 * @param anITrxContext - ITrxContext
	 * @param anIDeferralRequestTrxValue - IDeferralRequestTrxValue
	 * @return IDeferralRequestTrxValue - the deferral request trx interface
	 *         formulated
	 */
	private IDeferralRequestTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IDeferralRequestTrxValue anIDeferralRequestTrxValue) {
		anIDeferralRequestTrxValue.setTrxContext(anITrxContext);
		anIDeferralRequestTrxValue.setTransactionType(ICMSConstant.INSTANCE_DEFERRAL_REQ);
		return anIDeferralRequestTrxValue;
	}

	private IWaiverRequestTrxValue operate(IWaiverRequestTrxValue anIWaiverRequestTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter) throws GenerateRequestException {
		ICMSTrxResult result = operateForResult(anIWaiverRequestTrxValue, anOBCMSTrxParameter);
		return (IWaiverRequestTrxValue) result.getTrxValue();
	}

	private IDeferralRequestTrxValue operate(IDeferralRequestTrxValue anIDeferralRequestTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter) throws GenerateRequestException {
		ICMSTrxResult result = operateForResult(anIDeferralRequestTrxValue, anOBCMSTrxParameter);
		return (IDeferralRequestTrxValue) result.getTrxValue();
	}

	private ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws GenerateRequestException {
		try {
			ITrxController controller = (new GenerateRequestTrxControllerFactory()).getController(anICMSTrxValue,
					anOBCMSTrxParameter);
			if (controller == null) {
				throw new GenerateRequestException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			rollback();
			throw new GenerateRequestException(e);
		}
		catch (Exception ex) {
			rollback();
			throw new GenerateRequestException(ex.toString());
		}
	}

	protected abstract void rollback();

	protected abstract int getNoOfWaiverRequest(WaiverRequestSearchCriteria aCriteria) throws GenerateRequestException;

	protected abstract int getNoOfDeferralRequest(DeferralRequestSearchCriteria aCriteria)
			throws GenerateRequestException;

	protected abstract HashMap getCheckListItemListbyStatus(long aLimitProfileID, String anItemStatus)
			throws GenerateRequestException;

	protected abstract HashMap getCheckListItemListbyStatusForNonBorrower(long aCustomerID, String anItemStatus)
			throws GenerateRequestException;

	protected abstract long convertAmount(Amount anAmount, CurrencyCode aCurrencyCode) throws GenerateRequestException;
}
