/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/ICMSLegalEntity.java,v 1.7 2003/09/18 11:40:15 hltan Exp $
 */
package com.integrosys.cms.ui.collateral.cash;

import java.util.Date;
import java.util.List;

import com.integrosys.base.businfra.customer.ILegalEntity;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod;
import com.integrosys.cms.app.customer.bus.ISystem;

/**
 * This interface represents a Legal Entity in the customer package. A Legal
 * Entity refers to portion of a customer's attributes that are constant across
 * different subdiaries. However this interface itself is an "incomplete"
 * picture of a customer's attributes, and therefore should be extended to
 * complete the definition of a customer.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.7 $
 * @since $Date: 2003/09/18 11:40:15 $ Tag: $Name: $
 */
public interface ILienEntity extends ILegalEntity, IValueObject {
	

	public ILienMethod[] getILienMethod();  

	public void setILienMethod(ILienMethod[] lien); 
	
	

	public long getLienID();//LIEN_ID

	public void setLienID(long lienId);

	public Long getCashDepositId();//CASH_DEPOSIT_ID

	public void setCashDepositId(Long cashDepositeId);

	
	public long getDepositReferenceNumber();//DEPOSIT_REFERENCE_NUMBER

	public void setDepositReferenceNumber(long depositReferenceNumber);
	
	public String getLienNumber();//LIEN_NUMBER

	public void setLienNumber(String lienNumber);

	public double getLienAmount();//LIEN_AMOUNT

	public void setLienAmount(double lienAmount);
	
	  public String getStatus(); //STATUS
		
		public void setStatus(String status);
		
		public String getSerialNo();//SERIAL_NO

		public void setSerialNo(String serialNo);
		
		public String getRemark();//REMARK

		public void setRemark(String remark);
		
		public String getBaselSerial();   // BASEL_SERIAL
		
		public void setBaselSerial(String baselSerial);

	
}