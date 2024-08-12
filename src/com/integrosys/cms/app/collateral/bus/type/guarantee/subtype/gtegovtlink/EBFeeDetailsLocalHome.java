package com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Mar 1, 2007 Time: 3:42:13 PM
 * To change this template use File | Settings | File Templates.
 */

public interface EBFeeDetailsLocalHome extends EJBLocalHome {

	public EBFeeDetailsLocal create(IFeeDetails FeeDetails) throws CreateException;

	public EBFeeDetailsLocal findByPrimaryKey(Long pk) throws FinderException;

	public EBFeeDetailsLocal findByRefID(long refID) throws FinderException;
}