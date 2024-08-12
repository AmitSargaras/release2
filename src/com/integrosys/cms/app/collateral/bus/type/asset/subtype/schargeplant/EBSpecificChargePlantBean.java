package com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeplant;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.CreateException;

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

public abstract class EBSpecificChargePlantBean extends EBAssetChargeDetailBean {

	public abstract BigDecimal getEBQuantity();

	public abstract void setEBQuantity(BigDecimal eBQuantity);

	public Amount getQuantity() {
		if (getEBQuantity() != null) {
			return new Amount(getEBQuantity(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	public void setQuantity(Amount quantity) {
		setEBQuantity((quantity == null) ? null : quantity.getAmountAsBigDecimal());
	}

	public Amount getDpcash() {
		if (getEBdpcash() != null) {
			return new Amount(getEBdpcash(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	public void setDpcash(Amount dpcash) {
		setEBdpcash((dpcash == null) ? null : dpcash.getAmountAsBigDecimal());
	}

	public Amount getDptradein() {
		if (getEBdptradein() != null) {
			return new Amount(getEBdptradein(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	public void setDptradein(Amount dptradein) {
		setEBdptradein((dptradein == null) ? null : dptradein.getAmountAsBigDecimal());
	}

	public Amount getTradeinValue() {
		if (getEBtradeinValue() != null) {
			return new Amount(getEBtradeinValue(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	public void setTradeinValue(Amount tradeinValue) {
		setEBtradeinValue((tradeinValue == null) ? null : tradeinValue.getAmountAsBigDecimal());
	}

	public Amount getPlist() {
		if (getEBplist() != null) {
			return new Amount(getEBplist(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	public void setPlist(Amount plist) {
		setEBplist((plist == null) ? null : plist.getAmountAsBigDecimal());
	}

	public abstract BigDecimal getEBplist();

	public abstract void setEBplist(BigDecimal eBplist);

	public abstract String getPurpose();

	public abstract void setPurpose(String purpose);

	public abstract String getEquipmf();

	public abstract void setEquipmf(String equipmf);

	public abstract String getEquipriskgrading();

	public abstract void setEquipriskgrading(String equipriskgrading);

	public abstract String getEquipcode();

	public abstract void setEquipcode(String equipcode);

	public abstract String getInspectionStatusCategoryCode();

	public abstract void setInspectionStatusCategoryCode(String inspectionStatusCategoryCode);

	public abstract String getInspectionStatusEntryCode();

	public abstract void setInspectionStatusEntryCode(String inspectionStatusEntryCode);

	public abstract String getSerialNumber();

	public abstract void setSerialNumber(String serialNumber);

	public abstract String getInvoiceNumber();

	public abstract void setInvoiceNumber(String invoiceNumber);

	public abstract BigDecimal getEBdptradein();

	public abstract void setEBdptradein(BigDecimal eBdptradein);

	public abstract BigDecimal getEBdpcash();

	public abstract void setEBdpcash(BigDecimal eBdpcash);

	public abstract BigDecimal getEBtradeinValue();

	public abstract void setEBtradeinValue(BigDecimal eBtradeinValue);

	public abstract Date getInvoiceDate();

	public abstract void setInvoiceDate(Date invoiceDate);
	
	public abstract String getRamId();

	public abstract void setRamId(String ramId);

	public void setValue(ICollateral collateral) {
		super.setValue(collateral);
		setTradeInInfo((ISpecificChargePlant) collateral);
	}

	public void ejbPostCreate(ICollateral collateral) throws CreateException {
		super.ejbPostCreate(collateral);
		setTradeInInfo((ISpecificChargePlant) collateral);
	}

	public ITradeInInfo[] getTradeInInfo() {
		TradeInInfoDAO dao = (TradeInInfoDAO) BeanHouse.get("tradeInInfoDAO");
		return dao.getTradeInInfoByCollId(getTradeInEntityName(), getCollateralID());
	}

	public void setTradeInInfo(ITradeInInfo[] trandeInArray) {

	}

	public void setTradeInInfo(ISpecificChargePlant plant) {

		TradeInInfoDAO dao = (TradeInInfoDAO) BeanHouse.get("tradeInInfoDAO");

		ITradeInInfo[] actual = dao.getTradeInInfoByCollId(getTradeInEntityName(), getCollateralID());
		Map actualMap = new HashMap();
		if (actual != null && actual.length > 0) {
			for (int index = 0; index < actual.length; index++) {
				actualMap.put(actual[index].getId(), actual[index]);
			}
		}
		ITradeInInfo[] stage = plant.getTradeInInfo();
		if (stage != null && stage.length > 0) {
			for (int index = 0; index < stage.length; index++) {
				if (stage[index].getRefId() == 0 || stage[index].getRefId() == ICMSConstant.LONG_INVALID_VALUE) {
					OBTradeInInfo info = new OBTradeInInfo();
					AccessorUtil.copyValue(stage[index], info, TRADE_IN_METHOD);
					info.setCollateralId(plant.getCollateralID());
					dao.saveOrUpdateTradeInInfo(getTradeInEntityName(), info);
					// update ref_id to actual table
					info.setRefId(info.getId().longValue());
					dao.saveOrUpdateTradeInInfo(getTradeInEntityName(), info);
					stage[index].setRefId(info.getId().longValue());
					dao.saveOrUpdateTradeInInfo(getStageTradeInEntityName(), stage[index]);
				}
				else {
					OBTradeInInfo info = (OBTradeInInfo) actualMap.get(new Long(stage[index].getRefId()));

					DefaultLogger.debug(this, "Coll ID: " + info.getCollateralId());
					AccessorUtil.copyValue(stage[index], info, TRADE_IN_METHOD);
					if (info.getCollateralId() != plant.getCollateralID()) {
						info.setCollateralId(plant.getCollateralID());
					}
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
