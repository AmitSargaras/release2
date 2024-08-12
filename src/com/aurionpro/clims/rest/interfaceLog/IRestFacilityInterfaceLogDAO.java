package com.aurionpro.clims.rest.interfaceLog;

public interface IRestFacilityInterfaceLogDAO {
	
	public final static String FACILITY_REST_SERVICE_INTERFACE_LOG = "facilityRestServiceInterfaceLog";

	public void insertFacilityDetailsInterfaceLog(OBRestFacilityInterfaceLog restInterfaceLog);
	
	public final static String SECURITY_REST_SERVICE_INTERFACE_LOG = "securityRestServiceInterfaceLog";
	
	public void insertSecurityDetailsInterfaceLog(OBRestFacilityInterfaceLog restInterfaceLog);
	

/*	public void updateCamDetailsInterfaceLog(OBRestPartyInterfaceLog restInterfaceLog);*/

}
