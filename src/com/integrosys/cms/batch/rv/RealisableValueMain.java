package com.integrosys.cms.batch.rv;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.batch.factory.TransactionControlledBatchJob;

public class RealisableValueMain implements TransactionControlledBatchJob {
	private final Logger logger = LoggerFactory.getLogger(RealisableValueMain.class);

	/**
	 * default size for a batch of securities to be used for rv calculation in a
	 * single transaction
	 */
	private final int BATCH_SIZE = 3000;

	TransactionTemplate transactionTemplate;

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void execute(Map context) throws BatchJobException {
		String countryCode = (String) context.get("country");
		logger.info("Country Code for the batch job [" + countryCode + "]");

		try {
			doWork(countryCode);
		}
		catch (Exception e) {
			throw new IncompleteBatchJobException("fail to complete the realisable value batch job", e);
		}
	}

	public void doWork(final String countryCode) throws Exception {
		final RVDAO rvDao = new RVDAO();

		// get total number of record of security required for rv
		long totalRec = ((Long) getTransactionTemplate().execute(new TransactionCallback() {

			public Object doInTransaction(TransactionStatus status) {
				return new Long(rvDao.getNoOfSecurities(countryCode));
			}
		})).longValue();

		logger.debug("Total number of Securities required to calculate RV: " + totalRec);

		long start = 0;
		while (start < totalRec) {
			List secList = (List) getTransactionTemplate().execute(
					new NextBatchSecuritiesTransactionCallback(rvDao, countryCode, start));

			if (secList.size() > 0) {
				getTransactionTemplate().execute(new TransactionCallbackHandler(rvDao, secList));
			}
			secList.clear();

			start = start + BATCH_SIZE;
		}
	}

	class NextBatchSecuritiesTransactionCallback implements TransactionCallback {
		private String countryCode;

		private long offset;

		private RVDAO rvDao;

		public NextBatchSecuritiesTransactionCallback(RVDAO rvDao, String countryCode, long offset) {
			this.rvDao = rvDao;
			this.countryCode = countryCode;
			this.offset = offset;
		}

		public Object doInTransaction(TransactionStatus status) {
			try {
				return this.rvDao.getNextBatchSecurities(countryCode, offset, BATCH_SIZE);
			}
			catch (Exception ex) {
				logger.error("encounter error when retrieving batch of security for the offset [" + offset
						+ "], return empty list " + ex);
				return Collections.EMPTY_LIST;
			}
		}
	}

	class TransactionCallbackHandler extends TransactionCallbackWithoutResult {
		private List secList;

		private RVDAO rvDao;

		TransactionCallbackHandler(RVDAO rvDao, List secList) {
			this.rvDao = rvDao;
			this.secList = secList;
		}

		protected void doInTransactionWithoutResult(TransactionStatus transactionstatus) {
			for (int i = 0; i < secList.size(); i++) {
				ICollateral nextCol = (ICollateral) (secList.get(i));
				Amount secRvAmt = RVCalculator.calculateSecurityRV(nextCol);

				if (secRvAmt!=null && ( (nextCol.getNetRealisableAmount()==null) ||
                        ((nextCol.getNetRealisableAmount()!=null) && (secRvAmt.getAmount()!=nextCol.getNetRealisableAmount().getAmount())) )) {
					logger.debug("CMS Collateral ID [" + nextCol.getCollateralID() + "], New Realisable Amount ["
							+ secRvAmt.getAmountAsBigDecimal() + "]");
                    if (nextCol.getNetRealisableAmount() != null) {
                        logger.debug("Old Realisable Amount ["
                                + nextCol.getNetRealisableAmount().getAmountAsBigDecimal() + "]");
                    }
                }
				else {
					continue;
				}

				boolean toUpdateStaging = false;
				// update RV Info for Actual Security
				try {
					this.rvDao.persistSecRVInfo(nextCol.getCollateralID(), secRvAmt);
					toUpdateStaging = true;
				}
				catch (Exception e) {
					logger.warn("Error when update Security RV value for Collateral, key [" + nextCol.getCollateralID()
							+ "], skip this collateral " + e);
				}
				// update RV Info for Staging Security
				try {
					if (toUpdateStaging) {
						this.rvDao.persistStagingSecRVInfo(nextCol.getCollateralID(), secRvAmt);
					}
				}
				catch (Exception e) {
					logger.warn("Error when update Security RV value for Collateral, key [" + nextCol.getCollateralID()
							+ "], skip this collateral for staging " + e);
				}
			}
		}
	}
}
