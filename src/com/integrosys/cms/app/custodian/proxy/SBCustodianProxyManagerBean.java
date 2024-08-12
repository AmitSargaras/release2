/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/proxy/SBCustodianProxyManagerBean.java,v 1.56 2006/08/31 07:50:41 jzhai Exp $
 */
package com.integrosys.cms.app.custodian.proxy;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.event.EventHandlingException;
import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.bizstructure.proxy.CMSTeamProxy;
import com.integrosys.cms.app.checklist.bus.CheckListDAO;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.custodian.bus.CustodianException;
import com.integrosys.cms.app.custodian.bus.CustodianSearchCriteria;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.custodian.bus.ICustodianDocItem;
import com.integrosys.cms.app.custodian.bus.IMemo;
import com.integrosys.cms.app.custodian.bus.OBCustAuthorize;
import com.integrosys.cms.app.custodian.bus.OBCustodianDoc;
import com.integrosys.cms.app.custodian.bus.OBMemo;
import com.integrosys.cms.app.custodian.bus.SBCustodianBusManager;
import com.integrosys.cms.app.custodian.bus.SBCustodianBusManagerHome;
import com.integrosys.cms.app.custodian.trx.ICustodianTrxValue;
import com.integrosys.cms.app.custodian.trx.OBCustodianTrxValue;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.app.eventmonitor.updatecustodian.OBUpdateCustodian;
import com.integrosys.cms.app.eventmonitor.updatecustodian.UpdateCustodianListener;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.user.bus.StdUserDAO;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelopeItem;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.OBTeamType;

/**
 * Session bean implementation of the services provided by the custodian proxy
 * manager. This will only contains the persistance logic.
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.56 $
 * @since $Date: 2006/08/31 07:50:41 $ Tag: $Name: $
 */
public class SBCustodianProxyManagerBean extends AbstractCustodianProxyManager implements SessionBean {
	/** SessionContext object */
	private SessionContext _context = null;

