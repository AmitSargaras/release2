package com.integrosys.cms.ui.genlad;


import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;


public class ReportMapper extends AbstractCommonMapper {

	@Override
	public Object mapFormToOB(CommonForm cForm, HashMap inputs)
			throws MapperException {
		
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		GenerateLADForm form=(GenerateLADForm)cForm;
		OBFilter filter= new OBFilter();
		filter.setReportId(form.getReportId());
		filter.setSegment(form.getSegment());
		
		filter.setRelationshipMgr(form.getRelationshipMgr());
		filter.setRelationshipMgrId(form.getRelationshipMgrId());
		
		filter.setParty(form.getParty());
		filter.setPartyId(form.getPartyId());
		filter.setRelationManager(form.getRelationManager());
		filter.setRbiAsset(form.getRbiAsset());
		filter.setStatus(form.getStatus());
		filter.setBranchId(form.getBranchId());
		filter.setDocumentType(form.getDocumentType());
		filter.setRelatoionship(form.getRelatoionship());
		filter.setGuarantor(form.getGuarantor());
		filter.setUserId(form.getUserId());
		filter.setDepartmentId(form.getDepartmentId());
		filter.setIndustry(form.getIndustry());
		filter.setUploadSystem(form.getUploadSystem());
		filter.setProfile(form.getProfile());
		
		filter.setSearchCustomerName(form.getSearchCustomerName());
		filter.setRelationshipMgr(form.getRelationshipMgr());
		filter.setDueMonth(form.getDueMonth());
		filter.setDueYear(form.getDueYear());
		filter.setSegment(form.getSegment());
		
		filter.setRmRegion(form.getRmRegion());
		filter.setRelationshipMgr(form.getRelationshipMgr());
		filter.setCaseId(form.getCaseId());
		filter.setModuleEvent(form.getModuleEvent());
		filter.setTatCriteria(form.getTatCriteria());
		filter.setCategory(form.getCategory());
		filter.setFilterDocument(form.getFilterDocument());

		filter.setFilterPartyMode(form.getFilterPartyMode());
		filter.setUploadSystem(form.getUploadSystem());
		
		filter.setFilterUserMode(form.getFilterUserMode());
		
		if(form.getFromDate()!=null)
			filter.setFromDate(form.getFromDate());
		if(form.getToDate()!=null)
			filter.setToDate(form.getToDate());
		
		//Uma:For Cam Quarter Activity CR
		filter.setQuarter(form.getQuarter());
		
		return filter;
	}

	@Override
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs)
			throws MapperException {
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		OBFilter filter= (OBFilter)obj;
		GenerateLADForm form=new GenerateLADForm();
		form.setReportId(filter.getReportId());
		form.setPartyId(filter.getParty());
		form.setSegment(filter.getSegment());
		form.setStatus(filter.getStatus());
		form.setBranchId(filter.getBranchId());
		form.setDocumentType(filter.getDocumentType());
		form.setRelatoionship(filter.getRelatoionship());
		form.setGuarantor(filter.getGuarantor());
		form.setUserId(filter.getUserId());
		form.setDepartmentId(filter.getDepartmentId());
		form.setIndustry(filter.getIndustry());
		form.setRelationManager(filter.getRelationManager());
		form.setRbiAsset(filter.getRbiAsset());
		form.setRmRegion(filter.getRmRegion());
		form.setRelationshipMgr(filter.getRelationshipMgr());
		form.setCaseId(filter.getCaseId());
		form.setModuleEvent(filter.getModuleEvent());
		form.setTatCriteria(filter.getTatCriteria());
		form.setCategory(filter.getCategory());
		form.setProfile(filter.getProfile());
		
		form.setSearchCustomerName(filter.getSearchCustomerName());
		form.setRelationshipMgr(filter.getRelationshipMgr());
		form.setDueMonth(filter.getDueMonth());
		form.setDueMonth(filter.getDueYear());
		
		form.setRmRegion(filter.getRmRegion());
		form.setRelationshipMgr(filter.getRelationshipMgr());
		form.setCaseId(filter.getCaseId());
		form.setModuleEvent(filter.getModuleEvent());
		form.setTatCriteria(filter.getTatCriteria());
		form.setCategory(filter.getCategory());
		form.setProfile(filter.getProfile());
		
		form.setFilterPartyMode(filter.getFilterPartyMode());
		form.setFilterUserMode(filter.getFilterUserMode());
		
		if(filter.getFromDate()!=null)
			form.setFromDate(filter.getFromDate());
		if(filter.getToDate()!=null)
			form.setToDate(filter.getToDate());
		return form;
	}

}
