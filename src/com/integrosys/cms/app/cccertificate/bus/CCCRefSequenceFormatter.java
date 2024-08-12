/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/CCCRefSequenceFormatter.java,v 1.1 2003/11/27 07:32:33 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

//ofa
import com.integrosys.base.techinfra.dbsupport.ISequenceFormatter;

/**
 * This class is responsible for formatting the cc certificate reference
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/11/27 07:32:33 $ Tag: $Name: $
 */
public class CCCRefSequenceFormatter implements ISequenceFormatter {
	private static final int CCC_REF_MAX_LENGTH = 4;

	private static final String PREFIX_APPENDED = "";

	private static final String DEFAULT_APPENDED = "0";

	/**
	 * Default Constructor
	 */
	public CCCRefSequenceFormatter() {
	}

	/**
	 * Implementation of the interface method to perform the formatting
	 * @param aSequenceNo - String
	 * @return String - the formatted sequence number
	 * @throws Exception on errors
	 */
	public String formatSeq(String aSequenceNo) throws Exception {
		if ((aSequenceNo == null) || (aSequenceNo.trim().length() == 0)) {
			throw new Exception("The Sequence No is null or empty!!!");
		}
		StringBuffer pad = new StringBuffer(getSequencePrefix());
		int maxLength = getSequenceLength();
		for (int ii = 0; ii < (maxLength - aSequenceNo.trim().length()); ii++) {
			pad.append(getPadString());
		}
		return pad.toString() + aSequenceNo;
	}

	/**
	 * To return the prefix for the sequence number generated
	 * @return String - the value to be prefixed for the sequence
	 */
	protected String getSequencePrefix() {
		return PREFIX_APPENDED;
	}

	/**
	 * To return the maximum length of the sequence to be formatted to
	 * @return int - the maximum length of the sequence
	 */
	protected int getSequenceLength() {
		return CCC_REF_MAX_LENGTH - getSequencePrefix().length();
	}

	/**
	 * To return the string value to be padded to the sequence number
	 * @return String - the value to be padded to the sequence number
	 */
	protected String getPadString() {
		return DEFAULT_APPENDED;
	}
}
