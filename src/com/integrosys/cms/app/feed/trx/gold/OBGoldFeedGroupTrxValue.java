package com.integrosys.cms.app.feed.trx.gold;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBGoldFeedGroupTrxValue extends OBCMSTrxValue implements IGoldFeedGroupTrxValue {

	private IGoldFeedGroup actual;

	private IGoldFeedGroup staging;

	public OBGoldFeedGroupTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	public OBGoldFeedGroupTrxValue() {

	}

	public IGoldFeedGroup getGoldFeedGroup() {
		return actual;
	}

	public IGoldFeedGroup getStagingGoldFeedGroup() {
		return staging;
	}

	public void setGoldFeedGroup(IGoldFeedGroup value) {
		actual = value;
	}

	public void setStagingGoldFeedGroup(IGoldFeedGroup value) {
		staging = value;
	}

}
