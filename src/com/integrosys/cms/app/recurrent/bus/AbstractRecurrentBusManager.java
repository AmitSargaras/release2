package com.integrosys.cms.app.recurrent.bus;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

import java.util.List;

/**
 * This abstract class will contains a business related logic that is
 * independent of any technology implementation such as EJB
 *
 * @author $Author: hshii $<br>
 * @version $Revision: 1.66 $
 * @since $Date: 2006/10/09 05:41:15 $ Tag: $Name: $
 */
public abstract class AbstractRecurrentBusManager implements IRecurrentBusManager {


	/**
	 * Update a recurrent checklist
	 * @param anIRecurrentCheckList of IRecurrentCheckList tyoe
	 * @return IRecurrentCheckList - the recurrent checkList being updated
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException
	 */
	public IRecurrentCheckList update(IRecurrentCheckList anIRecurrentCheckList) throws ConcurrentUpdateException,
			RecurrentException {
		// TODO: recompute the recurrent checklist
		if (anIRecurrentCheckList == null) {
			throw new RecurrentException("IRecurrentCheckList is null!!!");
		}
		IRecurrentCheckList chkList = recomputeDueDate(anIRecurrentCheckList);
		return updateCheckList(chkList);
	}

	/**
	 * To compute the due date for the checklist items
	 * @param anIRecurrentCheckList of IRecurrentCheckList type
	 * @return IRecurrentCheckList - the recurrent checklist with the due date
	 *         recomputed
	 */
	private IRecurrentCheckList recomputeDueDate(IRecurrentCheckList anIRecurrentCheckList) {
		/*
		 * IRecurrentCheckListItem[] itemList =
		 * anIRecurrentCheckList.getCheckListItemList(); if ((itemList == null)
		 * || (itemList.length == 0)) { return anIRecurrentCheckList; }
		 *
		 * for (int ii=0; ii<itemList.length; ii++) { if
		 * (ICMSConstant.RECURRENT_ITEM_STATE_RECEIVED
		 * .equals(itemList[ii].getItemStatus())) { IItem item =
		 * itemList[ii].getItem(); int freq = itemList[ii].getFrequency();
		 * String freqUnit = itemList[ii].getFrequencyUnit(); Date dueDate =
		 * item.getExpiryDate(); if (freq != Integer.MIN_VALUE) {
		 * item.setExpiryDate(recomputeDate(dueDate, freq, freqUnit)); }
		 * itemList[ii].setItem(item); } }
		 * anIRecurrentCheckList.setCheckListItemList(itemList);
		 */
		return anIRecurrentCheckList;
	}

