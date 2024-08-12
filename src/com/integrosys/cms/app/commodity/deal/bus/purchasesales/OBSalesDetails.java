/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/purchasesales/OBSalesDetails.java,v 1.5 2004/08/12 07:26:15 wltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.purchasesales;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.main.bus.profile.IBuyer;

/**
 * This class represents deal sales information.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.5 $
 * @since $Date: 2004/08/12 07:26:15 $ Tag: $Name: $
 */
public class OBSalesDetails extends OBCommonDetails implements ISalesDetails {
	private IBuyer buyer;

	private String otherBuyerName;

	/**
	 * Default constructor.
	 */
	public OBSalesDetails() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param iValue is of type ISalesDetails
	 */
	public OBSalesDetails(ISalesDetails iValue) {
		if (iValue != null) {
			AccessorUtil.copyValue(iValue, this);
		}
	}

	public IBuyer getBuyer() {
		return buyer;
	}

	public void setBuyer(IBuyer buyer) {
		this.buyer = buyer;
	}

	public String getOtherBuyerName() {
		return otherBuyerName;
	}

	public void setOtherBuyerName(String otherBuyerName) {
		this.otherBuyerName = otherBuyerName;
	}
}
