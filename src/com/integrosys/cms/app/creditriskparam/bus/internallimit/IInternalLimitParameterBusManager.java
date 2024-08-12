/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/IForexFeedBusManager.java,v 1.1 2003/08/11 04:08:19 btchng Exp $
 */
package com.integrosys.cms.app.creditriskparam.bus.internallimit;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/11 04:08:19 $ Tag: $Name: $
 */
public interface IInternalLimitParameterBusManager extends Serializable {

	public List getALLInternalLimitParameter() throws InternalLimitException;

	public List getInternalLimitParameterByGroupID(long groupID) throws InternalLimitException;

	public List createInternalListParameter(List ilpList) throws InternalLimitException;
	
	public List updateInternalListParameter(List ilPList) throws InternalLimitException;

    public IInternalLimitParameter getInternalLimitParameterByEntityType(String type) throws InternalLimitException;
}
