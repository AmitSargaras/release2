package com.integrosys.cms.app.custexposure.bus;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.cci.bus.CCICounterpartyDetailsException;
import com.integrosys.cms.app.cci.bus.ICCICounterparty;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.constant.ICategoryEntryConstant;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.ExemptFacilityBusManagerFactory;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.ExemptFacilityException;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacility;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacilityGroup;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.SBExemptFacilityBusManager;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.ExemptedInstBusManagerFactory;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.ExemptedInstException;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.SBExemptedInstBusManager;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.SBCustomerManager;
import com.integrosys.cms.app.customer.bus.SBCustomerManagerHome;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.LimitComparator;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Abstract Customer exposure business manager
 * @author skchai
 *
 */
public class AbstractCustExposureBusManager implements ICustExposureBusManager {

	private static final long serialVersionUID = -5834202773605389799L;
	public static final int SCALE = 0;
	public static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
	/**
	 * Get Limit Profile by the given sub profile id
	 * @param criteria
	 * @return
	 * @throws CustExposureException
	 */
	public List getlimitProfileIDBySubProfileID(long criteria)
			throws CustExposureException {
		try {
			ICustExposureDAO dao = CustExposureDAOFactory.getDAO();
			return dao.getlimitProfileIDBySubProfileID(criteria);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustExposureException("Caught Exception!", e);
		}

	}

