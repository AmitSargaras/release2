package com.integrosys.cms.app.creditriskparam.trx.policycap;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCap;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class OBPolicyCapTrxValue extends OBCMSTrxValue implements IPolicyCapTrxValue {

	private IPolicyCap[] policyCap = null;

	private IPolicyCap[] stagePolicyCap = null;

	/**
	 * Default Constructor
	 */
	public OBPolicyCapTrxValue() {
	}

	public OBPolicyCapTrxValue(IPolicyCap[] obj) {
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSTrxValue - ICMSTrxValue
	 */
	public OBPolicyCapTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	public IPolicyCap[] getPolicyCap() {
		return policyCap;
	}

	public void setPolicyCap(IPolicyCap[] value) {
		policyCap = value;
	}

	public IPolicyCap[] getStagingPolicyCap() {
		return stagePolicyCap;
	}

	public void setStagingPolicyCap(IPolicyCap[] value) {
		stagePolicyCap = value;
	}
}
