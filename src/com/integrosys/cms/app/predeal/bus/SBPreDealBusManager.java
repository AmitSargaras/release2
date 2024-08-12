/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * SBPreDealBusManager
 *
 * Created on 2:51:35 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.cms.app.predeal.PreDealException;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 23, 2007 Time: 2:51:35 PM
 */
public interface SBPreDealBusManager extends EJBObject {
	public IPreDeal createNewEarMark(IPreDeal data) throws PreDealException, RemoteException;

	public IPreDeal updateEarMark(IPreDeal data) throws PreDealException, RemoteException;

	public IPreDeal getByEarMarkId(String earMarkId) throws PreDealException, RemoteException;

	public IEarMarkGroup getEarMarkGroupBySourceAndFeedId(String sourceSystem, long feedId) throws PreDealException,
			RemoteException;
}
