/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.app.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


/**
 * Describe this class. Purpose: To set getter and setter method for the value needed
 * by Party Detail Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Ankit
 * @version $Revision:$
 * @since $Date:05-AUG-2014 $ Tag: $Name$
 */

@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement(name = "PartyDetails")

public class PartyDirectorDetailsRequestDTO {
	
	private static final long serialVersionUID = -114309476199266725L;
	
	@XmlElement(name = "relatedType",required=true)
	private String relatedType;
	@XmlElement(name = "relationship",required=true)
	private String relationship;
	@XmlElement(name = "directorEmailId",required=true)
	private String directorEmailId;
	@XmlElement(name = "directorCountry",required=true)
	private String directorCountry;
	@XmlElement(name = "directorState",required=true)
	private String directorState;
	@XmlElement(name = "directorCity",required=true)
	private String directorCity;
	@XmlElement(name = "directorRegion",required=true)
	private String directorRegion;
	@XmlElement(name = "directorPincode",required=true)
	private String directorPincode;
	@XmlElement(name = "directorAddr3",required=true)
	private String directorAddr3;
	@XmlElement(name = "directorAddr2",required=true)
	private String directorAddr2;
	@XmlElement(name = "directorAddr1",required=true)
	private String directorAddr1;
	@XmlElement(name = "percentageOfControl",required=true)
	private String percentageOfControl;
	@XmlElement(name = "fullName",required=true)
	private String fullName;
	@XmlElement(name = "namePrefix",required=true)
	private String namePrefix;
	@XmlElement(name = "businessEntityName",required=true)
	private String businessEntityName;
	@XmlElement(name = "directorPAN",required=true)
	private String directorPAN;
	/*@XmlElement(name = "directorAADHAR",required=true)
	private String directorAADHAR;*/
	@XmlElement(name = "directorFaxStdCode",required=true)
	private String directorFaxStdCode;
	@XmlElement(name = "directorFaxNo",required=true)
	private String directorFaxNo;
	@XmlElement(name = "directorTelephoneStdCode",required=true)
	private String directorTelephoneStdCode;
	@XmlElement(name = "directorTelNo",required=true)
	private String directorTelNo;
	
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

	public String getDirectorEmailId() {
		return directorEmailId;
	}

	public void setDirectorEmailId(String directorEmailId) {
		this.directorEmailId = directorEmailId;
	}

	public String getDirectorFaxNo() {
		return directorFaxNo;
	}

	public void setDirectorFaxNo(String directorFaxNo) {
		this.directorFaxNo = directorFaxNo;
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

	public String getDirectorPincode() {
		return directorPincode;
	}

	public void setDirectorPincode(String directorPincode) {
		this.directorPincode = directorPincode;
	}

	public String getDirectorAddr3() {
		return directorAddr3;
	}

	public void setDirectorAddr3(String directorAddr3) {
		this.directorAddr3 = directorAddr3;
	}

	public String getDirectorAddr2() {
		return directorAddr2;
	}

	public void setDirectorAddr2(String directorAddr2) {
		this.directorAddr2 = directorAddr2;
	}

	public String getDirectorAddr1() {
		return directorAddr1;
	}

	public void setDirectorAddr1(String directorAddr1) {
		this.directorAddr1 = directorAddr1;
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

	public String getDirectorPAN() {
		return directorPAN;
	}

	public void setDirectorPAN(String directorPAN) {
		this.directorPAN = directorPAN;
	}
	
	/*public String getDirectorAADHAR() {
		return directorAADHAR;
	}

	public void setDirectorAADHAR(String directorAADHAR) {
		this.directorAADHAR = directorAADHAR;
	}
*/
	public String getDirectorTelephoneStdCode() {
		return directorTelephoneStdCode;
	}

	public void setDirectorTelephoneStdCode(String directorTelephoneStdCode) {
		this.directorTelephoneStdCode = directorTelephoneStdCode;
	}

	public String getDirectorFaxStdCode() {
		return directorFaxStdCode;
	}

	public void setDirectorFaxStdCode(String directorFaxStdCode) {
		this.directorFaxStdCode = directorFaxStdCode;
	}


}