/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/warehouse/IWarehouse.java,v 1.4 2004/08/27 08:29:27 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.warehouse;

import java.io.Serializable;

import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;

/**
 * This interface represents the warehouse.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/27 08:29:27 $ Tag: $Name: $
 */
public interface IWarehouse extends ICommodityMainInfo, Serializable {
	/**
	 * Get the warehouse ID.
	 * 
	 * @return long - warehouse ID
	 */
	public long getWarehouseID();

	/**
	 * Set the warehouse ID.
	 * 
	 * @param warehouseID - long
	 */
	public void setWarehouseID(long warehouseID);

	/**
	 * Get the name of the warehouse.
	 * 
	 * @return String - warehouse name
	 */
	public String getName();

	/**
	 * Set the name of the warehouse
	 * 
	 * @param name - String
	 */
	public void setName(String name);

	/**
	 * Get the country in which the warehouse is situated in.
	 * 
	 * @return String - ISO country code
	 */
	public String getCountryCode();

	/**
	 * Set the country in which the warehouse is situated in.
	 * 
	 * @param ctryCode - String denoting the ISO country code
	 */
	public void setCountryCode(String ctryCode);

	/**
	 * Get the warehouse's contact.
	 * 
	 * @return IWarehouseContact - warehouse's contact
	 */
	public IWarehouseContact getContact();

	/**
	 * Set the warehouse's contact.
	 * 
	 * @param contact - IWarehouseContact
	 */
	public void setContact(IWarehouseContact contact);

	/**
	 * Get the remarks for the warehouse.
	 * 
	 * @return String - remarks
	 */
	public String getRemarks();

	/**
	 * Set the remarks for the warehouse.
	 * 
	 * @param remarks - String
	 */
	public void setRemarks(String remarks);

	/**
	 * Get the group ID to which the warehouse belongs. Since a set of
	 * warehouses can be submitted in a single transaction, this group ID
	 * identify the set of warehouses that were submitted together.
	 * 
	 * @return long - ID for the group
	 */
	public long getGroupID();

	/**
	 * Set the group ID to which the warehouse belongs to. Since a set of
	 * warehouses can be submitted in a single transaction, this group ID
	 * identify the set of warehouses that were submitted together.
	 * 
	 * @param groupID
	 */
	public void setGroupID(long groupID);

	/**
	 * Get common reference for actual and staging warehouse.
	 * 
	 * @return long
	 */
	public long getCommonRef();

	/**
	 * Set common reference for actual and staging warehouse.
	 * 
	 * @param commonRef of type long
	 */
	public void setCommonRef(long commonRef);

}