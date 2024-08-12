package com.integrosys.cms.ui.collateral.property.propsphotel;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.ui.collateral.property.AbstractPropertyStpValidator;

import java.util.Map;

public class PropSpHotelStpValidator extends AbstractPropertyStpValidator {
	public boolean validate(Map context) {
		if (!validatePropertyCollateral(context)) {
			return false;
		}
		if (validateAndAccumulate(context).size() <= 0) {
			
		}
		else return false;
		return true;
	}

	public ActionErrors validateAndAccumulate(Map context) {
		ActionErrors errorMessages = validateAndAccumulateProperty(context);

		return errorMessages;
	}
}
