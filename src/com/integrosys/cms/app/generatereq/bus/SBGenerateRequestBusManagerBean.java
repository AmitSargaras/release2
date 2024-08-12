/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/SBGenerateRequestBusManagerBean.java,v 1.2 2003/09/12 02:29:26 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Session bean implementation of the services provided by the certificate bus
 * manager. This will only contains the persistance logic.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/12 02:29:26 $ Tag: $Name: $
 */
public class SBGenerateRequestBusManagerBean extends AbstractGenerateRequestBusManager implements SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBGenerateRequestBusManagerBean() {
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
	 * To get the number of waiver request that satisfy the criteria
	 * @param aCriteria of WaiverRequestSearchCriteria type
	 * @return int - the number of waiver request that satisfy the criteria
	 * @throws SearchDAOException, GenerateRequestException on errors
	 */
	public int getNoOfWaiverRequest(WaiverRequestSearchCriteria aCriteria) throws SearchDAOException,
			GenerateRequestException {
		try {
			EBWaiverRequestHome home = getEBWaiverRequestHome();
			return home.getNoOfWaiverRequest(aCriteria);
		}
		catch (RemoteException ex) {
			throw new GenerateRequestException("Exception in getNoOfWaiverRequest: " + ex.toString());
		}
	}

	/**
	 * To get the number of deferral request that satisfy the criteria
	 * @param aCriteria of DeferralRequestSearchCriteria type
	 * @return int - the number of deferral request that satisfy the criteria
	 * @throws SearchDAOException, GenerateRequestException on errors
	 */
	public int getNoOfDeferralRequest(DeferralRequestSearchCriteria aCriteria) throws SearchDAOException,
			GenerateRequestException {
		try {
			EBDeferralRequestHome home = getEBDeferralRequestHome();
			return home.getNoOfDeferralRequest(aCriteria);
		}
		catch (RemoteException ex) {
			throw new GenerateRequestException("Exception in getNoOfDeferralRequest: " + ex.toString());
		}
	}

