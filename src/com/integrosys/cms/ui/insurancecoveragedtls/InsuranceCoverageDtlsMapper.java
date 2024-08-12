package com.integrosys.cms.ui.insurancecoveragedtls;

import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.insurancecoverage.bus.OBInsuranceCoverage;
import com.integrosys.cms.app.insurancecoveragedtls.bus.OBInsuranceCoverageDtls;
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

public class InsuranceCoverageDtlsMapper extends AbstractCommonMapper{

	public InsuranceCoverageDtlsMapper() {
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
		
		InsuranceCoverageDtlsForm form = (InsuranceCoverageDtlsForm) cForm;
		IInsuranceCoverageDtls insuranceCoverageDtls = new OBInsuranceCoverageDtls();
		ICommonUser user = (ICommonUser) inputs.get(IGlobalConstant.USER);
		try{
		
			if(form.getId()== null){
				insuranceCoverageDtls.setId(1l);
			}else{		
				insuranceCoverageDtls.setId(Long.parseLong(form.getId()));
			}	
			if(form.getInsuranceCoverageId() != null && !form.getInsuranceCoverageId().trim().equals(""))
				insuranceCoverageDtls.setInsuranceCoverageCode(new OBInsuranceCoverage());
			
			insuranceCoverageDtls.getInsuranceCoverageCode().setId(Long.parseLong(form.getInsuranceCoverageId()));
			
			insuranceCoverageDtls.setInsuranceType(form.getInsuranceType());
			insuranceCoverageDtls.setInsuranceCategoryName(form.getInsuranceCategoryName());
			
			if(form.getDeprecated()!=null && form.getDeprecated().trim()!=""){
				insuranceCoverageDtls.setDeprecated(form.getDeprecated());
			}else{
				insuranceCoverageDtls.setDeprecated("N");
			}	
			if(form.getStatus()!=null && form.getStatus().trim()!=""){
				insuranceCoverageDtls.setStatus(form.getStatus());
			}else{
				insuranceCoverageDtls.setStatus(ICMSConstant.STATE_ACTIVE);
			}	
			insuranceCoverageDtls.setVersionTime(0l);
			if(form.getCreatedBy()!=null && form.getCreatedBy().trim()!=""){
				insuranceCoverageDtls.setCreatedBy(form.getCreatedBy());
			}else{
				insuranceCoverageDtls.setCreatedBy(user.getLoginID());
			}	
			insuranceCoverageDtls.setCreationDate(new Date());
			if(form.getLastUpdateBy()!=null && form.getLastUpdateBy().trim()!=""){
				insuranceCoverageDtls.setLastUpdateBy(user.getLoginID());
			}else{
				insuranceCoverageDtls.setLastUpdateBy(user.getLoginID());
			}	
			insuranceCoverageDtls.setLastUpdateDate(new Date());
			
		}catch (Exception e) {
			DefaultLogger.error(this, "Exception in InsuranceCoverageDtlsMapper",e);
			e.printStackTrace();
			throw new MapperException("Exception in InsuranceCoverageDtlsMapper");
		}	
		return insuranceCoverageDtls;
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

		IInsuranceCoverageDtls insuranceCoverageDtls = (OBInsuranceCoverageDtls) obj;
		InsuranceCoverageDtlsForm insuranceCoverageDtlsForm =  new InsuranceCoverageDtlsForm();

		try {

			insuranceCoverageDtlsForm.setId(Long.toString(insuranceCoverageDtls.getId()));
			insuranceCoverageDtlsForm.setInsuranceCoverageId(Long.toString(insuranceCoverageDtls.getInsuranceCoverageCode().getId()));
			insuranceCoverageDtlsForm.setInsuranceCoverageCode(insuranceCoverageDtls.getInsuranceCoverageCode().getInsuranceCoverageCode());
			insuranceCoverageDtlsForm.setInsuranceType(insuranceCoverageDtls.getInsuranceType());
			insuranceCoverageDtlsForm.setInsuranceCategoryName(insuranceCoverageDtls.getInsuranceCategoryName());
			
			insuranceCoverageDtlsForm.setDeprecated(insuranceCoverageDtls.getDeprecated());
			insuranceCoverageDtlsForm.setStatus(insuranceCoverageDtls.getStatus());
			insuranceCoverageDtlsForm.setCreatedBy(insuranceCoverageDtls.getCreatedBy());
			insuranceCoverageDtlsForm.setCreationDate(insuranceCoverageDtls.getCreationDate().toString());
			insuranceCoverageDtlsForm.setLastUpdateBy(insuranceCoverageDtls.getLastUpdateBy());
			insuranceCoverageDtlsForm.setLastUpdateDate(insuranceCoverageDtls.getLastUpdateDate().toString());
			insuranceCoverageDtlsForm.setVersionTime(insuranceCoverageDtls.getVersionTime());
		} catch (Exception e) {
			DefaultLogger.error(this, "Exception In InsuranceCoverageDtlsMapper", e);
			e.printStackTrace();
			throw new MapperException("Exception in InsuranceCoverageDtlsMapper");
		}

		return insuranceCoverageDtlsForm;

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
