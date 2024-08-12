/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/OBContact.java,v 1.2 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.customer.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class represents a contact information which includes address, email and
 * phone numbers.
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public class OBDirector implements IDirector{
	private long _directorID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	
    private String relatedType;
	
	private String relationship;
	
	private String directorEmail;
	
	private String directorFax;
	
	private String directorTelNo;
	
	private String directorCountry;

	private String directorState;

	private String directorCity;
	
	private String directorRegion;
	
	private String directorPostCode;
	
	private String directorAddress3;
	
	private String directorAddress2;
	
	private String directorAddress1;
	
	private String percentageOfControl;
	
	private String fullName;
	
	private String namePrefix;
	
	private String dirStdCodeTelNo;
	
	private String dirStdCodeTelex;
	
	private String businessEntityName;
	
	private String directorPan;
	
	private String directorAadhar;
	
	private String relatedDUNSNo;
	
	private String dinNo;
	
	private String directorName;

	private long LEID;
	
	
	/**
	 * Default Constructor
	 */
	public OBDirector() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type IContact
	 */
	public OBDirector(ISystem value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	

	public long getDirectorID() {
		return _directorID;
	}

	public void setDirectorID(long directorID) {
		_directorID = directorID;
	}

	
	public String getRelatedType() {
		return relatedType;
	}

	public void setRelatedType(String relatedType) {
		this.relatedType = relatedType;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getDirectorEmail() {
		return directorEmail;
	}

	public void setDirectorEmail(String directorEmail) {
		this.directorEmail = directorEmail;
	}

	public String getDirectorFax() {
		return directorFax;
	}

	public void setDirectorFax(String directorFax) {
		this.directorFax = directorFax;
	}

	public String getDirectorTelNo() {
		return directorTelNo;
	}

	public void setDirectorTelNo(String directorTelNo) {
		this.directorTelNo = directorTelNo;
	}

	public String getDirectorCountry() {
		return directorCountry;
	}

	public void setDirectorCountry(String directorCountry) {
		this.directorCountry = directorCountry;
	}

	public String getDirectorState() {
		return directorState;
	}

	public String getDirectorAadhar() {
		return directorAadhar;
	}

	public void setDirectorAadhar(String directorAadhar) {
		this.directorAadhar = directorAadhar;
	}

	public void setDirectorState(String directorState) {
		this.directorState = directorState;
	}

	public String getDirectorCity() {
		return directorCity;
	}

	public void setDirectorCity(String directorCity) {
		this.directorCity = directorCity;
	}

	public String getDirectorRegion() {
		return directorRegion;
	}

	public void setDirectorRegion(String directorRegion) {
		this.directorRegion = directorRegion;
	}

	public String getDirectorPostCode() {
		return directorPostCode;
	}

	public void setDirectorPostCode(String directorPostCode) {
		this.directorPostCode = directorPostCode;
	}

	public String getDirectorAddress3() {
		return directorAddress3;
	}

	public void setDirectorAddress3(String directorAddress3) {
		this.directorAddress3 = directorAddress3;
	}

	public String getDirectorAddress2() {
		return directorAddress2;
	}

	public void setDirectorAddress2(String directorAddress2) {
		this.directorAddress2 = directorAddress2;
	}

	public String getDirectorAddress1() {
		return directorAddress1;
	}

	public void setDirectorAddress1(String directorAddress1) {
		this.directorAddress1 = directorAddress1;
	}

	public String getPercentageOfControl() {
		return percentageOfControl;
	}

	public void setPercentageOfControl(String percentageOfControl) {
		this.percentageOfControl = percentageOfControl;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getNamePrefix() {
		return namePrefix;
	}

	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}

	public String getBusinessEntityName() {
		return businessEntityName;
	}

	public void setBusinessEntityName(String businessEntityName) {
		this.businessEntityName = businessEntityName;
	}

	public String getDirectorPan() {
		return directorPan;
	}

	public void setDirectorPan(String directorPan) {
		this.directorPan = directorPan;
	}

	public String getRelatedDUNSNo() {
		return relatedDUNSNo;
	}

	public void setRelatedDUNSNo(String relatedDUNSNo) {
		this.relatedDUNSNo = relatedDUNSNo;
	}

	public String getDinNo() {
		return dinNo;
	}

	public void setDinNo(String dinNo) {
		this.dinNo = dinNo;
	}

	public String getDirectorName() {
		return directorName;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}
	public long getLEID() {
		return LEID;
	}

	
	public void setLEID(long LEID) {
		this.LEID = LEID;
		
	}
	
	
	public String getDirStdCodeTelNo() {
		return dirStdCodeTelNo;
	}

	public void setDirStdCodeTelNo(String dirStdCodeTelNo) {
		this.dirStdCodeTelNo = dirStdCodeTelNo;
	}

	public String getDirStdCodeTelex() {
		return dirStdCodeTelex;
	}

	public void setDirStdCodeTelex(String dirStdCodeTelex) {
		this.dirStdCodeTelex = dirStdCodeTelex;
	}

	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}