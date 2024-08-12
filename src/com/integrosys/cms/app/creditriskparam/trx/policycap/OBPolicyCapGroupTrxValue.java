package com.integrosys.cms.app.creditriskparam.trx.policycap;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Implementation Class of IPolicyCapGroup Interface
 * 
 * @author $Author: Siew Kheat $<br>
 * @version $Revision: $
 * @since $Date: 29/Aug/2007 $ Tag: $Name: $
 */
public class OBPolicyCapGroupTrxValue extends OBCMSTrxValue implements IPolicyCapGroupTrxValue {

	private static final long serialVersionUID = 490179429957058904L;

	private IPolicyCapGroup policyCapGroup;

	private IPolicyCapGroup stagePolicyCapGroup;

	/**
	 * Default constructor
	 */
	public OBPolicyCapGroupTrxValue() {
	}

	public OBPolicyCapGroupTrxValue(IPolicyCapGroupTrxValue obj) {
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSTrxValue - ICMSTrxValue
	 */
	public OBPolicyCapGroupTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapGroupTrxValue
	 * #getPolicyCapGroup()
	 */
	public IPolicyCapGroup getPolicyCapGroup() {
		return this.policyCapGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapGroupTrxValue
	 * #getStagingPolicyGroup()
	 */
	public IPolicyCapGroup getStagingPolicyCapGroup() {
		return this.stagePolicyCapGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapGroupTrxValue
	 * #setPolicyCapGroup(com.integrosys.cms.app.creditriskparam.bus.policycap.
	 * IPolicyCapGroup)
	 */
	public void setPolicyCapGroup(IPolicyCapGroup policyCapGroup) {
		this.policyCapGroup = policyCapGroup;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapGroupTrxValue
	 * #
	 * setStagingPolicyCapGroup(com.integrosys.cms.app.creditriskparam.bus.policycap
	 * .IPolicyCapGroup)
	 */
	public void setStagingPolicyCapGroup(IPolicyCapGroup policyCapGroup) {
		this.stagePolicyCapGroup = policyCapGroup;

	}

}
