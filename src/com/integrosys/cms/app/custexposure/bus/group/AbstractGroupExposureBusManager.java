/**
 * 
 */
package com.integrosys.cms.app.custexposure.bus.group;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.cci.bus.CCICounterpartyDetailsException;
import com.integrosys.cms.app.cci.bus.ICCICounterparty;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
//import com.integrosys.cms.app.creditriskparam.bus.entitylimit.EntityLimitBusManagerFactory;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.EntityLimitException;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimit;
//import com.integrosys.cms.app.creditriskparam.bus.entitylimit.SBEntityLimitBusManager;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimitBusManager;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.ExemptedInstBusManagerFactory;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.ExemptedInstException;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.SBExemptedInstBusManager;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRating;
//import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.SBInternalCreditRatingBusManager;
//import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.SBInternalCreditRatingBusManagerHome;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRatingBusManager;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameterBusManager;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.InternalLimitException;
import com.integrosys.cms.app.custexposure.bus.CustExposureDAOFactory;
import com.integrosys.cms.app.custexposure.bus.CustExposureException;
import com.integrosys.cms.app.custexposure.bus.IContingentLiabilities;
import com.integrosys.cms.app.custexposure.bus.ICustExposure;
import com.integrosys.cms.app.custexposure.bus.ICustExposureDAO;
import com.integrosys.cms.app.custexposure.bus.ILimitExposureByBankEntity;
import com.integrosys.cms.app.custexposure.bus.ILimitExposureProfile;
import com.integrosys.cms.app.custexposure.bus.OBCustExposure;
import com.integrosys.cms.app.custexposure.bus.SBCustExposureBusManager;
import com.integrosys.cms.app.custexposure.bus.SBCustExposureBusManagerHome;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierException;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.IGroupCreditGrade;
import com.integrosys.cms.app.custgrpi.bus.IGroupMember;
import com.integrosys.cms.app.custgrpi.bus.IGroupOtrLimit;
import com.integrosys.cms.app.custgrpi.bus.IGroupSubLimit;
import com.integrosys.cms.app.custgrpi.bus.OBCustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.SBCustGrpIdentifierBusManager;
import com.integrosys.cms.app.custgrpi.bus.SBCustGrpIdentifierBusManagerHome;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.SBCustomerManager;
import com.integrosys.cms.app.customer.bus.SBCustomerManagerHome;
import com.integrosys.cms.app.custrelationship.bus.CustRelationshipDAO;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limitbooking.bus.LimitBookingDAO;
import com.integrosys.cms.ui.common.ForexHelper;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.custgrpi.CustGrpIdentifierUIHelper;

/**
 * @author user
 *
 */
