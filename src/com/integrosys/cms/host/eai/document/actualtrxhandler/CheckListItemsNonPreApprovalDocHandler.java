package com.integrosys.cms.host.eai.document.actualtrxhandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.IDynamicProperty;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.PropertiesConstantHelper;
import com.integrosys.cms.app.documentlocation.bus.IDocumentAppTypeItem;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.document.CheckListTemplateSystemException;
import com.integrosys.cms.host.eai.document.DocumentConstants;
import com.integrosys.cms.host.eai.document.DocumentConstantsCla;
import com.integrosys.cms.host.eai.document.DocumentInputMessageBody;
import com.integrosys.cms.host.eai.document.NoSuchTemplateDocumentException;
import com.integrosys.cms.host.eai.document.bus.CheckList;
import com.integrosys.cms.host.eai.document.bus.CheckListItem;
import com.integrosys.cms.host.eai.document.bus.IDocumentDao;
import com.integrosys.cms.host.eai.limit.bus.CMSTransaction;
import com.integrosys.cms.host.eai.security.bus.ISecurityJdbc;
import com.integrosys.cms.host.eai.security.bus.asset.AssetSecurity;

/**
 * <p>
 * Special steps to include non pre approval docs based on the template id
 * provided. All the checklist items found in the message are pre approval docs
 * only.
 * 
 * @author Chong Jun Yong
 * 
 */
public class CheckListItemsNonPreApprovalDocHandler extends AbstractCommonActualTrxHandler {

	private Map ccDocAppTypeMap = PropertiesConstantHelper.getTemplateCCDocApplicableApplicationTypesMap();

	private IDocumentDao documentDao;

	private ICheckListTemplateProxyManager templateProxy;

	private ISecurityJdbc securityJdbcDao;

	public IDocumentDao getDocumentDao() {
		return documentDao;
	}

	public ICheckListTemplateProxyManager getTemplateProxy() {
		return templateProxy;
	}

	public ISecurityJdbc getSecurityJdbcDao() {
		return securityJdbcDao;
	}

	public void setDocumentDao(IDocumentDao documentDao) {
		this.documentDao = documentDao;
	}

	public void setTemplateProxy(ICheckListTemplateProxyManager templateProxy) {
		this.templateProxy = templateProxy;
	}

	public void setSecurityJdbcDao(ISecurityJdbc securityJdbcDao) {
		this.securityJdbcDao = securityJdbcDao;
	}

