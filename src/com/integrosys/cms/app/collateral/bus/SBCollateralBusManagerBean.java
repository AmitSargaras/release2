/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/SBCollateralBusManagerBean.java,v 1.62 2006/07/27 04:36:39 jzhan Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.type.cash.EBCashCollateralBean;
import com.integrosys.cms.app.collateral.bus.type.cash.EBCashDepositLocal;
import com.integrosys.cms.app.collateral.bus.type.cash.EBCashDepositLocalHome;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.cashfd.ICashFd;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.cashfd.OBCashFd;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.EBMFChecklist;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.EBMFChecklistHome;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.EBCMSCustomer;
import com.integrosys.cms.app.customer.bus.EBCMSCustomerHome;
import com.integrosys.cms.app.sharesecurity.bus.EBShareSecurity;
import com.integrosys.cms.app.sharesecurity.bus.EBShareSecurityHome;
import com.integrosys.cms.app.sharesecurity.bus.IShareSecurity;

/**
 * This session bean provides the implementation of the
 * AbstractCollateralManager, wrapped in an EJB mechanism.
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.62 $
 * @since $Date: 2006/07/27 04:36:39 $ Tag: $Name: $
 */
public class SBCollateralBusManagerBean extends AbstractCollateralBusManager implements SessionBean {

	private static final long serialVersionUID = -7263003406816093434L;

	/** SessionContext object */
	private SessionContext ctx;

	/**
	 * Default Constructor
	 */
	public SBCollateralBusManagerBean() {
	}

	/**
	 * Get the collateral given its collateral id.
	 * 
	 * @param collateralID collateral id in CMS
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 */
	public ICollateral getCollateral(long collateralID) throws CollateralException {
		try {
			EBCollateralHome ejbHome = getEBCollateralHome();
			EBCollateral theEjb = ejbHome.findByPrimaryKey(new Long(collateralID));
			ICollateral collateral = theEjb.getValue();
			setCollateralParameter(collateral);
			collateral = setDerivedFields(collateral);

			DefaultLogger.debug(this, "Collateral SubTypeCode " + collateral.getCollateralSubType().getSubTypeCode());

			// this part is to prevent getting collateral detail with subtype
			// NA.
			if (((collateral.getCollateralSubType().getSubTypeCode() == null) || collateral.getCollateralSubType()
					.getSubTypeCode().equals(ICMSConstant.COLTYPE_NA))
					|| collateral.getCollateralType().getTypeCode().equals(ICMSConstant.COLTYPE_NOCOLLATERAL)
					|| collateral.getCollateralSubType().getSubTypeCode().equals(ICMSConstant.COLTYPE_NOCOLLATERAL)) {
				return collateral;
			}

			EBCollateralDetailHome detHome = getEBCollateralDetailHome(collateral);
			EBCollateralDetail detEjb = detHome.findByPrimaryKey(new Long(collateral.getCollateralID()));
			return detEjb.getValue(collateral);
		}
		catch (FinderException e) {
			throw new CollateralException("failed to retrieve collateral, collateral id provided [" + collateralID
					+ "]", e);
		}
		catch (RemoteException e) {
			throw new CollateralException("failed to retrieve collateral, collateral id provided [" + collateralID
					+ "], throwing root cause.", e.getCause());
		}
	}

	/**
	 * Get common collateral information given its collateral id.
	 * 
	 * @param collateralID collateral id
	 * @return collateral object
	 * @throws CollateralException on any errors encountered
	 */
	public ICollateral getCommonCollateral(long collateralID) throws CollateralException {
		try {
			EBCollateralHome ejbHome = getEBCollateralHome();
			EBCollateral theEjb = ejbHome.findByPrimaryKey(new Long(collateralID));
			return theEjb.getValue(false);
		}
		catch (FinderException e) {
			throw new CollateralException("failed to retrieve common collateral, collateral id provided ["
					+ collateralID + "]", e);
		}
		catch (RemoteException e) {
			throw new CollateralException("failed to retrieve common collateral, collateral id provided ["
					+ collateralID + "], throwing root cause.", e.getCause());
		}
	}

	/**
	 * Search collateral based on the criteria specified.
	 * 
	 * @param criteria of type CollateralSearchCriteria
	 * @return search result
	 * @throws CollateralException on errors encountered
	 */
	public SearchResult searchCollateral(CollateralSearchCriteria criteria) throws CollateralException {
		try {
			EBCollateralHome ejbHome = getEBCollateralHome();
			return ejbHome.searchCollateral(criteria);
		}
		catch (RemoteException e) {
			throw new CollateralException("failed to search collateral using search criteria, throwing root cause ", e
					.getCause());
		}
	}

	/**
	 * Create a new collateral.
	 * 
	 * @param collateral is of type ICollateral
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 */
	public ICollateral createCollateral(ICollateral collateral) throws CollateralException {
		collateral = super.createCollateral(collateral);

		try {
			EBCollateralHome ejbHome = getEBCollateralHome();
			EBCollateral theEjb = ejbHome.create(collateral);

			theEjb.createDependants(collateral, theEjb.getVersionTime(), true);

			ICollateral latestCol = theEjb.getValue(collateral);

			if (!(collateral instanceof com.integrosys.cms.app.collateral.bus.type.nocollateral.INoCollateral)) {
				EBCollateralDetailHome detHome = getEBCollateralDetailHome(latestCol);
				EBCollateralDetail detEjb = detHome.create(latestCol);
				
				latestCol = detEjb.getValue(latestCol);
			}
			return setDerivedFields(latestCol);
		}
		catch (CreateException e) {
			rollback();
			throw new CollateralException("failed to create new collateral, host id [" + collateral.getSCISecurityID()
					+ "]", e);
		}
		catch (VersionMismatchException e) {
			rollback();
			throw new CollateralException("failed to create new collateral due to concurrent update, host id ["
					+ collateral.getSCISecurityID() + "]", new ConcurrentUpdateException(e.getMessage()));
		}
		catch (RemoteException e) {
			rollback();
			throw new CollateralException("failed to create new collateral, host id [" + collateral.getSCISecurityID()
					+ "] throwing root cause.", e.getCause());
		}
	}

