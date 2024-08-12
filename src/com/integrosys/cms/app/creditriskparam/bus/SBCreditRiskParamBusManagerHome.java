package com.integrosys.cms.app.creditriskparam.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * SBCreditRiskParamBusManagerHome Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface SBCreditRiskParamBusManagerHome extends EJBHome {

	public SBCreditRiskParamBusManager create() throws CreateException, RemoteException;

}
