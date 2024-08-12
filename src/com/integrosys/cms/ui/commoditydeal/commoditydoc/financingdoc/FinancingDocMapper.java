/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/financingdoc/FinancingDocMapper.java,v 1.10 2005/09/15 06:00:48 czhou Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.financingdoc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.doc.IFinancingDoc;
import com.integrosys.cms.app.commodity.deal.bus.doc.OBFinancingDoc;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2005/09/15 06:00:48 $ Tag: $Name: $
 */

public class FinancingDocMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		FinancingDocForm aForm = (FinancingDocForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) inputs.get("commodityDealTrxValue");
		ICommodityDeal dealObj = trxValue.getStagingCommodityDeal();
		IFinancingDoc[] finDocArr = dealObj.getFinancingDocs();
		int index = Integer.parseInt((String) inputs.get("indexID"));
		HashMap uomMap = (HashMap) inputs.get("uomMap");

		OBFinancingDoc obToChange;
		if (index == -1) {
			obToChange = new OBFinancingDoc();
		}
		else {
			try {
				obToChange = (OBFinancingDoc) AccessorUtil.deepClone(finDocArr[index]);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}

		obToChange.setDocTypeCode(aForm.getSalesDocDesc());
		obToChange.setOtherDocType(aForm.getSalesDocDescOth());
		obToChange.setGoodsDesc(aForm.getDescGoods());
		if (isEmptyOrNull(aForm.getSalesOrderAmt())) {
			obToChange.setOrderAmount(null);
		}
		else {
			try {
				obToChange.setOrderAmount(UIUtil.convertToAmount(locale, aForm.getSalesOrderCcy(), aForm
						.getSalesOrderAmt()));
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}
		if (isEmptyOrNull(aForm.getQuantityVal())) {
			obToChange.setOrderQuantity(null);
		}
		else {
			UOMWrapper qtyUOM = (dealObj.getContractQuantity() == null ? null : dealObj.getContractQuantity()
					.getUnitofMeasure());
			try {
				BigDecimal qty = UIUtil.mapStringToBigDecimal(aForm.getQuantityVal());
				obToChange.setOrderQuantity(new Quantity(qty, qtyUOM));
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}
		if (isEmptyOrNull(aForm.getSalesDocNo())) {
			obToChange.setDocumentNo(null);
		}
		else {
			obToChange.setDocumentNo(aForm.getSalesDocNo());
		}
		obToChange.setOrderExpDate(compareDate(locale, obToChange.getOrderExpDate(), aForm.getExpiryDate()));
		obToChange.setBankCountryCode(aForm.getCountryExportIssBank());
		if (isEmptyOrNull(aForm.getExportLCIssBank())) {
			obToChange.setBankName(null);
		}
		else {
			obToChange.setBankName(aForm.getExportLCIssBank());
		}
		if (isEmptyOrNull(aForm.getLcReference())) {
			obToChange.setLCRefNo(null);
		}
		else {
			obToChange.setLCRefNo(aForm.getLcReference());
		}
		obToChange.setLCExpDate(compareDate(locale, obToChange.getLCExpDate(), aForm.getLcExpiryDate()));
		obToChange.setLOCDueDate(compareDate(locale, obToChange.getLOCDueDate(), aForm.getLocDueDate()));
		if (isEmptyOrNull(aForm.getCounterParty())) {
			obToChange.setCounterParty(null);
		}
		else {
			obToChange.setCounterParty(aForm.getCounterParty());
		}
		obToChange.setTermsMatched(aForm.getTermMatched());
		obToChange.setRemarks(aForm.getFinancingDocRemarks());

		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		FinancingDocForm aForm = (FinancingDocForm) cForm;
		IFinancingDoc financingDoc = (IFinancingDoc) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		String from_event = (String) inputs.get("from_event");
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) inputs.get("commodityDealTrxValue");
		ICommodityDeal dealObj = trxValue.getStagingCommodityDeal();
		if ((from_event != null) && from_event.equals(FinancingDocAction.EVENT_READ)) {
			dealObj = trxValue.getCommodityDeal();
		}

		aForm.setSalesDocDesc(financingDoc.getDocTypeCode().trim());
		aForm.setSalesDocDescOth(financingDoc.getOtherDocType());
		aForm.setDescGoods(financingDoc.getGoodsDesc());
		if ((financingDoc.getOrderAmount() != null) && (financingDoc.getOrderAmount().getCurrencyCode() != null)) {
			aForm.setSalesOrderCcy(financingDoc.getOrderAmount().getCurrencyCode());
			try {
				aForm.setSalesOrderAmt(UIUtil.formatNumber(financingDoc.getOrderAmount().getAmountAsBigDecimal(), 0,
						locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}

		if (financingDoc.getOrderQuantity() != null) {
			/*
			 * if (financingDoc.getOrderQuantity().getUnitofMeasure() != null) {
			 * if (from_event.equals(FinancingDocAction.EVENT_PREPARE_ADD_DEAL)
			 * || from_event.equals(FinancingDocAction.EVENT_PROCESS_UPDATE) ||
			 * from_event.equals(FinancingDocAction.EVENT_PREPARE_UPDATE)) {
			 * aForm
			 * .setQuantityUOM(financingDoc.getOrderQuantity().getUnitofMeasure
			 * ().getID()); } else {
			 * aForm.setQuantityUOM(financingDoc.getOrderQuantity
			 * ().getUnitofMeasure().getLabel()); } }
			 */
			try {
				aForm.setQuantityVal(UIUtil.formatNumber(financingDoc.getOrderQuantity().getQuantity(), 4, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		if ((dealObj.getContractQuantity() != null) && (dealObj.getContractQuantity().getUnitofMeasure() != null)) {
			aForm.setQuantityUOM(dealObj.getContractQuantity().getUnitofMeasure().getLabel());
		}

		aForm.setSalesDocNo(financingDoc.getDocumentNo());
		aForm.setExpiryDate(DateUtil.formatDate(locale, financingDoc.getOrderExpDate()));
		aForm.setCountryExportIssBank(financingDoc.getBankCountryCode());
		aForm.setExportLCIssBank(financingDoc.getBankName());
		aForm.setLcReference(financingDoc.getLCRefNo());
		aForm.setLcExpiryDate(DateUtil.formatDate(locale, financingDoc.getLCExpDate()));
		aForm.setLocDueDate(DateUtil.formatDate(locale, financingDoc.getLOCDueDate()));
		aForm.setCounterParty(financingDoc.getCounterParty());
		aForm.setTermMatched(financingDoc.getTermsMatched());
		aForm.setFinancingDocRemarks(financingDoc.getRemarks());

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE }, { "uomMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "from_event", "java.lang.String", SERVICE_SCOPE }, });
	}

	private static Date compareDate(Locale locale, Date dateOrigin, String dateStage) {
		Date returnDate = DateUtil.convertDate(locale, dateStage);

		if (dateOrigin != null) {
			String originalDate = DateUtil.formatDate(locale, dateOrigin);
			if (originalDate.equals(dateStage)) {
				returnDate = dateOrigin;
			}
		}

		return returnDate;
	}

}
