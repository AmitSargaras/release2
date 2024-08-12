package com.integrosys.cms.app.collateral.bus.type.asset.subtype.vessel;

import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.EBAssetChargeDetailBean;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public abstract class EBVesselBean extends EBAssetChargeDetailBean {
	/**
	 * Get if it is vessel charter contract
	 * 
	 * @return boolean
	 */
	public boolean getVesselCharterContract() {
		String isVesselCharterContract = getEBVesselCharterContract();
		if ((isVesselCharterContract != null) && isVesselCharterContract.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set if it is vessel charter contract
	 * 
	 * @param vesselCharterContract of type boolean
	 */
	public void setVesselCharterContract(boolean vesselCharterContract) {
		if (vesselCharterContract) {
			setEBVesselCharterContract(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBVesselCharterContract(ICMSConstant.FALSE_VALUE);
		}
	}

	public Amount getTradeInDeposit() {
		if (getEBTradeInDeposit() != null) {
			return new Amount(getEBTradeInDeposit(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	public void setTradeInDeposit(Amount tradeInDeposit) {
		setEBTradeInDeposit((tradeInDeposit == null) ? null : tradeInDeposit.getAmountAsBigDecimal());
	}

	public Amount getTradeInValue() {
		if (getEBTradeInDeposit() != null) {
			return new Amount(getEBTradeInValue(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	public void setTradeInValue(Amount tradeInValue) {
		setEBTradeInValue((tradeInValue == null) ? null : tradeInValue.getAmountAsBigDecimal());
	}

	public abstract String getEBVesselCharterContract();

	public abstract void setEBVesselCharterContract(String eBVesselCharterContract);

	public abstract String getVesselName();

	public abstract BigDecimal getEBTradeInDeposit();

	public abstract void setEBTradeInDeposit(BigDecimal eBTradeInDeposit);

	public abstract BigDecimal getEBTradeInValue();

	public abstract void setEBTradeInValue(BigDecimal eBTradeInValue);

	public abstract String getRegCountry();

	public abstract String getVesselState();

	public abstract Date getVesselExptOccupDate();

	public abstract String getVesselExptOccup();

	public abstract String getVesselOccupType();

	public abstract int getVesselBuildYear();

	public abstract String getVesselPurchaseCurrency();

	public abstract String getVesselBuilder();

	public abstract String getVesselMainReg();

	public abstract String getVesselLength();

	public abstract String getVesselWidth();

	public abstract String getVesselDepth();

	public abstract String getVesselDeckLoading();

	public abstract String getVesselDeckWeight();

	public abstract String getVesselSideBoard();

	public abstract String getVesselBOW();

	public abstract String getVesselDECK();

	public abstract String getVesselDeckThickness();

	public abstract String getVesselBottom();

	public abstract String getVesselWinchDrive();

	public abstract String getVesselBHP();

	public abstract String getVesselSpeed();

	public abstract String getVesselAnchor();

	public abstract String getVesselAnchorDrive();

	public abstract String getVesselClassSociety();

	public abstract String getVesselConstructCountry();

	public abstract String getVesselConstructPlace();

	public abstract String getVesselUse();

	public abstract String getVesselChartererName();

	public abstract int getVesselCharterPeriod();

	public abstract String getVesselCharterPeriodUnit();

	public abstract Double getVesselCharterAmt();

	public abstract String getVesselCharterCurrency();

	public abstract String getVesselCharterRateUnit();

	public abstract String getVesselCharterRateUnitOTH();

	public abstract String getVesselCharterRemarks();

	public abstract void setVesselName(String vesseName);

	public abstract void setRegCountry(String regCountry);

	public abstract void setVesselState(String vesselState);

	public abstract void setVesselExptOccupDate(Date vesselExptOccupDate);

	public abstract void setVesselExptOccup(String vesselExptOccup);

	public abstract void setVesselOccupType(String vesselOccupType);

	public abstract void setVesselBuildYear(int vesselBuildYear);

	public abstract void setVesselPurchaseCurrency(String vesselPurchaseCurrency);

	public abstract void setVesselBuilder(String vesselBuilder);

	public abstract void setVesselMainReg(String vesselMainReg);

	public abstract void setVesselLength(String vesselLength);

	public abstract void setVesselWidth(String vesselWidth);

	public abstract void setVesselDepth(String vesselDepth);

	public abstract void setVesselDeckLoading(String vesselDeckLoading);

	public abstract void setVesselDeckWeight(String vesselDeckWeight);

	public abstract void setVesselSideBoard(String vesselSideBoard);

	public abstract void setVesselBOW(String vesselBOW);

	public abstract void setVesselDECK(String vesselDECK);

	public abstract void setVesselDeckThickness(String vesselDeckThickness);

	public abstract void setVesselBottom(String vesselBottom);

	public abstract void setVesselWinchDrive(String vesselWinchDrive);

	public abstract void setVesselBHP(String vesselBHP);

	public abstract void setVesselSpeed(String vesselSpeed);

	public abstract void setVesselAnchor(String vesselAnchor);

	public abstract void setVesselAnchorDrive(String vesselAnchorDrive);

	public abstract void setVesselClassSociety(String vesselClassSociety);

	public abstract void setVesselConstructCountry(String vesselConstructCountry);

	public abstract void setVesselConstructPlace(String vesselConstructPlace);

	public abstract void setVesselUse(String vesselUse);

	public abstract void setVesselChartererName(String vesselChartererName);

	public abstract void setVesselCharterPeriod(int vesselCharterPeriod);

	public abstract void setVesselCharterPeriodUnit(String vesselCharterPeriodUnit);

	public abstract void setVesselCharterAmt(Double vesselCharterAmt);

	public abstract void setVesselCharterCurrency(String vesselCharterCurrency);

	public abstract void setVesselCharterRateUnit(String vesselCharterRateUnit);

	public abstract void setVesselCharterRateUnitOTH(String vesselCharterRateUnitOTH);

	public abstract void setVesselCharterRemarks(String vesselCharterRemarks);

}
