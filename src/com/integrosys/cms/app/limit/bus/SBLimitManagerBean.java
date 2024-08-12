/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/SBLimitManagerBean.java,v 1.27 2006/09/22 13:12:23 jzhan Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.FinderException;
import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.EBCMSCustomer;
import com.integrosys.cms.app.customer.bus.EBCMSCustomerHome;
import com.integrosys.cms.app.customer.bus.EBCustomerSysXRef;
import com.integrosys.cms.app.customer.bus.EBCustomerSysXRefHome;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;

/**
 * This session bean provides lifecyle management to the EBs in Limit module.
 * 
 * @author $Author: jzhan $
 * @version $Revision: 1.27 $
 * @since $Date: 2006/09/22 13:12:23 $ Tag: $Name: $
 */
public class SBLimitManagerBean implements javax.ejb.SessionBean {

	private static final long serialVersionUID = 8345817186238655251L;

	/**
	 * SessionContext object
	 */
	protected SessionContext _context = null;

	/**
	 * Default Constructor
	 */
	public SBLimitManagerBean() {
	}

	/**
	 * Create Limit Profile. This method does not create the limits details.
	 * 
	 * @param value is of type ILimitProfile
	 * @return ILimitProfile which exclude dependants.
	 * @throws LimitException on errors
	 */
	public ILimitProfile createLimitProfile(ILimitProfile value) throws LimitException {
		try {

			ILimitDAO dao = LimitDAOFactory.getDAO();
			if (dao.checkDuplicateAANumber(value.getBCAReference(), value.getLimitProfileID())) {
				LimitException exp = new LimitException("Cannot create LimitProfile");
				exp.setErrorCode(LimitException.ERR_DUPLICATE_AA_NUM);
				throw exp;
			}

			EBLimitProfileHome home = getEBHomeLimitProfile();
			EBLimitProfile rem = home.create(value);

			long verTime = rem.getVersionTime();
			// create child dependencies with checking on version time
			rem.createDependants(value, verTime);
			ILimitProfile newValue = rem.getValue(false);

			// Set customer is borrower when AA is created
			long custID = newValue.getCustomerID();
			EBCMSCustomerHome custHome = getEBCustomerHome();
			EBCMSCustomer cust = custHome.findByPrimaryKey(new Long(custID));
			cust.setNonBorrowerInd(false);
			return newValue;

		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Create a single limit
	 * 
	 * @param value is of type ILimit
	 * @return ILimit
	 * @throws LimitException on errors
	 */
	public ILimit createLimit(ILimit value) throws LimitException {
		try {
			EBLimitHome home = getEBHomeLimit();
			EBLimit rem = home.create(value);

			long lmtId = rem.getValue().getLimitID();
			// create child dependencies with checking on version time
			rem.createDependants(value, lmtId);

			return rem.getValue();
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Create a list of limits.
	 * 
	 * @param lmts of type ILimit[]
	 * @return ILimit[]
	 * @throws LimitException on any errors encountered
	 */
	public ILimit[] createLimits(ILimit[] lmts) throws LimitException {
		try {
			int count = 0;
			if ((lmts == null) || ((count = lmts.length) == 0)) {
				return lmts;
			}
			EBLimitLocalHome home = getEBLimitLocalHome();
			ILimit[] newLmts = new OBLimit[count];
			for (int i = 0; i < count; i++) {
				newLmts[i] = lmts[i];
				if (newLmts[i] != null) {
					EBLimitLocal theEjb = home.create(newLmts[i]);
					theEjb.createDependants(newLmts[i], theEjb.getVersionTime());
					newLmts[i] = theEjb.getValue();
				}
			}
			return newLmts;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * create co borrower limits
	 * @param limits
	 * @return
	 * @throws LimitException
	 */
	public ICoBorrowerLimit[] createCoBorrowerLimits(ICoBorrowerLimit[] limits) throws LimitException {
		try {
			if ((limits == null) || (limits.length == 0)) {
				return limits;
			}
			EBCoBorrowerLimitHome home = getEBHomeCoBorrowerLimit();
			ICoBorrowerLimit[] newLimits = new ICoBorrowerLimit[limits.length];
			for (int i = 0; i < limits.length; i++) {
				newLimits[i] = limits[i];
				if (newLimits[i] != null) {
					EBCoBorrowerLimit local = home.create(newLimits[i]);
					long verTime = local.getVersionTime();
					local.createDependants(limits[i], verTime);
					newLimits[i] = local.getValue();

				}
			}
			return newLimits;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException(e);
		}
	}

	/**
	 * Create a single co0borrower limit
	 * 
	 * @param value is of type ICoBorrowerLimit
	 * @return ICoBorrowerLimit
	 * @throws LimitException on errors
	 */
	public ICoBorrowerLimit createCoBorrowerLimit(ICoBorrowerLimit value) throws LimitException {
		try {
			EBCoBorrowerLimitHome home = getEBHomeCoBorrowerLimit();
			EBCoBorrowerLimit rem = home.create(value);

			return rem.getValue();
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Create Limits. This method creates the Limits contained in a Limit
	 * Profile.
	 * 
	 * @param value is of type ILimitProfile
	 * @return ILimitProfile
	 * @throws LimitException on errors
	 */
	public ILimitProfile createLimits(ILimitProfile value) throws LimitException {
		try {
			if (null == value) {
				throw new LimitException("ILimitProfile is null!");
			}
			ILimit[] limits = value.getLimits();
			if (null != limits) {
				EBLimitHome home = getEBHomeLimit();
				ArrayList aList = new ArrayList(limits.length);
				long profileID = value.getLimitProfileID();
				if (ICMSConstant.LONG_INVALID_VALUE == profileID) {
					throw new LimitException("Limit Profile ID is uninitialised: " + profileID);
				}

				for (int i = 0; i < limits.length; i++) {
					limits[i].setLimitProfileID(profileID);
					EBLimit rem = home.create(limits[i]);
					aList.add(rem.getValue());
				}

				value.setLimits((ILimit[]) aList.toArray(new ILimit[0]));
			}
			return value;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Update limit profile information.
	 * 
	 * @param value is of type ILimitProfile
	 * @return ILimitProfile which includes dependants
	 * @throws LimitException on errors
	 */
	public ILimitProfile updateLimitProfile(ILimitProfile value) throws LimitException {
		try {
			if (null == value) {
				throw new LimitException("ILimit is null!");
			}
			ILimitDAO dao = LimitDAOFactory.getDAO();
			if (dao.checkDuplicateAANumber(value.getBCAReference(), value.getLimitProfileID())) {
				LimitException exp = new LimitException("Cannot update LimitProfile");
				exp.setErrorCode(LimitException.ERR_DUPLICATE_AA_NUM);
				throw exp;
			}
			EBLimitProfileHome home = getEBHomeLimitProfile();
			EBLimitProfile rem = home.findByPrimaryKey(new Long(value.getLimitProfileID()));
			rem.setValue(value);

			return rem.getValue(true);
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Update limit information
	 * 
	 * @param value is of type ILimit
	 * @return ILimit
	 * @throws LimitException on errors
	 */
	public ILimit updateLimit(ILimit value) throws LimitException {
		try {

			if (null == value) {
				throw new LimitException("ILimit is null!");
			}
			EBLimitHome home = getEBHomeLimit();
			EBLimit rem = home.findByPrimaryKey(new Long(value.getLimitID()));
			rem.setValue(value);

			return rem.getValue();
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Update operational limit.
	 * 
	 * @param limit is of type ILimit
	 * @return ILimit
	 * @throws LimitException on errors
	 */
	public ILimit updateOperationalLimit(ILimit limit) throws LimitException {
		try {
			if (limit == null) {
				throw new LimitException("ILimit is null!");
			}
			EBLimitHome home = getEBHomeLimit();
			EBLimit rem = home.findByPrimaryKey(new Long(limit.getLimitID()));
			return rem.updateOperationalLimit(limit);
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Update CoBorrower limit information
	 * 
	 * @param value is of type ICoBorrowerLimit
	 * @return ICoBorrowerLimit
	 * @throws LimitException on errors
	 */
	public ICoBorrowerLimit updateCoBorrowerLimit(ICoBorrowerLimit value) throws LimitException {
		try {
			if (null == value) {
				throw new LimitException("ICoBorrowerLimit is null!");
			}
			EBCoBorrowerLimitHome home = getEBHomeCoBorrowerLimit();
			EBCoBorrowerLimit rem = home.findByPrimaryKey(new Long(value.getLimitID()));
			rem.setValue(value);

			return rem.getValue();
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Delete limit profile. This method will cascade delete the limits too.
	 * 
	 * @param value is of type ILimitProfile
	 * @throws LimitException on errors
	 */
	public void deleteLimitProfile(ILimitProfile value) throws LimitException {
		try {
			if (null == value) {
				throw new LimitException("ILimit is null!");
			}
			EBLimitProfileHome home = getEBHomeLimitProfile();
			EBLimitProfile rem = home.findByPrimaryKey(new Long(value.getLimitProfileID()));
			rem.remove();
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.SBLimitManager#removeLimitProfile
	 */
	public ILimitProfile removeLimitProfile(ILimitProfile value) throws LimitException {
		try {
			if (null == value) {
				throw new LimitException("ILimit is null!");
			}
			EBLimitProfileHome home = getEBHomeLimitProfile();
			long lmtProfileID = value.getLimitProfileID();
			EBLimitProfile rem = home.findByPrimaryKey(new Long(lmtProfileID));

			// do soft delete
			rem.setStatusDeleted(value);

			// set customer to non borrower when AA is removed and no active AA
			// is linked to it
			ILimitProfile newValue = rem.getValue(true);
			long custID = newValue.getCustomerID();
			LimitDAO dao = new LimitDAO();
			if (!dao.checkCustHasLimitProfile(custID, lmtProfileID)) {
				EBCMSCustomerHome custHome = getEBCustomerHome();
				EBCMSCustomer cust = custHome.findByPrimaryKey(new Long(custID));
				cust.setNonBorrowerInd(true);
			}
			return newValue;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Get a limit profile given a limit profile ID
	 * 
	 * @param limitProfileID is of type long
	 * @return ILimitProfile which include dependants
	 * @throws LimitException on error
	 */
	public ILimitProfile getLimitProfile(long limitProfileID) throws LimitException {
		try {
			EBLimitProfileHome home = getEBHomeLimitProfile();
			EBLimitProfile rem = home.findByPrimaryKey(new Long(limitProfileID));
			ILimitProfile limitprof = new OBLimitProfile();
			limitprof = rem.getValue(true);
			//LimitDAO dao = new LimitDAO();
			//limitprof.setApprovingOfficerGrade(dao.getCoinCode(limitprof.getApproverEmployeeID1()));
			return limitprof;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (FinderException e) {
			throw new LimitException("Unable to find limit profile with limit profile ID: " + limitProfileID, e);
		}
		catch (Exception e) {
			throw new LimitException("Caugnt Exception!", e);
		}
	}

	/**
	 * Get a limit given a limit ID
	 * 
	 * @param limitID is of type long
	 * @return ILimit
	 * @throws LimitException on error
	 */
	public ILimit getLimit(long limitID) throws LimitException {
		try {
			EBLimitHome home = getEBHomeLimit();
			EBLimit rem = home.findByPrimaryKey(new Long(limitID));
			return rem.getValue();
		}
		catch (LimitException e) {
			throw e;
		}
		catch (FinderException e) {
			throw new LimitException("Unable to find limit with limit ID: " + limitID, e);
		}
		catch (Exception e) {
			throw new LimitException("Caugnt Exception!", e);
		}
	}

	/**
	 * reset the given limit objects.
	 * 
	 * @param lmts is of type ILimit[]
	 * @param excludeMethods a list of methods to be excluded during resetting
	 * @throws LimitException on error getting the limits
	 */
	public ILimit[] resetLimits(ILimit[] lmts, String[] excludeMethods) throws LimitException {
		int count = 0;
		if ((lmts == null) || ((count = lmts.length) == 0)) {
			return lmts;
		}
		EBLimitLocalHome home = getEBLimitLocalHome();
		for (int i = 0; i < count; i++) {
			if (lmts[i] == null) {
				continue;
			}
			try {
				EBLimitLocal theEjb = home.findByPrimaryKey(new Long(lmts[i].getLimitID()));
				AccessorUtil.copyValue(theEjb.getValue(), lmts[i], excludeMethods);
			}
			catch (FinderException e) {
				throw new LimitException("Unable to find the limit id: " + lmts[i].getLimitID());
			}
		}
		return lmts;
	}

	/**
	 * reset co borrower limits
	 * @param limits
	 * @throws LimitException
	 */
	public ICoBorrowerLimit[] resetCoBorrowerLimits(ICoBorrowerLimit[] limits) throws LimitException {
		if ((limits == null) || (limits.length == 0)) {
			return limits;
		}
		EBCoBorrowerLimitHome home = getEBHomeCoBorrowerLimit();
		for (int i = 0; i < limits.length; i++) {
			try {
				EBCoBorrowerLimit local = home.findByPrimaryKey(new Long(limits[i].getLimitID()));
				AccessorUtil.copyValue(local.getValue(), limits[i], new String[] { "getProductDesc" });
			}
			catch (FinderException e) {
				throw new LimitException(e);
			}
			catch (RemoteException e) {
				throw new LimitException(e);
			}
		}
		return limits;
	}

	/**
	 * Get a CoBorrower limit given a limit ID
	 * 
	 * @param limitID is of type long
	 * @return ICoBorrowerLimit
	 * @throws LimitException on error
	 */
	public ICoBorrowerLimit getCoBorrowerLimit(long limitID) throws LimitException {
		try {
			EBCoBorrowerLimitHome home = getEBHomeCoBorrowerLimit();
			EBCoBorrowerLimit rem = home.findByPrimaryKey(new Long(limitID));
			return rem.getValue();
		}
		catch (LimitException e) {
			throw e;
		}
		catch (FinderException e) {
			throw new LimitException("Unable to find CoBorrowerLimit with limit ID: " + limitID, e);
		}
		catch (Exception e) {
			throw new LimitException("Caugnt Exception!", e);
		}
	}

	public List getFSVBalForMainborrowLimit(String limitId) {
		return new LimitDAO().getFSVBalForMainborrowLimit(limitId);
	}

	public List getFSVBalForCoborrowLimit(String limitId) {
		return new LimitDAO().getFSVBalForCoborrowLimit(limitId);
	}

	// ************* Protected Methods **************
	/**
	 * Method to get the EBHome for Limit Profile
	 * 
	 * @return EBLimitProfileHome
	 * @throws LimitException on error
	 */
	protected EBLimitProfileHome getEBHomeLimitProfile() throws LimitException {
		EBLimitProfileHome home = (EBLimitProfileHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_LIMIT_PROFILE_JNDI, EBLimitProfileHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBLimitProfileHome is null!");
		}
	}

	/**
	 * Method to get the EBHome for Limit
	 * 
	 * @return EBLimitHome
	 * @throws LimitException on error
	 */
	protected EBLimitHome getEBHomeLimit() throws LimitException {
		EBLimitHome home = (EBLimitHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_LIMIT_JNDI, EBLimitHome.class
				.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBLimitHome is null!");
		}
	}

	/**
	 * Method to get the limit local home interface.
	 * 
	 * @return EBLimitLocalHome
	 * @throws LimitException on error getting the limit local home
	 */
	protected EBLimitLocalHome getEBLimitLocalHome() throws LimitException {
		EBLimitLocalHome ejbHome = (EBLimitLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LIMIT_LOCAL_JNDI, EBLimitLocalHome.class.getName());

		if (ejbHome == null) {
			throw new LimitException("EBLimitLocalHome is NULL");
		}
		return ejbHome;
	}

	/**
	 * Method to get EB Local Home for EBCoBorrowerLimitHome
	 * 
	 * @return EBCoBorrowerLimitHome
	 * @throws LimitException on errors
	 */
	protected EBCoBorrowerLimitHome getEBHomeCoBorrowerLimit() throws LimitException {
		EBCoBorrowerLimitHome home = (EBCoBorrowerLimitHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_COBORROWER_LIMIT_JNDI, EBCoBorrowerLimitHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBCoBorrowerLimitHome is null!");
		}
	}

	/**
	 * Method to get the local home interface for coborrower limit.
	 * 
	 * @return EBCoBorrowerLimitLocalHome
	 * @throws LimitException on error getting the coborrower limit local home
	 */
	protected EBCoBorrowerLimitLocalHome getEBCoBorrowerLimitLocalHome() throws LimitException {
		EBCoBorrowerLimitLocalHome ejbHome = (EBCoBorrowerLimitLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COBORROWER_LIMIT_LOCAL_JNDI, EBCoBorrowerLimitLocalHome.class.getName());

		if (ejbHome == null) {
			throw new LimitException("EBCoBorrowerLimitLocalHome is NULL");
		}
		return ejbHome;
	}

	protected EBCustomerSysXRefHome getEBCustomerSysXRefHome() throws LimitException {
		EBCustomerSysXRefHome ejbHome = (EBCustomerSysXRefHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_CUSTOMER_SYS_REF_JNDI, EBCustomerSysXRefHome.class.getName());

		if (ejbHome == null) {
			throw new LimitException("EBCustomerSysXRefHome is NULL");
		}
		return ejbHome;
	}

	protected EBCMSCustomerHome getEBCustomerHome() throws LimitException {
		EBCMSCustomerHome ejbHome = (EBCMSCustomerHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_CUSTOMER_JNDI,
				EBCMSCustomerHome.class.getName());

		if (ejbHome == null) {
			throw new LimitException("EBCMSCustomerHome is NULL");
		}
		return ejbHome;
	}

	public ILimit createLimitWithAccounts(ICMSCustomer customer, ILimit value) throws LimitException {
		try {
			createAccountsForLimit(customer, value);
			EBLimitHome home = getEBHomeLimit();
			EBLimit rem = home.create(value);

			long lmtId = Long.parseLong(rem.getPrimaryKey().toString());
			// create child dependencies with checking on version time
			rem.createDependants(value, lmtId);

			value.setLimitID(lmtId);
			if ((value.getLimitRef() == null) || "".equals(value.getLimitRef())) {
				value.setLimitRef(String.valueOf(lmtId));
			}
			return value;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	public ILimit createLimitWithAccounts(ILimit value) throws LimitException {
		return createLimitWithAccounts(null, value);
	}

	public ILimit updateLimitWithAccounts(ILimit originLimit, ILimit value) throws LimitException {
		return updateLimitWithAccounts(null, originLimit, value);
	}

	public ILimit updateLimitWithAccounts(ICMSCustomer customer, ILimit originLimit, ILimit value)
			throws LimitException {
		try {
			if (null == value) {
				throw new LimitException("ILimit is null!");
			}
			updateAccountsForLimit(customer, originLimit, value);
			EBLimitHome home = getEBHomeLimit();
			EBLimit rem = home.findByPrimaryKey(new Long(value.getLimitID()));

			value = rem.setValue(value);

			return value;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}

	public void processCheckList(String limitId) {
		try {
			new com.integrosys.cms.app.checklist.bus.CheckListDAO().processCheckList(limitId);
		}
		catch (Exception ex) {
		}
	}

	private void createAccountsForLimit(ICMSCustomer customer, ILimit limit) throws LimitException {
		try {
			long customerID = ICMSConstant.LONG_INVALID_VALUE;
			if (customer != null) {
				customerID = customer.getCustomerID();
			}
			ILimitSysXRef[] link = limit.getLimitSysXRefs();
			if (link != null) {
				for (int i = 0; i < link.length; i++) {
					ILimitSysXRef nextRef = link[i];
					ICustomerSysXRef nextAccount = nextRef.getCustomerSysXRef();
					if (nextAccount != null) {
						EBCustomerSysXRefHome home = getEBCustomerSysXRefHome();
						EBCustomerSysXRef local = home.create(customerID, nextAccount);
						local.createDependants(customerID, nextAccount);
						nextRef.setCustomerSysXRef(local.getValue());
					}
				}
			}
		}
		catch (Exception ex) {
			throw new LimitException("failed to create account for limit, id [" + limit.getLimitID() + "]", ex);
		}
	}

	private void updateAccountsForLimit(ICMSCustomer customer, ILimit orginLimit, ILimit stagingLimit)
			throws LimitException {
		// compare origal and staging obj to determin what are the newly created
		// accounts
		// and persist them
		try {
			long customerID = ICMSConstant.LONG_INVALID_VALUE;
			if (customer != null) {
				customerID = customer.getCustomerID();
			}

			ILimitSysXRef[] origLink = orginLimit.getLimitSysXRefs();
			ILimitSysXRef[] stagingLink = stagingLimit.getLimitSysXRefs();
			if (stagingLink != null) {
				for (int i = 0; i < stagingLink.length; i++) {
					ILimitSysXRef nextStagingRef = stagingLink[i];
					boolean found = false;
					if (origLink != null) {
						DefaultLogger.debug(this, "<<<<<<, origLink length: " + String.valueOf(origLink.length));
						inner: for (int j = 0; i < origLink.length; i++) {
							if (nextStagingRef.getSID() == origLink[j].getSID()) {
								found = true;
								break inner;
							}
						}
					}
					if (!found) {
						ICustomerSysXRef nextAccount = nextStagingRef.getCustomerSysXRef();
						if (nextAccount != null) {
							DefaultLogger.debug(this, "<<<<<<<<< create account: " + nextAccount);
							EBCustomerSysXRefHome home = getEBCustomerSysXRefHome();
							EBCustomerSysXRef local = home.create(customerID, nextAccount);
							//local.createDependants(customerID, nextAccount);
							local.setValue(nextAccount);
							nextStagingRef.setCustomerSysXRef(local.getValue());
							DefaultLogger.debug(this, "<<<<<<<<< create account value: "
									+ nextStagingRef.getCustomerSysXRef());
						}
					}
				}
			}
		}
		catch (Exception ex) {
			throw new LimitException("failed to update account for limit, id [" + orginLimit.getLimitID() + "]", ex);
		}
	}

	// ************* EJB Methods *****************

	/* EJB Methods */
	/**
	 * Called by the container to create a session bean instance. Its parameters
	 * typically contain the information the client uses to customize the bean
	 * instance for its use. It requires a matching pair in the bean class and
	 * its home interface.
	 */
	public void ejbCreate() {
	}

	/**
	 * A container invokes this method before it ends the life of the session
	 * object. This happens as a result of a client's invoking a remove
	 * operation, or when a container decides to terminate the session object
	 * after a timeout. This method is called with no transaction context.
	 */
	public void ejbRemove() {

	}

	/**
	 * The activate method is called when the instance is activated from its
	 * 'passive' state. The instance should acquire any resource that it has
	 * released earlier in the ejbPassivate() method. This method is called with
	 * no transaction context.
	 */
	public void ejbActivate() {

	}

	/**
	 * The passivate method is called before the instance enters the 'passive'
	 * state. The instance should release any resources that it can re-acquire
	 * later in the ejbActivate() method. After the passivate method completes,
	 * the instance must be in a state that allows the container to use the Java
	 * Serialization protocol to externalize and store away the instance's
	 * state. This method is called with no transaction context.
	 */
	public void ejbPassivate() {

	}

	/**
	 * Set the associated session context. The container calls this method after
	 * the instance creation. The enterprise Bean instance should store the
	 * reference to the context object in an instance variable. This method is
	 * called with no transaction context.
	 */
	public void setSessionContext(javax.ejb.SessionContext sc) {
		_context = sc;
	}
	
	public ILimit updateLimitWithUdfAccounts(ILimit originLimit, ILimit value) throws LimitException {
		return updateLimitWithUdfAccounts(null, originLimit, value);
	}
	
	public ILimit updateLimitWithUdfAccounts(ICMSCustomer customer, ILimit originLimit, ILimit value)
			throws LimitException {
		try {
			if (null == value) {
				throw new LimitException("ILimit is null!");
			}
			updateUdfAccountsForLimit(customer, originLimit, value);
			EBLimitHome home = getEBHomeLimit();
			EBLimit rem = home.findByPrimaryKey(new Long(value.getLimitID()));

			value = rem.setValue(value);

			return value;
		}
		catch (LimitException e) {
			_context.setRollbackOnly();
			throw e;
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new LimitException("Caught Exception!", e);
		}
	}
	
	private void updateUdfAccountsForLimit(ICMSCustomer customer, ILimit orginLimit, ILimit stagingLimit)
			throws LimitException {
		// compare origal and staging obj to determin what are the newly created
		// accounts
		// and persist them
		try {
			long customerID = ICMSConstant.LONG_INVALID_VALUE;
			if (customer != null) {
				customerID = customer.getCustomerID();
			}

			ILimitSysXRef[] origLink = orginLimit.getLimitSysXRefs();
			ILimitSysXRef[] stagingLink = stagingLimit.getLimitSysXRefs();
			if (stagingLink != null) {
				for (int i = 0; i < stagingLink.length; i++) {
					ILimitSysXRef nextStagingRef = stagingLink[i];
					boolean found = true;
					/*if (origLink != null) {
						DefaultLogger.debug(this, "<<<<<<, origLink length: " + String.valueOf(origLink.length));
						inner: for (int j = 0; j < origLink.length; j++) {
							if(origLink.length==stagingLink.length) {
								if (nextStagingRef.getSID() == origLink[j].getSID()) {
									found = true;
									break inner;
								}
							}else {
								if (nextStagingRef.getSID() == origLink[j].getSID()) {
									found = true;
									break inner;
								}
								else {
									for (int k = 0; k < origLink.length; k++) {
										if (nextStagingRef.getSID() == origLink[k].getSID()) {
											found = true;
											break inner;
										}
										else {
											found = true;
											break inner;
										}
									}
								}
									
							}
						}
					}*/
					if (found) {
						ICustomerSysXRef nextAccount = nextStagingRef.getCustomerSysXRef();
						if(nextAccount != null) {
							/*if (null!=nextAccount.getXRefUdfData()) {*/
							DefaultLogger.debug(this, "<<<<<<<<< create account: " + nextAccount);
							EBCustomerSysXRefHome home = getEBCustomerSysXRefHome();
							EBCustomerSysXRef local = home.create(customerID, nextAccount);
							local.createDependants(customerID, nextAccount);
							//local.setValue(nextAccount);
							nextStagingRef.setCustomerSysXRef(local.getValue());
							DefaultLogger.debug(this, "<<<<<<<<< create account value: "
									+ nextStagingRef.getCustomerSysXRef());
							/*}*/
					}
				}
				}
			}
		}
		catch (Exception ex) {
			throw new LimitException("failed to update account for limit, id [" + orginLimit.getLimitID() + "]", ex);
		}
	}
}