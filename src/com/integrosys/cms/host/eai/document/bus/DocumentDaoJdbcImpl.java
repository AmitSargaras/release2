package com.integrosys.cms.host.eai.document.bus;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.base.techinfra.grouputil.OBItem;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;
import com.integrosys.cms.app.chktemplate.bus.OBDocumentItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICriInfo;
import com.integrosys.cms.host.eai.customer.bus.SubProfile;
import com.integrosys.cms.host.eai.document.DocumentConstants;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.Pledgor;
import com.integrosys.cms.host.eai.security.bus.SecurityLocation;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * ORM Based DAO using hibernate for documentation / checklist module
 * 
 * @author Iwan Satria
 * @since 28.11.2008
 * 
 */
public class DocumentDaoJdbcImpl extends JdbcTemplateAdapter implements IDocumentDaoJdbc {

	private Log logger = LogFactory.getLog(DocumentDaoJdbcImpl.class);

	public boolean isValidCIFNumberByMainBorrower(String aaNumber, final String cifNo) {
		String sql = "Select mp.LMP_LE_ID from SCI_LE_MAIN_PROFILE mp, SCI_LE_SUB_PROFILE sp, "
				+ "SCI_LSP_LMT_PROFILE aa where aa.CMS_CUSTOMER_ID=sp.CMS_LE_SUB_PROFILE_ID and "
				+ "sp.CMS_LE_MAIN_PROFILE_ID=mp.CMS_LE_MAIN_PROFILE_ID and aa.LOS_BCA_REF_NUM = ?";
		String result = (String) getJdbcTemplate().query(sql, new Object[] { aaNumber }, new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				String cifResult = null;
				boolean found = false;
				if (rs.next()) {
					cifResult = rs.getString("LMP_LE_ID");
					if (cifResult.equals(cifNo)) {
						found = true;
					}
				}
				if (found) {
					return cifResult;
				}
				else {
					return null;
				}
			}
		});
		return (cifNo.equals(result));
	}

	public boolean isValidCIFNumberByCoBorrower(String aaNumber, final String cifNo) {
		String sql = "Select cobo.LCL_COBO_LE_ID from SCI_LSP_CO_BORROW_LMT cobo, SCI_LSP_LMT_PROFILE aa "
				+ "where aa.CMS_CUSTOMER_ID=cobo.CMS_CUSTOMER_ID and aa.LOS_BCA_REF_NUM = ?";
		String result = (String) getJdbcTemplate().query(sql, new Object[] { aaNumber }, new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				String cifResult = null;
				boolean found = false;
				while (rs.next() && !found) {
					cifResult = rs.getString("LCL_COBO_LE_ID");
					if (cifResult.equals(cifNo)) {
						found = true;
					}
				}
				if (found) {
					return cifResult;
				}
				else {
					return null;
				}
			}
		});
		return (cifNo.equals(result));
	}

	public boolean isValidCIFNumberByJointBorrower(String aaNumber, String cifNo) {
		String sql = "Select jb.LJB_LE_ID " + "from SCI_LSP_JOINT_BORROWER jb, SCI_LSP_LMT_PROFILE aa "
				+ "where aa.LOS_BCA_REF_NUM = ? " + "and aa.CMS_LSP_LMT_PROFILE_ID = jb.CMS_LMP_LIMIT_PROFILE_ID "
				+ " and jb.LJB_LE_ID = ?";
		List resultList = (List) getJdbcTemplate().query(sql, new Object[] { aaNumber, cifNo }, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String cifResult = null;
				if (rs != null) {
					cifResult = rs.getString("LJB_LE_ID");
				}
				return cifResult;
			}
		});
		for (int i = 0; i < resultList.size(); i++) {
			if (cifNo.equals(resultList.get(i))) {
				return true;
			}
		}
		return false;
	}

	public boolean isValidCIFNumberByPledgor(String aaNumber, final String cifNo) {
		String sql = "Select plg.PLG_LE_ID from SCI_PLEDGOR_DTL plg, SCI_SEC_PLDGR_MAP plm, CMS_LIMIT_SECURITY_MAP lsm, CMS_SECURITY sec, "
				+ "SCI_LSP_APPR_LMTS lmt, SCI_LSP_LMT_PROFILE aa where plg.CMS_PLEDGOR_DTL_ID=plm.CMS_PLEDGOR_DTL_ID and "
				+ "plm.CMS_COLLATERAL_ID=sec.CMS_COLLATERAL_ID and sec.CMS_COLLATERAL_ID=lsm.CMS_COLLATERAL_ID and "
				+ "lsm.CMS_LSP_APPR_LMTS_ID=lmt.CMS_LSP_APPR_LMTS_ID and LMT.CMS_LIMIT_PROFILE_ID=aa.CMS_LSP_LMT_PROFILE_ID and "
				+ "aa.LOS_BCA_REF_NUM = ?";
		String result = (String) getJdbcTemplate().query(sql, new Object[] { aaNumber }, new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				String cifResult = null;
				boolean found = false;
				while (rs.next() && !found) {
					cifResult = rs.getString("PLG_LE_ID");
					if (cifResult.equals(cifNo)) {
						found = true;
					}
				}
				if (found) {
					return cifResult;
				}
				else {
					return null;
				}
			}
		});
		return (cifNo.equals(result));
	}

	public String getApplicationTypeByAANumber(String aaNumber) {
		String sql = "Select * from SCI_LSP_LMT_PROFILE where LOS_BCA_REF_NUM = ?";
		String result = (String) getJdbcTemplate().query(sql, new Object[] { aaNumber }, new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				String appType = null;
				if (rs.next()) {
					appType = rs.getString("APPLICATION_TYPE");
				}
				return appType;
			}
		});
		return result;
	}

	public String getOrigCountryByAANumber(String aaNumber, String checklistType) {
		String result = null;
		if (DocumentConstants.CHECKLIST_TYPE_BORROWER_PLEDGOR.equals(checklistType)) {
			String sql = "Select aa.CMS_ORIG_COUNTRY from SCI_LSP_LMT_PROFILE aa WHERE aa.LOS_BCA_REF_NUM = ?";
			result = (String) getJdbcTemplate().query(sql, new Object[] { aaNumber }, new ResultSetExtractor() {
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					String country = null;
					if (rs.next()) {
						country = rs.getString("CMS_ORIG_COUNTRY");
					}
					return country;
				}
			});
		}
		else if (DocumentConstants.CHECKLIST_TYPE_COLLATERAL.equals(checklistType)) {
			String sql = "Select distinct sec.SECURITY_LOCATION from SCI_LSP_LMT_PROFILE aa, SCI_LSP_APPR_LMTS lmt, "
					+ "CMS_LIMIT_SECURITY_MAP lsm, CMS_SECURITY sec "
					+ "where sec.CMS_COLLATERAL_ID=lsm.CMS_COLLATERAL_ID and lsm.CMS_LSP_APPR_LMTS_ID=lmt.CMS_LSP_APPR_LMTS_ID and "
					+ "lmt.CMS_LIMIT_PROFILE_ID=aa.CMS_LSP_LMT_PROFILE_ID and aa.LOS_BCA_REF_NUM = ?";
			result = (String) getJdbcTemplate().query(sql, new Object[] { aaNumber }, new ResultSetExtractor() {
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					String country = null;
					if (rs.next()) {
						country = rs.getString("SECURITY_LOCATION");
					}
					return country;
				}
			});
		}
		return result;
	}

	public String getOrigOrganisationByAANumber(String aaNumber, String checklistType) {
		String result = null;
		if (DocumentConstants.CHECKLIST_TYPE_BORROWER_PLEDGOR.equals(checklistType)) {
			String sql = "Select aa.CMS_ORIG_ORGANISATION from SCI_LSP_LMT_PROFILE aa where aa.LOS_BCA_REF_NUM = ?";
			result = (String) getJdbcTemplate().query(sql, new Object[] { aaNumber }, new ResultSetExtractor() {
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					String organisation = null;
					if (rs.next()) {
						organisation = rs.getString("CMS_ORIG_ORGANISATION");
					}
					return organisation;
				}
			});
		}
		else if (DocumentConstants.CHECKLIST_TYPE_COLLATERAL.equals(checklistType)) {
			String sql = "Select sec.SECURITY_ORGANISATION from SCI_LSP_LMT_PROFILE aa, SCI_LSP_APPR_LMTS lmt, "
					+ "CMS_LIMIT_SECURITY_MAP lsm, CMS_SECURITY sec "
					+ "where sec.CMS_COLLATERAL_ID=lsm.CMS_COLLATERAL_ID and lsm.CMS_LSP_APPR_LMTS_ID=lmt.CMS_LSP_APPR_LMTS_ID and "
					+ "lmt.CMS_LIMIT_PROFILE_ID=aa.CMS_LSP_LMT_PROFILE_ID and aa.LOS_BCA_REF_NUM = ?";
			result = (String) getJdbcTemplate().query(sql, new Object[] { aaNumber }, new ResultSetExtractor() {
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					String organisation = null;
					if (rs.next()) {
						organisation = rs.getString("SECURITY_ORGANISATION");
					}
					return organisation;
				}
			});
		}
		return result;
	}

	public Vector retrieveCheckListItemsByCheckListID(long checklistID) throws SearchDAOException {
		List itemList = getJdbcTemplate().query("Select * from CMS_CHECKLIST_ITEM where CHECKLIST_ID = ?",
				new Object[] { new Long(checklistID) }, new RowMapper() {

					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						CheckListItem item = null;
						item = processMessageCheckListItem(rs);
						return item;
					}
				});

		Vector newCol = null;
		if (itemList != null) {
			newCol = new Vector();
			newCol.addAll(itemList);
		}
		return newCol;
	}

	public ApprovedSecurity getCollateralByLOSSecurityID(String losSecurityID) {
		String sql = "Select * from CMS_SECURITY where LOS_SECURITY_DTL_ID = ?";
		ApprovedSecurity result = (ApprovedSecurity) getJdbcTemplate().query(sql, new Object[] { losSecurityID },
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						ApprovedSecurity col = null;
						if (rs.next()) {
							col = processMessageCollateral(rs);
						}
						return col;
					}
				});
		return result;
	}

	public ApprovedSecurity getCollateralByID(long id) {
		String sql = "Select * from CMS_SECURITY where CMS_COLLATERAL_ID = ?";
		ApprovedSecurity result = (ApprovedSecurity) getJdbcTemplate().query(sql, new Object[] { new Long(id) },
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						ApprovedSecurity col = null;
						if (rs.next()) {
							col = processMessageCollateral(rs);
						}
						return col;
					}
				});
		return result;
	}

	public LimitProfile retrieveLimitByAANumber(final String aaNumber) {
		String sql = "Select * from SCI_LSP_LMT_PROFILE where LOS_BCA_REF_NUM = ?";
		JdbcTemplate dbTemplate = getJdbcTemplate();
		LimitProfile result = (LimitProfile) dbTemplate.query(sql, new Object[] { aaNumber }, new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				LimitProfile lmt = null;
				if (rs.next()) {
					lmt = new LimitProfile();
					lmt.setLimitProfileId(rs.getLong("CMS_LSP_LMT_PROFILE_ID"));
					lmt.setAALawType(rs.getString("APPLICATION_LAW_TYPE"));
					lmt.setAASource(rs.getString("SOURCE_ID"));
					lmt.setAAStatus(rs.getString("CMS_BCA_STATUS"));
					lmt.setAAType(rs.getString("APPLICATION_TYPE"));
					lmt.setCIFId(rs.getString("LLP_LE_ID"));
					lmt.setCIFSource(rs.getString("CIF_SOURCE_ID"));
					lmt.setCmsSubProfileId(rs.getLong("CMS_CUSTOMER_ID"));
					lmt.setLOSAANumber(rs.getString("LOS_BCA_REF_NUM"));
					lmt.setHostAANumber(rs.getString("LLP_BCA_REF_NUM"));
				}
				return lmt;
			}
		});
		return result;
	}

	public CheckListItem retrieveCheckListItemByDocNo(long docNo) {
		String sql = "Select * from CMS_CHECKLIST_ITEM where DOC_ITEM_REF = ?";
		CheckListItem result = (CheckListItem) getJdbcTemplate().query(sql, new Object[] { new Long(docNo) },
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						CheckListItem item = null;
						if (rs.next()) {
							item = processMessageCheckListItem(rs);
						}
						return item;
					}
				});
		return result;
	}

	public CheckListItem[] retrieveNonDeletedCheckListItemByChecklistID(long checklistID) {
		String sql = "Select * from CMS_CHECKLIST_ITEM where IS_DELETED='N' and CHECKLIST_ID = ?";
		List result = (List) getJdbcTemplate().query(sql, new Object[] { new Long(checklistID) }, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				CheckListItem item = null;
				item = processMessageCheckListItem(rs);
				return item;
			}
		});
		if (result == null) {
			return null;
		}
		return (CheckListItem[]) result.toArray(new CheckListItem[0]);
	}

	public SubProfile retrieveSubProfileByCIFNumber(String cifNumber) {
		String sql = "select sp.* from SCI_LE_MAIN_PROFILE mp, SCI_LE_SUB_PROFILE sp "
				+ "where sp.CMS_LE_MAIN_PROFILE_ID = mp.CMS_LE_MAIN_PROFILE_ID " + "and mp.LMP_LE_ID= ? ";
		SubProfile result = (SubProfile) getJdbcTemplate().query(sql, new Object[] { cifNumber },
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						SubProfile subProfile = null;
						if (rs.next()) {
							subProfile = processMessageSubProfile(rs);
						}
						return subProfile;
					}
				});
		return result;
	}

	public Pledgor retrievePledgorByAAAndCIF(String aaNumber, String cifNumber) {
		String sql = "select p.cms_pledgor_dtl_id, p.plg_le_id, p.plg_legal_name, p.plg_rel_type_num, "
				+ " p.plg_rel_type_value, p.plg_legal_type_num, p.plg_legal_type_value, "
				+ " p.plg_dmcl_cntry_iso_code, p.cms_orig_country, p.cms_orig_organisation, "
				+ " p.source_id from sci_lsp_lmt_profile pf, cms_limit_security_map lsm, "
				+ " sci_sec_pldgr_map spm, sci_pledgor_dtl p " + " where pf.LOS_BCA_REF_NUM = ? "
				+ " and pf.cms_lsp_lmt_profile_id = lsm.CMS_LSP_LMT_PROFILE_ID "
				+ " and lsm.cms_collateral_id = spm.CMS_COLLATERAL_ID "
				+ " and spm.CMS_PLEDGOR_DTL_ID = p.CMS_PLEDGOR_DTL_ID " + " and p.PLG_LE_ID = ? ";
		Pledgor result = (Pledgor) getJdbcTemplate().query(sql, new Object[] { aaNumber, cifNumber },
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						Pledgor pledgor = null;
						if (rs.next()) {
							pledgor = processMessagePledgor(rs);
						}
						return pledgor;
					}
				});
		return result;
	}

	public long retrieveCheckListIDByMsgCheckList(CheckList checklist) {
		String aaNumber = checklist.getAANumber();
		long templateID = checklist.getTemplateID();
		String checklistType = checklist.getChecklistType();
		Long result = null;
		if (DocumentConstants.CHECKLIST_TYPE_BORROWER_PLEDGOR.equals(checklistType)) {
			String subCategory = checklist.getCcChecklist().getCustomerType();
			String sql = "";
			if (!ICMSConstant.CHECKLIST_PLEDGER.equals(subCategory)) {
				sql = "select chk.CHECKLIST_ID "
						+ " from CMS_CHECKLIST chk, SCI_LSP_LMT_PROFILE aa, sci_le_sub_profile sp "
						+ " where chk.CMS_LSP_LMT_PROFILE_ID = aa.CMS_LSP_LMT_PROFILE_ID "
						+ " and aa.LOS_BCA_REF_NUM= ? " + " and sp.LSP_LE_ID = ? "
						+ " and sp.CMS_LE_SUB_PROFILE_ID = chk.CMS_LMP_SUB_PROFILE_ID " + " and chk.CATEGORY = ? "
						+ " and chk.SUB_CATEGORY = ? ";
			}
			else {
				sql = "select chk.CHECKLIST_ID " + " from CMS_CHECKLIST chk, SCI_LSP_LMT_PROFILE aa, "
						+ " cms_limit_security_map lsm, sci_sec_pldgr_map spm, sci_pledgor_dtl p "
						+ " where chk.CMS_LSP_LMT_PROFILE_ID = aa.CMS_LSP_LMT_PROFILE_ID "
						+ " and aa.LOS_BCA_REF_NUM= ? " + " and p.PLG_LE_ID = ? "
						+ " and p.CMS_PLEDGOR_DTL_ID = spm.CMS_PLEDGOR_DTL_ID "
						+ " and p.cms_pledgor_dtl_id = chk.CMS_PLEDGOR_DTL_ID "
						+ " and spm.cms_collateral_id = lsm.cms_collateral_id "
						+ " and lsm.CMS_LSP_LMT_PROFILE_ID = aa.cms_lsp_lmt_profile_id " + " and chk.CATEGORY= ? "
						+ " and chk.SUB_CATEGORY= ? ";
			}
			result = (Long) getJdbcTemplate().query(sql,
					new Object[] { aaNumber, checklist.getCcChecklist().getCIFNo(), checklistType, subCategory },
					new ResultSetExtractor() {
						public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
							Long checklistID = null;
							if (rs.next()) {
								checklistID = new Long(rs.getLong("CHECKLIST_ID"));
							}
							return checklistID;
						}
					});
		}
		else if (DocumentConstants.CHECKLIST_TYPE_COLLATERAL.equals(checklistType)) {
			String losSecurityID = checklist.getScChecklist().getLOSSecurityId();
			ApprovedSecurity sec = getCollateralByLOSSecurityID(losSecurityID);
			long collateralID = sec.getCMSSecurityId();
			String sql = "select chk.* from CMS_CHECKLIST chk, SCI_LSP_LMT_PROFILE aa "
					+ "where chk.CMS_LSP_LMT_PROFILE_ID=aa.CMS_LSP_LMT_PROFILE_ID "
					+ "and aa.LOS_BCA_REF_NUM=? and chk.MASTERLIST_ID=? "
					+ "and chk.CATEGORY=? and chk.CMS_COLLATERAL_ID=?";
			result = (Long) getJdbcTemplate().query(sql,
					new Object[] { aaNumber, new Long(templateID), checklistType, new Long(collateralID) },
					new ResultSetExtractor() {
						public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
							Long checklistID = null;
							if (rs.next()) {
								checklistID = new Long(rs.getLong("CHECKLIST_ID"));
							}
							return checklistID;
						}
					});
		}
		if (result == null) {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
		return result.longValue();
	}

	private CheckListItem processMessageCheckListItem(ResultSet rs) throws SQLException {
		CheckListItem item = new CheckListItem();
		item.setCmsDocItemID(rs.getLong("DOC_ITEM_ID"));
		item.setChecklistID(rs.getLong("CHECKLIST_ID"));
		item.setDescription(rs.getString("DOC_DESCRIPTION"));
		item.setDocCode(rs.getString("DOCUMENT_CODE"));
		item.setDocNo(rs.getLong("DOC_ITEM_REF"));
		item.setExpiryDate(MessageDate.getInstance().getString(rs.getDate("EXPIRY_DATE")));
		item.setJDOExpiryDate(rs.getDate("EXPIRY_DATE"));
		item.setReceivedDate(MessageDate.getInstance().getString(rs.getDate("RECEIVED_DATE")));
		item.setJDOReceivedDate(rs.getDate("RECEIVED_DATE"));
		item.setWaivedDate(MessageDate.getInstance().getString(rs.getDate("WAIVED_DATE")));
		item.setJDOWaivedDate(rs.getDate("WAIVED_DATE"));
		item.setStatus(rs.getString("STATUS"));
		item.setPreApprove(ICMSConstant.YES.equals(rs.getString("IS_PRE_APPROVE")));
		return item;
	}

	private ApprovedSecurity processMessageCollateral(ResultSet rs) throws SQLException {
		ApprovedSecurity col = new ApprovedSecurity();
		col.setCMSSecurityId(rs.getLong("CMS_COLLATERAL_ID"));
		col.setCMSStatus(rs.getString("STATUS"));
		col.setCMSVersionTime(rs.getLong("VERSION_TIME"));
		col.setCmv(new Double(rs.getDouble("CMV")));
		col.setCmvCurrency(rs.getString("CMV_CURRENCY"));
		col.setFsv(new Double(rs.getDouble("FSV")));
		col.setFsvCurrency(rs.getString("FSV_CURRENCY"));
		col.setComments(rs.getString("COMMENT"));
		col.setCurrency(rs.getString("SCI_SECURITY_CURRENCY"));
		col.setCustodian(rs.getString("SECURITY_CUSTODIAN"));
		col.setCustodianType(rs.getString("CUSTODIAN_TYPE"));
		col.setLOSSecurityId(rs.getString("LOS_SECURITY_DTL_ID"));
		col.setSecurityLocation(new SecurityLocation(rs.getString("SECURITY_LOCATION"), rs
				.getString("SECURITY_ORGANISATION")));
		return col;
	}

	private SubProfile processMessageSubProfile(ResultSet rs) throws SQLException {
		SubProfile sub = new SubProfile();
		sub.setCmsId(rs.getLong("CMS_LE_SUB_PROFILE_ID"));
		sub.setCifId(rs.getString("LSP_LE_ID"));
		sub.setCmsMainProfileId(rs.getLong("CMS_LE_MAIN_PROFILE_ID"));
		sub.setOriginatingCountry(rs.getString("CMS_SUB_ORIG_COUNTRY"));
		sub.setOriginatingOrganisation(rs.getString("CMS_SUB_ORIG_ORGANISATION"));
		sub.setSubProfileId(rs.getLong("LSP_ID"));
		sub.setSubProfileName("LSP_SHORT_NAME");
		return sub;
	}

	private Pledgor processMessagePledgor(ResultSet rs) throws SQLException {
		Pledgor p = new Pledgor();
		p.setCmsId(rs.getLong("CMS_PLEDGOR_DTL_ID"));
		p.setCIF(rs.getString("PLG_LE_ID"));
		p.setCMSPledgorId(rs.getLong("CMS_PLEDGOR_DTL_ID"));
		p.setOriginatingCountry(rs.getString("CMS_ORIG_COUNTRY"));
		p.setOriginatingOrganisation(rs.getString("CMS_ORIG_ORGANISATION"));

		return p;
	}

	public String getDocSeqId() {
		int docId=0;
		try{
			String sql = "select CHECKLIST_ITEM_SEQ.nextval S from dual";
			docId = (int) getJdbcTemplate().queryForInt(sql);
		}catch(Exception ex){
			ex.printStackTrace();
		}
			return String.valueOf(docId);
		}

	public OBDocumentItem getAllRamratingDocumentlist(ICriInfo[] criList) {
		 String sql = "select * from cms_document_globallist where document_description like '%RAM%' and category='REC' and statement_type='RAM_RATING' and is_recurrent='on'";
//		 String sql1= "select * from cms_document_globallist where document_description like '%March%' and category='REC' and statement_type='RAM_RATING' and is_recurrent='on'";
		 OBDocumentItem resultList=new OBDocumentItem();
		 JdbcTemplate jdbcTemplate = getJdbcTemplate();
		 jdbcTemplate.setFetchSize(100);
		 /*String decemberDoc="DOC20180921000002084";
		 String marchDoc="DOC20180921000002085";*/
		try {
			int i =0;
			if(criList[i].getCustomerFyClouser().equals("December Ending"))
			{
				 resultList = (OBDocumentItem) getJdbcTemplate().query(sql,
						new ResultSetExtractor() {
							public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
								OBDocumentItem col = null;
								if (rs.next()) {
									col = processGlobalDocument(rs);  
								}
								return col;
							}
						});
			}
			if(criList[i].getCustomerFyClouser().equals("March Ending"))
			{
				 resultList = (OBDocumentItem) getJdbcTemplate().query(sql,
						new ResultSetExtractor() {
							public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
								OBDocumentItem col = null;
								if (rs.next()) {
									col = processGlobalDocument(rs); 
								}
								return col;
							}
						});
			}
		}
		catch(Exception ex)
		{
		ex.printStackTrace();	
		}
		
		return resultList;
	}
	

	private OBDocumentItem processGlobalDocument(ResultSet rs) throws SQLException {
		OBDocumentItem documentItem= new OBDocumentItem();
		
			documentItem.setItemID(rs.getLong("DOCUMENT_ID"));
			documentItem.setItemDesc(rs.getString("DOCUMENT_DESCRIPTION"));
			documentItem.setItemCode(rs.getString("DOCUMENT_CODE"));
			documentItem.setExpiryDate(rs.getDate("EXPIRY_DATE"));
			documentItem.setStatementType(rs.getString("STATEMENT_TYPE"));
		
		return documentItem;
	}

	public ICheckList getRamRatingDocList(final ICheckList checkList,long limitProfileID) {
		ICheckList cmsChecklist=null;
			String sql = "SELECT * FROM cms_checklist_item WHERE  " +
					" statement_type ='RAM_RATING' AND checklist_id  IN " +
					"(SELECT checklist_id FROM cms_checklist WHERE cms_lsp_lmt_profile_id=?)";
			try{
				
				 cmsChecklist = (ICheckList) getJdbcTemplate().query(sql, new Object[] { limitProfileID},
						new ResultSetExtractor() {
							public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
								OBCheckListItem item = null;
								ICheckList newCheckList=null;
								if (rs.next()) {
									item = processRamRatingCheckListItem(rs);
									newCheckList=newchecklistItem(checkList,item);
								}
								return newCheckList;
							}
						});
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			return cmsChecklist;
		}

	
	private ICheckList newchecklistItem(ICheckList checkList,OBCheckListItem item) throws SQLException {
		try{
		  ICheckListItem[] checklistItem = new ICheckListItem [checkList.getCheckListItemList().length+1];
		   for(int i = 0; i < checkList.getCheckListItemList().length; i++)
	       {
			   checklistItem[i] = checkList.getCheckListItemList()[i];
	       }
		   checklistItem[checkList.getCheckListItemList().length] = item;
		   checkList.setCheckListItemList(checklistItem);
		   checkList.setCheckListID(checkList.getCheckListID());
		
		return checkList;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	
	private OBCheckListItem processRamRatingCheckListItem(ResultSet rs) throws SQLException {
		OBCheckListItem item = new OBCheckListItem();
		item.setCheckListItemID(rs.getLong("DOC_ITEM_ID"));
		item.setCheckListItemDesc(rs.getString("DOC_DESCRIPTION"));
		item.setItemCode(rs.getString("DOCUMENT_CODE"));
//		item.setCheckListItemRef(rs.getLong("DOC_ITEM_REF"));
		item.setExpiryDate(rs.getDate("EXPIRY_DATE"));
		item.setItemStatus(rs.getString("STATUS"));
		item.setStatementType(rs.getString("STATEMENT_TYPE"));
		return item;
	}
	
}
