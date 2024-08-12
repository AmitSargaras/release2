package com.integrosys.cms.app.discrepency.bus;

/**
 * 
 * @author sandiip.shinde
 * @since 08-06-2011
 */

public class OBDiscrepencyFacilityList implements IDiscrepencyFacilityList{
	
	private long idFacility;
	private long discrepencyId;
	private long facilityId;	
	private String facilityName;
	private OBDiscrepency obDiscrepency;
	
	public OBDiscrepency getObDiscrepency() {
		return obDiscrepency;
	}
	public void setObDiscrepency(OBDiscrepency obDiscrepency) {
		this.obDiscrepency = obDiscrepency;
	}
	public long getIdFacility() {
		return idFacility;
	}
	public void setIdFacility(long idFacility) {
		this.idFacility = idFacility;
	}
	
	public long getDiscrepencyId() {
		return discrepencyId;
	}
	public void setDiscrepencyId(long discrepencyId) {
		this.discrepencyId = discrepencyId;
	}
	public long getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(long facilityId) {
		this.facilityId = facilityId;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public long getVersionTime() {
		return 0;
	}
	public void setVersionTime(long arg0) {		
	}
}
