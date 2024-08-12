package com.integrosys.cms.app.collateral.bus;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public abstract class EBInstrumentStageBean extends EBInstrumentBean {

	protected String getInstrumentIDSEQ() throws Exception {
		return (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_SECURITY_INSTRUMENT_STAGE, true);
	}
}