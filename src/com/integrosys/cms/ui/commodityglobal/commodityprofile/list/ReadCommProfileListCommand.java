/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityprofile/list/ReadCommProfileListCommand.java,v 1.13 2006/10/26 02:31:48 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityprofile.list;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.main.bus.profile.BuyerComparator;
import com.integrosys.cms.app.commodity.main.bus.profile.IBuyer;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.ISupplier;
import com.integrosys.cms.app.commodity.main.bus.profile.OBProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.ProfileComparator;
import com.integrosys.cms.app.commodity.main.bus.profile.ProfileSearchCriteria;
import com.integrosys.cms.app.commodity.main.bus.profile.SupplierComparator;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.commodity.main.trx.profile.IProfileTrxValue;
import com.integrosys.cms.app.commodity.main.trx.profile.OBProfileTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.commodityglobal.commodityprofile.CMDTProfConstants;

/**
 * Description
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.13 $
 * @since $Date: 2006/10/26 02:31:48 $ Tag: $Name: $
 */

public class ReadCommProfileListCommand extends AbstractCommand implements CMDTProfConstants {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ AN_CMDT_PROF_SEARCH_OBJ, CN_CMDT_PROF_SEARCH_OBJ, SERVICE_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "commProfileTrxValue",
				"com.integrosys.cms.app.commodity.main.trx.profile.IProfileTrxValue", SERVICE_SCOPE }, });
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
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		String event = (String) map.get("event");
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		IProfileTrxValue trxValue = new OBProfileTrxValue();
		DefaultLogger.debug(this, "Event : " + event);
		if (!event.equals(EVENT_PREPARE) && !event.equals(EVENT_READ)) {
			String trxID = (String) map.get("trxID");
			try {
				trxValue = proxy.getProfileByTrxID(ctx, trxID);
			}
			catch (Exception e) {
				throw new CommandProcessingException(e.getMessage());
			}
		}
		else {
			try {
				ProfileSearchCriteria criteria = (ProfileSearchCriteria) map.get(AN_CMDT_PROF_SEARCH_OBJ);
				trxValue = proxy.getProfileTrxValue(ctx, criteria);
				if ((trxValue.getProfile() == null) || (trxValue.getProfile().length == 0)) {
					DefaultLogger.debug(this, "trxValue.getProfile() == null");
				}
				DefaultLogger.debug(this, "Status : " + trxValue.getStatus());
				if (event.equals(EVENT_PREPARE) && !trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)
						&& !trxValue.getStatus().equals(ICMSConstant.STATE_ND)) {
					result.put("wip", "wip");
				}
				else if (event.equals(EVENT_PREPARE)) {
					if (trxValue.getProfile() != null) {
						IProfile[] staging = (IProfile[]) AccessorUtil.deepClone(trxValue.getProfile());
						trxValue.setStagingProfile(staging);
						DefaultLogger.debug(this, "Category : " + criteria.getCategory());
						if (!criteria.isEmpty()) {
							if ((criteria.getCategory() == null) || criteria.getCategory().trim().equals("")) {
								DefaultLogger.debug(this, "Category : set value.");
								criteria.setCategory(trxValue.getProfile()[0].getCategory());
							}
						}
					}
					else if ((criteria.getCategory() == null) || criteria.getCategory().trim().equals("")) {
						result.put("no_record", "no_record");
					}
				}
				trxValue.setSearchCriteria(criteria);
				if (trxValue.getSearchCriteria() == null) {
					DefaultLogger.debug(this, "trxValue.getSearchCriteria()==null");
				}
				else {
					DefaultLogger.debug(this, "Category : " + trxValue.getSearchCriteria().getCategory());
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}
		ProfileComparator c = new ProfileComparator(ProfileComparator.BY_CAT_PDTTYPE_SUBTYPE);
		if (trxValue.getProfile() != null) {
			IProfile[] actual = trxValue.getProfile();
			Arrays.sort(actual, c);
			actual = sortBuyerSupplier(actual);
			trxValue.setProfile(actual);
		}
		if (trxValue.getStagingProfile() != null) {
			IProfile[] stage = trxValue.getStagingProfile();
			Arrays.sort(stage, c);
			stage = sortBuyerSupplier(stage);
			trxValue.setStagingProfile(stage);
		}
		result.put("commProfileTrxValue", trxValue);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private IProfile[] sortBuyerSupplier(IProfile[] profile) {
		SupplierComparator s = new SupplierComparator();
		BuyerComparator b = new BuyerComparator();

		if (profile != null) {
			for (int i = 0; i < profile.length; i++) {
				ISupplier[] supplier = profile[i].getSuppliers();
				if (supplier != null) {
					Arrays.sort(supplier, s);
				}
				IBuyer[] buyer = profile[i].getBuyers();
				if (buyer != null) {
					Arrays.sort(buyer, b);
				}
				((OBProfile) profile[i]).setSuppliers(supplier);
				((OBProfile) profile[i]).setBuyers(buyer);
			}
		}
		return profile;
	}
}
