package com.integrosys.cms.ui.collateral.guarantees.gtegovtlink;

import java.util.HashMap;
import java.util.Locale;
import java.util.Date;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink.IGteGovtLink;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.base.techinfra.util.DateUtil;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/04/10 07:09:58 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class GteGovtLinkMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		IGteGovtLink iCol = (IGteGovtLink) obj;

		GteGovtLinkForm aForm = (GteGovtLinkForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		try {
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getMinimalFSV())) {
				iCol.setMinimalFSV(null);
			}
			else {
				iCol.setMinimalFSVCcyCode(iCol.getCurrencyCode());
				iCol.setMinimalFSV(CurrencyManager.convertToAmount(locale, iCol.getMinimalFSVCcyCode(), aForm
						.getMinimalFSV()));
			}
			iCol.setCurrentScheme(aForm.getCurrentScheme());
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getCancellationDateLG())) {
				Date stageDate = CollateralMapper.compareDate(locale, iCol.getCancellationDateLG(), aForm.getCancellationDateLG());
					iCol.setCancellationDateLG(stageDate);
			} else {
				iCol.setCancellationDateLG(null);
			}
		}
		catch (Exception e) {
			DefaultLogger.error("com.integrosys.cms.ui.collateral.guarantees.gtegovtlink.GteGovtLinkMapper",
					"error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return iCol;

	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		GteGovtLinkForm aForm = (GteGovtLinkForm) cForm;

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		IGteGovtLink iObj = (IGteGovtLink) obj;
		try {
			if ((iObj.getMinimalFSV() != null) && (iObj.getMinimalFSV().getCurrencyCode() != null)) {
				if (iObj.getMinimalFSV().getAmount() >= 0) {
					aForm.setMinimalFSV(CurrencyManager.convertToString(locale, iObj.getMinimalFSV()));
				}
			}
			aForm.setCurrentScheme(iObj.getCurrentScheme());
			aForm.setCancellationDateLG(DateUtil.formatDate(locale, iObj.getCancellationDateLG()));
		}
		catch (Exception e) {
			DefaultLogger.error("com.integrosys.cms.ui.collateral.guarantees.gtegovtlink.GteGovtLinkMapper",
					"error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((IGteGovtLink) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}

}
