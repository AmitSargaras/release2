/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus.type.property.marketfactor;

import javax.ejb.EntityContext;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This entity bean represents the persistence for MF Checklist Item staging
 * bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBMFChecklistItemStagingBean extends EBMFChecklistItemBean {

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Get the sequence of primary key for this staging MF Checklist Item.
	 * 
	 * @return String
	 */
	protected String getPKSequenceName() {
		return ICMSConstant.SEQUENCE_MF_CHECKLIST_ITEM_STAGE;
	}

	/**
	 * Default Constructor
	 */
	public EBMFChecklistItemStagingBean() {
	}

}