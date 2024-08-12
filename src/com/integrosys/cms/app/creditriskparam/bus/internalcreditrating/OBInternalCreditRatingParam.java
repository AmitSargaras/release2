package com.integrosys.cms.app.creditriskparam.bus.internalcreditrating;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import java.util.List;

public class OBInternalCreditRatingParam implements IInternalCreditRatingParam {

	private List internalCreditRatingList;	
	
	public List getInternalCreditRatingList() {
		return internalCreditRatingList;
	}
	
	public void setInternalCreditRatingList(List internalCreditRatingList) {
		this.internalCreditRatingList = internalCreditRatingList;
		
	}

}
