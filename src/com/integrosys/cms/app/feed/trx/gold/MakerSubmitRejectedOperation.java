package com.integrosys.cms.app.feed.trx.gold;

import com.integrosys.cms.app.common.constant.ICMSConstant;

public class MakerSubmitRejectedOperation extends MakerUpdateRejectedOperation {

	public MakerSubmitRejectedOperation() {
		super();
	}

	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_GOLD_FEED_GROUP;
	}
}
