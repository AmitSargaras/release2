//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.marketablesec.marksecbondlocal;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/08 11:51:30 $
 * Tag: $Name:  $
 */
/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class MarksecBondLocalValidationHelper {

	private static String LOGOBJ = MarksecBondLocalValidationHelper.class.getName();

	public static ActionErrors validateInput(MarksecBondLocalForm aForm, Locale locale, ActionErrors errors) {
		String errorCode;
		final double MAX_NUMBER = Double.parseDouble("99");
		String maximumInterestRate = IGlobalConstant.MAXIMUM_ALLOWED_INTEREST_RATE;

		if (!(errorCode = Validator.checkNumber(aForm.getInterestRate(), false, 0, MAX_NUMBER, 9, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("interestRate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
					maximumInterestRate));
			DefaultLogger.debug(LOGOBJ, "aForm.getInterestRate(): " + aForm.getInterestRate());		}
		
		List<String> restrictedEvent = Arrays.asList(MarksecBondLocalAction.EVENT_DELETE_ITEM, "marksecbondlocal_prepare_update_sub");
		
		if(!restrictedEvent.contains(aForm.getEvent())) {
			if(aForm.getErrorOnLineValue()) {
				errors.add("totalLineValue", new ActionMessage("error.security.total.line.value.exceeded"));
			}
		}
		
		return errors;

	}
}
