package com.integrosys.cms.ui.insurancecoverage;

import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.insurancecoverage.bus.OBInsuranceCoverage;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Describe this class Purpose Description
 * 
 * Used for setting values from OBObject to form & form to OBObject.
 * 
 * @author $Author: Dattatay Thorat $
 * @version $Revision:1.0 $
 * @since $Date: 2011-03-31 16:07:56 +0800  $ Tag : $Name$
 */

public class InsuranceCoverageMapper extends AbstractCommonMapper{

	public InsuranceCoverageMapper() {
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
		
		InsuranceCoverageForm form = (InsuranceCoverageForm) cForm;
		IInsuranceCoverage insuranceCoverage = new OBInsuranceCoverage();
		ICommonUser user = (ICommonUser) inputs.get(IGlobalConstant.USER);
		long contactNo = 0;
		try{
			if(form.getId()== null){
				insuranceCoverage.setId(1l);
			}else{		
				insuranceCoverage.setId(Long.parseLong(form.getId()));
			}	
			insuranceCoverage.setInsuranceCoverageCode(form.getInsuranceCoverageCode());
			insuranceCoverage.setCompanyName(form.getCompanyName());
			insuranceCoverage.setAddress(form.getAddress());
			if(form.getContactNumber()!=null && !form.getContactNumber().trim().equals(""))
				insuranceCoverage.setContactNumber(Long.parseLong(form.getContactNumber()));
			else
				insuranceCoverage.setContactNumber(contactNo);
			
			if(form.getDeprecated()!=null && form.getDeprecated().trim()!=""){
				insuranceCoverage.setDeprecated(form.getDeprecated());
			}else{
				insuranceCoverage.setDeprecated("N");
			}	
			if(form.getStatus()!=null && form.getStatus().trim()!=""){
				insuranceCoverage.setStatus(form.getStatus());
			}else{
				insuranceCoverage.setStatus("ACTIVE");
			}	
			insuranceCoverage.setVersionTime(0l);
			if(form.getCreatedBy()!=null && form.getCreatedBy().trim()!=""){
				insuranceCoverage.setCreatedBy(form.getCreatedBy());
			}else{
				insuranceCoverage.setCreatedBy(user.getLoginID());
			}	
			insuranceCoverage.setCreationDate(new Date());
			if(form.getLastUpdateBy()!=null && form.getLastUpdateBy().trim()!=""){
				insuranceCoverage.setLastUpdateBy(user.getLoginID());
			}else{
				insuranceCoverage.setLastUpdateBy(user.getLoginID());
			}	
			insuranceCoverage.setLastUpdateDate(new Date());
			
			insuranceCoverage.setFileUpload(form.getFileUpload());
			
		}catch (Exception e) {
			DefaultLogger.error(this, "Exception in RelationshipMgrMapper",e);
			e.printStackTrace();
			throw new MapperException("Exception in RelationshipMgrMapper");
		}	
		return insuranceCoverage;
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

		IInsuranceCoverage insuranceCoverage = (OBInsuranceCoverage) obj;
		InsuranceCoverageForm insuranceCoverageForm =  new InsuranceCoverageForm();

		try {

			insuranceCoverageForm.setId(Long.toString(insuranceCoverage.getId()));
			insuranceCoverageForm.setInsuranceCoverageCode(insuranceCoverage.getInsuranceCoverageCode());
			insuranceCoverageForm.setCompanyName(insuranceCoverage.getCompanyName());
			insuranceCoverageForm.setAddress(insuranceCoverage.getAddress());
			insuranceCoverageForm.setContactNumber(Long.toString(insuranceCoverage.getContactNumber()));
			
			insuranceCoverageForm.setDeprecated(insuranceCoverage.getDeprecated());
			insuranceCoverageForm.setStatus(insuranceCoverage.getStatus());
			insuranceCoverageForm.setCreatedBy(insuranceCoverage.getCreatedBy());
			insuranceCoverageForm.setCreationDate(insuranceCoverage.getCreationDate());
			insuranceCoverageForm.setLastUpdateBy(insuranceCoverage.getLastUpdateBy());
			insuranceCoverageForm.setLastUpdateDate(insuranceCoverage.getLastUpdateDate().toString());
			insuranceCoverageForm.setVersionTime(insuranceCoverage.getVersionTime());
		} catch (Exception e) {
			DefaultLogger.error(this, "Exception In RelationshipMgrMapper", e);
			e.printStackTrace();
			throw new MapperException("Exception in RelationshipMgrMapper");
		}

		return insuranceCoverageForm;

	}
	
	/**
	 * declares the key-value pair upfront for objects that needs to be accessed
	 * in scope
	 * @return 2D-array key value pair
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE }
				});
	}

}
