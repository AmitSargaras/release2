/*
 * Created on Jun 22, 2003
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.EJBLocalObject;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface EBSecApportionmentLocal extends EJBLocalObject {

	public ISecApportionment getValue();

	public void setValue(ISecApportionment value);

}
