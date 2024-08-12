package com.integrosys.cms.app.checklist.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Aug 28, 2006 Time: 7:23:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBSecurityCustomer implements ISecurityCustomer {
	private long securityID = ICMSConstant.LONG_INVALID_VALUE;

	private long collateralID = ICMSConstant.LONG_INVALID_VALUE;

	private String customerCategory;

	private long customerID = ICMSConstant.LONG_INVALID_VALUE;

	private long leID = ICMSConstant.LONG_INVALID_VALUE;

	private String leName;

	private long lspID = ICMSConstant.LONG_INVALID_VALUE;

	private String segment;

	public OBSecurityCustomer() {
	}

	public long getSecurityID() {
		return securityID;
	}

	public void setSecurityID(long securityID) {
		this.securityID = securityID;
	}

	public long getCollateralID() {
		return collateralID;
	}

	public void setCollateralID(long collateralID) {
		this.collateralID = collateralID;
	}

	public String getCustomerCategory() {
		return customerCategory;
	}

	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}

	public long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	public long getLeID() {
		return leID;
	}

	public void setLeID(long leID) {
		this.leID = leID;
	}

	public String getLeName() {
		return leName;
	}

	public void setLeName(String leName) {
		this.leName = leName;
	}

	public long getLspID() {
		return lspID;
	}

	public void setLspID(long lspID) {
		this.lspID = lspID;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
