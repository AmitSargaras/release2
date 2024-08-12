/**
 * Copyrfight Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/ProcessDetailsCustomerMapper.java,v 1.19 2006/10/16 06:55:43 jzhan Exp $
 */

package com.integrosys.cms.ui.customer;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.ui.common.ConvertFloatToString;

/**
 * Mapper class is used to map form values to objects and vice versa
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2006/10/16 06:55:43 $ Tag: $Name: $
 */
public class ProcessDetailsCustomerMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public ProcessDetailsCustomerMapper() {
		DefaultLogger.debug(this, "Inside constructor");
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {}

		);
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @throws com.integrosys.base.uiinfra.exception.MapperException on errors
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		String apgrade = (String) inputs.get("approvingOfficerGrade");
		String reqSecCov = (String) inputs.get("requiredSecurityCoverage");
		String limitprofile = (String) inputs.get("limitprofileID");
		String fullDocReviewInd = (String) inputs.get("fullDocReviewInd");
		DefaultLogger.debug("value of limitId is ", "");
		OBLimitProfile oblimitprofile = new OBLimitProfile();
		oblimitprofile.setLimitProfileID(Long.parseLong(limitprofile));
		if ((reqSecCov == null) || (reqSecCov.trim().length() == 0)) {
			oblimitprofile.setRequiredSecurityCoverage(ICMSConstant.DOUBLE_INVALID_VALUE);
		}
		else {
			oblimitprofile.setRequiredSecurityCoverage(Double.parseDouble(reqSecCov));
		}
		oblimitprofile.setFullDocReviewInd(Boolean.valueOf(fullDocReviewInd).booleanValue());
		return (oblimitprofile);

		// return cSearch;
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @throws com.integrosys.base.uiinfra.exception.MapperException on errors
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "inside mapOb to form ");
		ProcessDetailsCustomerForm aForm = (ProcessDetailsCustomerForm) cForm;
		// Locale locale =
		// (Locale)map.get(com.integrosys.base.uiinfra.common.Constants
		// .GLOBAL_LOCALE_KEY);
		Locale locale = Locale.UK;
		try {
			if (obj != null) {
				OBLimitProfile ob = (OBLimitProfile) obj;
				if ((ob.getRequiredSecurityCoverage() != ICMSConstant.FLOAT_INVALID_VALUE)
						&& (ob.getRequiredSecurityCoverage() >= 0)) {
					aForm.setRequiredSecurityCoverage(ConvertFloatToString.getInstance().convertDouble(
							ob.getRequiredSecurityCoverage()));
				}
				else {
					aForm.setRequiredSecurityCoverage("100");
				}

				if (ob.getActualSecurityCoverage() != ICMSConstant.FLOAT_INVALID_VALUE) {
					aForm.setActualSecurityCoverage(ConvertFloatToString.getInstance().convertDouble(
							ob.getActualSecurityCoverage()));
				}
				else {
					aForm.setActualSecurityCoverage("");
				}
				if (ob.getRamRating() != null) {
					aForm.setApprovingOfficerGrade(ob.getRamRating());
				}
				DefaultLogger.debug(this, "setting limit" + ob.getLimitProfileID());
				aForm.setLimitprofileID(Long.toString(ob.getLimitProfileID()));
				DefaultLogger.debug(this, "value of full documnet review is" + ob.getFullDocReviewInd());
				if (ob.getFullDocReviewInd()) {
					aForm.setFullDocReviewInd("true");
				}
				else {
					aForm.setFullDocReviewInd("false");
				}
				if (ob.getOriginatingLocation() != null) {
					if (ob.getOriginatingLocation().getCountryCode() != null) {
						aForm.setDocStoreLoc(ob.getOriginatingLocation().getCountryCode()); // todo
																							// may
																							// need
																							// to
																							// show
																							// organsisation
					}
				}

				if (ob.getNextAnnualReviewDate() != null) {
					aForm.setNextAnnualReviewDate(DateUtil.formatDate(locale, ob.getNextAnnualReviewDate()));
				}
				if (ob.getExtendedNextReviewDate() != null) {
					aForm.setExtendedNextReviewDate(DateUtil.formatDate(locale, ob.getExtendedNextReviewDate()));
				}
				DefaultLogger.debug(this, "Before putting vector result");

			}
			else {
				DefaultLogger.debug(this, "obj is null");
			}
			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in ProcessDetailsCustomerMapper is" + e);
			e.printStackTrace();
			throw new MapperException();
		}
	}

}
