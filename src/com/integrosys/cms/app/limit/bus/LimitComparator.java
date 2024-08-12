/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/LimitComparator.java,v 1.1 2004/08/12 07:43:12 lyng Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.util.Comparator;

/**
 * This comparator compares Limits.
 *
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/08/12 07:43:12 $
 *        Tag:     $Name:  $
 */
public class LimitComparator implements Comparator {
    public static final String COMPARE_BY_LMT_ID = "LMT_ID";
    private String compareBy;

    /**
     * Default constructor, compared using SCI limit id.
     */
    public LimitComparator() {
        compareBy = COMPARE_BY_LMT_ID;
    }

    /**
     * compared using the ind key pass in.
     */
    public LimitComparator(String ind) {
        compareBy = ind;
    }

    /**
     * Compares o1 and o2. It returns a negative integer, zero, or a positive
     * integer as the first argument is less than, equal to, or greater than the second.
     *
     * @return a negative integer, zero, or a positive integer
     */
    public int compare(Object o1, Object o2) {
        if (o1 == null && o2 == null) {
            return 0;
        } else if (o1 == null) {
            return -1;
        } else if (o2 == null) {
            return 1;
        }

        //return defaultCompare (o1, o2);

        if (compareBy.equals(COMPARE_BY_LMT_ID)) {
            return defaultCompare(o1, o2);
        } else {
            return compareByOuterLimitID(o1, o2);
        }
    }

    /**
     * Default comparison for LimitComparator. It compares using SCI limit id.
     *
     * @return a negative integer, zero, or a positive integer as the first argument
     *         is less than, equal to, or greater than the second.
     */
    private int defaultCompare(Object o1, Object o2) {
        ILimit obj1 = (ILimit) o1;
        ILimit obj2 = (ILimit) o2;

        String id1 = obj1.getLimitRef();
        String id2 = obj2.getLimitRef();

        return id1.compareTo(id2);
    }

    /**
     * It compares using Outer limit id.
     *
     * @return a negative integer, zero, or a positive integer as the first argument
     *         is less than, equal to, or greater than the second.
     */
    private int compareByOuterLimitID(Object o1, Object o2) {
        ILimit obj1 = (ILimit) o1;
        ILimit obj2 = (ILimit) o2;

        String id1 = String.valueOf(obj1.getOuterLimitID());
        String id2 = String.valueOf(obj2.getOuterLimitID());

        return id1.compareTo(id2);
    }
}