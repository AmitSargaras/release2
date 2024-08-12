package com.integrosys.cms.app.leiDateValidation.bus;

import java.io.Serializable;
import java.util.List;

import com.integrosys.cms.app.fileUpload.bus.OBLeiDetailsFile;

public interface ILeiDateValidationDao {

	
	static final String ACTUAL_LEI_DATE_VALIDATION = "actualLeiDateValidation";
	static final String STAGE_LEI_DATE_VALIDATION = "stageLeiDateValidation";
	
	ILeiDateValidation getLeiDateValidation(String entityName, Serializable key)throws LeiDateValidationException;
	ILeiDateValidation updateLeiDateValidation(String entityName, ILeiDateValidation item)throws LeiDateValidationException;
	ILeiDateValidation createLeiDateValidation(String entityName, ILeiDateValidation excludedFacility)
			throws LeiDateValidationException;
	
	public boolean isPartyIDUnique(String partyID);
	
	/*SearchResult getSearchedLeiDateValidation(String searchBy,String searchText)throws LeiDateValidationException;*/
	
	public List getLeiDateValidationList();
	public Integer getLeiDateValidationPeriod(String partyID);
	boolean isValidPartyID(String partyId);
	public void updatePublicInputLEI(OBLeiDetailsFile obLeiDetailsFile);
}
