package com.integrosys.cms.app.segmentWiseEmail.trx;

import com.integrosys.cms.app.segmentWiseEmail.bus.ISegmentWiseEmail;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface ISegmentWiseEmailTrxValue extends ICMSTrxValue{

	public ISegmentWiseEmail getSegmentWiseEmail();

	public ISegmentWiseEmail getStagingSegmentWiseEmail();

	public void setSegmentWiseEmail(ISegmentWiseEmail segmentWiseEmail);

	public void setStagingSegmentWiseEmail(ISegmentWiseEmail stagingSegmentWiseEmail);
}
