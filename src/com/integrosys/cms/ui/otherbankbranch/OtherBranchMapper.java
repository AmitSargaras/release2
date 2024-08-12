package com.integrosys.cms.ui.otherbankbranch;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.state.bus.OBState;
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.app.otherbranch.bus.OBOtherBranch;
import com.integrosys.cms.app.otherbranch.bus.OtherBranchException;

/**
 * Purpose : This is used for mapping values between object to form and viceversa.
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-02-18 16:07:56 +0800 (Fri, 18 Feb 2011) 
 */
public class OtherBranchMapper extends AbstractCommonMapper {

	public OtherBranchMapper() {
		DefaultLogger.debug(this, "constructor");
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm
	 * @param inputs
	 * @return
	 * @throws MapperException
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap inputs)
			throws MapperException {

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		OtherBranchForm form = (OtherBranchForm) cForm;
		IOtherBranch otherBankBranch = new OBOtherBranch();
	
		try{
			if(form.getId()!=null){
				otherBankBranch.setId(Long.parseLong(form.getId()));
			}	
			
			if (otherBankBranch.getOtherBankCode() == null)
				otherBankBranch.setOtherBankCode(new OBOtherBank());
            if(form.getOtherBankId()!=null){
			otherBankBranch.getOtherBankCode().setId(Long.parseLong(form.getOtherBankId()));
            }
			otherBankBranch.getOtherBankCode().setOtherBankCode(form.getOtherBankCode());
			
			if(form.getOtherBranchCode()!=null && !form.getOtherBranchCode().equals("") )
				otherBankBranch.setOtherBranchCode(form.getOtherBranchCode().toUpperCase());
			if(form.getOtherBranchName()!=null)//Govind S:24/08/2011
			{
			otherBankBranch.setOtherBranchName(form.getOtherBranchName().toUpperCase());
			}
			
			otherBankBranch.setAddress(form.getBranchAddress());
			
			
				if(form.getBranchCityId()!=null && !form.getBranchCityId().equals("")){
					otherBankBranch.setCity(new OBCity());
					otherBankBranch.getCity().setIdCity(Long.parseLong(form.getBranchCityId()));
				}	
			
			
			
			
				if(form.getBranchStateId()!=null && !form.getBranchStateId().equals("")){
					otherBankBranch.setState(new OBState());
					otherBankBranch.getState().setIdState(Long.parseLong(form.getBranchStateId()));
				}	
			
			
			
			

				if(form.getBranchRegionId()!=null && !form.getBranchRegionId().equals("")){
					otherBankBranch.setRegion(new OBRegion());
					otherBankBranch.getRegion().setIdRegion(Long.parseLong(form.getBranchRegionId()));
				}	
			
			
		

				if(form.getBranchCountryId()!=null && !form.getBranchCountryId().equals("")){
					otherBankBranch.setCountry(new OBCountry());
					otherBankBranch.getCountry().setIdCountry(Long.parseLong(form.getBranchCountryId()));
				}	
			
			
			if(form.getBranchContactNo()!=null && !form.getBranchContactNo().trim().equals(""))
				otherBankBranch.setContactNo(Long.parseLong(form.getBranchContactNo()));
			else
				otherBankBranch.setContactNo(0l);
			
			otherBankBranch.setContactMailId(form.getBranchContactMailId());
			
			if(form.getDeprecated()!=null && form.getDeprecated().trim()!=""){
				otherBankBranch.setDeprecated(form.getDeprecated());
			}else{
				otherBankBranch.setDeprecated("N");
			}	
			if(form.getStatus()!=null && form.getStatus().trim()!=""){
				otherBankBranch.setStatus(form.getStatus());
			}else{
				otherBankBranch.setStatus("ACTIVE");
			}	
			otherBankBranch.setVersionTime(0l);
			if(form.getCreatedBy()!=null && form.getCreatedBy().trim()!=""){
				otherBankBranch.setCreatedBy(form.getCreatedBy());
			}else{
				otherBankBranch.setCreatedBy("CMS");
			}	
			otherBankBranch.setCreationDate(new Date());
			if(form.getLastUpdateBy()!=null && form.getLastUpdateBy().trim()!=""){
				otherBankBranch.setLastUpdateBy(form.getLastUpdateBy());
			}else{
				otherBankBranch.setLastUpdateBy("CMS");
			}	
			otherBankBranch.setLastUpdateDate(new Date());
			if(form.getBranchFaxNo()!=null && !form.getBranchFaxNo().equals(""))
				otherBankBranch.setFaxNo(Long.parseLong(form.getBranchFaxNo()));
			else
				otherBankBranch.setFaxNo(0l);
			
			if(form.getBranchRbiCode()!=null && !form.getBranchRbiCode().equals(""))
				otherBankBranch.setRbiCode(Long.parseLong(form.getBranchRbiCode().toUpperCase()));
						
			//File Upload
			if(form.getFileUpload()!=null && (!form.getFileUpload().equals("")))
            {
				otherBankBranch.setFileUpload(form.getFileUpload());
            }
		}catch (Exception e) {
			// TODO: handle exception
			DefaultLogger.debug(this, "########### error in the OtherBranchMapper",e);
			e.printStackTrace();
			throw new OtherBranchException("Exception in OtherBranchMapper",e);
		}
		
		return otherBankBranch;
	}
	
