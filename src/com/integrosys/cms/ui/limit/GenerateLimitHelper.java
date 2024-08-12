/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/GenerateLimitHelper.java,v 1.6 2005/12/13 05:51:20 whuang Exp $
 */
package com.integrosys.cms.ui.limit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICustomerSearchResult;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitComparator;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/12/13 05:51:20 $ Tag: $Name: $
 */
public abstract class GenerateLimitHelper {

	/**
	 * get limit reference
	 * @param map
	 * @return
	 */
	public static ArrayList getLimitsReference(HashMap map, OBTrxContext trxContext) throws CommandProcessingException {
		ArrayList referenceList = new ArrayList();
		try {
			ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			trxContext.setCustomer(cust);
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ICustomerProxy customerproxy = CustomerProxyFactory.getProxy();
			ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			if (null == limitProfileOB) {
				throw new CommandProcessingException("ILimitProfile is null in session!");
			}
			long limitProfileID = limitProfileOB.getLimitProfileID();
			ILimitProfile limitprofile = limitProxy.getLimitProfile(limitProfileID);
			ILimit limit[] = limitprofile.getLimits();
			if (limit != null) {
				Arrays.sort(limit, new LimitComparator());
			}
			// Added for CR13
			HashMap bcaList = new HashMap();
			HashMap bcaInfo = null;
			CustomerSearchCriteria searchCriteria = new CustomerSearchCriteria();
			searchCriteria.setLimits(limit);
			searchCriteria.setCtx(trxContext);
			SearchResult bcaResult = customerproxy.searchCustomer(searchCriteria);
			Collection resultCollection = bcaResult.getResultList();
			Iterator itor = resultCollection.iterator();
			while (itor.hasNext()) {
				bcaInfo = new HashMap();
				ICustomerSearchResult customerSearchResult = (ICustomerSearchResult) itor.next();
				bcaInfo.put("bcaRef", customerSearchResult.getInstructionRefNo());
				bcaInfo.put("bkgLoc", customerSearchResult.getOrigLocCntry());
				bcaInfo.put("leId", customerSearchResult.getLegalReference());
				bcaInfo.put("custName", customerSearchResult.getCustomerName());
				bcaList.put(String.valueOf(customerSearchResult.getInnerLimitID()), bcaInfo);
			}
			ILimit[] newLimits = limitProxy.getFilteredNilColCheckListLimits(trxContext, limitprofile);
			limitprofile.setLimits(newLimits);
			referenceList.add(limitprofile);
			referenceList.add(bcaList);
			referenceList.add(getCoBorrowerLimitMap(limitprofile));

			return referenceList;
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error("com.integrosys.cms.ui.limit.GenerateLimitHelper", e);
			throw new CommandProcessingException();
		}
	}

	private static HashMap getCoBorrowerLimitMap(ILimitProfile limitProfile) {
		DefaultLogger
				.debug("com.integrosys.cms.ui.limit.GenerateLimitHelper", "<<<<<<< entering getCoBorrowerLimitMap");
		if (limitProfile == null) {
			return null;
		}

		HashMap returnMap = new HashMap();
		HashMap customerLimitMap = new HashMap();
		HashMap customerObjMap = new HashMap();

		ILimit[] limits = limitProfile.getLimits();
		if ((limits == null) || (limits.length == 0)) {
			return returnMap;
		}

		DefaultLogger.debug("com.integrosys.cms.ui.limit.GenerateLimitHelper", "<<<<<<< limits is not null");

		for (int i = 0; i < limits.length; i++) {
			ICoBorrowerLimit[] coLimits = limits[i].getCoBorrowerLimits();
			if ((coLimits != null) && (coLimits.length > 0)) {
				for (int j = 0; j < coLimits.length; j++) {
					String customerID = String.valueOf(coLimits[j].getCustomerID());
					ArrayList customerLimitList = new ArrayList();
					if (customerLimitMap.containsKey(customerID)) {
						customerLimitList = (ArrayList) returnMap.get(customerID);
					}
					else {
						customerObjMap.put(customerID, coLimits[j].getCoBorrowerCust());
					}
					customerLimitList.add(coLimits[j]);
					customerLimitMap.put(customerID, customerLimitList);
				}
			}
		}

		returnMap.put("customerLimitMap", customerLimitMap);
		returnMap.put("customerObjMap", customerObjMap);

		DefaultLogger.debug("com.integrosys.cms.ui.limit.GenerateLimitHelper",
				"<<<<<<< going out getCoBorrowerLimitMap");
		return returnMap;
	}

	public static void setMainCustomerInfoByLimitProfile(ICoBorrowerLimitTrxValue trxValue, ILimitProfile aLimitProfile) {
		ICustomerProxy customerProxy = CustomerProxyFactory.getProxy();
		Validate.notNull(aLimitProfile, "aLimitProfile object must not be null");

		ICoBorrowerLimit limit = trxValue.getLimit();
		ICoBorrowerLimit stagingLimit = trxValue.getStagingLimit();

		long customerID = aLimitProfile.getCustomerID();
		try {
			ICMSCustomer customer = customerProxy.getCustomer(customerID);
			limit.setMainBorrowerCust(customer);

			if (stagingLimit != null) {
				stagingLimit.setMainBorrowerCust(customer);
			}
		}
		catch (Exception e) {
			DefaultLogger.error("com.integrosys.cms.ui.limit.GenerateLimitHelper", e);
		}
	}

