/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/bfl/BflCommand.java,v 1.10 2006/02/20 09:29:07 pratheepa Exp $
 */

package com.integrosys.cms.ui.bfl;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ITATEntry;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * This class is used to list the customer details based on some search
 * contsraints
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/02/20 09:29:07 $ Tag: $Name: $
 */
public class BflCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public BflCommand() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }

		});
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "limitObList", "com.integrosys.cms.app.limit.bus.OBLimitProfile", REQUEST_SCOPE },
				{ "reminder", "java.util.String", REQUEST_SCOPE },
				{ "infoMap", "java.util.HashMap", REQUEST_SCOPE },
				{ "tatStamp", "java.util.Date", REQUEST_SCOPE },
				{ "tatList", "java.util.ArrayList", REQUEST_SCOPE },

				// Added by Pratheepa for CR148
				{ "infoMap_issue_draft_bfl", "java.util.HashMap", REQUEST_SCOPE },
				{ "infoMap_send_draft_bfl", "java.util.HashMap", REQUEST_SCOPE },
				{ "infoMap_recv_draft_bfl_ack", "java.util.HashMap", REQUEST_SCOPE },
				{ "infoMap_issue_clean_type_bfl", "java.util.HashMap", REQUEST_SCOPE },
				{ "infoMap_special_issue_clean_type_bfl", "java.util.HashMap", REQUEST_SCOPE },
				{ "infoMap_customer_accept_bfl", "java.util.HashMap", REQUEST_SCOPE },
				{ "infoMap_print_bfl_reminder", "java.util.HashMap", REQUEST_SCOPE }

		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	// Method modified by Pratheepa for CR148
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		try {
			OBLimitProfile ab = new OBLimitProfile();
			String event = (String) map.get("event");
			result.put("event", event);
			boolean reminder = true;
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			DefaultLogger.debug(this, "after setting ob");
			ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			HashMap infoMap = new HashMap();
			HashMap infoMap_issue_draft_bfl = new HashMap();
			HashMap infoMap_send_draft_bfl = new HashMap();
			HashMap infoMap_recv_draft_bfl_ack = new HashMap();
			HashMap infoMap_issue_clean_type_bfl = new HashMap();
			HashMap infoMap_special_issue_clean_type_bfl = new HashMap();
			HashMap infoMap_customer_accept_bfl = new HashMap();
			HashMap infoMap_print_bfl_reminder = new HashMap();

			if ((limitProfileOB != null) && (event != null)) {
				if (event.equals("print_bfl_reminder") || event.equals("view_print_bfl_reminder")) {
					reminder = limitProxy.isCleanBFLReminderRequired(limitProfileOB.getLimitProfileID());
					result.put("reminder", String.valueOf(reminder));
					infoMap = getInformation(limitProfileOB, ICMSConstant.TAT_CODE_PRINT_REMINDER_BFL, locale);
				}
				else if (event.equals("issue_draft_bfl") || event.equals("view_issue_draft_bfl")) {
					infoMap = getInformation(limitProfileOB, ICMSConstant.TAT_CODE_ISSUE_DRAFT_BFL, locale);
				}
				else if (event.equals("send_draft_bfl") || event.equals("view_send_draft_bfl")) {
					infoMap = getInformation(limitProfileOB, ICMSConstant.TAT_CODE_SEND_DRAFT_BFL, locale);
				}
				else if (event.equals("recv_draft_bfl_ack") || event.equals("view_recv_draft_bfl_ack")) {
					infoMap = getInformation(limitProfileOB, ICMSConstant.TAT_CODE_ACK_REC_DRAFT_BFL, locale);
				}
				else if (event.equals("issue_clean_type_bfl") || event.equals("view_issue_clean_type_bfl")) {
					infoMap = getInformation(limitProfileOB, ICMSConstant.TAT_CODE_ISSUE_CLEAN_BFL, locale);
				}
				else if (event.equals("special_issue_clean_type_bfl")
						|| event.equals("view_special_issue_clean_type_bfl")) {
					infoMap = getInformation(limitProfileOB, ICMSConstant.TAT_CODE_SPECIAL_ISSUE_CLEAN_BFL, locale);
				}
				else if (event.equals("customer_accept_bfl") || event.equals("view_customer_accept_bfl")) {
					infoMap = getInformation(limitProfileOB, ICMSConstant.TAT_CODE_CUSTOMER_ACCEPT_BFL, locale);
				}

				else if (event.equals("view_bfl") || event.equals("checker_view_bfl")) {
					infoMap_issue_draft_bfl = getInformation(limitProfileOB, ICMSConstant.TAT_CODE_ISSUE_DRAFT_BFL,
							locale);
					infoMap_send_draft_bfl = getInformation(limitProfileOB, ICMSConstant.TAT_CODE_SEND_DRAFT_BFL,
							locale);
					infoMap_recv_draft_bfl_ack = getInformation(limitProfileOB,
							ICMSConstant.TAT_CODE_ACK_REC_DRAFT_BFL, locale);
					infoMap_issue_clean_type_bfl = getInformation(limitProfileOB,
							ICMSConstant.TAT_CODE_ISSUE_CLEAN_BFL, locale);
					infoMap_special_issue_clean_type_bfl = getInformation(limitProfileOB,
							ICMSConstant.TAT_CODE_SPECIAL_ISSUE_CLEAN_BFL, locale);
					infoMap_customer_accept_bfl = getInformation(limitProfileOB,
							ICMSConstant.TAT_CODE_CUSTOMER_ACCEPT_BFL, locale);

					reminder = limitProxy.isCleanBFLReminderRequired(limitProfileOB.getLimitProfileID());
					result.put("reminder", String.valueOf(reminder));
					infoMap_print_bfl_reminder = getInformation(limitProfileOB,
							ICMSConstant.TAT_CODE_PRINT_REMINDER_BFL, locale);
				}
			}
			result.put("limitObList", ab);
			result.put("infoMap", infoMap);

			result.put("infoMap_issue_draft_bfl", infoMap_issue_draft_bfl);
			result.put("infoMap_send_draft_bfl", infoMap_send_draft_bfl);
			result.put("infoMap_recv_draft_bfl_ack", infoMap_recv_draft_bfl_ack);
			result.put("infoMap_issue_clean_type_bfl", infoMap_issue_clean_type_bfl);
			result.put("infoMap_special_issue_clean_type_bfl", infoMap_special_issue_clean_type_bfl);
			result.put("infoMap_customer_accept_bfl", infoMap_customer_accept_bfl);

			result.put("infoMap_print_bfl_reminder", infoMap_print_bfl_reminder);

			DefaultLogger.debug(this, "Going out of doExecute()");
			temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			return temp;
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}
	}

	private HashMap getInformation(ILimitProfile limitProfileOB, String code, Locale locale) {
		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		HashMap retMap = new HashMap();
		String infoReq = "false";
		Date tatStamp = new Date();
		ArrayList tatList = new ArrayList();
		try {
			if (null != limitProfileOB) {
				ITATEntry tat[] = limitProxy.getTatEntry(limitProfileOB.getLimitProfileID());

				if (tat != null) {
					for (int i = 0; i < tat.length; i++) {
						if ((tat[i].getTATServiceCode() != null) && (tat[i].getTATServiceCode().equals(code))) {
							infoReq = "true";
							if (tat[i].getTATStamp() != null) {
								tatList.add(tat[i]);
							}
						}
					}
				}
			}

			retMap.put("infoReq", infoReq);
			retMap.put("tatStamp", tatStamp);
			retMap.put("tatList", tatList);
			return (retMap);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in getInformation" + e);
			retMap.put("infoReq", infoReq);
			retMap.put("tatStamp", tatStamp);
			return (retMap);
		}
	}
}
