package com.integrosys.cms.batch.sibs.creditapplication;

import java.lang.ref.SoftReference;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.BatchSqlUpdate;

/**
 * Implementation of <tt>ICreditAppDao</tt> using JDBC routine.
 * @author Chong Jun Yong
 * @deprecated consider to use
 *             {@link com.integrosys.cms.batch.common.DefaultListRecordsJdbcDaoImpl}
 */
public class CreditAppJdbcDaoImpl extends JdbcDaoSupport implements ICreditAppDao {

	public ICreditApplication saveCreditAppNplClosedAcc(String entityName, ICreditApplicationNplClosedAcc obNplClosedAcc) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveCreditAppNplClosedAccList(String entityName, List creditAppNplClosedAccList) {
		// TODO Auto-generated method stub

	}

	public ICreditApplication saveCreditAppODTL(String entityName, ICreditApplicationODTL obCAODTL) {
		CreditApplicationODTL odtlFeed = (CreditApplicationODTL) obCAODTL;

		StringBuffer sql = new StringBuffer("INSERT INTO si_temp_sibs_ca001 (");
		sql.append("FEED_ID,");
		sql.append("RECORD_TYPE,");
		sql.append("AA_NUMBER,");
		sql.append("SRC_LIMIT_ID,");
		sql.append("OUT_LIMIT_ID,");
		sql.append("FACILITY_TYPE_CODE,");
		sql.append("EXTERNAL_SYSTEM_ACCT_NO,");
		sql.append("DRAWING_LIMIT_AMT,");
		sql.append("OUTSTANDING_SIGN,");
		sql.append("OUTSTANDING_AMT,");
		sql.append("FACILITY_SEQ,");
		sql.append("ACCT_TYPE,");
		sql.append("ACCT_SEQ,");
		sql.append("LOC_COUNTRY,");
		sql.append("LOC_ORG_CODE,");
		sql.append("FIRST_DISBURSEMENT_DATE,");
		sql.append("FINAL_DISBURSEMENT_DATE,");
		sql.append("CURRENT_BALANCE_AMT,");
		sql.append("CURRENT_BALANCE_SIGN,");
		sql.append("FINAL_PAYMENT_AMT_SIGN,");
		sql.append("FINAL_PAYMENT_AMT,");
		sql.append("PAYMENT_AMT_SIGN,");
		sql.append("PAYMENT_AMT,");
		sql.append("RELEASED_AMT_SIGN,");
		sql.append("RELEASED_AMT,");
		sql.append("LATE_CHARGES_AMT_SIGN,");
		sql.append("LATE_CHARGES_AMT,");
		sql.append("OTHER_CHARGES_AMT_SIGN,");
		sql.append("OTHER_CHARGES_AMT,");
		sql.append("MISC_CHARGES_AMT_SIGN,");
		sql.append("MISC_CHARGES_AMT,");
		sql.append("BILLED_INT_OS_AMT_SIGN,");
		sql.append("BILLED_INT_OS_AMT,");
		sql.append("ACCRUED_INTEREST_AMT_SIGN,");
		sql.append("ACCRUED_INTEREST_AMT,");
		sql.append("ACCRUED_LATE_CHARGE_AMT_SIGN,");
		sql.append("ACCRUED_LATE_CHARGE_AMT,");
		sql.append("ACCRUED_COMM_FEE_AMT_SIGN,");
		sql.append("ACCRUED_COMM_FEE_AMT,");
		sql.append("PRIME_RATE_NUMBER,");
		sql.append("PRIME_VARIANCE_SIGN,");
		sql.append("PRIME_VARIANCE_AMT) ");

		String bindVariables = StringUtils.repeat("?,", 41);

		sql.append("VALUES (NEXT VALUE FOR CMS_CREDIT_APP_OD_TL_SEQ, ");
		sql.append(bindVariables.substring(0, bindVariables.length() - 1)).append(")");

		List argList = new ArrayList();
		argList.add(odtlFeed.getRecordType());
		argList.add(odtlFeed.getAaNumber());
		argList.add(odtlFeed.getSourceLimitID());
		argList.add(odtlFeed.getOuterSourceLimitID());
		argList.add(odtlFeed.getFacilityTypeCode());
		argList.add(odtlFeed.getAccountNo());
		argList.add(new Double(odtlFeed.getDrawingLimit()));
		argList.add(odtlFeed.getOutstdgBalSign());
		argList.add(new Double(odtlFeed.getOutstandingBalance()));
		argList.add(new Long(odtlFeed.getFacilitySequence()));
		argList.add(odtlFeed.getAccountType());
		argList.add(new Long(odtlFeed.getAccountSequence()));
		argList.add(odtlFeed.getLocationCountry());
		argList.add(odtlFeed.getLocationOrgCode());
		argList.add(odtlFeed.getFirstDisbursementDate());
		argList.add(odtlFeed.getFinalDisbursementDate());
		argList.add(new Double(odtlFeed.getCurrentBalance()));
		argList.add(odtlFeed.getCurrentBalanceSign());
		argList.add(odtlFeed.getFinalPaymentAmountSign());
		argList.add(new Double(odtlFeed.getFinalPaymentAmount()));
		argList.add(odtlFeed.getPaymentAmountSign());
		argList.add(new Double(odtlFeed.getPaymentAmount()));
		argList.add(odtlFeed.getReleasedAmountSign());
		argList.add(new Double(odtlFeed.getReleasedAmount()));
		argList.add(odtlFeed.getLateChargesAmountSign());
		argList.add(new Double(odtlFeed.getLateChargesAmount()));
		argList.add(odtlFeed.getOtherChargesAmountSign());
		argList.add(new Double(odtlFeed.getOtherChargesAmount()));
		argList.add(odtlFeed.getMiscChargesAmountSign());
		argList.add(new Double(odtlFeed.getMiscChargesAmount()));
		argList.add(odtlFeed.getBilledIntOSAmountSign());
		argList.add(new Double(odtlFeed.getBilledIntOSAmount()));
		argList.add(odtlFeed.getAccruedInterestAmountSign());
		argList.add(new Double(odtlFeed.getAccruedInterestAmount()));
		argList.add(odtlFeed.getAccruedLateChargeAmountSign());
		argList.add(new Double(odtlFeed.getAccruedLateChargeAmount()));
		argList.add(odtlFeed.getAccruedCommFeeAmountSign());
		argList.add(new Double(odtlFeed.getAccruedCommFeeAmount()));
		argList.add(odtlFeed.getPrimeRateNumber());
		argList.add(odtlFeed.getPrimeVarianceSign());
		argList.add(new Double(odtlFeed.getPrimeVarianceAmount()));

		getJdbcTemplate().update(sql.toString(), argList.toArray());

		return null;
	}

