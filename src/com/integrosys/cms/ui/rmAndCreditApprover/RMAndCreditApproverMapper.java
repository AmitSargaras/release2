/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemMapper.java,v 1.8 2005/10/27 05:41:20 jtan Exp $
 */
package com.integrosys.cms.ui.rmAndCreditApprover;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;

/**
 *@author $Author: Abhijit R$
 *Mapper for System Bank Branch
 */

public class RMAndCreditApproverMapper extends AbstractCommonMapper {
	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		DefaultLogger.debug(this, "Entering method mapFormToOB");
		RMAndCreditApproverForm form = (RMAndCreditApproverForm) cForm;

		IRMAndCreditApprover obItem = null;
		try {
			
				obItem = new OBRMAndCreditApprover();
				
				if(form.getUserName()!=null && (!form.getUserName().trim().equals("")))
	            {
				obItem.setUserName(form.getUserName());
	            }
				if(form.getRegion()!=null && (!form.getRegion().trim().equals("")))
	            {
				obItem.setRegion(form.getRegion());
	            }
				if(form.getDeprecated()!=null && (!form.getDeprecated().trim().equals("")))
	            {
				obItem.setDeprecated(form.getDeprecated());
	            }
						
				if(form.getUserEmail()!=null && (!form.getUserEmail().trim().equals("")))
	            {
				obItem.setUserEmail(form.getUserEmail());
	            }
				/*if(form.getLoginId()!=null && (!form.getLoginId().trim().equals("")))
	            {
				obItem.setLoginId(form.getLoginId());
	            }*/
				if(form.getSupervisorId()!=null && (!form.getSupervisorId().trim().equals("")))
	            {
				obItem.setSupervisorId(form.getSupervisorId());
	            }
				if(form.getSeniorApproval()!=null && (!form.getSeniorApproval().trim().equals("")))
	            {
				obItem.setSeniorApproval(form.getSeniorApproval());
	            }
				if(form.getDpValue()!=null && (!form.getDpValue().trim().equals("")))
	            {
				obItem.setDpValue(form.getDpValue());
	            }
				
				if(form.getOperationName()!=null && (!form.getOperationName().trim().equals("")))
	            {
				obItem.setOperationName(form.getOperationName());
	            }
				
	            if(form.getCreateBy()!=null && (!form.getCreateBy().trim().equals("")))
	            {
				obItem.setCreateBy(form.getCreateBy());
	            }
	            if(form.getLastUpdateBy()!=null && (!form.getLastUpdateBy().trim().equals("")))
	            {
				obItem.setLastUpdateBy(form.getLastUpdateBy());
	            }
	            
				obItem.setVersionTime(0l);
				
				if( form.getCpsId() != null && ! form.getCpsId().equals("")){
					obItem.setCpsId(form.getCpsId());
					obItem.setLoginId(form.getCpsId());
				}
				
				if( form.getStatus() != null && ! form.getStatus().equals("")){
					obItem.setStatus(form.getStatus());
				}
				
				if(form.getUserUnitType() !=null && !form.getUserUnitType().equals("")) {
					obItem.setUserUnitType(form.getUserUnitType());
				}
				
				if(form.getUserRole() !=null && !form.getUserRole().equals("")) {
					obItem.setUserRole(form.getUserRole());
				}
				
			return obItem;

		}
		catch (Exception ex) {
			throw new MapperException("failed to map form to ob of systemBank Branch item", ex);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		RMAndCreditApproverForm form = (RMAndCreditApproverForm) cForm;
		IRMAndCreditApprover item = (IRMAndCreditApprover) obj;

		form.setCpsId(item.getCpsId());
		form.setCreateBy(item.getCreateBy());
		form.setLastUpdateBy(item.getLastUpdateBy());
		form.setDeprecated(item.getDeprecated());
		form.setStatus(item.getStatus());
		form.setUserName(item.getUserName());
		form.setRegion(item.getRegion());
		form.setDeprecated(item.getDeprecated());
		form.setUserEmail(item.getUserEmail());
		form.setLoginId(item.getCpsId());
		form.setSupervisorId(item.getSupervisorId());
		form.setSeniorApproval(item.getSeniorApproval());
		form.setDpValue(item.getDpValue());
		form.setOperationName(item.getOperationName());
		form.setUserName(item.getUserRole());
		form.setUserUnitType(item.getUserUnitType());
		
		return form;
	}
	
}
