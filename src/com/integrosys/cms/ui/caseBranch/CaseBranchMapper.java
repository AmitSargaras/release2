/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemMapper.java,v 1.8 2005/10/27 05:41:20 jtan Exp $
 */
package com.integrosys.cms.ui.caseBranch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.caseBranch.bus.ICaseBranch;
import com.integrosys.cms.app.caseBranch.bus.OBCaseBranch;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 *@author $Author: Abhijit R$
 *Mapper for CaseBranch
 */

public class CaseBranchMapper extends AbstractCommonMapper {
	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		DefaultLogger.debug(this, "Entering method mapFormToOB");
		MaintainCaseBranchForm form = (MaintainCaseBranchForm) cForm;

		ICaseBranch obItem = null;
		try {
			
				obItem = new OBCaseBranch();
				obItem.setBranchCode(form.getBranchCode());
				if(form.getCoordinator1()!=null && (!form.getCoordinator1().equals("")))
	            {
				obItem.setCoordinator1(form.getCoordinator1());
	            }
				if(form.getCoordinator2()!=null && (!form.getCoordinator2().equals("")))
	            {
				obItem.setCoordinator2(form.getCoordinator2());
	            }
				if(form.getCoordinator1Email()!=null && (!form.getCoordinator1Email().trim().equals("")))
	            {
				obItem.setCoordinator1Email(form.getCoordinator1Email());
	            }
				if(form.getCoordinator2Email()!=null && (!form.getCoordinator2Email().trim().equals("")))
	            {
				obItem.setCoordinator2Email(form.getCoordinator2Email());
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
				
				

			return obItem;

		}
		catch (Exception ex) {
			throw new MapperException("failed to map form to ob of CaseBranch Branch item", ex);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		MaintainCaseBranchForm form = (MaintainCaseBranchForm) cForm;
		ICaseBranch item = (ICaseBranch) obj;

		form.setBranchCode(item.getBranchCode());
		if(item.getCoordinator1()!=null && (!item.getCoordinator1().equals("")))
        {
		form.setCoordinator1(item.getCoordinator1());
        }
		if(item.getCoordinator2()!=null && (!item.getCoordinator2().equals("")))
        {
		form.setCoordinator2(item.getCoordinator2());
        }
		if(item.getCoordinator1Email()!=null && (!item.getCoordinator1Email().trim().equals("")))
        {
			form.setCoordinator1Email(item.getCoordinator1Email());
        }
		if(item.getCoordinator2Email()!=null && (!item.getCoordinator2Email().trim().equals("")))
        {
			form.setCoordinator2Email(item.getCoordinator2Email());
        }
	
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
				{ "caseBranchObj", "com.integrosys.cms.app.caseBranch.bus.OBCaseBranch", SERVICE_SCOPE }, });
	}
}
