/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemMapper.java,v 1.8 2005/10/27 05:41:20 jtan Exp $
 */
package com.integrosys.cms.ui.systemBank;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.state.bus.OBState;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.bus.OBSystemBank;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 *@author abhijit.rudrakshawar
 *Mapper for System Bank Master
 */

public class SystemBankMapper extends AbstractCommonMapper {
	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		DefaultLogger.debug(this, "Entering method mapFormToOB");
		MaintainSystemBankForm form = (MaintainSystemBankForm) cForm;
		
		ISystemBank obItem = null;
		try {
			
				obItem = new OBSystemBank();
				if(!(form.getAddress()==null||form.getAddress().trim().equalsIgnoreCase(""))){
				obItem.setAddress(form.getAddress());
				}
				
				if(!(form.getContactMail()==null||form.getContactMail().trim().equalsIgnoreCase(""))){
				obItem.setContactMail(form.getContactMail());
				}
				if(!(form.getContactNumber()==null||form.getContactNumber().trim().equalsIgnoreCase(""))){
				obItem.setContactNumber(Long.parseLong(form.getContactNumber()));
				}
				if(!(form.getFaxNumber()==null||form.getFaxNumber().trim().equalsIgnoreCase(""))){
				obItem.setFaxNumber(Long.parseLong(form.getFaxNumber()));
				}
				if( form.getCountry() != null && !form.getCountry().trim().equals("") ){
					obItem.setCountry(new OBCountry());
					obItem.getCountry().setIdCountry(Long.parseLong(form.getCountry()));
				}
				
				if( form.getRegion() != null && !form.getRegion().trim().equals("") ){
					obItem.setRegion(new OBRegion());
					obItem.getRegion().setIdRegion(Long.parseLong(form.getRegion()));
				}
				
				if( form.getState() != null && !form.getState().trim().equals("") ){
					obItem.setState(new OBState());
					obItem.getState().setIdState(Long.parseLong(form.getState()));
				}

				
				if( form.getCityTown() != null && !form.getCityTown().trim().equals("") ){
					obItem.setCityTown(new OBCity());
					obItem.getCityTown().setIdCity(Long.parseLong(form.getCityTown()));
				}
				obItem.setDeprecated(form.getDeprecated());
				if(form.getId()==null){
				long a=1l;
					obItem.setId(a);
					
				}else{
				obItem.setId(Long.parseLong(form.getId()));
				}
				obItem.setCreateBy(form.getCreateBy());
				obItem.setLastUpdateBy(form.getLastUpdateBy());
				obItem.setLastUpdateDate(df.parse(form.getLastUpdateDate()));
				obItem.setCreationDate(df.parse(form.getCreationDate()));
				obItem.setVersionTime(0l);
				obItem.setStatus(form.getStatus());
				obItem.setSystemBankCode(form.getSystemBankCode());
				obItem.setSystemBankName(form.getSystemBankName());
				obItem.setMasterId(1l);
				
				
			return obItem;

		}
		catch (Exception ex) {
			throw new MapperException("failed to map form to ob of systemBank item", ex);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		MaintainSystemBankForm form = (MaintainSystemBankForm) cForm;
		ISystemBank item = (ISystemBank) obj;

		form.setCreateBy(item.getCreateBy());
		form.setLastUpdateBy(item.getLastUpdateBy());
		form.setLastUpdateDate(df.format(item.getLastUpdateDate()));
		form.setCreationDate(df.format(item.getCreationDate()));
		form.setVersionTime(Long.toString(item.getVersionTime()));
		if(!(item.getAddress()==null||item.getAddress().trim().equalsIgnoreCase(""))){
		form.setAddress(item.getAddress());
		}
		
		if(!(item.getContactMail()==null||item.getContactMail().trim().equalsIgnoreCase(""))){
		form.setContactMail(item.getContactMail());
		}
		if(!(item.getContactNumber()==0)){
		form.setContactNumber(Long.toString(item.getContactNumber()));
		}
		if(!(item.getFaxNumber()==0)){
		form.setFaxNumber(Long.toString(item.getFaxNumber()));
		}

		form.setDeprecated(item.getDeprecated());
	
		
		
		if(item.getCountry()!=null){
		if(item.getCountry().getIdCountry()!=0){
			form.setCountry(Long.toString(item.getCountry().getIdCountry()));
			
		}
		}
		if(item.getRegion()!=null){
		if(item.getRegion().getIdRegion()!=0){
			form.setRegion(Long.toString(item.getRegion().getIdRegion()));
		
		}
		}
		if(item.getState()!=null){
		if(item.getState().getIdState()!=0){
			form.setState(Long.toString(item.getState().getIdState()));
	
		}
		}
		if(item.getCityTown()!=null){
		if(item.getCityTown().getIdCity()!=0){
			form.setCityTown(Long.toString(item.getCityTown().getIdCity()));
	
		}
		}
		form.setId(Long.toString(item.getId()));
		form.setStatus(item.getStatus());
		form.setSystemBankCode(item.getSystemBankCode());
		form.setSystemBankName(item.getSystemBankName());
		
		
		

		

		return form;
	}

	/**
	 * declares the key-value pair upfront for objects that needs to be accessed
	 * in scope
	 * @return 2D-array key value pair
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "systemBankObj", "com.integrosys.cms.app.systemBank.bus.OBSystemBank", SERVICE_SCOPE }, });
	}
}
