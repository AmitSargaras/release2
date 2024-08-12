package com.integrosys.cms.ui.limit.facility.officer;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.limit.bus.IFacilityOfficer;
import com.integrosys.cms.app.limit.bus.OBFacilityOfficer;
import com.integrosys.cms.ui.limit.CategoryCodeConstant;

public class OfficerMapper extends AbstractCommonMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		OfficerForm form = (OfficerForm) cForm;
		IFacilityOfficer facilityOfficerObj = new OBFacilityOfficer();
		facilityOfficerObj.setRelationshipCodeCategoryCode(CategoryCodeConstant.OFFICER_RELATIONSHIP);
		facilityOfficerObj.setRelationshipCodeEntryCode(form.getRelationshipCodeEntryCode());
		facilityOfficerObj.setOfficerTypeCategoryCode(CategoryCodeConstant.OFFICER_TYPE);
		facilityOfficerObj.setOfficerTypeEntryCode(form.getOfficerTypeEntryCode());
		facilityOfficerObj.setOfficerCategoryCode(CategoryCodeConstant.OFFICER);
		facilityOfficerObj.setOfficerEntryCode(form.getOfficerEntryCode());
		
		if (StringUtils.isEmpty(form.getSequenceNumber())) { 
			facilityOfficerObj.setHostSequenceNumber(null);
		} else {
			facilityOfficerObj.setHostSequenceNumber(Long.valueOf(form.getSequenceNumber()));
		}
		
		return facilityOfficerObj;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		OfficerForm form = (OfficerForm) cForm;
		IFacilityOfficer facilityOfficer = (IFacilityOfficer) obj;
		form.setRelationshipCodeEntryCode(facilityOfficer.getRelationshipCodeEntryCode());
		form.setSequenceNumber(String.valueOf(facilityOfficer.getHostSequenceNumber()));
		form.setOfficerTypeEntryCode(facilityOfficer.getOfficerTypeEntryCode());
		form.setOfficerEntryCode(facilityOfficer.getOfficerEntryCode());
		return form;
	}
}
