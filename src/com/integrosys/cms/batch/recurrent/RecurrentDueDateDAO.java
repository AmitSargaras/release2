/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/recurrent/RecurrentDueDateDAO.java,v 1.13 2006/09/29 11:31:58 hshii Exp $
 */
package com.integrosys.cms.batch.recurrent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.recurrent.bus.IConvenant;
import com.integrosys.cms.app.recurrent.bus.IConvenantSubItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListTableConstants;
import com.integrosys.cms.app.recurrent.bus.OBConvenant;
import com.integrosys.cms.app.recurrent.bus.OBConvenantSubItem;
import com.integrosys.cms.app.recurrent.bus.OBRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.OBRecurrentCheckListSubItem;

/**
 * DAO for recurrent checklist sub items.
 * @author $Author: hshii $<br>
 * @version $Revision: 1.13 $
 * @since $Date: 2006/09/29 11:31:58 $ Tag: $Name: $
 */
public class RecurrentDueDateDAO extends JdbcDaoSupport implements IRecurrentDueDateDAO,
		IRecurrentCheckListTableConstants {

	private final static String QUERY_GET_RECURRENT_ITEM = "SELECT"
			+ "  cms_recurrent_doc_item.recurrent_item_id,"
			+ "  cms_recurrent_doc_item.recurrent_item_ref_id,"
			+ "  cms_recurrent_doc_item.recurrent_doc_id,"
			+ "  cms_recurrent_doc_item.frequency,"
			+ "  cms_recurrent_doc_item.frequency_unit,"
			+ "  cms_recurrent_doc_item.due_date,"
			+ "  cms_recurrent_doc_item.doc_end_date,"
			+ "  cms_recurrent_doc_item.grace_period,"
			+ "  cms_recurrent_doc_item.grace_period_unit,"
			+ "  cms_recurrent_doc_item.chase_remind_ind,"
			+ "  cms_recurrent_doc_item.one_off,"
			+ "  cms_recurrent_doc_item.last_doc_entry_date,"
			+ "  cms_recurrent_doc_item.remarks,"
			+ "  cms_recurrent_doc_sub_item.sub_item_id,"
			+ "  cms_recurrent_doc_sub_item.sub_item_ref_id,"
			+ "  cms_recurrent_doc_sub_item.doc_end_date sub_end_date,"
			+ "  cms_recurrent_doc_sub_item.due_date sub_due_date,"
			+ "  cms_recurrent_doc_sub_item.rec_date,"
			+ "  cms_recurrent_doc_sub_item.deferred_date,"
			+ "  cms_recurrent_doc_sub_item.print_reminder_ind,"
			+ "  cms_recurrent_doc_sub_item.waived_date,"
			+ "  cms_recurrent_doc_sub_item.deferred_cnt,"
			+ "  cms_recurrent_doc_sub_item.remarks sub_remarks,"
			+ "  cms_recurrent_doc_sub_item.status sub_status,"
			// +
			// "  decode (end_date_changed_count, 0 , 'NO', 'YES' ) is_enddate_changed "
			+ "  (CASE  end_date_changed_count WHEN  0  THEN  'NO' ELSE  'YES' END ) is_enddate_changed "
			+ " FROM"
			+ "  cms_recurrent_doc_item  LEFT OUTER JOIN cms_recurrent_doc_sub_item "
			+ "    ON cms_recurrent_doc_item.recurrent_item_id = cms_recurrent_doc_sub_item.recurrent_item_id "
			// +
			// "WHERE cms_recurrent_doc_item.recurrent_item_id = cms_recurrent_doc_sub_item.recurrent_item_id(+) "
			+ "  WHERE cms_recurrent_doc_item.is_deleted = 'N' "
			+ "  AND cms_recurrent_doc_sub_item.is_deleted_ind = 'N' " + "  AND " + "  ( " + "  SELECT cms_bca_status "
			+ "  FROM" + "    sci_lsp_lmt_profile p," + "    cms_recurrent_doc rd "
			+ "  WHERE p.cms_lsp_lmt_profile_id = rd.cms_lsp_lmt_profile_id "
			+ "    AND rd.recurrent_doc_id = cms_recurrent_doc_item.recurrent_doc_id " + "  ) <> 'DELETED' " + "  AND "
			+ "  (" + "    cms_recurrent_doc_sub_item.sub_item_id IS null "
			+ "  OR cms_recurrent_doc_sub_item.sub_item_id = ( " + "    SELECT max(maxhist.sub_item_id) "
			+ "    FROM cms_recurrent_doc_sub_item maxhist "
			+ "    WHERE maxhist.recurrent_item_id = cms_recurrent_doc_item.recurrent_item_id "
			+ "      AND maxhist.status <> 'PENDING' " + "    ) " + "  OR cms_recurrent_doc_sub_item.sub_item_id = ( "
			+ "    SELECT max(maxpend.sub_item_id) " + "    FROM cms_recurrent_doc_sub_item maxpend "
			+ "    WHERE maxpend.recurrent_item_id = cms_recurrent_doc_item.recurrent_item_id "
			+ "      AND maxpend.status = 'PENDING' " + "    ) " + "  )";

	private final static String QUERY_GET_RECURRENT_ITEM_BY_CNTRY = "SELECT"
			+ "  cms_recurrent_doc_item.recurrent_item_id,"
			+ "  cms_recurrent_doc_item.recurrent_item_ref_id,"
			+ "  cms_recurrent_doc_item.recurrent_doc_id,"
			+ "  cms_recurrent_doc_item.frequency,"
			+ "  cms_recurrent_doc_item.frequency_unit,"
			+ "  cms_recurrent_doc_item.due_date,"
			+ "  cms_recurrent_doc_item.doc_end_date,"
			+ "  cms_recurrent_doc_item.grace_period,"
			+ "  cms_recurrent_doc_item.grace_period_unit,"
			+ "  cms_recurrent_doc_item.chase_remind_ind,"
			+ "  cms_recurrent_doc_item.one_off,"
			+ "  cms_recurrent_doc_item.last_doc_entry_date,"
			+ "  cms_recurrent_doc_item.remarks,"
			+ "  cms_recurrent_doc_sub_item.sub_item_id,"
			+ "  cms_recurrent_doc_sub_item.sub_item_ref_id,"
			+ "  cms_recurrent_doc_sub_item.doc_end_date sub_end_date,"
			+ "  cms_recurrent_doc_sub_item.due_date sub_due_date,"
			+ "  cms_recurrent_doc_sub_item.rec_date,"
			+ "  cms_recurrent_doc_sub_item.deferred_date,"
			+ "  cms_recurrent_doc_sub_item.print_reminder_ind,"
			+ "  cms_recurrent_doc_sub_item.waived_date,"
			+ "  cms_recurrent_doc_sub_item.deferred_cnt,"
			+ "  cms_recurrent_doc_sub_item.remarks sub_remarks,"
			+ "  cms_recurrent_doc_sub_item.status sub_status,"
			// +
			// "  decode (end_date_changed_count , 0 , 'NO', 'YES' ) is_enddate_changed "
			+ "  (CASE  end_date_changed_count WHEN  0  THEN  'NO' ELSE  'YES' END ) is_enddate_changed "
			+ "FROM"
			+ "  cms_recurrent_doc_item LEFT OUTER JOIN cms_recurrent_doc_sub_item "
			+ "    ON cms_recurrent_doc_item.recurrent_item_id = cms_recurrent_doc_sub_item.recurrent_item_id, "
			// +"  cms_recurrent_doc_sub_item, "
			+ "  cms_recurrent_doc, "
			+ "  sci_lsp_lmt_profile "
			// +
			// "WHERE cms_recurrent_doc_item.recurrent_item_id = cms_recurrent_doc_sub_item.recurrent_item_id(+) "
			+ "  WHERE cms_recurrent_doc_item.is_deleted = 'N' "
			+ "  AND cms_recurrent_doc_sub_item.is_deleted_ind = 'N' "
			+ "  AND cms_recurrent_doc.recurrent_doc_id = cms_recurrent_doc_item.recurrent_doc_id "
			+ "  AND cms_recurrent_doc.cms_lsp_lmt_profile_id = sci_lsp_lmt_profile.cms_lsp_lmt_profile_id "
			+ "  AND sci_lsp_lmt_profile.cms_bca_status <> 'DELETED' " + "  AND " + "  ("
			+ "    cms_recurrent_doc_sub_item.sub_item_id IS null "
			+ "  OR cms_recurrent_doc_sub_item.sub_item_id = ( " + "    SELECT max(maxhist.sub_item_id) "
			+ "    FROM cms_recurrent_doc_sub_item maxhist "
			+ "    WHERE maxhist.recurrent_item_id = cms_recurrent_doc_item.recurrent_item_id "
			+ "      AND maxhist.status <> 'PENDING' " + "    ) " + "  OR cms_recurrent_doc_sub_item.sub_item_id = ( "
			+ "    SELECT max(maxpend.sub_item_id) " + "    FROM cms_recurrent_doc_sub_item maxpend "
			+ "    WHERE maxpend.recurrent_item_id = cms_recurrent_doc_item.recurrent_item_id "
			+ "      AND maxpend.status = 'PENDING' " + "    ) " + "  )";

	private final static String QUERY_GET_COVENANT_ITEM = "SELECT"
			+ "  cms_convenant.convenant_id,"
			+ "  cms_convenant.convenant_ref_id,"
			+ "  cms_convenant.recurrent_doc_id,"
			+ "  cms_convenant.frequency,"
			+ "  cms_convenant.frequency_unit,"
			+ "  cms_convenant.due_date,"
			+ "  cms_convenant.doc_end_date,"
			+ "  cms_convenant.grace_period,"
			+ "  cms_convenant.grace_period_unit,"
			+ "  cms_convenant.chase_remind_ind,"
			+ "  cms_convenant.one_off,"
			+ "  cms_convenant.last_doc_entry_date,"
			+ "  cms_convenant.is_verified,"
			+ "  cms_convenant.date_checked,"
			+ "  cms_convenant.remarks,"
			+ "  cms_convenant_sub_item.sub_item_id,"
			+ "  cms_convenant_sub_item.sub_item_ref_id,"
			+ "  cms_convenant_sub_item.doc_end_date sub_end_date,"
			+ "  cms_convenant_sub_item.due_date sub_due_date,"
			+ "  cms_convenant_sub_item.checked_date sub_checked_date,"
			+ "  cms_convenant_sub_item.deferred_date,"
			+ "  cms_convenant_sub_item.print_reminder_ind,"
			+ "  cms_convenant_sub_item.waived_date,"
			+ "  cms_convenant_sub_item.deferred_cnt,"
			+ "  cms_convenant_sub_item.remarks sub_remarks,"
			+ "  cms_convenant_sub_item.is_verified_ind,"
			+ "  cms_convenant_sub_item.status sub_status,"
			// +
			// "  decode (end_date_changed_count , 0 , 'NO', 'YES') is_enddate_changed "
			+ "  (CASE  end_date_changed_count WHEN  0  THEN  'NO' ELSE  'YES' END ) is_enddate_changed "
			+ "FROM"
			+ "  cms_convenant LEFT OUTER JOIN  cms_convenant_sub_item"
			+ "   ON cms_convenant.convenant_id = cms_convenant_sub_item.convenant_id "
			// +
			// "WHERE cms_convenant.convenant_id = cms_convenant_sub_item.convenant_id "
			+ "WHERE cms_convenant.is_deleted = 'N' " + "  AND cms_convenant_sub_item.is_deleted_ind = 'N' " + "  AND "
			+ "  ( SELECT cms_bca_status " + "    FROM sci_lsp_lmt_profile p," + "    	cms_recurrent_doc rd "
			+ "  WHERE p.cms_lsp_lmt_profile_id = rd.cms_lsp_lmt_profile_id "
			+ "    AND rd.recurrent_doc_id = cms_convenant.recurrent_doc_id" + "  ) <> 'DELETED' " + "  AND " + "  ("
			+ "    cms_convenant_sub_item.sub_item_id IS null " + "  OR cms_convenant_sub_item.sub_item_id = ( "
			+ "    SELECT max(maxhist.sub_item_id) " + "    FROM cms_convenant_sub_item maxhist "
			+ "    WHERE maxhist.convenant_id = cms_convenant.convenant_id " + "      AND maxhist.status <> 'PENDING' "
			+ "    ) " + "  OR cms_convenant_sub_item.sub_item_id = ( " + "    SELECT max(maxpend.sub_item_id) "
			+ "    FROM cms_convenant_sub_item maxpend "
			+ "    WHERE maxpend.convenant_id = cms_convenant.convenant_id " + "      AND maxpend.status = 'PENDING' "
			+ "    ) " + "  )";

	private final static String QUERY_GET_COVENANT_ITEM_BY_CNTRY = "SELECT "
			+ " cms_convenant.convenant_id, "
			+ " cms_convenant.convenant_ref_id, "
			+ " cms_convenant.recurrent_doc_id, "
			+ " cms_convenant.frequency, "
			+ " cms_convenant.frequency_unit, "
			+ " cms_convenant.due_date, "
			+ " cms_convenant.doc_end_date, "
			+ " cms_convenant.grace_period, "
			+ " cms_convenant.grace_period_unit, "
			+ " cms_convenant.chase_remind_ind, "
			+ " cms_convenant.one_off, "
			+ " cms_convenant.last_doc_entry_date, "
			+ " cms_convenant.is_verified, "
			+ " cms_convenant.date_checked, "
			+ " cms_convenant.remarks, "
			+ " cms_convenant_sub_item.sub_item_id, "
			+ " cms_convenant_sub_item.sub_item_ref_id, "
			+ " cms_convenant_sub_item.doc_end_date sub_end_date, "
			+ " cms_convenant_sub_item.due_date sub_due_date, "
			+ " cms_convenant_sub_item.checked_date sub_checked_date, "
			+ " cms_convenant_sub_item.deferred_date, "
			+ " cms_convenant_sub_item.print_reminder_ind, "
			+ " cms_convenant_sub_item.waived_date, "
			+ " cms_convenant_sub_item.deferred_cnt, "
			+ " cms_convenant_sub_item.remarks sub_remarks, "
			+ " cms_convenant_sub_item.is_verified_ind, "
			+ " cms_convenant_sub_item.status sub_status, "
			// +
			// " decode (end_date_changed_count , 0 , 'NO', 'YES') is_enddate_changed "
			+ "  (CASE  end_date_changed_count WHEN  0  THEN  'NO' ELSE  'YES' END ) is_enddate_changed "
			+ " FROM "
			+ " cms_convenant LEFT OUTER JOIN cms_convenant_sub_item "
			+ "  ON cms_convenant.convenant_id = cms_convenant_sub_item.convenant_id, "
			+ " cms_recurrent_doc, "
			+ " sci_lsp_lmt_profile "
			// +
			// " WHERE cms_convenant.convenant_id = cms_convenant_sub_item.convenant_id(+) "
			+ " WHERE cms_convenant.is_deleted = 'N' " + " AND cms_convenant_sub_item.is_deleted_ind = 'N' " + " AND "
			+ " ( SELECT cms_bca_status " + " FROM sci_lsp_lmt_profile p, " + "  	cms_recurrent_doc rd "
			+ " WHERE p.cms_lsp_lmt_profile_id = rd.cms_lsp_lmt_profile_id "
			+ "  AND rd.recurrent_doc_id = cms_convenant.recurrent_doc_id " + " ) <> 'DELETED' " + " AND " + " ( "
			+ "  cms_convenant_sub_item.sub_item_id IS null " + " OR cms_convenant_sub_item.sub_item_id = ( "
			+ "  SELECT max(maxhist.sub_item_id) " + "  FROM cms_convenant_sub_item maxhist "
			+ "  WHERE maxhist.convenant_id = cms_convenant.convenant_id " + "    AND maxhist.status <> 'PENDING' "
			+ "  ) " + " OR cms_convenant_sub_item.sub_item_id = ( " + "  SELECT max(maxpend.sub_item_id) "
			+ " FROM cms_convenant_sub_item maxpend " + "  WHERE maxpend.convenant_id = cms_convenant.convenant_id "
			+ "    AND maxpend.status = 'PENDING' " + "  ) " + " ) "
			+ " and cms_recurrent_doc.RECURRENT_DOC_ID = cms_convenant.recurrent_doc_id "
			+ " and cms_recurrent_doc.cms_lsp_lmt_profile_id = sci_lsp_lmt_profile.cms_lsp_lmt_profile_id";

	/**
	 * Default Constructor
	 */
	public RecurrentDueDateDAO() {
	}

	/**
	 * Returns an array of recurrent items with the latest sub item is any
	 * @return IRecurrentCheckListSubItem[] - the list of recurrent checklist
	 *         sub items
	 * @throws SearchDAOException
	 */
	public IRecurrentCheckListItem[] getRecurrentCheckListItemList(String countryCode) {

		StringBuffer sqlBuffer = null;
		if ((countryCode != null) && !countryCode.trim().equals("")) {
			sqlBuffer = new StringBuffer(QUERY_GET_RECURRENT_ITEM_BY_CNTRY);
			sqlBuffer.append(" AND SCI_LSP_LMT_PROFILE.CMS_ORIG_COUNTRY = ? ");
		}
		else {
			sqlBuffer = new StringBuffer(QUERY_GET_RECURRENT_ITEM);
		}

		return (IRecurrentCheckListItem[]) getJdbcTemplate().query(sqlBuffer.toString(), new Object[] { countryCode },
				new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						Map recIDOBMap = new HashMap();
						Map recIDChangesMap = new HashMap();

						while (rs.next()) {

							Long recItemID = new Long(rs.getLong(ITMTBL_ITEM_ID));
							String isEndDateChanged = rs.getString("IS_ENDDATE_CHANGED");
							recIDChangesMap.put(recItemID, isEndDateChanged);

							if (!recIDOBMap.containsKey(recItemID)) {
								IRecurrentCheckListItem itm = new OBRecurrentCheckListItem();
								itm.setCheckListItemID(rs.getLong(ITMTBL_ITEM_ID));
								itm.setCheckListItemRef(rs.getLong(ITMTBL_ITEM_REF_ID));
								itm.setFrequency(rs.getInt(ITMTBL_FREQUENCY));
								itm.setFrequencyUnit(rs.getString(ITMTBL_FREQUENCY_UNIT));
								itm.setGracePeriod(rs.getInt(ITMTBL_GRACE_PERIOD));
								itm.setGracePeriodUnit(rs.getString(ITMTBL_GRACE_PERIOD_UNIT));
								itm.setInitialDocEndDate(rs.getDate(ITMTBL_DOC_END_DATE));
								itm.setInitialDueDate(rs.getDate(ITMTBL_DUE_DATE));
								itm.setRemarks(rs.getString(ITMTBL_REMARKS));
								if (ICMSConstant.TRUE_VALUE.equals(rs.getString(ITMTBL_ONE_OFF))) {
									itm.setIsOneOffInd(true);
								}
								else {
									itm.setIsOneOffInd(false);
								}
								itm.setLastDocEntryDate(rs.getDate(ITMTBL_LAST_DOC_ENTRY_DATE));
								if (rs.getLong(SUBITM_SUB_ITEM_ID) != 0) {
									IRecurrentCheckListSubItem[] subItem = new OBRecurrentCheckListSubItem[1];
									subItem[0] = createRecurrentCheckListSubItem(rs);
									itm.setRecurrentCheckListSubItemList(subItem);
								}

								recIDOBMap.put(recItemID, itm);
							}
							else {
								IRecurrentCheckListItem aRecurrentItem = (IRecurrentCheckListItem) recIDOBMap
										.get(recItemID);

								IRecurrentCheckListSubItem[] currentSubItems = aRecurrentItem
										.getRecurrentCheckListSubItemList();

								ArrayList aSubItemList = new ArrayList();
								if (currentSubItems != null) {
									aSubItemList.add(currentSubItems[0]);
								}

								if (rs.getLong(SUBITM_SUB_ITEM_ID) != 0) {
									IRecurrentCheckListSubItem subItem = createRecurrentCheckListSubItem(rs);
									aSubItemList.add(subItem);
									aRecurrentItem
											.setRecurrentCheckListSubItemList((IRecurrentCheckListSubItem[]) aSubItemList
													.toArray(new IRecurrentCheckListSubItem[0]));
								}
							}
						}

						if (recIDOBMap.size() == 0) {
							return null;
						}

						return getActualRecurrentList(recIDOBMap, recIDChangesMap);
					}
				});
	}

	private IRecurrentCheckListSubItem createRecurrentCheckListSubItem(ResultSet rs) throws SQLException {
		IRecurrentCheckListSubItem subItem = new OBRecurrentCheckListSubItem();

		subItem = new OBRecurrentCheckListSubItem();
		subItem.setSubItemID(rs.getLong(SUBITM_SUB_ITEM_ID));
		subItem.setSubItemRef(rs.getLong(SUBITM_SUB_ITEM_REF_ID));
		subItem.setDocEndDate(rs.getDate("SUB_END_DATE"));
		subItem.setDueDate(rs.getDate("SUB_DUE_DATE"));
		subItem.setStatus(rs.getString("SUB_STATUS"));
		subItem.setReceivedDate(rs.getDate(SUBITM_REC_DATE));
		subItem.setDeferredDate(rs.getDate(SUBITM_DEFERRED_DATE));
		subItem.setWaivedDate(rs.getDate(SUBITM_WAIVED_DATE));
		subItem.setDeferredCount(rs.getLong(SUBITM_DEFERRED_CNT));
		subItem.setRemarks(rs.getString(SUBITM_REMARKS));

		return subItem;
	}

	private IRecurrentCheckListItem[] getActualRecurrentList(Map IDOBMap, Map IDChangesMap) {
		ArrayList aRecItemList = new ArrayList();

		Iterator aIterator = IDOBMap.keySet().iterator();
		while (aIterator.hasNext()) {
			Long recItemID = (Long) aIterator.next();
			IRecurrentCheckListItem aRecurrentItem = (IRecurrentCheckListItem) IDOBMap.get(recItemID);

			IRecurrentCheckListSubItem[] subItems = aRecurrentItem.getRecurrentCheckListSubItemList();
			if ((subItems == null) || (subItems.length < 2)) {
				aRecItemList.add(aRecurrentItem);
				continue;
			}

			IRecurrentCheckListSubItem aPendingSubItem = null;
			IRecurrentCheckListSubItem aHistorySubItem = null;

			for (int i = 0; i < subItems.length; i++) {
				if (ICMSConstant.RECURRENT_ITEM_STATE_PENDING.equals(subItems[i].getStatus())) {
					aPendingSubItem = subItems[i];
				}
				else {
					aHistorySubItem = subItems[i];
				}
			}

			String isEndDateChanged = (String) IDChangesMap.get(recItemID);
			IRecurrentCheckListSubItem[] actualSubItem = new OBRecurrentCheckListSubItem[1];
			if (aPendingSubItem.getSubItemID() > aHistorySubItem.getSubItemID()) {
				actualSubItem[0] = aPendingSubItem;
			}
			else {
				if ("YES".equals(isEndDateChanged)) {
					actualSubItem[0] = aPendingSubItem;
				}
				else {
					actualSubItem[0] = aHistorySubItem;
				}
			}
			aRecurrentItem.setRecurrentCheckListSubItemList(actualSubItem);

			aRecItemList.add(aRecurrentItem);
		}

		return (aRecItemList.isEmpty()) ? null : (IRecurrentCheckListItem[]) aRecItemList
				.toArray(new IRecurrentCheckListItem[0]);
	}

	public IConvenant[] getCovenantList(String countryCode) {

		StringBuffer sqlBuffer = null;
		if ((countryCode != null) && !countryCode.trim().equals("")) {
			sqlBuffer = new StringBuffer(QUERY_GET_COVENANT_ITEM_BY_CNTRY);
			sqlBuffer.append(" AND SCI_LSP_LMT_PROFILE.CMS_ORIG_COUNTRY = ? ");
		}
		else {
			sqlBuffer = new StringBuffer(QUERY_GET_COVENANT_ITEM);
		}

		return (IConvenant[]) getJdbcTemplate().query(sqlBuffer.toString(), new Object[] { countryCode },
				new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						Map covIDOBMap = new HashMap();
						Map covIDChangesMap = new HashMap();

						while (rs.next()) {

							Long covID = new Long(rs.getLong(CVNTBL_ITEM_ID));
							String isEndDateChanged = rs.getString("IS_ENDDATE_CHANGED");
							covIDChangesMap.put(covID, isEndDateChanged);

							if (!covIDOBMap.containsKey(covID)) {
								IConvenant itm = new OBConvenant();
								itm.setConvenantID(covID.longValue());
								itm.setConvenantRef(rs.getLong(CVNTBL_ITEM_REF_ID));
								itm.setFrequency(rs.getInt(CVNTBL_FREQUENCY));
								itm.setFrequencyUnit(rs.getString(CVNTBL_FREQUENCY_UNIT));
								itm.setGracePeriod(rs.getInt(CVNTBL_GRACE_PERIOD));
								itm.setGracePeriodUnit(rs.getString(CVNTBL_GRACE_PERIOD_UNIT));
								itm.setInitialDocEndDate(rs.getDate(CVNTBL_DOC_END_DATE));
								itm.setInitialDueDate(rs.getDate(CVNTBL_DUE_DATE));
								itm.setRemarks(rs.getString(CVNTBL_REMARKS));
								if (ICMSConstant.TRUE_VALUE.equals(rs.getString(CVNTBL_ONE_OFF))) {
									itm.setIsOneOffInd(true);
								}
								else {
									itm.setIsOneOffInd(false);
								}
								itm.setLastDocEntryDate(rs.getDate(CVNTBL_LAST_DOC_ENTRY_DATE));
								if (ICMSConstant.TRUE_VALUE.equals(rs.getString(CVNTBL_IS_VERIFIED))) {
									itm.setIsVerifiedInd(true);
								}
								else {
									itm.setIsVerifiedInd(false);
								}
								itm.setDateChecked(rs.getDate(CVNTBL_CHECKED_DATE));

								if (rs.getLong(CVNSUBITM_SUB_ITEM_ID) != 0) {
									IConvenantSubItem[] subItem = new OBConvenantSubItem[1];
									subItem[0] = createConvenantSubItem(rs);
									itm.setConvenantSubItemList(subItem);
								}

								covIDOBMap.put(covID, itm);
							}
							else {
								IConvenant aCovenant = (IConvenant) covIDOBMap.get(covID);

								IConvenantSubItem[] currentSubItems = aCovenant.getConvenantSubItemList();
								ArrayList aSubItemList = new ArrayList();
								if (currentSubItems != null) {
									aSubItemList.add(currentSubItems[0]);
								}

								if (rs.getLong(CVNSUBITM_SUB_ITEM_ID) != 0) {
									IConvenantSubItem subItem = createConvenantSubItem(rs);
									aSubItemList.add(subItem);
									aCovenant.setConvenantSubItemList((IConvenantSubItem[]) aSubItemList
											.toArray(new IConvenantSubItem[0]));
								}
							}
						}

						if (covIDOBMap.size() == 0) {
							return null;
						}

						return getActualCovenantList(covIDOBMap, covIDChangesMap);
					}
				});

	}

	private IConvenantSubItem createConvenantSubItem(ResultSet rs) throws SQLException {
		IConvenantSubItem subItem = new OBConvenantSubItem();

		subItem.setSubItemID(rs.getLong(CVNSUBITM_SUB_ITEM_ID));
		subItem.setSubItemRef(rs.getLong(CVNSUBITM_SUB_ITEM_REF_ID));
		subItem.setDocEndDate(rs.getDate("SUB_END_DATE"));
		subItem.setDueDate(rs.getDate("SUB_DUE_DATE"));
		subItem.setCheckedDate(rs.getDate("SUB_CHECKED_DATE"));
		subItem.setStatus(rs.getString("SUB_STATUS"));
		subItem.setDeferredDate(rs.getDate(CVNSUBITM_DEFERRED_DATE));
		subItem.setWaivedDate(rs.getDate(CVNSUBITM_WAIVED_DATE));
		subItem.setDeferredCount(rs.getLong(CVNSUBITM_DEFERRED_CNT));
		subItem.setRemarks(rs.getString(CVNSUBITM_REMARKS));

		return subItem;
	}

	private IConvenant[] getActualCovenantList(Map IDOBMap, Map IDChangesMap) {
		ArrayList aCovItemList = new ArrayList();

		Iterator aIterator = IDOBMap.keySet().iterator();
		while (aIterator.hasNext()) {
			Long covID = (Long) aIterator.next();
			IConvenant aCovenant = (IConvenant) IDOBMap.get(covID);

			IConvenantSubItem[] subItems = aCovenant.getConvenantSubItemList();
			if ((subItems == null) || (subItems.length < 2)) {
				aCovItemList.add(aCovenant);
				continue;
			}

			IConvenantSubItem aPendingSubItem = null;
			IConvenantSubItem aHistorySubItem = null;

			for (int i = 0; i < subItems.length; i++) {
				if (ICMSConstant.RECURRENT_ITEM_STATE_PENDING.equals(subItems[i].getStatus())) {
					aPendingSubItem = subItems[i];
				}
				else {
					aHistorySubItem = subItems[i];
				}
			}

			String isEndDateChanged = (String) IDChangesMap.get(covID);
			IConvenantSubItem[] actualSubItem = new OBConvenantSubItem[1];
			if (aPendingSubItem.getSubItemID() > aHistorySubItem.getSubItemID()) {
				actualSubItem[0] = aPendingSubItem;
			}
			else {
				if ("YES".equals(isEndDateChanged)) {
					actualSubItem[0] = aPendingSubItem;
				}
				else {
					actualSubItem[0] = aHistorySubItem;
				}
			}
			aCovenant.setConvenantSubItemList(actualSubItem);

			aCovItemList.add(aCovenant);
		}

		return (aCovItemList.isEmpty()) ? null : (IConvenant[]) aCovItemList.toArray(new IConvenant[0]);
	}
}