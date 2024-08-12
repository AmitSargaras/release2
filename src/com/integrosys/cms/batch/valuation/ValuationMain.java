package com.integrosys.cms.batch.valuation;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.valuation.UnknownSecurityTypeException;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationDetailIncompleteException;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationNotRequiredException;
import com.integrosys.cms.app.collateral.bus.valuation.model.GenericValuationModel;
import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.batch.factory.TransactionControlledBatchJob;

/**
 * <p>
 * Batch Job for Collateral Valuation.
 * 
 * <p>
 * Required Parameters
 * <ul>
 * <li>country
 * <li>sectype
 * <li>source (optional)
 * <li>specialhandle (optional, required for Asset Based Vehicle)
 * <li>region (optional, required for Asset Based Vehicle)
 * </ul>
 * 
 * @author Cynthia Zhou
 * @author Chong Jun Yong
 * 
 */
public class ValuationMain extends ValuationMainAccessor implements TransactionControlledBatchJob, IValuationMain {

	private final Logger logger = LoggerFactory.getLogger(ValuationMain.class);

	public static final String COUNTRY = "country";

	public static final String SEC_TYPE = "sectype";

	public static final String SOURCE = "source";

	public static final String SPECIAL_HANDLING = "specialhandle";

	public static final String REGION = "region";

	public static final String ID = "id";

	public static final String SECID = "secid";

	private static final int DEFAULT_BATCH_SIZE = 3000;

	private int fetchCollateralNextBatchSize = DEFAULT_BATCH_SIZE;

	public void setFetchCollateralNextBatchSize(int fetchCollateralNextBatchSize) {
		this.fetchCollateralNextBatchSize = fetchCollateralNextBatchSize;
	}

	public void execute(Map context) throws BatchJobException {
		getValuationParameterValidator().validate(context);
		initialiseSingleton(context);
		executeInternal(context);
	}

	/**
	 * To retrieve collaterals to be valuated batch by batch, specified by
	 * 'fetchCollateralNextBatchSize'. If the retrieved collaterals is less than
	 * 'fetchCollateralNextBatchSize', whole batch job will stop.
	 * @param context batch context
	 */
	private void executeInternal(Map context) {
		boolean hasMoreCollaterals = true;
		int totalCount = 0;
		int batchCount = 1;
		while (hasMoreCollaterals) {
			final List secList = getValuationMainDao().getNextBatchSecurities(context, 0,
					this.fetchCollateralNextBatchSize);
			if (!secList.isEmpty()) {
				try {
					getTransactionTemplate().execute(new TransactionCallback() {

						public Object doInTransaction(TransactionStatus status) {
							processValuationModelList(secList);
							return null;
						}
					});
				}
				catch (IncompleteBatchJobException e) {
					logger.warn("failed valuation job for this batch [" + batchCount + "]", e);
				}
			}

			if (secList.size() < this.fetchCollateralNextBatchSize) {
				hasMoreCollaterals = false;
			}

			batchCount++;
			totalCount += secList.size();
		}

		logger.info("finish valuate collaterals, total count [" + totalCount + "], context info [" + context + "]");
	}

	public void processValuationModelList(List valuationModelList) {
		try {
			for (int i = 0; i < valuationModelList.size(); i++) {
				GenericValuationModel valuationModel = (GenericValuationModel) (valuationModelList.get(i));
				long collateralId = valuationModel.getCollateralId();
				String secId = valuationModel.getSecId();
				String subType = valuationModel.getSecSubtype();

				try {
					getValuationHandler().performSystemValuation(valuationModel);
				}
				catch (ValuationNotRequiredException e) {
					logger.warn("[ValNotRequired] " + "\t Security SubType [" + subType + "]" + "\t Collateral ID ["
							+ collateralId + "] " + "\t Security ID [" + secId + "]"
							+ "\t [Valuation is not required] " + "\t [error message: " + e.getMessage() + "]");
				}
				catch (UnknownSecurityTypeException e) {
					logger.warn("[UnknownSecType] " + "\t Security SubType [" + subType + "]" + "\t Collateral ID ["
							+ collateralId + "] " + "\t Security ID [" + secId + "]" + "\t [Unknown security type] "
							+ "\t [error message: " + e.getMessage() + "]");
				}
				catch (ValuationDetailIncompleteException e) {
					// logger.warn("Valuation detail is incomplete for collateral: "
					// + collateralId, e);
					logger.warn("[IncompleteDtls] " + "\t Security SubType [" + subType + "]" + "\t Collateral ID ["
							+ collateralId + "] " + "\t Security ID [" + secId + "]"
							+ "\t [Valuation detail is incomplete] " + "\t [error encountered includes: "
							+ e.getMessage() + "]");
				}
				catch (Throwable t) {
					logger.warn("[UnknownError] " + "\t Security SubType [" + subType + "]" + "\t Collateral ID ["
							+ collateralId + "] " + "\t Security ID [" + secId + "]"
							+ "\t [Unexpected Error!! Please Trace!!] " + "\t [error encountered includes: "
							+ t.getMessage() + "]", t);
				}
				finally {
					// to update valuated flag for this collateral.
					getValuationMainDao().updateValuatedFlagForCollateral(collateralId);
				}
			}
		}
		catch (Exception ex) {
			logger.error("Exception Encountered\n " + ex);
			throw new IncompleteBatchJobException("failed to perform system valuation, proceed to next batch", ex);
		}
	}

	private void initialiseSingleton(Map context) {
		String secType = (String) context.get(SEC_TYPE);
		String specialHandle = (String) context.get(SPECIAL_HANDLING);
		logger.debug("Initialising Valuation Profile Singleton \t sectype=[" + secType + "] \t specialHandle=["
				+ specialHandle + "]");

		getValuationProfileSingletonListener().reloadSingleton(new OBCollateralSubType(secType, null));
		getValuationProfileSingletonListener().reloadSingleton(new OBCollateralSubType(specialHandle));
	}

}
