package com.integrosys.cms.ui.poi.report;


import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.poi.report.OBFilter;

public class ReportMapper extends AbstractCommonMapper {

	@Override
	public Object mapFormToOB(CommonForm cForm, HashMap inputs)
			throws MapperException {
		
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ReportForm form=(ReportForm)cForm;
		OBFilter filter= new OBFilter();
		filter.setReportId(form.getReportId());
		filter.setParty(form.getPartyId());
		filter.setSegment(form.getSegment());
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
		filter.setFacility(form.getFacility());
		
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
		//Added by Uma Khot: Start:For Monthly Basel Report 08/09/2015
		filter.setSecurityType(form.getSecurityType());
		//Added by Uma Khot: End:For Monthly Basel Report 08/09/2015

			
		if(form.getFromDate()!=null)
			filter.setFromDate(form.getFromDate());
		if(form.getToDate()!=null)
			filter.setToDate(form.getToDate());
		
		if(form.getScodFromDate()!=null)
			filter.setScodFromDate(form.getScodFromDate());
		if(form.getScodToDate()!=null)
			filter.setScodToDate(form.getScodToDate());
		if(form.getEscodFromDate()!=null)
			filter.setEscodFromDate(form.getEscodFromDate());
		if(form.getEscodToDate()!=null)
			filter.setEscodToDate(form.getEscodToDate());		
		
		//Uma:For Cam Quarter Activity CR
		filter.setQuarter(form.getQuarter());
		//added by santosh For uBS CR
		filter.setRecordType(form.getRecordType());
		//end santosh
		
		//Added by Prachit For Cersai Report
		filter.setTypeOfSecurity(form.getTypeOfSecurity());
		filter.setBankingMethod(form.getBankingMethod());
		filter.setSecurityId(form.getSecurityId());
		filter.setReportFormat(form.getReportFormat());
		filter.setSearchCustomerName(form.getSearchCustomerName());
		filter.setPartyId(form.getPartyId());
		//end Prachit
		//Added by Prachit For Mortgage Report
		filter.setSecurityStatus(form.getSecurityStatus());
		//end
		filter.setSecuritySubType(form.getSecuritySubType());
		filter.setSecurityType1(form.getSecurityType1());


		filter.setIsExceptionalUser(form.getIsExceptionalUser());
		filter.setEventOrCriteria(form.getEventOrCriteria());
		filter.setMonthsOfAuditTrail(form.getMonthsOfAuditTrail());
		filter.setSelectYearDropdown(form.getSelectYearDropdown());
		filter.setEodSyncUpDate(form.getEodSyncUpDate());
		
		return filter;
	}

	@Override
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs)
			throws MapperException {
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		OBFilter filter= (OBFilter)obj;
		ReportForm form=new ReportForm();
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
		form.setFacility(filter.getFacility());
		
		form.setRmRegion(filter.getRmRegion());
		form.setRelationshipMgr(filter.getRelationshipMgr());
		form.setCaseId(filter.getCaseId());
		form.setModuleEvent(filter.getModuleEvent());
		form.setTatCriteria(filter.getTatCriteria());
		form.setCategory(filter.getCategory());
		form.setProfile(filter.getProfile());
		
		form.setFilterPartyMode(filter.getFilterPartyMode());
		form.setFilterUserMode(filter.getFilterUserMode());
		//added by santosh For uBS CR
		form.setRecordType(filter.getRecordType());
		//end santosh
		//Added by Prachit For Cersai Report
		form.setTypeOfSecurity(filter.getTypeOfSecurity());
		form.setBankingMethod(filter.getBankingMethod());
		form.setSecurityId(filter.getSecurityId());
		form.setReportFormat(filter.getReportFormat());
		form.setSearchCustomerName(filter.getSearchCustomerName());
		form.setPartyId(filter.getPartyId());
		//end Prachit
		//Added by Prachit For Mortgage Report
		form.setSecurityStatus(filter.getSecurityStatus());
		//end 
		
		if(filter.getFromDate()!=null)
			form.setFromDate(filter.getFromDate());
		if(filter.getToDate()!=null)
			form.setToDate(filter.getToDate());
		form.setSecuritySubType(filter.getSecuritySubType());
		form.setSecurityType1(filter.getSecurityType1());
		
		form.setUploadSystem(filter.getUploadSystem());		
		form.setEventOrCriteria(filter.getEventOrCriteria());
		form.setMonthsOfAuditTrail(filter.getMonthsOfAuditTrail());
		form.setIsExceptionalUser(filter.getIsExceptionalUser());
		form.setSelectYearDropdown(filter.getSelectYearDropdown());
		form.setEodSyncUpDate("");
		return form;
	}

}