public class AbstractGroupExposureBusManager implements
		IGroupExposureBusManager {

	private static final long serialVersionUID = -2367639874630655020L;

	/**
	 * Get Group Exposure object. This returns the group exposure with the first 5
	 * entity exposure retrieved
	 * @param subProfileId
	 * @return
	 * @throws CustExposureException
	 */
	public IGroupExposure getGroupExposure(long groupId) throws CustExposureException {
		
		try {
			SBCustGrpIdentifierBusManager groupManager = this.getSBCustGrpIdentifierBusManager();
			ICustGrpIdentifier custGrpi = new OBCustGrpIdentifier();
			custGrpi.setGrpID(groupId);
			ICustGrpIdentifier custGroup = groupManager.getCustGrpIdentifierByGrpID(custGrpi);
			
			if (custGroup == null)
				return null;
			
			CurrencyCode groupBaseCurrCode;
			if (custGroup.getGroupCurrency() != null && custGroup.getGroupCurrency().trim().length() != 0)
				groupBaseCurrCode = new CurrencyCode(custGroup.getGroupCurrency());
			else 
				groupBaseCurrCode = new CurrencyCode(PropertyManager.getValue("baseExchangeCcy"));
			
			IGroupExposure groupExposure = buildGroupExposure(custGroup);
			
			// filter duplicate customer and customer with similar cci 
			filterCustomers(groupExposure);
			
			// Retrieving group entity limits
			ICustExposure[] filteredCustExposures = groupExposure.getGroupMemberExposure();
			
			Debug("filteredCustExposures size : " + filteredCustExposures.length);
			
			// Get all customer exposures by all filtered customer exposures
			for (int i = 0; i < filteredCustExposures.length; i++) {
				long subProfileId = filteredCustExposures[i].getCMSCustomer().getCustomerID();
				
				ICustExposure populatedExposure = this.getCustExposure(subProfileId);
				populatedExposure.setGroupMemberRelation(filteredCustExposures[i].getGroupMemberRelation());
				
				if (populatedExposure != null)
					filteredCustExposures[i] = populatedExposure;
			}
			
			// populate group exposure limit aggregated outstanding exposure amount
			populateGroupExpCustGrpCustomOutstanding(groupExposure);
			
			// Retrieving cust grp entity limit 
			IGroupExpCustGrpEntityLimit[] groupExpCustGrpEntityLimits = getGroupExpCustGrpEntityLimit(filteredCustExposures, groupBaseCurrCode);
			groupExposure.getGroupExpCustGrp().setGroupExpCustGrpEntityLimit(groupExpCustGrpEntityLimits);
			
			// Retrieving group exposure by bank entity
			IGroupExpBankEntity[] groupExpBankEntitys = getGroupExpBankEntity(groupExposure);
			groupExposure.setGroupExpBankEntity(groupExpBankEntitys);
			
			return groupExposure;
		} catch (Exception ie) {
			ie.printStackTrace();
			throw new CustExposureException("getCustExposure throw exception", new CustExposureException (ie)); 
		}
	}
	
	/**
	 * Build group exposure with the customer group 
	 * @param custGroup
	 * @return
	 * @throws CustExposureException
	 */
	protected IGroupExposure buildGroupExposure(ICustGrpIdentifier custGroup) throws CustExposureException {
		
		if (custGroup == null)
			return null;
		
		OBGroupExposure groupExposure = new OBGroupExposure();
		
		OBGroupExpCustGrp groupExpCustGrp = new OBGroupExpCustGrp(custGroup);
		
		List custExposureList = new ArrayList();
		try {
			this.populateGroupMembers(custGroup, custExposureList, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustExposureException("populateGroupMembers error... ", e);
		}
		
		ICustExposure[] custExposures = (ICustExposure[])custExposureList.toArray(new ICustExposure[0]);
		Debug("custExposure in buildGroupExposure size : " + custExposures.length);
		groupExposure.setGroupMemberExposure(custExposures);
		
		IGroupSubLimit[] groupSubLimits = custGroup.getGroupSubLimit();
		List groupExpCustGrpSubLimitList = new ArrayList();
		
		
		if (groupSubLimits != null) {
			for (int i = 0; i < groupSubLimits.length; i++) {
				IGroupExpCustGrpSubLimit groupExpCustGrpSubLimit = new OBGroupExpCustGrpSubLimit(groupSubLimits[i]);
				groupExpCustGrpSubLimitList.add(groupExpCustGrpSubLimit);
			}
		}
		
		IGroupExpCustGrpSubLimit[] groupExpCustExpSubLimits = (IGroupExpCustGrpSubLimit[])groupExpCustGrpSubLimitList.toArray(new IGroupExpCustGrpSubLimit[0]);
		groupExpCustGrp.setGroupExpCustGrpSubLimit(groupExpCustExpSubLimits);
		
		IGroupOtrLimit[] groupOtrLimits = custGroup.getGroupOtrLimit();
		List groupExpCustGrpOtrLimitList = new ArrayList();
		
		if (groupOtrLimits != null) {
			for (int i = 0; i < groupOtrLimits.length; i++) {
				IGroupExpCustGrpOtrLimit groupExpCustGrpOtrLimit = new OBGroupExpCustGrpOtrLimit(groupOtrLimits[i]);
				groupExpCustGrpOtrLimitList.add(groupExpCustGrpOtrLimit);
			}
		}
		IGroupExpCustGrpOtrLimit[] groupExpCustExpOtrLimits = (IGroupExpCustGrpOtrLimit[])groupExpCustGrpOtrLimitList.toArray(new IGroupExpCustGrpOtrLimit[0]);
		groupExpCustGrp.setGroupExpCustGrpOtrLimit(groupExpCustExpOtrLimits);
		groupExposure.setGroupExpCustGrp(groupExpCustGrp);
		
		return groupExposure;
	}
	
	/**
	 * Recursive methods to multiple level members
	 * @param groupMembers
	 * @param groupMemberList
	 * @throws Exception 
	 * @throws CustGrpIdentifierException 
	 */
	protected void populateGroupMembers(ICustGrpIdentifier custGroup, List custExposureList, String relationshipName) throws CustExposureException, Exception {
		
		if (custGroup == null)
			return;
		
		String relationship = (relationshipName == null) ? "" : relationshipName;
		String relationshipBeforeChanged = relationship;
		
		IGroupMember[] groupMembers = custGroup.getGroupMember();
		
		if (groupMembers == null || groupMembers.length == 0)
			return;
		
		for (int i = 0; i < groupMembers.length; i++) {
			if ( ICMSConstant.ENTITY_TYPE_CUSTOMER.equals(groupMembers[i].getEntityType())) {
				ICustExposure custExposure = new OBCustExposure();
				
				// set customer
				ICMSCustomer customer = new OBCMSCustomer();
				customer.setCustomerID(groupMembers[i].getEntityID());
				custExposure.setCMSCustomer(customer);
				if (relationship.equals("")) {
					relationship = CustGrpIdentifierUIHelper.getCodeDesc(groupMembers[i].getRelationName(),ICMSUIConstant.GENT_REL);
					custExposure.setGroupMemberRelation(relationship + " of " + custGroup.getGroupName());
				} else {
					relationship = CustGrpIdentifierUIHelper.getCodeDesc(groupMembers[i].getRelationName(),ICMSUIConstant.GENT_REL) +
									" of " + relationship;
					custExposure.setGroupMemberRelation(relationship);
				}
				
				// set cci
				ICCICounterpartyDetails cciDetails = getCCICounterpartyDetails(groupMembers[i].getEntityID());
				custExposure.setCCICounterpartyDetails(cciDetails);
				custExposure.setReady(false);
				custExposureList.add(custExposure);
				
				populateGroupMemberWithShareHolderDirector(customer, custExposureList, new HashSet());
				relationship = relationshipBeforeChanged;
				
				// add cci-linked director, key management and shareholders
				if (cciDetails != null) {
					ICCICounterparty[] cciArray = cciDetails.getICCICounterparty();
					if (cciArray != null) {
						for (int j = 0; j < cciArray.length; j++) {
							ICCICounterparty tempCci = cciArray[j];
							long tempSubProfileId = tempCci.getSubProfileID();
							
							if (tempSubProfileId != customer.getCustomerID() && tempSubProfileId != ICMSConstant.LONG_INVALID_VALUE) {
								ICMSCustomer tempCust = new OBCMSCustomer();
								tempCust.setCustomerID(tempSubProfileId);
								populateGroupMemberWithShareHolderDirector(tempCust, custExposureList, new HashSet());
								relationship = relationshipBeforeChanged;
							}
						}
					}
				}
			} else if (ICMSConstant.ENTITY_TYPE_GROUP.equals(groupMembers[i].getEntityType())) {
				long groupId = groupMembers[i].getEntityID();
				
				SBCustGrpIdentifierBusManager groupManager = this.getSBCustGrpIdentifierBusManager();
				ICustGrpIdentifier custGrpi = new OBCustGrpIdentifier();
				custGrpi.setGrpID(groupId);
				ICustGrpIdentifier subCustGroup = groupManager.getCustGrpIdentifierByGrpID(custGrpi);
				
				if (relationship.equals("")) {
					relationship = subCustGroup.getGroupName() + " subgroup ";
					populateGroupMembers(subCustGroup, custExposureList, relationship);
					relationship = relationshipBeforeChanged;
				} else {
					relationship += subCustGroup.getGroupName() + " subgroup ";
					populateGroupMembers(subCustGroup, custExposureList, relationship);
					relationship = relationshipBeforeChanged;
				}
			}
		}
	}
	
	/**
	 * Populate group member with share holder & director, the method is recursive 
	 * @param customer
	 * @param custExposureList
	 * @throws CustExposureException
	 */
	protected void populateGroupMemberWithShareHolderDirector(ICMSCustomer customer, List custExposureList, Set shareDirectorSet) throws CustExposureException {
		CustRelationshipDAO dao = new CustRelationshipDAO();
		long parentSubProfileId = customer.getCustomerID();
		List customerIdList  = dao.getCustomerRelationshipIds(parentSubProfileId);
		
		if (customerIdList != null && customerIdList.size() != 0) {
			for (int i = 0; i < customerIdList.size(); i++ ) {
				long customerId = ((Long)((List)customerIdList.get(i)).get(0)).longValue();
				String custRelation = (String)((List)customerIdList.get(i)).get(1); 
				String parentCustName = (String)((List)customerIdList.get(i)).get(2); 
				
				ICustExposure custExposure = new OBCustExposure();
				
				ICMSCustomer newCustomer = new OBCMSCustomer();
				newCustomer.setCustomerID(customerId);
				custExposure.setCMSCustomer(newCustomer);
				
				//relationship  custRelation;
				custExposure.setGroupMemberRelation(custRelation + " of " + parentCustName);
				
				// set cci
				ICCICounterpartyDetails cciDetails = getCCICounterpartyDetails(customerId);
				custExposure.setCCICounterpartyDetails(cciDetails);
				// custExposure.setReady(false);
				
				
				custExposureList.add(custExposure);
				
				// recursive loop to make sure the under layer shareholder and director 
				// should be taken in for exposure.
				if (cciDetails != null) {
					// has cci
					ICCICounterparty[] parties = cciDetails.getICCICounterparty();
					for (int j = 0; j < parties.length; j++) {
						long subProfileId = parties[j].getSubProfileID();
						
						if (shareDirectorSet.contains(new Long(subProfileId)))
							continue;
						else
							shareDirectorSet.add(new Long(subProfileId));
						
						ICMSCustomer cust = new OBCMSCustomer();
						cust.setCustomerID(subProfileId);
						
						populateGroupMemberWithShareHolderDirector(cust, custExposureList, shareDirectorSet);
					}
				} else {
					// no cci related
					
					if (shareDirectorSet.contains(new Long(customerId)))
						continue;
					else
						shareDirectorSet.add(new Long(customerId));
					
					populateGroupMemberWithShareHolderDirector(newCustomer, custExposureList, shareDirectorSet);
				}
			}
		}
	}
	
	/**
	 * There could be possibility of duplicate customer in the system, this method used 
	 * to filter out the duplicates 
	 * @param groupExposure
	 */
	protected void filterCustomers (IGroupExposure groupExposure ) {
		
		List custExposures = new ArrayList();
		
		ICustExposure[] custExposureArray = groupExposure.getGroupMemberExposure();
		if (custExposureArray == null) 
			return;
		
		custExposures.addAll(Arrays.asList(custExposureArray)); 
		
		Iterator custExposureIterator = custExposures.iterator();
		
		
		// first level - filter by customer id
		ICustExposure temp;
		Set customerIdSet = new HashSet();
		
		while (custExposureIterator.hasNext()) {
			temp = (ICustExposure)custExposureIterator.next();
			Long customerId = new Long(temp.getCMSCustomer().getCustomerID());
			
			if (!customerIdSet.contains(customerId)) {
				customerIdSet.add(customerId);
			} else {
				
				// populate the group member relationship  to the previous used customer
				for (int i = 0; i < custExposures.size(); i++) {
					if (((ICustExposure)custExposures.get(i)).getCMSCustomer().getCustomerID() ==
						temp.getCMSCustomer().getCustomerID()) {
						((ICustExposure)custExposures.get(i)).addGroupMemberRelation(temp.getGroupMemberRelation());
						break;
					}
				}
				custExposureIterator.remove();
			}
			
		}
		
		// second level - filter by cci no
		custExposureIterator = custExposures.iterator();
		Set customerCciSet = new HashSet();
		
		while (custExposureIterator.hasNext()) {
			temp = (ICustExposure)custExposureIterator.next();
			
			if (temp.getCCICounterpartyDetails() != null) {
				Long cciNo = new Long(temp.getCCICounterpartyDetails().getGroupCCINo());
				
				if (!customerCciSet.contains(cciNo)) {
					customerCciSet.add(cciNo);
				} else {
					
					// populate the group member relationship  to the previous used customer
					for (int i = 0; i < custExposures.size(); i++) {
						
						ICustExposure temp2 = (ICustExposure)custExposures.get(i);
						if (temp2.getCCICounterpartyDetails() != null) {
							if (temp2.getCCICounterpartyDetails().getGroupCCINo() ==
								temp.getCCICounterpartyDetails().getGroupCCINo()) {
								((ICustExposure)custExposures.get(i)).addGroupMemberRelation(temp.getGroupMemberRelation());
								break;
							}
						}
					}
					custExposureIterator.remove();
				}
			}
		}	
		
		custExposureArray = (ICustExposure[])custExposures.toArray(new ICustExposure[0]);
		groupExposure.setGroupMemberExposure(custExposureArray);
	}
	
	/**
	 * Filter customers that is listed in the exempted institution list for GP5 reports only
	 * these customers will be removed from group svc view and any aggregated exposure 
	 * or limits
	 * 
	 * @param custExposures
	 */
	protected ICustExposure[] filterExemptedCustomer(ICustExposure[] custExposures) {
		
		if (custExposures == null || custExposures.length == 0)
			return custExposures;
		
		// get actual Exempted Institution
		IExemptedInst[] exemptedInsts = new IExemptedInst[0];
		try {
			SBExemptedInstBusManager mgr = ExemptedInstBusManagerFactory.getActualExemptedInstBusManager();
			exemptedInsts = mgr.getAllExemptedInst ();
		} catch (ExemptedInstException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		if (exemptedInsts == null || exemptedInsts.length == 0)
			return custExposures;
		
		// Putting exempted institution into hashMap
		Map exemptedInstMap = new HashMap();
		
		for (int i = 0; i < exemptedInsts.length; i++) {
			long customerId = exemptedInsts[i].getCustomerID();
			Long customerIdLong = new Long(customerId);
			exemptedInstMap.put(customerIdLong, customerIdLong);
		}
		
		List removeList = new ArrayList();
		
		for (int i = 0; i < custExposures.length; i++) {
			
			if (custExposures[i].getCCICounterpartyDetails() == null) {
				// no cci
				long subProfileId = custExposures[i].getCMSCustomer().getCustomerID();
				Object obj = exemptedInstMap.get(new Long(subProfileId));
				
				// not found in exempted institution
				if (obj == null)
					continue;
				else
					removeList.add(new Integer(i));
				
			} else {
				ICCICounterpartyDetails counterpartyDetails = custExposures[i].getCCICounterpartyDetails();
				ICCICounterparty[] parties = counterpartyDetails.getICCICounterparty();
				
				if (parties != null) {
					for (int j = 0; j < parties.length; j++) {
						long subProfileId = parties[j].getSubProfileID();
						Object obj = exemptedInstMap.get(new Long(subProfileId));
						
						// not found in exempted institution
						if (obj == null)
							continue;
						else
							removeList.add(new Integer(i));
					}
				}
			}
		}
		
		if (removeList.size() == 0)
			return custExposures;
		
		// remove those in the list
		List filteredCustExposures = new ArrayList();
		for (int i = 0; i < custExposures.length; i++) {
			
			boolean found = false;
			for (int j = 0; j < removeList.size(); j++) {
				int pos = ((Integer)removeList.get(j)).intValue();
				
				if (pos == j) {
					found = true;
					break;
				}
			}
			
			if (!found) {
				filteredCustExposures.add(custExposures[i]);
			}
		}
		
		return (ICustExposure[]) filteredCustExposures.toArray(new ICustExposure[0]);
	}
	
	
	/**
	 * Get the corresponding customer exposure
	 * @param subProfileId
	 * @return
	 */
	protected ICustExposure getCustExposure(long subProfileId) throws Exception {

		SBCustExposureBusManager custExposureManager = getSBCustExposureBusManager();
		
		ICustExposure custExposure = custExposureManager.getCustExposure(subProfileId);
		return custExposure;
	}
	
	/**
	 * Get the corresponding customer exposure
	 * @param subProfileId
	 * @return
	 */
	protected void getCustExposure(ICustExposure custExposure) throws Exception {

		SBCustExposureBusManager custExposureManager = getSBCustExposureBusManager();
		
		//ICustExposure custExposure = 
		custExposureManager.getCustExposure(custExposure);
		//return custExposure;
	}
	
	/**
	 * Calculate and populate the outstanding amount for Interbank limit & PDS
	 * @param groupExposure
	 */
	protected void populateGroupExpCustGrpCustomOutstanding(IGroupExposure groupExposure) {
		
		IGroupExpCustGrpSubLimit[] subLimits = groupExposure.getGroupExpCustGrp().getGroupExpCustGrpSubLimit();
		IGroupExpCustGrpOtrLimit[] otrLimits = groupExposure.getGroupExpCustGrp().getGroupExpCustGrpOtrLimit();
		ICustExposure[] custExposures = groupExposure.getGroupMemberExposure();
		
		if (custExposures == null || custExposures.length == 0)
			return;
		
		if ((subLimits == null || subLimits.length == 0) &&
				(otrLimits == null || otrLimits.length == 0)) 
			return;
		
		
		CurrencyCode groupBaseCurrCode = new CurrencyCode(groupExposure.getGroupExpCustGrp().getCustGroup().getGroupCurrency());
		
		HashMap interbankOutstanding = calcLimitAggrOutstandingByInterbank(custExposures, groupBaseCurrCode);
		
		if (subLimits != null && subLimits.length > 0) {
			for (int i = 0; i < subLimits.length; i++) {
				if (ICMSConstant.SUB_LIMIT_DESC_BANK_GROUP_INTER_LIMIT_ENTRY_CODE.equals(subLimits[i].getDescription())) {
					Amount nonExemptedAmount = (Amount)interbankOutstanding.get(ICMSConstant.SUB_LIMIT_TYPE_INTER_NON_EXEMPT_ENTRY_CODE);
					subLimits[i].setAggregatedOutstanding(nonExemptedAmount);
				} 	else if (ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE.equals(subLimits[i].getDescription()) ||
						ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE.equals(subLimits[i].getDescription()) ||
						ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE.equals(subLimits[i].getDescription())) {
					Amount outstanding = calcSubGroupLimitAggrOutstandingByBankEntity(custExposures, groupBaseCurrCode, subLimits[i].getDescription());
					subLimits[i].setAggregatedOutstanding(outstanding);
				} 
			}
		}
		
		if (otrLimits != null && otrLimits.length > 0) {
			for (int i = 0; i < otrLimits.length; i++) {
				if (ICMSConstant.OTHER_LIMIT_TYPE_INTER_EXEMPT_ENTRY_CODE.equals(otrLimits[i].getDescription())) {
					Amount exemptedAmount = (Amount)interbankOutstanding.get(ICMSConstant.OTHER_LIMIT_TYPE_INTER_EXEMPT_ENTRY_CODE);
					otrLimits[i].setAggregatedOutstanding(exemptedAmount);
				} 
				else if (ICMSConstant.OTHER_LIMIT_TYPE_PDS_ENTRY_CODE.equals(otrLimits[i].getDescription())) {
					Amount outstandingAmount = (Amount)interbankOutstanding.get(ICMSConstant.OTHER_LIMIT_TYPE_PDS_ENTRY_CODE);
					otrLimits[i].setAggregatedOutstanding(outstandingAmount);
				} 
			}
		}
		
	}
	
	/**
	 * Get corresponding entity limits that belongs to the group members 
	 * @param custExposures
	 * @return
	 */
	protected IGroupExpCustGrpEntityLimit[] getGroupExpCustGrpEntityLimit(ICustExposure[] custExposures, CurrencyCode groupBaseCurrCode) {
		
		if (custExposures == null)
			return null;
		
		List groupExpCustGrpEntityLimitList = new ArrayList();
		
		try {
			for (int i = 0; i < custExposures.length; i++) {
				if (custExposures[i].getCCICounterpartyDetails() == null) {
					// no cci
					long subProfileId = custExposures[i].getCMSCustomer().getCustomerID();
					
//					SBEntityLimitBusManager entityLimitBusManager = EntityLimitBusManagerFactory.getActualEntityLimitBusManager();
                    IEntityLimitBusManager entityLimitBusManager = getEntityLimitBusManager();
					IEntityLimit entityLimit = entityLimitBusManager.getEntityLimitBySubProfileID(subProfileId);
					
					if (entityLimit != null) {
						entityLimit.setCustomerName(custExposures[i].getCMSCustomer().getCustomerName());
						entityLimit.setCustIDSource(custExposures[i].getCMSCustomer().getCMSLegalEntity().getLegalIDSource());
						entityLimit.setLEReference(custExposures[i].getCMSCustomer().getCMSLegalEntity().getLEReference());
						
						Amount outstandingAmount = computeOutstandingAmountForEntityLimit(custExposures[i], groupBaseCurrCode);
						
						IGroupExpCustGrpEntityLimit a = new OBGroupExpCustGrpEntityLimit(entityLimit);
						a.setAggregatedOutstanding(outstandingAmount);
						groupExpCustGrpEntityLimitList.add(a);
					}
					
				} else {

					ICCICounterpartyDetails counterpartyDetails = custExposures[i].getCCICounterpartyDetails();
					ICCICounterparty[] parties = counterpartyDetails.getICCICounterparty();
					
					if (parties != null) {
						for (int j = 0; j < parties.length; j++) {
							long subProfileId = parties[j].getSubProfileID();
							
//							SBEntityLimitBusManager entityLimitBusManager = EntityLimitBusManagerFactory.getActualEntityLimitBusManager();
                            IEntityLimitBusManager entityLimitBusManager = getEntityLimitBusManager();
							IEntityLimit entityLimit = entityLimitBusManager.getEntityLimitBySubProfileID(subProfileId);
							
							if (entityLimit != null) {
								entityLimit.setCustomerName(parties[j].getCustomerName());
								entityLimit.setCustIDSource(parties[j].getSourceID());
								entityLimit.setLEReference(parties[j].getLmpLeID());
								
								Amount outstandingAmount = computeOutstandingAmountForEntityLimit(custExposures[i], groupBaseCurrCode);
								
								IGroupExpCustGrpEntityLimit a = new OBGroupExpCustGrpEntityLimit(entityLimit);
								a.setAggregatedOutstanding(outstandingAmount);
								groupExpCustGrpEntityLimitList.add(a);
							}
						}
					}
				}
			}
		} catch (EntityLimitException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return (IGroupExpCustGrpEntityLimit[])groupExpCustGrpEntityLimitList.toArray(new IGroupExpCustGrpEntityLimit[0]);
	}
	
	/**
	 * Compute outstanding amount for respective customer (refactored method)
	 * @param custExposure
	 * @param groupBaseCurrCode
	 * @return
	 */
	private Amount computeOutstandingAmountForEntityLimit(ICustExposure custExposure, CurrencyCode groupBaseCurrCode ) {
		double outstanding = 0;
		ForexHelper fr = new ForexHelper();
		
		ILimitExposureProfile[] limitProfiles = custExposure.getLimitExposureProfile();
		
		// to avoid the similar limit to be counted twice
		List limitIdList = new ArrayList();
		
		if (limitProfiles != null) {
			for (int i = 0; i < limitProfiles.length; i++) {
				ILimit[] limits = limitProfiles[i].getLimitProfile().getLimits();
				
				if (limits != null) {
					for (int j = 0; j < limits.length; j++) {
						if (limits[j].getLimitType().equals(ICMSConstant.CCC_OUTER_LIMIT)) {

							try {
								if (fr.convert(limits[j].getOutstandingAmount(), groupBaseCurrCode) != null
										&& !isLimitExist(limits[j], limitIdList)) {
									outstanding += fr.convert(limits[j].getOutstandingAmount(), groupBaseCurrCode).getAmount();
									addLimitId(limits[j], limitIdList);
								}
							} catch (Exception e) {
								
								e.printStackTrace();
							}
							
						}
					}
				}
			}
		}
		return new Amount(outstanding, groupBaseCurrCode);
		 
	}
	
	/**
	 * calculate sub group Limit aggregated outstanding amount
	 * @param custExposures
	 * @param groupBaseCurrCode
	 * @return
	 */
	protected Amount calcSubGroupLimitAggrOutstandingByBankEntity(ICustExposure[] custExposures, CurrencyCode groupBaseCurrCode, String bankEntityType) {
		
		if (custExposures == null || custExposures.length == 0) 
			return null;
		
		double outstanding = 0;
		ForexHelper fr = new ForexHelper();
		
		// to avoid the similar limit to be counted twice
		List limitIdList = new ArrayList();
		
		for (int i = 0; i < custExposures.length; i++) {
			HashMap map = custExposures[i].getLimitExposureByBankEntityMap();
			
			ILimitExposureByBankEntity[] be = (ILimitExposureByBankEntity[])map.get(bankEntityType);
			
			if (be != null && be.length > 0) {
				for (int j = 0; j < be.length; j++) {
					try {
						
						if (be[j].getLimit() != null && be[j].getLimit().getOutstandingAmount() != null &&
								be[j].getLimit().getLimitType().equals(ICMSConstant.CCC_OUTER_LIMIT)
								&& !isLimitExist(be[j].getLimit(), limitIdList)){
							outstanding += fr.convert(be[j].getLimit().getOutstandingAmount(), groupBaseCurrCode).getAmount();
							addLimitId(be[j].getLimit(), limitIdList);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return new Amount(outstanding, groupBaseCurrCode);
		
	}
	
	
	/**
	 * calculate sub-group/other Limit aggregated outstanding amount by inter-bank institution
	 * FI indicator is required, meant for Murex customers
	 * 
	 * @param custExposures the passed in cust exposures has the customer filtered out
	 * @param groupBaseCurrCode
	 * @return map of 2 aggregated outstanding amount with key EXEMPT (Outer Limit) , NONEXEMPT (Sub Group)
	 */
	protected HashMap calcLimitAggrOutstandingByInterbank(ICustExposure[] custExposures, CurrencyCode groupBaseCurrCode) {
		
		
		if (custExposures == null || custExposures.length == 0) 
			return null;
		
		double outstandingExempted = 0;
		double outstandingNonExempted = 0;
		double outstandingPDS = 0;
		ForexHelper fr = new ForexHelper();
		
		// get actual Exempted Institution
		IExemptedInst[] exemptedInsts = new IExemptedInst[0];
		try {
			SBExemptedInstBusManager mgr = ExemptedInstBusManagerFactory.getActualExemptedInstBusManager();
			exemptedInsts = mgr.getAllExemptedInst ();
		} catch (ExemptedInstException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		Set exemptedSet = new HashSet();
		if (exemptedInsts != null) {
			for (int i =0; i < exemptedInsts.length; i++) {
				exemptedSet.add(new Long(exemptedInsts[i].getCustomerID()));
			}
		}
		
		for (int i = 0; i < custExposures.length; i++) {
			ILimitExposureProfile[] limitExposureProfiles = custExposures[i].getLimitExposureProfile();
			
			if (limitExposureProfiles != null && limitExposureProfiles.length > 0) {
			
				for (int j = 0; j < limitExposureProfiles.length; j++) {
					try {
						String customerType = limitExposureProfiles[j].getCMSCustomer().getCMSLegalEntity().getCustomerType();
						ILimit[] limits = limitExposureProfiles[j].getLimitProfile().getLimits();
						long customerId = limitExposureProfiles[j].getCMSCustomer().getCustomerID();
						
						if (ICMSConstant.CUST_TYPE_FINANCE.equals(customerType)) {
							for (int k = 0; k < limits.length; k++) {
								if (limits[k].getLimitType().equals(ICMSConstant.CCC_OUTER_LIMIT)){
									
									if (exemptedSet.contains(new Long(customerId))) {
										outstandingExempted += fr.convert(limits[k].getOutstandingAmount(), groupBaseCurrCode).getAmount();
									} else {
										outstandingNonExempted += fr.convert(limits[k].getOutstandingAmount(), groupBaseCurrCode).getAmount();
									}
									
								}
							}
						}
									
						for (int l = 0; l < limits.length; l++) {
							if (limits[l].getProductDesc().equals(ICMSConstant.PROD_DESC_BOND) || limits[l].getProductDesc().equals(ICMSConstant.PROD_DESC_FI_BOND)){
									outstandingPDS += fr.convert(limits[l].getApprovedLimitAmount(), groupBaseCurrCode).getAmount();
								}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		Amount outstandingExemptedAmount = new Amount(outstandingExempted, groupBaseCurrCode);
		Amount outstandingNonExemptedAmount = new Amount(outstandingNonExempted, groupBaseCurrCode);
		Amount outstandingPDSAmount = new Amount(outstandingPDS, groupBaseCurrCode);
		
		HashMap returnMap = new HashMap();
		returnMap.put(ICMSConstant.OTHER_LIMIT_TYPE_INTER_EXEMPT_ENTRY_CODE, outstandingExemptedAmount);
		returnMap.put(ICMSConstant.SUB_LIMIT_TYPE_INTER_NON_EXEMPT_ENTRY_CODE, outstandingNonExemptedAmount);
		returnMap.put(ICMSConstant.OTHER_LIMIT_TYPE_PDS_ENTRY_CODE, outstandingPDSAmount);
		
		return returnMap;
	}
	
	/**
	 * Get exposure by bank group (aggregate and default the exposure summary table)
	 * @param groupExposure
	 * @return
	 * @throws InternalLimitException
	 * @throws Exception
	 */
	protected IGroupExpBankEntity[] getGroupExpBankEntity(IGroupExposure groupExposure) throws InternalLimitException, Exception {
		
		ICustGrpIdentifier custGroup = groupExposure.getGroupExpCustGrp().getCustGroup();
		
		CurrencyCode groupBaseCurrCode;
		if (custGroup.getGroupCurrency() != null && custGroup.getGroupCurrency().trim().length() != 0)
			groupBaseCurrCode = new CurrencyCode(custGroup.getGroupCurrency());
		else 
			groupBaseCurrCode = new CurrencyCode(PropertyManager.getValue("baseExchangeCcy"));
		
		ForexHelper fr = new ForexHelper();
		Debug("Group Base Curr Code : " + groupBaseCurrCode.getCode());
		
		IGroupExpBankEntity convBank = new OBGroupExpBankEntity();
		IGroupExpBankEntity islmBank = new OBGroupExpBankEntity();
		IGroupExpBankEntity invsBank = new OBGroupExpBankEntity();
		IGroupExpBankEntity groupBank = new OBGroupExpBankEntity();
		
		convBank.setBankEntity( ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE);
		islmBank.setBankEntity( ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE);
		invsBank.setBankEntity( ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE);
		groupBank.setBankEntity( ICMSConstant.BANKING_GROUP_CODE);
		
		
		convBank.setInternalLimit(new Amount(0, groupBaseCurrCode));
		islmBank.setInternalLimit(new Amount(0, groupBaseCurrCode));
		invsBank.setInternalLimit(new Amount(0, groupBaseCurrCode));
		groupBank.setInternalLimit(new Amount(0, groupBaseCurrCode));
		
//		List internalLimitParameters = getActualSBInternalLimitParameterLocal().getALLInternalLimitParameter();
        List internalLimitParameters = getInternalLimitParameterBusManager().getALLInternalLimitParameter();

		if (internalLimitParameters != null) {
			Iterator iLIter = internalLimitParameters.iterator();
			IInternalLimitParameter il;
			while (iLIter.hasNext()) {
				il = (IInternalLimitParameter)iLIter.next();
				
				String currencyCode = il.getCapitalFundAmountCurrencyCode();
				double gP5LimitAmount = il.getCapitalFundAmount() * il.getGp5LimitPercentage() / 100;
				
				if (il.getDescriptionCode().equals( ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE)) {
					convBank.setBankEntity(il.getDescriptionCode());
					convBank.setGP5Limit(new Amount(gP5LimitAmount, currencyCode));
				} else if (il.getDescriptionCode().equals(ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE)) {
					islmBank.setBankEntity(il.getDescriptionCode());
					islmBank.setGP5Limit(new Amount(gP5LimitAmount, currencyCode));
				} else if (il.getDescriptionCode().equals(ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE)) {
					invsBank.setBankEntity(il.getDescriptionCode());
					invsBank.setGP5Limit(new Amount(gP5LimitAmount, currencyCode));
				} else if (il.getDescriptionCode().equals(ICMSConstant.BANKING_GROUP_CODE)) {
					groupBank.setBankEntity(il.getDescriptionCode());
					groupBank.setGP5Limit(new Amount(gP5LimitAmount, currencyCode));
				} 
			}
		}
		
		IGroupExpCustGrpSubLimit groupSubLimits[] = groupExposure.getGroupExpCustGrp().getGroupExpCustGrpSubLimit();
		
		if (groupSubLimits != null) {
			for (int i = 0; i < groupSubLimits.length; i++) {
				
				if (groupSubLimits[i].getDescription().equals(ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE)) {
					Amount convBankLimitAmt = fr.convert(groupSubLimits[i].getLimitAmt(), groupBaseCurrCode);
					if (convBankLimitAmt != null) 
						convBank.setInternalLimit(convBankLimitAmt);
					convBank.setLimitReviewDate(groupSubLimits[i].getLastReviewedDt());
				} else if (groupSubLimits[i].getDescription().equals(ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE)) {
					Amount islmBankLimitAmt = fr.convert(groupSubLimits[i].getLimitAmt(),groupBaseCurrCode);
					if (islmBankLimitAmt != null)
						islmBank.setInternalLimit(islmBankLimitAmt);
					islmBank.setLimitReviewDate(groupSubLimits[i].getLastReviewedDt());
				} else if (groupSubLimits[i].getDescription().equals(ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE)) {
					Amount invsBankLimitAmt = fr.convert(groupSubLimits[i].getLimitAmt(), groupBaseCurrCode);
					if (invsBankLimitAmt != null)
						invsBank.setInternalLimit(invsBankLimitAmt);
					invsBank.setLimitReviewDate(groupSubLimits[i].getLastReviewedDt());
				} 
			}
		}
		
		if (custGroup.getInternalLmt() != null &&
				custGroup.getInternalLmt().equals(ICMSConstant.INTERNAL_LIMIT_BY_INTERNAL_CREDIT_RATING)) {
			IGroupCreditGrade[] grades = custGroup.getGroupCreditGrade();
			if (grades != null && grades.length > 0) {
				// there will be only one grades element
//				SBInternalCreditRatingBusManager icr = getSBInternalCreditRatingBusManager();
//				List icrList = icr.getAllInternalCreditRating();
                IInternalCreditRatingBusManager icr = getInternalCreditRatingBusManager();
                List icrList = icr.getAllInternalCreditRating();
				
				if (icrList != null && icrList.size() > 0) {
					for (int i = 0; i < icrList.size(); i++) {
						IInternalCreditRating cr = (IInternalCreditRating)icrList.get(i);
						if (cr.getIntCredRatCode().equals(grades[0].getRatingCD())) {
							Amount intCredRatLimitAmt = new Amount(cr.getIntCredRatLmtAmt(), cr.getIntCredRatLmtAmtCurCode());
							if (fr.convert(intCredRatLimitAmt, groupBaseCurrCode) != null)
								groupBank.setInternalLimit(fr.convert(intCredRatLimitAmt, groupBaseCurrCode));
							groupBank.setLimitReviewDate(grades[0].getRatingDt());
						}
					}
				}
			}
		} else {
			if (custGroup.getGroupLmt() != null && custGroup.getGroupLmt().getAmount() > 0 
					&& fr.convert(custGroup.getGroupLmt(), groupBaseCurrCode) != null)
				groupBank.setInternalLimit(fr.convert(custGroup.getGroupLmt(), groupBaseCurrCode));
			groupBank.setLimitReviewDate(custGroup.getLastReviewDt());
		}
		
		
		double convContingent = 0;
		double invsContingent = 0;
		double islmContingent = 0;
		double total = 0;
		
		double totalExposure = 0;
		double convExposure = 0;
		double invsExposure = 0;
		double islmExposure = 0;
		
		double totalBookedLimit = 0;
		double convBookLimit = 0;
		double invsBookLimit = 0;
		double islmBookLimit = 0;
		
		double totalLimitAvail = 0;
		double convLimitAvail = 0;
		double invsLimitAvail = 0;
		double islmLimitAvail = 0;
		
		double totalUsedLimit = 0;
		double convUsedLimit = 0;
		double invsUsedLimit = 0;
		double islmUsedLimit = 0;
		
		// take in limit booking first even without customer added into the group
		LimitBookingDAO limitBookingDao = new LimitBookingDAO();
		
		try {
			List bankEntityList = new ArrayList();
			bankEntityList.add( ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE );
			bankEntityList.add( ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE );
			bankEntityList.add( ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE );
			
			HashMap convBookLimitMap = limitBookingDao.retrieveGroupTotalBookByBankEntity(new Long( custGroup.getGrpID() ), bankEntityList); 
			
			Amount convBookLimitAmount = (Amount)convBookLimitMap.get( ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE );				
			if (convBookLimitAmount != null) { 
				convBank.setBookedLimit(convBookLimitAmount);
				convBookLimit = fr.convert(convBookLimitAmount, groupBaseCurrCode).getAmount();
			}								
			
			Amount invsBookLimitAmount = (Amount)convBookLimitMap.get( ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE );					
			if (invsBookLimitAmount != null) { 
				invsBank.setBookedLimit(invsBookLimitAmount);
				invsBookLimit = fr.convert(invsBookLimitAmount, groupBaseCurrCode).getAmount();
			} 							
			
			Amount islmBookLimitAmount = (Amount)convBookLimitMap.get( ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE );
			if (islmBookLimitAmount != null) { 
				islmBank.setBookedLimit(islmBookLimitAmount);
				islmBookLimit = fr.convert(islmBookLimitAmount, groupBaseCurrCode).getAmount();
			} 
			
			totalBookedLimit = convBookLimit + invsBookLimit + islmBookLimit;
			groupBank.setBookedLimit(new Amount(totalBookedLimit, groupBaseCurrCode));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// to avoid the similar limit to be counted twice
		List limitIdList = new ArrayList();
		
		// to avoid the similar contingent liabilities counted twice or more with security id as key
		List securityIdList = new ArrayList();
		
		ICustExposure[] custExposures = groupExposure.getGroupMemberExposure();
		
		if (custExposures != null && custExposures.length > 0) {
			for (int i = 0; i < custExposures.length; i++) {
				IContingentLiabilities[] cls = custExposures[i].getContingentLiabilities();
				
				if (cls != null && cls.length > 0) {
					for (int j = 0; j < cls.length; j++) {
						if (!securityIdList.contains(cls[j].getSourceSecurityId())) {
							if (cls[j].getBankEntity().equals(ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE)) 
								convContingent += fr.convert(cls[j].getGuaranteeAmt(), groupBaseCurrCode).getAmount();
							else if (cls[j].getBankEntity().equals(ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE)) 
								invsContingent += fr.convert(cls[j].getGuaranteeAmt(), groupBaseCurrCode).getAmount();
							else if (cls[j].getBankEntity().equals(ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE)) 
								islmContingent += fr.convert(cls[j].getGuaranteeAmt(), groupBaseCurrCode).getAmount();
							
							// add securityId into the
							securityIdList.add(cls[j].getSourceSecurityId());
						}
					}
				}
				
				HashMap lebeMap = custExposures[i].getLimitExposureByBankEntityMap();
				ILimitExposureByBankEntity[] convLimitExposure = 
					(ILimitExposureByBankEntity[])lebeMap.get(ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE);
				ILimitExposureByBankEntity[] invsLimitExposure = 
					(ILimitExposureByBankEntity[])lebeMap.get(ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE);
				ILimitExposureByBankEntity[] islmLimitExposure = 
					(ILimitExposureByBankEntity[])lebeMap.get(ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE);
				
				
				if (convLimitExposure != null) {
					for (int j = 0; j < convLimitExposure.length; j++) {
						if (convLimitExposure[j].getExposureAmount() != null &&
								convLimitExposure[j].getExposureAmount().getAmountAsDouble() > 0 &&
								!isLimitExist(convLimitExposure[j].getLimit(), limitIdList)) {
							convExposure += fr.convertAmount(convLimitExposure[j].getExposureAmount(), groupBaseCurrCode);
							addLimitId(convLimitExposure[j].getLimit(), limitIdList);
						}
					}
				}
				
				if (invsLimitExposure != null) {
					for (int j = 0; j < invsLimitExposure.length; j++) {
						if (invsLimitExposure[j].getExposureAmount() != null &&
								invsLimitExposure[j].getExposureAmount().getAmountAsDouble() > 0 &&
								!isLimitExist(invsLimitExposure[j].getLimit(), limitIdList)) {
							invsExposure += fr.convertAmount(invsLimitExposure[j].getExposureAmount(), groupBaseCurrCode);
							addLimitId(invsLimitExposure[j].getLimit(), limitIdList);
						}
					}
				}
				
				if (islmLimitExposure != null) {
					for (int j = 0; j < islmLimitExposure.length; j++) {
						if (islmLimitExposure[j].getExposureAmount() != null &&
								islmLimitExposure[j].getExposureAmount().getAmountAsDouble() > 0 &&
								!isLimitExist(islmLimitExposure[j].getLimit(), limitIdList)) {
							islmExposure += fr.convertAmount(islmLimitExposure[j].getExposureAmount(), groupBaseCurrCode);
							addLimitId(islmLimitExposure[j].getLimit(), limitIdList);
						}
					}
				}
			}
			total = convContingent + invsContingent + islmContingent;
			totalExposure = convExposure + invsExposure + islmExposure;
			
			convBank.setContingentLiabilities(new Amount(convContingent, groupBaseCurrCode));
			invsBank.setContingentLiabilities(new Amount(invsContingent, groupBaseCurrCode));
			islmBank.setContingentLiabilities(new Amount(islmContingent, groupBaseCurrCode));
			groupBank.setContingentLiabilities(new Amount(total, groupBaseCurrCode));
			
			convBank.setTotalExposure(new Amount(convExposure, groupBaseCurrCode));
			invsBank.setTotalExposure(new Amount(invsExposure, groupBaseCurrCode));
			islmBank.setTotalExposure(new Amount(islmExposure, groupBaseCurrCode));
			groupBank.setTotalExposure(new Amount(totalExposure, groupBaseCurrCode));
			
			convLimitAvail = convBank.getInternalLimit().getAmount() - convExposure - convBookLimit;
			invsLimitAvail = invsBank.getInternalLimit().getAmount() - invsExposure - invsBookLimit;
			islmLimitAvail = islmBank.getInternalLimit().getAmount() - islmExposure - islmBookLimit;
			
			if (custGroup.getGroupLmt() != null)
				totalLimitAvail = fr.convert(custGroup.getGroupLmt(), groupBaseCurrCode).getAmount() - totalExposure - totalBookedLimit;
			else
				totalLimitAvail = 0 - totalExposure - totalBookedLimit;
			
			if (convBank.getInternalLimit() != null && convBank.getInternalLimit().getAmount() > 0)
				convUsedLimit = (convExposure + convBookLimit) / convBank.getInternalLimit().getAmount() * 100;
			else if (convExposure + convBookLimit > 0)
				convUsedLimit = 100;
				
			
			if (invsBank.getInternalLimit() != null && invsBank.getInternalLimit().getAmount() > 0)
				invsUsedLimit = (invsExposure + invsBookLimit) / invsBank.getInternalLimit().getAmount() * 100;
			else if (invsExposure + invsBookLimit > 0)
				invsUsedLimit = 100;
			
			if (islmBank.getInternalLimit() != null && islmBank.getInternalLimit().getAmount() > 0)
				islmUsedLimit = (islmExposure + islmBookLimit) / islmBank.getInternalLimit().getAmount() * 100;
			else if (islmExposure + islmBookLimit > 0)
				islmUsedLimit = 100;
			
			if (custGroup.getGroupLmt() != null && custGroup.getGroupLmt().getAmount() > 0)
				totalUsedLimit = (totalExposure + totalBookedLimit) / fr.convert(custGroup.getGroupLmt(), groupBaseCurrCode).getAmount() * 100;
			else if (totalExposure + totalBookedLimit > 0)
				totalUsedLimit = 100;
			
			convBank.setAvailableLimit(new Amount (convLimitAvail, groupBaseCurrCode));
			invsBank.setAvailableLimit(new Amount (invsLimitAvail, groupBaseCurrCode));
			islmBank.setAvailableLimit(new Amount (islmLimitAvail, groupBaseCurrCode));
			groupBank.setAvailableLimit(new Amount(totalLimitAvail, groupBaseCurrCode));
			
			convBank.setPercentageLimitUsed(convUsedLimit);
			invsBank.setPercentageLimitUsed(invsUsedLimit);
			islmBank.setPercentageLimitUsed(islmUsedLimit);
			groupBank.setPercentageLimitUsed(totalUsedLimit);
		}
		
		IGroupExpBankEntity[] groupExpBankEntity = new IGroupExpBankEntity[4];
		groupExpBankEntity[0] = convBank;
		groupExpBankEntity[1] = islmBank;
		groupExpBankEntity[2] = invsBank;
		groupExpBankEntity[3] = groupBank;		
		
		return groupExpBankEntity;
	}
	
	
	private void addLimitId(ILimit e, List existingIdList) {
		if (e != null && 
				e.getLimitID() != 0 && 
				e.getLimitID() != ICMSConstant.LONG_INVALID_VALUE) {
			
			existingIdList.add(new Long(e.getLimitID()));
		}
	}
	
	/**
	 * To check if the limit exists in the existingIdList
	 * @param e
	 * @param existingId
	 * @return
	 */
	private boolean isLimitExist(ILimit e, List existingIdList) {
		
		if (e != null && 
				e.getLimitID() != 0 && 
				e.getLimitID() != ICMSConstant.LONG_INVALID_VALUE) {
			
			Long id = new Long(e.getLimitID());
			
			if (existingIdList.contains(id))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Get Counterparty Details object by subprofile id
	 * @param subProfileId
	 * @return
	 * @throws CustExposureException
	 * @throws CCICounterpartyDetailsException
	 */
	protected ICCICounterpartyDetails getCCICounterpartyDetails(long subProfileId)
			throws CustExposureException {
		
		try {
			ICustExposureDAO dao = CustExposureDAOFactory.getDAO(null);
			long groupCCINo = dao.getCCINoBySubProfileId(subProfileId);
			
			ICCICounterpartyDetails details = null;
			
			if (groupCCINo != 0 && groupCCINo != ICMSConstant.LONG_INVALID_VALUE) {
				SBCustomerManager mgr = getCustomerManager();
				details = mgr.getCCICounterpartyDetails(String.valueOf(groupCCINo));
			}
			
			return details;
			
		} catch (CCICounterpartyDetailsException ccie) {
			throw new CustExposureException("Caught Exception!", ccie);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustExposureException("Caught Exception!", e);
		}
	}
	
	/**
	 * Get Customer object by sub profile id
	 * @param subProfileId
	 * @return
	 * @throws CustExposureException
	 */
	protected ICMSCustomer getCustomar(long subProfileId) throws CustExposureException {
		
		try {

			SBCustomerManager mgr = getCustomerManager();
			ICMSCustomer customer = mgr.getCustomer(subProfileId);
			
			return customer;
			
		} catch (CustomerException ce) {
			throw new CustExposureException("Caught Exception!", ce);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustExposureException("Caught Exception!", e);
		}
		
	}
	
	protected SBCustomerManager getCustomerManager() throws Exception {
		SBCustomerManager home = (SBCustomerManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CUSTOMER_MGR_JNDI,
				SBCustomerManagerHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new Exception("SBCustomerManager for Actual is null!");
		}
	}
	
    /**
     * To get the remote handler for the actual group maintenance session bean
     *
     * @return SBUnitTrustFeedBusManager - the remote handler for the staging stockFeedGroup session bean
     */
    protected SBCustGrpIdentifierBusManager getSBCustGrpIdentifierBusManager() {
        SBCustGrpIdentifierBusManager remote = (SBCustGrpIdentifierBusManager) BeanController.getEJB(
                ICMSJNDIConstant.SB_CUST_GRP_IDENTIFIER_BUS_MANAGER_JNDI,
                SBCustGrpIdentifierBusManagerHome.class.getName());
        return remote;
    }
    
    /**
     * Get customer exposure business manager remote handler
     * @return
     * @throws CustExposureException
     */
    protected SBCustExposureBusManager getSBCustExposureBusManager() throws CustExposureException {
        SBCustExposureBusManager theEjb = (SBCustExposureBusManager) BeanController.getEJB(
                ICMSJNDIConstant.SB_CUST_EXPOSURE_BUS_MANAGER_JNDI, SBCustExposureBusManagerHome .class.getName());

        if (theEjb == null)
            throw new CustExposureException("AbstractCustExposureProxy is null!");

        return theEjb;
    }
    
    /**
     * Get internal limit parameter session bean
     * @return
     */
//	protected SBInternalLimitParameterBusManager getActualSBInternalLimitParameterLocal() {
//		return (SBInternalLimitParameterBusManager) BeanController.getEJB(
//				ICMSJNDIConstant.SB_ACTUAL_INTERNAL_LIMIT_JNDI,
//				SBInternalLimitParameterBusManagerHome.class.getName());
//	}
    protected IInternalLimitParameterBusManager getInternalLimitParameterBusManager() {
        return (IInternalLimitParameterBusManager) BeanHouse.get("internalLimitParameterBusManager");
    }
    /**
     * Get internal credit rating session bean
     * @return
     */
//	protected SBInternalCreditRatingBusManager getSBInternalCreditRatingBusManager() {
//		return (SBInternalCreditRatingBusManager) BeanController.getEJB(
//				ICMSJNDIConstant.SB_INTERNAL_CREDIT_RATING_JNDI,
//				SBInternalCreditRatingBusManagerHome.class.getName());
//	}
    protected IInternalCreditRatingBusManager getInternalCreditRatingBusManager() {
        return (IInternalCreditRatingBusManager) BeanHouse.get("internalCreditRatingBusManager");
    }

    protected IEntityLimitBusManager getEntityLimitBusManager() {
        return (IEntityLimitBusManager) BeanHouse.get("entityLimitBusManager");            
    }

	/**
	 * Output debug log refactor method
	 * @param msg
	 */
	private void Debug(String msg) {
		DefaultLogger.debug(this, "AbstractGroupExposureBusManager - " + msg);
	}
}
