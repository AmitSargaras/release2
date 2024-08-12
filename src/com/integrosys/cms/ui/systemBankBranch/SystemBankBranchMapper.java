/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemMapper.java,v 1.8 2005/10/27 05:41:20 jtan Exp $
 */
package com.integrosys.cms.ui.systemBankBranch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.state.bus.OBState;
import com.integrosys.cms.app.systemBank.bus.OBSystemBank;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 *@author $Author: Abhijit R$
 *Mapper for System Bank Branch
 */

public class SystemBankBranchMapper extends AbstractCommonMapper {
	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		DefaultLogger.debug(this, "Entering method mapFormToOB");
		MaintainSystemBankBranchForm form = (MaintainSystemBankBranchForm) cForm;

		ISystemBankBranch obItem = null;
		try {
			
				obItem = new OBSystemBankBranch();
				if(form.getSystemBankBranchCode()!=null && (!form.getSystemBankBranchCode().trim().equals("")))
	            {
				obItem.setSystemBankBranchCode(form.getSystemBankBranchCode());
	            }
				if(form.getSystemBankBranchName()!=null && (!form.getSystemBankBranchName().trim().equals("")))
	            {
				obItem.setSystemBankBranchName(form.getSystemBankBranchName());
	            }
				if(form.getAddress()!=null && (!form.getAddress().trim().equals("")))
	            {
				obItem.setAddress(form.getAddress());
	            }
				
				if( form.getCountry() != null && !(form.getCountry().trim().equals("")) ){
					obItem.setCountry(new OBCountry());
					obItem.getCountry().setIdCountry(Long.parseLong(form.getCountry()));
				}
				
				if( form.getRegion() != null && !(form.getRegion().trim().equals("")) ){
					obItem.setRegion(new OBRegion());
					obItem.getRegion().setIdRegion(Long.parseLong(form.getRegion()));
				}
				
				if( form.getState() != null && !(form.getState().trim().equals("")) ){
					obItem.setState(new OBState());
					obItem.getState().setIdState(Long.parseLong(form.getState()));
				}

				
				if( form.getCityTown() != null && !(form.getCityTown().trim().equals("")) ){
					obItem.setCityTown(new OBCity());
					obItem.getCityTown().setIdCity(Long.parseLong(form.getCityTown()));
				}
				
				if(form.getContactMail()!=null && (!form.getContactMail().trim().equals("")))
	            {
				obItem.setContactMail(form.getContactMail());
	            }
				if(form.getContactNumber()!=null && (!form.getContactNumber().trim().equals("")))
	            {
				obItem.setContactNumber(form.getContactNumber());
	            }
				if(form.getFaxNumber()!=null && (!form.getFaxNumber().trim().equals("")))
	            {
				obItem.setFaxNumber(form.getFaxNumber());
	            }
				if(form.getRbiCode()!=null && (!form.getRbiCode().trim().equals("")))
	            {
				obItem.setRbiCode(form.getRbiCode());
	            }
				if(form.getIsHub()!=null && (!form.getIsHub().trim().equals("")))
	            {
				obItem.setIsHub(form.getIsHub());
	            }if(form.getLinkedHub()!=null && (!form.getLinkedHub().trim().equals("")))
	            {obItem.setLinkedHub(form.getLinkedHub());
	            }
	           if(form.getDeprecated()!=null && (!form.getDeprecated().trim().equals("")))
	            {obItem.setDeprecated(form.getDeprecated());
	            }
	          
	           if(form.getId()!=null && (!form.getId().trim().equals("")))
	            {
					obItem.setId(Long.parseLong(form.getId()));
	            }if(form.getCreateBy()!=null && (!form.getCreateBy().trim().equals("")))
	            {
				obItem.setCreateBy(form.getCreateBy());
	            }if(form.getLastUpdateBy()!=null && (!form.getLastUpdateBy().trim().equals("")))
	            {
				obItem.setLastUpdateBy(form.getLastUpdateBy());
	            }if(form.getLastUpdateDate()!=null && (!form.getLastUpdateDate().equals("")))
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
	            obItem.setFileUpload(form.getFileUpload());
				obItem.setVersionTime(0l);
				obItem.setMasterId(1l);
				if( form.getStatus() != null && ! form.getStatus().equals(""))
					obItem.setStatus(form.getStatus());
				else
					obItem.setStatus("ACTIVE");
				if (!isEmptyOrNull(form.getSystemBankCode())) {
					DefaultLogger.debug(this,"inside the mapFormToOB  ::::: system bank code before ===================== "+form.getSystemBankCode());

					if (obItem.getSystemBankCode() == null)
						obItem.setSystemBankCode(new OBSystemBank());

					obItem.getSystemBankCode().setId(Long.parseLong(form.getSystemBankId()));
					obItem.getSystemBankCode().setSystemBankCode(form.getSystemBankCode());
					DefaultLogger.debug(this,"inside the mapFormToOB  ::::: system bank id after===================== "+obItem.getSystemBankCode().getId());
					DefaultLogger.debug(this,"inside the mapFormToOB  ::::: system bank code after===================== "+obItem.getSystemBankCode().getSystemBankCode());
				} else{
					obItem.setSystemBankCode(new OBSystemBank());

					 if(form.getSystemBankId()!=null && (!form.getSystemBankId().trim().equals("")))
			            {
							obItem.getSystemBankCode().setId(Long.parseLong(form.getSystemBankId()));
			            }else {
			            	obItem.getSystemBankCode().setId(1);
			            } 
					
				}
				// by abhijit 
					//obItem.setSystemBankCode(null);
				if(form.getIsVault()!=null){
					obItem.setIsVault(form.getIsVault());
				}
				if(form.getCustodian1()!=null && (!form.getCustodian1().trim().equals("")))
	            {
				obItem.setCustodian1(form.getCustodian1());
	            }
				if(form.getCustodian2()!=null && (!form.getCustodian2().trim().equals("")))
	            {
				obItem.setCustodian2(form.getCustodian2());
	            }
				
				if(form.getOperationName()!=null && (!form.getOperationName().trim().equals(""))){
					obItem.setOperationName(form.getOperationName());
				}
				if(form.getCpsId()!=null && (!form.getCpsId().trim().equals(""))){
					try {
						obItem.setCpsId(form.getCpsId().trim());
					} catch (Exception e) {
						e.printStackTrace();
						obItem.setCpsId("0");
					}
				}
				if(StringUtils.isNotBlank(form.getPincode())) {
					obItem.setPincode(form.getPincode().trim());
				}
			return obItem;

		}
		catch (Exception ex) {
			throw new MapperException("failed to map form to ob of systemBank Branch item", ex);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		MaintainSystemBankBranchForm form = (MaintainSystemBankBranchForm) cForm;
		ISystemBankBranch item = (ISystemBankBranch) obj;

		form.setCreateBy(item.getCreateBy());
		form.setLastUpdateBy(item.getLastUpdateBy());
		form.setLastUpdateDate(df.format(item.getLastUpdateDate()));
		form.setCreationDate(df.format(item.getCreationDate()));
		form.setVersionTime(Long.toString(item.getVersionTime()));
		form.setAddress(item.getAddress());
		if( item.getCountry() != null && !(item.getCountry().equals("")) ){
		if(item.getCountry().getIdCountry()!=0){
			form.setCountry(Long.toString(item.getCountry().getIdCountry()));
		}
		}
		if( item.getRegion() != null && !(item.getRegion().equals("")) ){
		if(item.getRegion().getIdRegion()!=0){
			form.setRegion(Long.toString(item.getRegion().getIdRegion()));
		}
		}
		if( item.getState() != null && !(item.getState().equals("")) ){
		if(item.getState().getIdState()!=0){
			form.setState(Long.toString(item.getState().getIdState()));
		}
		}
		if( item.getCityTown() != null && !(item.getCityTown().equals("")) ){
		if(item.getCityTown().getIdCity()!=0){
			form.setCityTown(Long.toString(item.getCityTown().getIdCity()));
		}
		}
		
		form.setContactMail(item.getContactMail());
		if( item.getContactNumber() != null && !(item.getContactNumber().equals("")) ){
		form.setContactNumber(item.getContactNumber());
		}
		if( item.getFaxNumber() != null && !(item.getFaxNumber().equals("")) ){
		form.setFaxNumber(item.getFaxNumber());
		}
		form.setRbiCode(item.getRbiCode());
		form.setIsHub(item.getIsHub());
		form.setLinkedHub(item.getLinkedHub());
		form.setIsVault(item.getIsVault());
		form.setCustodian1(item.getCustodian1());
		form.setCustodian2(item.getCustodian2());
		
		form.setDeprecated(item.getDeprecated());
		
		form.setId(Long.toString(item.getId()));
		form.setStatus(item.getStatus());
		form.setSystemBankId(Long.toString(item.getSystemBankCode().getId()));
		form.setSystemBankCode(item.getSystemBankCode().getSystemBankCode());
		form.setSystemBankName(item.getSystemBankCode().getSystemBankName());
		form.setSystemBankBranchCode(item.getSystemBankBranchCode());
		form.setSystemBankBranchName(item.getSystemBankBranchName());
		
		if(StringUtils.isNotBlank(item.getPincode())) {
			form.setPincode(item.getPincode().trim());
		}

		return form;
	}

	public static int adjustOffset(int offset, int length, int maxSize) {

		int adjustedOffset = offset;

		if (offset >= maxSize) {
			DefaultLogger.debug(SystemBankBranchMapper.class.getName(), "offset " + offset + " + length " + length
					+ " >= maxSize " + maxSize);
			if (maxSize == 0) {
				// Do not reduce offset further.
				adjustedOffset = 0;
			}
			else if (offset == maxSize) {
				// Reduce.
				adjustedOffset = offset - length;
			}
			else {
				// Rely on "/" = Integer division.
				// Go to the start of the last strip.
				adjustedOffset = maxSize / length * length;
			}
			DefaultLogger.debug(SystemBankBranchMapper.class.getName(), "adjusted offset = " + adjustedOffset);
		}

		return adjustedOffset;
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
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "systemBankBranchObj", "com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch", SERVICE_SCOPE }, });
	}
}
