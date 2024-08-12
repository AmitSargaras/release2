/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/IDealNo.java,v 1.1 2004/06/22 03:36:41 wltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/06/22 03:36:41 $ Tag: $Name: $
 */
public interface IDealNo {
	public String getNewDealNo() throws Exception;

	String getPrefix();

	String getPostfix();

	String getPadString();

	String getDealType();

	String getCountryCode();

	String getNewSequenceNo() throws Exception;
}
