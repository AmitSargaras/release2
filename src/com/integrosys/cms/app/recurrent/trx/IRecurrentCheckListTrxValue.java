/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/IRecurrentCheckListTrxValue.java,v 1.1 2003/07/28 02:17:38 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.trx;

import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a recurrent checkList trx value.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/28 02:17:38 $ Tag: $Name: $
 */
public interface IRecurrentCheckListTrxValue extends ICMSTrxValue {
	/**
	 * Get the recurrent checklist business entity
	 * 
	 * @return IRecurrentCheckList
	 */
	public IRecurrentCheckList getCheckList();

	/**
	 * Get the staging recurrent checkList business entity
	 * 
	 * @return IRecurrentCheckList
	 */
	public IRecurrentCheckList getStagingCheckList();

	/**
	 * Set the recurrent checkList business entity
	 * 
	 * @param value is of type ICheckList
	 */
	public void setCheckList(IRecurrentCheckList value);

	/**
	 * Set the staging recurrent checkList business entity
	 * 
	 * @param value is of type ICheckList
	 */
	public void setStagingCheckList(IRecurrentCheckList value);
}