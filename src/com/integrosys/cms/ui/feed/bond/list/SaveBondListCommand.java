/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/SaveBondListCommand.java,v 1.17 2005/08/30 09:49:57 hshii Exp $
 */

package com.integrosys.cms.ui.feed.bond.list;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.bond.BondReplicationUtils;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedGroup;
import com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.feed.bond.BondCommand;

/**
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2005/08/30 09:49:57 $ Tag: $Name: $
 */
public class SaveBondListCommand extends BondCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {

				{ BondListForm.MAPPER, "java.util.List", FORM_SCOPE },
				{ "bondFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, { "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ "bondFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ BondListForm.MAPPER, "com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue", FORM_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get(BondListForm.MAPPER);

			int offset = ((Integer) map.get("offset")).intValue();
			int length = ((Integer) map.get("length")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));
			IBondFeedGroup inputGroup = (IBondFeedGroup) inputList.get(1);
			IBondFeedEntry[] inputEntriesArr = inputGroup.getFeedEntries();

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			// Session-scoped trx value.
			IBondFeedGroupTrxValue value = (IBondFeedGroupTrxValue) map.get("bondFeedGroupTrxValue");
			IBondFeedGroup group = value.getStagingBondFeedGroup();

			IBondFeedGroup replicatedGroup = BondReplicationUtils.replicateBondFeedGroupForCreateStagingCopy(group);

			IBondFeedEntry[] entriesArr = replicatedGroup.getFeedEntries();

			String newBondName = "";
			String oldBondName = "";
			
			String newIsinCode = "";
			String oldIsinCode = "";
			
			String newCurrentUnitPrice = "";
			String oldCurrentUnitPrice = "";
			
			
			String newBondRating = "";
			String oldBondRating = "";
			
			String newCouponRating = "";
			String oldCouponRating = "";
			
			Date newMaturityDate;
			Date oldMaturityDate;
			
			int intDateValue;
			boolean booleanDateValue = true;
			
			for (int i = 0; i < inputEntriesArr.length; i++) {
				for (int j = 0; j < entriesArr.length; j++) {
					if (inputEntriesArr[i].getBondCode().equals(entriesArr[j].getBondCode())) {
						
						newBondName = inputEntriesArr[i].getName();
						oldBondName = entriesArr[j].getName();
				
						newIsinCode = String.valueOf(inputEntriesArr[i].getIsinCode());
						oldIsinCode = String.valueOf(entriesArr[j].getIsinCode());
						
						newCurrentUnitPrice = String.valueOf(inputEntriesArr[i].getUnitPrice());
						oldCurrentUnitPrice = String.valueOf(entriesArr[j].getUnitPrice());
						
						newBondRating = inputEntriesArr[i].getRating();
						oldBondRating = entriesArr[j].getRating();
				
						newCouponRating = String.valueOf(inputEntriesArr[i].getCouponRate());
						oldCouponRating = String.valueOf(entriesArr[j].getCouponRate());
						
						newMaturityDate = inputEntriesArr[i].getMaturityDate();
						oldMaturityDate = entriesArr[j].getMaturityDate();
						
						intDateValue = newMaturityDate.compareTo(oldMaturityDate);
						booleanDateValue = true;
						
						if( intDateValue != 0 )
							booleanDateValue = false;
						
						entriesArr[j].setName(inputEntriesArr[i].getName());
						entriesArr[j].setIsinCode(inputEntriesArr[i].getIsinCode());
						entriesArr[j].setUnitPrice(inputEntriesArr[i].getUnitPrice());
						entriesArr[j].setMaturityDate(inputEntriesArr[i].getMaturityDate());
						entriesArr[j].setRating(inputEntriesArr[i].getRating());
						entriesArr[j].setCouponRate(inputEntriesArr[i].getCouponRate());
						
						if( ! ( newBondName.equals(oldBondName) && newIsinCode.equals(oldIsinCode) && newCurrentUnitPrice.equals(oldCurrentUnitPrice)						
							&& newBondRating.equals(oldBondRating) && newCouponRating.equals(oldCouponRating) && booleanDateValue ))
							entriesArr[j].setLastUpdateDate(new Date());
						else
							entriesArr[j].setLastUpdateDate(inputEntriesArr[i].getLastUpdateDate());
					}
				}
			}

			replicatedGroup.setFeedEntries(entriesArr);
			value.setStagingBondFeedGroup(replicatedGroup);

			DefaultLogger.debug(this, "before: " + value.getVersionTime());

			IBondFeedGroupTrxValue resultValue = null;
			if (value.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
				resultValue = getBondFeedProxy().makerUpdateRejectedBondFeedGroup(trxContext, value);
			}
			else {
				resultValue = getBondFeedProxy().makerUpdateBondFeedGroup(trxContext, value, value.getStagingBondFeedGroup());
			}

			DefaultLogger.debug(this, "after: " + resultValue.getVersionTime());

			entriesArr = resultValue.getStagingBondFeedGroup().getFeedEntries();

			// Sort the array.
			if ((entriesArr != null) && (entriesArr.length != 0)) {
				Arrays.sort(entriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IBondFeedEntry entry1 = (IBondFeedEntry) a;
						IBondFeedEntry entry2 = (IBondFeedEntry) b;
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
			resultValue.getStagingBondFeedGroup().setFeedEntries(entriesArr);
			int entriesLength = 0;
			if (entriesArr != null) {
				entriesLength = entriesArr.length;
			}
			targetOffset = BondListMapper.adjustOffset(targetOffset, length, entriesLength);

			resultMap.put("bondFeedGroupTrxValue", resultValue);
			resultMap.put("request.ITrxValue", resultValue);
			resultMap.put("offset", new Integer(targetOffset));
			resultMap.put(BondListForm.MAPPER, resultValue);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}

}
