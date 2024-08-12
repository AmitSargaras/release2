package com.integrosys.cms.host.eai.covenant.handler.actualtrxhandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.covenant.CovenantMessageBody;
import com.integrosys.cms.host.eai.covenant.NoSuchCovenantException;
import com.integrosys.cms.host.eai.covenant.bus.CovenantItem;
import com.integrosys.cms.host.eai.covenant.bus.ICovenantDao;
import com.integrosys.cms.host.eai.covenant.bus.RecurrentDoc;
import com.integrosys.cms.host.eai.covenant.bus.StageConvenantItem;
import com.integrosys.cms.host.eai.covenant.bus.StageRecurrentDoc;
import com.integrosys.cms.host.eai.covenant.handler.ConvenantHandlerHelper;
import com.integrosys.cms.host.eai.limit.NoSuchLimitProfileException;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;

/**
 * 
 * @author Thurein</br>
 * @version 1.0</br>
 * @since 1.1
 * 
 */
public class CovenantActualTrxHandler extends AbstractCommonActualTrxHandler {

	private String COVENANT_KEY = IEaiConstant.COVENANT_KEY;

	private String[] NOT_COPIED_VALUES = new String[] { "getRecurrentDocId", "getCMSconvenantItemRefID",
			"getCMSCovenantItemID" };

	private ConvenantHandlerHelper convenantHandlerHelper;

	private ICovenantDao covenantDao;

	private String defaultRemarksIfBlank;

	public ConvenantHandlerHelper getConvenantHandlerHelper() {
		return convenantHandlerHelper;
	}

	public ICovenantDao getCovenantDao() {
		return covenantDao;
	}

	public String getDefaultRemarksIfBlank() {
		return defaultRemarksIfBlank;
	}

	public void setConvenantHandlerHelper(ConvenantHandlerHelper convenantHandlerHelper) {
		this.convenantHandlerHelper = convenantHandlerHelper;
	}

	public void setCovenantDao(ICovenantDao covenantDao) {
		this.covenantDao = covenantDao;
	}

	public void setDefaultRemarksIfBlank(String defaultRemarksIfBlank) {
		this.defaultRemarksIfBlank = defaultRemarksIfBlank;
	}