	public Message persistActualTrx(Message msg) {
		EAIMessage eaiMessage = (EAIMessage) msg;

		DocumentInputMessageBody msgBody = (DocumentInputMessageBody) eaiMessage.getMsgBody();
		CheckList checkList = msgBody.getCheckList();

		Collection nonPreApprovalDocs = new ArrayList();
		if (checkList.isInsertChecklist()) {
			Map parameters = new HashMap();
			parameters.put("transactionType", ICMSConstant.INSTANCE_TEMPLATE_LIST);
			parameters.put("referenceID", new Long(checkList.getTemplateID()));

			CMSTransaction templateTransaction = (CMSTransaction) getDocumentDao().retrieveObjectByParameters(
					parameters, CMSTransaction.class);
			if (templateTransaction == null) {
				throw new NoSuchTemplateDocumentException(checkList.getTemplateID());
			}

			ITemplateTrxValue templateTrxValue = null;
			String templateTrxId = Long.toString(templateTransaction.getTransactionID());
			try {
				templateTrxValue = getTemplateProxy().getTemplateByTrxID(templateTrxId);
			}
			catch (CheckListTemplateException e) {
				throw new CheckListTemplateSystemException("failed to retrieve template for template id ["
						+ checkList.getTemplateID() + "], LOS AA Number [" + checkList.getAANumber() + "]", e);
			}

			if (templateTrxValue != null) {
				ITemplate template = templateTrxValue.getTemplate();

				if (template.getTemplateItemList() != null) {
					for (int i = 0; i < template.getTemplateItemList().length; i++) {
						ITemplateItem templateItem = template.getTemplateItemList()[i];
						if (templateItem.getItem().getIsPreApprove()) {
							continue;
						}

						if (checkList.getScChecklist() != null) {
							long cmsCollateralId = checkList.getScChecklist().getCmsCollateralID();
							AssetSecurity asset = getSecurityJdbcDao().retrieveAssetVehiclePbrPbtGoodsStatusDetail(
									cmsCollateralId);

							if (asset != null) {
								boolean goodsStatusMatch = false;
								boolean pbrPbtIndMatch = false;

								String goodStatus = asset.getSpecificChargeDetail().getGoodStatus()
										.getStandardCodeValue();

								String pbtPbrInd = asset.getVehicleDetail().getPBTPBRInd();

								IDynamicProperty[] propertyList = templateItem.getItem().getPropertyList();
								if (propertyList != null) {
									for (int j = 0; j < propertyList.length && (!goodsStatusMatch || !pbrPbtIndMatch); j++) {
										if (!goodsStatusMatch
												&& ("GOODS_STATUS").equals(propertyList[j].getPropertyCategory())
												&& propertyList[j].getPropertyValue().equals(goodStatus)) {
											goodsStatusMatch = true;
											continue;
										}

										if (!pbrPbtIndMatch
												&& "PBR_PBT_IND".equals(propertyList[j].getPropertyCategory())
												&& propertyList[j].getPropertyValue().equals(pbtPbrInd)) {
											pbrPbtIndMatch = true;
										}
									}
								}

								if (!(goodsStatusMatch && pbrPbtIndMatch)) {
									continue;
								}
							}
						}

						if (checkList.getCcChecklist() != null) {
							String checklistCustomerType = checkList.getCcChecklist().getCustomerType();
							//String docApplicationType = templateItem.getItem().getLoanApplicationType();
							String[] applicableAppTypes = retrieveApplicationList(templateItem.getItem());
							String applicationType = checkList.getApplicationType();

							if (!ArrayUtils.contains(applicableAppTypes,applicationType)) 
							{
								continue;
							}

							if (ArrayUtils
									.contains(DocumentConstantsCla.getChecklistCategoryPledgor(), checklistCustomerType)
									&& templateItem.getItem().getIsForBorrower()
									&& !templateItem.getItem().getIsForPledgor()) {
								continue;
							}
							else if (ArrayUtils.contains(DocumentConstantsCla.getChecklistCategoryBorrower(),
									checklistCustomerType)
									&& templateItem.getItem().getIsForPledgor()
									&& !templateItem.getItem().getIsForBorrower()) {
								continue;
							}
						}

						String rawDocNo = "";
						try {
							rawDocNo = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_CHECKLIST_ITEM, true);
						}
						catch (Exception e) {
							throw new CheckListTemplateSystemException("failed to generate sequence number for ["
									+ ICMSConstant.SEQUENCE_CHECKLIST_ITEM + "] for template id ["
									+ checkList.getTemplateID() + "], LOS AA Number [" + checkList.getAANumber() + "]",
									e);
						}

						StringBuffer buf = new StringBuffer(rawDocNo);

						// insert the '1' (for CMS) at 8th characters of the
						// sequence
						buf.insert(8, 1l);

						CheckListItem tempItem = new CheckListItem();
						tempItem.setDocNo(Long.parseLong(buf.toString()));
						tempItem.setDocCode(templateItem.getItemCode());

						// setting mandatory indicator, Collateral Checklist
						// based on the mandatoryInd, CC Checklist based on
						// the MandatoryInd of Borrower/Pledgor
						if (DocumentConstants.CHECKLIST_TYPE_COLLATERAL.equals(checkList.getChecklistType())) {
							tempItem.setMandatoryInd(templateItem.getIsMandatoryInd() ? 'Y' : 'N');
						}
						else if (DocumentConstants.CHECKLIST_TYPE_BORROWER_PLEDGOR.equals(checkList.getChecklistType())) {
							if (ArrayUtils.contains(DocumentConstantsCla.getChecklistCategoryBorrower(), checkList
									.getCcChecklist().getCustomerType())) {
								tempItem.setMandatoryInd(templateItem.getIsMandatoryForBorrowerInd() ? 'Y' : 'N');
							}
							else if (ArrayUtils.contains(DocumentConstantsCla.getChecklistCategoryPledgor(), checkList
									.getCcChecklist().getCustomerType())) {
								tempItem.setMandatoryInd(templateItem.getIsMandatoryForPledgorInd() ? 'Y' : 'N');
							}
						}

						tempItem.setDescription(templateItem.getItemDesc());
						tempItem.setPreApprove(false);
						tempItem.setJDOExpiryDate(templateItem.getItem().getExpiryDate());
						tempItem.setStatus(ICMSConstant.STATE_ITEM_AWAITING);
						tempItem.setChangeIndicator(String.valueOf(CHANGEINDICATOR));
						tempItem.setUpdateStatusIndicator(String.valueOf(CREATEINDICATOR));

						nonPreApprovalDocs.add(tempItem);
					}
				}
			}
		}

		if (!nonPreApprovalDocs.isEmpty()) {
			checkList.getCheckListItem().addAll(nonPreApprovalDocs);
		}

		return eaiMessage;
	}
	
	private String[] retrieveApplicationList(IItem itemList)
	{
		Collection docAppItemList = itemList.getCMRDocAppItemList();
		List applicableAppTypes = new ArrayList();
		Iterator iter1 = docAppItemList.iterator();
		if(docAppItemList != null)
		{
			while (iter1.hasNext()) 
			{
				IDocumentAppTypeItem tempDocumentAppTypeItem = (IDocumentAppTypeItem)iter1.next();
				applicableAppTypes.add(tempDocumentAppTypeItem.getAppType());
			}
		}
		
		return (String[]) applicableAppTypes.toArray(new String[0]);
	}
}
