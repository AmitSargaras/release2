/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/SBCCCertificateBusManagerBean.java,v 1.8 2004/01/13 06:22:02 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Session bean implementation of the services provided by the certificate bus
 * manager. This will only contains the persistance logic.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2004/01/13 06:22:02 $ Tag: $Name: $
 */
public class SBCCCertificateBusManagerBean extends AbstractCCCertificateBusManager implements SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBCCCertificateBusManagerBean() {
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
	 * Get the CC Certificate based on the CC Certificate ID
	 * @param aCCCertID of long type
	 * @return ICCCertificate - the cc certificate with the cc certificate ID
	 * @throws CCCertificateException on errors
	 */
	public ICCCertificate getCCCertificate(long aCCCertID) throws CCCertificateException {
		try {
			EBCCCertificateHome home = getEBCCCertificateHome();
			EBCCCertificate remote = home.findByPrimaryKey(new Long(aCCCertID));
			return remote.getValue();
		}
		catch (FinderException ex) {
			throw new CCCertificateException("Exception in getCCCertificate: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getCCCertificate: " + ex.toString());
		}
	}

	/**
	 * Get the list of cc certificate trx based on the limit profile ID
	 * @param aLimitProfileID of long type
	 * @return CCCertificateSearchResult[] - the list of cc certificate trx info
	 * @throws SearchDAOException on errors
	 */
	public CCCertificateSearchResult[] getCCCertificateGenerated(long aLimitProfileID) throws SearchDAOException,
			CCCertificateException {
		try {
			EBCCCertificateHome home = getEBCCCertificateHome();
			return home.getNoOfCCCGenerated(aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getNoOfCCCGenerated: " + ex.toString());
		}
	}

	/**
	 * To get the number of cc certificate that satisfy the criteria
	 * @param aCriteria of CCCertificateSearchCriteria type
	 * @return int - the number of cc certificate that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfCCCertificate(CCCertificateSearchCriteria aCriteria) throws SearchDAOException,
			CCCertificateException {
		try {
			EBCCCertificateHome home = getEBCCCertificateHome();
			return home.getNoOfCCCertificate(aCriteria);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getNoOfCCCertificate: " + ex.toString());
		}
	}

	/**
	 * Get the main borrower CC Certificate
	 * @param aLimitProfileID of long type
	 * @param anOwnerID of long type
	 * @return ICCCertificate - the cc certificate of the main borrower
	 * @throws CCCertificateException on errors
	 */
	public ICCCertificate getMainBorrowerCCC(long aLimitProfileID, long anOwnerID) throws CCCertificateException {
		try {
			EBCCCertificateHome home = getEBCCCertificateHome();
			CCCertificateSearchCriteria criteria = new CCCertificateSearchCriteria();
			criteria.setLimitProfileID(aLimitProfileID);
			criteria.setCategory(ICMSConstant.CHECKLIST_MAIN_BORROWER);
			criteria.setSubProfileID(anOwnerID);
			long cccID = home.getCCCID(criteria);
			if (cccID != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				EBCCCertificate remote = home.findByPrimaryKey(new Long(cccID));
				return remote.getValue();
			}
			return null;
		}
		catch (FinderException ex) {
			// throw new
			// CCCertificateException("Exception in getMainBorrowerCCC: " +
			// ex.toString());
			return null;
		}
		catch (SearchDAOException ex) {
			throw new CCCertificateException("Exception in getNonBorrowerCCC", ex);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getMainBorrowerCCC: " + ex.toString());
		}
	}

	/**
	 * Get the Co-Borrower CC Certificate
	 * @param aLimitProfileID of long type
	 * @param anOwnerID of long type
	 * @return ICCCertificate - the co-borrower cc certificate
	 * @throws CCCertificateException on errors
	 */
	public ICCCertificate getCoBorrowerCCC(long aLimitProfileID, long anOwnerID) throws CCCertificateException {
		try {
			EBCCCertificateHome home = getEBCCCertificateHome();
			CCCertificateSearchCriteria criteria = new CCCertificateSearchCriteria();
			criteria.setLimitProfileID(aLimitProfileID);
			criteria.setCategory(ICMSConstant.CHECKLIST_CO_BORROWER);
			criteria.setSubProfileID(anOwnerID);
			long cccID = home.getCCCID(criteria);
			if (cccID != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				EBCCCertificate remote = home.findByPrimaryKey(new Long(cccID));
				return remote.getValue();
			}
			return null;
		}
		catch (FinderException ex) {
			// throw new
			// CCCertificateException("Exception in getCoBorrowerCCC: " +
			// ex.toString());
			return null;
		}
		catch (SearchDAOException ex) {
			throw new CCCertificateException("Exception in getNonBorrowerCCC", ex);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getCoBorrowerCCC: " + ex.toString());
		}
	}

	/**
	 * Get the pledgor CC Certificate
	 * @param aLimitProfileID of long type
	 * @param anOwnerID of long type
	 * @return ICCCertificate - the cc certificate of the pledgor
	 * @throws CCCertificateException on errors
	 */
	public ICCCertificate getPledgorCCC(long aLimitProfileID, long anOwnerID) throws CCCertificateException {
		try {
			EBCCCertificateHome home = getEBCCCertificateHome();
			CCCertificateSearchCriteria criteria = new CCCertificateSearchCriteria();
			criteria.setLimitProfileID(aLimitProfileID);
			criteria.setCategory(ICMSConstant.CHECKLIST_PLEDGER);
			criteria.setPledgorID(anOwnerID);
			long cccID = home.getCCCID(criteria);
			if (cccID != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				EBCCCertificate remote = home.findByPrimaryKey(new Long(cccID));
				return remote.getValue();
			}
			return null;
		}
		catch (FinderException ex) {
			// throw new CCCertificateException("Exception in getPledgorCCC: " +
			// ex.toString());
			return null;
		}
		catch (SearchDAOException ex) {
			throw new CCCertificateException("Exception in getPledgorCCC", ex);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getPledgorCCC: " + ex.toString());
		}
	}

	/**
	 * Get the non borrower CC Certificate
	 * @param anOwnerID of long type
	 * @return ICCCertificate - the non borrower cc certificate
	 * @throws CCCertificateException on errors
	 */
	public ICCCertificate getNonBorrowerCCC(long anOwnerID) throws CCCertificateException {
		try {
			EBCCCertificateHome home = getEBCCCertificateHome();
			CCCertificateSearchCriteria criteria = new CCCertificateSearchCriteria();
			criteria.setCategory(ICMSConstant.CHECKLIST_NON_BORROWER);
			criteria.setSubProfileID(anOwnerID);
			long cccID = home.getCCCID(criteria);
			if (cccID != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				EBCCCertificate remote = home.findByPrimaryKey(new Long(cccID));
				return remote.getValue();
			}
			return null;
		}
		catch (FinderException ex) {
			// throw new
			// CCCertificateException("Exception in getNonBorrowerCCC: " +
			// ex.toString());
			return null;
		}
		catch (SearchDAOException ex) {
			throw new CCCertificateException("Exception in getNonBorrowerCCC", ex);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getNonBorrowerCCC: " + ex.toString());
		}
	}

	/**
	 * Create a CC Certificate
	 * @param anICCCertificate of ICCCertificate type
	 * @return ICCCertificate - the cc certificate created
	 * @throws CCCertificateException on errors
	 */
	public ICCCertificate createCCCertificate(ICCCertificate anICCCertificate) throws CCCertificateException {
		try {
			if (anICCCertificate == null) {
				throw new CCCertificateException("The ICCCertificate to be created is null !!!");
			}
			EBCCCertificateHome home = getEBCCCertificateHome();
			EBCCCertificate remote = home.create(anICCCertificate);
			remote.createCCCertificateItems(anICCCertificate);
			return remote.getValue();
		}
		catch (CreateException ex) {
			throw new CCCertificateException("Exception in createCCCertificate: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in createCCCertificate: " + ex.toString());
		}
	}

	/**
	 * Update a CC Certificate
	 * @param anICCCerticate of ICCCertificate
	 * @return ICCCertificate - the cc certificate updated
	 * @throws CCCertificateException on errors
	 */
	public ICCCertificate updateCCCertificate(ICCCertificate anICCCertificate) throws ConcurrentUpdateException,
			CCCertificateException {
		try {
			if (anICCCertificate == null) {
				throw new CCCertificateException("The ICCCertificate to be updated is null !!!");
			}
			if (anICCCertificate.getCCCertID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				throw new CCCertificateException("The CCCertID of the CCC to be updated is invalid !!!");
			}

			EBCCCertificateHome home = getEBCCCertificateHome();
			EBCCCertificate remote = home.findByPrimaryKey(new Long(anICCCertificate.getCCCertID()));
			remote.setValue(anICCCertificate);
			return remote.getValue();
		}
		catch (FinderException ex) {
			throw new CCCertificateException("Exception in updateCCCertificate: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in updateCCCertificate: " + ex.toString());
		}
	}

	/**
	 * Get the cc certificate trx ID
	 * @param aCriteria of CCCertificateSearchCriteria type
	 * @return String - the CCC TRX ID
	 * @throws SearchDAOException on errors
	 */
	public String getCCCTrxID(CCCertificateSearchCriteria aCriteria) throws SearchDAOException, CCCertificateException {
		try {
			EBCCCertificateHome home = getEBCCCertificateHome();
			return home.getCCCTrxID(aCriteria);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getCCCTrxID: " + ex.toString());
		}
	}

	/**
	 * To get the home handler for the CCCertificate Entity Bean
	 * @return EBCCCertificateHome - the home handler for the CC Certificate
	 *         entity bean
	 */
	protected EBCCCertificateHome getEBCCCertificateHome() {
		EBCCCertificateHome ejbHome = (EBCCCertificateHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_CCCERTIFICATE_JNDI, EBCCCertificateHome.class.getName());
		return ejbHome;
	}
}
