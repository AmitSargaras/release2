package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;
import com.integrosys.cms.app.common.constant.PropertiesConstantHelper;
import com.integrosys.cms.app.documentlocation.bus.IDocumentAppTypeItem;
import com.integrosys.component.commondata.app.bus.CommonDataManagerFactory;
import com.integrosys.component.commondata.app.bus.ICodeCategory;
import com.integrosys.component.commondata.app.bus.ICodeCategoryEntry;
import com.integrosys.component.commondata.app.bus.ICommonDataManager;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * Created by IntelliJ IDEA. User: user Date: Jul 16, 2008 Time: 6:40:03 PM To
 * change this template use File | Settings | File Templates.
 */
public abstract class AbstractCheckListTemplateBusManager implements IDocumentBusManager, ITemplateBusManager {

	/** key is HP/NHP, value is applicable application types, in array */
	private Map ccDocAppTypeMap = PropertiesConstantHelper.getTemplateCCDocApplicableApplicationTypesMap();

	private static final long serialVersionUID = -4025607390093749869L;

	/**
	 * Get the document items that are not in the template
	 * @param anITemplate - ITemplate
	 * @return IDocumentItem - the list of document items that are not in the
	 *         template
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is DAO
	 *         errors
	 */
	public DocumentSearchResultItem[] getDocumentItemList(ITemplate anITemplate) throws CheckListTemplateException,
			SearchDAOException, TemplateNotSetupException {
		DocumentSearchCriteria criteria = new DocumentSearchCriteria();
		criteria.setDocumentType(anITemplate.getTemplateType());
		SearchResult result = getDocumentItemList(criteria);
		if (result == null) {
			TemplateNotSetupException exp = new TemplateNotSetupException(
					"There is no Global Document Items being setup yet.");
			exp.setErrorCode(ICMSErrorCodes.GLOBAL_NOT_SETUP);
			throw exp;
		}
		Collection resultCol = result.getResultList();
		if ((resultCol == null) || (resultCol.size() == 0)) {
			TemplateNotSetupException exp = new TemplateNotSetupException(
					"There is no Global Document Items being setup yet.");
			exp.setErrorCode(ICMSErrorCodes.GLOBAL_NOT_SETUP);
			throw exp;
		}
		DocumentSearchResultItem[] itemList = (DocumentSearchResultItem[]) resultCol
				.toArray(new DocumentSearchResultItem[resultCol.size()]);

		if ((anITemplate == null) || (anITemplate.getTemplateItemList() == null)
				|| (anITemplate.getTemplateItemList().length == 0)) {
			return itemList;
		}
		ITemplateItem[] tempItemList = anITemplate.getTemplateItemList();
		itemList = filter(itemList, tempItemList);

		return itemList;
	}

	/**
	 * To filter off those document items that are not in the list of templat
	 * items
	 * @param aResultItemList - IDocumentItem[]
	 * @param anITemplateItemList - ITemplateItem[]
	 * @return IDocumentItem[] - the list of document items after filtering
	 */
	private DocumentSearchResultItem[] filter(DocumentSearchResultItem[] aResultItemList,
			ITemplateItem[] anITemplateItemList) {
		ArrayList itemList = new ArrayList();
		boolean removeFlag = false;
		for (int ii = 0; ii < aResultItemList.length; ii++) {
			removeFlag = false;
			for (int jj = 0; jj < anITemplateItemList.length; jj++) {
				if (aResultItemList[ii].getItemCode().equals(anITemplateItemList[jj].getItemCode())) {
					removeFlag = true;
					break;
				}
			}
			if (!removeFlag) {
				itemList.add(aResultItemList[ii]);
			}
		}
		return (DocumentSearchResultItem[]) itemList.toArray(new DocumentSearchResultItem[itemList.size()]);
	}

