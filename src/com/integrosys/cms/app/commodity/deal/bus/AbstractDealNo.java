/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/AbstractDealNo.java,v 1.3 2004/07/10 02:55:21 wltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/10 02:55:21 $ Tag: $Name: $
 */
public abstract class AbstractDealNo implements IDealNo {

	private static final int DEAL_NO_MAX_LENGTH = 18;

	private static final String DEFAULT_PAD = "0";

	private static final String DEFAULT_PREFIX = "";

	private static final String YEAR_FORMAT = "yyyy";

	private String countryCode;

	protected AbstractDealNo(String ctryCode) {
		this.countryCode = ctryCode;
	}

	public String getPrefix() {
		return ICMSConstant.DEAL_NO_PREFIX;
	}

	public String getPostfix() {
		return DEFAULT_PREFIX;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getCurrentYear() {
		return DateUtil.formatCurrentTime(YEAR_FORMAT);
	}

	public String getNewDealNo() throws Exception {

		String sequenceNo = getNewSequenceNo();
		if ((sequenceNo == null) || (sequenceNo.trim().length() == 0)) {
			throw new Exception("The Sequence No is null or empty!!!");
		}

		StringBuffer buf = new StringBuffer();
		buf.append(getPrefix());
		buf.append(getDealType());
		buf.append(getCountryCode());
		buf.append(getCurrentYear());

		// Check if sequence number of too long
		int seqMaxLength = getSequenceLength();
		if (seqMaxLength < sequenceNo.trim().length()) {
			throw new Exception("The Sequence No is too long : " + sequenceNo);
		}

		// Pad sequence number to required length
		for (int ii = 0; ii < (seqMaxLength - sequenceNo.trim().length()); ii++) {
			buf.append(getPadString());
		}
		buf.append(sequenceNo);
		buf.append(getPostfix());
		return buf.toString();
	}

	private int getSequenceLength() {
		int totalPrefixLength = getPrefix().length() + getDealType().length() + getCountryCode().length()
				+ getCurrentYear().length() + getPostfix().length();
		return DEAL_NO_MAX_LENGTH - totalPrefixLength;
	}

	public String getPadString() {
		return DEFAULT_PAD;
	}
}