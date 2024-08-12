/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/warehouse/OBWarehouseContact.java,v 1.2 2004/08/30 12:44:40 hshii Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.warehouse;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.customer.bus.OBContact;

/**
 * This class represents warehouse contact.
 * 
 * @author wltan $
 * @version $
 * @since $Date: 2004/08/30 12:44:40 $ Tag: $Name: $
 */
public class OBWarehouseContact extends OBContact implements IWarehouseContact {

	private static final String[] EXCLUDE_METHOD = new String[] { "getContactType", "getContactReference",
			"getAttentionParty" };

	/**
	 * Default Constructor
	 */
	public OBWarehouseContact() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type IWarehouseContact
	 */
	public OBWarehouseContact(IWarehouseContact value) {
		this();
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);

	}

	/**
	 * Get contact type.
	 * 
	 * @return String
	 */
	public String getContactType() {
		throw new RuntimeException("Irrelevant for warehouse contact!!");
	}

	/**
	 * Set contact type
	 * 
	 * @param value is of type String
	 */
	public void setContactType(String value) {
		throw new RuntimeException("Irrelevant for warehouse contact!!");
	}

	/**
	 * Get the contact reference
	 * 
	 * @return String
	 */
	public String getContactReference() {
		throw new RuntimeException("Irrelevant for warehouse contact!!");
	}

	/**
	 * Set the contact reference
	 * 
	 * @param value is of type String
	 */
	public void setContactReference(String value) {
		throw new RuntimeException("Irrelevant for warehouse contact!!");
	}
}
