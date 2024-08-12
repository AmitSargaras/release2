/*
 * Created on Apr 10, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.manualinput.aa.proxy;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import java.math.BigDecimal;
/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface SBMIAAProxy extends EJBObject {
	public SearchResult searchMICustomer(CustomerSearchCriteria criteria) throws SearchDAOException, RemoteException;
	public List getCreditApproverList() throws SearchDAOException, RemoteException;
	public List getCreditApproverListWithLimit(BigDecimal sanctLmt) throws SearchDAOException, RemoteException;	
	public List getCheckCreditApproval(String approverCode) throws SearchDAOException, RemoteException;
}
