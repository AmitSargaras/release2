/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limit.bus;

import javax.ejb.EntityContext;

/**
 * This entity bean represents the persistence for threshold rating staging
 * bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBThresholdRatingStagingBean extends EBThresholdRatingBean {

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBThresholdRatingStagingBean() {
	}

}