	/**
	 * Get Customer Exposure object. This returns the object without the sorting the ILimit yet
	 * @param subProfileId
	 * @return
	 * @throws CustExposureException
	 */
	public ICustExposure getCustExposure(long subProfileId) throws CustExposureException {
		
		ICustExposure custExposure = new OBCustExposure();
		
		// Get CMS customer
		ICMSCustomer cMSCustomer = getCustomer(subProfileId);
		custExposure.setCMSCustomer(cMSCustomer);
		
		// Get corresponding cci
		ICCICounterpartyDetails cciDetails = getCCICounterpartyDetails(subProfileId);
		
		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		
		if (cciDetails != null) {
			
			// cci available
			custExposure.setCCICounterpartyDetails(cciDetails);
			ICCICounterparty[] cciArray = cciDetails.getICCICounterparty();
			
			if (cciArray != null) {
				for (int i = 0; i < cciArray.length; i++) {
					ICCICounterparty tempCci = cciArray[i];
					long tempSubProfileId = tempCci.getSubProfileID();
					
					List limitProfileIdList = getlimitProfileIDBySubProfileID(tempSubProfileId);
					
					if(limitProfileIdList != null && !limitProfileIdList.isEmpty() ) {

				
						for (Iterator itr = limitProfileIdList.iterator(); itr.hasNext();) {
							Long limitProfileId = (Long) itr.next();												
							
							// if limit profile id is not found, the AA is not sent to CLIMS or deleted
							// currently only every ARBS customers has limit profile   
							if (limitProfileId != null && limitProfileId.longValue() != ICMSConstant.LONG_INVALID_VALUE && 
									limitProfileId.longValue() != 0) {
								
								ILimitExposureProfile limitExposure = new OBLimitExposureProfile();
								ICMSCustomer customer = getCustomer(tempSubProfileId);
								limitExposure.setCMSCustomer(customer);
								
								// populate contingent, customer relationship
								// and group relationship into the customer exposure
								populateContgGrpEntityRel(customer, custExposure);
								
					            try {
									ILimitProfile limitProfile = limitProxy.getLimitProfile(limitProfileId.longValue());
									prepareLimitsReferences(limitProfile, new OBTrxContext(), limitProxy);
									limitExposure.setLimitProfile(limitProfile);
									
									custExposure.addLimitExposureProfile(limitExposure);
								} catch (LimitException le) {
									Debug("Error in gettng limit profile : " + le.getMessage());
								}
							}
						}
					}
		            
				}
			}
			
		} else {
			// no cci 
			
			// populate contingent, customer relationship
			// and group relationship into the customer exposure
			populateContgGrpEntityRel(cMSCustomer, custExposure);
			
			List limitProfileIdList = getlimitProfileIDBySubProfileID(subProfileId);
			
			if(limitProfileIdList != null && !limitProfileIdList.isEmpty() ) {

				
				for (Iterator itr = limitProfileIdList.iterator(); itr.hasNext();) {
					Long limitProfileId = (Long) itr.next();												
					
					// if limit profile id is not found, the AA is not sent to CLIMS or deleted
					// currently only every ARBS customers has limit profile   
					if (limitProfileId != null && limitProfileId.longValue() != ICMSConstant.LONG_INVALID_VALUE && 
							limitProfileId.longValue() != 0) {
						
						ILimitExposureProfile limitExposure = new OBLimitExposureProfile();
						limitExposure.setCMSCustomer(cMSCustomer);
						
						try {
				            ILimitProfile limitProfile = limitProxy.getLimitProfile(limitProfileId.longValue());
				            
				            prepareLimitsReferences(limitProfile, new OBTrxContext(), limitProxy);
				            limitExposure.setLimitProfile(limitProfile);
				            
				            custExposure.addLimitExposureProfile(limitExposure);
						} catch (LimitException le) {
							Debug("Error in gettng limit profile : " + le.getMessage());
						}
					}
			
				}//end for
			}
			
			
		}
		
		HashMap mapBE = new HashMap();
		List conventioanlBankList = new ArrayList();
		List islamBankList = new ArrayList();
		List investmentBankList = new ArrayList();
		
		IExemptFacilityGroup exemptFacGroup = getExemptFacilityGroup();
		HashMap exemptFacMap = new HashMap();
		
		if(exemptFacGroup != null && exemptFacGroup.getExemptFacility() != null
				&& exemptFacGroup.getExemptFacility().length > 0) {
			IExemptFacility[] exemptFacilities = exemptFacGroup.getExemptFacility();
			for (int i =0; i < exemptFacilities.length; i++) {
				exemptFacMap.put(exemptFacilities[i].getFacilityCode(), exemptFacilities[i]);
			}
		}
		
		IExemptedInst[] exemptInsts = getExemptedInst();
		HashMap exemptedInstMap = new HashMap();
		
		if (exemptInsts != null && exemptInsts.length > 0) {
			for (int i = 0; i < exemptInsts.length; i++) {
				exemptedInstMap.put(new Long(exemptInsts[i].getCustomerID()), exemptInsts[i]);
			}
		}
		
		
		// Get all contingent liabilities, entity relationship and group relationship
		// TODO Too messy, need to refactor
		if (custExposure.getLimitExposureProfile() != null && 
				custExposure.getLimitExposureProfile().length > 0) {
			for (int i = 0; i < custExposure.getLimitExposureProfile().length; i++) {
				
		        //populateContgGrpEntityRel(custExposure.getLimitExposureProfile()[i].getCMSCustomer(), custExposure);
				
		        ILimitProfile tempLimitProfile = custExposure.getLimitExposureProfile()[i].getLimitProfile();
		        ILimit[] limits = tempLimitProfile.getLimits();
				
		        for (int j = 0; j < limits.length; j++) {
		        	if (limits[j].getBookingLocation() !=null && 
		        			limits[j].getBookingLocation().getOrganisationCode() != null) {
		        		String accountType = "";
		        		ILimitSysXRef[] sysRefs = limits[j].getLimitSysXRefs();
		    		    if (sysRefs != null && sysRefs.length > 0) {
		    		    	
		        		    if (sysRefs[0].getCustomerSysXRef() != null) {
		        		    	accountType = sysRefs[0].getCustomerSysXRef().getExternalAccountType();
		        		    }
		    		    }
		    		    
		        		String orgCode = limits[j].getBookingLocation().getOrganisationCode();
		        		String bankEntity = getBankEntityByOrgCode(orgCode);
		        		
		    			ILimitExposureByBankEntity e = new OBLimitExposureByBankEntity();
		    			e.setBankEntity(bankEntity);
		    			e.setLimit(limits[j]);
		    			String productDesc = CommonDataSingleton.getCodeCategoryLabelByValue(
								ICategoryEntryConstant.FACILITY_DESCRIPTION, limits[j].getProductDesc());
		    			e.setProductDescription(productDesc);
		    			
		    			e.setExposureCurrency(limits[j].getApprovedLimitAmount().getCurrencyCode());
		    			
		    			if (limits[j].getDisbursedInd().equals(ICMSConstant.DISBURSED_IND_FULL)) {
		    				e.setDisbursement("Full");
		    			} else if (limits[j].getDisbursedInd().equals(ICMSConstant.DISBURSED_IND_FULL)) {
		    				e.setDisbursement("Partial");
		    			}
		    			
		    			// if Outer limit type and not exempted inst
		    			// 		if Term Loan 
		    			// 			For Partially disbursed, sum the approved limit to aggregate exposure. 
		    			// 			For Fully disbursed, sum the outstanding limit to aggregate the exposure. 
		    			// 		else if Overdrafts
		    			// 			For Fully/Partially disbursed, sum the approved limit amount to aggregate exposure. 
		    			
		    			//if (exemptedInstMap.get(key))
		    			
		    			if (limits[j].getLimitType().equals(ICMSConstant.CCC_OUTER_LIMIT) &&
		    					!isExemptedInst(exemptedInstMap, custExposure)) {
			    			if (accountType != null && accountType.equals(ICMSConstant.ACCOUNT_TYPE_LOAN_TERM) && 
			    					limits[j].getDisbursedInd().equals(ICMSConstant.DISBURSED_IND_FULL)) {
			    				
			    				// Term Loan
			    				Amount exposureAmount = getExposureAmount(exemptFacMap, limits[j].getProductDesc(), limits[j].getOutstandingAmount() );
			    				
			    				if (exposureAmount != null)
			    					e.setExposureAmount(exposureAmount);
			    				else
			    					e.setExposureAmount(limits[j].getOutstandingAmount());
			    				
			    			} else {
			    				// Overdraft and others
			    				Amount exposureAmount = getExposureAmount(exemptFacMap, limits[j].getProductDesc(), limits[j].getApprovedLimitAmount() );
			    				
			    				if (exposureAmount != null)
			    					e.setExposureAmount(exposureAmount);
			    				else
			    					e.setExposureAmount(limits[j].getApprovedLimitAmount());
			    			}
		    			}
		        		
		    			populateCollateralOMV(limits[j]);
		    			
		    			Debug("Bank Entity : " + bankEntity);
		        		if (bankEntity.equals(ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE)) {
		        			conventioanlBankList.add(e);
		        		} else if (bankEntity.equals(ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE)) {
		        			investmentBankList.add(e);
		        		} else if (bankEntity.equals(ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE)) {
		        			islamBankList.add(e);
		        		}
		        	}
		        }
			}
		}// else {
			// no customer limit profile, get relationship based on customer info 
			//populateContgGrpEntityRel(cMSCustomer, custExposure);
		//}
		
		mapBE.put(ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE, 
				conventioanlBankList.toArray(new ILimitExposureByBankEntity[0]));
		
		mapBE.put(ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE, 
				investmentBankList.toArray(new ILimitExposureByBankEntity[0]));
		
		mapBE.put(ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE, 
				islamBankList.toArray(new ILimitExposureByBankEntity[0]));
		
		custExposure.setLimitExposureByBankEntityMap(mapBE);
		
		
		return custExposure;
	}
	
	
	/**
	 * Get minimal Customer Exposure, called by Group Exposure to 
	 * CCI, customer relationship and group relationship retrieval is not needed
	 * @param subProfileId
	 * @throws CustExposureException
	 */
	public void getCustExposure(ICustExposure custExposure) throws CustExposureException {
		
		//ICustExposure custExposure = new OBCustExposure();
		
		// Get CMS customer
		//ICMSCustomer cMSCustomer = getCustomer(subProfileId);
		//custExposure.setCMSCustomer(cMSCustomer);
		
		// Get corresponding cci
		//ICCICounterpartyDetails cciDetails = getCCICounterpartyDetails(subProfileId);
		ICCICounterpartyDetails cciDetails = custExposure.getCCICounterpartyDetails();
		ICMSCustomer cMSCustomer = custExposure.getCMSCustomer();
		long subProfileId = cMSCustomer.getCustomerID();
		
		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		
		if (cciDetails != null) {
			
			// cci available
			custExposure.setCCICounterpartyDetails(cciDetails);
			ICCICounterparty[] cciArray = cciDetails.getICCICounterparty();
			
			if (cciArray != null) {
				for (int i = 0; i < cciArray.length; i++) {
					ICCICounterparty tempCci = cciArray[i];
					long tempSubProfileId = tempCci.getSubProfileID();
					
					List limitProfileIdList = getlimitProfileIDBySubProfileID(tempSubProfileId);
					
					if(limitProfileIdList != null && !limitProfileIdList.isEmpty() ) {

				
						for (Iterator itr = limitProfileIdList.iterator(); itr.hasNext();) {
							Long limitProfileId = (Long) itr.next();												
							
							// if limit profile id is not found, the AA is not sent to CLIMS or deleted
							// currently only every ARBS customers has limit profile   
							if (limitProfileId != null && limitProfileId.longValue() != ICMSConstant.LONG_INVALID_VALUE && 
									limitProfileId.longValue() != 0) {
								
								ILimitExposureProfile limitExposure = new OBLimitExposureProfile();
								ICMSCustomer customer = getCustomer(tempSubProfileId);
								limitExposure.setCMSCustomer(customer);
								
								// populate contingent, customer relationship
								// and group relationship into the customer exposure
								populateContgGrp(customer, custExposure);
								
					            try {
									ILimitProfile limitProfile = limitProxy.getLimitProfile(limitProfileId.longValue());
									prepareLimitsReferences(limitProfile, new OBTrxContext(), limitProxy);
									limitExposure.setLimitProfile(limitProfile);
									
									custExposure.addLimitExposureProfile(limitExposure);
								} catch (LimitException le) {
									Debug("Error in gettng limit profile : " + le.getMessage());
								}
							}
						}
					}
		            
				}
			}
			
		} else {
			// no cci 
			
			// populate contingent, customer relationship
			// and group relationship into the customer exposure
			populateContgGrp(cMSCustomer, custExposure);
			
			List limitProfileIdList = getlimitProfileIDBySubProfileID(subProfileId);
			
			if(limitProfileIdList != null && !limitProfileIdList.isEmpty() ) {

				
				for (Iterator itr = limitProfileIdList.iterator(); itr.hasNext();) {
					Long limitProfileId = (Long) itr.next();												
					
					// if limit profile id is not found, the AA is not sent to CLIMS or deleted
					// currently only every ARBS customers has limit profile   
					if (limitProfileId != null && limitProfileId.longValue() != ICMSConstant.LONG_INVALID_VALUE && 
							limitProfileId.longValue() != 0) {
						
						ILimitExposureProfile limitExposure = new OBLimitExposureProfile();
						limitExposure.setCMSCustomer(cMSCustomer);
						
						try {
				            ILimitProfile limitProfile = limitProxy.getLimitProfile(limitProfileId.longValue());
				            
				            prepareLimitsReferences(limitProfile, new OBTrxContext(), limitProxy);
				            limitExposure.setLimitProfile(limitProfile);
				            
				            custExposure.addLimitExposureProfile(limitExposure);
						} catch (LimitException le) {
							Debug("Error in gettng limit profile : " + le.getMessage());
						}
					}
			
				}//end for
			}
			
			
		}
		
		HashMap mapBE = new HashMap();
		List conventioanlBankList = new ArrayList();
		List islamBankList = new ArrayList();
		List investmentBankList = new ArrayList();
		
		IExemptFacilityGroup exemptFacGroup = getExemptFacilityGroup();
		HashMap exemptFacMap = new HashMap();
		
		if(exemptFacGroup != null && exemptFacGroup.getExemptFacility() != null
				&& exemptFacGroup.getExemptFacility().length > 0) {
			IExemptFacility[] exemptFacilities = exemptFacGroup.getExemptFacility();
			for (int i =0; i < exemptFacilities.length; i++) {
				exemptFacMap.put(exemptFacilities[i].getFacilityCode(), exemptFacilities[i]);
			}
		}
		
		IExemptedInst[] exemptInsts = getExemptedInst();
		HashMap exemptedInstMap = new HashMap();
		
		if (exemptInsts != null && exemptInsts.length > 0) {
			for (int i = 0; i < exemptInsts.length; i++) {
				exemptedInstMap.put(new Long(exemptInsts[i].getCustomerID()), exemptInsts[i]);
			}
		}
		
		
		// Get all contingent liabilities, entity relationship and group relationship
		// TODO Too messy, need to refactor
		if (custExposure.getLimitExposureProfile() != null && 
				custExposure.getLimitExposureProfile().length > 0) {
			for (int i = 0; i < custExposure.getLimitExposureProfile().length; i++) {
				
		        //populateContgGrpEntityRel(custExposure.getLimitExposureProfile()[i].getCMSCustomer(), custExposure);
				
		        ILimitProfile tempLimitProfile = custExposure.getLimitExposureProfile()[i].getLimitProfile();
		        ILimit[] limits = tempLimitProfile.getLimits();
				
		        for (int j = 0; j < limits.length; j++) {
		        	if (limits[j].getBookingLocation() !=null && 
		        			limits[j].getBookingLocation().getOrganisationCode() != null) {
		        		String accountType = "";
		        		ILimitSysXRef[] sysRefs = limits[j].getLimitSysXRefs();
		    		    if (sysRefs != null && sysRefs.length > 0) {
		    		    	
		        		    if (sysRefs[0].getCustomerSysXRef() != null) {
		        		    	accountType = sysRefs[0].getCustomerSysXRef().getExternalAccountType();
		        		    }
		    		    }
		    		    
		        		String orgCode = limits[j].getBookingLocation().getOrganisationCode();
		        		String bankEntity = getBankEntityByOrgCode(orgCode);
		        		
		    			ILimitExposureByBankEntity e = new OBLimitExposureByBankEntity();
		    			e.setBankEntity(bankEntity);
		    			e.setLimit(limits[j]);
		    			String productDesc = CommonDataSingleton.getCodeCategoryLabelByValue(
								ICategoryEntryConstant.FACILITY_DESCRIPTION, limits[j].getProductDesc());
		    			e.setProductDescription(productDesc);
		    			
		    			e.setExposureCurrency(limits[j].getApprovedLimitAmount().getCurrencyCode());
		    			
		    			if (limits[j].getDisbursedInd().equals(ICMSConstant.DISBURSED_IND_FULL)) {
		    				e.setDisbursement("Full");
		    			} else if (limits[j].getDisbursedInd().equals(ICMSConstant.DISBURSED_IND_FULL)) {
		    				e.setDisbursement("Partial");
		    			}
		    			
		    			// if Outer limit type and not exempted inst
		    			// 		if Term Loan 
		    			// 			For Partially disbursed, sum the approved limit to aggregate exposure. 
		    			// 			For Fully disbursed, sum the outstanding limit to aggregate the exposure. 
		    			// 		else if Overdrafts
		    			// 			For Fully/Partially disbursed, sum the approved limit amount to aggregate exposure. 
		    			
		    			//if (exemptedInstMap.get(key))
		    			
		    			if (limits[j].getLimitType().equals(ICMSConstant.CCC_OUTER_LIMIT) &&
		    					!isExemptedInst(exemptedInstMap, custExposure)) {
			    			if (accountType != null && accountType.equals(ICMSConstant.ACCOUNT_TYPE_LOAN_TERM) && 
			    					limits[j].getDisbursedInd().equals(ICMSConstant.DISBURSED_IND_FULL)) {
			    				
			    				// Term Loan
			    				Amount exposureAmount = getExposureAmount(exemptFacMap, limits[j].getProductDesc(), limits[j].getOutstandingAmount() );
			    				
			    				if (exposureAmount != null)
			    					e.setExposureAmount(exposureAmount);
			    				else
			    					e.setExposureAmount(limits[j].getOutstandingAmount());
			    				
			    			} else {
			    				// Overdraft and others
			    				Amount exposureAmount = getExposureAmount(exemptFacMap, limits[j].getProductDesc(), limits[j].getApprovedLimitAmount() );
			    				
			    				if (exposureAmount != null)
			    					e.setExposureAmount(exposureAmount);
			    				else
			    					e.setExposureAmount(limits[j].getApprovedLimitAmount());
			    			}
		    			}
		        		
		    			populateCollateralOMV(limits[j]);
		    			
		    			Debug("Bank Entity : " + bankEntity);
		        		if (bankEntity.equals(ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE)) {
		        			conventioanlBankList.add(e);
		        		} else if (bankEntity.equals(ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE)) {
		        			investmentBankList.add(e);
		        		} else if (bankEntity.equals(ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE)) {
		        			islamBankList.add(e);
		        		}
		        	}
		        }
			}
		}
		
		mapBE.put(ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE, 
				conventioanlBankList.toArray(new ILimitExposureByBankEntity[0]));
		
		mapBE.put(ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE, 
				investmentBankList.toArray(new ILimitExposureByBankEntity[0]));
		
		mapBE.put(ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE, 
				islamBankList.toArray(new ILimitExposureByBankEntity[0]));
		
		custExposure.setLimitExposureByBankEntityMap(mapBE);
		
	}
	
