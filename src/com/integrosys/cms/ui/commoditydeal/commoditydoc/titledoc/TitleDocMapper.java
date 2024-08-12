/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/titledoc/TitleDocMapper.java,v 1.10 2004/11/18 07:24:44 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.base.uiinfra.mapper.MapperUtil;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.bus.doc.IWarehouseReceipt;
import com.integrosys.cms.app.commodity.deal.bus.doc.OBCommodityTitleDocument;
import com.integrosys.cms.app.commodity.main.bus.titledocument.ITitleDocument;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2004/11/18 07:24:44 $ Tag: $Name: $
 */

public class TitleDocMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		TitleDocForm aForm = (TitleDocForm) cForm;
		ICommodityTitleDocument titleDocObj = (ICommodityTitleDocument) inputs.get("serviceTitleDocObj");
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		long index = Long.parseLong((String) inputs.get("indexID"));

		if ((index == -1) && (titleDocObj == null)) {
			titleDocObj = new OBCommodityTitleDocument();
		}

		if (aForm.getEvent().equals(TitleDocAction.EVENT_REMOVE)) {
			IWarehouseReceipt[] warehouseRec = titleDocObj.getWarehouseReceipts();
			String[] deleteWarehouse = aForm.getDeleteWarehouse();
			Object[] objArr = deleteArr(warehouseRec, deleteWarehouse);
			if (objArr != null) {
				warehouseRec = new IWarehouseReceipt[objArr.length];
				for (int i = 0; i < objArr.length; i++) {
					warehouseRec[i] = (IWarehouseReceipt) objArr[i];
				}
			}
			titleDocObj.setWarehouseReceipts(warehouseRec);
		}

		titleDocObj.setProcessStageCode(aForm.getProcessStage());
		if (isEmptyOrNull(aForm.getDocDesc())) {
			titleDocObj.setTitleDocType(null);
		}
		else {
			ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
			try {
				ITitleDocument titleDoc = proxy.getTitleDocumentByTitleDocumentID(Long.parseLong(aForm.getDocDesc()));
				titleDocObj.setTitleDocType(titleDoc);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}

		if (isEmptyOrNull(aForm.getEligibilityAdv())) {
			titleDocObj.setAdvEligibilityPct(ICMSConstant.DOUBLE_INVALID_VALUE);
		}
		else {
			try {
				titleDocObj.setAdvEligibilityPct(MapperUtil.mapStringToDouble(aForm.getEligibilityAdv(), locale));
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}

		titleDocObj.setIsSecured(aForm.getSecured());
		titleDocObj
				.setTransactionDate(compareDate(locale, titleDocObj.getTransactionDate(), aForm.getTransactionDate()));

		// bill lading
		titleDocObj.setBLShippingCompany(aForm.getShippingCompany());
		if (isEmptyOrNull(aForm.getBillLadingNo())) {
			titleDocObj.setBLNo(null);
		}
		else {
			titleDocObj.setBLNo(aForm.getBillLadingNo());
		}
		titleDocObj.setBLDate(compareDate(locale, titleDocObj.getBLDate(), aForm.getBillLadingDate()));
		titleDocObj.setBLRemarks(aForm.getBillLadingRemarks());

		// trust receipt details
		if (isEmptyOrNull(aForm.getTrNo())) {
			titleDocObj.setTRNo(null);
		}
		else {
			titleDocObj.setTRNo(aForm.getTrNo());
		}
		titleDocObj.setTRBillLadingNo(aForm.getBillLadingRefNo());
		if (isEmptyOrNull(aForm.getTrAmtVal())) {
			titleDocObj.setTRAmount(null);
		}
		else {
			try {
				titleDocObj.setTRAmount(CurrencyManager.convertToAmount(locale, aForm.getTrAmtCcy(), aForm
						.getTrAmtVal()));
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}
		titleDocObj.setTRDate(compareDate(locale, titleDocObj.getTRDate(), aForm.getTrDate()));
		titleDocObj.setTRMaturityDate(compareDate(locale, titleDocObj.getTRMaturityDate(), aForm.getTrMaturityDate()));
		titleDocObj.setTRRemarks(aForm.getTrRemarks());

		// other document details
		if (isEmptyOrNull(aForm.getDocumentNo())) {
			titleDocObj.setOtherDocNo(null);
		}
		else {
			titleDocObj.setOtherDocNo(aForm.getDocumentNo());
		}
		titleDocObj.setOtherDocDesc(aForm.getDocumentDesc());
		titleDocObj.setOtherDocDate(compareDate(locale, titleDocObj.getOtherDocDate(), aForm.getDocumentDate()));
		titleDocObj
				.setOtherDocDueDate(compareDate(locale, titleDocObj.getOtherDocDueDate(), aForm.getDocumentDueDate()));
		titleDocObj.setOtherDocRemarks(aForm.getDocumentRemarks());

		return titleDocObj;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		TitleDocForm aForm = (TitleDocForm) cForm;
		String from_event = (String) inputs.get("from_event");
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICommodityTitleDocument titleDocObj = (ICommodityTitleDocument) obj;

		aForm.setDeleteWarehouse(null);

		aForm.setProcessStage(titleDocObj.getProcessStageCode());
		if ((titleDocObj.getTitleDocType() != null)
				&& (titleDocObj.getTitleDocType().getTitleDocumentID() != ICMSConstant.LONG_INVALID_VALUE)) {
			aForm.setDocType(titleDocObj.getTitleDocType().getType());
			if (from_event.equals(TitleDocAction.EVENT_PREPARE_UPDATE)
					|| from_event.equals(TitleDocAction.EVENT_PROCESS_UPDATE)
					|| from_event.equals(TitleDocAction.EVENT_PREPARE_ADD_DEAL)) {
				if (aForm.getEvent().equals(TitleDocAction.EVENT_READ)) {
					aForm.setDocDesc(titleDocObj.getTitleDocType().getName());
				}
				else {
					aForm.setDocDesc(String.valueOf(titleDocObj.getTitleDocType().getTitleDocumentID()));
				}
			}
			else {
				aForm.setDocDesc(titleDocObj.getTitleDocType().getName());
			}
		}

		if (titleDocObj.getAdvEligibilityPct() != ICMSConstant.DOUBLE_INVALID_VALUE) {
			aForm.setEligibilityAdv(String.valueOf((int) titleDocObj.getAdvEligibilityPct()));
		}

		aForm.setSecured(titleDocObj.getIsSecured());
		aForm.setTransactionDate(DateUtil.formatDate(locale, titleDocObj.getTransactionDate()));

		// bill lading
		aForm.setShippingCompany(titleDocObj.getBLShippingCompany());
		aForm.setBillLadingNo(titleDocObj.getBLNo());
		aForm.setBillLadingDate(DateUtil.formatDate(locale, titleDocObj.getBLDate()));
		aForm.setBillLadingRemarks(titleDocObj.getBLRemarks());

		// trust receipt details
		aForm.setTrNo(titleDocObj.getTRNo());
		aForm.setBillLadingRefNo(titleDocObj.getTRBillLadingNo());
		if (titleDocObj.getTRAmount() != null) {
			aForm.setTrAmtCcy(titleDocObj.getTRAmount().getCurrencyCode());
			if (titleDocObj.getTRAmount().getAmount() != ICMSConstant.DOUBLE_INVALID_VALUE) {
				aForm.setTrAmtVal(MapperUtil.mapDoubleToString(titleDocObj.getTRAmount().getAmount(), 6, locale));
			}
		}
		aForm.setTrDate(DateUtil.formatDate(locale, titleDocObj.getTRDate()));
		aForm.setTrMaturityDate(DateUtil.formatDate(locale, titleDocObj.getTRMaturityDate()));
		aForm.setTrRemarks(titleDocObj.getTRRemarks());

		// other document details
		aForm.setDocumentNo(titleDocObj.getOtherDocNo());
		aForm.setDocumentDesc(titleDocObj.getOtherDocDesc());
		aForm.setDocumentDate(DateUtil.formatDate(locale, titleDocObj.getOtherDocDate()));
		aForm.setDocumentDueDate(DateUtil.formatDate(locale, titleDocObj.getOtherDocDueDate()));
		aForm.setDocumentRemarks(titleDocObj.getOtherDocRemarks());

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE },
				{ "serviceTitleDocObj", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument",
						SERVICE_SCOPE }, { "from_event", "java.lang.String", SERVICE_SCOPE }, });
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

	private Object[] deleteArr(Object[] oldArr, String[] chkDelete) {
		Object[] newList = null;
		if (chkDelete != null) {
			if (chkDelete.length <= oldArr.length) {
				int numDelete = 0;
				for (int i = 0; i < chkDelete.length; i++) {
					if (Integer.parseInt(chkDelete[i]) < oldArr.length) {
						numDelete++;
					}
				}
				if (numDelete != 0) {
					newList = new Object[oldArr.length - numDelete];
					int i = 0, j = 0;
					while (i < oldArr.length) {
						if ((j < chkDelete.length) && (Integer.parseInt(chkDelete[j]) == i)) {
							j++;
						}
						else {
							newList[i - j] = oldArr[i];
						}
						i++;
					}
				}
			}
		}
		return newList;
	}
}
