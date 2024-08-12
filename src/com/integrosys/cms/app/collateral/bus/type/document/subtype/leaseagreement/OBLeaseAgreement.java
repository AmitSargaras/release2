package com.integrosys.cms.app.collateral.bus.type.document.subtype.leaseagreement;

import java.util.Date;

import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.document.OBDocumentCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;

public class OBLeaseAgreement extends OBDocumentCollateral implements ILeaseAgreement {
	private String issuer;
	private boolean leaseRentalAgreement;
	private String leaseLimitation;
	private String propertyType;
	private String lotsLocation;
	private String titleNumberType;
	private String titleNumberValue;
	private Amount buybackValue;
	private String leaseType;
	private Date dateOfLeaseAgreement;
	private Amount guranteeAmount;
	
	
	public OBLeaseAgreement() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_DOC_LEASE_AGREEMENT));		
	}
	
	public OBLeaseAgreement(ILeaseAgreement obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}
	
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public boolean getLeaseRentalAgreement() {
		return leaseRentalAgreement;
	}
	public void setLeaseRentalAgreement(boolean leaseRentalAgreement) {
		this.leaseRentalAgreement = leaseRentalAgreement;
	}
	public String getLeaseLimitation() {
		return leaseLimitation;
	}
	public void setLeaseLimitation(String leaseLimitation) {
		this.leaseLimitation = leaseLimitation;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public String getLotsLocation() {
		return lotsLocation;
	}
	public void setLotsLocation(String lotsLocation) {
		this.lotsLocation = lotsLocation;
	}
	public String getTitleNumberType() {
		return titleNumberType;
	}
	public void setTitleNumberType(String titleNumberType) {
		this.titleNumberType = titleNumberType;
	}
	public String getTitleNumberValue() {
		return titleNumberValue;
	}
	public void setTitleNumberValue(String titleNumberValue) {
		this.titleNumberValue = titleNumberValue;
	}
	public Amount getBuybackValue() {
		return buybackValue;
	}
	public void setBuybackValue(Amount buybackValue) {
		this.buybackValue = buybackValue;
	}
	public String getLeaseType() {
		return leaseType;
	}
	public void setLeaseType(String leaseType) {
		this.leaseType = leaseType;
	}

	public Date getDateOfLeaseAgreement() {
		return dateOfLeaseAgreement;
	}

	public void setDateOfLeaseAgreement(Date dateOfLeaseAgreement) {
		this.dateOfLeaseAgreement = dateOfLeaseAgreement;
	}

	public Amount getGuranteeAmount() {
		return guranteeAmount;
	}

	public void setGuranteeAmount(Amount guranteeAmount) {
		this.guranteeAmount = guranteeAmount;
	}

}
