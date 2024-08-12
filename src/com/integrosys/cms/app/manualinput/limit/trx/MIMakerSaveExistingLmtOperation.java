package com.integrosys.cms.app.manualinput.limit.trx;

public class MIMakerSaveExistingLmtOperation extends MIMakerUpdateLmtOperation {
	public String getOperationName() {
		return TransactionActionConst.ACTION_MANUAL_SAVE_LMT;
	}
}