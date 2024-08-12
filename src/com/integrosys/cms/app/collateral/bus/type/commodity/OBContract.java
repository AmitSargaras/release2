/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/OBContract.java,v 1.5 2004/08/30 10:24:27 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.QuantityDifferential;
import com.integrosys.cms.app.commodity.main.bus.profile.ISupplier;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: May 3, 2004 Time: 2:16:06
 * PM To change this template use File | Settings | File Templates.
 */
public class OBContract implements IContract {
	private long contractID;

	private IApprovedCommodityType approvedCommodityType;

	private ISupplier supplier;

	private Date maturityDate;

	private int minShippingFrequency;

	private String minShippingFrequencyUnit;

	private Date lastShipmentDate;

	private String mainContractNumber;

	private Amount contractPrice;

	private Quantity contractedQty;

	private QuantityDifferential qtyDifferential;

	private String remarks;

	private String status;

	private long commonRef;

	public OBContract() {
	}

	public IApprovedCommodityType getApprovedCommodityType() {
		return approvedCommodityType;
	}

	public void setApprovedCommodityType(IApprovedCommodityType approvedCommodityType) {
		this.approvedCommodityType = approvedCommodityType;
	}

	public Quantity getContractedQty() {
		return contractedQty;
	}

	public void setContractedQty(Quantity contractedQty) {
		this.contractedQty = contractedQty;
	}

	public long getContractID() {
		return contractID;
	}

	public void setContractID(long contractID) {
		this.contractID = contractID;
	}

	public Date getLastShipmentDate() {
		return lastShipmentDate;
	}

	public void setLastShipmentDate(Date lastShipmentDate) {
		this.lastShipmentDate = lastShipmentDate;
	}

	public Amount getContractPrice() {
		return contractPrice;
	}

	public void setContractPrice(Amount contractPrice) {
		this.contractPrice = contractPrice;
	}

	public Amount getMainContractAmount() {
		if ((contractPrice == null) || (contractedQty == null) || (contractedQty.getQuantity() == null)) {
			return null;
		}
		return contractPrice.multiply(contractedQty.getQuantity());
	}

	public String getMainContractNumber() {
		return mainContractNumber;
	}

	public void setMainContractNumber(String mainContractNumber) {
		this.mainContractNumber = mainContractNumber;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public int getMinShippingFrequency() {
		return minShippingFrequency;
	}

	public void setMinShippingFrequency(int minShippingFrequency) {
		this.minShippingFrequency = minShippingFrequency;
	}

	public String getMinShippingFrequencyUnit() {
		return minShippingFrequencyUnit;
	}

	public void setMinShippingFrequencyUnit(String minShippingFrequencyUnit) {
		this.minShippingFrequencyUnit = minShippingFrequencyUnit;
	}

	public QuantityDifferential getQtyDifferential() {
		return qtyDifferential;
	}

	public void setQtyDifferential(QuantityDifferential qtyDifferential) {
		this.qtyDifferential = qtyDifferential;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public ISupplier getSupplier() {
		return supplier;
	}

	public void setSupplier(ISupplier supplier) {
		this.supplier = supplier;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getCommonRef() {
		return commonRef;
	}

	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	public boolean equals(Object o) {
		return ((o != null) && this.toString().equals(o.toString())) && (o.getClass().equals(this.getClass()));
	}
}
