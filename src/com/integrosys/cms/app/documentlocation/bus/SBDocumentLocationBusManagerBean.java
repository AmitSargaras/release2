/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/bus/SBDocumentLocationBusManagerBean.java,v 1.4 2004/08/05 06:26:54 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.IPledgor;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;

/**
 * Session bean implementation of the services provided by the certificate bus
 * manager. This will only contains the persistance logic.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/05 06:26:54 $ Tag: $Name: $
 */
public class SBDocumentLocationBusManagerBean extends AbstractDocumentLocationBusManager implements SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBDocumentLocationBusManagerBean() {
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
	 * To get the number of cc documentation location that satisfy the criteria
	 * @param aCriteria of DocumentLocationSearchCriteria type
	 * @return int - the number of CC documentation location that satisfy the
	 *         criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfCCDocumentLocation(CCDocumentLocationSearchCriteria aCriteria) throws SearchDAOException,
			DocumentLocationException {
		try {
			EBCCDocumentLocationHome home = getEBCCDocumentLocationHome();
			return home.getNoOfCCDocumentLocation(aCriteria);
		}
		catch (RemoteException ex) {
			throw new DocumentLocationException("Exception in getNoOfCCDocumentLocation: " + ex.toString());
		}
	}

	/**
	 * To get the list of cc documentation location that satisfy the criteria
	 * @param aCritieria of CCDocumentLocationSearchCritieria type
	 * @return CCDocumentLocationSearchResult[] - the list of CC documentation
	 *         location
	 * @throws SearchDAOException
	 */
	public CCDocumentLocationSearchResult[] getCCDocumentLocation(CCDocumentLocationSearchCriteria aCriteria)
			throws SearchDAOException, DocumentLocationException {
		try {
			EBCCDocumentLocationHome home = getEBCCDocumentLocationHome();
			return home.getCCDocumentLocation(aCriteria);
		}
		catch (RemoteException ex) {
			throw new DocumentLocationException("Exception in getCCDocumentLocation: " + ex.toString());
		}
	}

	/**
	 * Get the CC documentation location by limit profile ID, cust category and
	 * customer ID
	 * @param aLimitProfileID of long type
	 * @param aCustCategory of String type
	 * @param aCustomerID of long type
	 * @return ICCDocumentLocation - the CC documentation location
	 * @throws DocumentLocationException
	 */
	/*
	 * public ICCDocumentLocation getCCDocumentLocation(long aLimitProfileID,
	 * String aCustCategory, long aCustomerID) throws DocumentLocationException
	 * { try { EBCCDocumentLocationHome home = getEBCCDocumentLocationHome();
	 * Collection remoteList = null; if
	 * (ICMSConstant.CHECKLIST_PLEDGER.equals(aCustCategory)) { remoteList =
	 * home.findByLimitProfileAndPledgorID(new Long(aLimitProfileID), new
	 * Long(aCustomerID)); } else { remoteList =
	 * home.findByLimitProfileAndSubProfile(new Long(aLimitProfileID), new
	 * Long(aCustomerID)); } if ((remoteList == null) || (remoteList.size() ==
	 * 0)) { return null; } if (remoteList.size() > 1) { throw new
	 * DocumentLocationException("More than 1 records found with " +
	 * aLimitProfileID + ", " + aCustCategory + ", " + aCustomerID); } Iterator
	 * iter = remoteList.iterator(); EBCCDocumentLocation remote =
	 * (EBCCDocumentLocation)iter.next(); ICCDocumentLocation ccDocumentLocation
	 * = remote.getValue(); populateCustomerInfo(ccDocumentLocation); return
	 * ccDocumentLocation; } catch(FinderException ex) { throw new
	 * DocumentLocationException("Exception in getCCDocumentLocation: " +
	 * ex.toString());
	 * 
	 * } catch(RemoteException ex) { throw new
	 * DocumentLocationException("Exception in getCCDocumentLocation: " +
	 * ex.toString()); } }
	 */

	/**
	 * Get the list of cc document location based on the criteria
	 * @param anOwnerType of String type
	 * @param aLimitProfileID of long type
	 * @param anOwnerID of long type
	 * @return ICCDocumentLocation[] - the list of cc document location
	 * @throws DocumentLocationException on error
	 */
	public ICCDocumentLocation[] getCCDocumentLocation(String anOwnerType, long aLimitProfileID, long anOwnerID)
			throws DocumentLocationException {
		try {
			EBCCDocumentLocationHome home = getEBCCDocumentLocationHome();
			return home.getCCDocumentLocation(anOwnerType, aLimitProfileID, anOwnerID);
		}
		catch (SearchDAOException ex) {
			throw new DocumentLocationException("Caught SearchDAOException in getCCDocumentLocation", ex);
		}
		catch (RemoteException ex) {
			throw new DocumentLocationException("Exception in getCCDocumentLocation: " + ex.toString());
		}
	}

