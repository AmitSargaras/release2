/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/schargevehicle/ISpecificChargeVehicle.java,v 1.5 2005/08/12 10:34:32 lyng Exp $: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/schargeothers/ISpecificChargeOthers.java,v 1.1 2003/06/13 07:07:36 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargevehicle;

import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.IChargeCommon;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.ITradeInInfo;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: May 24, 2007 Time: 10:59:41 AM
 * To change this template use File | Settings | File Templates.
 */

public interface ISpecificChargeVehicle extends IChargeCommon {

	/**
	 * Get coverage type.
	 * 
	 * @return String
	 */
	public String getCoverageType();

	/**
	 * Set coverage type.
	 * 
	 * @param coverageType of type String
	 */
	public void setCoverageType(String coverageType);

	public String getChassisNumber();

	public void setChassisNumber(String chassisNumber);

	public String getIindicator();

	public void setIindicator(String iindicator);

	public String getCollateralfee();

	public void setCollateralfee(String collateralfee);

	public Amount getDptradein();

	public void setDptradein(Amount dptradein);

	public Amount getDpcash();

	public void setDpcash(Amount dpcash);

	public Amount getFcharges();

	public void setFcharges(Amount fcharges);

	public Amount getPlist();

	public void setPlist(Amount plist);

	public String getHeavyvehicle();

	public void setHeavyvehicle(String heavyvehicle);

	public Amount getCoe();

	public void setCoe(Amount coe);

	public String getEngineNo();

	public void setEngineNo(String engineNo);

	public String getTransType();

	public void setTransType(String transType);

	public String getEnergySource();

	public void setEnergySource(String energySource);

	public String getHorsePower();

	public void setHorsePower(String horsePower);

	public String getVehColor();

	public void setVehColor(String vehColor);

	public Amount getAmtCollectedFromSales();

	public void setAmtCollectedFromSales(Amount amtCollectedFromSales);

	public String getPBTIndicator();

	public void setPBTIndicator(String pbt);

	public Long getPbtPbrPeriodDays();

	public void setPbtPbrPeriodDays(Long pbtPbrPeriodDays);

	public Amount getRoadTaxAmt();

	public String getRoadTaxAmtType();

	public void setRoadTaxAmt(Amount taxRoadAmt);

	public void setRoadTaxAmtType(String roadTaxAmtType);

	public Date getRoadTaxExpiryDate();

	public void setRoadTaxExpiryDate(Date roadTaxExpiryDate);

	public boolean getIsAllowPassive();

	public void setIsAllowPassive(boolean isAllowPassive);

	public String getLogBookNumber();

	public void setLogBookNumber(String logBookNumber);

	public String getEngineCapacity();

	public void setEngineCapacity(String engineCapacity);

	public String getOwnershipClaimNumber();

	public void setOwnershipClaimNumber(String ownershipClaimNumber);

	public String getYardOptions();

	public void setYardOptions(String yardOptions);

	public String getDealerName();

	public void setDealerName(String dealerName);

	public String getEHakMilikNumber();

	public void setEHakMilikNumber(String eHakMilikNumber);

	public String getRlSerialNumber();

	public void setRlSerialNumber(String rlSerialNumber);

	public Amount getTradeinValue();

	public void setTradeinValue(Amount tradeinValue);

	public ITradeInInfo[] getTradeInInfo();

	public void setTradeInInfo(ITradeInInfo[] tradeInInfo);

    public Date getInvoiceDate();

    public void setInvoiceDate(Date invoiceDate);

	public String getInvoiceNo();

	public void setInvoiceNo(String invoiceNo);
	
	
	
	public String getRamId();
	public void setRamId(String ramId);
	public Date getStartDate();
	public void setStartDate(Date startDate);
	
	public BigDecimal getAssetCollateralBookingVal();

	public void setAssetCollateralBookingVal(BigDecimal assetCollateralBookingVal);
	public String getDescriptionAssets() ;

	public void setDescriptionAssets(String descriptionAssets) ;
}
