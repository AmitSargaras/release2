package com.aurionpro.clims.rest.interfaceLog;

public interface IRestCamInterfaceLogDAO {
	
	public final static String CAM_REST_SERVICE_INTERFACE_LOG = "camRestServiceInterfaceLog";

	public void insertCamDetailsInterfaceLog(OBRestCamInterfaceLog restInterfaceLog);

	public void updateCamDetailsInterfaceLog(OBRestCamInterfaceLog restInterfaceLog);

}
