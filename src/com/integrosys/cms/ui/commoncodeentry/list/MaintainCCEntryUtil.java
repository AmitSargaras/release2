package com.integrosys.cms.ui.commoncodeentry.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryConstant;
import com.integrosys.cms.app.commoncodeentry.bus.CommonCodeEntryComparator;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntries;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;
import com.integrosys.cms.app.commoncodeentry.trx.ICommonCodeEntriesTrxValue;
import com.integrosys.cms.ui.common.CountryList;

/**
 * @Author: BaoHongMan
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/22 13:21:41 $ Tag: $Name%
 */
public class MaintainCCEntryUtil {
	public static final String REF_ID = "1010";

	public static ArrayList getCurrentPageCCEntryList(ICommonCodeEntriesTrxValue trxValue, Integer offset,
			boolean isActual) {
		ICommonCodeEntries ccEntries = null;
		if (isActual) {
			ccEntries = trxValue.getCommonCodeEntries();
		}
		else {
			ccEntries = trxValue.getStagingCommonCodeEntries();
		}
		ArrayList allEntryList = (ArrayList) ccEntries.getEntries();
		int startIdx = offset.intValue();
		int pageLength = CommonCodeEntryConstant.FIXED_LENGTH.intValue();
		int size = allEntryList.size();
		int endIdx = Math.min(size, startIdx + pageLength);
		ArrayList aPageList = new ArrayList();
		for (int index = startIdx; index < endIdx; index++) {
			aPageList.add(allEntryList.get(index));
		}
		return aPageList;
	}

	public static void updateCurrentPage2EntryList(ICommonCodeEntriesTrxValue trxValue, Integer offset,
			ArrayList aPageList) {
		if ((aPageList == null) || (aPageList.size() == 0)) {
			return;
		}
		ICommonCodeEntries ccEntries = trxValue.getStagingCommonCodeEntries();
		ArrayList allEntryList = (ArrayList) ccEntries.getEntries();
		if (offset.intValue() + aPageList.size() >= allEntryList.size()) {
			// error here!!!;
		}
		for (int index = 0; index < aPageList.size(); index++) {
			OBCommonCodeEntry src = (OBCommonCodeEntry) aPageList.get(index);
			OBCommonCodeEntry dest = (OBCommonCodeEntry) allEntryList.get(index + offset.intValue());
			dest.setEntryName(src.getEntryName());
			dest.setEntryCode(src.getEntryCode());
			dest.setCountry(src.getCountry());
			dest.setRefEntryCode(src.getRefEntryCode());
		}
	}

	public static void sortCCEntryList(ICommonCodeEntriesTrxValue trxValue) {
		ICommonCodeEntries ccEntries = trxValue.getCommonCodeEntries();
		ArrayList allEntryList = (ArrayList) ccEntries.getEntries();
		if ((allEntryList != null) && (allEntryList.size() > 0)) {
			Collections.sort(allEntryList, new CommonCodeEntryComparator());
			ccEntries.setEntries(allEntryList);
		}
		// sort staging...
		ccEntries = trxValue.getStagingCommonCodeEntries();
		if (ccEntries == null) {
			return;
		}
		allEntryList = (ArrayList) ccEntries.getEntries();
		if ((allEntryList != null) && (allEntryList.size() > 0)) {
			Collections.sort(allEntryList, new CommonCodeEntryComparator());
			ccEntries.setEntries(allEntryList);
		}
	}

	public static Map getEntryIdMap(ICommonCodeEntriesTrxValue trxValue, boolean isActual) {
		ICommonCodeEntries ccEntries = null;
		if (isActual) {
			ccEntries = trxValue.getCommonCodeEntries();
		}
		else {
			ccEntries = trxValue.getStagingCommonCodeEntries();
		}
		ArrayList allEntryList = (ArrayList) ccEntries.getEntries();
		Map idMap = new HashMap();
		if ((allEntryList != null) && (allEntryList.size() > 0)) {
			for (int index = 0; index < allEntryList.size(); index++) {
				OBCommonCodeEntry entry = (OBCommonCodeEntry) allEntryList.get(index);
				idMap.put(new Long(entry.getEntryId()), entry);
			}
		}
		return idMap;
	}

