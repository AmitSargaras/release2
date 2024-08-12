package com.integrosys.cms.ui.geography.country;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.bus.OBCountry;

public class CountryMapper extends AbstractCommonMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs)
			throws MapperException {

		DefaultLogger.debug(this,
				" -- CountryMapper-- Entering method mapFormToOB");
		CountryForm form = (CountryForm) cForm;
      
		ICountry country = new OBCountry();
		country.setCountryCode(form.getCountryCode());
		if(form.getCountryName()!=null){
		country.setCountryName(form.getCountryName().trim());
		}
		country.setStatus(form.getStatus());
		country.setDeprecated(form.getDeprecated());
		if (form.getId() != "" && form.getId() != null) {
			country.setIdCountry(Long.parseLong(form.getId()));
		}
		
		country.setFileUpload(form.getFileUpload());
		country.setCpsId(form.getCpsId());
		country.setOperationName(form.getOperationName());
		return country;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs)
			throws MapperException {
		DefaultLogger.debug(this,
				" -- CountryMapper-- Entering method mapOBToForm");
		CountryForm form = (CountryForm) cForm;
		ICountry country = (OBCountry) obj;

		form.setCountryCode(country.getCountryCode());
		form.setCountryName(country.getCountryName());
		form.setId(String.valueOf(country.getIdCountry()));
		form.setDeprecated(country.getDeprecated());
		form.setStatus(country.getStatus());
		return form;
	} 
}
