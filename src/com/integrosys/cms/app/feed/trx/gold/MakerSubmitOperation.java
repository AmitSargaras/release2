package com.integrosys.cms.app.feed.trx.gold;

import com.integrosys.cms.app.common.constant.ICMSConstant;

public class MakerSubmitOperation extends MakerUpdateOperation {

	public MakerSubmitOperation() {
		super();
	}

	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SUBMIT_GOLD_FEED_GROUP;
	}
}