	/**
	 * Get the global items as well as those that are newly added at template
	 * level It will perform the following
     * 1. Get the list of global doc if any
	 * 2. Get the list of item under the template that the checklist inherited
	 * from
     * 3. filter the template according the security check list mapping
     * 4. Merge the 2 lists
     *
	 * @param checklistCategory - Category of checklist
	 * @param templateID - Template ID
     * @param goodsStatus - N, U, R, I, G
     * @param pbrInd     - PBR, PBT, NA
	 * @return IItem[] - the list of items that are in global as well as those
	 *         newly added at template level
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is DAO
	 *         errors
	 */
	public IItem[] getParentItemList(String checklistCategory, long templateID, String goodsStatus, String pbrInd)
            throws CheckListTemplateException, SearchDAOException {
		DocumentSearchCriteria criteria = new DocumentSearchCriteria();
		criteria.setDocumentType(checklistCategory);
		DocumentSearchResultItem[] docItemList = null;
		SearchResult result = getDocumentItemList(criteria);
		if (result != null) {
			Collection resultCol = result.getResultList();
			if ((resultCol != null) && (resultCol.size() > 0)) {
				docItemList = (DocumentSearchResultItem[]) resultCol.toArray(new DocumentSearchResultItem[resultCol
						.size()]);
			}
		}
		ITemplateItem[] templateItemList = null;
		if (ICMSConstant.LONG_MIN_VALUE != templateID) {
            ITemplate template = getTemplateByID(templateID);
            template = filterCollateralTemplate(template, goodsStatus, pbrInd);
			templateItemList = template.getTemplateItemList();
		}
		else {
			ArrayList temp = new ArrayList();
			templateItemList = (ITemplateItem[]) temp.toArray(new ITemplateItem[0]);
		}
		IItem[] itemList = mergeItemList(docItemList, templateItemList);

		return itemList;
	}

    /**
	 * Get the global items as well as those that are newly added at template
	 * level It will perform the following 1. Get the list of global doc if any
	 * 2. Get the list of item under the template that the checklist inherited
	 * from 3. Merge the 2 lists
	 * @param checklistCategory - Category of checklist
	 * @param templateID - Template ID
	 * @return IItem[] - the list of items that are in global as well as those
	 *         newly added at template level
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is DAO
	 *         errors
	 */
	public IItem[] getParentItemList(String checklistCategory, long templateID) throws CheckListTemplateException,
			SearchDAOException {
		DocumentSearchCriteria criteria = new DocumentSearchCriteria();
		criteria.setDocumentType(checklistCategory);
		DocumentSearchResultItem[] docItemList = null;
		SearchResult result = getDocumentItemList(criteria);
		if (result != null) {
			Collection resultCol = result.getResultList();
			if ((resultCol != null) && (resultCol.size() > 0)) {
				docItemList = (DocumentSearchResultItem[]) resultCol.toArray(new DocumentSearchResultItem[resultCol
						.size()]);
			}
		}
		ITemplateItem[] templateItemList = null;
		if (ICMSConstant.LONG_MIN_VALUE != templateID) {
			ITemplate template = getTemplateByID(templateID);
			templateItemList = template.getTemplateItemList();
		}
		else {
			ArrayList temp = new ArrayList();
			templateItemList = (ITemplateItem[]) temp.toArray(new ITemplateItem[0]);
		}
		IItem[] itemList = mergeItemList(docItemList, templateItemList);

		return itemList;
	}

	/**
	 * To merge the list of items from the global and the template
	 * @param anItemList of type DocumentSearchResultItem[] which contains the
	 *        list of global items
	 * @param aTemplateItemList of type ITemplateItem[] which contains the list
	 *        of template items
	 * @return IItem[] - the merged item list without duplicates
	 */
	private IItem[] mergeItemList(DocumentSearchResultItem[] anItemList, ITemplateItem[] aTemplateItemList) {
		ArrayList itemList = new ArrayList();
		ArrayList itemCodeList = new ArrayList();
		if (anItemList != null) {
			for (int ii = 0; ii < anItemList.length; ii++) {
				itemList.add(anItemList[ii].getItem());
				itemCodeList.add(anItemList[ii].getItemCode());
			}
		}
		if (aTemplateItemList != null) {
			for (int ii = 0; ii < aTemplateItemList.length; ii++) {
				if (!itemCodeList.contains(aTemplateItemList[ii].getItemCode())) {
					itemList.add(aTemplateItemList[ii].getItem());
					itemCodeList.add(aTemplateItemList[ii].getItemCode());
				}
			}
		}
		return (IItem[]) itemList.toArray(new IItem[itemList.size()]);
	}

