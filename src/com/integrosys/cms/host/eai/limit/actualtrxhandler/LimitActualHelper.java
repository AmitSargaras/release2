package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.util.Iterator;
import java.util.Vector;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.limit.NoSuchLimitException;
import com.integrosys.cms.host.eai.limit.bus.Limits;
import com.integrosys.cms.host.eai.limit.bus.LimitsApprovedSecurityMap;

/**
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.1
 */
public class LimitActualHelper implements IEaiConstant {

	public long getChargeIdByCmsLimitIdAndCmsSecurityId(long collateralId, long limitId, Vector limitSecurityMap) {
		if ((limitSecurityMap == null) || (limitSecurityMap.size() == 0)) {
			return ICMSConstant.LONG_INVALID_VALUE;
		}

		for (int i = 0; i < limitSecurityMap.size(); i++) {
			LimitsApprovedSecurityMap map = (LimitsApprovedSecurityMap) limitSecurityMap.elementAt(i);
			if (map.getCmsSecurityId() == collateralId && map.getCmsLimitId() == limitId) {
				return map.getCmsId();
			}
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	public long getChargeID(String sourceSecurityID, String sourceLimitID, String cifId, Vector limitSecurityMap) {
		if ((limitSecurityMap == null) || (limitSecurityMap.size() == 0)) {
			return ICMSConstant.LONG_INVALID_VALUE;
		}

		for (int i = 0; i < limitSecurityMap.size(); i++) {
			LimitsApprovedSecurityMap map = (LimitsApprovedSecurityMap) limitSecurityMap.elementAt(i);
			if (map.getSecurityId().equals(sourceSecurityID) && map.getLimitId().equals(sourceLimitID)
					&& map.getCIFId().equals(cifId)) {
				return map.getCmsId();
			}
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	public long getInternalCollateralID(String sourceSecurityID, Vector limitSecurityMap) {
		if ((limitSecurityMap == null) || (limitSecurityMap.size() == 0)) {
			return ICMSConstant.LONG_INVALID_VALUE;
		}

		for (int i = 0; i < limitSecurityMap.size(); i++) {
			LimitsApprovedSecurityMap map = (LimitsApprovedSecurityMap) limitSecurityMap.elementAt(i);
			if (map.getSecurityId().equals(sourceSecurityID)) {
				return map.getCmsSecurityId();
			}
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * To retrieve the Limit CMS Key provided the LOS Limit Id.
	 * 
	 * @param limits a list of limits, mostly is from the message itself
	 * @param limitId the LOS limit id
	 * @return limit cms key corresponding to the limit id provided
	 * @throws NoSuchLimitException if the limit cannot find in the list of
	 *         limit
	 */
	public long getInternalLimitId(Vector limits, String limitId) throws NoSuchLimitException {
		for (Iterator iter = limits.iterator(); iter.hasNext();) {
			Limits temp = (Limits) iter.next();

			if ((temp.getLimitGeneral().getLOSLimitId() != null)
					&& temp.getLimitGeneral().getLOSLimitId().trim().equals(limitId)) {
				return temp.getLimitGeneral().getCmsId();
			}
		}

		throw new NoSuchLimitException("No matching limit found for limit id [" + limitId + "] from message itself.");
	}

	/**
	 * To retrieve the Limit Object provided the LOS Limit Id.
	 * 
	 * @param limits a list of limits, mostly is from the message itself
	 * @param limitId the LOS limit id
	 * @return the limit object corresponding to the limit id provided
	 * @throws NoSuchLimitException if the limit cannot find in the list of
	 *         limit
	 */
	public Limits getInternalLimit(Vector limits, String limitId) throws NoSuchLimitException {
		for (Iterator iter = limits.iterator(); iter.hasNext();) {
			Limits temp = (Limits) iter.next();

			if ((temp.getLimitGeneral().getLOSLimitId() != null)
					&& temp.getLimitGeneral().getLOSLimitId().trim().equals(limitId)) {
				return temp;
			}
		}

		throw new NoSuchLimitException("No matching limit found for limit id [" + limitId + "] from message itself.");
	}
}