	/**
	 * Update a collateral.
	 * 
	 * @param collateral is of type ICollateral
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 */
	public ICollateral updateCollateral(ICollateral collateral) throws CollateralException {
		collateral = super.updateCollateral(collateral);

		try {
			
			DefaultLogger.debug(this, "===============================105-4-6-1=========update============= ");

			EBCollateralHome ejbHome = getEBCollateralHome();
			EBCollateral theEjb = ejbHome.findByPrimaryKey(new Long(collateral.getCollateralID()));
			DefaultLogger.debug(this, "===============================105-4-6-2=========update============= ");

			theEjb.setValue(collateral);

			ICollateral latestCol = theEjb.getValue();
			DefaultLogger.debug(this, "===============================105-4-6-3=========update============= ");
			if (!(collateral instanceof com.integrosys.cms.app.collateral.bus.type.nocollateral.INoCollateral)) {
				EBCollateralDetailHome detHome = getEBCollateralDetailHome(collateral);
				EBCollateralDetail detEjb = detHome.findByPrimaryKey(new Long(collateral.getCollateralID()));
				detEjb.setValue(collateral);
				latestCol = detEjb.getValue(latestCol);
			}
			DefaultLogger.debug(this, "===============================105-4-6-4=========update============= ");
			if(collateral instanceof ICashFd){
				return collateral;
			}
			return setDerivedFields(latestCol);
		}
		catch (FinderException e) {
			rollback();
			throw new CollateralException("failed to update collateral, collateral id [" + collateral.getCollateralID()
					+ "]", e);
		}
		catch (VersionMismatchException e) {
			rollback();
			throw new CollateralException("failed to update collateral due to concurrent update, collateral id ["
					+ collateral.getCollateralID() + "]", new ConcurrentUpdateException(e.getMessage()));
		}
		catch (RemoteException e) {
			rollback();
			throw new CollateralException("failed to update collateral, collateral id [" + collateral.getCollateralID()
					+ "], throwing root cause", e.getCause());
		}
	}

	/**
	 * Update commodity pre-condition.
	 * 
	 * @param collateral is of type ICollateral
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 */
	public ICollateral updatePreCondition(ICollateral collateral) throws CollateralException {
		try {
			EBCollateralHome ejbHome = getEBCollateralHome();
			EBCollateral theEjb = ejbHome.findByPrimaryKey(new Long(collateral.getCollateralID()));
			long versionTime = theEjb.updateVersionTime(collateral);
			collateral.setVersionTime(versionTime);

			EBCollateralDetailHome detHome = getEBCollateralDetailHome(collateral);
			EBCollateralDetail detEjb = detHome.findByPrimaryKey(new Long(collateral.getCollateralID()));
			detEjb.setValue(collateral);
			return collateral;
		}
		catch (FinderException e) {
			rollback();
			throw new CollateralException("failed to retrieve collateral, collateral id ["
					+ collateral.getCollateralID() + "]", e);
		}
		catch (VersionMismatchException e) {
			rollback();
			throw new CollateralException("failed to create new collateral due to concurrent update, host id ["
					+ collateral.getSCISecurityID() + "]", new ConcurrentUpdateException(e.getMessage()));
		}
		catch (RemoteException e) {
			rollback();
			throw new CollateralException("failed to retrieve/update collateral, collateral id ["
					+ collateral.getCollateralID() + "], throwing root cause", e.getCause());
		}
	}

	/**
	 * Update common collateral information.
	 * 
	 * @param collateral is of type ICollateral
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 */
	public ICollateral updateCommonCollateral(ICollateral collateral) throws CollateralException {
		collateral = super.updateCollateral(collateral);

		try {
			EBCollateralHome ejbHome = getEBCollateralHome();
			EBCollateral theEjb = ejbHome.findByPrimaryKey(new Long(collateral.getCollateralID()));
			theEjb.setValue(collateral);
			ICollateral latestCol = theEjb.getValue(collateral);
			return latestCol;
		}
		catch (FinderException e) {
			rollback();
			throw new CollateralException("failed to retrieve collateral, collateral id ["
					+ collateral.getCollateralID() + "]", e);
		}
		catch (VersionMismatchException e) {
			rollback();
			throw new CollateralException("failed to update collateral due to concurrent update, collateral id ["
					+ collateral.getCollateralID() + "]", new ConcurrentUpdateException(e.getMessage()));
		}
		catch (RemoteException e) {
			rollback();
			throw new CollateralException("failed to retrieve/update collateral, collateral id ["
					+ collateral.getCollateralID() + "] throwing root cause", e.getCause());
		}
	}

	/**
	 * Get the valuation given its valuation id.
	 * 
	 * @param valuationID valuation id
	 * @return valuation
	 * @throws CollateralException on errors encountered
	 */
	public IValuation getValuation(long valuationID) throws CollateralException {
		try {
			EBValuationHome ejbHome = getEBValuationHome();
			EBValuation theEjb = ejbHome.findByPrimaryKey(new Long(valuationID));
			return theEjb.getValue();
		}
		catch (FinderException e) {
			throw new CollateralException("failed to retrieve valuation, valuation id [" + valuationID + "]", e);
		}
		catch (RemoteException e) {
			throw new CollateralException("failed to retrieve valuation, valuation id [" + valuationID
					+ "] throwing root cause", e.getCause());
		}
	}

	/**
	 * Create a new valuation
	 * 
	 * @param valuation is of type IValuation
	 * @return valuation
	 * @throws CollateralException on errors encountered
	 */
	public IValuation createValuation(IValuation valuation) throws CollateralException {
		try {
			EBCollateralHome ejbHome = getEBCollateralHome();
			EBCollateral theEjb = ejbHome.findByPrimaryKey(new Long(valuation.getCollateralID()));
			return theEjb.createValuation(valuation);
		}
		catch (FinderException e) {
			rollback();
			throw new CollateralException("failed to retrieve collateral, collateral id ["
					+ valuation.getCollateralID() + "]", e);
		}
		catch (RemoteException e) {
			rollback();
			throw new CollateralException("failed to create valuation, collateral id [" + valuation.getCollateralID()
					+ "] throwing root cause", e.getCause());
		}
	}

	/**
	 * Get a list of collateral subtypes belong to the type given.
	 * 
	 * @param colType of type ICollateralType
	 * @return a list of collateral subtypes
	 * @throws CollateralException on error finding the collateral subtypes
	 */
	public ICollateralSubType[] getCollateralSubTypeByType(ICollateralType colType) throws CollateralException {
		try {

			EBCollateralSubTypeHome ejbHome = getEBCollateralSubTypeHome();
			return ejbHome.searchByTypeCode(colType.getTypeCode());
		}
		catch (RemoteException e) {
			throw new CollateralException("failed to retrieve all collateral subtype by type [" + colType
					+ "] throwing root cause", e.getCause());
		}
	}

