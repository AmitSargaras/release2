/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBConvenantLocal.java,v 1.3 2005/09/09 07:56:44 wltan Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

import javax.ejb.EJBLocalObject;
import java.util.List;

/**
 * This is the local interface to the EBConvenant entity bean.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/09/09 07:56:44 $ Tag: $Name: $
 */
public interface EBConvenantLocal extends EJBLocalObject {
	/**
	 * Return the convenant ID
	 * @return long - the convenant ID
	 */
	public long getConvenantID();

	/**
	 * Return the convenant reference
	 * @return long - the convenant reference
	 */
	public long getConvenantRef();

	/**
	 * Return an object representation of the convenant
	 * @return IConvenant
	 */
	public IConvenant getValue() throws RecurrentException;;

	/**
	 * Persist a convenant info
	 * @param value is of IConvenant type
	 */
	public void setValue(IConvenant value) throws RecurrentException;;

	/**
	 * Set the item as deleted
	 * @param anIsDeletedInd of boolean type
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd);

	/**
	 * Get the item as deleted
	 * @return boolean - the delete indicator
	 */
	public boolean getIsDeletedInd();

	public void createDependents(IConvenant anIConvenant) throws RecurrentException;

	public List createSubItems(List aCreationList) throws RecurrentException;

}
