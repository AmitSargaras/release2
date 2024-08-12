/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/CollateralComparator.java,v 1.2 2004/08/11 05:44:58 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.util.Comparator;

import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * This comparator compares Collateral.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/08/11 05:44:58 $ Tag: $Name: $
 */
public class CollateralComparator implements Comparator {
	public static final String COMPARE_BY_SCI_SEC_ID = "1";

	public static final String COMPARE_BY_SUBTYPE = "2";

	private String compareBy;

	/**
	 * Default constructor, compared SCI security id.
	 */
	public CollateralComparator() {
		compareBy = COMPARE_BY_SCI_SEC_ID;
	}

	/**
	 * Constructs Collateral comparator based on COMPARE_BY_SCI_SEC_ID or
	 * COMPARE_BY_SUBTYPE.
	 * 
	 * @param compareBy of type String
	 */
	public CollateralComparator(String compareBy) {
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

		if (compareBy.equals(COMPARE_BY_SUBTYPE)) {
			return compareBySubType(o1, o2);
		}
		else {
			return defaultCompare(o1, o2);
		}
	}

	/**
	 * Default comparison for CollateralComparator. It compares using SCI
	 * security id.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int defaultCompare(Object o1, Object o2) {
		String id1 = null;
		String id2 = null;

		if (o1 instanceof ICollateral) {
			ICollateral obj1 = (ICollateral) o1;
			id1 = obj1.getSCISecurityID();
		}
		else if (o1 instanceof ICollateralTrxValue) {
			ICollateralTrxValue obj1 = (ICollateralTrxValue) o1;
			id1 = obj1.getCollateral().getSCISecurityID();
		}
		else {
			ICollateralSearchResult obj1 = (ICollateralSearchResult) o1;
			id1 = obj1.getSecurityID();
		}

		if (o2 instanceof ICollateral) {
			ICollateral obj2 = (ICollateral) o2;
			id2 = obj2.getSCISecurityID();
		}
		else if (o2 instanceof ICollateralTrxValue) {
			ICollateralTrxValue obj2 = (ICollateralTrxValue) o2;
			id2 = obj2.getCollateral().getSCISecurityID();
		}
		else {
			ICollateralSearchResult obj2 = (ICollateralSearchResult) o2;
			id2 = obj2.getSecurityID();
		}
		return compareStr(id1,id2);
	}

	/**
	 * Helper method to compare using security subtype code.
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	private int compareBySubType(Object o1, Object o2) {
		String st1 = null, st2 = null;

		if (o1 instanceof ICollateral) {
			ICollateral obj1 = (ICollateral) o1;
			st1 = obj1.getCollateralSubType().getSubTypeCode();
		}
		else if (o1 instanceof ICollateralTrxValue) {
			ICollateralTrxValue obj1 = (ICollateralTrxValue) o1;
			st1 = obj1.getCollateral().getCollateralSubType().getSubTypeCode();
		}
		else {
			ICollateralSearchResult obj1 = (ICollateralSearchResult) o1;
			st1 = obj1.getSubTypeCode();
		}

		if (o2 instanceof ICollateral) {
			ICollateral obj2 = (ICollateral) o2;
			st2 = obj2.getCollateralSubType().getSubTypeCode();
		}
		else if (o2 instanceof ICollateralTrxValue) {
			ICollateralTrxValue obj2 = (ICollateralTrxValue) o2;
			st2 = obj2.getCollateral().getCollateralSubType().getSubTypeCode();
		}
		else {
			ICollateralSearchResult obj2 = (ICollateralSearchResult) o2;
			st2 = obj2.getSubTypeCode();
		}

		return compareStr(st1,st2);
	}
	
	private int compareStr(String str1,String str2){
		if(str1==null&&str2==null){
			return 0;
		}else if(str1==null&&str2!=null){
			return -1;
		}else if(str1!=null&&str2==null){
			return 1;
		}else{
			return str1.compareTo(str2);
		}		
	}
}
