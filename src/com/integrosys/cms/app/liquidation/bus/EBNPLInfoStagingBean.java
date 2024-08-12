/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.app.liquidation.bus;

/**
 * Entity bean implementation for staging NPL Info entity.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public abstract class EBNPLInfoStagingBean extends EBNPLInfoBean {
	protected boolean isStaging() {
		return true;
	}
}