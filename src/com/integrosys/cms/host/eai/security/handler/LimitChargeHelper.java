package com.integrosys.cms.host.eai.security.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.limit.bus.ChargeDetail;
import com.integrosys.cms.host.eai.limit.bus.LimitChargeMap;
import com.integrosys.cms.host.eai.limit.bus.LimitSecurityMap;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.ISecurityDao;
import com.integrosys.cms.host.eai.security.bus.SecuritySource;

/**
 * Limit Charge Persistent helper
 * 
 * @author zhaijian
 * @author Chong Jun Yong
 * 
 */
public class LimitChargeHelper implements ILimitChargeHelper {

	private ISecurityDao securityDao;

	public ISecurityDao getSecurityDao() {
		return securityDao;
	}

	public void setSecurityDao(ISecurityDao securityDao) {
		this.securityDao = securityDao;
	}

	public void persistAll(SecurityMessageBody secMsgBody, ApprovedSecurity aa) {
		if ((aa.getSharedSecurityId() == ICMSConstant.LONG_INVALID_VALUE) || (aa.getSharedSecurityId() == 0)) {
			return;
		}

		long col1 = getCollateralIdByShareSecurityId(secMsgBody, aa);
		long col2 = getCollateralIdBySourceSecurityId(secMsgBody, aa);

		if ((col1 != ICMSConstant.LONG_INVALID_VALUE) && (col2 != ICMSConstant.LONG_INVALID_VALUE) && (col1 != col2)) {
			persistSecurityRelated(col1, col2);
		}

	}

	private void persistSecurityRelated(long col1, long col2) {
		persistChargeDetail(col1, col2);
	}

	private void persistLimitSecurityMap(long col1, long col2) {
		LimitSecurityMap obLimtSecurityMap = new LimitSecurityMap();

		List list1 = getLimitSecurityMapList(col2);
		for (int i = 0; i < list1.size(); i++) {
			obLimtSecurityMap = (LimitSecurityMap) list1.get(i);

			LimitSecurityMap limitSecMap = (LimitSecurityMap) getSecurityDao().retrieve(
					new Long(obLimtSecurityMap.getChargeId()), obLimtSecurityMap.getClass());

			obLimtSecurityMap.setCmsCollateralId(col1);
			AccessorUtil.copyValue(obLimtSecurityMap, limitSecMap);

			getSecurityDao().update(limitSecMap, limitSecMap.getClass());
		}

	}

	private List getLimitSecurityMapList(long co2) {

		Map parameters = new HashMap();
		parameters.put("cmsCollateralId", new Long(co2));
		List limitSecMapList = getSecurityDao().retrieveObjectListByParameters(parameters, LimitSecurityMap.class);

		return limitSecMapList;
	}

	private void persistChargeDetail(long col1, long col2) {
		ChargeDetail chargeDetail = new ChargeDetail();
		List list1 = getChargeList(col2);
		for (int i = 0; i < list1.size(); i++) {
			chargeDetail = (ChargeDetail) list1.get(i);

			ChargeDetail storedChargeDetail = (ChargeDetail) getSecurityDao().retrieve(
					new Long(chargeDetail.getChargeDetailId()), chargeDetail.getClass());

			chargeDetail.setCmsSecurityId(col1);
			AccessorUtil.copyValue(chargeDetail, storedChargeDetail);

			getSecurityDao().update(storedChargeDetail, storedChargeDetail.getClass());
		}
	}

	private List getChargeList(long co2) {

		HashMap parameters = new HashMap();
		parameters.put("cmsCollateralId", new Long(co2));

		List chargeDetailsList = getSecurityDao().retrieveObjectListByParameters(parameters, ChargeDetail.class);

		return chargeDetailsList;
	}

	private void persistLimitChargeMap(long col1, long col2) {
		LimitChargeMap oblimitChargeMap = new LimitChargeMap();

		List list1 = getLimitChargeMapList(col2);
		for (int i = 0; i < list1.size(); i++) {
			oblimitChargeMap = (LimitChargeMap) list1.get(i);

			LimitChargeMap storedLimitChargeMap = (LimitChargeMap) getSecurityDao().retrieve(
					new Long(oblimitChargeMap.getChargeMapID()), oblimitChargeMap.getClass());

			oblimitChargeMap.setCollateralID(col1);
			AccessorUtil.copyValue(oblimitChargeMap, storedLimitChargeMap);

			getSecurityDao().update(storedLimitChargeMap, storedLimitChargeMap.getClass());
		}
	}

	private List getLimitChargeMapList(long co2) {
		Map parameters = new HashMap();
		parameters.put("collateralID", new Long(co2));

		List limitChargeMapList = getSecurityDao().retrieveObjectListByParameters(parameters, LimitChargeMap.class);

		return limitChargeMapList;
	}

	private long getCollateralIdByShareSecurityId(SecurityMessageBody secMsgBody, ApprovedSecurity aa) {
		long collaterlId = ICMSConstant.LONG_INVALID_VALUE;
		Map parameters = new HashMap();
		parameters.put("CMSSecurityId", new Long(aa.getSharedSecurityId()));

		SecuritySource securitySource = (SecuritySource) getSecurityDao().retrieveObjectByParameters(parameters,
				SecuritySource.class);

		if (securitySource != null) {
			collaterlId = securitySource.getCMSSecurityId();
		}

		return collaterlId;
	}

	private long getCollateralIdBySourceSecurityId(SecurityMessageBody secMsgBody, ApprovedSecurity aa) {
		long collaterlId = ICMSConstant.LONG_INVALID_VALUE;

		Map parameters = new HashMap();
		parameters.put("sourceSecurityId", aa.getLOSSecurityId());
		parameters.put("sourceId", aa.getSourceId());
		parameters.put("securitySubTypeId", aa.getSecuritySubType().getStandardCodeValue());

		SecuritySource securitySource = (SecuritySource) getSecurityDao().retrieveObjectByParameters(parameters,
				SecuritySource.class);

		if (securitySource != null) {
			collaterlId = securitySource.getCMSSecurityId();
		}
		return collaterlId;
	}

}
