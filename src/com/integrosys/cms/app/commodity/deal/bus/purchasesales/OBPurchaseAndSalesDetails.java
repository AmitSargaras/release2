/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/purchasesales/OBPurchaseAndSalesDetails.java,v 1.3 2004/06/18 03:02:08 wltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.purchasesales;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/06/18 03:02:08 $ Tag: $Name: $
 */
public class OBPurchaseAndSalesDetails implements IPurchaseAndSalesDetails {

	private long id = ICMSConstant.LONG_INVALID_VALUE;

	private IPurchaseDetails purchaseDetails;

	private ISalesDetails salesDetails;

	private String status;

	private long commonReferenceID = ICMSConstant.LONG_INVALID_VALUE;

	public OBPurchaseAndSalesDetails() {
	}

	public OBPurchaseAndSalesDetails(IPurchaseAndSalesDetails iValue) {
		if (iValue != null) {
			AccessorUtil.copyValue(iValue, this);
		}
	}

	public long getDetailsID() {
		return id;
	}

	public void setDetailsID(long detailsID) {
		this.id = detailsID;
	}

	public IPurchaseDetails getPurchaseDetails() {
		return purchaseDetails;
	}

	public void setPurchaseDetails(IPurchaseDetails purchaseDetails) {
		this.purchaseDetails = purchaseDetails;
	}

	public ISalesDetails getSalesDetails() {
		return salesDetails;
	}

	public void setSalesDetails(ISalesDetails salesDetails) {
		this.salesDetails = salesDetails;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getCommonReferenceID() {
		return commonReferenceID;
	}

	public void setCommonReferenceID(long commonReferenceID) {
		this.commonReferenceID = commonReferenceID;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof OBPurchaseAndSalesDetails)) {
			return false;
		}

		final OBPurchaseAndSalesDetails obPurchaseAndSalesDetails = (OBPurchaseAndSalesDetails) o;

		if (commonReferenceID != obPurchaseAndSalesDetails.commonReferenceID) {
			return false;
		}
		if (id != obPurchaseAndSalesDetails.id) {
			return false;
		}
		if (purchaseDetails != null ? !purchaseDetails.equals(obPurchaseAndSalesDetails.purchaseDetails)
				: obPurchaseAndSalesDetails.purchaseDetails != null) {
			return false;
		}
		if (salesDetails != null ? !salesDetails.equals(obPurchaseAndSalesDetails.salesDetails)
				: obPurchaseAndSalesDetails.salesDetails != null) {
			return false;
		}
		if (status != null ? !status.equals(obPurchaseAndSalesDetails.status)
				: obPurchaseAndSalesDetails.status != null) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result;
		result = (int) (id ^ (id >>> 32));
		result = 29 * result + (purchaseDetails != null ? purchaseDetails.hashCode() : 0);
		result = 29 * result + (salesDetails != null ? salesDetails.hashCode() : 0);
		result = 29 * result + (status != null ? status.hashCode() : 0);
		result = 29 * result + (int) (commonReferenceID ^ (commonReferenceID >>> 32));
		return result;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

}
