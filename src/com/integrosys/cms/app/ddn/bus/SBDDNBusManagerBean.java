/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/SBDDNBusManagerBean.java,v 1.6 2005/06/08 06:39:05 htli Exp $
 */
package com.integrosys.cms.app.ddn.bus;

//java
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Session bean implementation of the services provided by the DDN bus manager.
 * This will only contains the persistance logic.
 * 
 * @author $Author: htli $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/06/08 06:39:05 $ Tag: $Name: $
 */
public class SBDDNBusManagerBean extends AbstractDDNBusManager implements SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBDDNBusManagerBean() {
	}

	public void ejbCreate() {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void setSessionContext(javax.ejb.SessionContext sc) {
		_context = sc;
	}

	/**
	 * Get the DDN based on the DDN ID
	 * @param aDDNID of long type
	 * @return IDDN - the DDN with the DDN ID
	 * @throws DDNException on errors
	 */
	public IDDN getDDN(long aDDNID) throws DDNException {
		try {
			EBDDNHome home = getEBDDNHome();
			EBDDN remote = home.findByPrimaryKey(new Long(aDDNID));
			return remote.getValue();
		}
		catch (FinderException ex) {
			throw new DDNException("Exception in getDDN: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in getDDN: " + ex.toString());
		}
	}

	/**
	 * Get the DDN by the limit profile ID
	 * @param aLimitProfileID of long type
	 * @return IDDN - the DDN
	 * @throws DDNException on errors
	 */
	public IDDN getDDNByLimitProfileID(long aLimitProfileID, String type) throws DDNException {
		try {
			EBDDNHome home = getEBDDNHome();
            Collection remoteList = home.findByLimitProfileID(new Long(aLimitProfileID));
            for (Iterator iterator = remoteList.iterator(); iterator.hasNext();) {
                EBDDN ebddn = (EBDDN) iterator.next();
                return ebddn.getValue();
            }
			return null;
		}
		catch (SearchDAOException ex) {
			throw new DDNException("SearchDAOException", ex);
		}
		catch (FinderException ex) {
			DefaultLogger.info("Cannot find DDN with LimitProfileID " + aLimitProfileID, ex);
			return null;
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in getDDNByLimitProfileID: " + ex.toString());
		}
	}

	/**
	 * To get the number of DDN that satisfy the criteria
	 * @param aCriteria of DDNSearchCriteria type
	 * @return int - the number of DDN that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfDDN(DDNSearchCriteria aCriteria) throws SearchDAOException, DDNException {
		try {
			EBDDNHome home = getEBDDNHome();
			return home.getNoOfDDN(aCriteria);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in getNoOfDDN: " + ex.toString());
		}
	}

	/**
	 * Create a DDN
	 * @param anIDDN of IDDN type
	 * @return IDDN - the DDN created
	 * @throws DDNException on errors
	 */
	public IDDN createDDN(IDDN anIDDN) throws DDNException {
		try {
			if (anIDDN == null) {
				throw new DDNException("The IDDN to be created is null !!!");
			}
			EBDDNHome home = getEBDDNHome();
			EBDDN remote = home.create(anIDDN);
			remote.createDDNItems(anIDDN);
			return remote.getValue();
		}
		catch (CreateException ex) {
			throw new DDNException("Exception in createDDN: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in createDDN: " + ex.toString());
		}
	}

	/**
	 * Update a DDN
	 * @param anIDDN of IDDN
	 * @return IDDN - the DDN updated
	 * @throws DDNException on errors
	 */
	public IDDN updateDDN(IDDN anIDDN) throws ConcurrentUpdateException, DDNException {
		try {
			if (anIDDN == null) {
				throw new DDNException("The IDDN to be updated is null !!!");
			}
			if (anIDDN.getDDNID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				throw new DDNException("The DDNID of the DDN to be updated is invalid !!!");
			}
			EBDDNHome home = getEBDDNHome();
			EBDDN remote = home.findByPrimaryKey(new Long(anIDDN.getDDNID()));
			remote.setValue(anIDDN);
			return remote.getValue();
		}
		catch (FinderException ex) {
			throw new DDNException("Exception in updateDDN: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in updateDDN: " + ex.toString());
		}
	}

	/**
	 * Update the scc issued indicator
	 * @param aLimitProfileID of long type
	 * @param anIndicator of boolean type
	 * @throws ConcurrentUpdateException if there is a concurrent update
	 * @throws DDNException on errors
	 */
	public void setSCCIssuedIndicator(long aLimitProfileID, boolean anIndicator) throws ConcurrentUpdateException,
			DDNException {
		try {
			EBDDNHome home = getEBDDNHome();
			Collection remoteList = home.findByLimitProfileID(new Long(aLimitProfileID));
			Iterator iter = remoteList.iterator();
			while (iter.hasNext()) {
				EBDDN remote = (EBDDN) iter.next();
				IDDN ddn = remote.getValue();
				ddn.setIsSCCIssuedInd(anIndicator);
				remote.setValue(ddn);
			}
		}
		catch (FinderException ex) {
			DefaultLogger.info(this, "No DDN for this limit profile ID: " + aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in sccIssued: " + ex.toString());
		}
	}

	/**
	 * To get the home handler for the DDN Entity Bean
	 * @return EBDDNHome - the home handler for the DDN entity bean
	 */
	protected EBDDNHome getEBDDNHome() {
		EBDDNHome ejbHome = (EBDDNHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_DDN_JNDI, EBDDNHome.class
				.getName());
		return ejbHome;
	}
}
