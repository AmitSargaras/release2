package com.integrosys.cms.app.fileUpload.bus;

public class OBFacilitydetailsFile extends OBCommonFile{

	public OBFacilitydetailsFile()
	{
		super();
	}
	
	private String facilityID;
	private String sanctionAmt;
	private String sanctionAmtInr;
	private String releasableAmt;
	public String getFacilityID() {
		return facilityID;
	}
	public void setFacilityID(String facilityID) {
		this.facilityID = facilityID;
	}
	public String getSanctionAmt() {
		return sanctionAmt;
	}
	public void setSanctionAmt(String sanctionAmt) {
		this.sanctionAmt = sanctionAmt;
	}
	public String getSanctionAmtInr() {
		return sanctionAmtInr;
	}
	public void setSanctionAmtInr(String sanctionAmtInr) {
		this.sanctionAmtInr = sanctionAmtInr;
	}
	public String getReleasableAmt() {
		return releasableAmt;
	}
	public void setReleasableAmt(String releasableAmt) {
		this.releasableAmt = releasableAmt;
	}
	
		
}
