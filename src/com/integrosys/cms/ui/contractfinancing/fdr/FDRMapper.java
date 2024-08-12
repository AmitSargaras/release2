/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.contractfinancing.fdr;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.contractfinancing.bus.IContractFinancing;
import com.integrosys.cms.app.contractfinancing.bus.IFDR;
import com.integrosys.cms.app.contractfinancing.bus.OBFDR;
import com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue;

/**
 * Mapper class used to map form values to objects and vice versa
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.0 $
 * @since $Date: 2007/Feb/07 $ Tag: $Name: $
 */
public class FDRMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public FDRMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "fdrIndex", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "contractFinancingTrxValue",
						"com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue", SERVICE_SCOPE }, });

	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "******************** Inside Map Form to OB ");

		String event = (String) map.get(ICommonEventConstant.EVENT);
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		FDRForm aForm = (FDRForm) cForm;

		IContractFinancingTrxValue trxValue = (IContractFinancingTrxValue) map.get("contractFinancingTrxValue");

		IContractFinancing contractFinancingObj = trxValue.getStagingContractFinancing();

		try {
			if (FDRAction.EVENT_CREATE.equals(event)) {

				DefaultLogger.debug(this, ">>>>>>>>>> In Mapping for event=" + event);

				// define fdr object
				OBFDR newFDR = new OBFDR();
				newFDR.setFdrDate(DateUtil.convertDate(locale, aForm.getFdrDate()));
				newFDR.setAccountNo(aForm.getAccountNo());
				if (!aForm.getFdrCurrency().equals("") && !aForm.getFdrAmount().equals("")) {
					newFDR.setFdrAmount(CurrencyManager.convertToAmount(locale, aForm.getFdrCurrency(), aForm
							.getFdrAmount()));
				}
				newFDR.setReferenceNo(aForm.getReferenceNo());
				newFDR.setRemarks(aForm.getRemarks());

				ArrayList fdrList = new ArrayList();
				IFDR[] oldFDR = contractFinancingObj.getFdrList();
				boolean addNew = true;
				if ((oldFDR != null) && (oldFDR.length != 0)) {
					for (int i = 0; i < oldFDR.length; i++) {
						OBFDR obFDR = (OBFDR) oldFDR[i];
						fdrList.add(obFDR);

						// check duplicate data
						// need?
					}
				}
				if (addNew) {
					fdrList.add(newFDR);
				}

				contractFinancingObj.setFdrList((IFDR[]) fdrList.toArray(new OBFDR[0]));
				return contractFinancingObj; // contractFinancingObj
			}
			else if (FDRAction.EVENT_UPDATE.equals(event)) {

				DefaultLogger.debug(this, ">>>>>>>>>> In Mapping for event=" + event);

				String fdrIndex = (String) map.get("fdrIndex");

				IFDR[] newFDR = contractFinancingObj.getFdrList();
				int i = Integer.parseInt(fdrIndex);
				newFDR[i].setFdrDate(DateUtil.convertDate(locale, aForm.getFdrDate()));
				newFDR[i].setAccountNo(aForm.getAccountNo());
				if (!aForm.getFdrCurrency().equals("") && !aForm.getFdrAmount().equals("")) {
					newFDR[i].setFdrAmount(CurrencyManager.convertToAmount(locale, aForm.getFdrCurrency(), aForm
							.getFdrAmount()));
				}
				newFDR[i].setReferenceNo(aForm.getReferenceNo());
				newFDR[i].setRemarks(aForm.getRemarks());

				contractFinancingObj.setFdrList(newFDR);
				return contractFinancingObj; // contractFinancingObj
			}
			else if (FDRAction.EVENT_DELETE.equals(event)) {

				DefaultLogger.debug(this, ">>>>>>>>>> In Mapping for event=" + event);

				int fdrIndex = Integer.parseInt((String) map.get("fdrIndex"));
				ArrayList fdrList = new ArrayList();
				IFDR[] oldFDR = contractFinancingObj.getFdrList();
				if ((oldFDR != null) && (oldFDR.length != 0)) {
					for (int i = 0; i < oldFDR.length; i++) {

						if (i == fdrIndex) {

							OBFDR obFDR = (OBFDR) oldFDR[i];
							if (obFDR.getFdrID() != ICMSConstant.LONG_INVALID_VALUE) {
								obFDR.setIsDeleted(true);
								fdrList.add(obFDR);
							}
							else {
								// not to add
							}
						}
						else {
							OBFDR obFDR = (OBFDR) oldFDR[i];
							fdrList.add(obFDR);
						}
					}
				}

				contractFinancingObj.setFdrList((IFDR[]) fdrList.toArray(new OBFDR[0]));
				return contractFinancingObj; // contractFinancingObj
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MapperException(e.getMessage());
		}
		return contractFinancingObj;
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		try {
			DefaultLogger.debug(this, "inside mapOb to form");
			FDRForm aForm = (FDRForm) cForm;
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

			if (obj != null) {
				OBFDR obFdr = (OBFDR) obj;

				aForm.setFdrDate(DateUtil.formatDate(locale, obFdr.getFdrDate()));
				aForm.setAccountNo(obFdr.getAccountNo());
				if (obFdr.getFdrAmount() != null) {
					aForm.setFdrCurrency(obFdr.getFdrAmount().getCurrencyCode());
					aForm.setFdrAmount(new DecimalFormat("#").format(obFdr.getFdrAmount().getAmount()));
				}
				aForm.setReferenceNo(obFdr.getReferenceNo());
				aForm.setRemarks(obFdr.getRemarks());

			}
			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in FDRMapper is" + e);
		}
		return null;
	}
}