	public Message persistActualTrx(Message msg) {
		CovenantMessageBody covenantMsgBdy = (CovenantMessageBody) msg.getMsgBody();

		RecurrentDoc recurrentDoc = (RecurrentDoc) covenantMsgBdy.getRecurrentDoc();

		Vector covenantItems = recurrentDoc.getConvenantItems();

		if (convenantHandlerHelper.isCovenantItemsChanged(covenantItems)) {
			LimitProfile existingLimitProfile = (LimitProfile) getCovenantDao().getLimitProfile(
					recurrentDoc.getLOSAANumber());

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
					recurrentDoc.setCmsLimitProfileID(limitProfileID);
					recurrentDoc.setCmsSubProfileID(subProfileID);
					storedRecurrenDoc = createNewRecurrentDoc(RecurrentDoc.class, limitProfileID, subProfileID);
					storedRecurrenDoc.setStatus(ICMSConstant.ACTION_SYSTEM_CREATE_CHECKLIST);
				}
				else {
					storedRecurrenDoc.setStatus(ICMSConstant.ACTION_SYSTEM_UPDATE_CHECKLIST);
				}
				storedRecurrenDoc.setLOSAANumber(recurrentDoc.getLOSAANumber());

				for (Iterator itr = covenantItems.iterator(); itr.hasNext();) {
					CovenantItem item = (CovenantItem) itr.next();
					item.setDueDate(existingLimitProfile.getAAApproveDate());
					if (StringUtils.isBlank(item.getRemarks())
							&& ICMSConstant.SOURCE_SYSTEM_RLOS.equals(msg.getMsgHeader().getSource())) {
						item.setRemarks(getDefaultRemarksIfBlank());
					}
				}

				Vector storedConvItems = persistConvenantItems(storedRecurrenDoc.getRecurrentDocID(), covenantItems,
						msg.getMsgHeader().getSource());

				storedRecurrenDoc.setConvenantItems(storedConvItems);

				covenantMsgBdy.setRecurrentDoc(storedRecurrenDoc);

				msg.setMsgBody(covenantMsgBdy);
			}
			else {
				throw new NoSuchLimitProfileException(recurrentDoc.getLOSAANumber());
			}
		}
		return msg;
	}

	public Message persistStagingTrx(Message msg, Object trxValueMap) {

		CovenantMessageBody covenantMsgBdy = (CovenantMessageBody) msg.getMsgBody();

		RecurrentDoc actualRrecurrentDoc = (RecurrentDoc) covenantMsgBdy.getRecurrentDoc();

		long limitProfileID = actualRrecurrentDoc.getCmsLimitProfileID();

		long subProfileID = actualRrecurrentDoc.getCmsSubProfileID();

		HashMap trxMap = new HashMap();

		if (trxValueMap != null) {
			trxMap = (HashMap) trxValueMap;
		}

		IRecurrentCheckListTrxValue trxValue = (IRecurrentCheckListTrxValue) trxMap.get(actualRrecurrentDoc
				.getLOSAANumber());

		if (trxValue == null) {
			logger.warn("#persitStagingTrx cannot find RecurrentCheckList trx , for covenant id ["
					+ actualRrecurrentDoc.getLOSAANumber() + "]");
		}

		if (convenantHandlerHelper.isCovenantItemsChanged(actualRrecurrentDoc.getConvenantItems())) {
			StageRecurrentDoc storedStgRecurrenDoc = (StageRecurrentDoc) getCovenantDao().getRecurrentDoc(
					limitProfileID, subProfileID, StageRecurrentDoc.class);
			/**
			 * If there is no recurrent Doc record, add a record first into
			 * Recurrent Doc before inserting a Covenant Item
			 **/
			if (storedStgRecurrenDoc == null) {
				storedStgRecurrenDoc = (StageRecurrentDoc) createNewRecurrentDoc(StageRecurrentDoc.class,
						limitProfileID, subProfileID);
			}

			Vector stgCovenantItems = convenantHandlerHelper.copyConvItemsForStaging(actualRrecurrentDoc
					.getConvenantItems());

			Vector storedStgCovenantItems = persistConvenantItems(storedStgRecurrenDoc.getRecurrentDocID(),
					stgCovenantItems, msg.getMsgHeader().getSource());

			storedStgRecurrenDoc.setConvenantItems(storedStgCovenantItems);

			covenantMsgBdy.setRecurrentDoc(storedStgRecurrenDoc);

			msg.setMsgBody(covenantMsgBdy);
		}

		return msg;
	}

	public Vector persistConvenantItems(long recurrentID, Vector convItems, String sourceID) {
		Iterator iter = convItems.iterator();

		Vector persistedconvItems = new Vector();

		while (iter.hasNext()) {
			CovenantItem convItem = (CovenantItem) iter.next();

			/** Assign the default parameterlized value to N **/
			if (convItem.getIsParameterized() == null) {
				convItem.setIsParameterized(ICMSConstant.FALSE_VALUE);
			}

			convItem.setSourceID(sourceID);

			/** set the due date to the same as doc end date **/
			convItem.setDueDate(convItem.getDocEndDate());

			// set the default for the oneOff and riskTrigger to 'Y'
			convItem.setRiskTrigger(ICMSConstant.TRUE_VALUE);

			convItem.setIsDeleted(ICMSConstant.FALSE_VALUE);
			if (convenantHandlerHelper.isCreateCovenantItem(convItem)) {
				createCovenantItem(recurrentID, convItem);

			}
			else if (convenantHandlerHelper.isUpdateCovenantItem(convItem)) {
				CovenantItem tmpCovItm = getCovenantDao().getConvenantItem(convItem.getCMSCovenantItemID().longValue(),
						recurrentID, convItem.getClass());

				// For the convenants which are created from CMS, need to use
				// refid to get the staging convenant
				if (tmpCovItm == null && convItem.getClass().equals(StageConvenantItem.class)) {
					tmpCovItm = getCovenantDao().getConvenantItem(convItem.getCMSconvenantItemRefID().longValue(),
							recurrentID, convItem.getClass());
				}

				if (tmpCovItm != null) {
					AccessorUtil.copyValue(convItem, tmpCovItm, NOT_COPIED_VALUES);
					getCovenantDao().update(tmpCovItm, convItem.getClass());
					convItem = tmpCovItm;
				}
				else {
					throw new NoSuchCovenantException(convItem.getCMSCovenantItemID());
				}
			}
			else if (convenantHandlerHelper.isDeleteCovenantItem(convItem)) {
				convItem.setIsDeleted(ICMSConstant.TRUE_VALUE);
				CovenantItem tmpCovItm = getCovenantDao().getConvenantItem(convItem.getCMSCovenantItemID().longValue(),
						recurrentID, convItem.getClass());
				if (tmpCovItm != null) {
					tmpCovItm.setIsDeleted(ICMSConstant.TRUE_VALUE);

					getCovenantDao().update(tmpCovItm, convItem.getClass());
					// convItem.setIsDeleted(tmpCovItm.getIsDeleted());
				}
			}
			persistedconvItems.add(convItem);
		}
		return persistedconvItems;
	}

	private CovenantItem createCovenantItem(long recurrentID, CovenantItem convItem) {
		convItem.setRecurrentDocId(new Long(recurrentID));
		if (convItem.getClass() == CovenantItem.class) {
			/**
			 * for temporary persist, assign LONG_INVALID_VALUE to
			 * CMSconvenantItemRefID because this is a not null field in the db
			 **/

			convItem.setCMSconvenantItemRefID(new Long(ICMSConstant.LONG_INVALID_VALUE));
			Long cmsCovenantItemID = (Long) getCovenantDao().store(convItem, CovenantItem.class);
			convItem.setCMSCovenantItemID(cmsCovenantItemID);
			convItem.setCMSconvenantItemRefID(cmsCovenantItemID);
			getCovenantDao().update(convItem, CovenantItem.class);
			return convItem;
		}
		else if (convItem.getClass() == StageConvenantItem.class) {
			Long cmsCovenantItemID = (Long) getCovenantDao().store(convItem, StageConvenantItem.class);
			convItem.setCMSCovenantItemID(cmsCovenantItemID);
			getCovenantDao().update(convItem, StageConvenantItem.class);
			return convItem;
		}
		else {
			return null;
		}

	}

	/**
	 * 
	 * @param recurrentdoc Class for the persisting recurrentdoc i.e actual or
	 *        staging
	 * @param limitProfileID long
	 * @param subPorfileID long
	 * @return stored RecurrentDoc object
	 */
	public RecurrentDoc createNewRecurrentDoc(Class recurrentdoc, long limitProfileID, long subPorfileID) {
		/** Actual Recurrent Doc **/
		if (recurrentdoc == RecurrentDoc.class) {
			RecurrentDoc recDoc = new RecurrentDoc();

			recDoc.setCmsLimitProfileID(limitProfileID);

			recDoc.setCmsSubProfileID(subPorfileID);

			Long recurrentDocID = (Long) getCovenantDao().store(recDoc, RecurrentDoc.class, "actualRecurrent");

			recDoc.setRecurrentDocID(recurrentDocID.longValue());

			return recDoc;
		}

		/** Stage Recurrent Doc **/
		else if (recurrentdoc == StageRecurrentDoc.class) {
			StageRecurrentDoc recDoc = new StageRecurrentDoc();

			recDoc.setCmsLimitProfileID(limitProfileID);

			recDoc.setCmsSubProfileID(subPorfileID);

			Long recurrentDocID = (Long) getCovenantDao().store(recDoc, StageRecurrentDoc.class, "stageRecurrent");

			recDoc.setRecurrentDocID(recurrentDocID.longValue());

			return recDoc;
		}

		else {
			return null;
		}

	}

	/**
	 * Return transaction key that is used by this actual business data.
	 * 
	 * @return covenant transaction key
	 */
	public String getTrxKey() {
		return COVENANT_KEY;
	}

}
