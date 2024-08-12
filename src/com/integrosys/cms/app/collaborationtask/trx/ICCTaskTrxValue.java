/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/trx/ICCTaskTrxValue.java,v 1.1 2003/08/31 14:00:35 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.trx;

import com.integrosys.cms.app.collaborationtask.bus.ICCTask;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a CC task trx value.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/31 14:00:35 $ Tag: $Name: $
 */
public interface ICCTaskTrxValue extends ICMSTrxValue {
	/**
	 * Get the CC task business entity
	 * 
	 * @return ICCTask
	 */
	public ICCTask getCCTask();

	/**
	 * Get the staging CC task business entity
	 * 
	 * @return ICCTask
	 */
	public ICCTask getStagingCCTask();

	/**
	 * Set the CC task business entity
	 * 
	 * @param value is of type ICCTask
	 */
	public void setCCTask(ICCTask value);

	/**
	 * Set the staging CC task business entity
	 * 
	 * @param value is of type ICCTask
	 */
	public void setStagingCCTask(ICCTask value);
}