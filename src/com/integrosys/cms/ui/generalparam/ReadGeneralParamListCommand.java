package com.integrosys.cms.ui.generalparam;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.generalparam.proxy.IGeneralParamProxy;
import com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.OBCommonUser;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 * This class implements command
 */
public class ReadGeneralParamListCommand extends AbstractCommand {
	
	private IGeneralParamProxy generalParamProxy;

	public IGeneralParamProxy getGeneralParamProxy() {
		return generalParamProxy;
	}

	public void setGeneralParamProxy(IGeneralParamProxy generalParamProxy) {
		this.generalParamProxy = generalParamProxy;
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
			{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {// Produce all the feed entries.
			{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
		{ "generalParamGroupTrxValue", "com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue", SERVICE_SCOPE }, // Produce
																													// the
																													// offset
																													// .
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// length.
				{ "length", "java.util.Integer", SERVICE_SCOPE }, // To populate
																	// the form.
				{ GeneralParamListForm.MAPPER, "com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue", FORM_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		IGeneralParamGroupTrxValue trxValue = null;
		try {
			String event = (String) map.get("event");
			
			OBCommonUser user = (OBCommonUser) map.get(IGlobalConstant.GLOBAL_LOS_USER);
			trxValue = getGeneralParamProxy().getGeneralParamGroup();

			DefaultLogger.debug(this, "after getting mutual funds feed group from proxy.");

			// If this is the very first online read, then there will be
			// no staging records. So copy the actual records as staging
			// records.
			if (trxValue.getStagingGeneralParamGroup() == null) {
				trxValue.setStagingGeneralParamGroup((IGeneralParamGroup) CommonUtil.deepClone(trxValue.getGeneralParamGroup()));
			}

			if (trxValue.getGeneralParamGroup().getFeedEntries() == null) {
				trxValue.getGeneralParamGroup().setFeedEntries(new IGeneralParamEntry[0]);
			}

			if (trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)
					|| trxValue.getStatus().equals(ICMSConstant.STATE_ND) || GeneralParamListAction.EVENT_READ.equals(event)) {
				// Set the staging to be the same as actual.
				trxValue.setStagingGeneralParamGroup((IGeneralParamGroup) CommonUtil.deepClone(trxValue.getGeneralParamGroup()));
			}

			// Sort the staging entries.
			//IGeneralParamEntry[] entriesArr = trxValue.getStagingGeneralParamGroup().getFeedEntries();
			IGeneralParamDao paramDao = (IGeneralParamDao)BeanHouse.get("generalParamDao");
			IGeneralParamEntry[] stageEntriesArr = paramDao.getGeneralParamEntries("CMS_STAGE_GENERAL_PARAM",String.valueOf(user.getTeamTypeMembership().getMembershipID()));
			IGeneralParamEntry[] actualEntriesArr = paramDao.getGeneralParamEntries("CMS_GENERAL_PARAM",String.valueOf(user.getTeamTypeMembership().getMembershipID()));
			if (stageEntriesArr == null) {
				stageEntriesArr = new IGeneralParamEntry[0];
				trxValue.getStagingGeneralParamGroup().setFeedEntries(stageEntriesArr);
			}			
			else if (stageEntriesArr.length != 0) {
				trxValue.getStagingGeneralParamGroup().setFeedEntries(stageEntriesArr);
				Arrays.sort(stageEntriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IGeneralParamEntry entry1 = (IGeneralParamEntry) a;
						IGeneralParamEntry entry2 = (IGeneralParamEntry) b;
						if (entry1.getParamName() == null) {
							entry1.setParamName("");
						}
						if (entry2.getParamName() == null) {
							entry2.setParamName("");
						}
						return entry1.getParamName().compareTo(entry2.getParamName());
					}
				});
			}
			
			if (actualEntriesArr == null) {
				actualEntriesArr = new IGeneralParamEntry[0];
				trxValue.getGeneralParamGroup().setFeedEntries(actualEntriesArr);
			}			
			else if (actualEntriesArr.length != 0) {
				trxValue.getGeneralParamGroup().setFeedEntries(actualEntriesArr);
				Arrays.sort(actualEntriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IGeneralParamEntry entry1 = (IGeneralParamEntry) a;
						IGeneralParamEntry entry2 = (IGeneralParamEntry) b;
						if (entry1.getParamName() == null) {
							entry1.setParamName("");
						}
						if (entry2.getParamName() == null) {
							entry2.setParamName("");
						}
						return entry1.getParamName().compareTo(entry2.getParamName());
					}
				});
			}
			
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		resultMap.put("generalParamGroupTrxValue", trxValue);
		resultMap.put("offset", new Integer(0));
		resultMap.put("length", new Integer(10));
		resultMap.put(GeneralParamListForm.MAPPER, trxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
