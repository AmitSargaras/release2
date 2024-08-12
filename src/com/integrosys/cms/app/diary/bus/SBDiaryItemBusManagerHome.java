/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/SBDiaryItemBusManagerHome.java,v 1.1 2004/05/12 13:09:47 jtan Exp $
 */
package com.integrosys.cms.app.diary.bus;

//java

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Session bean home interface for the services provided by the diary item
 * location bus manager
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/05/12 13:09:47 $ Tag: $Name: $
 */
public interface SBDiaryItemBusManagerHome extends EJBHome {
	public SBDiaryItemBusManager create() throws CreateException, RemoteException;
}
