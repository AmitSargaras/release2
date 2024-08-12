package com.integrosys.cms.app.creditriskparam.bus;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * SBCreditRiskParamBusManagerBean Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class SBCreditRiskParamBusManagerBean implements SessionBean {

	private SessionContext _context = null;

	// public SBCreditRiskParamBusManagerBean()
	// {}

	public void ejbCreate() {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void setSessionContext(SessionContext sc) {
		_context = sc;
	}

	public ICreditRiskParamGroup createCreditRiskParameters(ICreditRiskParamGroup paramGroup)
			throws CreditRiskParamGroupException {
		try {
			if (paramGroup == null) {
				throw new CreditRiskParamGroupException("The paramGroup to be created is null !!!");
			}
			if (paramGroup.getFeedEntries() == null) {
				throw new CreditRiskParamGroupException("The paramGroup.getFeedEntries() to be created is invalid !!!");
			}

			OBCreditRiskParam[] obCreditRiskParams = paramGroup.getFeedEntries();
			ArrayList createList = new ArrayList();
			// ArrayList removeList = new ArrayList();
			long removeRefId = ICMSConstant.LONG_INVALID_VALUE;

			EBCreditRiskParamHome home = getEBCreditRiskParamHome();

			for (int i = 0; i < obCreditRiskParams.length; i++) {
				OBCreditRiskParam oBParam = (OBCreditRiskParam) obCreditRiskParams[i];

				if (i == 0) {
					DefaultLogger.debug(this, "current Ref ID : " + oBParam.getParameterRef());
					removeRefId = oBParam.getParameterRef();
				}

				DefaultLogger.debug(this, "new Record...");
				createList.add(oBParam);
				// removeList.add(oBParam);
			}
			removeStagingCreditRiskParams(removeRefId); // remove previous
														// staging record
			createStagingCreditRiskParams(createList);

			return paramGroup;
		}
		catch (Exception ex) {
			throw new CreditRiskParamGroupException("Exception in updateCreditRiskParameters: " + ex.toString());
		}
	}

	public ICreditRiskParamGroup getCreditRiskParameters(long groupFeedId) throws CreditRiskParamGroupException {
		try {

			return getCreditRiskParamDAO().getCreditRiskParams(groupFeedId);
		}
		catch (SearchDAOException ex) {
			throw new CreditRiskParamGroupException("Exception in getCreditRiskParams: " + ex.toString());
		}
	}

	public ICreditRiskParamGroup getStagingCreditRiskParameters(long groupFeedId) throws CreditRiskParamGroupException {
		try {
			return getCreditRiskParamDAO().getStagingCreditRiskParams(groupFeedId);
		}
		catch (Exception ex) {
			throw new CreditRiskParamGroupException("Exception in getCreditRiskParams: " + ex.toString());
		}
	}

	public ICreditRiskParamGroup updateCreditRiskParameters(ICreditRiskParamGroup paramGroup)
			throws ConcurrentUpdateException, CreditRiskParamGroupException {
		try {
			if (paramGroup == null) {
				throw new CreditRiskParamGroupException("The paramGroup to be updated is null !!!");
			}
			if (paramGroup.getFeedEntries() == null) {
				throw new CreditRiskParamGroupException("The paramGroup.getFeedEntries() to be updated is invalid !!!");
			}

			OBCreditRiskParam[] obCreditRiskParams = paramGroup.getFeedEntries();
			ArrayList createList = new ArrayList();
			ArrayList updateList = new ArrayList();

			EBCreditRiskParamHome home = getEBCreditRiskParamHome();

			for (int i = 0; i < obCreditRiskParams.length; i++) {
				OBCreditRiskParam oBParam = (OBCreditRiskParam) obCreditRiskParams[i];

//				DefaultLogger.debug(this, "Now searching for record by feed id : \'"
//						+ oBParam.getCreditRiskParamEntryID() + "\'");

				EBCreditRiskParam checkParam = null;
				try {
					checkParam = home.findByFeedId(new Long(oBParam.getCreditRiskParamEntryID()));
				}
				catch (FinderException ex) {
					checkParam = null;
				}
				// catch(RemoteException ex)
				// {
				// throw new CreditRiskParamGroupException(
				// "Exception in updateCreditRiskParameters: " + ex.toString());
				// }

				if (checkParam == null) {
					DefaultLogger.debug(this, "new Record...");
					createList.add(oBParam);
				}
				else {
					DefaultLogger.debug(this, "existing Record...");
					updateList.add(oBParam);
				}
			}

			createCreditRiskParams(createList);
			updateCreditRiskParams(updateList);

			// return remote.getValue();
			return paramGroup;
		}
		// catch(FinderException ex)
		// {
		// throw new CreditRiskParamGroupException(
		// "Exception in updateCreditRiskParameters: " + ex.toString());
		// }
		catch (RemoteException ex) {
			throw new CreditRiskParamGroupException("Exception in updateCreditRiskParameters: " + ex.toString());
		}
	}

	public ArrayList getCreditRiskParamGroup(String groupType, String groupSubType, String groupStockType)
			throws CreditRiskParamGroupException {
		try {
			if ((groupType == null) || ("").equals(groupType)) {
				throw new CreditRiskParamGroupException("The groupType is null / empty !!!");
			}
			return (ArrayList) getCreditRiskParamDAO().getCreditRiskParamGroup(groupType, groupSubType, groupStockType);
		}
		catch (SearchDAOException ex) {
			throw new CreditRiskParamGroupException("Exception in getCreditRiskParamGroup: " + ex.toString());
		}
	}

	protected EBCreditRiskParamHome getEBCreditRiskParamHome() {
		EBCreditRiskParamHome home = (EBCreditRiskParamHome) BeanController.getEJBHome("EBCreditRiskParamHome",
				EBCreditRiskParamHome.class.getName());
		return home;
	}

	private CreditRiskParamDAO getCreditRiskParamDAO() {
		return new CreditRiskParamDAO();
	}

	/**
	 * Create the list of CreditRiskParam under the current CreditRiskParamGroup
	 * @param creditRiskParamsList - List of OBCreditRiskParams to be inserted
	 *        into child EB
	 * @throws CreditRiskParamGroupException on errors
	 */
	private void createCreditRiskParams(List creditRiskParamsList) throws CreditRiskParamGroupException {

		DefaultLogger.debug(this, "entering createCreditRiskParams(<<List>>)...");

		if ((creditRiskParamsList == null) || (creditRiskParamsList.size() == 0)) {
			return; // do nothing
		}

		Iterator iter = creditRiskParamsList.iterator();

		try {
			EBCreditRiskParamHome home = getEBCreditRiskParamHome();

			DefaultLogger.debug(this, "the runtime class of EBCreditRiskParamHome is " + home.getClass().getName());

			while (iter.hasNext()) {
				OBCreditRiskParam obj = (OBCreditRiskParam) iter.next();
				DefaultLogger.debug(this, "Creating CreditRiskParam ID: " + obj.getParameterId());

				obj.setParameterType(obj.getType());
				obj.setFeedId(obj.getCreditRiskParamEntryID());
				// obj.setParameterRef(obj.getCreditRiskParamEntryRef());

				ICreditRiskParam iCreditRiskParam = (ICreditRiskParam) obj;

				// EBUnitTrustFeedEntryLocal local = home.create(
				// new Long(getUnitTrustFeedGroupID()), obj);
				home.create(iCreditRiskParam);

				// col.add(local); // container managed persistence
				//
				// handleHistory(col2, obj);

			}
		}
		catch (Exception ex) {
			throw new CreditRiskParamGroupException("Exception in createCreditRiskParams: " + ex.toString());
		}
	}

	/**
	 * Create the list of CreditRiskParam under the current CreditRiskParamGroup
	 * @param creditRiskParamsList - List of OBCreditRiskParams to be inserted
	 *        into child EB
	 * @throws CreditRiskParamGroupException on errors
	 */
	private void createStagingCreditRiskParams(List creditRiskParamsList) throws CreditRiskParamGroupException {

		DefaultLogger.debug(this, "entering createStagingCreditRiskParams(<<List>>)...");

		if ((creditRiskParamsList == null) || (creditRiskParamsList.size() == 0)) {
			return; // do nothing
		}

		Iterator iter = creditRiskParamsList.iterator();

		try {
			EBCreditRiskParamHome home = getEBCreditRiskParamHome();

			DefaultLogger.debug(this, "the runtime class of EBCreditRiskParamHome is " + home.getClass().getName());

			long ref = ICMSConstant.LONG_INVALID_VALUE;
			if (iter.hasNext()) {
				DefaultLogger.debug(this, "Sequence Name: " + getSequenceName());
				ref = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			}

			while (iter.hasNext()) {
				OBCreditRiskParam obj = (OBCreditRiskParam) iter.next();
				DefaultLogger.debug(this, "Creating CreditRiskParam ID: " + obj.getParameterId());

				obj.setParameterType(obj.getType());
				obj.setFeedId(obj.getCreditRiskParamEntryID());
				obj.setParameterRef(ref);

				ICreditRiskParam iCreditRiskParam = (ICreditRiskParam) obj;

//				DefaultLogger.debug(this, "Creating CreditRiskParam : "
//						+ AccessorUtil.printMethodValue(iCreditRiskParam));

				home.create(iCreditRiskParam);
			}
		}
		catch (Exception ex) {
			throw new CreditRiskParamGroupException("Exception in createStagingCreditRiskParams: " + ex.toString());
		}
	}

	/**
	 * Delete the list of CreditRiskParam under the current CreditRiskParamGroup
	 * for the previous staging table
	 * @param refId - ref ID of the staging data
	 * @throws CreditRiskParamGroupException on errors
	 */
	private void removeStagingCreditRiskParams(long refId) throws CreditRiskParamGroupException {

		DefaultLogger.debug(this, "entering removeStagingCreditRiskParams(<<long>>)...");

		if ((refId == ICMSConstant.LONG_INVALID_VALUE)) {
			return; // do nothing
		}

		try {
			EBCreditRiskParamHome home = getEBCreditRiskParamHome();
			Collection collection = new ArrayList();

			DefaultLogger.debug(this, "Remove CreditRiskParam Ref: " + refId);
			collection = home.findByParamRef(new Long(refId));

			DefaultLogger.debug(this, "the runtime class of EBCreditRiskParamHome is " + home.getClass().getName());
			DefaultLogger.debug(this, "no. of staging records to be deleted : " + collection.size());

			Iterator iterList = collection.iterator();

			while (iterList.hasNext()) {
				EBCreditRiskParam obj = (EBCreditRiskParam) iterList.next();
				DefaultLogger.debug(this, "obj.getParameterId() : " + obj.getParameterId());
				// EBCreditRiskParam ebParam = home.findByPrimaryKey(new
				// Long(obj.getParameterId()));
				obj.remove();
			}
		}
		catch (Exception ex) {
			throw new CreditRiskParamGroupException("Exception in removeStagingCreditRiskParams: " + ex.toString());
		}
	}

	/**
	 * update the list of CreditRiskParam under the current CreditRiskParamGroup
	 * @param creditRiskParamsList - List of OBCreditRiskParams to be inserted
	 *        into child EB
	 * @throws CreditRiskParamGroupException on errors
	 */
	private void updateCreditRiskParams(List creditRiskParamsList) throws CreditRiskParamGroupException {

		DefaultLogger.debug(this, "entering updateCreditRiskParams(<<List>>)...");

		if ((creditRiskParamsList == null) || (creditRiskParamsList.size() == 0)) {
			return; // do nothing
		}

		Iterator iter = creditRiskParamsList.iterator();

		try {
			EBCreditRiskParamHome home = getEBCreditRiskParamHome();

			DefaultLogger.debug(this, "the runtime class of EBCreditRiskParamHome is " + home.getClass().getName());

			while (iter.hasNext()) {
				OBCreditRiskParam obj = (OBCreditRiskParam) iter.next();
				//DefaultLogger.debug(this, "Updating CreditRiskParam ID: " + obj.getParameterId());

				obj.setParameterType(obj.getType());
				obj.setFeedId(obj.getCreditRiskParamEntryID());
				// obj.setParameterRef(obj.getCreditRiskParamEntryRef());

				ICreditRiskParam iCreditRiskParam = (ICreditRiskParam) obj;

				// EBUnitTrustFeedEntryLocal local = home.create(
				// new Long(getUnitTrustFeedGroupID()), obj);
				// EBCreditRiskParam ebParam = home.findByPrimaryKey(new
				// Long(iCreditRiskParam.getParameterId()));
				EBCreditRiskParam ebParam = home.findByFeedId(new Long(iCreditRiskParam.getFeedId()));
				ebParam.setValue(iCreditRiskParam);

				// col.add(local); // container managed persistence
				//
				// handleHistory(col2, obj);
			}
		}
		catch (Exception ex) {
			throw new CreditRiskParamGroupException("Exception in createCreditRiskParams: " + ex.toString());
		}
	}

	/**
	 * Get the name of the sequence to be used for the item id
	 * @return String - the name of the sequence
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_CMS_STG_CREDIT_RISK_PARAM_REF_SEQ;
	}

}
