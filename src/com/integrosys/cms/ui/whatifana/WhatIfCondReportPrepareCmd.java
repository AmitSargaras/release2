/*
Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.whatifana;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Prepares the input form for entering input to generate what if condition
 * report.
 * 
 * @author Author: siew kheat
 */
public class WhatIfCondReportPrepareCmd extends AbstractCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] { { com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale",
				GLOBAL_SCOPE } };
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return new String[][] { { "stateLabels", "java.util.List", REQUEST_SCOPE },
				{ "stateValues", "java.util.List", REQUEST_SCOPE },
				{ "indexTypeLabels", "java.util.List", REQUEST_SCOPE },
				{ "indexTypeValues", "java.util.List", REQUEST_SCOPE },
				{ "makeLabels", "java.util.List", REQUEST_SCOPE }, { "makeValues", "java.util.List", REQUEST_SCOPE },
				{ "goldGradeLabels", "java.util.List", REQUEST_SCOPE },
				{ "goldGradeValues", "java.util.List", REQUEST_SCOPE },
				{ "directionLabels", "java.util.List", REQUEST_SCOPE },
				{ "directionValues", "java.util.List", REQUEST_SCOPE }};
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			// Preparation.

			List stateLabels = new ArrayList(CommonDataSingleton
					.getCodeCategoryLabels(WhatIfCondReportConstants.STATE_CATEGORY));
			stateLabels.add(0, "Please Select");
			stateLabels.add(WhatIfCondReportConstants.OPTION_ALL);

			List stateValues = new ArrayList(CommonDataSingleton
					.getCodeCategoryValues(WhatIfCondReportConstants.STATE_CATEGORY));
			stateValues.add(0, "");
			stateValues.add(WhatIfCondReportConstants.OPTION_ALL);

			List indexTypeLabels = new ArrayList();
			indexTypeLabels.add(0, "Please Select");
			indexTypeLabels.add(1, "Main Index");
			indexTypeLabels.add(2, "Other Listed");

			List indexTypeValues = new ArrayList();
			indexTypeValues.add(0, "");
			indexTypeValues.add(1, WhatIfCondReportConstants.PARAM_VALUE_INDEX_TYPE_MAIN);
			indexTypeValues.add(2, WhatIfCondReportConstants.PARAM_VALUE_INDEX_TYPE_OTHER);

			List makeLabels = new ArrayList(CommonDataSingleton
					.getCodeCategoryLabels(WhatIfCondReportConstants.MAKE_CATEGORY));
			makeLabels.add(0, "Please Select");
			makeLabels.add(WhatIfCondReportConstants.OPTION_ALL);

			List makeValues = new ArrayList(CommonDataSingleton
					.getCodeCategoryValues(WhatIfCondReportConstants.MAKE_CATEGORY));
			makeValues.add(0, "");
			makeValues.add(WhatIfCondReportConstants.OPTION_ALL);

			List goldGradeLabels = new ArrayList(CommonDataSingleton
					.getCodeCategoryLabels(WhatIfCondReportConstants.GOLD_GRADE_CATEGORY));
			goldGradeLabels.add(0, "Please Select");
			goldGradeLabels.add(WhatIfCondReportConstants.OPTION_ALL);

			List goldGradeValues = new ArrayList(CommonDataSingleton
					.getCodeCategoryValues(WhatIfCondReportConstants.GOLD_GRADE_CATEGORY));
			goldGradeValues.add(0, "");
			goldGradeValues.add(WhatIfCondReportConstants.OPTION_ALL);

			List directionLabels = new ArrayList();
			directionLabels.add(0, "Please Select");
			directionLabels.add(1, "Increase");
			directionLabels.add(2, "Decrease");

			List directionValues = new ArrayList();
			directionValues.add(0, "");
			directionValues.add(1, WhatIfCondReportConstants.PARAM_VALUE_DIRECTION_INCREASE);
			directionValues.add(2, WhatIfCondReportConstants.PARAM_VALUE_DIRECTION_DECREASE);

			// Put the lists into the result map.
			resultMap.put("stateLabels", stateLabels);
			resultMap.put("stateValues", stateValues);
			resultMap.put("indexTypeLabels", indexTypeLabels);
			resultMap.put("indexTypeValues", indexTypeValues);
			resultMap.put("makeLabels", makeLabels);
			resultMap.put("makeValues", makeValues);
			resultMap.put("goldGradeLabels", goldGradeLabels);
			resultMap.put("goldGradeValues", goldGradeValues);
			resultMap.put("directionLabels", directionLabels);
			resultMap.put("directionValues", directionValues);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}

}
