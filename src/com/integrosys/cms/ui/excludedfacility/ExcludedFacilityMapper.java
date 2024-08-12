package com.integrosys.cms.ui.excludedfacility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.excludedfacility.bus.IExcludedFacility;
import com.integrosys.cms.app.excludedfacility.bus.OBExcludedFacility;
import com.integrosys.cms.app.facilityNewMaster.bus.OBFacilityNewMaster;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class ExcludedFacilityMapper extends AbstractCommonMapper{

	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	
	public Object mapFormToOB(CommonForm cForm, HashMap map)
			throws MapperException {
		DefaultLogger.debug(this, "Entering method mapFormToOB");
		ExcludedFacilityForm form=(ExcludedFacilityForm)cForm;
		Locale locale=(Locale)map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		IExcludedFacility obItem=null;
		
		try {
			
			obItem = new OBExcludedFacility();
			/*if(form.getExcludedfacilityCategory()!=null && (!form.getExcludedfacilityCategory().trim().equals("")))
			{
				if(form.getActualExcludedfacilityCategoryCode()!=null && (!form.getActualExcludedfacilityCategoryCode().trim().equals(""))){
					obItem.setExcludedFacilityCategory(form.getActualExcludedfacilityCategoryCode());
				}else{
					obItem.setExcludedFacilityCategory(form.getExcludedfacilityCategory());
				}
				
			}*/
			
			if(form.getExcludedfacilityDescription()!=null && (!form.getExcludedfacilityDescription().trim().equals("")))
			{
				obItem.setExcludedFacilityDescription(form.getExcludedfacilityDescription());
			}
			
			if(form.getDeprecated()!=null && (!form.getDeprecated().trim().equals(""))){
			obItem.setDeprecated(form.getDeprecated());
			}else{
				obItem.setDeprecated("N");
			}
			if(form.getId()!=null && (!form.getId().trim().equals("")))
            {
				obItem.setId(Long.parseLong(form.getId()));
            }
			obItem.setCreateBy(form.getCreateBy());
			obItem.setLastUpdateBy(form.getLastUpdateBy());
			if(form.getLastUpdateDate()!=null && (!form.getLastUpdateDate().equals("")))
            {
			obItem.setLastUpdateDate(df.parse(form.getLastUpdateDate()));
            }else{
            	obItem.setLastUpdateDate(new Date());
            }
			if(form.getCreationDate()!=null && (!form.getCreationDate().equals("")))
            {
			obItem.setCreationDate(df.parse(form.getCreationDate()));
            }else{
            	obItem.setCreationDate(new Date());
            }
			obItem.setVersionTime(0l);
			if( form.getStatus() != null && ! form.getStatus().equals(""))
				obItem.setStatus(form.getStatus());
			else
				obItem.setStatus("ACTIVE");
			
			if(form.getOperationName()!=null && (!form.getOperationName().trim().equals(""))){
				obItem.setOperationName(form.getOperationName());
			}
			if(form.getCpsId()!=null && (!form.getCpsId().trim().equals(""))){
					obItem.setCpsId(form.getCpsId());
			}
			

		return obItem;

	}
	catch (Exception ex) {
		throw new MapperException("failed to map form to ob of ExcludedFacility ", ex);
	}
	}

	
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs)	throws MapperException {
		
		ExcludedFacilityForm form = (ExcludedFacilityForm) cForm;
		IExcludedFacility item = (IExcludedFacility) obj;

		/*form.setExcludedfacilityCategory(item.getExcludedFacilityCategory());
		form.setActualExcludedfacilityCategoryCode(item.getExcludedFacilityCategory());*/
		
		form.setExcludedfacilityDescription(item.getExcludedFacilityDescription());
		
		form.setCreateBy(item.getCreateBy());
		form.setLastUpdateBy(item.getLastUpdateBy());
		form.setLastUpdateDate(df.format(item.getLastUpdateDate()));
		form.setCreationDate(df.format(item.getCreationDate()));
		form.setVersionTime(Long.toString(item.getVersionTime()));
		form.setDeprecated(item.getDeprecated());
		form.setId(Long.toString(item.getId()));
		form.setStatus(item.getStatus());

		return form;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "excludedFacilityObj", "com.integrosys.cms.app.baselmaster.bus.OBBaselMaster", SERVICE_SCOPE }, });
	}
}
