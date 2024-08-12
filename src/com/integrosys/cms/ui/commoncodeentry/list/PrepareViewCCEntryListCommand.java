/**
 * CommonCodeParamEditCommand.java
 *
 * Created on January 29, 2007, 6:00 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.ui.commoncodeentry.list;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

/**
 * @Author: BaoHongMan
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/22 13:21:41 $ Tag: $Name%
 */
public class PrepareViewCCEntryListCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "ctyValLabMap", "java.util.Map", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		try {
			Map ctyValLabMap = MaintainCCEntryUtil.getCountryValueLabelMap();
			result.put("ctyValLabMap", ctyValLabMap);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}
}