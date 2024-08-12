/**
 * Copyrfight Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/CoBorrowerCustomerMapper.java,v 1.3 2003/12/04 11:19:30 pooja Exp $
 */
package com.integrosys.cms.ui.limit;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.trx.OBCoBorrowerLimitTrxValue;

/**
 * Mapper class is used to map form values to objects and vice versa
 * @author $Author: pooja $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/12/04 11:19:30 $ Tag: $Name: $
 */
public class CoBorrowerCustomerMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public CoBorrowerCustomerMapper() {
		DefaultLogger.debug(this, "Inside constructor");
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "coBorrowertrxValue", "com.integrosys.cms.app.limit.trx.OBCoBorrowerLimitTrxValue", SERVICE_SCOPE },
				{ ICommonEventConstant.REQUEST_LOCALE, "java.util.Locale", REQUEST_SCOPE } }

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
		try {
			ViewLimitsForm aform = (ViewLimitsForm) cForm;
			OBCoBorrowerLimitTrxValue coBorrowertrxValue = (OBCoBorrowerLimitTrxValue) inputs.get("coBorrowertrxValue");
			DefaultLogger.debug(this, "initialising trxval" + coBorrowertrxValue);
			ICoBorrowerLimit coborrowerlimit;
			coborrowerlimit = coBorrowertrxValue.getLimit();
			if (coborrowerlimit == null) {
				throw new MapperException("The limit ob is null in mapper");
			}

			return (coborrowerlimit);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error occured in CoBorrowerCustomerMapper" + e);

			return null;
		}
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
		DefaultLogger.debug(this, "inside mapOb to form CoBorrowerCustomerMapper");
		ViewLimitsForm aForm = (ViewLimitsForm) cForm;
		DefaultLogger.debug(this, "in try");
		if (obj != null) {
			DefaultLogger.debug(this, "mapping OB TO FORM");
			ICMSCustomer sr = (ICMSCustomer) obj;

			if (sr.getCustomerReference() != null) {
				aForm.setCoBorrowerSubProfileID(sr.getCustomerReference());
				DefaultLogger.debug(this, "value of customer ref" + sr.getCustomerReference());
			}
			else {
				aForm.setCoBorrowerSubProfileID("-");
			}
			if (sr.getCMSLegalEntity().getLEReference() != null) {
				aForm.setLeID(sr.getCMSLegalEntity().getLEReference());
				DefaultLogger.debug(this, "value of legal ref" + sr.getCMSLegalEntity().getLEReference());
			}
			else {
				aForm.setLeID("-");
			}
			if (sr.getCustomerName() != null) {
				aForm.setCoBorrowerName(sr.getCustomerName());
//				DefaultLogger.debug(this, "value of setCoBorrowerName ref" + sr.getCustomerName());
			}
			else {
				aForm.setCoBorrowerName("-");
			}
			DefaultLogger.debug(this, "Going out of mapOb to form of CoBorrowerCustomerMapper ");

		}
		return aForm;
	}
}
