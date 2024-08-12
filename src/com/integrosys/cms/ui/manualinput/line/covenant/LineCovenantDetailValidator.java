package com.integrosys.cms.ui.manualinput.line.covenant;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class LineCovenantDetailValidator {
	public static ActionErrors validateInput(LineCovenantDetailForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = null;
		
		if(form.getCovenantRequired()==null || "".equals(form.getCovenantRequired())) {
			errors.add("covenantRequired", new ActionMessage("error.string.mandatory"));
		}
		
		if(form.getCountryRestriction()!=null && "Y".equalsIgnoreCase(form.getCountryRestriction())) {
			if(form.getCountryListEmpty()!=null && "Y".equalsIgnoreCase(form.getCountryListEmpty())) {
			if(AbstractCommonMapper.isEmptyOrNull(form.getCountryRestrictionName())) {
				errors.add("countryRestrictionName", new ActionMessage("error.string.mandatory"));
			}
			if(AbstractCommonMapper.isEmptyOrNull(form.getCountryRestrictionAmt())) {
				errors.add("countryRestrictionAmt", new ActionMessage("error.string.mandatory"));
			}
		}
		}
		
		if(form.getDrawerRestriction()!=null && "Y".equalsIgnoreCase(form.getDrawerRestriction())) {
			if(form.getDrawerListEmpty()!=null && "Y".equalsIgnoreCase(form.getDrawerListEmpty())) {
			/*if(AbstractCommonMapper.isEmptyOrNull(form.getDrawerName())) {
				errors.add("drawerName", new ActionMessage("error.string.mandatory"));
			}*/
			if(AbstractCommonMapper.isEmptyOrNull(form.getDrawerAmount())) {
				errors.add("drawerAmount", new ActionMessage("error.string.mandatory"));
			}
			if(AbstractCommonMapper.isEmptyOrNull(form.getDrawerCustId())) {
				errors.add("drawerCustId", new ActionMessage("error.string.mandatory"));
			}
			/*if(AbstractCommonMapper.isEmptyOrNull(form.getDrawerCustName())) {
				errors.add("drawerCustName", new ActionMessage("error.string.mandatory"));
			}*/
			if (!(errorCode = Validator.checkNumber(form.getDrawerAmount(), true, 0, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10)).equals(Validator.ERROR_NONE)) {
				errors.add("drawerAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10_STR));
			}
		}
		}
		
		if(form.getDraweeRestriction()!=null && "Y".equalsIgnoreCase(form.getDraweeRestriction())) {
			if(form.getDraweeListEmpty()!=null && "Y".equalsIgnoreCase(form.getDraweeListEmpty())) {
			/*if(AbstractCommonMapper.isEmptyOrNull(form.getDraweeName())) {
				errors.add("draweeName", new ActionMessage("error.string.mandatory"));
			}*/
			if(AbstractCommonMapper.isEmptyOrNull(form.getDraweeAmount())) {
				errors.add("draweeAmount", new ActionMessage("error.string.mandatory"));
			}
			if(AbstractCommonMapper.isEmptyOrNull(form.getDraweeCustId())) {
				errors.add("draweeCustId", new ActionMessage("error.string.mandatory"));
			}
			/*if(AbstractCommonMapper.isEmptyOrNull(form.getDraweeCustName())) {
				errors.add("draweeCustName", new ActionMessage("error.string.mandatory"));
			}*/
			if (!(errorCode = Validator.checkNumber(form.getDraweeAmount(), true, 0, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10)).equals(Validator.ERROR_NONE)) {
				errors.add("draweeAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10_STR));
			}
		}
		}
		if(form.getBeneRestriction()!=null && "Y".equalsIgnoreCase(form.getBeneRestriction())) {
			if(form.getBeneListEmpty()!=null && "Y".equalsIgnoreCase( form.getBeneListEmpty())) {
			/*if(AbstractCommonMapper.isEmptyOrNull(form.getBeneficiaryName())) {
				errors.add("beneficiaryName", new ActionMessage("error.string.mandatory"));
			}*/
			if(AbstractCommonMapper.isEmptyOrNull(form.getBeneficiaryAmount())) {
				errors.add("beneficiaryAmount", new ActionMessage("error.string.mandatory"));
			}
			if(AbstractCommonMapper.isEmptyOrNull(form.getBeneficiaryCustId())) {
				errors.add("beneficiaryCustId", new ActionMessage("error.string.mandatory"));
			}
			/*if(AbstractCommonMapper.isEmptyOrNull(form.getBeneficiaryCustName())) {
				errors.add("beneficiaryCustName", new ActionMessage("error.string.mandatory"));
			}*/
			if (!(errorCode = Validator.checkNumber(form.getBeneficiaryAmount(), true, 0, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10)).equals(Validator.ERROR_NONE)) {
				errors.add("beneficiaryAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10_STR));
			}
		}
		}
		
		if(form.getCombinedTenorRestriction()!=null && "Y".equalsIgnoreCase(form.getCombinedTenorRestriction())) {
			//boolean flag=false;
			if(AbstractCommonMapper.isEmptyOrNull(form.getMaxCombinedTenor())) {
				errors.add("maxCombinedTenor", new ActionMessage("error.string.mandatory"));
			}
			if(AbstractCommonMapper.isEmptyOrNull(form.getPostShipmentLinkage())) {
				errors.add("postShipmentLinkage", new ActionMessage("error.string.mandatory"));
			}
			if(AbstractCommonMapper.isEmptyOrNull(form.getPreShipmentLinkage())) {
				errors.add("preShipmentLinkage", new ActionMessage("error.string.mandatory"));
			}
			if (!(errorCode = Validator.checkNumber(form.getMaxCombinedTenor(), true, 0, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10)).equals(Validator.ERROR_NONE)) {
				errors.add("maxCombinedTenor", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10_STR));
				//flag=true;
			}
			/*if(
					Double.parseDouble(UIUtil.removeComma(form.getMaxCombinedTenor()))>(100))
			{
				errors.add("maxCombinedTenor", new ActionMessage("error.string.mandatory"));
			}*/
			
			
		}
		
		if(form.getRunningAccountRestriction()!=null && "Y".equalsIgnoreCase(form.getRunningAccountRestriction())) {
			if(AbstractCommonMapper.isEmptyOrNull(form.getRunningAccount())&&AbstractCommonMapper.isEmptyOrNull(form.getOrderBackedByLC())) {
				errors.add("runningAccount", new ActionMessage("error.anyone.mandatory"));
				errors.add("orderBackedByLC", new ActionMessage("error.anyone.mandatory"));
			}
			if(AbstractCommonMapper.isEmptyOrNull(form.getIncoTerm())) {
				errors.add("incoTerm", new ActionMessage("error.string.mandatory"));
			}
			if(AbstractCommonMapper.isEmptyOrNull(form.getIncoMargin())) {
				errors.add("incoMargin", new ActionMessage("error.string.mandatory"));
			}
			if(AbstractCommonMapper.isEmptyOrNull(form.getModuleCode())) {
				errors.add("moduleCode", new ActionMessage("error.string.mandatory"));
			}
			if(AbstractCommonMapper.isEmptyOrNull(form.getIncoDescription())) {
				errors.add("incoDescription", new ActionMessage("error.string.mandatory"));
			}
			if(AbstractCommonMapper.isEmptyOrNull(form.getCommitmentTenor())) {
				errors.add("commitmentTenor", new ActionMessage("error.string.mandatory"));
			}
			if(!form.getRunningAccount().equals("") && !form.getRunningAccount().equals(""))
			{
				if("N".equalsIgnoreCase(form.getRunningAccount()) && "N".equalsIgnoreCase(form.getOrderBackedByLC()))
				{
					errors.add("runningAccount", new ActionMessage("error.value.runningAccountNotSelected"));
				}
			}
			
		}
		
		if(form.getSellDownRestriction()!=null && "Y".equalsIgnoreCase(form.getSellDownRestriction())) {
			if(AbstractCommonMapper.isEmptyOrNull(form.getSellDown())) {
				errors.add("sellDown", new ActionMessage("error.string.mandatory"));
			}
			if(!form.getSellDown().equals("") && !"".equals(form.getSellDown())) {
				if (!(errorCode = Validator.checkNumber(form.getSellDown(), true, 0, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10)).equals(Validator.ERROR_NONE)) {
					errors.add("sellDown", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10_STR));
				}
			}
		}
		
		if(form.getAvailDateRestriction()!=null && "Y".equalsIgnoreCase(form.getAvailDateRestriction())) {
			//boolean dateFlag=false;
			if(AbstractCommonMapper.isEmptyOrNull(form.getLastAvailableDate())) {
				errors.add("lastAvailableDate", new ActionMessage("error.string.mandatory"));
			}
			if(!(AbstractCommonMapper.isEmptyOrNull(form.getLastAvailableDate())))
			{
				IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
				IGeneralParamGroup generalParamGroup = generalParamDao
						.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
				IGeneralParamEntry[] generalParamEntries = generalParamGroup.getFeedEntries();
				Date applicationDate = new Date();
				for (int i = 0; i < generalParamEntries.length; i++) {
					if (generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")) {
						applicationDate = new Date(generalParamEntries[i].getParamValue());
					}
				}
				if(DateUtil.convertDate(locale, form.getLastAvailableDate()).before(applicationDate))
				{
					errors.add("lastAvailableDate", new ActionMessage("error.date.compareDate.cannotBeEarlierOrSame",
							"Last Available Date", "Application Date"));
				}
			}
			
		}
		if(form.getMoratoriumRestriction()!=null && "Y".equalsIgnoreCase(form.getMoratoriumRestriction())) {
			if(AbstractCommonMapper.isEmptyOrNull(form.getMoratorium())) {
				errors.add("moratorium", new ActionMessage("error.string.mandatory"));
			}
			if(AbstractCommonMapper.isEmptyOrNull(form.getNoOfInsallments())) {
				errors.add("noOfInsallments", new ActionMessage("error.string.mandatory"));
			}
			if(AbstractCommonMapper.isEmptyOrNull(form.getEmiFrequency())) {
				errors.add("emiFrequency", new ActionMessage("error.string.mandatory"));
			}
			if(!form.getMoratorium().equals("") && !"".equals(form.getMoratorium())) {
				if (!(errorCode = Validator.checkNumber(form.getMoratorium(), true, 0,12)).equals(Validator.ERROR_NONE)) {
					errors.add("moratorium", new ActionMessage("error.moratorium.value"));
				}
			}
			if(!form.getNoOfInsallments().equals("") && !"".equals(form.getNoOfInsallments())) {
				if (!(errorCode = Validator.checkNumber(form.getNoOfInsallments(), true, 0, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10)).equals(Validator.ERROR_NONE)) {
					errors.add("noOfInsallments", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10_STR));
				}
			}
		} 
		
		if(form.getGoodsRestriction()!=null && "Y".equalsIgnoreCase(form.getGoodsRestriction())) {
			if(form.getParentGoodsListEmpty() != null && "Y".equalsIgnoreCase(form.getParentGoodsListEmpty()))
			{
			if(AbstractCommonMapper.isEmptyOrNull(form.getGoodsRestrictionCode())) {
				errors.add("goodsRestrictionCode", new ActionMessage("error.string.mandatory"));
			}
			}
		}
//		if(form.getGoodsRestriction()!=null && "Y".equalsIgnoreCase(form.getGoodsRestriction())) {
//		if(AbstractCommonMapper.isEmptyOrNull(form.getGoodsRestrictionCode())) {
//			errors.add("goodsRestrictionCode", new ActionMessage("error.string.mandatory"));
//		}
//	}
		if(form.getCurrencyRestrictions()!=null && "Y".equalsIgnoreCase(form.getCurrencyRestrictions())) {
			if(form.getCurrencyListEmpty()!=null && "Y".equalsIgnoreCase( form.getCurrencyListEmpty())) {
			if(AbstractCommonMapper.isEmptyOrNull(form.getCurrencyRestrictionCode())) {
				errors.add("currencyRestrictionCode", new ActionMessage("error.string.mandatory"));
			}
			}
		}
		if(form.getBankRestriction()!=null && "Y".equalsIgnoreCase(form.getBankRestriction())) {
			if(form.getBankListEmpty()!=null && "Y".equalsIgnoreCase( form.getBankListEmpty())) {
			if(AbstractCommonMapper.isEmptyOrNull(form.getBankRestrictionCode())) {
				errors.add("bankRestrictionCode", new ActionMessage("error.string.mandatory"));
			}
		}
		}
		
		if(form.getBuyersRatingRestriction()!=null && "Y".equalsIgnoreCase(form.getBuyersRatingRestriction())) {
			if(AbstractCommonMapper.isEmptyOrNull(form.getBuyersRating())) {
				errors.add("buyersRating", new ActionMessage("error.string.mandatory"));
			}
			if(AbstractCommonMapper.isEmptyOrNull(form.getAgencyMasterCode())) {
				errors.add("agencyMasterCode", new ActionMessage("error.string.mandatory"));
			}
			if(AbstractCommonMapper.isEmptyOrNull(form.getRatingMasterCode())) {
				errors.add("ratingMasterCode", new ActionMessage("error.string.mandatory"));
			}
			
		} 
		if(form.getEcgcCoverRestriction()!=null && "Y".equalsIgnoreCase(form.getEcgcCoverRestriction())) {
			if(AbstractCommonMapper.isEmptyOrNull(form.getEcgcCover())) {
				errors.add("ecgcCover", new ActionMessage("error.string.mandatory"));
			}
		}
		
		DefaultLogger.warn(LineCovenantDetailValidator.class.getName() ,"  errors " + errors.size());
		return errors;
	}
	
}
