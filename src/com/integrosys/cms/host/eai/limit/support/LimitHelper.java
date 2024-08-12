/*
 * Created by IntelliJ IDEA.
 * User: Sulin
 * Date: Oct 20, 2003
 * Time: 2:46:05 PM
 */
package com.integrosys.cms.host.eai.limit.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.host.eai.customer.EAICustomerMessageException;
import com.integrosys.cms.host.eai.limit.NoSuchLimitException;
import com.integrosys.cms.host.eai.limit.bus.Limits;
import com.integrosys.cms.host.eai.limit.bus.LimitsApprovedSecurityMap;

/**
 * This is a Helper class use by all TrxHandler , ActualTrxHandler, Validator.
 * LimitHelper provides utility methods for Limit, LimitSysXRef,
 * LimitSecurityMap.
 */
public class LimitHelper extends CommonHelper {
	/**
	 * This will return a cLimit that have the leId, LimitProfileId, LImitId and
	 * subprofileId equivalent to the variable parse in.
	 */
	
	public static enum CoBorrowerSystems {

		BAHARIN("BAHRAIN"),
		GIFT_CITY("GIFT_CITY"),
		GIFTCITY("GIFTCITY"),
		HONG_KONG("HONGKONG"),
		UBS_LIMITS("UBS-LIMITS");

		private final String systemName;

		CoBorrowerSystems(String systemName) {
			this.systemName = systemName;
		}

		public static boolean contains(String input) {
			return StringUtils.isNotEmpty(input)

					&& (BAHARIN.systemName.equals(input) || GIFT_CITY.systemName.equals(input) ||  GIFTCITY.systemName.equals(input)
							|| HONG_KONG.systemName.equals(input) || UBS_LIMITS.systemName.equals(input));
		}
	}
	
	public Limits getLimit(Vector limit, String leid, long subprofileid, long limitprofileid, String limitId)
			throws NoSuchLimitException {
		if (!anyLimit(limit)) {
			DefaultLogger.debug(this, "--> Get Limit is empty");
			limit = new Vector();
		}

		for (Iterator iter = limit.iterator(); iter.hasNext();) {
			Limits lmts = (Limits) iter.next();

			if ((lmts.getLimitGeneral().getCIFId().equals(leid))
					&& lmts.getLimitGeneral().getLOSLimitId().equals(limitId)) {
				return lmts;
			}
		}
		throw new NoSuchLimitException(leid, subprofileid, limitprofileid, limitId);
	}

	public Limits getLimitByCmsLimitId(Vector limits, long cmsLimitId) {
		if (!anyLimit(limits)) {
			DefaultLogger.debug(this, "--> Get Limit is empty");
			limits = new Vector();
		}

		for (Iterator iter = limits.iterator(); iter.hasNext();) {
			Limits lmts = (Limits) iter.next();

			if ((lmts.getLimitGeneral().getCmsId() == cmsLimitId)
					|| (lmts.getLimitGeneral().getCMSLimitId() == cmsLimitId)) {
				return lmts;
			}
		}

		return null;
	}

