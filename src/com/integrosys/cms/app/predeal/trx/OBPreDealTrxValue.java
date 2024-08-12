/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * OBPreDealTrxValue
 *
 * Created on 6:09:27 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.predeal.bus.IPreDeal;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 22, 2007 Time: 6:09:27 PM
 */
public class OBPreDealTrxValue extends OBCMSTrxValue implements IPreDealTrxValue {
	private IPreDeal actual;

	private IPreDeal staging;

	public OBPreDealTrxValue() {
		super();
	}

	public OBPreDealTrxValue(ICMSTrxValue in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	public OBPreDealTrxValue(IPreDealTrxValue in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	public OBPreDealTrxValue(ITrxValue in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	public IPreDeal getPreDeal() {
		return actual;
	}

	public IPreDeal getStagingPreDeal() {
		return staging;
	}

	public void setPreDeal(IPreDeal actualData) {
		actual = actualData;
	}

	public void setStagingPreDeal(IPreDeal stagingData) {
		staging = stagingData;
	}

}