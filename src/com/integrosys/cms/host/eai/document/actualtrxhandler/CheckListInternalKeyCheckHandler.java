package com.integrosys.cms.host.eai.document.actualtrxhandler;

import java.util.Iterator;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.document.DocumentInputMessageBody;
import com.integrosys.cms.host.eai.document.NoSuchCheckListException;
import com.integrosys.cms.host.eai.document.NoSuchCheckListItemException;
import com.integrosys.cms.host.eai.document.bus.CheckList;
import com.integrosys.cms.host.eai.document.bus.CheckListItem;
import com.integrosys.cms.host.eai.document.bus.IDocumentDao;
import com.integrosys.cms.host.eai.document.bus.IDocumentDaoJdbc;
import com.integrosys.cms.host.eai.security.NoSuchSecurityException;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;

/**
 * <p>
 * First handler to check the internal available in the message object.
 * <p>
 * The key to check are the keys of checklist, checklist item and collateral.
 * This will also determine whether the checklist will be updated or inserted.
 * 
 * @author Chong Jun Yong
 * 
 */
public class CheckListInternalKeyCheckHandler extends AbstractCommonActualTrxHandler {

	private IDocumentDao documentDao;

	private IDocumentDaoJdbc documentJdbcDao;

	public IDocumentDao getDocumentDao() {
		return documentDao;
	}

	public IDocumentDaoJdbc getDocumentJdbcDao() {
		return documentJdbcDao;
	}

	public void setDocumentDao(IDocumentDao documentDao) {
		this.documentDao = documentDao;
	}

	public void setDocumentJdbcDao(IDocumentDaoJdbc documentJdbcDao) {
		this.documentJdbcDao = documentJdbcDao;
	}

	public Message persistActualTrx(Message msg) {
		EAIMessage eaiMessage = (EAIMessage) msg;

		DocumentInputMessageBody msgBody = (DocumentInputMessageBody) eaiMessage.getMsgBody();
		CheckList checkList = msgBody.getCheckList();

		if (checkList.getCheckListID() > 0) {
			CheckList storedCheckList = (CheckList) getDocumentDao().retrieve(new Long(checkList.getCheckListID()),
					"actualCheckListEntity");
			if (storedCheckList == null) {
				throw new NoSuchCheckListException(checkList.getCheckListID());
			}

			checkList.setUpdateStatusIndicator(UPDATEINDICATOR);
			checkList.setChangeIndicator(CHANGEINDICATOR);
		}
		else {
			checkList.setUpdateStatusIndicator(CREATEINDICATOR);
			checkList.setChangeIndicator(CHANGEINDICATOR);
		}

		for (Iterator itr = checkList.getCheckListItem().iterator(); itr.hasNext();) {
			CheckListItem item = (CheckListItem) itr.next();
			if (item.getCmsDocItemID() > 0) {
				CheckListItem storedCheckListItem = (CheckListItem) getDocumentDao().retrieve(
						new Long(item.getCmsDocItemID()), "actualCheckListItemEntity");

				if (storedCheckListItem == null) {
					throw new NoSuchCheckListItemException(item.getCmsDocItemID());
				}

				item.setUpdateStatusIndicator(Character.toString(UPDATEINDICATOR));
				item.setChangeIndicator(Character.toString(CHANGEINDICATOR));
			}
			else {
				item.setUpdateStatusIndicator(Character.toString(CREATEINDICATOR));
				item.setChangeIndicator(Character.toString(CHANGEINDICATOR));
			}
		}

		if (checkList.getScChecklist() != null && checkList.getScChecklist().getCmsCollateralID() > 0) {
			ApprovedSecurity sec = getDocumentJdbcDao().getCollateralByID(
					checkList.getScChecklist().getCmsCollateralID());

			if (sec == null) {
				throw new NoSuchSecurityException(checkList.getScChecklist().getCmsCollateralID());
			}
		}

		return eaiMessage;
	}
}
