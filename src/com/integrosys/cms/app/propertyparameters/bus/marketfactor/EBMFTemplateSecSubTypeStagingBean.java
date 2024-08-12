/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

import javax.ejb.EntityContext;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This entity bean represents the persistence for MF Template Security SubType
 * staging bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBMFTemplateSecSubTypeStagingBean extends EBMFTemplateSecSubTypeBean {

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBMFTemplateSecSubTypeStagingBean() {
	}

	/**
	 * Get the sequence of primary key for this staging MF Template Security
	 * SubType.
	 * 
	 * @return String
	 */
	protected String getPKSequenceName() {
		return ICMSConstant.SEQUENCE_MF_SEC_SUBTYPE_STAGE;
	}

}