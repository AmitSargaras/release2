/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/SBSCCertificateBusManagerBean.java,v 1.6 2003/11/26 10:26:02 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Session bean implementation of the services provided by the certificate bus
 * manager. This will only contains the persistance logic.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/11/26 10:26:02 $ Tag: $Name: $
 */
public class SBSCCertificateBusManagerBean extends AbstractSCCertificateBusManager implements SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBSCCertificateBusManagerBean() {
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
	 * Get the SC Certificate based on the SC Certificate ID
	 * @param aSCCertID of long type
	 * @return ISCCertificate - the SC certificate with the SC certificate ID
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificate getSCCertificate(long aSCCertID) throws SCCertificateException {
		try {
			EBSCCertificateHome home = getEBSCCertificateHome();
			EBSCCertificate remote = home.findByPrimaryKey(new Long(aSCCertID));
			return remote.getValue();
		}
		catch (FinderException ex) {
			throw new SCCertificateException("Exception in getSCCertificate: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getSCCertificate: " + ex.toString());
		}
	}

	/**
	 * Get the Partial SC Certificate based on the Partial SC Certificate ID
	 * @param aSCCertID of long type
	 * @return IPartialSCCertificate - the Partial SC certificate with the
	 *         Partial SC certificate ID
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificate getPartialSCCertificate(long aSCCertID) throws SCCertificateException {
		try {
			EBPartialSCCertificateHome home = getEBPartialSCCertificateHome();
			EBPartialSCCertificate remote = home.findByPrimaryKey(new Long(aSCCertID));
			return remote.getValue();
		}
		catch (FinderException ex) {
			throw new SCCertificateException("Exception in getPartialSCCertificate: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getPartialSCCertificate: " + ex.toString());
		}
	}

	/**
	 * Get the SC Certificate by the limit profile ID
	 * @param aLimitProfileID of long type
	 * @return ISCCertificate - the sc certificate
	 * @throws CertificationException on errors
	 */
	public ISCCertificate getSCCertificateByLimitProfileID(long aLimitProfileID) throws SCCertificateException {
		try {
			EBSCCertificateHome home = getEBSCCertificateHome();
			long sccID = home.getSCCIDbyLimitProfile(aLimitProfileID);
			EBSCCertificate remote = home.findByPrimaryKey(new Long(sccID));
			return remote.getValue();
		}
		catch (SearchDAOException ex) {
			throw new SCCertificateException("SearchDAOException", ex);
		}
		catch (FinderException ex) {
			// throw new SCCertificateException(
			// "Exception in getSCCertificateByLimitProfileID: " +
			// ex.toString());
			DefaultLogger.info("Cannot find SCC with LimitProfileID " + aLimitProfileID, ex);
			return null;
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getSCCertificateByLimitProfileID: " + ex.toString());
		}
	}

	/**
	 * Get the Partial SC Certificate by the limit profile ID
	 * @param aLimitProfileID of long type
	 * @return IPartialSCCertificate - the Partial sc certificate
	 * @throws CertificationException on errors
	 */
	public IPartialSCCertificate getPartialSCCertificateByLimitProfileID(long aLimitProfileID)
			throws SCCertificateException {
		try {
			/*
			 * EBPartialSCCertificateHome home =
			 * getEBPartialSCCertificateHome(); Collection remoteList =
			 * home.findByLimitProfileID(new Long(aLimitProfileID)); if
			 * (remoteList.size() == 0) { return null; } if (remoteList.size() >
			 * 1) { throw newSCCertificateException(
			 * "There is more than 1 SCC undet the limit profile " +
			 * aLimitProfileID); } Iterator iter = remoteList.iterator();
			 * while(iter.hasNext()) { EBPartialSCCertificate remote =
			 * (EBPartialSCCertificate)iter.next(); return remote.getValue(); }
			 * return null;
			 */
			EBPartialSCCertificateHome home = getEBPartialSCCertificateHome();
			long sccID = home.getPSCCIDbyLimitProfile(aLimitProfileID);
			EBPartialSCCertificate remote = home.findByPrimaryKey(new Long(sccID));
			return remote.getValue();
		}
		catch (SearchDAOException ex) {
			throw new SCCertificateException("SearchDAOException", ex);
		}
		catch (FinderException ex) {
			// throw new SCCertificateException(
			// "Exception in getSCCertificateByLimitProfileID: " +
			// ex.toString());
			DefaultLogger.info("Cannot find PSCC with LimitProfileID " + aLimitProfileID, ex);
			return null;
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getPartialSCCertificateByLimitProfileID: " + ex.toString());
		}
	}

