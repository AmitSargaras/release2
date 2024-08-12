/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/checklist/recurrent/UpdateRecurrentCommand.java,v 1.10 2006/11/16 07:01:19 jychong Exp $
 */
package com.integrosys.cms.ui.checklist.recurrent;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem;

/**
 * Description
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/11/16 07:01:19 $ Tag: $Name: $
 */
public class UpdateRecurrentCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public UpdateRecurrentCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "recChkLst", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList", SERVICE_SCOPE },
				{ "recurrentItem", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem", FORM_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "recChkLst", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList",
				SERVICE_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			IRecurrentCheckListItem recurrentItem = (IRecurrentCheckListItem) map.get("recurrentItem");
			String index = (String) map.get("index");
			IRecurrentCheckList recChkLst = (IRecurrentCheckList) map.get("recChkLst");

			IRecurrentCheckListItem storedRecItem = recChkLst.getCheckListItemList()[Integer.parseInt(index)];
			String storedRecItmFreqUnit = (storedRecItem.getFrequencyUnit() == null) ? "NA" : storedRecItem
					.getFrequencyUnit();
			String storedRecItmGraceUnit = (storedRecItem.getGracePeriodUnit() == null) ? "NA" : storedRecItem
					.getGracePeriodUnit();
			boolean isEndDateUpdateRequired = ((storedRecItem.getInitialDocEndDate() != recurrentItem
					.getInitialDocEndDate())
					|| (storedRecItem.getFrequency() != recurrentItem.getFrequency())
					|| (!storedRecItmFreqUnit.equals(recurrentItem.getFrequencyUnit()))
					|| (storedRecItem.getGracePeriod() != recurrentItem.getGracePeriod())
					|| (!storedRecItmGraceUnit.equals(recurrentItem.getGracePeriodUnit())) || (storedRecItem
					.getLastDocEntryDate() != recurrentItem.getLastDocEntryDate()));

			if (isEndDateUpdateRequired) {
				recurrentItem.setEndDateChangedCount(recurrentItem.getEndDateChangedCount() + 1);
			}
			recChkLst.updateItem(Integer.parseInt(index), recurrentItem);
			// CMSSP-701: To Update Pending Items Only
			IRecurrentCheckListSubItem aPendingSubItemList[] = recurrentItem
					.getSubItemsByCondition(ICMSConstant.RECCOV_SUB_ITEM_COND_PENDING);

			java.util.Date nextEndDate = recurrentItem.getInitialDocEndDate();
			java.util.Date nextDueDate = recurrentItem.getInitialDueDate();
			java.util.Date lastDocEntryDate = recurrentItem.getLastDocEntryDate();

			java.util.Date comparisonDate = null;
			if (recurrentItem.getFrequencyUnit() != null) {
				comparisonDate = CommonUtil.rollUpDate(DateUtil.getDate(), recurrentItem.getFrequency() * 2,
						recurrentItem.getFrequencyUnit());
			}

			for (int i = 0; i < aPendingSubItemList.length; i++) {
				if (((comparisonDate != null) && (comparisonDate.after(nextEndDate) || comparisonDate
						.equals(nextEndDate)))
						&& ((lastDocEntryDate == null) || ((lastDocEntryDate != null) && (lastDocEntryDate
								.after(nextEndDate) || lastDocEntryDate.equals(nextEndDate))))) {
					if (isEndDateUpdateRequired) {
						aPendingSubItemList[i].setDocEndDate(nextEndDate);
						aPendingSubItemList[i].setDueDate(nextDueDate);
						aPendingSubItemList[i].setFrequency(recurrentItem.getFrequency());
						aPendingSubItemList[i].setFrequencyUnit(recurrentItem.getFrequencyUnit());
						aPendingSubItemList[i].setGracePeriod(recurrentItem.getGracePeriod());
						aPendingSubItemList[i].setGracePeriodUnit(recurrentItem.getGracePeriodUnit());
					}

					// fix for null frequency unit for one-off case
					if (!recurrentItem.getIsOneOffInd()
							&& (recurrentItem.getFrequency() != Integer.MIN_VALUE)
							&& ((recurrentItem.getFrequencyUnit() != null) || (recurrentItem.getFrequencyUnit().trim()
									.length() != 0))) {
						nextEndDate = CommonUtil.rollUpDate(nextEndDate, recurrentItem.getFrequency(), recurrentItem
								.getFrequencyUnit());
						if ((recurrentItem.getGracePeriodUnit() == null)
								|| (recurrentItem.getGracePeriodUnit().trim().length() == 0)) {
							nextDueDate = nextEndDate;
						}
						else {
							nextDueDate = CommonUtil.rollUpDate(nextEndDate, recurrentItem.getGracePeriod(),
									recurrentItem.getGracePeriodUnit());
						}
					}
					// cms 1544 - to propagate checklist comments to all subitem
					// checklists with pending status.
					aPendingSubItemList[i].setRemarks(recurrentItem.getRemarks()); // sets
					// the
					// new
					// remarks
					// into
					// the
					// sub
					// -
					// item
					aPendingSubItemList[i].setIsDeletedInd(false);
				}
				else {
					aPendingSubItemList[i].setIsDeletedInd(true);
				}
			}

			resultMap.put("recChkLst", recChkLst);
		}
		catch (Exception e) {

			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}