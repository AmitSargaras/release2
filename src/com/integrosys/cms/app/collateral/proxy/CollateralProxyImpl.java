/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/proxy/CollateralProxyImpl.java,v 1.58 2006/11/10 08:59:12 jzhai Exp $
 */
package com.integrosys.cms.app.collateral.proxy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.SessionContext;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.CollateralSearchCriteria;
import com.integrosys.cms.app.collateral.bus.IAddtionalDocumentFacilityDetails;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.ICollateralType;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.IPledgor;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.bus.OBInsurancePolicyColl;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeaircraft.OBSpecificChargeAircraft;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeplant.OBSpecificChargePlant;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.comgeneral.OBCommercialGeneral;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxParameter;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxParameter;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.MakerCheckerUserUtil;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.ui.collateral.IInsuranceGCDao;

/**
 * This class facades the ICollateralProxy implementation by delegating the
 * handling of requests to an ejb session bean.s
 * 
 * @author jzhai
 * @author Chong Jun Yong
 * @author Andy Wong
 * @since 2006/11/10
 */
public class CollateralProxyImpl extends CollateralProxyAccessor implements ICollateralProxy {

	protected Logger logger = LoggerFactory.getLogger(CollateralProxyImpl.class);

	public String[] getSecuritySubTypeForTrxByTrxID(long aTrxID) throws CollateralException, SearchDAOException {
		return getCollateralTrxDao().getSecuritySubTypeForTrxByTrxID(aTrxID);
	}

	public ICollateralTrxValue getCollateralTrxValue(ITrxContext ctx, long collateralID) throws CollateralException {
		OBCollateralTrxParameter param = new OBCollateralTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_COL_BY_COLID);
		OBCollateralTrxValue trxValue = new OBCollateralTrxValue();
		trxValue.setReferenceID(String.valueOf(collateralID));