	/**
	 * Get a list of collateral subtypes by its group id.
	 * 
	 * @param groupID group id
	 * @return a list of collateral subtypes
	 * @throws CollateralException on error getting the collateral subtypes
	 */
	public ICollateralSubType[] getCollateralSubTypeByGroupID(long groupID) throws CollateralException {
		try {
			EBCollateralSubTypeHome ejbHome = getEBCollateralSubTypeHome();
			Iterator i = ejbHome.findByGroupID(groupID).iterator();

			List arrList = new ArrayList();

			while (i.hasNext()) {
				EBCollateralSubType theEjb = (EBCollateralSubType) i.next();
				arrList.add(theEjb.getValue());
			}

			return (ICollateralSubType[]) arrList.toArray(new OBCollateralSubType[0]);
		}
		catch (FinderException e) {
			throw new CollateralException("failed to retrieve all collateral subtype by group id [" + groupID + "]", e);
		}
		catch (RemoteException e) {
			throw new CollateralException("failed to retrieve all collateral subtype by group id [" + groupID
					+ "] throwing root cause", e.getCause());
		}
	}

	/**
	 * Get security parameter in CMS using country ISO code and security type
	 * code.
	 * 
	 * @param countryCode country code
	 * @param typeCode security type code
	 * @return a list of security parameters
	 * @throws CollateralException on error getting the security parameters
	 */
	public ICollateralParameter[] getCollateralParameter(String countryCode, String typeCode)
			throws CollateralException {
		try {
			EBCollateralParameterHome ejbHome = getEBCollateralParameterHome();
			return ejbHome.searchByCountryAndColType(countryCode, typeCode);
		}
		catch (RemoteException e) {
			throw new CollateralException("failed to retrieve collateral parameter by country [" + countryCode
					+ "] and type [" + typeCode + "] throwing root cause", e.getCause());
		}
	}

	/**
	 * Get security parameter in CMS by its group id.
	 * 
	 * @param groupID group id
	 * @return a list of security parameters
	 * @throws CollateralException on error getting the security parameters
	 */
	public ICollateralParameter[] getCollateralParameter(long groupID) throws CollateralException {
		try {
			EBCollateralParameterHome ejbHome = getEBCollateralParameterHome();
			return ejbHome.searchByGroupID(groupID);
		}
		catch (RemoteException e) {
			throw new CollateralException("failed to retrieve collateral parameter by group id [" + groupID
					+ "] throwing root cause", e.getCause());
		}
	}

	/**
	 * Get security parameter value given the country code and security subtype
	 * code.
	 * 
	 * @param countryCode country ISO code
	 * @param subTypeCode security subtype code
	 * @return security parameter
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralParameter getColParamByCountryAndSubTypeCode(String countryCode, String subTypeCode)
			throws CollateralException {
		try {
			EBCollateralParameterHome ejbHome = (EBCollateralParameterHome) BeanController.getEJBHome(
					ICMSJNDIConstant.EB_COLLATERAL_PARAMETER_JNDI, EBCollateralParameterHome.class.getName());

			EBCollateralParameter theEjb = ejbHome.findByCountryAndSubTypeCode(countryCode, subTypeCode);
			return theEjb.getValue();
		}
		catch (FinderException e) {
			return null;
		}
		catch (RemoteException e) {
			throw new CollateralException("failed to retrieve collateral parameter by country [" + countryCode
					+ "] and subtype [" + subTypeCode + "] throwing root cause", e.getCause());
		}
	}

	/**
	 * Updates the input list of collateral subtypes.
	 * 
	 * @param subTypes a list of collateral subtypes
	 * @return updated list of collateral subtypes
	 * @throws CollateralException on error updating the subtypes
	 */
	public ICollateralSubType[] updateCollateralSubTypes(ICollateralSubType[] subTypes) throws CollateralException {
		EBCollateralSubTypeHome ejbHome = getEBCollateralSubTypeHome();

		try {
			List arrList = new ArrayList();

			for (int i = 0; i < subTypes.length; i++) {
				EBCollateralSubType theEjb = ejbHome.findByPrimaryKey(subTypes[i].getSubTypeCode());
				theEjb.setSubTypeValue(subTypes[i]);
				arrList.add(theEjb.getValue());
			}
			return (ICollateralSubType[]) arrList.toArray(new ICollateralSubType[0]);
		}
		catch (FinderException e) {
			rollback();
			throw new CollateralException("failed to retrieve collateral subtypes", e);
		}
		catch (VersionMismatchException e) {
			rollback();
			throw new CollateralException("failed to update collateral sub types due to concurrent update",
					new ConcurrentUpdateException(e.getMessage()));
		}
		catch (RemoteException e) {
			rollback();
			throw new CollateralException("failed to retrieve/update collateral subtypes, throwing root cause", e
					.getCause());
		}
	}

	/**
	 * Creates a list of collateral subtypes.
	 * 
	 * @param subTypes a list of collateral subtypes
	 * @return a newly created collateral subtypes
	 * @throws CollateralException on erros creating the security subtypes
	 */
	public ICollateralSubType[] createCollateralSubTypes(ICollateralSubType[] subTypes) throws CollateralException {
		if ((subTypes == null) || (subTypes.length == 0)) {
			throw new CollateralException("ICollateralSubType[] is null");
		}

		EBCollateralSubTypeHome ejbHome = getEBCollateralSubTypeHome();

		try {
			ArrayList arrList = new ArrayList();
			long groupID = ICMSConstant.LONG_MIN_VALUE;

			for (int i = 0; i < subTypes.length; i++) {
				ICollateralSubType subtype = new OBCollateralSubType(subTypes[i]);
				subtype.setGroupID(groupID);
				EBCollateralSubType theEjb = ejbHome.create(subtype);
				subtype = theEjb.getValue();
				groupID = subtype.getGroupID();
				arrList.add(subtype);
			}
			return (ICollateralSubType[]) arrList.toArray(new OBCollateralSubType[0]);
		}
		catch (CreateException e) {
			rollback();
			throw new CollateralException("failed to create collateral subtypes", e);
		}
		catch (RemoteException e) {
			rollback();
			throw new CollateralException("failed to create collateral subtypes, throwing root cause", e.getCause());
		}
	}

