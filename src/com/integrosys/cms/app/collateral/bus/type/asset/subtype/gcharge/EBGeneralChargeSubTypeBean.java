/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBGeneralChargeSubTypeBean.java,v 1.6 2005/08/12 03:32:36 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.math.BigDecimal;

import javax.ejb.EntityBean;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * For EBStockBean
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/08/12 03:32:36 $ Tag: $Name: $
 */
public abstract class EBGeneralChargeSubTypeBean extends EBValuationDetailsBean implements EntityBean,
		IGeneralChargeSubType {

	public abstract Double getEBMargin();

	public abstract void setEBMargin(Double margin);

	public abstract BigDecimal getEBGrossValue();

	public abstract void setEBGrossValue(BigDecimal grossValue);

	public abstract BigDecimal getEBNetValue();

	public abstract void setEBNetValue(BigDecimal netValue);

	public abstract void setStatus(String status);

	public double getMargin() {
		return (getEBMargin() == null) ? ICMSConstant.DOUBLE_INVALID_VALUE : getEBMargin().doubleValue();
	}

	public void setMargin(double margin) {
		// to cater for both -1 and -0.01 invalid values
		setEBMargin((margin < 0) ? null : new Double(margin));
	}

	public Amount getGrossValue() {
		if ((getEBGrossValue() != null) && (getValuationCurrency() != null)) {
			return new Amount(getEBGrossValue(), new CurrencyCode(getValuationCurrency()));
		}
		else {
			return null;
		}
	}

	public void setGrossValue(Amount grossValue) {
		if (grossValue != null) {
			setEBGrossValue(grossValue.getAmountAsBigDecimal());
		}
	}

	public Amount getNetValue() {
		if ((getEBNetValue() != null) && (getValuationCurrency() != null)) {
			return new Amount(getEBNetValue(), new CurrencyCode(getValuationCurrency()));
		}
		else {
			return null;
		}
	}

	public void setNetValue(Amount netValue) {
		if (netValue != null) {
			setEBNetValue(netValue.getAmountAsBigDecimal());
		}
	}
}
