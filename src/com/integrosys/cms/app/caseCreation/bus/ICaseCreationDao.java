package com.integrosys.cms.app.caseCreation.bus;

import java.io.Serializable;
import java.util.ArrayList;

import com.integrosys.base.businfra.search.SearchResult;
/**
 * @author  Abhijit R. 
 */
public interface ICaseCreationDao {

	static final String ACTUAL_HOLIDAY_NAME = "actualCaseCreation";

	ICaseCreation getCaseCreation(String entityName, long caseId, long checklistitemid)
	throws Exception;
	ICaseCreation updateCaseCreation(String entityName, ICaseCreation item)
	throws Exception;
	ICaseCreation load(String entityName,long id)
	throws Exception;
	SearchResult listCaseCreation( String entityName,long caseCreationId)
	throws Exception ;
	ICaseCreation createCaseCreation(String entityName, ICaseCreation caseCreation)
	throws Exception;
	
	public void createCaseCreationRemark(String entityName,ICaseCreationRemark caseCreationRemark)
	throws Exception;
	
	public SearchResult listCaseCreationRemark( String entityName,long limitProfileId)
	throws Exception;
	
	public SearchResult listCaseCreationByLimitProfileId( String entityName,long limitProfileId)throws Exception ;
	
	public ArrayList getStageCaseCreation(String entityName, long key)throws Exception;
	
	public ArrayList listStageCaseCreationByLimitProfileId( long limitProfileId)throws Exception ;
	
	public void updateImageTagUntagStatus(String imageId,String checklistId,String status);
}
