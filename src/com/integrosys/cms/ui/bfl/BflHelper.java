/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/bfl/BflHelper.java,v 1.2 2006/07/06 10:05:14 pratheepa Exp $
 */

package com.integrosys.cms.ui.bfl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.limit.bus.ITATEntry;

/**
 * @author $Author: pratheepa $<br>
 * @version $Revision:
 * @since $Date: Tag: $Name: $
 */
public class BflHelper {
	/**
	 * Method to get sorted array of ITATEntry Returns sorted array or first
	 * three elements of sorted array depending upon from_event.
	 * @param tatEntryList of type String array.
	 * @param from_event of type ITATEntry.
	 * @return array of ITATEntry
	 */
	public static ITATEntry[] sortTatEntryList(ITATEntry[] tatEntryList, String from_event) {
		if ((tatEntryList != null) && (tatEntryList.length > 0)) {
			DefaultLogger.debug(new BflHelper(), "inside if1");
			DefaultLogger.debug(new BflHelper(), "event:" + from_event);
			if ((from_event != null) & (from_event.trim().length() > 0)) {
				String event_rxd = from_event.trim();
				if (!((event_rxd.equalsIgnoreCase("send_draft_bfl"))
						|| (event_rxd.equalsIgnoreCase("customer_accept_bfl"))
						|| (event_rxd.equalsIgnoreCase("issue_clean_type_bfl"))
						|| (event_rxd.equalsIgnoreCase("issue_draft_bfl"))
						|| (event_rxd.equalsIgnoreCase("print_bfl_reminder"))
						|| (event_rxd.equalsIgnoreCase("recv_draft_bfl_ack")) || (event_rxd
						.equalsIgnoreCase("special_issue_clean_type_bfl")))) {
					DefaultLogger.debug(new BflHelper(), "inside if");
					Arrays.sort(tatEntryList, new Comparator() {
						public int compare(Object o1, Object o2) {
							Date date1 = ((ITATEntry) o1).getTATStamp();
							Date date2 = ((ITATEntry) o1).getTATStamp();
							return (date2.compareTo(date1));
						}
					});
					ITATEntry[] tatEntryLatestThree;
					if (tatEntryList.length > 3) {
						tatEntryLatestThree = new ITATEntry[3];
					}
					else {
						tatEntryLatestThree = new ITATEntry[tatEntryList.length];
					}
					int tatEntryListLength = tatEntryList.length;
					for (int i = 0; i <= tatEntryLatestThree.length - 1; i++, tatEntryListLength--) {
						tatEntryLatestThree[i] = tatEntryList[tatEntryListLength - 1];
					}
					tatEntryList = tatEntryLatestThree;

				}
				else {
					DefaultLogger.debug(new BflHelper(), "inside else");
					Arrays.sort(tatEntryList, new Comparator() {
						public int compare(Object o1, Object o2) {
							Date date1 = ((ITATEntry) o1).getTATStamp();
							Date date2 = ((ITATEntry) o1).getTATStamp();
							return (date1.compareTo(date2));
						}
					});
				}
			}

		}
		DefaultLogger.debug(new BflHelper(), "length11" + tatEntryList.length);
		return tatEntryList;
	}
}
