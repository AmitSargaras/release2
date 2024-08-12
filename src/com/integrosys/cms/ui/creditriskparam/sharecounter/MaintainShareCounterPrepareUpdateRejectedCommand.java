/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * MaintainShareCounterPrepareUpdateRejectedCommand
 *
 * Created on 6:09:39 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.ui.creditriskparam.sharecounter;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.CreditRiskParamType;
import com.integrosys.cms.app.creditriskparam.OBShareCounter;
import com.integrosys.cms.app.creditriskparam.bus.OBCreditRiskParam;
import com.integrosys.cms.app.creditriskparam.bus.OBCreditRiskParamGroup;
import com.integrosys.cms.app.creditriskparam.proxy.CreditRiskParamProxyManagerFactory;
import com.integrosys.cms.app.creditriskparam.proxy.ICreditRiskParamProxy;
import com.integrosys.cms.app.creditriskparam.trx.OBCreditRiskParamGroupTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 26, 2007 Time: 6:09:39 PM
 */
public class MaintainShareCounterPrepareUpdateRejectedCommand extends AbstractCommand implements ICommonEventConstant {
	public String[][] getParameterDescriptor() {
		return new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ ShareCounterConstants.OFFSET, "java.lang.String", REQUEST_SCOPE },
				{ ShareCounterConstants.CURRENT_OFFSET_NUMBER, "java.lang.String", REQUEST_SCOPE },
				{ ShareCounterConstants.CREDIT_RISK_PARAM_TRX_VALUE, "java.lang.Object", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ ShareCounterConstants.SHARE_COUNTER_FORM, "java.lang.Object", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ ShareCounterConstants.CREDIT_RISK_PARAM_TRX_VALUE, "java.lang.Object", REQUEST_SCOPE },
				{ ShareCounterConstants.CREDIT_RISK_PARAM_TRX_VALUE, "java.lang.Object", SERVICE_SCOPE },
				{ ShareCounterConstants.SHARE_COUNTER_FORM, "java.lang.Object", FORM_SCOPE },
				{ ShareCounterConstants.OFFSET, "java.lang.Integer", REQUEST_SCOPE },
				{ ShareCounterConstants.LENGTH, "java.lang.Integer", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		String offsetStr = (String) hashMap.get(ShareCounterConstants.OFFSET);
		String event = (String) hashMap.get("event");

		Integer offset;

		try {
			offset = new Integer(offsetStr);
		}
		catch (Exception ex) {
			offset = ShareCounterConstants.INITIAL_OFFSET;
		}

		DefaultLogger.debug(this, "Event is  : " + event);

		if (ShareCounterConstants.SHARE_COUNTER_MAKER_PAGINATE_REJECTED.equals(event)
				|| ShareCounterConstants.SHARE_COUNTER_MAKER_UPDATE_REJECTED_ERROR.equals(event)
				|| ShareCounterConstants.SHARE_COUNTER_MAKER_REFRESH_REJECTED.equals(event)) {
			OBCreditRiskParamGroupTrxValue trxValue = (OBCreditRiskParamGroupTrxValue) hashMap
					.get(ShareCounterConstants.CREDIT_RISK_PARAM_TRX_VALUE);
			OBShareCounter obTrx = (OBShareCounter) hashMap.get(ShareCounterConstants.SHARE_COUNTER_FORM);
			OBCreditRiskParamGroup stagingData = (OBCreditRiskParamGroup) trxValue.getStagingCreditRiskParamGroup();
			OBCreditRiskParamGroup actual = (OBCreditRiskParamGroup) trxValue.getCreditRiskParamGroup();

			try {
				String[] shareStatus = obTrx.getParamShareStatus();
				if (shareStatus != null) {
					for (int i = 0; i < shareStatus.length; i++) {
						String status = CommonDataSingleton.getCodeCategoryLabelByValue(
								ShareCounterConstants.SHARE_STATUS, shareStatus[i]);
						OBCreditRiskParam[] params = stagingData.getFeedEntries();
						try {
							String feedIdString = obTrx.getFeedId()[i];
							long feedId1 = Long.parseLong(feedIdString);

							for (int j = 0; j < params.length; j++) {
								long feedId2 = params[j].getFeedId();

								if (feedId1 == feedId2) {
									if ("normal".equalsIgnoreCase(status)) {
										params[j].setIsAcceptable("Y");
									}
									else {
										params[j].setIsAcceptable("N");
									}
								}
							}
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				actual = MaintainShareCounterUtil.mergeStagingWithOb(actual, obTrx); // merge
																						// actual
																						// data
																						// in
																						// trx
																						// object
																						// so
																						// that
																						// it
																						// can
																						// seen
																						// the
																						// ui
				stagingData = MaintainShareCounterUtil.mergeStagingWithOb(stagingData, obTrx); // merge
																								// also
																								// the
																								// staging
																								// data
																								// object
																								// to
																								// be
																								// saved

				trxValue.setStagingCreditRiskParamGroup(stagingData);
				trxValue.setCreditRiskParamGroup(actual);

				// populatePolicyCap ( trxValue.getCreditRiskParamGroup
				// ().getFeedEntries () , null ) ;
				// populatePolicyCap ( trxValue.getStagingCreditRiskParamGroup
				// ().getFeedEntries () , null ) ;
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (ShareCounterConstants.SHARE_COUNTER_MAKER_UPDATE_REJECTED_ERROR.equals(event)) {
					String lastOffest = (String) hashMap.get(ShareCounterConstants.CURRENT_OFFSET_NUMBER);
					offset = new Integer(lastOffest);
				}
			}
			catch (Exception ex) {
				offset = ShareCounterConstants.INITIAL_OFFSET;
			}

			result.put(ShareCounterConstants.CREDIT_RISK_PARAM_TRX_VALUE, trxValue);
		}
		else {
			try {

				String trxId = (String) hashMap.get("TrxId");
				OBTrxContext ctx = (OBTrxContext) hashMap.get("theOBTrxContext");
				OBCreditRiskParamGroupTrxValue trxValue = new OBCreditRiskParamGroupTrxValue();
				ICreditRiskParamProxy proxy = CreditRiskParamProxyManagerFactory.getICreditRiskParamProxy();

				trxValue.setTransactionID(trxId);

				trxValue = (OBCreditRiskParamGroupTrxValue) proxy.checkerViewCreditRiskParam(ctx, trxValue, null,
						CreditRiskParamType.SHARE_COUNTER);

				DefaultLogger.debug(this, "trxValue.getReferenceID () : " + trxValue.getReferenceID());

				result.put(ShareCounterConstants.CREDIT_RISK_PARAM_TRX_VALUE, trxValue);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		result.put("offset", offset);
		result.put("length", ShareCounterConstants.FIXED_LENGTH);

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}

	// private void populatePolicyCap ( OBCreditRiskParam entries [ ] , HashMap
	// result2 )
	// {
	// if ( entries.length > 0 )
	// {
	// DefaultLogger.debug ( this , "Entered." ) ;
	// DefaultLogger.debug ( this , "entries[ 0 ].getExchange ()  : " + entries[
	// 0 ].getExchange () ) ;
	//
	// try
	// {
	// IPolicyCapProxyManager proxy2 =
	// PolicyCapProxyManagerFactory.getPolicyCapProxyManager () ;
	// IPolicyCap[] policyCapList = proxy2.getPolicyCapByExchange ( entries[ 0
	// ].getExchange () ) ;
	//
	// if ( policyCapList != null && policyCapList.length > 0 )
	// {
	// for ( int loop = 0 ; loop < entries.length ; loop ++ )
	// {
	// String broadType = entries[ loop ].getParamBoardType () ;
	// String shareStatus = entries[ loop ].getParamShareStatus () ;
	// String isIntSuspended = entries[ loop ].getIntSuspend () ;
	//
	// DefaultLogger.debug ( this , "broadType : " + broadType) ;
	// DefaultLogger.debug ( this , "shareStatus : " + shareStatus) ;
	//
	// if ( "1".equals ( shareStatus ) && ! "Y".equalsIgnoreCase (
	// isIntSuspended ) ) // share status is normal and not internally suspended
	// {
	// entries[ loop ].setIsAcceptable ( "Y" ) ;
	// }
	// else
	// {
	// entries[ loop ].setIsAcceptable ( "N" ) ;
	// }
	//
	// for ( int loop2 = 0 ; loop2 < policyCapList.length ; loop2 ++ )
	// {
	// DefaultLogger.debug ( this , "policyCapList[ loop2 ].getBoard () : " +
	// policyCapList[ loop2 ].getBoard () ) ;
	//
	//
	// DefaultLogger.debug ( this ,
	// "policyCapList[ loop2 ].getMaxCollateralCapFI () : " + policyCapList[
	// loop2 ].getMaxCollateralCapFI () ) ;
	// DefaultLogger.debug ( this ,
	// "policyCapList[ loop2 ].getQuotaCollateralCapFI () : " + policyCapList[
	// loop2 ].getQuotaCollateralCapFI () ) ;
	// DefaultLogger.debug ( this ,
	// "policyCapList[ loop2 ].getMaxCollateralCapNonFI () : " + policyCapList[
	// loop2 ].getMaxCollateralCapNonFI () ) ;
	// DefaultLogger.debug ( this ,
	// "policyCapList[ loop2 ].getQuotaCollateralCapNonFI () : " +
	// policyCapList[ loop2 ].getQuotaCollateralCapNonFI () ) ;
	//
	//
	// if ( policyCapList[ loop2 ].getMaxCollateralCapFI () ==
	// ICMSConstant.DOUBLE_INVALID_VALUE &&
	// policyCapList[ loop2 ].getQuotaCollateralCapFI () ==
	// ICMSConstant.DOUBLE_INVALID_VALUE &&
	// policyCapList[ loop2 ].getMaxCollateralCapNonFI () ==
	// ICMSConstant.DOUBLE_INVALID_VALUE &&
	// policyCapList[ loop2 ].getQuotaCollateralCapNonFI () ==
	// ICMSConstant.DOUBLE_INVALID_VALUE )
	// {
	// if ( result2 != null)
	// {
	// result2.put ( ShareCounterConstants.SHARE_COUNTER_WIP ,
	// ShareCounterConstants.SHARE_COUNTER_POLICY_NOT_SET ) ;
	// }
	//
	// break ;
	// }
	//
	//
	// if ( broadType != null && broadType.equalsIgnoreCase ( policyCapList[
	// loop2 ].getBoard () ) )
	// {
	// entries [ loop ].setMaxCollCapFi ( policyCapList[ loop2
	// ].getMaxCollateralCapFI () ) ;
	// entries [ loop ].setQuotaCollCapFi ( policyCapList[ loop2
	// ].getQuotaCollateralCapFI () ) ;
	// entries [ loop ].setMaxCollCapNonFi ( policyCapList[ loop2
	// ].getMaxCollateralCapNonFI () ) ;
	// entries [ loop ].setQuotaCollCapNonFi ( policyCapList[ loop2
	// ].getQuotaCollateralCapNonFI () ) ;
	//
	// DefaultLogger.debug ( this , "Broad type match found." ) ;
	//
	// break ;
	// }
	// }
	//
	// }
	// }
	// }
	// catch ( PolicyCapException capEx)
	// {
	// DefaultLogger.debug ( this ,
	// "Error in retrieving policy cap data for share counter : " +
	// capEx.getMessage () ) ;
	// }
	//
	// //groupParam.setFeedEntries ( entries ) ;
	//
	// }
	// else
	// {
	// DefaultLogger.debug ( this , "entries list is empty !" ) ;
	// }
	// }

}