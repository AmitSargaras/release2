package com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargevehicle;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.EBAssetChargeDetailBean;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.ITradeInInfo;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.OBTradeInInfo;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.TradeInInfoDAO;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public abstract class EBSpecificChargeVehicleBean extends EBAssetChargeDetailBean {
	private static final long serialVersionUID = -4043677042113949978L;

	public abstract BigDecimal getEBdptradein();

	public abstract void setEBdptradein(BigDecimal eBdptradein);

	public Amount getDptradein() {
		if (getEBdptradein() != null) {
			return new Amount(getEBdptradein(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	public void setDptradein(Amount dptradein) {
		setEBdptradein((dptradein == null) ? null : dptradein.getAmountAsBigDecimal());
	}

	public abstract BigDecimal getEBdpcash();

	public abstract void setEBdpcash(BigDecimal eBdpcash);

	public Amount getDpcash() {
		if (getEBdpcash() != null) {
			return new Amount(getEBdpcash(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	public void setDpcash(Amount dpcash) {
		setEBdpcash((dpcash == null) ? null : dpcash.getAmountAsBigDecimal());
	}

	public abstract BigDecimal getEBfcharges();

	public abstract void setEBfcharges(BigDecimal eBfcharges);

	public Amount getFcharges() {
		if (getEBfcharges() != null) {
			return new Amount(getEBfcharges(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	public void setFcharges(Amount fcharges) {
		setEBfcharges((fcharges == null) ? null : fcharges.getAmountAsBigDecimal());
	}

	public abstract BigDecimal getEBplist();

	public abstract void setEBplist(BigDecimal eBplist);

	public Amount getPlist() {
		if (getEBplist() != null) {
			return new Amount(getEBplist(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	public void setPlist(Amount plist) {
		setEBplist((plist == null) ? null : plist.getAmountAsBigDecimal());
	}

	public abstract BigDecimal getEBCoe();

	public abstract void setEBCoe(BigDecimal eBCoe);

	public Amount getCoe() {
		if (getEBCoe() != null) {
			return new Amount(getEBCoe(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	public void setCoe(Amount coe) {
		setEBCoe((coe == null) ? null : coe.getAmountAsBigDecimal());
	}

	public abstract BigDecimal getEBAmtCollectedFromSales();

	public abstract void setEBAmtCollectedFromSales(BigDecimal eBAmtCollectedFromSales);

	public Amount getAmtCollectedFromSales() {
		if (getEBAmtCollectedFromSales() != null) {
			return new Amount(getEBAmtCollectedFromSales(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	public void setAmtCollectedFromSales(Amount amtCollectedFromSales) {
		setEBAmtCollectedFromSales((amtCollectedFromSales == null) ? null : amtCollectedFromSales
				.getAmountAsBigDecimal());
	}

	public abstract BigDecimal getEBRoadTaxAmt();

	public abstract void setEBRoadTaxAmt(BigDecimal roadTaxAmt);

	public Amount getRoadTaxAmt() {
		if (getEBRoadTaxAmt() != null) {
			return new Amount(getEBRoadTaxAmt(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	public void setRoadTaxAmt(Amount roadTaxAmt) {
		setEBRoadTaxAmt((roadTaxAmt == null) ? null : roadTaxAmt.getAmountAsBigDecimal());
	}

	public boolean getIsAllowPassive() {
		if ((getIsAllowPassiveStr() != null) && getIsAllowPassiveStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	public void setIsAllowPassive(boolean isAllowPassive) {
		setIsAllowPassiveStr((isAllowPassive) ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
	}

	public Amount getTradeinValue() {
		if (getEBTradeinValue() != null) {
			return new Amount(getEBTradeinValue(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	public void setTradeinValue(Amount tradeinValue) {
		setEBTradeinValue((tradeinValue == null) ? null : tradeinValue.getAmountAsBigDecimal());
	}

	/**
	 * Get coverage type.
	 * 
	 * @return String
	 */
	public abstract String getCoverageType();

	/**
	 * Set coverage type.
	 * 
	 * @param coverageType of type String
	 */
	public abstract void setCoverageType(String coverageType);

	public abstract String getChassisNumber();

	public abstract void setChassisNumber(String chassisNumber);

	public abstract String getIindicator();

	public abstract void setIindicator(String iindicator);

	public abstract String getCollateralfee();

	public abstract void setCollateralfee(String collateralfee);

	public abstract String getHeavyvehicle();

	public abstract void setHeavyvehicle(String heavyvehicle);

	public abstract String getEngineNo();

	public abstract void setEngineNo(String engineNo);

	public abstract String getTransType();

	public abstract void setTransType(String transType);

	public abstract String getEnergySource();

	public abstract void setEnergySource(String energySource);

	public abstract String getHorsePower();

	public abstract void setHorsePower(String horsePower);

	public abstract String getVehColor();

	public abstract void setVehColor(String vehColor);

	public abstract String getRoadTaxAmtType();

	public abstract void setRoadTaxAmtType(String roadTaxAmtType);

	public abstract Date getRoadTaxExpiryDate();

	public abstract void setRoadTaxExpiryDate(Date roadExpiryDate);

	public abstract String getIsAllowPassiveStr();

	public abstract void setIsAllowPassiveStr(String isAllowPassive);

	public abstract String getLogBookNumber();

	public abstract void setLogBookNumber(String logBookNumber);

	public abstract String getEngineCapacity();

	public abstract void setEngineCapacity(String engineCapacity);

	public abstract String getOwnershipClaimNumber();

	public abstract void setOwnershipClaimNumber(String ownershipClaimNumber);

	public abstract String getYardOptions();

	public abstract void setYardOptions(String yardOptions);

	public abstract String getDealerName();

	public abstract void setDealerName(String dealerName);

	public abstract String getEHakMilikNumber();

	public abstract void setEHakMilikNumber(String eHakMilikNumber);

	public abstract String getPBTIndicator();

	public abstract void setPBTIndicator(String pbtIndicator);

	public abstract BigDecimal getEBTradeinValue();

	public abstract void setEBTradeinValue(BigDecimal tradeinValue);

	public abstract Long getPbtPbrPeriodDays();

	public abstract void setPbtPbrPeriodDays(Long pbtPbrPeriodDays);

	public abstract Date getInvoiceDate();

	public abstract void setInvoiceDate(Date invoiceDate);
	
	public abstract String getInvoiceNo();

	public abstract void setInvoiceNo(String invoiceNo);	
	
	public abstract String getRamId();
	
	public abstract void setRamId(String ramId);
	
	public abstract Date getStartDate();
	
	public abstract void setStartDate(Date startDate);
	
	
	public abstract BigDecimal getAssetCollateralBookingVal() ;

	public abstract void setAssetCollateralBookingVal(BigDecimal assetCollateralBookingVal);
	public abstract String getDescriptionAssets() ;

	public abstract void setDescriptionAssets(String descriptionAssets) ;

	
	public void setValue(ICollateral collateral) {
		super.setValue(collateral);
		setTradeInInfo((ISpecificChargeVehicle) collateral);
	}

	public void ejbPostCreate(ICollateral collateral) throws CreateException {
		try {
			super.ejbPostCreate(collateral);
			setTradeInInfo((ISpecificChargeVehicle) collateral);
		}
		catch (Exception e) {
			throw new EJBException("Failed to create Trade-In Info collateral id [" + collateral.getCollateralID()
					+ "]", e);
		}
	}

	public ITradeInInfo[] getTradeInInfo() {
		TradeInInfoDAO dao = (TradeInInfoDAO) BeanHouse.get("tradeInInfoDAO");
		return dao.getTradeInInfoByCollId(getTradeInEntityName(), getCollateralID());
	}

	public void setTradeInInfo(ITradeInInfo[] trandeInArray) {

	}

	public void setTradeInInfo(ISpecificChargeVehicle vehicle) {

		TradeInInfoDAO dao = (TradeInInfoDAO) BeanHouse.get("tradeInInfoDAO");

		ITradeInInfo[] actual = dao.getTradeInInfoByCollId(getTradeInEntityName(), getCollateralID());
		Map actualMap = new HashMap();
		if ((actual != null) && (actual.length > 0)) {
			for (int index = 0; index < actual.length; index++) {
				actualMap.put(actual[index].getId(), actual[index]);
			}
		}
		ITradeInInfo[] stage = vehicle.getTradeInInfo();
		if ((stage != null) && (stage.length > 0)) {
			for (int index = 0; index < stage.length; index++) {
				if ((stage[index].getRefId() == 0) || (stage[index].getRefId() == ICMSConstant.LONG_INVALID_VALUE)) {
					OBTradeInInfo info = new OBTradeInInfo();
					AccessorUtil.copyValue(stage[index], info, TRADE_IN_METHOD);
					info.setCollateralId(vehicle.getCollateralID());
					dao.saveOrUpdateTradeInInfo(getTradeInEntityName(), info);
					// update ref_id to actual table
					info.setRefId(info.getId().longValue());
					dao.saveOrUpdateTradeInInfo(getTradeInEntityName(), info);
					// DefaultLogger.debug(this, "Coll ID: " +
					// info.getCollateralId());
					// DefaultLogger.debug(this, "Create - ID: " +
					// info.getId());
					// update ref_id to stage table
					stage[index].setRefId(info.getId().longValue());
					dao.saveOrUpdateTradeInInfo(getStageTradeInEntityName(), stage[index]);
				}
				else {
					OBTradeInInfo info = (OBTradeInInfo) actualMap.get(new Long(stage[index].getRefId()));

					DefaultLogger.debug(this, "Coll ID: " + info.getCollateralId());
					AccessorUtil.copyValue(stage[index], info, TRADE_IN_METHOD);
					if (info.getCollateralId() != vehicle.getCollateralID()) {
						// DefaultLogger.debug(this, " extra.... ");
						info.setCollateralId(vehicle.getCollateralID());
					}
					// DefaultLogger.debug(this, "Update - ID: " +
					// info.getId());
					dao.saveOrUpdateTradeInInfo(getTradeInEntityName(), info);
				}
			}
		}
	}

	protected final String[] TRADE_IN_METHOD = new String[] { "getId", "setId", "getVersionTime", "setVersionTime",
			"getCollateralID", "setCollateralID" };

	public String getStageTradeInEntityName() {
		return "stageTradeInInfo";
	}

	public String getTradeInEntityName() {
		return "actualTradeInInfo";
	}
}