	/**
	 * Creates a list of collateral parameters.
	 * 
	 * @param colParams a list of security parameters
	 * @return a list of security parameters
	 * @throws CollateralException on errors creating the security parameters
	 */
	public ICollateralParameter[] createCollateralParameters(ICollateralParameter[] colParams)
			throws CollateralException {
		if ((colParams == null) || (colParams.length == 0)) {
			throw new CollateralException("ICollateralParameter[] is null");
		}

		EBCollateralParameterHome ejbHome = getEBCollateralParameterHome();

		try {
			ArrayList arrList = new ArrayList();
			long groupID = ICMSConstant.LONG_MIN_VALUE;

			for (int i = 0; i < colParams.length; i++) {
				ICollateralParameter colParam = new OBCollateralParameter(colParams[i]);
				colParam.setGroupId(groupID);
				EBCollateralParameter theEjb = ejbHome.create(colParam);
				colParam = theEjb.getValue();
				groupID = colParam.getGroupId();
				arrList.add(colParam);
			}
			return (ICollateralParameter[]) arrList.toArray(new OBCollateralParameter[0]);
		}
		catch (CreateException e) {
			rollback();
			throw new CollateralException("failed to create collateral parameters", e);
		}
		catch (RemoteException e) {
			rollback();
			throw new CollateralException("failed to create collateral parameters, throwing root cause", e.getCause());
		}
	}

	/**
	 * Updates the input list of collateral parameters.
	 * 
	 * @param colParams a list of security parameters
	 * @return a list of security parameters
	 * @throws CollateralException on error updating the security parameters
	 */
	public ICollateralParameter[] updateCollateralParameters(ICollateralParameter[] colParams)
			throws CollateralException {
		EBCollateralParameterHome ejbHome = getEBCollateralParameterHome();

		try {
			ArrayList arrList = new ArrayList();

			for (int i = 0; i < colParams.length; i++) {
				EBCollateralParameter theEjb = ejbHome.findByPrimaryKey(new Long(colParams[i].getId()));
				theEjb.setValue(colParams[i]);
				arrList.add(theEjb.getValue());
			}
			return (ICollateralParameter[]) arrList.toArray(new OBCollateralParameter[0]);
		}
		catch (FinderException e) {
			rollback();
			throw new CollateralException("failed to retrieve collateral parameters", e);
		}
		catch (VersionMismatchException e) {
			rollback();
			throw new CollateralException("failed to update collateral parameters due to concurrent update",
					new ConcurrentUpdateException(e.getMessage()));
		}
		catch (RemoteException e) {
			rollback();
			throw new CollateralException("failed to retrieve/update collateral parameters, throwing root cause", e
					.getCause());
		}
	}

	/**
	 * Get a list of collateral subtypes in CMS.
	 * 
	 * @return ICollateralSubType[]
	 * @throws CollateralException on error getting the collateral subtypes
	 */
	public ICollateralSubType[] getAllCollateralSubTypes() throws CollateralException {
		try {

			EBCollateralSubTypeHome ejbHome = getEBCollateralSubTypeHome();

			Collection collection = ejbHome.findAll();
			Iterator i = collection.iterator();
			ArrayList arrList = new ArrayList();

			while (i.hasNext()) {
				EBCollateralSubType theEjb = (EBCollateralSubType) i.next();
				arrList.add(theEjb.getValue());
			}
			return (OBCollateralSubType[]) arrList.toArray(new OBCollateralSubType[0]);
		}
		catch (FinderException e) {
			throw new CollateralException("failed to retrieve all collateral subtypes", e);
		}
		catch (RemoteException e) {
			throw new CollateralException("failed to retrieve all collateral subtypes, throwing root cause", e
					.getCause());
		}
	}

	/**
	 * Get the pledgor info based on the pledgor ID
	 * 
	 * @param aPledgorID of long type
	 * @return plegor information
	 * @throws CollateralException on errors getting the pledgor info
	 */
	public IPledgor getPledgor(long aPledgorID) throws CollateralException {
		try {
			EBPledgorHome ejbHome = getEBPledgorHome();
			EBPledgor ejb = ejbHome.findByPrimaryKey(new Long(aPledgorID));
			IPledgor pledgor = ejb.getValue();

			if ((pledgor.getCreditGrades() == null) || (pledgor.getCreditGrades().length == 0)) {
				pledgor.setCreditGrades(getPledgorCreditGrades(aPledgorID));
			}

			return pledgor;
		}
		catch (FinderException e) {
			throw new CollateralException("failed to retrieve pledgor, pledogr id [" + aPledgorID + "]", e);
		}
		catch (RemoteException e) {
			throw new CollateralException("failed to retrieve pledgor, pledogr id [" + aPledgorID
					+ "], throwing root cause", e.getCause());
		}
	}

	/**
	 * Get the pledgor info based on the pledgor ID from SCI. Note: this method
	 * will not return credit grades for the pledgor.
	 * 
	 * @param sciPledgorID pledgor id from SCI of long type
	 * @return pledgor information
	 * @throws CollateralException on errors getting the pledgor info
	 */
	public IPledgor getPledgorBySCIPledgorID(long sciPledgorID) throws CollateralException {
		try {
			EBPledgorHome ejbHome = getEBPledgorHome();
			EBPledgor ejb = ejbHome.findBySCIPledgorID(sciPledgorID);
			IPledgor pledgor = ejb.getValue();
			return pledgor;
		}
		catch (FinderException e) {
			throw new CollateralException("failed to retrieve pledgor, host pledogr id [" + sciPledgorID + "]", e);
		}
		catch (RemoteException e) {
			throw new CollateralException("failed to retrieve pledgor, host pledogr id [" + sciPledgorID
					+ "], throwing root cause", e.getCause());
		}
	}

	/**
	 * Helper method to get a list of pledgor credit grades given its pledgor
	 * id.
	 * 
	 * @param pledgorID cms pledgor id
	 * @return a list of pledgor credit grades
	 * @throws CollateralException on error getting the pledgor credit grades
	 */
	private IPledgorCreditGrade[] getPledgorCreditGrades(long pledgorID) throws CollateralException {
		try {
			EBPledgorCreditGradeHome ejbHome = getEBPledgorCreditGradeHome();
			Iterator i = ejbHome.findByCMSPledgorID(pledgorID).iterator();
			ArrayList arrList = new ArrayList();
			while (i.hasNext()) {
				EBPledgorCreditGrade theEjb = (EBPledgorCreditGrade) i.next();
				arrList.add(theEjb.getValue());
			}
			return (IPledgorCreditGrade[]) arrList.toArray(new OBPledgorCreditGrade[0]);
		}
		catch (FinderException e) {
			DefaultLogger.warn(this, "failed to retrieve pledgor credit grade, pledgor id [" + pledgorID
					+ "], return null.", e);
			return null;
		}
		catch (RemoteException e) {
			throw new CollateralException("failed to retrieve pledgor credit grade, pledgor id [" + pledgorID
					+ "], throwing root cause", e.getCause());
		}
	}

