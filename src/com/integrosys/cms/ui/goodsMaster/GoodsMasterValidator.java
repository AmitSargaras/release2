package com.integrosys.cms.ui.goodsMaster;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;


public class GoodsMasterValidator {

	public static ActionErrors validateInput(ActionForm commonform,Locale locale){
		ActionErrors errors = new ActionErrors();
		GoodsMasterForm form=(GoodsMasterForm)commonform;
		String errorCode = null;
		if(form.getEvent().equalsIgnoreCase("maker_create_goods_master")|| form.getEvent().equalsIgnoreCase("maker_save_update")||
				form.getEvent().equalsIgnoreCase("maker_draft_goods_master")
				||form.getEvent().equalsIgnoreCase("maker_confirm_resubmit_delete")){
			
			if(null==form.getGoodsCode()||form.getGoodsCode().trim().equals("")){
				errors.add("goodsCode", new ActionMessage("error.string.mandatory"));			
			}else {
				int lengthChild = form.getGoodsCode().length();

				if (!Validator.ERROR_NONE.equals(errorCode = Validator.checkNumber(form.getGoodsCode(), false, 1000, 99999999))){
					errors.add("goodsCodeInvalidCharacterError", new ActionMessage("error.string.invalidCharacterForGoods"));
				}else {
					if(null!=form.getGoodsParentCode()||!form.getGoodsParentCode().trim().equals("")){
						int lengthParent = form.getGoodsParentCode().length();
						
						if(lengthParent==4) {
							if(lengthChild!=6) {
								errors.add("goodsCode", new ActionMessage("error.string.length.goodsCode6"));
							}
						}else if(lengthParent==6) {
							if(lengthChild!=8) {
								errors.add("goodsCode", new ActionMessage("error.string.length.goodsCode8"));
							}
						}else if(lengthChild!=4) {
							errors.add("goodsCode", new ActionMessage("error.string.length.goodsCode"));
						}
					}
			}
			
		}
		
			if(null==form.getGoodsName()||form.getGoodsName().trim().equals("")){
				errors.add("goodsName", new ActionMessage("error.string.mandatory"));			
			}
			if (null!=form.getGoodsName() && form.getGoodsName().length() > 50){
				errors.add("goodsNameLengthError", new ActionMessage("error.string.length.goodsName"));
			}
			if(null==form.getRestrictionType()||form.getRestrictionType().trim().equals("")){
				errors.add("restrictionType", new ActionMessage("error.string.mandatory"));			
			}
		}	
		
		
		if(form.getEvent().equalsIgnoreCase("maker_edit_goods_master")||form.getEvent().equalsIgnoreCase("maker_update_draft_goods_master")){
		
			if(null==form.getGoodsName()||form.getGoodsName().trim().equals("")){
				errors.add("goodsName", new ActionMessage("error.string.mandatory"));			
			}
			if (null!=form.getGoodsName() && form.getGoodsName().length() > 50){
				errors.add("goodsNameLengthError", new ActionMessage("error.string.length.goodsName"));
			}
			if(null==form.getRestrictionType()||form.getRestrictionType().trim().equals("")){
				errors.add("restrictionType", new ActionMessage("error.string.mandatory"));			
			}
		}	
		return errors;
	}
}
