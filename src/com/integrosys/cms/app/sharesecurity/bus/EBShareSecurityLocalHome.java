/*
 * Created on Mar 17, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.sharesecurity.bus;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface EBShareSecurityLocalHome extends EJBLocalHome {
	public EBShareSecurityLocal create(IShareSecurity sec) throws CreateException;

	public EBShareSecurityLocal findByPrimaryKey(Long pk) throws FinderException;

	public Collection findByCollateralId(Long colId) throws FinderException;

	public Collection findByColAndSource(Long colId, String sourceId) throws FinderException;
}