	/**
	 * Get a list of collateral asset life in CMS.
	 * 
	 * @return a list of collateral assetlife
	 * @throws CollateralException on error finding the collateral assetlife
	 */
	public ICollateralAssetLife[] getCollateralAssetLife() throws CollateralException {
		try {

			EBCollateralAssetLifeHome ejbHome = getEBCollateralAssetLifeHome();

			Collection collection = ejbHome.findAll();

			Iterator i = collection.iterator();
			ArrayList arrList = new ArrayList();

			while (i.hasNext()) {
				EBCollateralAssetLife theEjb = (EBCollateralAssetLife) i.next();

				arrList.add(updateCollateralSubTypeName(theEjb.getValue()));
			}

			return (OBCollateralAssetLife[]) arrList.toArray(new OBCollateralAssetLife[0]);
		}
		catch (FinderException e) {
			throw new CollateralException("failed to retrieve all collateral asset life", e);
		}
		catch (RemoteException e) {
			throw new CollateralException("failed to retrieve all collateral asset life, throwing root cause", e
					.getCause());
		}

	}

	/**
	 * Get a list of collateral asset life by its group id.
	 * 
	 * @param groupID group id
	 * @return a list of collateral assetlife
	 * @throws CollateralException on error getting the collateral assetlife
	 */
	public ICollateralAssetLife[] getCollateralAssetLifeByGroupID(long groupID) throws CollateralException {
		try {
			EBCollateralAssetLifeHome ejbHome = getEBCollateralAssetLifeHome();
			Iterator i = ejbHome.findByGroupID(groupID).iterator();

			ArrayList arrList = new ArrayList();

			while (i.hasNext()) {
				EBCollateralAssetLife theEjb = (EBCollateralAssetLife) i.next();

				arrList.add(updateCollateralSubTypeName(theEjb.getValue()));

			}

			return (ICollateralAssetLife[]) arrList.toArray(new OBCollateralAssetLife[0]);
		}
		catch (FinderException e) {
			throw new CollateralException("failed to retrieve collateral asset life by groupd id [" + groupID + "]", e);
		}
		catch (RemoteException e) {
			throw new CollateralException("failed to retrieve collateral asset life by groupd id [" + groupID
					+ "], throwing root cause", e.getCause());
		}
	}

	/**
	 * Updates the input list of collateral asset life.
	 * 
	 * @param assetLife a list of collateral assetlife
	 * @return updated list of collateral assetlife
	 * @throws CollateralException on error updating the assetlife
	 */
	public ICollateralAssetLife[] updateCollateralAssetLifes(ICollateralAssetLife[] assetLifes)
			throws CollateralException {
		EBCollateralAssetLifeHome ejbHome = getEBCollateralAssetLifeHome();

		try {
			List arrList = new ArrayList();

			for (int i = 0; i < assetLifes.length; i++) {
				EBCollateralAssetLife theEjb = ejbHome.findByPrimaryKey(assetLifes[i].getSubTypeCode());
				theEjb.setLifeSpanValue(assetLifes[i]);

				arrList.add(updateCollateralSubTypeName(theEjb.getValue()));

			}
			return (ICollateralAssetLife[]) arrList.toArray(new OBCollateralAssetLife[0]);
		}
		catch (FinderException e) {
			rollback();
			throw new CollateralException("failed to retrieve collateral asset life", e);
		}
		catch (VersionMismatchException e) {
			rollback();
			throw new CollateralException("failed to update collateral asset life due to concurrent update",
					new ConcurrentUpdateException(e.getMessage()));
		}
		catch (RemoteException e) {
			rollback();
			throw new CollateralException("failed to retrieve/update collateral asset life, throwing root cause", e
					.getCause());
		}
	}

	/**
	 * Creates a list of collateral asset life.
	 * 
	 * @param assetLifes a list of collateral assetlife
	 * @return a newly created collateral assetlife
	 * @throws CollateralException on erros creating the security assetlife
	 */
	public ICollateralAssetLife[] createCollateralAssetLifes(ICollateralAssetLife[] assetLifes)
			throws CollateralException {
		if ((assetLifes == null) || (assetLifes.length == 0)) {
			throw new CollateralException("ICollateralAssetLife[] is null");
		}

		EBCollateralAssetLifeHome ejbHome = getEBCollateralAssetLifeHome();

		try {
			List arrList = new ArrayList();
			long groupID = ICMSConstant.LONG_MIN_VALUE;

			for (int i = 0; i < assetLifes.length; i++) {
				OBCollateralAssetLife assetLife = new OBCollateralAssetLife(assetLifes[i]);
				assetLife.setGroupID(groupID);
				EBCollateralAssetLife theEjb = ejbHome.create(assetLife);
				assetLife = (OBCollateralAssetLife) theEjb.getValue();
				groupID = assetLife.getGroupID();

				arrList.add(updateCollateralSubTypeName(assetLife));
			}
			return (ICollateralAssetLife[]) arrList.toArray(new OBCollateralAssetLife[0]);
		}
		catch (CreateException e) {
			rollback();
			throw new CollateralException("failed to create collateral asset life", e);
		}
		catch (RemoteException e) {
			rollback();
			throw new CollateralException("failed to create collateral asset life, throwing root cause", e.getCause());
		}
	}

	/**
	 * Updates collateral sub type name and description to collateral assetlife.
	 * 
	 * @param obAssetLife the collateral assetlife
	 * @return the updated collateral assetlife
	 * @throws CollateralException on error updating the assetlife
	 */
	private ICollateralAssetLife updateCollateralSubTypeName(ICollateralAssetLife assetLife) throws CollateralException {
		ICollateralDAO dao = getCollateralDAO();
		OBCollateralSubType obSubType = (OBCollateralSubType) dao.getCollateralSubTypesBySubTypeCode(assetLife
				.getSubTypeCode());
		assetLife.setSubTypeDesc(obSubType.getSubTypeDesc());
		assetLife.setSubTypeName(obSubType.getSubTypeName());
		return assetLife;
	}

	/**
	 * Get DAO implementation for collateral dao.
	 * 
	 * @return ICollateralDAO
	 */
	protected ICollateralDAO getCollateralDAO() {
		return CollateralDAOFactory.getDAO();
	}

