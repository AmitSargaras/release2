/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.bus.policycap;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Purpose: Policy Cap Group Data Transfer Object
 * 
 * @author $Author: siewkheat $<br>
 * @version $Revision: 1.0 $
 * @since $Date: 27/AUG/2007 $ Tag: $Name: $
 */
public class OBPolicyCapGroup implements IPolicyCapGroup {

	private static final long serialVersionUID = -9210636359916022544L;

	private long policyCapGroupId;

	private String stockExchange;

	private String bankEntity;

	private long versionTime;

	private IPolicyCap[] policyCaps;

	public OBPolicyCapGroup() {
		policyCapGroupId = ICMSConstant.LONG_INVALID_VALUE;
		versionTime = ICMSConstant.LONG_INVALID_VALUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup#
	 * getBankEntity()
	 */
	public String getBankEntity() {
		return this.bankEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup#
	 * getPolicyCapGroup()
	 */
	public IPolicyCap[] getPolicyCapArray() {
		return this.policyCaps;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup#
	 * getPolicyGroupID()
	 */
	public long getPolicyCapGroupID() {
		return this.policyCapGroupId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup#
	 * getStockExchange()
	 */
	public String getStockExchange() {
		return this.stockExchange;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup#
	 * getVersionTime()
	 */
	public long getVersionTime() {
		return this.versionTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup#
	 * setBankEntity(java.lang.String)
	 */
	public void setBankEntity(String bankEntity) {
		this.bankEntity = bankEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup#
	 * setPolicyCapGroup(java.util.Collection)
	 */
	public void setPolicyCapArray(IPolicyCap[] policyCapArray) {
		this.policyCaps = policyCapArray;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup#
	 * setPolicyCapGroupID(long)
	 */
	public void setPolicyCapGroupID(long groupID) {
		this.policyCapGroupId = groupID;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup#
	 * setStockExchange(java.lang.String)
	 */
	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup#
	 * setVersionTime(long)
	 */
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;

	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

}
