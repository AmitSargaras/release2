package com.integrosys.cms.app.discrepency.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * 
 * @author sandiip.shinde
 * @since 01-06-2011
 */

public class OBDiscrepencyTrxValue extends OBCMSTrxValue implements IDiscrepencyTrxValue{

	private static final long serialVersionUID = -8925595229657400462L;
	
	private IDiscrepency actualDiscrepency;
	
	private IDiscrepency stagingDiscrepency;	

	public IDiscrepency getActualDiscrepency() {
		return actualDiscrepency;
	}

	public void setActualDiscrepency(IDiscrepency actualDiscrepency) {
		this.actualDiscrepency = actualDiscrepency;
	}

	public IDiscrepency getStagingDiscrepency() {
		return stagingDiscrepency;
	}

	public void setStagingDiscrepency(IDiscrepency stagingDiscrepency) {
		this.stagingDiscrepency = stagingDiscrepency;
	}

	/**
	 * Default Constructor
	 */
	public OBDiscrepencyTrxValue() {
		super.setTransactionType(ICMSConstant.INSTANCE_DISCREPENCY);
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ITrxValue
	 */
	public OBDiscrepencyTrxValue(ITrxValue value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

}
