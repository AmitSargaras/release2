/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.app.chktemplate.bus;

/**
 * This is the implementation class for the IDocumentItem
 * 
 * @author $Author: sathish $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/22 11:13:25 $ Tag: $Name: $
 */
public class OBDocumentItem extends OBItem implements IDocumentItem {

	private static final long serialVersionUID = 6437948946416370019L;

	private long versionTime = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	/**
	 * Getter method for version time
	 * @return long - the version time
	 */
	public long getVersionTime() {
		return this.versionTime;
	}

	/**
	 * Setter method for version time
	 * @param aVersionTime - long
	 */
	public void setVersionTime(long aVersionTime) {
		this.versionTime = aVersionTime;
	}
}
