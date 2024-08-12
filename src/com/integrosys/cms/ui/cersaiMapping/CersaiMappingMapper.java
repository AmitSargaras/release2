package com.integrosys.cms.ui.cersaiMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping;
import com.integrosys.cms.app.cersaiMapping.bus.OBCersaiMapping;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

public class CersaiMappingMapper extends AbstractCommonMapper{

DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	
	public Object mapFormToOB(CommonForm cForm, HashMap map)
			throws MapperException {
		DefaultLogger.debug(this, "Entering method mapFormToOB");
		CersaiMappingForm form=(CersaiMappingForm)cForm;
		Locale locale=(Locale)map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICersaiMapping obItem=null;
		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
		ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
		try {
			obItem = new OBCersaiMapping();
			
			if(form.getEvent().equals("submit") || form.getEvent().equals("maker_confirm_resubmit_delete")) {
				
	
			obItem.setStatus("ACTIVE");
			
			
			if (form.getId() != null && (!form.getId().trim().equals(""))) {
				obItem.setId(Long.parseLong(form.getId()));
			}

			if (form.getCreateBy() != null && !"".equals(form.getCreateBy())) {
				obItem.setCreateBy(form.getCreateBy());
			} else {
				obItem.setCreateBy(user.getLoginID());
			}

			if (form.getLastUpdateBy() != null && !"".equals(form.getLastUpdateBy())) {
				obItem.setLastUpdateBy(user.getLoginID());
			} else {
				obItem.setLastUpdateBy(user.getLoginID());
			}

//			if (form.getLastUpdateDate() != null && (!form.getLastUpdateDate().equals(""))) {
//				obItem.setLastUpdateDate(df.parse(form.getLastUpdateDate()));
//			} else {
//				obItem.setLastUpdateDate(new Date());
//			}
//			
//			if (form.getCreationDate() != null && (!form.getCreationDate().equals(""))) {
//			obItem.setCreationDate(df.parse(form.getCreationDate()));
//			} else {
//			obItem.setCreationDate(new Date());
//			}
			
			if (form.getLastUpdateDate() != null && (!form.getLastUpdateDate().equals(""))) {
				obItem.setLastUpdateDate(new Date());
			} else {
				obItem.setLastUpdateDate(new Date());
			}
			
			if (form.getCreationDate() != null && (!form.getCreationDate().equals(""))) {
				obItem.setCreationDate(new Date());
			} else {
				obItem.setCreationDate(new Date());
			}
			
			if (form.getMasterName() != null && (!form.getMasterName().equals(""))) {
				obItem.setMasterName(form.getMasterName());
			} else {
				obItem.setMasterName("");
			}
			
//			if (form.getClimsValues() != null) {
//				obItem.setClimsValues(form.getClimsValues());
//			}
			
			if (form.getUpdatedClimsValue() != null) {
				obItem.setUpdatedClimsValue(form.getUpdatedClimsValue());
			}
			
			if (form.getUpdatedCersaiValue() != null ) {
				obItem.setUpdatedCersaiValue(form.getUpdatedCersaiValue());
			}
			
			
			
			String[] updatedCersaiValue = form.getUpdatedCersaiValue();
			String[] climsValues = form.getClimsValues();
			String[] updatedClimsValue = form.getUpdatedClimsValue();
			//String[] lastUpdatedDate = form.getLastUpdatedDate();
			
			ICersaiMapping[] feedEntriesArr = new ICersaiMapping[updatedCersaiValue.length];
			for (int i = 0; i < updatedCersaiValue.length; i++) {
				feedEntriesArr[i] = new OBCersaiMapping();
				feedEntriesArr[i].setCersaiValue(updatedCersaiValue[i]);
				//feedEntriesArr[i].setClimsValue(climsValues[i]);
				if(updatedClimsValue == null || updatedClimsValue.length <= 0) {
					feedEntriesArr[i].setClimsValue(climsValues[i]);
				}else {
					feedEntriesArr[i].setClimsValue(updatedClimsValue[i]);
				}
				
				//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				/*try {
					feedEntriesArr[i].setLastUpdatedDate(sdf.parse(lastUpdatedDate[i]));
				} catch (ParseException e) {
					e.printStackTrace();
				}*/
			}
			
			
			obItem.setFeedEntriesArr(feedEntriesArr);
			obItem.setMasterValueList(feedEntriesArr);
			
			}
		}
	catch (Exception ex) {
		throw new MapperException("failed to map form to ob of CersaiMapping master ", ex);
	}
		return obItem;
	}

	
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs)	throws MapperException {
		
		CersaiMappingForm form = (CersaiMappingForm) cForm;
		ICersaiMapping item = (ICersaiMapping) obj;

		form.setCersaiValue(item.getCersaiValue());
		
		form.setClimsValue(item.getClimsValue());
		
		form.setCreateBy(item.getCreateBy());
		form.setLastUpdateBy(item.getLastUpdateBy());
//		form.setLastUpdateDate(df.format(item.getLastUpdateDate()));
//		form.setCreationDate(df.format(item.getCreationDate()));
		
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
				{ "cersaiMappingObj", "com.integrosys.cms.app.cersaiMapping.bus.OBCersaiMapping", SERVICE_SCOPE }, });
	}
}
