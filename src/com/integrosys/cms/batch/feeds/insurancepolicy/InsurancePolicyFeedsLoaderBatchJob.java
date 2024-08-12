package com.integrosys.cms.batch.feeds.insurancepolicy;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.batch.item.file.mapping.DefaultFieldSet;
import org.springframework.batch.item.file.mapping.FieldSet;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.integrosys.cms.batch.common.DefaultDomainAwareBatchFeedLoader;

/**
 * Feeds Loader batch job to load insurance policy feeds and update into
 * insurance policy table using stored procedure.
 * @author Chong Jun Yong
 * 
 */
public class InsurancePolicyFeedsLoaderBatchJob extends DefaultDomainAwareBatchFeedLoader {

	private String successLoadedFeedsFilePath;

	private String failLoadedFeedsFilePath;

	private LineAggregator successLoadedFeedsLineAggregator;

	private LineAggregator failLoadedFeedsLineAggregator;

	public void setSuccessLoadedFeedsFilePath(String successLoadedFeedsFilePath) {
		this.successLoadedFeedsFilePath = successLoadedFeedsFilePath;
	}

	public void setFailLoadedFeedsFilePath(String failLoadedFeedsFilePath) {
		this.failLoadedFeedsFilePath = failLoadedFeedsFilePath;
	}

	public void setSuccessLoadedFeedsLineAggregator(LineAggregator successLoadedFeedsLineAggregator) {
		this.successLoadedFeedsLineAggregator = successLoadedFeedsLineAggregator;
	}

	public void setFailLoadedFeedsLineAggregator(LineAggregator failLoadedFeedsLineAggregator) {
		this.failLoadedFeedsLineAggregator = failLoadedFeedsLineAggregator;
	}

	protected void postprocess(Map context) {
		logger.info("Processing to copy success loaded feeds to file [" + this.successLoadedFeedsFilePath + "]");

		getJdbcTemplate().query("SELECT * FROM si_temp_insr_policy WHERE is_valid = 'Y'", new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				FileWriter os;
				try {
					os = new FileWriter(successLoadedFeedsFilePath);
				}
				catch (IOException ex) {
					IllegalStateException iex = new IllegalStateException("failed to create file ["
							+ successLoadedFeedsFilePath + "]");
					iex.initCause(ex);
					throw iex;
				}

				while (rs.next()) {
					List records = new ArrayList();
					records.add("R");
					records.add(rs.getString("APPLICATION_NUM"));
					records.add(rs.getString("FACILITY_CODE"));
					records.add(rs.getString("FACILITY_SEQ"));
					records.add(StringUtils.rightPad(rs.getString("COLLATERAL_NAME"), 40));
					records.add(rs.getString("COLLATERAL_SEQ"));
					records.add(rs.getString("ACCOUT_NUM"));
					records.add("");
					records.add("");
					records.add("");
					records.add("");
					records.add(StringUtils.rightPad(rs.getString("INSR_POLICY_NO"), 20));
					records.add("");
					records.add(rs.getString("INSURED_AMOUNT"));
					records.add(rs.getString("GROSS_PREMIUM_AMOUNT"));
					records.add(rs.getString("COMMISSION_AMOUNT"));
					records.add(rs.getString("REBATE_AMOUNT"));
					records.add(rs.getString("SERVICE_TAX_AMOUNT"));
					records.add(rs.getString("STAMP_DUTY_AMOUNT"));
					records.add(rs.getString("SERVICE_TAX_PERC"));
					records.add(rs.getString("NET_PREM_TO_BORROWER"));
					records.add(rs.getString("NET_PREM_TO_COMPANY"));
					records.add(DateFormatUtils.format(rs.getDate("INSR_EXPIRY_DATE"), "ddMMyyyy"));

					FieldSet fs = new DefaultFieldSet((String[]) records.toArray(new String[0]));
					String result = successLoadedFeedsLineAggregator.aggregate(fs);
					try {
						os.write(result);
						os.write(System.getProperty("line.separator"));
					}
					catch (IOException ex) {
						logger.error("failed to write result [" + result + "] into file [" + successLoadedFeedsFilePath
								+ "]", ex);
					}
				}

				try {
					os.close();
				}
				catch (IOException ex) {
					logger.error("failed to close file writer", ex);
				}
				return null;
			}
		});

		logger.info("Processing to copy fail loaded feeds to file [" + this.failLoadedFeedsFilePath + "]");

		getJdbcTemplate().query(
				"SELECT key1_value, key2_value, key3_value, key4_value, key5_value, error_code, error_msg "
						+ "FROM si_error_log error, si_temp_insr_policy tmp "
						+ "WHERE tmp.is_valid = 'N' and key1_value = tmp.collateral_name "
						+ "and key2_value = tmp.collateral_seq and error.time_stamp > (current_timestamp - 1 days)"
						+ "and system_id = 'UNIASIA' and interface_id = 'INSRPOL' ", new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						FileWriter os;
						try {
							os = new FileWriter(failLoadedFeedsFilePath);
						}
						catch (IOException ex) {
							IllegalStateException iex = new IllegalStateException("failed to create file ["
									+ failLoadedFeedsFilePath + "]");
							iex.initCause(ex);
							throw iex;
						}

						String header = failLoadedFeedsLineAggregator.aggregate(new DefaultFieldSet(new String[] {
								"Application No", "Facility code", "Fac seq no.", "Collateral Name", "Collateral No",
								"Error Code", "Error Msg" }));
						try {
							os.write(header);
							os.write(System.getProperty("line.separator"));
						}
						catch (IOException ex) {
							logger.error("failed to write header into file [" + failLoadedFeedsFilePath + "]", ex);
						}

						while (rs.next()) {
							List records = new ArrayList();
							records.add(rs.getString("key3_value"));
							records.add(rs.getString("key4_value"));
							records.add(rs.getString("key5_value"));
							records.add(rs.getString("key1_value"));
							records.add(rs.getString("key2_value"));
							records.add(rs.getString("error_code"));
							records.add(rs.getString("error_msg"));

							FieldSet fs = new DefaultFieldSet((String[]) records.toArray(new String[0]));
							String result = failLoadedFeedsLineAggregator.aggregate(fs);
							try {
								os.write(result);
								os.write(System.getProperty("line.separator"));
							}
							catch (IOException ex) {
								logger.error("failed to write result [" + result + "] into file ["
										+ failLoadedFeedsFilePath + "]", ex);
							}
						}

						try {
							os.close();
						}
						catch (IOException ex) {
							logger.error("failed to close file writer", ex);
						}
						return null;
					}
				});

		super.postprocess(context);
	}
}
