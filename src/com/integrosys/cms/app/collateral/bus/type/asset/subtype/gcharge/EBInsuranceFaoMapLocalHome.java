/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBInsuranceFaoMapLocalHome.java,v 1.1 2005/03/17 07:06:15 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Entity bean local home interface for post dated cheque.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/03/17 07:06:15 $ Tag: $Name: $
 */
public interface EBInsuranceFaoMapLocalHome extends EJBLocalHome {
	/**
	 * Create insurance info.
	 * 
	 * @param insurance of type IGenChargeMapEntry
	 * @return local insurance info ejb object
	 * @throws CreateException on error creating the insurance Fao map
	 */
	public EBInsuranceFaoMapLocal create(IGenChargeMapEntry mapEntry) throws CreateException;

	/**
	 * Find the insurance info by its primary key.
	 * 
	 * @param pk map entry id
	 * @return local insurance Fao map ejb object
	 * @throws FinderException on error finding the insurance Fao map
	 */
	public EBInsuranceFaoMapLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find the insurance Fao map by reference id.
	 * 
	 * @param refID reference id of actual and staging data
	 * @return local insurance Fao map
	 * @throws FinderException on error finding the insurance Fao map
	 */
	public EBInsuranceFaoMapLocal findByRefID(long refID) throws FinderException;
}