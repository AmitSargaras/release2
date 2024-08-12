package com.integrosys.cms.ui.geography.city;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.state.bus.OBState;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class CityMapper extends AbstractCommonMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs)
			throws MapperException {

		DefaultLogger.debug(this,
				" -- CityMapper-- Entering method mapFormToOB");
		CityForm form = (CityForm) cForm;

		ICity city = new OBCity();

		if (form.getId() != "" && form.getId() != null) {
			city.setIdCity(Long.parseLong(form.getId()));
		}

		city.setCityCode(form.getCityCode());
		if(form.getCityName()!=null){
		city.setCityName(form.getCityName().trim());
		}
		city.setStatus(form.getStatus());
		city.setDeprecated(form.getDeprecated());

		city.setStateId(new OBState());
		if (form.getStateId() != null && form.getStateId() != "")
			city.getStateId().setIdState(Long.parseLong(form.getStateId()));
		else {
			if (form.getStateOBId() != null && form.getStateOBId() != "")
				city.getStateId().setIdState(Long.parseLong(form.getStateOBId()));
		}
		
		city.getStateId().setRegionId(new OBRegion());
		if (form.getRegionId() != null && form.getRegionId() != "")
			city.getStateId().getRegionId().setIdRegion(Long.parseLong(form.getRegionId()));
		else{
			if( form.getRegionOBId() != null && form.getRegionOBId() != "" )
				city.getStateId().getRegionId().setIdRegion(Long.parseLong(form.getRegionOBId()));
		}
		
		city.getStateId().getRegionId().setCountryId(new OBCountry());
		if (form.getCountryId() != null && form.getCountryId() != "")
			city.getStateId().getRegionId().getCountryId().setIdCountry(Long.parseLong(form.getCountryId()));
		else {
			if( form.getCountryOBId() != null && form.getCountryOBId() != "" )
				city.getStateId().getRegionId().getCountryId().setIdCountry(Long.parseLong(form.getCountryOBId()));
		}
		
		if(StringUtils.isNotBlank(form.getEcbfCityId()) && StringUtils.isNumeric(form.getEcbfCityId())) {
			city.setEcbfCityId(Long.valueOf(form.getEcbfCityId()));
		}
		
		city.setFileUpload(form.getFileUpload());

		return city;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs)
			throws MapperException {
		DefaultLogger.debug(this,
				" -- CityMapper-- Entering method mapOBToForm");
		CityForm form = (CityForm) cForm;
		ICity city = (OBCity) obj;

		if (city.getStateId().getIdState() != 0) {
			form.setStateOBId(Long.toString(city.getStateId().getIdState()));
			form.setStateId(Long.toString(city.getStateId().getIdState()));
		}

		if (city.getStateId().getRegionId().getIdRegion() != 0) {
			form.setRegionOBId(Long.toString(city.getStateId().getRegionId()
					.getIdRegion()));
			form.setRegionId(Long.toString(city.getStateId().getRegionId()
					.getIdRegion()));
		}

		if (city.getStateId().getRegionId().getCountryId().getIdCountry() != 0) {
			form.setCountryOBId(Long.toString(city.getStateId().getRegionId()
					.getCountryId().getIdCountry()));
			form.setCountryId(Long.toString(city.getStateId().getRegionId()
					.getCountryId().getIdCountry()));
		}
		
		if(city.getEcbfCityId() !=null && city.getEcbfCityId() > 0) {
			form.setEcbfCityId(String.valueOf(city.getEcbfCityId()));
		}

		form.setCityCode(city.getCityCode());
		form.setCityName(city.getCityName());
		form.setId(String.valueOf(city.getIdCity()));
		form.setDeprecated(city.getDeprecated());
		form.setStatus(city.getStatus());
		return form;
	}
}
