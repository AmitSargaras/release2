package com.integrosys.cms.app.manualinput.party.bus;

/**
 @author $Author: Ankit Limbadia  $
 */
import java.util.Date;

import com.integrosys.cms.app.manualinput.party.IPANValidationLog;

public interface IPanValidationLogDao {

	static final String ACTUAL_INTERFACE_LOG_NAME = "actualPANValidationLog";

	IPANValidationLog updateInterfaceLog(String entityName, IPANValidationLog item);
	
	IPANValidationLog createInterfaceLog(IPANValidationLog iPANValidationLog);
	
	Boolean checkPANAlreadyValidated (String entityName, String panNo,String partyId, String partyNameAsPerPan, String dateOfIncorporation);
	
	public Date fetchLastValidatedDate (String entityName, String panNo,String partyId);
	
//	String getSequenceNoForRequestNo();

}
