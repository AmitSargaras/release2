/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemMapper.java,v 1.8 2005/10/27 05:41:20 jtan Exp $
 */
package com.integrosys.cms.ui.collateralNewMaster;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.collateralNewMaster.bus.OBCollateralNewMaster;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 *@author $Author: Abhijit R$
 *Mapper for CollateralNewMaster
 */

public class CollateralNewMasterMapper extends AbstractCommonMapper {
	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		DefaultLogger.debug(this, "Entering method mapFormToOB");
		MaintainCollateralNewMasterForm form = (MaintainCollateralNewMasterForm) cForm;

		ICollateralNewMaster obItem = null;
		try {
				obItem = new OBCollateralNewMaster();
				if(form.getNewCollateralCode()!=null && (!form.getNewCollateralCode().trim().equals(""))){
					obItem.setNewCollateralCode(form.getNewCollateralCode().trim());
				}
				if(form.getNewCollateralDescription()!=null && (!form.getNewCollateralDescription().trim().equals(""))){
					obItem.setNewCollateralDescription(form.getNewCollateralDescription().trim());
				}
				if(form.getNewCollateralMainType()!=null && (!form.getNewCollateralMainType().trim().equals(""))){
					obItem.setNewCollateralMainType(form.getNewCollateralMainType().trim());
				}else{
					if(form.getNewCollateralSubType()!=null && (!form.getNewCollateralSubType().trim().equals(""))){
						if(form.getNewCollateralSubType().trim().length()>2){
							obItem.setNewCollateralMainType(form.getNewCollateralSubType().trim().substring(0,2));
						}else{
							obItem.setNewCollateralMainType(form.getNewCollateralSubType().trim());
						}
					}
				}
				if(form.getNewCollateralSubType()!=null && (!form.getNewCollateralSubType().trim().equals(""))){
					obItem.setNewCollateralSubType(form.getNewCollateralSubType().trim());
				}
				if(form.getInsurance()!=null && (!form.getInsurance().trim().equals(""))){
					obItem.setInsurance(form.getInsurance().trim());
				}
				if(form.getRevaluationFrequencyCount()!=null && (!form.getRevaluationFrequencyCount().trim().equals(""))){
					obItem.setRevaluationFrequencyCount(Long.parseLong(form.getRevaluationFrequencyCount().trim()));
				}
				if(form.getRevaluationFrequencyDays()!=null && (!form.getRevaluationFrequencyDays().trim().equals(""))){
					obItem.setRevaluationFrequencyDays(form.getRevaluationFrequencyDays().trim());
				}
				
				if(form.getDeprecated()!=null && (!form.getDeprecated().trim().equals(""))){
					obItem.setDeprecated(form.getDeprecated().trim());
				}else{
					obItem.setDeprecated("N");
				}
				
				if(form.getId()!=null && (!form.getId().trim().equals(""))){
					obItem.setId(Long.parseLong(form.getId().trim()));
	            }
				obItem.setCreateBy(form.getCreateBy().trim());
				obItem.setLastUpdateBy(form.getLastUpdateBy().trim());
				if(form.getLastUpdateDate()!=null && (!form.getLastUpdateDate().equals(""))){
					obItem.setLastUpdateDate(df.parse(form.getLastUpdateDate().trim()));
	            }else{
	            	obItem.setLastUpdateDate(new Date());
	            }
				if(form.getCreationDate()!=null && (!form.getCreationDate().equals(""))){
					obItem.setCreationDate(df.parse(form.getCreationDate().trim()));
	            }else{
	            	obItem.setCreationDate(new Date());
	            }
				
				obItem.setVersionTime(0l);
				
				if( form.getStatus() != null && !form.getStatus().equals("")){
					obItem.setStatus(form.getStatus().trim());
				}else{
					obItem.setStatus("ACTIVE");
				}

				if(form.getOperationName() != null && !form.getOperationName().trim().isEmpty()){
					obItem.setOperationName(form.getOperationName().trim());
				}
				
				if(form.getCpsId()!=null && !form.getCpsId().trim().isEmpty()){
					obItem.setCpsId(form.getCpsId().trim());
				}
				
				if(StringUtils.isNotBlank(form.getNewCollateralCategory())) {
					obItem.setNewCollateralCategory(form.getNewCollateralCategory());
				}
				if(StringUtils.isNotBlank(form.getIsApplicableForCersaiInd())) {
					obItem.setIsApplicableForCersaiInd(form.getIsApplicableForCersaiInd());
				}

			return obItem;

		}
		catch (Exception ex) {
			throw new MapperException("failed to map form to ob of CollateralNewMaster Branch item", ex);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		MaintainCollateralNewMasterForm form = (MaintainCollateralNewMasterForm) cForm;
		ICollateralNewMaster item = (ICollateralNewMaster) obj;

		form.setNewCollateralCode(item.getNewCollateralCode());
		form.setNewCollateralDescription(item.getNewCollateralDescription());
		form.setNewCollateralMainType(item.getNewCollateralMainType());
		form.setNewCollateralSubType(item.getNewCollateralSubType());
		form.setInsurance(item.getInsurance());
		form.setRevaluationFrequencyCount(String.valueOf(item.getRevaluationFrequencyCount()));
		form.setRevaluationFrequencyDays(item.getRevaluationFrequencyDays());
		
		form.setCreateBy(item.getCreateBy());
		form.setLastUpdateBy(item.getLastUpdateBy());
		form.setLastUpdateDate(df.format(item.getLastUpdateDate()));
		form.setCreationDate(df.format(item.getCreationDate()));
		form.setVersionTime(Long.toString(item.getVersionTime()));
		form.setDeprecated(item.getDeprecated());
		form.setId(Long.toString(item.getId()));
		form.setStatus(item.getStatus());
		
		form.setNewCollateralCategory(item.getNewCollateralCategory());
		form.setIsApplicableForCersaiInd(item.getIsApplicableForCersaiInd());

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
				{ "collateralNewMasterObj", "com.integrosys.cms.app.collateralNewMaster.bus.OBCollateralNewMaster", SERVICE_SCOPE }, });
	}
}
