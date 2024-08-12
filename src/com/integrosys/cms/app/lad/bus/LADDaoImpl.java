package com.integrosys.cms.app.lad.bus;

import java.io.Serializable;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.state.bus.OBState;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.otherbank.bus.IOtherBankDAO;
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.OBRecurrentCheckListItem;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.systemBank.bus.SystemBankJdbcImpl.SystemBankRowMapper;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranchDao;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchException;
import com.integrosys.cms.host.eai.limit.bus.CMSTransaction;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
/**
 * @author $Author: Abhijit R $<br>
 * 
 *         Dao Implication declares the methods used by Bus manager Implication
 */
public class LADDaoImpl extends HibernateDaoSupport implements
		ILADDao {
	final static String LAD_SEQ = "lad_seq";
	final static String LAD_ITEM_SEQ = "lad_item_seq";
	final static String LAD_SUB_ITEM_SEQ = "lad_sub_item_seq";

	private DBUtil dbUtil = null;

	public List getLAD(long limit_profile_id, String stage) throws Exception {

		String sql = "Select id,lad_id,lad_name,lad_due_date ,generation_date,IS_OPERATION_ALLOWED,receive_date,document_date,expiry_date,lad_counter,status,version_time,limit_profile_id from cms_lad where status='"
				+ stage
				+ "' and limit_profile_id='"
				+ limit_profile_id
				+ "' order by generation_date desc";

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
			ArrayList resultList = new ArrayList();
			ResultSet rs = dbUtil.executeQuery();
			ILAD ilad = null;
			while (rs.next()) {

				ilad = new OBLAD();
				ilad.setDocument_date(rs.getDate("document_date"));
				ilad.setExpiry_date(rs.getDate("expiry_date"));
				ilad.setGeneration_date(rs.getDate("generation_date"));
				ilad.setId(rs.getLong("id"));
				ilad.setLad_counter(rs.getLong("lad_counter"));
				ilad.setLad_due_date(rs.getDate("lad_due_date"));
				ilad.setLad_id(rs.getLong("lad_id"));
				ilad.setLad_name(rs.getString("lad_name"));
				ilad.setReceive_date(rs.getDate("receive_date"));
				ilad.setStatus(rs.getString("status"));
				ilad.setVersionTime(rs.getLong("version_time"));
				ilad.setIsOperationAllowed(rs.getString("IS_OPERATION_ALLOWED"));
				ilad.setLimit_profile_id(rs.getLong("limit_profile_id"));
				resultList.add(ilad);
			}

			rs.close();
			return resultList;
		} catch (SQLException ex) {
			throw new SearchDAOException(
					"SQLException in getCheckListItemListForAudit", ex);
		} catch (Exception ex) {
			throw new SearchDAOException(
					"Exception in getCheckListItemListForAudit", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in getCheckListItemListForAudit", ex);
			}
		}

	}

	public List getLADNormal(long limit_profile_id) throws Exception {

		String sql = "Select id,lad_id,lad_name,lad_due_date ,generation_date,IS_OPERATION_ALLOWED,receive_date,document_date,expiry_date,lad_counter,status,version_time,limit_profile_id from cms_lad where  limit_profile_id='"
				+ limit_profile_id + "' order by generation_date desc";

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
			ArrayList resultList = new ArrayList();
			ResultSet rs = dbUtil.executeQuery();
			ILAD ilad = null;
			while (rs.next()) {

				ilad = new OBLAD();
				ilad.setDocument_date(rs.getDate("document_date"));
				ilad.setExpiry_date(rs.getDate("expiry_date"));
				ilad.setGeneration_date(rs.getDate("generation_date"));
				ilad.setId(rs.getLong("id"));
				ilad.setLad_counter(rs.getLong("lad_counter"));
				ilad.setLad_due_date(rs.getDate("lad_due_date"));
				ilad.setLad_id(rs.getLong("lad_id"));
				ilad.setLad_name(rs.getString("lad_name"));
				ilad.setReceive_date(rs.getDate("receive_date"));
				ilad.setStatus(rs.getString("status"));
				ilad.setIsOperationAllowed(rs.getString("IS_OPERATION_ALLOWED"));
				ilad.setVersionTime(rs.getLong("version_time"));
				ilad.setLimit_profile_id(rs.getLong("limit_profile_id"));
				resultList.add(ilad);
			}

			rs.close();
			return resultList;
		} catch (SQLException ex) {
			throw new SearchDAOException(
					"SQLException in getCheckListItemListForAudit", ex);
		} catch (Exception ex) {
			throw new SearchDAOException(
					"Exception in getCheckListItemListForAudit", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in getCheckListItemListForAudit", ex);
			}
		}

	}

	public List getLADItem(long lad_id) throws Exception {

		String sql = "Select id, doc_item_id,  lad_id ,  doc_description,  status,category,  doc_date,  expiry_date,version_time from cms_lad_item where lad_id="
				+ lad_id;

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);

			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			ILADItem iladItem = null;
			while (rs.next()) {

				iladItem = new OBLADItem();
				iladItem.setExpiry_date(rs.getDate("expiry_date"));
				iladItem.setId(rs.getLong("id"));
				iladItem.setLad_id(rs.getLong("lad_id"));
				iladItem.setDoc_date(rs.getDate("doc_date"));
				iladItem.setDoc_description(rs.getString("doc_description"));
				iladItem.setDoc_item_id(rs.getLong("doc_item_id"));
				iladItem.setStatus(rs.getString("status"));
				iladItem.setCategory(rs.getString("category"));
				iladItem.setVersionTime(rs.getLong("version_time"));
				resultList.add(iladItem);
			}

			rs.close();
			return resultList;
		} catch (SQLException ex) {
			throw new SearchDAOException(
					"SQLException in getCheckListItemListForAudit", ex);
		} catch (Exception ex) {
			throw new SearchDAOException(
					"Exception in getCheckListItemListForAudit", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in getCheckListItemListForAudit", ex);
			}
		}

	}

	public List getLADSubItem(long lad_item_id) throws Exception {

		String sql = "Select id, doc_item_id,  doc_sub_item_id ,  doc_description,  status,category,  doc_date,  expiry_date,version_time from cms_lad_sub_item where doc_item_id="
				+ lad_item_id + " ORDER BY EXPIRY_DATE";

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);

			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			ILADSubItem iladItem = null;
			while (rs.next()) {

				iladItem = new OBLADSubItem();
				iladItem.setExpiry_date(rs.getDate("expiry_date"));
				iladItem.setId(rs.getLong("id"));
				iladItem.setDoc_sub_item_id(rs.getLong("doc_sub_item_id"));
				iladItem.setDoc_date(rs.getDate("doc_date"));
				iladItem.setDoc_description(rs.getString("doc_description"));
				iladItem.setDoc_item_id(rs.getLong("doc_item_id"));
				iladItem.setStatus(rs.getString("status"));
				iladItem.setCategory(rs.getString("category"));
				iladItem.setVersionTime(rs.getLong("version_time"));
				resultList.add(iladItem);
			}

			rs.close();
			return resultList;
		} catch (SQLException ex) {
			throw new SearchDAOException(
					"SQLException in getCheckListItemListForAudit", ex);
		} catch (Exception ex) {
			throw new SearchDAOException(
					"Exception in getCheckListItemListForAudit", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in getCheckListItemListForAudit", ex);
			}
		}

	}

	public List getLADSubItemSorted(long lad_id) throws Exception {

		String sql = "Select id, doc_item_id,  doc_sub_item_id ,  doc_description,  status,category,  doc_date,  expiry_date,version_time from cms_lad_sub_item where doc_item_id in (select doc_item_id from cms_lad_item where lad_id="
				+ lad_id + " ) ORDER BY EXPIRY_DATE";

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);

			ResultSet rs = dbUtil.executeQuery();
			ArrayList resultList = new ArrayList();
			ILADSubItem iladItem = null;
			while (rs.next()) {

				iladItem = new OBLADSubItem();
				iladItem.setExpiry_date(rs.getDate("expiry_date"));
				iladItem.setId(rs.getLong("id"));
				iladItem.setDoc_sub_item_id(rs.getLong("doc_sub_item_id"));
				iladItem.setDoc_date(rs.getDate("doc_date"));
				iladItem.setDoc_description(rs.getString("doc_description"));
				iladItem.setDoc_item_id(rs.getLong("doc_item_id"));
				iladItem.setStatus(rs.getString("status"));
				iladItem.setCategory(rs.getString("category"));
				iladItem.setVersionTime(rs.getLong("version_time"));
				resultList.add(iladItem);
			}

			rs.close();
			return resultList;
		} catch (SQLException ex) {
			throw new SearchDAOException(
					"SQLException in getCheckListItemListForAudit", ex);
		} catch (Exception ex) {
			throw new SearchDAOException(
					"Exception in getCheckListItemListForAudit", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in getCheckListItemListForAudit", ex);
			}
		}

	}

	public ILAD createLAD(ILAD ilad) throws Exception {
		if (ilad != null) {
			try {
				long pk = Long.parseLong((new SequenceManager()).getSeqNum(
						LAD_SEQ, true));
				ilad.setLad_id(pk);
				Long key = (Long) getHibernateTemplate().save("actualOBLAD",
						ilad);
				ilad.setId(key.longValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ilad;
		} else {
			throw new Exception("ERROR- Entity name or key is null ");
		}
	}

	public ILADItem createLADItem(ILADItem ilad) throws Exception {
		if (ilad != null) {
			try {
				long pk = Long.parseLong((new SequenceManager()).getSeqNum(
						LAD_ITEM_SEQ, true));

				if (ilad.getDoc_item_id() == 0) {
					ilad.setDoc_item_id(pk);
				}
				Long key = (Long) getHibernateTemplate().save(
						"actualOBLADItem", ilad);
				ilad.setId(key.longValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ilad;
		} else {
			throw new Exception("ERROR- Entity name or key is null ");
		}
	}

	public ILADSubItem createLADSubItem(ILADSubItem ilad) throws Exception {
		if (ilad != null) {
			try {
				DefaultLogger.debug(this,
						">>>>>>>>>>>>>>>>>>>> Creating LAD SUB ITEM");
				long pk = Long.parseLong((new SequenceManager()).getSeqNum(
						LAD_SUB_ITEM_SEQ, true));
				if (ilad.getDoc_sub_item_id() == 0) {
					ilad.setDoc_sub_item_id(pk);
				}
				Long key = (Long) getHibernateTemplate().save(
						"actualOBLADSubItem", ilad);
				ilad.setId(key.longValue());
				DefaultLogger.debug(
						this,
						">>>>>>>>>>>>>>>>>>>> CREATED LAD WITH ID "
								+ ilad.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ilad;
		} else {
			throw new Exception("ERROR- Entity name or key is null ");
		}
	}

	public ILAD updateLAD(ILAD ilad) throws Exception {
		if (ilad != null) {
			try {
				getHibernateTemplate().update("actualOBLAD", ilad);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ilad;
		} else {
			throw new Exception("ERROR- Entity name or key is null ");
		}
	}

	public boolean isUniqueCode(String coloumn, String value) throws Exception {

		String query = "SELECT FROM actualOBLAD  WHERE Upper(" + coloumn
				+ ") = '" + value.toUpperCase() + "'";
		ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
		if (resultList.size() > 0)
			return true;
		else
			return false;

	}

	public List getAllLADNotGenerated() throws Exception {

		String sql = "Select id,lad_id,lad_name,lad_due_date ,IS_OPERATION_ALLOWED,generation_date,receive_date,document_date,expiry_date,lad_counter,status,version_time,limit_profile_id from cms_lad where IS_OPERATION_ALLOWED='N' ";

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
			ArrayList resultList = new ArrayList();
			ResultSet rs = dbUtil.executeQuery();
			ILAD ilad = null;
			while (rs.next()) {

				ilad = new OBLAD();
				ilad.setDocument_date(rs.getDate("document_date"));
				ilad.setExpiry_date(rs.getDate("expiry_date"));
				ilad.setGeneration_date(rs.getDate("generation_date"));
				ilad.setId(rs.getLong("id"));
				ilad.setLad_counter(rs.getLong("lad_counter"));
				ilad.setLad_due_date(rs.getDate("lad_due_date"));
				ilad.setLad_id(rs.getLong("lad_id"));
				ilad.setLad_name(rs.getString("lad_name"));
				ilad.setReceive_date(rs.getDate("receive_date"));
				ilad.setStatus(rs.getString("status"));
				ilad.setIsOperationAllowed(rs.getString("IS_OPERATION_ALLOWED"));
				ilad.setVersionTime(rs.getLong("version_time"));
				ilad.setLimit_profile_id(rs.getLong("limit_profile_id"));
				resultList.add(ilad);
			}

			rs.close();
			return resultList;
		} catch (SQLException ex) {
			throw new SearchDAOException(
					"SQLException in getCheckListItemListForAudit", ex);
		} catch (Exception ex) {
			throw new SearchDAOException(
					"Exception in getCheckListItemListForAudit", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in getCheckListItemListForAudit", ex);
			}
		}

	}

	public List getAllLAD() throws Exception {

		String sql = "Select id,lad_id,lad_name,lad_due_date ,IS_OPERATION_ALLOWED,generation_date,receive_date,document_date,expiry_date,lad_counter,status,version_time,limit_profile_id from cms_lad ";

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
			ArrayList resultList = new ArrayList();
			ResultSet rs = dbUtil.executeQuery();
			ILAD ilad = null;
			while (rs.next()) {

				ilad = new OBLAD();
				ilad.setDocument_date(rs.getDate("document_date"));
				ilad.setExpiry_date(rs.getDate("expiry_date"));
				ilad.setGeneration_date(rs.getDate("generation_date"));
				ilad.setId(rs.getLong("id"));
				ilad.setLad_counter(rs.getLong("lad_counter"));
				ilad.setLad_due_date(rs.getDate("lad_due_date"));
				ilad.setLad_id(rs.getLong("lad_id"));
				ilad.setLad_name(rs.getString("lad_name"));
				ilad.setReceive_date(rs.getDate("receive_date"));
				ilad.setStatus(rs.getString("status"));
				ilad.setIsOperationAllowed(rs.getString("IS_OPERATION_ALLOWED"));
				ilad.setVersionTime(rs.getLong("version_time"));
				ilad.setLimit_profile_id(rs.getLong("limit_profile_id"));
				resultList.add(ilad);
			}

			rs.close();
			return resultList;
		} catch (SQLException ex) {
			throw new SearchDAOException(
					"SQLException in getCheckListItemListForAudit", ex);
		} catch (Exception ex) {
			throw new SearchDAOException(
					"Exception in getCheckListItemListForAudit", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in getCheckListItemListForAudit", ex);
			}
		}

	}

	public void deleteLADItem(long lad_id) throws Exception {

		String sql = "delete    from cms_lad_item where lad_id=" + lad_id;

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);

			dbUtil.execute();

			// return resultList;
		} catch (SQLException ex) {
			throw new SearchDAOException(
					"SQLException in getCheckListItemListForAudit", ex);
		} catch (Exception ex) {
			throw new SearchDAOException(
					"Exception in getCheckListItemListForAudit", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in getCheckListItemListForAudit", ex);
			}
		}

	}

	public void deleteLADSubItem(long lad_item_id) throws Exception {

		String sql = "delete  from cms_lad_sub_item where doc_item_id="
				+ lad_item_id;

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);

			dbUtil.execute();

			// return resultList;
		} catch (SQLException ex) {
			throw new SearchDAOException(
					"SQLException in getCheckListItemListForAudit", ex);
		} catch (Exception ex) {
			throw new SearchDAOException(
					"Exception in getCheckListItemListForAudit", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in getCheckListItemListForAudit", ex);
			}
		}

	}

	public void updateLADOperation(String operation, long limitProfileId)
			throws Exception {

		String sql = "update  cms_lad set IS_OPERATION_ALLOWED='" + operation
				+ "'  where limit_profile_id=" + limitProfileId;

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);

			dbUtil.execute();

			// return resultList;
		} catch (SQLException ex) {
			throw new SearchDAOException(
					"SQLException in getCheckListItemListForAudit", ex);
		} catch (Exception ex) {
			throw new SearchDAOException(
					"Exception in getCheckListItemListForAudit", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in getCheckListItemListForAudit", ex);
			}
		}

	}

	/*
	 * public ICheckListItem getCheckListItemLad(long limit_profile_id){
	 * 
	 * //String sql =
	 * "Select id,lad_id,lad_name,lad_due_date ,generation_date,IS_OPERATION_ALLOWED,receive_date,document_date,expiry_date,lad_counter,status,version_time,limit_profile_id from cms_lad where  limit_profile_id='"
	 * +limit_profile_id+"' order by generation_date desc"; String sql=
	 * "SELECT DOC_ITEM_ID, DOC_DESCRIPTION,STATUS,CHECKLIST_ID,DOC_ITEM_REF,IDENTIFY_DATE,DOC_COMPLETION_DATE,COMPLETED_DATE FROM cms_checklist_item WHERE status= 'AWAITING'  AND checklist_id= (SELECT checklist_id  FROM cms_checklist  WHERE category='LAD'  AND cms_lsp_lmt_profile_id='"
	 * +limit_profile_id+"')";
	 * 
	 * try { dbUtil = new DBUtil(); dbUtil.setSQL(sql); //
	 * DefaultLogger.debug(this, sql); //ArrayList resultList = new ArrayList();
	 * ResultSet rs = dbUtil.executeQuery(); ICheckListItem iladItem = null;
	 * while (rs.next()) {
	 * 
	 * iladItem = new OBCheckListItem();
	 * iladItem.setIdentifyDate(rs.getDate("IDENTIFY_DATE"));
	 * iladItem.setCompletedDate(rs.getDate("COMPLETED_DATE"));
	 * iladItem.setDocCompletionDate(rs.getDate("DOC_COMPLETION_DATE"));
	 * iladItem.setCheckListItemID(rs.getLong("DOC_ITEM_ID"));
	 * iladItem.setItemDesc(rs.getString("DOC_DESCRIPTION"));
	 * iladItem.setCheckListItemID(rs.getLong("CHECKLIST_ID"));
	 * iladItem.setCheckListItemRef(rs.getLong("DOC_ITEM_REF"));
	 * iladItem.setItemStatus(rs.getString("STATUS"));
	 * 
	 * //resultList.add(ilad); }
	 * 
	 * rs.close(); return iladItem; } catch (SQLException ex) { throw new
	 * SearchDAOException("SQLException in getCheckListItemListForAudit", ex); }
	 * catch (Exception ex) { throw new
	 * SearchDAOException("Exception in getCheckListItemListForAudit", ex); }
	 * finally { try { dbUtil.close(); } catch (SQLException ex) { throw new
	 * SearchDAOException("SQLException in getCheckListItemListForAudit", ex); }
	 * }
	 * 
	 * 
	 * }
	 */

	public List getLADSubItemById(long ladId) throws Exception {

		String sql = "select doc_description,category,doc_version,doc_amount,doc_date,expiry_date,type,chk_doc_id,CHK_DOC_ITEM_ID_LAD from cms_lad_sub_item where chk_doc_item_id_lad="
				+ ladId + " and document_status='ACTIVE'";

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
			ArrayList resultList = new ArrayList();
			ResultSet rs = dbUtil.executeQuery();
			ILADSubItem ilad = null;
			while (rs.next()) {

				ilad = new OBLADSubItem();
				ilad.setDoc_description(rs.getString("doc_description"));
				ilad.setCategory(rs.getString("category"));
				ilad.setDocChklistVersion(rs.getString("doc_version"));
				ilad.setDocChklistAmt(rs.getString("doc_amount"));
				ilad.setExpiry_date(rs.getDate("expiry_date"));
				ilad.setDoc_date(rs.getDate("doc_date"));
				ilad.setType(rs.getString("type"));
				ilad.setChklistDocId(rs.getLong("chk_doc_id"));
				ilad.setChklistDocItemId(rs.getLong("CHK_DOC_ITEM_ID_LAD"));
				resultList.add(ilad);
			}

			rs.close();
			return resultList;
		} catch (SQLException ex) {
			throw new SearchDAOException(
					"SQLException in getCheckListItemListForAudit", ex);
		} catch (Exception ex) {
			throw new SearchDAOException(
					"Exception in getCheckListItemListForAudit", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in getCheckListItemListForAudit", ex);
			}
		}

	}

	public long getReceivedLAD(long ladID) throws Exception {

		String sql = "SELECT DOC_ITEM_ID FROM cms_checklist_item  WHERE checklist_id=(SELECT checklist_id  FROM cms_checklist WHERE cms_lsp_lmt_profile_id="
				+ ladID
				+ " AND category ='LAD') AND status IN('AWAITING','DEFERRED')";
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
			ArrayList resultList = new ArrayList();
			ResultSet rs = dbUtil.executeQuery();
			long ilad = 0;
			while (rs.next()) {
				ilad = rs.getLong("DOC_ITEM_ID");

			}

			rs.close();
			return ilad;
		} catch (SQLException ex) {
			throw new SearchDAOException(
					"SQLException in getCheckListItemListForAudit", ex);
		} catch (Exception ex) {
			throw new SearchDAOException(
					"Exception in getCheckListItemListForAudit", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in getCheckListItemListForAudit", ex);
			}
		}
	}

	public String getFacilityInfo(long id) {
		String sql = "select facility_type from sci_lsp_appr_lmts where cms_lsp_appr_lmts_id=(select cms_collateral_id from cms_checklist where checklist_id="
				+ id + ")";
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
			HashMap resultMap = new HashMap();
			ResultSet rs = dbUtil.executeQuery();

			ILimit facility = new OBLimit();
			while (rs.next()) {

				facility.setFacilityType(rs.getString("facility_type"));
			}

			rs.close();
			return facility.getFacilityType();
		} catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getFacilityInfo", ex);
		} catch (Exception ex) {
			throw new SearchDAOException("Exception in getFacilityInfo", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in getCheckListItemListForAudit", ex);
			}
		}
	}

	public String getSecurityInfo(long id) {
		String sql = "SELECT type_name FROM cms_security WHERE cms_collateral_id=(select cms_collateral_id from cms_checklist where checklist_id="
				+ id + ")";
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
			HashMap resultMap = new HashMap();
			ResultSet rs = dbUtil.executeQuery();

			ILimit facility = new OBLimit();
			while (rs.next()) {

				facility.setFacilityType(rs.getString("type_name"));
			}

			rs.close();
			return facility.getFacilityType();
		} catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getSecurityInfo", ex);
		} catch (Exception ex) {
			throw new SearchDAOException("Exception in getSecurityInfo", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in getCheckListItemListForAudit", ex);
			}
		}
	}

	public List checkPendingLAD(long id) {
		String sql = "select doc_item_id,DOC_DESCRIPTION,STATUS from cms_checklist_item where checklist_id=(select CHECKLIST_ID from cms_checklist where cms_lsp_lmt_profile_id="
				+ id + " and category='LAD')";
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
			ArrayList resultList = new ArrayList();
			ResultSet rs = dbUtil.executeQuery();

			ICheckListItem item = null;
			while (rs.next()) {

				item = new OBCheckListItem();
				item.setCheckListItemID(rs.getLong("doc_item_id"));
				item.setItemDesc(rs.getString("DOC_DESCRIPTION"));
				item.setItemStatus(rs.getString("STATUS"));
				resultList.add(item);
			}

			rs.close();
			return resultList;
		} catch (SQLException ex) {
			throw new SearchDAOException(
					"SQLException in getCheckListItemListForAudit", ex);
		} catch (Exception ex) {
			throw new SearchDAOException(
					"Exception in getCheckListItemListForAudit", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in getCheckListItemListForAudit", ex);
			}
		}
	}

	public List getTraxID(long id) {
		String sql = "select transaction_id from transaction where limit_profile_id="
				+ id
				+ " and transaction_subtype='LAD_CHECKLIST_REC' and transaction_type='CHECKLIST'";
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
			ArrayList resultList = new ArrayList();
			ResultSet rs = dbUtil.executeQuery();

			while (rs.next()) {
				CMSTransaction trxObj = new CMSTransaction();
				trxObj.setTransactionID(rs.getLong("transaction_id"));
				resultList.add(trxObj);
			}

			rs.close();
			return resultList;
		} catch (SQLException ex) {
			throw new SearchDAOException(
					"SQLException in getCheckListItemListForAudit", ex);
		} catch (Exception ex) {
			throw new SearchDAOException(
					"Exception in getCheckListItemListForAudit", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in getCheckListItemListForAudit", ex);
			}
		}
	}

	public List checkPendingLADAtferCreate(long id) {
		String sql = "select doc_item_id,DOC_DESCRIPTION,STATUS from cms_checklist_item where checklist_id=(select CHECKLIST_ID from cms_checklist where cms_lsp_lmt_profile_id="
				+ id + " and category='LAD') And status='AWAITING'";
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
			ArrayList resultList = new ArrayList();
			ResultSet rs = dbUtil.executeQuery();

			ICheckListItem item = null;
			while (rs.next()) {

				item = new OBCheckListItem();
				item.setCheckListItemID(rs.getLong("doc_item_id"));
				item.setItemDesc(rs.getString("DOC_DESCRIPTION"));
				item.setItemStatus(rs.getString("STATUS"));
				resultList.add(item);
			}

			rs.close();
			return resultList;
		} catch (SQLException ex) {
			throw new SearchDAOException(
					"SQLException in getCheckListItemListForAudit", ex);
		} catch (Exception ex) {
			throw new SearchDAOException(
					"Exception in getCheckListItemListForAudit", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in getCheckListItemListForAudit", ex);
			}
		}
	}

	public List getUpdateLad(long id) {
		String sql = "select chk_doc_id from cms_lad_sub_item where chk_doc_item_id_lad="
				+ id;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
			ArrayList resultList = new ArrayList();
			ResultSet rs = dbUtil.executeQuery();

			ILADSubItem item = null;
			while (rs.next()) {

				item = new OBLADSubItem();
				item.setChklistDocId(rs.getLong("chk_doc_id"));
				resultList.add(item);
			}

			rs.close();
			return resultList;
		} catch (SQLException ex) {
			throw new SearchDAOException(
					"SQLException in getCheckListItemListForAudit", ex);
		} catch (Exception ex) {
			throw new SearchDAOException(
					"Exception in getCheckListItemListForAudit", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in getCheckListItemListForAudit", ex);
			}
		}
	}

	public long getLadid(long id) {
		String sql = "select LAD_ID from cms_lad where limit_profile_id=" + id;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
			ArrayList resultList = new ArrayList();
			ResultSet rs = dbUtil.executeQuery();

			long item = 0;
			while (rs.next()) {

				item = rs.getLong("LAD_ID");

			}

			rs.close();
			return item;
		} catch (SQLException ex) {
			throw new SearchDAOException(
					"SQLException in getCheckListItemListForAudit", ex);
		} catch (Exception ex) {
			throw new SearchDAOException(
					"Exception in getCheckListItemListForAudit", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException ex) {
				throw new SearchDAOException(
						"SQLException in getCheckListItemListForAudit", ex);
			}
		}
	}


	
	

	
	

}