	/**
	 * Get the master template based on the law and legal constitution
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @return ITemplate - the master template of the specified law and legal
	 *         constitution
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplate getCCTemplate(String aLaw, String aLegalConstitution) throws CheckListTemplateException {
		ITemplate[] templateList = getCCTemplateList(aLaw, aLegalConstitution);
		if ((templateList != null) && (templateList.length > 0)) {
			return templateList[0];
		}
		return null;
	}

	/**
	 * Get the country template based on the law, legal constitution and country
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @param aCountry - String
	 * @return ITemplate - the country template of the specified law, legal
	 *         constitution and country
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplate getCCTemplate(String aLaw, String aLegalConstitution, String aCountry)
			throws CheckListTemplateException {
		ITemplate[] templateList = getCCTemplateList(aLaw, aLegalConstitution, aCountry);
		if ((templateList != null) && (templateList.length > 0)) {
			return templateList[0];
		}
		return null;
	}

	/**
	 * <p>
	 * Used by SI search (LOS) - for method details refer to
	 * getCCTemplate(String aLaw, String aLegalConstitution, String aCountry)
	 * The differences between the 2 methods is such that this will return
	 * <p>
	 * Borrower Type: =============== 1. Only docs applicable for borrower only
	 * when "borrowerType" = ICMSConstant.CHECKLIST_MAIN_BORROWER 2. Only docs
	 * applicable for pledgor only when "borrowerType" =
	 * ICMSConstant.CHECKLIST_PLEDGER
	 * <p>
	 * Pre-Approval Flag: ================== 1. Only Pre-Approval Doc when param
	 * "preApprovalDocFlag" is 'P' [PreApprove] 2. Only Non Pre-Approval Doc
	 * when param "preApprovalDocFlag" is 'N' [Non-PreApprove] 3. All docs when
	 * param "preApprovalDocFlag" is 'A' [All] (currently this is not in use) If
	 * for CMS usage, the flag "preApprovalDocFlag" should always be 'A'
	 * 
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @param aCountry - String
	 * @param borrowerType - String
	 * @param preApprovalDocFlag - 'P' to return only pre-approve documents
	 *        only, 'N' to return non-pre-approve documents only, 'A' to return
	 *        all documents (pre-approve + non-pre-approve)
	 * @return ITemplate - the country template of the specified law, legal
	 *         constitution and country
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplate getCCTemplate(String aLaw, String aLegalConstitution, String aCountry, String applicationType,
			String borrowerType, String preApprovalDocFlag) throws CheckListTemplateException {

		ITemplate template = getCCTemplate(aLaw, aLegalConstitution, aCountry);
		if (template == null) {
			throw new TemplateNotSetupException("No Template not setup for law " + aLaw + " and legalConstitution "
					+ aLegalConstitution + "and country " + aCountry);
		}

		// filter according to borrower type and pre-approval flag
		ArrayList itemList = new ArrayList();
		ITemplateItem[] allTemplateItemList = template.getTemplateItemList();
		if (allTemplateItemList == null || allTemplateItemList.length == 0) {
			return null;
		}

		boolean preApprovalDoc = ("P".equals(preApprovalDocFlag));

		String docAppType = null;
		for (Iterator itr = ccDocAppTypeMap.entrySet().iterator(); itr.hasNext();) {
			Map.Entry entry = (Map.Entry) itr.next();
			String storedDocAppType = (String) entry.getKey();
			String[] storedApplicableAppTypes = (String[]) entry.getValue();
			Arrays.sort(storedApplicableAppTypes);
			if (Arrays.binarySearch(storedApplicableAppTypes, applicationType) >= 0) {
				docAppType = storedDocAppType;
			}
		}

		for (int i = 0; i < allTemplateItemList.length; i++) {
			IItem item = allTemplateItemList[i].getItem();
			String[] applicableAppTypes = retrieveApplicationList(item);
			if (ArrayUtils.contains(applicableAppTypes, docAppType)) {

				if ("A".equals(preApprovalDocFlag) || item.getIsPreApprove() == preApprovalDoc) {
					if (item.getIsForBorrower() && ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(borrowerType)) {
						allTemplateItemList[i].setIsMandatoryInd(allTemplateItemList[i].getIsMandatoryForBorrowerInd());
						itemList.add(allTemplateItemList[i]);
					}
					else if (item.getIsForPledgor() && ICMSConstant.CHECKLIST_PLEDGER.equals(borrowerType)) {
						allTemplateItemList[i].setIsMandatoryInd(allTemplateItemList[i].getIsMandatoryForPledgorInd());
						itemList.add(allTemplateItemList[i]);
					}
				}

			}
		}

		template.setTemplateItemList((ITemplateItem[]) itemList.toArray(new ITemplateItem[0]));
		return template;
	}

	/**
	 * Get the global collateral template based on the collateral type and sub
	 * type
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @return ITemplate - the security global template of the specified
	 *         collateral type and sub type
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplate getCollateralTemplate(String aCollateralType, String aCollateralSubType)
			throws CheckListTemplateException {
		ITemplate[] templateList = getCollateralTemplateList(aCollateralType, aCollateralSubType);
		if ((templateList != null) && (templateList.length > 0)) {
			return templateList[0];
		}
		return null;
	}
	
	

	/**
	 * Get the country template based on the law, legal constitution and country
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @param aCountry - String
	 * @return ITemplate - the country template of the specified law, legal
	 *         constitution and country
	 * @throws com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException
	 *         if template is not setup
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplate getDefaultCCTemplate(String aLaw, String aLegalConstitution, String aCountry)
			throws TemplateNotSetupException, CheckListTemplateException {
		ITemplate template = getCCTemplate(aLaw, aLegalConstitution);
		if (template == null) {
			throw new TemplateNotSetupException("Master CC Template not setup for law " + aLaw
					+ " and legalConstitution " + aLegalConstitution);
		}
		ITemplate defaultTemplate = MergeTemplateUtilFactory.getMergeTemplateUtil().prepareTemplateForRetrieval(
				template, null);
		defaultTemplate.setParentTemplateID(template.getTemplateID());
		defaultTemplate.setCountry(aCountry);
		sortTemplateItemList(defaultTemplate);
		return defaultTemplate;
	}

	/**
	 * To perform a sort in the template items based on the item description
	 * @param anITemplate of ITemplate type
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	private void sortTemplateItemList(ITemplate anITemplate) throws CheckListTemplateException {
		try {
			ITemplateItem[] itemList = anITemplate.getTemplateItemList();
			if (itemList != null) {
				Arrays.sort(itemList);
			}
		}
		catch (Exception ex) {
			throw new CheckListTemplateException("Exception in sortTemplateItemList: " + ex.toString());
		}
	}

	/**
	 * Get the global security template based on the collateral type and sub
	 * type
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @param aCountry - String
	 * @return ITemplate - the security global template of the specified
	 *         collateral type and sub type
	 * @throws com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException
	 *         if template is not setup
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplate getDefaultCollateralTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
			throws TemplateNotSetupException, CheckListTemplateException {
		ITemplate template = getCollateralTemplate(aCollateralType, aCollateralSubType);
		if (template == null) {
			throw new TemplateNotSetupException("Global Collateral Template not setup for CollateralType "
					+ aCollateralType + " and subType " + aCollateralSubType);
		}
		ITemplate defaultTemplate = MergeTemplateUtilFactory.getMergeTemplateUtil().prepareTemplateForRetrieval(
				template, null);
		defaultTemplate.setParentTemplateID(template.getTemplateID());
		defaultTemplate.setCountry(aCountry);
		sortTemplateItemList(defaultTemplate);
		return defaultTemplate;
	}

	/**
	 * Get the master collateral template based on the collateral type, sub type
	 * and country
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @param aCountry - String
	 * @return ITemplate - the security global template of the specified
	 *         collateral type and sub type
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplate getCollateralTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
			throws CheckListTemplateException {
		ITemplate[] templateList = getCollateralTemplateList(aCollateralType, aCollateralSubType, aCountry);
		if ((templateList != null) && (templateList.length > 0)) {
			return templateList[0];
		}
		return null;
	}
	
	
	public ITemplate getCAMTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
	throws CheckListTemplateException {
		ITemplate[] templateList = getCAMTemplateList(aCollateralType, aCollateralSubType, aCountry);
		if ((templateList != null) && (templateList.length > 0)) {
			return templateList[0];
		}
		return null;
	}
	
	public ITemplate getFacilityTemplate(String aCollateralType, String aCollateralSubType, String aCountry)
	throws CheckListTemplateException {
		ITemplate[] templateList = getFacilityTemplateList(aCollateralType, aCollateralSubType, aCountry);
		if ((templateList != null) && (templateList.length > 0)) {
			return templateList[0];
		}
		return null;
	}

   	/**
	 * Perform filter on template item base on goodstatus and pbrInd for Plant & Equipment and Vehicle only
	 * @param template - ITemplate
	 * @param goodsStatus - String
	 * @param pbrInd - String
	 * @return ITemplate - the security global template of the specified
	 *         collateral type and sub type
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
    public ITemplate filterCollateralTemplate(ITemplate template, String goodsStatus, String pbrInd)
			throws CheckListTemplateException {

		if (template == null) {
			return null;
		}

		ITemplateItem[] itemList = template.getTemplateItemList();

		if (itemList == null || itemList.length == 0) {
			return null;
		}

		ArrayList filteredItemList = new ArrayList();

		for (int i = 0; i < itemList.length; i++) {
                if ((!(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(template.getCollateralSubType()) || ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT.equals(template.getCollateralSubType())))
					|| (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(template.getCollateralSubType()) && metTemplateCriteriaForVehicleSubtype(
							itemList[i], template.getCollateralSubType(), goodsStatus, pbrInd))
                    || (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT.equals(template.getCollateralSubType()) && metTemplateCriteriaForPlantNEquipmentSubtype(
							itemList[i], template.getCollateralSubType(), goodsStatus))){
				    filteredItemList.add(itemList[i]);
			    }
		}

		if (filteredItemList.size() == 0) {
			template.setTemplateItemList(null);
		}
		else {
			template.setTemplateItemList((ITemplateItem[]) filteredItemList.toArray(new ITemplateItem[0]));
		}

		return template;
	}

    private boolean metTemplateCriteriaForPlantNEquipmentSubtype(ITemplateItem templateItem, String collateralSubType,
			String goodsStatus) {

		if (!ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT.equals(collateralSubType)) {
			return true; // no filtering required for other types or CC

		}else if(goodsStatus == null || goodsStatus.trim().length() == 0 ){
            return false;
        }else {
            if(templateItem.getNewWithFBR()==true){
                if(goodsStatus.equals(ICMSConstant.GOOD_STATUS_NEW)
                        || goodsStatus.equals(ICMSConstant.GOOD_STATUS_IMPORTED_REGISTRATION)
                        || goodsStatus.equals(ICMSConstant.GOOD_STATUS_IMPORTED_NON_REGISTRATION)){
                    return true;
                }
            }else if(templateItem.getNewWithoutFBR()==true){
                if(goodsStatus.equals(ICMSConstant.GOOD_STATUS_NEW)
                        || goodsStatus.equals(ICMSConstant.GOOD_STATUS_IMPORTED_REGISTRATION)
                        || goodsStatus.equals(ICMSConstant.GOOD_STATUS_IMPORTED_NON_REGISTRATION)){
                    return true;
                }
            }else if(templateItem.getUsedWithFBR()==true){
                if(goodsStatus.equals(ICMSConstant.GOOD_STATUS_USED)
                        || goodsStatus.equals(ICMSConstant.GOOD_STATUS_RECONDITIONED)){
                    return true;
                }
            }else if(templateItem.getUsedWithoutFBR()==true){
                if(goodsStatus.equals(ICMSConstant.GOOD_STATUS_USED)
                        || goodsStatus.equals(ICMSConstant.GOOD_STATUS_RECONDITIONED)){
                    return true;
                }
            }
            return false;
		}
	}

	private boolean metTemplateCriteriaForVehicleSubtype(ITemplateItem templateItem, String collateralSubType,
			String goodsStatus, String pbrInd) {

		if (!ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(collateralSubType)) {
			return true; // no filtering required for other types or CC
        }else if(goodsStatus == null || goodsStatus.trim().length() == 0 || pbrInd ==null || pbrInd.trim().length() ==0){
            return false;
		}else {
            if(templateItem.getNewWithFBR()==true){
                if((goodsStatus.equals(ICMSConstant.GOOD_STATUS_NEW)
                        || goodsStatus.equals(ICMSConstant.GOOD_STATUS_IMPORTED_REGISTRATION)
                        || goodsStatus.equals(ICMSConstant.GOOD_STATUS_IMPORTED_NON_REGISTRATION))
                    && pbrInd.equals(ICMSConstant.LOS_PAYMENT_INDICATOR_PBR)){
                    return true;
                }
            }else if(templateItem.getNewWithoutFBR()==true){
                if((goodsStatus.equals(ICMSConstant.GOOD_STATUS_NEW)
                        || goodsStatus.equals(ICMSConstant.GOOD_STATUS_IMPORTED_REGISTRATION)
                        || goodsStatus.equals(ICMSConstant.GOOD_STATUS_IMPORTED_NON_REGISTRATION))
                    && (pbrInd.equals(ICMSConstant.LOS_PAYMENT_INDICATOR_PBT)
                        || pbrInd.equals(ICMSConstant.LOS_PAYMENT_INDICATOR_NA))){
                    return true;
                }
            }else if(templateItem.getUsedWithFBR()==true){
                if((goodsStatus.equals(ICMSConstant.GOOD_STATUS_USED)
                        || goodsStatus.equals(ICMSConstant.GOOD_STATUS_RECONDITIONED))
                    && pbrInd.equals(ICMSConstant.LOS_PAYMENT_INDICATOR_PBT)){
                    return true;
                }
            }else if(templateItem.getUsedWithoutFBR()==true){
                if((goodsStatus.equals(ICMSConstant.GOOD_STATUS_USED)
                        || goodsStatus.equals(ICMSConstant.GOOD_STATUS_RECONDITIONED))
                    && (pbrInd.equals(ICMSConstant.LOS_PAYMENT_INDICATOR_PBR)
                        || pbrInd.equals(ICMSConstant.LOS_PAYMENT_INDICATOR_NA))){
                    return true;
                }
            }
            return false;
		}
	}

	public ITemplate getCollateralTemplate(String aCollateralType, String aCollateralSubType, String aCountry,
			String applicationType, String goodsStatus, String pbrInd, String preApproveDocFlag)
			throws CheckListTemplateException {

		ITemplate template = getCollateralTemplate(aCollateralType, aCollateralSubType, aCountry);

		if (template == null) {
			return null;
		}

		ITemplateItem[] itemList = template.getTemplateItemList();

		if (itemList == null || itemList.length == 0) {
			return null;
		}

		boolean preApproveDoc = ("P".equals(preApproveDocFlag));
		ArrayList filteredItemList = new ArrayList();

		for (int i = 0; i < itemList.length; i++) {
			IItem itemDetail = itemList[i].getItem();
			String[] applicableAppTypes = retrieveApplicationList(itemDetail);
			if ("A".equals(preApproveDocFlag) || itemDetail.getIsPreApprove() == preApproveDoc) {
				if (!isExpired(itemDetail) && (ArrayUtils.contains(applicableAppTypes, applicationType))) {

					if (!ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(aCollateralSubType)) {
						filteredItemList.add(itemList[i]);
					}
					else {
						IDynamicProperty[] propertyList = itemDetail.getPropertyList();
						boolean goodsStatusMatch = false;
						boolean pbrPbtIndMatch = false;
						if (propertyList != null) {
							for (int j = 0; j < propertyList.length && (!goodsStatusMatch || !pbrPbtIndMatch); j++) {
								if (!goodsStatusMatch && ("GOODS_STATUS").equals(propertyList[j].getPropertyCategory())
										&& propertyList[j].getPropertyValue().equals(goodsStatus)) {
									goodsStatusMatch = true;
									continue;
								}

								if (!pbrPbtIndMatch && "PBR_PBT_IND".equals(propertyList[j].getPropertyCategory())
										&& propertyList[j].getPropertyValue().equals(pbrInd)) {
									pbrPbtIndMatch = true;
								}
							}
						}

						if (goodsStatusMatch && pbrPbtIndMatch) {
							filteredItemList.add(itemList[i]);
						}
					}
				}
			}
		}

		if (filteredItemList.size() == 0) {
			template.setTemplateItemList(null);
		}
		else {
			template.setTemplateItemList((ITemplateItem[]) filteredItemList.toArray(new ITemplateItem[0]));
		}

		return template;
	}

	protected boolean isExpired(IItem anItem) {
		Date expiryDate = anItem.getExpiryDate();
		// DefaultLogger.debug(this, "EXPIRY DATE: " + expiryDate);
		if (expiryDate != null) {
            //Andy Wong, 21 May 2010: strip current time when comparing against expiry date, template expiry date doesnt include time
			Date currentDate = DateUtil.clearTime(DateUtil.getDate());
			// DefaultLogger.debug(this, "CURRENT: " + currentDate.getTime());
			// DefaultLogger.debug(this, "EXPIRY DATE: " +
			// expiryDate.getTime());
			if (expiryDate.getTime() < currentDate.getTime()) {
				return true;
			}
		}
		return false;
	}

	/*
	 * To check if a checklist is locked or not. A checklist is considered
	 * locked if one of its items is locked.
	 * 
	 * @param anICheckList of ICheckList type
	 * 
	 * @return boolean - true if checklist is locked annd false otherwise
	 * 
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 * on errors
	 */
	/*
	 * private boolean isCheckListLocked(ICheckList anICheckList) throws
	 * CheckListTemplateException { ICheckListItem[] itemList =
	 * anICheckList.getCheckListItemList(); if ((itemList == null) ||
	 * (itemList.length == 0)) { throw new
	 * CheckListTemplateException("There is no items under the checklist !!!");
	 * } for (int ii=0; ii<itemList.length; ii++) { if
	 * (itemList[ii].getIsLockedInd()) { return true; } } return false; }
	 */