		return doInTrxController(getCollateralReadController(), param, constructTrxValue(ctx, trxValue));
	}

	public ICollateralTrxValue[] getCollateralTrxValues(ITrxContext[] ctx, long[] colIDs) throws CollateralException {
		int size = 0;
		if ((colIDs == null) || ((size = colIDs.length) == 0)) {
			throw new CollateralException("The list of Collateral IDs is null!");
		}

		ICollateralTrxValue[] trxVals = new OBCollateralTrxValue[size];
		for (int i = 0; i < size; i++) {
			trxVals[i] = getCollateralTrxValue(ctx[i], colIDs[i]);
		}
		return trxVals;
	}

	public ICollateralTrxValue getCollateralTrxValue(ITrxContext ctx, String trxID) throws CollateralException {
		OBCollateralTrxParameter param = new OBCollateralTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_COL_BY_TRXID);
		OBCollateralTrxValue trxValue = new OBCollateralTrxValue();
		trxValue.setTransactionID(trxID.trim());

		return doInTrxController(getCollateralReadController(), param, constructTrxValue(ctx, trxValue));
	}

	public ICollateralTrxValue[] getCollateralTrxValues(ITrxContext ctx, String aTrxRefID) throws CollateralException,
			SearchDAOException {
		String[] trxIDs = getCollateralTrxDao().getColTrxIDsByTrxRefID(aTrxRefID);
		int size = 0;
		if ((trxIDs == null) || ((size = trxIDs.length) == 0)) {
			throw new CollateralException("No transactions attached to the trx reference id!!!");
		}

		ICollateralTrxValue[] trxVals = new OBCollateralTrxValue[size];
		for (int i = 0; i < size; i++) {
			trxVals[i] = getCollateralTrxValue(ctx, trxIDs[i]);
		}
		return trxVals;
	}

	public SearchResult searchCollateral(ITrxContext ctx, CollateralSearchCriteria criteria) throws SearchDAOException {
		criteria.setTrxContext(ctx);
		return getCollateralDao().searchCollateral(criteria);
	}

	public ICollateral getCollateral(long collateralID, boolean includeDetails) throws CollateralException {
		// DefaultLogger.debug(this, " collateral id " + collateralID);
		ICollateral col = null;
		if (includeDetails) {
			col = getActualCollateralBusManager().getCollateral(collateralID);
		}
		else {
			col = getActualCollateralBusManager().getCommonCollateral(collateralID);
		}
		// DefaultLogger.debug(this, " After get collateral id:" +
		// collateralID);
		return col;
	}

	public ICollateralTrxValue subscribeCreateCollateral(ICollateralTrxValue trxValue) throws CollateralException {
		OBCollateralTrxParameter param = new OBCollateralTrxParameter();
		param.setAction(ICMSConstant.ACTION_SUBSCRIBE_CREATE_COL);

		return doInTrxController(getCollateralTrxController(), param, constructTrxValue(trxValue.getTrxContext(),
				trxValue));
	}

	public ICollateralTrxValue subscribeDeleteCollateral(ICollateralTrxValue trxValue) throws CollateralException,
			SearchDAOException {
		OBCollateral obCollateral = (OBCollateral) trxValue.getCollateral();
		boolean canFullDelete = getCollateralDao().canFullDeleteCollateral(obCollateral.getCollateralID(),
				obCollateral.getSCISecurityID());

		OBCollateralTrxParameter param = new OBCollateralTrxParameter();

		if (canFullDelete) {
			param.setAction(ICMSConstant.ACTION_FULL_DELETE_COL);
		}
		else {
			param.setAction(ICMSConstant.ACTION_PART_DELETE_COL);
		}

		return doInTrxController(getCollateralTrxController(), param, constructTrxValue(trxValue.getTrxContext(),
				trxValue));

	}

	public boolean isCollateralCustodianDocInVault(long collateralID) throws CollateralException {
		try {
			ICustodianDoc[] doc = getCustodianProxyManager().getCustodianDoc(collateralID);

			return ((doc != null) && doc.length > 0);
		}
		catch (Throwable t) {
			throw new CollateralException("failed to retrieve all doc stored in custodian vault for collateral id ["
					+ collateralID + "] ", t);
		}
	}

	public boolean isCollateralNoChecklist(long collateralID) throws CollateralException {
		try {
			return getCheckListProxyManager().areAllCheckListsDeleted(collateralID);
		}
		catch (Throwable t) {
			throw new CollateralException("fail to get collateral checklist status, collateral id [" + collateralID
					+ "]", t);
		}
	}

	public ICollateralTrxValue makerUpdateCollateral(ITrxContext ctx, ICollateralTrxValue trxValue, ICollateral col)
			throws CollateralException {
		OBCollateralTrxParameter param = new OBCollateralTrxParameter();

		if (trxValue.getCollateral() == null) {
			param.setAction(ICMSConstant.ACTION_MAKER_CREATE_COL);
		}
		else {
			param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_COL);
		}

		trxValue.setTransactionSubType(null);
		trxValue.setStagingCollateral(col);

		return doInTrxController(getCollateralTrxController(), param, constructTrxValue(ctx, trxValue));
	}

	public ICollateralTrxValue[] makerUpdateCollaterals(ITrxContext[] ctx, ICollateralTrxValue[] trxVals,
			ICollateral[] cols) throws CollateralException {
		int size = 0;
		if ((trxVals == null) || ((size = trxVals.length) == 0)) {
			throw new CollateralException("Collateral Trx Values are NULL!");
		}
		ICollateralTrxValue[] results = new OBCollateralTrxValue[size];
		fillParentTrxID(trxVals);

		for (int i = 0; i < size; i++) {
			results[i] = makerUpdateCollateral(ctx[i], trxVals[i], cols[i]);
		}
		return results;
	}

	public ICollateralTrxValue checkerVerifyCollateral(ITrxContext ctx, ICollateralTrxValue trxValue)
			throws CollateralException {
		OBCollateralTrxParameter param = new OBCollateralTrxParameter();
		param.setAction(getVerifyCollateralAction(trxValue));

		return doInTrxController(getCollateralTrxController(), param, constructTrxValue(ctx, trxValue));
	}
	
	public ICMSTrxResult checkerVerifyCollateralRest(ITrxContext ctx, ICollateralTrxValue trxValue)
			throws CollateralException {
		OBCollateralTrxParameter param = new OBCollateralTrxParameter();
		param.setAction(getVerifyCollateralAction(trxValue));

		return doInTrxControllerRest(getCollateralTrxController(), param, constructTrxValue(ctx, trxValue));
	}


	public ICollateralTrxValue[] checkerVerifyUpdateCollaterals(ITrxContext[] ctx, ICollateralTrxValue[] trxVals)
			throws CollateralException {
		int size = 0;
		if ((trxVals == null) || ((size = trxVals.length) == 0)) {
			throw new CollateralException("Collateral Trx Values are null!");
		}
		ICollateralTrxValue[] results = new OBCollateralTrxValue[size];
		for (int i = 0; i < size; i++) {
			results[i] = checkerVerifyCollateral(ctx[i], trxVals[i]);
		}
		return results;
	}

	public ICollateralTrxValue makerSaveCollateral(ITrxContext ctx, ICollateralTrxValue trxValue, ICollateral col)
			throws CollateralException {
		OBCollateralTrxParameter param = new OBCollateralTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_COL);
		trxValue.setStagingCollateral(col);
		trxValue.setTransactionSubType(null);

		return doInTrxController(getCollateralTrxController(), param, constructTrxValue(ctx, trxValue));
	}

	public ICollateralTrxValue[] makerSaveCollaterals(ITrxContext[] ctx, ICollateralTrxValue[] trxVals,
			ICollateral[] cols) throws CollateralException {
		int size = 0;
		if ((trxVals == null) || ((size = trxVals.length) == 0)) {
			throw new CollateralException("Collateral transaction values are null!");
		}

		ICollateralTrxValue[] results = new OBCollateralTrxValue[size];
		fillParentTrxID(trxVals);
		for (int i = 0; i < size; i++) {
			results[i] = makerSaveCollateral(ctx[i], trxVals[i], cols[i]);
		}
		return results;
	}

	public ICollateralTrxValue makerCancelCollateral(ITrxContext ctx, ICollateralTrxValue trxValue)
			throws CollateralException {
		OBCollateralTrxParameter param = new OBCollateralTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CANCEL_COL);

		return doInTrxController(getCollateralTrxController(), param, constructTrxValue(ctx, trxValue));
	}

	public ICollateralTrxValue[] makerCancelCollaterals(ITrxContext[] ctx, ICollateralTrxValue[] trxVals)
			throws CollateralException {
		int size = 0;
		if ((trxVals == null) || ((size = trxVals.length) == 0)) {
			throw new CollateralException("Collateral Transaction Values are null!");
		}

		ICollateralTrxValue[] results = new OBCollateralTrxValue[size];
		for (int i = 0; i < size; i++) {
			results[i] = makerCancelCollateral(ctx[i], trxVals[i]);
		}
		return results;
	}

	public ICollateralTrxValue systemCancelCollateral(ITrxContext ctx, ICollateralTrxValue trxValue)
			throws CollateralException {
		OBCollateralTrxParameter param = new OBCollateralTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_CANCEL_COL);

		return doInTrxController(getCollateralTrxController(), param, constructTrxValue(ctx, trxValue));
	}

	public ICollateralTrxValue systemUpdateCollateral(ITrxContext ctx, ICollateralTrxValue trxValue)
			throws CollateralException {
		OBCollateralTrxParameter param = new OBCollateralTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_UPDATE_COL);

		return doInTrxController(getCollateralTrxController(), param, constructTrxValue(ctx, trxValue));
	}

	public int getDocumentNoCount(String docNo, boolean isCreate, long insPolicyId, long collateralId)
			throws CollateralException, SearchDAOException {
		return getCollateralDao().getDocumentNoCount(docNo, isCreate, insPolicyId, collateralId);
	}

	public List getDisTaskBcaLocationList(long collateralId) throws CollateralException, SearchDAOException {
		return getCollateralDao().getDisTaskBcaLocationList(collateralId);
	}

	public String getCustomerNameByCollateralID(long collateralID) throws CollateralException, SearchDAOException {
		return getCollateralDao().getCustomerNameByCollateralID(collateralID);
	}

	public String getCustomerIDByCollateralID(long collateralID) throws CollateralException, SearchDAOException {
		return getCollateralDao().getCustomerIDByCollateralID(collateralID);
	}

	public ICollateralTrxValue[] systemUpdateCollateralCharge(ITrxContext ctx, ICollateral[] collaterals, long limitID)
			throws CollateralException {
		if ((collaterals == null) || (collaterals.length == 0)) {
			return null;
		}

		List trxValues = new ArrayList();
		for (int i = 0; i < collaterals.length; i++) {
			ICollateralTrxValue trxValue = getCollateralTrxValue(ctx, collaterals[i].getCollateralID());
			ICollateral actualCol = trxValue.getCollateral();
			ICollateral stageCol = trxValue.getStagingCollateral();

			if (actualCol != null) {
				actualCol.setLimitCharges(CollateralProxyUtil.removeCollateralCharges(actualCol.getLimitCharges(),
						limitID));
				actualCol.setCollateralLimits(CollateralProxyUtil.removeCollateralLimit(
						actualCol.getCollateralLimits(), limitID));
			}

			if (stageCol != null) {
				stageCol.setLimitCharges(CollateralProxyUtil.removeCollateralCharges(stageCol.getLimitCharges(),
						limitID));
				stageCol.setCollateralLimits(CollateralProxyUtil.removeCollateralLimit(stageCol.getCollateralLimits(),
						limitID));
			}

			OBCollateralTrxParameter param = new OBCollateralTrxParameter();
			param.setAction(ICMSConstant.ACTION_SYSTEM_UPDATE_COL);
			trxValue = doInTrxController(getCollateralTrxController(), param, constructTrxValue(ctx, trxValue));
			trxValues.add(trxValue);
		}
		return (ICollateralTrxValue[]) trxValues.toArray(new OBCollateralTrxValue[0]);
	}

	public ICollateralTrxValue hostUpdateCollateral(ITrxContext ctx, ICollateralTrxValue trxValue)
			throws CollateralException {
		OBCollateralTrxParameter param = new OBCollateralTrxParameter();
		param.setAction(ICMSConstant.ACTION_HOST_UPDATE_COL);

		return doInTrxController(getCollateralTrxController(), param, constructTrxValue(ctx, trxValue));
	}

	public ICollateralTrxValue checkerRejectCollateral(ITrxContext ctx, ICollateralTrxValue trxValue)
			throws CollateralException {
		OBCollateralTrxParameter param = new OBCollateralTrxParameter();
		param.setAction(getRejectCollateralAction(trxValue));

		return doInTrxController(getCollateralTrxController(), param, constructTrxValue(ctx, trxValue));
	}

	public ICollateralTrxValue[] checkerRejectUpdateCollaterals(ITrxContext[] ctx, ICollateralTrxValue[] trxVals)
			throws CollateralException {
		int size = 0;
		if ((trxVals == null) || ((size = trxVals.length) == 0)) {
			throw new CollateralException("Collateral transaction values are null!");
		}

		ICollateralTrxValue[] results = new OBCollateralTrxValue[size];
		for (int i = 0; i < size; i++) {
			results[i] = checkerRejectCollateral(ctx[i], trxVals[i]);
		}
		return results;
	}

	public ICollateralParameter getCollateralParameter(String countryCode, String subTypeCode)
			throws CollateralException {
		return getActualCollateralBusManager().getColParamByCountryAndSubTypeCode(countryCode, subTypeCode);
	}

	public ICollateralSubType[] getCollateralSubTypeByType(ICollateralType colType) throws CollateralException {
		return getActualCollateralBusManager().getCollateralSubTypeByType(colType);
	}

	public ICollateralType[] getAllCollateralTypes() throws CollateralException {
		return getCollateralDao().getAllCollateralTypes();
	}

	public ICollateralSubType[] getAllCollateralSubTypes() throws CollateralException {
		return getActualCollateralBusManager().getAllCollateralSubTypes();
	}

	public IPledgor getPledgor(long aPledgorID) throws CollateralException {
		return getActualCollateralBusManager().getPledgor(aPledgorID);
	}

	public IPledgor getPledgorBySCIPledgorID(long sciPledgorID) throws CollateralException {
		return getActualCollateralBusManager().getPledgorBySCIPledgorID(sciPledgorID);
	}

	public boolean isCollateralCheckListCompleted(ICollateral collateral) {
		Validate.notNull(collateral, "'collateral' must not be null");

		boolean checkStatus = false;
		try {
			checkStatus = getCheckListProxyManager().getChecklistCompletedStatus(collateral.getCollateralID());
		}
		catch (Throwable t) {
			DefaultLogger.warn(this, "fail to get checklist completed stataus for collateral id ["
					+ collateral.getCollateralID() + "], returns false ", t);
			return checkStatus;
		}

		return checkStatus;
	}

	public IBookingLocation[] getAllBookingLocation() throws CollateralException {
		return getCollateralDao().getAllBookingLocation();
	}

	public boolean isCollateralShared(ICollateral collateral) throws CollateralException {
		Validate.notNull(collateral, "'collatral' must not be null.");

		ICollateralLimitMap[] secMap = collateral.getCollateralLimits();
		if ((secMap == null) || (secMap.length == 0)) {
			return false;
		}
		boolean isShared = false;
		long tempLPID = ICMSConstant.LONG_INVALID_VALUE;

		for (int i = 0; i < secMap.length; i++) {
			if (tempLPID == ICMSConstant.LONG_INVALID_VALUE) {
				tempLPID = secMap[i].getSCILimitProfileID();
			}
			else if (tempLPID != secMap[i].getSCILimitProfileID()) {
				isShared = true;
				break;
			}
		}
		return isShared;
	}

	public int getCollateralCountForPledgor(long pledgorID) throws CollateralException, SearchDAOException {
		return getCollateralDao().getCollateralCountForPledgor(pledgorID);
	}

	/**
	 * Get the number of securities a pledgor has.
	 * 
	 * @param countryCode security location
	 * @return Collateral valuer of the security location
	 * @throws CollateralException on errors
	 */
	public Map getCollateralValuer(String countryCode) throws CollateralException, SearchDAOException {
		return getCollateralDao().getCollateralValuer(countryCode);
	}

	/**
	 * Get a list of pledgors belong to a collateral.
	 * 
	 * @param collateralID cms collateral ID
	 * @return a list of ICollateralPledgor objects
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralPledgor[] getCollateralPledgors(long collateralID) throws CollateralException, SearchDAOException {
		return getCollateralDao().getCollateralPledgors(collateralID);
	}

	public IValuationModel getCollateralCMVFSV(ICollateral collateral) throws CollateralException {

		IValuationModel valModel = getValuationHandler().performOnlineValuation(collateral);
		valModel.setValuationType(ICMSConstant.VALUATION_SOURCE_TYPE_A);
		Amount cmvAmt = valModel.getValOMV();
		Amount fsvAmt = valModel.getValFSV();

		if (collateral.getSourceValuation() != null) {
			IValuation sysVal = collateral.getSourceValuation();
			if (cmvAmt != null) {
				sysVal.setCurrencyCode(cmvAmt.getCurrencyCode());
				sysVal.setCMV(cmvAmt);
			}
			if (fsvAmt != null) {
				sysVal.setFSV(fsvAmt);
			}

			sysVal.setValuationDate(new Date());
			sysVal.setSourceType(ICMSConstant.VALUATION_SOURCE_TYPE_A);
			sysVal.setSourceId(ICMSConstant.SOURCE_SYSTEM_CMS);

		}

		return getValuationHandler().calculateSecCmvFsv(valModel);

	}

	/**
	 * update security perfection date by AA Number
	 * 
	 * @param aANumber AA Number
	 * @return updatedrecord count
	 * @throws CollateralException on errors
	 */
	public int updateSecPerfectDateByAANumber(long aANumber) throws CollateralException, SearchDAOException {
		return getCollateralDao().updateSecPerfectDateByAANumber(aANumber);
	}

	public String getLiquidationIsNPL(long collateralId) throws CollateralException, SearchDAOException {
		return getCollateralDao().getLiquidationIsNPL(collateralId);
	}

	public ICollateralTrxValue makerDeleteCollateral(ITrxContext ctx, ICollateralTrxValue trxValue)
			throws CollateralException {
		OBCollateralTrxParameter param = new OBCollateralTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_DELETE_COL);
		trxValue.setTransactionSubType(null);

		return doInTrxController(getCollateralTrxController(), param, constructTrxValue(ctx, trxValue));
	}
	
	public ICollateralTrxValue makerDeleteCollateralRest(ITrxContext ctx, ICollateralTrxValue trxValue)
			throws CollateralException {
		OBCollateralTrxParameter param = new OBCollateralTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_DELETE_COL);
		trxValue.setTransactionSubType(null);

		return doInTrxController(getCollateralTrxController(), param, constructTrxValue(ctx, trxValue));
	}

	public ICollateralTrxValue systemApproveCollateral(ITrxContext context, ICollateralTrxValue trxValue)
			throws CollateralException {
		OBCollateralTrxParameter param = new OBCollateralTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_APPROVE_COL);

		trxValue.setTrxContext(context);
		return doInTrxController(getCollateralTrxController(), param, constructTrxValue(context, trxValue));
	}

	public ICollateralTrxValue systemRejectCollateral(ITrxContext context, ICollateralTrxValue trxValue)
			throws CollateralException {
		OBCollateralTrxParameter param = new OBCollateralTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_REJECT_COL);

		trxValue.setTrxContext(context);
		return doInTrxController(getCollateralTrxController(), param, constructTrxValue(context, trxValue));
	}

	public void updateOrInsertStpAllowedIndicator(ICollateralTrxValue trxValue, boolean isStpAllowed) {
		try {
			getCollateralDao().updateOrInsertStpReadyStatus(trxValue.getTransactionID(), isStpAllowed);
		}
		catch (Throwable ex) {
			logger.warn("failed to update or insert stp ready status indicator for transaction id ["
					+ trxValue.getTransactionID() + "], please verify.", ex);
		}
	}

	/**
	 * Helper method to contruct transaction value.
	 * 
	 * @param ctx of type ITrxContext
	 * @param trxValue of type ITrxValue
	 * @return transaction value
	 */
	private ICollateralTrxValue constructTrxValue(ITrxContext ctx, ICollateralTrxValue trxValue)
			throws CollateralException {

		if ((ctx != null) && (ctx.getCollateralID() != ICMSConstant.LONG_INVALID_VALUE)) {
			ctx.setCustomer(null);
			ctx.setLimitProfile(null);
			if (trxValue.getCollateral() != null) {
				//trxValue.setLegalName(String.valueOf(trxValue.getCollateral().getSCISecurityID()));
				trxValue.setLegalName(trxValue.getLegalName());
				trxValue.setCustomerName(trxValue.getCollateral().getCollateralType().getTypeName() + " - "
						+ trxValue.getCollateral().getCollateralSubType().getSubTypeName());
				//trxValue.setLegalID(null);
				//trxValue.setCustomerID(ICMSConstant.LONG_INVALID_VALUE);
				//trxValue.setLimitProfileID(ICMSConstant.LONG_INVALID_VALUE);
				trxValue.setLegalID(String.valueOf(trxValue.getCollateral().getSCISecurityID()));
				trxValue.setLimitProfileID(trxValue.getLimitProfileID());
				trxValue.setLimitProfileReferenceNumber(trxValue.getLimitProfileReferenceNumber());
			}
		}
		/**
		 * TODO: commented pls check
		 */
		// ctx.setTrxSegment(ICMSConstant.SEGMENT_INVALID_VALUE);
		trxValue.setTrxContext(ctx);

		return trxValue;
	}

	protected ICollateralTrxValue doInTrxController(ITrxController trxController, ICollateralTrxParameter trxParam,
			ICollateralTrxValue trxValue) throws CollateralException {
		Validate.notNull(trxController, "'trxController' must not be null");
		Validate.notNull(trxParam, "'trxParam' must not be null");
		Validate.notNull(trxValue, "'trxValue' must not be null");

		try {
			ICMSTrxResult result = (ICMSTrxResult) trxController.operate(trxValue, trxParam);

			return (ICollateralTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			throw new CollateralException("failed to operate collateral workflow", e);
		}
	}
	
	protected ICMSTrxResult doInTrxControllerRest(ITrxController trxController, ICollateralTrxParameter trxParam,
			ICollateralTrxValue trxValue) throws CollateralException {
		Validate.notNull(trxController, "'trxController' must not be null");
		Validate.notNull(trxParam, "'trxParam' must not be null");
		Validate.notNull(trxValue, "'trxValue' must not be null");

		try {
			ICMSTrxResult result = (ICMSTrxResult) trxController.operate(trxValue, trxParam);

			return result;
		}
		catch (TransactionException e) {
			throw new CollateralException("failed to operate collateral workflow", e);
		}
	}

	protected String getVerifyCollateralAction(ICollateralTrxValue trxValue) throws CollateralException {
		String status = trxValue.getStatus();
		// Andy Wong, 13 Dec 2008: enable checker to refire stp for pending
		// retry trx
		if (status.equals(ICMSConstant.STATE_PENDING_UPDATE)
				|| status.equals(ICMSConstant.STATE_PENDING_CREATE)
				|| (status.equals(ICMSConstant.STATE_PENDING_RETRY) && !trxValue.getFromState().equals(
						ICMSConstant.STATE_LOADING_DELETE))) {
			return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_COL;
		}
		else if (status.equals(ICMSConstant.STATE_PENDING_DELETE)
				|| (status.equals(ICMSConstant.STATE_PENDING_RETRY) && trxValue.getFromState().equals(
						ICMSConstant.STATE_LOADING_DELETE))) {
			return ICMSConstant.ACTION_CHECKER_APPROVE_DELETE_COL;
		}
		else {
			throw new CollateralException("Cannot verify for the status [" + status + "] transaction id ["
					+ trxValue.getTransactionID() + "]");
		}
	}

	protected String getRejectCollateralAction(ICollateralTrxValue trxValue) throws CollateralException {
		String status = trxValue.getStatus();
		if (status.equals(ICMSConstant.STATE_PENDING_UPDATE) || status.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			return ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_COL;
		}
		else if (status.equals(ICMSConstant.STATE_PENDING_DELETE)) {
			return ICMSConstant.ACTION_CHECKER_REJECT_DELETE_COL;
		}
		else {
			throw new CollateralException("Cannot reject for the status [" + status + "] transaction id ["
					+ trxValue.getTransactionID() + "]");
		}
	}

	/**
	 * Helper method to fill in parent trx id.
	 * 
	 * @param trxValues a list of transaction values
	 */
	private void fillParentTrxID(ICollateralTrxValue[] trxValues) {
		String trxID = trxValues[0].getTransactionID();
		for (int i = 0; i < trxValues.length; i++) {
			trxValues[i].setTrxReferenceID(trxID);
		}
	}
	private SessionContext _context = null;

	public boolean getPolicyNumber(String policyNo, String insurancePolicyReferenceNum) throws CollateralException,
			SearchDAOException {
		return getCollateralDao().getPolicyNumber(policyNo, insurancePolicyReferenceNum);
	}

	public boolean getCollateralName(String collateralName, long cmsCollateralId) throws CollateralException,
			SearchDAOException {
		return getCollateralDao().getCollateralName(collateralName, cmsCollateralId);
	}
	

	/*public ICollateralTrxValue createLien(ICollateralTrxValue value)
			throws CollateralException {
		try {
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_CREATE_LIEN);

			ITrxController controller = (new CollateralTrxControllerFactory()).getController(value, param);
			ITrxResult result = controller.operate(value, param);
			return (ICollateralTrxValue) result.getTrxValue();
		}
		catch (TransactionException e) {
			_context.setRollbackOnly();
			throw new CustomerException("Failed to create customer via workflows", e);
		}
	}*/
	public ICollateralTrxValue createLien(ICollateralTrxValue trxValue) throws CollateralException {
		OBCollateralTrxParameter param = new OBCollateralTrxParameter();
		param.setAction(ICMSConstant.ACTION_CREATE_LIEN);

		return doInTrxController(getCollateralTrxController(), param, constructTrxValue(trxValue.getTrxContext(),
				trxValue));
	}
	
	public List getRecurrentDueDateListByCustomerAndCollatralID(
			long customerID, long cmsCollatralId) {
		return getCollateralDao().getRecurrentDueDateListByCustomerAndCollatralID(customerID,cmsCollatralId);
	}
	
	public ILimit getReleasableAmountByCollateralID(long collateralID) throws CollateralException, SearchDAOException {
		return getCollateralDao().getReleasableAmountByCollateralID(collateralID);
	}	
	public String getStatementNameByDocCode(String docCode) throws CollateralException, SearchDAOException{
		return getCollateralDao().getStatementNameByDocCode(docCode);
	}
	//Start Santosh IRB
	public String getDpCalculateManuallyByDateAndDocCode(Date selectedDueDate,String selectedDocCode,Long cmsCollateralId) throws CollateralException, SearchDAOException{
		return getCollateralDao().getDpCalculateManuallyByDateAndDocCode(selectedDueDate,selectedDocCode,cmsCollateralId);
	}
	
	public ICollateralTrxValue makerUpdateCollateralRest(ITrxContext ctx, ICollateralTrxValue trxValue, ICollateral col)
			throws CollateralException {
		OBCollateralTrxParameter param = new OBCollateralTrxParameter();

		if (trxValue.getCollateral() == null) {
			param.setAction(ICMSConstant.ACTION_MAKER_CREATE_COL);
		}
		else {
			param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_COL);
		}

		trxValue.setTransactionSubType(null);
		trxValue.setStagingCollateral(col);

		return doInTrxController(getCollateralTrxController(), param, constructTrxValue(ctx, trxValue));
	}

	
	//End Santosh IRB
	//Stock DP calculation
	public List getStockAndDateDetailsWithAssetId(Long cmsCollateralId) throws CollateralException, SearchDAOException{
		return getCollateralDao().getStockAndDateDetailsWithAssetId(cmsCollateralId);
	}
	
	public List getStockAndDateDetailsForEachAssetId(String assetId) throws CollateralException, SearchDAOException{
		return getCollateralDao().getStockAndDateDetailsForEachAssetId(assetId);
		}
	
	public List getLocationForEachAssetId(String assetId) throws CollateralException, SearchDAOException{
		return getCollateralDao().getLocationForEachAssetId(assetId);
		}
	
	public List getStockDisplayList(String assetId) throws CollateralException, SearchDAOException{
		return getCollateralDao().getStockDisplayList(assetId);
		}
	
	public ICollateralTrxValue updateCollateralWithApprovalThroughREST(ITrxContext context, ICollateralTrxValue trxValue, ICollateral col) throws CollateralException
	{
		ICollateralTrxValue trxResult = new OBCollateralTrxValue();
		try{
			
		
			if (null == trxValue) {
				throw new CollateralException("ICollateralTrxValue is null!");
			}
		
			
			MakerCheckerUserUtil mcUtil = (MakerCheckerUserUtil) BeanHouse.get("makerCheckerUserUtil");
			context = mcUtil.setContextForMaker();
			trxValue =makerUpdateCollateralRest(context, trxValue, col);
			updateInsuranceForActual(trxValue);
			context = mcUtil.setContextForChecker();
			trxResult = checkerVerifyCollateral(context, trxValue);
			
			return trxResult;
		}catch (CollateralException e) {
			rollback();
			throw e;
		}
		catch (Exception e) {
			rollback();
			throw new CustomerException("Exception caught! " + e.toString(), e);
		}
	}
	
	public ICollateralTrxValue updateInsuranceForActual(ICollateralTrxValue trxValue) {
		IInsuranceGCDao insuranceGCDao = (IInsuranceGCDao) BeanHouse.get("insuranceGcDao");
		if (trxValue.getCollateral() instanceof OBSpecificChargePlant || trxValue.getCollateral() instanceof OBSpecificChargeAircraft
				|| trxValue.getCollateral() instanceof OBCommercialGeneral) {
			IInsurancePolicy[] insurancePlantList = trxValue.getStagingCollateral().getInsurancePolicies();
			
			List<IInsurancePolicy> iInsurancePolicy=new ArrayList();
			
			
			for(int i=0;i<insurancePlantList.length;i++){
				
				if("PENDING_RECEIVED".equals(insurancePlantList[i].getInsuranceStatus())){
					
					
					OBInsurancePolicyColl newOBInsurancePolicy= new OBInsurancePolicyColl();
					newOBInsurancePolicy.setOriginalTargetDate(insurancePlantList[i].getExpiryDate());
					newOBInsurancePolicy.setLastApproveBy(insurancePlantList[i].getLastApproveBy());
					newOBInsurancePolicy.setLastApproveOn(insurancePlantList[i].getLastApproveOn());
					newOBInsurancePolicy.setLastUpdatedBy(insurancePlantList[i].getLastUpdatedBy());
					newOBInsurancePolicy.setLastUpdatedOn(insurancePlantList[i].getLastUpdatedOn());
					newOBInsurancePolicy.setStatus("ACTIVE");
					newOBInsurancePolicy.setCmsCollateralId(trxValue.getStagingCollateral().getCollateralID());
					newOBInsurancePolicy.setInsuranceStatus(ICMSConstant.STATE_ITEM_AWAITING);
					
					IInsurancePolicy createInsurancePolicy = insuranceGCDao.createAndUpdateInsurancePolicy("stageInsurancepolicy",newOBInsurancePolicy);
					
					iInsurancePolicy.add(createInsurancePolicy);
					
				}				
				if("PENDING_RECEIVED".equals(insurancePlantList[i].getInsuranceStatus()) || "UPDATE_RECEIVED".equals(insurancePlantList[i].getInsuranceStatus())){
					insurancePlantList[i].setInsuranceStatus(ICMSConstant.STATE_ITEM_RECEIVED);
				}
				else if(ICMSConstant.STATE_ITEM_PENDING_WAIVER.equals(insurancePlantList[i].getInsuranceStatus())){
					insurancePlantList[i].setInsuranceStatus(ICMSConstant.STATE_ITEM_WAIVED);
				}
				else if(ICMSConstant.STATE_ITEM_PENDING_DEFERRAL.equals(insurancePlantList[i].getInsuranceStatus())){
					insurancePlantList[i].setInsuranceStatus(ICMSConstant.STATE_ITEM_DEFERRED);
				}
				else if(ICMSConstant.STATE_ITEM_PENDING_UPDATE.equals(insurancePlantList[i].getInsuranceStatus())){
					insurancePlantList[i].setInsuranceStatus(ICMSConstant.STATE_ITEM_AWAITING);
				}else{
					insurancePlantList[i].setInsuranceStatus(insurancePlantList[i].getInsuranceStatus());
				}
				iInsurancePolicy.add(insurancePlantList[i]);
			}
			
			IInsurancePolicy[] iInsurancePolicyTemp = new IInsurancePolicy[iInsurancePolicy.size()];
			trxValue.getStagingCollateral().setInsurancePolicies(iInsurancePolicy.toArray(iInsurancePolicyTemp));
			
			
			
			IAddtionalDocumentFacilityDetails[] addDocFacDetPlantList = trxValue.getStagingCollateral().getAdditonalDocFacDetails();
			
			List<IAddtionalDocumentFacilityDetails> iaddDocFacDetList=new ArrayList();
			
			
			for(int i=0;i<addDocFacDetPlantList.length;i++){
				if("PENDING_EDIT".equals(addDocFacDetPlantList[i].getAddFacDocStatus()) 
						|| "PENDING_UPDATE".equals(addDocFacDetPlantList[i].getAddFacDocStatus())){
					addDocFacDetPlantList[i].setAddFacDocStatus("SUCCESS");
				}
				iaddDocFacDetList.add(addDocFacDetPlantList[i]);
			}
			
			IAddtionalDocumentFacilityDetails[] iAddDocFacDetTemp = new IAddtionalDocumentFacilityDetails[iaddDocFacDetList.size()];
			trxValue.getStagingCollateral().setAdditonalDocFacDetails(iaddDocFacDetList.toArray(iAddDocFacDetTemp));
		}
		return trxValue;
		
	}
	
	public ICollateralTrxValue deleteCollateralWithApprovalThroughREST(ITrxContext context, ICollateralTrxValue trxValue, ICollateral col) throws CollateralException
	{
		ICollateralTrxValue trxResult = new OBCollateralTrxValue();
		try{
			
		
			if (null == trxValue) {
				throw new CollateralException("ICollateralTrxValue is null!");
			}
		
			
			MakerCheckerUserUtil mcUtil = (MakerCheckerUserUtil) BeanHouse.get("makerCheckerUserUtil");
			context = mcUtil.setContextForMaker();
			trxValue =makerDeleteCollateral(context, trxValue);
			context = mcUtil.setContextForChecker();
			trxResult = checkerVerifyCollateral(context, trxValue);
			
			return trxResult;
		}catch (CollateralException e) {
			rollback();
			throw e;
		}
		catch (Exception e) {
			rollback();
			throw new CustomerException("Exception caught deleteCollateralWithApprovalThroughREST! " + e.toString(), e);
		}
		
		
	}
	protected void rollback() throws CollateralException {
		try {
			_context.setRollbackOnly();
		}
		catch (Exception e) {
			throw new CollateralException(e.toString());
		}
	}
}