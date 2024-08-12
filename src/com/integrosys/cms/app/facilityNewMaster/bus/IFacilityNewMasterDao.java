package com.integrosys.cms.app.facilityNewMaster.bus;

import java.io.Serializable;
/**
 * @author  Abhijit R. 
 */
public interface IFacilityNewMasterDao {

	static final String ACTUAL_FACILITY_NEW_MASTER_NAME = "actualFacilityNewMaster";
	static final String STAGE_FACILITY_NEW_MASTER_NAME = "stageFacilityNewMaster";

	IFacilityNewMaster getFacilityNewMaster(String entityName, Serializable key)throws FacilityNewMasterException;
	IFacilityNewMaster updateFacilityNewMaster(String entityName, IFacilityNewMaster item)throws FacilityNewMasterException;
	IFacilityNewMaster deleteFacilityNewMaster(String entityName, IFacilityNewMaster item);
	IFacilityNewMaster load(String entityName,long id)throws FacilityNewMasterException;
	boolean isUniqueCode(String lineNumber,String system) throws FacilityNewMasterException;
	boolean isUniqueFacilityCode(String facilityCode) throws FacilityNewMasterException;
	IFacilityNewMaster createFacilityNewMaster(String entityName, IFacilityNewMaster facilityNewMaster)
	throws FacilityNewMasterException;

	public IFacilityNewMaster getFacilityNewMasterRiskType(String facilityCat,String facilityName)throws FacilityNewMasterException;
	public IFacilityNewMaster getFacilityNewMasterRiskTypeWithFacCode(String facCode)throws FacilityNewMasterException;
	public boolean isFacilityNameUnique(String facilityName);
	public boolean isFacilityCpsIdUnique(String cpsId);
	
}
