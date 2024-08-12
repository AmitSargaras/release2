package com.integrosys.cms.app.collateral.bus;

import java.io.Serializable;

public interface IInstrument extends Serializable {
	public long getCollateralID();

	public void setCollateralID(long collateralID);

	public String getInstrumentCode();

	public void setInstrumentCode(String instrumentCode);

	public long getInstrumentID();

	public void setInstrumentID(long instrumentID);
}