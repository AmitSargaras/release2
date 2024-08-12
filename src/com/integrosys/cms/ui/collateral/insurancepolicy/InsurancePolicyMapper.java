/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insurancepolicy/InsurancePolicyMapper.java,v 1.3 2006/04/11 09:04:15 pratheepa Exp $
 */
package com.integrosys.cms.ui.collateral.insurancepolicy;

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
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.OBInsurancePolicy;
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

public class InsurancePolicyMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		DefaultLogger.debug(this, "Inside Insurance Policy mapper formtoobj");
		InsurancePolicyForm aForm = (InsurancePolicyForm) cForm;

		ICollateral iCol = (ICollateral) (((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		IInsurancePolicy[] insPolicyArr = iCol.getInsurancePolicies();
		int index = Integer.parseInt((String) inputs.get("indexID"));

		IInsurancePolicy obToChange = null;
		if (index == -1) {
			obToChange = new OBInsurancePolicy();
		}
		else {
			try {
				obToChange = (IInsurancePolicy) AccessorUtil.deepClone(insPolicyArr[index]);
			}
			catch (Exception e) {
				DefaultLogger.debug(this, e.getMessage());
			}
		}

		Date stageDate = null;
		try {
			obToChange.setPolicyNo(aForm.getInsPolicyNum().trim());
			obToChange.setInsurerName(aForm.getInsurerName());
			obToChange
					.setInsuranceCompanyName(InsurerNameList.getInstance().getInsurerNameItem(aForm.getInsurerName()));
			obToChange.setInsuranceType(aForm.getInsuranceType());
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

			if (!isEmptyOrNull(aForm.getExpiryDateIns())) {
				stageDate = UIUtil.compareDate(locale, obToChange.getExpiryDate(), aForm.getExpiryDateIns());
				obToChange.setExpiryDate(stageDate);
			}
			else {
				obToChange.setExpiryDate(null);
			}

			obToChange.setCurrencyCode(aForm.getInsPolicyCurrency());
			obToChange.setConversionCurrency(aForm.getConversionCurrency());

			if (isEmptyOrNull(aForm.getInsurableAmt())) {
				obToChange.setInsurableAmount(null);
			}
			else {
				Amount amt = new Amount(UIUtil.mapStringToBigDecimal( aForm.getInsurableAmt()),
						new CurrencyCode(iCol.getCurrencyCode()));
				obToChange.setInsurableAmount(amt);
			}

			if (isEmptyOrNull(aForm.getInsuredAmt())) {
				obToChange.setInsuredAmount(null);
			}
			else {
				Amount amt = new Amount(UIUtil.mapStringToBigDecimal( aForm.getInsuredAmt()),
						new CurrencyCode(iCol.getCurrencyCode()));
				obToChange.setInsuredAmount(amt);
			}

			if (!isEmptyOrNull(aForm.getEffectiveDateIns())) {
				stageDate = UIUtil.compareDate(locale, obToChange.getEffectiveDate(), aForm.getEffectiveDateIns());
				obToChange.setEffectiveDate(stageDate);
			}
			else {
				obToChange.setEffectiveDate(null);
			}

			if (!isEmptyOrNull(aForm.getReceivedDate())) {
				obToChange.setReceivedDate(DateUtil.convertDate(aForm.getReceivedDate()));
			}
			else {
				obToChange.setReceivedDate(null);
			}
		
			
			if (!isEmptyOrNull(aForm.getInsuredAddress())) {
				OBAddress address = (OBAddress) obToChange.getAddress();
				if (address == null) {
					address = new OBAddress();
				}
				address.setAddress(aForm.getInsuredAddress());
				obToChange.setAddress(address);
			}

			obToChange.setInsuredAgainst(aForm.getInsuredAgainst());

			if (!isEmptyOrNull(aForm.getCoverNoteNumber())) {
				obToChange.setCoverNoteNumber(aForm.getCoverNoteNumber());
			}
			else {
				obToChange.setCoverNoteNumber(null);
			}

			if (!isEmptyOrNull(aForm.getInsIssueDate())) {
				obToChange.setInsIssueDate(DateUtil.convertDate(aForm.getInsIssueDate()));
			}
			else {
				obToChange.setInsIssueDate(null);
			}

			if (!isEmptyOrNull(aForm.getInsuranceExchangeRate())) {
				obToChange.setInsuranceExchangeRate(Double.valueOf(aForm.getInsuranceExchangeRate()));
			}
			else {
				obToChange.setInsuranceExchangeRate(null);
			}

			if (!isEmptyOrNull(aForm.getDebitingACNo())) {
				obToChange.setDebitingACNo(aForm.getDebitingACNo());
			}
			else {
				obToChange.setDebitingACNo(null);
			}

			if (!isEmptyOrNull(aForm.getAcType())) {
				obToChange.setAcType(aForm.getAcType());
			}
			else {
				obToChange.setAcType(null);
			}

			if (!isEmptyOrNull(aForm.getNonSchemeScheme())) {
				obToChange.setNonSchemeScheme(aForm.getNonSchemeScheme());
			}
			else {
				obToChange.setNonSchemeScheme(null);
			}

			obToChange.setInsurancePremium(UIUtil.mapStringToAmount(locale, obToChange.getCurrencyCode(), aForm
					.getInsurancePremium(), null));

			if (!isEmptyOrNull(aForm.getAutoDebit())) {
				if (aForm.getAutoDebit().equals("Yes")) {
					obToChange.setAutoDebit("Y");
				}
				else {
					obToChange.setAutoDebit("N");
				}
			}
			else {
				obToChange.setAutoDebit(null);
			}

			obToChange.setTakafulCommission(UIUtil.mapStringToAmount(locale, obToChange.getCurrencyCode(), aForm
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
			}

			if (!isEmptyOrNull(aForm.getInsuranceClaimDate())) {
				obToChange.setInsuranceClaimDate(DateUtil.convertDate(aForm.getInsuranceClaimDate()));
			}
			else {
				obToChange.setInsuranceClaimDate(null);
			}

			if (!isEmptyOrNull(aForm.getTypeOfFloor())) {
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

			if (!isEmptyOrNull(aForm.getRemark1())) {
				obToChange.setRemark1(aForm.getRemark1());
			}
			else {
				obToChange.setRemark1(null);
			}

			if (!isEmptyOrNull(aForm.getRemark2())) {
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
			}

			if (!isEmptyOrNull(aForm.getInsuredAgainst())) {
				obToChange.setInsuredAgainst(aForm.getInsuredAgainst());
			}
			else {
				obToChange.setInsuredAgainst(null);
			}

			if (!isEmptyOrNull(aForm.getRebateAmount())) {
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
			}


			 //Uma Khot::Insurance Deferral maintainance
			
			if(null!=aForm.getInsuranceStatus()){
				obToChange.setInsuranceStatus(aForm.getInsuranceStatus());
			}
			
			if(aForm.getOriginalTargetDate()!=null && (!aForm.getOriginalTargetDate().equals("")))
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
            }
			
			DateFormat df1 = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
			obToChange.setLastApproveBy(aForm.getLastApproveBy());
			if(aForm.getLastApproveOn()!=null && (!aForm.getLastApproveOn().equals(""))){
				obToChange.setLastApproveOn(df1.parse(aForm.getLastApproveOn()));
			}
			obToChange.setLastUpdatedBy(aForm.getLastUpdatedBy());
			if(aForm.getLastUpdatedOn()!=null && (!aForm.getLastUpdatedOn().equals(""))){
				obToChange.setLastUpdatedOn(df1.parse(aForm.getLastUpdatedOn()));
			}
			
			DefaultLogger.debug(this, "Finish insurance policy mapping");

		}
		catch (Exception e) {
			throw new MapperException("failed to map insurance policy form object to value object", e);
		}

		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		InsurancePolicyForm aForm = (InsurancePolicyForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		HashMap insuranceMap = (HashMap) obj;
		IInsurancePolicy insurance = (IInsurancePolicy) insuranceMap.get("obj");
		ICollateral iCol = (ICollateral) insuranceMap.get("col");
		String conversionCcy = null;
		if (insurance != null && insurance.getConversionCurrency() != null
				&& !insurance.getConversionCurrency().equals("")) {
			conversionCcy = insurance.getConversionCurrency();
		}
		else if (insurance != null && insurance.getCurrencyCode() != null && !insurance.getCurrencyCode().equals("")) {
			conversionCcy = insurance.getCurrencyCode();
		}
		else {
			conversionCcy = iCol.getCurrencyCode();
		}
		CurrencyCode conversionCcyObj = new CurrencyCode(conversionCcy);
		aForm.setConversionCurrency(conversionCcy);
		Amount amt = null;
		double value = 0;
		// ForexHelper fr = new ForexHelper();

		if (insurance != null) { // Edit Insurance
			aForm.setInsPolicyNum(insurance.getPolicyNo());
			aForm.setInsurerName(insurance.getInsurerName());
			aForm.setInsuranceType(insurance.getInsuranceType());
			aForm.setExpiryDateIns(DateUtil.formatDate(locale, insurance.getExpiryDate()));
			if(insurance.getReceivedDate()!= null && !insurance.getReceivedDate().equals("")){
				aForm.setReceivedDate(DateUtil.formatDate(locale, insurance.getReceivedDate()));
			}	
			aForm.setInsPolicyCurrency(insurance.getCurrencyCode());

			if ((insurance.getDocumentNo() != null) && (insurance.getDocumentNo().trim().length() > 0)) {
				DefaultLogger.debug(this + " InsurancePolicyMapper", "Doc num: :" + insurance.getDocumentNo());
				aForm.setDocumentNo(insurance.getDocumentNo());
			}

			if (insurance.getLmtProfileId() != null) {
				aForm.setLmtProfileId(String.valueOf(insurance.getLmtProfileId()));
			}

			amt = insurance.getInsurableAmount();
			if ((amt != null) && (amt.getCurrencyCode() != null)) {
				try {
					aForm.setInsurableAmt(CurrencyManager.convertToString(locale, amt));
				}
				catch (Exception e) {
					DefaultLogger.error(this, "exception thrown...");
					throw new MapperException(e.getMessage());
				}
			}

			amt = insurance.getInsuredAmount();
			if ((amt != null) && (amt.getCurrencyCode() != null)) {
				try {
					//aForm.setInsuredAmt(amt.getAmountAsBigDecimal().toString());
					
					//Phase 3 CR:comma separated
					aForm.setInsuredAmt(CurrencyManager.convertToString(locale,amt));
				}
				catch (Exception e) {
					DefaultLogger.error(this, "exception thrown...");
					throw new MapperException(e.getMessage());
				}
				try {
					// Andy Wong, 22 April 2009: insurance exchange rate default
					// by Sibs during Stp
					// return same amt when both currency code same or
					// conversion null
					if (StringUtils.isBlank(insurance.getConversionCurrency())
							|| StringUtils.equals(insurance.getCurrencyCode(), insurance.getConversionCurrency())) {
						value = insurance.getInsuredAmount().getAmountAsBigDecimal().setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
					}
					else if (insurance.getInsuranceExchangeRate() != null && insurance.getInsuredAmount() != null) {
						value = (new BigDecimal(insurance.getInsuranceExchangeRate().doubleValue()).multiply(amt
								.getAmountAsBigDecimal()).setScale(2, BigDecimal.ROUND_HALF_UP)).doubleValue();
					}

					aForm.setInsuredAmtConvert(CurrencyManager.convertToString(locale, new Amount(value,
							conversionCcyObj)));
				}
				catch (Exception e) {
					DefaultLogger.error(this, "Caught Forex Exception!", e);
					aForm.setInsuredAmtConvert("Forex Error");
				}

				if (value >= 0) {
					aForm.setInsuranceExchangeRate(MapperUtil.mapDoubleToString((value / amt.getAmountAsDouble()), 2,
							locale));
				}
				else {
					aForm.setInsuranceExchangeRate("");
				}
			}

			amt = insurance.getInsurancePremium();
			if ((amt != null) && (amt.getCurrencyCode() != null)) {
				try {
					aForm.setInsurancePremium(CurrencyManager.convertToString(locale, amt));
				}
				catch (Exception e) {
					DefaultLogger.error(this, "exception thrown...");
					throw new MapperException(e.getMessage());
				}
			}

			aForm.setEffectiveDateIns(DateUtil.formatDate(locale, insurance.getEffectiveDate()));

			if (insurance.getAddress() != null) {
				aForm.setInsuredAddress(insurance.getAddress().getAddress());
			}
			else {
				aForm.setInsuredAddress(null);
			}

			// --added by thurein--//

			aForm.setCoverNoteNumber(insurance.getCoverNoteNumber());

			if (insurance.getInsIssueDate() != null) {
				aForm.setInsIssueDate(DateUtil.formatDate(locale, insurance.getInsIssueDate()));
			}

			if (insurance.getInsuranceCompanyName() != null) {
				aForm.setInsuranceCompanyName(insurance.getInsuranceCompanyName());
			}

			if (insurance.getDebitingACNo() != null) {
				aForm.setDebitingACNo(insurance.getDebitingACNo());
			}

			if (insurance.getAcType() != null) {
				aForm.setAcType(insurance.getAcType());
			}

			if (insurance.getNonSchemeScheme() != null) {
				aForm.setNonSchemeScheme(insurance.getNonSchemeScheme());
			}

			if (insurance.getAutoDebit() != null) {
				if (insurance.getAutoDebit().equals("Y")) {
					aForm.setAutoDebit("Yes");
				}
				else {
					aForm.setAutoDebit("No");
				}
			}

			amt = insurance.getTakafulCommission();
			if ((amt != null) && (amt.getCurrencyCode() != null)) {
				try {
					aForm.setTakafulCommission(CurrencyManager.convertToString(locale, amt));
				}
				catch (Exception e) {
					DefaultLogger.error(this, "exception thrown...");
					throw new MapperException(e.getMessage());
				}
			}

			if (insurance.getEndorsementDate() != null) {
				aForm.setEndorsementDate(DateUtil.convertToDisplayDate(insurance.getEndorsementDate()));
			}
			else {
				aForm.setEndorsementDate(null);
			}
			if (insurance.getBuildingOccupation() != null) {
				aForm.setBuildingOccpation(insurance.getBuildingOccupation());
			}
			else {
				aForm.setBuildingOccpation(null);
			}

			if (insurance.getNatureOfWork() != null) {
				aForm.setNatureOfWork(insurance.getNatureOfWork());
			}

			if (insurance.getBuildingType() != null) {
				aForm.setTypeOfBuilding(insurance.getBuildingType());
			}
			else {
				aForm.setTypeOfBuilding(null);
			}

			if (insurance.getNumberOfStorey() != null) {
				aForm.setNumberOfStorey(Integer.toString(insurance.getNumberOfStorey().intValue()));
			}

			if (insurance.getWall() != null) {
				aForm.setWall(insurance.getWall());
			}
			else {
				aForm.setWall(null);
			}

			if (insurance.getExtensionWalls() != null) {
				aForm.setExtensionWalls(insurance.getExtensionWalls());
			}
			else {
				aForm.setExtensionWalls(null);
			}

			if (insurance.getRoof() != null) {
				aForm.setRoof(insurance.getRoof());
			}
			else {
				aForm.setRoof(null);
			}

			if (insurance.getExtensionRoof() != null) {
				aForm.setExtensionRoof(insurance.getExtensionRoof());
			}
			else {
				aForm.setExtensionRoof(null);
			}

			if (insurance.getEndorsementCode() != null) {
				aForm.setEndorsementCode(insurance.getEndorsementCode());
			}

			if (insurance.getPolicyCustodian() != null) {
				aForm.setPolicyCustodian(insurance.getPolicyCustodian());
			}
			else {
				aForm.setPolicyCustodian(null);
			}

			if (insurance.getInsuranceClaimDate() != null) {
				aForm.setInsuranceClaimDate(DateUtil.convertToDisplayDate(insurance.getInsuranceClaimDate()));
			}

			if (insurance.getTypeOfFloor() != null) {
				aForm.setTypeOfFloor(insurance.getTypeOfFloor());
			}
			else {
				aForm.setTypeOfFloor(null);
			}

			if (insurance.getTypeOfPerils1() != null) {
				aForm.setTypeOfPerils1(insurance.getTypeOfPerils1());
			}
			else {
				aForm.setTypeOfPerils1(null);
			}

			if (insurance.getTypeOfPerils2() != null) {
				aForm.setTypeOfPerils2(insurance.getTypeOfPerils2());
			}
			else {
				aForm.setTypeOfPerils2(null);
			}

			if (insurance.getTypeOfPerils3() != null) {
				aForm.setTypeOfPerils3(insurance.getTypeOfPerils3());
			}
			else {
				aForm.setTypeOfPerils3(null);
			}

			if (insurance.getTypeOfPerils4() != null) {
				aForm.setTypeOfPerils4(insurance.getTypeOfPerils4());
			}
			else {
				aForm.setTypeOfPerils4(null);
			}

			if (insurance.getTypeOfPerils5() != null) {
				aForm.setTypeOfPerils5(insurance.getTypeOfPerils5());
			}
			else {
				aForm.setTypeOfPerils5(null);
			}

			if (insurance.getRemark1() != null) {
				aForm.setRemark1(insurance.getRemark1());
			}

			if (insurance.getRemark2() != null) {
				aForm.setRemark2(insurance.getRemark2());
			}

			if (insurance.getRemark3() != null) {
				aForm.setRemark3(insurance.getRemark3());
			}

			if (insurance.getBankCustomerArrange() != null) {
				aForm.setBankCustomerArrange(insurance.getBankCustomerArrange());
			}

			aForm.setNettPermByBorrower(UIUtil.formatBigDecimalToStr(insurance.getNettPermByBorrower()));

			aForm.setNettPermToInsCo(UIUtil.formatBigDecimalToStr(insurance.getNettPermToInsCo()));

			aForm.setCommissionEarned(UIUtil.formatBigDecimalToStr(insurance.getCommissionEarned()));

			aForm.setStampDuty(UIUtil.formatBigDecimalToStr(insurance.getStampDuty()));

			if (insurance.getNewAmtInsured() != null) {
				aForm.setNewAmountInsured(UIUtil.formatBigDecimalToStr(insurance.getNewAmtInsured()
						.getAmountAsBigDecimal()));
			}

			aForm.setGrossPremium(UIUtil.formatBigDecimalToStr(insurance.getGrossPremium()));

			aForm.setInsuredAgainst(insurance.getInsuredAgainst());

			aForm.setRebateAmount(UIUtil.formatBigDecimalToStr(insurance.getRebateAmount()));
			aForm.setServiceTaxAmount(UIUtil.formatBigDecimalToStr(insurance.getServiceTaxAmount()));
			aForm.setServiceTaxPercentage(UIUtil.formatBigDecimalToStr(insurance.getServiceTaxPercentage()));
			
			
				if(null!=insurance.getInsuranceStatus()){
					aForm.setInsuranceStatus(insurance.getInsuranceStatus());
				}
				if(insurance.getOriginalTargetDate()!=null  && !insurance.getOriginalTargetDate().equals("")){
					String date=DateUtil.formatDate(locale,insurance.getOriginalTargetDate());
					aForm.setOriginalTargetDate(date);
					}
				if(insurance.getNextDueDate()!=null  && !insurance.getNextDueDate().equals("")){
					String date=DateUtil.formatDate(locale,insurance.getNextDueDate());
					aForm.setNextDueDate(date);
					}
				if(insurance.getDateDeferred()!=null  && !insurance.getDateDeferred().equals("")){
					String date=DateUtil.formatDate(locale,insurance.getDateDeferred());
					aForm.setDateDeferred(date);
					}
				if(insurance.getWaivedDate()!=null  && !insurance.getWaivedDate().equals("")){
					String date=DateUtil.formatDate(locale,insurance.getWaivedDate());
					aForm.setWaivedDate(date);
					}
				if(insurance.getCreditApprover()!=null && (!insurance.getCreditApprover().equals("")))
	            {
					aForm.setCreditApprover(insurance.getCreditApprover());
	            }
				
				aForm.setLastApproveBy(insurance.getLastApproveBy());
				if(insurance.getLastApproveOn()!=null){
					aForm.setLastApproveOn(insurance.getLastApproveOn().toString());
				}
				aForm.setLastUpdatedBy(insurance.getLastUpdatedBy());
				if(insurance.getLastUpdatedOn()!=null){
					aForm.setLastUpdatedOn(insurance.getLastUpdatedOn().toString());
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
