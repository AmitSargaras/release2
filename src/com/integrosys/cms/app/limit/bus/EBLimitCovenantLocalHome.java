package com.integrosys.cms.app.limit.bus;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EBLimitCovenantLocalHome extends EJBLocalHome {
	/**
	 * Create a limit x-ref information type
	 * 
	 * @param value is the ILimitCovenant object
	 * @return EBLimitCovenantLocal
	 * @throws CreateException on error
	 */
	public EBLimitCovenantLocal create(ILimitCovenant value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param pk is Long value of the primary key
	 * @return EBLimitCovenantLocal
	 * @throws FinderException on error
	 */
	public EBLimitCovenantLocal findByPrimaryKey(Long pk) throws FinderException;
	

}