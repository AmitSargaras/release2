/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/AbstractCheckListBusManager.java,v 1.66 2006/10/09 05:41:15 hshii Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//java

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.chktemplate.bus.*;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.collateral.bus.CollateralDetailFactory;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeplant.ISpecificChargePlant;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargevehicle.ISpecificChargeVehicle;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
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
 * This abstract class will contains a business related logic that is
 * independent of any technology implementation such as EJB
 * 
 * @author $Author: Abhijit Rudrakshawar $<br>
 * @version $Revision: 1.66 $
 * @since $Date: 2006/10/09 05:41:15 $ Tag: $Name: $
 */
public abstract class AbstractCheckListBusManager implements ICheckListBusManager {

	private static final long serialVersionUID = -6343985575238704615L;

	/** key is HP/NHP, value is applicable application types, in array */
	private Map ccDocAppTypeMap = PropertiesConstantHelper.getTemplateCCDocApplicableApplicationTypesMap();

	/**
	 * Get the global items as well as those that are newly added at template
	 * level It will perform the following
	 * <ol>
	 * <li>Get the list of global doc if any
	 * <li>Get the list of item under the template that the checklist inherited
	 * from
	 * <li>Merge the 2 lists
	 * </ol>
	 * @param anICheckList - ICheckList
	 * @return IItem[] - the list of items that are in global as well as those
	 *         newly added at template level
	 * @throws CheckListException on errors
	 * @throws SearchDAOException is DAO errors
	 */
	public IItem[] getItemList(ICheckList anICheckList) throws CheckListTemplateException, CheckListException,
			SearchDAOException {
		ICheckListTemplateProxyManager templateProxy = CheckListTemplateProxyManagerFactory
				.getCheckListTemplateProxyManager();
		IItem[] itemList = templateProxy.getParentItemList(anICheckList.getCheckListType(), anICheckList
				.getTemplateID());         

		ICheckListItem[] checkListItemList = anICheckList.getCheckListItemList();
		itemList = filter(itemList, checkListItemList);
       // itemList = filterByType(itemList, anICheckList);

		return itemList;
	}

    /**
	 * Get the global items as well as those that are newly added at template
	 * level It will perform the following
	 * <ol>
	 * <li>Get the list of global doc if any
	 * <li>Get the list of item under the template that the checklist inherited
	 * from
	 * <li>Merge the 2 lists
	 * </ol>
	 * @param anICheckList - ICheckList
	 * @return IItem[] - the list of items that are in global as well as those
	 *         newly added at template level
	 * @throws CheckListException on errors
	 * @throws SearchDAOException is DAO errors
	 */
	public IItem[] getItemList(ICheckList anICheckList, String collateralID) throws CheckListTemplateException, CheckListException,
			SearchDAOException {
		ICheckListTemplateProxyManager templateProxy = CheckListTemplateProxyManagerFactory
				.getCheckListTemplateProxyManager();
        ICollateral col = getCollateral(new Long(collateralID).longValue(), true);
        String goodStatus = null, pbrInd = null;
        
        if(col instanceof ISpecificChargeVehicle){
            ISpecificChargeVehicle veh = (ISpecificChargeVehicle) col;
            goodStatus = veh.getGoodStatus();
            pbrInd = veh.getPBTIndicator();
        }else if (col instanceof ISpecificChargePlant){
            ISpecificChargePlant plant = (ISpecificChargePlant) col;
            goodStatus = plant.getGoodStatus();
        }

		IItem[] itemList = templateProxy.getParentItemList(anICheckList.getCheckListType(), anICheckList
				.getTemplateID(), goodStatus, pbrInd);
		ICheckListItem[] checkListItemList = anICheckList.getCheckListItemList();
		itemList = filter(itemList, checkListItemList);
       // itemList = filterByType(itemList, anICheckList);

		return itemList;
	}

	/**
	 * Filter off those items that are already in the checklist
	 * @param anItemList of type IItem[]
	 * @param aCheckListItemList of type ICheckListItem[] which contains the
	 *        list of checklist items
	 */
	private IItem[] filter(IItem[] anItemList, ICheckListItem[] aCheckListItemList) {
		List resultList = new ArrayList();
		if (aCheckListItemList == null) {
			return anItemList;
		}
		boolean found = false;
		for (int ii = 0; ii < anItemList.length; ii++) {
			for (int jj = 0; jj < aCheckListItemList.length; jj++) {
				
				if(null!=aCheckListItemList[jj].getItemCode()){ //Uma:Prod issue:Null Pointer Exception while get recurrent item list
					if (aCheckListItemList[jj].getItemCode().equals(anItemList[ii].getItemCode())
							&& !(aCheckListItemList[jj].getIsDeletedInd() || ICMSConstant.STATE_ITEM_DELETED
									.equals(aCheckListItemList[jj].getItemStatus()))) {
						found = true;
						break;
					}
				}
			}
			if (isExpired(anItemList[ii])) {
				found = true;
			}
			if (!found) {
				resultList.add(anItemList[ii]);
			}
			found = false;
		}
		return (IItem[]) resultList.toArray(new IItem[resultList.size()]);
	}

	private IItem[] filterByType(IItem[] itemList, ICheckList checklist) {
		if (itemList == null || itemList.length <= 0) {
			return itemList;
		}

		if (ICMSConstant.DOC_TYPE_CC.equals(checklist.getCheckListType())) {
			return filterByBorrowerType(itemList, checklist);
		}
		else {

			return filterByApplicationType(itemList, checklist);
		}
	}

	private IItem[] filterByBorrowerType(IItem[] itemList, ICheckList checklist) {
		List resultList = new ArrayList();
		boolean isForBorrower = ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(checklist.getCheckListOwner()
				.getSubOwnerType())
				|| ICMSConstant.CHECKLIST_JOINT_BORROWER.equals(checklist.getCheckListOwner().getSubOwnerType());
		boolean isForPledgor = ICMSConstant.CHECKLIST_PLEDGER.equals(checklist.getCheckListOwner().getSubOwnerType());

		for (int i = 0; i < itemList.length; i++) {
			if ((isForBorrower && itemList[i].getIsForBorrower()) || (isForPledgor && itemList[i].getIsForPledgor())) {
				String[] applicableAppTypes = retrieveApplicationList(itemList[i]);
				if (ArrayUtils.contains(applicableAppTypes, checklist.getApplicationType())) {
					resultList.add(itemList[i]);
				}
			}
		}
		return (IItem[]) resultList.toArray(new IItem[resultList.size()]);
	}

