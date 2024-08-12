/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/TemplateItemCodeSequenceFormatter.java,v 1.2 2003/07/08 03:12:46 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.cms.app.chktemplate.bus.ItemCodeSequenceFormatter;

/**
 * This class is responsible for formatting the template item code
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/08 03:12:46 $ Tag: $Name: $
 */
public class TemplateItemCodeSequenceFormatter extends ItemCodeSequenceFormatter {
	private static final int LENGTH_OF_CTRY_CODE = 2;

	// private static final int ITEM_CODE_MAX_LENGTH = 7;

	/**
	 * Default Constructor
	 */
	public TemplateItemCodeSequenceFormatter() {
	}

	/**
	 * To return the maximum length of the sequence to be formatted to
	 * @return int - the maximum length of the sequence
	 */
	protected int getSequenceLength() {
		return super.getSequenceLength() - LENGTH_OF_CTRY_CODE;
	}
}
