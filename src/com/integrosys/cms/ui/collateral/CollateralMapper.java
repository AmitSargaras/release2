/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/CollateralMapper.java,v 1.101 2006/10/03 07:10:14 jzhan Exp $
 */

package com.integrosys.cms.ui.collateral;

import static com.integrosys.cms.app.common.util.MapperUtil.bigDecimalToString;
import static com.integrosys.cms.app.common.util.MapperUtil.doubleToString;
import static com.integrosys.cms.ui.collateral.CollateralConstant.RECEIVED;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.common.IMapper;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.IInstrument;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.ISecurityCoverage;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.bus.OBInstrument;
import com.integrosys.cms.app.collateral.bus.OBLimitCharge;
import com.integrosys.cms.app.collateral.bus.OBValuation;
import com.integrosys.cms.app.collateral.bus.valuation.support.ValuationUtil;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.util.MapperUtil;
import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.collateral.cash.cashfd.FixedDepositForm;
import com.integrosys.cms.ui.collateral.marketablesec.marksecmainlocal.MarksecMainLocalForm;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;


/**
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.101 $
 * @since $Date: 2006/10/03 07:10:14 $ Tag: $Name: $
 */

public abstract class CollateralMapper extends AbstractCommonMapper implements IMapper, ICommonEventConstant {

	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	public Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {

		CollateralForm colForm = (CollateralForm) cForm;
		ICollateral iCollateral = (ICollateral) obj;
        /***/
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) inputs.get("serviceColObj");
		if(!"".equals(colForm.getSpread())){
		float spread=Float.parseFloat(colForm.getSpread());
		iCollateral.setSpread(spread);
		}
		/***/
		String event = (String) inputs.get("event");
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		ICommonUser user = (ICommonUser) inputs.get(IGlobalConstant.USER);
		boolean isCurrencyChange = false;
		if ((user != null) && (user.getUserName() != null)) {
			itrxValue.setUserInfo(user.getUserName());
		}

		Date stageDate;
		if (!AbstractCommonMapper.isEmptyOrNull(iCollateral.getCurrencyCode())) {
			if (!iCollateral.getCurrencyCode().equals(colForm.getCollateralCurrency())) {
				isCurrencyChange = true;
			}
		}
		if (!ICMSConstant.COLTYPE_NOCOLLATERAL.equals(iCollateral.getCollateralSubType().getSubTypeCode())) {
			iCollateral.setCurrencyCode(colForm.getCollateralCurrency());
		}

		iCollateral.setIsExchangeCtrlObtained(colForm.getExchangeControl());
		if (!AbstractCommonMapper.isEmptyOrNull(colForm.getExchangeControlDate())) {
			stageDate = CollateralMapper.compareDate(locale, iCollateral.getExchangeCtrlDate(), colForm
					.getExchangeControlDate());
			iCollateral.setExchangeCtrlDate(stageDate);
		}
		else {
			iCollateral.setExchangeCtrlDate(null);
		}

		iCollateral.setCollateralStatus(colForm.getCollateralStatus());
		iCollateral.setCollateralLocation(colForm.getCollateralLoc());
		iCollateral.setCollateralCustodianType(colForm.getSecCustodianType());
		iCollateral.setSecurityOrganization(colForm.getSecurityOrganization());
		if (colForm.getSecCustodianType().equals(ICMSConstant.INTERNAL_COL_CUSTODIAN)) {
			iCollateral.setCollateralCustodian(colForm.getSecCustodianInt());
		}
		else if (colForm.getSecCustodianType().equals(ICMSConstant.EXTERNAL_COL_CUSTODIAN)) {
			iCollateral.setCollateralCustodian(colForm.getSecCustodianExt());
		}
		if(null!=colForm.getSecPriority() && !"".equals(colForm.getSecPriority()) ) // Prod issue: Security priority getting changed while doing  stock statement updation
			iCollateral.setSecPriority(colForm.getSecPriority());
		
		
		if (!colForm.getLe().equals(iCollateral.getIsLE())) {
			iCollateral.setIsLE(colForm.getLe());
		}
		iCollateral.setSCIReferenceNote(colForm.getCollateralName());
		iCollateral.setSourceSecuritySubType(colForm.getSourceSecuritySubType());

