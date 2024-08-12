/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/schargeplant/OBSpecificChargePlant.java,v 1.5 2006/01/18 05:33:46 priya Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeplant;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.ITradeInInfo;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.OBChargeCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.Date;

/**
 * This class represents Asset of type Specific Charge - Plant & Equipment.
 * 
 * @author $Author: priya $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/01/18 05:33:46 $ Tag: $Name: $
 */
public class OBSpecificChargePlant extends OBChargeCommon implements ISpecificChargePlant {

	private String purpose;

	private String equipmf;

	private String equipriskgrading;

	private String equipcode;

	private Amount quantity;

	private String serialNumber;

	private String invoiceNumber;

	private Amount dpcash;

	private Amount dptradein;

	private Amount tradeinValue;
/*
	private String inspectionStatusCategoryCode;

	private String inspectionStatusEntryCode;
*/
	private ITradeInInfo[] tradeInInfo;

    private Date invoiceDate;
    
	private Amount plist;
	
	private String ramId;

	private String insuranceStatus;
	private Date originalTargetDate;
	private Date nextDueDate;
	private Date dateDeferred;
	private String creditApprover;
	private Date waivedDate;

	public String getInsuranceStatus() {
		return insuranceStatus;
	}

	public void setInsuranceStatus(String insuranceStatus) {
		this.insuranceStatus = insuranceStatus;
	}

	public Date getOriginalTargetDate() {
		return originalTargetDate;
	}

	public void setOriginalTargetDate(Date originalTargetDate) {
		this.originalTargetDate = originalTargetDate;
	}

	public Date getNextDueDate() {
		return nextDueDate;
	}

	public void setNextDueDate(Date nextDueDate) {
		this.nextDueDate = nextDueDate;
	}

	public Date getDateDeferred() {
		return dateDeferred;
	}

	public void setDateDeferred(Date dateDeferred) {
		this.dateDeferred = dateDeferred;
	}

	public String getCreditApprover() {
		return creditApprover;
	}

	public void setCreditApprover(String creditApprover) {
		this.creditApprover = creditApprover;
	}

	public Date getWaivedDate() {
		return waivedDate;
	}

	public void setWaivedDate(Date waivedDate) {
		this.waivedDate = waivedDate;
	}
	
	public String getRamId() {
		return ramId;
	}

	public void setRamId(String ramId) {
		this.ramId = ramId;
	}
	
	
	
	public Amount getPlist() {
		return plist;
	}

	public void setPlist(Amount plist) {
		this.plist = plist;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Amount getDpcash() {
		return dpcash;
	}

	public void setDpcash(Amount dpcash) {
		this.dpcash = dpcash;
	}

	public Amount getDptradein() {
		return dptradein;
	}

	public void setDptradein(Amount dptradein) {
		this.dptradein = dptradein;
	}

	public Amount getTradeinValue() {
		return tradeinValue;
	}

	public void setTradeinValue(Amount tradeinValue) {
		this.tradeinValue = tradeinValue;
	}

	/**
	 * Default Constructor.
	 */
	public OBSpecificChargePlant() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ISpecificChargePlant
	 */
	public OBSpecificChargePlant(ISpecificChargePlant obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getEquipmf() {
		return equipmf;
	}

	public void setEquipmf(String equipmf) {
		this.equipmf = equipmf;
	}

	public String getEquipriskgrading() {
		return equipriskgrading;
	}

	public void setEquipriskgrading(String equipriskgrading) {
		this.equipriskgrading = equipriskgrading;
	}

	public String getEquipcode() {
		return equipcode;
	}

	public void setEquipcode(String equipcode) {
		this.equipcode = equipcode;
	}

	public void setQuantity(Amount quantity) {
		this.quantity = quantity;
	}

	public Amount getQuantity() {
		return quantity;
	}

	/*public String getInspectionStatusCategoryCode() {
		return inspectionStatusCategoryCode;
	}

	public void setInspectionStatusCategoryCode(String inspectionStatusCategoryCode) {
		this.inspectionStatusCategoryCode = inspectionStatusCategoryCode;
	}

	public String getInspectionStatusEntryCode() {
		return inspectionStatusEntryCode;
	}

	public void setInspectionStatusEntryCode(String inspectionStatusEntryCode) {
		this.inspectionStatusEntryCode = inspectionStatusEntryCode;
	}*/

	public ITradeInInfo[] getTradeInInfo() {
		return this.tradeInInfo;
	}

	public void setTradeInInfo(ITradeInInfo[] tradeInInfo) {
		this.tradeInInfo = tradeInInfo;
	}

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBSpecificChargePlant)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}