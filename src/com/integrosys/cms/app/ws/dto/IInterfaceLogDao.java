package com.integrosys.cms.app.ws.dto;

/**
 @author $Author: Bharat waghela $
 */
import java.io.Serializable;

public interface IInterfaceLogDao {

	static final String ACTUAL_INTERFACE_LOG_NAME = "actualInterfaceLog";

	IInterfaceLogDao getInterfaceLog(String entityName, Serializable key)
			throws InterfaceLogException;
	
	IInterfaceLogDao getInterfaceLogById(Serializable key)
	throws InterfaceLogException;

	IInterfaceLogDao updateInterfaceLog(String entityName, IInterfaceLog item)
			throws InterfaceLogException;

	IInterfaceLogDao deleteInterfaceLog(String entityName, IInterfaceLog item)
			throws InterfaceLogException;

	IInterfaceLogDao enableInterfaceLog(String entityName, IInterfaceLog item)
			throws InterfaceLogException;

	/*IInterfaceLogDao createInterfaceLog(String entityName, IInterfaceLogDao partyGroup)
			throws InterfaceLogException;*/
	
	IInterfaceLog createInterfaceLog(IInterfaceLog PartyGroup)
	        throws InterfaceLogException ;
    
    OBInterfaceLog  getActualFromDTO( InterfaceLoggingDTO dto ) throws InterfaceLogException ;
    
  

}