	public void saveCreditAppODTLList(String entityName, List creditAppODTLList) {
		if (creditAppODTLList == null || creditAppODTLList.isEmpty()) {
			return;
		}

		StringBuffer sql = new StringBuffer("INSERT INTO si_temp_sibs_ca001 (");
		sql.append("FEED_ID,");
		sql.append("RECORD_TYPE,");
		sql.append("AA_NUMBER,");
		sql.append("SRC_LIMIT_ID,");
		sql.append("OUT_LIMIT_ID,");
		sql.append("FACILITY_TYPE_CODE,");
		sql.append("EXTERNAL_SYSTEM_ACCT_NO,");
		sql.append("DRAWING_LIMIT_AMT,");
		sql.append("OUTSTANDING_SIGN,");
		sql.append("OUTSTANDING_AMT,");
		sql.append("FACILITY_SEQ,");
		sql.append("ACCT_TYPE,");
		sql.append("ACCT_SEQ,");
		sql.append("LOC_COUNTRY,");
		sql.append("LOC_ORG_CODE,");
		sql.append("FIRST_DISBURSEMENT_DATE,");
		sql.append("FINAL_DISBURSEMENT_DATE,");
		sql.append("CURRENT_BALANCE_AMT,");
		sql.append("CURRENT_BALANCE_SIGN,");
		sql.append("FINAL_PAYMENT_AMT_SIGN,");
		sql.append("FINAL_PAYMENT_AMT,");
		sql.append("PAYMENT_AMT_SIGN,");
		sql.append("PAYMENT_AMT,");
		sql.append("RELEASED_AMT_SIGN,");
		sql.append("RELEASED_AMT,");
		sql.append("LATE_CHARGES_AMT_SIGN,");
		sql.append("LATE_CHARGES_AMT,");
		sql.append("OTHER_CHARGES_AMT_SIGN,");
		sql.append("OTHER_CHARGES_AMT,");
		sql.append("MISC_CHARGES_AMT_SIGN,");
		sql.append("MISC_CHARGES_AMT,");
		sql.append("BILLED_INT_OS_AMT_SIGN,");
		sql.append("BILLED_INT_OS_AMT,");
		sql.append("ACCRUED_INTEREST_AMT_SIGN,");
		sql.append("ACCRUED_INTEREST_AMT,");
		sql.append("ACCRUED_LATE_CHARGE_AMT_SIGN,");
		sql.append("ACCRUED_LATE_CHARGE_AMT,");
		sql.append("ACCRUED_COMM_FEE_AMT_SIGN,");
		sql.append("ACCRUED_COMM_FEE_AMT,");
		sql.append("PRIME_RATE_NUMBER,");
		sql.append("PRIME_VARIANCE_SIGN,");
		sql.append("PRIME_VARIANCE_AMT) ");

		String bindVariables = StringUtils.repeat("?,", 41);

		sql.append("VALUES (NEXT VALUE FOR CMS_CREDIT_APP_OD_TL_SEQ, ");
		sql.append(bindVariables.substring(0, bindVariables.length() - 1)).append(")");

		int[] parameterTypes = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.DECIMAL, Types.VARCHAR, Types.DECIMAL, Types.NUMERIC, Types.VARCHAR,
				Types.NUMERIC, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP, Types.DECIMAL,
				Types.VARCHAR, Types.VARCHAR, Types.DECIMAL, Types.VARCHAR, Types.DECIMAL, Types.VARCHAR,
				Types.DECIMAL, Types.VARCHAR, Types.DECIMAL, Types.VARCHAR, Types.DECIMAL, Types.VARCHAR,
				Types.DECIMAL, Types.VARCHAR, Types.DECIMAL, Types.VARCHAR, Types.DECIMAL, Types.VARCHAR,
				Types.DECIMAL, Types.VARCHAR, Types.DECIMAL, Types.VARCHAR, Types.VARCHAR, Types.DECIMAL };

		BatchSqlUpdate batch = new BatchSqlUpdate(getDataSource(), sql.toString(), parameterTypes);

		Object[] odtlFeeds = creditAppODTLList.toArray();
		for (int i = 0; i < odtlFeeds.length; i++) {
			SoftReference ref = new SoftReference(odtlFeeds[i]);
			List records = (List) ref.get();

			SoftReference recordsRef = new SoftReference(records.toArray());

			batch.update((Object[]) recordsRef.get());

			// clearing resource
			recordsRef.clear();
			recordsRef = null;
			records.clear();
			records = null;
			ref.clear();
			ref = null;
			odtlFeeds[i] = null;
		}

		creditAppODTLList.clear();
		odtlFeeds = null;

		batch.compile();
		batch.flush();
		batch.reset();
		batch = null;
	}
}
