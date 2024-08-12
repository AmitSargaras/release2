/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.vessel;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.IChargeCommon;

/**
 * This interface represents Asset of type Vessel.
 * 
 * @author $Author: Naveen $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2007/02/03 10:34:32 $ Tag: $Name: $
 */
public interface IVessel extends IChargeCommon {
	public String getVesselName();

	public String getRegCountry();

	public String getVesselState();

	public Date getVesselExptOccupDate();

	public String getVesselExptOccup();

	public String getVesselOccupType();

	public int getVesselBuildYear();

	public String getVesselPurchaseCurrency();

	public String getVesselBuilder();

	public String getVesselMainReg();

	public String getVesselLength();

	public String getVesselWidth();

	public String getVesselDepth();

	public String getVesselDeckLoading();

	public String getVesselDeckWeight();

	public String getVesselSideBoard();

	public String getVesselBOW();

	public String getVesselDECK();

	public String getVesselDeckThickness();

	public String getVesselBottom();

	public String getVesselWinchDrive();

	public String getVesselBHP();

	public String getVesselSpeed();

	public String getVesselAnchor();

	public String getVesselAnchorDrive();

	public String getVesselClassSociety();

	public String getVesselConstructCountry();

	public String getVesselConstructPlace();

	public String getVesselUse();

	public boolean getVesselCharterContract();

	public String getVesselChartererName();

	public int getVesselCharterPeriod();

	public String getVesselCharterPeriodUnit();

	public Double getVesselCharterAmt();

	public String getVesselCharterCurrency();

	public String getVesselCharterRateUnit();

	public String getVesselCharterRateUnitOTH();

	public String getVesselCharterRemarks();

	public void setVesselName(String vesseName);

	public Amount getTradeInDeposit();

	public void setTradeInDeposit(Amount tradeInDeposit);

	public Amount getTradeInValue();

	public void setTradeInValue(Amount tradeInValue);

	public void setRegCountry(String regCountry);

	public void setVesselState(String vesselState);

	public void setVesselExptOccupDate(Date vesselExptOccupDate);

	public void setVesselExptOccup(String vesselExptOccup);

	public void setVesselOccupType(String vesselOccupType);

	public void setVesselBuildYear(int vesselBuildYear);

	public void setVesselPurchaseCurrency(String vesselPurchaseCurrency);

	public void setVesselBuilder(String vesselBuilder);

	public void setVesselMainReg(String vesselMainReg);

	public void setVesselLength(String vesselLength);

	public void setVesselWidth(String vesselWidth);

	public void setVesselDepth(String vesselDepth);

	public void setVesselDeckLoading(String vesselDeckLoading);

	public void setVesselDeckWeight(String vesselDeckWeight);

	public void setVesselSideBoard(String vesselSideBoard);

	public void setVesselBOW(String vesselBOW);

	public void setVesselDECK(String vesselDECK);

	public void setVesselDeckThickness(String vesselDeckThickness);

	public void setVesselBottom(String vesselBottom);

	public void setVesselWinchDrive(String vesselWinchDrive);

	public void setVesselBHP(String vesselBHP);

	public void setVesselSpeed(String vesselSpeed);

	public void setVesselAnchor(String vesselAnchor);

	public void setVesselAnchorDrive(String vesselAnchorDrive);

	public void setVesselClassSociety(String vesselClassSociety);

	public void setVesselConstructCountry(String vesselConstructCountry);

	public void setVesselConstructPlace(String vesselConstructPlace);

	public void setVesselUse(String vesselUse);

	public void setVesselCharterContract(boolean vesselCharterContract);

	public void setVesselChartererName(String vesselChartererName);

	public void setVesselCharterPeriod(int vesselCharterPeriod);

	public void setVesselCharterPeriodUnit(String vesselCharterPeriodUnit);

	public void setVesselCharterAmt(Double vesselCharterAmt);

	public void setVesselCharterCurrency(String vesselCharterCurrency);

	public void setVesselCharterRateUnit(String vesselCharterRateUnit);

	public void setVesselCharterRateUnitOTH(String vesselCharterRateUnitOTH);

	public void setVesselCharterRemarks(String vesselCharterRemarks);
}
