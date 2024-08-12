/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insurancepolicy/AddtionalDocumentFacilityDetailsMapper.java,v 1.3 2006/04/11 09:04:15 pratheepa Exp $
 */
package com.integrosys.cms.ui.collateral.addtionalDocumentFacilityDetails;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.contact.OBAddress;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.mapper.MapperUtil;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.IAddtionalDocumentFacilityDetails;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.OBAddtionalDocumentFacilityDetails;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.InsurerNameList;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/04/11 09:04:15 $ Tag: $Name: $
 */
//public class AddtionalDocumentFacilityDetailsMapper{
public class AddtionalDocumentFacilityDetailsMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		DefaultLogger.debug(this, "Inside Insurance Policy mapper formtoobj");
		AddtionalDocumentFacilityDetailsForm aForm = (AddtionalDocumentFacilityDetailsForm) cForm;

		ICollateral iCol = (ICollateral) (((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		IAddtionalDocumentFacilityDetails[] insPolicyArr = iCol.getAdditonalDocFacDetails();
		int index = Integer.parseInt((String) inputs.get("indexID"));

		IAddtionalDocumentFacilityDetails obToChange = null;
		if (index == -1) {
			obToChange = new OBAddtionalDocumentFacilityDetails();
		}
		else {
			try {
				obToChange = (IAddtionalDocumentFacilityDetails) AccessorUtil.deepClone(insPolicyArr[index]);
			}
			catch (Exception e) {
				DefaultLogger.debug(this, e.getMessage());
			}
		}

		Date stageDate = null;
		try {
//			obToChange.setPolicyNo(aForm.getInsPolicyNum().trim());
//			obToChange.setInsurerName(aForm.getInsurerName());
//			obToChange
//					.setInsuranceCompanyName(InsurerNameList.getInstance().getInsurerNameItem(aForm.getInsurerName()));
//			obToChange.setInsuranceType(aForm.getInsuranceType());
			
			
			if (!isEmptyOrNull(aForm.getDocFacilityCategory())) {
				obToChange.setDocFacilityCategory(aForm.getDocFacilityCategory());
			}
			else {
				obToChange.setDocFacilityCategory(null);
			}
			
			if (!isEmptyOrNull(aForm.getDocFacilityType())) {
				obToChange.setDocFacilityType(aForm.getDocFacilityType());
			}
			else {
				obToChange.setDocFacilityType(null);
			}
			
			if (!isEmptyOrNull(aForm.getDocFacilityAmount())) {
				
				String num = aForm.getDocFacilityAmount();
				num = UIUtil.removeComma(num);
				num = num.replace(".00", "");
				obToChange.setDocFacilityAmount(num);
				
//				obToChange.setDocFacilityAmount(aForm.getDocFacilityAmount());
			}
			else {
				obToChange.setDocFacilityAmount(null);
			}
			
			if (!isEmptyOrNull(aForm.getDocFacilityTotalAmount())) {
				obToChange.setDocFacilityTotalAmount(aForm.getDocFacilityTotalAmount());
			}
			else {
				obToChange.setDocFacilityTotalAmount(null);
			}
			
			
			
			if (!isEmptyOrNull(aForm.getDocumentNo())) {
				obToChange.setDocumentNo(aForm.getDocumentNo());
			}
			else {
				obToChange.setDocumentNo(null);
			}
			if (!isEmptyOrNull(aForm.getLmtProfileId())) {
				obToChange.setLmtProfileId(Long.valueOf(aForm.getLmtProfileId()));
			}
			else {
				obToChange.setLmtProfileId(null);
			}

			/*if (!isEmptyOrNull(aForm.getExpiryDateIns())) {
				stageDate = UIUtil.compareDate(locale, obToChange.getExpiryDate(), aForm.getExpiryDateIns());
				obToChange.setExpiryDate(stageDate);
			}
			else {
				obToChange.setExpiryDate(null);
			}*/

//			obToChange.setCurrencyCode(aForm.getInsPolicyCurrency());
//			obToChange.setConversionCurrency(aForm.getConversionCurrency());

//			if (isEmptyOrNull(aForm.getInsurableAmt())) {
//				obToChange.setInsurableAmount(null);
//			}
//			else {
//				Amount amt = new Amount(UIUtil.mapStringToBigDecimal( aForm.getInsurableAmt()),
//						new CurrencyCode(iCol.getCurrencyCode()));
//				obToChange.setInsurableAmount(amt);
//			}

//			if (isEmptyOrNull(aForm.getInsuredAmt())) {
//				obToChange.setInsuredAmount(null);
//			}
//			else {
//				Amount amt = new Amount(UIUtil.mapStringToBigDecimal( aForm.getInsuredAmt()),
//						new CurrencyCode(iCol.getCurrencyCode()));
//				obToChange.setInsuredAmount(amt);
//			}
//
//			if (!isEmptyOrNull(aForm.getEffectiveDateIns())) {
//				stageDate = UIUtil.compareDate(locale, obToChange.getEffectiveDate(), aForm.getEffectiveDateIns());
//				obToChange.setEffectiveDate(stageDate);
//			}
//			else {
//				obToChange.setEffectiveDate(null);
//			}

			if (!isEmptyOrNull(aForm.getReceivedDate())) {
				obToChange.setReceivedDate(DateUtil.convertDate(aForm.getReceivedDate()));
			}
			else {
				obToChange.setReceivedDate(null);
			}
		
			
//			if (!isEmptyOrNull(aForm.getInsuredAddress())) {
//				OBAddress address = (OBAddress) obToChange.getAddress();
//				if (address == null) {
//					address = new OBAddress();
//				}
//				address.setAddress(aForm.getInsuredAddress());
//				obToChange.setAddress(address);
//			}
//
//			obToChange.setInsuredAgainst(aForm.getInsuredAgainst());

			/*if (!isEmptyOrNull(aForm.getCoverNoteNumber())) {
				obToChange.setCoverNoteNumber(aForm.getCoverNoteNumber());
			}
			else {
				obToChange.setCoverNoteNumber(null);
			}*/

//			if (!isEmptyOrNull(aForm.getInsIssueDate())) {
//				obToChange.setInsIssueDate(DateUtil.convertDate(aForm.getInsIssueDate()));
//			}
//			else {
//				obToChange.setInsIssueDate(null);
//			}
//
//			if (!isEmptyOrNull(aForm.getInsuranceExchangeRate())) {
//				obToChange.setInsuranceExchangeRate(Double.valueOf(aForm.getInsuranceExchangeRate()));
//			}
//			else {
//				obToChange.setInsuranceExchangeRate(null);
//			}

			/*if (!isEmptyOrNull(aForm.getDebitingACNo())) {
				obToChange.setDebitingACNo(aForm.getDebitingACNo());
			}
			else {
				obToChange.setDebitingACNo(null);
			}*/

//			if (!isEmptyOrNull(aForm.getAcType())) {
//				obToChange.setAcType(aForm.getAcType());
//			}
//			else {
//				obToChange.setAcType(null);
//			}

			/*if (!isEmptyOrNull(aForm.getNonSchemeScheme())) {
				obToChange.setNonSchemeScheme(aForm.getNonSchemeScheme());
			}
			else {
				obToChange.setNonSchemeScheme(null);
			}*/

//			obToChange.setInsurancePremium(UIUtil.mapStringToAmount(locale, obToChange.getCurrencyCode(), aForm
//					.getInsurancePremium(), null));
//
//			if (!isEmptyOrNull(aForm.getAutoDebit())) {
//				if (aForm.getAutoDebit().equals("Yes")) {
//					obToChange.setAutoDebit("Y");
//				}
//				else {
//					obToChange.setAutoDebit("N");
//				}
//			}
//			else {
//				obToChange.setAutoDebit(null);
//			}

			/*obToChange.setTakafulCommission(UIUtil.mapStringToAmount(locale, obToChange.getCurrencyCode(), aForm
					.getTakafulCommission(), null));

			obToChange.setNewAmtInsured(UIUtil.mapStringToAmount(locale, obToChange.getCurrencyCode(), aForm
					.getNewAmountInsured(), null));

			if (!isEmptyOrNull(aForm.getEndorsementDate())) {
				obToChange.setEndorsementDate(DateUtil.convertDate(aForm.getEndorsementDate()));
			}
			else {
				obToChange.setEndorsementDate(null);
			}

			if (!isEmptyOrNull(aForm.getBuildingOccpation())) {
				obToChange.setBuildingOccupation(aForm.getBuildingOccpation());
			}
			else {
				obToChange.setBuildingOccupation(null);
			}

			if (!isEmptyOrNull(aForm.getNatureOfWork())) {
				obToChange.setNatureOfWork(aForm.getNatureOfWork());
			}
			else {
				obToChange.setNatureOfWork(null);
			}

			if (!isEmptyOrNull(aForm.getTypeOfBuilding())) {
				obToChange.setBuildingType(aForm.getTypeOfBuilding());
			}
			else {
				obToChange.setBuildingType(null);
			}

			if (!isEmptyOrNull(aForm.getNumberOfStorey())) {
				obToChange.setNumberOfStorey(Integer.valueOf(aForm.getNumberOfStorey()));
			}
			else {
				obToChange.setNumberOfStorey(null);
			}

			if (!isEmptyOrNull(aForm.getWall())) {
				obToChange.setWall(aForm.getWall());
			}
			else {
				obToChange.setWall(null);
			}

			if (!isEmptyOrNull(aForm.getExtensionWalls())) {
				obToChange.setExtensionWalls(aForm.getExtensionWalls());
			}
			else {
				obToChange.setExtensionWalls(null);
			}

			if (!isEmptyOrNull(aForm.getRoof())) {
				obToChange.setRoof(aForm.getRoof());
			}
			else {
				obToChange.setRoof(null);
			}

			if (!isEmptyOrNull(aForm.getExtensionRoof())) {
				obToChange.setExtensionRoof(aForm.getExtensionRoof());
			}
			else {
				obToChange.setExtensionRoof(null);
			}

			if (!isEmptyOrNull(aForm.getEndorsementCode())) {
				obToChange.setEndorsementCode(aForm.getEndorsementCode());
			}
			else {
				obToChange.setEndorsementCode(null);
			}

			if (!isEmptyOrNull(aForm.getPolicyCustodian())) {
				obToChange.setPolicyCustodian(aForm.getPolicyCustodian());
			}
			else {
				obToChange.setPolicyCustodian(null);
			}*/

//			if (!isEmptyOrNull(aForm.getInsuranceClaimDate())) {
//				obToChange.setInsuranceClaimDate(DateUtil.convertDate(aForm.getInsuranceClaimDate()));
//			}
//			else {
//				obToChange.setInsuranceClaimDate(null);
//			}

			/*if (!isEmptyOrNull(aForm.getTypeOfFloor())) {
				obToChange.setTypeOfFloor(aForm.getTypeOfFloor());
			}
			else {
				obToChange.setTypeOfFloor(null);
			}

			if (!isEmptyOrNull(aForm.getTypeOfPerils1())) {
				obToChange.setTypeOfPerils1(aForm.getTypeOfPerils1());
			}
			else {
				obToChange.setTypeOfPerils1(null);
			}

			if (!isEmptyOrNull(aForm.getTypeOfPerils2())) {
				obToChange.setTypeOfPerils2(aForm.getTypeOfPerils2());
			}
			else {
				obToChange.setTypeOfPerils2(null);
			}

			if (!isEmptyOrNull(aForm.getTypeOfPerils3())) {
				obToChange.setTypeOfPerils3(aForm.getTypeOfPerils3());
			}
			else {
				obToChange.setTypeOfPerils3(null);
			}

			if (!isEmptyOrNull(aForm.getTypeOfPerils4())) {
				obToChange.setTypeOfPerils4(aForm.getTypeOfPerils4());
			}
			else {
				obToChange.setTypeOfPerils4(null);
			}

			if (!isEmptyOrNull(aForm.getTypeOfPerils5())) {
				obToChange.setTypeOfPerils5(aForm.getTypeOfPerils5());
			}
			else {
				obToChange.setTypeOfPerils5(null);
			}
*/
			if (!isEmptyOrNull(aForm.getRemark1())) {
				obToChange.setRemark1(aForm.getRemark1());
			}
			else {
				obToChange.setRemark1(null);
			}

			/*if (!isEmptyOrNull(aForm.getRemark2())) {
				obToChange.setRemark2(aForm.getRemark2());
			}
			else {
				obToChange.setRemark2(null);
			}

			if (!isEmptyOrNull(aForm.getRemark3())) {
				obToChange.setRemark3(aForm.getRemark3());
			}
			else {
				obToChange.setRemark3(null);
			}

			if (!isEmptyOrNull(aForm.getBankCustomerArrange())) {
				obToChange.setBankCustomerArrange(aForm.getBankCustomerArrange());
			}
			else {
				obToChange.setBankCustomerArrange(null);
			}

			if (!isEmptyOrNull(aForm.getNettPermByBorrower())) {
				obToChange
						.setNettPermByBorrower(new BigDecimal(StringUtils.remove(aForm.getNettPermByBorrower(), ',')));
			}
			else {
				obToChange.setNettPermByBorrower(null);
			}

			if (!isEmptyOrNull(aForm.getNettPermToInsCo())) {
				obToChange.setNettPermToInsCo(new BigDecimal(StringUtils.remove(aForm.getNettPermToInsCo(), ',')));
			}
			else {
				obToChange.setNettPermToInsCo(null);
			}

			if (!isEmptyOrNull(aForm.getCommissionEarned())) {
				obToChange.setCommissionEarned(new BigDecimal(StringUtils.remove(aForm.getCommissionEarned(), ',')));
			}
			else {
				obToChange.setCommissionEarned(null);
			}

			if (!isEmptyOrNull(aForm.getStampDuty())) {
				obToChange.setStampDuty(new BigDecimal(StringUtils.remove(aForm.getStampDuty(), ',')));
			}
			else {
				obToChange.setStampDuty(null);
			}

			if (!isEmptyOrNull(aForm.getGrossPremium())) {
				obToChange.setGrossPremium(new BigDecimal(StringUtils.remove(aForm.getGrossPremium(), ',')));
			}
			else {
				obToChange.setGrossPremium(null);
			}*/

//			if (!isEmptyOrNull(aForm.getInsuredAgainst())) {
//				obToChange.setInsuredAgainst(aForm.getInsuredAgainst());
//			}
//			else {
//				obToChange.setInsuredAgainst(null);
//			}

			/*if (!isEmptyOrNull(aForm.getRebateAmount())) {
				obToChange.setRebateAmount(new BigDecimal(StringUtils.remove(aForm.getRebateAmount(), ',')));
			}
			else {
				obToChange.setRebateAmount(null);
			}

			if (!isEmptyOrNull(aForm.getServiceTaxAmount())) {
				obToChange.setServiceTaxAmount(new BigDecimal(StringUtils.remove(aForm.getServiceTaxAmount(), ',')));
			}
			else {
				obToChange.setServiceTaxAmount(null);
			}

			if (!isEmptyOrNull(aForm.getServiceTaxPercentage())) {
				obToChange.setServiceTaxPercentage(new BigDecimal(StringUtils.remove(aForm.getServiceTaxPercentage(),
						',')));
			}
			else {
				obToChange.setServiceTaxPercentage(null);
			}*/


			 //Uma Khot::Insurance Deferral maintainance
			
			if(null!=aForm.getAddFacDocStatus()){
				obToChange.setAddFacDocStatus(aForm.getAddFacDocStatus());
			}
			
			/*if(aForm.getOriginalTargetDate()!=null && (!aForm.getOriginalTargetDate().equals("")))
            {
			obToChange.setOriginalTargetDate(DateUtil.convertDate(aForm.getOriginalTargetDate()));
            }
			if(aForm.getNextDueDate()!=null && (!aForm.getNextDueDate().equals("")))
            {
			obToChange.setNextDueDate(DateUtil.convertDate(aForm.getNextDueDate()));
            }
			if(aForm.getDateDeferred()!=null && (!aForm.getDateDeferred().equals("")))
            {
			obToChange.setDateDeferred(DateUtil.convertDate(aForm.getDateDeferred()));
            }
			if(aForm.getCreditApprover()!=null && (!aForm.getCreditApprover().equals("")))
            {
			obToChange.setCreditApprover(aForm.getCreditApprover());
            }
			if(aForm.getWaivedDate()!=null && (!aForm.getWaivedDate().equals("")))
            {
			obToChange.setWaivedDate(DateUtil.convertDate(aForm.getWaivedDate()));
            }*/
			
			DateFormat df1 = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
			obToChange.setLastApproveBy(aForm.getLastApproveBy());
			if(aForm.getLastApproveOn()!=null && (!aForm.getLastApproveOn().equals(""))){
				obToChange.setLastApproveOn(df1.parse(aForm.getLastApproveOn()));
			}
			obToChange.setLastUpdatedBy(aForm.getLastUpdatedBy());
			if(aForm.getLastUpdatedOn()!=null && (!aForm.getLastUpdatedOn().equals(""))){
				obToChange.setLastUpdatedOn(df1.parse(aForm.getLastUpdatedOn()));
			}
			
			DefaultLogger.debug(this, "Finish facDocDetailObj policy mapping");

		}
		catch (Exception e) {
			throw new MapperException("failed to map facDocDetailObj policy form object to value object", e);
		}

		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		AddtionalDocumentFacilityDetailsForm aForm = (AddtionalDocumentFacilityDetailsForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		HashMap facDocDetailMap = (HashMap) obj;
		IAddtionalDocumentFacilityDetails facDocDetailObj = (IAddtionalDocumentFacilityDetails) facDocDetailMap.get("obj");
		ICollateral iCol = (ICollateral) facDocDetailMap.get("col");
		String conversionCcy = null;
		/*if (facDocDetailObj != null && facDocDetailObj.getConversionCurrency() != null
				&& !facDocDetailObj.getConversionCurrency().equals("")) {
			conversionCcy = facDocDetailObj.getConversionCurrency();
		}
		else if (facDocDetailObj != null && facDocDetailObj.getCurrencyCode() != null && !facDocDetailObj.getCurrencyCode().equals("")) {
			conversionCcy = facDocDetailObj.getCurrencyCode();
		}
		else {
			conversionCcy = iCol.getCurrencyCode();
		}*/
//		CurrencyCode conversionCcyObj = new CurrencyCode(conversionCcy);
//		aForm.setConversionCurrency(conversionCcy);
		Amount amt = null;
		double value = 0;
		// ForexHelper fr = new ForexHelper();

		if (facDocDetailObj != null) { // Edit Insurance
//			aForm.setInsPolicyNum(facDocDetailObj.getPolicyNo());
//			aForm.setInsurerName(facDocDetailObj.getInsurerName());
//			aForm.setInsuranceType(facDocDetailObj.getInsuranceType());
			
			
			
			
			aForm.setDocFacilityCategory(facDocDetailObj.getDocFacilityCategory());
			
			if(facDocDetailObj.getDocFacilityAmount() != null && !"".equals(facDocDetailObj.getDocFacilityAmount())) {
				
				String num = UIUtil.formatWithCommaAndDecimalNew(facDocDetailObj.getDocFacilityAmount());
				aForm.setDocFacilityAmount(num);
			}
			
			aForm.setDocFacilityType(facDocDetailObj.getDocFacilityType());
//			aForm.setDocFacilityAmount(facDocDetailObj.getDocFacilityAmount());
			aForm.setDocFacilityTotalAmount(facDocDetailObj.getDocFacilityTotalAmount());
			
//			aForm.setExpiryDateIns(DateUtil.formatDate(locale, facDocDetailObj.getExpiryDate()));
			if(facDocDetailObj.getReceivedDate()!= null && !facDocDetailObj.getReceivedDate().equals("")){
				aForm.setReceivedDate(DateUtil.formatDate(locale, facDocDetailObj.getReceivedDate()));
			}	
//			aForm.setInsPolicyCurrency(facDocDetailObj.getCurrencyCode());

			if ((facDocDetailObj.getDocumentNo() != null) && (facDocDetailObj.getDocumentNo().trim().length() > 0)) {
				DefaultLogger.debug(this + " AddtionalDocumentFacilityDetailsMapper", "Doc num: :" + facDocDetailObj.getDocumentNo());
				aForm.setDocumentNo(facDocDetailObj.getDocumentNo());
			}

			if (facDocDetailObj.getLmtProfileId() != null) {
				aForm.setLmtProfileId(String.valueOf(facDocDetailObj.getLmtProfileId()));
			}

//			amt = facDocDetailObj.getInsurableAmount();
//			if ((amt != null) && (amt.getCurrencyCode() != null)) {
//				try {
//					aForm.setInsurableAmt(CurrencyManager.convertToString(locale, amt));
//				}
//				catch (Exception e) {
//					DefaultLogger.error(this, "exception thrown...");
//					throw new MapperException(e.getMessage());
//				}
//			}

//			amt = facDocDetailObj.getInsuredAmount();
//			if ((amt != null) && (amt.getCurrencyCode() != null)) {
//				try {
//					//aForm.setInsuredAmt(amt.getAmountAsBigDecimal().toString());
//					
//					//Phase 3 CR:comma separated
//					aForm.setInsuredAmt(CurrencyManager.convertToString(locale,amt));
//				}
//				catch (Exception e) {
//					DefaultLogger.error(this, "exception thrown...");
//					throw new MapperException(e.getMessage());
//				}
//				try {
//					// Andy Wong, 22 April 2009: facDocDetailObj exchange rate default
//					// by Sibs during Stp
//					// return same amt when both currency code same or
//					// conversion null
//					if (StringUtils.isBlank(facDocDetailObj.getConversionCurrency())
//							|| StringUtils.equals(facDocDetailObj.getCurrencyCode(), facDocDetailObj.getConversionCurrency())) {
//						value = facDocDetailObj.getInsuredAmount().getAmountAsBigDecimal().setScale(2,
//								BigDecimal.ROUND_HALF_UP).doubleValue();
//					}
//					else if (facDocDetailObj.getInsuranceExchangeRate() != null && facDocDetailObj.getInsuredAmount() != null) {
//						value = (new BigDecimal(facDocDetailObj.getInsuranceExchangeRate().doubleValue()).multiply(amt
//								.getAmountAsBigDecimal()).setScale(2, BigDecimal.ROUND_HALF_UP)).doubleValue();
//					}
//
//					aForm.setInsuredAmtConvert(CurrencyManager.convertToString(locale, new Amount(value,
//							conversionCcyObj)));
//				}
//				catch (Exception e) {
//					DefaultLogger.error(this, "Caught Forex Exception!", e);
//					aForm.setInsuredAmtConvert("Forex Error");
//				}
//
//				if (value >= 0) {
//					aForm.setInsuranceExchangeRate(MapperUtil.mapDoubleToString((value / amt.getAmountAsDouble()), 2,
//							locale));
//				}
//				else {
//					aForm.setInsuranceExchangeRate("");
//				}
//			}

//			amt = facDocDetailObj.getInsurancePremium();
//			if ((amt != null) && (amt.getCurrencyCode() != null)) {
//				try {
//					aForm.setInsurancePremium(CurrencyManager.convertToString(locale, amt));
//				}
//				catch (Exception e) {
//					DefaultLogger.error(this, "exception thrown...");
//					throw new MapperException(e.getMessage());
//				}
//			}
//
//			aForm.setEffectiveDateIns(DateUtil.formatDate(locale, facDocDetailObj.getEffectiveDate()));
//
//			if (facDocDetailObj.getAddress() != null) {
//				aForm.setInsuredAddress(facDocDetailObj.getAddress().getAddress());
//			}
//			else {
//				aForm.setInsuredAddress(null);
//			}

			// --added by thurein--//

//			aForm.setCoverNoteNumber(facDocDetailObj.getCoverNoteNumber());

//			if (facDocDetailObj.getInsIssueDate() != null) {
//				aForm.setInsIssueDate(DateUtil.formatDate(locale, facDocDetailObj.getInsIssueDate()));
//			}
//
//			if (facDocDetailObj.getInsuranceCompanyName() != null) {
//				aForm.setInsuranceCompanyName(facDocDetailObj.getInsuranceCompanyName());
//			}

//			if (facDocDetailObj.getDebitingACNo() != null) {
//				aForm.setDebitingACNo(facDocDetailObj.getDebitingACNo());
//			}

//			if (facDocDetailObj.getAcType() != null) {
//				aForm.setAcType(facDocDetailObj.getAcType());
//			}

//			if (facDocDetailObj.getNonSchemeScheme() != null) {
//				aForm.setNonSchemeScheme(facDocDetailObj.getNonSchemeScheme());
//			}

//			if (facDocDetailObj.getAutoDebit() != null) {
//				if (facDocDetailObj.getAutoDebit().equals("Y")) {
//					aForm.setAutoDebit("Yes");
//				}
//				else {
//					aForm.setAutoDebit("No");
//				}
//			}

//			amt = facDocDetailObj.getTakafulCommission();
			/*if ((amt != null) && (amt.getCurrencyCode() != null)) {
				try {
					aForm.setTakafulCommission(CurrencyManager.convertToString(locale, amt));
				}
				catch (Exception e) {
					DefaultLogger.error(this, "exception thrown...");
					throw new MapperException(e.getMessage());
				}
			}

			if (facDocDetailObj.getEndorsementDate() != null) {
				aForm.setEndorsementDate(DateUtil.convertToDisplayDate(facDocDetailObj.getEndorsementDate()));
			}
			else {
				aForm.setEndorsementDate(null);
			}
			if (facDocDetailObj.getBuildingOccupation() != null) {
				aForm.setBuildingOccpation(facDocDetailObj.getBuildingOccupation());
			}
			else {
				aForm.setBuildingOccpation(null);
			}

			if (facDocDetailObj.getNatureOfWork() != null) {
				aForm.setNatureOfWork(facDocDetailObj.getNatureOfWork());
			}

			if (facDocDetailObj.getBuildingType() != null) {
				aForm.setTypeOfBuilding(facDocDetailObj.getBuildingType());
			}
			else {
				aForm.setTypeOfBuilding(null);
			}

			if (facDocDetailObj.getNumberOfStorey() != null) {
				aForm.setNumberOfStorey(Integer.toString(facDocDetailObj.getNumberOfStorey().intValue()));
			}

			if (facDocDetailObj.getWall() != null) {
				aForm.setWall(facDocDetailObj.getWall());
			}
			else {
				aForm.setWall(null);
			}

			if (facDocDetailObj.getExtensionWalls() != null) {
				aForm.setExtensionWalls(facDocDetailObj.getExtensionWalls());
			}
			else {
				aForm.setExtensionWalls(null);
			}

			if (facDocDetailObj.getRoof() != null) {
				aForm.setRoof(facDocDetailObj.getRoof());
			}
			else {
				aForm.setRoof(null);
			}

			if (facDocDetailObj.getExtensionRoof() != null) {
				aForm.setExtensionRoof(facDocDetailObj.getExtensionRoof());
			}
			else {
				aForm.setExtensionRoof(null);
			}

			if (facDocDetailObj.getEndorsementCode() != null) {
				aForm.setEndorsementCode(facDocDetailObj.getEndorsementCode());
			}

			if (facDocDetailObj.getPolicyCustodian() != null) {
				aForm.setPolicyCustodian(facDocDetailObj.getPolicyCustodian());
			}
			else {
				aForm.setPolicyCustodian(null);
			}*/

//			if (facDocDetailObj.getInsuranceClaimDate() != null) {
//				aForm.setInsuranceClaimDate(DateUtil.convertToDisplayDate(facDocDetailObj.getInsuranceClaimDate()));
//			}

			/*if (facDocDetailObj.getTypeOfFloor() != null) {
				aForm.setTypeOfFloor(facDocDetailObj.getTypeOfFloor());
			}
			else {
				aForm.setTypeOfFloor(null);
			}

			if (facDocDetailObj.getTypeOfPerils1() != null) {
				aForm.setTypeOfPerils1(facDocDetailObj.getTypeOfPerils1());
			}
			else {
				aForm.setTypeOfPerils1(null);
			}

			if (facDocDetailObj.getTypeOfPerils2() != null) {
				aForm.setTypeOfPerils2(facDocDetailObj.getTypeOfPerils2());
			}
			else {
				aForm.setTypeOfPerils2(null);
			}

			if (facDocDetailObj.getTypeOfPerils3() != null) {
				aForm.setTypeOfPerils3(facDocDetailObj.getTypeOfPerils3());
			}
			else {
				aForm.setTypeOfPerils3(null);
			}

			if (facDocDetailObj.getTypeOfPerils4() != null) {
				aForm.setTypeOfPerils4(facDocDetailObj.getTypeOfPerils4());
			}
			else {
				aForm.setTypeOfPerils4(null);
			}

			if (facDocDetailObj.getTypeOfPerils5() != null) {
				aForm.setTypeOfPerils5(facDocDetailObj.getTypeOfPerils5());
			}
			else {
				aForm.setTypeOfPerils5(null);
			}*/

			if (facDocDetailObj.getRemark1() != null) {
				aForm.setRemark1(facDocDetailObj.getRemark1());
			}

			/*if (facDocDetailObj.getRemark2() != null) {
				aForm.setRemark2(facDocDetailObj.getRemark2());
			}

			if (facDocDetailObj.getRemark3() != null) {
				aForm.setRemark3(facDocDetailObj.getRemark3());
			}

			if (facDocDetailObj.getBankCustomerArrange() != null) {
				aForm.setBankCustomerArrange(facDocDetailObj.getBankCustomerArrange());
			}

			aForm.setNettPermByBorrower(UIUtil.formatBigDecimalToStr(facDocDetailObj.getNettPermByBorrower()));

			aForm.setNettPermToInsCo(UIUtil.formatBigDecimalToStr(facDocDetailObj.getNettPermToInsCo()));

			aForm.setCommissionEarned(UIUtil.formatBigDecimalToStr(facDocDetailObj.getCommissionEarned()));

			aForm.setStampDuty(UIUtil.formatBigDecimalToStr(facDocDetailObj.getStampDuty()));

			if (facDocDetailObj.getNewAmtInsured() != null) {
				aForm.setNewAmountInsured(UIUtil.formatBigDecimalToStr(facDocDetailObj.getNewAmtInsured()
						.getAmountAsBigDecimal()));
			}

			aForm.setGrossPremium(UIUtil.formatBigDecimalToStr(facDocDetailObj.getGrossPremium()));

//			aForm.setInsuredAgainst(facDocDetailObj.getInsuredAgainst());

			aForm.setRebateAmount(UIUtil.formatBigDecimalToStr(facDocDetailObj.getRebateAmount()));
			aForm.setServiceTaxAmount(UIUtil.formatBigDecimalToStr(facDocDetailObj.getServiceTaxAmount()));
			aForm.setServiceTaxPercentage(UIUtil.formatBigDecimalToStr(facDocDetailObj.getServiceTaxPercentage()));
			*/
			
				if(null!=facDocDetailObj.getAddFacDocStatus()){
					aForm.setAddFacDocStatus(facDocDetailObj.getAddFacDocStatus());
				}
				/*if(facDocDetailObj.getOriginalTargetDate()!=null  && !facDocDetailObj.getOriginalTargetDate().equals("")){
					String date=DateUtil.formatDate(locale,facDocDetailObj.getOriginalTargetDate());
					aForm.setOriginalTargetDate(date);
					}
				if(facDocDetailObj.getNextDueDate()!=null  && !facDocDetailObj.getNextDueDate().equals("")){
					String date=DateUtil.formatDate(locale,facDocDetailObj.getNextDueDate());
					aForm.setNextDueDate(date);
					}
				if(facDocDetailObj.getDateDeferred()!=null  && !facDocDetailObj.getDateDeferred().equals("")){
					String date=DateUtil.formatDate(locale,facDocDetailObj.getDateDeferred());
					aForm.setDateDeferred(date);
					}
				if(facDocDetailObj.getWaivedDate()!=null  && !facDocDetailObj.getWaivedDate().equals("")){
					String date=DateUtil.formatDate(locale,facDocDetailObj.getWaivedDate());
					aForm.setWaivedDate(date);
					}
				if(facDocDetailObj.getCreditApprover()!=null && (!facDocDetailObj.getCreditApprover().equals("")))
	            {
					aForm.setCreditApprover(facDocDetailObj.getCreditApprover());
	            }*/
				
				aForm.setLastApproveBy(facDocDetailObj.getLastApproveBy());
				if(facDocDetailObj.getLastApproveOn()!=null){
					aForm.setLastApproveOn(facDocDetailObj.getLastApproveOn().toString());
				}
				aForm.setLastUpdatedBy(facDocDetailObj.getLastUpdatedBy());
				if(facDocDetailObj.getLastUpdatedOn()!=null){
					aForm.setLastUpdatedOn(facDocDetailObj.getLastUpdatedOn().toString());
				}
				
		}

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }, });
	}
}