	/**
	 * Get the waiver request based on the request ID
	 * @param aRequestID of long type
	 * @return IWaiverRequest - the cc certificate with the cc certificate ID
	 * @throws GenerateRequestException on errors
	 */
	public IWaiverRequest getWaiverRequest(long aRequestID) throws GenerateRequestException {
		try {
			EBWaiverRequestHome home = getEBWaiverRequestHome();
			EBWaiverRequest remote = home.findByPrimaryKey(new Long(aRequestID));
			return remote.getValue();
		}
		catch (FinderException ex) {
			throw new GenerateRequestException("Exception in getWaiverRequest: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new GenerateRequestException("Exception in getWaiverRequest: " + ex.toString());
		}
	}

	/**
	 * Get the deferral request based on the request ID
	 * @param aRequestID of long type
	 * @return IDeferralRequest - the deferral request with the request ID
	 * @throws GenerateRequestException on errors
	 */
	public IDeferralRequest getDeferralRequest(long aRequestID) throws GenerateRequestException {
		try {
			EBDeferralRequestHome home = getEBDeferralRequestHome();
			EBDeferralRequest remote = home.findByPrimaryKey(new Long(aRequestID));
			return remote.getValue();
		}
		catch (FinderException ex) {
			throw new GenerateRequestException("Exception in getDeferralRequest: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new GenerateRequestException("Exception in getDeferralRequest: " + ex.toString());
		}
	}

	/**
	 * Create a waiver request
	 * @param anIWaiverRequest of IWaiverRequest type
	 * @return IWaiverRequest - the waiver request created
	 * @throws GenerateRequestException on errors
	 */
	public IWaiverRequest createRequest(IWaiverRequest anIWaiverRequest) throws GenerateRequestException {
		try {
			if (anIWaiverRequest == null) {
				throw new GenerateRequestException("The IWaiverRequest to be created is null !!!");
			}
			EBWaiverRequestHome home = getEBWaiverRequestHome();
			EBWaiverRequest remote = home.create(anIWaiverRequest);
			remote.createWaiverRequestItems(anIWaiverRequest);
			return remote.getValue();
		}
		catch (CreateException ex) {
			throw new GenerateRequestException("Exception in createRequest: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new GenerateRequestException("Exception in createRequest: " + ex.toString());
		}
	}

	/**
	 * Create a deferral request
	 * @param anIDeferralRequest of IDeferralRequest type
	 * @return IDeferralRequest - the deferral request created
	 * @throws GenerateRequestException on errors
	 */
	public IDeferralRequest createRequest(IDeferralRequest anIDeferralRequest) throws GenerateRequestException {
		try {
			if (anIDeferralRequest == null) {
				throw new GenerateRequestException("The IDeferralRequest to be created is null !!!");
			}
			EBDeferralRequestHome home = getEBDeferralRequestHome();
			EBDeferralRequest remote = home.create(anIDeferralRequest);
			remote.createDeferralRequestItems(anIDeferralRequest);
			return remote.getValue();
		}
		catch (CreateException ex) {
			throw new GenerateRequestException("Exception in createRequest: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new GenerateRequestException("Exception in createRequest: " + ex.toString());
		}
	}

	/**
	 * Update a waiver request
	 * @param anIWaiverRequest of IWaiverRequest
	 * @return IWaiverRequest - the waiver request updated
	 * @throw GenerateException on errors
	 */
	public IWaiverRequest updateRequest(IWaiverRequest anIWaiverRequest) throws ConcurrentUpdateException,
			GenerateRequestException {
		try {
			if (anIWaiverRequest == null) {
				throw new GenerateRequestException("The IWaiverRequest to be updated is null !!!");
			}
			if (anIWaiverRequest.getRequestID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				throw new GenerateRequestException("The requestID of the waiver request to be updated is invalid !!!");
			}

			EBWaiverRequestHome home = getEBWaiverRequestHome();
			EBWaiverRequest remote = home.findByPrimaryKey(new Long(anIWaiverRequest.getRequestID()));
			remote.setValue(anIWaiverRequest);
			return remote.getValue();
		}
		catch (FinderException ex) {
			throw new GenerateRequestException("Exception in updateRequest: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new GenerateRequestException("Exception in updateRequest: " + ex.toString());
		}
	}

	/**
	 * Update a deferral request
	 * @param anIDeferralRequest of IDeferralRequest
	 * @return IDeferralRequest - the deferral request updated
	 * @throw GenerateException on errors
	 */
	public IDeferralRequest updateRequest(IDeferralRequest anIDeferralRequest) throws ConcurrentUpdateException,
			GenerateRequestException {
		try {
			if (anIDeferralRequest == null) {
				throw new GenerateRequestException("The IDeferralRequest to be updated is null !!!");
			}
			if (anIDeferralRequest.getRequestID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				throw new GenerateRequestException("The requestID of the deferral request to be updated is invalid !!!");
			}

			EBDeferralRequestHome home = getEBDeferralRequestHome();
			EBDeferralRequest remote = home.findByPrimaryKey(new Long(anIDeferralRequest.getRequestID()));
			remote.setValue(anIDeferralRequest);
			return remote.getValue();
		}
		catch (FinderException ex) {
			throw new GenerateRequestException("Exception in updateRequest: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new GenerateRequestException("Exception in updateRequest: " + ex.toString());
		}
	}

	/**
	 * To get the home handler for the Waiver Request Entity Bean
	 * @return EBWaiverRequestHome - the home handler for the CC Certificate
	 *         entity bean
	 */
	protected EBWaiverRequestHome getEBWaiverRequestHome() {
		EBWaiverRequestHome ejbHome = (EBWaiverRequestHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_WAIVER_REQUEST_JNDI, EBWaiverRequestHome.class.getName());
		return ejbHome;
	}

	/**
	 * To get the home handler for the deferral Request Entity Bean
	 * @return EBDeferralRequestHome - the home handler for the CC Certificate
	 *         entity bean
	 */
	protected EBDeferralRequestHome getEBDeferralRequestHome() {
		EBDeferralRequestHome ejbHome = (EBDeferralRequestHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_DEFERRAL_REQUEST_JNDI, EBDeferralRequestHome.class.getName());
		return ejbHome;
	}
}
