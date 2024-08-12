package com.integrosys.cms.ui.tatdoc;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * <p>
 * Web Action for TAT module to update Date of Instruction to Solicitor into TAT
 * document, And also the Date of Instructed in all the facilities of the AA.
 * <p>
 * If no TAT created, the request will be ignored.
 * @author Chong Jun Yong
 * 
 */
public class TatDocSolicitorInstructionAction extends Action {

	private final Logger logger = LoggerFactory.getLogger(TatDocSolicitorInstructionAction.class);

	private JdbcTemplate jdbcTemplate;

	private TransactionTemplate transactionTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String losLimitProfileReference = request.getParameter("LosLimitProfileReference");
		if (losLimitProfileReference == null) {
			logger.warn("no parameter for 'LosLimitProfileReference' input from the request.");
			return null;
		}

		try {
			final long cmsLimitProfileId = this.jdbcTemplate.queryForLong(
					"SELECT cms_lsp_lmt_profile_id FROM sci_lsp_lmt_profile WHERE los_bca_ref_num = ?",
					new Object[] { losLimitProfileReference });

			String tatDocTransactionId = (String) this.jdbcTemplate.queryForObject(
					"SELECT transaction_id FROM transaction trx, cms_tat_document tat "
							+ "WHERE trx.reference_id = tat.tat_id AND tat.cms_lsp_lmt_profile_id = ? "
							+ "AND trx.transaction_type = ?", new Object[] { new Long(cmsLimitProfileId),
							ICMSConstant.INSTANCE_TAT_DOC }, String.class);

			if (tatDocTransactionId != null) {
				this.transactionTemplate.execute(new TransactionCallback() {

					public Object doInTransaction(TransactionStatus status) {
						Date todayDate = new Date();

						StringBuffer tatBuf = new StringBuffer();
						tatBuf.append("UPDATE cms_tat_document SET solicitor_instruction_date = ? ");
						tatBuf.append("WHERE cms_lsp_lmt_profile_id = ? ");
						tatBuf.append("AND solicitor_instruction_date IS NULL ");

						jdbcTemplate.update(tatBuf.toString(), new Object[] { todayDate, new Long(cmsLimitProfileId) });

						tatBuf = new StringBuffer();
						tatBuf.append("UPDATE cms_stage_tat_document SET solicitor_instruction_date = ? ");
						tatBuf.append("WHERE tat_id = (SELECT staging_reference_id ");
						tatBuf.append("FROM transaction WHERE reference_id = ( ");
						tatBuf.append("SELECT tat_id FROM cms_tat_document ");
						tatBuf.append("WHERE cms_lsp_lmt_profile_id = ? ) AND transaction_type = ?) ");
						tatBuf.append("AND solicitor_instruction_date IS NULL");

						jdbcTemplate.update(tatBuf.toString(), new Object[] { todayDate, new Long(cmsLimitProfileId),
								ICMSConstant.INSTANCE_TAT_DOC });

						StringBuffer facBuf = new StringBuffer();
						facBuf.append("UPDATE cms_facility_master SET date_instructed = ? ");
						facBuf.append("WHERE cms_lsp_appr_lmts_id IN (SELECT cms_lsp_appr_lmts_id ");
						facBuf.append("FROM sci_lsp_appr_lmts WHERE cms_limit_profile_id = ?) ");
						facBuf.append("AND date_instructed IS NULL");

						jdbcTemplate.update(facBuf.toString(), new Object[] { todayDate, new Long(cmsLimitProfileId) });

						facBuf = new StringBuffer();
						facBuf.append("UPDATE cms_stg_facility_master SET date_instructed = ? ");
						facBuf.append("WHERE id IN (SELECT staging_reference_id ");
						facBuf.append("FROM transaction WHERE reference_id IN ( ");
						facBuf.append("SELECT id FROM cms_facility_master WHERE cms_lsp_appr_lmts_id IN ( ");
						facBuf.append("SELECT cms_lsp_appr_lmts_id FROM sci_lsp_appr_lmts ");
						facBuf.append("WHERE cms_limit_profile_id = ?)) AND transaction_type = ?) ");
						facBuf.append("AND date_instructed IS NULL");

						jdbcTemplate.update(facBuf.toString(), new Object[] { todayDate, new Long(cmsLimitProfileId),
								"FACILITY" });

						return null;
					}
				});
			}

		}
		catch (IncorrectResultSizeDataAccessException ex) {
			logger.warn("No Limit Profile / AA found for the LOS AA Number [" + losLimitProfileReference
					+ "], TAT update will be skipped.");
		}

		return null;
	}
}
