/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/CommDocMapper.java,v 1.11 2006/02/20 06:47:02 pratheepa Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.bus.doc.IFinancingDoc;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificate;
import com.integrosys.cms.app.sccertificate.proxy.ISCCertificateProxyManager;
import com.integrosys.cms.app.sccertificate.proxy.SCCertificateProxyManagerFactory;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2006/02/20 06:47:02 $ Tag: $Name: $
 */
// Method modified by Pratheepa on 16.01.2006 while fixing R1.5 CR129.Added
// check for DOC_TYPE_WAREHOUSE_RECEIPT_N also.
public class CommDocMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		CommDocForm aForm = (CommDocForm) cForm;
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) inputs.get("commodityDealTrxValue");
		ICommodityDeal dealObj = trxValue.getStagingCommodityDeal();

		String from_event = (String) inputs.get("from_event");
		if (from_event.equals(CommDocAction.EVENT_PREPARE_UPDATE)
				|| from_event.equals(CommDocAction.EVENT_PROCESS_UPDATE)
				|| from_event.equals(CommDocAction.EVENT_PREPARE_ADD_DEAL)) {
			if (aForm.getEvent().endsWith(CommDocAction.EVENT_DELETE_ITEM)) {
				if (aForm.getEvent().startsWith(CommodityDealConstant.FINANCING_DOCUMENT)) {
					IFinancingDoc[] financingDoc = dealObj.getFinancingDocs();
					String[] chkDelete = aForm.getDeleteFinancingDoc();
					Object[] objArr = deleteArr(financingDoc, chkDelete);
					if (objArr != null) {
						financingDoc = new IFinancingDoc[objArr.length];
						for (int i = 0; i < objArr.length; i++) {
							financingDoc[i] = (IFinancingDoc) objArr[i];
						}
					}
					dealObj.setFinancingDocs(financingDoc);
				}
				if (aForm.getEvent().startsWith(CommodityDealConstant.TITLE_DOCUMENT)) {
					ICommodityTitleDocument[] titleDoc = dealObj.getTitleDocsLatest();
					String[] chkDelete = aForm.getDeleteTitleDoc();
					Object[] objArr = deleteArr(titleDoc, chkDelete);

					if (objArr != null) {
						titleDoc = new ICommodityTitleDocument[objArr.length];
						boolean isWRTitleDoc = false;

						// Modified by Pratheepa for CR129
						boolean isWRTitleDoc_N = false;
						boolean isWRTitleDoc_NN = false;
						for (int i = 0; i < titleDoc.length; i++) {
							String titleDocType = null;

							titleDoc[i] = (ICommodityTitleDocument) objArr[i];
							titleDocType = titleDoc[i].getTitleDocType().getName();
							if ((!isWRTitleDoc && titleDoc[i].getTitleDocType().getName().equals(
									CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT))
									|| (!isWRTitleDoc && titleDoc[i].getTitleDocType().getName().equals(
											CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT_N))) {
								isWRTitleDoc = true;
							}
							if (!isWRTitleDoc_N
									&& CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT_N.equals(titleDocType)) {
								DefaultLogger.debug(this, "coming inside neg");
								isWRTitleDoc_N = true;
							}
							if (!isWRTitleDoc_NN
									&& CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT.equals(titleDocType)) {
								DefaultLogger.debug(this, "coming inside nonneg");
								isWRTitleDoc_NN = true;
							}

						}
						dealObj.setIsAnyWRTitleDoc(isWRTitleDoc);
						dealObj.setIsAnyWRTitleDoc_N(isWRTitleDoc_N);
						dealObj.setIsAnyWRTitleDoc_NN(isWRTitleDoc_NN);

						DefaultLogger.debug(this, "isWRTitleDoc" + isWRTitleDoc);
						DefaultLogger.debug(this, "isWRTitleDoc_N" + isWRTitleDoc_N);
						DefaultLogger.debug(this, "isWRTitleDoc_NN" + isWRTitleDoc_NN);

						if (titleDoc.length == 0) {
							titleDoc = new ICommodityTitleDocument[1];
						}
					}

					dealObj.setTitleDocsLatest(titleDoc);
				}
			}
			dealObj.setIsPledgeDocumentRequired(aForm.getDocStatusConfirm());
		}
		return dealObj;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		CommDocForm aForm = (CommDocForm) cForm;
		ICommodityDeal dealObj = (ICommodityDeal) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		aForm.setDeleteFinancingDoc(new String[0]);
		aForm.setDeleteTitleDoc(new String[0]);
		aForm.setDocStatusConfirm(dealObj.getIsPledgeDocumentRequired());

		ILimitProfile limitProfile = (ILimitProfile) inputs.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		ISCCertificateProxyManager sccProxy = SCCertificateProxyManagerFactory.getSCCertificateProxyManager();
		try {
			ISCCertificate scc = sccProxy.getSCCertificateByLimitProfile(limitProfile);
			if ((scc != null) && (scc.getDateGenerated() != null)) {
				aForm.setSccIssuedDate(DateUtil.formatDate(locale, scc.getDateGenerated()));
			}
			else {
				aForm.setSccIssuedDate("");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "from_event", "java.lang.String", SERVICE_SCOPE } });
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
		else {
			newList = oldArr;
		}

		return newList;
	}
}
