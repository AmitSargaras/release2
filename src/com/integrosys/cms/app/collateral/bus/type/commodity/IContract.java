/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/IContract.java,v 1.6 2004/10/08 03:47:38 wltan Exp $
 */

package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.QuantityDifferential;
import com.integrosys.cms.app.commodity.main.bus.profile.ISupplier;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: May 3, 2004 Time:
 * 10:28:49 AM To change this template use File | Settings | File Templates.
 */
public interface IContract extends java.io.Serializable {
	public long getContractID();

	public IApprovedCommodityType getApprovedCommodityType();

	public ISupplier getSupplier();

	public Date getMaturityDate();

	public void setMaturityDate(Date date);

	public int getMinShippingFrequency();

	public void setMinShippingFrequency(int freq);

	/**
	 * @return ICommodityConstant.FREQ_UNIT_DAYS/WEEKS/MONTHS/YEARS
	 */
	public String getMinShippingFrequencyUnit();

	public void setMinShippingFrequencyUnit(String freqUnit);

	public Date getLastShipmentDate();

	public void setLastShipmentDate(Date date);

	public String getMainContractNumber();

	public void setMainContractNumber(String contractNo);

	public Amount getContractPrice();

	public void setContractPrice(Amount price);

	public Amount getMainContractAmount();

	public Quantity getContractedQty();

	public void setContractedQty(Quantity qty);

	public QuantityDifferential getQtyDifferential();

	public void setQtyDifferential(QuantityDifferential qtyDifferential);

	public String getRemarks();

	public void setRemarks(String remarks);

	public String getStatus();

	public void setStatus(String status);

	public long getCommonRef();

	public void setCommonRef(long commonRef);
}
