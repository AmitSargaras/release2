package com.integrosys.cms.ui.geography.state;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.bus.OBState;

public class StateOTRMapper extends AbstractCommonMapper{

	public Object mapFormToOB(CommonForm cForm, HashMap inputs)throws MapperException {

		DefaultLogger.debug(this, " -- StateOTRMapper-- Entering method mapFormToOB");
		StateOTRForm form = (StateOTRForm) cForm;		
		IState state = new OBState();
		
		
		if(form.getCpsId()!=null){
			state.setCpsId(form.getCpsId().trim());
			}
		
		if(form.getOperationName()!=null){
			state.setOperationName(form.getOperationName().trim());
			}
		
		if ( form.getId() !=  "" && form.getId() != null) {
			state.setIdState(Long.parseLong(form.getId()));
		}		
		state.setStateCode(form.getStateCode());
		if(form.getStateName()!=null){
		state.setStateName(form.getStateName().trim());
		}
		System.out.println("StateOTRMapper ############################ form.getCpsId()"+form.getCpsId());
		if(form.getCpsId()!=null){
			state.setCpsId(form.getCpsId().trim());
			}
		state.setStatus(form.getStatus());
		state.setDeprecated(form.getDeprecated());
	
		state.setRegionId(new OBRegion());
		if( form.getRegionId() != null && form.getRegionId() != "" ) {
			state.getRegionId().setIdRegion(Long.parseLong(form.getRegionId()));		
		}
		state.getRegionId().setCountryId(new OBCountry());
		if( form.getCountryId() != null && form.getCountryId() != "" ) {
			state.getRegionId().getCountryId().setIdCountry(Long.parseLong(form.getCountryId()));
		}

		return state;	
	}
   

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs)	throws MapperException {
		DefaultLogger.debug(this, " -- StateMapper-- Entering method mapOBToForm");
		System.out.println("####### -- StateMapper-- Entering method mapOBToForm");
		StateOTRForm form = (StateOTRForm) cForm;
		IState state = (OBState)obj;
	
		if(state.getRegionId().getIdRegion()!=0){
		//	form.setRegionOBId(Long.toString(state.getRegionId().getIdRegion()));
			form.setRegionId(Long.toString(state.getRegionId().getIdRegion()));
		}
		
		if(state.getRegionId().getCountryId().getIdCountry()!=0){
			//form.setCountryOBId(Long.toString(state.getRegionId().getCountryId().getIdCountry()));
			form.setCountryId(Long.toString(state.getRegionId().getCountryId().getIdCountry()));
		}
		
		form.setStateCode(state.getStateCode());
		form.setStateName(state.getStateName());				
		form.setId(String.valueOf(state.getIdState()));
		form.setDeprecated(state.getDeprecated());				
		form.setStatus(state.getStatus());	
		System.out.println("StateOTRMapper ############################ form.getCpsId()"+state.getCpsId());
		form.setCpsId(state.getCpsId());
		form.setOperationName(state.getOperationName());
		
		return form;		
	}
}
