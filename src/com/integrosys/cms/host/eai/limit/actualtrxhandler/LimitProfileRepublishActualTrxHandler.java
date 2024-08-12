package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.CifMismatchException;
import com.integrosys.cms.host.eai.limit.NoSuchCreditGradeException;
import com.integrosys.cms.host.eai.limit.bus.LimitCreditGrade;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.limit.support.LimitProfileHelper;

/**
 * Limit Profile Actual Trx Handler for Republish Type
 * 
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.0
 */

public class LimitProfileRepublishActualTrxHandler extends LimitProfileActualTrxHandler {

	public Message preprocess(Message msg) {
		// call parent super method
		msg = super.preprocess(msg);

		AAMessageBody aaMsg = (AAMessageBody) msg.getMsgBody();

		LimitProfile limitProfile = aaMsg.getLimitProfile();

		String source = msg.getMsgHeader().getSource();

		Map parameters = new HashMap();
		if ((limitProfile.getCMSLimitProfileId() != null) && (limitProfile.getCMSLimitProfileId().longValue() > 0)) {
			parameters.put("limitProfileId", limitProfile.getCMSLimitProfileId());
		}
		else {
			parameters.put("LOSAANumber", limitProfile.getLOSAANumber());
		}
		parameters.put("AASource", source);

		aaMsg.setLimitProfile(fixLimitProfileRepublishInd(limitProfile, parameters));

		if (aaMsg.getCreditGrade() != null) {
			fixCreditGradeRepublishInd(aaMsg.getCreditGrade(), limitProfile.getLOSAANumber());
		}

		return msg;
	}

	protected LimitProfile fixLimitProfileRepublishInd(LimitProfile limitProfile, Map parameters) {
		limitProfile.setUpdateStatusIndicator(String.valueOf(CREATEINDICATOR));
		limitProfile.setChangeIndicator(String.valueOf(CHANGEINDICATOR));
		
		List limitProfileList = getLimitDao().retrieveObjectListByParameters(parameters, LimitProfile.class);

		if (!limitProfileList.isEmpty()) {
			LimitProfile storedLimitProfile = (LimitProfile) limitProfileList.get(0);
			if (storedLimitProfile != null) {
				if (!storedLimitProfile.getCIFId().equals(limitProfile.getCIFId())) {
					throw new CifMismatchException(storedLimitProfile.getLOSAANumber(), storedLimitProfile
							.getAASource(), limitProfile.getCIFId(), storedLimitProfile.getCIFId());
				}
				limitProfile.setUpdateStatusIndicator(String.valueOf(UPDATEINDICATOR));
				limitProfile.setChangeIndicator(String.valueOf(CHANGEINDICATOR));
				limitProfile.setLimitProfileId(storedLimitProfile.getLimitProfileId());
			}
		}

		return limitProfile;
	}

	private Vector fixCreditGradeRepublishInd(Vector creditGrades, String aaNo) {
		LimitProfileHelper hp = new LimitProfileHelper();

		Iterator creditGradesIter = creditGrades.iterator();
		while (creditGradesIter.hasNext()) {
			LimitCreditGrade limitCreditGrade = (LimitCreditGrade) creditGradesIter.next();

			limitCreditGrade.setUpdateStatusIndicator(String.valueOf(CREATEINDICATOR));
			limitCreditGrade.setChangeIndicator(String.valueOf(CHANGEINDICATOR));
		}

		HashMap parameters = new HashMap();
		parameters.put("LOSAANumber", aaNo);
		List existingCreditGrades = getLimitDao().retrieveNonDeletedObjectsListByParameters(parameters,
				"updateStatusIndicator", LimitCreditGrade.class);

		if (existingCreditGrades.isEmpty()) {
			return creditGrades;
		}

		if (existingCreditGrades != null) {
			Iterator existingCreitGradeIter = existingCreditGrades.iterator();
			while (existingCreitGradeIter.hasNext()) {
				LimitCreditGrade existingCreitGrade = (LimitCreditGrade) existingCreitGradeIter.next();

				try {
					LimitCreditGrade updatingCreitGrade = hp.getLimitCreditGrade(creditGrades, existingCreitGrade
							.getCreditGradeId(), existingCreitGrade.getLOSAANumber());
					updatingCreitGrade.setUpdateStatusIndicator(String.valueOf(UPDATEINDICATOR));
					updatingCreitGrade.setChangeIndicator(String.valueOf(CHANGEINDICATOR));
				}
				catch (NoSuchCreditGradeException ex) {
					existingCreitGrade.setUpdateStatusIndicator(String.valueOf(DELETEINDICATOR));
					existingCreitGrade.setChangeIndicator(String.valueOf(CHANGEINDICATOR));

					try {
						existingCreitGrade = (LimitCreditGrade) AccessorUtil.deepClone(existingCreitGrade);
						creditGrades.add(existingCreitGrade);
					}
					catch (Throwable t) {
						IllegalStateException isex = new IllegalStateException(
								"error cloning credit grade with AA Number[" + existingCreitGrade.getLOSAANumber()
										+ "] credit grade id[" + existingCreitGrade.getCreditGradeId() + "] details ["
										+ t.getMessage() + "]");
						isex.initCause(t);
						throw isex;
					}

				}
			}

		}

		return creditGrades;
	}

}
