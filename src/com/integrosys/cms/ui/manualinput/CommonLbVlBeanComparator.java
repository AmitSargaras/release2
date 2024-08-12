package com.integrosys.cms.ui.manualinput;

import java.util.Comparator;

import org.apache.struts.util.LabelValueBean;

public class CommonLbVlBeanComparator implements Comparator {
	public int compare(Object o1, Object o2) throws ClassCastException {
		if ((o1 instanceof LabelValueBean) && (o2 instanceof LabelValueBean)) {
			LabelValueBean lv1 = (LabelValueBean) o1;
			LabelValueBean lv2 = (LabelValueBean) o2;
			if ((lv1.getLabel() != null) && (lv2.getLabel() != null)) {
				return lv1.getLabel().compareToIgnoreCase(lv2.getLabel());
			}
			else {
				return 0;
			}
		}
		else {
			throw new ClassCastException();
		}
	}

	public boolean equals(Object obj) {
		if (obj instanceof CommonLbVlBeanComparator) {
			return true;
		}
		return false;
	}
}