/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemMapper.java,v 1.8 2005/10/27 05:41:20 jtan Exp $
 */
package com.integrosys.cms.ui.fccBranch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.fccBranch.bus.OBFCCBranch;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/***
 * 
 * @author komal.agicha
 *
 */

public class FCCBranchMapper extends AbstractCommonMapper {
	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		DefaultLogger.debug(this, "Entering method mapFormToOB");
		FCCBranchForm form = (FCCBranchForm) cForm;
		ICommonUser user = (ICommonUser) inputs.get(IGlobalConstant.USER);
		IFCCBranch obItem = null;
		try {
			
				obItem = new OBFCCBranch();
				obItem.setBranchCode(form.getBranchCode());
				if(form.getBranchName()!=null && (!form.getBranchName().equals("")))
	            {
				obItem.setBranchName(form.getBranchName());
	            }
				
				
				obItem.setDeprecated(form.getDeprecated());
				
				if(form.getId()!=null && (!form.getId().equals("")))
	            {
					obItem.setId(Long.parseLong(form.getId()));
	            }
				obItem.setVersionTime(0l);
				if( form.getStatus() != null && ! form.getStatus().equals(""))
					obItem.setStatus(form.getStatus());
				else
					obItem.setStatus("ACTIVE");
				//File Upload
				
				if(form.getCreatedBy()!=null && form.getCreatedBy().trim()!=""){
					obItem.setCreatedBy(form.getCreatedBy());
				}else{
					obItem.setCreatedBy(user.getLoginID());
				}	
				obItem.setCreatedOn(new Date());
				if(form.getLastUpdatedBy()!=null && form.getLastUpdatedBy().trim()!=""){
					obItem.setLastUpdatedBy(user.getLoginID());
				}else{
					obItem.setLastUpdatedBy(user.getLoginID());
				}	
				obItem.setLastUpdatedOn(new Date());
				
				obItem.setAliasBranchCode(form.getAliasBranchCode());

			return obItem;

		}
		catch (Exception ex) {
			throw new MapperException("failed to map form to ob of FCCBranch Branch item", ex);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		FCCBranchForm form = (FCCBranchForm) cForm;
		IFCCBranch item = (IFCCBranch) obj;

		form.setBranchCode(item.getBranchCode());
		if(item.getBranchName()!=null && (!item.getBranchName().equals("")))
        {
		form.setBranchName(item.getBranchName());
        }
		
	
		form.setVersionTime(Long.toString(item.getVersionTime()));
		form.setDeprecated(item.getDeprecated());
		form.setId(Long.toString(item.getId()));
		form.setStatus(item.getStatus());
		
		form.setCreatedBy(item.getCreatedBy());
		if(item.getCreatedOn()!=null){
			form.setCreatedOn(df.format(item.getCreatedOn()));
		}
		form.setLastUpdatedBy(item.getLastUpdatedBy());
		if(item.getLastUpdatedOn()!=null){
			form.setLastUpdatedOn(df.format(item.getLastUpdatedOn()));
		}
		
		form.setAliasBranchCode(item.getAliasBranchCode());

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
				{ "fccBranchObj", "com.integrosys.cms.app.fccBranch.bus.OBFCCBranch", SERVICE_SCOPE }, });
	}
}
