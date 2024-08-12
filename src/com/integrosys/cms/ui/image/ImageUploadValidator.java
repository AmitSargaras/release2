package com.integrosys.cms.ui.image;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.image.bus.ImageUploadException;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.UIValidator;

/**
 * ImageUploadValidator class validate ImageUpload Search for corresponding
 * Customer event...
 * 
 * @author $Author: Govind S $<br>
 * @version $Revision: 0 $
 * @since $Date: 2011/02/28 03:55:48 $ Tag: $Name: $
 */
public class ImageUploadValidator {
	/**
	 * method validate Input for text field
	 * 
	 * @param aForm
	 *            is of type IImageUploadAdd
	 * @throws ImageUploadException
	 *             on errors
	 * @throws Exception
	 *             on errors
	 * @return ActionErrors
	 */
	public static ActionErrors validateInput(CommonForm aForm) throws ImageUploadException {
		ActionErrors errors = new ActionErrors();
		try {
			if ("list_image".equals(aForm.getEvent())) {
				return validateImageUploadCusSearchform((ImageUploadForm) aForm, errors);
			}
			if (ImageUploadAction.EVENT_SAVE_IMAGE_DETAILS.equals(aForm.getEvent())
					|| ImageUploadAction.EVENT_ADD_DOC_LINKAGE.equals(aForm.getEvent())
					) {
				return validateImageUploadDetails((ImageUploadAddForm) aForm, errors);
			}
			DefaultLogger.debug(" Total Errors:", "" + errors.size());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ImageUploadException("Unable to validateInput customer search");
		}
		return errors;

	}

