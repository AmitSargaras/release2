/**
 * Copyrfight Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/tat/TatMapper.java,v 1.9 2005/02/25 10:35:28 wltan Exp $
 */
package com.integrosys.cms.ui.tat;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.customer.bus.ICreditGrade;
import com.integrosys.cms.app.customer.bus.ICreditStatus;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Mapper class is used to map form values to objects and vice versa
 * @author $Author: wltan $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2005/02/25 10:35:28 $ Tag: $Name: $
 */
public class TatMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public TatMapper() {
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

		String limitID = (String) inputs.get("limitID");
		OBLimit oblimit = new OBLimit();
		oblimit.setLimitID(java.lang.Long.parseLong(limitID));
		return (oblimit);

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
		DefaultLogger.debug(this, "inside mapOb to form");
		TatsForm aForm = (TatsForm) cForm;
		if (obj != null) {
			OBCMSCustomer sr = (OBCMSCustomer) obj;
			ICreditGrade cg[];
			cg = sr.getCMSLegalEntity().getCreditGrades();

			String creditgrade = null;
			if ((null != cg) && (cg.length != 0)) {
				for (int i = 0; i < cg.length; i++) {
					if ((cg[i].getCGType() != null) && cg[i].getCGType().equals("INTERNAL")) {
						creditgrade = cg[i].getCGCode();
					}
				}
			}
			ICreditStatus cs[];
			cs = sr.getCMSLegalEntity().getCreditStatus();
			String creditstatus = null;
			if ((null != cs) && (cs.length != 0)) {
				creditstatus = cs[0].getCSValue();
			}
			if (sr.getCMSLegalEntity().getLEReference() != null) {
				aForm.setLeRef(sr.getCMSLegalEntity().getLEReference());
			}
			if (sr.getCMSLegalEntity().getLegalName() != null) {
				aForm.setLeName(sr.getCMSLegalEntity().getLegalName());
			}
			if (sr.getCustomerReference() != null) {
				aForm.setSubprofileID(sr.getCustomerReference());
			}
			if (sr.getCustomerName() != null) {
				aForm.setCustomerName(sr.getCustomerName());
			}
			if (sr.getCMSLegalEntity().getCustomerSegment() != null) {
				aForm.setCustomerSegment(sr.getCMSLegalEntity().getCustomerSegment());
			}
			String creditGradeName = "-";
			if (creditgrade != null) {
				creditGradeName = CommonDataSingleton.getCodeCategoryLabelByValue("19", creditgrade.toString());
			}
			String creditStatusValue = "-";
			if (creditstatus != null) {
				creditStatusValue = CommonDataSingleton.getCodeCategoryLabelByValue("21", creditstatus.toString());
			}
			DefaultLogger.debug(this, "value of creditgrade " + creditgrade);
			DefaultLogger.debug(this, "value of creditGradeName " + creditGradeName);
			DefaultLogger.debug(this, "value of creditStatusValue " + creditStatusValue);
			aForm.setCreditGrade(creditGradeName);
			aForm.setCreditStatus(creditStatusValue);
			DefaultLogger.debug(this, "Before putting vector result");

		}
		else {
			DefaultLogger.debug(this, "obj is null");
		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}

}