	/**
	 * Populate contingent liabilities, entity relationship, 
	 * 	group relationship for respective group member
	 * @param customer
	 * @param custExposure
	 */
	public void populateContgGrpEntityRel(ICMSCustomer c, ICustExposure custExposure) {
		
		if (c == null)
			return;
		
		CustExposureSearchCriteria criteria = new CustExposureSearchCriteria();
        
        criteria.setSourceId(c.getCMSLegalEntity().getSourceID());
        criteria.setSubProfileID(c.getCustomerID() + "");
        criteria.setSourceId(c.getCMSLegalEntity().getSourceID());
        criteria.setLEReference(c.getCMSLegalEntity().getLEReference());
        
        try {
        	Map map = getDependentExposureRecords(criteria);

			List childList = new ArrayList();
			// set ContingentLiabilities
			if (map.get("customer.contingentLiabilities") != null) {
				childList = (List) map.get("customer.contingentLiabilities");
				Debug("customer.contingentLiabilities  " + childList.size());
				if (childList != null && !childList.isEmpty()) {
					IContingentLiabilities[] arrayObj = (IContingentLiabilities[]) childList
							.toArray(new OBContingentLiabilities[0]);
					custExposure.addContingentLiabilities(arrayObj);
				}
			}

			// set GroupRelationship
			if (map.get("customer.groupRelationship") != null) {
				childList = (List) map.get("customer.groupRelationship");
				Debug("customer.groupRelationship  " + childList.size());
				if (childList != null && !childList.isEmpty()) {
					ICustExposureGroupRelationship[] arrayObj = (ICustExposureGroupRelationship[]) childList
							.toArray(new OBCustExposureGroupRelationship[0]);
					custExposure.addCustExposureGroupRelationship(arrayObj);
				}
			}

			// set getCustomerRelationship for the child to parent
			if (map.get("customer.customerRelationship") != null) {
				childList = (List) map.get("customer.customerRelationship");
				Debug("customer.customerRelationship  " + childList.size());
				if (childList != null && !childList.isEmpty()) {
					ICustExposureEntityRelationship[] arrayObj = (ICustExposureEntityRelationship[]) childList
							.toArray(new OBCustExposureEntityRelationship[0]);
					custExposure.addCustExposureEntityRelationship(arrayObj);
				}
			}
        } catch (CustExposureException ce) {
        	ce.printStackTrace();
        }
	}
	
	
	/**
	 * Populate contingent liabilities for respective group member
	 * @param customer
	 * @param custExposure
	 */
	public void populateContgGrp(ICMSCustomer c, ICustExposure custExposure) {
		
		if (c == null)
			return;
		
		CustExposureSearchCriteria criteria = new CustExposureSearchCriteria();
        
        criteria.setSourceId(c.getCMSLegalEntity().getSourceID());
        criteria.setSubProfileID(c.getCustomerID() + "");
        criteria.setSourceId(c.getCMSLegalEntity().getSourceID());
        criteria.setLEReference(c.getCMSLegalEntity().getLEReference());
        
        try {
        	ICustExposureDAO dao = CustExposureDAOFactory.getDAO();
        	Map map = dao.getContingentLiabilitiesResults(criteria);

			List childList = new ArrayList();
			// set ContingentLiabilities
			if (map.get("customer.contingentLiabilities") != null) {
				childList = (List) map.get("customer.contingentLiabilities");
				Debug("customer.contingentLiabilities  " + childList.size());
				if (childList != null && !childList.isEmpty()) {
					IContingentLiabilities[] arrayObj = (IContingentLiabilities[]) childList
							.toArray(new OBContingentLiabilities[0]);
					custExposure.addContingentLiabilities(arrayObj);
				}
			}

        } catch (Exception ce) {
        	ce.printStackTrace();
        }
	}
	
