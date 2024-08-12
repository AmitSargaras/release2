/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBItemLocal.java,v 1.1 2003/06/24 11:36:00 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.documentlocation.bus.IDocumentAppTypeItem;

import javax.ejb.EJBLocalObject;

/**
 * This is the local interface to the EBItem entity bean.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/24 11:36:00 $ Tag: $Name: $
 */
public interface EBDocAppItemLocal extends EJBLocalObject {
	/**
	 * Return an object representation of the item information.
	 * 
	 * @return IItem
	 * 
	 */
    
	public Long getDocumentId();
	
	public String getAppType();
	
    
	public IDocumentAppTypeItem getValue() throws CheckListTemplateException;

	/**
	 * Persist a item information
	 * 
	 * @param value is of type IItem
	 */
	public void setValue(IDocumentAppTypeItem value);
}