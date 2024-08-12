package com.integrosys.cms.ui.otherbankbranch;

import java.util.Date;
import java.util.HashMap;

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
import com.integrosys.cms.app.otherbank.bus.OtherBankException;

/**
 * Describe this class Purpose Description
 * 
 * Used for setting values from OBObject to form & form to OBObject.
 * 
 * @author $Author: Dattatay Thorat $
 * @version $Revision:1.0 $
 * @since $Date: 2011-02-18 16:07:56 +0800  $ Tag : $Name$
 */

public class OtherBankMapper extends AbstractCommonMapper{

	public OtherBankMapper() {
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
		
		
		
		OtherBankForm form = (OtherBankForm) cForm;
		IOtherBank otherBank = new OBOtherBank();
		try{
		
			if(form.getId()== null){
				otherBank.setId(1l);
			}else{		
				otherBank.setId(Long.parseLong(form.getId()));
			}
			if(form.getOtherBankCode()!=null)//Govind S:23/08/2011
			{
			otherBank.setOtherBankCode(form.getOtherBankCode().toUpperCase());
			}
			if(form.getOtherBankName()!=null)//Govind S:23/08/2011
			{
			otherBank.setOtherBankName(form.getOtherBankName().toUpperCase());
			}
			otherBank.setAddress(form.getAddress());
			
			if(otherBank.getCity()==null){
				
				if(form.getCityId()!=null && !form.getCityId().equals("")){
					otherBank.setCity(new OBCity());
					otherBank.getCity().setIdCity(Long.parseLong(form.getCityId()));
				}
			}
			
			if(otherBank.getState()==null ){
				if(form.getStateId()!=null && !form.getStateId().equals("")){
					otherBank.setState(new OBState());
					otherBank.getState().setIdState(Long.parseLong(form.getStateId()));
				}
					
			}
			
			if(otherBank.getRegion()==null){
				
				if(form.getRegionId()!=null && !form.getRegionId().equals("")){
					otherBank.setRegion(new OBRegion());
					otherBank.getRegion().setIdRegion(Long.parseLong(form.getRegionId()));
				}
			}
			
			if(otherBank.getCountry()==null){
				if(form.getCountryId()!=null && !form.getCountryId().equals("")){
					otherBank.setCountry(new OBCountry());
					otherBank.getCountry().setIdCountry(Long.parseLong(form.getCountryId()));
				}
			}
			
			if(form.getContactNo() !=null && !form.getContactNo().trim().equals(""))
				otherBank.setContactNo(Long.parseLong(form.getContactNo().trim()));
			else
				otherBank.setContactNo(0l);
			
			otherBank.setContactMailId(form.getContactMailId());
			
			if(form.getDeprecated()!=null && form.getDeprecated().trim()!=""){
				otherBank.setDeprecated(form.getDeprecated());
			}else{
				otherBank.setDeprecated("N");
			}	
			if(form.getStatus()!=null && form.getStatus().trim()!=""){
				otherBank.setStatus(form.getStatus());
			}else{
				otherBank.setStatus("ACTIVE");
			}	
			otherBank.setVersionTime(0l);
			if(form.getCreatedBy()!=null && form.getCreatedBy().trim()!=""){
				otherBank.setCreatedBy(form.getCreatedBy());
			}else{
				otherBank.setCreatedBy("CMS");
			}	
			otherBank.setCreationDate(new Date());
			if(form.getLastUpdateBy()!=null && form.getLastUpdateBy().trim()!=""){
				otherBank.setLastUpdateBy(form.getLastUpdateBy());
			}else{
				otherBank.setLastUpdateBy("CMS");
			}	
			otherBank.setLastUpdateDate(new Date());
			if(form.getFaxNo()!=null && !form.getFaxNo().equals(""))
				otherBank.setFaxNo(Long.parseLong(form.getFaxNo()));
			else
				otherBank.setFaxNo(0l);
			
			
			//File Upload
			if(form.getFileUpload()!=null && (!form.getFileUpload().equals("")))
            {
				otherBank.setFileUpload(form.getFileUpload());
            }
			
		}catch (Exception e) {
			DefaultLogger.error(this, "Exception in OtherBankMapper",e);
			e.printStackTrace();
			throw new OtherBankException("Exception in OtherBankMapper",e);
		}	
		return otherBank;
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

		OBOtherBank obOtherBank = (OBOtherBank) obj;
		OtherBankForm bankForm =  new OtherBankForm();

		try {

			bankForm.setId(Long.toString(obOtherBank.getId()));
			bankForm.setOtherBankCode(obOtherBank.getOtherBankCode());
			bankForm.setOtherBankName(obOtherBank.getOtherBankName());
			bankForm.setAddress(obOtherBank.getAddress());
			
			if(obOtherBank.getCity()!=null){
				bankForm.setCityId(Long.toString(obOtherBank.getCity().getIdCity()));
				bankForm.setCity(obOtherBank.getCity().getCityName());
			}
			
			if(obOtherBank.getState()!=null){
				bankForm.setStateId(Long.toString(obOtherBank.getState().getIdState()));
				bankForm.setState(obOtherBank.getState().getStateName());
			}	
			
			if(obOtherBank.getRegion()!=null){
				bankForm.setRegionId(Long.toString(obOtherBank.getRegion().getIdRegion()));
				bankForm.setRegion(obOtherBank.getRegion().getRegionName());
			}

			if(obOtherBank.getCountry()!=null){
				bankForm.setCountryId(Long.toString(obOtherBank.getCountry().getIdCountry()));
				bankForm.setCountry(obOtherBank.getCountry().getCountryName());
			}
			
			if(obOtherBank.getContactNo()==0l)
				bankForm.setContactNo("");
			else
				bankForm.setContactNo(Long.toString(obOtherBank.getContactNo()));
			bankForm.setContactMailId(obOtherBank.getContactMailId());
			bankForm.setDeprecated(obOtherBank.getDeprecated());
			bankForm.setStatus(obOtherBank.getStatus());
			bankForm.setCreatedBy(obOtherBank.getCreatedBy());
			bankForm.setCreationDate(obOtherBank.getCreationDate());
			bankForm.setLastUpdateBy(obOtherBank.getLastUpdateBy());
			bankForm.setLastUpdateDate(obOtherBank.getLastUpdateDate().toString());
			if(obOtherBank.getFaxNo()==0l)
				bankForm.setFaxNo("");
			else
				bankForm.setFaxNo(Long.toString(obOtherBank.getFaxNo()));
			bankForm.setVersionTime(obOtherBank.getVersionTime());
		} catch (Exception e) {
			DefaultLogger.error(this, "In mapper", e);
			e.printStackTrace();
			throw new OtherBankException("Exception in OtherBankMapper",e);
		}

		return bankForm;

	}

}
