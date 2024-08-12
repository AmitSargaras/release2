/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

import javax.ejb.EntityContext;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This entity bean represents the persistence for MF Item staging bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBMFItemStagingBean extends EBMFItemBean {

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Get the sequence of primary key for this staging MF Item.
	 * 
	 * @return String
	 */
	protected String getPKSequenceName() {
		return ICMSConstant.SEQUENCE_MF_ITEM_STAGE;
	}

	/**
	 * Default Constructor
	 */
	public EBMFItemStagingBean() {
	}

}