/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/guarantees/GuaranteesMapperHelper.java,v 1.14 2005/08/24 08:41:31 hshii Exp $
 */

//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.guarantees;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.base.uiinfra.mapper.MapperUtil;
import com.integrosys.cms.app.collateral.bus.CollateralValuator;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.OBLimitCharge;
import com.integrosys.cms.app.collateral.bus.type.guarantee.IGuaranteeCollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail.ILineDetail;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink.IFeeDetails;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink.OBFeeDetails;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.bus.OBState;
import com.integrosys.cms.ui.collateral.CollateralConstant;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.cms.ui.collateral.SecuritySubTypeUtil;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2005/08/24 08:41:31 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class GuaranteesMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		GuaranteesForm aForm = (GuaranteesForm) cForm;
		IGuaranteeCollateral iObj = (IGuaranteeCollateral) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug("Inside GuaranteesMapperHelper - mapFormToOB", "Locale is: " + locale);

		DefaultLogger.debug("GuaranteesMapperHelper", "GuaranteesMapperHelper  mapFormToOB  , iObj.getCurrencyCode()"
				+ iObj.getCurrencyCode());
		DefaultLogger.debug("GuaranteesMapperHelper", "  mapFormToOB  , iObj.getCurrencyCode()"
				+ iObj.getGuaranteeCcyCode());

		try {

			if (CollateralConstant.FEE_DETAILS.equals(aForm.getItemType())) {
				if (aForm.getDeleteItem() != null) {
					String[] id = aForm.getDeleteItem();
					IFeeDetails[] oldList = iObj.getFeeDetails();
					int numDelete = SecuritySubTypeUtil.getNumberOfDelete(id, oldList.length);
					if (numDelete != 0) {
						IFeeDetails[] newList = new OBFeeDetails[oldList.length - numDelete];
						newList = (IFeeDetails[]) SecuritySubTypeUtil.deleteObjByList(oldList, newList, id);
						iObj.setFeeDetails(newList);
					}
				}
			}
			iObj.setDescription(aForm.getDescGuarantee());
			iObj.setReferenceNo(aForm.getGuaRefNo());
			if(!"".equals(iObj.getCurrencyCode())){
				iObj.setGuaranteeCcyCode(iObj.getCurrencyCode());
			}else if(!"".equals(iObj.getSCICurrencyCode())){
	    	  iObj.setGuaranteeCcyCode(iObj.getSCICurrencyCode());
			}
			boolean amtChanged = false;
			Amount amt = null;

			amtChanged = false;
			amt = null;
			if (aForm.getAmtGuarantee().equals("") && (iObj.getGuaranteeAmount() != null)
					&& (iObj.getGuaranteeAmount().getAmount() >= 0)) {
				amtChanged = true;
				amt = CurrencyManager.convertToAmount(locale, iObj.getGuaranteeCcyCode(), "0");
			}
			else if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAmtGuarantee())) {
				double guaranteeAmt = MapperUtil.mapStringToDouble(aForm.getAmtGuarantee(), locale);
                if (iObj.getGuaranteeAmount() != null) {
					if (iObj.getGuaranteeAmount().getAmount() != guaranteeAmt) {
                        amtChanged = true;
                    }
                } else { 
                    amtChanged = true;
                }
                amt = CurrencyManager.convertToAmount(locale, iObj.getGuaranteeCcyCode(), aForm.getAmtGuarantee());
			}
			iObj.setGuaranteeAmount(amt);

            //========= cz: this set of codes replaced to call CollateralValuator instead =======//
            // For guarantee same currency, change in guarantee amount update
			// the valuation date
