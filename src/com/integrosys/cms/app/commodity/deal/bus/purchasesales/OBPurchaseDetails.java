/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/purchasesales/OBPurchaseDetails.java,v 1.4 2004/08/12 07:26:15 wltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.purchasesales;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.main.bus.profile.ISupplier;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/12 07:26:15 $ Tag: $Name: $
 */
public class OBPurchaseDetails extends OBCommonDetails implements IPurchaseDetails {

	private ISupplier supplier;

	private String otherSupplierName;

	public OBPurchaseDetails() {
		super();
	}

	public OBPurchaseDetails(IPurchaseDetails iValue) {
		if (iValue != null) {
			AccessorUtil.copyValue(iValue, this);
		}
	}

	public ISupplier getSupplier() {
		return supplier;
	}

	public void setSupplier(ISupplier supplier) {
		this.supplier = supplier;
	}

	public String getOtherSupplierName() {
		return otherSupplierName;
	}

	public void setOtherSupplierName(String otherSupplierName) {
		this.otherSupplierName = otherSupplierName;
	}
}
