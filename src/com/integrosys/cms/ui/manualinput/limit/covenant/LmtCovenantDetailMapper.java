package com.integrosys.cms.ui.manualinput.limit.covenant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.Constants;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitCovenant;
import com.integrosys.cms.app.limit.bus.OBLimitCovenant;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class LmtCovenantDetailMapper extends AbstractCommonMapper {
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");

		public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
			Locale locale = (Locale) inputs.get(Constants.GLOBAL_LOCALE_KEY);
			
			LmtCovenantDetailForm form = (LmtCovenantDetailForm) cForm;
			
			ILimitCovenant covenantDetail = new OBLimitCovenant();
			
			//covenantDetail.setLineId(form.getLineNo());
			covenantDetail.setCovenantReqd(form.getCovenantRequired());
			covenantDetail.setCountryRestrictionReqd(form.getCountryRestriction());
			covenantDetail.setDrawerReqd(form.getDrawerRestriction());
			covenantDetail.setDraweeReqd(form.getDraweeRestriction());
			covenantDetail.setBeneficiaryReqd(form.getBeneRestriction());
			covenantDetail.setCombinedTenorReqd(form.getCombinedTenorRestriction());
			covenantDetail.setBankRestrictionReqd(form.getBankRestriction());
			covenantDetail.setCurrencyRestrictionReqd(form.getCurrencyRestriction());
			covenantDetail.setRunningAccountReqd(form.getRunningAccountRestriction());
			covenantDetail.setSellDownReqd(form.getSellDownRestriction());
			covenantDetail.setLastAvailableDateReqd(form.getAvailDateRestriction());
			covenantDetail.setMoratoriumReqd(form.getMoratoriumRestriction());
			covenantDetail.setGoodsRestrictionReqd(form.getGoodsRestriction());
			covenantDetail.setEcgcCoverReqd(form.getEcgcCoverRestriction());
			covenantDetail.setBuyersRatingReqd(form.getBuyersRatingRestriction());
			covenantDetail.setRestrictedCountryname(form.getCountryRestrictionName());
			covenantDetail.setRestrictedAmount(form.getCountryRestrictionAmt());
			covenantDetail.setDrawerName(form.getDrawerName());
			covenantDetail.setDrawerAmount(form.getDrawerAmount());
			covenantDetail.setDrawerCustId(form.getDrawerCustId());
			covenantDetail.setDrawerCustName(form.getDrawerCustName());
			covenantDetail.setDraweeName(form.getDraweeName());
			covenantDetail.setDraweeAmount(form.getDraweeAmount());
			covenantDetail.setDraweeCustId(form.getDraweeCustId());
			covenantDetail.setDraweeCustName(form.getDraweeCustName());
			covenantDetail.setBeneName(form.getBeneficiaryName());
			covenantDetail.setBeneAmount(form.getBeneficiaryAmount());
			covenantDetail.setBeneCustId(form.getBeneficiaryCustId());
			covenantDetail.setBeneCustName(form.getBeneficiaryCustName());
			covenantDetail.setMaxCombinedTenor(form.getMaxCombinedTenor());
			covenantDetail.setPreshipmentLinkage(form.getPreShipmentLinkage());
			covenantDetail.setPostShipmentLinkage(form.getPostShipmentLinkage());
			covenantDetail.setRunningAccount(form.getRunningAccount());
			covenantDetail.setOrderBackedbylc(form.getOrderBackedByLC());
			covenantDetail.setIncoTerm(form.getIncoTerm());
			covenantDetail.setIncoTermMarginPercent(form.getIncoMargin());
			covenantDetail.setIncoTermDesc(form.getIncoDescription());
			covenantDetail.setModuleCode(form.getModuleCode());
			covenantDetail.setCommitmentTenor(form.getCommitmentTenor());
			covenantDetail.setSellDown(form.getSellDown());
			try {
				if (!isEmptyOrNull(form.getLastAvailableDate())) {
				covenantDetail.setLastAvailableDate(sdf.parse(form.getLastAvailableDate()));
				}
			} catch (ParseException e) {
				DefaultLogger.debug(this,"getting exception in parsing date "+e.getMessage());
				e.printStackTrace();
			}
			covenantDetail.setMoratoriumPeriod(form.getMoratorium());
			covenantDetail.setEmiFrequency(form.getEmiFrequency());
			covenantDetail.setNoOfInstallments(form.getNoOfInsallments());
			covenantDetail.setRestrictedCurrency(form.getCurrencyRestrictionCode());
			covenantDetail.setRestrictedCurrencyAmount(form.getCurrencyRestrictionAmount());
			covenantDetail.setRestrictedBank(form.getBankRestrictionCode());
			covenantDetail.setRestrictedBankAmount(form.getBankRestrictionAmount());
			covenantDetail.setBuyersRating(form.getBuyersRating());
			covenantDetail.setAgencyMaster(form.getAgencyMasterCode());
			covenantDetail.setRatingMaster(form.getRatingMasterCode());
			covenantDetail.setEcgcCover(form.getEcgcCover());
			covenantDetail.setGoodsRestrictionCode(form.getGoodsRestrictionCode());
			covenantDetail.setGoodsRestrictionParentCode(form.getGoodsRestrictionParentCode());
			
			
			return covenantDetail;
		}

		public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
			
			LmtCovenantDetailForm form = (LmtCovenantDetailForm) cForm;
			ILimitCovenant covenantDetail = (ILimitCovenant) obj;
			
			/*if(!isEmptyOrNull(covenantDetail.getLineId())) {
				form.setLineNo(covenantDetail.getLineId());
			}*/
			if(!isEmptyOrNull(covenantDetail.getCountryRestrictionReqd())) {
				form.setCountryRestriction(covenantDetail.getCountryRestrictionReqd());
			}
			if(!isEmptyOrNull(covenantDetail.getCovenantReqd())) {
				form.setCovenantRequired(covenantDetail.getCovenantReqd());
			}
			if(!isEmptyOrNull(covenantDetail.getCurrencyRestrictionReqd())) {
				form.setCurrencyRestriction(covenantDetail.getCurrencyRestrictionReqd());
			}
			if(!isEmptyOrNull(covenantDetail.getBankRestrictionReqd())) {
				form.setBankRestriction(covenantDetail.getBankRestrictionReqd());
			}
			if(!isEmptyOrNull(covenantDetail.getBeneficiaryReqd())) {
				form.setBeneRestriction(covenantDetail.getBeneficiaryReqd());
			}
			if(!isEmptyOrNull(covenantDetail.getDraweeReqd())) {
				form.setDraweeRestriction(covenantDetail.getDraweeReqd());
			}
			if(!isEmptyOrNull(covenantDetail.getDrawerReqd())) {
				form.setDrawerRestriction(covenantDetail.getDrawerReqd());
			}
			if(!isEmptyOrNull(covenantDetail.getCombinedTenorReqd())) {
				form.setCombinedTenorRestriction(covenantDetail.getCombinedTenorReqd());
			}
			if(!isEmptyOrNull(covenantDetail.getMoratoriumReqd())) {
				form.setMoratoriumRestriction(covenantDetail.getMoratoriumReqd());
			}
			if(!isEmptyOrNull(covenantDetail.getGoodsRestrictionReqd())) {
				form.setGoodsRestriction(covenantDetail.getGoodsRestrictionReqd());
			}
			
			if(!isEmptyOrNull(covenantDetail.getSellDownReqd())) {
				form.setSellDownRestriction(covenantDetail.getSellDownReqd());
			}
			
			if(!isEmptyOrNull(covenantDetail.getLastAvailableDateReqd())) {
				form.setAvailDateRestriction(covenantDetail.getLastAvailableDateReqd());
			}
			
			if(!isEmptyOrNull(covenantDetail.getEcgcCoverReqd())) {
				form.setEcgcCoverRestriction(covenantDetail.getEcgcCoverReqd());
			}
			if(!isEmptyOrNull(covenantDetail.getBuyersRatingReqd())) {
				form.setBuyersRatingRestriction(covenantDetail.getBuyersRatingReqd());
			}
			if(!isEmptyOrNull(covenantDetail.getRunningAccountReqd())) {
				form.setRunningAccountRestriction(covenantDetail.getRunningAccountReqd());
			}
			if(!isEmptyOrNull(covenantDetail.getRestrictedCountryname())) {
				form.setCountryRestrictionName(null);
			}
			if(!isEmptyOrNull(covenantDetail.getRestrictedAmount())) {
				form.setCountryRestrictionAmt(covenantDetail.getRestrictedAmount());
			}
			if(!isEmptyOrNull(covenantDetail.getDrawerName())) {
				form.setDrawerName(covenantDetail.getDrawerName());
			}
			if(!isEmptyOrNull(covenantDetail.getDrawerAmount())) {
				form.setDrawerAmount(covenantDetail.getDrawerAmount());
			}
			if(!isEmptyOrNull(covenantDetail.getDrawerCustId())) {
				form.setDrawerCustId(covenantDetail.getDrawerCustId());
			}
			if(!isEmptyOrNull(covenantDetail.getDrawerCustName())) {
				form.setDrawerCustName(covenantDetail.getDrawerCustName());
			}
			if(!isEmptyOrNull(covenantDetail.getDraweeName())) {
				form.setDraweeName(covenantDetail.getDraweeName());
			}
			if(!isEmptyOrNull(covenantDetail.getDraweeAmount())) {
				form.setDraweeAmount(covenantDetail.getDraweeAmount());
			}
			if(!isEmptyOrNull(covenantDetail.getDraweeCustId())) {
				form.setDraweeCustId(covenantDetail.getDraweeCustId());
			}
			if(!isEmptyOrNull(covenantDetail.getDraweeCustName())) {
				form.setDraweeCustName(covenantDetail.getDraweeCustName());
			}
			if(!isEmptyOrNull(covenantDetail.getBeneName())) {
				form.setBeneficiaryName(covenantDetail.getBeneName());
			}
			if(!isEmptyOrNull(covenantDetail.getBeneAmount())) {
				form.setBeneficiaryAmount(covenantDetail.getBeneAmount());
			}
			if(!isEmptyOrNull(covenantDetail.getBeneCustId())) {
				form.setBeneficiaryCustId(covenantDetail.getBeneCustId());
			}
			if(!isEmptyOrNull(covenantDetail.getBeneCustName())) {
				form.setBeneficiaryCustName(covenantDetail.getBeneCustName());
			}
			if(!isEmptyOrNull(covenantDetail.getMaxCombinedTenor())) {
				form.setMaxCombinedTenor(covenantDetail.getMaxCombinedTenor());
			}
			if(!isEmptyOrNull(covenantDetail.getPreshipmentLinkage())) {
				form.setPreShipmentLinkage(covenantDetail.getPreshipmentLinkage());
			}
			if(!isEmptyOrNull(covenantDetail.getPostShipmentLinkage())) {
				form.setPostShipmentLinkage(covenantDetail.getPostShipmentLinkage());
			}
			if(!isEmptyOrNull(covenantDetail.getRunningAccount())) {
				form.setRunningAccount(covenantDetail.getRunningAccount());
			}
			if(!isEmptyOrNull(covenantDetail.getOrderBackedbylc())) {
				form.setOrderBackedByLC(covenantDetail.getOrderBackedbylc());
			}
			if(!isEmptyOrNull(covenantDetail.getIncoTerm())) {
				form.setIncoTerm(covenantDetail.getIncoTerm());
			}
			if(!isEmptyOrNull(covenantDetail.getIncoTermDesc())) {
				form.setIncoDescription(covenantDetail.getIncoTermDesc());
			}
			if(!isEmptyOrNull(covenantDetail.getIncoTermMarginPercent())) {
				form.setIncoMargin(covenantDetail.getIncoTermMarginPercent());
			}
			if(!isEmptyOrNull(covenantDetail.getModuleCode())) {
				form.setModuleCode(covenantDetail.getModuleCode());
			}
			if(!isEmptyOrNull(covenantDetail.getCommitmentTenor())) {
				form.setCommitmentTenor(covenantDetail.getCommitmentTenor());
			}
			if(!isEmptyOrNull(covenantDetail.getSellDown())) {
				form.setSellDown(covenantDetail.getSellDown());
			}
			
			try {
				if (covenantDetail.getLastAvailableDate()!=null) {
					form.setLastAvailableDate(sdf.format(covenantDetail.getLastAvailableDate()));
				}
			} catch (Exception e) {
				DefaultLogger.debug(this,"getting exception in parsing date "+e.getMessage());
				e.printStackTrace();
			}
			if(!isEmptyOrNull(covenantDetail.getMoratoriumPeriod())) {
				form.setMoratorium(covenantDetail.getMoratoriumPeriod());
			}
			if(!isEmptyOrNull(covenantDetail.getNoOfInstallments())) {
				form.setNoOfInsallments(covenantDetail.getNoOfInstallments());
			}
			if(!isEmptyOrNull(covenantDetail.getEmiFrequency())) {
				form.setEmiFrequency(covenantDetail.getEmiFrequency());
			}
			if(!isEmptyOrNull(covenantDetail.getRestrictedCurrencyAmount())) {
				form.setCurrencyRestrictionAmount(covenantDetail.getRestrictedCurrencyAmount());
			} 
			if(!isEmptyOrNull(covenantDetail.getRestrictedCurrency())) {
				form.setCurrencyRestrictionCode(null);
			}
			if(!isEmptyOrNull(covenantDetail.getRestrictedBank())) {
				form.setBankRestrictionCode(null);
			}
			if(!isEmptyOrNull(covenantDetail.getRestrictedBankAmount())) {
				form.setBankRestrictionAmount(covenantDetail.getRestrictedBankAmount());
			}
			if(!isEmptyOrNull(covenantDetail.getBuyersRating())) {
				form.setBuyersRating(covenantDetail.getBuyersRating());
			}
			if(!isEmptyOrNull(covenantDetail.getAgencyMaster())) {
				form.setAgencyMasterCode(covenantDetail.getAgencyMaster());
			}
			if(!isEmptyOrNull(covenantDetail.getRatingMaster())) {
				form.setRatingMasterCode(covenantDetail.getRatingMaster());
			}
			if(!isEmptyOrNull(covenantDetail.getEcgcCover())) {
				form.setEcgcCover(covenantDetail.getEcgcCover());
			}
			
			if(StringUtils.isNotBlank(covenantDetail.getGoodsRestrictionCode())) {
				form.setGoodsRestrictionCode(covenantDetail.getGoodsRestrictionCode());
			}
			if(StringUtils.isNotBlank(covenantDetail.getGoodsRestrictionParentCode())) {
				form.setGoodsRestrictionParentCode(covenantDetail.getGoodsRestrictionParentCode());
			}
			
			form.setCovenantId(String.valueOf(covenantDetail.getCovenantId()));
			
			return form;
		}
		
		
		
		public String[][] getParameterDescriptor() {
			DefaultLogger.debug(this,
					"Entering getParameterDescriptor of LmtCovenantDetailMapper");
			return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile", GLOBAL_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "itemType", "java.lang.String", REQUEST_SCOPE }, });
		}
}