	public static Map getCountryValueLabelMap() {
		Collection labels = CountryList.getInstance().getCountryLabels();
		Collection values = CountryList.getInstance().getCountryValues();
		Iterator labelIterator = labels.iterator();
		Iterator valueIterator = values.iterator();
		Map vlMap = new HashMap();
		while (valueIterator.hasNext() && labelIterator.hasNext()) {
			vlMap.put(valueIterator.next(), labelIterator.next());
		}
		return vlMap;
	}

	public static boolean isDuplicateEntry(OBCommonCodeEntry entry, ICommonCodeEntriesTrxValue trxValue) {
		ICommonCodeEntries ccEntries = trxValue.getStagingCommonCodeEntries();
		ArrayList allEntryList = (ArrayList) ccEntries.getEntries();
		if ((allEntryList == null) || (allEntryList.size() == 0)) {
			return false;
		}
		for (int index = 0; index < allEntryList.size(); index++) {
			OBCommonCodeEntry tmp = (OBCommonCodeEntry) allEntryList.get(index);
			if(!tmp.getActiveStatus()){
				continue;
			}
			if (entry.getEntryCode().equals(tmp.getEntryCode())) {
				if ((entry.getCountry() == null) || (tmp.getCountry() == null)) {
					return true;
				}
				else if (entry.getCountry().equals(tmp.getCountry())) {
					return true;
				}
			}
		}
		return false;
	}

	public static HashMap getDuplicateEntryMap(ArrayList entryList, Integer offset, ICommonCodeEntriesTrxValue trxValue) {
		HashMap errorEntryMap = new HashMap();
		if ((entryList == null) || (entryList.size() == 0)) {
			return errorEntryMap;
		}
		if (REF_ID.equals(trxValue.getTrxReferenceID())) {
			return errorEntryMap;
		}
		HashMap entryCodeMap = new HashMap();
		for (int index = 0; index < entryList.size(); index++) {
			OBCommonCodeEntry tmpEntry = (OBCommonCodeEntry) entryList.get(index);
			if(!tmpEntry.getActiveStatus()){
				continue;
			}
			String entryCode = tmpEntry.getEntryCode();
			String ctyCode = tmpEntry.getCountry();
			if (ctyCode == null) {
				ctyCode = "-";
			}
			String tmpCtyCode = (String) entryCodeMap.get(entryCode);
			if ((tmpCtyCode != null) && tmpCtyCode.equals(ctyCode)) {
				// System.out.println(
				// "duplicate entry_code in the same page found:"+entryCode);
				errorEntryMap.put(entryCode, entryCode);
				// return true;// duplicate entry_code in the same page.
			}
			else {
				entryCodeMap.put(entryCode, ctyCode);
			}
		}
		int startIdx = offset.intValue();
		ICommonCodeEntries ccEntries = trxValue.getStagingCommonCodeEntries();
		ArrayList allEntryList = (ArrayList) ccEntries.getEntries();
		if ((allEntryList == null) || (allEntryList.size() == 0)) {
			return errorEntryMap;
		}
		for (int index = 0; index < allEntryList.size(); index++) {
			if ((index >= startIdx) && (index <= startIdx + entryList.size())) {
				continue;
			}
			OBCommonCodeEntry tmpEntry = (OBCommonCodeEntry) allEntryList.get(index);
			if(!tmpEntry.getActiveStatus()){
				continue;
			}
			String entryCode = tmpEntry.getEntryCode();
			String ctyCode = tmpEntry.getCountry();
			if (ctyCode == null) {
				ctyCode = "-";
			}
			String tmpCtyCode = (String) entryCodeMap.get(entryCode);
			if ((tmpCtyCode != null) && tmpCtyCode.equals(ctyCode)) {
				//System.out.println("duplicate entry_code detected:"+entryCode)
				// ;
				errorEntryMap.put(entryCode, entryCode);
				// return true;
			}
			else {
				// entryCodeMap.put(entryCode,ctyCode);
			}
		}
		return errorEntryMap;
	}

	public static boolean isEqualString(String str1, String str2) {
		if (str1 == null) {
			return (str2 == null);
		}
		else {
			if (str2 == null) {
				return false;
			}
			else {
				return str1.trim().equals(str2.trim());
			}
		}
	}
}