/**
 * 
 */
package com.integrosys.cms.app.custexposure.bus;

import java.io.Serializable;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.limit.bus.ILimit;

/**
 * @author skchai
 *
 */
public interface ILimitExposureByBankEntity extends Serializable {

	/**
	 * @return the bankEntity
	 */
	public String getBankEntity();

	/**
	 * @param bankEntity the bankEntity to set
	 */
	public void setBankEntity(String bankEntity);

	public ILimit getLimit();
	
	public void setLimit(ILimit limit);
	
	public String getProductDescription();
	
	public void setProductDescription(String productDesc);

	public Amount getExposureAmount();
	
	public Amount getOutstandingAmount();

	public void setExposureAmount(Amount exposureAmount);

	public String getExposureCurrency();

	public void setExposureCurrency(String exposureCurrency);

	public String getDisbursement();

	public void setDisbursement(String disbursement);
	
	public long getOuterLimitID();
}
