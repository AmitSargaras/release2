/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/reports/TemplateParameterSetter.java,v 1.2 2006/09/05 05:39:42 hshii Exp $
 **/

package com.integrosys.cms.batch.reports;

import java.util.HashMap;

/**
 * Description: Interface for setting Crystal report parameters knows how to set
 * various parameters for reports
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/09/05 05:39:42 $ Tag: $Name: $
 */
public interface TemplateParameterSetter {
	HashMap getTemplateParameters();
}
