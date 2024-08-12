/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/sublimittype/list/ViewSLTListCommand.java,v 1.1 2005/10/07 02:40:40 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.sublimittype.list;

import com.integrosys.base.uiinfra.common.ICommand;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-10-06
 * @Tag 
 *      com.integrosys.cms.ui.commodityglobal.sublimit.list.ViewSLTListCommand.java
 */
public class ViewSLTListCommand extends ReadSLTListCommand implements ICommand {
	protected boolean isWorkInProgress(String status) {
		return false;
	}
}
