package com.integrosys.cms.app.propertyparameters.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Jan 30, 2007 Time: 4:11:23 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EBPropertyParametersLocalHome extends EJBLocalHome {

	public EBPropertyParametersLocal create(IPropertyParameters anICollateralTask) throws CreateException;

	public EBPropertyParametersLocal findByPrimaryKey(Long aPK) throws FinderException;

}
