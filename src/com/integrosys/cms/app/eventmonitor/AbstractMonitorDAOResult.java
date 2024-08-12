/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/AbstractMonitorDAOResult.java,v 1.9 2005/05/27 05:43:50 wltan Exp $
 */

package com.integrosys.cms.app.eventmonitor;

import java.util.Iterator;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.9 $ This class wraps the database result set returned
 *          by the MonitorDAO It provides a getElement method which returns the
 *          DDN object which is going to be due, dued, or expired. The sequence
 *          of calls is this: 1. Monitor calls MonitorDAO 2. MonitorDAO executes
 *          a SQL statement returning an instance of this class 3. Monitor
 *          iterates through this result from step 2, evaluates MonitorRule, and
 *          fires MonitorListener if this result is true.
 */
public abstract class AbstractMonitorDAOResult implements IMonitorDAOResult {

	private Object ob = null;

	private List results;

	private Iterator resultsIterator;

	/**
	 * Do not allow caller to call this contructor without parameter
	 */
	protected AbstractMonitorDAOResult() {
	}

	public AbstractMonitorDAOResult(List results) {
		if (results != null) {
			this.results = results;
			this.resultsIterator = results.iterator();
		}
	}

	/**
	 * Checks if there is next record to be processed This method calls the
	 * subclass's processResultSet to derive the result If the subclass
	 * processElement returns a null, this method will return a false otherwise
	 * returns true. The caller should call getElement() to get the returned
	 * result from processResultSet if the result of hasNextElement() is true.
	 * 
	 * @return boolean true | false
	 */
	public boolean hasNextElement() throws EventMonitorException {
		try {
			if ((resultsIterator != null) && resultsIterator.hasNext()) {
				this.ob = resultsIterator.next();
				return (ob != null);
			}
			return false;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Other exception", e);
			throw new EventMonitorException(e);
		}

	}

	public void close() {
		results = null;
	}

	/**
	 * This is called by xxxMonitor class The caller needs to cast to its
	 * respective type
	 */
	public Object getElement() {
		return this.ob;
	}

}
