package com.integrosys.cms.app.collateral.bus.type.asset.subtype;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class OBTradeInInfo implements ITradeInInfo {

	private static final long serialVersionUID = 4635147021728690644L;

	private Long id;

	private long collateralId;

	private long refId = ICMSConstant.LONG_INVALID_VALUE;

	private Long versionTime;

	private String make;

	private String model;

	private Integer yearOfManufacture;

	private String registrationNo;

	private Amount tradeInValue;

	private Amount tradeInDeposit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getCollateralId() {
		return collateralId;
	}

	public void setCollateralId(long collateralId) {
		this.collateralId = collateralId;
	}

	public long getRefId() {
		return refId;
	}

	public void setRefId(long refId) {
		this.refId = refId;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getYearOfManufacture() {
		return yearOfManufacture;
	}

	public void setYearOfManufacture(Integer yearOfManufacture) {
		this.yearOfManufacture = yearOfManufacture;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public Amount getTradeInValue() {
		return tradeInValue;
	}

	public void setTradeInValue(Amount tradeInValue) {
		this.tradeInValue = tradeInValue;
	}

	public Amount getTradeInDeposit() {
		return tradeInDeposit;
	}

	public void setTradeInDeposit(Amount tradeInDeposit) {
		this.tradeInDeposit = tradeInDeposit;
	}

	public Long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(Long versionTime) {
		this.versionTime = versionTime;
	}

}
