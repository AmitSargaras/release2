package com.integrosys.cms.app.custexposure.bus;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.OBLimit;

/**
 * Contains the exposure limit by bank entity
 * @author skchai
 */
public class OBLimitExposureByBankEntity extends OBLimit implements ILimitExposureByBankEntity {

	private static final long serialVersionUID = 1L;
	private Amount exposureAmount = null;
	
	private String exposureCurrency = null;
	private String disbursement = null;
	
	private String bankEntity = null;
	private ILimit limit = null;
	private String productDescription = null;

	/**
	 * @return the productDescription
	 */
	public String getProductDescription() {
		return productDescription;
	}

	/**
	 * @param productDescription the productDescription to set
	 */
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	/**
	 * @return the bankEntity
	 */
	public String getBankEntity() {
		return bankEntity;
	}

	/**
	 * @param bankEntity the bankEntity to set
	 */
	public void setBankEntity(String bankEntity) {
		this.bankEntity = bankEntity;
	}

	public Amount getExposureAmount() {
		return exposureAmount;
	}

	public void setExposureAmount(Amount exposureAmount) {
		this.exposureAmount = exposureAmount;
	}

	public String getExposureCurrency() {
		return exposureCurrency;
	}

	public void setExposureCurrency(String exposureCurrency) {
		this.exposureCurrency = exposureCurrency;
	}

	public String getDisbursement() {
		return disbursement;
	}

	public void setDisbursement(String disbursement) {
		this.disbursement = disbursement;
	}

	/**
	 * @return the limit
	 */
	public ILimit getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(ILimit limit) {
		this.limit = limit;
	}

    /**
	  * Return a String representation of the object
	  *
	  * @return String
	  */
    public String toString() {
        return AccessorUtil.printMethodValue(this);
    }
}
