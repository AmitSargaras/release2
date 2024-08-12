/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/DocumentItemCodeSequenceFormatter.java,v 1.3 2003/07/07 08:09:21 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.cms.app.chktemplate.bus.ItemCodeSequenceFormatter;

/**
 * This class is responsible for formatting the document item code
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/07 08:09:21 $ Tag: $Name: $
 */
public class DocumentItemCodeSequenceFormatter extends ItemCodeSequenceFormatter {
	private static final String PREFIX_APPENDED = "9";

	/**
	 * Default Constructor
	 */
	public DocumentItemCodeSequenceFormatter() {
	}

	/**
	 * To return the prefix for the sequence number generated
	 * @return String - the value to be prefixed for the sequence
	 */
	protected String getSequencePrefix() {
		return PREFIX_APPENDED;
	}
}