	/*
	 * public void createDueDateEntries(IRecurrentCheckListItem anItem) throws
	 * RecurrentException { List createList = new ArrayList(); if
	 * (anItem.getRecurrentCheckListSubItemList() == null ||
	 * anItem.getRecurrentCheckListSubItemList().length == 0) {
	 * DefaultLogger.debug(this, "IN Create Default!!!");
	 * createDefaultSet(createList,anItem); } else { DefaultLogger.debug(this,
	 * "IN Create NonDefault!!!"); createNonDefaultSet(createList, anItem); }
	 *
	 * if (createList.size() > 0) {
	 * createRecurrentCheckListSubItem(anItem.getCheckListItemID(), createList);
	 * } }
	 *
	 * private void createNonDefaultSet(List aList, IRecurrentCheckListItem
	 * anItem) throws RecurrentException { Date nextEndDate =
	 * anItem.getRecurrentCheckListSubItemList()[0].getDocEndDate();; Date
	 * nextDueDate = anItem.getRecurrentCheckListSubItemList()[0].getDueDate();;
	 * IRecurrentCheckListSubItem subItem = null; Date compDate =
	 * getComparisonDate(DateUtil.getDate(), anItem.getFrequencyUnit(),
	 * anItem.getFrequency()); for (int ii=0; ii<getNoOfSubItemToCreate(); ii++)
	 * { if (generateNextDueDate(compDate, nextEndDate)) { subItem = new
	 * OBRecurrentCheckListSubItem(); nextEndDate = recomputeDate(nextEndDate,
	 * anItem.getFrequency(), anItem.getFrequencyUnit()); nextDueDate =
	 * recomputeDueDate(nextEndDate, anItem.getGracePeriod(),
	 * anItem.getGracePeriodUnit()); subItem.setDocEndDate(nextEndDate);
	 * subItem.setDueDate(nextDueDate); aList.add(subItem); } else { return; } }
	 * }
	 *
	 * private boolean generateNextDueDate(Date aCompDate, Date aDocEndDate)
	 * throws RecurrentException { try { int result =
	 * aCompDate.compareTo(aDocEndDate); DefaultLogger.debug(this, "result: " +
	 * result); return (result >= 0); } catch(Exception ex) { throw new
	 * RecurrentException("Exception in generateNextDueDate", ex); } }
	 *
	 * private Date getComparisonDate(Date aDate, String aFreqUnit, int aFreq)
	 * throws RecurrentException { if
	 * (ICMSConstant.FREQ_UNIT_DAYS.equals(aFreqUnit)) { return
	 * CommonUtil.rollUpDateByDays(aDate, aFreq); } if
	 * (ICMSConstant.FREQ_UNIT_WEEKS.equals(aFreqUnit)) { return
	 * CommonUtil.rollUpDateByWeeks(aDate, aFreq); } if
	 * (ICMSConstant.FREQ_UNIT_MONTHS.equals(aFreqUnit)) { return
	 * CommonUtil.rollUpDateByMonths(aDate, aFreq); } if
	 * (ICMSConstant.FREQ_UNIT_YEARS.equals(aFreqUnit)) { return
	 * CommonUtil.rollUpDateByYears(aDate, aFreq); } throw new
	 * RecurrentException("Frequency unit " + aFreqUnit + " is invalid."); }
	 *
	 * private void createDefaultSet(List aList, IRecurrentCheckListItem anItem)
	 * throws RecurrentException { Date nextEndDate = null; Date nextDueDate =
	 * null; IRecurrentCheckListSubItem subItem = null; for (int ii=0;
	 * ii<getNoOfSubItemToCreate(); ii++) { subItem = new
	 * OBRecurrentCheckListSubItem(); if (nextEndDate == null) { nextEndDate =
	 * anItem.getInitialDocEndDate(); nextDueDate = anItem.getInitialDueDate();
	 * } else { nextEndDate = recomputeDate(nextEndDate, anItem.getFrequency(),
	 * anItem.getFrequencyUnit()); nextDueDate = recomputeDueDate(nextEndDate,
	 * anItem.getGracePeriod(), anItem.getGracePeriodUnit()); } subItem = new
	 * OBRecurrentCheckListSubItem(); subItem.setDocEndDate(nextEndDate);
	 * subItem.setDueDate(nextDueDate); aList.add(subItem); } }
	 *
	 * private int getNoOfSubItemToCreate() { return 2; }
	 *
	 * private Date recomputeDueDate(Date aDate, int aFreq, String aFreqUnit)
	 * throws RecurrentException { if (aFreqUnit == null) { return aDate; }
	 * return recomputeDate(aDate, aFreq, aFreqUnit);
	 *
	 * }
	 *
	 * private Date recomputeDate(Date aDate, int aFreq, String aFreqUnit)
	 * throws RecurrentException { if
	 * (ICMSConstant.FREQ_UNIT_DAYS.equals(aFreqUnit)) { return
	 * CommonUtil.rollUpDateByDays(aDate, aFreq); } if
	 * (ICMSConstant.FREQ_UNIT_WEEKS.equals(aFreqUnit)) { return
	 * CommonUtil.rollUpDateByWeeks(aDate, aFreq); } if
	 * (ICMSConstant.FREQ_UNIT_MONTHS.equals(aFreqUnit)) {
	 * DefaultLogger.debug(this, "aDATE: " + aDate); DefaultLogger.debug(this,
	 * "Feq: " + aFreq); return CommonUtil.rollUpDateByMonths(aDate, aFreq); }
	 * if (ICMSConstant.FREQ_UNIT_YEARS.equals(aFreqUnit)) { return
	 * CommonUtil.rollUpDateByYears(aDate, aFreq); } throw new
	 * RecurrentException("The frequency unit : " + aFreqUnit +
	 * " is invalid !!!"); }
	 */

	protected abstract IRecurrentCheckList updateCheckList(IRecurrentCheckList anICheckList)
			throws ConcurrentUpdateException, RecurrentException;

	protected abstract List createRecurrentCheckListSubItem(long anItemID, List aCreateList) throws RecurrentException;

	protected abstract List createConvenantSubItem(long anItemID, List aCreateList) throws RecurrentException;
}