		try {
			iCollateral.setCollateralMaturityDate(UIUtil.mapFormString_OBDate(locale, iCollateral
					.getCollateralMaturityDate(), colForm.getCollateralMaturityDate()));
			iCollateral.setLEDate(UIUtil.mapFormString_OBDate(locale, iCollateral.getLEDate(), colForm.getLeDate()));

			if (colForm.getCgcPledged().equals(ICMSConstant.TRUE_VALUE)) {
				iCollateral.setIsCGCPledged(true);
			}
			else if (colForm.getCgcPledged().equals(ICMSConstant.FALSE_VALUE)) {
				iCollateral.setIsCGCPledged(false);
			}

			iCollateral.setPerfectionDate(UIUtil.mapFormString_OBDate(locale, iCollateral.getPerfectionDate(), colForm
					.getPerfectionDate()));

			if (colForm.getBorrowerDependency().equals(ICMSConstant.TRUE_VALUE)) {
				iCollateral.setIsBorrowerDependency(true);
			}
			else {
				iCollateral.setIsBorrowerDependency(false);
			}

			if (!AbstractCommonMapper.isEmptyOrNull(colForm.getNetRealisableSecValue())) {
				Amount rvAmt = new Amount(UIUtil.mapStringToBigDecimal(colForm.getNetRealisableSecValue()),
						new CurrencyCode(iCollateral.getSCICurrencyCode()));

				iCollateral.setNetRealisableAmount(rvAmt);
			}
			else {
				iCollateral.setNetRealisableAmount(null);
			}
			synInstrument2OB(iCollateral, colForm);

			if (!AbstractCommonMapper.isEmptyOrNull(colForm.getReservePrice())
					&& !AbstractCommonMapper.isEmptyOrNull(iCollateral.getCurrencyCode())) {
				iCollateral.setReservePrice(CurrencyManager.convertToAmount(locale, iCollateral.getCurrencyCode(),
						colForm.getReservePrice()));
			}

			if (!isEmptyOrNull(colForm.getReservePriceDate())) {
				iCollateral.setReservePriceDate(UIUtil.mapFormString_OBDate(locale, iCollateral.getReservePriceDate(),
						colForm.getReservePriceDate()));
			}
			// else {
			// iCollateral.setReservePrice(CurrencyManager.convertToAmount(locale,
			// iCollateral.getCurrencyCode(), String
			// .valueOf(ICMSConstant.LONG_INVALID_VALUE)));
			// }

			if (!ICMSConstant.COLTYPE_NOCOLLATERAL.equals(iCollateral.getCollateralSubType().getSubTypeCode())) {
				iCollateral = (OBCollateral) formToOBUpdateLimitCharge(iCollateral, colForm, locale);
			}

			// changes made in this function by Dattatray Thorat for Other Listed Local Valuation i.e for constant value COLTYPE_MAR_OTHERLISTED_LOCAL
			
			if (ICMSConstant.SECURITY_TYPE_PROPERTY.equals(iCollateral.getCollateralType().getTypeCode())
					|| ICMSConstant.SECURITY_TYPE_ASSET.equals(iCollateral.getCollateralType().getTypeCode())
					|| ICMSConstant.SECURITY_TYPE_OTHERS.equals(iCollateral.getCollateralType().getTypeCode())
					|| ICMSConstant.COLTYPE_DOC_LEASE_AGREEMENT.equals(iCollateral.getCollateralSubType()
							.getSubTypeCode())
					|| ICMSConstant.COLTYPE_DOC_DEED_ASSIGNMENT.equals(iCollateral.getCollateralSubType()
							.getSubTypeCode())
							
					//*************** Start of Lines added & commented by Dattatray Thorat *************
							
					||ICMSConstant.COLTYPE_MAR_OTHERLISTED_LOCAL.equals(iCollateral.getCollateralSubType()
							.getSubTypeCode())
							
							) {
				if (CollateralAction.EVENT_SUBMIT.equals(event)) {
					iCollateral = (OBCollateral) formToOBUpdateValuationIntoCMS(iCollateral, colForm, locale, inputs, isCurrencyChange);
				}
				else {
					iCollateral = (OBCollateral) formToOBSaveValuation(iCollateral, colForm, locale, inputs, isCurrencyChange);
				}
					
					//*************** End of Lines added & commented by Dattatray Thorat *************	
			}

			/*if (AbstractCommonMapper.isEmptyOrNull(colForm.getMargin())) {
				iCollateral.setMargin(ICMSConstant.DOUBLE_INVALID_VALUE);
			}
			else {
				iCollateral.setMargin(Double.parseDouble(colForm.getMargin()));
			}*/

			//Govind S:Uncommented line use for OMV for HDFC bank, 08/07/2011
			if (!AbstractCommonMapper.isEmptyOrNull(colForm.getAmountCMV())) {
				Amount amt = new Amount(UIUtil.mapStringToBigDecimal(colForm.getAmountCMV()),
						new CurrencyCode(iCollateral.getCurrencyCode()));

				iCollateral.setCMV(amt);
				iCollateral.setCMVCcyCode(iCollateral.getCurrencyCode());
			}
			else {
				iCollateral.setCMV(null);
			}
			//Govind S:End Line here/
			
			//Dattatray Thorat:lines added for loanable amount for change request
			if(colForm.getMargin() != null && !colForm.getMargin().equals(""))
				iCollateral.setMargin(Double.parseDouble(colForm.getMargin()));
			
			iCollateral.setLoanableAmount(UIUtil.removeComma(colForm.getLoanableAmount()));
			//Dattatray Thorat:End Line here/
			
			//Added by Pramod Katkar for New Filed CR on 13-08-2013
			if(colForm.getMonitorProcess()!=null){
				iCollateral.setMonitorProcess(colForm.getMonitorProcess());
				}
			if(colForm.getMonitorFrequency()!=null && "Y".equals(colForm.getMonitorProcess())){
				iCollateral.setMonitorFrequency(colForm.getMonitorFrequency());
				}
			else{
				iCollateral.setMonitorFrequency("");
			}
			
			if(colForm.getCommonRevalFreq()!=null){
			iCollateral.setCommonRevalFreq(colForm.getCommonRevalFreq());
			}
			if(colForm.getValuationAmount()!=null){
			iCollateral.setValuationAmount(UIUtil.removeComma(colForm.getValuationAmount()));
			}
			if(colForm.getValuationDate()!=null){
			iCollateral.setValuationDate(DateUtil.convertDate(colForm.getValuationDate()));
			}
			if(colForm.getNextValDate()!=null ){
			iCollateral.setNextValDate(DateUtil.convertDate(colForm.getNextValDate()));
			}else{
				iCollateral.setNextValDate(null);
			}
			if(colForm.getTypeOfChange()!=null){
			iCollateral.setTypeOfChange(colForm.getTypeOfChange());
			}
			if(colForm.getTypeOfChange()!=null && (colForm.getTypeOfChange().equalsIgnoreCase("SECOND_CHARGE"))){
				String num=colForm.getOtherBankCharge().trim();
					if(num!=null && !num.isEmpty() && !StringUtils.isBlank(num)){
						float numCal=Float.parseFloat(num); 
						if(numCal%1==0){
						num=num.replaceFirst("^0+(?!$)", "");
					}
				}
				iCollateral.setOtherBankCharge(num);
			}
			else{
				iCollateral.setOtherBankCharge("");
			}
			
			if(null != colForm.getTermLoanOutstdAmt() && !"".equals(colForm.getTermLoanOutstdAmt()) ) {
				iCollateral.setTermLoanOutstdAmt(colForm.getTermLoanOutstdAmt());
			}else {
				iCollateral.setTermLoanOutstdAmt("0.00");
			}
			if(null != colForm.getMarginAssetCover()  && !"".equals(colForm.getMarginAssetCover()) ) {
				iCollateral.setMarginAssetCover(colForm.getMarginAssetCover());
			}else {
				iCollateral.setMarginAssetCover("0.00");
			}
			if(null != colForm.getRecvGivenByClient()  && !"".equals(colForm.getRecvGivenByClient()) ) {
				iCollateral.setRecvGivenByClient(colForm.getRecvGivenByClient());
			}
			else {
				iCollateral.setRecvGivenByClient("0.00");
			}
			//End by Pramod Katkar
			
			//Start New General fields Added
			
			if(null != colForm.getPrimarySecurityAddress()) {
				iCollateral.setPrimarySecurityAddress(colForm.getPrimarySecurityAddress());
			}
			
			if(colForm.getSecurityValueAsPerCAM()!=null ){
				iCollateral.setSecurityValueAsPerCAM(DateUtil.convertDate(colForm.getSecurityValueAsPerCAM()));
				}else{
					iCollateral.setSecurityValueAsPerCAM(null);
			}
			
			if(null != colForm.getSecondarySecurityAddress()) {
				iCollateral.setSecondarySecurityAddress(colForm.getSecondarySecurityAddress());
			}
			
			if(null != colForm.getSecurityMargin()) {
				iCollateral.setSecurityMargin(colForm.getSecurityMargin());
			}
			
			if(null != colForm.getChargePriority()) {
				iCollateral.setChargePriority(colForm.getChargePriority());
			}
			
			//End New General fields Added
			
			if(colForm.getTypeOfCharge()!=null && ("submit".equals(colForm.getEvent()) ||"update".equals(colForm.getEvent()))){
				iCollateral.setTypeOfCharge(colForm.getTypeOfCharge());
				}
			
			//CERSAI Fields
			if(null != colForm.getOwnerOfProperty()) {
				iCollateral.setOwnerOfProperty(colForm.getOwnerOfProperty());
			}
			if(null != colForm.getCinForThirdParty()) {
				iCollateral.setCinForThirdParty(colForm.getCinForThirdParty());
			}
			
			if(null != colForm.getCersaiTransactionRefNumber()) {
				iCollateral.setCersaiTransactionRefNumber(colForm.getCersaiTransactionRefNumber());
			}
			if(null != colForm.getCersaiSecurityInterestId()) {
				iCollateral.setCersaiSecurityInterestId(colForm.getCersaiSecurityInterestId());
			}
			if(null != colForm.getCersaiAssetId()) {
				iCollateral.setCersaiAssetId(colForm.getCersaiAssetId());
			}
			if(null != colForm.getCersaiId()) {
				iCollateral.setCersaiId(colForm.getCersaiId());
			}
			if(null != colForm.getThirdPartyAddress()) {
				iCollateral.setThirdPartyAddress(colForm.getThirdPartyAddress());
			}
			if(null != colForm.getThirdPartyPincode()){
				iCollateral.setThirdPartyPincode(colForm.getThirdPartyPincode());
			}
			if(null != colForm.getDateOfCersaiRegisteration()) {
				iCollateral.setDateOfCersaiRegisteration(DateUtil.convertDate(colForm.getDateOfCersaiRegisteration()));
			}
			if(null != colForm.getSaleDeedPurchaseDate()) {
				iCollateral.setSaleDeedPurchaseDate(DateUtil.convertDate(colForm.getSaleDeedPurchaseDate()));
			}
			if(null != colForm.getThirdPartyEntity()){
				iCollateral.setThirdPartyEntity(colForm.getThirdPartyEntity());
			}
			if(null != colForm.getSecurityOwnership()) {
				iCollateral.setSecurityOwnership(colForm.getSecurityOwnership());
			}
			if(null != colForm.getThirdPartyState()){
				iCollateral.setThirdPartyState(colForm.getThirdPartyState());
			}
			if(null != colForm.getThirdPartyCity()){
				iCollateral.setThirdPartyCity(colForm.getThirdPartyCity());
			}

			if(null != colForm.getFdRebooking()) {
			if("Yes".equals(colForm.getFdRebooking().trim())) {
				iCollateral.setFdRebooking("Y");
			}else if("No".equals(colForm.getFdRebooking().trim())) {
				iCollateral.setFdRebooking("N");
			}
			}
//			UIUtil.mapOBDate_FormString(locale, objICol.getLEDate())
			if(null != colForm.getFdRebooking()) {
			if("Yes".equals(colForm.getFdRebooking().trim())) {
				iCollateral.setFdRebooking("Y");
			}else if("No".equals(colForm.getFdRebooking().trim())) {
				iCollateral.setFdRebooking("N");
			}
			}

			List<ISecurityCoverage> securityCoverage = CollateralMapperHelper.mapSecurityCoverageFormToOB(colForm);
			iCollateral.setSecurityCoverage(securityCoverage);
		}
		catch (Exception ex) {
			throw new MapperException("failed to map form to collateral ob", ex);
		}

