package com.integrosys.cms.ui.pincodemapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.geography.state.bus.OBState;
import com.integrosys.cms.app.pincodemapping.bus.IPincodeMapping;
import com.integrosys.cms.app.pincodemapping.bus.OBPincodeMapping;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class PincodeMapper extends AbstractCommonMapper {

	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		
			DefaultLogger.debug(this, "Entering method mapFormToOB");
			PincodeMappingForm form = (PincodeMappingForm) cForm;

			com.integrosys.cms.app.pincodemapping.bus.IPincodeMapping obItem = null;
			try {

				obItem = new OBPincodeMapping();
				if(form.getPincode()!=null && !form.getPincode().trim().equals("")){
					obItem.setPincode(Long.parseLong(form.getPincode()));
				}
				/*if(form.getStateCode()!=null && !form.getStateCode().trim().equals("")){
					obItem.setStateCode(form.getStateCode());
				}*/
				
				if(obItem.getStateId()==null){
					if(form.getStateId()!=null && !form.getStateId().equals("")){
						obItem.setStateId(new OBState());
						obItem.getStateId().setIdState(Long.parseLong(form.getStateId()));
					}
				}
				
				obItem.setDeprecated(form.getDeprecated());

				if (form.getId() != null && (!form.getId().equals(""))) {
					obItem.setId(Long.parseLong(form.getId()));
				}
				obItem.setCreatedBy(form.getCreatedBy()==null?"":form.getCreatedBy());
				
				obItem.setLastUpdateBy(form.getLastUpdateBy()==null?"":form.getLastUpdateBy());
				
				if (form.getLastUpdateDate() != null
						&& (!form.getLastUpdateDate().equals(""))) {
					obItem.setLastUpdateDate(df.parse(form.getLastUpdateDate()));
				} else {
					obItem.setLastUpdateDate(new Date());
				}
				if (form.getCreationDate() != null
						&& (!form.getCreationDate().equals(""))) {
					obItem.setCreationDate(df.parse(form.getCreationDate()));
				} else {
					obItem.setCreationDate(new Date());
				}
				
				obItem.setVersionTime(0l);
				obItem.setStatus(form.getStatus());
				obItem.setOperationName(form.getOperationName());
				obItem.setCpsId(form.getCpsId());
				
				return obItem;

			} catch (Exception ex) {
				throw new MapperException(
						"failed to map form to ob of pincode item", ex);
			}
	}

	@Override
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		Locale locale = (Locale) inputs
				.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		PincodeMappingForm form = (PincodeMappingForm) cForm;
		IPincodeMapping item = (IPincodeMapping) obj;
		String state="";
		
		form.setCreatedBy(item.getCreatedBy());
		form.setLastUpdateBy(item.getLastUpdateBy());
		form.setLastUpdateDate(df.format(item.getLastUpdateDate()));
		form.setCreationDate(df.format(item.getCreationDate()));
		form.setVersionTime(Long.toString(item.getVersionTime()));
		
		/*try {
			IStateDAO  stateDAO=(IStateDAO) BeanHouse.get("stateDAO");
			IState state2=stateDAO.getStateById(item.getStateId());
			form.setStateName(state2.getStateName());
		}catch(Exception e){
			e.printStackTrace();
			DefaultLogger.error(this, "Cannot get StateName", e);
		}*/
		
		/*form.setStateCode(item.getStateCode());*/
		form.setPincode(Long.toString(item.getPincode()));
		
		if(item.getStateId()!=null){
			form.setStateId(Long.toString(item.getStateId().getIdState()));
			form.setStateName(item.getStateId().getStateName());
		}
		
		/*form.setStateId(Long.toString(item.getStateId()));*/
		form.setDeprecated(item.getDeprecated());
		form.setId(Long.toString(item.getId()));
		
		form.setStatus(item.getStatus());

		return form;
	}
	/**
	 * declares the key-value pair upfront for objects that needs to be accessed
	 * in scope
	 * 
	 * @return 2D-array key value pair
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM,
						"com.integrosys.component.bizstructure.app.bus.ITeam",
						GLOBAL_SCOPE },
				{ IGlobalConstant.USER,
						"com.integrosys.component.user.app.bus.ICommonUser",
						GLOBAL_SCOPE },
				{ "pincodeMappingObj",
						"com.integrosys.cms.app.pincodemapping.bus.OBPincodeMapping",
						SERVICE_SCOPE }, });
	}

}
