package com.integrosys.cms.ui.limit.facility.multitierfin;

import java.io.Serializable;
import java.util.Comparator;

import com.integrosys.cms.app.limit.bus.IFacilityMultiTierFinancing;

/**
 * <p>
 * Comparator to use to compare items of
 * <tt>com.integrosys.cms.app.limit.bus.OBFacilityMultiTierFinancing</tt>.
 * <p>
 * <b>Order of comparing:</b>
 * <ol>
 * <li>Compare the equality, via
 * {@link com.integrosys.cms.app.limit.bus.OBFacilityMultiTierFinancing#equals(Object)}
 * <li>If both tier sequence number having the same value, compare the term and
 * rate, else just compare tier sequence number directly.
 * <li>If sequence number of either one object is null, compare the term and
 * rate.
 * </ol>
 * @author Chong Jun Yong
 * 
 */
public class FacilityMultiTierFinComparator implements Comparator, Serializable {

	private static final long serialVersionUID = -893534730276632900L;

	public int compare(Object obj1, Object obj2) {
		IFacilityMultiTierFinancing multiTierFin1 = (IFacilityMultiTierFinancing) obj1;
		IFacilityMultiTierFinancing multiTierFin2 = (IFacilityMultiTierFinancing) obj2;

		if (multiTierFin1.equals(multiTierFin2)) {
			return 0;
		}
		else if (multiTierFin1.getTierSeqNo() != null && multiTierFin2.getTierSeqNo() != null) {
			if (multiTierFin1.getTierSeqNo().compareTo(multiTierFin2.getTierSeqNo()) == 0) {
				return compareTermAndRate(multiTierFin1, multiTierFin2);
			}
			else {
				return multiTierFin1.getTierSeqNo().compareTo(multiTierFin2.getTierSeqNo());
			}
		}
		else {
			return compareTermAndRate(multiTierFin1, multiTierFin2);
		}
	}

	private int compareTermAndRate(IFacilityMultiTierFinancing multiTierFin1, IFacilityMultiTierFinancing multiTierFin2) {
		return (multiTierFin1.getTierTerm() + String.valueOf(multiTierFin1.getTierTermCode()) + String
				.valueOf(multiTierFin1.getRate())).compareTo(multiTierFin2.getTierTerm()
				+ String.valueOf(multiTierFin2.getTierTermCode()) + String.valueOf(multiTierFin2.getRate()));
	}
}