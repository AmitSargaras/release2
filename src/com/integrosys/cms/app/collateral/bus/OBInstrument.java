package com.integrosys.cms.app.collateral.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

public class OBInstrument implements IInstrument {
	private long instrumentID = ICMSConstant.LONG_MIN_VALUE;

	private long collateralID = ICMSConstant.LONG_MIN_VALUE;

	private String instrumentCode;

	public long getCollateralID() {
		return collateralID;
	}

	public void setCollateralID(long collateralID) {
		this.collateralID = collateralID;
	}

	public String getInstrumentCode() {
		return instrumentCode;
	}

	public void setInstrumentCode(String instrumentCode) {
		this.instrumentCode = instrumentCode;
	}

	public long getInstrumentID() {
		return instrumentID;
	}

	public void setInstrumentID(long instrumentID) {
		this.instrumentID = instrumentID;
	}
}