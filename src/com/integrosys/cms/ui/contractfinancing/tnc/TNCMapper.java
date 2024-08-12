/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.contractfinancing.tnc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.contractfinancing.bus.IContractFinancing;
import com.integrosys.cms.app.contractfinancing.bus.ITNC;
import com.integrosys.cms.app.contractfinancing.bus.OBTNC;
import com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue;

/**
 * Mapper class used to map form values to objects and vice versa
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.0 $
 * @since $Date: 2007/Feb/07 $ Tag: $Name: $
 */
public class TNCMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public TNCMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "tncIndex", "java.lang.String", REQUEST_SCOPE },
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
		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>  Inside Map Form to OB ");

		String event = (String) map.get(ICommonEventConstant.EVENT);
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		TNCForm aForm = (TNCForm) cForm;

		IContractFinancingTrxValue trxValue = (IContractFinancingTrxValue) map.get("contractFinancingTrxValue");

		IContractFinancing contractFinancingObj = trxValue.getStagingContractFinancing();

		try {
			if (TNCAction.EVENT_CREATE.equals(event)) {

				DefaultLogger.debug(this, ">>>>>>>>>> In Mapping for event=" + event);

				// define TNC object
				OBTNC newTNC = new OBTNC();
				newTNC.setTerms(aForm.getTerms());
				newTNC.setTermsOthers(aForm.getTermsOthers());
				newTNC.setTncDate(DateUtil.convertDate(locale, aForm.getTncDate()));
				newTNC.setConditions(aForm.getConditions());
				newTNC.setRemarks(aForm.getRemarks());

				ArrayList tncList = new ArrayList();
				ITNC[] oldTNC = contractFinancingObj.getTncList();
				boolean addNew = true;
				if ((oldTNC != null) && (oldTNC.length != 0)) {
					for (int i = 0; i < oldTNC.length; i++) {
						OBTNC obTNC = (OBTNC) oldTNC[i];
						tncList.add(obTNC);

						// check duplicate data
						if (!oldTNC[i].getIsDeleted()) {
							if (oldTNC[i].getTerms().equals(newTNC.getTerms())) {
								addNew = false;
								// todo: need route to wip??
							}
						}
					}
				}
				if (addNew) {
					tncList.add(newTNC);
				}

				contractFinancingObj.setTncList((ITNC[]) tncList.toArray(new OBTNC[0]));

				return contractFinancingObj; // contractFinancingObj
			}
			else if (TNCAction.EVENT_UPDATE.equals(event)) {

				DefaultLogger.debug(this, ">>>>>>>>>> In Mapping for event=" + event);

				String tncIndex = (String) map.get("tncIndex");

				ITNC[] newTNC = contractFinancingObj.getTncList();
				int i = Integer.parseInt(tncIndex);
				newTNC[i].setTncDate(DateUtil.convertDate(locale, aForm.getTncDate()));
				newTNC[i].setConditions(aForm.getConditions());
				newTNC[i].setRemarks(aForm.getRemarks());

				contractFinancingObj.setTncList(newTNC);
				return contractFinancingObj; // contractFinancingObj
			}
			else if (TNCAction.EVENT_DELETE.equals(event)) {

				DefaultLogger.debug(this, ">>>>>>>>>> In Mapping for event=" + event);

				String[] deletedBox = aForm.getDeletedBox();
				ArrayList tncList = new ArrayList();
				ITNC[] oldTNC = contractFinancingObj.getTncList();
				if ((oldTNC != null) && (oldTNC.length != 0)) {
					for (int i = 0; i < oldTNC.length; i++) {
						boolean userDelete = false;
						if (deletedBox != null) {
							for (int j = 0; j < deletedBox.length; j++) {
								if (deletedBox[j].equals(String.valueOf(i))) {
									userDelete = true;
								}
							}
						}
						if (userDelete) {
							OBTNC obTNC = (OBTNC) oldTNC[i];
							if (obTNC.getTncID() != ICMSConstant.LONG_INVALID_VALUE) {
								obTNC.setIsDeleted(true);
								tncList.add(obTNC);
							}
							else {
								// not to add
							}
						}
						else {
							OBTNC obTNC = (OBTNC) oldTNC[i];
							tncList.add(obTNC);
						}
					}
				}

				contractFinancingObj.setTncList((ITNC[]) tncList.toArray(new OBTNC[0]));
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
			TNCForm aForm = (TNCForm) cForm;
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

			if (obj != null) {
				OBTNC obTnc = (OBTNC) obj;

				aForm.setTerms(obTnc.getTerms());
				aForm.setTermsOthers(obTnc.getTermsOthers());
				aForm.setTncDate(DateUtil.formatDate(locale, obTnc.getTncDate()));
				aForm.setConditions(obTnc.getConditions());
				aForm.setRemarks(obTnc.getRemarks());
			}
			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in TNCMapper is" + e);
		}
		return null;
	}
}