	/**
	 * user to getDependentExposureRecords
	 * 
	 * @param criteria
	 * @return
	 * @throws CustExposureException
	 */
	public Map getDependentExposureRecords(CustExposureSearchCriteria criteria)
			throws CustExposureException {
		try {
			ICustExposureDAO dao = CustExposureDAOFactory.getDAO();
			return dao.getCustExposureRecords(criteria);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustExposureException("Caught Exception!", e);
		}

	}
	
	/**
	 * Get exposure amount from 
	 * @param exemptFacMap
	 * @param limit
	 * @return
	 */
	private Amount getExposureAmount(HashMap exemptFacMap, String prodDesc, Amount exposureAmount) {
		
		if (exemptFacMap.get(prodDesc) != null) {
			IExemptFacility ef = (IExemptFacility)exemptFacMap.get( prodDesc );
			
			if (exposureAmount != null &&
					exposureAmount.getCurrencyCodeAsObject() != null) {
				
				if (ef.getFacilityStatusExempted().equals(ICMSConstant.EXEMPT_FACILITY_STATUS_CONDITIONAL) &&
						ef.getFacilityStatusConditionalPerc() != 0 &&
						ef.getFacilityStatusConditionalPerc() != ICMSConstant.DOUBLE_INVALID_VALUE) {
				
					double amount = exposureAmount.getAmountAsDouble();
					double percentage = ef.getFacilityStatusConditionalPerc();
										
					BigDecimal result = new BigDecimal( amount ).multiply(new BigDecimal( percentage ) ).divide( new BigDecimal( 100 ), SCALE, ROUNDING_MODE);
					
					return new Amount(result.doubleValue(), exposureAmount.getCurrencyCodeAsObject());
				
				} else if (ef.getFacilityStatusExempted().equals(ICMSConstant.EXEMPT_FACILITY_STATUS_EXEMPTED)) {
					
					return new Amount(0, exposureAmount.getCurrencyCodeAsObject());
				}
			}
		}
		return null;
		
	}
	
