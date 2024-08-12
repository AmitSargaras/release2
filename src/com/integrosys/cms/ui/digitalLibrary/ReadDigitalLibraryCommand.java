package com.integrosys.cms.ui.digitalLibrary;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryEntry;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryGroup;
import com.integrosys.cms.app.digitalLibrary.proxy.IDigitalLibraryProxy;
import com.integrosys.cms.app.digitalLibrary.trx.IDigitalLibraryTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * This class implements command
 */
public class ReadDigitalLibraryCommand extends AbstractCommand {
	
	private IDigitalLibraryProxy digitalLibraryProxy;
	
	/**
	 * @return the digitalLibraryProxy
	 */
	public IDigitalLibraryProxy getDigitalLibraryProxy() {
		return digitalLibraryProxy;
	}

	/**
	 * @param digitalLibraryProxy the digitalLibraryProxy to set
	 */
	public void setDigitalLibraryProxy(IDigitalLibraryProxy digitalLibraryProxy) {
		this.digitalLibraryProxy = digitalLibraryProxy;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
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
				{ "digitalLibraryTrxValue", "com.integrosys.cms.app.digitalLibrary.trx.IDigitalLibraryTrxValue", SERVICE_SCOPE }, // Produce
																													// the
																													// offset
																													// .
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// length.
				{ "length", "java.util.Integer", SERVICE_SCOPE }, // To populate
																	// the form.
				{ DigitalLibraryForm.MAPPER, "com.integrosys.cms.app.feed.trx.bond.IDigitalLibraryGroupTrxValue", FORM_SCOPE } });
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

		IDigitalLibraryTrxValue trxValue = null;
		try {
			String event = (String) map.get("event");
			
			try {
				trxValue = getDigitalLibraryProxy().getDigitalLibraryGroup();
			}catch(Exception e) {
				DefaultLogger.error(this, "Exception caught in doExecute()", e);
				//exceptionMap.put("application.exception", e);
			}

			DefaultLogger.debug(this, "after getting bond feed group from proxy.");

			// If this is the very first online read, then there will be
			// no staging records. So copy the actual records as staging
			// records.
			if (trxValue!=null && trxValue.getStagingDigitalLibraryGroup() == null) {
				trxValue.setStagingDigitalLibraryGroup((IDigitalLibraryGroup) CommonUtil.deepClone(trxValue.getDigitalLibraryGroup()));
			}

			if (trxValue!=null && trxValue.getDigitalLibraryGroup().getFeedEntries() == null) {
				trxValue.getDigitalLibraryGroup().setFeedEntries(new IDigitalLibraryEntry[0]);
			}

			if (trxValue!=null && (trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)
					|| trxValue.getStatus().equals(ICMSConstant.STATE_ND) || DigitalLibraryAction.EVENT_READ.equals(event))) {
				// Set the staging to be the same as actual.
				trxValue.setStagingDigitalLibraryGroup((IDigitalLibraryGroup) CommonUtil.deepClone(trxValue.getDigitalLibraryGroup()));
			}

			// Sort the staging entries.
			IDigitalLibraryEntry[] entriesArr =null;
			if(trxValue!=null) {
				entriesArr = trxValue.getStagingDigitalLibraryGroup().getFeedEntries();
			}

			if (trxValue!=null && entriesArr == null) {
				entriesArr = new IDigitalLibraryEntry[0];
				trxValue.getStagingDigitalLibraryGroup().setFeedEntries(entriesArr);
			}
			/*else if (entriesArr.length != 0) {
				Arrays.sort(entriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IDigitalLibraryEntry entry1 = (IDigitalLibraryEntry) a;
						IDigitalLibraryEntry entry2 = (IDigitalLibraryEntry) b;
						if (entry1.getName() == null) {
							entry1.setName("");
						}
						if (entry2.getName() == null) {
							entry2.setName("");
						}
						return entry1.getName().compareTo(entry2.getName());
					}
				});
			}*/
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		resultMap.put("digitalLibraryTrxValue", trxValue);
		resultMap.put("offset", new Integer(0));
		resultMap.put("length", new Integer(10));
		resultMap.put(DigitalLibraryForm.MAPPER, trxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
