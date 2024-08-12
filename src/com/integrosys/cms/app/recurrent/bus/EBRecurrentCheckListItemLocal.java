/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBRecurrentCheckListItemLocal.java,v 1.6 2005/09/09 07:56:44 wltan Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

import javax.ejb.EJBLocalObject;
import java.util.List;

/**
 * This is the local interface to the EBRecurrentCheckListItem entity bean.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.6 $
 * @since $Date: 2005/09/09 07:56:44 $ Tag: $Name: $
 */
public interface EBRecurrentCheckListItemLocal extends EJBLocalObject {
	/**
	 * Return the checklist item ID of the checklist item
	 * @return long - the checklist item ID
	 */
	public long getCheckListItemID();

	/**
	 * Return the checklist item reference of the checklist item
	 * @return long - the checklist item reference
	 */
	public long getCheckListItemRef();

	/**
	 * Return an object representation of the recurrent checklist item
	 * information.
	 * @return IRecurrentCheckListItem
	 */
	public IRecurrentCheckListItem getValue() throws RecurrentException;

	/**
	 * Persist a checklist item information
	 * 
	 * @param value is of IRecurrentCheckListItem type
	 */
	public void setValue(IRecurrentCheckListItem value) throws RecurrentException;

	/**
	 * Set the item as deleted
	 * @param anIsDeletedInd of boolean type
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd);

	/**
	 * Set the item's one-off indicator.
	 * @param isOneOffInd of boolean type
	 */
	public void setIsOneOffInd(boolean isOneOffInd);

	/**
	 * Get the item as deleted
	 * @return boolean - the delete indicator
	 */
	public boolean getIsDeletedInd();

	/**
	 * Get the item's one-off indicator.
	 * @return boolean - the one-off indicator
	 */
	public boolean getIsOneOffInd();

	public void createDependents(IRecurrentCheckListItem anIRecurrentCheckListItem) throws RecurrentException;

	public List createSubItems(List aCreationList) throws RecurrentException;

}
