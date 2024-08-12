package com.integrosys.cms.app.creditriskparam.trx.internalcreditrating;

import java.util.List;

import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * @author priya
 *
 */
public interface IInternalCreditRatingTrxValue extends ICMSTrxValue {
	
	public List getActualICRList();

	public void setActualICRList(List actualICRList);

	public List getStagingICRList();

	public void setStagingICRList(List stagingICRList);

}
