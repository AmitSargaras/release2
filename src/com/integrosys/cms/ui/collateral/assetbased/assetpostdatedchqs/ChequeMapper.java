package com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.base.uiinfra.mapper.MapperUtil;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IAssetPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.OBPostDatedCheque;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.25 $
 * @since $Date: 2005/08/26 10:12:37 $ Tag: $Name: $
 */

public class ChequeMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		Date stageDate;
		ChequeForm aForm = (ChequeForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		IAssetPostDatedCheque iAsset = (IAssetPostDatedCheque) (((ICollateralTrxValue) inputs.get("serviceColObj"))
				.getStagingCollateral());
		IPostDatedCheque[] obCheque = iAsset.getPostDatedCheques();

		DefaultLogger.debug(this, "indexID  is:" + inputs.get("indexID") + ":");

		int index = Integer.parseInt((String) inputs.get("indexID"));

		IPostDatedCheque obToChange = null;
		if (index == -1) {
			obToChange = new OBPostDatedCheque();

		}
		else {
			obToChange = obCheque[index];
		}
     	try {
				
			obToChange.setPacketNumber(aForm.getPacketNumber());
			obToChange.setDraweeBank(aForm.getDraweeBank());
			obToChange.setIssuerName(aForm.getBankBranchType());
			obToChange.setChequeNumber(aForm.getChequeNumber());
			obToChange.setReturnStatus(aForm.getReturnStatus());
			obToChange.setReturnDate(DateUtil.convertDate(locale, aForm.getReturnDate()));
			obToChange.setRemarks(aForm.getRemarks());
			
			obToChange.setBankCode(aForm.getBankCode());
			
			obToChange.setBranchCode(aForm.getBranchCode());
			
			obToChange.setLoanable(aForm.getLoanable());
			obToChange.setBankName(aForm.getBankName());
			
			obToChange.setBranchName(aForm.getBranchName());
			if(aForm.getChequeNoFrom()!=null && !aForm.getChequeNoFrom().equals("")){
			obToChange.setChequeNoFrom(Long.parseLong(aForm.getChequeNoFrom()));
			}
			if(aForm.getChequeNoTo()!=null && !aForm.getChequeNoTo().equals("")){
			obToChange.setChequeNoTo(Long.parseLong(aForm.getChequeNoTo()));
			}
			
			obToChange.setBulkSingle(aForm.getBulkSingle());
			if (isEmptyOrNull(aForm.getChequeAmt())) {
				obToChange.setChequeAmount(null);
			}
			else if (!isEmptyOrNull(aForm.getChequeAmt())) {
				
				obToChange.setChequeAmount(new Amount(new BigDecimal(UIUtil.removeComma(aForm.getChequeAmt())),new CurrencyCode("INR"))); 
						//CurrencyManager.convertToAmount(locale, "INR",aForm.getChequeAmt()));
			
			
			}
			
			if (!isEmptyOrNull(aForm.getIssueDate())) {
				stageDate = CollateralMapper.compareDate(locale, obToChange.getIssueDate(), aForm.getIssueDate());
				obToChange.setIssueDate(stageDate);
			}
			else {
				obToChange.setIssueDate(null);
			}
			if (!isEmptyOrNull(aForm.getExpiryDate())) {
				stageDate = CollateralMapper.compareDate(locale, obToChange.getExpiryDate(), aForm.getExpiryDate());
				obToChange.setExpiryDate(stageDate);
			}
			else {
				obToChange.setExpiryDate(null);
			}
			
			if (!isEmptyOrNull(aForm.getStartDate())) {
				stageDate = CollateralMapper.compareDate(locale, obToChange.getStartDate(), aForm.getStartDate());
				obToChange.setStartDate(stageDate);
			}
			else {
				obToChange.setStartDate(null);
			}
			
			if (!isEmptyOrNull(aForm.getMaturityDate())) {
				stageDate = CollateralMapper.compareDate(locale, obToChange.getMaturityDate(), aForm.getMaturityDate());
				obToChange.setMaturityDate(stageDate);
			}
			else {
				obToChange.setMaturityDate(null);
			}
			
			 obToChange.setRamId(aForm.getRamId());

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		IPostDatedCheque iCheque = (IPostDatedCheque) obj;
		ChequeForm aForm = (ChequeForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		String strAmt;
		Amount amt;
		DefaultLogger.debug(this, "inside mapOBToForm aForm is :" + aForm);

		try {
			
			aForm.setExchangeControl(iCheque.getIsExchangeCtrlObtained());
			aForm.setExchangeControlDate(DateUtil.formatDate(locale, iCheque.getExchangeCtrlDate()));
			
			aForm.setChequeCurrency(iCheque.getChequeCcyCode());
			amt = iCheque.getChequeAmount();
			if ((amt != null) && (amt.getCurrencyCode() != null)) {
				if (amt.getAmount() >= 0) {
					aForm.setChequeAmt(CurrencyManager.convertToString(locale, amt));
				}
			}
			if (iCheque.getMargin() * 100 >= 0) {
				aForm.setMargin(String.valueOf((int) (iCheque.getMargin() * 100)));
			}
			if ((amt != null) && (amt.getCurrencyCode() != null)) {
				if (amt.getAmount() >= 0) {
					aForm.setBefValMargin(CurrencyManager.convertToString(locale, amt));
					if (iCheque.getMargin() * 100 >= 0) {
						double afterMargin = amt.getAmount() * iCheque.getMargin();
						strAmt = CurrencyManager
								.convertToString(locale, new Amount(afterMargin, amt.getCurrencyCode()));
						DefaultLogger.debug(this, "after margin is: " + strAmt);
						aForm.setAftValMargin(strAmt);
					}
				}
			}

			aForm.setPacketNumber(iCheque.getPacketNumber());
			aForm.setChequeNumber(iCheque.getChequeNumber());
			aForm.setReturnStatus(iCheque.getReturnStatus());
			aForm.setReturnDate(DateUtil.formatDate(locale,iCheque.getReturnDate()));
			aForm.setDraweeBank(iCheque.getDraweeBank());
			OBPostDatedCheque post= (OBPostDatedCheque)iCheque;
			if(post.getOtherbank()!=null){
			aForm.setBankName(post.getOtherbank().getOtherBankName());
			}
			if("S".equals(post.getDraweeBank()))
			{
			if(null != post.getSystemBank()){
				aForm.setBankName(post.getSystemBank().getSystemBankName());
				}
				
				if(null != post.getSystemBankBranch()){
					aForm.setBranchName(post.getSystemBankBranch().getSystemBankBranchName());
					}
			}
			if("O".equals(post.getDraweeBank()))
			{
			if(null != post.getOtherbranch()){
				aForm.setBranchName(post.getOtherbranch().getOtherBranchName());
				}
			}
			
			
			
			if(iCheque.getChequeAmount()!=null){
				//Phase 3 CR:comma separated
			aForm.setChequeAmt(UIUtil.formatWithCommaAndDecimal(String.valueOf(iCheque.getChequeAmount().getAmountAsBigDecimal())));
			}
			aForm.setRemarks(iCheque.getRemarks());
			aForm.setBankCode(iCheque.getBankCode());
			aForm.setBranchCode(iCheque.getBranchCode());
			aForm.setLoanable(iCheque.getLoanable());
			aForm.setChequeNoFrom(String.valueOf(iCheque.getChequeNoFrom()));
			aForm.setChequeNoTo(String.valueOf(iCheque.getChequeNoTo()));
			if(iCheque.getBulkSingle()!=null){
			aForm.setBulkSingle(iCheque.getBulkSingle());
			}else{
				aForm.setBulkSingle("");
			}
	    	aForm.setIssueDate(DateUtil.formatDate(locale, iCheque.getIssueDate()));
			aForm.setExpiryDate(DateUtil.formatDate(locale, iCheque.getExpiryDate()));
			
			aForm.setStartDate(DateUtil.formatDate(locale, iCheque.getStartDate()));
			aForm.setMaturityDate(DateUtil.formatDate(locale, iCheque.getMaturityDate()));
			aForm.setRamId(iCheque.getRamId());

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}
		DefaultLogger.debug(this, " aForm is :" + aForm);
		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "serviceColObj", "java.lang.Object", SERVICE_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, });
	}

	
}
