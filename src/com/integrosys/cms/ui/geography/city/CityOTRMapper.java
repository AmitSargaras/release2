package com.integrosys.cms.ui.geography.city;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.state.bus.OBState;

public class CityOTRMapper extends AbstractCommonMapper{

	@Override
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		DefaultLogger.debug(this,
				" -- CityMapper-- Entering method mapFormToOB");
		CityOTRForm form =(CityOTRForm) cForm;

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
		
		
		city.getStateId().setRegionId(new OBRegion());
		
		
		city.getStateId().getRegionId().setCountryId(new OBCountry());
		
		city.setOperationName(form.getOperationName());
				
		if (form.getCpsId() != null) {
			city.setCpsId(form.getCpsId().trim());
		}
		
		return city;

	}

	@Override
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		DefaultLogger.debug(this,
				" -- CityMapper-- Entering method mapOBToForm");
		CityOTRForm form = (CityOTRForm) cForm;
		ICity city = (OBCity) obj;

		if (city.getStateId().getIdState() != 0) {			
			form.setStateId(Long.toString(city.getStateId().getIdState()));
		}	

		form.setCityCode(city.getCityCode());
		form.setCityName(city.getCityName());
		form.setId(String.valueOf(city.getIdCity()));
		form.setDeprecated(city.getDeprecated());
		form.setStatus(city.getStatus());
		form.setCpsId(city.getCpsId());
		return form;
	}

}
