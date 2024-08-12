/*
 * Created on May 28, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.batch.reuterfeed;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface IFeedReader {
	public void setFeedConfiguration(FeedConfiguration config);

	public FeedConfiguration getFeedConfiguration();

	public void initialize() throws Exception;

	public boolean hasMoreRows() throws Exception;

	public void processNextLineFromFeed() throws Exception;

	public void clearup() throws Exception;
}
