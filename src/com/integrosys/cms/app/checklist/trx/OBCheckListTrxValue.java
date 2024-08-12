/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/OBCheckListTrxValue.java,v 1.5 2005/10/13 03:36:25 hshii Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This class provides the implementation for ICheckListTrxValue
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.5 $
 * @since $Date: 2005/10/13 03:36:25 $ Tag: $Name: $
 */
public class OBCheckListTrxValue extends OBCMSTrxValue implements ICheckListTrxValue {
	private ICheckList checkList = null;

	private ICheckList stagingCheckList = null;

	private boolean sendNotificationInd = false;

	private boolean sendItemNotificationInd = false;

	private boolean sendReadyDeleteInd = false;
	
	private String flagSchedulers = "";

	/**
	 * Default Constructor
	 */
	public OBCheckListTrxValue() {
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSValue - ICMSTrxValue
	 */
	public OBCheckListTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Get the checklist busines entity
	 * 
	 * @return ICheckList
	 */
	
	public ICheckList getCheckList() {
		return this.checkList;
	}

	public String getFlagSchedulers() {
		return flagSchedulers;
	}

	public void setFlagSchedulers(String flagSchedulers) {
		this.flagSchedulers = flagSchedulers;
	}

	/**
	 * Get the staging checkList business entity
	 * 
	 * @return ICheckList
	 */
	
	public ICheckList getStagingCheckList() {
		return this.stagingCheckList;
	}

	/**
	 * Get the send notification indicator
	 * @return boolean - true if need to send notification and false otherwise
	 */
	public boolean getSendNotificationInd() {
		return this.sendNotificationInd;
	}

	/**
	 * Get the send item notification indicator
	 * @return boolean - true if need to send notification and false otherwise
	 */
	public boolean getSendItemNotificationInd() {
		return this.sendItemNotificationInd;
	}

	/**
	 * Get the send ready delete notification indicator
	 * @return boolean - true if needs to send ready delete notification and
	 *         false otherwise
	 */
	public boolean getSendReadyDeleteInd() {
		return this.sendReadyDeleteInd;
	}

	/**
	 * Set the checkList busines entity
	 * 
	 * @param anICheckList is of type ICheckList
	 */
	public void setCheckList(ICheckList anICheckList) {
		this.checkList = anICheckList;
	}

	/**
	 * Set the staging checkList business entity
	 * 
	 * @param anICheckList is of type ICheckList
	 */
	public void setStagingCheckList(ICheckList anICheckList) {
		this.stagingCheckList = anICheckList;
	}

	/**
	 * Set the send notification indicator
	 * @param aSendNotificationInd of boolean type
	 */
	public void setSendNotificationInd(boolean aSendNotificationInd) {
		this.sendNotificationInd = aSendNotificationInd;
	}

	/**
	 * Set the send item notification indicator
	 * @param aSendItemNotificationInd of boolean type
	 */
	public void setSendItemNotificationInd(boolean aSendItemNotificationInd) {
		this.sendItemNotificationInd = aSendItemNotificationInd;
	}

	/*
	 * Set the send ready delete notification indicator
	 * 
	 * @param aSendReadyDeleteInd of boolean type
	 */
	public void setSendReadyDeleteInd(boolean aSendReadyDeleteInd) {
		this.sendReadyDeleteInd = aSendReadyDeleteInd;
	}
}