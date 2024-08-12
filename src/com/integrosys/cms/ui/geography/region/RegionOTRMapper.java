package com.integrosys.cms.ui.geography.region;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.OBRegion;

public class RegionOTRMapper extends AbstractCommonMapper{

	public Object mapFormToOB(CommonForm cForm, HashMap inputs)throws MapperException {

		DefaultLogger.debug(this, " -- CountryMapper-- Entering method mapFormToOB");
		RegionOTRForm form = (RegionOTRForm) cForm;		

		IRegion region = new OBRegion();
		
		/*Dilshad Ahmad*/
		region.setCpsId(form.getCpsId());
		region.setOperationName(form.getOperationName());
		/*Dilshad End*/
		region.setRegionCode(form.getRegionCode());
		if(form.getRegionName()!=null){
		region.setRegionName(form.getRegionName().trim());	
		}
		region.setStatus(form.getStatus());
		region.setDeprecated(form.getDeprecated());		

		if ( form.getId() !=  "" && form.getId() != null) {
			region.setIdRegion(Long.parseLong(form.getId()));
		}

		region.setCountryId(new OBCountry());
		
		region.setFileUpload(form.getFileUpload());

		if( form.getCountryId() != null && form.getCountryId() != "" )
			region.getCountryId().setIdCountry(Long.parseLong(form.getCountryId()));
		
		return region;	
	}


	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs)	throws MapperException {
		DefaultLogger.debug(this, " -- RegionMapper-- Entering method mapOBToForm");
		RegionOTRForm form = (RegionOTRForm) cForm;
		IRegion region = (OBRegion)obj;

		if(region.getCountryId().getIdCountry()!=0){
			form.setCountryId(Long.toString(region.getCountryId().getIdCountry()));
		}

		form.setRegionCode(region.getRegionCode());
		form.setRegionName(region.getRegionName());				
		form.setId(String.valueOf(region.getIdRegion()));
		form.setDeprecated(region.getDeprecated());				
		form.setStatus(region.getStatus());
		form.setCpsId(String.valueOf(region.getIdRegion()));
		return form;		
	}
}