	/**
	 * Check whether there is only one object in the Vector , and its empty. If
	 * its empty return true.
	 */
	public boolean anyLimit(Vector vec) {
		if (vec == null) {
			return false;
		}

		if (vec.size() == 1) {
			Limits obj = (Limits) vec.elementAt(0);
			if ((obj == null) || Character.isIdentifierIgnorable(obj.getLimitGeneral().getChangeIndicator().charAt(0))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * to identify a Limit in a hashmap , the key is a combination of leid ,
	 * subprofileid, LimitprofileId and the limitid , see constructLimitKey
	 * method . this method will extract the leid from the LmitKey.
	 */
	public String extractCIFID(String key) throws EAICustomerMessageException {
		if (-1 != key.indexOf(LIMIT_KEY)) {
			return getTokenString(key, 1);
		}
		throw new EAICustomerMessageException("Not a Limit KEY : <" + key + ">");
	}

	/**
	 * to identify a Limit in a hashmap , the key is a combination of leid ,
	 * subprofileid, LimitprofileId and the limitid , see constructLimitKey
	 * method . this method will extract the subprofileID from the LimitKey.
	 */
	public long extractSubProfileId(String key) throws EAICustomerMessageException {
		if (-1 != key.indexOf(LIMIT_KEY)) {
			return getToken(key, 2);
		}
		throw new EAICustomerMessageException("Not a Limit KEY : <" + key + ">");
	}

	/**
	 * to identify a Limit in a hashmap , the key is a combination of leid ,
	 * subprofileid, LimitprofileId and the limitid , see constructLimitKey
	 * method . this method will extract the limitprofileID from the LimitKey.
	 */
	public long extractLimitProfileId(String key) throws EAICustomerMessageException {
		if (-1 != key.indexOf(LIMIT_KEY)) {
			return getToken(key, 3);
		}
		throw new EAICustomerMessageException("Not a Limit KEY : <" + key + ">");
	}

	/**
	 * to identify a Limit in a hashmap , the key is a combination of leid ,
	 * subprofileid, LimitprofileId and the limitid , see constructLimitKey
	 * method . this method will extract the limitid from the LimitKey.
	 */
	public String extractLimitId(String key) throws EAICustomerMessageException {
		if (-1 != key.indexOf(LIMIT_KEY)) {
			return getTokenString(key, 4);
		}
		throw new EAICustomerMessageException("Not a Limit KEY : <" + key + ">");
	}

	/**
	 * to identify a Limit in a hashmap , the key is a combination of leid ,
	 * subprofileid, LimitprofileId and the limitid. This method will return the
	 * key .
	 */
	public String constructLimitKey(String leid, long subprofileid, long limitprofileid, String limitid) {
		return LIMIT_KEY + DELIMITER + leid + DELIMITER + subprofileid + DELIMITER + limitprofileid + DELIMITER
				+ limitid;
	}

	protected String resolveUpdateStatusInd(Object obj) {
		try {
			Method mUpdateInd = obj.getClass().getMethod("getUpdateStatusIndicator", (Class[])null);
			return (String) mUpdateInd.invoke(obj, (Object[])null);
		}
		catch (NoSuchMethodException mex) {
			throw new IllegalStateException(
					"Exception occured while resolving the update status indicator of the object ["
							+ obj.getClass().getName() + "]," + mex.getMessage());
		}
		catch (InvocationTargetException tex) {
			throw new IllegalStateException(
					"Exception occured while resolving the update status indicator of the object ["
							+ obj.getClass().getName() + "]," + tex.getMessage());
		}
		catch (IllegalAccessException aex) {
			throw new IllegalStateException(
					"Exception occured while resolving the update status indicator of the object ["
							+ obj.getClass().getName() + "]," + aex.getMessage());
		}
	}

	protected String resolveChangeStatusInd(Object obj) {
		try {
			Method mChangeInd = obj.getClass().getMethod("getChangeIndicator", (Class[])null);
			return (String) mChangeInd.invoke(obj, (Object[])null);
		}
		catch (NoSuchMethodException mex) {
			throw new IllegalStateException(
					"Exception occured while resolving the change status indicator of the object ["
							+ obj.getClass().getName() + "]," + mex.getMessage());
		}
		catch (InvocationTargetException tex) {
			throw new IllegalStateException(
					"Exception occured while resolving the change status indicator of the object ["
							+ obj.getClass().getName() + "]," + tex.getMessage());
		}
		catch (IllegalAccessException aex) {
			throw new IllegalStateException(
					"Exception occured while resolving the change status indicator of the object ["
							+ obj.getClass().getName() + "]," + aex.getMessage());
		}
	}

	public boolean isUpdate(Object obj) {

		String updateStatusInd = resolveUpdateStatusInd(obj);
		String changeStatusInd = resolveChangeStatusInd(obj);
		return ((changeStatusInd.equals(String.valueOf(CHANGEINDICATOR))) && (updateStatusInd.equals(String
				.valueOf(UPDATEINDICATOR))));
	}

	public boolean isDelete(Object obj) {
		String updateStatusInd = resolveUpdateStatusInd(obj);
		String changeStatusInd = resolveChangeStatusInd(obj);
		return ((changeStatusInd.equals(String.valueOf(CHANGEINDICATOR))) && (updateStatusInd.equals(String
				.valueOf(DELETEINDICATOR))));
	}

	/**
	 * This will return a collection LimitSecurityMap that has the leId,
	 * LimitProfileId, LImitId and subprofileId equivalent to the variable parse
	 * in .
	 */
	// public LimitsApprovedSecurityMap getLimitMap (Vector vecmap , String
	// limitLeId, long subprofileid, long limitprofileid, String limitid, String
	// secId, long mapId)
	public LimitsApprovedSecurityMap getLimitMap(Vector vecmap, String limitLeId, Long subprofileid, String limitid,
			String secId) {
		// if (!anyLimitSecMap(vecmap)) {
		// vecmap = new Vector();
		// }

		Iterator i = vecmap.iterator();
		while (i.hasNext()) {
			LimitsApprovedSecurityMap lmtsMap = (LimitsApprovedSecurityMap) i.next();
			if (lmtsMap.getCIFId().equals(limitLeId) && lmtsMap.getLimitId().equals(limitid)
					&& (lmtsMap.getSubProfileId().equals(subprofileid)) && lmtsMap.getSecurityId().equals(secId)) {
				return lmtsMap;
			}
		}
		return null;
	}

	/**
	 * This will return a collection LimitSecurityMap that has the same CMS
	 * Limit Security Map id
	 * 
	 */
	public LimitsApprovedSecurityMap getLimitMapByCMSId(Vector vecmap, long cmsId) {

		Iterator i = vecmap.iterator();
		while (i.hasNext()) {
			LimitsApprovedSecurityMap lmtsMap = (LimitsApprovedSecurityMap) i.next();
			if (lmtsMap.getLimitsApprovedSecurityMapId() == cmsId) {
				return lmtsMap;
			}
		}
		return null;
	}

	public Vector updateLimitSourceId(Vector limits, String limitSourceId) {
		if (limits != null) {
			Iterator iter = limits.iterator();

			while (iter.hasNext()) {
				Limits limit = (Limits) iter.next();

				limit.getLimitGeneral().setSourceId(limitSourceId);
			}
		}

		return limits;
	}
}
