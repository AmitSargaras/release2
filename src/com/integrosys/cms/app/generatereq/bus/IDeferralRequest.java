/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/IDeferralRequest.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

/**
 * This interface defines the list of attributes that is required for deferral
 * request generation
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */
public interface IDeferralRequest extends IRequest {
	public IDeferralRequestItem[] getDeferralRequestItemList();
}