	private IItem[] filterByApplicationType(IItem[] itemList, ICheckList checklist) {
		List resultList = new ArrayList();

		for (int i = 0; i < itemList.length; i++) {
			String[] applicableAppTypes = retrieveApplicationList(itemList[i]);
			if (ArrayUtils.contains(applicableAppTypes, checklist.getApplicationType())) 
			{
				resultList.add(itemList[i]);
			}
		}
		return (IItem[]) resultList.toArray(new IItem[resultList.size()]);
	}

	/**
	 * Formulate the default checklist from the template selected based on the
	 * law, legal constitution and country
	 * @param anICheckListOwner - ICheckListOwner
	 * @param aLegalConstitution - String
	 * @param anIBookingLocation - IBookingLocation
	 * @return ICheckList - the default checklist
	 * @throws TemplateNotSetupException if there is no template setup for the
	 *         checklist
	 * @throws CheckListException on errors
	 */
	public ICheckList getDefaultCCCheckList(ICheckListOwner anICheckListOwner, String aLegalConstitution,
			IBookingLocation anIBookingLocation, String law) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException {
		if (anICheckListOwner instanceof ICCCheckListOwner) {
			if (anIBookingLocation == null) {
				TemplateNotSetupException exp = new TemplateNotSetupException("The Country is null or empty !!!");
				exp.setErrorCode(ICMSErrorCodes.NO_INSTR_BKG_LOCATION);
				throw exp;
			}
			ITemplate template = retrieveCCTemplate(aLegalConstitution, anIBookingLocation.getCountryCode(), law);
			ICheckList checkList = new OBCheckList(anICheckListOwner);
			checkList.setCheckListType(ICMSConstant.DOC_TYPE_CC);
			checkList.setCheckListLocation(anIBookingLocation);
			checkList.setApplicationType(anICheckListOwner.getApplicationType());
			setCCOwnerInfo((ICCCheckListOwner) anICheckListOwner);
			return convertToCheckList(checkList, template, null);
		}
		throw new CheckListException("Exception in getDefaultCCCheckList: "
				+ "The checklist owner must be of type ICCCheckListOwner");
	}

	/**
	 * For use by SI to retrieve the default cc checklist
	 * @param legalConstitution - legal constituition
	 * @param country - country
	 * @param law - law
	 * @return
	 * @throws TemplateNotSetupException if there is no template setup for the
	 *         checklist
	 * @throws CheckListTemplateException on errors
	 * @throws CheckListException on errors
	 */
	public ICheckList getDefaultCCCheckList(String customerType, String legalConstitution, String country, String law)
			throws TemplateNotSetupException, CheckListTemplateException, CheckListException {

		ICCCheckListOwner ccChecklistOwner = new OBCCCheckListOwner(ICMSConstant.LONG_INVALID_VALUE,
				ICMSConstant.LONG_INVALID_VALUE, customerType);
		ITemplate template = retrieveCCTemplate(legalConstitution, country, law);
		ICheckList checkList = new OBCheckList(ccChecklistOwner);
		checkList.setCheckListType(ICMSConstant.DOC_TYPE_CC);
		checkList.setCheckListLocation(new OBBookingLocation(country, null));
		return convertToCheckList(checkList, template, null);

	}

	private ITemplate retrieveCCTemplate(String legalConstitution, String country, String law)
			throws TemplateNotSetupException, CheckListTemplateException, CheckListException {
		if ((country == null) || (country.trim().length() == 0)) {
			TemplateNotSetupException exp = new TemplateNotSetupException("The country is null or empty !!!");
			exp.setErrorCode(ICMSErrorCodes.NO_INSTR_BKG_LOCATION);
			throw exp;
		}
		if ((legalConstitution == null) || (legalConstitution.trim().length() == 0)) {
			TemplateNotSetupException exp = new TemplateNotSetupException("The Legal Constitution is null or empty !!!");
			exp.setErrorCode(ICMSErrorCodes.NO_LEGAL_CONSTITUTION);
			throw exp;
		}

		ICheckListTemplateProxyManager templateProxy = CheckListTemplateProxyManagerFactory
				.getCheckListTemplateProxyManager();
		ITemplate template = templateProxy.getCCTemplate(law, legalConstitution, country);
		if (template == null) {
			throw new TemplateNotSetupException("Template with Law " + law + ", legalConst " + legalConstitution
					+ " and country " + country + " not setup !!!");
		}

		return template;
	}

	/**
	 * Formulate the default checklist from the template selected based on the
	 * collateral type, collateral subtype and country
	 * @param anICheckListOwner - ICheckListOwner
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @param aCountry - String
	 * @return ICheckList - the default checklist
	 * @throws TemplateNotSetupException if there is no template setup for the
	 *         checklist
	 * @throws CheckListException on errors
	 */
	public ICheckList getDefaultCollateralCheckList(ICheckListOwner anICheckListOwner, String aCollateralType,
			String aCollateralSubType, String aCountry, String aOrgCode) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException {
		if (anICheckListOwner instanceof ICollateralCheckListOwner) {
			ITemplate template = retrieveCollateralChecklist(aCountry, aCollateralType, aCollateralSubType);
			ICollateralCheckListOwner colOwner = (ICollateralCheckListOwner) anICheckListOwner;
			ICollateral col = getCollateral(colOwner.getCollateralID(), true);
			colOwner.setCollateralRef(col.getSCISecurityID());
			ICheckList checkList = new OBCheckList(colOwner);
			checkList.setCheckListType(ICMSConstant.DOC_TYPE_SECURITY);
			checkList.setApplicationType(colOwner.getApplicationType());
			IBookingLocation bkgLocation = new OBBookingLocation(aCountry, aOrgCode);
			checkList.setCheckListLocation(bkgLocation);
			// setCollateralOwnerInfo((ICollateralCheckListOwner)anICheckListOwner);
			return convertToCheckList(checkList, template, col);
		}
		throw new CheckListException("Exception in getDefaultCollateralCheckList: "
				+ "The checklist owner must be of type ICollateralCheckListOwner");
	}

