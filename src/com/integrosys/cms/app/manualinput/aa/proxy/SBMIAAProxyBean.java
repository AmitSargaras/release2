/*
 * Created on Apr 9, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.manualinput.aa.proxy;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.customer.bus.CustomerDAO;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.manualinput.aa.bus.CAMDAO;
import java.math.BigDecimal;
/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SBMIAAProxyBean implements SessionBean {
	private SessionContext _context = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext sc) throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		_context = sc;
	}

	/**
	 * Called by the container to create a session bean instance. Its parameters
	 * typically contain the information the client uses to customize the bean
	 * instance for its use. It requires a matching pair in the bean class and
	 * its home interface.
	 */
	public void ejbCreate() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public SearchResult searchMICustomer(CustomerSearchCriteria criteria) throws SearchDAOException {
		try {
			CustomerDAO custDao = new CustomerDAO();
			SearchResult res = custDao.searchCustomer(criteria);
			return res;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException();
		}
	}
	
	public List getCreditApproverList() throws SearchDAOException {
		try {
			CAMDAO camDao = new CAMDAO();
			List res = camDao.getCreditApproverList();
			return res;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException();
		}
	}
	
	public List getCreditApproverListWithLimit(BigDecimal sanctLmt) throws SearchDAOException {
		try {
			CAMDAO camDao = new CAMDAO();
			List res = camDao.getCreditApproverListWithLimit(sanctLmt);
			return res;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException();
		}
	}
	
	
	public List getCheckCreditApproval(String approverCode) throws SearchDAOException {
		try {
			CAMDAO camDao = new CAMDAO();
			List res = camDao.getCheckCreditApproval(approverCode);
			return res;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException();
		}
	}
}
