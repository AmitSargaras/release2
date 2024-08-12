///*
//* Copyright Integro Technologies Pte Ltd
//* $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/others/othersa/OthersaMapperHelper.java,
//*/
////STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
//package com.integrosys.cms.ui.collateral.others.othersa;
//
//import com.integrosys.base.uiinfra.common.*;
//import com.integrosys.base.uiinfra.exception.MapperException;
//import com.integrosys.base.uiinfra.mapper.MapperUtil;
//
//import com.integrosys.cms.ui.collateral.*;
//import com.integrosys.cms.ui.collateral.others.*;
//import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
//import com.integrosys.cms.app.collateral.bus.OBInsurancePolicy;
//import com.integrosys.cms.app.collateral.bus.type.others.subtype.othersa.IOthersa;
//import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
//import com.integrosys.cms.app.common.constant.ICMSConstant;
//import com.integrosys.base.techinfra.util.DateUtil;
//import com.integrosys.base.techinfra.logger.DefaultLogger;
//import com.integrosys.base.businfra.currency.CurrencyManager;
//import com.integrosys.base.businfra.currency.Amount;
//import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
//import com.integrosys.cms.app.limit.proxy.ILimitProxy;
//
//import java.util.*;
///**
//* @author $Author: hshii $<br>
//* @version $Revision: 1.3 $
//* @since $Date: 2006/09/12 12:24:11 $
//* Tag: $Name:  $
//*/
///**
// * Created by IntelliJ IDEA.
// * User: ssathish
// * Date: Jun 22, 2003
// * Time: 4:45:05 PM
// * To change this template use Options | File Templates.
// */
//public class OthersaMapperHelper  {
//
//    public static Object mapFormToOB(CommonForm cForm, HashMap inputs,Object obj) throws MapperException {
//        IOthersa iObj = (IOthersa) obj;
//        OthersaForm aForm = (OthersaForm) cForm;
//        Locale locale = (Locale)inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
//        DefaultLogger.debug("OthersaMapperHelper - mapFormToOB", "Locale is: "+locale);
//        Date stageDate;
//        try {
//			boolean updated = false;
//			IInsurancePolicy[] insList = iObj.getInsurancePolicies();
//			IInsurancePolicy insurance = null;
//			if (insList != null && insList.length > 0) {
//				insurance = insList[0];
//			} else {
//				insurance = new OBInsurancePolicy();
//			}
//			insurance.setCurrencyCode(iObj.getCurrencyCode());
//
//			if (!aForm.getInsureName().equals(insurance.getInsurerName())) {
//				updated = true;
//				insurance.setInsurerName(aForm.getInsureName());
//			}
//
//			if (!aForm.getInsurType().equals(insurance.getInsuranceType())) {
//				updated = true;
//				insurance.setInsuranceType(aForm.getInsurType());
//			}
//
//			if (!aForm.getInsuredAgainst().equals(insurance.getInsuredAgainst())) {
//				updated =true;
//				insurance.setInsuredAgainst(aForm.getInsuredAgainst());
//			}
//			if (aForm.getInsurableAmt().equals("") && insurance.getInsurableAmount() != null &&
//					insurance.getInsurableAmount().getAmount() > 0) {
//				updated = true;
//				insurance.setInsurableAmount(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), "0"));
//			} else if (!AbstractCommonMapper.isEmptyOrNull(aForm.getInsurableAmt())) {
//				updated = true;
//				insurance.setInsurableAmount(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), aForm.getInsurableAmt()));
//			}
//			if (aForm.getInsuredAmt().equals("") && insurance.getInsuredAmount() != null &&
//					insurance.getInsuredAmount().getAmount() > 0) {
//				updated = true;
//				insurance.setInsuredAmount(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), "0"));
//			} else  if (!AbstractCommonMapper.isEmptyOrNull(aForm.getInsuredAmt())) {
//				updated = true;
//				insurance.setInsuredAmount(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), aForm.getInsuredAmt()));
//			}
//			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getEffDateInsurance())) {
//				updated = true;
//				stageDate = CollateralMapper.compareDate(locale, insurance.getEffectiveDate(), aForm.getEffDateInsurance());
//				insurance.setEffectiveDate(stageDate);
//			} else {
//				insurance.setEffectiveDate(null);
//			}
//
//			if (!aForm.getPolicyNo().equals(insurance.getPolicyNo())) {
//				updated = true;
//				insurance.setPolicyNo(aForm.getPolicyNo());
//			}
//			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getExpiryDateInsure())) {
//				updated = true;
//				stageDate = CollateralMapper.compareDate(locale, insurance.getExpiryDate(), aForm.getExpiryDateInsure());
//				insurance.setExpiryDate(stageDate);
//			} else {
//				insurance.setExpiryDate(null);
//			}
//
//			// if(!aForm.getDocumentNo().equals(insurance.getDocumentNo())) {
//			// updated = true;
//			// insurance.setDocumentNo(aForm.getDocumentNo());
//			// }
//			//
//			// if(!AbstractCommonMapper.isEmptyOrNull(aForm.getLmtProfileId()))
//			// {
//			// updated = true;
//			// insurance.setLmtProfileId(Long.parseLong(aForm.getLmtProfileId()));
//			// } else {
//			// if (insurance.getLmtProfileId() > 0)
//			// updated = true;
//			// insurance.setLmtProfileId(ICMSConstant.LONG_INVALID_VALUE);
//			//	        }
//
//			if (updated) {
//				if (insList == null || insList.length == 0) {
//					insList = new IInsurancePolicy[1];
//				}
//				insList[0] = insurance;
//				iObj.setInsurancePolicies(insList);
//			}
//        } catch (Exception e) {
//             DefaultLogger.debug("com.integrosys.cms.ui.collateral.others.othersa.OthersaMapperHelper","error is :"+e.toString());
//            throw new MapperException(e.getMessage());
//        }
//
//    	return iObj;
//    }
//
//    public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
//        IOthersa iObj = (IOthersa) obj;
//        OthersaForm aForm = (OthersaForm) cForm;
//        Locale locale = (Locale)inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
//        DefaultLogger.debug("OthersaMapperHelper - mapOBToForm", "Locale is: "+locale);
//		String from_event = (String)inputs.get("from_event");
//        try {
//			IInsurancePolicy insurance = null;
//			if (iObj.getInsurancePolicies() != null &&
//				iObj.getInsurancePolicies().length > 0) {
//				insurance = iObj.getInsurancePolicies()[0];
//			}
//			if (insurance != null) {
//				aForm.setInsureName(insurance.getInsurerName());
//				aForm.setInsurType(insurance.getInsuranceType());
//				aForm.setInsuredAgainst(insurance.getInsuredAgainst());
//				if (insurance.getInsurableAmount() != null && insurance.getInsurableAmount().getCurrencyCode() != null) {
//					if (insurance.getInsurableAmount().getAmount() > 0) {
//						aForm.setInsurableAmt(CurrencyManager.convertToString(locale, insurance.getInsurableAmount()));
//					}
//				}
//				if (insurance.getInsuredAmount() != null && insurance.getInsuredAmount().getCurrencyCode() != null) {
//					if (insurance.getInsuredAmount().getAmount() > 0) {
//						aForm.setInsuredAmt(CurrencyManager.convertToString(locale,insurance.getInsuredAmount()));
//					}
//				}
//				aForm.setEffDateInsurance(DateUtil.formatDate(locale, insurance.getEffectiveDate()));
//				aForm.setPolicyNo(insurance.getPolicyNo());
//				aForm.setExpiryDateInsure(DateUtil.formatDate(locale,insurance.getExpiryDate()));
//				// if(insurance.getDocumentNo()!= null &&
//				// insurance.getDocumentNo().trim().length()>0){
//				// aForm.setDocumentNo(insurance.getDocumentNo());
//				// }
//				// if( Long.toString(insurance.getLmtProfileId())!=null &&
//				// insurance.getLmtProfileId()!=ICMSConstant.LONG_INVALID_VALUE){
//				// DefaultLogger.debug("OthersaMapperHelper.java", "<<<<
//				// from_event: "+from_event);
//				// DefaultLogger.debug("OthersaMapperHelper.java", "<<<< event:
//				// "+aForm.getEvent());
//				// if
//				// (aForm.getEvent().equals(OthersaAction.EVENT_PREPARE_UPDATE)
//				// ||
//				// aForm.getEvent().equals(OthersaAction.EVENT_PROCESS_UPDATE)
//				// ||
//				// aForm.getEvent().equals(OthersaAction.EVENT_UPDATE_RETURN)) {
//				// aForm.setLmtProfileId(String.valueOf(insurance.getLmtProfileId()));
//				// } else {
//				// ILimitProxy limitProxy = LimitProxyFactory.getProxy();
//				// aForm.setLmtProfileId(limitProxy.getLEIdAndBCARef(insurance.getLmtProfileId()));
//				//					}
//				//				}
//
//			}
//		} catch (Exception e) {
//			 DefaultLogger.debug("com.integrosys.cms.ui.collateral.others.othersa.OthersaMapperHelper","error is :"+e.toString());
//			throw new MapperException(e.getMessage());
//		}
//		return aForm;
//	}
//
//	public static Object getObject(HashMap inputs) {
//		return ((IOthersa)((ICollateralTrxValue)inputs.get("serviceColObj")).getStagingCollateral());
//	}
//}
