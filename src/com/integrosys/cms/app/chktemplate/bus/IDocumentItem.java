/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/IDocumentItem.java,v 1.2 2003/07/08 03:11:43 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//ofa
import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.chktemplate.bus.IItem;

/**
 * This interface defines the list of attributes that will be available to a
 * document (global) item
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/08 03:11:43 $ Tag: $Name: $
 */
public interface IDocumentItem extends IItem, IValueObject {

}
