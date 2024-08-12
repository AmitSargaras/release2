package com.integrosys.cms.app.bridgingloan.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.bus.IArea;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface IBuildUp extends Serializable {

	public long getBuildUpID();

	public void setBuildUpID(long buildUpID);

	public long getProjectID();

	public void setProjectID(long projectID);

	public String getPropertyType();

	public void setPropertyType(String propertyType);

	public String getUnitID();

	public void setUnitID(String unitID);

	public String getUnitNo();

	public void setUnitNo(String unitNo);

	public String getBlockNo();

	public void setBlockNo(String blockNo);

	public String getTitleNo();

	public void setTitleNo(String titleNo);

	public boolean getIsUnitDischarged();

	public void setIsUnitDischarged(boolean isUnitDischarged);

	public IArea getApproxArea();

	public void setApproxArea(IArea area);

	public IArea getApproxAreaSecondary();

	public void setApproxAreaSecondary(IArea area);

	public Amount getRedemptionAmount();

	public void setRedemptionAmount(Amount redemptionAmount);

	public Amount getSalesAmount();

	public void setSalesAmount(Amount salesAmount);

	public Date getSalesDate();

	public void setSalesDate(Date salesDate);

	public String getPurchaserName();

	public void setPurchaserName(String purchaserName);

	public String getEndFinancier();

	public void setEndFinancier(String endFinancier);

	public String getBuRemarks();

	public void setBuRemarks(String buRemarks);

	public Date getTenancyDate();

	public void setTenancyDate(Date tenancyDate);

	public String getTenantName();

	public void setTenantName(String tenancyName);

	public String getTenancyPeriodUnit();

	public void setTenancyPeriodUnit(String tenancyPeriodUnit);

	public int getTenancyPeriod();

	public void setTenancyPeriod(int tenancyPeriod);

	public Date getTenancyExpiryDate();

	public void setTenancyExpiryDate(Date tenancyExpiryDate);

	public Amount getRentalAmount();

	public void setRentalAmount(Amount rentalAmount);

	public String getPaymentFrequency();

	public void setPaymentFrequency(String paymentFrequency);

	public ISalesProceeds[] getSalesProceedsList();

	public void setSalesProceedsList(ISalesProceeds[] salesProceedsList);

	// ========================
	// Common Methods
	// ========================
	public long getCommonRef();

	public void setCommonRef(long commonRef);

	public boolean getIsDeletedInd();

	public void setIsDeletedInd(boolean isDeleted);

}
