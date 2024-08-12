package com.integrosys.cms.ui.newtatmaster;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.newtatmaster.bus.INewTatMaster;
import com.integrosys.cms.app.newtatmaster.bus.OBNewTatMaster;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class NewTatMasterMapper extends AbstractCommonMapper {
	
	
	
	
	Date date = new Date();
	DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
	

	
	public Object mapFormToOB(CommonForm cForm, HashMap map)
			throws MapperException {
		MaintainNewTatForm form =(MaintainNewTatForm)cForm;
		Locale locale=(Locale)map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		//DateUtil.convertDate(form.getLastUpdatedOn());
		INewTatMaster obj= null;
		try{
			obj=new OBNewTatMaster();
			if(!cForm.getEvent().equals("maker_conform_edit_tat_event")){
			obj.setCreatedBy(form.getCreatedBy());
			if(form.getCreatedOn()!=null){
			obj.setCreatedOn(format.parse(form.getCreatedOn()));
			}
			}
			obj.setDeprecated(form.getDeprecated());
			obj.setEndTime(form.getEndTime());
			obj.setId(Long.parseLong(form.getId()));
			if(!cForm.getEvent().equals("maker_conform_edit_tat_event")){
			obj.setLastUpdatedBy(form.getLastUpdatedBy());
			if(form.getLastUpdatedOn()!=null){
			obj.setLastUpdatedOn(DateUtil.convertDate(form.getLastUpdatedOn()));
			}
			}
			obj.setStartTime(form.getStartTime());
			obj.setUserEvent(form.getUserEvent());
			obj.setStatus(form.getStatus());
			obj.setEventCode(form.getEventCode());
			obj.setVersionTime(Long.parseLong(form.getVersionTime()));
			
			obj.setTimingHours(form.getTiming().substring(0,2));
			obj.setTimingMin(form.getTiming().substring(3,5));
			
			
			return obj;
		}
		catch (Exception ex) {
			throw new MapperException("failed to map Form To Ob of Tat Master", ex);
		}
		
		
		
	}

	
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs)
			throws MapperException {
		
		MaintainNewTatForm form =(MaintainNewTatForm)cForm;
		
		if("maker_conform_edit_tat_event_error".equals(form.getEvent())){
			return form;
		}
		
		INewTatMaster item=(INewTatMaster)obj;
		
		form.setCreatedBy(item.getCreatedBy());
		if(item.getCreatedOn()!=null){
		form.setCreatedOn(df.format(item.getCreatedOn()));
		}
		form.setEndTime(item.getEndTime());
		form.setLastUpdatedBy(item.getLastUpdatedBy());
		if(item.getLastUpdatedOn()!=null){
		form.setLastUpdatedOn(df.format(item.getLastUpdatedOn()));
		}
		form.setEventCode(item.getEventCode());
		form.setUserEvent(item.getUserEvent());
		form.setStartTime(item.getStartTime());
		form.setTiming(item.getTimingHours()+":"+item.getTimingMin());
		form.setId(String.valueOf(item.getId()));
		form.setVersionTime(String.valueOf(item.getVersionTime()));
		return form;
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "tatEventObj", "com.integrosys.cms.app.newtatmaster.bus.OBNewTatMaster", SERVICE_SCOPE }, });
	}

}
