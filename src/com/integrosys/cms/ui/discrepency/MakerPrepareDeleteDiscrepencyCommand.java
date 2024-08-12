/**
 * 
 */
package com.integrosys.cms.ui.discrepency;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.discrepency.bus.NoSuchDiscrepencyException;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepency;
import com.integrosys.cms.app.discrepency.proxy.IDiscrepencyProxyManager;
import com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;

/**
 * @author sandiip.shinde
 * 
 */
public class MakerPrepareDeleteDiscrepencyCommand extends AbstractCommand {

	private IDiscrepencyProxyManager discrepencyProxy;
	
	public IDiscrepencyProxyManager getDiscrepencyProxy() {
		return discrepencyProxy;
	}

	public void setDiscrepencyProxy(IDiscrepencyProxyManager discrepencyProxy) {
		this.discrepencyProxy = discrepencyProxy;
	}

	/**
	 * Default Constructor
	 */

	public MakerPrepareDeleteDiscrepencyCommand() {
	}

	/**
	 * Defines 2-D array to be passed to doExecute Method by HashMap
	 */

	public String[][] getParameterDescriptor() {

		return (new String[][] {
				{ "discrepencyId", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {

		return (new String[][] {
				{ "discrepencyObj", "com.integrosys.cms.app.discrepency.bus.IDiscrepency",
						FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{
						com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,
						"java.util.Locale", GLOBAL_SCOPE },
				{
						"IDiscrepencyTrxValue",
						"com.integrosys.cms.app.geography.country.trx.IDiscrepencyTrxValue",
						SERVICE_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			String id = (String) map.get("discrepencyId");
			String event = (String) map.get("event");
			/*String startIdx = (String) map.get("startIndex");
			IDiscrepencyTrxValue discrepencyTrxValue = null;
			IDiscrepency discrepency = new OBDiscrepency();

			discrepencyTrxValue = getDiscrepencyProxy().getDiscrepencyTrxValue(Long.parseLong(id));
			discrepency = discrepencyTrxValue.getActualDiscrepency();

			if (discrepencyTrxValue.getStatus().equals("PENDING_CREATE")
					|| discrepencyTrxValue.getStatus().equals("PENDING_UPDATE")
					|| discrepencyTrxValue.getStatus().equals("PENDING_DELETE")
					|| discrepencyTrxValue.getStatus().equals("REJECTED")
					|| discrepencyTrxValue.getStatus().equals("DRAFT"))
				resultMap.put("wip", "wip");


			resultMap.put("event", event);
			resultMap.put("discrepencyObj", discrepency);
			resultMap.put("startIndex", startIdx);
			resultMap.put("IDiscrepencyTrxValue", discrepencyTrxValue);*/
		} catch (NoSuchDiscrepencyException nsde) {
			CommandProcessingException cpe = new CommandProcessingException(
					nsde.getMessage());
			cpe.initCause(nsde);
			throw cpe;
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException(
					"Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}
//		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, map);
		return returnMap;
	}
}
