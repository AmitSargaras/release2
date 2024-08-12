/*
 * Created on May 28, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.batch.reuterfeed;

import java.util.Map;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface IFeedPostProcessor {
	public void performFeedPostProcess(Map fieldsMapping) throws Exception;
}
