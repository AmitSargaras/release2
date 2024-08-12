package com.integrosys.cms.app.commodity.deal.bus.purchasesales;

import com.integrosys.cms.app.commodity.main.bus.profile.IBuyer;

/**
 * This interface defines sales details.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/12 07:26:15 $ Tag: $Name: $
 */
public interface ISalesDetails extends ICommonDetails {
	public IBuyer getBuyer();

	public void setBuyer(IBuyer buyer);

	public String getOtherBuyerName();

	public void setOtherBuyerName(String otherBuyerName);
}
