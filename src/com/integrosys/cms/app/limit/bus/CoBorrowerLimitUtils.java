package com.integrosys.cms.app.limit.bus;

import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;

public abstract class CoBorrowerLimitUtils {

	public static ICoBorrowerLimit[] getAllCoBorowerLimitsByLimitProfile(ILimitProfile aLimitProfile) {
		if (aLimitProfile == null) {
			return new OBCoBorrowerLimit[0];
		}

		ILimit[] aLimitList = aLimitProfile.getLimits();
		if (ArrayUtils.isEmpty(aLimitList)) {
			return new OBCoBorrowerLimit[0];
		}

		ArrayList coBorrowerLimitList = new ArrayList();
		for (int i = 0; i < aLimitList.length; i++) {
			ICoBorrowerLimit[] aCoboLimitList = aLimitList[i].getCoBorrowerLimits();
			if (ArrayUtils.isEmpty(aCoboLimitList)) {
				continue;
			}

			for (int j = 0; j < aCoboLimitList.length; j++) {
				ICoBorrowerLimit aCoboLimit = aCoboLimitList[j];
				aCoboLimit.setProductDesc(aLimitList[i].getProductDesc());
				coBorrowerLimitList.add(aCoboLimit);
			}
		}

		return (ICoBorrowerLimit[]) coBorrowerLimitList.toArray(new ICoBorrowerLimit[0]);
	}
}