package com.integrosys.cms.app.collateral.bus.type.document.subtype.leaseagreement;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.type.document.IDocumentCollateral;

public interface ILeaseAgreement extends IDocumentCollateral {
	public String getIssuer();
	
	public void setIssuer(String issuer);
	
	public boolean getLeaseRentalAgreement();
	
	public void setLeaseRentalAgreement(boolean leaseRentalAgreement);
	
	public String getLeaseLimitation();
	
	public void setLeaseLimitation(String leaseLimitation);
	
	public String getPropertyType();
	
	public void setPropertyType(String propertyType);
	
	public String getLotsLocation();
	
	public void setLotsLocation(String lotsLocation);
	
	public String getTitleNumberType();
	
	public void setTitleNumberType(String titleNumberType);
	
	public String getTitleNumberValue();
	
	public void setTitleNumberValue(String titleNumberValue);
	
	public Amount getBuybackValue();
	
	public void setBuybackValue(Amount buybackValue);
	
	public String getLeaseType();
	
	public void setLeaseType(String leaseType);
	
	public Date getDateOfLeaseAgreement();
	
	public void setDateOfLeaseAgreement(Date dateOfLeaseAgreement);
		
	public Amount getGuranteeAmount();
	
	public void setGuranteeAmount(Amount guranteeAmount);
	
}