	public ICheckList getDefaultCollateralCheckList(String country, String collateralType, String collateralSubType,
			String applicationType, String goodsStatus, String pbrInd) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException {
		try {
			ITemplate template = retrieveCollateralChecklist(country, collateralType, collateralSubType);
			ICheckList checkList = new OBCheckList();
			checkList.setCheckListType(ICMSConstant.DOC_TYPE_SECURITY);
			checkList.setApplicationType(applicationType);
			checkList.setCheckListLocation(new OBBookingLocation(country, null));
			ICollateral col = CollateralDetailFactory.getOB(new OBCollateralSubType(collateralSubType));
			if (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(collateralSubType)) {
				ISpecificChargeVehicle veh = (ISpecificChargeVehicle) col;
				veh.setGoodStatus(goodsStatus);
				veh.setPBTIndicator(pbrInd);
			}else if(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT.equals(collateralSubType)) {
                ISpecificChargePlant plant = (ISpecificChargePlant) col;
				plant.setGoodStatus(goodsStatus);
            }
			return convertToCheckList(checkList, template, col);

		}
		catch (CollateralException e) {
			throw new CheckListException("Exception in getDefaultCollateralCheckList: "
					+ "CollateralException Encountered - please check that collateral sub type is correct");
		}
	}
	
	public ICheckList getDefaultCAMCheckList(ICheckListOwner anICheckListOwner,String country, String collateralType, String collateralSubType,
			String applicationType, String goodsStatus, String pbrInd) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException {
		try {
			ITemplate template = retrieveCAMChecklist(country, collateralType, collateralSubType);
			ICollateralCheckListOwner colOwner = (ICollateralCheckListOwner) anICheckListOwner;
			ICheckList checkList = new OBCheckList(colOwner);
			if(collateralType.equals("O")){
			checkList.setCheckListType(ICMSConstant.DOC_TYPE_OTHER);
			}
			if(collateralType.equals("REC")){
				checkList.setCheckListType(ICMSConstant.DOC_TYPE_RECURRENT_MASTER);
				}
			if(collateralType.equals("CAM")){
				checkList.setCheckListType(ICMSConstant.DOC_TYPE_CAM);
				}
			//checkList.setApplicationType(applicationType);
			checkList.setCheckListLocation(new OBBookingLocation(country, null));
			//ICollateral col = CollateralDetailFactory.getOB(new OBCollateralSubType());
//			if (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(collateralSubType)) {
//				ISpecificChargeVehicle veh = (ISpecificChargeVehicle) col;
//				veh.setGoodStatus(goodsStatus);
//				veh.setPBTIndicator(pbrInd);
//			}else if(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT.equals(collateralSubType)) {
//                ISpecificChargePlant plant = (ISpecificChargePlant) col;
//				plant.setGoodStatus(goodsStatus);
//            }
			ICollateral col = null;
			return convertToCheckList(checkList, template, col);

		}catch (TemplateNotSetupException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in template "+e.getMessage());
			throw new TemplateNotSetupException("There is no template item !!!");
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in Parent "+e.getMessage());
			throw new CheckListException("Exception in getDefaultCollateralCheckList: "
					+ "CollateralException Encountered - please check that collateral sub type is correct");
		}
	}

	public ICheckList getDefaultPariPassuCheckList(ICheckListOwner anICheckListOwner ,String pariPassuType, String pariPassuSubType,
			String country, String orgcode) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException {
		try {
			ITemplate template = retrievePariPassuChecklist(country, pariPassuType, pariPassuSubType);
			ICollateralCheckListOwner colOwner = (ICollateralCheckListOwner) anICheckListOwner;
			ICheckList checkList = new OBCheckList(colOwner);
			checkList.setCheckListType(ICMSConstant.DOC_TYPE_PARIPASSU);
			//checkList.setApplicationType(applicationType);
			checkList.setCheckListLocation(new OBBookingLocation(country, null));
			//ICollateral col = CollateralDetailFactory.getOB(new OBCollateralSubType());
//			if (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(collateralSubType)) {
//				ISpecificChargeVehicle veh = (ISpecificChargeVehicle) col;
//				veh.setGoodStatus(goodsStatus);
//				veh.setPBTIndicator(pbrInd);
//			}else if(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT.equals(collateralSubType)) {
//                ISpecificChargePlant plant = (ISpecificChargePlant) col;
//				plant.setGoodStatus(goodsStatus);
//            }
			ICollateral col = null;
			return convertToCheckList(checkList, template, col);

		}
		catch (Exception e) {
			throw new CheckListException("Exception in getDefaultCollateralCheckList: "
					+ "CollateralException Encountered - please check that collateral sub type is correct");
		}
	}
	
	
	private ITemplate retrieveCAMChecklist(String country, String collateralType, String collateralSubType)
	throws TemplateNotSetupException, CheckListTemplateException, CheckListException {
		
		ICheckListTemplateProxyManager templateProxy = CheckListTemplateProxyManagerFactory
				.getCheckListTemplateProxyManager();
		ITemplate template = templateProxy.getCAMTemplate(collateralType, collateralSubType, country);
		if (template == null) {
			throw new TemplateNotSetupException("Template with collateral type " + collateralType + ", subType "
					+ collateralSubType + " and country " + country + " not setup !!!");
		}
		return template;
	}
	
	private ITemplate retrievePariPassuChecklist(String country, String pariPassuType, String pariPassuSubType)
	throws TemplateNotSetupException, CheckListTemplateException, CheckListException {
		
		ICheckListTemplateProxyManager templateProxy = CheckListTemplateProxyManagerFactory
				.getCheckListTemplateProxyManager();
		ITemplate template = templateProxy.getCAMTemplate(pariPassuType, pariPassuSubType, country);
		if (template == null) {
			throw new TemplateNotSetupException("Template with collateral type " + pariPassuType + ", subType "
					+ pariPassuSubType + " and country " + country + " not setup !!!");
		}
		return template;
	}
	
