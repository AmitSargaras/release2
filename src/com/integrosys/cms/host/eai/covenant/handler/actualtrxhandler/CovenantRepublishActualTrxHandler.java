package com.integrosys.cms.host.eai.covenant.handler.actualtrxhandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.covenant.CovenantMessageBody;
import com.integrosys.cms.host.eai.covenant.bus.CovenantItem;
import com.integrosys.cms.host.eai.covenant.bus.RecurrentDoc;
import com.integrosys.cms.host.eai.covenant.handler.CovenantRepublishHandlerHelper;
import com.integrosys.cms.host.eai.limit.NoSuchLimitProfileException;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;

/**
 * 
 * @author Thurein </br>
 * @since 1.1 </br> Handler to republish the convenant. The main logic is to fix
 *        the update and chage indicator based on whether the records are
 *        already stored in the database or not.
 * 
 */
public class CovenantRepublishActualTrxHandler extends CovenantActualTrxHandler {

	private CovenantRepublishHandlerHelper convenantRepublichHandlerHelper;

	public CovenantRepublishHandlerHelper getConvenantRepublichHandlerHelper() {
		return convenantRepublichHandlerHelper;
	}

	public void setConvenantRepublichHandlerHelper(CovenantRepublishHandlerHelper convenantRepublichHandlerHelper) {
		this.convenantRepublichHandlerHelper = convenantRepublichHandlerHelper;
	}

	private RecurrentDoc getExistingRecurrentDoc(RecurrentDoc rec) {

		LimitProfile existingLimitProfile = (LimitProfile) getCovenantDao().getLimitProfile(rec.getLOSAANumber());

		if (existingLimitProfile != null) {

			long limitProfileID = existingLimitProfile.getLimitProfileId();

			long subProfileID = existingLimitProfile.getCmsSubProfileId();

			RecurrentDoc storedRecurrenDoc = (RecurrentDoc) getCovenantDao().getRecurrentDoc(limitProfileID,
					subProfileID, RecurrentDoc.class);

			/**
			 * If there is no recurrent Doc record, add a record first into
			 * Recurrent Doc before inserting a Covenant Item
			 **/

			if (storedRecurrenDoc == null) {
				rec.setCmsLimitProfileID(limitProfileID);
				rec.setCmsSubProfileID(subProfileID);
				storedRecurrenDoc = createNewRecurrentDoc(RecurrentDoc.class, limitProfileID, subProfileID);
				storedRecurrenDoc.setStatus(ICMSConstant.ACTION_SYSTEM_CREATE_CHECKLIST);
			}
			else {
				storedRecurrenDoc.setStatus(ICMSConstant.ACTION_SYSTEM_UPDATE_CHECKLIST);
			}
			storedRecurrenDoc.setLOSAANumber(rec.getLOSAANumber());
			return storedRecurrenDoc;
		}
		else {
			throw new NoSuchLimitProfileException(rec.getLOSAANumber());
		}
	}

	public Message preprocess(Message msg) {
		// call parent super method
		msg = super.preprocess(msg);

		CovenantMessageBody siMsg = (CovenantMessageBody) msg.getMsgBody();

		RecurrentDoc recurrentDoc = siMsg.getRecurrentDoc();

		RecurrentDoc existingRecurrent = getExistingRecurrentDoc(recurrentDoc);

		existingRecurrent.setConvenantItems(fixCovenantRepublishInd(recurrentDoc.getConvenantItems(), existingRecurrent
				.getRecurrentDocID()));

		siMsg.setRecurrentDoc(existingRecurrent);

		return msg;
	}

	protected Vector fixCovenantRepublishInd(Vector covItems, long recurrentDocID) {

		Vector deletingCovItems = new Vector();

		if (covItems == null) {
			covItems = new Vector();
		}

		for (int i = covItems.size() - 1; i >= 0; i--) {
			CovenantItem tmp = (CovenantItem) covItems.elementAt(i);

			tmp.setUpdateStatusIndicator(IEaiConstant.UPDATE_STATUS_IND_INSERT);
			tmp.setChangeIndicator(IEaiConstant.CHANGE_INDICATOR_YES);
		}

		Map parameters = new HashMap();
		parameters.put("isDeleted", ICMSConstant.FALSE_VALUE);
		parameters.put("recurrentDocId", new Long(recurrentDocID));

		List storedCovItems = getCovenantDao().retrieveObjectsListByParameters(parameters, CovenantItem.class);

		int count = 0;
		for (Iterator itr = storedCovItems.iterator(); itr.hasNext();) {
			CovenantItem value = (CovenantItem) itr.next();

			CovenantItem v = convenantRepublichHandlerHelper.getCovenantItem(covItems, value.getCMSCovenantItemID()
					.longValue());

			++count;

			if (v == null) {
				value.setUpdateStatusIndicator(IEaiConstant.UPDATE_STATUS_IND_DELETE);
				value.setChangeIndicator(IEaiConstant.CHANGE_INDICATOR_YES);

				logger.debug("Convenant Item is null, deleting this entry with cms id : ["
						+ value.getCMSCovenantItemID() + "]");

				CovenantItem deletingCovItem = new CovenantItem();
				AccessorUtil.copyValue(value, deletingCovItem);
				deletingCovItems.addElement(deletingCovItem);

			}
			else {
				v.setUpdateStatusIndicator(IEaiConstant.UPDATE_STATUS_IND_UPDATE);
				v.setChangeIndicator(IEaiConstant.CHANGE_INDICATOR_YES);

				logger.debug("Convenant Item is not null, updating this entry with cms id : ["
						+ value.getCMSCovenantItemID() + "]");
			}
		}
		covItems.addAll(deletingCovItems);
		return covItems;
	}

}