		DefaultLogger.debug(this, "Existing mapFormToOB  ");

		return iCollateral;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		CollateralForm aForm = (CollateralForm) cForm;
		ICollateral objICol = (ICollateral) obj;
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) inputs.get("serviceColObj");
		String customerEntity = (String) inputs.get("customerEntity");
		String customerPartyName = (String) inputs.get("customerPartyName");
		
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		aForm.setSpread(String.valueOf(objICol.getSpread()));
		aForm.setCollateralMaturityDate(DateUtil.formatDate(locale, objICol.getCollateralMaturityDate()));
		aForm.setExchangeControl(objICol.getIsExchangeCtrlObtained());
		aForm.setExchangeControlDate(DateUtil.formatDate(locale, objICol.getExchangeCtrlDate()));
		aForm.setCollateralStatus(objICol.getCollateralStatus());

		if ((objICol.getCollateralLocation() != null) && (objICol.getCollateralLocation().length() > 0)) {
			aForm.setCollateralLoc(objICol.getCollateralLocation());
		}
		else {
			ILimitProfile profile = (ILimitProfile) inputs.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			if ((profile != null) && (profile.getOriginatingLocation() != null)) {
				aForm.setCollateralLoc(profile.getOriginatingLocation().getCountryCode());
			}
		}

		if ((objICol.getSecurityOrganization() != null) && (objICol.getSecurityOrganization().length() > 0)) {
			aForm.setSecurityOrganization(objICol.getSecurityOrganization());
		}
		else {
			ILimitProfile profile = (ILimitProfile) inputs.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			if ((profile != null) && (profile.getOriginatingLocation() != null)) {
				aForm.setSecurityOrganization(profile.getOriginatingLocation().getOrganisationCode());
			}
		}

		if (objICol.getCurrencyCode() != null) {
			aForm.setCollateralCurrency(objICol.getCurrencyCode());
		}
		else {
			aForm.setCollateralCurrency(objICol.getSCICurrencyCode());
		}

		aForm.setSciCurrency(objICol.getSCICurrencyCode());

		if (AbstractCommonMapper.isEmptyOrNull(objICol.getCollateralCustodianType())) {
			aForm.setSecCustodianType(ICMSConstant.EXTERNAL_COL_CUSTODIAN);
			aForm.setSecCustodianExt(objICol.getCollateralCustodian());
		}
		else {
			aForm.setSecCustodianType(objICol.getCollateralCustodianType());
			if (objICol.getCollateralCustodianType().equals(ICMSConstant.INTERNAL_COL_CUSTODIAN)) {
				aForm.setSecCustodianInt(objICol.getCollateralCustodian());
			}
			else {
				aForm.setSecCustodianExt(objICol.getCollateralCustodian());
			}
		}

		aForm.setSecPriority(objICol.getSecPriority());
		
		aForm.setLe(objICol.getIsLE());
		aForm.setRemargin(objICol.getRemargin());

		aForm.setLeDate(UIUtil.mapOBDate_FormString(locale, objICol.getLEDate()));
		aForm.setLastRemarginDate(UIUtil.mapOBDate_FormString(locale, objICol.getLastRemarginDate()));
		aForm.setNextRemarginDate(UIUtil.mapOBDate_FormString(locale, objICol.getNextRemarginDate()));
		aForm.setPerfectionDate(UIUtil.mapOBDate_FormString(locale, objICol.getPerfectionDate()));
		aForm.setSourceSecuritySubType(objICol.getSourceSecuritySubType());
		aForm.setCollateralName(objICol.getSCIReferenceNote());
		aForm.setCommonRevalFreq(objICol.getCommonRevalFreq());
		aForm.setMonitorFrequency(objICol.getMonitorFrequency());
		aForm.setMonitorProcess(objICol.getMonitorProcess());
		 aForm.setValuationAmount(UIUtil.formatWithCommaAndDecimal(objICol.getValuationAmount()));
		if(objICol.getValuationDate()!=null){
		aForm.setValuationDate(df.format(objICol.getValuationDate()));
		}
		else{
			aForm.setValuationDate("");
		}
		if(objICol.getNextValDate()!=null){
		aForm.setNextValDate(df.format(objICol.getNextValDate()));
		}
		else{
			aForm.setNextValDate("");
		}
		if(objICol.getTypeOfChange()!=null){
		aForm.setTypeOfChange(objICol.getTypeOfChange());
		}
		else{
			aForm.setTypeOfChange("");
		}
		if(objICol.getTypeOfChange()!=null){
		if(objICol.getTypeOfChange().equalsIgnoreCase("SECOND_CHARGE")&& !objICol.getTypeOfChange().trim().equals("")){
			aForm.setOtherBankCharge(objICol.getOtherBankCharge());
		}
		else{
			aForm.setOtherBankCharge("");
		}
		}else{
			aForm.setOtherBankCharge("");
		}

		
		//Start New General fields Added
		
		if(null != objICol.getPrimarySecurityAddress()) {
			aForm.setPrimarySecurityAddress(objICol.getPrimarySecurityAddress());
		}else {
			aForm.setPrimarySecurityAddress("");
		}
		
		if(objICol.getSecurityValueAsPerCAM()!=null){
			aForm.setSecurityValueAsPerCAM(df.format(objICol.getSecurityValueAsPerCAM()));
		}
		else{
				aForm.setSecurityValueAsPerCAM("");
		}
		
		
		if(null != objICol.getSecondarySecurityAddress()) {
			aForm.setSecondarySecurityAddress(objICol.getSecondarySecurityAddress());
		}else {
			aForm.setSecondarySecurityAddress("");
		}
		
		if(null != objICol.getSecurityMargin()) {
			aForm.setSecurityMargin(objICol.getSecurityMargin());
		}else {
			aForm.setSecurityMargin("");
		}
		
		if(null != objICol.getChargePriority()) {
			aForm.setChargePriority(objICol.getChargePriority());
		}else {
			aForm.setChargePriority("");
		}
		
		//End New General fields Added
		
		
		
		aForm.setReservePrice("");
		if ((objICol.getReservePrice() != null) && (objICol.getCurrencyCode() != null)) {
			if (objICol.getReservePrice().getAmount() >= 0) {
				try {
					aForm.setReservePrice(CurrencyManager.convertToString(locale, objICol.getReservePrice()));
				}
				catch (Exception ex) {
					throw new MapperException("failed to covert reserve price", ex);
				}
			}
		}

		aForm.setRiskMitigationCategory(objICol.getRiskMitigationCategory());

		if (objICol.getIsBorrowerDependency()) {
			aForm.setBorrowerDependency(ICMSConstant.TRUE_VALUE);
		}
		else if (!objICol.getIsBorrowerDependency()) {
			aForm.setBorrowerDependency(ICMSConstant.FALSE_VALUE);
		}

		aForm = obToFormUpdateLimitCharge(objICol, aForm, locale);
		// aForm = obToFormUpdateValuation (objICol, aForm, locale);

		// Set Valuation
		aForm = obToFormUpdateValuationIntoCMS(objICol, aForm, locale);

		synInstrument2Form(objICol, aForm);

		if (objICol.getIsCGCPledged()) {
			aForm.setCgcPledged(ICMSConstant.TRUE_VALUE);
		}
		else {
			aForm.setCgcPledged(ICMSConstant.FALSE_VALUE);
		}

		if (!AbstractCommonMapper.isEmptyOrNull(aForm.getCollateralLoc())) {
			ICollateralParameter parameter = null;
			try {
				parameter = CollateralProxyFactory.getProxy().getCollateralParameter(aForm.getCollateralLoc(),
						objICol.getSCISubTypeValue());
			}
			catch (Exception e) {
				DefaultLogger.warn(this, "There is no collateral parameter for collateral country ["
						+ aForm.getCollateralLoc() + "], subtype [" + objICol.getSCISubTypeValue() + "]", e);
			}

			if (parameter != null) {
				setRevalDate(objICol, parameter);
				aForm.setRevalFreq(String.valueOf(parameter.getValuationFrequency()));
				// String unit =
				// TimeFreqList.getInstance().getTimeFreqItem(parameter.getValuationFrequencyUnit());
				if (parameter.getValuationFrequencyUnit() != null) {
					aForm.setRevalFreqUnit(parameter.getValuationFrequencyUnit());
				}
				else {
					aForm.setRevalFreqUnit("");
				}
			}
		}
		else {
			aForm.setRevalFreq("");
			aForm.setRevalFreqUnit("");
		}

		if ((objICol.getNetRealisableAmount() != null) && (objICol.getNetRealisableAmount().getCurrencyCode() != null)) {
			try {
				aForm.setNetRealisableSecValue(UIUtil.formatAmount(objICol.getNetRealisableAmount(), 2, locale, false));
			}
			catch (Exception ex) {
				throw new MapperException("failed to convert net realisable value to display value", ex);
			}
		}
		
		/****
		 * Govind S:HDFC bank 14/07/2011
		 * 
		 */
		if ((objICol.getCMV() != null) && (objICol.getCMV().getCurrencyCode() != null)) {
			try {
				 //Start:Uma Khot: added to allow upto decimal 4 in Security OMV for Markatable Sec-Mutual Fund Security
				if(aForm instanceof MarksecMainLocalForm){
					aForm.setAmountCMV(UIUtil.formatAmount(objICol.getCMV(), 4,locale , false));
				}
				 //End:Uma Khot: added to allow upto decimal 4 in Security OMV for Markatable Sec-Mutual Fund Security
				
				else{
				// aForm.setAmountCMV(UIUtil.formatAmount(objICol.getCMV(), 2,locale , false));
				/*DefaultLogger.debug(this, "----------------1------------"+objICol.getCMV());
				DefaultLogger.debug(this, "----------------2------------"+objICol.getCMV().getAmountAsBigDecimal());
				DefaultLogger.debug(this, "----------------3------------"+objICol.getCMV().getAmountAsBigDecimal().toString());
				*/
				 aForm.setAmountCMV(CurrencyManager.convertToString(locale,objICol.getCMV()));
				}
			
			}
			catch (Exception ex) {
				ex.printStackTrace();
				//throw new MapperException("failed to convert net realisable value to display value", ex);
			}
		}
		aForm.setLoanableAmount(UIUtil.formatWithCommaAndDecimal(objICol.getLoanableAmount()));
		/***
		 * 
		 */
		if(!"".equals(aForm.getTypeOfCharge()) && "update_return".equals(aForm.getEvent())&& !itrxValue.getCollateral().getTypeOfCharge().equals(aForm.getTypeOfCharge())){
			itrxValue.getCollateral().setTypeOfCharge(aForm.getTypeOfCharge());
			objICol.setTypeOfCharge(aForm.getTypeOfCharge());
		}else{
			aForm.setTypeOfCharge(itrxValue.getCollateral().getTypeOfCharge());
		}
		

		if (null != objICol.getTermLoanOutstdAmt() ) {
			aForm.setTermLoanOutstdAmt(objICol.getTermLoanOutstdAmt());
		}
		if (null != objICol.getMarginAssetCover() ) {
			aForm.setMarginAssetCover(objICol.getMarginAssetCover());
		}
		if (null != objICol.getRecvGivenByClient() ) {
			aForm.setRecvGivenByClient(objICol.getRecvGivenByClient());
		}
		
		if (null != objICol.getTermLoanOutstdAmt() ) {
			aForm.setTermLoanOutstdAmt(objICol.getTermLoanOutstdAmt());
		}
		if (null != objICol.getMarginAssetCover() ) {
			aForm.setMarginAssetCover(objICol.getMarginAssetCover());
		}
		if (null != objICol.getRecvGivenByClient() ) {
			aForm.setRecvGivenByClient(objICol.getRecvGivenByClient());
		}
		//CERSAI Fields

		if (StringUtils.isNotBlank(objICol.getCersaiTransactionRefNumber())) {
			aForm.setCersaiTransactionRefNumber(objICol.getCersaiTransactionRefNumber());
		}
		if (StringUtils.isNotBlank(objICol.getCersaiSecurityInterestId())) {
			aForm.setCersaiSecurityInterestId(objICol.getCersaiSecurityInterestId());
		}
		if (StringUtils.isNotBlank(objICol.getCersaiAssetId())) {
			aForm.setCersaiAssetId(objICol.getCersaiAssetId());
		}
		if (StringUtils.isNotBlank(objICol.getCersaiId())) {
			aForm.setCersaiId(objICol.getCersaiId());
		}

		if (null != objICol.getDateOfCersaiRegisteration()) {
			aForm.setDateOfCersaiRegisteration(UIUtil.mapOBDate_FormString(locale, objICol.getDateOfCersaiRegisteration()));
		}
		if (null != objICol.getSaleDeedPurchaseDate()) {
			aForm.setSaleDeedPurchaseDate(UIUtil.mapOBDate_FormString(locale, objICol.getSaleDeedPurchaseDate()));
		}

		if (StringUtils.isNotBlank(objICol.getSecurityOwnership())) {
			aForm.setSecurityOwnership(objICol.getSecurityOwnership());
		}
		if (StringUtils.isBlank(objICol.getOwnerOfProperty())
				&& CommonCodeEntryConstant.SecurityOwnershipCodes.BORROWER.equals(objICol.getSecurityOwnership())) {
			aForm.setOwnerOfProperty(customerPartyName);
		} else {
			aForm.setOwnerOfProperty(objICol.getOwnerOfProperty());
		}
		if (StringUtils.isBlank(objICol.getThirdPartyEntity())
				&& CommonCodeEntryConstant.SecurityOwnershipCodes.BORROWER.equals(objICol.getSecurityOwnership())) {
			aForm.setThirdPartyEntity(customerEntity);
		} else {
			aForm.setThirdPartyEntity(objICol.getThirdPartyEntity());
		}
		if (StringUtils.isNotBlank(objICol.getCinForThirdParty())) {
			aForm.setCinForThirdParty(objICol.getCinForThirdParty());
		}
		if (CommonCodeEntryConstant.SecurityOwnershipCodes.THIRD_PARTY.equals(objICol.getSecurityOwnership())
				&& StringUtils.isNotBlank(objICol.getThirdPartyState())) {
			aForm.setThirdPartyState(objICol.getThirdPartyState());
		}
		if (CommonCodeEntryConstant.SecurityOwnershipCodes.THIRD_PARTY.equals(objICol.getSecurityOwnership())
				&& StringUtils.isNotBlank(objICol.getThirdPartyCity())) {
			aForm.setThirdPartyCity(objICol.getThirdPartyCity());
		}
		if (CommonCodeEntryConstant.SecurityOwnershipCodes.THIRD_PARTY.equals(objICol.getSecurityOwnership())
				&& StringUtils.isNotBlank(objICol.getThirdPartyAddress())) {
			aForm.setThirdPartyAddress(objICol.getThirdPartyAddress());
		}
		if (CommonCodeEntryConstant.SecurityOwnershipCodes.THIRD_PARTY.equals(objICol.getSecurityOwnership())
				&& StringUtils.isNotBlank(objICol.getThirdPartyPincode())) {
			aForm.setThirdPartyPincode(objICol.getThirdPartyPincode());
		}

		if(null != objICol.getFdRebooking()) {
		if("Y".equalsIgnoreCase(objICol.getFdRebooking().trim())) {
			aForm.setFdRebooking("Yes");			
		}else if("N".equalsIgnoreCase(objICol.getFdRebooking().trim())) {
			aForm.setFdRebooking("No");
		}
		}
		if(null != objICol.getFdRebooking()) {
		if("Y".equalsIgnoreCase(objICol.getFdRebooking().trim())) {
			aForm.setFdRebooking("Yes");			
		}else if("N".equalsIgnoreCase(objICol.getFdRebooking().trim())) {
			aForm.setFdRebooking("No");
		}
		}
		aForm = (CollateralForm) (new DeleteApportionmentMapper().mapOBToForm(cForm, obj, inputs));
		
		List<ISecurityCoverage> securityCoverage = objICol.getSecurityCoverage();
		SecurityCoverageForm  securityCoverageForm = CollateralMapperHelper.mapSecurityCoverageOBToForm(securityCoverage);
		aForm.setCoverageAmount(securityCoverageForm.getCoverageAmount());
		aForm.setCoveragePercentage(securityCoverageForm.getCoveragePercentage());
		aForm.setAdHocCoverageAmount(securityCoverageForm.getAdHocCoverageAmount());
		aForm.setSecurityCoverageForm(securityCoverageForm);
		
		DefaultLogger.debug(this, "Existing mapOBToForm  ");
		return aForm;
	}

	private void setRevalDate(ICollateral col, ICollateralParameter parameter) {
		IValuation iSourceVal = col.getSourceValuation();
		IValuation iManualVal = col.getValuationIntoCMS();
		if ((iSourceVal != null) && (iSourceVal.getValuationDate() != null)) {
			int revalFreq = parameter.getValuationFrequency();
			String freqUnit = parameter.getValuationFrequencyUnit();
			Date reValDate = UIUtil.calculateDate(revalFreq, freqUnit, iSourceVal.getValuationDate());
			if(iSourceVal.getValuationDate()!=null)
				iSourceVal.setFSVEvaluationDate(iSourceVal.getValuationDate());
			else
				iSourceVal.setFSVEvaluationDate(new Date());
			iSourceVal.setRevaluationDate(reValDate);
		}
		if ((iManualVal != null) && (iManualVal.getValuationDate() != null)) {
			int revalFreq = parameter.getValuationFrequency();
			String freqUnit = parameter.getValuationFrequencyUnit();
			Date reValDate = UIUtil.calculateDate(revalFreq, freqUnit, iManualVal.getValuationDate());
			iManualVal.setFSVEvaluationDate(iManualVal.getValuationDate());
			iManualVal.setRevaluationDate(reValDate);
		}
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the mapFormToOB method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",GLOBAL_SCOPE }, 
				{ "valuerName", "java.util.Collection", SERVICE_SCOPE },
				{ "oldValuer", "java.lang.String", SERVICE_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE }, 
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{ "customerEntity", "java.lang.String", SERVICE_SCOPE },
				{ "customerPartyName", "java.lang.String", SERVICE_SCOPE },
				{ "collateralCategory", String.class.getName(), SERVICE_SCOPE },
				{ "cersaiApplicableInd", String.class.getName(), SERVICE_SCOPE },
				{ "deferralIds", String.class.getName(), SERVICE_SCOPE },
				{ "valuation2Mandatory", String.class.getName(), SERVICE_SCOPE }
				});
	}

	public static Date compareDate(Locale locale, Date dateOrigin, String dateStage) {

		Date returnDate = DateUtil.convertDate(locale, dateStage);

		if (dateOrigin != null) {
			String originalDate = DateUtil.formatDate(locale, dateOrigin);
			if (originalDate.equals(dateStage)) {
				returnDate = dateOrigin;
			}
		}
		return returnDate;
	}

	// method to update limit charge details from Form to OB
	private ICollateral formToOBUpdateLimitCharge(ICollateral iCollateral, CollateralForm colForm, Locale locale)
			throws Exception {
		Date stageDate = null;

		ILimitCharge[] limitCharge = iCollateral.getLimitCharges();
		boolean updated = false;
		if (!SecuritySubTypeUtil.canCollateralMaintainMultipleCharge(iCollateral)) {
			ILimitCharge objLimit = null;
			if (limitCharge != null) {
				if (limitCharge.length > 0) {
					objLimit = limitCharge[0];
					if (objLimit == null) {
						objLimit = new OBLimitCharge();
						objLimit.setChargeCcyCode(iCollateral.getCurrencyCode());
					}
				}
				else {
					limitCharge = new OBLimitCharge[1];
					objLimit = new OBLimitCharge();
					objLimit.setChargeCcyCode(iCollateral.getCurrencyCode());
				}
			}
			else {
				limitCharge = new OBLimitCharge[1];
				objLimit = new OBLimitCharge();
				objLimit.setChargeCcyCode(iCollateral.getCurrencyCode());
			}

			if (!colForm.getNatureOfCharge().equals(objLimit.getNatureOfCharge())) {
				updated = true;
				objLimit.setNatureOfCharge(colForm.getNatureOfCharge());
			}
			if (objLimit.getChargeCcyCode() == null) {
				objLimit.setChargeCcyCode(iCollateral.getCurrencyCode());
			}

			/*if (AbstractCommonMapper.isEmptyOrNull(colForm.getAmtCharge()) && (objLimit.getChargeAmount() != null)
					&& (objLimit.getChargeAmount().getAmount() >= 0)) {
				updated = true;
				objLimit.setChargeAmount(CurrencyManager.convertToAmount(locale, objLimit.getChargeCcyCode(), "0"));
			}
			else if (!AbstractCommonMapper.isEmptyOrNull(colForm.getAmtCharge())) {
				updated = true;
				objLimit.setChargeAmount(CurrencyManager.convertToAmount(locale, objLimit.getChargeCcyCode(), colForm
						.getAmtCharge()));
			}*/

			if (!AbstractCommonMapper.isEmptyOrNull(colForm.getLegalChargeDate())) {
				updated = true;
				stageDate = compareDate(locale, objLimit.getLegalChargeDate(), colForm.getLegalChargeDate());
				objLimit.setLegalChargeDate(stageDate);
			}
			else {
				objLimit.setLegalChargeDate(null);
			}

			if (!AbstractCommonMapper.isEmptyOrNull(colForm.getChargeType())) {
				updated = true;
				objLimit.setChargeType(colForm.getChargeType());
			}

			if (!AbstractCommonMapper.isEmptyOrNull(colForm.getConfirmChargeDate())) {
				updated = true;
				stageDate = compareDate(locale, objLimit.getChargeConfirmationDate(), colForm.getConfirmChargeDate());
				objLimit.setChargeConfirmationDate(stageDate);
			}
			else {
				objLimit.setChargeConfirmationDate(null);
			}

			limitCharge[0] = objLimit;

			if (updated) {
				if (iCollateral.getCurrentCollateralLimits() != null
						&& iCollateral.getCurrentCollateralLimits().length != 0) {
					limitCharge[0].setLimitMaps(iCollateral.getCurrentCollateralLimits());
				}

				iCollateral.setLimitCharges(limitCharge);
			}
		}
		return iCollateral;
	}

	// method to set limit charge dtails info form
	private CollateralForm obToFormUpdateLimitCharge(ICollateral objICol, CollateralForm aForm, Locale locale) {
		ILimitCharge[] charge = objICol.getLimitCharges();
		if ((charge != null) && (charge.length > 0)) {
			ILimitCharge objC = charge[0];
			if (objC != null) {
				aForm.setNatureOfCharge(objC.getNatureOfCharge());
				if ((objC.getChargeAmount() != null) && (objC.getChargeAmount().getCurrencyCode() != null)) {
					if (objC.getChargeAmount().getAmount() >= 0) {
						try {
							aForm.setAmtCharge(CurrencyManager.convertToString(locale, objC.getChargeAmount()));
						}
						catch (Exception ex) {
							throw new MapperException("failed to covert charge amount to display value", ex);
						}
						aForm.setCurrencyCharge(objC.getChargeAmount().getCurrencyCode());
					}
				}
				aForm.setLegalChargeDate(DateUtil.formatDate(locale, objC.getLegalChargeDate()));
				aForm.setChargeType(objC.getChargeType());
				aForm.setConfirmChargeDate(DateUtil.formatDate(locale, objC.getChargeConfirmationDate()));
			}
		}
		return aForm;
	}

	private void synInstrument2OB(ICollateral collateral, CollateralForm colForm) {
		IInstrument[] instrumentArray = collateral.getInstrumentArray();
		String[] instrmentCodeArray = colForm.getSecInstrument();
		if ((instrmentCodeArray == null) || ((instrmentCodeArray != null) && (instrmentCodeArray.length == 0))) {
			collateral.setInstrumentArray(null);
			return;
		}
		HashMap instrumentCodeMap = new HashMap();
		int length = instrumentArray == null ? 0 : instrumentArray.length;
		for (int index = 0; index < length; index++) {
			instrumentCodeMap.put(instrumentArray[index].getInstrumentCode(), instrumentArray[index]);
		}
		IInstrument[] newInstrumentArray = new IInstrument[instrmentCodeArray.length];
		for (int index = 0; index < instrmentCodeArray.length; index++) {
			IInstrument aInstrument = (IInstrument) instrumentCodeMap.get(instrmentCodeArray[index]);
			if (aInstrument == null) {
				aInstrument = new OBInstrument();
				aInstrument.setInstrumentCode(instrmentCodeArray[index]);
				aInstrument.setCollateralID(collateral.getCollateralID());
			}
			newInstrumentArray[index] = aInstrument;
		}
		collateral.setInstrumentArray(newInstrumentArray);
	}

	private void synInstrument2Form(ICollateral collateral, CollateralForm colForm) {
		IInstrument[] instrumentArray = collateral.getInstrumentArray();
		if ((instrumentArray == null) || (instrumentArray.length == 0)) {
			return;
		}
		String[] instrmentCodeArray = new String[instrumentArray.length];
		for (int index = 0; index < instrumentArray.length; index++) {
			instrmentCodeArray[index] = instrumentArray[index].getInstrumentCode();
		}
		colForm.setSecInstrument(instrmentCodeArray);
	}

	private boolean isManualValuationDeleteRequired(IValuation manualVal, CollateralForm colForm, Locale locale,
			ICollateral collateral) {
		if ((manualVal != null)
				&& (CommonUtil.isEmpty(colForm.getValuer()) && CommonUtil.isEmpty(colForm.getValDate())
						&& CommonUtil.isEmpty(colForm.getAmountCMV()) && CommonUtil.isEmpty(colForm.getValCurrency()))) {
			return true;
		}

		return false;
	}

	private boolean isManualValuationInsertRequired(IValuation manualVal, CollateralForm colForm, Locale locale,
			ICollateral collateral) {

		// if no valuation data input by user - always return false, no
		// valuation record will be inserted into db
		if ((CommonUtil.isEmpty(colForm.getValDate()) || CommonUtil.isEmpty(colForm.getAmountCMV()) || CommonUtil
				.isEmpty(colForm.getValCurrency()))) {
			return false;
		}

		// ======= if there is valuation data input by user ==== //
		// previously no data - need to insert into db
		if (manualVal == null) {
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>> Manual Valuation = null");
			return true;
		}

		// ====== if prev manual valuation not null - comparison required
		// ===========//

		// compare if valuation date is the same
		Date manualValDate = manualVal.getValuationDate();
		Date inputValDate = DateUtil.clearTime(DateUtil.convertDate(locale, colForm.getValDate()));

		// same date
		if (((manualValDate == null) && (inputValDate != null))
				|| ((manualValDate != null) && (manualValDate.compareTo(inputValDate) != 0))) {
			return true;
		}

		double margin = ICMSConstant.DOUBLE_INVALID_VALUE;
		if (collateral.getMargin() >= 0) {
			margin = collateral.getMargin() * 100;
		}
		double inputMargin = 100;

		if (!CommonUtil.isEmpty(colForm.getMargin())) {
			inputMargin = Double.parseDouble(colForm.getMargin());
		}
		else {
			try {
				ICollateralParameter parameter = CollateralProxyFactory.getProxy().getCollateralParameter(
						colForm.getCollateralLoc(), collateral.getSCISubTypeValue());
				if (parameter != null) {
					inputMargin = ValuationUtil.determineMargin(parameter.getThresholdPercent());
					DefaultLogger.debug(this, ">>>>>>>> [isManualValuationInsertRequired] input margin=" + inputMargin);
				}

			}
			catch (CollateralException ex) {
				DefaultLogger.warn(this,
						"Collateral Exception encountered when retrieving haircut for calculating FSV "
								+ " Collateral id=[" + collateral.getCollateralID() + "]");
			}
		}

		Amount manualCmvAmt = manualVal.getCMV();
		if (manualCmvAmt == null) {
			DefaultLogger.debug(this, ">>>>>>>> [isManualValuationInsertRequired] manualCmvAmt == null");
			return true;
		}
		else {
			try {
				String manualCmvCurr = manualCmvAmt.getCurrencyCode();
				double manualCmv = manualCmvAmt.getAmount();

				// need to remove commas
				BigDecimal inputCmv = UIUtil.mapStringToBigDecimal(colForm.getAmountCMV());

				double inputCmvAmt = inputCmv.doubleValue();
				String inputCmvCurr = colForm.getValCurrency();

				return (!(manualCmvCurr.equals(inputCmvCurr) && (manualCmv == inputCmvAmt) && (margin == inputMargin)));

			}
			catch (NumberFormatException e) {
				DefaultLogger.debug(this, ">>>>>>>> [isManualValuationInsertRequired] NumberFormatException!!!");
				// error with the input amt, no need to insert into db then
				return false;
			}
		}

	}

	private ICollateral formToOBUpdateValuationIntoCMS(ICollateral iCollateral, CollateralForm colForm, Locale locale,
			HashMap inputs, boolean isCurrencyChange) throws Exception {

		IValuation manualVal = iCollateral.getValuationIntoCMS();

		if (isManualValuationDeleteRequired(manualVal, colForm, locale, iCollateral)) {
			// set valuation info to null if NOT REQUIRED to insert Manual
			// Valuation
			iCollateral.setValuationIntoCMS(null);
			return iCollateral;
		}


		if (manualVal == null) {
			manualVal = new OBValuation();
		}
		
		//************ Start of Lines added by Dattatray Thorat *************
        manualVal.setCurrencyCode("MYR");
		manualVal.setSourceId(ICMSConstant.SOURCE_SYSTEM_CMS);
		manualVal.setSourceType(ICMSConstant.VALUATION_SOURCE_TYPE_A);
		manualVal.setValuerName(ICMSConstant.SOURCE_SYSTEM_CMS);
		manualVal.setValuationDate(new Date());
		
//		manualVal.setCMV(UIUtil.mapFormString_OBAmount(locale, manualVal.getCurrencyCode(), "200",
//				manualVal.getCMV()));
		
		/*******/
		//Govind S:Add for OMV for HDFC bank, 08/07/2011
		if (!AbstractCommonMapper.isEmptyOrNull(colForm.getAmountCMV())) {
			Amount amt = new Amount(UIUtil.mapStringToBigDecimal(colForm.getAmountCMV()),
					new CurrencyCode(colForm.getCollateralCurrency()));

			manualVal.setCMV(amt);
		}
		else {
			manualVal.setCMV(null);
		}
		
		//Govind S:End Line here/
		/*******/
		
		manualVal.setNextRevaluationDate(new Date());
		manualVal.setRevaluationFreq(0);
		manualVal.setFSV(UIUtil.mapFormString_OBAmount(locale, manualVal.getCurrencyCode(), "200",
				manualVal.getCMV()));
		manualVal.setValuationType("COL");
		manualVal.setReservePriceDate(new Date());
		manualVal.setRevaluationFreq(0);
		
		iCollateral.setLastRemarginDate(new Date());
		iCollateral.setNextRemarginDate(new Date());
		iCollateral.setValuer("CMS");
		iCollateral.setValuationType("COL");
		iCollateral.setFSVCcyCode(null);
		iCollateral.setFSV(null);
		
        //************ End of Lines added by Dattatray Thorat *************	
		
		/*manualVal.setCurrencyCode(colForm.getValCurrency());
		manualVal.setSourceId(ICMSConstant.SOURCE_SYSTEM_CMS);
		manualVal.setSourceType(ICMSConstant.VALUATION_SOURCE_TYPE_M);
		manualVal.setValuerName(colForm.getValuerInGCMS());
		manualVal.setValuationDate(UIUtil.mapFormString_OBDate(locale, manualVal.getValuationDate(), colForm
				.getValDate()));

		manualVal.setCMV(UIUtil.mapFormString_OBAmount(locale, manualVal.getCurrencyCode(), colForm.getAmountCMV(),
				manualVal.getCMV()));

		if (manualVal.getCMV() != null) {
			ICollateralParameter parameter = CollateralProxyFactory.getProxy().getCollateralParameter(
					colForm.getCollateralLoc(), iCollateral.getSCISubTypeValue());

			Date nextValuationDate = null;
			if (parameter != null) {
				nextValuationDate = UIUtil.calculateDate(parameter.getValuationFrequency(), parameter
						.getValuationFrequencyUnit(), manualVal.getValuationDate());
				manualVal.setNextRevaluationDate(nextValuationDate);
			}

			double cmvAmtVal = manualVal.getCMV().getAmount();
			double fsvAmtVal = 0;
			if (!AbstractCommonMapper.isEmptyOrNull(colForm.getMargin())) {
				fsvAmtVal = cmvAmtVal * Double.parseDouble(colForm.getMargin()) / 100.0;
			}
			else {
				if (parameter != null) {
					fsvAmtVal = cmvAmtVal * ValuationUtil.determineMargin(parameter.getThresholdPercent()) / 100.0;
				}
			}

			if (AbstractCommonMapper.isEmptyOrNull(colForm.getAmountFSV())) {
				manualVal.setFSV(new Amount(fsvAmtVal, manualVal.getCurrencyCode()));
			}
			else {
				manualVal.setFSV(UIUtil.mapFormString_OBAmount(locale, manualVal.getCurrencyCode(), colForm
						.getAmountFSV(), manualVal.getFSV()));
			}

			// original: for manual input valuation, update fsv and cmv in
			// security. updated: compare manual vs los vs sys valuation.
			IValuation[] losValList = iCollateral.getValuationFromLOS();
			IValuation losVal = ((losValList != null) && (losValList.length > 0)) ? losValList[0] : null;
			IValuation sysVal = iCollateral.getSourceValuation();

			Date nextRemarginDate = null;

			IValuationModel losValuationModel = (losVal == null) ? null : new GenericValuationModel(losVal);
			IValuationModel sysValuationModel = (sysVal == null) ? null : new GenericValuationModel(sysVal);
			IValuationModel manualValuationModel = (manualVal == null) ? null : new GenericValuationModel(manualVal);

			Map valuationModelMap = new HashMap();
			valuationModelMap.put(ICMSConstant.VALUATION_SOURCE_TYPE_M, manualValuationModel);
			valuationModelMap.put(ICMSConstant.VALUATION_SOURCE_TYPE_S, losValuationModel);
			valuationModelMap.put(ICMSConstant.VALUATION_SOURCE_TYPE_A, sysValuationModel);

			IValuationModel latestValuationModel = ValuationUtil.getLatestValuationModel(valuationModelMap,
					new ValuationFrequency(parameter.getValuationFrequencyUnit(), parameter.getValuationFrequency()));

			if (latestValuationModel != null) {
				nextRemarginDate = latestValuationModel.getNextValuationDate();

				iCollateral.setCMVCcyCode(latestValuationModel.getValOMV().getCurrencyCode());
				iCollateral.setCMV(latestValuationModel.getValOMV());
				if (latestValuationModel.getValFSV() != null) {
					iCollateral.setFSVCcyCode(latestValuationModel.getValFSV().getCurrencyCode());
					iCollateral.setFSV(latestValuationModel.getValFSV());
				}
				else {
					iCollateral.setFSVCcyCode(null);
					iCollateral.setFSV(null);
				}
				iCollateral.setLastRemarginDate(latestValuationModel.getValuationDate());
				iCollateral.setNextRemarginDate(nextRemarginDate);
				iCollateral.setValuer(latestValuationModel.getValuer());
				iCollateral.setValuationType(latestValuationModel.getValuationType());
			}
		}

		manualVal.setRevaluationFreq(UIUtil.mapFormString_OBInteger(colForm.getRevalFreq()));

		if (!AbstractCommonMapper.isEmptyOrNull(colForm.getRevalFreqUnit())) {
			manualVal.setRevaluationFreqUnit(colForm.getRevalFreqUnit());
		}

		manualVal.setReservePriceDate(UIUtil.mapFormString_OBDate(locale, manualVal.getReservePriceDate(), colForm
				.getReservePriceDate()));

		manualVal.setValuationType(colForm.getValuationType());*/
		
		if (!isManualValuationInsertRequired(manualVal, colForm, locale, iCollateral)) {
			
			//********** Lines Added by Dattatray Thorat
			
			iCollateral.setValuationIntoCMS(manualVal);
			
			//********** Lines Added by Dattatray Thorat
			
			return iCollateral;
		}
		
		iCollateral.setValuationIntoCMS(manualVal);

		return iCollateral;
	}

	private CollateralForm obToFormUpdateValuationIntoCMS(ICollateral objICol, CollateralForm aForm, Locale locale) {
		IValuation iVal = objICol.getValuationIntoCMS();
		if (iVal != null) {
			aForm.setValDate(DateUtil.formatDate(locale, iVal.getValuationDate()));
			aForm.setValCurrency(iVal.getCurrencyCode());

			aForm.setValBefMargin("");
			if ((iVal.getBeforeMarginValue() != null) && (iVal.getBeforeMarginValue().getCurrencyCode() != null)) {
				if (iVal.getBeforeMarginValue().getAmount() >= 0) {
					try {
						aForm.setValBefMargin(CurrencyManager.convertToString(locale, iVal.getBeforeMarginValue()));
					}
					catch (Exception ex) {
						throw new MapperException("failed to covert valuation before margin value to display value", ex);
					}
				}
			}

			if (iVal.getNonRevaluationFreq() != ICMSConstant.INT_INVALID_VALUE) {
				aForm.setNonStdFreq(String.valueOf(iVal.getNonRevaluationFreq()));
				aForm.setNonStdFreqUnit(iVal.getNonRevaluationFreqUnit());
				aForm.setNonStdFreqUnitDesc(TimeFreqList.getInstance()
						.getTimeFreqItem(iVal.getNonRevaluationFreqUnit()));
			}

			aForm.setValAftMargin("");
			if ((iVal.getAfterMarginValue() != null) && (iVal.getAfterMarginValue().getCurrencyCode() != null)) {
				if (iVal.getAfterMarginValue().getAmount() >= 0) {
					try {
						aForm.setValAftMargin(CurrencyManager.convertToString(locale, iVal.getAfterMarginValue()));
					}
					catch (Exception ex) {
						throw new MapperException("failed to covert valuation after margin value to display value", ex);
					}
				}
			}

			aForm.setValuationType(iVal.getValuationType());
			aForm.setValuerInGCMS(iVal.getValuerName());

			aForm.setAmountCMV("");
			if ((iVal.getCMV() != null) && (iVal.getCMV().getCurrencyCode() != null) && !(iVal.getCMV().getCurrencyCode().trim().equals(""))) {
				if (iVal.getCMV().getAmount() >= 0) {
					try {
						aForm.setAmountCMV(CurrencyManager.convertToString(locale, iVal.getCMV()));
					}
					catch (Exception ex) {
						throw new MapperException("failed to covert cmv to display value", ex);
					}
				}
			}

			aForm.setAmountFSV("");
			if ((iVal.getFSV() != null) && (iVal.getFSV().getCurrencyCode() != null)) {
				if (iVal.getFSV().getAmount() >= 0) {
					try {
						aForm.setAmountFSV(CurrencyManager.convertToString(locale, iVal.getFSV()));
					}
					catch (Exception ex) {
						throw new MapperException("failed to covertfsv to display value", ex);
					}
				}
			}

			/*if (objICol.getMargin() != ICMSConstant.DOUBLE_INVALID_VALUE) {
				aForm.setMargin(String.valueOf((int) (objICol.getMargin() * 100)));
			}
			else {
				ICollateralParameter parameter = null;
				try {
					parameter = CollateralProxyFactory.getProxy().getCollateralParameter(aForm.getCollateralLoc(),
							objICol.getSCISubTypeValue());
				}
				catch (CollateralException ex) {
					DefaultLogger.warn("failed to retrieve collateral parameter, for collateral country ["
							+ aForm.getCollateralLoc() + "], subtype [" + objICol.getSCISubTypeValue() + "]", ex);
				}
				if (parameter != null) {
					aForm.setMargin(String.valueOf(parameter.getThresholdPercent()));
				}
			}*/
			aForm.setMargin(Double.toString(objICol.getMargin()));
			aForm.setReservePriceDate(DateUtil.formatDate(locale, iVal.getReservePriceDate()));
			aForm.setLastValDate(DateUtil.formatDate(locale, iVal.getLastEvaluationDate()));
		}
		return aForm;
	}

	private ICollateral formToOBSaveValuation(ICollateral iCollateral, CollateralForm colForm, Locale locale,
			HashMap inputs, boolean isCurrencyChange) throws Exception {

        //************ Start of Lines added & commented by Dattatray Thorat *************
		
		/*String valuer = colForm.getValuerInGCMS();
		String valDate = colForm.getValDate();
		String currency = colForm.getValCurrency();
		String haircut = colForm.getMargin();
		String valOmv = colForm.getAmountCMV();
        String valType = colForm.getValuationType();*/
		
		String valuer = "CMS";
		String valDate = "2011-02-11 00:00:00.0";
		String currency = "MYR";
		String haircut = "5";
		String valOmv = "1000000";
        String valType = "COL";
        //************ End of Lines added & commented by Dattatray Thorat *************        

		if (CommonUtil.isEmpty(valuer) && CommonUtil.isEmpty(valDate) && CommonUtil.isEmpty(currency)
				&& CommonUtil.isEmpty(haircut) && CommonUtil.isEmpty(valOmv) && CommonUtil.isEmpty(valType)) {
			if (iCollateral.getValuationIntoCMS() != null) {
				// remove if it no value now but have value before
				iCollateral.setValuationIntoCMS(null);
			}
			return iCollateral;
		}

		// if all the 3 fields are there
		if (!CommonUtil.isEmpty(valDate) && !CommonUtil.isEmpty(currency) && !CommonUtil.isEmpty(valOmv)) {
			return formToOBUpdateValuationIntoCMS(iCollateral, colForm, locale, inputs, isCurrencyChange);
		}

		IValuation manualVal = iCollateral.getValuationIntoCMS();
		if (manualVal == null) {
			manualVal = new OBValuation();

		}

		manualVal.setValuerName(valuer);
		manualVal.setSourceId(ICMSConstant.SOURCE_SYSTEM_CMS);
		//manualVal.setSourceType(ICMSConstant.VALUATION_SOURCE_TYPE_M);
		manualVal.setSourceType(ICMSConstant.VALUATION_SOURCE_TYPE_A);

		if (!CommonUtil.isEmpty(valDate)) {
			//manualVal.setValuationDate(DateUtil.convertDate(locale, valDate.trim()));
			manualVal.setValuationDate(new Date());
		}
		else {
			//manualVal.setValuationDate(null);
			manualVal.setValuationDate(new Date());
		}

		/*if (!CommonUtil.isEmpty(haircut)) {
			//iCollateral.setMargin(MapperUtil.mapStringToDouble(colForm.getMargin(), locale) / 100);
			iCollateral.setMargin(MapperUtil.mapStringToDouble("1000", locale) / 100);
		}
		else {
			iCollateral.setMargin(ICMSConstant.DOUBLE_INVALID_VALUE);
		}*/

		if (!CommonUtil.isEmpty(valOmv) && !CommonUtil.isEmpty(currency)) {
			manualVal.setCurrencyCode(currency);
			manualVal.setCMV(UIUtil.mapFormString_OBAmount(locale, currency, valOmv, manualVal.getCMV()));
		}
		else {
			manualVal.setCMV(null);
		}

        if (!CommonUtil.isEmpty(valType) ) {
			manualVal.setValuationType(valType);      
		}
		else {
			manualVal.setValuationType(null);
		}
        
		iCollateral.setValuationIntoCMS(manualVal);
		return iCollateral;
	}
}