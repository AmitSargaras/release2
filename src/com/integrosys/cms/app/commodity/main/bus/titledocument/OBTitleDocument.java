/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/titledocument/OBTitleDocument.java,v 1.4 2004/07/23 05:59:37 lyng Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.titledocument;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.main.bus.OBCommodityMainInfo;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents a title document type used in commodity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/07/23 05:59:37 $ Tag: $Name: $
 */
public class OBTitleDocument extends OBCommodityMainInfo implements ITitleDocument {
	private long titleDocumentID;

	private String name;

	private String type;

	private long groupID;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	/**
	 * Default constructor.
	 */
	public OBTitleDocument() {
		super();
		this.status = ICMSConstant.STATE_ACTIVE;
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ITitleDocument
	 */
	public OBTitleDocument(ITitleDocument obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get title document type id.
	 * 
	 * @return long
	 */
	public long getTitleDocumentID() {
		return titleDocumentID;
	}

	/**
	 * Set title document type id.
	 * 
	 * @param titleDocumentID of type long
	 */
	public void setTitleDocumentID(long titleDocumentID) {
		this.titleDocumentID = titleDocumentID;
	}

	/**
	 * Get documentation description.
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set documentation description
	 * 
	 * @param name of type String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get title document type, ITitleDocument.NEGOTIABLE or
	 * ITitleDocument.NON_NEGOTIABLE.
	 * 
	 * @return String
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set title document type, ITitleDocument.NEGOTIABLE or
	 * ITitleDocument.NON_NEGOTIABLE.
	 * 
	 * @param type of type String
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Get cms group id.
	 * 
	 * @return long
	 */
	public long getGroupID() {
		return groupID;
	}

	/**
	 * Set cms group id.
	 * 
	 * @param groupID of type long
	 */
	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	/**
	 * Get cms common reference id across actual and staging tables.
	 * 
	 * @return long
	 */
	public long getCommonRef() {
		return commonRef;
	}

	/**
	 * Set cms common reference id across actual and staging tables.
	 * 
	 * @param commonRef of type long
	 */
	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

	/**
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(titleDocumentID);
		return hash.hashCode();
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof ITitleDocument)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}
