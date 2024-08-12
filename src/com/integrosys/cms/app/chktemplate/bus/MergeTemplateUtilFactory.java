/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/MergeTemplateUtilFactory.java,v 1.1 2003/07/11 01:45:26 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

/**
 * Factory class for the checkList DAO
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/11 01:45:26 $
 */

public class MergeTemplateUtilFactory {
	/**
	 * Get the utility class to perform the merging of templates
	 * @return IMergeTemplateUtil - the interface for the template merging
	 */
	public static IMergeTemplateUtil getMergeTemplateUtil() {
		return new MergeTemplateUtil();
	}
}
