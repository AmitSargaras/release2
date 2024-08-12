package com.integrosys.cms.app.collateral.bus.type.asset.subtype;

import java.io.Serializable;

import com.integrosys.base.businfra.currency.Amount;

public interface ITradeInInfo extends Serializable {

	public Long getId();

	public void setId(Long id);

	public long getCollateralId();

	public void setCollateralId(long collateralId);

	public long getRefId();

	public void setRefId(long refId);

	public String getMake();

	public void setMake(String make);

	public String getModel();

	public void setModel(String model);

	public Integer getYearOfManufacture();

	public void setYearOfManufacture(Integer yearOfManufacture);

	public String getRegistrationNo();

	public void setRegistrationNo(String registrationNo);

	public Amount getTradeInValue();

	public void setTradeInValue(Amount tradeInValue);

	public Amount getTradeInDeposit();

	public void setTradeInDeposit(Amount tradeInDeposit);

	public Long getVersionTime();

	public void setVersionTime(Long versionTime);
}