	public ICheckList getDefaultFacilityCheckList(ICheckListOwner anICheckListOwner,String country, String collateralType, String collateralSubType,
			String applicationType, String goodsStatus, String pbrInd) throws TemplateNotSetupException,
			CheckListTemplateException, CheckListException {
		try {
			ITemplate template = retrieveFacilityChecklist(country, collateralType, collateralSubType);
			ICollateralCheckListOwner colOwner = (ICollateralCheckListOwner) anICheckListOwner;
			ICheckList checkList = new OBCheckList(colOwner);
			checkList.setCheckListType(ICMSConstant.DOC_TYPE_FACILITY);
			//checkList.setApplicationType(applicationType);
			checkList.setCheckListLocation(new OBBookingLocation(country, null));
			//ICollateral col = CollateralDetailFactory.getOB(new OBCollateralSubType());
//			if (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(collateralSubType)) {
//				ISpecificChargeVehicle veh = (ISpecificChargeVehicle) col;
//				veh.setGoodStatus(goodsStatus);
//				veh.setPBTIndicator(pbrInd);
//			}else if(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT.equals(collateralSubType)) {
//                ISpecificChargePlant plant = (ISpecificChargePlant) col;
//				plant.setGoodStatus(goodsStatus);
//            }
			ICollateral col = null;
			if(template!=null){
			return convertToCheckList(checkList, template, col);
			}else{
				return null;
			}
		}
		catch (Exception e) {
			throw new CheckListException("Exception in getDefaultCollateralCheckList: "
					+ "CollateralException Encountered - please check that collateral sub type is correct");
		}
	}
	
	private ITemplate retrieveFacilityChecklist(String country, String collateralType, String collateralSubType)
	throws TemplateNotSetupException, CheckListTemplateException, CheckListException {
		
		ICheckListTemplateProxyManager templateProxy = CheckListTemplateProxyManagerFactory
				.getCheckListTemplateProxyManager();
		ITemplate template = templateProxy.getFacilityTemplate(collateralType, collateralSubType, country);
		// By Abhijit 5 September For proper error message Master template not found.
		/*if (template == null) {
			throw new TemplateNotSetupException("Template with collateral type " + collateralType + ", subType "
					+ collateralSubType + " and country " + country + " not setup !!!");
		}*/
		return template;
	}

	private ITemplate retrieveCollateralChecklist(String country, String collateralType, String collateralSubType)
			throws TemplateNotSetupException, CheckListTemplateException, CheckListException {

		ICheckListTemplateProxyManager templateProxy = CheckListTemplateProxyManagerFactory
				.getCheckListTemplateProxyManager();
		ITemplate template = templateProxy.getCollateralTemplate(collateralType, collateralSubType, country);
		if (template == null) {
			throw new TemplateNotSetupException("Template with collateral type " + collateralType + ", subType "
					+ collateralSubType + " and country " + country + " not setup !!!");
		}
		return template;
	}

	/**
	 * Convert an instance of a template to an instance of a checklist
	 * @param anICheckList - ICheckList
	 * @param anITemplate - ITemplate
	 * @return ICheckList - the checklist converted from the template
	 * @throws CheckListException on errors
	 */
	private ICheckList convertToCheckList(ICheckList anICheckList, ITemplate anITemplate, ICollateral collateral)
			throws TemplateNotSetupException, CheckListException {
		if (anICheckList == null) {
			throw new CheckListException("The ICheckList is null !!!");
		}
		if (anITemplate == null) {
			throw new CheckListException("The anITemplate is null !!!");
		}
		anICheckList.setTemplateID(anITemplate.getTemplateID());
		ITemplateItem[] itemList = anITemplate.getTemplateItemList();
		if ((itemList == null) || (itemList.length == 0)) {
			DefaultLogger.debug(this, " >>>>>>>>> template item list is empty");
			throw new TemplateNotSetupException("There is no template item !!!");
		}
		ICheckListItem[] checkListItemList = convertToCheckListItem(anICheckList, itemList, collateral);
		anICheckList.setCheckListItemList(checkListItemList);
		return anICheckList;
	}
	
	
	

	/**
	 * Convert a list of template items to a list of checklist items
	 * @param anITemplateItemList - ITemplateItem[]
	 * @return ICheckListItem[] - the list of checklist items converted
	 */
	private ICheckListItem[] convertToCheckListItem(ICheckList checklist, ITemplateItem[] anITemplateItemList,
			ICollateral collateral) throws TemplateNotSetupException {

		ArrayList itemList = new ArrayList();

		for (int i = 0; i < anITemplateItemList.length; i++) {
			if (!isExpired(anITemplateItemList[i])) {
				if(anITemplateItemList[i].getIsMandatoryDisplayInd()||anITemplateItemList[i].getIsMandatoryInd()){
				ICheckListItem item = convertToCheckListItem(checklist, anITemplateItemList[i], collateral);
				if (item != null) {
					itemList.add(item);
				}
				}
			}
		}
// As per HDFC Bank.
		/*if (itemList.size() == 0) {
			throw new TemplateNotSetupException("There is no template item !!!");
		}*/

		return (ICheckListItem[]) itemList.toArray(new ICheckListItem[0]);
	}

	private boolean isExpired(ITemplateItem anITemplateItem) {
		return isExpired(anITemplateItem.getItem());
	}

	// same method as in AbstractCheckListTemplateBusManager
	private boolean isExpired(IItem anItem) {
		Date expiryDate = anItem.getExpiryDate();
		if (expiryDate != null) {
            //Andy Wong, 21 May 2010: strip current time when comparing against expiry date, template expiry date doesnt include time
			Date currentDate = DateUtil.clearTime(DateUtil.getDate());
			if (expiryDate.getTime() < currentDate.getTime()) {
				return true;
			}
		}
		return false;
	}

