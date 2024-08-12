package com.integrosys.cms.ui.geography;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.geography.bus.IGeography;
import com.integrosys.cms.app.geography.bus.OBGeography;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class GeographyMapper extends AbstractCommonMapper{

	public Object mapFormToOB(CommonForm cForm, HashMap inputs)	throws MapperException {


		DefaultLogger.debug(this, "Entering method mapFormToOB");
		GeographyForm form = (GeographyForm) cForm;		
		
		IGeography geography = new OBGeography();
		geography.setCodeDesc(form.getCodeDesc());
		geography.setCodeName(form.getCodeName());
		geography.setParentId(form.getParentId());
		geography.setGeographyId(form.getGeographyId());
		geography.setRegionId(form.getRegionId());
		geography.setStatus(form.getStatus());
		geography.setMasterId(Long.parseLong(form.getMasterId()));
		
		if ( form.getId() !=  "" && form.getId() != null) {
			geography.setId(Long.parseLong(form.getId()));
		}
				
		if( form.getVersionTime() != "" && form.getVersionTime() != null)
			geography.setVersionTime(Long.parseLong(form.getVersionTime()));
		
			
//			geography.setUpdatedBy(form.getUpdateBy());
//			geography.setUpdateDate(DateUtil.getDate());
		
		return geography;
	
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs)throws MapperException {
		DefaultLogger.debug(this, "Entering method mapOBToForm");
		GeographyForm form = (GeographyForm) cForm;
		IGeography geography = (OBGeography)obj;
		
		form.setCodeDesc(geography.getCodeDesc());
		form.setCodeName(geography.getCodeName());
		form.setParentId(geography.getParentId());
		form.setGeographyId(geography.getGeographyId());
		form.setRegionId(geography.getRegionId());
				
		if ( form.getId() !=  "" && form.getId() != null) {
			form.setId(String.valueOf(geography.getId()));
		}
		
		form.setStatus(geography.getStatus());
		form.setCreateBy(geography.getCreateBy());
		form.setCreationDate(String.valueOf(geography.getCreationDate()));		
		form.setUpdateBy(geography.getUpdateBy());
		form.setUpdateDate(String.valueOf(geography.getUpdateDate()));		
		form.setVersionTime(String.valueOf(geography.getVersionTime()));
		form.setMasterId(String.valueOf(geography.getMasterId()));
		
		return form;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "formbj", "com.integrosys.cms.app.geography.bus.OBGeography", SERVICE_SCOPE }, });
	}

}
