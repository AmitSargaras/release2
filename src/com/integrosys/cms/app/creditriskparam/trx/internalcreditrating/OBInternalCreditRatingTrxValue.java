/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.trx.internalcreditrating;

import java.util.List;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * @author priya
 *
 */
public class OBInternalCreditRatingTrxValue extends OBCMSTrxValue implements IInternalCreditRatingTrxValue {
	
	private static final long serialVersionUID = 1L;

	private List actualICRList;

	private List stagingICRList;

	public OBInternalCreditRatingTrxValue (ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	public OBInternalCreditRatingTrxValue() {
	}

	public List getActualICRList() {
		return actualICRList;
	}

	public void setActualICRList(List actualICRList) {
		this.actualICRList = actualICRList;
	}

	public List getStagingICRList() {
		return stagingICRList;
	}

	public void setStagingICRList(List stagingICRList) {
		this.stagingICRList = stagingICRList;
	}

}
