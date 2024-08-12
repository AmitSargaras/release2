package com.integrosys.cms.app.creditriskparam.trx.productlimit;

import com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Author: Priya
 * Date: Oct 9, 2009
 */
public interface IProductLimitParameterTrxValue extends ICMSTrxValue {
	
	IProductProgramLimitParameter getActualProductProgramLimitParameter();
	void setActualProductProgramLimitParameter(IProductProgramLimitParameter productProgramLimitParameter);
	
	IProductProgramLimitParameter getStagingProductProgramLimitParameter();
	void setStagingProductProgramLimitParameter(IProductProgramLimitParameter productProgramLimitParameter);
	
}
