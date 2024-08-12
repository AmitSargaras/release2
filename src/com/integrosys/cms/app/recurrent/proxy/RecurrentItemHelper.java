package com.integrosys.cms.app.recurrent.proxy;

import java.util.Date;
import java.util.List;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.recurrent.bus.IConvenant;
import com.integrosys.cms.app.recurrent.bus.IConvenantSubItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem;
import com.integrosys.cms.app.recurrent.bus.OBConvenantSubItem;
import com.integrosys.cms.app.recurrent.bus.OBRecurrentCheckListSubItem;
import com.integrosys.cms.app.recurrent.bus.RecurrentException;

/**
 * Recurrent Proxy helper class to deal with item and sub item.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class RecurrentItemHelper {

	public static void createNonDefaultSet(List aList, IRecurrentCheckListItem anItem) throws RecurrentException {
		IRecurrentCheckListSubItem[] subItems = anItem.getRecurrentCheckListSubItemList();
		Date nextEndDate = subItems[0].getDocEndDate();
		Date nextDueDate = subItems[0].getDueDate();
		IRecurrentCheckListSubItem subItem = null;
		Date lastDocEntryDate = anItem.getLastDocEntryDate();
		Date currentDate = DateUtil.clearTime(DateUtil.getDate());
		if ((anItem.getFrequencyUnit() == null) || ((lastDocEntryDate != null) && currentDate.after(lastDocEntryDate))) {
			return;
		}

		Date compDate = getComparisonDate(DateUtil.getDate(), anItem.getFrequencyUnit(), anItem.getFrequency());
		boolean oneOff = anItem.getIsOneOffInd();

		if (!oneOff || (oneOff && (subItems.length < 1))) {
			int countGenerated = 0;
			while (true) {
				if (oneOff && (countGenerated >= 1)) {
					return;
				}

				if (generateNextDueDate(compDate, nextEndDate)) {
					nextEndDate = recomputeDate(nextEndDate, anItem.getFrequency(), anItem.getFrequencyUnit());
					nextDueDate = recomputeDueDate(nextEndDate, anItem.getGracePeriod(), anItem.getGracePeriodUnit());

					if ((oneOff && (countGenerated == 0))
							|| (!oneOff && (anItem.getFrequency() > 0) && (anItem.getFrequencyUnit() != null))) {
						if ((lastDocEntryDate == null)
								|| ((lastDocEntryDate != null) && (lastDocEntryDate.after(nextEndDate) || lastDocEntryDate
										.equals(nextEndDate)))) {
							subItem = new OBRecurrentCheckListSubItem();
							subItem.setDocEndDate(nextEndDate);
							subItem.setDueDate(nextDueDate);
							subItem.setFrequency(anItem.getFrequency());
							subItem.setFrequencyUnit(anItem.getFrequencyUnit());
							subItem.setGracePeriod(anItem.getGracePeriod());
							subItem.setGracePeriodUnit(anItem.getGracePeriodUnit());
							subItem.setRemarks(anItem.getRemarks());

							aList.add(subItem); // add a new sub-item

							countGenerated++;
						}
						else {
							return;
						}
					}
					else {
						return;
					}
				}
				else {
					return;
				}
			}
		}
	}

	public static void createNonDefaultSet(List aList, IConvenant anItem) throws RecurrentException {
		IConvenantSubItem[] subItems = anItem.getConvenantSubItemList();
		Date nextEndDate = subItems[0].getDocEndDate();
		Date nextDueDate = subItems[0].getDueDate();
		IConvenantSubItem subItem = null;
		Date lastDocEntryDate = anItem.getLastDocEntryDate();
		Date currentDate = DateUtil.clearTime(DateUtil.getDate());
		if ((anItem.getFrequencyUnit() == null) || ((lastDocEntryDate != null) && currentDate.after(lastDocEntryDate))) {
			return;
		}

		Date compDate = getComparisonDate(DateUtil.getDate(), anItem.getFrequencyUnit(), anItem.getFrequency());
		boolean oneOff = anItem.getIsOneOffInd();

		if (!oneOff || (oneOff && (subItems.length < 1))) {
			int countGenerated = 0;
			while (true) {

				if (oneOff && (countGenerated >= 1)) {
					return;
				}

				if (generateNextDueDate(compDate, nextEndDate)) {
					nextEndDate = recomputeDate(nextEndDate, anItem.getFrequency(), anItem.getFrequencyUnit());
					nextDueDate = recomputeDueDate(nextEndDate, anItem.getGracePeriod(), anItem.getGracePeriodUnit());
					if ((oneOff && (countGenerated == 0))
							|| (!oneOff && (anItem.getFrequency() > 0) && (anItem.getFrequencyUnit() != null))) {

						if ((lastDocEntryDate == null)
								|| ((lastDocEntryDate != null) && (lastDocEntryDate.after(nextEndDate) || lastDocEntryDate
										.equals(nextEndDate)))) {
							subItem = new OBConvenantSubItem();
							subItem.setDocEndDate(nextEndDate);
							subItem.setDueDate(nextDueDate);
							subItem.setFrequency(anItem.getFrequency());
							subItem.setFrequencyUnit(anItem.getFrequencyUnit());
							subItem.setGracePeriod(anItem.getGracePeriod());
							subItem.setGracePeriodUnit(anItem.getGracePeriodUnit());
							subItem.setRemarks(anItem.getRemarks());
							aList.add(subItem); // add a new sub-item
							countGenerated++;
						}
						else {
							return;
						}
					}
					else {
						return;
					}
				}
				else {
					return;
				}
			}
		}
	}

	public static boolean generateNextDueDate(Date aCompDate, Date aDocEndDate) {
		int result = aCompDate.compareTo(aDocEndDate);
		return (result >= 0);
	}

	public static Date getComparisonDate(Date aDate, String aFreqUnit, int aFreq) throws RecurrentException {
		if (ICMSConstant.FREQ_UNIT_DAYS.equals(aFreqUnit)) {
			return CommonUtil.rollUpDateByDays(aDate, aFreq);
		}
		if (ICMSConstant.FREQ_UNIT_WEEKS.equals(aFreqUnit)) {
			return CommonUtil.rollUpDateByWeeks(aDate, aFreq);
		}
		if (ICMSConstant.FREQ_UNIT_MONTHS.equals(aFreqUnit)) {
			return CommonUtil.rollUpDateByMonths(aDate, aFreq);
		}
		if (ICMSConstant.FREQ_UNIT_YEARS.equals(aFreqUnit)) {
			return CommonUtil.rollUpDateByYears(aDate, aFreq);
		}
		throw new RecurrentException("Frequency unit " + aFreqUnit + " is invalid.");
	}

	public static void createDefaultSet(List aList, IRecurrentCheckListItem anItem) throws RecurrentException {
		Date nextEndDate = null;
		Date nextDueDate = null;
		IRecurrentCheckListSubItem subItem = null;
		boolean oneOff = anItem.getIsOneOffInd();
		Date lastDocEntryDate = anItem.getLastDocEntryDate();

		for (int ii = 0; ii < getNoOfSubItemToCreate(oneOff); ii++) {
			if ((anItem.getFrequencyUnit() == null) && (ii == 1)) {
				return;
			}

			if (nextEndDate == null) {
				nextEndDate = anItem.getInitialDocEndDate();
				nextDueDate = anItem.getInitialDueDate();
			}
			else {
				nextEndDate = recomputeDate(nextEndDate, anItem.getFrequency(), anItem.getFrequencyUnit());
				nextDueDate = recomputeDueDate(nextEndDate, anItem.getGracePeriod(), anItem.getGracePeriodUnit());
			}

			if ((lastDocEntryDate == null) || ((lastDocEntryDate != null) && !nextEndDate.after(lastDocEntryDate))) {
				subItem = new OBRecurrentCheckListSubItem();
				subItem.setDocEndDate(nextEndDate);
				subItem.setDueDate(nextDueDate);
				subItem.setFrequency(anItem.getFrequency());
				subItem.setFrequencyUnit(anItem.getFrequencyUnit());
				subItem.setGracePeriod(anItem.getGracePeriod());
				subItem.setGracePeriodUnit(anItem.getGracePeriodUnit());
				subItem.setRemarks(anItem.getRemarks());
				aList.add(subItem);
			}
		}
	}

	public static void createDefaultSet(List aList, IConvenant anItem) throws RecurrentException {
		Date nextEndDate = null;
		Date nextDueDate = null;
		IConvenantSubItem subItem = null;
		boolean oneOff = anItem.getIsOneOffInd();
		Date lastDocEntryDate = anItem.getLastDocEntryDate();

		for (int ii = 0; ii < getNoOfSubItemToCreate(oneOff); ii++) {
			if ((anItem.getFrequencyUnit() == null) && (ii == 1)) {
				return;
			}
			subItem = new OBConvenantSubItem();
			if (nextEndDate == null) {

				if (anItem.getDateChecked() != null) {
					subItem.setCheckedDate(anItem.getDateChecked());
				}
				subItem.setIsVerifiedInd(anItem.getIsVerifiedInd());

				nextEndDate = anItem.getInitialDocEndDate();
				nextDueDate = anItem.getInitialDueDate();
			}
			else {
				nextEndDate = recomputeDate(nextEndDate, anItem.getFrequency(), anItem.getFrequencyUnit());
				nextDueDate = recomputeDueDate(nextEndDate, anItem.getGracePeriod(), anItem.getGracePeriodUnit());
			}

			if ((lastDocEntryDate == null) || ((lastDocEntryDate != null) && !nextEndDate.after(lastDocEntryDate))) {

				// subItem = new OBConvenantSubItem();
				subItem.setDocEndDate(nextEndDate);
				subItem.setDueDate(nextDueDate);
				subItem.setFrequency(anItem.getFrequency());
				subItem.setFrequencyUnit(anItem.getFrequencyUnit());
				subItem.setGracePeriod(anItem.getGracePeriod());
				subItem.setGracePeriodUnit(anItem.getGracePeriodUnit());
				subItem.setRemarks(anItem.getRemarks());
				aList.add(subItem);
			}
		}
	}

	public static int getNoOfSubItemToCreate(boolean oneOff) {
		return (oneOff) ? 1 : 2;
	}

	public static Date recomputeDueDate(Date aDate, int aFreq, String aFreqUnit) throws RecurrentException {
		if ((aFreqUnit == null) || (aFreqUnit.trim().length() == 0)) {
			return aDate;
		}

		return recomputeDate(aDate, aFreq, aFreqUnit);
	}

	public static Date recomputeDate(Date aDate, int aFreq, String aFreqUnit) throws RecurrentException {
		if (ICMSConstant.FREQ_UNIT_DAYS.equals(aFreqUnit)) {
			return CommonUtil.rollUpDateByDays(aDate, aFreq);
		}
		if (ICMSConstant.FREQ_UNIT_WEEKS.equals(aFreqUnit)) {
			return CommonUtil.rollUpDateByWeeks(aDate, aFreq);
		}
		if (ICMSConstant.FREQ_UNIT_MONTHS.equals(aFreqUnit)) {
			return CommonUtil.rollUpDateByMonths(aDate, aFreq);
		}
		if (ICMSConstant.FREQ_UNIT_YEARS.equals(aFreqUnit)) {
			return CommonUtil.rollUpDateByYears(aDate, aFreq);
		}

		throw new RecurrentException("The frequency unit : [" + aFreqUnit + "] is not value.");
	}
}
