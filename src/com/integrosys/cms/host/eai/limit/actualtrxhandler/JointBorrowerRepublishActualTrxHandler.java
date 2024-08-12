package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.NoSuchJointBorrowerException;
import com.integrosys.cms.host.eai.limit.bus.JointBorrower;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.limit.support.LimitProfileHelper;

/**
 * <p>
 * Handler to process Joint Borrower for REPUBLISH publishing.
 * <p>
 * It will fix the indicator based on the comparison between Joint Borrowers
 * between source and CMS.
 * @author Chong Jun Yong
 * 
 */
public class JointBorrowerRepublishActualTrxHandler extends JointBorrowerActualTrxHandler {
	public Message preprocess(Message msg) {
		AAMessageBody aaMsg = (AAMessageBody) msg.getMsgBody();

		LimitProfile limitProfile = aaMsg.getLimitProfile();

		aaMsg.setJointBorrower(fixJoinBorrowerRepublishInd(aaMsg.getJointBorrower(), limitProfile));

		return msg;
	}

	private Vector fixJoinBorrowerRepublishInd(Vector joinBorrowers, LimitProfile limitProfile) {
		LimitProfileHelper hp = new LimitProfileHelper();

		if (joinBorrowers == null) {
			return new Vector();
		}

		Iterator joinBorrowersIter = joinBorrowers.iterator();
		while (joinBorrowersIter.hasNext()) {
			JointBorrower joinBorrower = (JointBorrower) joinBorrowersIter.next();

			joinBorrower.setUpdateStatusIndicator(String.valueOf(CREATEINDICATOR));
			joinBorrower.setChangeIndicator(String.valueOf(CHANGEINDICATOR));
		}

		HashMap parameters = new HashMap();
		if ((limitProfile.getCMSLimitProfileId() != null) && (limitProfile.getCMSLimitProfileId().longValue() > 0)) {
			parameters.put("cmsLimitProfileId", limitProfile.getCMSLimitProfileId());
		}
		else {
			parameters.put("AANumber", limitProfile.getLOSAANumber());
		}

		List existingJointBorrowers = getLimitDao().retrieveNonDeletedObjectsListByParameters(parameters,
				"updateStatusIndicator", JointBorrower.class);

		if (existingJointBorrowers.isEmpty()) {
			return joinBorrowers;
		}

		if (existingJointBorrowers != null) {
			Iterator existingJoinBorrIter = existingJointBorrowers.iterator();
			while (existingJoinBorrIter.hasNext()) {
				JointBorrower existingJointBorrower = (JointBorrower) existingJoinBorrIter.next();

				try {
					JointBorrower updatingJointBorrower = hp.getJointBorrower(joinBorrowers, existingJointBorrower
							.getCIFId(), existingJointBorrower.getAANumber());
					updatingJointBorrower.setUpdateStatusIndicator(String.valueOf(UPDATEINDICATOR));
					updatingJointBorrower.setChangeIndicator(String.valueOf(CHANGEINDICATOR));
				}
				catch (NoSuchJointBorrowerException ex) {
					JointBorrower deletingJointBorrower = new JointBorrower();
					AccessorUtil.copyValue(existingJointBorrower, deletingJointBorrower);
					deletingJointBorrower.setUpdateStatusIndicator(String.valueOf(DELETEINDICATOR));
					deletingJointBorrower.setChangeIndicator(String.valueOf(CHANGEINDICATOR));
					joinBorrowers.add(deletingJointBorrower);
				}
			}

		}

		return joinBorrowers;
	}
}