//			if (iObj!=null && iObj.getCollateralSubType()!=null) {
//				String subtypecode = iObj.getCollateralSubType().getSubTypeCode();
//				if (amtChanged
//						&& (subtypecode.equals(ICMSConstant.COLTYPE_GUARANTEE_BANK_SAMECCY) || subtypecode
//							.equals(ICMSConstant.COLTYPE_GUARANTEE_SBLC_SAMECCY))) {
//					IValuation objVal = iObj.getValuation();
//					if (objVal == null) {
//						objVal = new OBValuation();
//					}
//					objVal.setValuationDate(DateUtil.getDate());
//					iObj.setValuation(objVal);
//				}
//			}

            if (iObj!=null && amtChanged) {
                try {
                    CollateralValuator valuator = new CollateralValuator();
                    valuator.setCollateralCMVFSV(iObj);
                }
                catch (Exception e) {
                    DefaultLogger.warn("GuaranteesMapperHelper", "Collateral ID: \t [" + iObj.getCollateralID() + "] \t" +
                            "Security Number: \t [" + iObj.getSCISecurityID() + "] \t " +
                            "[Error in calculating cmv and fsv] " + "[" + e.getMessage() + "]");
                }                
            }

            if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDateGuarantee())) {
				Date stageDate = CollateralMapper
						.compareDate(locale, iObj.getGuaranteeDate(), aForm.getDateGuarantee());
				iObj.setGuaranteeDate(stageDate);
			}
			else {
				iObj.setGuaranteeDate(null);
			}

			iObj.setIssuingBank(aForm.getSecIssueBank());
			iObj.setReimbursementBankEntryCode(aForm.getReimbursementBankEntryCode());

			iObj.setIssuingBankCountry(aForm.getCountrySecurityIssBank());

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getBankCountryRiskAppr())) {
				iObj.setIsBankCountryRiskApproval(aForm.getBankCountryRiskAppr());
			}

			iObj.setBeneficiaryName(aForm.getBeneName());

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getHoldingPeriod())) {
				iObj.setHoldingPeriod(aForm.getHoldingPeriod());
			}
			else {
				iObj.setHoldingPeriod(null);
			}

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getHoldingPeriodTimeUnit())) {
				iObj.setHoldingPeriodTimeUnit(aForm.getHoldingPeriodTimeUnit());
			}
			else {
				iObj.setHoldingPeriodTimeUnit(null);
			}

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getClaimPeriod())) {
				iObj.setClaimPeriod(aForm.getClaimPeriod());
			}
			else {
				iObj.setClaimPeriod(null);
			}
						
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getClaimPeriodUnit())) {
				iObj.setClaimPeriodUnit(aForm.getClaimPeriodUnit());
			}
			else {
				iObj.setClaimPeriodUnit(null);
			}

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getClaimDate())) {
				iObj.setClaimDate(UIUtil.mapFormString_OBDate(locale, iObj.getClaimDate(), aForm.getClaimDate()));
			}
			else {
				iObj.setClaimDate(null);
			}

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getSecuredPortion())) {
				iObj.setSecuredPortion(Integer.parseInt(aForm.getSecuredPortion().trim()));
			}
			else {
				iObj.setSecuredPortion(-1);
			}

			amt = null;
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getSecuredAmountOrigin())) {
				amt = CurrencyManager.convertToAmount(locale, iObj.getGuaranteeCcyCode(), "0");
			}
			else {
				amt = CurrencyManager.convertToAmount(locale, iObj.getGuaranteeCcyCode(), aForm.getSecuredAmountOrigin());
			}
			iObj.setSecuredAmountOrigin(amt);

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getUnsecuredPortion()))
				iObj.setUnsecuredPortion(Integer.parseInt(aForm.getUnsecuredPortion().trim()));
			else {
				iObj.setUnsecuredPortion(-1);
			}

			amtChanged = false;
			amt = null;
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getUnsecuredAmountOrigin())) {
				amt = CurrencyManager.convertToAmount(locale, iObj.getGuaranteeCcyCode(), "0");
			} else {
				amt = CurrencyManager.convertToAmount(locale, iObj.getGuaranteeCcyCode(), aForm.getUnsecuredAmountOrigin());
			}
			iObj.setUnsecuredAmountOrigin(amt);
			
			/* It is calculated, not input from UI
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getSecuredAmountCalc())) {
				amt = CurrencyManager.convertToAmount(locale, iObj.getGuaranteeCcyCode(), "0");
			} else {
				amt = CurrencyManager.convertToAmount(locale, iObj.getGuaranteeCcyCode(), aForm.getSecuredAmountCalc());
			}
			
			iObj.setSecuredAmountCalc(amt);

			if (AbstractCommonMapper.isEmptyOrNull(aForm.getUnsecuredAmountCalc())) {
				amt = CurrencyManager.convertToAmount(locale, iObj.getGuaranteeCcyCode(), "0");
			} else {
				amt = CurrencyManager.convertToAmount(locale, iObj.getGuaranteeCcyCode(), aForm.getUnsecuredAmountCalc());
			}

			iObj.setUnsecuredAmountCalc(amt);

			if (AbstractCommonMapper.isEmptyOrNull(aForm.getGuaranteeAmtCalc())) {
				amt = CurrencyManager.convertToAmount(locale, iObj.getGuaranteeCcyCode(), "0");
			} else {
				amt = CurrencyManager.convertToAmount(locale, iObj.getGuaranteeCcyCode(), aForm.getGuaranteeAmtCalc());
			}

			iObj.setGuaranteeAmtCalc(amt);
			*/
			
			iObj.setICCRulesComplied(aForm.getIccRulesComplied());
			iObj.setURDComplied(aForm.getUrdComplied());
			iObj.setUCP600Complied(aForm.getUcp600Complied());
			iObj.setIssuingDate((DateUtil.convertDate(locale, aForm.getIssuingDate())));
			iObj.setComments(aForm.getComments());
			
			// Start - Lines added by Dattatray Thorat for Guarantee Security on 15th July 2011 
			if(aForm.getTelephoneNumber() != null && !aForm.getTelephoneNumber().equals(""))
				{
				iObj.setTelephoneNumber(aForm.getTelephoneNumber());
				}else{
					iObj.setTelephoneNumber("0");
				}
			
			iObj.setGuarantersDunsNumber(aForm.getGuarantersDunsNumber());
			iObj.setGuarantersPam(aForm.getGuarantersPam());
			iObj.setGuarantersName(aForm.getGuarantersName());
			iObj.setGuarantersNamePrefix(aForm.getGuarantersNamePrefix());
			iObj.setGuarantersFullName(aForm.getGuarantersFullName());
			iObj.setAddressLine1(aForm.getAddressLine1());
			iObj.setAddressLine2(aForm.getAddressLine2());
			iObj.setAddressLine3(aForm.getAddressLine3());
			if(aForm.getRamId()!=null&&!"".trim().equals(aForm.getRamId())){
				iObj.setRamId(aForm.getRamId());
			}
			else{
				iObj.setRamId("0");
			}
			ICity city = new OBCity();
			if(aForm.getCity()!=null && !aForm.getCity().equals("")){
				iObj.setCity(aForm.getCity());
			}	
			else{
				iObj.setCity("");
			}
			IState state = new OBState();
			if(aForm.getState()!=null && !aForm.getState().equals("")){
				iObj.setState(aForm.getState());
			}else{
				iObj.setState("");
			}	
			
			IRegion region = new OBRegion();
			if(aForm.getRegion()!=null && !aForm.getRegion().equals("")){
				iObj.setRegion(aForm.getRegion());
			}else{
				iObj.setRegion("");
			}		
			
			ICountry country = new OBCountry();
			if(aForm.getCountry()!=null && !aForm.getCountry().equals("")){
				iObj.setCountry(aForm.getCountry());
			}else{
				iObj.setCountry("");
			}		
			if(aForm.getTelephoneAreaCode()!=null && !aForm.getTelephoneAreaCode().equals(""))
				iObj.setTelephoneAreaCode(aForm.getTelephoneAreaCode());
			
			iObj.setRating(aForm.getRating());
			iObj.setRecourse(aForm.getRecourse());
			iObj.setDiscriptionOfAssets(aForm.getDiscriptionOfAssets());
			
			
			iObj.setAssetStatement(aForm.getAssetStatement());
			iObj.setGuarantorType(aForm.getGuarantorType());
			iObj.setDistrict(aForm.getDistrict());
			iObj.setPinCode(aForm.getPinCode());
			iObj.setGuarantorNature(aForm.getGuarantorNature());
			
			if(!AbstractCommonMapper.isEmptyOrNull(aForm.getFollowUpDate())) {
				iObj.setFollowUpDate((DateUtil.convertDate(locale, aForm.getFollowUpDate())));
			}
			// End - Lines added by Dattatray Thorat for Guarantee Security on 15th July 2011
			populateChargeDetails(iObj);

		}
		catch (Exception e) {
			DefaultLogger.debug("GuaranteesMapperHelper", "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return iObj;
	}

	protected static void populateChargeDetails(IGuaranteeCollateral col) {
		Amount guaranteeAmt = col.getGuaranteeAmount();
		Date guaranteeDate = col.getGuaranteeDate();

		ILimitCharge charge = null;
		ILimitCharge limitCharges[] = col.getLimitCharges();
		if (limitCharges != null) {
			if (limitCharges.length > 0) {
				charge = limitCharges[0];
			}
			else {
				limitCharges = new OBLimitCharge[1];
			}
		}
		else {
			limitCharges = new OBLimitCharge[1];
		}

		if (charge == null) {
			charge = new OBLimitCharge();
			charge.setChargeCcyCode(col.getGuaranteeCcyCode());
		}
		else {
			charge.setChargeCcyCode(col.getGuaranteeCcyCode());
		}

		charge.setChargeAmount(guaranteeAmt);
		charge.setLegalChargeDate(guaranteeDate);

		limitCharges[0] = charge;
		col.setLimitCharges(limitCharges);
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		GuaranteesForm aForm = (GuaranteesForm) cForm;
		IGuaranteeCollateral iObj = (IGuaranteeCollateral) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		DefaultLogger.debug(" Inside GuaranteesMapperHelper - mapOBToForm", "Locale is: " + locale);

		try {
			aForm.setDescGuarantee(iObj.getDescription());

			aForm.setGuaRefNo(iObj.getReferenceNo());
			if ((iObj.getGuaranteeAmount() != null) && (iObj.getGuaranteeAmount().getCurrencyCode() != null)) {
				if (iObj.getGuaranteeAmount().getAmount() >= 0) {
				//	int guaranteeAmt = (int) (iObj.getGuaranteeAmount().getAmountAsDouble());
				//	aForm.setAmtGuarantee(Integer.toString(guaranteeAmt));
					aForm.setAmtGuarantee(CurrencyManager.convertToString(locale, iObj.getGuaranteeAmount()));
				}
			}
			aForm.setCurrencyGuarantee(iObj.getGuaranteeCcyCode());
			aForm.setDateGuarantee(DateUtil.formatDate(locale, iObj.getGuaranteeDate()));
			aForm.setSecIssueBank(iObj.getIssuingBank());
			aForm.setCountrySecurityIssBank(iObj.getIssuingBankCountry());
			aForm.setBankCountryRiskAppr(iObj.getIsBankCountryRiskApproval());
			aForm.setBeneName(iObj.getBeneficiaryName());
			aForm.setReimbursementBankEntryCode(iObj.getReimbursementBankEntryCode());

			aForm.setDeleteItem(new String[0]);
			aForm.setDeleteInsItem(new String[0]);

			if ((iObj.getHoldingPeriod() != null)) {
				aForm.setHoldingPeriod(iObj.getHoldingPeriod());
			}
			aForm.setHoldingPeriodTimeUnit(iObj.getHoldingPeriodTimeUnit());

			if ((iObj.getClaimPeriod() != null)) {
				aForm.setClaimPeriod(iObj.getClaimPeriod());
			}
			aForm.setClaimPeriodUnit(iObj.getClaimPeriodUnit());
			aForm.setClaimDate(UIUtil.mapOBDate_FormString(locale, iObj.getClaimDate()));
			
			if (iObj.getSecuredPortion()==ICMSConstant.INT_INVALID_VALUE) aForm.setSecuredPortion("");
			else aForm.setSecuredPortion(String.valueOf(iObj.getSecuredPortion()));
			
			if (iObj.getUnsecuredPortion()==ICMSConstant.INT_INVALID_VALUE) aForm.setUnsecuredPortion("");
			else aForm.setUnsecuredPortion(String.valueOf(iObj.getUnsecuredPortion()));

			aForm.setSecuredAmountCalc("");
			if ((iObj.getSecuredAmountCalc() != null) && (iObj.getSecuredAmountCalc().getCurrencyCode() != null)) {
				if (iObj.getSecuredAmountCalc().getAmount() >= 0) {
					aForm.setSecuredAmountCalc(CurrencyManager.convertToString(locale, iObj.getSecuredAmountCalc()));
				}
			} 

			aForm.setUnsecuredAmountCalc("");
			if ((iObj.getUnsecuredAmountCalc() != null) && (iObj.getUnsecuredAmountCalc().getCurrencyCode() != null)) {
				if (iObj.getUnsecuredAmountCalc().getAmount() >= 0) {
					aForm.setUnsecuredAmountCalc(CurrencyManager.convertToString(locale, iObj.getUnsecuredAmountCalc()));
				}
			} 

			aForm.setSecuredAmountOrigin("");
			if ((iObj.getSecuredAmountOrigin() != null) && (iObj.getSecuredAmountOrigin().getCurrencyCode() != null)) {
				if (iObj.getSecuredAmountOrigin().getAmount() >= 0) {
					aForm.setSecuredAmountOrigin(CurrencyManager.convertToString(locale, iObj.getSecuredAmountOrigin()));
				}
			} 

			aForm.setUnsecuredAmountOrigin("");
			if ((iObj.getUnsecuredAmountOrigin() != null) && (iObj.getUnsecuredAmountOrigin().getCurrencyCode() != null)) {
				if (iObj.getUnsecuredAmountOrigin().getAmount() >= 0) {
					aForm.setUnsecuredAmountOrigin(CurrencyManager.convertToString(locale, iObj.getUnsecuredAmountOrigin()));
				}
			}

			aForm.setGuaranteeAmtCalc("");
			if ((iObj.getGuaranteeAmtCalc() != null) && (iObj.getGuaranteeAmtCalc().getCurrencyCode() != null)) {
				if (iObj.getGuaranteeAmtCalc().getAmount() >= 0) {
					int guaranteeAmtCalc = (int) (iObj.getGuaranteeAmtCalc().getAmountAsDouble());
					aForm.setGuaranteeAmtCalc(Integer.toString(guaranteeAmtCalc));
				}
			} 
			
			aForm.setIccRulesComplied(iObj.getICCRulesComplied());
			aForm.setUrdComplied(iObj.getURDComplied());
			aForm.setUcp600Complied(iObj.getUCP600Complied());
			aForm.setComments(iObj.getComments());
			
			// Start - Lines added by Dattatray Thorat for Guarantee Security on 15th July 2011 
			if(iObj.getTelephoneNumber() != null)
				aForm.setTelephoneNumber(iObj.getTelephoneNumber());
			else
				aForm.setTelephoneNumber(null);
			
			aForm.setGuarantersDunsNumber(iObj.getGuarantersDunsNumber());
			aForm.setGuarantersPam(iObj.getGuarantersPam());
			aForm.setGuarantersName(iObj.getGuarantersName());
			aForm.setGuarantersNamePrefix(iObj.getGuarantersNamePrefix());
			aForm.setGuarantersFullName(iObj.getGuarantersFullName());
			aForm.setAddressLine1(iObj.getAddressLine1());
			aForm.setAddressLine2(iObj.getAddressLine2());
			aForm.setAddressLine3(iObj.getAddressLine3());
			if(iObj.getRamId()==null){
				aForm.setRamId("");
			}else{
			aForm.setRamId(iObj.getRamId());
			}
			if(iObj.getCity()!=null)
				aForm.setCity(iObj.getCity());
			if(iObj.getState()!=null)
				aForm.setState(iObj.getState());
			if(iObj.getRegion()!=null)
				aForm.setRegion(iObj.getRegion());
			if(iObj.getCountry()!=null)
				aForm.setCountry(iObj.getCountry());
			if(iObj.getTelephoneAreaCode()!= null)
				aForm.setTelephoneAreaCode(iObj.getTelephoneAreaCode());
			else
				aForm.setTelephoneAreaCode(null);
			aForm.setRating(iObj.getRating());
			aForm.setRecourse(iObj.getRecourse());
			aForm.setDiscriptionOfAssets(iObj.getDiscriptionOfAssets());
			
			aForm.setAssetStatement(iObj.getAssetStatement());
			aForm.setGuarantorType(iObj.getGuarantorType());
			aForm.setDistrict(iObj.getDistrict());
			aForm.setPinCode(iObj.getPinCode());
			aForm.setGuarantorNature(iObj.getGuarantorNature());
			
			// End - Lines added by Dattatray Thorat for Guarantee Security on 15th July 2011
			
			if (iObj.getIssuingDate()!=null) aForm.setIssuingDate(DateUtil.formatDate(locale, iObj.getIssuingDate()));
			
			aForm.setFollowUpDate(DateUtil.formatDate(locale, iObj.getFollowUpDate()));
			
			ILineDetail[] lineDetails = iObj.getLineDetails();
			if(lineDetails != null && lineDetails.length > 0) {
				BigDecimal totalLineLevelSecurityOMV = BigDecimal.valueOf(0);
				for(ILineDetail lineDetail: lineDetails) {
					if (lineDetail.getLineLevelSecurityOMV()!=null) {
						totalLineLevelSecurityOMV = totalLineLevelSecurityOMV.add(lineDetail.getLineLevelSecurityOMV());
					}
				}
				aForm.setTotalLineLevelSecurityOMV(totalLineLevelSecurityOMV.toPlainString());
			}
			
		}
		catch (Exception e) {
			DefaultLogger.debug("GuaranteesMapperHelper", "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		DefaultLogger.debug(" Existing GuaranteesMapperHelper - mapOBToForm", "Locale is: " + locale);

		return aForm;
	}

}