	private ICheckListItem convertToCheckListItem(ICheckList checklist, ITemplateItem templateItem,
			ICollateral collateral) {
		if (ICMSConstant.DOC_TYPE_CC.equals(checklist.getCheckListType())) {
			return convertToCCCheckListItem(checklist, templateItem);
		}
		else if (ICMSConstant.DOC_TYPE_SECURITY.equals(checklist.getCheckListType())) {
			return convertToSecurityCheckListItem(checklist, templateItem, collateral);
		}
		else if (ICMSConstant.DOC_TYPE_CAM.equals(checklist.getCheckListType())) {
			return convertToCAMCheckListItem(checklist, templateItem);
		}else if (ICMSConstant.DOC_TYPE_FACILITY.equals(checklist.getCheckListType())) {
			return convertToFacilityCheckListItem(checklist, templateItem);
		}else if (ICMSConstant.DOC_TYPE_OTHER.equals(checklist.getCheckListType())) {
			return convertToOtherCheckListItem(checklist, templateItem);
		}else if (ICMSConstant.DOC_TYPE_RECURRENT_MASTER.equals(checklist.getCheckListType())) {
			return convertToRecurrentCheckListItem(checklist, templateItem);
		}

		return null;
	}

	private ICheckListItem convertToCCCheckListItem(ICheckList checklist, ITemplateItem templateItem) {

		String owner = checklist.getCheckListOwner().getSubOwnerType();
		IItem item = templateItem.getItem();
		//String docAppType = item.getLoanApplicationType();
		String checklistAppType = checklist.getApplicationType();
//		String[] applicableAppTypes = (String[]) ccDocAppTypeMap.get(docAppType);
		String[] applicableAppTypes = retrieveApplicationList(item);

		if (ArrayUtils.contains(applicableAppTypes, checklistAppType)) {
			if (ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(owner)
					|| ICMSConstant.CHECKLIST_JOINT_BORROWER.equals(owner)) {
				if (item.getIsForBorrower()) {
					OBCheckListItem checkListItem = new OBCheckListItem(templateItem);
					checkListItem.setIsMandatoryInd(templateItem.getIsMandatoryForBorrowerInd());
					checkListItem.setIsInherited(true);
					return checkListItem;
				}

			}
			else if (ICMSConstant.CHECKLIST_PLEDGER.equals(owner)) {
				if (item.getIsForPledgor()) {
					OBCheckListItem checkListItem = new OBCheckListItem(templateItem);
					checkListItem.setIsMandatoryInd(templateItem.getIsMandatoryForPledgorInd());
					checkListItem.setIsInherited(true);
					return checkListItem;
				}
			}
		}

		return null;

	}
	
	private ICheckListItem convertToCAMCheckListItem(ICheckList checklist, ITemplateItem templateItem) {

		//String owner = checklist.getCheckListOwner().getSubOwnerType();
	//	IItem item = templateItem.getItem();
		//String docAppType = item.getLoanApplicationType();
		//String checklistAppType = checklist.getApplicationType();
//		String[] applicableAppTypes = (String[]) ccDocAppTypeMap.get(docAppType);
		//String[] applicableAppTypes = retrieveApplicationList(item);

		
			
					OBCheckListItem checkListItem = new OBCheckListItem(templateItem);
				//	checkListItem.setIsMandatoryInd(templateItem.getIsMandatoryForPledgorInd());
					checkListItem.setIsInherited(true);
					return checkListItem;
			

	}
	
	private ICheckListItem convertToOtherCheckListItem(ICheckList checklist, ITemplateItem templateItem) {
					OBCheckListItem checkListItem = new OBCheckListItem(templateItem);
					checkListItem.setIsInherited(true);
					return checkListItem;
	}
	private ICheckListItem convertToRecurrentCheckListItem(ICheckList checklist, ITemplateItem templateItem) {
		OBCheckListItem checkListItem = new OBCheckListItem(templateItem);
		checkListItem.setIsInherited(true);
		checkListItem.setStatementType(templateItem.getStatementType());
		return checkListItem;
}
	
	private ICheckListItem convertToFacilityCheckListItem(ICheckList checklist, ITemplateItem templateItem) {
					OBCheckListItem checkListItem = new OBCheckListItem(templateItem);
					checkListItem.setIsInherited(true);
					return checkListItem;
	}


	private ICheckListItem convertToSecurityCheckListItem(ICheckList checklist, ITemplateItem templateItem,
			ICollateral collateral) {

		String secSubType = collateral.getCollateralSubType().getSubTypeCode();
        String secType = collateral.getCollateralType().getTypeCode();
		//String appType = templateItem.getItem().getLoanApplicationType();
		
		String[] applicableAppTypes = retrieveApplicationList(templateItem.getItem());        
// if condition removed	By Abhijit R 	
	//	if (ArrayUtils.contains(applicableAppTypes, checklist.getApplicationType())) {
			OBCheckListItem checkListItem = new OBCheckListItem(templateItem);
			checkListItem.setIsInherited(true);
// The (!) has been removed in the below if condition -- By Abhijit R 
            if (!((ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(secSubType) || ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT.equals(secSubType) || ICMSConstant.SECURITY_TYPE_PROPERTY.equals(secType)))
                            || (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(secSubType) && metTemplateCriteriaForVehicleSubtype(
                            templateItem, secSubType, collateral))
                            || (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT.equals(secSubType) && metTemplateCriteriaForPlantNEquipmentSubtype(
                            templateItem, secSubType, collateral))
                            || (ICMSConstant.SECURITY_TYPE_PROPERTY.equals(secType) && metTemplateCriteriaForAllPropertytype(
                            templateItem, secType, collateral))
                    ) {
                return checkListItem;
            }

            /***
                ABG-CLIMS P2B - FS Documentation V1.2.doc : Business Rule 5.6.2
                This is not usable anymore

                if ((!ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(secSubType))
                        || (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(secSubType) && metTemplateCriteriaForVehicleSubtype(
                                templateItem, secSubType, collateral))) {
                    return checkListItem;
                }

                return checkListItem;
			****/
		//}
// added by abhijit initially it was returning null changed to checklistItem.
		return checkListItem;
	}
    /*
        Mapping for security checklist

        New-With FBR		1
        New-Without FBR 	2
        Used-With FBT		3
        Used-Without FBT	4

            N	U	R	I	G
        PBR	1	4	4	1	1
        PBT	2	3	3	2	2
        NA	2	4	4	2	2
     */
    private boolean metTemplateCriteriaForPlantNEquipmentSubtype(ITemplateItem templateItem, String collateralSubType,
			ICollateral collateral) {

		if (!ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT.equals(collateralSubType)) {
			return true; // no filtering required for other types or CC
			// checklist
		}
		else {
			ISpecificChargePlant plant = (ISpecificChargePlant) collateral;

            if(templateItem.getNewWithFBR()==true){
                if (plant.getGoodStatus() != null) {
                    if(plant.getGoodStatus().equals(ICMSConstant.GOOD_STATUS_NEW)
                            || plant.getGoodStatus().equals(ICMSConstant.GOOD_STATUS_IMPORTED_REGISTRATION)
                            || plant.getGoodStatus().equals(ICMSConstant.GOOD_STATUS_IMPORTED_NON_REGISTRATION)){
                        return true;
                    }
                }
            }else if(templateItem.getNewWithoutFBR()==true){
                if (plant.getGoodStatus() != null) {
                    if(plant.getGoodStatus().equals(ICMSConstant.GOOD_STATUS_NEW)
                            || plant.getGoodStatus().equals(ICMSConstant.GOOD_STATUS_IMPORTED_REGISTRATION)
                            || plant.getGoodStatus().equals(ICMSConstant.GOOD_STATUS_IMPORTED_NON_REGISTRATION)){
                        return true;
                    }
                }
            }else if(templateItem.getUsedWithFBR()==true){
                if (plant.getGoodStatus() != null) {
                    if(plant.getGoodStatus().equals(ICMSConstant.GOOD_STATUS_USED)
                            || plant.getGoodStatus().equals(ICMSConstant.GOOD_STATUS_RECONDITIONED)){
                        return true;
                    }
                }
            }else if(templateItem.getUsedWithoutFBR()==true){
                if (plant.getGoodStatus() != null) {
                    if(plant.getGoodStatus().equals(ICMSConstant.GOOD_STATUS_USED)
                            || plant.getGoodStatus().equals(ICMSConstant.GOOD_STATUS_RECONDITIONED)){
                        return true;
                    }
                }
            }
            return false;
		}
	}

