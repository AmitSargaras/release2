/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemMapper.java,v 1.8 2005/10/27 05:41:20 jtan Exp $
 */
package com.integrosys.cms.ui.caseCreationUpdate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.caseCreationUpdate.bus.ICaseCreation;

import com.integrosys.cms.app.caseCreationUpdate.bus.OBCaseCreation;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 *@author $Author: Abhijit R$
 *Mapper for CaseCreation
 *
 */

public class CaseCreationMapper extends AbstractCommonMapper {
	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		DefaultLogger.debug(this, "Entering method mapFormToOB");
		MaintainCaseCreationForm form = (MaintainCaseCreationForm) cForm;

		ICaseCreation obItem = null;
		try {
			
				obItem = new OBCaseCreation();
				obItem.setDescription(form.getDescription());
				obItem.setBranchCode(form.getBranchCode());
				obItem.setLimitProfileId(Long.parseLong(form.getLimitProfileId()));
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
				/*if(form.getFileUpload()!=null && (!form.getFileUpload().equals("")))
	            {
					obItem.setFileUpload(form.getFileUpload());
	            }*/
				
				String[] updateddocBarCoded = form.getUpdatedDocBarcode();
				System.out.println("updateddocBarCoded=>"+updateddocBarCoded);
				obItem.setUpdatedBoxBarCodes(form.getUpdatedBoxBarCode());
				obItem.setUpdatedDocAmounts(form.getUpdatedDocAmount());
				obItem.setUpdatedDocBarcodes(form.getUpdatedDocBarcode());
				obItem.setUpdatedFileBarCodes(form.getUpdatedFileBarCode());
				obItem.setUpdatedLotNumbers(form.getUpdatedLotNumber());
				obItem.setUpdatedPlaceOfExecutions(form.getUpdatedPlaceOfExecution()); 
				obItem.setUpdatedRackNumbers(form.getUpdatedRackNumber());
				obItem.setUpdatedRetrievaldates(form.getUpdatedRetrievaldate());
				obItem.setUpdatedStampDutys(form.getUpdatedStampDuty());
				obItem.setUpdatedSubmittedTos(form.getUpdatedSubmittedTo());
				obItem.setUpdatedUserNames(form.getUpdatedUserName());
				obItem.setUpdatedVaultLocations(form.getUpdatedVaultLocation());
				obItem.setUpdatedVaultNumbers(form.getUpdatedVaultNumber());
				obItem.setUpdatedVaultReceiptDates(form.getUpdatedVaultReceiptDate());
				obItem.setCheckBoxValues(form.getCheckBoxValues());



			return obItem;

		}
		catch (Exception ex) {
			throw new MapperException("failed to map form to ob of CaseCreation Branch item", ex);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		MaintainCaseCreationForm form = (MaintainCaseCreationForm) cForm;
		ICaseCreation item = (ICaseCreation) obj;
		if(item.getDescription()!=null){
			form.setDescription(item.getDescription());	
		}
		
		if(item.getCreateBy()!=null){
		form.setCreateBy(item.getCreateBy());
		
		}
		if(item.getLastUpdateBy()!=null){
		form.setLastUpdateBy(item.getLastUpdateBy());
		}
		if(item.getLastUpdateDate()!=null){
		form.setLastUpdateDate(df.format(item.getLastUpdateDate()));
		}
		if(item.getCreationDate()!=null){
		form.setCreationDate(df.format(item.getCreationDate()));
		}
		
		form.setVersionTime(Long.toString(item.getVersionTime()));
		
		if(item.getDeprecated()!=null){
		form.setDeprecated(item.getDeprecated());
		}
		form.setId(Long.toString(item.getId()));
		form.setLimitProfileId(Long.toString(item.getLimitProfileId()));
		form.setStatus(item.getStatus());
		
		form.setBranchCode(item.getBranchCode());
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
				{ "caseCreationUpdateObj", "com.integrosys.cms.app.caseCreationUpdate.bus.OBCaseCreation", SERVICE_SCOPE }, });
	}
}
