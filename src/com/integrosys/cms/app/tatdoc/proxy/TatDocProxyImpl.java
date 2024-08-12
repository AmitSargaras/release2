package com.integrosys.cms.app.tatdoc.proxy;

import java.util.*;

import com.integrosys.cms.app.maintaintat.bus.OBTatLimitTrackStage;
import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.maintaintat.bus.OBTatLimitTrack;
import com.integrosys.cms.app.tatdoc.bus.ITatDoc;
import com.integrosys.cms.app.tatdoc.bus.ITatDocBusManager;
import com.integrosys.cms.app.tatdoc.bus.TatDocException;
import com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue;
import com.integrosys.cms.app.tatdoc.trx.OBTatDocTrxValue;
import com.integrosys.cms.app.tatduration.bus.ITatParam;
import com.integrosys.cms.app.tatduration.bus.OBTatParamItem;
import com.integrosys.cms.app.tatduration.bus.TatParamException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * Created by IntelliJ IDEA. User: Cynthia Date: Aug 27, 2008 Time: 5:18:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class TatDocProxyImpl implements ITatDocProxy {

	private ITatDocBusManager tatDocBusManager;

	private ITatDocBusManager stageTatDocBusManager;

	private ITrxController tatDocReadController;

	private ITrxController tatDocTrxController;

	public ITatDocBusManager getTatDocBusManager() {
		return tatDocBusManager;
	}

	public void setTatDocBusManager(ITatDocBusManager tatDocBusManager) {
		this.tatDocBusManager = tatDocBusManager;
	}

	public ITatDocBusManager getStageTatDocBusManager() {
		return stageTatDocBusManager;
	}

	public void setStageTatDocBusManager(ITatDocBusManager stageTatDocBusManager) {
		this.stageTatDocBusManager = stageTatDocBusManager;
	}

	public ITrxController getTatDocReadController() {
		return tatDocReadController;
	}

	public void setTatDocReadController(ITrxController tatDocReadController) {
		this.tatDocReadController = tatDocReadController;
	}

	public ITrxController getTatDocTrxController() {
		return tatDocTrxController;
	}

	public void setTatDocTrxController(ITrxController tatDocTrxController) {
		this.tatDocTrxController = tatDocTrxController;
	}

	// *********************
	// Read Methods
	// *********************
	/**
	 * Get the (default) actual tat doc transaction object via limit profile id
	 * @param limitProfileID - Limit Profile ID to retrieve the tat doc trxValue
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws com.integrosys.cms.app.tatdoc.bus.TatDocException on errors
	 */
	public ITatDoc getTatDocByLimitProfileID(long limitProfileID) throws TatDocException {
		return getTatDocBusManager().getTatDocByLimitProfileID(limitProfileID);
	}

	public ITatDocTrxValue getTrxValueByLimitProfileID(long limitProfileID) throws TatDocException {
		String[] returnResult = getTatDocBusManager().getTrxValueByLimitProfileID(limitProfileID);
		String trxId = returnResult[0];
		// String solicitorDate = returnResult[1];
		return trxId != null ? getTatDocTrxValueByTrxID(trxId) : null;
	}

	public Date getDateOfInstructionToSolicitor(long limitProfileId) {
		return getTatDocBusManager().getDateOfInstructionToSolicitor(limitProfileId);
	}

	/**
	 * Get the tat document transaction object via primary key
	 * @param pk - Primary Key used to retrieve the trxValue
	 * @return ITatDocTrxValue
	 * @throws TatDocException on errors
	 */
	public ITatDocTrxValue getTatDocTrxValue(long pk) throws TatDocException {
		if (pk == ICMSConstant.LONG_INVALID_VALUE) {
			throw new TatDocException("Invalid primary key. Unable to get ITatDocTrxValue object from invalid pk");
		}

		ITatDocTrxValue trxValue = new OBTatDocTrxValue();
		trxValue.setReferenceID(String.valueOf(pk));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_TAT_DOC);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_TAT_DOC);
		return operate(getTatDocReadController(), trxValue, param);
	}

	/**
	 * Get the tat document transaction object via transaction id
	 * @param trxID - transaction ID
	 * @return ITatDocTrxValue
	 * @throws TatDocException on errors
	 */
	public ITatDocTrxValue getTatDocTrxValueByTrxID(String trxID) throws TatDocException {
		if ((trxID == null) || trxID.equals("")) {
			throw new TatDocException("Invalid trxID. Unable to get ITatDocTrxValue object from invalid transaction id");
		}

		ITatDocTrxValue trxValue = new OBTatDocTrxValue();
		trxValue.setTransactionID(trxID);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_TAT_DOC);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_TAT_DOC_ID);
		return operate(getTatDocReadController(), trxValue, param);
	}

	// *********************
	// Maker-Checker Methods
	// *********************
	/**
	 * Maker creates tat document
	 * @param ctx of ITrxContext type
	 * @param trxValue of ITatDocTrxValue type
	 * @param tatDoc of ITatDoc type
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws TatDocException on errors
	 */
	public ITatDocTrxValue makerCreateTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue, ITatDoc tatDoc)
			throws TatDocException {
		Validate.notNull(ctx, "ITrxContext must not be null");
		Validate.notNull(tatDoc, "ITatDoc must not be null");
		trxValue = formulateTrxValue(ctx, trxValue, tatDoc);
		DefaultLogger.debug(this, "in TatDocProxyImpl");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_TAT_DOC);
		return operate(getTatDocTrxController(), trxValue, param);
	}

	public ITatDocTrxValue makerSaveTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue, ITatDoc tatDoc)
			throws TatDocException {
		Validate.notNull(ctx, "ITrxContext must not be null");
		Validate.notNull(tatDoc, "ITatDoc must not be null");
		trxValue = formulateTrxValue(ctx, trxValue, tatDoc);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_TAT_DOC);
		return operate(getTatDocTrxController(), trxValue, param);
	}

	/**
	 * Maker updates tat document
	 * @param ctx of ITrxContext type
	 * @param trxValue of ITatDocTrxValue type
	 * @param tatDoc of ITatDoc type
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws TatDocException on errors
	 */
	public ITatDocTrxValue makerUpdateTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue, ITatDoc tatDoc)
			throws TatDocException {
		Validate.notNull(ctx, "ITrxContext must not be null");
		Validate.notNull(trxValue, "ITatDocTrxValue must not be null");
		Validate.notNull(tatDoc, "ITatDoc must not be null");
		trxValue = formulateTrxValue(ctx, trxValue, tatDoc);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_TAT_DOC);
		return operate(getTatDocTrxController(), trxValue, param);
	}

	/**
	 * Approve the tat document update.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - tat document to be approved
	 * @return Approved tat document
	 * @throws TatDocException on errors
	 */
	public ITatDocTrxValue checkerApproveTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue) throws TatDocException {
		Validate.notNull(ctx, "ITrxContext must not be null");
		Validate.notNull(trxValue, "ITatDocTrxValue must not be null");
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_TAT_DOC);
		return operate(getTatDocTrxController(), trxValue, param);
	}

	/**
	 * Rejects the tat document update.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - tat document to be rejected
	 * @return Rejected tat document
	 * @throws TatDocException on errors
	 */
	public ITatDocTrxValue checkerRejectTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue) throws TatDocException {
		Validate.notNull(ctx, "ITrxContext must not be null");
		Validate.notNull(trxValue, "ITatDocTrxValue must not be null");
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_TAT_DOC);
		return operate(getTatDocTrxController(), trxValue, param);
	}

	/**
	 * Close the (rejected) tat document.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - tat document to be closed
	 * @return Closed tat document
	 * @throws TatDocException on errors
	 */
	public ITatDocTrxValue makerCloseTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue) throws TatDocException {
		Validate.notNull(ctx, "ITrxContext must not be null");
		Validate.notNull(trxValue, "ITatDocTrxValue must not be null");
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_TAT_DOC);
		return operate(getTatDocTrxController(), trxValue, param);
	}

	/**
	 * Close the (rejected) tat document.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - tat document to be closed
	 * @return Closed updated tat document
	 * @throws TatDocException on errors
	 */
	public ITatDocTrxValue makerCloseUpdateTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue) throws TatDocException {
		Validate.notNull(ctx, "ITrxContext must not be null");
		Validate.notNull(trxValue, "ITatDocTrxValue must not be null");
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_TAT_DOC);
		return operate(getTatDocTrxController(), trxValue, param);
	}

	// **************** Helper Methods ************
	private ITatDocTrxValue operate(ITrxController controller, ITrxValue trxVal, ITrxParameter param)
			throws TatDocException {
		if (trxVal == null) {
			throw new TatDocException("ITrxValue is null!");
		}

		try {
			// ITrxController controller = new
			// TatDocTrxControllerFactory().getController(trxVal, param);
			if (controller == null) {
				throw new TatDocException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(trxVal, param);
			ITrxValue obj = result.getTrxValue();
			return (ITatDocTrxValue) obj;

		}
		catch (TransactionException e) {
			throw new TatDocException("TransactionException caught! " + e.toString(), e);
		}
	}

	/**
	 * Formulate the tat document Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anICMSTrxValue - ICMSTrxValue
	 * @param tatDoc - ITatDoc
	 * @return ITatDocTrxValue - the tat document trx interface formulated
	 */
	private ITatDocTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, ITatDoc tatDoc) {
		ITatDocTrxValue tatDocTrxValue = null;
		if (anICMSTrxValue != null) {
			tatDocTrxValue = new OBTatDocTrxValue(anICMSTrxValue);
		}
		else {
			tatDocTrxValue = new OBTatDocTrxValue();
		}
		tatDocTrxValue.setStagingTatDoc(tatDoc);
		tatDocTrxValue = formulateTrxValue(anITrxContext, tatDocTrxValue);
		return tatDocTrxValue;
	}

	/**
	 * Formulate the tat document trx object
	 * @param anITrxContext - ITrxContext
	 * @param trxValue - ITatDocTrxValue
	 * @return ITatDocTrxValue - the tat document trx interface formulated
	 */
	private ITatDocTrxValue formulateTrxValue(ITrxContext anITrxContext, ITatDocTrxValue trxValue) {
		trxValue.setTrxContext(anITrxContext);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_TAT_DOC);
		return trxValue;
	}
	
	public ITatParam getTatParamByApplicationType(String applicationType) throws TatParamException
	{
		return getTatDocBusManager().getTatParamByApplicationType(applicationType);
	}

    public OBTatLimitTrack getTatStageTrackingListByLimitProfileId(long limitProfileId, String applicationType) throws TatDocException {
        OBTatLimitTrack trackOB = getTatDocBusManager().getTatStageTrackingListByLimitProfileId(limitProfileId);

        if (trackOB == null) {
            ITatParam tatParam = getTatParamByApplicationType(applicationType);

            List tatParamItem = new ArrayList(tatParam.getTatParamItemList());

            for (int i = 0; i < tatParamItem.size() - 1; i++) {
                OBTatParamItem item = (OBTatParamItem) tatParamItem.get(i);
                OBTatParamItem nextItem = (OBTatParamItem) tatParamItem.get(i + 1);
                if (item.getSequenceOrder() > nextItem.getSequenceOrder()) {
                    tatParamItem.set(i, nextItem);
                    tatParamItem.set(i + 1, item);
                    // reset the loop
                    i = -1;
                }
            }

            trackOB = new OBTatLimitTrack();
            trackOB.setLimitProfileId(limitProfileId);
            trackOB.setPreDisbursementRemarks("");
            trackOB.setDisbursementRemarks("");
            trackOB.setPostDisbursementRemarks("");

            Set stageListSet = new LinkedHashSet();

            for (int i = 0; i < tatParamItem.size(); i++) {
                OBTatParamItem item = (OBTatParamItem) tatParamItem.get(i);

                OBTatLimitTrackStage trackStageOB = new OBTatLimitTrackStage();
                trackStageOB.setTatParamItem(item);
                trackStageOB.setTatParamItemId(item.getTatParamItemId());
                trackStageOB.setTatApplicable("Y");
                trackStageOB.setIsTatApplicable(true);
                if (item.getSequenceOrder() == 1)
                    trackStageOB.setStageActive("Y");
                else
                    trackStageOB.setStageActive("N");

                stageListSet.add(trackStageOB);
            }

            trackOB.setStageListSet(stageListSet);
        }

        return trackOB;
    }
	
	public void commitTatTrackingList(OBTatLimitTrack trackOB) throws TatDocException
	{
		getTatDocBusManager().commiTrackingList(trackOB);
	}
	
	public Long getNonWorkingDaysByBranch(String branch, Date startDate, Date endDate) throws TatDocException
	{
		return getTatDocBusManager().getNonWorkingDaysByBranch(branch, startDate, endDate);
	}
}