	/**
	 * Method to rollback a transaction
	 * 
	 * @throws CollateralException on errors encountered
	 */
	protected void rollback() throws CollateralException {
		ctx.setRollbackOnly();
	}

	/**
	 * helper method to get home interface of EBCollateralBean.
	 * 
	 * @return collateral home interface
	 * @throws CollateralException on errors encountered
	 */
	protected EBCollateralHome getEBCollateralHome() throws CollateralException {
		EBCollateralHome ejbHome = (EBCollateralHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_COLLATERAL_JNDI,
				EBCollateralHome.class.getName());

		if (ejbHome == null) {
			throw new CollateralException("EBCollateralHome is null!");
		}

		return ejbHome;
	}

	/**
	 * helper method to get home interface of EBValuationBean.
	 * 
	 * @return valuation home interface
	 * @throws CollateralException on errors encountered
	 */
	protected EBValuationHome getEBValuationHome() throws CollateralException {
		EBValuationHome ejbHome = (EBValuationHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_COL_VALUATION_JNDI,
				EBValuationHome.class.getName());

		if (ejbHome == null) {
			throw new CollateralException("EBValuationHome is null!");
		}

		return ejbHome;
	}

	/**
	 * Helper method to get home interface for collateral detail.
	 * 
	 * @param collateral of type ICollateral
	 * @return EBCollateralDetailHome
	 * @throws CollateralException on error getting the home interface
	 */
	protected EBCollateralDetailHome getEBCollateralDetailHome(ICollateral collateral) throws CollateralException {
		return CollateralDetailFactory.getEBHome(collateral);
	}

	/**
	 * Get home interface of EBCollateralSubTypeBean.
	 * 
	 * @return collateral subtype home interface
	 * @throws CollateralException on errors encountered
	 */
	protected EBCollateralSubTypeHome getEBCollateralSubTypeHome() throws CollateralException {
		EBCollateralSubTypeHome ejbHome = (EBCollateralSubTypeHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_COL_SUBTYPE_JNDI, EBCollateralSubTypeHome.class.getName());

		if (ejbHome == null) {
			throw new CollateralException("EBCollateralSubTypeHome is null!");
		}

		return ejbHome;
	}

	/**
	 * Helper method to get home interface of EBCollateralParameterBean.
	 * 
	 * @return collateral parameter home interface
	 * @throws CollateralException on errors encountered
	 */
	protected EBCollateralParameterHome getEBCollateralParameterHome() throws CollateralException {
		EBCollateralParameterHome home = (EBCollateralParameterHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_COLLATERAL_PARAMETER_JNDI, EBCollateralParameterHome.class.getName());

		if (home == null) {
			throw new CollateralException("EBCollateralParameterHome is null!");
		}

		return home;
	}

	/**
	 * helper method to get home interface of EBPledgorBean.
	 * 
	 * @return pledgor home interface
	 * @throws CollateralException on errors encountered
	 */
	protected EBPledgorHome getEBPledgorHome() throws CollateralException {
		EBPledgorHome ejbHome = (EBPledgorHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_PLEDGOR_JNDI,
				EBPledgorHome.class.getName());

		if (ejbHome == null) {
			throw new CollateralException("EBPledgorHome is null!");
		}

		return ejbHome;
	}

	/**
	 * helper method to get home interface of EBPledgorCreditGradeBean.
	 * 
	 * @return pledgor credit grade home interface
	 * @throws CollateralException on errors encountered
	 */
	protected EBPledgorCreditGradeHome getEBPledgorCreditGradeHome() throws CollateralException {
		EBPledgorCreditGradeHome ejbHome = (EBPledgorCreditGradeHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_PLEDGOR_CREDIT_GRADE_JNDI, EBPledgorCreditGradeHome.class.getName());

		if (ejbHome == null) {
			throw new CollateralException("EBPledgorCreditGradeHome is null!");
		}

		return ejbHome;
	}

	/**
	 * Get home interface of EBCollateralAssetLifeBean.
	 * 
	 * @return collateral asset life home interface
	 * @throws CollateralException on errors encountered
	 */
	protected EBCollateralAssetLifeHome getEBCollateralAssetLifeHome() throws CollateralException {
		EBCollateralAssetLifeHome ejbHome = (EBCollateralAssetLifeHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_COL_ASSETLIFE_JNDI, EBCollateralAssetLifeHome.class.getName());

		if (ejbHome == null) {
			throw new CollateralException("EBCollateralAssetLifeHome is null!");
		}

		return ejbHome;
	}

	/**
	 * Helper method to set revaluation frequency and its unit.
	 * 
	 * @param col of type ICollateral
	 */
	protected void setCollateralParameter(ICollateral col) {
		if (col.getValuation() != null) {
			int freq = 0;
			String freqUnit = null;
			try {
				ICollateralParameter param = getColParamByCountryAndSubTypeCode(col.getCollateralLocation(), col
						.getCollateralSubType().getSubTypeCode());
				freq = param.getValuationFrequency();
				freqUnit = param.getValuationFrequencyUnit();
			}
			catch (Exception e) {
				DefaultLogger.warn(this, "Unable to get Collateral Param By Country and SubType Code!");
				// DefaultLogger.error (this, "", e);
				// do nothing here.
			}
			col.getValuation().setRevaluationFreq(freq);
			col.getValuation().setRevaluationFreqUnit(freqUnit);
		}
	}

	public List getLimitDetailForNewApportionment(String collateralId) throws CollateralException {
		return new SecApportionmentDAO().getLimitDetailForNewApportionment(collateralId);
	}

	public boolean checkSecurityRequireApportion(String collateralId) throws CollateralException {
		return new SecApportionmentDAO().checkSecurityRequireApportion(collateralId);
	}

