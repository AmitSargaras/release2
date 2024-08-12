/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/tat/UpdateBflIndTatCommand.java,v 1.1 2004/09/03 07:38:09 pooja Exp $
 */

package com.integrosys.cms.ui.tat;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * This class is used to list the company borrowers based on some search
 * contsraints
 * @author $Author: pooja $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/09/03 07:38:09 $ Tag: $Name: $
 */
public class UpdateBflIndTatCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public UpdateBflIndTatCommand() {

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
				{ "limitprofileObItem", "com.integrosys.cms.app.limit.bus.OBLimitProfile", FORM_SCOPE },
				{ "trxValue", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "date3", "java.lang.String", FORM_SCOPE }, { "bflRequired", "java.lang.String", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }

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
		return (new String[][] { { "trxRes", "com.integrosys.cms.app.transaction.OBCMSTrxResult", SERVICE_SCOPE },
				{ "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult", REQUEST_SCOPE },
				{ "Error_BFL", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },

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
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ILimitProxy limitproxy = LimitProxyFactory.getProxy();
			OBLimitProfile limitprofileOb = (OBLimitProfile) map.get("limitprofileObItem");
			ICMSCustomer customerOb = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			if (null == customerOb) {
				throw new CommandProcessingException("customerOb is null in session!");
			}
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			DefaultLogger.debug(this, "after getting OBLimit");
			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			OBLimitProfileTrxValue txnvalue = (OBLimitProfileTrxValue) map.get("trxValue");
			theOBTrxContext.setTrxCountryOrigin(txnvalue.getLimitProfile().getOriginatingLocation().getCountryCode());
			theOBTrxContext.setTrxOrganisationOrigin(txnvalue.getLimitProfile().getOriginatingLocation()
					.getOrganisationCode());
			Date dt[] = new Date[2];
			String duedate = new String();
			String event = (String) map.get("event");
			result.put("event", event);

			try {
				int check = 0;
				if (limitprofileOb.getTATCreateDate() != null) {
					if (limitprofileOb.getBFLRequiredInd()) {
						if ((limitprofileOb.getOriginatingLocation().getCountryCode() != null)
								&& (customerOb.getCMSLegalEntity().getCustomerSegment() != null)) {
							dt = limitproxy.getBFLDueDates(limitprofileOb.getRenewalInd(), customerOb
									.getCMSLegalEntity().getCustomerSegment(), limitprofileOb.getOriginatingLocation()
									.getCountryCode(), limitprofileOb.getTATCreateDate());
						}
						if (limitprofileOb.getBCALocalInd()) {
							if (dt[0] != null) {
								duedate = (DateUtil.formatDate(locale, dt[0]));
							}
							else {
								duedate = null;
								check = 1;
							}
						}
						else {
							if (dt[1] != null) {
								duedate = (DateUtil.formatDate(locale, dt[1]));
							}
							else {
								duedate = null;
								check = 1;
							}
						}
					}
					else {
						duedate = "-";// Due date calculation is not required as
										// BFL requiredInd is No
					}
				}
				if (check == 1) {
					if (event.equals("updateBflInd")) {
						result.put("Error_BFL", "Error_BFL");
					}
					else if (event.equals("submit")) {
						result.put("Error_BFL", "Error_Res_BFL");
					}
					else {
						result.put("Error_BFL", "");
					}
					temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
					temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

					return temp;
				}
			}
			catch (Exception e) {
				temp.put("Error_BFL", "Error_BFL");
				DefaultLogger.debug(this, "got exception in doExecute" + e);
				return temp;
			}
			DefaultLogger.debug(this, "limitprofileOb date for update is " + limitprofileOb.getBflIndUpdateDate());
			// DefaultLogger.debug(this, "before calling proxy"+limitprofileOb);
			// DefaultLogger.debug(this,
			// "before calling proxy::TrxValue is:"+txnvalue);
			txnvalue.setTransactionSubType("TAT");
			ICMSTrxResult resultOb = limitproxy.makerUpdateLimitProfile(theOBTrxContext, txnvalue, limitprofileOb);
			DefaultLogger.debug(this, "before putting result");
			result.put("trxRes", resultOb);
			DefaultLogger.debug(this, "after putting result");
			result.put("request.ITrxResult", resultOb);
		}
		catch (LimitException e) {
			DefaultLogger.debug(this, "Error_BFL" + "Error_BFL");
			temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			temp.put("Error_BFL", "Error_BFL");
			return temp;
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;

	}

}
