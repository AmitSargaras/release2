/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/IContact.java,v 1.2 2003/06/17 05:08:10 kllee Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.cash;

import com.integrosys.cms.app.customer.bus.ISystem;

/**
 * This interface represents a contact information which includes address, email
 * and phone numbers.
 * 
 * @author $@author sachin.patil $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/17 05:08:10 $ Tag: $Name: $
 */
public interface ILienMethod extends java.io.Serializable {
	
	public long getLienID();//LIEN_ID

	public void setLienID(long lienId);

	public long getCashDepositID();//CASH_DEPOSIT_ID

	public void setCashDepositID(long cashDepositeId);

	
	public long getDepositReferenceNumber();//DEPOSIT_REFERENCE_NUMBER

	public void setDepositReferenceNumber(long depositReferenceNumber);
	
	public String getLienNumber();//LIEN_NUMBER

	public void setLienNumber(String lienNumber);

	public double getLienAmount();//LIEN_AMOUNT

	public void setLienAmount(double lienAmount);
	
	public long getActualLienId();//LIEN_AMOUNT

	public void setActualLienId(long actualLienId);
	
	public String getStatus(); //STATUS
	
	public String getSerialNo();//SERIAL_NO

	public void setSerialNo(String serialNo);
	
	public String getBaselSerial();//BASEL_SERIAL

	public void setBaselSerial(String baselSerial);//BASEL_SERIAL
	
	public String getRemark();//REMARK

	public void setRemark(String remark);
	
	public void setStatus(String status);
		
	/*public ILienMethod[] getLien();  

	public void setLien(ILienMethod[] Lien); */
		
		
	/*public long  getLien();
	
	public void setLien(long lienId);
	
	public void getCashDepositFK();
	
	public long setCashDepositFK(long lienFK);
	
	public void getLienPK();
	
	public long setLienPK(long lienPK);*/
	public String getFacilityName();
	public void setFacilityName(String facilityName);
	public String getFacilityId();
	public void setFacilityId(String facilityId);
	public String getLcnNo();
	public void setLcnNo(String lcnNo);
}