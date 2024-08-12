package com.integrosys.cms.app.eventmonitor;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: Jul 21, 2003 Time:
 * 5:17:39 PM To change this template use Options | File Templates.
 */
public interface IMonitorDAOResult {
	public boolean hasNextElement() throws EventMonitorException;

	public void close();

	public Object getElement();
}
