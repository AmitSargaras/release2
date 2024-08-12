/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/ddndue/DDNDueDAOResult.java,v 1.7 2005/05/19 05:34:34 eliliana Exp $
 */

package com.integrosys.cms.app.eventmonitor.ddndue;

import java.util.List;

import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAOResult;

/**
 * @author $Author: eliliana $
 * @version $Revision: 1.7 $ Refer to AbstractMonitorDAOResult
 * @refer AbstractMonitorDAOResult
 */
public class DDNDueDAOResult extends AbstractMonitorDAOResult {

	private DDNDueDAOResult() {
	}

	public DDNDueDAOResult(List results) {
		super(results);
	}
}
