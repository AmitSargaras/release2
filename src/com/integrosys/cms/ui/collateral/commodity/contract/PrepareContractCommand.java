/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/contract/PrepareContractCommand.java,v 1.6 2004/10/20 08:56:14 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.contract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.IApprovedCommodityType;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.IContract;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.common.UOMWrapperComparator;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.ISupplier;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.TimeFreqList;
import com.integrosys.cms.ui.commodityglobal.CommodityCategoryList;
import com.integrosys.cms.ui.common.CurrencyList;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/10/20 08:56:14 $ Tag: $Name: $
 */

public class PrepareContractCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "secIndexID", "java.lang.String", REQUEST_SCOPE },
				{ "securityID", "java.lang.String", REQUEST_SCOPE },
				{ "productType", "java.lang.String", REQUEST_SCOPE },
				{ "productSubType", "java.lang.String", REQUEST_SCOPE },
				{ "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "timeFreqID", "java.util.Collection", REQUEST_SCOPE },
				{ "timeFreqValue", "java.util.Collection", REQUEST_SCOPE },
				{ "secID", "java.util.Collection", REQUEST_SCOPE },
				{ "secValue", "java.util.Collection", REQUEST_SCOPE },
				{ "productID", "java.util.Collection", REQUEST_SCOPE },
				{ "productValue", "java.util.Collection", REQUEST_SCOPE },
				{ "productSubID", "java.util.Collection", REQUEST_SCOPE },
				{ "productSubValue", "java.util.Collection", REQUEST_SCOPE },
				{ "supplierID", "java.util.Collection", REQUEST_SCOPE },
				{ "supplierValue", "java.util.Collection", REQUEST_SCOPE },
				{ "currencyCode", "java.util.Collection", REQUEST_SCOPE },
				{ "unitOfMeasureID", "java.util.Collection", REQUEST_SCOPE },
				{ "unitOfMeasureValue", "java.util.Collection", REQUEST_SCOPE },
				{ "uomMap", "java.util.HashMap", SERVICE_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		TimeFreqList timeFreqList = TimeFreqList.getInstance();
		result.put("timeFreqID", timeFreqList.getTimeFreqID());
		result.put("timeFreqValue", timeFreqList.getTimeFreqValue());

		HashMap trxValueMap = (HashMap) map.get("commodityMainTrxValue");
		ICommodityCollateral[] colStaging = (ICommodityCollateral[]) trxValueMap.get("staging");
		ICommodityCollateral[] colActual = (ICommodityCollateral[]) trxValueMap.get("actual");
		int ind = -1;
		int secInd = -1;
		String index = (String) map.get("indexID");
		String secIndex = (String) map.get("secIndexID");
		if ((index != null) && (secIndex != null)) {
			ind = Integer.parseInt(index);
			secInd = Integer.parseInt(secIndex);
		}

		Collection secID = new ArrayList();
		if (colActual != null) {
			for (int i = 0; i < colActual.length; i++) {
				if (colActual[i].getApprovedCommodityTypes() != null) {
					boolean hasApprovedACT = false;
					IApprovedCommodityType[] actList = colActual[i].getApprovedCommodityTypes();
					for (int j = 0; !hasApprovedACT && (j < actList.length); j++) {
						if (actList[j].getApprovedCommodityTypeID() > 0) {
							hasApprovedACT = true;
						}
					}
					if (((secInd != -1) && (secInd == i))
							|| (hasApprovedACT && !colActual[i].getStatus().equals(ICMSConstant.STATE_DELETED) && !colActual[i]
									.getStatus().equals(ICMSConstant.STATE_PENDING_DELETE))) {
						secID.add(String.valueOf(colActual[i].getSCISecurityID()));
					}
				}
			}
		}

		result.put("secID", secID);
		result.put("secValue", secID);

		String event = (String) map.get("event");
		String securityID = (String) map.get("securityID");
		String productType = (String) map.get("productType");
		String productSubType = (String) map.get("productSubType");
		DefaultLogger.debug(this, "event: " + event + "\tproductType: " + productType + "\tproductSubType: "
				+ productSubType);
		long profileID = ICMSConstant.LONG_INVALID_VALUE;
		if ((productSubType != null) && (productSubType.length() > 0)) {
			profileID = Long.parseLong(productSubType);
		}

		IApprovedCommodityType[] appCommTypeList = null;
		String category = null;
		if (event.equals(ContractAction.EVENT_PREPARE_UPDATE_SUB)) {
			if (secInd != -1) {
				String sciSecurityID = colStaging[secInd].getSCISecurityID();
				boolean foundSec = false;
				for (int i = 0; !foundSec && (i < colActual.length); i++) {
					if (colActual[i].getSCISecurityID().equals(sciSecurityID)) {
						appCommTypeList = colActual[i].getApprovedCommodityTypes();
						category = colActual[i].getCollateralSubType().getSubTypeCode();
						foundSec = true;
					}
				}
			}
			if ((secInd != -1) && (ind != -1)) {
				IContract contract = colStaging[secInd].getContracts()[ind];
				if (contract.getApprovedCommodityType() != null) {
					IProfile conProfile = contract.getApprovedCommodityType().getProfile();
					productType = conProfile.getProductType();
					productSubType = conProfile.getProductSubType();
					profileID = conProfile.getProfileID();
					category = conProfile.getCategory();
				}
				else {
					productType = null;
					productSubType = null;
					profileID = ICMSConstant.LONG_INVALID_VALUE;
					category = null;
				}
			}
		}
		else if (event.equals(ContractAction.EVENT_REFRESH) || event.equals(ContractAction.EVENT_PREPARE)) {
			if ((colActual != null) && (securityID != null) && (securityID.length() > 0)) {
				/*
				 * if (secInd != -1) { if
				 * (String.valueOf(col[secInd].getSCISecurityID
				 * ()).equals(securityID)) { appCommTypeList =
				 * col[secInd].getApprovedCommodityTypes(); category =
				 * col[secInd].getCollateralSubType().getSubTypeCode(); } } else
				 * {
				 */
				boolean found = false;
				for (int i = 0; !found && (i < colActual.length); i++) {
					if (String.valueOf(colActual[i].getSCISecurityID()).equals(securityID)) {
						found = true;
						appCommTypeList = colActual[i].getApprovedCommodityTypes();
						category = colActual[i].getCollateralSubType().getSubTypeCode();
					}
				}
				// }
			}
		}
		DefaultLogger.debug(this, "profileID: " + profileID);
		if (appCommTypeList == null) {
			result.put("productID", new ArrayList());
			result.put("productValue", new ArrayList());
			result.put("productSubID", new ArrayList());
			result.put("productSubValue", new ArrayList());
			result.put("supplierID", new ArrayList());
			result.put("supplierValue", new ArrayList());
		}
		else {
			Collection productTypeID = new ArrayList();
			Collection productTypeValue = new ArrayList();
			Collection productSubTypeID = new ArrayList();
			Collection productSubTypeValue = new ArrayList();
			Collection supplierID = new ArrayList();
			Collection supplierValue = new ArrayList();
			CommodityCategoryList categoryList = CommodityCategoryList.getInstance();
			for (int i = 0; i < appCommTypeList.length; i++) {
				if (appCommTypeList[i].getApprovedCommodityTypeID() > 0) {
					IProfile tempProfile = appCommTypeList[i].getProfile();
					if (!productTypeID.contains(tempProfile.getProductType())) {
						productTypeID.add(tempProfile.getProductType());
						productTypeValue.add(categoryList.getCommProductItem(category, tempProfile.getProductType()));
					}
					if ((productType != null) && (productType.length() > 0)) {
						if (tempProfile.getProductType().equals(productType)) {
							if (!productSubTypeID.contains(String.valueOf(tempProfile.getProfileID()))) {
								productSubTypeID.add(String.valueOf(tempProfile.getProfileID()));
								productSubTypeValue.add(tempProfile.getProductSubType());
							}
						}
					}
					if ((profileID != -1) && (tempProfile.getProfileID() == profileID)) {
						ISupplier[] tempSupplier = tempProfile.getSuppliers();
						if (tempSupplier != null) {
							for (int j = 0; j < tempSupplier.length; j++) {
								supplierID.add(String.valueOf(tempSupplier[j].getSupplierID()));
								supplierValue.add(tempSupplier[j].getName());
							}
						}
					}
				}
			}
			if ((secInd != -1) && (ind != -1)) {
				IContract contract = colStaging[secInd].getContracts()[ind];
				IProfile conProfile = contract.getApprovedCommodityType().getProfile();
				if ((productType != null) && productType.equals(conProfile.getProductType())) {
					productType = conProfile.getProductType();
					if (!productTypeID.contains(productType)) {
						productTypeID.add(productType);
						productTypeValue.add(categoryList.getCommProductItem(category, productType));
					}
					if (!productSubTypeID.contains(String.valueOf(conProfile.getProfileID()))) {
						productSubTypeID.add(String.valueOf(conProfile.getProfileID()));
						productSubTypeValue.add(conProfile.getProductSubType());
					}

					if ((profileID != -1) && (profileID == conProfile.getProfileID())) {
						ISupplier[] tempSupplier = conProfile.getSuppliers();
						if (tempSupplier != null) {
							for (int j = 0; j < tempSupplier.length; j++) {
								if (!supplierID.contains(String.valueOf(tempSupplier[j].getSupplierID()))) {
									supplierID.add(String.valueOf(tempSupplier[j].getSupplierID()));
									supplierValue.add(tempSupplier[j].getName());
								}
							}
						}
					}
				}
			}

			result.put("productID", productTypeID);
			result.put("productValue", productTypeValue);
			result.put("productSubID", productSubTypeID);
			result.put("productSubValue", productSubTypeValue);
			result.put("supplierID", supplierID);
			result.put("supplierValue", supplierValue);
		}

		UOMWrapper[] uomList = null;
		Collection uomID = new ArrayList();
		Collection uomValue = new ArrayList();
		HashMap uomMap = new HashMap();
		try {
			ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
			uomList = proxy.getUnitofMeasure(profileID);
			if (uomList != null) {
				Arrays.sort(uomList, new UOMWrapperComparator());
				for (int i = 0; i < uomList.length; i++) {
					uomID.add(uomList[i].getID());
					uomValue.add(uomList[i].getLabel());
					uomMap.put(uomList[i].getID(), uomList[i]);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}

		result.put("unitOfMeasureID", uomID);
		result.put("unitOfMeasureValue", uomValue);
		result.put("uomMap", uomMap);

		CurrencyList currList = CurrencyList.getInstance();
		result.put("currencyCode", currList.getCountryValues());

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
