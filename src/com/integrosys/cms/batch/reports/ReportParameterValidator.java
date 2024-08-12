package com.integrosys.cms.batch.reports;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.integrosys.cms.batch.InvalidParameterBatchJobException;
import com.integrosys.cms.batch.factory.BatchParameterValidator;

/**
 * <p>
 * To validate report parameters that is required to generate report. Throwing
 * {@link InvalidReportParameterException} if there is any invalid or missing
 * parameters.
 * 
 * <p>
 * Following highlight in <b>bold</b> is the key inside the map supplied
 * <ol>
 * <li>First validate <b>scope</b>, this must not be empty
 * <li>Then the <b>scope</b> <i>MUST</i> match any available following valid
 * scope.
 * <ul>
 * <li>ID
 * <li>Global
 * <li>MIS
 * <li>SYS
 * <li>Country
 * <li>Region
 * <li>Com
 * <li>DOC
 * </ul>
 * <li>If <b>scope</b> is <i>ID</i>, <b>value</b> must be numeric, which is the
 * report master id.
 * <li>If <b>scope</b> is <i>Global</i>, there must be only this parameters
 * exists in the arguments
 * <li>For other <b>scope</b>, <b>value</b> must not be null.
 * </ol>
 * 
 * @author Chong Jun Yong
 * @see ReportScheduler#allValidScopeTypes
 */
public class ReportParameterValidator implements BatchParameterValidator {
	public void validate(Map parameters) throws InvalidParameterBatchJobException {
		List missingParameterList = new ArrayList();

		String scope = (String) parameters.get(ReportConstants.KEY_SCOPE);
		if (scope == null) {
			missingParameterList.add(ReportConstants.KEY_SCOPE);
			throw new InvalidParameterBatchJobException("missing parameters for report generation",
					(String[]) missingParameterList.toArray(new String[0]));
		}

		boolean validScope = false;
		String[] scopeTypes = ReportScheduler.getAllValidScopeTypes();
		for (int i = 0; i < scopeTypes.length; i++) {
			if (scope.equalsIgnoreCase(scopeTypes[i])) {
				validScope = true;
				break;
			}
		}

		if (!validScope) {
			missingParameterList.add(ReportConstants.KEY_SCOPE);
			throw new InvalidParameterBatchJobException("missing parameters for report generation",
					(String[]) missingParameterList.toArray(new String[0]));
		}

		if (scope.equals(ReportConstants.REPORT_CONFIG_BY_ID)) {
			String masterID = (String) parameters.get(ReportConstants.KEY_ID);
			try {
				// just to validate if report master id is a number
				Integer.parseInt(masterID);
			}
			catch (NumberFormatException e) {
				throw new InvalidParameterBatchJobException("'ID' scope's 'id' parameter must be a number.");
			}

            if (parameters.get(ReportConstants.KEY_COUNTRY) == null) {
                throw new InvalidParameterBatchJobException("missing 'country' parameters required for 'ID' scope report generation.");
            }
		}
		else if (scope.equalsIgnoreCase(ReportConstants.GLOBAL_SCOPE)) {
			if (parameters.size() > 1) {
				throw new InvalidParameterBatchJobException("should have only 1 parameter for 'Global' scope.");
			}
		}
        else if (scope.equalsIgnoreCase(ReportConstants.MIS_CATEGORY)
                || scope.equalsIgnoreCase(ReportConstants.SYSTEM_CATEGORY)
                || scope.equalsIgnoreCase(ReportConstants.COUNTRY_SCOPE)) {
            if (parameters.get(ReportConstants.KEY_COUNTRY) == null) {
                throw new InvalidParameterBatchJobException("missing 'country' parameters required for " + scope + " scope report generation.");
            }
        }
		else if (parameters.get(ReportConstants.KEY_VALUE) == null) {
			throw new InvalidParameterBatchJobException("missing 'value' parameters required for report generation.");
		}
	}
}