	/**
	 * Get the CC documentation location based on the documentation location ID
	 * @param aDocLocationID of long type
	 * @return ICCDocumentLocation - the CC documentation location with the
	 *         documentation location ID
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocation getCCDocumentLocation(long aDocLocationID) throws DocumentLocationException {
		try {
			EBCCDocumentLocationHome home = getEBCCDocumentLocationHome();
			EBCCDocumentLocation remote = home.findByPrimaryKey(new Long(aDocLocationID));
			ICCDocumentLocation colDocLocation = remote.getValue();
			populateCustomerInfo(colDocLocation);
			return colDocLocation;
		}
		catch (FinderException ex) {
			throw new DocumentLocationException("Exception in getCCDocumentLocation: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new DocumentLocationException("Exception in getCCDocumentLocation: " + ex.toString());
		}
	}

	/**
	 * Create a CC documentation location
	 * @param anICCDocumentLocation of ICCDocumentLocation type
	 * @return ICCDocumentLocation - the CC documentation location created
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocation createCCDocumentLocation(ICCDocumentLocation anICCDocumentLocation)
			throws DocumentLocationException {
		try {
			if (anICCDocumentLocation == null) {
				throw new DocumentLocationException("The ICCDocumentLocation to be created is null !!!");
			}
			EBCCDocumentLocationHome home = getEBCCDocumentLocationHome();
			EBCCDocumentLocation remote = home.create(anICCDocumentLocation);
			ICCDocumentLocation colDocLocation = remote.getValue();
			populateCustomerInfo(colDocLocation);
			return colDocLocation;
		}
		catch (CreateException ex) {
			throw new DocumentLocationException("Exception in createCCDocumentLocation: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new DocumentLocationException("Exception in createCCDocumentLocation: " + ex.toString());
		}
	}

	/**
	 * Update a CC documentation location
	 * @param anICCDocumentLocation of ICCDocumentLocation
	 * @return ICCDocumentLocation - the CC documentation location updated
	 * @throws DocumentLocationException on errors
	 */
	public ICCDocumentLocation updateCCDocumentLocation(ICCDocumentLocation anICCDocumentLocation)
			throws ConcurrentUpdateException, DocumentLocationException {
		try {
			if (anICCDocumentLocation == null) {
				throw new DocumentLocationException("The ICCDocumentLocation to be updated is null !!!");
			}
			if (anICCDocumentLocation.getDocLocationID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				throw new DocumentLocationException(
						"The DocumentLocationID of the CC documentation location to be updated is invalid !!!");
			}
			EBCCDocumentLocationHome home = getEBCCDocumentLocationHome();
			EBCCDocumentLocation remote = home.findByPrimaryKey(new Long(anICCDocumentLocation.getDocLocationID()));
			remote.setValue(anICCDocumentLocation);
			ICCDocumentLocation colDocumentLocation = remote.getValue();
			populateCustomerInfo(colDocumentLocation);
			return colDocumentLocation;
		}
		catch (FinderException ex) {
			throw new DocumentLocationException("Exception in updateCCDocumentLocation: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new DocumentLocationException("Exception in updateCCDocumentLocation: " + ex.toString());
		}
	}

	private void populateCustomerInfo(ICCDocumentLocation anICCDocumentLocation) throws DocumentLocationException {
		try {
			String locCategory = anICCDocumentLocation.getDocLocationCategory();
			if (ICMSConstant.CHECKLIST_PLEDGER.equals(locCategory)) {
				IPledgor pledgor = getPledgor(anICCDocumentLocation.getCustomerID());
				anICCDocumentLocation.setLegalRef(String.valueOf(pledgor.getLegalID()));
				anICCDocumentLocation.setLegalName(pledgor.getPledgorName());
				anICCDocumentLocation.setCustomerType(pledgor.getLegalType());
				return;
			}
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer customer = custProxy.getCustomer(anICCDocumentLocation.getCustomerID());
			anICCDocumentLocation.setLegalRef(customer.getCMSLegalEntity().getLEReference());
			anICCDocumentLocation.setLegalName(customer.getCMSLegalEntity().getLegalName());
			anICCDocumentLocation.setCustomerType(customer.getCMSLegalEntity().getLegalConstitution());
		}
		catch (CustomerException ex) {
			throw new DocumentLocationException("Exception in populateCustomerInfo", ex);
		}
	}

	protected IPledgor getPledgor(long aPledgorID) throws DocumentLocationException {
		try {
			ICollateralProxy proxy = CollateralProxyFactory.getProxy();
			return proxy.getPledgor(aPledgorID);
		}
		catch (CollateralException ex) {
			throw new DocumentLocationException("Exception in getPledgor: " + ex.toString());
		}
	}

	/**
	 * To get the home handler for the Documentation location Entity Bean
	 * @return EBCCDocumentLocationHome - the home handler for the SC
	 *         Certificate entity bean
	 */
	protected EBCCDocumentLocationHome getEBCCDocumentLocationHome() {
		EBCCDocumentLocationHome ejbHome = (EBCCDocumentLocationHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_CC_DOC_LOCATION_JNDI, EBCCDocumentLocationHome.class.getName());
		return ejbHome;
	}
}
