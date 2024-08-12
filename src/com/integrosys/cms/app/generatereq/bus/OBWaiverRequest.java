/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/OBWaiverRequest.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

import java.util.Arrays;
import java.util.Collection;

/**
 * This class provides the implementation for IWaiverRequest
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */
public class OBWaiverRequest extends OBRequest implements IWaiverRequest {
	/**
	 * Set the list of waiver items
	 * @return IWaiverRequestItem[] - the list of waiver items
	 */
	public IWaiverRequestItem[] getWaiverRequestItemList() {
		IRequestItem[] itemList = getRequestItemList();
		Collection itemCol = Arrays.asList(itemList);
		return (IWaiverRequestItem[]) itemCol.toArray(new IWaiverRequestItem[0]);
	}

}