	/**
	 * 
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm
	 * @param obj
	 * @param map
	 * @return
	 * @throws MapperException
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map)
			throws MapperException {
		OBOtherBranch obOtherBranch = (OBOtherBranch) obj;
		OtherBranchForm bankForm = (OtherBranchForm) cForm;

			try {
				bankForm.setId(Long.toString(obOtherBranch.getId()));
				bankForm.setOtherBranchCode(obOtherBranch.getOtherBranchCode());
				bankForm.setOtherBranchName(obOtherBranch.getOtherBranchName());
				bankForm.setBranchAddress(obOtherBranch.getAddress());
				
				if(obOtherBranch.getCity()!=null){
					bankForm.setBranchCityId(Long.toString(obOtherBranch.getCity().getIdCity()));
					bankForm.setBranchCity(obOtherBranch.getCity().getCityName());
				}
				
				if(obOtherBranch.getState()!=null){
					bankForm.setBranchStateId(Long.toString(obOtherBranch.getState().getIdState()));
					bankForm.setBranchState(obOtherBranch.getState().getStateName());
				}	
				
				if(obOtherBranch.getRegion()!=null){
					bankForm.setBranchRegionId(Long.toString(obOtherBranch.getRegion().getIdRegion()));
					bankForm.setBranchRegion(obOtherBranch.getRegion().getRegionName());
				}

				if(obOtherBranch.getCountry()!=null){
					bankForm.setBranchCountryId(Long.toString(obOtherBranch.getCountry().getIdCountry()));
					bankForm.setBranchCountry(obOtherBranch.getCountry().getCountryName());
				}	
				if(obOtherBranch.getContactNo()==0l)
					bankForm.setBranchContactNo("");
				else
					bankForm.setBranchContactNo(Long.toString(obOtherBranch.getContactNo()));
				
				bankForm.setBranchContactMailId(obOtherBranch.getContactMailId());
				bankForm.setBranchRbiCode(Long.toString(obOtherBranch.getRbiCode()));
				if(obOtherBranch.getFaxNo()==0l)
					bankForm.setBranchFaxNo("");
				else
					bankForm.setBranchFaxNo(Long.toString(obOtherBranch.getFaxNo()));				
				
				bankForm.setDeprecated(obOtherBranch.getDeprecated());
				bankForm.setStatus(obOtherBranch.getStatus());
				bankForm.setCreatedBy(obOtherBranch.getCreatedBy());
				bankForm.setCreationDate(obOtherBranch.getCreationDate().toString());
				bankForm.setLastUpdateBy(obOtherBranch.getLastUpdateBy());
				bankForm.setLastUpdateDate(obOtherBranch.getLastUpdateDate().toString());
				
				mapOtherBankOBToForm(obOtherBranch, bankForm);
			} catch (Exception e) {
				DefaultLogger.error(this, "In mapper", e);
				e.printStackTrace();
				throw new OtherBranchException("Exception in OtherBranchMapper",e);
			}

			return bankForm;
	}
	/**
	 * Purpose : Used for setting values from OtherBankBranch to
	 * @param otherBankBranch
	 * @param form
	 */
	public void mapOtherBankOBToForm(IOtherBranch otherBankBranch,OtherBranchForm form){
		form.setOtherBankId(Long.toString(otherBankBranch.getOtherBankCode().getId()));
		form.setOtherBankCode(otherBankBranch.getOtherBankCode().getOtherBankCode());
		form.setOtherBankName(otherBankBranch.getOtherBankCode().getOtherBankName());
		
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{"OtherBranchObj","om.integrosys.cms.app.otherbranch.bus.OBOtherBranch",SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
		});
	}
}
