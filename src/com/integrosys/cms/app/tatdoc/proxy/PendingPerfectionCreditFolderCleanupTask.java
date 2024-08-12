package com.integrosys.cms.app.tatdoc.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * A cleanup task to remove entry from pending perfetion credit folder if the AA
 * having all cancelled facilities.
 * <p>
 * This class is a Runnable object, either can be executed in adhoc basic or
 * scheduled to keep running in a certain frequency.
 * 
 * @author Chong Jun Yong
 * 
 */
public class PendingPerfectionCreditFolderCleanupTask implements Runnable {

	private final Logger logger = LoggerFactory.getLogger(PendingPerfectionCreditFolderCleanupTask.class);

	private JdbcTemplate jdbcTemplate;

	private TransactionTemplate transactionTemplate;

	/**
	 * To indicate whether this bean is running, supposing this is created as
	 * singleton
	 */
	private boolean isRunning = false;

	private final Object runningMonitor = new Object();

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		if (this.jdbcTemplate.getDataSource() == null) {
			throw new IllegalStateException("'jdbcTemplate' supplied must have 'dataSource' injected");
		}
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
		if (this.transactionTemplate.getTransactionManager() == null) {
			throw new IllegalStateException("'transactionTemplate' supplied must have 'transactionManager' injected");
		}
	}

	public void run() {
		if (this.isRunning) {
			return;
		}

		synchronized (runningMonitor) {
			this.isRunning = true;
		}

		this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				StringBuffer buf = new StringBuffer();
				buf.append("DELETE FROM cms_aa_pending_perfection ppcf ");
				buf.append("WHERE NOT EXISTS (SELECT 1 FROM sci_lsp_appr_lmts lmt, ");
				buf.append("cms_facility_master fac, cms_fac_general fg ");
				buf.append("WHERE cms_limit_profile_id = ppcf.cms_lsp_lmt_profile_id ");
				buf.append("AND lmt.cms_lsp_appr_lmts_id = fac.cms_lsp_appr_lmts_id ");
				buf.append("AND fac.id = fg.cms_fac_master_id ");
				buf.append("AND fg.fac_status_code_value <> ? ) ");

				int count = jdbcTemplate
						.update(buf.toString(), new Object[] { ICMSConstant.FACILITY_STATUS_CANCELLED });

				logger.info("Number of Pending Perfection Credit Folder entry has been purged : [" + count + "]");
			}
		});

		synchronized (runningMonitor) {
			this.isRunning = false;
		}
	}
}
