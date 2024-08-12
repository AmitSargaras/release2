/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/concentrationreport/MaintainConcReportMapper.java,v 1.2 2005/05/10 10:22:09 wltan Exp $
 */
package com.integrosys.cms.ui.concentrationreport;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.systemparameters.Constants;
import com.integrosys.cms.app.systemparameters.bus.OBSystemParameters;
import com.integrosys.cms.app.systemparameters.bus.SystemParametersSearchCriteria;
import com.integrosys.component.commondata.app.bus.BusinessParameterComparator;
import com.integrosys.component.commondata.app.bus.IBusinessParameter;
import com.integrosys.component.commondata.app.bus.OBBusinessParameter;
import com.integrosys.component.commondata.app.bus.OBBusinessParameterGroup;
import com.integrosys.component.commondata.app.trx.IBusinessParameterGroupTrxValue;

/**
 * Mapper class used to map form values to objects and vice versa
 * @author $Author: wltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/05/10 10:22:09 $ Tag: $Name: $
 */
public class MaintainConcReportMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public MaintainConcReportMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "SystemParametersTrxValue", "com.integrosys.cms.app.systemparameters.trx.OBSystemParametersTrxValue",
						SERVICE_SCOPE } });

	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "Inside Map Form to OB ");
		String event = (String) map.get(ICommonEventConstant.EVENT);
		MaintainConcReportForm aForm = (MaintainConcReportForm) cForm;
		if (event.equals("maker_list_concreport") || event.equals("redirect_list_concreport")) {

			// build a default ConcReportSearchCriteira
			return new SystemParametersSearchCriteria(Constants.CONC_REPORT_PARAMS_GROUP_CODE);

		}
		else if (event.equals("maker_edit_concreport") || event.equals("maker_edit_reject_edit")
				|| event.equalsIgnoreCase("maker_edit_concreport_read_rejected")) {
			IBusinessParameterGroupTrxValue iBusinessParameterGroupTrxVal = (IBusinessParameterGroupTrxValue) map
					.get("SystemParametersTrxValue");
			OBBusinessParameterGroup obBPGroup = new OBSystemParameters();

			if (event.equalsIgnoreCase("maker_edit_concreport_read_rejected")) {
				AccessorUtil.copyValue(iBusinessParameterGroupTrxVal.getStagingBusinessParameterGroup(), obBPGroup);
			}
			else {
				// copy old values from ORIGINAL value.
				AccessorUtil.copyValue(iBusinessParameterGroupTrxVal.getBusinessParameterGroup(), obBPGroup);
			}
			// fill new values - i.e. parameterValues
			IBusinessParameter[] oldParameters = obBPGroup.getBusinessParameters();
			String[] formParamCodes = aForm.getParameterCodes();
			String[] formParamValues = aForm.getParameterValues();

			for (int i = 0; i < oldParameters.length; i++) {
				for (int j = 0; j < oldParameters.length; j++) {
					if (oldParameters[i].getParameterCode().equals(formParamCodes[j])) {
						((OBBusinessParameter) oldParameters[i]).setParameterValue(formParamValues[j]);
					}
				}
			}

			// oldParameters are modified with new parameterValues. set into the
			// group, and return
			obBPGroup.setBusinessParameters(oldParameters);

			return obBPGroup;
		}
		return null;
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
			DefaultLogger.debug(this, "inside mapOb to form MaintainConcReportMapper");
			MaintainConcReportForm aForm = (MaintainConcReportForm) cForm;
			if (obj != null) {
				OBBusinessParameterGroup sr = (OBBusinessParameterGroup) obj;
				if (sr != null) {

					if (sr.getBusinessParameters() != null) {
						String[] businessParameter = new String[sr.getBusinessParameters().length];
						String[] parameterCode = new String[sr.getBusinessParameters().length];
						String[] parameterName = new String[sr.getBusinessParameters().length];
						IBusinessParameter[] iBusinessParameter = sr.getBusinessParameters();
						Arrays.sort(iBusinessParameter, new BusinessParameterComparator());
						for (int i = 0; i < sr.getBusinessParameters().length; i++) {
							businessParameter[i] = iBusinessParameter[i].getParameterValue();
							parameterCode[i] = iBusinessParameter[i].getParameterCode();
							parameterName[i] = iBusinessParameter[i].getParameterName();
						}
						if (businessParameter != null) {
							aForm.setParameterValues(businessParameter);
						}
						if (parameterCode != null) {
							aForm.setParameterCodes(parameterCode);
						}
						if (parameterName != null) {
							aForm.setParameterNames(parameterName);
						}

					}
				}
			}
			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in MaintainConcReportMapper is" + e);
		}
		return null;

	}
}
