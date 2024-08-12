/*
 * Created on Jun 8, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.batch.reuterfeed;

import java.util.Iterator;

import javax.ejb.SessionContext;

import com.integrosys.cms.app.eventmonitor.AbstractMonitorAdapter;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;
import com.integrosys.cms.batch.common.StartupInit;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ReuterFeedMain extends AbstractMonitorAdapter {
	public ReuterFeedMain() {
		StartupInit.init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.cms.app.eventmonitor.IMonitor#start(java.lang.String,
	 * javax.ejb.SessionContext)
	 */
	public void start(String countryCode, SessionContext context) throws EventMonitorException {
		try {
			doWork(countryCode, context);
		}
		catch (Exception ex) {
			throw new EventMonitorException();
		}
	}

	public void doWork(String countryCode, SessionContext context) throws Exception {
		try {
			FeedConfigurationReader configReader = FeedConfigurationReader.getInstance();
			configReader.printConfiguration();
			Iterator iter = configReader.getConfigurationIterator();
			while (iter.hasNext()) {
				FeedConfiguration nextConfig = (FeedConfiguration) (iter.next());
				nextConfig.processFeed(context);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
