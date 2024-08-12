package com.integrosys.cms.app.function.trx;

import com.integrosys.cms.app.common.constant.ICMSConstant;

public class MakerSubmitRejectedOperation extends MakerUpdateRejectedOperation {
	public MakerSubmitRejectedOperation() {
		super();
	}

	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_TEAM_FUNCTION_GRP;
	}
}
