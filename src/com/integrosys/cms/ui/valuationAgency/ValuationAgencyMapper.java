package com.integrosys.cms.ui.valuationAgency;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.state.bus.OBState;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;
import com.integrosys.cms.app.valuationAgency.bus.OBValuationAgency;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author rajib.aich For Valuation Agency Command for checker to approve edit .
 */
public class ValuationAgencyMapper extends AbstractCommonMapper {

	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");

	public Object mapFormToOB(CommonForm cForm, HashMap inputs)
			throws MapperException {

		DefaultLogger.debug(this, "Entering method mapFormToOB");
		ValuationAgencyForm form = (ValuationAgencyForm) cForm;

		IValuationAgency obItem = null;
		try {

			obItem = new OBValuationAgency();
			
			if( form.getAddress()!=null && !form.getAddress().equals("")){
				obItem.setAddress(form.getAddress());
			}
			
			if( form.getCountry()!=null && !form.getCountry().equals("")){
				obItem.setCountry(new OBCountry());
				obItem.getCountry().setIdCountry(Long.parseLong(form.getCountry()));
			}

			
			if( form.getRegion()!=null && !form.getRegion().equals("")){
				obItem.setRegion(new OBRegion());
				obItem.getRegion().setIdRegion(Long.parseLong(form.getRegion()));
			}

			if( form.getState()!=null && !form.getState().equals("")){
				obItem.setState(new OBState());
				obItem.getState().setIdState(Long.parseLong(form.getState()));
			}

			
			if( form.getCityTown()!=null && !form.getCityTown().equals("")){
				obItem.setCityTown(new OBCity());
				obItem.getCityTown().setIdCity(Long.parseLong(form.getCityTown()));
			}
			
			obItem.setDeprecated(form.getDeprecated());

			if (form.getId() != null && (!form.getId().trim().equals(""))) {
				obItem.setId(Long.parseLong(form.getId()));
			}

			obItem.setCreateBy(form.getCreateBy());
			obItem.setLastUpdateBy(form.getLastUpdateBy());

			if (form.getLastUpdateDate() != null
					&& (!form.getLastUpdateDate().equals(""))) {
				obItem.setLastUpdateDate(df.parse(form.getLastUpdateDate()));
			} else {
				obItem.setLastUpdateDate(new Date());
			}

			if (form.getCreationDate() != null
					&& (!form.getCreationDate().equals(""))) {
				obItem.setCreationDate(df.parse(form.getCreationDate()));
			} else {
				obItem.setCreationDate(new Date());
			}

			obItem.setVersionTime(0l);
			obItem.setStatus(form.getStatus());
			obItem.setValuationAgencyCode(form.getValuationAgencyCode());
			obItem.setValuationAgencyName(form.getValuationAgencyName());
			obItem.setMasterId(1l);
			
			//File Upload
			if(form.getFileUpload()!=null && (!form.getFileUpload().equals("")))
            {
				obItem.setFileUpload(form.getFileUpload());
            }

			return obItem;

		} catch (Exception ex) {
			throw new MapperException(
					"failed to map form to ob of ValuationAgencyMapper item",
					ex);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs)
			throws MapperException {
		Locale locale = (Locale) inputs
				.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		ValuationAgencyForm form = (ValuationAgencyForm) cForm;
		IValuationAgency item = (IValuationAgency) obj;

		if(item.getCreateBy()==null){
			form.setCreateBy("");	
		}else{
		form.setCreateBy(item.getCreateBy());
		}
		if(item.getLastUpdateBy()==null){
			form.setLastUpdateBy("");
		}else{
		form.setLastUpdateBy(item.getLastUpdateBy());
		}
		if(item.getLastUpdateDate()==null){
			form.setLastUpdateDate("");
		}else{
			form.setLastUpdateDate(df.format(item.getLastUpdateDate()));
		}
		if(item.getCreationDate()==null){
			form.setCreationDate("");
		}else{
			form.setCreationDate(df.format(item.getCreationDate()));
		}
		
		form.setVersionTime(Long.toString(item.getVersionTime()));
		
		if(item.getAddress()==null){
			form.setAddress("");
		}else{
			form.setAddress(item.getAddress());
		}
		
		form.setDeprecated(item.getDeprecated());
		

         if(item.getCountry()!=null){
		     if(item.getCountry().getIdCountry()!=0){
			form.setCountry(Long.toString(item.getCountry().getIdCountry()));
		    }
         }
         
         if(item.getRegion()!=null){
	    	if(item.getRegion().getIdRegion()!=0){
			form.setRegion(Long.toString(item.getRegion().getIdRegion()));
	     	}
         }
         
         if(item.getState()!=null){
	    	if(item.getState().getIdState()!=0){
				form.setState(Long.toString(item.getState().getIdState()));
	    	}
         }
		
         if(item.getCityTown()!=null){
             if(item.getCityTown().getIdCity()!=0){
			 form.setCityTown(Long.toString(item.getCityTown().getIdCity()));
		    }
         }
		
		form.setId(Long.toString(item.getId()));
		form.setStatus(item.getStatus());
		
		if(item.getValuationAgencyCode()==null){
			form.setValuationAgencyCode("");
		}else{
		form.setValuationAgencyCode(item.getValuationAgencyCode());
		}
		if(item.getValuationAgencyName()==null){
			form.setValuationAgencyName("");
		}else{
		form.setValuationAgencyName(item.getValuationAgencyName());
		}
		return form;
	}

	/**
	 * declares the key-value pair upfront for objects that needs to be accessed
	 * in scope
	 * 
	 * @return 2D-array key value pair
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM,
						"com.integrosys.component.bizstructure.app.bus.ITeam",
						GLOBAL_SCOPE },
				{ IGlobalConstant.USER,
						"com.integrosys.component.user.app.bus.ICommonUser",
						GLOBAL_SCOPE },
				{
						"valuationObj",
						"com.integrosys.cms.app.valuationAgency.bus.OBValuationAgency",
						SERVICE_SCOPE }, });
	}
}

