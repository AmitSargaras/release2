package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;

/**
 * <p>
 * Handler to do the clearing/insertion of response log
 * <p>
 * A response log will be created if it's not found, but without the response
 * fire date (which scheduler will pick it up based on this fire date is null).
 * Or else response log fire date will be cleared, and AA received date will be
 * updated to current date.
 * 
 * @author Chong Jun Yong
 * 
 */
public class LimitProfileResponseCleanupActualTrxHandler extends AbstractCommonActualTrxHandler {

	private ILimitDAO limitJdbcDao;

	private String[] filteredApplicationTypes;

	public void setLimitJdbcDao(ILimitDAO limitJdbcDao) {
		this.limitJdbcDao = limitJdbcDao;
	}

	public void setFilteredApplicationTypes(String[] filteredApplicationTypes) {
		this.filteredApplicationTypes = filteredApplicationTypes;
	}

	public Message persistActualTrx(Message msg) {
		AAMessageBody aaMessageBody = (AAMessageBody) msg.getMsgBody();
		LimitProfile theLimitProfile = aaMessageBody.getLimitProfile();

		if (!ArrayUtils.contains(this.filteredApplicationTypes, theLimitProfile.getAAType())
				&& String.valueOf(CHANGEINDICATOR).equals(theLimitProfile.getChangeIndicator())) {
			ILimitProfile limitProfile = new OBLimitProfile();
			limitProfile.setLimitProfileID(theLimitProfile.getLimitProfileId());
			limitProfile.setBCAReference(theLimitProfile.getHostAANumber());
			limitProfile.setLosLimitProfileReference(theLimitProfile.getLOSAANumber());
			limitProfile.setSourceID(theLimitProfile.getAASource());

			try {
				this.limitJdbcDao.insertOrUpdateLimitProfileResponseLog(limitProfile);
			}
			catch (Throwable ex) {
				logger.error("Failed to clear limit profile response log for Limit Profile internal key ["
						+ theLimitProfile.getLimitProfileId() + "], please verify.", ex);
			}
		}

		return msg;
	}
}
