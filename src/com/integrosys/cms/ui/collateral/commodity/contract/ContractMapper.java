/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/contract/ContractMapper.java,v 1.10 2004/10/11 02:20:36 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.contract;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.commodity.IApprovedCommodityType;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.IContract;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBContract;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.QuantityDifferential;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.ISupplier;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2004/10/11 02:20:36 $ Tag: $Name: $
 */

public class ContractMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		ContractForm aForm = (ContractForm) cForm;

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		int indexID = Integer.parseInt((String) inputs.get("indexID"));
		OBContract obToChange = null;
		HashMap trxValueMap = (HashMap) inputs.get("commodityMainTrxValue");
		ICommodityCollateral[] colStaging = (ICommodityCollateral[]) trxValueMap.get("staging");
		ICommodityCollateral[] colActual = (ICommodityCollateral[]) trxValueMap.get("actual");

		if (indexID == -1) {
			obToChange = new OBContract();
		}
		else {
			try {
				int secIndexID = Integer.parseInt((String) inputs.get("secIndexID"));
				obToChange = (OBContract) AccessorUtil.deepClone(colStaging[secIndexID].getContracts()[indexID]);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}

		// set the approved commodity type from actual commodity - the approved
		// by checker
		IApprovedCommodityType[] appCommTypeList = null;
		if (colActual != null) {
			boolean found = false;
			for (int i = 0; !found && (i < colActual.length); i++) {
				if (String.valueOf(colActual[i].getSCISecurityID()).equals(aForm.getSecurityID())) {
					found = true;
					appCommTypeList = colActual[i].getApprovedCommodityTypes();
				}
			}
		}
		boolean found = false;
		if (appCommTypeList != null) {
			for (int i = 0; !found && (i < appCommTypeList.length); i++) {
				IProfile tempProfile = appCommTypeList[i].getProfile();
				if ((tempProfile != null)
						&& String.valueOf(tempProfile.getProfileID()).equals(aForm.getProductSubType())) {
					found = true;
					obToChange.setApprovedCommodityType(appCommTypeList[i]);
					ISupplier[] tempSupplier = tempProfile.getSuppliers();
					if (tempSupplier != null) {
						boolean foundSupplier = false;
						for (int j = 0; !foundSupplier && (j < tempSupplier.length); j++) {
							if (String.valueOf(tempSupplier[j].getSupplierID()).equals(aForm.getSupplier())) {
								foundSupplier = true;
								obToChange.setSupplier(tempSupplier[j]);
							}
						}
					}
				}
			}
		}

		// the approved commodity type is not in the actual list
		// the approved commodity has been deleted, but still need to remain in
		// the contract
		if (!found) {
			IApprovedCommodityType tmpACT = obToChange.getApprovedCommodityType();
			if (tmpACT != null) {
				IProfile tempProfile = tmpACT.getProfile();
				if ((tempProfile != null)
						&& String.valueOf(tempProfile.getProfileID()).equals(aForm.getProductSubType())) {
					found = true;
					obToChange.setApprovedCommodityType(tmpACT);
					ISupplier[] tempSupplier = tempProfile.getSuppliers();
					if (tempSupplier != null) {
						boolean foundSupplier = false;
						for (int j = 0; !foundSupplier && (j < tempSupplier.length); j++) {
							if (String.valueOf(tempSupplier[j].getSupplierID()).equals(aForm.getSupplier())) {
								foundSupplier = true;
								obToChange.setSupplier(tempSupplier[j]);
							}
						}
					}
				}
			}
		}

		obToChange.setMaturityDate(CollateralMapper.compareDate(locale, obToChange.getMaturityDate(), aForm
				.getContractMaturityDate()));
		if (isEmptyOrNull(aForm.getMinShippingFreq())) {
			obToChange.setMinShippingFrequency(0);
		}
		else {
			obToChange.setMinShippingFrequency(Integer.parseInt(aForm.getMinShippingFreq()));
		}
		obToChange.setMinShippingFrequencyUnit(aForm.getMinShippingFreqUOM());
		obToChange.setLastShipmentDate(CollateralMapper.compareDate(locale, obToChange.getLastShipmentDate(), aForm
				.getLastShipmentDate()));
		obToChange.setMainContractNumber(aForm.getMainContractNo());
		obToChange.setContractPrice(UIUtil.convertToAmount(locale, aForm.getMainContractPriceCcy(), aForm
				.getMainContractPrice()));

		BigDecimal contractQty = null;
		if (isEmptyOrNull(aForm.getContractQty())) {
			contractQty = null;
		}
		else {
			try {
				contractQty = UIUtil.mapStringToBigDecimal(aForm.getContractQty());
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}

		UOMWrapper unit = null;
		HashMap uomMap = (HashMap) inputs.get("uomMap");
		if (isEmptyOrNull(aForm.getContractUOM())) {
			unit = null;
		}
		else {
			unit = ((UOMWrapper) uomMap.get(aForm.getContractUOM()));
		}

		if (unit != null) {
			obToChange.setContractedQty(new Quantity(contractQty, unit));
		}
		else {
			obToChange.setContractedQty(null);
		}

		obToChange.setRemarks(aForm.getContractRemarks());
		QuantityDifferential qtyDiff = null;
		BigDecimal qtyDiffVal = null;
		if (isEmptyOrNull(aForm.getQuantityDiff())) {
			qtyDiff = null;
		}
		else {
			qtyDiffVal = UIUtil.mapStringToBigDecimal(aForm.getQuantityDiff());
		}

		if (!isEmptyOrNull(aForm.getDiffQuantitysign())) {
			Quantity qty = new Quantity(qtyDiffVal, unit);
			qtyDiff = new QuantityDifferential(qty, aForm.getDiffQuantitysign());
		}
		obToChange.setQtyDifferential(qtyDiff);
		// obToChange.setQtyDifferential(aForm.getDiffQuantitysign());

		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		ContractForm aForm = (ContractForm) cForm;
		HashMap contractMap = (HashMap) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		IContract contract = (IContract) contractMap.get("obj");
		IApprovedCommodityType appCommType = contract.getApprovedCommodityType();
		IProfile profile = null;
		if (appCommType != null) {
			profile = appCommType.getProfile();
		}

		aForm.setSecurityID((String) contractMap.get("securityID"));
		if (profile != null) {
			aForm.setSecuritySubType(profile.getCategory());
			aForm.setProductType(profile.getProductType());
			if (aForm.getEvent().equals(ContractAction.EVENT_READ)) {
				aForm.setProductSubType(profile.getProductSubType());
			}
			else {
				aForm.setProductSubType(String.valueOf(profile.getProfileID()));
			}
		}
		ISupplier supplier = contract.getSupplier();
		if (supplier != null) {
			if (aForm.getEvent().equals(ContractAction.EVENT_READ)) {
				aForm.setSupplier(supplier.getName());
			}
			else {
				aForm.setSupplier(String.valueOf(supplier.getSupplierID()));
			}
		}
		aForm.setContractMaturityDate(DateUtil.formatDate(locale, contract.getMaturityDate()));
		aForm.setMinShippingFreq(String.valueOf(contract.getMinShippingFrequency()));
		if (!isEmptyOrNull(contract.getMinShippingFrequencyUnit())) {
			aForm.setMinShippingFreqUOM(contract.getMinShippingFrequencyUnit().trim());
		}
		else {
			aForm.setMinShippingFreqUOM(null);
		}
		aForm.setLastShipmentDate(DateUtil.formatDate(locale, contract.getLastShipmentDate()));
		aForm.setMainContractNo(contract.getMainContractNumber());

		if (contract.getContractPrice() != null) {
			aForm.setMainContractPriceCcy(contract.getContractPrice().getCurrencyCode());
			if (contract.getContractPrice().getAmount() >= 0) {
				try {
					aForm.setMainContractPrice(UIUtil.formatNumber(contract.getContractPrice().getAmountAsBigDecimal(),
							6, locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
		}
		else {
			aForm.setMainContractPriceCcy("");
			aForm.setMainContractPrice("");
		}

		if (contract.getMainContractAmount() != null) {
			aForm.setMainContractAmtCcy(contract.getMainContractAmount().getCurrencyCode());
			if (contract.getMainContractAmount().getAmount() >= 0) {
				try {
					aForm.setMainContractAmt(UIUtil.formatNumber(contract.getMainContractAmount()
							.getAmountAsBigDecimal(), 0, locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
		}
		else {
			aForm.setMainContractAmtCcy("");
			aForm.setMainContractAmt("");
		}

		if (contract.getContractedQty() != null) {
			if (contract.getContractedQty().getQuantity() != null) {
				try {
					aForm.setContractQty(UIUtil.formatNumber(contract.getContractedQty().getQuantity(), 4, locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
			else {
				aForm.setContractQty("");
			}
			if (contract.getContractedQty().getUnitofMeasure() != null) {
				if (ContractAction.EVENT_READ.equals(aForm.getEvent())) {
					aForm.setContractUOM(contract.getContractedQty().getUnitofMeasure().getLabel());
				}
				else {
					aForm.setContractUOM(contract.getContractedQty().getUnitofMeasure().getID());
				}
			}
			else {
				aForm.setContractUOM("");
			}
		}
		else {
			aForm.setContractQty("");
			aForm.setContractUOM("");
		}

		aForm.setContractRemarks(contract.getRemarks());
		if (contract.getQtyDifferential() != null) {
			if (contract.getQtyDifferential().getSign() != null) {
				aForm.setDiffQuantitysign(contract.getQtyDifferential().getSign().getName());
			}
			else {
				aForm.setDiffQuantitysign("");
			}
			if (contract.getQtyDifferential().getQuantity() != null) {
				try {
					aForm.setQuantityDiff(UIUtil.formatNumber(
							contract.getQtyDifferential().getQuantity().getQuantity(), 0, locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
			else {
				aForm.setQuantityDiff("");
			}
		}
		else {
			aForm.setDiffQuantitysign("");
			aForm.setQuantityDiff("");
		}
		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "secIndexID", "java.lang.String", REQUEST_SCOPE },
				{ "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "uomMap", "java.util.HashMap", SERVICE_SCOPE }, });
	}
}
