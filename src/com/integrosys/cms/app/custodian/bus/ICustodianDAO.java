/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/ICustodianDAO.java,v 1.15 2006/09/29 02:35:37 czhou Exp $
 */
package com.integrosys.cms.app.custodian.bus;

//ofa
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.checklist.bus.ICheckListItemDAO;
import com.integrosys.cms.app.checklist.bus.ICheckListTableConstants;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelopeItem;

import java.util.List;

/**
 * This interface defines the constant specific to the custodian table and the
 * methods required by the custodian
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2006/09/29 02:35:37 $ Tag: $Name: $
 */
public interface ICustodianDAO {
	// public static final String CUSTODIAN_TABLE = "CMS_CUSTODIAN_DOC";

	public static final String STATUS = "STATUS";

	public static final String DOC_TYPE = "CATEGORY";

	public static final String DOC_SUB_TYPE = "SUB_CATEGORY";

	public static final String LIMIT_PROFILE_ID = "CMS_LSP_APPR_LMTS_ID";

	public static final String STAGE_CHECKLIST_ITEM_LAST_UPDATE_ALIAS = "STAGE_ITEM_LAST_UPDATE"; // bernard
																									// -
																									// added
																									// for
																									// CMS
																									// -
																									// 1476

	public static final String TRX_TABLE = ICMSTrxTableConstants.TRX_TBL_NAME;

	public static final String TRX_ID = ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID;

	public static final String TRX_DATE = ICMSTrxTableConstants.TRXTBL_TRANSACTION_DATE;

	public static final String TRX_REF_ID = ICMSTrxTableConstants.TRXTBL_REFERENCE_ID;

	public static final String TRX_TYPE = ICMSTrxTableConstants.TRXTBL_TRANSACTION_TYPE;

	public static final String TRX_STATUS = ICMSTrxTableConstants.TRXTBL_STATUS;

	public static final String TRX_STATUS_ALIAS = "TRXSTATUS";

	public static final String CHECKLIST_ITEM = ICheckListItemDAO.CHECKLIST_ITEM_TABLE;

	public static final String STAGE_CHECKLIST_ITEM_TABLE = ICheckListItemDAO.STAGE_CHECKLIST_ITEM_TABLE;

	public static final String ITEM_CHECKLIST_ID = ICheckListItemDAO.CHECKLIST_ID;

	public static final String ITEM_CHECKLIST_ITEM_ID = ICheckListItemDAO.DOC_ITEM_NO;

	public static final String ITEM_DOC_ITEM_REF = ICheckListItemDAO.DOC_ITEM_REF;

	public static final String ITEM_DOCUMENT_CODE = ICheckListItemDAO.DOCUMENT_CODE;

	public static final String ITEM_DOC_DESCRIPTION = ICheckListItemDAO.DOC_DESCRIPTION;

	public static final String ITEM_LAST_UPDATE_DATE = ICheckListItemDAO.LAST_UPDATE_DATE;

	public static final String ITEM_STATUS = ICheckListItemDAO.ITMTBL_STATUS;

	public static final String ITEM_CPC_CUST_STATUS = ICheckListItemDAO.CPC_CUST_STATUS;

	public static final String ITEM_IS_DELETED = ICheckListItemDAO.IS_DELETED;

	public static final String ITEM_STATUS_ALIAS = "ITEM_STATUS";

	public static final String STAGE_ITEM_STATUS_ALIAS = "STAGE_ITEM_STATUS";

	public static final String CHECKLIST_TABLE = ICheckListTableConstants.CHKLIST_TABLE;

	public static final String CHECKLIST_CHECKLISTID_PREF = ICheckListTableConstants.CHKTBL_CHECKLISTID_PREF;

	public static final String CHECKLIST_LIMIT_PROFILE_ID_PREF = ICheckListTableConstants.CHKTBL_LIMIT_PROFILE_ID_PREF;

	public static final String CHECKLIST_SUB_PROFILE_ID_PREF = ICheckListTableConstants.CHKTBL_BORROWER_ID_PREF;

	public static final String CHECKLIST_PLEDGOR_ID_PREF = ICheckListTableConstants.CHKTBL_PLEDGER_ID_PREF;

	public static final String CHECKLIST_COLLATERAL_ID_PREF = ICheckListTableConstants.CHKTBL_COLLATERAL_ID_PREF;

	public static final String CHECKLIST_CATEGORY_PREF = ICheckListTableConstants.CHKTBL_CATEGORY_PREF;

	public static final String CHECKLIST_SUB_CATEGORY_PREF = ICheckListTableConstants.CHKTBL_SUB_CATEGORY_PREF;

	public static final String CHECKLIST_DOC_LOC_CTRY_PREF = ICheckListTableConstants.CHKTBL_DOC_LOC_CTRY_PREF;

	public static final String CHECKLIST_DOC_LOC_ORG_PREF = ICheckListTableConstants.CHKTBL_DOC_LOC_ORG_PREF;

	public SearchResult searchCustodianDoc(CustodianSearchCriteria aCustodianSearchCriteria) throws SearchDAOException;

	public long getTrxID(CustodianSearchCriteria searchCriteria) throws SearchDAOException;

    public boolean getCheckEnvelopeBarcodeExist(long limitprofile, String envBarcode) throws SearchDAOException;

    public boolean getCheckDocItemBarcodeExist(String docItemBarcode, long checkListItemRefID) throws SearchDAOException;

    public ISecEnvelopeItem getSecEnvItemLoc(String docItemBarcode) throws SearchDAOException;

	public ICustodianDoc getNewDoc(long checkListID) throws SearchDAOException;

	public ICustodianDocItem[] getNewItems(long custodianDocID) throws SearchDAOException;

	public ICustodianDocItem[] getDocByChecklistItemRefID(Long[] itemRefList) throws SearchDAOException;
}
