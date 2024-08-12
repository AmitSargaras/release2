/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemMapper.java,v 1.8 2005/10/27 05:41:20 jtan Exp $
 */
package com.integrosys.cms.ui.facilityNewMaster;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.facilityNewMaster.bus.OBFacilityNewMaster;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 *@author $Author: Abhijit R$
 *Mapper for FacilityNewMaster
 */

public class FacilityNewMasterMapper extends AbstractCommonMapper {
	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		DefaultLogger.debug(this, "Entering method mapFormToOB");
		MaintainFacilityNewMasterForm form = (MaintainFacilityNewMasterForm) cForm;

		IFacilityNewMaster obItem = null;
		try {
			
				obItem = new OBFacilityNewMaster();
				if(form.getNewFacilityCode()!=null && (!form.getNewFacilityCode().trim().equals(""))){
					obItem.setNewFacilityCode(form.getNewFacilityCode());
					}
				if(form.getNewFacilityName()!=null && (!form.getNewFacilityName().trim().equals(""))){
					obItem.setNewFacilityName(form.getNewFacilityName());
					}
				if(form.getNewFacilityType()!=null && (!form.getNewFacilityType().trim().equals(""))){
					obItem.setNewFacilityType(form.getNewFacilityType());
					}
				if(form.getNewFacilityCategory()!=null && (!form.getNewFacilityCategory().trim().equals(""))){
					obItem.setNewFacilityCategory(form.getNewFacilityCategory());
					}
				if(form.getLineNumber()!=null && (!form.getLineNumber().trim().equals(""))){
					obItem.setLineNumber(form.getLineNumber().toUpperCase());
					}
				else{
					obItem.setLineNumber("");
				}
				if(form.getPurpose()!=null && (!form.getPurpose().trim().equals(""))){
					obItem.setPurpose(form.getPurpose());
					}
				if(form.getNewFacilitySystem()!=null && (!form.getNewFacilitySystem().trim().equals(""))){
					obItem.setNewFacilitySystem(form.getNewFacilitySystem());
					}
				if(form.getWeightage()!=null && (!form.getWeightage().trim().equals(""))){
					obItem.setWeightage(Double.parseDouble(form.getWeightage()));
					}else{
						obItem.setWeightage(100.00D);
					}
				
				
				if(form.getDeprecated()!=null && (!form.getDeprecated().trim().equals(""))){
				obItem.setDeprecated(form.getDeprecated());
				}else{
					obItem.setDeprecated("N");
				}
				if(form.getId()!=null && (!form.getId().trim().equals("")))
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
				
				if(form.getOperationName()!=null && (!form.getOperationName().trim().equals(""))){
					obItem.setOperationName(form.getOperationName());
				}
				if(form.getCpsId()!=null && (!form.getCpsId().trim().equals(""))){
						obItem.setCpsId(form.getCpsId());
				}
				
				if(form.getRuleId()!=null && (!form.getRuleId().trim().equals(""))){
					obItem.setRuleId(form.getRuleId());
					}
				if(form.getProductAllowed()!=null && (!form.getProductAllowed().trim().equals(""))){
					obItem.setProductAllowed(form.getProductAllowed());
					}
				else{
					obItem.setProductAllowed("-");
				}
				if(form.getCurrencyRestriction()!=null && (!form.getCurrencyRestriction().trim().equals(""))){
					obItem.setCurrencyRestriction(form.getCurrencyRestriction());
					}
				else{
					obItem.setCurrencyRestriction("-");
				}
				if(form.getRevolvingLine()!=null && (!form.getRevolvingLine().trim().equals(""))){
					obItem.setRevolvingLine(form.getRevolvingLine());
					}
				if(form.getLineCurrency()!=null && (!form.getLineCurrency().trim().equals(""))){
					obItem.setLineCurrency(form.getLineCurrency());
					}
				else{
					obItem.setLineCurrency("-");
				}
				if(form.getIntradayLimit()!=null && (!form.getIntradayLimit().trim().equals(""))){
					obItem.setIntradayLimit(form.getIntradayLimit());
				}
				if(form.getStlFlag()!=null && (!form.getStlFlag().trim().equals(""))){
					obItem.setStlFlag(form.getStlFlag());
					}
				if(form.getLineDescription()!=null && (!form.getLineDescription().trim().equals(""))){
					obItem.setLineDescription(form.getLineDescription());
					}
				if(form.getScmFlag()!=null && (!form.getScmFlag().trim().equals(""))){
					obItem.setScmFlag(form.getScmFlag());
					}
				obItem.setLineExcludeFromLoa(ICMSConstant.YES.equals(form.getLineExcludeFromLoa())? ICMSConstant.YES :ICMSConstant.NO);
				if(form.getSelectedRiskTypes()!=null && (!form.getSelectedRiskTypes().trim().equals(""))){
					obItem.setSelectedRiskTypes(form.getSelectedRiskTypes());
					}
				
				if(form.getAvailAndOptionApplicable() !=null && (!form.getAvailAndOptionApplicable().trim().equals(""))){
					obItem.setAvailAndOptionApplicable(form.getAvailAndOptionApplicable());
					}else {
						obItem.setAvailAndOptionApplicable("off");
					}
				if(form.getIdlApplicableFlag()!=null && (!form.getIdlApplicableFlag().trim().equals(""))){
					obItem.setIdlApplicableFlag(form.getIdlApplicableFlag());
					}
				
			return obItem;

		}
		catch (Exception ex) {
			throw new MapperException("failed to map form to ob of FacilityNewMaster Branch item", ex);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		MaintainFacilityNewMasterForm form = (MaintainFacilityNewMasterForm) cForm;
		IFacilityNewMaster item = (IFacilityNewMaster) obj;

		form.setNewFacilityCode(item.getNewFacilityCode());
		form.setNewFacilityName(item.getNewFacilityName());
		form.setNewFacilityType(item.getNewFacilityType());
		form.setNewFacilityCategory(item.getNewFacilityCategory());
		form.setLineNumber(item.getLineNumber());
		form.setPurpose(item.getPurpose());
		form.setNewFacilitySystem(item.getNewFacilitySystem());
		form.setWeightage(Double.toString(item.getWeightage()));
		
		form.setCreateBy(item.getCreateBy());
		form.setLastUpdateBy(item.getLastUpdateBy());
		form.setLastUpdateDate(df.format(item.getLastUpdateDate()));
		form.setCreationDate(df.format(item.getCreationDate()));
		form.setVersionTime(Long.toString(item.getVersionTime()));
		form.setDeprecated(item.getDeprecated());
		form.setId(Long.toString(item.getId()));
		form.setStatus(item.getStatus());
		form.setRuleId(item.getRuleId());
		form.setProductAllowed(item.getProductAllowed());
		form.setCurrencyRestriction(item.getCurrencyRestriction());
		form.setRevolvingLine(item.getRevolvingLine());
		form.setLineCurrency(item.getLineCurrency());
		form.setIntradayLimit(item.getIntradayLimit());
		form.setStlFlag(item.getStlFlag());
		form.setLineDescription(item.getLineDescription());
		form.setScmFlag(item.getScmFlag());
		form.setLineExcludeFromLoa(item.getLineExcludeFromLoa());
		form.setSelectedRiskTypes(item.getSelectedRiskTypes());
		//form.setAvailAndOptionApplicable(item.getAvailAndOptionApplicable());
		form.setAvailAndOptionApplicable(item.getAvailAndOptionApplicable() != null ? item.getAvailAndOptionApplicable().trim() : "off");
		form.setIdlApplicableFlag(item.getIdlApplicableFlag());
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
				{ "facilityNewMasterObj", "com.integrosys.cms.app.facilityNewMaster.bus.OBFacilityNewMaster", SERVICE_SCOPE }, });
	}
}
