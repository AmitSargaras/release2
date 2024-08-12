/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/ICheckListTrxValue.java,v 1.4 2005/10/13 03:36:25 hshii Exp $
 */
package com.integrosys.cms.app.checklist.trx;

import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a CheckList trx value.
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/10/13 03:36:25 $ Tag: $Name: $
 */
public interface ICheckListTrxValue extends ICMSTrxValue {
	/**
	 * Get the checklist busines entity
	 * 
	 * @return ICheckList
	 */
	public ICheckList getCheckList();

	/**
	 * Get the staging checkList business entity
	 * 
	 * @return ICheckList
	 */
	public ICheckList getStagingCheckList();

	/**
	 * Get the send notification indicator
	 * @return boolean - true if needs to send notification and false otherwise
	 */
	public boolean getSendNotificationInd();

	/**
	 * Get the send item notification indicator
	 * @return boolean - true if need to send notification and false otherwise
	 */
	public boolean getSendItemNotificationInd();

	/**
	 * Get the send ready delete notification indicator
	 * @return boolean - true if needs to send ready delete notification and
	 *         false otherwise
	 */
	public boolean getSendReadyDeleteInd();

	/**
	 * Set the checkList busines entity
	 * 
	 * @param value is of type ICheckList
	 */
	public void setCheckList(ICheckList value);

	/**
	 * Set the staging checkList business entity
	 * 
	 * @param value is of type ICheckList
	 */
	public void setStagingCheckList(ICheckList value);

	/**
	 * Set the notification indicator
	 * @param aSendNotificationInd of boolean type
	 */
	public void setSendNotificationInd(boolean aSendNotificationInd);

	/**
	 * Set the notification indicator
	 * @param aSendItemNotificationInd of boolean type
	 */
	public void setSendItemNotificationInd(boolean aSendItemNotificationInd);

	/*
	 * Set the send ready delete notification indicator
	 * 
	 * @param aSendReadyDeleteInd of boolean type
	 */
	public void setSendReadyDeleteInd(boolean aSendReadyDeleteInd);
	
	public String getFlagSchedulers();
	public void setFlagSchedulers(String flagSchedulers) ;
}