	/**
	 * To get the number of SC certificate that satisfy the criteria
	 * @param aCriteria of SCCertificateSearchCriteria type
	 * @return int - the number of SC certificate that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfSCCertificate(SCCertificateSearchCriteria aCriteria) throws SearchDAOException,
			SCCertificateException {
		try {
			EBSCCertificateHome home = getEBSCCertificateHome();
			return home.getNoOfSCCertificate(aCriteria);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getNoOfSCCertificate: " + ex.toString());
		}
	}

	/**
	 * To get the number of Partial SC certificate that satisfy the criteria
	 * @param aCriteria of SCCertificateSearchCriteria type
	 * @return int - the number of SC certificate that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfPartialSCCertificate(SCCertificateSearchCriteria aCriteria) throws SearchDAOException,
			SCCertificateException {
		try {
			EBPartialSCCertificateHome home = getEBPartialSCCertificateHome();
			return home.getNoOfPartialSCCertificate(aCriteria);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getNoOfSCCertificate: " + ex.toString());
		}
	}

	/**
	 * Create a SC Certificate
	 * @param anISCCertificate of ISCCertificate type
	 * @return ISCCertificate - the SC certificate created
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificate createSCCertificate(ISCCertificate anISCCertificate) throws SCCertificateException {
		try {
			if (anISCCertificate == null) {
				throw new SCCertificateException("The ISCCertificate to be created is null !!!");
			}
			EBSCCertificateHome home = getEBSCCertificateHome();
			EBSCCertificate remote = home.create(anISCCertificate);
			remote.createSCCertificateItems(anISCCertificate);
			return remote.getValue();
		}
		catch (CreateException ex) {
			throw new SCCertificateException("Exception in createSCCertificate: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in createSCCertificate: " + ex.toString());
		}
	}

	/**
	 * Create a Partial SC Certificate
	 * @param anIPartialSCCertificate of IPartialSCCertificate type
	 * @return IPartialSCCertificate - the Partial SC certificate created
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificate createPartialSCCertificate(IPartialSCCertificate anIPartialSCCertificate)
			throws SCCertificateException {
		try {
			if (anIPartialSCCertificate == null) {
				throw new SCCertificateException("The IPartialSCCertificate to be created is null !!!");
			}
			EBPartialSCCertificateHome home = getEBPartialSCCertificateHome();
			EBPartialSCCertificate remote = home.create(anIPartialSCCertificate);
			remote.createPartialSCCertificateItems(anIPartialSCCertificate);
			return remote.getValue();
		}
		catch (CreateException ex) {
			throw new SCCertificateException("Exception in createPartialSCCertificate: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in createPartialSCCertificate: " + ex.toString());
		}
	}

	/**
	 * Update a SC Certificate
	 * @param anISCCerticate of ISCCertificate
	 * @return ISCCertificate - the SC certificate updated
	 * @throws SCCertificateException on errors
	 */
	public ISCCertificate updateSCCertificate(ISCCertificate anISCCertificate) throws ConcurrentUpdateException,
			SCCertificateException {
		try {
			if (anISCCertificate == null) {
				throw new SCCertificateException("The ISCCertificate to be updated is null !!!");
			}
			if (anISCCertificate.getSCCertID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				throw new SCCertificateException("The SCCertID of the SCC to be updated is invalid !!!");
			}
			EBSCCertificateHome home = getEBSCCertificateHome();
			EBSCCertificate remote = home.findByPrimaryKey(new Long(anISCCertificate.getSCCertID()));
			remote.setValue(anISCCertificate);
			return remote.getValue();
		}
		catch (FinderException ex) {
			throw new SCCertificateException("Exception in updateSCCertificate: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in updateSCCertificate: " + ex.toString());
		}
	}

	/**
	 * Update a Partial SC Certificate
	 * @param anIPartialSCCerticate of IPartialSCCertificate
	 * @return IPartialSCCertificate - the Partial SC certificate updated
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificate updatePartialSCCertificate(IPartialSCCertificate anIPartialSCCertificate)
			throws ConcurrentUpdateException, SCCertificateException {
		try {
			if (anIPartialSCCertificate == null) {
				throw new SCCertificateException("The IPartialSCCertificate to be updated is null !!!");
			}
			if (anIPartialSCCertificate.getSCCertID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				throw new SCCertificateException("The SCCertID of the Partial SCC to be updated is invalid !!!");
			}
			EBPartialSCCertificateHome home = getEBPartialSCCertificateHome();
			EBPartialSCCertificate remote = home.findByPrimaryKey(new Long(anIPartialSCCertificate.getSCCertID()));
			remote.setValue(anIPartialSCCertificate);
			return remote.getValue();
		}
		catch (FinderException ex) {
			throw new SCCertificateException("Exception in updatePartialSCCertificate: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in updatePartialSCCertificate: " + ex.toString());
		}
	}

	/**
	 * To get the SCC Trx ID by limit profile ID
	 * @param aLimitProfileID of long type
	 * @return String - the SCC Trx ID
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on errors
	 */
	public String getSCCTrxIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException, SCCertificateException {
		try {
			EBSCCertificateHome home = getEBSCCertificateHome();
			return home.getSCCTrxIDbyLimitProfile(aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getSCCTrxIDbyLimitProfile", ex);
		}
	}

	/**
	 * To get the PSCC Trx ID by limit profile ID
	 * @param aLimitProfileID of long type
	 * @return String - the SCC Trx ID
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on errors
	 */
	public String getPSCCTrxIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException, SCCertificateException {
		try {
			EBPartialSCCertificateHome home = getEBPartialSCCertificateHome();
			return home.getPSCCTrxIDbyLimitProfile(aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getSCCTrxIDbyLimitProfile", ex);
		}
	}

	/**
	 * To get the home handler for the SCCertificate Entity Bean
	 * @return EBSCCCertificateHome - the home handler for the SC Certificate
	 *         entity bean
	 */
	protected EBSCCertificateHome getEBSCCertificateHome() {
		EBSCCertificateHome ejbHome = (EBSCCertificateHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_SCCERTIFICATE_JNDI, EBSCCertificateHome.class.getName());
		return ejbHome;
	}

	/**
	 * To get the home handler for the PartialSCCertificate Entity Bean
	 * @return EBPartialSCCCertificateHome - the home handler for the Partial SC
	 *         Certificate entity bean
	 */
	protected EBPartialSCCertificateHome getEBPartialSCCertificateHome() {
		EBPartialSCCertificateHome ejbHome = (EBPartialSCCertificateHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_PSCCERTIFICATE_JNDI, EBPartialSCCertificateHome.class.getName());
		return ejbHome;
	}
}
