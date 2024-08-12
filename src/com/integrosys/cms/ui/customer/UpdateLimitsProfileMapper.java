/**
 * Copyrfight Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/UpdateLimitsProfileMapper.java,v 1.8 2005/07/08 02:48:03 lyng Exp $
 */
package com.integrosys.cms.ui.customer;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue;

/**
 * Mapper class is used to map form values to objects and vice versa
 * @author $Author: lyng $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/07/08 02:48:03 $ Tag: $Name: $
 */
public class UpdateLimitsProfileMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public UpdateLimitsProfileMapper() {
		DefaultLogger.debug(this, "Inside constructor");
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "trxValue", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE }, }

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

		ProcessDetailsCustomerForm aform = (ProcessDetailsCustomerForm) cForm;
		OBLimitProfileTrxValue trxVal = (OBLimitProfileTrxValue) inputs.get("trxValue");
		ILimitProfile temp;
		temp = trxVal.getLimitProfile();
		if (temp == null) {
			throw new MapperException("The limitprofile ob is null in mapper");
		}
		Amount amt = null;
		if ((aform.getRequiredSecurityCoverage() == null) || (aform.getRequiredSecurityCoverage().trim().length() == 0)) {
			temp.setRequiredSecurityCoverage(ICMSConstant.FLOAT_INVALID_VALUE);
		}
		else {
			temp.setRequiredSecurityCoverage(Float.parseFloat(aform.getRequiredSecurityCoverage()));
		}
		temp.setFullDocReviewInd(Boolean.valueOf(aform.getFullDocReviewInd()).booleanValue());
		if (temp == null) {
			throw new MapperException("The limitprofile ob is null in mapper....");
		}
		return (temp);

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
		if (obj != null) {

			DefaultLogger.debug(this, "Before putting vector result");

		}
		else {

		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}

}
