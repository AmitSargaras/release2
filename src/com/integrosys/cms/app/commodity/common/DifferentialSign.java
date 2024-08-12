/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/common/DifferentialSign.java,v 1.4 2004/08/31 08:11:42 wltan Exp $
 */
package com.integrosys.cms.app.commodity.common;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/31 08:11:42 $ Tag: $Name: $
 */
public class DifferentialSign extends NameValuePair {

	private static final String POSITIVE_CODE = ICMSConstant.SIMPLE_SIGN_PLUS;

	private static final String NEGATIVE_CODE = ICMSConstant.SIMPLE_SIGN_MINUS;

	private static final String POSITIVE_OR_NEGATIVE_CODE = ICMSConstant.SIMPLE_SIGN_PLUS_OR_MINUS;

	private static final String POSITIVE_LABEL = "+";

	private static final String NEGATIVE_LABEL = "-";

	private static final String POSITIVE_OR_NEGATIVE_LABEL = "+/-";

	public static final DifferentialSign POSITIVE_DIFFERENTIAL = new DifferentialSign(POSITIVE_CODE, POSITIVE_LABEL);

	public static final DifferentialSign NEGATIVE_DIFFERENTIAL = new DifferentialSign(NEGATIVE_CODE, NEGATIVE_LABEL);

	public static final DifferentialSign POSTIVE_OR_NEGATIVE_DIFF = new DifferentialSign(POSITIVE_OR_NEGATIVE_CODE,
			POSITIVE_OR_NEGATIVE_LABEL);

	protected static final DifferentialSign[] list = new DifferentialSign[] { POSITIVE_DIFFERENTIAL,
			NEGATIVE_DIFFERENTIAL, POSTIVE_OR_NEGATIVE_DIFF };

	public DifferentialSign() {
	}

	private DifferentialSign(String aCode, String aLabel) {
		super(aCode, aLabel);
	}

	public static DifferentialSign[] getAll() {
		return list;
	}

	public static DifferentialSign valueOf(String aCodeOrLabel) {
		if (POSITIVE_CODE.equals(aCodeOrLabel) || POSITIVE_LABEL.equals(aCodeOrLabel)) {
			return POSITIVE_DIFFERENTIAL;
		}
		else if (NEGATIVE_CODE.equals(aCodeOrLabel) || NEGATIVE_LABEL.equals(aCodeOrLabel)) {
			return NEGATIVE_DIFFERENTIAL;

		}
		else if (POSITIVE_OR_NEGATIVE_CODE.equals(aCodeOrLabel) || POSITIVE_OR_NEGATIVE_LABEL.equals(aCodeOrLabel)) {
			return POSTIVE_OR_NEGATIVE_DIFF;
		}
		else {
			throw new RuntimeException("No such differential sign : " + aCodeOrLabel);
		}
	}
}