	/**
	 * method validate Input for text field
	 * 
	 * @param form
	 *            is of type IImageUploadAdd
	 * @param errors
	 *            is of type ActionErrors
	 * @throws Exception
	 *             on errors
	 * @return ActionErrors
	 */
	private static ActionErrors validateImageUploadCusSearchform(ImageUploadForm form, ActionErrors errors)
			throws ImageUploadException {

		// DefaultLogger.debug("ImageUploadCusSearch ", " - Code: " +
		// form.getCustomerName());
		String errorCode = "";
		try {
			/*if (form.getGobutton().equals("1")) {

				if (!(Validator.checkString(form.getCustomerName(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
					errors.add("customerNameError", new ActionMessage("error.customerName.mandatory"));
				}
			} else if (form.getGobutton().equals("2")) {
				if (!(Validator.checkString(form.getLegalID(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
					errors.add("legalIDError", new ActionMessage("error.legalID.mandatory"));
				}
			}*/
			
			/*if("SELECTD_PARTY".equals(form.getFilterPartyName())) {
				if (!(Validator.checkString(form.getCustomerName(), true, 1, 100)).equals(Validator.ERROR_NONE)) {
					errors.add("customerNameError", new ActionMessage("error.customerName.mandatory"));
				}
			}*/
			
			if("SELECTD_PARTY".equals(form.getFilterPartyName())) {
				if (!(Validator.checkString(form.getCustomerName(), true, 1, 100)).equals(Validator.ERROR_NONE) && (form.getLegalID() == null || "".equals(form.getLegalID()))) {
					errors.add("customerNameError", new ActionMessage("error.customerName.mandatory"));
				}
			}
			
			
			
			if((form.getDocumentDateType() != null && !"".equals(form.getDocumentDateType())) || (form.getToDatedoc() != null && !"".equals(form.getToDatedoc())) || (form.getFromDateDoc() != null && !"".equals(form.getFromDateDoc()))) {
			if(form.getDocumentDateType() == null || "".equals(form.getDocumentDateType())) {
				errors.add("docDateTypeError", new ActionMessage("error.string.mandatory"));
			}
			if(form.getToDatedoc() == null || "".equals(form.getToDatedoc())) {
				errors.add("toDateError", new ActionMessage("error.string.mandatory"));
			}
			if(form.getFromDateDoc() == null || "".equals(form.getFromDateDoc())) {
				errors.add("fromDateError", new ActionMessage("error.string.mandatory"));
			}
			
			}
			
			boolean flags = false;
			
			if(form.getFromAmount() != null && !"".equals(form.getFromAmount())) {
				String str = form.getFromAmount();
				boolean isNumber=ASSTValidator.isValidDecimalNumberWithComma(str);
				if(!isNumber) {
					errors.add("fromAmountError", new ActionMessage("error.amount.format"));
					flags = true;
			}
			}
				
			if(form.getToAmount() != null && !"".equals(form.getToAmount())) {
				String str = form.getToAmount();
				boolean isNumber=ASSTValidator.isValidDecimalNumberWithComma(str);
				if(!isNumber) {
					errors.add("toAmountError", new ActionMessage("error.amount.format"));
					flags=true;
			}
			}
			
			
			if(form.getFromDateDoc() != null && !"".equals(form.getFromDateDoc()) && form.getToDatedoc() != null && !"".equals(form.getToDatedoc())) {
				
			if (!(errorCode = UIValidator.compareDateEarlier(form.getFromDateDoc(), form.getToDatedoc(), Locale.getDefault()))
							.equals(Validator.ERROR_NONE)) {
				errors.add("fromDateEarlyError", new ActionMessage("error.fromdate.early.error"));
				}
				
			}
			
			if(form.getFromAmount() != null && !"".equals(form.getFromAmount()) && form.getToAmount() != null && !"".equals(form.getToAmount())) {
				if(flags == false) {
//				int frmAmt = Integer.parseInt(form.getFromAmount());
//				int toAmt = Integer.parseInt(form.getToAmount());
				double frmAmt = Double.parseDouble(form.getFromAmount());
				double toAmt = Double.parseDouble(form.getToAmount());
				
				if(frmAmt > toAmt) {
					errors.add("fromAmountBigError", new ActionMessage("error.fromamount.big.error"));
				}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ImageUploadException("Unable to validate Image Upload CusSearch form");
		}
		return errors;
	}

	/**
	 * method validate Input for text field
	 * 
	 * @param form
	 *            is of type IImageUploadAdd
	 * @param errors
	 *            is of type ActionErrors
	 * @throws Exception
	 *             on errors
	 * @return ActionErrors
	 */
	private static ActionErrors validateImageUploadDetails(ImageUploadAddForm form, ActionErrors errors)
			throws ImageUploadException {
		// DefaultLogger.debug("Inside validateImageUploadDetails ", " - Code: " +
		// form.getCustName());
		String errorCode = "";
		try {
			
			boolean isFacilityImg = ICMSConstant.IMAGE_CATEGORY_FACILITY.equals(form.getCategory()) ||
					ICMSConstant.CODE_IMG_CATEGORY_FACILITY.equals(form.getCategory());
//			if ("Facility".equals(form.getTypeOfDocument())) {
			if (isFacilityImg) {
				if (!(Validator.checkString(form.getFacilityName(), true, 1, 200)).equals(Validator.ERROR_NONE)) {
					errors.add("facilityNameError", new ActionMessage("error.imageupload.facilityname.mandatory"));
				}
				if (!(Validator.checkString(form.getFacilityDocName(), true, 1, 200)).equals(Validator.ERROR_NONE)) {
					errors.add("facilityDocNameError",
							new ActionMessage("error.imageupload.facilitydocumentname.mandatory"));
				}
				
				if("Other Additional Document".equals(form.getFacilityDocName())) {
					if (!(Validator.checkString(form.getOtherDocName(), true, 1, 200)).equals(Validator.ERROR_NONE)) {
						errors.add("otherFacilityDocNameError",
								new ActionMessage("error.imageupload.otherfacilitydocumentname.mandatory"));
					}
				}
				
			}

			boolean isSecurityImg = ICMSConstant.IMAGE_CATEGORY_SECURITY.equals(form.getCategory()) ||
					ICMSConstant.CODE_IMG_CATEGORY_SECURITY.equals(form.getCategory());
//			if ("Security".equals(form.getTypeOfDocument())) {
			if (isSecurityImg) {
				if (!(Validator.checkString(form.getSecurityNameId(), true, 1, 200)).equals(Validator.ERROR_NONE)) {
					errors.add("securityNameIdError", new ActionMessage("error.imageupload.securityname.mandatory"));
				}
				if (!(Validator.checkString(form.getSecurityDocName(), true, 1, 200)).equals(Validator.ERROR_NONE)) {
					errors.add("securityDocNameIdError",
							new ActionMessage("error.imageupload.securitydocumentname.mandatory"));
				}
				
				if("Other Additional Document".equals(form.getSecurityDocName())) {
					if (!(Validator.checkString(form.getOtherSecDocName(), true, 1, 200)).equals(Validator.ERROR_NONE)) {
						errors.add("otherSecurityDocNameError",
								new ActionMessage("error.imageupload.othersecuritydocumentname.mandatory"));
					}
				}
			}
			
			
			boolean isCamImg = ICMSConstant.IMAGE_CATEGORY_CAM.equals(form.getCategory()) ||
					ICMSConstant.CODE_IMG_CATEGORY_CAM.equals(form.getCategory());
//			if ("Security".equals(form.getTypeOfDocument())) {
			if (isCamImg) {
				if (!(Validator.checkString(form.getCamDocName(), true, 1, 200)).equals(Validator.ERROR_NONE)) {
					errors.add("camDocNameError", new ActionMessage("error.imageupload.camdocumentname.mandatory"));
				}
				if (!(Validator.checkString(form.getHasCam(), true, 1, 200)).equals(Validator.ERROR_NONE)) {
					errors.add("camNumberError",
							new ActionMessage("error.imageupload.camnumber.mandatory"));
				}
			}
			
			boolean isOtherImg = ICMSConstant.IMAGE_CATEGORY_OTHER.equals(form.getCategory()) ||
					ICMSConstant.CODE_IMG_CATEGORY_OTHERS.equals(form.getCategory());
//			if ("Security".equals(form.getTypeOfDocument())) {
			if (isOtherImg) {
				if (!(Validator.checkString(form.getOthersDocsName(), true, 1, 200)).equals(Validator.ERROR_NONE)) {
					errors.add("otherMasterError", new ActionMessage("error.imageupload.otherocumentname.mandatory"));
				}
				
			}
			
			boolean isExchangeOfInfoImg = ICMSConstant.IMAGE_CATEGORY_EXCHANGE_OF_INFO.equals(form.getCategory()) ||
					ICMSConstant.CODE_IMG_CATEGORY_EXCH_INFO.equals(form.getCategory());
//			if ("Security".equals(form.getTypeOfDocument())) {
			if (isExchangeOfInfoImg) {
				if (!(Validator.checkString(form.getBank(), true, 1, 200)).equals(Validator.ERROR_NONE)) {
					errors.add("bankError", new ActionMessage("error.imageupload.bank.mandatory"));
				}
				
			}
			
			boolean isStatementImg = ICMSConstant.IMAGE_CATEGORY_STATEMENT.equals(form.getCategory()) ||
					ICMSConstant.CODE_IMG_CATEGORY_STOCK_STMT.equals(form.getCategory());
//			if ("Security".equals(form.getTypeOfDocument())) {
			if (isStatementImg) {
				if (!(Validator.checkString(form.getStatementTyped(), true, 1, 200)).equals(Validator.ERROR_NONE)) {
					errors.add("statementTypeError", new ActionMessage("error.imageupload.statementtype.mandatory"));
				}
				if (!(Validator.checkString(form.getStatementDocName(), true, 1, 200)).equals(Validator.ERROR_NONE)) {
					errors.add("statementDocNameError",
							new ActionMessage("error.imageupload.statementdocumentname.mandatory"));
				}
			}

//			if (!(Validator.checkString(form.getTypeOfDocument(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
			if (!(Validator.checkString(form.getCategory(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("typeOfDocumentError", new ActionMessage("error.imageupload.typeofdocument.mandatory"));
			}

			// Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
			// if(null!=form.getCategory() &&
			// "IMG_CATEGORY_EXCHANE_OF_INFO".equals(form.getCategory())){
			// if(null!=form.getBank() && form.getBank().isEmpty()){
			// errors.add("bankError", new
			// ActionMessage("error.imageupload.bank.mandatory"));
			// }
			// }
			// Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II

			// commented for test

			// if(form.getHasSubfolder()!=null &&
			// !("".equals(form.getHasSubfolder().trim()))){
			// if (!(errorCode = Validator.checkString(form.getSubfolderName(), true, 1,
			// 20)).equals(Validator.ERROR_NONE)) {
			// errors.add("subfolderName", new
			// ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
			// "20"));
			// }else if(
			// ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getSubfolderName())){
			// errors.add("subfolderName", new
			// ActionMessage("error.string.invalidCharacter"));
			// }
			//
			// if (!(errorCode = Validator.checkString(form.getDocumentName(), true, 1,
			// 50)).equals(Validator.ERROR_NONE)) {
			// errors.add("documentName", new
			// ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
			// "50"));
			// }else if(
			// ASSTValidator.isValidAlphaNumStringWithSpace(form.getDocumentName())){
			// errors.add("documentName", new
			// ActionMessage("error.string.invalidCharacter"));
			// }
			// }

			// commented for test

		} catch (Exception e) {
			e.printStackTrace();
			throw new ImageUploadException("Unable to validate Image Upload Details.", e);
		}
		return errors;
	}
}