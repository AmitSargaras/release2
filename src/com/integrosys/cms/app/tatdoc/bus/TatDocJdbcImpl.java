package com.integrosys.cms.app.tatdoc.bus;

import com.integrosys.cms.app.maintaintat.bus.OBTatLimitTrackStage;
import org.apache.commons.lang.Validate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.DataAccessException;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.maintaintat.bus.OBTatLimitTrack;
import com.integrosys.cms.app.tatduration.bus.OBTatParam;
import com.integrosys.cms.app.tatduration.bus.OBTatParamItem;

import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Cynthia
 * @since Sep 1, 2008
 */
public class TatDocJdbcImpl extends JdbcDaoSupport implements ITatDocJdbc {

	public Date getDateOfInstructionToSolicitor(long limitProfileID) {

		String query = "SELECT LI_GENERATION_DATE FROM SCI_LSP_LMT_PROFILE WHERE CMS_LSP_LMT_PROFILE_ID = ?";

		Date result = (Date) getJdbcTemplate().query(query, new Object[] { new Long(limitProfileID) },
				new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getDate("LI_GENERATION_DATE");
						}

						return null;
					}
				});

		return result;
	}

	public String getTrxValueByLimitProfileID(long limitProfileID) {

		String query = "SELECT TRANSACTION_ID FROM TRANSACTION WHERE STATUS <> 'CLOSED' "
				+ "AND TRANSACTION_TYPE = 'TAT_DOC' AND LIMIT_PROFILE_ID = ?";

		String result = (String) getJdbcTemplate().query(query, new Object[] { new Long(limitProfileID) },
				new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getString("TRANSACTION_ID");
						}

						return null;
					}

				});

		return result;
	}

    public OBTatLimitTrack getTatStageTrackingListByLimitProfileId(long limitProfileID) {

//		String query = "SELECT TAT.TAT_TRACK_ID, TAT.LMT_PROFILE_ID, TAT.TAT_PARAM_ITEM_ID, TAT.START_DATE, TAT.END_DATE, " +
//				"TAT.ACTUAL_DATE, TAT.REASON_FOR_EXCEEDING, TAT.APPLICABLE_TAT_STAGE_FLAG, TAT.IS_STAGE_ACTIVE, " +
//				"ITEM.PRE_STAGE, ITEM.POST_STAGE, ITEM.DURATION, ITEM.DURATION_TYPE, ITEM.SEQUENCE_ORDER, ITEM.POST_STAGE, " +
//				"ITEM.STAGE_TYPE, TAT.VERSION_TIME " +
//				"FROM CMS_TAT_TRACK TAT " +
//				"LEFT OUTER JOIN CMS_TAT_PARAM_ITEM ITEM ON ITEM.TAT_PARAM_ITEM_ID = TAT.TAT_PARAM_ITEM_ID " +
//				"WHERE TAT.LMT_PROFILE_ID = ? " +
//				"ORDER BY ITEM.SEQUENCE_ORDER";
        String query = "SELECT TAT.TAT_TRACK_ID, TAT.VERSION_TIME, TAT.LMT_PROFILE_ID, TAT.PRE_DISBURSEMENT_REMARKS, TAT.DISBURSEMENT_REMARKS, TAT.POST_DISBURSEMENT_REMARKS, " +
                "TAT_TRACK_ITEM.TAT_TRACK_STAGE_ID, TAT_TRACK_ITEM.TAT_PARAM_ITEM_ID, TAT_TRACK_ITEM.START_DATE, TAT_TRACK_ITEM.ACTUAL_DATE, TAT_TRACK_ITEM.END_DATE, TAT_TRACK_ITEM.REASON_FOR_EXCEEDING, TAT_TRACK_ITEM.APPLICABLE_TAT_STAGE_FLAG, TAT_TRACK_ITEM.IS_STAGE_ACTIVE, TAT_TRACK_ITEM.STATUS, " +
                "ITEM.PRE_STAGE, ITEM.POST_STAGE, ITEM.DURATION, ITEM.DURATION_TYPE, ITEM.SEQUENCE_ORDER, ITEM.STAGE_TYPE " +
                "FROM CMS_TAT_TRACK TAT " +
                "LEFT OUTER JOIN CMS_TAT_TRACK_ITEM TAT_TRACK_ITEM ON TAT_TRACK_ITEM.TAT_TRACK_ID = TAT.TAT_TRACK_ID " +
                "LEFT OUTER JOIN CMS_TAT_PARAM_ITEM ITEM ON ITEM.TAT_PARAM_ITEM_ID = TAT_TRACK_ITEM.TAT_PARAM_ITEM_ID " +
                "WHERE TAT.LMT_PROFILE_ID = ? " +
                "ORDER BY ITEM.SEQUENCE_ORDER";

        List resultList = getJdbcTemplate().query(query, new Object[]{new Long(limitProfileID)}, new TatTrackRowMapper());

        if (resultList != null && resultList.size() != 0) {
            return (OBTatLimitTrack) resultList.get(0);
        }

        return null;
    }
	
	/*public List getHolidayListByBranchAndYear(String branch, int year)
	{
		String query = "SELECT W.PROCESSING_DATE, W.BRANCH, W.BIZ_DAY_FLAG, W.STATUS " +
				"FROM CMS_WORKING_DAYS W " +
				"WHERE W.BRANCH = ? AND year (date(W.PROCESSING_DATE)) = ?";
		
		List resultList = getJdbcTemplate().query(query, new Object[]{ branch, new Long(year), }, new WorkingDaysRowMapper());
		return resultList;
	}*/
	
	public Long getNonWorkingDayNumber(String branch, Date startDate, Date endDate)
	{
		String query = "SELECT GET_REST_DAY(CAST(? AS Date), CAST(? AS Date), " +
				"CAST(? AS VarChar(40))) AS REST_DAY FROM DUAL";
		Object param[] = new Object[]{ startDate, endDate, branch };
//		getJdbcTemplate().query(query, new Object[]{ startDate, endDate, branch, }, new WorkingDaysNumberRowMapper());
		List list = getJdbcTemplate().query(query, param,  new WorkingDaysNumberRowMapper());
		if(!list.isEmpty())
			return new Long(String.valueOf(list.get(0)));
		else
			return null;
	}

	public void insertOrRemovePendingPerfectionCreditFolder(ITatDoc tatDoc, ILimitProfile limitProfile) {
		Validate.notNull(limitProfile, "limitProfile supplied must not be null.");
		Long limitProfileKey = new Long(limitProfile.getLimitProfileID());

		if (ICMSConstant.STATE_REJECTED.equals(limitProfile.getBCAStatus())) {
			// clear the entry from pending perfection folder for 'rejected' AA
			getJdbcTemplate().update("DELETE FROM cms_aa_pending_perfection WHERE cms_lsp_lmt_profile_id = ? ",
					new Object[] { limitProfileKey });
		}
		else {
			String hostAANumber = limitProfile.getBCAReference();
			String losAANumber = limitProfile.getLosLimitProfileReference();
			Long cmsCustomerId = new Long(limitProfile.getCustomerID());
			String origCountry = limitProfile.getOriginatingLocation().getCountryCode();
			String origOrganisation = limitProfile.getOriginatingLocation().getOrganisationCode();
			String applicationType = limitProfile.getApplicationType();
			String cifId = limitProfile.getLEReference();

			StringBuffer buf = new StringBuffer();
			if (tatDoc == null) {
				int count = getJdbcTemplate().queryForInt(
						"SELECT COUNT(*) FROM cms_aa_pending_perfection WHERE cms_lsp_lmt_profile_id = ? ",
						new Object[] { limitProfileKey });

				if (count == 0) {
					buf.append("INSERT INTO cms_aa_pending_perfection ");
					buf.append("(cms_lsp_lmt_profile_id, host_bca_ref_num, los_bca_ref_num, ");
					buf.append("cms_customer_id, cms_orig_country, cms_orig_organisation, ");
					buf.append("application_type, llp_le_id) ");
					buf.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?) ");
					getJdbcTemplate().update(
							buf.toString(),
							new Object[] { limitProfileKey, hostAANumber, losAANumber, cmsCustomerId, origCountry,
									origOrganisation, applicationType, cifId });
				}
			}
			else {
				int count = getJdbcTemplate().queryForInt(
						"SELECT COUNT(*) FROM cms_aa_pending_perfection WHERE cms_lsp_lmt_profile_id = ? ",
						new Object[] { limitProfileKey });
				if (tatDoc.getDocCompletionDate() != null) {
					if (count > 0) {
						getJdbcTemplate().update(
								"DELETE FROM cms_aa_pending_perfection WHERE cms_lsp_lmt_profile_id = ?",
								new Object[] { limitProfileKey });
					}
				}
				else {
					// contingency plan, in case entry not inside the folder
					// before
					if (count == 0) {
						buf.append("INSERT INTO cms_aa_pending_perfection ");
						buf.append("(cms_lsp_lmt_profile_id, host_bca_ref_num, los_bca_ref_num, ");
						buf.append("cms_customer_id, cms_orig_country, cms_orig_organisation, ");
						buf.append("application_type, llp_le_id) ");
						buf.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?) ");
						getJdbcTemplate().update(
								buf.toString(),
								new Object[] { limitProfileKey, hostAANumber, losAANumber, cmsCustomerId, origCountry,
										origOrganisation, applicationType, cifId });
					}
				}
			}
		}
	}
	
	
	/*private class WorkingDaysRowMapper implements RowMapper
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
            OBWorkingDays day = new OBWorkingDays();
            day.setBranch(rs.getString("BRANCH"));
            day.setBusinessDayFlag(rs.getString("BIZ_DAY_FLAG"));
            day.setProcessingDate(rs.getTimestamp("PROCESSING_DATE"));
            day.setStatus(rs.getString("STATUS"));
            
			return day;
		}
	}*/
	
	private class WorkingDaysNumberRowMapper implements RowMapper
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			return rs.getString("REST_DAY");
		}
	}

    private class TatTrackRowMapper implements RowMapper {
        private HashMap map = new HashMap();

        private TatTrackRowMapper() {
            this.map = new HashMap();
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            long tatTrackingID = rs.getLong("TAT_TRACK_ID");

            OBTatLimitTrack trackOB = null;
            Set stageListSet = null;

            if (map.containsKey(tatTrackingID + "")) {
                trackOB = (OBTatLimitTrack) map.get(tatTrackingID + "");
                stageListSet = trackOB.getStageListSet();
            } else {
                trackOB = new OBTatLimitTrack();
                trackOB.setTatTrackingId(rs.getLong("TAT_TRACK_ID"));
                trackOB.setVersionTime(rs.getLong("VERSION_TIME"));
                trackOB.setLimitProfileId(rs.getLong("LMT_PROFILE_ID"));
                trackOB.setPreDisbursementRemarks(rs.getString("PRE_DISBURSEMENT_REMARKS"));
                trackOB.setDisbursementRemarks(rs.getString("DISBURSEMENT_REMARKS"));
                trackOB.setPostDisbursementRemarks(rs.getString("POST_DISBURSEMENT_REMARKS"));
                stageListSet = new LinkedHashSet();

                map.put(tatTrackingID + "", trackOB);
            }

            OBTatLimitTrackStage trackStageOB = new OBTatLimitTrackStage();
            trackStageOB.setTatTrackingStageId(rs.getLong("TAT_TRACK_STAGE_ID"));
            trackStageOB.setTatParamItemId(rs.getLong("TAT_PARAM_ITEM_ID"));
            trackStageOB.setStartDate(rs.getTimestamp("START_DATE"));
            trackStageOB.setActualDate(rs.getTimestamp("ACTUAL_DATE"));
            trackStageOB.setEndDate(rs.getTimestamp("END_DATE"));
            trackStageOB.setReasonExceeding(rs.getString("REASON_FOR_EXCEEDING"));
            trackStageOB.setTatApplicable(rs.getString("APPLICABLE_TAT_STAGE_FLAG"));
            trackStageOB.setStageActive(rs.getString("IS_STAGE_ACTIVE"));
            trackStageOB.setStatus(rs.getString("STATUS"));

            OBTatParamItem item = new OBTatParamItem();
            item.setTatParamItemId(rs.getLong("TAT_PARAM_ITEM_ID"));
            item.setPreStage(rs.getString("PRE_STAGE"));
            item.setPostStage(rs.getString("POST_STAGE"));
            item.setDuration(rs.getDouble("DURATION"));
            item.setDurationType(rs.getString("DURATION_TYPE"));
            item.setStageType(rs.getString("STAGE_TYPE"));
            item.setSequenceOrder(rs.getLong("SEQUENCE_ORDER"));
            trackStageOB.setTatParamItem(item);

            stageListSet.add(trackStageOB);
            trackOB.setStageListSet(stageListSet);

            return trackOB;
        }
    }
}
