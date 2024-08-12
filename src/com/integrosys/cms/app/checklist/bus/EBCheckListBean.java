/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBCheckListBean.java,v 1.83 2006/11/20 09:01:23 czhou Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.SortUtil;
import com.integrosys.cms.app.chktemplate.bus.DocumentHeldSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.custodian.bus.CustodianDAOFactory;
import com.integrosys.cms.app.custodian.bus.CustodianSearchCriteria;
import com.integrosys.cms.app.custodian.bus.EBCustodianDocHome;
import com.integrosys.cms.app.custodian.bus.ICustodianDocItem;
import com.integrosys.cms.app.custodian.bus.ICustodianDocItemSearchResult;
import com.integrosys.cms.app.custodian.bus.ICustodianDocSearchResult;

/**
 * Implementation for the checklist entity bean
 * 
 * @author $Author: Abhijit Rudrakshawar $<br>
 * @version $Revision: 1.83 $
 * @since $Date: 2006/11/20 09:01:23 $ Tag: $Name: $
 */

public abstract class EBCheckListBean implements EntityBean, ICheckList {
	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getCheckListID" };

	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getCheckListID" };

	/**
	 * Default Constructor
	 */
	public EBCheckListBean() {
	}

	// *************Override Inteface Methods (Java 1.4 requirement)
	// **************
	public abstract String getCheckListType();

	public abstract String getCheckListStatus();

	public abstract Date getLastDocReceivedDate();

	public abstract String getRemarks();

	public abstract Date getDeficiencyDate();

	public abstract Date getNextActionDate();

	public abstract String getApplicationType();

	public abstract void setCheckListType(String aCheckListType);

	public abstract void setCheckListStatus(String aCheckListStatus);

	public abstract void setLastDocReceivedDate(Date aLastDocReceivedDate);

	public abstract void setRemarks(String aRemarks);

	public abstract void setDeficiencyDate(Date deficiencyDate);

	public abstract void setNextActionDate(Date nextActionDate);

	public abstract void setApplicationType(String applicationType);

	public abstract String getLegalFirm();

	public abstract void setLegalFirm(String aLegalFirm);

	public abstract String getLawyerReferenceNumber();

	public abstract void setLawyerReferenceNumber(String lawyerReferenceNumber);

	public abstract String getLawyerInCharge();

	public abstract void setLawyerInCharge(String lawyerInCharge);

	public abstract String getLawFirmAddress();

	public abstract void setLawFirmAddress(String lawFirmAddress);

	public abstract String getLawFirmContactNumber();

	public abstract void setLawFirmContactNumber(String lawFirmContactNumber);

	public abstract String getLawyerEmail();

	public abstract void setLawyerEmail(String lawyerEmail);

	public abstract String getLawFirmPanelFlag();

	public abstract void setLawFirmPanelFlag(String lawFirmPanelFlag);
	
	public abstract String getValuerFirm();

	public abstract void setValuerFirm(String aValuerFirm);

	public abstract String getValuerReferenceNumber();

	public abstract void setValuerReferenceNumber(String valuerReferenceNumber);

	public abstract String getValuerInCharge();

	public abstract void setValuerInCharge(String valuerInCharge);

	public abstract String getValuerFirmAddress();

	public abstract void setValuerFirmAddress(String valuerFirmAddress);

	public abstract String getValuerFirmContactNumber();

	public abstract void setValuerFirmContactNumber(String valuerFirmContactNumber);

	public abstract String getValuerEmail();

	public abstract void setValuerEmail(String valuerEmail);

	public abstract String getValuerFirmPanelFlag();

	public abstract void setValuerFirmPanelFlag(String valuerFirmPanelFlag);
	
	public abstract String getInsurerFirm();

	public abstract void setInsurerFirm(String aInsurerFirm);

	public abstract String getInsurerReferenceNumber();

	public abstract void setInsurerReferenceNumber(String insurerReferenceNumber);

	public abstract String getInsurerInCharge();

	public abstract void setInsurerInCharge(String insurerInCharge);

	public abstract String getInsurerFirmAddress();

	public abstract void setInsurerFirmAddress(String insurerFirmAddress);

	public abstract String getInsurerFirmContactNumber();

	public abstract void setInsurerFirmContactNumber(String insurerFirmContactNumber);

	public abstract String getInsurerEmail();

	public abstract void setInsurerEmail(String insurerEmail);

	public abstract String getInsurerFirmPanelFlag();

