/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/OBForexFeedGroupTrxValue.java,v 1.5 2003/08/06 05:42:09 btchng Exp $
 */

package com.integrosys.cms.app.creditriskparam.trx.internallimit;

import java.util.List;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.21 $
 * @since $Date: 2005/08/05 10:12:13 $ Tag: $Name: $
 */

public class OBInternalLimitParameterTrxValue extends OBCMSTrxValue implements
		IInternalLimitParameterTrxValue {

	private static final long serialVersionUID = 1L;

	// a list of IInternalLimitParameter object.
	private List actualILPList;

	private List stagingILPList;

	public OBInternalLimitParameterTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	public OBInternalLimitParameterTrxValue() {
	}

	public List getActualILPList() {
		return actualILPList;
	}

	public void setActualILPList(List actualILPList) {
		this.actualILPList = actualILPList;
	}

	public List getStagingILPList() {
		return stagingILPList;
	}

	public void setStagingILPList(List stagingILPList) {
		this.stagingILPList = stagingILPList;
	}

}
