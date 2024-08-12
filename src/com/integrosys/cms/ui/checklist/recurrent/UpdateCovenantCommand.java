/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recurrent;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.recurrent.bus.ConvenantComparator;
import com.integrosys.cms.app.recurrent.bus.IConvenant;
import com.integrosys.cms.app.recurrent.bus.IConvenantSubItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;

/**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2006/11/16 07:01:19 $ Tag: $Name: $
 */
public class UpdateCovenantCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public UpdateCovenantCommand() {
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
				{ "covenantItem", "com.integrosys.cms.app.recurrent.bus.IConvenant", FORM_SCOPE },
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
			IConvenant covenant = (IConvenant) map.get("covenantItem");
			String index = (String) map.get("index");
			IRecurrentCheckList recChkLst = (IRecurrentCheckList) map.get("recChkLst");

			IConvenant storedCovItem = recChkLst.getConvenantList()[Integer.parseInt(index)];
			String storedCovFreqUnit = (storedCovItem.getFrequencyUnit() == null) ? "NA" : storedCovItem
					.getFrequencyUnit();
			String storedCovGraceUnit = (storedCovItem.getGracePeriodUnit() == null) ? "NA" : storedCovItem
					.getGracePeriodUnit();
			boolean isEndDateUpdateRequired = ((storedCovItem.getInitialDocEndDate() != covenant.getInitialDocEndDate())
					|| (storedCovItem.getFrequency() != covenant.getFrequency())
					|| (!storedCovFreqUnit.equals(covenant.getFrequencyUnit()))
					|| (storedCovItem.getGracePeriod() != covenant.getGracePeriod())
					|| (!storedCovGraceUnit.equals(covenant.getGracePeriodUnit())) || (storedCovItem
					.getLastDocEntryDate() != covenant.getLastDocEntryDate()));

			if (isEndDateUpdateRequired) {
				covenant.setEndDateChangedCount(covenant.getEndDateChangedCount() + 1);
			}

			recChkLst.updateConvenant(Integer.parseInt(index), covenant);

			// CMSSP-701 To Update Pending Items only
			IConvenantSubItem subItemList[] = covenant
					.getSubItemsByCondition(ICMSConstant.RECCOV_SUB_ITEM_COND_PENDING);
			Date nextEndDate = covenant.getInitialDocEndDate();
			Date nextDueDate = covenant.getInitialDueDate();
			Date lastDocEntryDate = covenant.getLastDocEntryDate();

			Date comparisonDate = null;
			if (covenant.getFrequencyUnit() != null) {
				comparisonDate = CommonUtil.rollUpDate(DateUtil.getDate(), covenant.getFrequency() * 2, covenant
						.getFrequencyUnit());
			}

			DefaultLogger.debug(this, "%%%%%%%% comparisonDate: %%%%%%%%" + comparisonDate);
			for (int i = 0; i < subItemList.length; i++) {
				if (((comparisonDate != null) && (comparisonDate.after(nextEndDate) || comparisonDate
						.equals(nextEndDate)))
						&& ((lastDocEntryDate == null) || ((lastDocEntryDate != null) && (lastDocEntryDate
								.after(nextEndDate) || lastDocEntryDate.equals(nextEndDate))))) {
					if (isEndDateUpdateRequired) {
						subItemList[i].setDocEndDate(nextEndDate);
						subItemList[i].setDueDate(nextDueDate);
						subItemList[i].setFrequency(covenant.getFrequency());
						subItemList[i].setFrequencyUnit(covenant.getFrequencyUnit());
						subItemList[i].setGracePeriod(covenant.getGracePeriod());
						subItemList[i].setGracePeriodUnit(covenant.getGracePeriodUnit());
					}

					if (!covenant.getIsOneOffInd()
							&& (covenant.getFrequency() != Integer.MIN_VALUE)
							&& ((covenant.getFrequencyUnit() != null) || (covenant.getFrequencyUnit().trim().length() != 0))) {
						nextEndDate = CommonUtil.rollUpDate(nextEndDate, covenant.getFrequency(), covenant
								.getFrequencyUnit());
						if ((covenant.getGracePeriodUnit() == null)
								|| (covenant.getGracePeriodUnit().trim().length() == 0)) {
							nextDueDate = nextEndDate;
						}
						else {
							nextDueDate = CommonUtil.rollUpDate(nextEndDate, covenant.getGracePeriod(), covenant
									.getGracePeriodUnit());
						}
					}
					subItemList[i].setRemarks(covenant.getRemarks()); // sets
					// the
					// new
					// remarks
					// into
					// the
					// sub
					// -item
					subItemList[i].setIsDeletedInd(false);
				}
				else {
					subItemList[i].setIsDeletedInd(true);
				}
			}

			IConvenant conList[] = recChkLst.getConvenantList();
			if (conList != null) {
				// Added by Pratheepa for CR234
				Arrays.sort(conList, new ConvenantComparator());
			}
			recChkLst.setConvenantList(conList);
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