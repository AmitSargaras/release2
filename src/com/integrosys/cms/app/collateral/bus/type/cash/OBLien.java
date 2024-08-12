/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/OBContact.java,v 1.2 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.cash;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class represents a contact information which includes address, email and
 * phone numbers.
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public class OBLien implements ILienMethod {
	
	
	//private long _bankingMethodID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	
		
	private long cashDepositID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	
	/////////////////////////
	private long lienID;//LIEN_ID
	
	//private Long cashDepositId;	//CASH_DEPOSIT_ID
	
	private long depositReferenceNumber;//DEPOSIT_REFERENCE_NUMBER

	private String lienNumber;//LIEN_NUMBER
	
	private double lienAmount;//LIEN_AMOUNT
	
	private long actualLienId;

	private String status;//STATUS
	
	private String serialNo;//SERIAL_NO
	
	private String remark;//REMARK
	
	private String baselSerial;
	
    private String facilityName;
	 
	 private String facilityId;
	 
	 private String lcnNo;
	
	public long getCashDepositID() {
		return cashDepositID;
	}
	
	public void setCashDepositID(long value) {
		cashDepositID = value;
	}
	

	/*public Long getCashDepositId() {
		return cashDepositId;
	}

	public void setCashDepositId(Long cashDepositId) {
		this.cashDepositId = cashDepositId;
	}*/

	public long getActualLienId() {
		return actualLienId;
	}

	public void setActualLienId(long actualLienId) {
		this.actualLienId = actualLienId;
	}

	public long getDepositReferenceNumber() {
		return depositReferenceNumber;
	}

	public void setDepositReferenceNumber(long depositReferenceNumber) {
		this.depositReferenceNumber = depositReferenceNumber;
	}

	public String getLienNumber() {
		return lienNumber;
	}

	public void setLienNumber(String lienNumber) {
		this.lienNumber = lienNumber;
	}
	
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	public double getLienAmount() {
		return lienAmount;
	}

	public void setLienAmount(double lienAmount) {
		this.lienAmount = lienAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Default Constructor
	 */
	public OBLien() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type IContact
	 */
	public OBLien(ILienMethod value) {
		this();
		AccessorUtil.copyValue(value, this);
	}
	
	
	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	public long getLienID() {
		return lienID;
	}

	public void setLienID(long lienID) {
		this.lienID = lienID;
	}

	public String getBaselSerial() {
		return baselSerial;
	}

	public void setBaselSerial(String baselSerial) {
		this.baselSerial=baselSerial;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getLcnNo() {
		return lcnNo;
	}

	public void setLcnNo(String lcnNo) {
		this.lcnNo = lcnNo;
	}

	

	
}