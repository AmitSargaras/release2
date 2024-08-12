/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/pdcheque/EBPostDatedChequeLocalHome.java,v 1.2 2003/08/20 04:21:50 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Entity bean local home interface for post dated cheque.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/20 04:21:50 $ Tag: $Name: $
 */
public interface EBPostDatedChequeLocalHome extends EJBLocalHome {
	/**
	 * Create post dated cheque.
	 * 
	 * @param cheque of type IPostDatedCheque
	 * @return local post dated cheque ejb object
	 * @throws CreateException on error creating the post dated cheque
	 */
	public EBPostDatedChequeLocal create(IPostDatedCheque cheque) throws CreateException;

	/**
	 * Find the post dated cheque by its primary key.
	 * 
	 * @param pk post dated cheque id
	 * @return local post dated cheque ejb object
	 * @throws FinderException on error finding the post dated cheque
	 */
	public EBPostDatedChequeLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find the post dated cheque by reference id.
	 * 
	 * @param refID reference id of actual and staging data
	 * @return local post dated cheque
	 * @throws FinderException on error findign the post dated cheque
	 */
	public EBPostDatedChequeLocal findByRefID(long refID) throws FinderException;
}