/*
 * Created on Mar 17, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.sharesecurity.bus;

import javax.ejb.EJBLocalObject;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface EBShareSecurityLocal extends EJBLocalObject {
	public IShareSecurity getValue() throws Exception;

	public void setValue(IShareSecurity sec) throws Exception;
}