	public SBCustodianProxyManagerBean() {
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
	 * To call the custodian bus mgr session bean to create the custodian doc
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the custodian document created
	 * @throws CustodianException if errors
	 */
	protected ICustodianDoc createCustodianDoc(ICustodianDoc anICustodianDoc) throws CustodianException {
		try {
			return getCustodianBusManager().create(anICustodianDoc);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

    public SearchResult getPendingReversalList(long aLimitProfileID) throws CustodianException {

            return  getPendingReversalList(null, aLimitProfileID);
        }

    public SearchResult getPendingReversalList(ITrxContext aTrxContext, long aLimitProfileID)
                throws CustodianException {
            // TODO: implement
            try {
                CustodianSearchCriteria sc = new CustodianSearchCriteria();
                sc.setLimitProfileID(aLimitProfileID);
                // String[] status = new String[]{ICMSConstant.STATE_RECEIVED,
                // ICMSConstant.STATE_AUTHZ_RELODGED};
                // String[] statusExclude = new String[]
                // {ICMSConstant.STATE_PENDING_LODGE, ICMSConstant.STATE_LODGED,
                // ICMSConstant.STATE_PENDING_RELODGE};
                // sc.setCPCCustodianStatus(status);
                // sc.setCPCCustodianStatusExclude(statusExclude);

                // bernard - added the following to display lodgement documents of
                // the following statuses
                // when constructing CheckListCustodianStatus, note the following:
                // 1. null will omit the field entirely
                // 2. empty string will translate into "IS NULL" for that field
                // 3. non-empty string will be equated to that field as a criteria
                // CheckListCustodianStatus[] checkListCustodianStatuses = new
                // CheckListCustodianStatus[4];
                // checkListCustodianStatuses[0] = new
                // CheckListCustodianStatus(ICMSConstant
                // .STATE_ITEM_PENDING_COMPLETE, null, null, null, null, null);
                // checkListCustodianStatuses[1] = new
                // CheckListCustodianStatus(null,
                // ICMSConstant.STATE_PENDING_AUTHZ_RELODGE, null, null, null,
                // ICMSConstant.STATE_TEMP_UPLIFTED);
                // checkListCustodianStatuses[2] = new
                // CheckListCustodianStatus(null, null, null,
                // ICMSConstant.STATE_RECEIVED, "", null);
                // checkListCustodianStatuses[3] = new
                // CheckListCustodianStatus(null, null, null,
                // ICMSConstant.STATE_AUTHZ_RELODGED, null,
                // ICMSConstant.STATE_TEMP_UPLIFTED);
                // sc.setCheckListCustodianStatus(checkListCustodianStatuses);
                sc.setIsInVaultInd(Boolean.TRUE);
                sc.setIsPrintReversal(true);
                sc.setIsDocItemNarrationRequired(true);

                if (aTrxContext != null) {
                    ITeam team = aTrxContext.getTeam();
                    // DefaultLogger.debug(this,
                    // "Team abbreviation="+team.getAbbreviation());
                    String[] countryCodes = team.getCountryCodes();
                    String[] orgCodes = team.getOrganisationCodes();

                    /*
                     * if (countryCodes!=null) { for (int i=0;
                     * i<countryCodes.length; i++) DefaultLogger.debug(this,
                     * "Country code["+i+"]="+countryCodes[i]); } if
                     * (orgCodes!=null) { for (int i=0; i<orgCodes.length; i++)
                     * DefaultLogger.debug(this,
                     * "Organisation code["+i+"]="+orgCodes[i]); }
                     */

                    sc.setCountryCodes(countryCodes);
                    sc.setOrganisationCodes(orgCodes);
                }
                else {
                    DefaultLogger.debug(this, "aTrxContext is null!");
                }

                SearchResult sr = getDocList(sc);
                return sr;
            }
            catch (CustodianException rex) {
                rex.printStackTrace();
                throw new CustodianException(rex.toString());
            }
        }


    public SearchResult getPendingReversalListForNonBorrower(long aCustomerID) throws CustodianException {

        return  getPendingReversalListForNonBorrower(null, aCustomerID);
    }


    public SearchResult getPendingReversalListForNonBorrower(ITrxContext aTrxContext, long aCustomerID)
            throws CustodianException {
        try {
            CustodianSearchCriteria sc = new CustodianSearchCriteria();
            sc.setSubProfileID(aCustomerID);
            sc.setDocSubType(ICMSConstant.CHECKLIST_NON_BORROWER);
            // String[] status = new String[]{ICMSConstant.STATE_RECEIVED,
            // ICMSConstant.STATE_AUTHZ_RELODGED};
            // String[] statusExclude = new String[]
            // {ICMSConstant.STATE_PENDING_LODGE, ICMSConstant.STATE_LODGED,
            // ICMSConstant.STATE_PENDING_RELODGE};
            // sc.setCPCCustodianStatus(status);
            // sc.setCPCCustodianStatusExclude(statusExclude);

            // bernard - added the following to display lodgement documents of
            // the following statuses
            // when constructing CheckListCustodianStatus, note the following:
            // 1. null will omit the field entirely
            // 2. empty string will translate into "IS NULL" for that field
            // 3. non-empty string will be equated to that field as a criteria
            // CheckListCustodianStatus[] checkListCustodianStatuses = new
            // CheckListCustodianStatus[4];
            // checkListCustodianStatuses[0] = new
            // CheckListCustodianStatus(ICMSConstant
            // .STATE_ITEM_PENDING_COMPLETE, null, null, null, null, null);
            // checkListCustodianStatuses[1] = new
            // CheckListCustodianStatus(null,
            // ICMSConstant.STATE_PENDING_AUTHZ_RELODGE, null, null, null,
            // ICMSConstant.STATE_TEMP_UPLIFTED);
            // checkListCustodianStatuses[2] = new
            // CheckListCustodianStatus(null, null, null,
            // ICMSConstant.STATE_RECEIVED, "", null);
            // checkListCustodianStatuses[3] = new
            // CheckListCustodianStatus(null, null, null,
            // ICMSConstant.STATE_AUTHZ_RELODGED, null,
            // ICMSConstant.STATE_TEMP_UPLIFTED);
            // sc.setCheckListCustodianStatus(checkListCustodianStatuses);
            sc.setIsInVaultInd(Boolean.TRUE);
            sc.setIsPrintReversal(true);
            sc.setIsDocItemNarrationRequired(true);

            if (aTrxContext != null) {
                ITeam team = aTrxContext.getTeam();
                // DefaultLogger.debug(this,
                // "Team abbreviation="+team.getAbbreviation());
                String[] countryCodes = team.getCountryCodes();
                String[] orgCodes = team.getOrganisationCodes();

                /*
                 * if (countryCodes!=null) { for (int i=0;
                 * i<countryCodes.length; i++) DefaultLogger.debug(this,
                 * "Country code["+i+"]="+countryCodes[i]); } if
                 * (orgCodes!=null) { for (int i=0; i<orgCodes.length; i++)
                 * DefaultLogger.debug(this,
                 * "Organisation code["+i+"]="+orgCodes[i]); }
                 */

                sc.setCountryCodes(countryCodes);
                sc.setOrganisationCodes(orgCodes);
            }
            else {
                DefaultLogger.debug(this, "aTrxContext is null!");
            }

            SearchResult sr = getDocList(sc);
            return sr;
        }
        catch (CustodianException rex) {
            throw new CustodianException(rex.toString());
        }
    }

    public SearchResult getPendingReversalListForNonBorrower(long aLimitProfileID, long aCustomerID)
            throws CustodianException {
        return getPendingReversalListForNonBorrower(null, aLimitProfileID, aCustomerID);
    }


    public SearchResult getPendingReversalListForNonBorrower(ITrxContext aTrxContext, long aLimitProfileID,
            long aCustomerID) throws CustodianException {
        try {
            CustodianSearchCriteria sc = new CustodianSearchCriteria();
            sc.setLimitProfileID(aLimitProfileID);
            sc.setSubProfileID(aCustomerID);
            sc.setDocSubType(ICMSConstant.CHECKLIST_NON_BORROWER);
            // String[] status = new String[]{ICMSConstant.STATE_RECEIVED,
            // ICMSConstant.STATE_AUTHZ_RELODGED};
            // String[] statusExclude = new String[]
            // {ICMSConstant.STATE_PENDING_LODGE, ICMSConstant.STATE_LODGED,
            // ICMSConstant.STATE_PENDING_RELODGE};
            // sc.setCPCCustodianStatus(status);
            // sc.setCPCCustodianStatusExclude(statusExclude);

            // bernard - added the following to display lodgement documents of
            // the following statuses
            // when constructing CheckListCustodianStatus, note the following:
            // 1. null will omit the field entirely
            // 2. empty string will translate into "IS NULL" for that field
            // 3. non-empty string will be equated to that field as a criteria
            // CheckListCustodianStatus[] checkListCustodianStatuses = new
            // CheckListCustodianStatus[4];
            // checkListCustodianStatuses[0] = new
            // CheckListCustodianStatus(ICMSConstant
            // .STATE_ITEM_PENDING_COMPLETE, null, null, null, null, null);
            // checkListCustodianStatuses[1] = new
            // CheckListCustodianStatus(null,
            // ICMSConstant.STATE_PENDING_AUTHZ_RELODGE, null, null, null,
            // ICMSConstant.STATE_TEMP_UPLIFTED);
            // checkListCustodianStatuses[2] = new
            // CheckListCustodianStatus(null, null, null,
            // ICMSConstant.STATE_RECEIVED, "", null);
            // checkListCustodianStatuses[3] = new
            // CheckListCustodianStatus(null, null, null,
            // ICMSConstant.STATE_AUTHZ_RELODGED, null,
            // ICMSConstant.STATE_TEMP_UPLIFTED);
            // sc.setCheckListCustodianStatus(checkListCustodianStatuses);
            sc.setIsInVaultInd(Boolean.TRUE);
            sc.setIsPrintReversal(true);
            sc.setIsDocItemNarrationRequired(true);

            sc.setForMemoPrinting(true);
            if (aTrxContext != null) {
                ITeam team = aTrxContext.getTeam();
                // DefaultLogger.debug(this,
                // "Team abbreviation="+team.getAbbreviation());
                String[] countryCodes = team.getCountryCodes();
                String[] orgCodes = team.getOrganisationCodes();

                /*
                 * if (countryCodes!=null) { for (int i=0;
                 * i<countryCodes.length; i++) DefaultLogger.debug(this,
                 * "Country code["+i+"]="+countryCodes[i]); } if
                 * (orgCodes!=null) { for (int i=0; i<orgCodes.length; i++)
                 * DefaultLogger.debug(this,
                 * "Organisation code["+i+"]="+orgCodes[i]); }
                 */

                sc.setCountryCodes(countryCodes);
                sc.setOrganisationCodes(orgCodes);
            }
            else {
                DefaultLogger.debug(this, "aTrxContext is null!");
            }

            SearchResult sr = getDocList(sc);
            return sr;
        }
        catch (CustodianException rex) {
            throw new CustodianException(rex.toString());
        }
    }


    /**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing
	 * @param aLimitProfileID - the limit profile ID
	 * @return SearchResult - the list of custodian doc that is pending
	 *         lodgement
	 * @throws CustodianException
	 */
	public SearchResult getPendingLodgementList(long aLimitProfileID) throws CustodianException {

		return getPendingLodgementList(null, aLimitProfileID);
	}

	/**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing
	 * @param aTrxContext - the transaction context
	 * @param aLimitProfileID - the limit profile ID
	 * @return SearchResult - the list of custodian doc that is pending
	 *         lodgement
	 * @throws CustodianException
	 */
	public SearchResult getPendingLodgementList(ITrxContext aTrxContext, long aLimitProfileID)
			throws CustodianException {
		// TODO: implement
		try {
			CustodianSearchCriteria sc = new CustodianSearchCriteria();
			sc.setLimitProfileID(aLimitProfileID);
			// String[] status = new String[]{ICMSConstant.STATE_RECEIVED,
			// ICMSConstant.STATE_AUTHZ_RELODGED};
			// String[] statusExclude = new String[]
			// {ICMSConstant.STATE_PENDING_LODGE, ICMSConstant.STATE_LODGED,
			// ICMSConstant.STATE_PENDING_RELODGE};
			// sc.setCPCCustodianStatus(status);
			// sc.setCPCCustodianStatusExclude(statusExclude);

			// bernard - added the following to display lodgement documents of
			// the following statuses
			// when constructing CheckListCustodianStatus, note the following:
			// 1. null will omit the field entirely
			// 2. empty string will translate into "IS NULL" for that field
			// 3. non-empty string will be equated to that field as a criteria
			// CheckListCustodianStatus[] checkListCustodianStatuses = new
			// CheckListCustodianStatus[4];
			// checkListCustodianStatuses[0] = new
			// CheckListCustodianStatus(ICMSConstant
			// .STATE_ITEM_PENDING_COMPLETE, null, null, null, null, null);
			// checkListCustodianStatuses[1] = new
			// CheckListCustodianStatus(null,
			// ICMSConstant.STATE_PENDING_AUTHZ_RELODGE, null, null, null,
			// ICMSConstant.STATE_TEMP_UPLIFTED);
			// checkListCustodianStatuses[2] = new
			// CheckListCustodianStatus(null, null, null,
			// ICMSConstant.STATE_RECEIVED, "", null);
			// checkListCustodianStatuses[3] = new
			// CheckListCustodianStatus(null, null, null,
			// ICMSConstant.STATE_AUTHZ_RELODGED, null,
			// ICMSConstant.STATE_TEMP_UPLIFTED);
			// sc.setCheckListCustodianStatus(checkListCustodianStatuses);
			sc.setIsInVaultInd(Boolean.TRUE);
			sc.setIsPrintLodgement(true);
			sc.setIsDocItemNarrationRequired(true);

			if (aTrxContext != null) {
				ITeam team = aTrxContext.getTeam();
				// DefaultLogger.debug(this,
				// "Team abbreviation="+team.getAbbreviation());
				String[] countryCodes = team.getCountryCodes();
				String[] orgCodes = team.getOrganisationCodes();

				/*
				 * if (countryCodes!=null) { for (int i=0;
				 * i<countryCodes.length; i++) DefaultLogger.debug(this,
				 * "Country code["+i+"]="+countryCodes[i]); } if
				 * (orgCodes!=null) { for (int i=0; i<orgCodes.length; i++)
				 * DefaultLogger.debug(this,
				 * "Organisation code["+i+"]="+orgCodes[i]); }
				 */

				sc.setCountryCodes(countryCodes);
				sc.setOrganisationCodes(orgCodes);
			}
			else {
				DefaultLogger.debug(this, "aTrxContext is null!");
			}

			SearchResult sr = getDocList(sc);
			return sr;
		}
		catch (CustodianException rex) {
			rex.printStackTrace();
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing
	 * @param aCustomerID - the custopmer ID
	 * @return SearchResult - the list of custodian doc that is pending
	 *         lodgement
	 * @throws CustodianException
	 */
	public SearchResult getPendingLodgementListForNonBorrower(long aCustomerID) throws CustodianException {

		return getPendingLodgementListForNonBorrower(null, aCustomerID);
	}

	/**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing
	 * @param aTrxContext - the transaction context
	 * @param aCustomerID - the custopmer ID
	 * @return SearchResult - the list of custodian doc that is pending
	 *         lodgement
	 * @throws CustodianException
	 */
	public SearchResult getPendingLodgementListForNonBorrower(ITrxContext aTrxContext, long aCustomerID)
			throws CustodianException {
		try {
			CustodianSearchCriteria sc = new CustodianSearchCriteria();
			sc.setSubProfileID(aCustomerID);
			sc.setDocSubType(ICMSConstant.CHECKLIST_NON_BORROWER);
			// String[] status = new String[]{ICMSConstant.STATE_RECEIVED,
			// ICMSConstant.STATE_AUTHZ_RELODGED};
			// String[] statusExclude = new String[]
			// {ICMSConstant.STATE_PENDING_LODGE, ICMSConstant.STATE_LODGED,
			// ICMSConstant.STATE_PENDING_RELODGE};
			// sc.setCPCCustodianStatus(status);
			// sc.setCPCCustodianStatusExclude(statusExclude);

			// bernard - added the following to display lodgement documents of
			// the following statuses
			// when constructing CheckListCustodianStatus, note the following:
			// 1. null will omit the field entirely
			// 2. empty string will translate into "IS NULL" for that field
			// 3. non-empty string will be equated to that field as a criteria
			// CheckListCustodianStatus[] checkListCustodianStatuses = new
			// CheckListCustodianStatus[4];
			// checkListCustodianStatuses[0] = new
			// CheckListCustodianStatus(ICMSConstant
			// .STATE_ITEM_PENDING_COMPLETE, null, null, null, null, null);
			// checkListCustodianStatuses[1] = new
			// CheckListCustodianStatus(null,
			// ICMSConstant.STATE_PENDING_AUTHZ_RELODGE, null, null, null,
			// ICMSConstant.STATE_TEMP_UPLIFTED);
			// checkListCustodianStatuses[2] = new
			// CheckListCustodianStatus(null, null, null,
			// ICMSConstant.STATE_RECEIVED, "", null);
			// checkListCustodianStatuses[3] = new
			// CheckListCustodianStatus(null, null, null,
			// ICMSConstant.STATE_AUTHZ_RELODGED, null,
			// ICMSConstant.STATE_TEMP_UPLIFTED);
			// sc.setCheckListCustodianStatus(checkListCustodianStatuses);
			sc.setIsInVaultInd(Boolean.TRUE);
			sc.setIsPrintLodgement(true);
			sc.setIsDocItemNarrationRequired(true);

			if (aTrxContext != null) {
				ITeam team = aTrxContext.getTeam();
				// DefaultLogger.debug(this,
				// "Team abbreviation="+team.getAbbreviation());
				String[] countryCodes = team.getCountryCodes();
				String[] orgCodes = team.getOrganisationCodes();

				/*
				 * if (countryCodes!=null) { for (int i=0;
				 * i<countryCodes.length; i++) DefaultLogger.debug(this,
				 * "Country code["+i+"]="+countryCodes[i]); } if
				 * (orgCodes!=null) { for (int i=0; i<orgCodes.length; i++)
				 * DefaultLogger.debug(this,
				 * "Organisation code["+i+"]="+orgCodes[i]); }
				 */

				sc.setCountryCodes(countryCodes);
				sc.setOrganisationCodes(orgCodes);
			}
			else {
				DefaultLogger.debug(this, "aTrxContext is null!");
			}

			SearchResult sr = getDocList(sc);
			return sr;
		}
		catch (CustodianException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing This will take into consideration those deleted checklist
	 * as well
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return SearchResult - the list of custodian docs that are pending
	 *         lodgement
	 * @throws CustodianException on errors
	 */
	public SearchResult getPendingLodgementListForNonBorrower(long aLimitProfileID, long aCustomerID)
			throws CustodianException {
		return getPendingLodgementListForNonBorrower(null, aLimitProfileID, aCustomerID);
	}

	/**
	 * Get the list of custodian doc that are pending lodgement for lodgement
	 * memo printing This will take into consideration those deleted checklist
	 * as well
	 * @param aTrxContext the transaction context
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return SearchResult - the list of custodian docs that are pending
	 *         lodgement
	 * @throws CustodianException on errors
	 */
	public SearchResult getPendingLodgementListForNonBorrower(ITrxContext aTrxContext, long aLimitProfileID,
			long aCustomerID) throws CustodianException {
		try {
			CustodianSearchCriteria sc = new CustodianSearchCriteria();
			sc.setLimitProfileID(aLimitProfileID);
			sc.setSubProfileID(aCustomerID);
			sc.setDocSubType(ICMSConstant.CHECKLIST_NON_BORROWER);
			// String[] status = new String[]{ICMSConstant.STATE_RECEIVED,
			// ICMSConstant.STATE_AUTHZ_RELODGED};
			// String[] statusExclude = new String[]
			// {ICMSConstant.STATE_PENDING_LODGE, ICMSConstant.STATE_LODGED,
			// ICMSConstant.STATE_PENDING_RELODGE};
			// sc.setCPCCustodianStatus(status);
			// sc.setCPCCustodianStatusExclude(statusExclude);

			// bernard - added the following to display lodgement documents of
			// the following statuses
			// when constructing CheckListCustodianStatus, note the following:
			// 1. null will omit the field entirely
			// 2. empty string will translate into "IS NULL" for that field
			// 3. non-empty string will be equated to that field as a criteria
			// CheckListCustodianStatus[] checkListCustodianStatuses = new
			// CheckListCustodianStatus[4];
			// checkListCustodianStatuses[0] = new
			// CheckListCustodianStatus(ICMSConstant
			// .STATE_ITEM_PENDING_COMPLETE, null, null, null, null, null);
			// checkListCustodianStatuses[1] = new
			// CheckListCustodianStatus(null,
			// ICMSConstant.STATE_PENDING_AUTHZ_RELODGE, null, null, null,
			// ICMSConstant.STATE_TEMP_UPLIFTED);
			// checkListCustodianStatuses[2] = new
			// CheckListCustodianStatus(null, null, null,
			// ICMSConstant.STATE_RECEIVED, "", null);
			// checkListCustodianStatuses[3] = new
			// CheckListCustodianStatus(null, null, null,
			// ICMSConstant.STATE_AUTHZ_RELODGED, null,
			// ICMSConstant.STATE_TEMP_UPLIFTED);
			// sc.setCheckListCustodianStatus(checkListCustodianStatuses);
			sc.setIsInVaultInd(Boolean.TRUE);
			sc.setIsPrintLodgement(true);
			sc.setIsDocItemNarrationRequired(true);

			sc.setForMemoPrinting(true);
			if (aTrxContext != null) {
				ITeam team = aTrxContext.getTeam();
				// DefaultLogger.debug(this,
				// "Team abbreviation="+team.getAbbreviation());
				String[] countryCodes = team.getCountryCodes();
				String[] orgCodes = team.getOrganisationCodes();

				/*
				 * if (countryCodes!=null) { for (int i=0;
				 * i<countryCodes.length; i++) DefaultLogger.debug(this,
				 * "Country code["+i+"]="+countryCodes[i]); } if
				 * (orgCodes!=null) { for (int i=0; i<orgCodes.length; i++)
				 * DefaultLogger.debug(this,
				 * "Organisation code["+i+"]="+orgCodes[i]); }
				 */

				sc.setCountryCodes(countryCodes);
				sc.setOrganisationCodes(orgCodes);
			}
			else {
				DefaultLogger.debug(this, "aTrxContext is null!");
			}

			SearchResult sr = getDocList(sc);
			return sr;
		}
		catch (CustodianException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Get the list of custodian doc that are pending withdrawal for withdrawal
	 * memo printing
	 * @param aLimitProfileID the limit profile identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException
	 */
	public SearchResult getPendingWithdrawalList(long aLimitProfileID) throws CustodianException {

		return getPendingWithdrawalList(null, aLimitProfileID);
	}

	/**
	 * Get the list of custodian doc that are pending withdrawal for withdrawal
	 * memo printing
	 * @param aTrxContext the transaction context
	 * @param aLimitProfileID the limit profile identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException
	 */
	public SearchResult getPendingWithdrawalList(ITrxContext aTrxContext, long aLimitProfileID)
			throws CustodianException {
		try {
			CustodianSearchCriteria sc = new CustodianSearchCriteria();
			sc.setLimitProfileID(aLimitProfileID);
			// String[] status = new
			// String[]{ICMSConstant.STATE_AUTHZ_TEMP_UPLIFTED
			// ,ICMSConstant.STATE_AUTHZ_PERM_UPLIFTED};
			// String[] statusExclude = new String[]
			// {ICMSConstant.STATE_PENDING_TEMP_UPLIFT,
			// ICMSConstant.STATE_TEMP_UPLIFTED,
			// ICMSConstant.STATE_PENDING_PERM_UPLIFT,
			// ICMSConstant.STATE_PERM_UPLIFTED};
			// sc.setCPCCustodianStatus(status);
			// sc.setCPCCustodianStatusExclude(statusExclude);

			// bernard - added the following to display withdrawal documents of
			// the following statuses
			// when constructing CheckListCustodianStatus, note the following:
			// 1. null will omit the field entirely
			// 2. empty string will translate into "IS NULL" for that field
			// 3. non-empty string will be equated to that field as a criteria
			// CheckListCustodianStatus[] checkListCustodianStatuses = new
			// CheckListCustodianStatus[4];
			// checkListCustodianStatuses[0] = new
			// CheckListCustodianStatus(null,
			// ICMSConstant.STATE_PENDING_AUTHZ_TEMP_UPLIFT, null, null, null,
			// ICMSConstant.STATE_LODGED);
			// checkListCustodianStatuses[1] = new
			// CheckListCustodianStatus(null,
			// ICMSConstant.STATE_PENDING_AUTHZ_PERM_UPLIFT, null, null, null,
			// ICMSConstant.STATE_LODGED);
			// checkListCustodianStatuses[2] = new
			// CheckListCustodianStatus(null, null, null,
			// ICMSConstant.STATE_AUTHZ_TEMP_UPLIFTED, null,
			// ICMSConstant.STATE_LODGED);
			// checkListCustodianStatuses[3] = new
			// CheckListCustodianStatus(null, null, null,
			// ICMSConstant.STATE_AUTHZ_PERM_UPLIFTED, null,
			// ICMSConstant.STATE_LODGED);
			// sc.setCheckListCustodianStatus(checkListCustodianStatuses);
			sc.setIsInVaultInd(Boolean.TRUE);
			sc.setIsPrintWithdrawal(true);
			sc.setIsDocItemNarrationRequired(true);

			if (aTrxContext != null) {
				ITeam team = aTrxContext.getTeam();
				// DefaultLogger.debug(this,
				// "Team abbreviation="+team.getAbbreviation());
				String[] countryCodes = team.getCountryCodes();
				String[] orgCodes = team.getOrganisationCodes();

				/*
				 * if (countryCodes!=null) { for (int i=0;
				 * i<countryCodes.length; i++) DefaultLogger.debug(this,
				 * "Country code["+i+"]="+countryCodes[i]); } if
				 * (orgCodes!=null) { for (int i=0; i<orgCodes.length; i++)
				 * DefaultLogger.debug(this,
				 * "Organisation code["+i+"]="+orgCodes[i]); }
				 */

				sc.setCountryCodes(countryCodes);
				sc.setOrganisationCodes(orgCodes);
			}
			else {
				DefaultLogger.debug(this, "aTrxContext is null!");
			}

			SearchResult sr = getDocList(sc);
			return sr;
		}
		catch (CustodianException rex) {
			throw new CustodianException(rex.toString());
		}
	}

    
    /**
	 * Get the list of custodian doc that are pending withdrawal for withdrawal
	 * memo printing
	 * @param aCustomerID the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException
	 */
	public SearchResult getPendingWithdrawalListForNonBorrower(long aCustomerID) throws CustodianException {

		return getPendingWithdrawalListForNonBorrower(null, aCustomerID);
	}

	/**
	 * Get the list of custodian doc that are pending withdrawal for withdrawal
	 * memo printing
	 * @param aTrxContext the transaction context
	 * @param aCustomerID the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException
	 */
	public SearchResult getPendingWithdrawalListForNonBorrower(ITrxContext aTrxContext, long aCustomerID)
			throws CustodianException {
		try {
			CustodianSearchCriteria sc = new CustodianSearchCriteria();
			sc.setSubProfileID(aCustomerID);
			sc.setDocSubType(ICMSConstant.CHECKLIST_NON_BORROWER);
			// String[] status = new
			// String[]{ICMSConstant.STATE_AUTHZ_TEMP_UPLIFTED
			// ,ICMSConstant.STATE_AUTHZ_PERM_UPLIFTED};
			// String[] statusExclude = new String[]
			// {ICMSConstant.STATE_PENDING_TEMP_UPLIFT,
			// ICMSConstant.STATE_TEMP_UPLIFTED,
			// ICMSConstant.STATE_PENDING_PERM_UPLIFT,
			// ICMSConstant.STATE_PERM_UPLIFTED};
			// sc.setCPCCustodianStatus(status);
			// sc.setCPCCustodianStatusExclude(statusExclude);

			// bernard - added the following to display withdrawal documents of
			// the following statuses
			// when constructing CheckListCustodianStatus, note the following:
			// 1. null will omit the field entirely
			// 2. empty string will translate into "IS NULL" for that field
			// 3. non-empty string will be equated to that field as a criteria
			// CheckListCustodianStatus[] checkListCustodianStatuses = new
			// CheckListCustodianStatus[4];
			// checkListCustodianStatuses[0] = new
			// CheckListCustodianStatus(null,
			// ICMSConstant.STATE_PENDING_AUTHZ_TEMP_UPLIFT, null, null, null,
			// ICMSConstant.STATE_LODGED);
			// checkListCustodianStatuses[1] = new
			// CheckListCustodianStatus(null,
			// ICMSConstant.STATE_PENDING_AUTHZ_PERM_UPLIFT, null, null, null,
			// ICMSConstant.STATE_LODGED);
			// checkListCustodianStatuses[2] = new
			// CheckListCustodianStatus(null, null, null,
			// ICMSConstant.STATE_AUTHZ_TEMP_UPLIFTED, null,
			// ICMSConstant.STATE_LODGED);
			// checkListCustodianStatuses[3] = new
			// CheckListCustodianStatus(null, null, null,
			// ICMSConstant.STATE_AUTHZ_PERM_UPLIFTED, null,
			// ICMSConstant.STATE_LODGED);
			// sc.setCheckListCustodianStatus(checkListCustodianStatuses);
			sc.setIsInVaultInd(Boolean.TRUE);
			sc.setIsPrintWithdrawal(true);
			sc.setIsDocItemNarrationRequired(true);

			if (aTrxContext != null) {
				ITeam team = aTrxContext.getTeam();
				// DefaultLogger.debug(this,
				// "Team abbreviation="+team.getAbbreviation());
				String[] countryCodes = team.getCountryCodes();
				String[] orgCodes = team.getOrganisationCodes();

				/*
				 * if (countryCodes!=null) { for (int i=0;
				 * i<countryCodes.length; i++) DefaultLogger.debug(this,
				 * "Country code["+i+"]="+countryCodes[i]); } if
				 * (orgCodes!=null) { for (int i=0; i<orgCodes.length; i++)
				 * DefaultLogger.debug(this,
				 * "Organisation code["+i+"]="+orgCodes[i]); }
				 */

				sc.setCountryCodes(countryCodes);
				sc.setOrganisationCodes(orgCodes);
			}
			else {
				DefaultLogger.debug(this, "aTrxContext is null!");
			}

			SearchResult sr = getDocList(sc);
			return sr;
		}
		catch (CustodianException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Get the list of custodian doc that are pending withdrawal memo printing
	 * This will take into consideration those custodian doc belonging to
	 * deleted checklists as well
	 * @param aLimitProfileID the limit profile identifier
	 * @param aCustomerID the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException on errors
	 */
	public SearchResult getPendingWithdrawalListForNonBorrower(long aLimitProfileID, long aCustomerID)
			throws CustodianException {
		return getPendingWithdrawalListForNonBorrower(null, aLimitProfileID, aCustomerID);
	}

	/**
	 * Get the list of custodian doc that are pending withdrawal memo printing
	 * This will take into consideration those custodian doc belonging to
	 * deleted checklists as well
	 * @param aTrxContext the transaction context
	 * @param aLimitProfileID the limit profile identifier
	 * @param aCustomerID the customer identifier
	 * @return SearchResult - the list of custodian doc that is pending
	 *         withdrawal
	 * @throws CustodianException on errors
	 */
	public SearchResult getPendingWithdrawalListForNonBorrower(ITrxContext aTrxContext, long aLimitProfileID,
			long aCustomerID) throws CustodianException {
		try {
			CustodianSearchCriteria sc = new CustodianSearchCriteria();
			sc.setLimitProfileID(aLimitProfileID);
			sc.setSubProfileID(aCustomerID);
			sc.setDocSubType(ICMSConstant.CHECKLIST_NON_BORROWER);
			// String[] status = new
			// String[]{ICMSConstant.STATE_AUTHZ_TEMP_UPLIFTED
			// ,ICMSConstant.STATE_AUTHZ_PERM_UPLIFTED};
			// String[] statusExclude = new String[]
			// {ICMSConstant.STATE_PENDING_TEMP_UPLIFT,
			// ICMSConstant.STATE_TEMP_UPLIFTED,
			// ICMSConstant.STATE_PENDING_PERM_UPLIFT,
			// ICMSConstant.STATE_PERM_UPLIFTED};
			// sc.setCPCCustodianStatus(status);
			// sc.setCPCCustodianStatusExclude(statusExclude);

			// bernard - added the following to display withdrawal documents of
			// the following statuses
			// when constructing CheckListCustodianStatus, note the following:
			// 1. null will omit the field entirely
			// 2. empty string will translate into "IS NULL" for that field
			// 3. non-empty string will be equated to that field as a criteria
			// CheckListCustodianStatus[] checkListCustodianStatuses = new
			// CheckListCustodianStatus[4];
			// checkListCustodianStatuses[0] = new
			// CheckListCustodianStatus(null,
			// ICMSConstant.STATE_PENDING_AUTHZ_TEMP_UPLIFT, null, null, null,
			// ICMSConstant.STATE_LODGED);
			// checkListCustodianStatuses[1] = new
			// CheckListCustodianStatus(null,
			// ICMSConstant.STATE_PENDING_AUTHZ_PERM_UPLIFT, null, null, null,
			// ICMSConstant.STATE_LODGED);
			// checkListCustodianStatuses[2] = new
			// CheckListCustodianStatus(null, null, null,
			// ICMSConstant.STATE_AUTHZ_TEMP_UPLIFTED, null,
			// ICMSConstant.STATE_LODGED);
			// checkListCustodianStatuses[3] = new
			// CheckListCustodianStatus(null, null, null,
			// ICMSConstant.STATE_AUTHZ_PERM_UPLIFTED, null,
			// ICMSConstant.STATE_LODGED);
			// sc.setCheckListCustodianStatus(checkListCustodianStatuses);
			sc.setIsInVaultInd(Boolean.TRUE);
			sc.setIsPrintWithdrawal(true);
			sc.setIsDocItemNarrationRequired(true);

			sc.setForMemoPrinting(true);
			if (aTrxContext != null) {
				ITeam team = aTrxContext.getTeam();
				// DefaultLogger.debug(this,
				// "Team abbreviation="+team.getAbbreviation());
				String[] countryCodes = team.getCountryCodes();
				String[] orgCodes = team.getOrganisationCodes();

				/*
				 * if (countryCodes!=null) { for (int i=0;
				 * i<countryCodes.length; i++) DefaultLogger.debug(this,
				 * "Country code["+i+"]="+countryCodes[i]); } if
				 * (orgCodes!=null) { for (int i=0; i<orgCodes.length; i++)
				 * DefaultLogger.debug(this,
				 * "Organisation code["+i+"]="+orgCodes[i]); }
				 */

				sc.setCountryCodes(countryCodes);
				sc.setOrganisationCodes(orgCodes);
			}
			else {
				DefaultLogger.debug(this, "aTrxContext is null!");
			}

			SearchResult sr = getDocList(sc);
			return sr;
		}
		catch (CustodianException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * To get the custodian document using the custodian document ID
	 * @param aCustodianDocID - long
	 * @return OBCustodianDoc - the object containing the custodian document
	 *         info
	 * @throws CustodianException
	 */

	public ICustodianDoc getDocByID(long aCustodianDocID) throws CustodianException {
		try {
			ICustodianDoc doc = getCustodianBusManager().getCustodianDoc(aCustodianDocID);
			return doc;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Get the list of custodian document that satisfy the criteria
	 * @param aCustodianSearchCriteria - CustodianSearchCriteria
	 * @return SearchResult - the search result containing the list of custodian
	 *         doc
	 * @throws CustodianException
	 */
	public SearchResult getDocList(CustodianSearchCriteria aCustodianSearchCriteria) throws CustodianException {
		try {
			return getCustodianBusManager().getCustodianDocList(aCustodianSearchCriteria);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

    /**
	 * Get boolean to check whether envelope barcode exist
	 * @param long - limitprofile
     * @param String - envelope barcode
	 * @return boolean - return true/false on whether envelope barcode exist
	 * @throws CustodianException
	 */
	public boolean getCheckEnvelopeBarcodeExist(long limitprofile, String envBarcode) throws CustodianException {
		try {
			return getCustodianBusManager().getCheckEnvelopeBarcodeExist(limitprofile, envBarcode);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

   /**
	 * Get boolean to check whether document item barcode is unique
	 * @param String - document item barcode
	 * @return boolean - returning true/false on whether document item barcode exist
	 * @throws CustodianException
	 */
	public boolean getCheckDocItemBarcodeExist(String docItemBarcode, long checkListItemRefID) throws CustodianException {
		try {
			return getCustodianBusManager().getCheckDocItemBarcodeExist(docItemBarcode, checkListItemRefID);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

    /**
	 * Get ISecEnvelopeItem to with location base on barcode
	 * @param String - document item barcode
	 * @return ISecEnvelopeItem - detail of OBSecEnvelopeItem
	 * @throws CustodianException
	 */
    public ISecEnvelopeItem getSecEnvItemLoc(String docItemBarcode) throws CustodianException {
		try {
			return getCustodianBusManager().getSecEnvItemLoc(docItemBarcode);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Persist custodian print authorization details...
	 * @param custAuthz - OBCustAuthorize[]
	 * @throws CustodianException if errors
	 */
	public void persistPrintAuthzDetails(OBCustAuthorize[] custAuthz) throws CustodianException {
		try {
			getCustodianBusManager().persistPrintAuthzDetails(custAuthz);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Persist custodian print authorization details...
	 * @param customer - ICMSCustomer
	 * @param checkListMap - HashMap with key as CheckListID and value as list
	 *        of CheckListItemRef for this checklist
	 * @return IMemo
	 * @throws CustodianException if errors
	 */
	public IMemo getPrintMemo(ICMSCustomer customer, HashMap checkListMap) throws CustodianException {

		if (checkListMap == null) {
			throw new CustodianException("Invalid input params: Null HashMap");
		}

		// get all required custodian doc with filtered list of items
		ICustodianDoc[] custDocs = new ICustodianDoc[checkListMap.size()];
		Iterator checkListIDs = checkListMap.keySet().iterator();
		for (int i = 0; checkListIDs.hasNext(); i++) {
			String checkListID = (String) checkListIDs.next();
			List wantedItemRefIDs = (List) checkListMap.get(checkListID);
			custDocs[i] = getPrintMemoCustodianDocWithFilteredItems(Long.parseLong(checkListID), wantedItemRefIDs);
		}

		// create memo
		IMemo memo = createMemo(customer, custDocs);

		return memo;
	}

	/**
	 * Helper method to get custodian doc with wanted items specified in the
	 * wantedItemRefIDs list.
	 * @param checkListID - long
	 * @param wantedItemRefIDs - List of wanted item ref IDs
	 * @return ICustodianDoc
	 * @throws CustodianException if error occurs
	 */
	private ICustodianDoc getPrintMemoCustodianDocWithFilteredItems(long checkListID, List wantedItemRefIDs)
			throws CustodianException {
		ICustodianDoc custDoc = null;
		// get existing actual doc
		try {
			custDoc = getCustodianDocByCheckList(checkListID);
		}catch(FinderException ex){
            DefaultLogger.debug(this, "No existing custodian doc found for checklist ID : " + checkListID);
        }catch (CustodianException ex) {
			DefaultLogger.debug(this, "Exception in getCustodianDocByCheckList");
		}
        
		// get staging otherwise
		if (custDoc == null) {
			custDoc = getNewDocTrxValue(checkListID).getStagingCustodianDoc();
			filterOutUnwantedItem(custDoc, wantedItemRefIDs);
		}

		return custDoc;
	}

	/**
	 * Helper method to filter out unwanted items.
	 * @param custodianDoc - ICustodianDoc
	 * @param wantedItemRefIDs - List
	 */
	private void filterOutUnwantedItem(ICustodianDoc custodianDoc, List wantedItemRefIDs) {

		if ((custodianDoc == null) || (wantedItemRefIDs == null) || (wantedItemRefIDs.size() == 0)) {
			return;
		}

		ArrayList wantedItems = new ArrayList(wantedItemRefIDs.size());
		Iterator allItems = custodianDoc.getCustodianDocItems().iterator();

		// find wanted items
		Iterator wantedIDs = wantedItemRefIDs.iterator();
		while (wantedIDs.hasNext()) {
			long wantedID = Long.parseLong((String) wantedIDs.next());
			while (allItems.hasNext()) {
				ICustodianDocItem item = (ICustodianDocItem) allItems.next();
				long itemRef = item.getCheckListItemRefID();
				if (itemRef == wantedID) {
					wantedItems.add(item);
					break;
				}
			}
		}

		// set wanted items
		custodianDoc.setCustodianDocItems(wantedItems);
	}

	/**
	 * Helper method to create a memo
	 * @param customer - ICMSCustomer
	 * @param custodianDoc - ICustodianDoc
	 */
	private IMemo createMemo(ICMSCustomer customer, ICustodianDoc[] custDocs) {
		String famCode = getFamCode(customer);
		OBMemo memo = new OBMemo();
		memo.setCustodianDocList(custDocs);
		memo.setCustomer(customer);
		memo.setMemoDate(new Date());
		memo.setMemoFrom("xxx");// todo get from const file
		memo.setMemoTo("xxx");// todo get from const file
		memo.setMemoType("xxx");// todo get from const file
		memo.setMemoReference("xxx");// todo get from const file
		memo.setMemoSubject("xxx");// todo get from const file
		memo.setFAMCode(famCode);
		return memo;
	}

	/**
	 * Helper method to get fam code for a customer
	 * @param customer - ICMSCustomer
	 * @return String
	 */
	private String getFamCode(ICMSCustomer customer) {
		String famCode = null;
		try {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			HashMap famNameMap = limitProxy.getFAMNameByCustomer(customer.getCustomerID());
			famCode = (famNameMap != null) ? (String) famNameMap.get(ICMSConstant.FAM_CODE) : null;
		}
		catch (LimitException le) {
			DefaultLogger.debug(this, "Error occurred while getting fam code for customer : "
					+ customer.getCustomerID());
		}
		return famCode;
	}

	/**
	 * Get the list of custodian doc by collateral ID
	 * @param aCollateralID - long
	 * @return ICustodianDoc[] - the list of custodian doc. Null if no custodian
	 *         doc found
	 * @throws CustodianException on errors
	 */
	public ICustodianDoc[] getCustodianDoc(long aCollateralID) throws CustodianException {
		try {
			return getCustodianBusManager().getCustodianDocsByCollateralID(aCollateralID);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Get the custodian doc by checklist item ID
	 * @param aCheckListID of long type
	 * @return ICustodianDoc - the custodian doc with the checklist ID
	 * @throws CustodianException on errors
	 */
	public ICustodianDoc getCustodianDocByCheckList(long aCheckListID) throws CustodianException, FinderException {
		try {
			return getCustodianBusManager().getCustodianDocByCheckList(aCheckListID);
		}catch(FinderException ex){
            return null;
        }catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/*
	 * public ICustodianTrxValue getNewCustodianDoc(long aCheckListItemID)
	 * throws CustodianException { try { ICustodianTrxValue trxValue = new
	 * OBCustodianTrxValue(); ICustodianDoc doc =
	 * getCustodianBusManager().getNewCustodianDoc(aCheckListItemID);
	 * ICheckListProxyManager proxy =
	 * CheckListProxyManagerFactory.getCheckListProxyManager(); ICheckListItem
	 * item = proxy.getCheckListItem(aCheckListItemID);
	 * doc.setCheckListItem(item); trxValue.setStagingCustodianDoc(doc);
	 * trxValue.setCustodianDoc(doc); return trxValue; }
	 * catch(CheckListException ex) { throw new
	 * CustodianException("Caught exception in getNewCustodianDoc with ItemID "
	 * + aCheckListItemID, ex); } catch (RemoteException rex) { throw new
	 * CustodianException(rex.toString()); } }
	 */

	public ICustodianDoc setOwnerInfo(ICustodianDoc anICustodianDoc) throws CustodianException {
		try {
			return getCustodianBusManager().setOwnerInfo(anICustodianDoc);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Get the custodian trx value given a checklist ID with no custodian items
	 * created.
	 * 
	 * @param checkListID - long
	 * @return ICustodianTrxValue
	 * @throws CustodianException
	 */
	public ICustodianTrxValue getNewDocTrxValue(long checkListID) throws CustodianException {
		try {
			DefaultLogger.debug(this, "getNewDocTrxValue - checkListID : " + checkListID);
			ICustodianTrxValue trxValue = new OBCustodianTrxValue();
			ICustodianDoc doc = getStagingCustodianBusManager().getNewDoc(checkListID);
			if (doc != null) {
				trxValue.setStagingCustodianDoc(doc);
				trxValue.setCustodianDoc((ICustodianDoc) AccessorUtil.deepClone(doc));
			}
			return trxValue;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
		catch (Exception ex) {
			throw new CustodianException(ex.toString());
		}
	}

	/**
	 * Get the custodian trx ID given a set of search criteria
	 * 
	 * @param searchCriteria - CustodianSearchCriteria
	 * @return long - the custodian trx ID
	 * @return ICMSConstant.LONG_INVALID_VALUE if no such custodian trx found.
	 * @throws CustodianException
	 */
	public long getTrxID(CustodianSearchCriteria searchCriteria) throws CustodianException {
		try {
			return getCustodianBusManager().getTrxID(searchCriteria);
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * update transaction
	 */
	public void updateTransaction(ITrxValue trxValue) throws CustodianException {
		ICheckListTrxValue checkListTrxValue = (ICheckListTrxValue) trxValue;
		ICheckList checkList = checkListTrxValue.getCheckList();
		CustodianSearchCriteria criteria = new CustodianSearchCriteria();
		criteria.setCheckListID(checkList.getCheckListID());
		long transactionID = ICMSConstant.LONG_INVALID_VALUE;
		try {

			transactionID = getCustodianBusManager().getTrxID(criteria);
			if (transactionID == ICMSConstant.LONG_INVALID_VALUE) {
				DefaultLogger.debug(this,
						"----------------------------------- create custodian transaction ------------------------- ");
				createRequiredCustodianTransaction(checkListTrxValue);
			}
			else {
				DefaultLogger.debug(this, "transactionID: -------------------------------------- " + transactionID);
				ICustodianTrxValue value = super.getDocByTrxID(String.valueOf(transactionID));
				String status = value.getStatus();
				if (ICMSConstant.STATE_PENDING_CREATE.equals(status)
						|| ICMSConstant.STATE_PENDING_UPDATE.equals(status)) {
					DefaultLogger.debug(this,
							"------------------------------- send notification ------------------------------------- ");
					sendNotification(checkListTrxValue);
				}
				else if (ICMSConstant.STATE_ACTIVE.equals(status)) {
					DefaultLogger.debug(this,
							"------------------------------- update custodian transaction -------------------------- ");
					updateRequiredCustodianTransaction(checkListTrxValue, value);
				}
				else if (ICMSConstant.STATE_DRAFT.equals(status) || ICMSConstant.STATE_REJECTED.equals(status)
						|| ICMSConstant.STATE_REQUIRED.equals(status)) {
					DefaultLogger.debug(this,
							"------------------------------- do nothing ------------------------------- ");
				}
			}
		}
		catch (RemoteException e) {
			throw new CustodianException(e);
		}
	}

	/**
	 * update required custodian transaction
	 * @param trxValue
	 * @throws CustodianException
	 */
	private void updateRequiredCustodianTransaction(ICheckListTrxValue checkListTrxValue, ICustodianTrxValue trxValue)
			throws CustodianException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_REQUIRED);
		checkListTrxValue = this.setTeam(checkListTrxValue);
		trxValue.setTrxContext(checkListTrxValue.getTrxContext());
		super.operate(trxValue, param);
	}

	/**
	 * set team for custodian
	 * @param trxValue
	 * @throws CustodianException
	 */
	private ICheckListTrxValue setTeam(ICheckListTrxValue trxValue) throws CustodianException {
		// set team
		ITrxContext trxContext = trxValue.getTrxContext();
		ITeam team = trxContext.getTeam();
		ITeam[] teams = this.getTeams();
		if (team != null) {
			if ((teams != null) && (teams.length != 0)) {
				team.setTeamID(teams[0].getTeamID());
			}
			OBTeamType teamType = (OBTeamType) team.getTeamType();
			if (teamType != null) {
				teamType.setTeamTypeID(ICMSConstant.TEAM_TYPE_CPCCUSTODIAN);
			}
		}
		return trxValue;
	}

	/**
	 * create custodian transaction
	 * @param trxValue
	 */
	private void createRequiredCustodianTransaction(ICheckListTrxValue trxValue) throws CustodianException {
		// set action
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_REQUIRED);
		// set team
		trxValue = this.setTeam(trxValue);
		// set staging custodian doc
		ITrxContext trxContext = trxValue.getTrxContext();
		ICustodianDoc stagingCustDoc = new OBCustodianDoc();
		ICMSCustomer customer = trxContext.getCustomer();
		if (customer != null) {
			stagingCustDoc.setSubProfileID(customer.getCustomerID());
		}
		ICheckList checkList = trxValue.getCheckList();
		if (checkList.getCheckListType().equals(ICMSConstant.DOC_TYPE_CC)) {
			OBCCCheckListOwner checkListOwner = (OBCCCheckListOwner) checkList.getCheckListOwner();
			stagingCustDoc.setCollateralID(ICMSConstant.LONG_INVALID_VALUE);
			stagingCustDoc.setDocSubType(checkListOwner.getSubOwnerType());
			stagingCustDoc.setLimitProfileID(checkListOwner.getLimitProfileID());
			stagingCustDoc.setPledgorID(checkListOwner.getSubOwnerID());
			stagingCustDoc.setSubProfileID(checkListOwner.getSubOwnerID());
		}
		else if (checkList.getCheckListType().equals(ICMSConstant.DOC_TYPE_SECURITY)) {
			OBCollateralCheckListOwner checkListOwner = (OBCollateralCheckListOwner) checkList.getCheckListOwner();
			stagingCustDoc.setCollateralID(checkListOwner.getCollateralID());
			stagingCustDoc.setDocSubType(checkListOwner.getSubOwnerType());
			stagingCustDoc.setLimitProfileID(checkListOwner.getLimitProfileID());
			stagingCustDoc.setPledgorID(ICMSConstant.LONG_INVALID_VALUE);
			stagingCustDoc.setSubProfileID(ICMSConstant.LONG_INVALID_VALUE);
		}
		stagingCustDoc.setCheckListID(checkList.getCheckListID());
		stagingCustDoc.setDocType(checkList.getCheckListType());

		// set custodian trx value

		ICustodianTrxValue custValue = new OBCustodianTrxValue();
		if (trxValue.getTransactionSubType().equals(ICMSConstant.TRX_TYPE_CC_CHECKLIST)
				|| trxValue.getTransactionSubType().equals(ICMSConstant.TRX_TYPE_CC_CHECKLIST_RECEIPT)) {
			custValue.setTransactionSubType(ICMSConstant.TRX_TYPE_CC_CUSTODIAN);
		}
		else if (trxValue.getTransactionSubType().equals(ICMSConstant.TRX_TYPE_COL_CHECKLIST)
				|| trxValue.getTransactionSubType().equals(ICMSConstant.TRX_TYPE_COL_CHECKLIST_RECEIPT)) {
			custValue.setTransactionSubType(ICMSConstant.TRX_TYPE_COL_CUSTODIAN);
		}
		else if (trxValue.getTransactionSubType().equals(ICMSConstant.TRX_TYPE_DELETE_CC_CHECKLIST_RECEIPT)) {
			custValue.setTransactionSubType(ICMSConstant.TRX_TYPE_DELETED_CC_CUSTODIAN);
		}
		else if (trxValue.getTransactionSubType().equals(ICMSConstant.TRX_TYPE_DELETE_COL_CHECKLIST_RECEIPT)) {
			custValue.setTransactionSubType(ICMSConstant.TRX_TYPE_DELETED_COL_CUSTODIAN);
		}

		custValue.setTransactionType(ICMSConstant.INSTANCE_CUSTODIAN);
		custValue.setStatus(ICMSConstant.STATE_ND);
		custValue.setFromState(ICMSConstant.STATE_ND);
		custValue.setStagingCustodianDoc(stagingCustDoc);
		custValue.setTrxContext(trxValue.getTrxContext());
		super.operate(custValue, param);
	}

	/**
	 * send notification
	 * 
	 */
	private void sendNotification(ICheckListTrxValue trxValue) throws CustodianException {
		try {
			ITeam[] teams = this.getTeams();
			DefaultLogger.debug(this, "teams length: ------------------------------ " + teams.length);
			ICMSCustomer customer = trxValue.getTrxContext().getCustomer();
			String country = "";
			String org = "";
			String segment = "";
			ILimitProfile limitProfile = trxValue.getTrxContext().getLimitProfile();
			if (customer != null) {
				if (limitProfile.getLimitProfileID() > 0) {
					country = limitProfile.getOriginatingLocation().getCountryCode();
					org = limitProfile.getOriginatingLocation().getOrganisationCode();
				}
				else {
					country = customer.getOriginatingLocation().getCountryCode();
					org = customer.getOriginatingLocation().getOrganisationCode();
				}
				ICMSLegalEntity legal = customer.getCMSLegalEntity();
				if (legal != null) {
					segment = legal.getCustomerSegment();
				}
			}
			DefaultLogger.debug(this, "country code: ---------------------- " + country);
			DefaultLogger.debug(this, "org code: -------------------------- " + org);
			DefaultLogger.debug(this, "segment: --------------------------- " + segment);
			ArrayList selectedTeamList = filterTeams(teams, country, org, segment);
			if (selectedTeamList.size() != 0) {
				ArrayList paramList = new ArrayList();
				// set custodian event info
				ICheckList checkList = trxValue.getCheckList();
				CheckListDAO checkListDAO = new CheckListDAO();
				HashMap paramMap = checkListDAO.getLeParams(checkList.getCheckListID());
				String category = (String) paramMap.get("category");
				String subCategory = (String) paramMap.get("sub_category");
				String leID = (String) paramMap.get("le_id");
				String leName = (String) paramMap.get("le_name");
				String pledgorID = (String) paramMap.get("pledgor_id");
				String pledgorName = (String) paramMap.get("pledgor_name");
				OBUpdateCustodian cust = new OBUpdateCustodian();
				if (category.equals(ICMSConstant.DOC_TYPE_CC) && (subCategory != null)
						&& subCategory.equals(ICMSConstant.CHECKLIST_PLEDGER)) {
					cust.setLeID(pledgorID);
					cust.setLeName(pledgorName);
				}
				else {
					cust.setLeID(leID);
					cust.setLeName(leName);
				}
				cust.setOriginatingCountry(country);
				cust.setOriginatingOrganisation(org);
				cust.setSegment(segment);
				if (checkList.getCheckListType().equals(ICMSConstant.DOC_TYPE_CC)) {
					cust.setCategory("CC Checklist");
				}
				else if (checkList.getCheckListType().equals(ICMSConstant.DOC_TYPE_SECURITY)) {
					ICollateralCheckListOwner checkListOwner = (ICollateralCheckListOwner) checkList
							.getCheckListOwner();
					String securityID = checkListOwner.getCollateralRef();
					cust.setCategory(securityID);
				}
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append("Lodgement/ Withdrawal Pending for ");
				strBuffer.append(cust.getLeID() + " (" + cust.getLeName() + ").<br>");
				strBuffer.append("This same checklist is currently held by Custodian Checker.<br>");
				strBuffer.append("Please perform via search for ");
				strBuffer.append(cust.getCategory() + " to process the transaction upon Custodian Checker's approval.");
				// strBuffer.append(
				// "Lodgement/Withdrawal Pending at Custodian Checker before Maker can process"
				// );
				// cust.setSubject(strBuffer.toString());
				cust.setMsgDetails(strBuffer.toString());
				paramList.add(cust);
				// set recepient list
				StdUserDAO dao = new StdUserDAO();
				ArrayList recipientList = dao.getCustodianMakerByTeamID(selectedTeamList);
				DefaultLogger.debug(this, "recepientList length: -------------------------------- "
						+ recipientList.size());
				paramList.add(recipientList);

				UpdateCustodianListener listener = new UpdateCustodianListener();
				listener.fireEvent(UpdateCustodianListener.EVENT_ID, paramList);
			}
		}
		catch (SearchDAOException e) {
			throw new CustodianException(e);
		}
		catch (EventHandlingException e) {
			throw new CustodianException(e);
		}
		catch (Exception e) {
			throw new CustodianException(e);
		}
	}

	/**
	 * get selected teams
	 * @param teams
	 * @return
	 */
	private ArrayList filterTeams(ITeam[] teams, String country, String org, String segment) {
		ArrayList selectedTeams = new ArrayList();
		if (teams != null) {
			for (int i = 0; i < teams.length; i++) {
				ITeam team = teams[i];
				if (isTeamSelected(team, country, org, segment)) {
					selectedTeams.add(team);
				}
			}
		}
		return selectedTeams;
	}

	/**
	 * filter team
	 * @param team
	 * @param country
	 * @param org
	 * @param segment
	 * @return
	 */
	protected boolean isTeamSelected(ITeam team, String country, String org, String segment) {
		String[] countryList = team.getCountryCodes();
		String[] orgList = team.getOrganisationCodes();
		String[] segmentList = team.getSegmentCodes();

		boolean foundCountry = false;
		boolean foundOrg = false;
		boolean foundSegment = false;

		// test country
		for (int i = 0; i < countryList.length; i++) {
			if (country.equals(countryList[i])) {
				foundCountry = true;
			}
		}
		if (false == foundCountry) {
			return false;
		}
		// then org
		for (int j = 0; j < orgList.length; j++) {
			if (org.equals(orgList[j])) {
				foundOrg = true;
			}
		}
		if (false == foundOrg) {
			return false;
		}
		// lastly test segment
		for (int k = 0; k < segmentList.length; k++) {
			if (segment.equals(segmentList[k])) {
				foundSegment = true;
			}
		}
		if (false == foundSegment) {
			return false;
		}
		// all is found, so return true
		return true;
	}

	/**
	 * get teams
	 * @return
	 * @throws CustodianException
	 */
	private ITeam[] getTeams() throws CustodianException {
		CMSTeamProxy proxy = new CMSTeamProxy();
		try {
			ITeam[] teams = proxy.getTeamsByTeamType(ICMSConstant.TEAM_TYPE_CPCCUSTODIAN);
			return teams;
		}
		catch (EntityNotFoundException e) {
			throw new CustodianException(e);
		}
	}

	/**
	 * To rollback a transaction
	 */
	protected void rollback() {
		_context.setRollbackOnly();
	}

	/**
	 * Helper method to return the custodian bus session bean
	 * 
	 * @return SBCustodianBusManager - the remote handler for tge custodian bus
	 *         manager session bean
	 * @throws CustodianException for any errors encountered
	 */
	private SBCustodianBusManager getCustodianBusManager() throws CustodianException {
		SBCustodianBusManager busmgr = (SBCustodianBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CUSTODIAN_BUS_JNDI, SBCustodianBusManagerHome.class.getName());
		if (busmgr == null) {
			throw new CustodianException("SBCustodianBusManager is null!");
		}
		return busmgr;
	}

    /**
	 * Helper method to return the custodian staging bus session bean
	 *
	 * @return SBCustodianBusManager - the remote handler for tge custodian bus
	 *         manager session bean
	 * @throws CustodianException for any errors encountered
	 */
	private SBCustodianBusManager getStagingCustodianBusManager() throws CustodianException {
		SBCustodianBusManager busmgr = (SBCustodianBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_CUSTODIAN_BUS_JNDI, SBCustodianBusManagerHome.class.getName());
		if (busmgr == null) {
			throw new CustodianException("SBStagingCustodianBusManager is null!");
		}
		return busmgr;
	}
}