	public static void setProductDescriptionFromMainBorrowerLimit(ICoBorrowerLimit aCoBorrowerLimit) {
		String prodDesc = "-";
		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		if (aCoBorrowerLimit.getOuterLimitID() > 0) {
			try {
				ILimit mainBorrowerLimit = limitProxy.getLimit(aCoBorrowerLimit.getOuterLimitID());
				if (mainBorrowerLimit.getProductDesc() != null) {
					prodDesc = mainBorrowerLimit.getProductDesc();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			aCoBorrowerLimit.setProductDesc(prodDesc);
		}
	}

	/**
	 * set customer into co borrower limit trx value
	 * @param trxValue
	 * @throws CommandProcessingException
	 */
	public static void setCustomerInCoBorrowerLimit(ICoBorrowerLimitTrxValue trxValue)
			throws CommandProcessingException {
		ICustomerProxy customerProxy = CustomerProxyFactory.getProxy();
		ICoBorrowerLimitTrxValue[] trxValues = trxValue.getCoBorrowerLimitTrxValues();
		if (trxValues != null) {
			for (int i = 0; i < trxValues.length; i++) {
				ICoBorrowerLimit limit = trxValues[i].getLimit();
				ICoBorrowerLimit stagingLimit = trxValues[i].getStagingLimit();

				long customerID = limit.getCustomerID();
				try {
					ICMSCustomer customer = customerProxy.getCustomer(customerID);
					limit.setCustomer(customer);

					if (stagingLimit != null) {
						stagingLimit.setCustomer(customer);
					}
				}
				catch (Exception e) {
					DefaultLogger.error("com.integrosys.cms.ui.limit.GenerateLimitHelper", e);
				}
			}
		}
		else {
			ICoBorrowerLimit limit = trxValue.getLimit();
			ICoBorrowerLimit stagingLimit = trxValue.getStagingLimit();

			long customerID = limit.getCustomerID();
			try {
				ICMSCustomer customer = customerProxy.getCustomer(customerID);
				limit.setCustomer(customer);

				if (stagingLimit != null) {
					stagingLimit.setCustomer(customer);
				}
			}
			catch (Exception e) {
				DefaultLogger.error("com.integrosys.cms.ui.limit.GenerateLimitHelper", e);
			}
		}
	}

	/**
	 * set customer into co borrower limit array
	 * @param trxValue
	 * @throws CommandProcessingException
	 */
	public static void setCustomerInfoIntoCoBorrowerLimits(ICoBorrowerLimit[] coBorrowerLimitList)
			throws CommandProcessingException {
		for (int i = 0; i < coBorrowerLimitList.length; i++) {
			setCustomerInfoIntoCoBorrowerLimit(coBorrowerLimitList[i]);
		}
	}

	/**
	 * set customer into co borrower limit
	 * @param trxValue
	 * @throws CommandProcessingException
	 */
	public static void setCustomerInfoIntoCoBorrowerLimit(ICoBorrowerLimit coBorrowerLimit)
			throws CommandProcessingException {
		ICustomerProxy customerProxy = CustomerProxyFactory.getProxy();

		long customerID = coBorrowerLimit.getCustomerID();
		try {
			ICMSCustomer customer = customerProxy.getCustomer(customerID);
			coBorrowerLimit.setCustomer(customer);
		}
		catch (Exception e) {
			DefaultLogger.error("com.integrosys.cms.ui.limit.GenerateLimitHelper", e);
		}
	}

	/**
	 * get customerIDMap
	 * @param trxValue
	 * @return
	 */
	public static HashMap getCustomerIDMap(ICoBorrowerLimitTrxValue trxValue) {
		HashMap customerIDMap = new HashMap();
		ICoBorrowerLimitTrxValue[] limitTrxValues = trxValue.getCoBorrowerLimitTrxValues();
		if (limitTrxValues != null) {
			for (int i = 0; i < limitTrxValues.length; i++) {
				ICoBorrowerLimit coBorrowerLimit = limitTrxValues[i].getLimit();
				String customerID = String.valueOf(coBorrowerLimit.getCustomerID());
				if (customerIDMap.containsKey(customerID)) {
					ArrayList coBorrowerTrxValueList = (ArrayList) customerIDMap.get(customerID);
					coBorrowerTrxValueList.add(limitTrxValues[i]);
				}
				else {
					ArrayList coBorrowerTrxValueList = new ArrayList();
					coBorrowerTrxValueList.add(limitTrxValues[i]);
					customerIDMap.put(customerID, coBorrowerTrxValueList);
				}
			}
		}
		return customerIDMap;
	}

	/**
	 * get CoBorrowerTrxValues by Customer ID
	 * @param trxValue
	 * @param customerID
	 * @return
	 */
	public static ICoBorrowerLimitTrxValue getCoBorrowerTrxValueByCustomerID(ICoBorrowerLimitTrxValue trxValue,
			String customerID) {
		ArrayList coBorrowerTrxValueList = new ArrayList();
		ICoBorrowerLimitTrxValue[] limitTrxValues = trxValue.getCoBorrowerLimitTrxValues();
		if (limitTrxValues != null) {
			for (int i = 0; i < limitTrxValues.length; i++) {
				ICoBorrowerLimit coBorrowerLimit = limitTrxValues[i].getLimit();
				if (customerID.equals(String.valueOf(coBorrowerLimit.getCustomerID()))) {
					coBorrowerTrxValueList.add(limitTrxValues[i]);
				}
			}
		}
		ICoBorrowerLimitTrxValue[] trxValues = (ICoBorrowerLimitTrxValue[]) coBorrowerTrxValueList
				.toArray(new ICoBorrowerLimitTrxValue[0]);
		trxValue.setCoBorrowerLimitTrxValues(trxValues);
		return trxValue;
	}
}
