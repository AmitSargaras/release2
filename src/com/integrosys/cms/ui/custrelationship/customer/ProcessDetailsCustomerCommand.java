package com.integrosys.cms.ui.custrelationship.customer;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cci.proxy.CCICustomerProxyFactory;
import com.integrosys.cms.app.cci.proxy.ICCICustomerProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.host.eai.customer.bus.MainProfile;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

public class ProcessDetailsCustomerCommand extends AbstractCommand implements
		ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ProcessDetailsCustomerCommand() {

	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {		
				{ "cifNo", "java.lang.String", REQUEST_SCOPE },
				{ "cifSource", "java.lang.String", REQUEST_SCOPE },
				{ "cifName", "java.lang.String", REQUEST_SCOPE },
				{ "scifNo", "java.lang.String", SERVICE_SCOPE },
				{ "scifSource", "java.lang.String", SERVICE_SCOPE },
				{ "scifName", "java.lang.String", SERVICE_SCOPE },
				{ "fam", "java.lang.String", REQUEST_SCOPE },
				{ "famcode", "java.lang.String", REQUEST_SCOPE },
				{ "transactionID", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "sub_profile_id", "java.lang.String", REQUEST_SCOPE },
				{ "customerType", "java.lang.String", REQUEST_SCOPE },
				{ "iCCICustomer", "java.lang.String", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {				
				{ "cifNo", "java.lang.String", REQUEST_SCOPE },
				{ "cifSource", "java.lang.String", REQUEST_SCOPE },
				{ "cifName", "java.lang.String", REQUEST_SCOPE },
				{ "scifNo", "java.lang.String", SERVICE_SCOPE },
				{ "scifSource", "java.lang.String", SERVICE_SCOPE },
				{ "scifName", "java.lang.String", SERVICE_SCOPE },
				{ "customerOb",
						"com.integrosys.cms.app.customer.bus.OBCMSCustomer",
						REQUEST_SCOPE },
				{ "limitprofileOb",
						"com.integrosys.cms.app.limit.bus.OBLimitProfile",
						REQUEST_SCOPE },
				{
						"trxValue",
						"com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue",
						SERVICE_SCOPE },
				{ "transactionID", "java.lang.String", REQUEST_SCOPE },
				{ "fam", "java.lang.String", SERVICE_SCOPE },
				{ "famcode", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "isMainBorrowerOnly", "java.lang.String", GLOBAL_SCOPE },
				{ "sub_profile_id", "java.lang.String", SERVICE_SCOPE },
				{ "customerType", "java.lang.String", SERVICE_SCOPE },
		{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						GLOBAL_SCOPE },					
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *             on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *             on errors
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {

		DefaultLogger.debug(this, "Inside doExecute()");

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		String customerType = null;
		String fam = null;
		String famcode = null;
		ICMSCustomer custOB = null;
		ILimitProfileTrxValue trxLimitProfile = null;
		ArrayList mainBorrowerList = new ArrayList();
		String isMainBorrowerOnly = "N";

		String event = (String) map.get("event");

		ILimitProfile aILimitProfile = null;
		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
		ICCICustomerProxy cciCustproxy = CCICustomerProxyFactory.getProxy();

		// Retrieve Customer records based on CIFNo & CIFSource
		// External Customer Search Integration
		String custID = (String) map.get("sub_profile_id");
		String cifNo = (String) map.get("cifNo");
		String cifSource = (String) map.get("cifSource");
		String cifName = (String) map.get("cifName");
		
		// if we can't get it from request scope when return 
		// from customer search of shareholder and cust rel
		// get it from service scope instead
		if (StringUtils.isEmpty(cifName))
			cifName = (String)map.get("scifName");
		
		if (StringUtils.isEmpty(cifNo))
			cifNo = (String)map.get("scifNo");
		
		if (StringUtils.isEmpty(cifSource))
			cifSource = (String)map.get("scifSource");
		
		if (StringUtils.isEmpty(cifNo)||StringUtils.isEmpty(cifSource))
				throw new CommandProcessingException("Invalid CifNo or CifSource");

		try {
			DefaultLogger.debug(this,
					"** Get Main Profile or Create Dummy Record **");

			DefaultLogger.debug(this, cifNo + cifSource + cifName);
			DefaultLogger.debug(this, custID);

			
//			MainProfile mp = EAICustomerHelper.getInstance()
//					.getMainProfileOrCreateDummy(new CastorDb(true), cifNo,
//							cifSource, cifName);
        	MainProfile mirrorMainProfile=new MainProfile();
        	mirrorMainProfile.setSubProfilePrimaryKey(Long.parseLong( custID ) );
			mirrorMainProfile.setCustomerNameLong(cifName);
        	mirrorMainProfile.setCustomerNameShort(cifName);
        	//mirrorMainProfile.setJDOIncorporationDate(col.getIncorporationDate());
        	//mirrorMainProfile.setIdNo(StringUtils.defaultString(col.getIdNO()));
        	
			MainProfile mp = mirrorMainProfile;
			DefaultLogger.debug(this, "getSubProfilePrimaryKey"
					+ mp.getSubProfilePrimaryKey());

			// String sub_profile_id = (String) map.get("sub_profile_id"); // It
			// is actually lmt_profile_id
			String sub_profile_id1 = "" + mp.getSubProfilePrimaryKey();

			// if (sub_profile_id != null) {
			// String sub_profile_id1 = cciCustproxy.searchCustomer(Long
			// .parseLong(sub_profile_id));
			// System.out.println("sub_profile_id1 = " + sub_profile_id1);
			if (sub_profile_id1 != null) {
				custOB = this.getCustomer(sub_profile_id1);
			}
			if (custOB != null) {
				mainBorrowerList = custproxy.getMBlistByCBleId(custOB
						.getCustomerID());
				if (mainBorrowerList == null || mainBorrowerList.size() == 0) {
					isMainBorrowerOnly = "Y";
				}

				if (custOB.getNonBorrowerInd()) {
					if (Long.toString(custOB.getCustomerID()) != null) {
						fam = (String) limitProxy.getFAMNameByCustomer(
								custOB.getCustomerID()).get(
								ICMSConstant.FAM_NAME);
						famcode = (String) limitProxy.getFAMNameByCustomer(
								custOB.getCustomerID()).get(
								ICMSConstant.FAM_CODE);
					}
				}
				ICMSLegalEntity legal = custOB.getCMSLegalEntity();

				customerType = legal.getCustomerType();

			}
			// }
			DefaultLogger.debug(this, "custOB " + custOB);

			result.put("limitprofileOb", aILimitProfile);
			result.put("customerOb", custOB);
			result.put(IGlobalConstant.GLOBAL_CUSTOMER_OBJ, custOB);
			result.put("isMainBorrowerOnly", isMainBorrowerOnly);
			result.put("fam", fam);
			result.put("famcode", famcode);
			result.put("customerType", customerType);
			
			// setting the customer variable into service scope
			result.put("scifNo", cifNo);
			result.put("scifSource", cifSource);
			result.put("scifName", cifName);
			
			
			
			DefaultLogger.debug(this,"sub_profile_id:"+sub_profile_id1);			
			
			result.put("sub_profile_id", sub_profile_id1);

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,
					exceptionMap);
			return returnMap;
		} catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute", e);
			throw (new CommandProcessingException(e.getMessage()));
		}
	}

	private ICMSCustomer getCustomer(String sub_profile_id) {
		ICMSCustomer custOB = null;
		ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
		try {
			custOB = custproxy.getCustomer(Long.parseLong(sub_profile_id));
			if (custOB != null) {
				return custOB;
			}
		} catch (Exception e) {

		}
		return custOB;

	}

	private ILimitProfile getLimitProfile(String limitProfileID) {
		ILimitProfile aILimitProfile = null;
		ILimitProfileTrxValue trxLimitProfile = null;
		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		try {
			trxLimitProfile = limitProxy.getTrxLimitProfile(Long
					.parseLong(limitProfileID));
			aILimitProfile = trxLimitProfile.getLimitProfile();
			if (aILimitProfile != null) {
				return aILimitProfile;
			}
		} catch (Exception e) {

		}
		return aILimitProfile;

	}

}
