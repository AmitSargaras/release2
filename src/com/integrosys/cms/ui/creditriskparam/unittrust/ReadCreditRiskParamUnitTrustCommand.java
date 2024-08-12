package com.integrosys.cms.ui.creditriskparam.unittrust;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.creditriskparam.CreditRiskParamType;
import com.integrosys.cms.app.creditriskparam.bus.CreditRiskParamGroupException;
import com.integrosys.cms.app.creditriskparam.bus.ICreditRiskParamGroup;
import com.integrosys.cms.app.creditriskparam.bus.OBCreditRiskParam;
import com.integrosys.cms.app.creditriskparam.proxy.CreditRiskParamProxyManagerFactory;
import com.integrosys.cms.app.creditriskparam.proxy.ICreditRiskParamProxy;
import com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.OBCreditRiskParamGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * ReadCreditRiskParamUnitTrustCommand Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class ReadCreditRiskParamUnitTrustCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ CreditRiskParamUnitTrustForm.MAPPER,
						"com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue", FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
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
				{ "creditRiskParamGroupTrxValue",
						"com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue", SERVICE_SCOPE }, // Produce
																														// the
																														// offset
																														// .
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// length.
				{ "length", "java.util.Integer", SERVICE_SCOPE }, // To populate
																	// the form.
				{ CreditRiskParamUnitTrustForm.MAPPER,
						"com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue", FORM_SCOPE } });
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

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		ICreditRiskParamGroupTrxValue trxValue = new OBCreditRiskParamGroupTrxValue();

		ICreditRiskParamGroupTrxValue inputValue = (ICreditRiskParamGroupTrxValue) map
				.get(CreditRiskParamUnitTrustForm.MAPPER);

		ICreditRiskParamGroup group = inputValue.getCreditRiskParamGroup();

		String trxId = inputValue.getTransactionID();
		String event = (String) map.get("event");
		try {

			DefaultLogger.debug(this, " group.getCreditRiskParamGroupID() = " + group.getCreditRiskParamGroupID());

			DefaultLogger.debug(this, "the trx id = " + trxId);

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			ICreditRiskParamProxy proxy = CreditRiskParamProxyManagerFactory.getICreditRiskParamProxy();

			if ((trxId == null) || trxId.equals("")) {
				trxValue.setReferenceID(Long.toString(group.getCreditRiskParamGroupID()));
				trxValue = (ICreditRiskParamGroupTrxValue) proxy.makerReadCreditRiskParam(trxContext, trxValue, null,
						CreditRiskParamType.UNIT_TRUST);
			}
			else {

				trxValue.setTransactionID(trxId);
				if (CreditRiskParamUnitTrustAction.EVENT_READ_CHECKER_APPROVE_REJECT.equals(event)) {
					trxValue = (ICreditRiskParamGroupTrxValue) proxy.checkerViewCreditRiskParam(trxContext, trxValue,
							null, CreditRiskParamType.UNIT_TRUST);
				}
				else {
					trxValue = (ICreditRiskParamGroupTrxValue) proxy.makerReadRejectedCreditRiskParam(trxContext,
							trxValue, null, CreditRiskParamType.UNIT_TRUST);
				}
			}

			DefaultLogger.debug(this, "the group feed Id = " + group.getCreditRiskParamGroupID());
			DefaultLogger.debug(this, "after getting unit trust feed group from proxy.");

			// If this is the very first online read, then there will be
			// no staging records. So copy the actual records as staging
			// records.
			if (trxValue.getStagingCreditRiskParamGroup() == null) {
				trxValue.setStagingCreditRiskParamGroup((ICreditRiskParamGroup) CommonUtil.deepClone(trxValue
						.getCreditRiskParamGroup()));
			}

			if (trxValue.getCreditRiskParamGroup().getFeedEntries() == null) {
				trxValue.getCreditRiskParamGroup().setFeedEntries(new OBCreditRiskParam[0]);
			}

			if (trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)
					|| trxValue.getStatus().equals(ICMSConstant.STATE_ND)
					|| CreditRiskParamUnitTrustAction.EVENT_READ.equals(event)) {
				// Set the staging to be the same as actual.
				trxValue.setStagingCreditRiskParamGroup((ICreditRiskParamGroup) CommonUtil.deepClone(trxValue
						.getCreditRiskParamGroup()));
			}

			// Sort the staging entries.
			OBCreditRiskParam[] entriesArr = trxValue.getStagingCreditRiskParamGroup().getFeedEntries();

			if (entriesArr == null) {
				entriesArr = new OBCreditRiskParam[0];
				trxValue.getStagingCreditRiskParamGroup().setFeedEntries(entriesArr);
			}
			else if (entriesArr.length != 0) {
				Arrays.sort(entriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						OBCreditRiskParam entry1 = (OBCreditRiskParam) a;
						OBCreditRiskParam entry2 = (OBCreditRiskParam) b;
						if (entry1.getName() == null) {
							entry1.setName("");
						}
						if (entry2.getName() == null) {
							entry2.setName("");
						}
						return entry1.getName().compareTo(entry2.getName());
					}
				});
			}
		}
		catch (CreditRiskParamGroupException e) {
			// if (e.getErrorCode().equals(IUnitTrustFeedProxy.NO_FEED_GROUP)) {
			// DefaultLogger.error(this, "no feed group found.");
			// if (trxValue == null) {
			// trxValue = new OBCreditRiskParamGroupTrxValue();
			// }
			// exceptionMap.put("countryCode",
			// new ActionMessage(FeedConstants.INFO_MISSING_SETUP_DATA));
			// } else {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
			// }
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		resultMap.put("creditRiskParamGroupTrxValue", trxValue);
		resultMap.put("offset", new Integer(0));
		resultMap.put("length", new Integer(10));
		resultMap.put(CreditRiskParamUnitTrustForm.MAPPER, trxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}

}
