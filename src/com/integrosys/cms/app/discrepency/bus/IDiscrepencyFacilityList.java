package com.integrosys.cms.app.discrepency.bus;

import java.io.Serializable;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
 * @author sandiip.shinde
 * @since 08-06-2011
 */

public interface IDiscrepencyFacilityList extends IValueObject,Serializable{
	
	public long getIdFacility();
	public void setIdFacility(long idFacility);
	
	public long getDiscrepencyId();
	public void setDiscrepencyId(long discrepencyId);
	
	public long getFacilityId();
	public void setFacilityId(long facilityId);
	 
	public String getFacilityName();
	public void setFacilityName(String facilityName);
	
	public OBDiscrepency getObDiscrepency() ;
	public void setObDiscrepency(OBDiscrepency obDiscrepency) ;
}