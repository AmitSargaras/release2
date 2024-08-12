/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemMapper.java,v 1.8 2005/10/27 05:41:20 jtan Exp $
 */
package com.integrosys.cms.ui.holiday;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.holiday.bus.OBHoliday;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 *@author $Author: Abhijit R$
 *Mapper for Holiday
 */

public class HolidayMapper extends AbstractCommonMapper {
	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		DefaultLogger.debug(this, "Entering method mapFormToOB");
		MaintainHolidayForm form = (MaintainHolidayForm) cForm;

		IHoliday obItem = null;
		try {
			
				obItem = new OBHoliday();
				obItem.setDescription(form.getDescription());
				if(form.getStartDate()!=null && (!form.getStartDate().equals("")))
	            {
				obItem.setStartDate(df.parse(form.getStartDate()));
	            }
				if(form.getEndDate()!=null && (!form.getEndDate().equals("")))
	            {
				obItem.setEndDate(df.parse(form.getEndDate()));
	            }
				if(form.getIsRecurrent()!=null && (!form.getIsRecurrent().trim().equals("")))
	            {
				obItem.setIsRecurrent(form.getIsRecurrent());
	            }
				obItem.setDeprecated(form.getDeprecated());
				
				if(form.getId()!=null && (!form.getId().equals("")))
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
				//File Upload
				if(form.getFileUpload()!=null && (!form.getFileUpload().equals("")))
	            {
					obItem.setFileUpload(form.getFileUpload());
	            }
				

			return obItem;

		}
		catch (Exception ex) {
			throw new MapperException("failed to map form to ob of Holiday Branch item", ex);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		MaintainHolidayForm form = (MaintainHolidayForm) cForm;
		IHoliday item = (IHoliday) obj;

		form.setDescription(item.getDescription());
		if(item.getStartDate()!=null && (!item.getStartDate().equals("")))
        {
		form.setStartDate(df.format(item.getStartDate()));
        }
		if(item.getEndDate()!=null && (!item.getEndDate().equals("")))
        {
		form.setEndDate(df.format(item.getEndDate()));
        }
		if(item.getIsRecurrent()!=null && (!item.getIsRecurrent().trim().equals("")))
        {
			form.setIsRecurrent(item.getIsRecurrent());
        }
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

	/**
	 * declares the key-value pair upfront for objects that needs to be accessed
	 * 
	 * in scope
	 * @return 2D-array key value pair
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "holidayObj", "com.integrosys.cms.app.holiday.bus.OBHoliday", SERVICE_SCOPE }, });
	}
}
