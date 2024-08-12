/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/OBDeferralRequest.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

import java.util.Arrays;
import java.util.Collection;

/**
 * This class provides the implementation for IDeferralRequest
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */
public class OBDeferralRequest extends OBRequest implements IDeferralRequest {
	/**
	 * Set the list of deferral items
	 * @return IDeferralRequestItem[] - the list of deferral items
	 */
	public IDeferralRequestItem[] getDeferralRequestItemList() {
		IRequestItem[] itemList = getRequestItemList();
		Collection itemCol = Arrays.asList(itemList);
		return (IDeferralRequestItem[]) itemCol.toArray(new IDeferralRequestItem[0]);
	}
}
