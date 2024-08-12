package com.integrosys.cms.app.udf.bus;

import java.io.Serializable;
import java.util.List;

import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.manualinput.aa.AADetailForm;

public interface IUdfDao {
	static final String ACTUAL_UDF_NAME = "actualUdf";
	static final String STAGE_UDF_NAME = "stageUdf";
	
	
	public IUdf insertUdf(IUdf udf) throws UdfException;
	public IUdf findUdfById(String entityName, long id) throws UdfException;
	public void deleteUdf(IUdf udf) throws UdfException;
	public IUdf updateUdf(IUdf udf) throws UdfException;
	public List findAllUdfs() throws UdfException;	
	public List getUdfByModuleId (String moduleId) throws UdfException;
	public List getUdfSequencesByModuleId (String moduleId)  throws UdfException;
	public void freezeUdf(IUdf udf) throws UdfException;
	public List findUdfByStatus(String entityName, String status) throws UdfException;
	public List getUdfByModuleIdAndStatus (String moduleId, String status)  throws UdfException;
	public List getUdfByMandatory (String moduleId)  throws UdfException ;
	public List getUdfByFieldTypeId(String moduleId ,int fieldId)  throws UdfException;

	public boolean findUdfByName( String name , String ModuleId) throws UdfException ;
	//Start: Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)
	public AADetailForm setUdfDataForCam(ILimitProfile limitProfileOB);
	//End: Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)
	//added by santosh for CR ubs limit
	public List getUdfByNonMandatory (String moduleId)  throws UdfException ;
	//end santosh
	
	IUdf getUdf(String entityName, Serializable key)throws UdfException;
	IUdf updateUdf(String entityName, IUdf item)throws UdfException;
	IUdf createUdf(String entityName, IUdf excludedFacility) throws UdfException;
	public List getUdfList();
	
	IUdf deleteUdf(String entityName, IUdf item)
			throws UdfException;

	IUdf enableUdf(String entityName, IUdf item)
			throws UdfException;
	
	IUdf getUdfByModuleIdAndSequence(String moduleId, String sequence);
//	public List getUdfByMandatoryView(String string)  throws UdfException ;
//	public List getUdfByNonMandatoryView(String moduleId)  throws UdfException;

	
}