	private boolean metTemplateCriteriaForVehicleSubtype(ITemplateItem templateItem, String collateralSubType,
			ICollateral collateral) {

		if (!ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(collateralSubType)) {
			return true; // no filtering required for other types or CC
			// checklist
		}else {
			ISpecificChargeVehicle veh = (ISpecificChargeVehicle) collateral;

            if(templateItem.getNewWithFBR()==true){
                if (veh.getGoodStatus() != null) {
                    if((veh.getGoodStatus().equals(ICMSConstant.GOOD_STATUS_NEW)
                            || veh.getGoodStatus().equals(ICMSConstant.GOOD_STATUS_IMPORTED_REGISTRATION)
                            || veh.getGoodStatus().equals(ICMSConstant.GOOD_STATUS_IMPORTED_NON_REGISTRATION))
                        && veh.getPBTIndicator().equals(ICMSConstant.LOS_PAYMENT_INDICATOR_PBR)){
                        return true;
                    }
                }                
            }else if(templateItem.getNewWithoutFBR()==true){
                if (veh.getGoodStatus() != null) {
                    if((veh.getGoodStatus().equals(ICMSConstant.GOOD_STATUS_NEW)
                            || veh.getGoodStatus().equals(ICMSConstant.GOOD_STATUS_IMPORTED_REGISTRATION)
                            || veh.getGoodStatus().equals(ICMSConstant.GOOD_STATUS_IMPORTED_NON_REGISTRATION))
                        && (veh.getPBTIndicator().equals(ICMSConstant.LOS_PAYMENT_INDICATOR_PBT)
                            || veh.getPBTIndicator().equals(ICMSConstant.LOS_PAYMENT_INDICATOR_NA))){
                        return true;
                    }
                }
            }else if(templateItem.getUsedWithFBR()==true){
                if (veh.getGoodStatus() != null) {
                    if((veh.getGoodStatus().equals(ICMSConstant.GOOD_STATUS_USED)
                            || veh.getGoodStatus().equals(ICMSConstant.GOOD_STATUS_RECONDITIONED))
                        && veh.getPBTIndicator().equals(ICMSConstant.LOS_PAYMENT_INDICATOR_PBT)){
                        return true;
                    }
                }
            }else if(templateItem.getUsedWithoutFBR()==true){
                if (veh.getGoodStatus() != null) {
                    if((veh.getGoodStatus().equals(ICMSConstant.GOOD_STATUS_USED)
                            || veh.getGoodStatus().equals(ICMSConstant.GOOD_STATUS_RECONDITIONED))
                        && (veh.getPBTIndicator().equals(ICMSConstant.LOS_PAYMENT_INDICATOR_PBR)
                            || veh.getPBTIndicator().equals(ICMSConstant.LOS_PAYMENT_INDICATOR_NA))){
                        return true;
                    }
                }
            }
            return false;
		}

        /*if (!ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(collateralSubType)) {
			return true; // no filtering required for other types or CC
			// checklist
		}
		else {
			ISpecificChargeVehicle veh = (ISpecificChargeVehicle) collateral;
			String goodsCategory = "GOODS_STATUS";
			String pbtCategory = "PBR_PBT_IND";

			IDynamicProperty[] propertyList = templateItem.getItem().getPropertyList();
			if (propertyList == null) {
				DefaultLogger.warn(this, ">>>>>>>> dynamic property list is null!!!");
				return false;
			}

			boolean goodsStatusMatch = false;
			boolean pbrPbtIndMatch = false;
			for (int i = 0; i < propertyList.length && (!goodsStatusMatch || !pbrPbtIndMatch); i++) {
				if (!goodsStatusMatch && propertyList[i].getPropertyCategory().equals(goodsCategory)
						&& propertyList[i].getPropertyValue().equals(veh.getGoodStatus())) {
					goodsStatusMatch = true;
					continue;
				}

				if (!pbrPbtIndMatch && propertyList[i].getPropertyCategory().equals(pbtCategory)
						&& propertyList[i].getPropertyValue().equals(veh.getPBTIndicator())) {
					pbrPbtIndMatch = true;
				}
			}

			return goodsStatusMatch && pbrPbtIndMatch;
		}*/
	}

