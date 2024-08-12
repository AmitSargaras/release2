package com.integrosys.cms.app.recurrent.proxy;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;
import com.integrosys.cms.app.recurrent.trx.OBRecurrentCheckListTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Transaction Helper to facilitate workflow routine of Recurrent. Such
 * formulate transaction value, operation transaction operation.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class RecurrentTransactionHelper {
	/**
	 * Formulate the recurrent checklist Trx Object
	 * @param anITrxContext of ITrxContext type
	 * @param anIRecurrentCheckList of IRecurrentCheckList type
	 * @paran anOperationType - int
	 * @return IRecurrentCheckListTrxValue - the recurrent checklist trx
	 *         interface formulated
	 */
	public static IRecurrentCheckListTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IRecurrentCheckList anIRecurrentCheckList, int anOperationType) {
		return formulateTrxValue(anITrxContext, null, anIRecurrentCheckList, anOperationType);
	}

	/**
	 * Formulate the recurrent checklist Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anICMSTrxValue - ICMSTrxValue
	 * @param anIRecurrentCheckList of IRecurrentCheckList type
	 * @param anOperationType of int type
	 * @return IRecurrentCheckListTrxValue - the checklist trx interface
	 *         formulated
	 */
	public static IRecurrentCheckListTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IRecurrentCheckList anIRecurrentCheckList, int anOperationType) {
		IRecurrentCheckListTrxValue checkListTrxValue = (anICMSTrxValue == null) ? new OBRecurrentCheckListTrxValue()
				: new OBRecurrentCheckListTrxValue(anICMSTrxValue);

		checkListTrxValue.setStagingCheckList(anIRecurrentCheckList);
		checkListTrxValue = formulateTrxValue(anITrxContext, checkListTrxValue, anOperationType);

		return checkListTrxValue;
	}

	/**
	 * Formulate the recurrent checklist trx object
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @return IRecurrentCheckListTrxValue - the checklist trx interface
	 *         formulated
	 */
	public static IRecurrentCheckListTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue, int anOperationType) {
		anICheckListTrxValue.setTrxContext(anITrxContext);
		anICheckListTrxValue.setTransactionType(ICMSConstant.INSTANCE_RECURRENT_CHECKLIST);

		if (ICMSConstant.CHECKLIST_MAINTAIN == anOperationType) {
			anICheckListTrxValue.setTransactionSubType(ICMSConstant.TRX_TYPE_REC_CHECKLIST);
		}
		else if (ICMSConstant.CHECKLIST_RECEIPT == anOperationType) {
			anICheckListTrxValue.setTransactionSubType(ICMSConstant.TRX_TYPE_REC_CHECKLIST_RECEIPT);
		}
		else if (ICMSConstant.CHECKLIST_ANNEXURE == anOperationType) {
			anICheckListTrxValue.setTransactionSubType(ICMSConstant.TRX_TYPE_REC_CHECKLIST_ANNEXURE);
		}

		return anICheckListTrxValue;
	}
}
