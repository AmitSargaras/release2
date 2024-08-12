/**
 * CommonCodeParamListCommand.java
 *
 * Created on January 29, 2007, 3:32 PM
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.commoncode.Constants;
import com.integrosys.cms.app.commoncode.bus.CommonCodeTypeSearchCriteria;
import com.integrosys.cms.app.commoncode.proxy.CommonCodeTypeManagerFactory;
import com.integrosys.cms.app.commoncode.proxy.ICommonCodeTypeProxy;
import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryConstant;

/**
 * @Author: BaoHongMan
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/22 13:21:41 $ Tag: $Name%
 */
public class ListEditableCCCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] { { "IsMaintainRef", "java.lang.String", REQUEST_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { CommonCodeEntryConstant.EDITABLE_PARAM_LIST, "java.util.Collection", REQUEST_SCOPE },
				{ "IsMaintainRef", "java.lang.String", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap retValue = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();

		try {
			ArrayList commonCategoryList = new ArrayList();
			ICommonCodeTypeProxy proxy = CommonCodeTypeManagerFactory.getCommonCodeTypeProxy();
			CommonCodeTypeSearchCriteria criteria = new CommonCodeTypeSearchCriteria();

			String isMaintainRef = (String) map.get("IsMaintainRef");
			if ("Y".equals(isMaintainRef)) {
				criteria.setMaintainRef(true);
				criteria.setCategoryType(-1);
			}
			else {
				criteria.setCategoryType(Constants.CMS_DATA);
				isMaintainRef = "N";
			}
			criteria.setFirstSort("CATEGORY_NAME");
			criteria.setActiveStatus("A");

			SearchResult result = proxy.getCategoryList(criteria);
			if (result != null) {
				Collection col = result.getResultList();
				if (col != null) {
					commonCategoryList = new ArrayList(col);
				}
			}
			resultMap.put(CommonCodeEntryConstant.EDITABLE_PARAM_LIST, commonCategoryList);
			resultMap.put("IsMaintainRef", isMaintainRef);
		}
		catch (Exception e) {
			DefaultLogger.error(this,e.getMessage(),e);
			throw (new CommandProcessingException(e.getMessage()));
		}

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}

}
