package com.integrosys.cms.ui.manualinput.limit.othercovenantsdetails;

import java.util.List;

import com.integrosys.cms.app.limit.bus.OBOtherCovenant;

public interface IOtherCovenantDetailsDAO {
	
	public final static String ACTUAL_OTHER_COVENANT_DETAILS = "actualOtherCovenantDetails";
	
	public final static String STAGING_OTHER_COVENANT_DETAILS = "stagingotherCovenantDetails";
	
	public final static String STAGING_OTHER_COVENANT_DETAILS_VALUES = "stagingotherCovenantDetailsValues";
	
	public final static String ACTUAL_OTHER_COVENANT_DETAILS_VALUES = "actualOtherCovenantDetailsValues";
	
	public void insertOtherCovenantDetailsStage(OBOtherCovenant obothercovenant);

	public void insertOtherCovenantDetailsActual(OBOtherCovenant obothercovenant);

	public List getOtherCovenantDetailsStaging(String stagingrefid);
	
	public List getOtherCovenantDetailsActual(String refid);

	public void disableOtherCovenantDetails(String referenceId);
	
	public void updateOtherCovenantDetailsActual(OBOtherCovenant obothercovenant);
	
	public List getFaciltyNameForOtherCovenant(String cmsLmtProID);
	
	public String getOtherCovenantDetailsStagingIdFromSeq();
	
	public String getOtherCovenantDetailsActualIdFromSeq();
	
	public void insertStageOtherCovenantDetailsValues(OBOtherCovenant obothercovenant);
	
	public void insertActualsOtherCovenantDetailsValues(OBOtherCovenant obothercovenant);

	public List getOtherCovenantDetailsValuesStaging(String StagingOCid);
	
	public List getOtherCovenantDetailsValuesActualList(String StagingOCid);
	
	public String getOtherCovenantDetailsValuesActual(String ActualOCid);
	
	public String getOtherCovenantDetailsValuesStagingInString(String StagingOCid);
	
	public void updateOtherCovenantDetailsActualValues(OBOtherCovenant obothercovenant);

	public void deleteOtherCovenantValues(String othercovenantvalues);

	public String getOtherCovenantDetailsFacilityValuesActual(String ActualOCid);
	
	public void deleteOtherCovenantStagingValues(String othercovenantid);
	
	public void updateOtherCovenantDetailsStaging(OBOtherCovenant obothercovenant);
	
	public String getOtherCovenantDetailsFacilityValuesStaging(String StagingOCid) ;
	
	public long getOtherCovenantIdActual(String partyId);
	
	public void deleteOtherCovenantValues1(String partyId);

	public void deleteOtherCovenantValues2(String partyId);
	
	public long getCMSLimitProfileIdActual(String partyId);

	public void deleteOtherCovenantDetailsStage(String partyId);

	public void deleteOtherCovenantDetailsActual(String partyId);

}
