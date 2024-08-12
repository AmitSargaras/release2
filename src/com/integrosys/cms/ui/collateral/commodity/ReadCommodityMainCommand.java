/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/ReadCommodityMainCommand.java,v 1.23 2006/09/15 12:41:34 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralComparator;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.type.commodity.BorrowerComparator;
import com.integrosys.cms.app.collateral.bus.type.commodity.GuarantorComparator;
import com.integrosys.cms.app.collateral.bus.type.commodity.IBorrower;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.IGuarantor;
import com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency;
import com.integrosys.cms.app.collateral.bus.type.commodity.ILoanLimit;
import com.integrosys.cms.app.collateral.bus.type.commodity.IParticipant;
import com.integrosys.cms.app.collateral.bus.type.commodity.ISubLimit;
import com.integrosys.cms.app.collateral.bus.type.commodity.LoanLimitComparator;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBLoanAgency;
import com.integrosys.cms.app.collateral.bus.type.commodity.ParticipantComparator;
import com.integrosys.cms.app.collateral.bus.type.commodity.SubLimitComparator;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyUtil;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.collateral.CollateralHelper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.23 $
 * @since $Date: 2006/09/15 12:41:34 $ Tag: $Name: $
 */

public class ReadCommodityMainCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "collateralID", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "flag", "java.lang.String", REQUEST_SCOPE },
				{ "flag1", "java.lang.String", SERVICE_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE } });

	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "customerOb", "com.integrosys.cms.app.customer.bus.OBCMSCustomer", REQUEST_SCOPE },
				{ "limitprofileOb", "com.integrosys.cms.app.limit.bus.OBLimitProfile", REQUEST_SCOPE },
				{ "flag1", "java.lang.String", SERVICE_SCOPE }, { "ARM_Code", "java.lang.String", SERVICE_SCOPE },
				{ "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ "commodityMainObj", "java.util.HashMap", FORM_SCOPE }, });

	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		String event = (String) map.get("event");
		String flag = (String) map.get("flag");
		String flag1 = (String) map.get("flag1");

		ICollateralTrxValue[] trxValue = null;

		if (flag1 != null) {
			result.put("flag1", flag1);
		}
		else {
			result.put("flag1", flag);
		}
		ICMSCustomer custOB = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);

		if (null != custOB) {
			result.put("customerOb", custOB);
		}

		if (null != limitProfileOB) {
			result.put("limitprofileOb", limitProfileOB);
		}

		// If login user is not belongs to BCA booking location, he/she cannot
		// edit the info
		if (event.equals(CommodityMainAction.EVENT_PREPARE_UPDATE)
				|| event.equals(CommodityMainAction.EVENT_PROCESS_UPDATE)) {
			ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
			String[] teamCountry = team.getCountryCodes();
			String customerCountry = limitProfileOB.getOriginatingLocation().getCountryCode();
			boolean isCustomerBelongTeam = false;
			// DefaultLogger.debug(this, "team location length: " +
			// teamCountry.length);
			if (customerCountry != null) {
				for (int i = 0; (i < teamCountry.length) && !isCustomerBelongTeam; i++) {
					DefaultLogger.debug(this, "Team location " + i + " :" + teamCountry[i] + "\t");
					if (customerCountry.equals(teamCountry[i])) {
						isCustomerBelongTeam = true;
					}
				}
			}
			else {
				isCustomerBelongTeam = true;
			}
			if (!isCustomerBelongTeam) {
				throw new AccessDeniedException("BCA Booking Location is not belongs to team location.");
			}
		}

		if (!(event.equals(CommodityMainAction.EVENT_PREPARE_UPDATE) || event.equals(CommodityMainAction.EVENT_READ))) {
			String trxID = (String) map.get("trxID");
			DefaultLogger.debug(this, "Transaction id is: " + trxID);
			try {
				trxValue = CollateralProxyFactory.getProxy().getCollateralTrxValues(ctx, trxID);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}
		else {
			DefaultLogger.debug(this, "---------------- limit profile id ------------- "
					+ limitProfileOB.getLimitProfileID());
			try {
				// CMSSP-536: Filter Limits which has Sec-Limit Linkage deleted
				// and no checklist attached
				ILimit[] newLimits = LimitProxyFactory.getProxy().getFilteredNilColCheckListLimits(ctx, limitProfileOB);
				limitProfileOB.setLimits(newLimits);
				// CMSSP-536 Ends
				Long[] collateralIDList = CollateralProxyUtil.getCommodityIDs(limitProfileOB);
				long[] colIDList = null;
				ITrxContext[] contextArr = null;
				DefaultLogger.debug(this, "<<<<<<<< collateralIDList is null: " + (collateralIDList == null));
				if (collateralIDList != null) {
					colIDList = new long[collateralIDList.length];
					contextArr = new OBTrxContext[collateralIDList.length];
					for (int i = 0; i < collateralIDList.length; i++) {
						colIDList[i] = collateralIDList[i].longValue();
						DefaultLogger.debug(this, "<<<<<<<<<<<<< " + i + " collateral ID: " + colIDList[i]);
						contextArr[i] = ctx;
					}
				}
				DefaultLogger.debug(this, "+++++++++++++++++++++++++++ collateral ID List: " + colIDList);
				trxValue = CollateralProxyFactory.getProxy().getCollateralTrxValues(contextArr, colIDList);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}

		// Sort the trxvalue by security id if it is not at checker page.
		// Because compareUtil comparison will return a list which is not in
		// sorted order
		if (!event.equals(EVENT_PROCESS)) {
			Arrays.sort(trxValue, new CollateralComparator());
		}

		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		// Get FAM code of the customer
		String armCode = "";
		if (limitProfileOB != null) {
			try {
				HashMap famMap = limitProxy.getFAMName(limitProfileOB.getLimitProfileID());
				armCode = (String) famMap.get(ICMSConstant.FAM_CODE);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}

		result.put("ARM_Code", armCode);

		// Get the transaction status
		ICollateralTrxValue objTrxValue = null;
		if ((trxValue != null) && (trxValue.length > 0)) {
			objTrxValue = trxValue[0];
		}

		// check whether is the transaciton in WIP
		if ((trxValue != null) && event.equals(CommodityMainAction.EVENT_PREPARE_UPDATE)) {
			for (int i = 0; i < trxValue.length; i++) {
				objTrxValue = trxValue[i];
				if (!result.containsKey("wip") && !objTrxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)
						&& !objTrxValue.getStatus().equals(ICMSConstant.STATE_ND)) {
					result.put("wip", "wip");
				}
			}
			if ((!result.containsKey("wip") && objTrxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE))
					|| objTrxValue.getStatus().equals(ICMSConstant.STATE_ND)) {
				for (int i = 0; i < trxValue.length; i++) {
					try {
						trxValue[i].setStagingCollateral((ICollateral) AccessorUtil.deepClone(trxValue[i]
								.getCollateral()));
					}
					catch (Exception e) {
						e.printStackTrace();
						throw new CommandProcessingException(e.getMessage());
					}
				}
			}
		}

		// HashMap trxValueMap contains 5 objects
		// key: 'trxvalue' value: ICollateralTrxValue[] -- array of trxValue get
		// from proxy
		// key: 'actual' value: ICommodityCollateral[] -- actual commodity array
		// list
		// key: 'staging' value: ICommodityCollateral[] -- staging commodity
		// array list
		// key: 'actualLimit' value: HashMap (key: cms_limitID value:
		// ICollateralLimitMap)
		// key: 'stageLimit' value: HashMap (key: cms_limitID value:
		// ICollateralLimitMap)
		// key: 'limitList' value: HashMap (key: cms_limitID value: ILimit)
		// key: 'limitOuterBCAMap' value: HashMap (key: cms_limitID
		// value:OBCustomerSearchResult)
		// limit details need the ICollateralLimitMap, ILimit and
		// OBCustomerSearchResult details
		if (trxValue != null) {
			HashMap trxValueMap = new HashMap();
			HashMap actualLimit = new HashMap();
			HashMap stageLimit = new HashMap();

			HashMap limitListMap = new HashMap();
			HashMap MBLimitListMap = new HashMap(); // only to keep Main
													// Borrower limit (ILimit)

			trxValueMap.put("trxValue", trxValue);

			ICommodityCollateral[] actual = new ICommodityCollateral[trxValue.length];
			ICommodityCollateral[] staging = new ICommodityCollateral[trxValue.length];
			for (int i = 0; i < trxValue.length; i++) {
				actual[i] = (ICommodityCollateral) trxValue[i].getCollateral();
				if (trxValue[i].getCollateral() != null) {
					ICollateralLimitMap[] limitMap = trxValue[i].getCollateral().getCurrentCollateralLimits();
					if (limitMap != null) {
						for (int j = 0; j < limitMap.length; j++) {
							// only set the ICollateralLimitMap belongs to the
							// customer into ICommodityCollateral (for shared
							// security problem)
							String limitID = CollateralHelper.getColLimitMapLimitID(limitMap[j]);

							if (!actualLimit.containsKey(limitID)) {
								Object limit = CollateralProxyUtil.getCustomerLimit(limitProfileOB, limitMap[j]);
								// if limit is null, the limit is not belongs to
								// the customer (for shared security problem)
								if (limit != null) {
									actualLimit.put(limitID, limitMap[j]);
									if (!limitListMap.containsKey(limitID)) {
										limitListMap.put(limitID, limit);
										if (ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER.equals(limitMap[j]
												.getCustomerCategory())) {
											MBLimitListMap.put(limitID, limit);
										}
										// DefaultLogger.debug(this,
										// "<<<<< add limit: "
										// +limitMap[j].getLimitID
										// ()+" limit obj id: "
										// +limit.getLimitID());
									}
								}
							}
						}
					}
					if (event.equals(EVENT_PROCESS)) {
						ILoanAgency[] loanAgencyList = actual[i].getLoans();
						loanAgencyList = sortLoanAgency(loanAgencyList);
					}
				}
				staging[i] = (ICommodityCollateral) trxValue[i].getStagingCollateral();
				if (trxValue[i].getStagingCollateral() != null) {
					ICollateralLimitMap[] limitMap = trxValue[i].getStagingCollateral().getCurrentCollateralLimits();
					if (limitMap != null) {
						for (int j = 0; j < limitMap.length; j++) {
							// only set the ICollateralLimitMap belongs to the
							// customer into ICommodityCollateral (for shared
							// security problem)
							String limitID = CollateralHelper.getColLimitMapLimitID(limitMap[j]);

							if (!stageLimit.containsKey(limitID)) {
								Object limit = CollateralProxyUtil.getCustomerLimit(limitProfileOB, limitMap[j]);
								// if limit is null, the limit is not belongs to
								// the customer (for shared security problem)
								if (limit != null) {
									stageLimit.put(limitID, limitMap[j]);
									if (!limitListMap.containsKey(limitID)) {
										limitListMap.put(limitID, limit);
										if (ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER.equals(limitMap[j]
												.getCustomerCategory())) {
											MBLimitListMap.put(limitID, limit);
										}
									}
								}
							}
						}
					}
				}
				if (event.equals(EVENT_PROCESS)) {
					ILoanAgency[] loanAgencyList = staging[i].getLoans();
					loanAgencyList = sortLoanAgency(loanAgencyList);
				}
			}

			// set the outer limit bca information to the respective limit id
			HashMap limitOuterBCAMap = new HashMap();
			if (!limitListMap.isEmpty()) {
				Collection limitCol = MBLimitListMap.values();
				if (!limitCol.isEmpty()) {
					ILimit[] limitList = (ILimit[]) limitCol.toArray(new ILimit[0]);

					CustomerSearchCriteria objSearch = new CustomerSearchCriteria();
					objSearch.setLimits(limitList);
					try {
						ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
						SearchResult sr = custproxy.searchCustomer(objSearch);
						Collection resultList = sr.getResultList();
						if (!resultList.isEmpty()) {
							Iterator itr = resultList.iterator();
							while (itr.hasNext()) {
								OBCustomerSearchResult obResult = (OBCustomerSearchResult) itr.next();
								if (obResult != null) {
									limitOuterBCAMap.put(String.valueOf(obResult.getInnerLimitID()), obResult);
								}
							}
						}
					}
					catch (Exception e) {
						DefaultLogger.error(this, "Caught exception in outer limit bca search", e);
						throw new CommandProcessingException(e.toString());
					}
				}
			}

			trxValueMap.put("actual", actual);
			trxValueMap.put("staging", staging);
			trxValueMap.put("actualLimit", actualLimit);
			trxValueMap.put("stageLimit", stageLimit);
			trxValueMap.put("limitList", limitListMap);
			trxValueMap.put("limitOuterBCAMap", limitOuterBCAMap);

			result.put("commodityMainTrxValue", trxValueMap);
			if (event.equals("read")) {
				HashMap actualMap = new HashMap();
				actualMap.put("obj", actual);
				actualMap.put("limit", actualLimit);
				result.put("commodityMainObj", actualMap);
			}
			else {
				HashMap stagingMap = new HashMap();
				stagingMap.put("obj", staging);
				stagingMap.put("limit", stageLimit);
				result.put("commodityMainObj", stagingMap);
			}
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	// It is used to sort the arrays in OBLoanAgency in order
	// Needed for compareOBUtil.
	// If inner array is not sorted, compareOBUtil will highlight as changed.
	private ILoanAgency[] sortLoanAgency(ILoanAgency[] loanAgencyList) {
		BorrowerComparator b = new BorrowerComparator();
		GuarantorComparator g = new GuarantorComparator();
		SubLimitComparator s = new SubLimitComparator();
		ParticipantComparator p = new ParticipantComparator();
		LoanLimitComparator l = new LoanLimitComparator();
		if (loanAgencyList != null) {
			for (int j = 0; j < loanAgencyList.length; j++) {
				OBLoanAgency obLoan = (OBLoanAgency) loanAgencyList[j];
				ILoanLimit[] limitID = obLoan.getLimitIDs();
				if (limitID != null) {
					Arrays.sort(limitID, l);
				}
				obLoan.setLimitIDs(limitID);

				IBorrower[] borrowerList = obLoan.getBorrowers();
				if (borrowerList != null) {
					Arrays.sort(borrowerList, b);
					obLoan.setBorrowers(borrowerList);
				}

				IGuarantor[] guarantorList = obLoan.getGuarantors();
				if (guarantorList != null) {
					Arrays.sort(guarantorList, g);
					obLoan.setGuarantors(guarantorList);
				}

				ISubLimit[] subLimitList = obLoan.getSubLimits();
				if (subLimitList != null) {
					Arrays.sort(subLimitList, s);
					obLoan.setSubLimits(subLimitList);
				}

				IParticipant[] participantList = obLoan.getParticipants();
				if (participantList != null) {
					Arrays.sort(participantList, p);
					obLoan.setParticipants(participantList);
				}
				String[] currency = obLoan.getGlobalCurrencies();
				if (currency != null) {
					Arrays.sort(currency);
					obLoan.setGlobalCurrencies(currency);
				}
				loanAgencyList[j] = obLoan;
			}
		}

		return loanAgencyList;
	}
}
