/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/OBRecurrentCheckListTrxValue.java,v 1.1 2003/07/28 02:17:38 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.trx;

//ofa

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This class provides the implementation for IRecurrentCheckListTrxValue
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/28 02:17:38 $ Tag: $Name: $
 */
public class OBRecurrentCheckListTrxValue extends OBCMSTrxValue implements IRecurrentCheckListTrxValue {
	private IRecurrentCheckList checkList = null;

	private IRecurrentCheckList stagingCheckList = null;

	/**
	 * Default Constructor
	 */
	public OBRecurrentCheckListTrxValue() {
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSTrxValue - ICMSTrxValue
	 */
	public OBRecurrentCheckListTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Get the recurrent checklist business entity
	 * 
	 * @return IRecurrentCheckList
	 */
	public IRecurrentCheckList getCheckList() {
		return this.checkList;
	}

	/**
	 * Get the staging checkList business entity
	 * 
	 * @return IRecurrentCheckList
	 */
	public IRecurrentCheckList getStagingCheckList() {
		return this.stagingCheckList;
	}

	/**
	 * Set the recurrent checkList business entity
	 * 
	 * @param anICheckList is of type IRecurrentCheckList
	 */
	public void setCheckList(IRecurrentCheckList anICheckList) {
		this.checkList = anICheckList;
	}

	/**
	 * Set the staging recurrent checkList business entity
	 * 
	 * @param anICheckList is of type IRecurrentCheckList
	 */
	public void setStagingCheckList(IRecurrentCheckList anICheckList) {
		this.stagingCheckList = anICheckList;
	}
}