    private boolean metTemplateCriteriaForAllPropertytype(ITemplateItem templateItem, String secType,
                                                          ICollateral collateral) {

        if (!ICMSConstant.SECURITY_TYPE_PROPERTY.equals(secType)) {
            return true;
        } else {
            IPropertyCollateral property = (IPropertyCollateral) collateral;

            if (templateItem.getWithTitle() == true) {
                if (property.getMasterTitle() != null) {
                    if (property.getMasterTitle().equals("N")) {
                        return true;
                    }
                }
            } else if (templateItem.getWithoutTitle() == true) {
                if (property.getMasterTitle() != null) {
                    if (property.getMasterTitle().equals("Y")) {
                        return true;
                    }
                }
            } else if (templateItem.getUnderConstruction() == true) {
                if (property.getPropertyCompletionStatus() == 'N') {
                    return true;
                }
            } else if (templateItem.getPropertyCompleted() == true) {
                if (property.getPropertyCompletionStatus() == 'Y') {
                    return true;
                }
            }

            return false;
        }
    }

	/**
	 * Create a checklist
	 * @param anICheckList - ICheckList
	 * @return ICheckList - the checkList being created
	 * @throws CheckListException;
	 */
	public ICheckList create(ICheckList anICheckList) throws CheckListException {
		anICheckList.setCheckListStatus(getStatus(anICheckList));
		filterOffExpiredItems(anICheckList);
		return createCheckList(anICheckList);
	}

	private void filterOffExpiredItems(ICheckList anICheckList) {
		ICheckListItem[] itemList = anICheckList.getCheckListItemList();
		if ((itemList == null) || (itemList.length == 0)) {
			return;
		}
		ArrayList resultList = new ArrayList();
		for (int ii = 0; ii < itemList.length; ii++) {
			if (!ICMSConstant.STATE_ITEM_NOT_USED.equals(itemList[ii].getItemStatus())) {
				resultList.add(itemList[ii]);
			}
		}
		anICheckList.setCheckListItemList((ICheckListItem[]) resultList.toArray(new ICheckListItem[0]));
	}

	/**
	 * Update a checklist
	 * @param anICheckList - ICheckList
	 * @return ICheckList - the checkList being updated
	 * @throws ConcurrentUpdateException
	 * @throws CheckListException on errors
	 */
	public ICheckList update(ICheckList anICheckList) throws ConcurrentUpdateException, CheckListException {
		anICheckList.setCheckListStatus(getStatus(anICheckList));
		anICheckList.setAllowDeleteInd(getAllowDeleteInd(anICheckList));
		return updateCheckList(anICheckList);
	}

	/**
	 * Get the list of allowable operation for the checklist items
	 * @return ICheckListItemOperation[] - the list of allowed checklist item
	 *         operations
	 * @throws CheckListException on errors
	 */
	public ICheckListItemOperation[] getAllowableOperationList() throws CheckListException {
		try {
			ICheckListItemOperation[] itemList = getCheckListItemOperation();
			if (itemList == null) {
				return null;
			}
			List resultList = new ArrayList();
			String operationDesc = null;
			for (int ii = 0; ii < itemList.length; ii++) {
				if ((!itemList[ii].getState().startsWith("PENDING"))
						|| (itemList[ii].getOperation().startsWith("VIEW"))
						|| (itemList[ii].getOperation().equals("UPDATE"))) {
					operationDesc = itemList[ii].getOperation().replace('_', ' ');
					itemList[ii].setOperationDesc(operationDesc);
					resultList.add(itemList[ii]);
				}
			}
			return (ICheckListItemOperation[]) resultList.toArray(new ICheckListItemOperation[resultList.size()]);
		}
		catch (SearchDAOException ex) {
			throw new CheckListException("Exception in getAllowableOperationList", ex);
		}
	}

