//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.marketablesec.marksecmainlocal;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

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
public class MarksecMainLocalValidationHelper {
	public static ActionErrors validateInput(MarksecMainLocalForm aForm, Locale locale, ActionErrors errors) {

		String errorCode = null;
		// Please implement this LOGIC
		if ("Y".equals(aForm.getLe())) {
			if (!(errorCode = Validator.checkDate(aForm.getLeDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("leDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1",
						256 + ""));
				DefaultLogger
						.debug("com.integrosys.cms.ui.collateral.marketablesec.PortItemValidator", "... LeDate...");
			}
		}

		return errors;

	}
}
