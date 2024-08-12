/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genddn;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.ddn.bus.IDDN;
import com.integrosys.cms.app.ddn.bus.IDDNCustomerDetail;
import com.integrosys.cms.app.ddn.bus.IDDNItem;
import com.integrosys.cms.app.ddn.proxy.DDNProxyManagerFactory;
import com.integrosys.cms.app.ddn.proxy.IDDNProxyManager;
import com.integrosys.cms.app.ddn.trx.IDDNTrxValue;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: whuang $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2005/08/30 13:27:42 $ Tag: $Name: $
 */
public class SubmitGenerateDDNCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public SubmitGenerateDDNCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "certTrxVal", "com.integrosys.cms.app.ddn.trx.IDDNTrxValue", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.ddn.bus.IDDN", FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ "custDetail", "com.integrosys.cms.app.ddn.bus.IDDNCustomerDetail", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "certTrxVal", "com.integrosys.cms.app.ddn.trx.IDDNTrxValue", SERVICE_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			IDDNTrxValue certTrxVal = (IDDNTrxValue) map.get("certTrxVal");
			IDDN cert = (IDDN) map.get("cert");
			ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			IDDNProxyManager proxy = DDNProxyManagerFactory.getDDNProxyManager();
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ctx.setTrxCountryOrigin(limitProfile.getOriginatingLocation().getCountryCode());
			IDDNCustomerDetail custDetails = (IDDNCustomerDetail) map.get("custDetail");
			// persist cust details into cert
			cert.setLegalID(custDetails.getLegalID());
			cert.setLegalName(custDetails.getLegalName());
			cert.setCustomerName(custDetails.getCustomerName());
			cert.setSubProfileID(custDetails.getCustomerReference());
			cert.setCreditGradeCode(custDetails.getCreditGrade().getCGCode());
			cert.setCustSegmentCode(custDetails.getCustomerSegmentCode());
			cert.setFamCode(custDetails.getFamCode());
			cert.setFamName(custDetails.getFamName());
			cert.setBcaApprovalDate(custDetails.getApprovalDate());
			cert.setBcaApprovalAuthority(custDetails.getApprovalAuthority());
			cert.setBcaNextReviewDate(custDetails.getNextReviewDate());
			cert.setBcaExtReviewDate(custDetails.getExtReviewDate());
			cert.setBcaOrigCtry(custDetails.getOriginatingLocation().getCountryCode());
			cert.setBcaOrigOrg(custDetails.getOriginatingLocation().getOrganisationCode());
			// persist info into cert item
			ILimit[] limits = limitProfile.getLimits();
			IDDNItem[] ddnItems = cert.getDDNItemList();
//			if ((limits != null) && (ddnItems != null)) {
//				for (int x = 0; x < ddnItems.length; x++) {
//					IDDNItem item = ddnItems[x];
//					for (int y = 0; y < limits.length; y++) {
//						ILimit limit = limits[y];
//						item.setProductType(item.getProductDesc());
//						item.setApprovalLimitDate(limitProfile.getApprovalDate());
//						item.setCoBorrowName(item.getCoBorrowerName());
//						item.setCoBorrowLegalID(item.getCoBorrowerLegalID());
//						item.setOutLimitRef(item.getOuterLimitRef());
//						if (!item.getLimitType().equals(ICMSConstant.CCC_CB_INNER_LIMIT)) {
//							if (item.getLimitID() == limit.getLimitID()) {
//								item.setApprovedLimitAmt(limit.getApprovedLimitAmount().getAmountAsBigDecimal());
//								item.setBkgLoctnCtry(limit.getBookingLocation().getCountryCode());
//								item.setBkgLoctnOrg(limit.getBookingLocation().getOrganisationCode());
//								item.setSecurityTypes(limit.getLimitSecuredType());
//								item.setProductType(item.getProductDesc());
//								item.setApprovalLimitDate(limitProfile.getApprovalDate());
//								item.setCoBorrowName(item.getCoBorrowerName());
//								item.setCoBorrowLegalID(item.getCoBorrowerLegalID());
//								item.setOutLimitRef(item.getOuterLimitRef());
//							}
//						}
//						else {
//							ICoBorrowerLimit[] cbLimits = limit.getCoBorrowerLimits();
//							item.setProductType(item.getProductDesc());
//							item.setCoBorrowName(item.getCoBorrowerName());
//							item.setCoBorrowLegalID(item.getCoBorrowerLegalID());
//							item.setOutLimitRef(item.getOuterLimitRef());
//							if (cbLimits != null) {
//								for (int i = 0; i < cbLimits.length; i++) {
//									if (item.getLimitID() == cbLimits[i].getLimitID()) {
//										item.setApprovedLimitAmt(cbLimits[i].getApprovedLimitAmount()
//												.getAmountAsBigDecimal());
//										item.setBkgLoctnCtry(cbLimits[i].getBookingLocation().getCountryCode());
//										item.setBkgLoctnOrg(cbLimits[i].getBookingLocation().getOrganisationCode());
//										item.setApprovalLimitDate(limitProfile.getApprovalDate());
//										item.setProductType(item.getProductDesc());
//										item.setCoBorrowName(item.getCoBorrowerName());
//										item.setCoBorrowLegalID(item.getCoBorrowerLegalID());
//										item.setOutLimitRef(item.getOuterLimitRef());
//									}
//								}
//							}
//						}
//					}
//				}
//			}
			// set to differentiate update remark and generate DDN.
			String eventStr = (String) map.get("event");
			if (eventStr != null) {
				DefaultLogger.debug(this, "event " + eventStr);
				if ("submit_ddn".equals(eventStr)) {
					certTrxVal.setTransactionSubType(null);
				}
				else if ("submit_remarks".equals(eventStr)) {
					certTrxVal.setTransactionSubType(ICMSConstant.ACTION_MAKER_UPDATE_DDN_SUBTYPE);
				}
				else {
					certTrxVal.setTransactionSubType(null);
				}
			}
			else {
				DefaultLogger.debug(this, "event null" + eventStr);

			}

			if ((certTrxVal != null) && ICMSConstant.STATE_REJECTED.equals(certTrxVal.getStatus())) {
				DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>maker edit generate");
				certTrxVal = proxy.makerEditRejectedGenerateDDN(ctx, certTrxVal, cert);
			}
			else {
				DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>maker generate");
				// certTrxVal =
				// proxy.makerGenerateNewBcaDDN(ctx,certTrxVal,cert);
				certTrxVal = proxy.makerGenerateDDN(ctx, certTrxVal, cert);
			}
			resultMap.put("request.ITrxValue", certTrxVal);
			resultMap.put("certTrxVal", certTrxVal);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
