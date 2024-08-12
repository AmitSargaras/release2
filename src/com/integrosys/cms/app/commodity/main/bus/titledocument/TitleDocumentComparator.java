/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/titledocument/TitleDocumentComparator.java,v 1.4 2004/11/18 07:26:27 hshii Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.titledocument;

import java.util.Comparator;

import com.integrosys.base.techinfra.diff.CompareResult;

/**
 * This comparator compares title document type by common reference id or type
 * name
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/11/18 07:26:27 $ Tag: $Name: $
 */
public class TitleDocumentComparator implements Comparator {

	public static final String BY_REF_ID = "ID";

	public static final String BY_NAME = "NM";

	private String compareBy;

	/**
	 * Default constructor, compared using TitleDocumentComparator.BY_REF_ID.
	 */
	public TitleDocumentComparator() {
		compareBy = BY_REF_ID;
	}

	/**
	 * Constructs profile comparator based on TitleDocumentComparator.BY_REF_ID
	 * or TitleDocumentComparator.BY_NAME.
	 * 
	 * @param compareBy of type String
	 */
	public TitleDocumentComparator(String compareBy) {
		this.compareBy = compareBy;
	}

	/**
	 * Compares o1 and o2. It returns a negative integer, zero, or a positive
	 * integer as the first argument is less than, equal to, or greater than the
	 * second.
	 * 
	 * @return a negative integer, zero, or a positive integer
	 */
	public int compare(Object o1, Object o2) {
		if ((o1 == null) && (o2 == null)) {
			return 0;
		}
		else if (o1 == null) {
			return -1;
		}
		else if (o2 == null) {
			return 1;
		}
		if (compareBy.equals(BY_NAME)) {
			return compareByName(o1, o2);
		}
		else {
			return defaultCompare(o1, o2);
		}
	}

	/**
	 * Default comparison for TitleDocumentComparator. It compares using title
	 * document type common reference id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		Long id1 = null;
		Long id2 = null;
		if (o1 instanceof CompareResult) {
			ITitleDocument doc1 = (ITitleDocument) ((CompareResult) o1).getObj();
			id1 = new Long(doc1.getCommonRef());
		}
		else {
			id1 = new Long(((ITitleDocument) o1).getCommonRef());
		}

		if (o2 instanceof CompareResult) {
			ITitleDocument doc2 = (ITitleDocument) ((CompareResult) o2).getObj();
			id2 = new Long(doc2.getCommonRef());
		}
		else {
			id2 = new Long(((ITitleDocument) o2).getCommonRef());
		}

		return id1.compareTo(id2);
	}

	/**
	 * Helper method to compare title doucment type by type name.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int compareByName(Object o1, Object o2) {
		ITitleDocument obj1 = null;
		ITitleDocument obj2 = null;

		if (o1 instanceof CompareResult) {
			obj1 = (ITitleDocument) ((CompareResult) o1).getObj();
		}
		else {
			obj1 = (ITitleDocument) o1;
		}

		if (o2 instanceof CompareResult) {
			obj2 = (ITitleDocument) ((CompareResult) o2).getObj();
		}
		else {
			obj2 = (ITitleDocument) o2;
		}

		return obj1.getName().compareToIgnoreCase(obj2.getName());
	}
}
