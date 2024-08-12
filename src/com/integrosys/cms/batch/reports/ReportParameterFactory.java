/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/reports/ReportParameterFactory.java,v 1.3 2006/01/11 02:23:24 hshii Exp $
 */

package com.integrosys.cms.batch.reports;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Description: Factory for creating report parameters based on inputs
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/01/11 02:23:24 $ Tag: $Name: $
 */
public class ReportParameterFactory {

	public static ReportParameter createReportParameter(String type, boolean hasOrg,
                                                        ParamData data, OBReportConfig reportConfig) {

		if (type == null) {
			throw new RuntimeException("Type cannot be null !!!");
		}
		else {

			DefaultLogger.debug(ReportParameterFactory.class.getName(), " >>>>>>>>>>>>> type " + type);

		}

		ReportParameter param = null;

		if ((type.equalsIgnoreCase(ReportConstants.MIS_CATEGORY) || type.equalsIgnoreCase(ReportConstants.DOC_CATEGORY))
				&& hasOrg) {
			param = new MISOrgParameter(data, reportConfig);
			//param.setInitializer(new MISOrgParamInitializer(param));

		}
		else if (type.equalsIgnoreCase(ReportConstants.MIS_CATEGORY) && (data.getLimitProfileID() > 0)) {
			param = new BCAReportParameter(data, reportConfig);
			//param.setInitializer(new BCAParamInitializer(param));

			/*
			 * } else if ( type.equalsIgnoreCase( ReportConstants.COUNTRY_REPORT
			 * ) ) { param = new CountryReportParameter(); param.setInitializer(
			 * new CountryReportInitializer( param ));
			 */

		}
		else if (type.equalsIgnoreCase(ReportConstants.MIS_CATEGORY)
				|| type.equalsIgnoreCase(ReportConstants.DOC_CATEGORY)) {
			param = new MISReportParameter(data, reportConfig);
			//param.setInitializer(new MISCommonInitializer(param));

		}
		else if (type.equalsIgnoreCase(ReportConstants.SYSTEM_CATEGORY)) {
			param = new SYSReportParameter(data, reportConfig);
			//param.setInitializer(new SYSInitializer(param));

		}
		else if (type.equalsIgnoreCase(ReportConstants.DIARY_REPORT)) {
			param = new DiaryItemParameter(data, reportConfig);
			//param.setInitializer(new DiaryParamInitializer(param));

		}
		else if (type.equalsIgnoreCase(ReportConstants.CONCENTRATION_CATEGORY)) {
			param = new ConcentrationReportParameter(data, reportConfig);
			//param.setInitializer(new ConcentrationInitializer(param));

		}
		else if (type.equalsIgnoreCase(ReportConstants.BL_DISCLAIMER_REPORT)) {
			param = new BLDisclaimerParameter(data, reportConfig);
			//param.setInitializer(new BLDisclaimerParamInitializer(param));

		}
		else if (isConcentration(type)) {
			param = new ConcentrationReportParameter(data, reportConfig);
			//param.setInitializer(new ConcentrationInitializer(param));

		}
		else {
			throw new UnsupportedOperationException("Type " + type + " is not supported");
		}

		//param.setData(data);
		//param.init();

		DefaultLogger.debug(ReportParameterFactory.class.getName(), param.toString());
		return param;
	}

	private static boolean isConcentration(String type) {
		if (type == null) {
			return false;
		}

		if (type.equalsIgnoreCase(ReportConstants.COUNTRY_SCOPE)
				|| type.equalsIgnoreCase(ReportConstants.COMMODITY_SCOPE)
				|| type.equalsIgnoreCase(ReportConstants.GLOBAL_SCOPE)
				|| type.equalsIgnoreCase(ReportConstants.REGION_SCOPE)
				|| type.equalsIgnoreCase(ReportConstants.EXCHANGE_SCOPE)) {

			return true;
		}

		return false;

	}

}
