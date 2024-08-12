package com.integrosys.cms.ui.collateral.property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.OBCollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.OBLimitCharge;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.CollateralMapper;

/**
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.29 $
 * @since $Date: 2006/06/21 06:40:33 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 25, 2003 Time: 4:56:59 PM
 * To change this template use Options | File Templates.
 */
public class ChargeMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		ChargeForm aForm = (ChargeForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug(this + " - mapFormToOB", "Locale is: " + locale);

		IPropertyCollateral iProperty = (IPropertyCollateral) (((ICollateralTrxValue) inputs.get("serviceColObj"))
				.getStagingCollateral());
		ILimitCharge[] obLimitCharge = iProperty.getLimitCharges();

		DefaultLogger.debug(this, "indexID  is:" + inputs.get("indexID") + ":");

		int index = Integer.parseInt((String) inputs.get("indexID"));

		ILimitCharge obToChange = null;
		if (index == -1) {
			obToChange = new OBLimitCharge();

		}
		else {
			try {
				obToChange = (ILimitCharge) AccessorUtil.deepClone(obLimitCharge[index]);
			}
			catch (Exception e) {
				DefaultLogger.debug(this, e.getMessage());
			}
		}
		Date stageDate;
		try {
			DefaultLogger.debug(this, "entering charge mapper - mapFormToOB");
			obToChange.setChargeCcyCode(iProperty.getCurrencyCode());
			obToChange.setPriorChargeCcyCode(iProperty.getCurrencyCode());

			ICollateralLimitMap[] colLimitMap = iProperty.getCollateralLimits();
			ICollateralLimitMap[] chargeLimit = obToChange.getLimitMaps();
			String[] limitId = aForm.getLimitID();
			DefaultLogger.debug(this, "limitId is: " + limitId);

			DefaultLogger.debug(this, "limitId size  is: " + limitId.length);
			OBCollateralLimitMap[] newLimitMap = new OBCollateralLimitMap[limitId.length];
			for (int i = 0; i < limitId.length; i++) {
				boolean sameID = false;
				for (int j = 0; (j < colLimitMap.length) && !sameID; j++) {
					if (limitId[i].equals(String.valueOf(colLimitMap[j].getChargeID()))) {
						DefaultLogger.debug(this, "Entering same limit id: " + limitId[i]);
						sameID = true;
						newLimitMap[i] = (OBCollateralLimitMap) colLimitMap[j];
					}
				}
				if (chargeLimit != null) {
					for (int j = 0; (j < chargeLimit.length) && !sameID; j++) {
						if (limitId[i].equals(String.valueOf(colLimitMap[j].getChargeID()))) {
							sameID = true;
							newLimitMap[i] = (OBCollateralLimitMap) chargeLimit[j];
						}
					}
				}
			}
			DefaultLogger.debug(this + " new", "newLimitMap size: " + newLimitMap.length);
			obToChange.setLimitMaps(newLimitMap);
			DefaultLogger.debug(this, "obToChange limitMap size  is: " + obToChange.getLimitMaps().length);
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getRank())) {
				obToChange.setSecurityRank(Integer.parseInt(aForm.getRank().trim()));
			}

			if (AbstractCommonMapper.isEmptyOrNull(aForm.getChargeAmount())) {
				obToChange.setChargeAmount(null);
			}
			else {
				obToChange.setChargeAmount(CurrencyManager.convertToAmount(locale, obToChange.getChargeCcyCode(), aForm
						.getChargeAmount()));
			}

			if (AbstractCommonMapper.isEmptyOrNull(aForm.getPriorChargeAmount())) {
				obToChange.setPriorChargeAmount(null);
			}
			else {
				obToChange.setPriorChargeAmount(CurrencyManager.convertToAmount(locale, obToChange
						.getPriorChargeCcyCode(), aForm.getPriorChargeAmount()));
			}

			obToChange.setPriorChargeChargee(aForm.getChargeePriorCharge());

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDateLegalCharge())) {
				stageDate = CollateralMapper.compareDate(locale, obToChange.getLegalChargeDate(), aForm
						.getDateLegalCharge());
				obToChange.setLegalChargeDate(stageDate);
			}
			else {
				obToChange.setLegalChargeDate(null);
			}

			obToChange.setNatureOfCharge(aForm.getNatureOfCharge());

			obToChange.setChargeType(aForm.getChargeType());
			obToChange.setPriorChargeType(aForm.getPriorChargeType());

			// Set Caveat Information
			if (StringUtils.isNotBlank(aForm.getCaveatWaivedInd())) {
				if (ICMSConstant.TRUE_VALUE.equals(aForm.getCaveatWaivedInd())) {
					obToChange.setCaveatWaivedInd(Boolean.TRUE);
				}
				else if (ICMSConstant.FALSE_VALUE.equals(aForm.getCaveatWaivedInd())) {
					obToChange.setCaveatWaivedInd(Boolean.FALSE);
				}
			}
			else {
				obToChange.setCaveatWaivedInd(null);
			}

			obToChange.setCaveatReferenceNo(aForm.getCaveatReferenceNo());

			stageDate = CollateralMapper.compareDate(locale, obToChange.getExpiryDate(), aForm.getExpiryDate());
			obToChange.setExpiryDate(stageDate);

			obToChange.setPresentationNo(aForm.getPresentationNo());

			stageDate = CollateralMapper.compareDate(locale, obToChange.getPresentationDate(), aForm
					.getPresentationDate());
			obToChange.setPresentationDate(stageDate);

			stageDate = CollateralMapper.compareDate(locale, obToChange.getLodgedDate(), aForm.getLodgedDate());
			obToChange.setLodgedDate(stageDate);

			obToChange.setSolicitorName(aForm.getSolicitorName());
			// added by thurien

			obToChange.setFolio(aForm.getFolio());
			obToChange.setJilid(aForm.getJilid());
			obToChange.setPartyCharge(aForm.getPartyCharge());

			if (aForm.getRedemption().equals("Yes")) {
				obToChange.setRedemption("Y");
			}
			else {
				obToChange.setRedemption("N");
			}

		}
		catch (Exception e) {
			throw new MapperException("failed to map Form to OB for Charge", e);
		}

		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		ILimitCharge iLimitCharge = (ILimitCharge) obj;

		ChargeForm aForm = (ChargeForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug(this + " - mapOBToForm", "Locale is: " + locale);
		try {
			IPropertyCollateral iProperty = null;
			ICollateralTrxValue trxValue = (ICollateralTrxValue) inputs.get("serviceColObj");
			if ((inputs.get("from_event") != null) && inputs.get("from_event").equals("read")) {
				iProperty = (IPropertyCollateral) trxValue.getCollateral();
			}
			else {
				iProperty = (IPropertyCollateral) trxValue.getStagingCollateral();
			}
			DefaultLogger.debug(this, "After get OBCollateral");
			aForm.setSecurityID((trxValue.getCollateral() != null) ? String.valueOf(trxValue.getCollateral()
					.getCollateralID()) : "");
			ICollateralLimitMap[] limitMap = iLimitCharge.getLimitMaps();
			String[] limitId = new String[limitMap.length];
			Collection temp = new ArrayList();
			for (int i = 0; i < limitMap.length; i++) {
				limitId[i] = String.valueOf(limitMap[i].getChargeID());
				temp.add(String.valueOf(limitMap[i].getChargeID()));
			}
			aForm.setLimitID(limitId);

			ICollateralLimitMap[] limit = iProperty.getCollateralLimits();
			Collection colLimitMap = new ArrayList();
			if (limit != null) {
				for (int i = 0; i < limit.length; i++) {
					if ((limit[i].getSCIStatus() == null)
							|| !limit[i].getSCIStatus().equals(ICMSConstant.HOST_STATUS_DELETE)) {
						colLimitMap.add(String.valueOf(limit[i].getChargeID()));
					}
				}
			}

			colLimitMap.removeAll(temp);

			aForm.setLimitMapID((String[]) colLimitMap.toArray(new String[0]));
			DefaultLogger.debug(this, "limitMapID.length :" + colLimitMap.size());

			aForm.setRank(String.valueOf(iLimitCharge.getSecurityRank()));
			if ((iLimitCharge.getChargeAmount() != null) && (iLimitCharge.getChargeAmount().getCurrencyCode() != null)) {
				if (iLimitCharge.getChargeAmount().getAmount() >= 0) {
					aForm.setChargeAmount(CurrencyManager.convertToString(locale, iLimitCharge.getChargeAmount()));
				}
			}
			if ((iLimitCharge.getPriorChargeAmount() != null)
					&& (iLimitCharge.getPriorChargeAmount().getCurrencyCode() != null)) {
				if (iLimitCharge.getPriorChargeAmount().getAmount() >= 0) {
					aForm.setPriorChargeAmount(CurrencyManager.convertToString(locale, iLimitCharge
							.getPriorChargeAmount()));
				}
			}
			aForm.setChargeePriorCharge(iLimitCharge.getPriorChargeChargee());
			aForm.setDateLegalCharge(DateUtil.formatDate(locale, iLimitCharge.getLegalChargeDate()));

			aForm.setNatureOfCharge(iLimitCharge.getNatureOfCharge());
			aForm.setChargeType(iLimitCharge.getChargeType());
			aForm.setPriorChargeType(iLimitCharge.getPriorChargeType());

			// Set Caveat Information
			if (iLimitCharge.getCaveatWaivedInd() != null) {
				if (iLimitCharge.getCaveatWaivedInd().booleanValue()) {
					aForm.setCaveatWaivedInd(ICMSConstant.TRUE_VALUE);
				}
				else {
					aForm.setCaveatWaivedInd(ICMSConstant.FALSE_VALUE);
				}
			}

			aForm.setCaveatReferenceNo(iLimitCharge.getCaveatReferenceNo());
			aForm.setExpiryDate(DateUtil.formatDate(locale, iLimitCharge.getExpiryDate()));
			aForm.setPresentationNo(iLimitCharge.getPresentationNo());
			aForm.setPresentationDate(DateUtil.formatDate(locale, iLimitCharge.getPresentationDate()));
			aForm.setLodgedDate(DateUtil.formatDate(locale, iLimitCharge.getLodgedDate()));
			aForm.setSolicitorName(iLimitCharge.getSolicitorName());
			// added by thurein

			aForm.setFolio(iLimitCharge.getFolio());
			aForm.setJilid(iLimitCharge.getJilid());
			aForm.setPartyCharge(iLimitCharge.getPartyCharge());
            aForm.setHostCollateralId(iLimitCharge.getHostCollateralId());

			if (ICMSConstant.TRUE_VALUE.equals(iLimitCharge.getRedemption())) {
				aForm.setRedemption("Yes");
			}
			else {
				aForm.setRedemption("No");
			}

		}
		catch (Exception e) {
			throw new MapperException("Failed to map from OB to Form for Charge", e);
		}
		DefaultLogger.debug(this, "********************************* aForm is :" + aForm);
		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "serviceColObj", "java.lang.Object", SERVICE_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE }, { "from_event", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, });
	}
}
