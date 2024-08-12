/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/OBWaiverRequestItem.java,v 1.1 2003/09/11 05:48:56 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class that provides the implementation for IWaiverRequestItem
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:56 $ Tag: $Name: $
 */
public class OBWaiverRequestItem extends OBRequestItem implements IWaiverRequestItem {
	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
