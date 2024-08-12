package com.integrosys.cms.ui.riskType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.riskType.bus.IRiskType;
import com.integrosys.cms.app.riskType.bus.OBRiskType;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

public class RiskTypeMapper extends AbstractCommonMapper{

DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	
	public Object mapFormToOB(CommonForm cForm, HashMap map)
			throws MapperException {
		DefaultLogger.debug(this, "Entering method mapFormToOB");
		RiskTypeForm form=(RiskTypeForm)cForm;
		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
		Locale locale=(Locale)map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		IRiskType obItem=null;
		
		try {
			
			obItem = new OBRiskType();
			
			if(null != form.getRiskTypeName() && (!form.getRiskTypeName().trim().equals("")))
			{
				obItem.setRiskTypeName(form.getRiskTypeName());
			}
			
			if(null != form.getRiskTypeCode() && (!form.getRiskTypeCode().trim().equals("")))
			{
				
				obItem.setRiskTypeCode(form.getRiskTypeCode());
			}
			
		
			if(null != form.getDeprecated() && (!form.getDeprecated().trim().equals(""))){
			obItem.setDeprecated(form.getDeprecated());
			}else{
				obItem.setDeprecated("N");
			}
			if(null != form.getId() && (!form.getId().trim().equals("")))
            {
				obItem.setId(Long.parseLong(form.getId()));
            }
//			obItem.setCreateBy(form.getCreateBy());
//			obItem.setLastUpdateBy(form.getLastUpdateBy());
			
			if( null != form.getCreateBy() && !form.getCreateBy().trim().equals("")){
				obItem.setCreateBy(form.getCreateBy());
			}else{
				obItem.setCreateBy(user.getLoginID());
			}	
			
			if(null != form.getLastUpdateBy() && !form.getLastUpdateBy().trim().equals("")){
				obItem.setLastUpdateBy(form.getLastUpdateBy());
			}else{
				obItem.setLastUpdateBy(user.getLoginID());
			}	
			
			
			if(null != form.getLastUpdateDate() && (!form.getLastUpdateDate().equals("")))
            {
			obItem.setLastUpdateDate(df.parse(form.getLastUpdateDate()));
            }else{
            	obItem.setLastUpdateDate(new Date());
            }
			if(null != form.getCreationDate() && (!form.getCreationDate().equals("")))
            {
			obItem.setCreationDate(df.parse(form.getCreationDate()));
            }else{
            	obItem.setCreationDate(new Date());
            }
			obItem.setVersionTime(0l);
			if( null != form.getStatus()  && ! form.getStatus().equals(""))
				obItem.setStatus(form.getStatus());
			else
				obItem.setStatus("ACTIVE");
			
			if(null != form.getOperationName() && (!form.getOperationName().trim().equals(""))){
				obItem.setOperationName(form.getOperationName());
			}

		return obItem;

	}
	catch (Exception ex) {
		throw new MapperException("failed to map form to ob of RiskType ", ex);
	}
	}

	
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs)	throws MapperException {
		
		RiskTypeForm form = (RiskTypeForm) cForm;
		IRiskType item = (IRiskType) obj;

		form.setRiskTypeName(item.getRiskTypeName());
		form.setRiskTypeCode(item.getRiskTypeCode());
		
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
				{ "riskTypeObj", "com.integrosys.cms.app.riskType.bus.OBRiskType", SERVICE_SCOPE }, });
	}
}
