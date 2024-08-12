/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/IMonitorDAO.java,v 1.1 2003/08/18 06:27:32 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor;

public interface IMonitorDAO {
	IMonitorDAOResult getInitialSet(IRuleParam ruleParam) throws MonitorDaoResultRetrievalFailureException;
}