	public ICollateral createCollateralWithPledgor(ICollateral collateral) throws CollateralException,RemoteException {
		try {
			createPledgorForCol(collateral);
			EBCollateralHome ejbHome = getEBCollateralHome();
			EBCollateral theEjb = ejbHome.create(collateral);
			theEjb.createDependants(collateral, theEjb.getVersionTime(), true);
			ICollateral latestCol = theEjb.getValue(collateral);

			if (!(collateral instanceof com.integrosys.cms.app.collateral.bus.type.nocollateral.INoCollateral)) {
				EBCollateralDetailHome detHome = getEBCollateralDetailHome(latestCol);
				EBCollateralDetail detEjb = detHome.create(latestCol);
				latestCol = detEjb.getValue(latestCol);
			}
			return setDerivedFields(latestCol);
		}
		catch (CreateException e) {
			rollback();
			throw new CollateralException("failed to create collateral, host id [" + collateral.getSCISecurityID()
					+ "]", e);
		}
		catch (VersionMismatchException e) {
			rollback();
			throw new CollateralException("failed to create collateral due to concurrent update, host id ["
					+ collateral.getSCISecurityID() + "]", new ConcurrentUpdateException(e.getMessage()));
		}
		catch (RemoteException e) {
			rollback();
			throw new CollateralException("failed to create collateral, host id [" + collateral.getSCISecurityID()
					+ "], throwing root cause", e.getCause());
		}
	}

	public Collection getCmsCollateralIdList(String securityId, String source) throws CollateralException {

		try {
			EBShareSecurityHome home = getEBShareSecurityHome();

			Collection col = home.findBySecurityIdAndSource(securityId, source);

			ArrayList returnList = new ArrayList();

			for (Iterator iter = col.iterator(); iter.hasNext();) {
				IShareSecurity security = ((EBShareSecurity) iter.next()).getValue();
				returnList.add(security);
			}

			return returnList;
		}
		catch (RemoteException ex) {
			rollback();
			throw new CollateralException("failed to retrieve collateral id list, given security id [" + securityId
					+ "] source [" + source + "], throwing root cause", ex.getCause());
		}
		catch (FinderException ex) {
			rollback();
			throw new CollateralException("failed to find share security, given security id [" + securityId
					+ "] source [" + source + "]", ex);
		}
		catch (Exception ex) {
			rollback();
			throw new CollateralException("failed to find share security, given security id [" + securityId
					+ "] source [" + source + "]", ex);
		}
	}

	public Long getCmsCollateralId(String securityId, String source) throws CollateralException {
		try {
			EBShareSecurityHome home = getEBShareSecurityHome();

			Collection col = home.findBySecurityIdAndSource(securityId, source);

			if (col.size() == 0) {
				throw new FinderException("No internal security id key found !");
			}

			if (col.size() == 1) {
				Iterator iter = col.iterator();
				EBShareSecurity security = (EBShareSecurity) iter.next();
				Long returnValue = new Long(security.getValue().getCmsCollateralId());

				DefaultLogger.debug(this, "One unqiue record found , internal key security id : "
						+ returnValue.longValue());

				return returnValue;
			}

			DefaultLogger.debug(this, "Mutliple records found, proceeding to filter out only MS600");

			for (Iterator iter = col.iterator(); iter.hasNext();) {
				IShareSecurity security = ((EBShareSecurity) iter.next()).getValue();

				if ((security.getSecuritySubTypeId() != null)
						&& ICMSConstant.COLTYPE_MAR_MAIN_IDX_LOCAL.equalsIgnoreCase(security.getSecuritySubTypeId()
								.trim())) {
					Long returnValue = new Long(security.getCmsCollateralId());

					DefaultLogger.debug(this, "One unqiue record found , internal key security id : "
							+ returnValue.longValue());

					return returnValue;
				}
			}

			DefaultLogger.fatal(this, "No internal security id key found !");

			throw new FinderException("No internal security id key found !");

			// return new Long ( ICMSConstant.LONG_INVALID_VALUE ) ;
		}
		catch (RemoteException ex) {
			rollback();
			throw new CollateralException("failed to retrieve cms collateral id list given, security id [" + securityId
					+ "] from source [" + source + "], throwing root cause", ex.getCause());
		}
		catch (FinderException ex) {
			rollback();
			throw new CollateralException("failed to retrieve cms collateral id list given, security id [" + securityId
					+ "] from source [" + source + "]", ex);
		}
		catch (Exception ex) {
			rollback();
			throw new CollateralException("failed to retrieve cms collateral id list given, security id [" + securityId
					+ "] from source [" + source + "]", ex);
		}
	}

