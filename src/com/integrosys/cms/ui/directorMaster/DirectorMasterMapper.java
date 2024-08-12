/**
 * Copyright AurionPro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemMapper.java,v 1.8 2011-05-03 15:13:16 +0800 jtan Exp $
 */
package com.integrosys.cms.ui.directorMaster;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.directorMaster.bus.IDirectorMaster;
import com.integrosys.cms.app.directorMaster.bus.OBDirectorMaster;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;


/**
 * 
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-03 15:13:16 +0800 (Tue, 03 May 2011) $
 * Tag : $Name$
 */


public class DirectorMasterMapper extends AbstractCommonMapper {
	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		DefaultLogger.debug(this, "Entering method mapFormToOB");
		MaintainDirectorMasterForm form = (MaintainDirectorMasterForm) cForm;

		IDirectorMaster obItem = null;
		try {
			
				obItem = new OBDirectorMaster();
				
				obItem.setDinNo(form.getDinNo());
				obItem.setName(form.getName());
				obItem.setDirectorCode(form.getDirectorCode());
								
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
	
				obItem.setStatus(form.getStatus());
	

			return obItem;

		}
		catch (Exception ex) {
			throw new MapperException("failed to map form to ob of director Master item", ex);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		MaintainDirectorMasterForm form = (MaintainDirectorMasterForm) cForm;
		IDirectorMaster item = (IDirectorMaster) obj;

		form.setCreateBy(item.getCreateBy());
		form.setLastUpdateBy(item.getLastUpdateBy());
		form.setLastUpdateDate(df.format(item.getLastUpdateDate()));
		form.setCreationDate(df.format(item.getCreationDate()));
		form.setVersionTime(Long.toString(item.getVersionTime()));
		form.setDinNo(item.getDinNo());
		form.setName(item.getName());
		form.setDirectorCode(item.getDirectorCode());
		form.setDeprecated(item.getDeprecated());
		form.setId(Long.toString(item.getId()));
		form.setStatus(item.getStatus());

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
				{ "directorMasterObj", "com.integrosys.cms.app.directorMaster.bus.OBDirectorMaster", SERVICE_SCOPE }, });
	}
}
