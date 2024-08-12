/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/sublimittype/SubLimitTypeComparator.java,v 1.2 2005/11/12 04:40:09 hmbao Exp $
 */
package com.integrosys.cms.app.creditriskparam.bus.internallimit;

import java.util.Comparator;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-19
 * @Tag com.integrosys.cms.app.commodity.main.bus.sublimittype.SubLimitTypeComparator.java
 */
public class InternalLimitParamComparator implements Comparator {

	public InternalLimitParamComparator() {
		super();
	}

	/*
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object arg0, Object arg1) {
		if (arg0 == null && arg1 == null) {
			return 0;
		} else if (arg0 == null) {
			return -1;
		} else if (arg1 == null) {
			return 1;
		}
		String lt1 = ((IInternalLimitParameter) arg0).getDescriptionCode();
		String lt2 = ((IInternalLimitParameter) arg1).getDescriptionCode();
		if (lt1 == null && lt2 == null) {
			return 0;
		} else if (lt1 == null) {
			return -1;
		} else if (lt2 == null) {
			return 1;
		}
		return lt1.compareToIgnoreCase(lt2);
	}
}
