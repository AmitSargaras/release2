package com.integrosys.cms.ui.baselmaster;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.baselmaster.bus.IBaselMaster;
import com.integrosys.cms.app.baselmaster.bus.OBBaselMaster;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.component.MaintainComponentForm;

public class BaselMapper extends AbstractCommonMapper{

	DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
	public Object mapFormToOB(CommonForm cForm, HashMap map)
			throws MapperException {
		MaintainBaselForm form=(MaintainBaselForm)cForm;
		Locale locale=(Locale)map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		IBaselMaster obj=null;
		try{
			obj=new OBBaselMaster();
			obj.setBaselValidation(form.getBaselValidation());
			obj.setDeprecated(form.getDeprecated());
			obj.setExposureSource(form.getExposureSource());
			if(null!=form.getId() && "".equals(form.getId())){
				obj.setId(Long.parseLong(form.getId()));
			}
			obj.setReportHandOff(form.getReportHandOff());
			obj.setStatus(form.getStatus());
			obj.setSystem(form.getSystem());
			obj.setSystemValue(form.getSystemValue());
			if(null!=form.getVersionTime() && "".equals(form.getVersionTime())){
				obj.setVersionTime(Long.parseLong(form.getVersionTime()));
			}
		}
		catch(Exception ex){
			throw new MapperException("failed to map Form To Ob of Basel Master", ex);
		}
		return obj;
	}

	
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs)	throws MapperException {
		MaintainBaselForm form = (MaintainBaselForm) cForm;
		IBaselMaster item = (IBaselMaster) obj;

			form.setVersionTime(Long.toString(item.getVersionTime()));
			form.setDeprecated(item.getDeprecated());
			form.setId(Long.toString(item.getId()));
			form.setStatus(item.getStatus());
			form.setBaselValidation(item.getBaselValidation());
			form.setSystem(item.getSystem());
			form.setSystemValue(item.getSystemValue());
			form.setExposureSource(item.getExposureSource());
			form.setReportHandOff(item.getReportHandOff());
			form.setCreatedBy(item.getCreatedBy());
			if(item.getCreatedOn()!=null){
				form.setCreatedOn(df.format(item.getCreatedOn()));
			}
			form.setLastUpdatedBy(item.getLastUpdatedBy());
			if(item.getLastUpdatedOn()!=null){
				form.setLastUpdatedOn(df.format(item.getLastUpdatedOn()));
			}
			
			

			return form;
			}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "baselObj", "com.integrosys.cms.app.baselmaster.bus.OBBaselMaster", SERVICE_SCOPE }, });
	}
}
