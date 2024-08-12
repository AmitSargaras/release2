package com.integrosys.cms.app.creditriskparam.trx.sectorlimit;

import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter;

/**
 * Author: Syukri
 * Date: Jun 3, 2008
 */
public class OBSectorLimitParameterTrxValue extends OBCMSTrxValue implements ISectorLimitParameterTrxValue {

	private static final long serialVersionUID = 1L;
	
	private IMainSectorLimitParameter actualMainSectorLimitParameter;
	private IMainSectorLimitParameter stagingMainSectorLimitParameter;

    public OBSectorLimitParameterTrxValue() {
    }

    public OBSectorLimitParameterTrxValue(ITrxValue in) {
        AccessorUtil.copyValue(in, this);
    }
    
	public IMainSectorLimitParameter getActualMainSectorLimitParameter() {
		return actualMainSectorLimitParameter;
	}

	public void setActualMainSectorLimitParameter(
			IMainSectorLimitParameter actualMainSectorLimitParameter) {
		this.actualMainSectorLimitParameter = actualMainSectorLimitParameter;
	}

	public IMainSectorLimitParameter getStagingMainSectorLimitParameter() {
		return stagingMainSectorLimitParameter;
	}

	public void setStagingMainSectorLimitParameter(
			IMainSectorLimitParameter stagingMainSectorLimitParameter) {
		this.stagingMainSectorLimitParameter = stagingMainSectorLimitParameter;
	}

}
