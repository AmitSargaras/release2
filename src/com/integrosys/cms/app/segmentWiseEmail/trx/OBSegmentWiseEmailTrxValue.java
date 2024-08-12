package com.integrosys.cms.app.segmentWiseEmail.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.segmentWiseEmail.bus.ISegmentWiseEmail;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBSegmentWiseEmailTrxValue extends OBCMSTrxValue implements
ISegmentWiseEmailTrxValue{

	public OBSegmentWiseEmailTrxValue() {
	}
	
	ISegmentWiseEmail segmentWiseEmail;
	ISegmentWiseEmail stagingSegmentWiseEmail;
	
	public OBSegmentWiseEmailTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	public ISegmentWiseEmail getSegmentWiseEmail() {
		return segmentWiseEmail;
	}

	public void setSegmentWiseEmail(ISegmentWiseEmail segmentWiseEmail) {
		this.segmentWiseEmail = segmentWiseEmail;
	}

	public ISegmentWiseEmail getStagingSegmentWiseEmail() {
		return stagingSegmentWiseEmail;
	}

	public void setStagingSegmentWiseEmail(ISegmentWiseEmail stagingSegmentWiseEmail) {
		this.stagingSegmentWiseEmail = stagingSegmentWiseEmail;
	}
}