	/**
	 * Get law base on the country
	 * @param aCountry of String type
	 * @return String - the law for the specified country
	 * @throws CheckListException on errors
	 */
	public String getLaw(String aCountry) throws CheckListException {
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

			for (int ii = 0; ii < codeEntryList.length; ii++) {
				if (codeEntryList[ii].getEntryCode().equals(aCountry)) {
					return codeEntryList[ii].getEntryName();
				}
			}
			return null;
		}
		catch (Exception ex) {
			throw new CheckListException("Failed to retrieve 'COUNTRY_LAW_MAP' common code", ex);
		}
	}

	/**
	 * @deprecated replaced by getStatus - this is meant for history only. To
	 *             get the checklist status based on the checklist item status.
	 *             If any one of the item is deferred then the status will be
	 *             DEFERRED. (Deferred will take precedence over the rest)
	 * @param anICheckList of ICheckList type
	 * @return String - the checklist status
	 * @throws CheckListException on errors
	 */
	protected String getStatusOriginalLogic(ICheckList anICheckList) throws CheckListException {
		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>> In getStatus!!! ");
		if (anICheckList == null) {
			throw new CheckListException("The ICheckList is null !!!");
		}

		if (ICMSConstant.STATE_DELETED.equals(anICheckList.getCheckListStatus())
				|| ICMSConstant.STATE_CHECKLIST_OBSOLETE.equals(anICheckList.getCheckListStatus())) {
			return anICheckList.getCheckListStatus();
		}

		ICheckListItem[] itemList = anICheckList.getCheckListItemList();
		if ((itemList == null) || (itemList.length == 0)) {
			// throw new
			// CheckListException("There is no items under the checklist !!!");
			return ICMSConstant.STATE_CHECKLIST_IN_PROGRESS;
		}

		boolean isAllMandatoryPerfected = true;
		boolean isAllNonMandatoryPerfected = true;
		boolean isWaived = false;
		boolean isAllDeleted = true;
		for (int ii = 0; ii < itemList.length; ii++) {
			// DefaultLogger.debug(this, "ItemStatus: " +
			// itemList[ii].getItemStatus());
			if (!itemList[ii].getItemStatus().equals(ICMSConstant.STATE_ITEM_DELETED)) {
				if (itemList[ii].getItemStatus().equals(ICMSConstant.STATE_ITEM_DEFERRED)) {
					return ICMSConstant.STATE_CHECKLIST_DEFERRED;
				}
				if (itemList[ii].getItemStatus().equals(ICMSConstant.STATE_ITEM_WAIVED)) {
					isWaived = true;
				}
				if (!((itemList[ii].getItemStatus().equals(ICMSConstant.STATE_ITEM_COMPLETED))
						|| (itemList[ii].getItemStatus().equals(ICMSConstant.STATE_ITEM_EXPIRED)) || (itemList[ii]
						.getItemStatus().equals(ICMSConstant.STATE_ITEM_RENEWED)))) {
					if (itemList[ii].getIsMandatoryInd()) {
						isAllMandatoryPerfected = false;
					}
					else {
						isAllNonMandatoryPerfected = false;
					}
				}
				isAllDeleted = false;
			}
		}
		// if ((isPerfected) || (!isMandatory))
		if (isWaived) {
			return ICMSConstant.STATE_CHECKLIST_WAIVED;
		}
		DefaultLogger.debug(this, "isAllDeleted: " + isAllDeleted);
		if (!isAllDeleted) {
			if (isAllMandatoryPerfected && isAllNonMandatoryPerfected) {
				return ICMSConstant.STATE_CHECKLIST_COMPLETED;
			}
			if (isAllMandatoryPerfected && !isAllNonMandatoryPerfected) {
				return ICMSConstant.STATE_CHECKLIST_CERT_ALLOWED;
			}
		}
		return ICMSConstant.STATE_CHECKLIST_IN_PROGRESS;
	}

	private String getStatus(ICheckList anICheckList) throws CheckListException {
		if (anICheckList == null) {
			throw new CheckListException("The ICheckList is null !!!");
		}

		if (ICMSConstant.STATE_DELETED.equals(anICheckList.getCheckListStatus())
				|| ICMSConstant.STATE_CHECKLIST_OBSOLETE.equals(anICheckList.getCheckListStatus())) {
			return anICheckList.getCheckListStatus();
		}

		ICheckListItem[] itemList = anICheckList.getCheckListItemList();
		if ((itemList == null) || (itemList.length == 0)) {
			return ICMSConstant.STATE_CHECKLIST_IN_PROGRESS;
		}

		Map calculateCheckListStatusMap = new HashMap(2);

		for (int i = 0; i < itemList.length; i++) {
			if (!(PropertiesConstantHelper.isFilterPreApprovalDocuments() && itemList[i].getItem().getIsPreApprove() && !itemList[i]
					.getIsLockedInd())) {
				String itemStatus = itemList[i].getItemStatus();

				if (ICMSConstant.STATE_ITEM_AWAITING.equals(itemStatus)
						|| ICMSConstant.STATE_ITEM_DEFERRED.equals(itemStatus)
						|| ICMSConstant.STATE_TEMP_UPLIFTED.equals(itemStatus)
						|| ICMSConstant.STATE_ITEM_PENDING_REDEEM.equals(itemStatus)
						|| ICMSConstant.STATE_ITEM_PENDING_WAIVER.equals(itemStatus)
						|| ICMSConstant.STATE_ITEM_PENDING_DEFERRAL.equals(itemStatus)
						|| ICMSConstant.STATE_PENDING_RELODGE.equals(itemStatus)) {
					calculateCheckListStatusMap.put(ICMSConstant.STATE_CHECKLIST_IN_PROGRESS, null);
				}
				else {
					calculateCheckListStatusMap.put(ICMSConstant.STATE_CHECKLIST_COMPLETED, null);
				}
			}
		}

		try {
			if (calculateCheckListStatusMap.containsKey(ICMSConstant.STATE_CHECKLIST_IN_PROGRESS)) {
				return ICMSConstant.STATE_CHECKLIST_IN_PROGRESS;
			}
			else {
				return ICMSConstant.STATE_CHECKLIST_COMPLETED;
			}
		}
		finally {
			calculateCheckListStatusMap.clear();
		}
	}

	/**
	 * Get the allow delete indicator of a checklist
	 * @param anICheckList of ICheckList type
	 * @return boolean - true of checklist can be deleted
	 */
	private boolean getAllowDeleteInd(ICheckList anICheckList) {
		ICheckListItem[] itemList = anICheckList.getCheckListItemList();
		if ((itemList == null) || (itemList.length == 0)) {
			return true;
		}
		for (int ii = 0; ii < itemList.length; ii++) {
			if ((itemList[ii].getCustodianDocStatus() == null)
					&& !ICMSConstant.STATE_ITEM_DELETED.equals(itemList[ii].getItemStatus())) {
				return false;
			}
			if (itemList[ii].getIsInVaultInd() && (itemList[ii].getCustodianDocStatus() != null)
					&& !ICMSConstant.STATE_PERM_UPLIFTED.equals(itemList[ii].getCustodianDocStatus())) {
				return false;
			}
		}
		return true;
	}
	
	private String[] retrieveApplicationList(IItem itemList)
	{
		Collection docAppItemList = itemList.getCMRDocAppItemList();
		List applicableAppTypes = new ArrayList();
		if(docAppItemList != null)
		{
			Iterator iter1 = docAppItemList.iterator();
			while (iter1.hasNext()) 
			{
				IDocumentAppTypeItem tempDocumentAppTypeItem = (IDocumentAppTypeItem)iter1.next();
				applicableAppTypes.add(tempDocumentAppTypeItem.getAppType());
			}
		}
	
		
		return (String[]) applicableAppTypes.toArray(new String[0]);
	}

	public abstract ICheckList getCheckListByID(long aCheckListID) throws CheckListException;
	
	public abstract CheckListSearchResult getCheckListByCollateralID(long aCheckListID) throws CheckListException;

	public abstract CheckListSearchResult getCAMCheckListByCategoryAndProfileID(String category,long collateralID) throws CheckListException;
	
	
	public abstract CheckListSearchResult getPariPassuCheckListByCategoryAndProfileID(String category,long collateralID) throws CheckListException;
	
	protected abstract ICheckListItemOperation[] getCheckListItemOperation() throws SearchDAOException,
			CheckListException;

	public abstract ICheckList createCheckList(ICheckList anIChecklist) throws CheckListException;

	public abstract ICheckList updateCheckList(ICheckList anICheckList) throws ConcurrentUpdateException,
			CheckListException;

	protected abstract ICollateral getCollateral(long aCollateralID, boolean includeDetails) throws CheckListException;

	protected abstract void setCCOwnerInfo(ICCCheckListOwner anICCCheckListOwner) throws CheckListException;

	protected abstract void setCollateralOwnerInfo(ICollateralCheckListOwner anICCCheckListOwner)
			throws CheckListException;
	
}
