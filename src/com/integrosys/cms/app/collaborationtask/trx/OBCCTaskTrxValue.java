/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/trx/OBCCTaskTrxValue.java,v 1.1 2003/08/31 14:00:35 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.trx;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collaborationtask.bus.ICCTask;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This class provides the implementation for ICCTaskTrxValue
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/31 14:00:35 $ Tag: $Name: $
 */
public class OBCCTaskTrxValue extends OBCMSTrxValue implements ICCTaskTrxValue {
	private ICCTask cCTask = null;

	private ICCTask stagingCCTask = null;

	/**
	 * Default Constructor
	 */
	public OBCCTaskTrxValue() {
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSValue - ICMSTrxValue
	 */
	public OBCCTaskTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Get the CC task business entity
	 * 
	 * @return ICCTask
	 */
	public ICCTask getCCTask() {
		return this.cCTask;
	}

	/**
	 * Get the staging CC task business entity
	 * 
	 * @return ICCTask
	 */
	public ICCTask getStagingCCTask() {
		return this.stagingCCTask;
	}

	/**
	 * Set the CC task business entity
	 * 
	 * @param value is of type ICCTask
	 */
	public void setCCTask(ICCTask value) {
		this.cCTask = value;
	}

	/**
	 * Set the staging CC task business entity
	 * 
	 * @param value is of type ICCTask
	 */
	public void setStagingCCTask(ICCTask value) {
		this.stagingCCTask = value;
	}
}