	public abstract void setInsurerFirmPanelFlag(String insurerFirmPanelFlag);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long l);

	// ************** Abstract methods ************
	public abstract Long getCMPCheckListID();

	public abstract Long getCMPTemplateID();

	public abstract Long getCMPCollateralID();

	public abstract Long getCMPLimitProfileID();

	public abstract Long getCMPPledgerID();

	public abstract Long getCMPBorrowerID();

	public abstract String getSubCategory();

	public abstract String getCMPAllowDeleteInd();

	public abstract String getCountryCode();

	public abstract String getOrgCode();

	public abstract String getCMPDisableCollaborationInd();

	public abstract void setCMPDisableCollaborationInd(String adisableCollaborationInd);

	public long getLimitProfileId() {
		if (getCMPLimitProfileID() == null)
			return ICMSConstant.LONG_INVALID_VALUE;
		return getCMPLimitProfileID().longValue();
	}

	public void setLimitProfileId(long limitProfileId) {
		setCMPLimitProfileID(new Long(limitProfileId));
	}

	public String getDisableCollaborationInd() {
		return getCMPDisableCollaborationInd();
	}

	public void setDisableCollaborationInd(String adisableCollaborationInd) {
		setCMPDisableCollaborationInd(adisableCollaborationInd);
	}

	/**
	 * Helper method to get the checklist ID
	 * @return long - the long value of the checklist ID
	 */
	public long getCheckListID() {
		if (getCMPCheckListID() != null) {
			return getCMPCheckListID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Helper method to get the template ID
	 * @return long - the long value of the template ID
	 */
	public long getTemplateID() {
		if (getCMPTemplateID() != null) {
			return getCMPTemplateID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Get the checkList owner
	 * @return ICheckListOwner - the checklist owner info
	 */
	public ICheckListOwner getCheckListOwner() {
		ICheckListOwner owner = null;
		long pledgerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		long collateralID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		long borrowerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

		if (getCMPPledgerID() != null) {
			pledgerID = getCMPPledgerID().longValue();
		}
		if (getCMPCollateralID() != null) {
			collateralID = getCMPCollateralID().longValue();
		}
		if (getCMPBorrowerID() != null) {
			borrowerID = getCMPBorrowerID().longValue();
		}

		if (pledgerID != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			owner = new OBCCCheckListOwner(getCMPLimitProfileID().longValue(), getCMPPledgerID().longValue(),
					ICMSConstant.CHECKLIST_PLEDGER);
			return owner;
		}
		if (collateralID != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			// owner = new
			// OBCollateralCheckListOwner(getCMPLimitProfileID().longValue(),
			// getCMPCollateralID().longValue(), getCMPBorrowerID().longValue(),
			// getSubCategory());
			owner = new OBCollateralCheckListOwner(getCMPLimitProfileID().longValue(),
					getCMPCollateralID().longValue(), getSubCategory());
			return owner;
		}

		if (borrowerID != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			if (getCMPLimitProfileID() != null) {
				owner = new OBCCCheckListOwner(getCMPLimitProfileID().longValue(), getCMPBorrowerID().longValue(),
						getSubCategory());
			}
			else {
				owner = new OBCCCheckListOwner(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE,
						getCMPBorrowerID().longValue(), getSubCategory());
			}
			return owner;
		}
		return owner;
	}

	public IBookingLocation getCheckListLocation() {
		String countryCode = getCountryCode();
		String orgCode = getOrgCode();

		if (countryCode != null) {
			return new OBBookingLocation(countryCode, orgCode);
		}
		return null;
	}

	public boolean getAllowDeleteInd() {
		if (getCMPAllowDeleteInd() != null) {
			if (ICMSConstant.TRUE_VALUE.equals(getCMPAllowDeleteInd())) {
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Not implemented here
	 */
	public ICheckListItem[] getCheckListItemList() {
		return null;
	}

	// setters
	public abstract void setCMPCheckListID(Long aCheckListID);

	public abstract void setCMPTemplateID(Long aTemplateID);

	public abstract void setCMPCollateralID(Long aCollateralID);

	public abstract void setCMPLimitProfileID(Long aLimitProfileID);

	public abstract void setCMPPledgerID(Long aPledgerID);

	public abstract void setCMPBorrowerID(Long aBorrowerID);

	public abstract void setSubCategory(String aSubCategory);

	public abstract void setCMPAllowDeleteInd(String anAllowDeleteInd);

	public abstract void setCountryCode(String aCountryCode);

	public abstract void setOrgCode(String anOrgCode);

	/**
	 * Helper method to set the checklist ID
	 * @param aCheckListID - long
	 */
	public void setCheckListID(long aCheckListID) {
		setCMPCheckListID(new Long(aCheckListID));
	}

	/**
	 * Helper method to set the template ID
	 * @param aTemplateID - long
	 */
	public void setTemplateID(long aTemplateID) {
		setCMPTemplateID(new Long(aTemplateID));
	}

	public void setCheckListOwner(ICheckListOwner anICheckListOwner) {
		if (anICheckListOwner instanceof ICCCheckListOwner) {
			ICCCheckListOwner checkListOwner = (ICCCheckListOwner) anICheckListOwner;
			if ((checkListOwner.getSubOwnerType().equals(ICMSConstant.CHECKLIST_MAIN_BORROWER))
					|| (checkListOwner.getSubOwnerType().equals(ICMSConstant.CHECKLIST_CO_BORROWER))
					|| (checkListOwner.getSubOwnerType().equals(ICMSConstant.CHECKLIST_JOINT_BORROWER))) {
				setCMPLimitProfileID(new Long(checkListOwner.getLimitProfileID()));
				setCMPBorrowerID(new Long(checkListOwner.getSubOwnerID()));
				setSubCategory(checkListOwner.getSubOwnerType());
				setCMPCollateralID(null); // to handle websphere. websphere will
				// default to 0 instead of null
				setCMPPledgerID(null); // to handle websphere. websphere will
				// default to 0 instead of null
				return;
			}
			if (checkListOwner.getSubOwnerType().equals(ICMSConstant.CHECKLIST_NON_BORROWER)) {
				setCMPBorrowerID(new Long(checkListOwner.getSubOwnerID()));
				setSubCategory(checkListOwner.getSubOwnerType());
				// CMSSP-798
				setCMPLimitProfileID(new Long(checkListOwner.getLimitProfileID()));
				setCMPCollateralID(null); // to handle websphere. websphere will
				// default to 0 instead of null
				setCMPPledgerID(null); // to handle websphere. websphere will
				// default to 0 instead of null
				return;
			}
			if (checkListOwner.getSubOwnerType().equals(ICMSConstant.CHECKLIST_PLEDGER)) {
				setCMPLimitProfileID(new Long(checkListOwner.getLimitProfileID()));
				setCMPPledgerID(new Long(checkListOwner.getSubOwnerID()));
				setSubCategory(checkListOwner.getSubOwnerType());
				setCMPCollateralID(null); // to handle websphere. websphere will
				// default to 0 instead of null
				return;
			}
			return;
		}
		if (anICheckListOwner instanceof ICollateralCheckListOwner) {
			ICollateralCheckListOwner checkListOwner = (ICollateralCheckListOwner) anICheckListOwner;
			setCMPLimitProfileID(new Long(checkListOwner.getLimitProfileID()));
			setCMPCollateralID(new Long(checkListOwner.getCollateralID()));
			setCMPPledgerID(null); // for security checklist, pledgor id is
			// always null

			if ((ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(checkListOwner.getSubOwnerType()))
					|| (ICMSConstant.CHECKLIST_CO_BORROWER.equals(checkListOwner.getSubOwnerType()))) {
				setCMPLimitProfileID(new Long(checkListOwner.getLimitProfileID()));
				if ((ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(checkListOwner.getSubOwnerType()))) {
					setCMPBorrowerID(new Long(checkListOwner.getSubOwnerID()));
				}
				else {
					setCMPBorrowerID(null); // R1.5 CR35 :- Multiple CoBorrower
					// for 1 Security
				}
				setSubCategory(checkListOwner.getSubOwnerType());
				return;
			}
		}
		return;
	}

	public void setCheckListLocation(IBookingLocation anIBookingLocation) {
		if (anIBookingLocation != null) {
			if (anIBookingLocation.getCountryCode() != null) {
				setCountryCode(anIBookingLocation.getCountryCode());
			}

			if (anIBookingLocation.getOrganisationCode() != null) {
				setOrgCode(anIBookingLocation.getOrganisationCode());
			}
		}
	}

	public void setAllowDeleteInd(boolean anAllowDeleteInd) {
		if (anAllowDeleteInd) {
			setCMPAllowDeleteInd(ICMSConstant.TRUE_VALUE);
		}
		else {
			setCMPAllowDeleteInd(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Not implemented here
	 */
	public void setCheckListItemList(ICheckListItem[] aCheckListItemList) {
		// do nothing
	}

	/**
	 * Add a list of items into the template
	 * @param anItemList - IItem[]
	 */
	public void addItems(IItem[] anItemList) {
		// do nothing
	}

	/**
	 * Remove a list of items into the template
	 * @param anItemIndexList int[] - the list of item index to be removed
	 */
	public void removeItems(int[] anItemIndexList) {
		// do nothing
	}

	/**
	 * Update an item in the template
	 * @param anItemIndex - int
	 * @param anItem - IItem
	 */
	public void updateItem(int anItemIndex, IItem anItem) {
		// do nothing
	}

	public void setReversalRemarks(String remarks) {
		// do nothing
	}

	public String getReversalRemarks() {
		return "";
	}

	public void setReversalRmkUpdatedUserInfo(String userInfo) {
		// do nothing
	}

	public String getReversalRmkUpdatedUserInfo() {
		return "";
	}

	// ************** CMR methods ***************
	// Getters
	/**
	 * Get all checklist items
	 * 
	 * @return Collection of EBCheckListItemLocal objects
	 */
	public abstract Collection getCMRCheckListItems();

	// Setters
	/**
	 * Set all checklist items
	 * 
	 * @param aCheckListItems is of type Collection of EBCheckListItemLocal
	 *        objects
	 */
	public abstract void setCMRCheckListItems(Collection aCheckListItems);

	/**
	 * Return a checklist object
	 * @return ICheckList - the object containing the checklist object
	 * @throws CheckListException on errors
	 */
	public ICheckList getValue() throws CheckListException {
		OBCheckList value = new OBCheckList();
		AccessorUtil.copyValue(this, value, null);

		ICheckListItem[] itemList = retrieveCheckListItems();

		// Start for cr-17
		// DefaultLogger.debug(this,
		// ">>>>>>>>>> IN EBCheckListBean (getValue)!!!");
		ArrayList resultList = new ArrayList();
		if ((itemList != null) && (itemList.length > 0)) {
			for (int i = 0; i < itemList.length; i++) {
				ICheckListItem obj = itemList[i];
				resultList.add(obj);
			}
		}
		if ((getCheckListID() != 0)
				|| (getCheckListID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
			ICheckListItem[] shareItemList = getShareDocumentItemId(getCheckListID());
			// DefaultLogger.debug(this,
			// ">>>>>>>>>>>>>> EBCheckListBean.getValue - no of shared item list: "
			// + ((shareItemList == null) ? 0: shareItemList.length));

			if ((shareItemList != null) && (shareItemList.length > 0)) {
				for (int i = 0; i < shareItemList.length; i++) {
					resultList.add(shareItemList[i]);
				}
			}
		}
		itemList = (ICheckListItem[]) resultList.toArray(new ICheckListItem[0]);
		// End for cr-17
//		DefaultLogger.debug(this, ">>>>>>>>>>>>>> EBCheckListBean.getValue - total no of item: " + itemList.length);

		// populateItemsWithCustodianDetails(value, itemList);

		if ((itemList != null) && (itemList.length > 1)) {
			Arrays.sort(itemList, new Comparator() {

				public int compare(Object thisObject, Object thatObject) {
					String thisItemDesc = (thisObject == null) ? null : ((ICheckListItem) thisObject).getItemDesc();
					String thatItemDesc = (thatObject == null) ? null : ((ICheckListItem) thatObject).getItemDesc();
					if (thisItemDesc == null) {
						return (thatItemDesc == null) ? 0 : -1;
					}

					return (thatItemDesc == null) ? 1 : thisItemDesc.compareTo(thatItemDesc);
				}
			});
		}

		value.setCheckListItemList(itemList);
		return value;
	}

	private void populateItemsWithCustodianDetails(ICheckList value, ICheckListItem[] anICheckListItemList)
			throws CheckListException {
		// DefaultLogger.debug("EBCheckListBean.populateItemsWithCustodianDetails"
		// , ">>>>>>>>>>>>>>> start");
		try {
			if (anICheckListItemList != null) {
				CustodianSearchCriteria criteria = new CustodianSearchCriteria();

				// get actual checklist id
				long actualCheckListID = getActualCheckListID(anICheckListItemList);
				// defaults to the checklist id passed in
				if (actualCheckListID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					actualCheckListID = value.getCheckListID();
				}

				// DefaultLogger.debug(
				// "EBCheckListBean.populateItemsWithCustodianDetails",
				// ">>>>>>> checkListID :  " + actualCheckListID);
				// DefaultLogger.debug(
				// "EBCheckListBean.populateItemsWithCustodianDetails",
				// ">>>>>>> type : " + value.getCheckListType());

				criteria.setCheckListID(actualCheckListID);
				criteria.setDocType(value.getCheckListType());
				SearchResult result = getEBCustodianDocHome().getDocList(criteria);
				// CR34 set custodian reversal remarks into checklist
				if (result != null) {
					Iterator iterator = result.getResultList().iterator();
					if (iterator.hasNext()) {
						ICustodianDocSearchResult doc = (ICustodianDocSearchResult) iterator.next();
						value.setReversalRemarks(doc.getReversalRemarks());
						value.setReversalRmkUpdatedUserInfo(doc.getReversalRmkUpdatedUserInfo());
					}
				}
				for (int ii = 0; ii < anICheckListItemList.length; ii++) {
					if ((anICheckListItemList[ii].getIsInVaultInd())
							&& (!anICheckListItemList[ii].getViewable())
							&& ((anICheckListItemList[ii].getItemStatus().equals(ICMSConstant.STATE_ITEM_COMPLETED))
									|| (anICheckListItemList[ii].getItemStatus()
											.equals(ICMSConstant.STATE_ITEM_RENEWED))
									|| (anICheckListItemList[ii].getItemStatus()
											.equals(ICMSConstant.STATE_ITEM_EXPIRED))
									|| (anICheckListItemList[ii].getItemStatus()
											.equals(ICMSConstant.STATE_ITEM_DELETED))
									|| (anICheckListItemList[ii].getItemStatus().equals("PENDING_UPDATE"))
									|| (anICheckListItemList[ii].getItemStatus().equals("PENDING_RENEWAL")) || (anICheckListItemList[ii]
									.getItemStatus().equals(ICMSConstant.STATE_ITEM_PENDING_DELETE)))) { // Priya
						// Added
						// "PENDING_RENEWAL"
						// FOR
						// CMS
						// -
						// 3021.
						anICheckListItemList[ii]
								.setCustodianDocStatus(anICheckListItemList[ii].getCPCCustodianStatus());
						long checkListItemRef = anICheckListItemList[ii].getCheckListItemRef();
						if (result != null) {
							Iterator docIterator = result.getResultList().iterator();
							if (docIterator.hasNext()) {
								ICustodianDocSearchResult doc = (ICustodianDocSearchResult) docIterator.next();
								ICustodianDocItemSearchResult[] items = doc.getCustodianDocItems();
								for (int jj = 0; jj < items.length; jj++) {
									if (checkListItemRef == items[jj].getCheckListItemID()) {

										// DefaultLogger.debug(
										// "EBCheckListBean.populateItemsWithCustodianDetails"
										// , ">>>> item ref : " +
										// items[jj].getCheckListItemID());

										// populate custodian doc item status
										String origcustDocStatus = getCustodianDocItemStatus(items[jj]);
										String itemStatus = anICheckListItemList[ii].getCPCCustodianStatus();
										String custDocStatus = CheckListCustodianHelper.getCheckListCustodianStatus(
												itemStatus, origcustDocStatus);
										anICheckListItemList[ii].setCustodianDocStatus(custDocStatus);
										// DefaultLogger.debug(
										// "EBCheckListBean.populateItemsWithCustodianDetails"
										// , ">>>> cust status : " +
										// custDocStatus);
										// populate custodian doc item trx date
										Date custodianDocItemTrxDate = items[jj].getTrxDate();
										anICheckListItemList[ii].setCustodianDocItemTrxDate(custodianDocItemTrxDate);

										// DefaultLogger.debug(
										// "EBCheckListBean.populateItemsWithCustodianDetails"
										// , ">>>> cust trx date : " +
										// custodianDocItemTrxDate);

										break;
									}
								}
							}
						}
					}
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new CheckListException("Exception in populateItemsWithCustodianDetails: " + ex.toString());
		}
		// DefaultLogger.debug("EBCheckListBean.populateItemsWithCustodianDetails"
		// , ">>>>>>>>>>>>>>> end");
	}

	/**
	 * To return the actual custodian doc status
	 * @param aSearchResult of ICustodianDocSearchResult type
	 * @return String - the custodian doc status
	 */
	/*
	 * protected String getCustodianDocStatus(ICustodianDocSearchResult
	 * aSearchResult) { return aSearchResult.getTrxStatus(); }
	 */

	/**
	 * To return the actual custodian doc item status
	 * @param aSearchResult of ICustodianDocItemSearchResult type
	 * @return String - the custodian doc item status
	 */
	protected String getCustodianDocItemStatus(ICustodianDocItemSearchResult aSearchResult) {
		return aSearchResult.getStatus();
	}

	/**
	 * Get the actual checklist id given a list of checklist items which maybe
	 * actual or staging business objects
	 * @param checklistItems - ICheckListItem[]
	 * @return long - the actual checklist id
	 * @return ICMSConstant.LONG_INVALID_VALUE if given list of actual checklist
	 *         item or if no checklist items passed in.
	 */
	protected long getActualCheckListID(ICheckListItem[] checklistItems) throws CheckListException {
		if ((checklistItems == null) || (checklistItems.length == 0)) {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}

		EBCheckListItemLocalHome home = getActualEBCheckListItemLocalHome();
		// no need to search for actual checklist id if actual bean used
		if (home == null) {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}

		// search for actual checklist id if staging bean used
		// sort checklistitem by the doc item ref in ascending order to reduce
		// number of iterations
		CheckListItemComparator comp = new CheckListItemComparator();
		Arrays.sort(checklistItems, comp);
		for (int i = 0; i < checklistItems.length; i++) {
			try {
				long checkListItemRef = checklistItems[i].getCheckListItemRef();
				EBCheckListItemLocal local = home.findByCheckListItemRef(checkListItemRef);
				return local.getCMPCheckListID().longValue();
			}
			catch (FinderException e) {
				break;
			}
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Prepare the checklist item based on the template items list
	 * @param aCheckListItemList of ICheckListItem[] type
	 * @param aTemplateItemList of ITemplateItem[] type
	 * @return ICheckListItem[] - the list of checklist items prepared
	 */
	/*
	 * private ICheckListItem[] prepareCheckListItem(ICheckListItem[]
	 * aCheckListItemList, ITemplateItem[] aTemplateItemList) { for (int ii=0;
	 * ii<aCheckListItemList.length; ii++) { for (int jj=0;
	 * jj<aTemplateItemList.length; jj++) { if
	 * (aCheckListItemList[ii].getItemCode
	 * ().equals(aTemplateItemList[jj].getItemCode())) {
	 * //aCheckListItemList[ii].setIsInheritedInd(true);
	 * aCheckListItemList[ii].setParentItemID
	 * (aTemplateItemList[ii].getTemplateItemID(); break; } } } return
	 * aCheckListItemList; }
	 * 
	 * / Get the template that this checklist inherit from
	 * 
	 * @return ITemplate - the template related to this checklist
	 * 
	 * @throws CheckListException on errors
	 */
	/*
	 * private ITemplate retrieveTemplate() throws CheckListException { try {
	 * EBTemplateLocal templateLocal =
	 * getEBTemplateLocalHome().findByPrimaryKey(getCMPTemplateID()); return
	 * templateLocal.getValue(); } catch(Exception ex) { throw new
	 * CheckListException("Exception at retrieveTemplate: " + ex.toString()); }
	 * }
	 */

	/**
	 * To retrieve the list of checklist items
	 * @return ICheckListItem[] - the list of checklist items
	 * @throws CheckListException on error
	 */
	private ICheckListItem[] retrieveCheckListItems() throws CheckListException {
		Collection col = getCMRCheckListItems();
		if ((col == null) || (col.size() == 0)) {
			return null;
		}
		else {
			ArrayList itemList = new ArrayList();
			Iterator iter = col.iterator();
			while (iter.hasNext()) {
				EBCheckListItemLocal local = (EBCheckListItemLocal) iter.next();
				if (!local.getIsDeletedInd()) {
					ICheckListItem obj = local.getValue();
					itemList.add(obj);
				}
			}
			return (ICheckListItem[]) itemList.toArray(new ICheckListItem[0]);
		}
	}

	/**
	 * Set the checklist object.
	 * @param anICheckList - ICheckList
	 * @throws CheckListException on error
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(ICheckList anICheckList) throws CheckListException, ConcurrentUpdateException {
		try {
			if (getVersionTime() != anICheckList.getVersionTime()) {
				ConcurrentUpdateException exp = new ConcurrentUpdateException("Mismatch timestamp");
				exp.setErrorCode(ICMSErrorCodes.CONCURRENT_UPDATE);
				throw exp;
			}
			AccessorUtil.copyValue(anICheckList, this, EXCLUDE_METHOD_UPDATE);
			
			if(anICheckList.getCheckListItemList() != null) {
				if("Y".equals(anICheckList.getFlagSchedulersCheck())) {
				for (int ii = 0; ii < anICheckList.getCheckListItemList().length; ii++) {
					anICheckList.getCheckListItemList()[ii].setFlagSchedulerCheckItem("Y");
				}
			}}
			
			updateCheckListItems(anICheckList.getCheckListItemList());
			
			setVersionTime(VersionGenerator.getVersionNumber());
		}
		catch (ConcurrentUpdateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (CheckListException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
	}

	/**
	 * Create the child items that are under this checklist
	 * @param anICheckList - ICheckList
	 * @throws CheckListException
	 */
	public void createCheckListItems(ICheckList anICheckList) throws CheckListException {
//		if("Y".equals(anICheckList.getFlagSchedulersCheck()) && anICheckList.getCheckListItemList() != null ) {
//		for(int i=0; i<anICheckList.getCheckListItemList().length;i++) {
//			anICheckList.getCheckListItemList()[i].setFlagSchedulerCheckItem("Y");
//		}
//	}
		updateCheckListItems(anICheckList.getCheckListItemList());
	}

	private void updateCheckListItems(ICheckListItem[] anICheckListItemList) throws CheckListException {

		// DefaultLogger.debug(this, (
		// " Starting Private updateCheckListItems(ICheckListItem[] anICheckListItemList)  "
		// );
		// Delete if any share checkList Value
		DefaultLogger.debug(this, "<<<>>> 847 Inside the updateCheckListItems() of EBCheckListBean.java..");
		anICheckListItemList = removeShareDocumentCheckListId(anICheckListItemList);
		try {
			Collection col = getCMRCheckListItems();
			if (anICheckListItemList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else {
					// delete all records
					deleteCheckListItems(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) {
				// create new records
				createCheckListItems(Arrays.asList(anICheckListItemList));
			}
			else {
				Iterator iter = col.iterator();
				ArrayList createList = new ArrayList();
				ArrayList deleteList = new ArrayList(); // contains list of
				// local interfaces

				// identify identify records for delete or udpate first
				while (iter.hasNext()) {
					EBCheckListItemLocal local = (EBCheckListItemLocal) iter.next();
					long checkListItemRef = local.getCheckListItemRef();
					boolean update = false;

					for (int ii = 0; ii < anICheckListItemList.length; ii++) {
						ICheckListItem newOB = anICheckListItemList[ii];

						if (newOB.getCheckListItemRef() == checkListItemRef) {
							// perform update
							local.setValue(newOB);
							update = true;
							break;
						}
					}
					if (!update) {
						// add for delete
						deleteList.add(local);
					}
				}
				// next identify records for add
				DefaultLogger.debug(this, "<<<>>> 891 updateCheckListItems() of EBCheckListBean.java..");
				for (int ii = 0; ii < anICheckListItemList.length; ii++) {
					/*
					 * if (anICheckListItemList[ii].getCheckListItemID() ==
					 * com.integrosys
					 * .cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)
					 * { createList.add(anICheckListItemList[ii]); }
					 */

					iter = col.iterator();
					ICheckListItem newOB = anICheckListItemList[ii];
					boolean found = false;

					while (iter.hasNext()) {
						EBCheckListItemLocal local = (EBCheckListItemLocal) iter.next();
						long ref = local.getCheckListItemRef();

						if (newOB.getCheckListItemRef() == ref) {
							DefaultLogger.debug(this, "<<<>>> 914 In the updateCheckListItems() of EBCheckListBean.java"+ref);
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						DefaultLogger.debug(this, "<<<>>> 922 In the updateCheckListItems() of EBCheckListBean.java");
						createList.add(newOB);
					}
				}
				DefaultLogger.debug(this, "<<<>>> 918 In the updateCheckListItems() of EBCheckListBean.java=> before deleteCheckListItems()");
				deleteCheckListItems(deleteList);
				createCheckListItems(createList);
				DefaultLogger.debug(this, "<<<>>> 922 In the updateCheckListItems() of EBCheckListBean.java=> after createCheckListItems()");
			}
		}
		catch (CheckListException ex) {
			DefaultLogger.debug(this, "<<<>>> Exception: 934 got exception in the In the updateCheckListItems() of EBCheckListBean.java");
			throw ex;
		}
	}

	/**
	 * Delete the list of checklist items under the current checklist
	 * @param aDeletionList - List
	 * @throws CheckListException on errors
	 */
	private void deleteCheckListItems(List aDeletionList) throws CheckListException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}

		Collection col = getCMRCheckListItems();
		Iterator iter = aDeletionList.iterator();
		while (iter.hasNext()) {
			EBCheckListItemLocal local = (EBCheckListItemLocal) iter.next();
			// col.remove(local);
			// local.remove();
			local.setIsDeletedInd(true);
		}
	}

	/**
	 * Create the list of checklist items under the current checklist
	 * @param aCreationList - List
	 * @throws CheckListException on errors
	 */
	private void createCheckListItems(List aCreationList) throws CheckListException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRCheckListItems();
		Iterator iter = aCreationList.iterator();
		EBCheckListItemLocalHome home = getEBCheckListItemLocalHome();
		while (iter.hasNext()) {
			ICheckListItem obj = (ICheckListItem) iter.next();

			try {
				EBCheckListItemLocal local = home.create(new Long(getCheckListID()), obj);
				local.createDocumentshareList(obj);
				col.add(local);
			}
			catch (CreateException ex) {
				throw new CheckListException("failed to create checklist item [code: " + obj.getItemCode() + ", desc: "
						+ obj.getItemDesc() + "]", ex);
			}

		}
	}

	/**
	 * To generate the checklist item reference and set it into the checklist
	 * item This is supposed to be generated at staging //* @param
	 * anICheckListItem - ICheckListItem //* @throws Exception on errors
	 */
	/*
	 * protected void preCreationProcess(ICheckListItem anICheckListItem) throws
	 * Exception { //do nothing. }
	 * 
	 * / Create a checklist.
	 * 
	 * @param anICheckList - ICheckList
	 * 
	 * @return Long - the checklist ID
	 * 
	 * @throws CreateException on error
	 */
	public Long ejbCreate(ICheckList anICheckList) throws CreateException {
		if (anICheckList == null) {
			throw new CreateException("ICMSCustomer is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			// DefaultLogger.debug(this, "Creating Customer with ID: " + pk);
			setCheckListID(pk);
			AccessorUtil.copyValue(anICheckList, this, EXCLUDE_METHOD_CREATE);
			setVersionTime(VersionGenerator.getVersionNumber());
			return new Long(pk);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			CreateException cex = new CreateException("Failed to create checklist when getting sequence ["
					+ getSequenceName() + "]");
			cex.initCause(ex);
			throw cex;
		}
	}

	/**
	 * EJB Post Create Method
	 * @param anICheckList - ICheckList
	 */
	public void ejbPostCreate(ICheckList anICheckList) throws CreateException {
	}

	/**
	 * EJB Callback Method
	 */
	public void setEntityContext(EntityContext ctx) {
		_context = ctx;
	}

	/**
	 * EJB Callback Method
	 */
	public void unsetEntityContext() {
		_context = null;
	}

	/**
	 * A container invokes this method when the instance is taken out of the
	 * pool of available instances to become associated with a specific EJB
	 * object. This method transitions the instance to the ready state. This
	 * method executes in an unspecified transaction context.
	 */
	public void ejbActivate() {
	}

	/**
	 * A container invokes this method on an instance before the instance
	 * becomes disassociated with a specific EJB object. After this method
	 * completes, the container will place the instance into the pool of
	 * available instances. This method executes in an unspecified transaction
	 * context.
	 */
	public void ejbPassivate() {
	}

	/**
	 * A container invokes this method to instruct the instance to synchronize
	 * its state by loading it from the underlying database. This method always
	 * executes in the transaction context determined by the value of the
	 * transaction attribute in the deployment descriptor.
	 */
	public void ejbLoad() {
	}

	/**
	 * A container invokes this method to instruct the instance to synchronize
	 * its state by storing it to the underlying database. This method always
	 * executes in the transaction context determined by the value of the
	 * transaction attribute in the deployment descriptor.
	 */
	public void ejbStore() {
	}

	/**
	 * A container invokes this method before it removes the EJB object that is
	 * currently associated with the instance. It is invoked when a client
	 * invokes a remove operation on the enterprise Bean's home or remote
	 * interface. It transitions the instance from the ready state to the pool
	 * of available instances. It is called in the transaction context of the
	 * remove operation.
	 */
	public void ejbRemove() throws RemoveException {
	}

	/**
	 * Get the number of limits that a collateral is related in a limit profile
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @return int - the number of limits that a collateral is related in a
	 *         limit profile
	 * @throws SearchDAOException on errors
	 */
	public int ejbHomeGetLimitProfileCollateralCount(long aLimitProfileID, long aCollateralID)
			throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getLimitProfileCollateralCount(aLimitProfileID, aCollateralID);
	}

	public int ejbHomeGetLimitProfilePledgorCount(long aLimitProfileID, long aPledgorID, long[] aDeletedLimitSecMapList)
			throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getLimitProfilePledgorCount(aLimitProfileID, aPledgorID,
				aDeletedLimitSecMapList);
	}

	/**
	 * Get the number of limits that a pledgor is related in a limit profile
	 * @param aLimitProfileID of long type
	 * @param aPledgorID of long type
	 * @return int - the number of limits that a pledgor is related in a limit
	 *         profile
	 * @throws SearchDAOException on errors
	 */
	public int ejbHomeGetLimitProfilePledgorCount(long aLimitProfileID, long aPledgorID) throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getLimitProfilePledgorCount(aLimitProfileID, aPledgorID);
	}

	/**
	 * Get the number of limits that a coborrower is related in a limit profile
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @return int - the number of limits that a coborrower is related in a
	 *         limit profile
	 * @throws SearchDAOException on errors
	 */
	public int ejbHomeGetLimitProfileCoBorrowerCount(long aLimitProfileID, long aCustomerID) throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getLimitProfileCoBorrowerCount(aLimitProfileID, aCustomerID);
	}

	/**
	 * Call the DAO to get Hashtable with collateral ID and the status
	 * @param aLimitProfileID of long type
	 * @return HashMap - this the security ID as the key and the status as the
	 *         value
	 * @throws SearchDAOException on errors
	 */
	public HashMap ejbHomeGetCollateralCheckListStatus(long aLimitProfileID) throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCollateralCheckListStatus(aLimitProfileID);
	}
	
	public CheckListSearchResult ejbHomeGetCheckListByCollateralID(long collateralId) throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCheckListByCollateralID(collateralId);
	}
	
	public CheckListSearchResult ejbHomeGetCAMCheckListByCategoryAndProfileID(String category,long collateralID) throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCAMCheckListByCategoryAndProfileID(category, collateralID);
	}

	public CheckListSearchResult[] ejbHomeGetCheckListByCategory(String category) throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCheckListByCategory(category);
	}
	
	public CheckListSearchResult ejbHomeGetPariPassuCheckListByCategoryAndProfileID(String category,long collateralID) throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getPariPassuCheckListByCategoryAndProfileID(category, collateralID);
	}
	/**
	 * Call the DAO to get Hashtable with borrower ID/pledger ID and the status
	 * @param aLimitProfileID of long type
	 * @return HashMap - this the security ID as the key and the status as the
	 *         value
	 * @throws SearchDAOException on errors
	 */
	public HashMap ejbHomeGetCCCheckListStatus(long aLimitProfileID, boolean isFullListInd) throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCCCheckListStatus(aLimitProfileID, isFullListInd);
	}
	
	
	public HashMap ejbHomeGetFacilityCheckListStatus(long aLimitProfileID, boolean isFullListInd) throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getFacilityCheckListStatus(aLimitProfileID, isFullListInd);
	}

	/**
	 * Get Hashtable with borrower ID/pledger ID and the status for deleted
	 * checklist
	 * @param aLimitProfileID of long type
	 * @return CCCheckListSummary[] - this the borrowerID/pledgerID as the key
	 *         and the status as the value
	 * @throws SearchDAOException on errors
	 */
	public CCCheckListSummary[] ejbHomeGetCCCheckListList(long aLimitProfileID, boolean aDeletedInd)
			throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCCCheckListList(aLimitProfileID, aDeletedInd);
	}

	/**
	 * Get Hashtable with borrower ID/pledger ID and the status for deleted
	 * checklist
	 * @param aCustomerID of long type
	 * @param aDeletedInd of boolean
	 * @return CCCheckListSummary[] - this the borrowerID/pledgerID as the key
	 *         and the status as the value
	 * @throws SearchDAOException on errors
	 */
	public CCCheckListSummary[] ejbHomeGetCCCheckListListForNonBorrower(long aCustomerID, boolean aDeletedInd)
			throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCCCheckListListForNonBorrower(aCustomerID, aDeletedInd);
	}

	/**
	 * Call the DAO to get Hashtable with non borrower ID and the status
	 * @param aCustomerID of long type
	 * @return HashMap - this the customer ID as the key and the status as the
	 *         value
	 * @throws SearchDAOException on errors
	 */
	public HashMap ejbHomeGetCCCheckListStatusForNonBorrower(long aLimitProfileID, long aCustomerID)
			throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCCCheckListStatusForNonBorrower(aLimitProfileID, aCustomerID);
	}

	/**
	 * Get the list of checklist item operation
	 * @return ICheckListItemOperation[] - the list of checklist item
	 * @throws SearchDAOException on errors
	 */
	public ICheckListItemOperation[] ejbHomeGetCheckListItemOperation() throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCheckListItemOperation();
	}

	/**
	 * Get the number of checklist based on the attribute specified in the owner
	 * and the status of the trx
	 * @param anICheckListOwner of ICheckListOwner
	 * @param aStatusList of String[] type
	 * @return CheckListSearchResult[] - the list of checklist result
	 * @throws SearchDAOException
	 */
	public CheckListSearchResult[] ejbHomeGetCheckList(ICheckListOwner anICheckListOwner, String[] aStatusList)
			throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCheckList(anICheckListOwner, aStatusList);
	}

	/**
	 * Get the checklist item for waiver/deferral generation
	 * @param aLimitProfileID of long type
	 * @param anItemStatus of String type
	 * @return HashMap - the checkListID and the list of checklist item (not the
	 *         full detail)
	 * @throws SearchDAOException
	 */
	public HashMap ejbHomeGetCheckListItemListbyStatus(long aLimitProfileID, String anItemStatus)
			throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCheckListItemListbyStatus(aLimitProfileID, anItemStatus);
	}

    /**
     * Get the checklist item by checklist category
     *
     * @param aLimitProfileID     of long type
     * @param aCheckListCategory of String type
     * @return HashMap - the checkListID and the list of checklist item (not the full detail)
     * @throws SearchDAOException
     */
    public HashMap ejbHomeGetCheckListItemListbyCategory(long aLimitProfileID, String aCheckListCategory)
            throws SearchDAOException {
        return CheckListDAOFactory.getCheckListDAO().getCheckListItemListbyCategory(aLimitProfileID, aCheckListCategory);
	}

	/**
	 * Get the checklist item for waiver/deferral generation for non borrower
	 * @param aCustomerID of long type
	 * @param anItemStatus of String type
	 * @return HashMap - the checkListID and the list of checklist item (not the
	 *         full detail)
	 * @throws SearchDAOException
	 */
	public HashMap ejbHomeGetCheckListItemListbyStatusForNonBorrower(long aCustomerID, String anItemStatus)
			throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCheckListItemListbyStatusForNonBorrower(aCustomerID,
				anItemStatus);
	}

	/**
	 * Get the list of checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @param aStatusList of String[] type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 */
	public ICheckListAudit[] ejbHomeGetCheckListAuditList(long aLimitProfileID, String[] aStatusList)
			throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCheckListAuditList(aLimitProfileID, aStatusList);
	}

	/**
	 * Get the list of Checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @param aStatusList of String[] type
	 * @return IAuditItem[] - the list of checklist items that requires auditing
	 * @throws SearchDAOException on errors
	 */
	public IAuditItem[] ejbHomeGetCheckListItemListForAudit(long aLimitProfileID, String[] aStatusList)
			throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCheckListItemListForAudit(aLimitProfileID, aStatusList);
	}

	/**
	 * Get the list of checklist items that requires auditing for non borrower
	 * @param aCustomerID of long type
	 * @param aStatusList of String[] type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 */
	public ICheckListAudit[] ejbHomeGetCheckListAuditListForNonBorrower(long aCustomerID, String[] aStatusList)
			throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCheckListAuditListForNonBorrower(aCustomerID, aStatusList);
	}

	/**
	 * Get the list of Checklist items that requires auditing for non borrower
	 * @param aCustomerID of long type
	 * @param aStatusList of String[] type
	 * @return IAuditItem[] - the list of checklist items that requires auditing
	 * @throws SearchDAOException on errors
	 */
	public IAuditItem[] ejbHomeGetCheckListItemListForAuditForNonBorrower(long aCustomerID, String[] aStatusList)
			throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCheckListItemListForAuditForNonBorrower(aCustomerID,
				aStatusList);
	}

	/**
	 * Get the list of document categories for this borrower.
	 * @param aLimitProfileID of long type
	 * @return HashMap - the list of document categories
	 * @throws SearchDAOException on errors
	 */
	public HashMap ejbHomeGetDocumentCategories(long aLimitProfileID) throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getDocumentCategories(aLimitProfileID);
	}

	/**
	 * Get the list of document categories for this non-borrower.
	 * @param aCustomerID of long type
	 * @return HashMap - the list of document categories
	 * @throws SearchDAOException on errors
	 */
	public HashMap ejbHomeGetDocumentCategoriesForNonBorrower(long aCustomerID, long aLimitProfileID)
			throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getDocumentCategoriesForNonBorrower(aCustomerID, aLimitProfileID);
	}

	/**
	 * Get the map of documents held given the search criteria.
	 * 
	 * @param criteria - DocumentHeldSearchCriteria
	 * @return HashMap - Map of list of document held fulfilling the criteria
	 * @throws SearchDAOException on errors
	 */
	public HashMap ejbHomeGetDocumentsHeld(DocumentHeldSearchCriteria criteria) throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getDocumentsHeld(criteria);
	}

	/**
	 * Get the list of securities pledged for this borrower type.
	 * @param aLimitProfileID the limit profile identifier
	 * @param pledgorID the pledgor identifier
	 * @return HashMap - the securities pledged for this customer
	 * @throws SearchDAOException on errors
	 */
	public HashMap ejbHomeGetSecuritiesPledged(long aLimitProfileID, long pledgorID) throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getSecuritiesPledged(aLimitProfileID, pledgorID);
	}

	/**
	 * Search checklist based on the criteria specified. Currently only used to
	 * search for checklist pending multi-level approval.
	 * 
	 * @param criteria of type CheckListSearchCriteria
	 * @return CheckListSearchResult[]
	 * @throws SearchDAOException on errors encountered searching for checklist
	 */
	public CheckListSearchResult[] ejbHomeSearchCheckList(CheckListSearchCriteria criteria) throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().searchCheckList(criteria);
	}

	/**
	 * Get the name of the sequence to be used for key generation
	 * @return String - the sequence to be used
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_CHECKLIST;
	}

	/**
	 * Method to get EB Local Home for checkList item
	 */
	protected EBCheckListItemLocalHome getEBCheckListItemLocalHome() throws CheckListException {
		EBCheckListItemLocalHome home = (EBCheckListItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CHECKLIST_ITEM_LOCAL_JNDI, EBCheckListItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CheckListException("EBCheckListItemLocal is null!");
	}

	/**
	 * Method to get EB Local Home for checkList item
	 */
	protected EBCheckListItemLocalHome getActualEBCheckListItemLocalHome() throws CheckListException {
		return null;
	}

	/**
	 * To get the home handler for the Custodian Doc Entity Bean
	 * @return EBCustodianDocHome - the home handler for the custodian doc
	 *         entity bean
	 */
	protected EBCustodianDocHome getEBCustodianDocHome() {
		EBCustodianDocHome ejbHome = (EBCustodianDocHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_CUSTODIAN_DOC_HOME, EBCustodianDocHome.class.getName());
		return ejbHome;
	}

	/**
	 * Method to get EB Local Home for template
	 */
	/*
	 * protected EBTemplateLocalHome getEBTemplateLocalHome() throws
	 * CheckListException { EBTemplateLocalHome home =
	 * (EBTemplateLocalHome)BeanController.getEJBLocalHome(
	 * ICMSJNDIConstant.EB_TEMPLATE_LOCAL_JNDI,
	 * EBTemplateLocalHome.class.getName()); if(home != null) { return home; }
	 * throw new CheckListException ("EBTemplateLocal is null!"); }
	 */

	/**
	 * FOR CR CMS-662 Get all CHECKLIST related to a SECURITY based on
	 * CMS_COLLATERAL_ID.
	 * @param aCollateralID of long type
	 * @return int - the number of checklist related to security.
	 * @throws SearchDAOException on errors
	 */
	public int ejbHomeGetSecurityChkListCount(long aCollateralID) throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getSecurityChkListCount(aCollateralID);
	}

	/**
	 * FOR CR CMS-310 Get the list of checklist items that requires auditing
	 * @param aLimitProfileID of long type
	 * @param asOfDate of String type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 */
	public ICheckListAudit[] ejbHomeGetCheckListAudit(long aLimitProfileID, String asOfDate) throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCheckListAudit(aLimitProfileID, asOfDate);
	}

	/**
	 * FOR CR CMS-310 Get the list of checklist items that requires auditing for
	 * non borrower
	 * @param aCustomerID of long type
	 * @param asOfDate of String type
	 * @return ICheckListAudit[] - the list of checklist items that requires
	 *         auditing
	 * @throws SearchDAOException on errors
	 */
	public ICheckListAudit[] ejbHomeGetCheckListAuditForNonBorrower(long aCustomerID, String asOfDate)
			throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCheckListAuditForNonBorrower(aCustomerID, asOfDate);
	}

	/**
	 * Get the checklist ID of those pledgor checklist that is no longer valid
	 * due to this coborrower
	 * @param aCoBorrowerLegalRef of long type
	 * @param aLimitProfileRef of long type
	 * @return Long[] - the list of checklist IDs of those pledgor checklist
	 *         affected
	 * @throws SearchDAOException on error
	 */
	public Long[] ejbHomeGetAffectedPledgorCheckList(long aCoBorrowerLegalRef, long aLimitProfileRef)
			throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getAffectedPledgorCheckList(aCoBorrowerLegalRef, aLimitProfileRef);
	}

	/**
	 * Get the limit profile IDs that are linked to this pledgor
	 * @param aPledgorLegalRef of long type
	 * @return Long[] - the list of limit profile IDs linked to this pledgor
	 * @throws SearchDAOException on error
	 */
	public Long[] ejbHomeGetAffectedLimitProfileID(long aPledgorLegalRef) throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getAffectedLimitProfileID(aPledgorLegalRef);
	}

	// Start for cr-17
	// public List ejbHomeGetCheckListDetailsByCheckListId(String[]
	// aCheckListId, String categoryType,String subCategoryType) throws
	// SearchDAOException {
	// return
	// CheckListDAOFactory.getCheckListDAO().getCheckListDetailsByCheckListId
	// (aCheckListId,categoryType, subCategoryType);
	// }

	public List ejbHomeGetCheckListDetailsByCheckListId(String[] aCheckListId, String categoryType)
			throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCheckListDetailsByCheckListId(aCheckListId, categoryType);
	}

	// can be combined with the one above
	public List ejbHomeGetCheckListDetailsByCheckListId(String[] aCheckListId) throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCheckListDetailsByCheckListId(aCheckListId);
	}

	/*
	 * public OBShareDoc ejbHomeGetLeName(long aProfileId) throws
	 * SearchDAOException { return
	 * CheckListDAOFactory.getCheckListDAO().getLeName(aProfileId); }
	 * 
	 * public OBShareDoc ejbHomeGetSecuritySubDetails(long aProfileId, long
	 * collateralId) throws SearchDAOException { return
	 * CheckListDAOFactory.getCheckListDAO().getSecuritySubDetails(aProfileId,
	 * collateralId); }
	 */

	private ICheckListItem[] getShareDocumentItemId(long aCheckListId) throws CheckListException {
		//DefaultLogger.debug(this, ">>>>>>>>> starting getShareDocumentItemId for checklist id: " + aCheckListId);
		try {
			EBDocumentshareLocalHome shareHome = getEBDocumentshareLocalHome();
			EBCheckListItemLocalHome ItemLocalhome = getEBCheckListItemLocalHome();
			ArrayList itemList = new ArrayList();
			// List list = shareHome.getShareDocumentItemId(aCheckListId);
			Collection list = shareHome.findByCheckListID(aCheckListId);
			ArrayList itemRefList = new ArrayList();

			if ((list != null) && !list.isEmpty()) {
				// DefaultLogger.debug(this,
				// ">>>>>>>>>> iterating the shared item doc list");
				Iterator it = list.iterator();
				while (it.hasNext()) {
					EBDocumentshareLocal localShareDoc = (EBDocumentshareLocal) it.next();
					// DefaultLogger.debug(this,
					// "Debug Pt2 - localShareDoc.docShareIDRef: " +
					// localShareDoc.getDocShareIdRef() +
					// " | localShareDoc ChecklistID:  " +
					// localShareDoc.getCMPCheckListItemID());
					EBCheckListItemLocal local = ItemLocalhome.findByPrimaryKey(localShareDoc.getCMPCheckListItemID());
					if (!local.getIsDeletedInd()) {
						ICheckListItem obj = local.getValue(true, aCheckListId);
						obj.setViewable(true);
						itemList.add(obj);
						itemRefList.add(new Long(obj.getCheckListItemRef()));
					}
				}

				// populate custodian doc item information into checklist item
				Long[] itemRefListCust = (Long[]) itemRefList.toArray(new Long[0]);
				ICustodianDocItem[] custList = CustodianDAOFactory.getCustodianDAO().getDocByChecklistItemRefID(
						itemRefListCust);
				if ((custList != null) && (custList.length > 0)) {
					for (int i = 0; i < custList.length; i++) {
						for (int j = 0; j < itemList.size(); j++) {
							ICheckListItem item = (ICheckListItem) itemList.get(j);
							if (custList[i].getCheckListItemRefID() == item.getCheckListItemRef()) {
								String custDocStatus = CheckListCustodianHelper.getCheckListCustodianStatus(item
										.getCPCCustodianStatus(), custList[i].getStatus());
								item.setCustodianDocStatus(custDocStatus);
								item.setCustodianDocItemTrxDate(custList[i].getLastUpdateDate());
								// DefaultLogger.debug(this,
								// "$$$$$$$$$$$$$$$$$$$$$ >>>>>>>>>>>>>> itemDesc: "
								// + item.getItemDesc() +
								// "| item custodian status = " +
								// item.getCustodianDocStatus());
							}
						}
					}
				}

			}
		//	DefaultLogger.debug(this, ">>>>>>>>>Ending getShareDocumentItemId  " + itemList.size());
			return (ICheckListItem[]) itemList.toArray(new ICheckListItem[0]);

		}
		catch (FinderException ex) {
			DefaultLogger.warn(this, "Cannot find the share document or the checklist item, returning 'null'", ex);
			return null;
		}
		catch (CheckListException ex) {
			DefaultLogger.warn(this, "Unknown error found, returning 'null'", ex);
			return null;
		}
	}

	private ICheckListItem[] removeShareDocumentCheckListId(ICheckListItem[] aICheckListItem) throws CheckListException {
		ArrayList itemList = new ArrayList();
		if (aICheckListItem == null) {
			return null;
		}
	//	DefaultLogger.debug(this, "Starting RemoveShareDocumentCheckListId size " + aICheckListItem.length);
		if ((aICheckListItem != null) && (aICheckListItem.length > 0)) {
			for (int i = 0; i < aICheckListItem.length; i++) {
				ICheckListItem obj = aICheckListItem[i];
				if (!obj.getViewable()) {
					itemList.add(obj);
				}
			}
		}
//		DefaultLogger.debug(this, "Ending RemoveShareDocumentCheckListId size " + itemList.size());
		return (ICheckListItem[]) itemList.toArray(new ICheckListItem[0]);

	}

	protected EBDocumentshareLocalHome getEBDocumentshareLocalHome() throws CheckListException {
		EBDocumentshareLocalHome home = (EBDocumentshareLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CHECKLIST_DOCUMENT_SHARE_LOCAL_JNDI, EBDocumentshareLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CheckListException("EBDocumentshareLocalHome is null!");
	}

	// End for cr-17
	
	
	
	//Fields added for CAM Document CR By Abhijit R
	public abstract Date getCamDate() ;
	public abstract void setCamDate(Date camDate) ;

	public abstract String getCamNumber() ;
	public abstract void setCamNumber(String camNumber) ;

	public abstract String getCamType() ;
	public abstract void setCamType(String camType) ;
	
	public abstract String getIsLatest();
	public abstract void setIsLatest(String isLatest);
	
	public abstract String getIsDisplay();
	public abstract void setIsDisplay(String isDisplay);

	//To store maker checker information while maintaining checklist
	
	public abstract Date getUpdatedDate();
	public abstract void setUpdatedDate(Date updatedDate);

	public abstract Date getApprovedDate();
	public abstract void setApprovedDate(Date approvedDate);

	public abstract String getUpdatedBy();
	public abstract void setUpdatedBy(String updatedBy);

	public abstract String getApprovedBy();
	public abstract void setApprovedBy(String approvedBy);
	
	// END
	
	public String getFlagSchedulersCheck() {
		return null;
	}

	public void setFlagSchedulersCheck(String flagSchedulersCheck) {}
}