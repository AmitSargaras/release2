package com.integrosys.cms.app.commodity.deal.bus.purchasesales;

import com.integrosys.cms.app.commodity.main.bus.profile.ISupplier;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/12 07:26:15 $ Tag: $Name: $
 */
public interface IPurchaseDetails extends ICommonDetails {
	public ISupplier getSupplier();

	public void setSupplier(ISupplier supplier);

	public String getOtherSupplierName();

	public void setOtherSupplierName(String otherSupplierName);
}
