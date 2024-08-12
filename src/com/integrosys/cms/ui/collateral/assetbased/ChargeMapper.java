/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/ChargeMapper.java,v 1.28 2006/06/21 06:38:53 pratheepa Exp $
 */

package com.integrosys.cms.ui.collateral.assetbased;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
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
import com.integrosys.cms.app.collateral.bus.type.asset.IAssetBasedCollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.CollateralMapper;

/**
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.28 $
 * @since $Date: 2006/06/21 06:38:53 $
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

		IAssetBasedCollateral iAsset = (IAssetBasedCollateral) (((ICollateralTrxValue) inputs.get("serviceColObj"))
				.getStagingCollateral());
		ILimitCharge[] obLimitCharge = iAsset.getLimitCharges();

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
			obToChange.setChargeCcyCode(iAsset.getCurrencyCode());
			obToChange.setPriorChargeCcyCode(iAsset.getCurrencyCode());

			ICollateralLimitMap[] colLimitMap = iAsset.getCollateralLimits();
			ICollateralLimitMap[] chargeLimit = obToChange.getLimitMaps();
			String[] limitId = aForm.getLimitID();
			DefaultLogger.debug(this, "limitId is: " + limitId);

			DefaultLogger.debug(this, "limitId size  is: " + limitId.length);
			OBCollateralLimitMap[] newLimitMap = new OBCollateralLimitMap[limitId.length];
			for (int i = 0; i < limitId.length; i++) {
				boolean sameID = false;
				for (int j = 0; (j < colLimitMap.length) && !sameID; j++) {
					if (limitId[i].equals(String.valueOf(colLimitMap[j].getChargeID()))) {
						DefaultLogger.debug(this, "Entering same charge id: " + limitId[i]);
						sameID = true;
						DefaultLogger.debug(this, "Entering same limit id: assign limit: colLimitMap[j].id"
								+ colLimitMap[j].getSCILimitID());
						newLimitMap[i] = (OBCollateralLimitMap) colLimitMap[j];
						DefaultLogger.debug(this, "Entering same limit id: after assign limit");
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
			/*
			 * if (!AbstractCommonMapper.isEmptyOrNull(aForm.getChargeAmount()))
			 * {
			 * obToChange.setChargeAmount(CurrencyManager.convertToAmount(locale
			 * , obToChange.getChargeCcyCode(), aForm.getChargeAmount())); }
			 */
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

			/*
			 * obToChange.setIsLEByChargeRanking(aForm.getIsLEChargeRank());
			 * obToChange.setIsLEByJurisdiction(aForm.getIsLEJurisdiction());
			 * obToChange.setIsLEByGovernLaws(aForm.getIsLEGovernLaw());
			 * 
			 * if
			 * (!AbstractCommonMapper.isEmptyOrNull(aForm.getDateLEChageRank()))
			 * { stageDate = CollateralMapper.compareDate(locale,
			 * obToChange.getLEDateByChargeRanking(),
			 * aForm.getDateLEChageRank());
			 * obToChange.setLEDateByChargeRanking(stageDate); } else {
			 * obToChange.setLEDateByChargeRanking(null); } if
			 * (!AbstractCommonMapper
			 * .isEmptyOrNull(aForm.getDateLEJurisdiction())) { stageDate =
			 * CollateralMapper.compareDate(locale,
			 * obToChange.getLEDateByJurisdiction(),
			 * aForm.getDateLEJurisdiction());
			 * obToChange.setLEDateByJurisdiction(stageDate); } else {
			 * obToChange.setLEDateByJurisdiction(null); }
			 * 
			 * if
			 * (!AbstractCommonMapper.isEmptyOrNull(aForm.getDateLEGovernLaw()))
			 * { stageDate = CollateralMapper.compareDate(locale,
			 * obToChange.getLEDateByGovernLaws(), aForm.getDateLEGovernLaw());
			 * obToChange.setLEDateByGovernLaws(stageDate); } else {
			 * obToChange.setLEDateByGovernLaws(null); }
			 * 
			 * // add by zhan jia for R1.5 CR 235
			 * obToChange.setIsLE(aForm.getIsLE()); if
			 * (!AbstractCommonMapper.isEmptyOrNull(aForm.getDateLE())) {
			 * stageDate = CollateralMapper.compareDate(locale,
			 * obToChange.getLEDate(), aForm.getDateLE());
			 * obToChange.setLEDate(stageDate); } else {
			 * obToChange.setLEDate(null); }
			 */

			obToChange.setNatureOfCharge(aForm.getNatureOfCharge());
			obToChange.setChargeType(aForm.getChargeType());
			obToChange.setPriorChargeType(aForm.getPriorChargeType());
			//added by thurien
			
			obToChange.setJilid(aForm.getJilid());
			obToChange.setFolio(aForm.getFolio());
			obToChange.setPartyCharge(aForm.getPartyCharge());
			
			if(aForm.getRedemption().equals("Yes"))
				obToChange.setRedemption("Y");
			else
				obToChange.setRedemption("N");
		}
		catch (Exception e) {
			DefaultLogger.debug(this + " ChargeMapper", "error is :" + e.toString());
			e.printStackTrace();
			throw new MapperException(e.getMessage());
		}

		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		ILimitCharge iLimitCharge = (ILimitCharge) obj;

		ChargeForm aForm = (ChargeForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		Amount amt;
		DefaultLogger.debug(this, "inside mapOBToForm .... ********************************* .... aForm is :" + aForm);
		try {
			IAssetBasedCollateral iAsset = null;
			ICollateralTrxValue trxValue = (ICollateralTrxValue) inputs.get("serviceColObj");
			
			if ((inputs.get("from_event") != null) && inputs.get("from_event").equals("read")) {
				iAsset = (IAssetBasedCollateral) trxValue.getCollateral();
			}
			else {
				iAsset = (IAssetBasedCollateral) trxValue.getStagingCollateral();
			}
			
			aForm.setSecurityID((trxValue.getCollateral() != null)? String.valueOf(trxValue.getCollateral().getCollateralID()):"");
			ICollateralLimitMap[] limitMap = iLimitCharge.getLimitMaps();
			String[] limitId = new String[limitMap.length];
			Collection temp = new ArrayList();
			for (int i = 0; i < limitMap.length; i++) {
				limitId[i] = String.valueOf(limitMap[i].getChargeID());
				temp.add(String.valueOf(limitMap[i].getChargeID()));
			}
			aForm.setLimitID(limitId);

			ICollateralLimitMap[] limit = iAsset.getCollateralLimits();
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
			amt = iLimitCharge.getChargeAmount();
			if ((amt != null) && (amt.getCurrencyCode() != null)) {
				if (amt.getAmount() >= 0) {
					aForm.setChargeAmount(CurrencyManager.convertToString(locale, amt));
				}
			}
			amt = iLimitCharge.getPriorChargeAmount();
			if ((amt != null) && (amt.getCurrencyCode() != null)) {
				if (amt.getAmount() >= 0) {
					aForm.setPriorChargeAmount(CurrencyManager.convertToString(locale, amt));
				}
			}
			aForm.setChargeePriorCharge(iLimitCharge.getPriorChargeChargee());
			aForm.setDateLegalCharge(DateUtil.formatDate(locale, iLimitCharge.getLegalChargeDate()));
			aForm.setIsLEChargeRank(iLimitCharge.getIsLEByChargeRanking());
			aForm.setIsLEJurisdiction(iLimitCharge.getIsLEByJurisdiction());
			aForm.setIsLEGovernLaw(iLimitCharge.getIsLEByGovernLaws());

			aForm.setDateLEChageRank(DateUtil.formatDate(locale, iLimitCharge.getLEDateByChargeRanking()));
			aForm.setDateLEJurisdiction(DateUtil.formatDate(locale, iLimitCharge.getLEDateByJurisdiction()));
			aForm.setDateLEGovernLaw(DateUtil.formatDate(locale, iLimitCharge.getLEDateByGovernLaws()));

			// added by zhanjia for R1.5 CR 235
			aForm.setIsLE(iLimitCharge.getIsLE());
			aForm.setDateLE(DateUtil.formatDate(locale, iLimitCharge.getLEDate()));
			
			aForm.setNatureOfCharge(iLimitCharge.getNatureOfCharge());
			aForm.setChargeType(iLimitCharge.getChargeType());
			aForm.setPriorChargeType(iLimitCharge.getPriorChargeType());
			
			//added by thurein 
			
			aForm.setFolio(iLimitCharge.getFolio());
			aForm.setJilid(iLimitCharge.getJilid());
			aForm.setPartyCharge(iLimitCharge.getPartyCharge());
			
			if (ICMSConstant.TRUE_VALUE.equals(iLimitCharge.getRedemption()))
				aForm.setRedemption("Yes");
			else
				aForm.setRedemption("No");

            aForm.setHostCollateralId(iLimitCharge.getHostCollateralId());
		}
		catch (Exception e) {
			DefaultLogger.debug(this + " ChargeMapper", "error is :" + e.toString());
			e.printStackTrace();
			throw new MapperException(e.getMessage());
		}
		DefaultLogger.debug(this, "********************************* aForm is :" + aForm);
		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "serviceColObj", "java.lang.Object", SERVICE_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE }, { "from_event", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, });
	}

	/*
	 * private ILimitCharge getItem(ILimitCharge temp[],long itemRef){
	 * ILimitCharge item = null; if(temp == null){ return item; } for(int
	 * i=0;i<temp.length;i++){ if(temp[i].getRefID()==itemRef){ item = temp[i];
	 * }else{ continue; } } return item; }
	 */
}
