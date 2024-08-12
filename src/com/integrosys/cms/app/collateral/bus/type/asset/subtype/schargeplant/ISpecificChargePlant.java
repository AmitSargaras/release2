/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/schargeplant/ISpecificChargePlant.java,v 1.4 2003/07/28 06:06:49 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeplant;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.IChargeCommon;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.ITradeInInfo;

import java.util.Date;

/**
 * This interface represents Asset of type Specific Charge - Plant & Equipment.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/07/28 06:06:49 $ Tag: $Name: $
 */
public interface ISpecificChargePlant extends IChargeCommon {

	// methods for specific charge - plant and equipment
	public String getPurpose();

	public void setPurpose(String purpose);

	public String getEquipmf();

	public void setEquipmf(String equipmf);

	public String getEquipriskgrading();

	public void setEquipriskgrading(String equipriskgrading);

	public String getEquipcode();

	public void setEquipcode(String equipcode);

	public Amount getQuantity();

	public void setQuantity(Amount quantity);

	public String getSerialNumber();

	public void setSerialNumber(String serialNumber);

	public String getInvoiceNumber();

	public void setInvoiceNumber(String invoiceNumber);

	public Amount getDptradein();

	public void setDptradein(Amount dptradein);

	public Amount getDpcash();

	public void setDpcash(Amount dpcash);

	public Amount getTradeinValue();

	public void setTradeinValue(Amount tradeinValue);

	/*public String getInspectionStatusCategoryCode();

	public void setInspectionStatusCategoryCode(String inspectionStatusCategoryCode);

	public String getInspectionStatusEntryCode();

	public void setInspectionStatusEntryCode(String inspectionStatusEntryCode);*/

	public ITradeInInfo[] getTradeInInfo();

	public void setTradeInInfo(ITradeInInfo[] tradeInInfo);

    public Date getInvoiceDate();

    public void setInvoiceDate(Date invoiceDate);
    
	public Amount getPlist();

	public void setPlist(Amount plist);
	
	public String getRamId();

	public void setRamId(String ramId);
	
	public String getInsuranceStatus();
	
	public void setInsuranceStatus(String insuranceStatus);

	public Date getOriginalTargetDate();

	public void setOriginalTargetDate(Date originalTargetDate);

	public Date getNextDueDate();

	public void setNextDueDate(Date nextDueDate);
	
	public Date getDateDeferred();

	public void setDateDeferred(Date dateDeferred);

	public String getCreditApprover();

	public void setCreditApprover(String creditApprover);

	public Date getWaivedDate();

	public void setWaivedDate(Date waivedDate);
}