	/**
	 * Check if the cust exposure fall into exempted institution
	 * @param exemptInstMap
	 * @param custExposure
	 * @return
	 */
	private boolean isExemptedInst(HashMap exemptInstMap, ICustExposure custExposure) {
		
		if (custExposure != null) {
			if (custExposure.getCCICounterpartyDetails() != null) {
				
				ICCICounterpartyDetails counterpartyDetails = custExposure.getCCICounterpartyDetails();
				ICCICounterparty[] parties = counterpartyDetails.getICCICounterparty();
				
				if (parties != null) {
					for (int j = 0; j < parties.length; j++) {
						long subProfileId = parties[j].getSubProfileID();
						Object obj = exemptInstMap.get(new Long(subProfileId));
						
						// not found in exempted institution
						if (obj != null)
							return true;
					}
				}
			} else {
			
				ICMSCustomer customer = custExposure.getCMSCustomer();
				
				if (exemptInstMap.get(new Long(customer.getCustomerID())) != null)
					return true;
			}
		}
		
		return false;
	}
	
	/**
	 * populate collateral with latest valuation omv
	 * @param limitObj
	 */
	public void populateCollateralOMV(ILimit limitObj) {
		
		ICustExposureDAO dao = CustExposureDAOFactory.getDAO();
        ICollateralAllocation[] collaterals = limitObj.getCollateralAllocations();
        long collateralId = ICMSConstant.LONG_INVALID_VALUE;
        Amount omv = null;

        
        if (collaterals != null){
            for (int x = 0; x < collaterals.length; x++) {
                collateralId = collaterals[x].getCollateral().getCollateralID();
                try {
	                omv = dao.retrieveLatestValuationByCollateralId(String.valueOf(collateralId));
	                
	                if (omv != null)
	                	collaterals[x].getCollateral().setCMV(omv);
                } catch (Exception e) {
                	e.printStackTrace();
                	
                }
            }
        }
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
			ICustExposureDAO dao = CustExposureDAOFactory.getDAO();
			long groupCCINo = dao.getCCINoBySubProfileId(subProfileId);
			
			ICCICounterpartyDetails details = null;

			SBCustomerManager mgr = getCustomerManager();
			details = mgr.getCCICounterpartyDetails(String.valueOf(groupCCINo));
			
			return details;
			
		} catch (CCICounterpartyDetailsException ccie) {
			throw new CustExposureException("Caught Exception!", ccie);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustExposureException("Caught Exception!", e);
		}
	}
	
	/**
	 * Get customer with the given sub profile id
	 * @param subProfileId
	 * @return
	 * @throws CustExposureException
	 */
	protected ICMSCustomer getCustomer(long subProfileId) throws CustExposureException {
		
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
	
	/**
	 * Get all exempted institution
	 * @return
	 */
	public IExemptedInst[] getExemptedInst() {
		
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
		
		return exemptedInsts;
	}
	
	/**
	 * Get all exempted facilities in the wrapper 
	 * @return
	 */
	public IExemptFacilityGroup getExemptFacilityGroup() {
		IExemptFacilityGroup actualGrp = null;
		
		
		SBExemptFacilityBusManager mgr;
		try {
			mgr = ExemptFacilityBusManagerFactory.getActualFeedBusManager();
			actualGrp = mgr.getExemptFacilityGroup();
		} catch (ExemptFacilityException e) {
			e.printStackTrace();
		} catch (RemoteException re) {
			re.printStackTrace();
		}
		
		return actualGrp;
	}
	
	/**
	 * Get the related bank group for a given branch code, if not bank group is maintained
	 * in the bank group bank code Standard Code, Conventional Bank code will be returned.
	 *   02 - Conventional \n <br/>
	 *   03 - Islamic \n <br/>
	 *   04 - Investment \n <br/>
	 * @param orgCode
	 * @return
	 */
    private String getBankEntityByOrgCode(String orgCode) {
    	ICustExposureDAO dao = CustExposureDAOFactory.getDAO();
    	return dao.getBankEntityByOrgCode(orgCode);
    }
    
	/**
	 * Get Limits that belongs to the limit profile
	 * @param limitProfileID
	 * @param trxContext
	 * @param sortBy
	 * @return
	 */
    private void prepareLimitsReferences(ILimitProfile limitProfile, OBTrxContext trxContext, ILimitProxy limitProxy) {
        try {
        	// TODO Super messy, will change later
            //ILimitProxy limitProxy = LimitProxyFactory.getProxy();

            ILimit [] newLimits = limitProxy.getFilteredNilColCheckListLimits(trxContext, limitProfile);
                if (newLimits != null){

                    int i = 0;
                    int j = 0;
                    ArrayList innerLimits = new ArrayList();
                    ArrayList outerLimits = new ArrayList();
                    ArrayList outerLmtsWithInn = new ArrayList();
                    ArrayList outerLmtsWithoutInn = new ArrayList();
                    ArrayList tempComLmt = new ArrayList();

                    for (i = 0; i < newLimits.length; i++) {
                        if (newLimits[i].getOuterLimitID() == 0 || newLimits[i].getOuterLimitID() == ICMSConstant.LONG_INVALID_VALUE){
                            outerLimits.add(newLimits[i]);
                        } else {
                            innerLimits.add(newLimits[i]);
                        }
                    }

                    boolean found = false;
                    ILimit tempLmt = new OBLimit();
                    String outerLimitID[] = limitProxy.getDistinctOuterLimitID(limitProfile.getLimitProfileID());
                    if (outerLimitID != null){

                        Arrays.sort(outerLimitID);

                        for (i = 0; i < outerLimits.size(); i++) {
                            tempLmt = (ILimit) outerLimits.get(i);
                            for (j = 0; j < outerLimitID.length; j++) {
                                if (tempLmt.getLimitID() == Long.parseLong(outerLimitID[j])){
                                    outerLmtsWithInn.add(tempLmt);
                                    found = true;
                                    break;
                                } else {
                                    found = false;
                                }
                            }
                            if (!found){
                                outerLmtsWithoutInn.add(tempLmt);
                            }
                        }

                        Arrays.sort(innerLimits.toArray(), new LimitComparator("outerLimitID"));

                        HashMap innerLmtMap = new HashMap();
                        for (i = 0; i < outerLimitID.length; i++) {
                            ArrayList tempInnLmt = new ArrayList();
                            for (j = 0; j < innerLimits.size(); j++) {
                                tempLmt = (ILimit) innerLimits.get(j);
                                if (Long.parseLong(outerLimitID[i]) == tempLmt.getOuterLimitID()){
                                    tempInnLmt.add(tempLmt);
                                }
                            }
                            innerLmtMap.put(outerLimitID[i], tempInnLmt);
                        }

                        for (i = 0; i < outerLmtsWithInn.size(); i++) {
                            tempLmt = (ILimit) outerLmtsWithInn.get(i);
                            tempComLmt.add(tempLmt);
                            if (innerLmtMap.containsKey(String.valueOf(tempLmt.getLimitID()))){
                                Collection tempLimits = (Collection) innerLmtMap.get(String.valueOf(tempLmt.getLimitID()));
                                Iterator itorLmt = tempLimits.iterator();
                                while (itorLmt.hasNext()) {
                                    tempLmt = (ILimit) itorLmt.next();
                                    tempComLmt.add(tempLmt);
                                    if (innerLmtMap.containsKey(String.valueOf(tempLmt.getLimitID()))){
                                        getChildLmt(String.valueOf(tempLmt.getLimitID()), innerLmtMap, tempComLmt);
                                    }
                                }
                            }
                        }
                    }
                    ILimit tempAllLmt[] = new OBLimit[newLimits.length];
                    int outLmt = 0;
                    if (innerLimits.size() < 1){
                        for (i = 0; i < outerLimits.size(); i++) {
                            tempAllLmt[i] = (OBLimit) outerLimits.get(i);
                        }
                    } else {
                        outLmt = outerLmtsWithoutInn.size();
                        for (i = 0; i < outLmt; i++) {
                            tempAllLmt[i] = (OBLimit) outerLmtsWithoutInn.get(i);
                        }
                        for (i = 0; i < tempComLmt.size(); i++) {
                            tempAllLmt[outLmt + i] = (OBLimit) tempComLmt.get(i);
                        }
                    }

                    limitProfile.setLimits(tempAllLmt);

                }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static ArrayList getChildLmt(String limitID, HashMap lmtMap, ArrayList tempComLmt) {

        ILimit tempChildLmt = new OBLimit();
        if (lmtMap.containsKey(limitID)){
            Collection tempChildLimits = (Collection) lmtMap.get(limitID);
            Iterator itorChildLmt = tempChildLimits.iterator();
            while (itorChildLmt.hasNext()) {
                tempChildLmt = (ILimit) itorChildLmt.next();
                tempComLmt.add(tempChildLmt);
                if (lmtMap.containsKey(String.valueOf(tempChildLmt.getLimitID()))){
                    getChildLmt(String.valueOf(tempChildLmt.getLimitID()), lmtMap, tempComLmt);
                }
            }
        }
        return tempComLmt;
    }
    
    /**
     * Get customer business manger remote interface
     * @return
     * @throws Exception
     */
	private SBCustomerManager getCustomerManager() throws Exception {
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
	 * Output debug log
	 * @param msg
	 */
	private void Debug(String msg) {
		DefaultLogger.debug(this,"AbstractCustExposureBusManager = " + msg);
	}
}
