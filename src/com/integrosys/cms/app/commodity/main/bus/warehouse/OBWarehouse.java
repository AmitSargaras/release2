/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/warehouse/OBWarehouse.java,v 1.4 2004/08/27 08:29:27 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.warehouse;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.main.bus.OBCommodityMainInfo;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents warehouse.
 * 
 * @author wltan $
 * @version $
 * @since $Date: 2004/08/27 08:29:27 $ Tag: $Name: $
 */
public class OBWarehouse extends OBCommodityMainInfo implements IWarehouse {

	private long warehouseID = ICMSConstant.LONG_INVALID_VALUE;

	private String name;

	private String countryCode;

	private IWarehouseContact contact;

	private String remarks;

	private long groupID = ICMSConstant.LONG_INVALID_VALUE;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	/**
	 * Default Constructor
	 */
	public OBWarehouse() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type IWarehouseContact
	 */
	public OBWarehouse(IWarehouse value) {
		if (value != null) {
			AccessorUtil.copyValue(value, this);
		}
	}

	/**
	 * Construct OB from paramters.
	 * 
	 * @param warehouseID
	 * @param name
	 * @param countryCode
	 * @param groupID
	 * @param commonRef
	 */
	public OBWarehouse(long warehouseID, String name, String countryCode, long groupID, long commonRef) {
		this.warehouseID = warehouseID;
		this.name = name;
		this.countryCode = countryCode;
		this.groupID = groupID;
		this.commonRef = commonRef;
	}

	/**
	 * Get the warehouse ID.
	 * 
	 * @return long - warehouse ID
	 */
	public long getWarehouseID() {
		return warehouseID;
	}

	/**
	 * Set the warehouse ID.
	 * 
	 * @param warehouseID - long
	 */
	public void setWarehouseID(long warehouseID) {
		this.warehouseID = warehouseID;
	}

	/**
	 * Get the name of the warehouse.
	 * 
	 * @return String - warehouse name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the warehouse
	 * 
	 * @param name - String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the country in which the warehouse is situated in.
	 * 
	 * @return String - ISO country code
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * Set the country in which the warehouse is situated in.
	 * 
	 * @param ctryCode - String denoting the ISO country code
	 */
	public void setCountryCode(String ctryCode) {
		this.countryCode = ctryCode;
	}

	/**
	 * Get the warehouse's contact.
	 * 
	 * @return IWarehouseContact - warehouse's contact
	 */
	public IWarehouseContact getContact() {
		return contact;
	}

	/**
	 * Set the warehouse's contact.
	 * 
	 * @param contact - IWarehouseContact
	 */
	public void setContact(IWarehouseContact contact) {
		this.contact = contact;
	}

	/**
	 * Get the remarks for the warehouse.
	 * 
	 * @return String - remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * Set the remarks for the warehouse.
	 * 
	 * @param remarks - String
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Get the group ID to which the warehouse belongs. Since a set of
	 * warehouses can be submitted in a single transaction, this group ID
	 * identify the set of warehouses that were submitted together.
	 * 
	 * @return long - ID for the group
	 */
	public long getGroupID() {
		return groupID;
	}

	/**
	 * Set the group ID to which the warehouse belongs to. Since a set of
	 * warehouses can be submitted in a single transaction, this group ID
	 * identify the set of warehouses that were submitted together.
	 * 
	 * @param groupID
	 */
	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	/**
	 * Get common reference for actual and staging warehouse.
	 * 
	 * @return long
	 */
	public long getCommonRef() {
		return commonRef;
	}

	/**
	 * Set common reference for actual and staging warehouse.
	 * 
	 * @param commonRef of type long
	 */
	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

}
