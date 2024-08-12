package com.integrosys.cms.host.eai.security.sharedsecurity.bus;

import com.integrosys.cms.host.eai.security.bus.property.PropertySecurity;

/**
 * @author Iwan
 * @author Chong Jun Yong
 */
public class SharedSecurityResultItem implements java.io.Serializable {

	private static final long serialVersionUID = 5426478276268743346L;

	private long cmsSecurityId;

	private String country;

	private String pledgorLegalName;

	private String idNumber;

	private PropertySecurity property;

	private VehicleDetailSearchItem vehicle;

	private AirCraftDetailSearchItem aircraft;

	private VesselDetailSearchItem vessel;

	private PlantEquipDetailSearchItem plantEquip;

	public AirCraftDetailSearchItem getAircraft() {
		return aircraft;
	}

	public long getCmsSecurityId() {
		return cmsSecurityId;
	}

	public String getCountry() {
		return country;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public PlantEquipDetailSearchItem getPlantEquip() {
		return plantEquip;
	}

	public String getPledgorLegalName() {
		return pledgorLegalName;
	}

	public PropertySecurity getProperty() {
		return property;
	}

	public VehicleDetailSearchItem getVehicle() {
		return vehicle;
	}

	public VesselDetailSearchItem getVessel() {
		return vessel;
	}

	public void setAircraft(AirCraftDetailSearchItem aircraft) {
		this.aircraft = aircraft;
	}

	public void setCmsSecurityId(long cmsSecurityId) {
		this.cmsSecurityId = cmsSecurityId;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public void setPlantEquip(PlantEquipDetailSearchItem plantEquip) {
		this.plantEquip = plantEquip;
	}

	public void setPledgorLegalName(String pledgorLegalName) {
		this.pledgorLegalName = pledgorLegalName;
	}

	public void setProperty(PropertySecurity property) {
		this.property = property;
	}

	public void setVehicle(VehicleDetailSearchItem vehicle) {
		this.vehicle = vehicle;
	}

	public void setVessel(VesselDetailSearchItem vessel) {
		this.vessel = vessel;
	}

}