	// ******************** Methods for MF Checklist ****************
	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralBusManager#getMFChecklistByCollateralID
	 */
	public IMFChecklist getMFChecklistByCollateralID(long collateralID) throws CollateralException {
		if ((collateralID == ICMSConstant.LONG_INVALID_VALUE) || (collateralID == ICMSConstant.LONG_MIN_VALUE)) {
			throw new CollateralException("collateralID is invalid");
		}
		EBMFChecklistHome ejbHome = getEBMFChecklistHome();
		try {
			EBMFChecklist theEjb = ejbHome.findByCollateralID(collateralID);

			return theEjb.getValue();
		}
		catch (FinderException e) {
			return null;
		}
		catch (RemoteException e) {
			throw new CollateralException("failed to retrieve market factor checklist, collateral id [" + collateralID
					+ "], throwing root cause", e.getCause());
		}
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralBusManager#getMFChecklist
	 */
	public IMFChecklist getMFChecklist(long mFChecklistID) throws CollateralException {

		EBMFChecklistHome ejbHome = getEBMFChecklistHome();
		try {

			EBMFChecklist theEjb = ejbHome.findByPrimaryKey(new Long(mFChecklistID));

			return theEjb.getValue();
		}
		catch (FinderException e) {
			throw new CollateralException("failed to retrieve market factor checklist, id [" + mFChecklistID + "]", e);
		}
		catch (RemoteException e) {
			throw new CollateralException("failed to retrieve market factor checklist, id [" + mFChecklistID
					+ "], throwing root cause", e.getCause());
		}
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralBusManager#createMFChecklist
	 */
	public IMFChecklist createMFChecklist(IMFChecklist value) throws CollateralException {
		if (value == null) {
			throw new CollateralException("IMFChecklist is null");
		}

		EBMFChecklistHome ejbHome = getEBMFChecklistHome();

		try {
			EBMFChecklist theEjb = ejbHome.create(value);

			long verTime = theEjb.getVersionTime();
			theEjb.createDependants(value, verTime, theEjb.getMFChecklistID());

			return theEjb.getValue();
		}
		catch (CreateException e) {
			rollback();
			throw new CollateralException("failed to create market factor checklist, collateral id ["
					+ value.getCollateralID() + "]", e);
		}
		catch (ConcurrentUpdateException e) {
			rollback();
			throw new CollateralException("failed to update market factor checklist due to concurrent update", e);
		}
		catch (RemoteException e) {
			rollback();
			throw new CollateralException("failed to create market factor checklist, collateral id ["
					+ value.getCollateralID() + "], throwing root cause", e.getCause());
		}
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralBusManager#updateMFChecklist
	 */
	public IMFChecklist updateMFChecklist(IMFChecklist value) throws CollateralException {
		if (value == null) {
			throw new CollateralException("IMFChecklist is null");
		}

		EBMFChecklistHome ejbHome = getEBMFChecklistHome();
		try {

			EBMFChecklist theEjb = ejbHome.findByPrimaryKey(new Long(value.getMFChecklistID()));
			theEjb.setValue(value);

			return theEjb.getValue();
		}
		catch (FinderException e) {
			rollback();
			throw new CollateralException("failed to retrieve market factor checklist, id [" + value.getMFChecklistID()
					+ "]", e);
		}
		catch (VersionMismatchException e) {
			rollback();
			throw new CollateralException("failed to update market factor checklist due to concurrent update",
					new ConcurrentUpdateException(e.getMessage()));
		}
		catch (RemoteException e) {
			rollback();
			throw new CollateralException("failed to retrieve market factor checklist, id [" + value.getMFChecklistID()
					+ "], throwing root cause", e.getCause());
		}

	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralBusManager#createLimitChargeMap
	 */
	public ICollateral createLimitChargeMap(ILimitChargeMap lmtChargeMap) throws CollateralException {
		try {
			EBCollateralHome ejbHome = getEBCollateralHome();
			EBCollateral theEjb = ejbHome.findByPrimaryKey(new Long(lmtChargeMap.getCollateralID()));

			theEjb.setLimitChargeMapRef(lmtChargeMap, false);

			return theEjb.getValue();
		}
		catch (FinderException e) {
			rollback();
			throw new CollateralException("failed to retrieve collateral, collateral id ["
					+ lmtChargeMap.getCollateralID() + "]");
		}
		catch (RemoteException e) {
			rollback();
			throw new CollateralException("failed to create limit charge map, collateral id ["
					+ lmtChargeMap.getCollateralID() + "], throwing root cause", e.getCause());
		}
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralBusManager#removeLimitChargeMap
	 */
	public void removeLimitChargeMap(ILimitChargeMap lmtChargeMap) throws CollateralException {
		try {
			EBCollateralHome ejbHome = getEBCollateralHome();
			EBCollateral theEjb = ejbHome.findByPrimaryKey(new Long(lmtChargeMap.getCollateralID()));

			theEjb.setLimitChargeMapRef(lmtChargeMap, true);
		}
		catch (FinderException e) {
			rollback();
			throw new CollateralException("failed to retrieve collateral, collateral id ["
					+ lmtChargeMap.getCollateralID() + "]");
		}
		catch (RemoteException e) {
			rollback();
			throw new CollateralException("failed to remove limit charge map, collateral id ["
					+ lmtChargeMap.getCollateralID() + "], throwing root cause", e.getCause());
		}
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralBusManager#getCollateralLimitMap
	 */
	public ICollateralLimitMap getCollateralLimitMap(long collateralID, long cmsLimitID) throws CollateralException {

		try {
			EBCollateralHome ejbHome = getEBCollateralHome();
			EBCollateral theEjb = ejbHome.findByPrimaryKey(new Long(collateralID));

			return theEjb.getCollateralLimitMap(cmsLimitID);
		}
		catch (FinderException e) {
			rollback();
			throw new CollateralException("failed to retrieve collateral limit map, collateral id [" + collateralID
					+ "]", e);
		}
		catch (RemoteException e) {
			rollback();
			throw new CollateralException("failed to retrieve collateral limit map, collateral id [" + collateralID
					+ "], throwing root cause", e.getCause());
		}
	}

	/**
	 * Helper method to get home interface of EBMFChecklistBean.
	 * 
	 * @return MF checklist home interface
	 * @throws CollateralException on errors encountered
	 */
	protected EBMFChecklistHome getEBMFChecklistHome() throws CollateralException {
		EBMFChecklistHome ejbHome = (EBMFChecklistHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_MF_CHECKLIST_JNDI, EBMFChecklistHome.class.getName());

		if (ejbHome == null) {
			throw new CollateralException("EBMFChecklistHome is null!");
		}

		return ejbHome;
	}

	private EBShareSecurityHome getEBShareSecurityHome() throws CollateralException {
		EBShareSecurityHome home = (EBShareSecurityHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_SHARE_SECURITY_JNDI, EBShareSecurityHome.class.getName());

		if (home == null) {
			throw new CollateralException("EBShareSecurityHome is null!");
		}

		return home;
	}

	private void createPledgorForCol(ICollateral col) throws CollateralException {
		try {
			ICollateralPledgor[] pledgors = col.getPledgors();
			if (pledgors != null) {
				EBPledgorHome pledgorHome = getEBPledgorHome();
				for (int i = 0; i < pledgors.length; i++) {
					ICollateralPledgor nextLink = pledgors[i];
					EBPledgor newPledgor = null;
					try {
						// do not create pledgor with same legal ID in pledgor
						// details table
						newPledgor = pledgorHome.findByLegalID(nextLink.getLegalID());
						newPledgor.setValue(nextLink);

					}
					catch (FinderException fe) {
						newPledgor = pledgorHome.create(nextLink);
					}
					nextLink.setPledgorID(newPledgor.getValue().getPledgorID());
				}
			}
		}
		catch (CreateException ex) {
			throw new CollateralException("failed to create pledgor, collateral id [" + col.getCollateralID() + "]", ex);
		}
		catch (RemoteException ex) {
			throw new CollateralException("failed to create pledgor, collateral id [" + col.getCollateralID()
					+ "], throwing root cause", ex.getCause());
		}
	}

	/**
	 * Called by the container to create a session bean instance. Its parameters
	 * typically contain the information the client uses to customize the bean
	 * instance for its use. It requires a matching pair in the bean class and
	 * its home interface. No implementation is required for this bean.
	 */
	public void ejbCreate() {
	}

	/**
	 * Set the associated session context. The container calls this method after
	 * the instance creation. The enterprise bean instance should store the
	 * reference to the context object in an instance variable. This method is
	 * called with no transaction context.
	 */
	public void setSessionContext(SessionContext ctx) {
		this.ctx = ctx;
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
	 * A container invokes this method before it ends the life of the session
	 * object. This happens as a result of a client's invoking a remove
	 * operation, or when a container decides to terminate the session object
	 * after a timeout. This method is called with no transaction context.
	 */
	public void ejbRemove() {
	}

}