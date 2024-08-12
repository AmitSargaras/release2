/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/ApproveBondListCommand.java,v 1.9 2005/08/30 09:49:57 hshii Exp $
 */
package com.integrosys.cms.ui.digitalLibrary;

import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryEntry;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryGroup;
import com.integrosys.cms.app.digitalLibrary.bus.OBDigitalLibraryEntry;
import com.integrosys.cms.app.digitalLibrary.proxy.IDigitalLibraryProxy;
import com.integrosys.cms.app.digitalLibrary.trx.IDigitalLibraryTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: hshii $
 * @version $Revision: 1.9 $
 * @since $Date: 2005/08/30 09:49:57 $ Tag: $Name: $
 */
public class ApproveDigitalLibraryCommand extends AbstractCommand {
	
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
		return new String[][] {
				// Consume the current feed entries to be saved as a whole.
				{ "digitalLibraryTrxValue", "com.integrosys.cms.app.digitalLibrary.trx.IDigitalLibraryTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.feed.trx.bond.IDigitalLibraryTrxValue",
				REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			IDigitalLibraryTrxValue value = (IDigitalLibraryTrxValue) map.get("digitalLibraryTrxValue");

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			if (trxContext == null) {
				DefaultLogger.debug(this, "trxContext obtained from map is null.");
			}

			IDigitalLibraryGroup group = value.getDigitalLibraryGroup();
			IDigitalLibraryGroup stageGroup = value.getStagingDigitalLibraryGroup();
			IDigitalLibraryEntry[] actualEntries = null;
			IDigitalLibraryEntry[] stageEntries = null;
			if (group != null) {
				actualEntries = group.getFeedEntries();
			}
			if (stageGroup != null) {
				stageEntries = stageGroup.getFeedEntries();
			}
			
			if(group.getFeedEntries()==null){
				DefaultLogger.debug(this, "///////////////////////////group is null //////////");
			}else{
				DefaultLogger.debug(this, "///////////////////////////group is not null //////////");
			}

			if(null != stageEntries)
			{
				for (int i = 0; i < stageEntries.length; i++) {
					//OBDigitalLibraryEntry entryOb =  (OBDigitalLibraryEntry) stageEntries[i];
					String climsDocCategory =  stageEntries[i].getClimsDocCategory();
					if(("Facility").equalsIgnoreCase(climsDocCategory))
					{
						stageEntries[i].setClimsDocCategoryCode("F");
					}
					else if(("CAM").equalsIgnoreCase(climsDocCategory))
					{
						stageEntries[i].setClimsDocCategoryCode("CAM");
					}
					else if(("Recurrent").equalsIgnoreCase(climsDocCategory))
					{
						stageEntries[i].setClimsDocCategoryCode("REC");
					}
					else if(("Other").equalsIgnoreCase(climsDocCategory))
					{
						stageEntries[i].setClimsDocCategoryCode("O");
					}
					else if(("Security").equalsIgnoreCase(climsDocCategory))
					{
						stageEntries[i].setClimsDocCategoryCode("S");
					}
				
				}
			}
			stageEntries = updateLastUpdateDate(actualEntries, stageEntries);
			stageGroup.setFeedEntries(stageEntries);
			value.setStagingDigitalLibraryGroup(stageGroup);


			value = getDigitalLibraryProxy().checkerApproveDigitalLibraryGroup(trxContext, value);

			resultMap.put("request.ITrxValue", value);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}

	private IDigitalLibraryEntry[] updateLastUpdateDate(IDigitalLibraryEntry[] actualEntries, IDigitalLibraryEntry[] stageEntries) {
		if ((actualEntries == null) || (actualEntries.length == 0)) {
			if (stageEntries != null) {
				for (int i = 0; i < stageEntries.length; i++) {
					stageEntries[i].setLastUpdatedDate(new Date());
				}
			}
		}
		else if ((actualEntries != null) && (stageEntries != null)) {
			HashMap actualMap = new HashMap();
			for (int i = 0; i < actualEntries.length; i++) {
				actualMap.put(String.valueOf(actualEntries[i].getDigitalLibraryEntryRef()), actualEntries[i]);
			}
			for (int i = 0; i < stageEntries.length; i++) {
				IDigitalLibraryEntry actual = (IDigitalLibraryEntry) actualMap.get(String.valueOf(stageEntries[i]
						.getDigitalLibraryEntryRef()));
				if ((actual == null) || (actual.getDigiLibDocCategory() != stageEntries[i].getDigiLibDocCategory())) {
					stageEntries[i].setLastUpdatedDate(new Date());
				}
			}
		}
		return stageEntries;
	}

}
