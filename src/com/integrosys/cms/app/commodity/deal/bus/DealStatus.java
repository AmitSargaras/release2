/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/DealStatus.java,v 1.4 2005/11/04 04:46:23 czhou Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import com.integrosys.cms.app.commodity.common.NameValuePair;

/**
 * @author $Author: czhou $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/11/04 04:46:23 $ Tag: $Name: $
 */
public class DealStatus extends NameValuePair {

	// old implementataion; before CR119
	private static final String ACTIVE_CODE = "A";

	private static final String ACTIVE_LABEL = "ACTIVE";

	public static final DealStatus ACTIVE_DEAL_STATUS = new DealStatus(ACTIVE_CODE, ACTIVE_LABEL);

	// New Status as stated in CR119
	private static final String NEW_CODE = "N";

	private static final String ENHANCED_CODE = "E";

	private static final String REDUCED_CODE = "R";

	private static final String PARTIALLY_SETTLED_CODE = "P";

	private static final String SETTLED_CODE = "S";

	private static final String CLOSED_CODE = "C";

	// changed from private to public for access by
	// AbstractCommodityDealTrxOperation.determineSetStatus();
	public static final String NEW_LABEL = "NEW";

	public static final String ENHANCED_LABEL = "ENHANCED";

	public static final String REDUCED_LABEL = "REDUCTION";

	public static final String PARTIALLY_SETTLED_LABEL = "PARTIALLY SETTLED";

	public static final String SETTLED_LABEL = "SETTLED";

	public static final String CLOSED_LABEL = "CLOSED";

	public static final DealStatus NEW_DEAL_STATUS = new DealStatus(NEW_CODE, NEW_LABEL);

	public static final DealStatus ENHANCED_DEAL_STATUS = new DealStatus(ENHANCED_CODE, ENHANCED_LABEL);

	public static final DealStatus REDUCED_DEAL_STATUS = new DealStatus(REDUCED_CODE, REDUCED_LABEL);

	public static final DealStatus PARTIALLY_SETTLED_DEAL_STATUS = new DealStatus(PARTIALLY_SETTLED_CODE,
			PARTIALLY_SETTLED_LABEL);

	public static final DealStatus SETTLED_DEAL_STATUS = new DealStatus(SETTLED_CODE, SETTLED_LABEL);

	public static final DealStatus CLOSED_DEAL_STATUS = new DealStatus(CLOSED_CODE, CLOSED_LABEL);

	private DealStatus(String aCode, String aLabel) {
		super(aCode, aLabel);
	}

	public static DealStatus valueOf(String aCodeOrLabel) {

		// old implementation; before CR119
		if (ACTIVE_CODE.equals(aCodeOrLabel) || ACTIVE_LABEL.equals(aCodeOrLabel)) {
			return ACTIVE_DEAL_STATUS;
		}

		if (NEW_CODE.equals(aCodeOrLabel) || NEW_LABEL.equals(aCodeOrLabel)) {
			return NEW_DEAL_STATUS;
		}
		if (ENHANCED_CODE.equals(aCodeOrLabel) || ENHANCED_LABEL.equals(aCodeOrLabel)) {
			return ENHANCED_DEAL_STATUS;
		}
		if (REDUCED_CODE.equals(aCodeOrLabel) || REDUCED_LABEL.equals(aCodeOrLabel)) {
			return REDUCED_DEAL_STATUS;
		}
		if (PARTIALLY_SETTLED_CODE.equals(aCodeOrLabel) || PARTIALLY_SETTLED_LABEL.equals(aCodeOrLabel)) {
			return PARTIALLY_SETTLED_DEAL_STATUS;
		}
		if (SETTLED_CODE.equals(aCodeOrLabel) || SETTLED_LABEL.equals(aCodeOrLabel)) {
			return SETTLED_DEAL_STATUS;
		}
		if (CLOSED_CODE.equals(aCodeOrLabel) || CLOSED_LABEL.equals(aCodeOrLabel)) {
			return CLOSED_DEAL_STATUS;
		}
		throw new RuntimeException("No such deal status : " + aCodeOrLabel);
	}

}
