/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CommonCodeEntriesMapper.java
 *
 * Created on February 5, 2007, 6:04 PM
 *
 * Purpose:
 * Description:
 *
 * @Author: BaoHongMan
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.ui.commoncodeentry.item;

import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;

public class CCEntryMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm commonForm, Object object, HashMap hashMap) throws MapperException {
		DefaultLogger.debug(this, "inside mapOBToForm.");
		MaintainCCEntryForm form = (MaintainCCEntryForm) commonForm;
		OBCommonCodeEntry entry = (OBCommonCodeEntry) object;
		if (entry == null) {
			return form;
		}
		form.setEntryName(entry.getEntryName());
		form.setEntryCode(entry.getEntryCode());
		form.setCountry(entry.getCountry());
		form.setRefCategoryCode(entry.getRefEntryCode());
		if(entry.getCategoryCode()!=null && !entry.getCategoryCode().trim().isEmpty()){
			form.setCategoryCode(entry.getCategoryCode().trim());
		}
		if(entry.getOperationName()!=null && !entry.getOperationName().trim().isEmpty()){
			form.setOperationName(entry.getOperationName().trim());
		}

		return form;
	}

	public Object mapFormToOB(CommonForm commonForm, HashMap hashMap) throws MapperException {
		DefaultLogger.debug(this, "inside mapFormToOB.");
		MaintainCCEntryForm form = (MaintainCCEntryForm) commonForm;
		OBCommonCodeEntry entry = new OBCommonCodeEntry();

		entry.setEntryName(form.getEntryName().trim());
		entry.setEntryCode(form.getEntryCode());
		if (StringUtils.isEmpty(form.getCountry())) {
			entry.setCountry(null);
		} else {
			entry.setCountry(form.getCountry());
		}
		if (StringUtils.isEmpty(form.getRefCategoryCode())) {
			entry.setRefEntryCode(null);
		} else {
			entry.setRefEntryCode(form.getRefCategoryCode());
		}

		if(form.getCpsId()!=null && !form.getCpsId().trim().isEmpty()){
			entry.setCpsId(form.getCpsId().trim());
		}
		entry.setActiveStatus(true);
		entry.setUpdateFlag('I');
		//Added By Anil
		IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
		IGeneralParamEntry generalParamEntry = generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
		String applicationDate = generalParamEntry.getParamValue();
		Date appDate = new Date(applicationDate);
		entry.setCreationDate(appDate);
		entry.setLastUpdateDate(appDate);
		
		if(form.getCategoryCode()!=null && !form.getCategoryCode().trim().isEmpty()){
			entry.setCategoryCode(form.getCategoryCode().trim());
		}
		if(form.getOperationName()!=null && !form.getOperationName().trim().isEmpty()){
			entry.setOperationName(form.getOperationName().trim());
			if(form.getOperationName().trim().equalsIgnoreCase("I")){
				entry.setEntryCode(form.getCpsId().trim());
			}
		}
		return entry;
	}
}
