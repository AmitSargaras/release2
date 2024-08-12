package com.integrosys.cms.app.creditriskparam.trx.productlimit;

import com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.businfra.transaction.ITrxValue;


/**
 * Author: Priya
 * Date: Oct 9, 2009
 */
public class OBProductLimitParameterTrxValue extends OBCMSTrxValue implements IProductLimitParameterTrxValue {

	private static final long serialVersionUID = 1L;
	
	private IProductProgramLimitParameter actualProductProgramLimitParameter;
	private IProductProgramLimitParameter stagingProductProgramLimitParameter;

    public OBProductLimitParameterTrxValue() {
    }

    public OBProductLimitParameterTrxValue(ITrxValue in) {
        AccessorUtil.copyValue(in, this);
    }

	public IProductProgramLimitParameter getActualProductProgramLimitParameter() {
		return actualProductProgramLimitParameter;
	}
	
	public void setActualProductProgramLimitParameter(IProductProgramLimitParameter productProgramLimitParameter) {
		this.actualProductProgramLimitParameter = productProgramLimitParameter;
	}
	
	public IProductProgramLimitParameter getStagingProductProgramLimitParameter() {
		return stagingProductProgramLimitParameter;
	}
	
	public void setStagingProductProgramLimitParameter(IProductProgramLimitParameter productProgramLimitParameter) {
		this.stagingProductProgramLimitParameter = productProgramLimitParameter;
	}
		
}
