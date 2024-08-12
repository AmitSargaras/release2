package com.integrosys.cms.app.customer.bus;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import org.springframework.util.comparator.NullSafeComparator;

public class OBLineCovenantReport implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*private long covenantId = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	private long lineFK = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	public long getLineFK() {
		return lineFK;
	}

	public void setLineFK(long lineFK) {
		this.lineFK = lineFK;
	}*/

	/*public OBLineCovenantReport(String partyId, String partyName, String facilityName, String facilityId,
			String facilityCategory, String lineCode, String serialNumber, String typeOfCovenant, String condition1,
			String condition2, String condition3, String condition4, String condition5) {
		super();
		this.partyId = partyId;
		this.partyName = partyName;
		this.facilityName = facilityName;
		this.facilityId = facilityId;
		this.facilityCategory = facilityCategory;
		this.lineCode = lineCode;
		this.serialNumber = serialNumber;
		this.typeOfCovenant = typeOfCovenant;
		this.condition1 = condition1;
		this.condition2 = condition2;
		this.condition3 = condition3;
		this.condition4 = condition4;
		this.condition5 = condition5;
	}
*/
	private String partyId;
	private String partyName;
	private String facilityName;
	private String facilityId;	
	private String facilityCategory;
	private String lineCode;
	private String serialNumber;
	private String typeOfCovenant;
	private String condition1;
	private String condition2;
	private String condition3;
	private String condition4;
	private String condition5;

	/*public long getCovenantId() {
		return covenantId;
	}

	public void setCovenantId(long covenantId) {
		this.covenantId = covenantId;
	}*/

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
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

	public String getFacilityCategory() {
		return facilityCategory;
	}

	public void setFacilityCategory(String facilityCategory) {
		this.facilityCategory = facilityCategory;
	}

	public String getLineCode() {
		return lineCode;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getTypeOfCovenant() {
		return typeOfCovenant;
	}

	public void setTypeOfCovenant(String typeOfCovenant) {
		this.typeOfCovenant = typeOfCovenant;
	}

	public String getCondition1() {
		return condition1;
	}

	public void setCondition1(String condition1) {
		this.condition1 = condition1;
	}

	public String getCondition2() {
		return condition2;
	}

	public void setCondition2(String condition2) {
		this.condition2 = condition2;
	}

	public String getCondition3() {
		return condition3;
	}

	public void setCondition3(String condition3) {
		this.condition3 = condition3;
	}

	public String getCondition4() {
		return condition4;
	}

	public void setCondition4(String condition4) {
		this.condition4 = condition4;
	}

	public String getCondition5() {
		return condition5;
	}

	public void setCondition5(String condition5) {
		this.condition5 = condition5;
	}
	
	
	
	
}
