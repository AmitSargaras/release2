/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * MaintainShareCounterUtil
 *
 * Created on 5:36:40 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.ui.creditriskparam.sharecounter;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.OBShareCounter;
import com.integrosys.cms.app.creditriskparam.bus.OBCreditRiskParam;
import com.integrosys.cms.app.creditriskparam.bus.OBCreditRiskParamGroup;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 21, 2007 Time: 5:36:40 PM
 */
public class MaintainShareCounterUtil {
	public static OBCreditRiskParamGroup mergeStagingWithOb(OBCreditRiskParamGroup stagingData, OBShareCounter ob) {
		// String parameterId[] = ob.getParameterId () ;
		// String parameterType[] = ob.getParameterType () ;
		String marginOfAdvance[] = ob.getMarginOfAdvance();
		// String maximumCap[] = ob.getMaximumCap () ;
		String isIntSuspend[] = ob.getIsIntSuspend();
		// String status[] = ob.getStatus () ;
		String isLiquid[] = ob.getIsLiquid();
		// String versionTime[] = ob.getVersionTime () ;
		String feedId[] = ob.getFeedId();
		// String parameterRef[] = ob.getParameterRef () ;
		String isFi[] = ob.getIsFi();
		String paramBoardType[] = ob.getParamBoardType();
		String paramShareStatus[] = ob.getParamShareStatus();

		OBCreditRiskParam params[] = stagingData.getFeedEntries();

		for (int loop = 0; loop < feedId.length; loop++) {
			OBCreditRiskParam param = findOBCreditRiskParam(params, Long.parseLong(feedId[loop]));

			if (param != null) {
				// merge the data
				if ((marginOfAdvance != null) && (marginOfAdvance[loop] != null)) {
					param.setMarginOfAdvance(new Double(marginOfAdvance[loop]).doubleValue());
				}

				param.setIsLiquid(isLiquid[loop]);
				param.setIsIntSuspend(isIntSuspend[loop]);
				param.setIsFi(isFi[loop]);
				param.setParamBoardType(paramBoardType[loop]);
				param.setParamShareStatus(paramShareStatus[loop]);
			}
		}

		return stagingData;
	}

	public static boolean compareIsSuspended(OBCreditRiskParam[] actualParams, String isSuspended, long feedId) {
		OBCreditRiskParam param = findOBCreditRiskParam(actualParams, feedId);

		return (param != null) && compareString(param.getIsIntSuspend(), isSuspended);
	}

	public static boolean compareIsLiquid(OBCreditRiskParam[] actualParams, String isLiquid, long feedId) {
		OBCreditRiskParam param = findOBCreditRiskParam(actualParams, feedId);

		return (param != null) && compareString(param.getIsLiquid(), isLiquid);
	}

	public static boolean compareIsFi(OBCreditRiskParam[] actualParams, String isFi, long feedId) {
		OBCreditRiskParam param = findOBCreditRiskParam(actualParams, feedId);

		return (param != null) && compareString(param.getIsFi(), isFi);
	}

	public static boolean compareMoa(OBCreditRiskParam[] actualParams, double moa, long feedId) {
		OBCreditRiskParam param = findOBCreditRiskParam(actualParams, feedId);

		return (param != null) && (moa == param.getMarginOfAdvance());
	}

	public static boolean compareMaxCap(OBCreditRiskParam[] actualParams, double maxCap, long feedId) {
		OBCreditRiskParam param = findOBCreditRiskParam(actualParams, feedId);

		return (param != null) && (maxCap == param.getMaximumCap());
	}

	public static boolean compareBoardType(OBCreditRiskParam[] actualParams, String boardType, long feedId) {
		OBCreditRiskParam param = findOBCreditRiskParam(actualParams, feedId);

		return (param != null) && compareString(param.getParamBoardType(), boardType);
	}

	public static boolean compareShareStatus(OBCreditRiskParam[] actualParams, String shareStatus, long feedId) {
		OBCreditRiskParam param = findOBCreditRiskParam(actualParams, feedId);

		return (param != null) && compareString(param.getParamShareStatus(), shareStatus);
	}

	public static boolean compareCreditRiskParam (OBCreditRiskParam[] actualParams, OBCreditRiskParam stgObj) {
		OBCreditRiskParam param = findOBCreditRiskParam(actualParams, stgObj.getFeedId());
		
		if (param == null)
			return false;
		
		if (!compareString(param.getIsIntSuspend(), stgObj.getIsIntSuspend()))
			return false;
				
		if (!compareString(param.getIsLiquid(), stgObj.getIsLiquid()))
			return false;
		
		if (!compareString(param.getIsFi(), stgObj.getIsFi()))
			return false;
		
		if (param.getMarginOfAdvance() != stgObj.getMarginOfAdvance())
			return false;
		
		if (param.getMaximumCap() != stgObj.getMaximumCap())
			return false;
		
		if (!compareString(param.getParamBoardType(), stgObj.getParamBoardType()))
			return false;
		
		if (!compareString(param.getParamShareStatus(), stgObj.getParamShareStatus()))
			return false;
		
		return true;
	}
	
	private static OBCreditRiskParam findOBCreditRiskParam(OBCreditRiskParam[] actualParams, long feedId) {
		for (int loop = 0; loop < actualParams.length; loop++) {
			if (feedId == actualParams[loop].getFeedId()) {
				return actualParams[loop];
			}
		}

		return null;
	}

	private static boolean compareString(String str1, String str2) {
		return str1 == null ? (str2 == null || str2.equals(ICMSConstant.FALSE_VALUE)) : str1.equals(str2);
	}

}