	public String getLaw(String aCountry) throws CheckListTemplateException {
		String[] lawList = getLaw(aCountry, false);
		return (lawList == null) ? null : lawList[0];
	}

	/**
	 * Get law base on the country
	 * @param aCountry of String type
	 * @return String - the law for the specified country
	 * @throws CheckListTemplateException on errors
	 */
	public String[] getLaw(String aCountry, boolean allFlag) throws CheckListTemplateException {
		try {
			if (aCountry == null) {
				return null;
			}
			ICommonDataManager mgr = CommonDataManagerFactory.getManager();
			ICodeCategory codeCategory = mgr.getCodeCategory(ICMSConstant.CATEGORY_COUNTRY_LAW_MAP);
			if (codeCategory == null) {
				return null;
			}
			ICodeCategoryEntry[] codeEntryList = codeCategory.getEntries();
			if ((codeEntryList == null) || (codeEntryList.length == 0)) {
				return null;
			}

			ArrayList lawList = new ArrayList();
			for (int ii = 0; ii < codeEntryList.length; ii++) {
				if (codeEntryList[ii].getEntryCode().equals(aCountry)) {
					lawList.add(codeEntryList[ii].getEntryName());
					DefaultLogger.debug(this, "#################>>>>>>>>> Country: " + aCountry + "& The law is: "
							+ codeEntryList[ii].getEntryName());
					// return codeEntryList[ii].getEntryName();
					if (!allFlag) {
						break;
					}
				}
			}

			return (lawList.size() == 0) ? null : (String[]) lawList.toArray(new String[0]);
		}
		catch (Exception ex) {
			throw new CheckListTemplateException("Exception in getLaw(): " + ex.toString());
		}
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

	// Abstract methods to be implemented by the subclasses
	public abstract SearchResult getDocumentItemList(DocumentSearchCriteria aCriteria) throws SearchDAOException,
			CheckListTemplateException;// public abstract List

	// getCheckListItemMonitorList(int
	// monitorForDays,

	// String[] statusArray) throws CheckListTemplateException;
	public abstract ITemplate getTemplateByID(long aTemplateID) throws CheckListTemplateException;

	protected abstract ITemplate[] getCCTemplateList(String aLaw, String aLegalConstitution)
			throws CheckListTemplateException;

	protected abstract ITemplate[] getCCTemplateList(String aLaw, String aLegalConstitution, String aCountry)
			throws CheckListTemplateException;

	protected abstract ITemplate[] getCollateralTemplateList(String aCollateralType, String aCollateralSubType)
			throws CheckListTemplateException;

	protected abstract ITemplate[] getCollateralTemplateList(String aCollateralType, String aCollateralSubType,
			String aCountry) throws CheckListTemplateException;
	

	protected abstract ITemplate[] getCAMTemplateList(String aCollateralType, String aCollateralSubType,
			String aCountry) throws CheckListTemplateException;
	
	
	protected abstract ITemplate[] getFacilityTemplateList(String aCollateralType, String aCollateralSubType,
			String aCountry) throws CheckListTemplateException;

	/**
	 * Check that the document code entered is unique. Business Rule: Even
	 * deleted document code cannot be reused.
	 * 
	 * @param docCode - document code to be checked for uniqueness
	 * @param category - category of document to check for. Takes one of these 3
	 *        values: 1. ICMSConstant.DOC_TYPE_CC - for CC 2.
	 *        ICMSConstant.DOC_TYPE_SECURITY - for Security 3. null - will not
	 *        check against specific category (unique for both category)
	 * 
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is errors
	 *         in DAO
	 * 
	 * @return true if code is unique; false otherwise
	 */
	public abstract boolean isDocumentCodeUnique(String docCode, String category) throws SearchDAOException,
			CheckListTemplateException;

	/**
	 * Retrieves the set of dynamic properties for a given security subtype
	 * @param securitySubtype - security subtype to retrieve the dynamic
	 *        properties for
	 * @return set of dynamic properties of IDynamicPropertySetup for the given
	 *         subtype
	 * @throws SearchDAOException if errors during retrieval
	 */
	public abstract IDynamicPropertySetup[] getDynamicPropertySetup(String securitySubtype) throws SearchDAOException,
			CheckListTemplateException;

	/**
	 * Retrieves the transaction subtype by transaction id. Used for action
	 * redirection
	 * 
	 * @param transactionID - transaction id
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 * @throws com.integrosys.base.businfra.search.SearchDAOException is errors
	 *         in DAO
	 * @return transaction sub type of the given transaction id
	 */
	public abstract String getTrxSubTypeByTrxID(long transactionID) throws SearchDAOException,
			CheckListTemplateException;
	
	

}
