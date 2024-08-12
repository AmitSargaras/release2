package com.integrosys.cms.host.eai.security.bus.asset;

import java.io.Serializable;
import java.math.BigDecimal;

import com.integrosys.cms.host.eai.StandardCode;

/**
 * <p>
 * Entity represent trade in information of an asset type of collateral.
 * <p>
 * Mainly for Plant & Equipment and Vehicle.
 * @author Chong Jun Yong
 * 
 */
public class TradeInInfo implements Serializable {

	private static final long serialVersionUID = -4794917031697267163L;

	private Long id;

	private Long cmsCollateralId;

	private Long refId;

	private Long versionTime;

	private StandardCode make;

	private StandardCode model;

	private Integer yearOfManufacture;

	private String registrationNo;

	private BigDecimal tradeInValue;

	private BigDecimal tradeInDeposit;

	private String currencyCode;

	public Long getCmsCollateralId() {
		return cmsCollateralId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public Long getId() {
		return id;
	}

	public StandardCode getMake() {
		return make;
	}

	public StandardCode getModel() {
		return model;
	}

	public Long getRefId() {
		return refId;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public BigDecimal getTradeInDeposit() {
		return tradeInDeposit;
	}

	public BigDecimal getTradeInValue() {
		return tradeInValue;
	}

	public Long getVersionTime() {
		return versionTime;
	}

	public Integer getYearOfManufacture() {
		return yearOfManufacture;
	}

	public void setCmsCollateralId(Long collateralId) {
		this.cmsCollateralId = collateralId;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMake(StandardCode make) {
		this.make = make;
	}

	public void setModel(StandardCode model) {
		this.model = model;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public void setTradeInDeposit(BigDecimal tradeInDeposit) {
		this.tradeInDeposit = tradeInDeposit;
	}

	public void setTradeInValue(BigDecimal tradeInValue) {
		this.tradeInValue = tradeInValue;
	}

	public void setVersionTime(Long versionTime) {
		this.versionTime = versionTime;
	}

	public void setYearOfManufacture(Integer yearOfManufacture) {
		this.yearOfManufacture = yearOfManufacture;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("TradeIn Info : ");
		buf.append("CMS Collateral Id [").append(this.cmsCollateralId).append("], ");
		buf.append("Make [").append(this.make).append("], ");
		buf.append("Model [").append(this.model).append("], ");
		buf.append("Year Of Manufacture [").append(this.yearOfManufacture).append("], ");
		buf.append("Registration Number [").append(this.registrationNo).append("], ");
		buf.append("TradeIn Value [").append(this.tradeInValue).append("], ");
		buf.append("TradeIn Deposit [").append(this.tradeInDeposit).append("]");

		return buf.toString();
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((make == null || make.getStandardCodeValue() == null) ? 0 : make.getStandardCodeValue().hashCode());
		result = prime
				* result
				+ ((model == null || model.getStandardCodeValue() == null) ? 0 : model.getStandardCodeValue()
						.hashCode());
		result = prime * result + ((registrationNo == null) ? 0 : registrationNo.hashCode());
		result = prime * result + ((yearOfManufacture == null) ? 0 : yearOfManufacture.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		TradeInInfo other = (TradeInInfo) obj;
		if (make == null) {
			if (other.make != null) {
				return false;
			}
		}
		else if (make.getStandardCodeValue() == null) {
			if (other.make.getStandardCodeValue() != null) {
				return false;
			}
		}
		else if (!make.getStandardCodeValue().equals(other.make.getStandardCodeValue())) {
			return false;
		}

		if (model == null) {
			if (other.model != null) {
				return false;
			}
		}
		else if (model.getStandardCodeValue() == null) {
			if (other.model.getStandardCodeValue() != null) {
				return false;
			}
		}
		else if (!model.getStandardCodeValue().equals(other.model.getStandardCodeValue())) {
			return false;
		}

		if (registrationNo == null) {
			if (other.registrationNo != null) {
				return false;
			}
		}
		else if (!registrationNo.equals(other.registrationNo)) {
			return false;
		}

		if (yearOfManufacture == null) {
			if (other.yearOfManufacture != null) {
				return false;
			}
		}
		else if (!yearOfManufacture.equals(other.yearOfManufacture)) {
			return false;
		}

		return true;
	}

}
