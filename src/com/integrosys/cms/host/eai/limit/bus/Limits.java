package com.integrosys.cms.host.eai.limit.bus;

import java.util.List;
import java.util.Vector;

/**
 * An entity represents a Limit, containing basic info, facility, and other
 * information belong facility, such as BNM, multitier, officer, relationship,
 * rental renewal, security deposit, incremental, reductions.
 * 
 * @author marvin
 * @author Thurein
 * @author Chong Jun Yong
 */
public class Limits implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private LimitGeneral limitGeneral;

	private FacilityMaster facilityMaster;

	private FacilityBNM facilityBNM;

	/** a list of <tt>FacilityOfficer</tt> objects */
	private Vector facilityOfficer;

	/** a list of <tt>FacilityRelationship</tt> objects */
	private Vector facilityRelationship;

	/** a list of <tt>FacilityMultiTierFinancing</tt> objects */
	private Vector facilityMultiTierFinancings;

	private IslamicFacilityMaster islamicFacilityMaster;

	private FacilityBBAVariPackage facilityBBAVariPackage;

	/** a list of <tt>FacilityMessage</tt> objects */
	private List facilityMessages;

	private FacilityIslamicRentalRenewal facilityIslamicRentalRenewal;

	private FacilityIslamicSecurityDeposit facilityIslamicSecurityDeposit;

	/** a list of <tt>FacilityIncremental</tt> objects */
	private List facilityIncrementals;

	/** a list of <tt>FacilityReduction</tt> objects */
	private List facilityReductions;

	public FacilityBBAVariPackage getFacilityBBAVariPackage() {
		return facilityBBAVariPackage;
	}

	public FacilityBNM getFacilityBNM() {
		return facilityBNM;
	}

	public List getFacilityIncrementals() {
		return facilityIncrementals;
	}

	public FacilityIslamicRentalRenewal getFacilityIslamicRentalRenewal() {
		return facilityIslamicRentalRenewal;
	}

	public FacilityIslamicSecurityDeposit getFacilityIslamicSecurityDeposit() {
		return facilityIslamicSecurityDeposit;
	}

	public FacilityMaster getFacilityMaster() {
		return facilityMaster;
	}

	public List getFacilityMessages() {
		return facilityMessages;
	}

	public Vector getFacilityMultiTierFinancings() {
		return facilityMultiTierFinancings;
	}

	public Vector getFacilityOfficer() {
		return facilityOfficer;
	}

	public List getFacilityReductions() {
		return facilityReductions;
	}

	public Vector getFacilityRelationship() {
		return facilityRelationship;
	}

	public IslamicFacilityMaster getIslamicFacilityMaster() {
		return islamicFacilityMaster;
	}

	public LimitGeneral getLimitGeneral() {
		return limitGeneral;
	}

	public void setFacilityBBAVariPackage(FacilityBBAVariPackage facilityBBAVariPackage) {
		this.facilityBBAVariPackage = facilityBBAVariPackage;
	}

	public void setFacilityBNM(FacilityBNM facilityBNM) {
		this.facilityBNM = facilityBNM;
	}

	public void setFacilityIncrementals(List facilityIncrementals) {
		this.facilityIncrementals = facilityIncrementals;
	}

	public void setFacilityIslamicRentalRenewal(FacilityIslamicRentalRenewal facilityIslamicRentalRenewal) {
		this.facilityIslamicRentalRenewal = facilityIslamicRentalRenewal;
	}

	public void setFacilityIslamicSecurityDeposit(FacilityIslamicSecurityDeposit facilityIslamicSecurityDeposit) {
		this.facilityIslamicSecurityDeposit = facilityIslamicSecurityDeposit;
	}

	public void setFacilityMaster(FacilityMaster facilityMaster) {
		this.facilityMaster = facilityMaster;
	}

	public void setFacilityMessages(List facilityMessages) {
		this.facilityMessages = facilityMessages;
	}

	public void setFacilityMultiTierFinancings(Vector facilityMultiTierFinancings) {
		this.facilityMultiTierFinancings = facilityMultiTierFinancings;
	}

	public void setFacilityOfficer(Vector facilityOfficer) {
		this.facilityOfficer = facilityOfficer;
	}

	public void setFacilityReductions(List facilityReductions) {
		this.facilityReductions = facilityReductions;
	}

	public void setFacilityRelationship(Vector facilityRelationship) {
		this.facilityRelationship = facilityRelationship;
	}

	public void setIslamicFacilityMaster(IslamicFacilityMaster islamicFacilityMaster) {
		this.islamicFacilityMaster = islamicFacilityMaster;
	}

	public void setLimitGeneral(LimitGeneral limitGeneral) {
		this.limitGeneral = limitGeneral;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("Limits [");
		buf.append("limitGeneral=");
		buf.append(limitGeneral);
		buf.append(", facilityMaster=");
		buf.append(facilityMaster);
		buf.append(", facilityBNM=");
		buf.append(facilityBNM);
		buf.append(", facilityRelationship=");
		buf.append(facilityRelationship);
		buf.append(", facilityOfficer=");
		buf.append(facilityOfficer);
		buf.append(", facilityMultiTierFinancings=");
		buf.append(facilityMultiTierFinancings);
		buf.append(", islamicFacilityMaster=");
		buf.append(islamicFacilityMaster);
		buf.append(", facilityBBAVariPackage=");
		buf.append(facilityBBAVariPackage);
		buf.append(", facilityMessages=");
		buf.append(facilityMessages);
		buf.append(", facilityIslamicRentalRenewal=");
		buf.append(facilityIslamicRentalRenewal);
		buf.append(", facilityIslamicSecurityDeposit=");
		buf.append(facilityIslamicSecurityDeposit);
		buf.append(", facilityIncrementals=");
		buf.append(facilityIncrementals);
		buf.append(", facilityReductions=");
		buf.append(facilityReductions);
		buf.append("]");
		return buf.toString();
	}

}
