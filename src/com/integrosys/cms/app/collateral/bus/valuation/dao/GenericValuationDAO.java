package com.integrosys.cms.app.collateral.bus.valuation.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.dbsupport.ISequenceFormatter;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.OBValuation;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationDAO;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationDetailIncompleteException;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.GenericValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.support.ValuationFrequency;
import com.integrosys.cms.app.collateral.bus.valuation.support.ValuationUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class GenericValuationDAO extends JdbcDaoSupport implements IValuationDAO {

	/**
	 * Valution records select statements, ordering might be different for
	 * different source type. This is tally with CollateralDAO, which used by
	 * EBCollateralBean.
	 */
	private static final String SELECT_VALUATION_QUERY = "SELECT SOURCE_TYPE, VALUATION_DATE, VALUATION_CURRENCY, "
			+ "CMV, FSV, VALUER FROM CMS_VALUATION WHERE SOURCE_TYPE = ? AND CMS_COLLATERAL_ID = ? ";

	private ISequenceFormatter sequenceFormatter;

	/**
	 * @return the sequenceFormatter
	 */
	public ISequenceFormatter getSequenceFormatter() {
		return sequenceFormatter;
	}

	/**
	 * @param sequenceFormatter the sequenceFormatter to set
	 */
	public void setSequenceFormatter(ISequenceFormatter sequenceFormatter) {
		this.sequenceFormatter = sequenceFormatter;
	}

	public boolean retrieveValuationParams(final IValuationModel valModel, final List errorList)
			throws ValuationException {
		final List resultList = new ArrayList();

		StringBuffer queryBuf = new StringBuffer();
		queryBuf.append("SELECT THRESHOLD_PERCENT, VALUATION_FREQUENCY_UNIT, VALUATION_FREQUENCY ");
		queryBuf.append("FROM CMS_SECURITY_PARAMETER WHERE COUNTRY_ISO_CODE = ? AND SECURITY_SUB_TYPE_ID = ? ");

		getJdbcTemplate().query(queryBuf.toString(),
				new Object[] { valModel.getSecurityCountry(), valModel.getSecSubtype() }, new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							// String thresholdPerc =
							// rs.getString("THRESHOLD_PERCENT");
							double threshold = rs.getDouble("THRESHOLD_PERCENT");
							String valFreqUnit = rs.getString("VALUATION_FREQUENCY_UNIT");
							String valFreq = rs.getString("VALUATION_FREQUENCY");

							if ((valFreqUnit != null) && (valFreq != null)) {
								ValuationFrequency freq = new ValuationFrequency();
								freq.setFreq(Integer.parseInt(valFreq));
								freq.setFreqUnit(valFreqUnit);
								valModel.setValuationFrequency(freq);
							}
							else {
								errorList.add("valuation frequency is not defined");
								resultList.add(new Object());
							}

							ValuationUtil.determineMargin(valModel, threshold);
						}

						return null;
					}

				});

		return resultList.isEmpty();
	}

	/**
	 * Case 1 where there is at least 1 previous system valuation for this
	 * collateral then the valuation date = latest system valuation's valuation
	 * date + valuation frequency
	 * <p/>
	 * Case 2 where there is no previous system valuation, we will retrieve the
	 * latest los valuation or manual valuation, if there is manual valuation
	 * valuation date = manual valuation date + valuation frequency or if there
	 * is los valuation, valuation date = los valuation date + valuation
	 * frequency
	 */
	public boolean retrieveValuationDate(IValuationModel valModel, List errorList) throws ValuationException {
		StringBuffer queryBuf = new StringBuffer();
		queryBuf.append("SELECT SOURCE_TYPE, MAX(VALUATION_DATE) AS LAST_VAL_DATE, MAX(UPDATE_DATE) AS UPDATE_DATE ");
		queryBuf.append("FROM CMS_VALUATION WHERE CMS_COLLATERAL_ID = ? ");
		queryBuf.append("GROUP BY SOURCE_TYPE");

		Map resultMap = (Map) getJdbcTemplate().query(queryBuf.toString(),
				new Object[] { new Long(valModel.getCollateralId()) }, new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						Map resultMap = new HashMap();
						while (rs.next()) {
							String valType = rs.getString("SOURCE_TYPE");
							Date lastValDate = rs.getDate("LAST_VAL_DATE");

							if (lastValDate != null) {
								resultMap.put(valType, lastValDate);
							}

						}
						return resultMap;
					}
				});

		String[] valuationType = new String[] { ICMSConstant.VALUATION_SOURCE_TYPE_A,
				ICMSConstant.VALUATION_SOURCE_TYPE_M, ICMSConstant.VALUATION_SOURCE_TYPE_S };
		for (int i = 0; i < valuationType.length; i++) {
			if (resultMap.containsKey(valuationType[i])) {
				Date d = (Date) resultMap.get(valuationType[i]);
				valModel.setPrevValuationDate(d);
				valModel.setValuationDate(ValuationUtil.getNextValuationDate(d, valModel.getValuationFrequency()));

				return true;
			}
		}

		errorList.add("no previous valuation date can be derived for collateral id [" + valModel.getCollateralId()
				+ "]");

		resultMap.clear();

		return false;

	}

	public void persistValuation(IValuationModel valModel) throws ValuationException {
		String query = "INSERT INTO CMS_VALUATION(VALUATION_ID, VALUATION_CURRENCY, VALUATION_DATE, CMV, FSV, REVAL_FREQ, "
				+ "REVAL_FREQ_UNIT, CMS_COLLATERAL_ID, SOURCE_TYPE, SOURCE_ID) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		// For DB2 
		/*long valuationSequence = getJdbcTemplate()
				.queryForLong("VALUES nextval FOR " + ICMSConstant.SEQUENCE_VALUATION);*/
		//For Oracle
		long valuationSequence = getJdbcTemplate()
			.queryForLong("SELECT " + ICMSConstant.SEQUENCE_VALUATION+".NEXVAL FROM DUAL");

		String valuationId = null;
		try {
			valuationId = sequenceFormatter.formatSeq(String.valueOf(valuationSequence));
		}
		catch (Throwable t) {
			throw new ValuationDetailIncompleteException("not able to get valuation sequence for collateral id ["
					+ valModel.getCollateralId() + "]", t);
		}

		List argList = new ArrayList();
		argList.add(Long.valueOf(valuationId));
		argList.add(valModel.getValOMV().getCurrencyCode());
		argList.add(new java.sql.Date(valModel.getValuationDate().getTime()));
		argList.add(new Double(valModel.getValOMV().getAmount()));
		argList.add(new Double(valModel.getValFSV().getAmount()));
		argList.add(new Integer(valModel.getValuationFrequency().getFreq()));
		argList.add(valModel.getValuationFrequency().getFreqUnit());
		argList.add(new Long(valModel.getCollateralId()));
		argList.add(ICMSConstant.VALUATION_SOURCE_TYPE_A);
		argList.add(ICMSConstant.SOURCE_SYSTEM_CMS);

		getJdbcTemplate().update(query, argList.toArray());
	}

	public Map retrievePrevValuationInfo(final long collateralId, String currentValType) throws ValuationException {
		List argList = new ArrayList();
		StringBuffer buf = new StringBuffer();

		if (!ICMSConstant.VALUATION_SOURCE_TYPE_A.equals(currentValType)) {
//			For Db2
			/*StringBuffer sysBuf = new StringBuffer(SELECT_VALUATION_QUERY);
			sysBuf.append("ORDER BY VALUATION_ID DESC FETCH FIRST 1 ROWS ONLY ");*/
//			For Oracle
			StringBuffer sysBuf = new StringBuffer("SELECT * FROM ( ");
			sysBuf.append(SELECT_VALUATION_QUERY);
			sysBuf.append("ORDER BY VALUATION_ID DESC ");
			sysBuf.append(" )FROM TEMP WHERE ROWNUM<=1");

			buf.append("(");
			buf.append(sysBuf.toString());
			buf.append(")");
			argList.add(ICMSConstant.VALUATION_SOURCE_TYPE_A);
			argList.add(new Long(collateralId));
		}

		if (!ICMSConstant.VALUATION_SOURCE_TYPE_S.equals(currentValType)) {
			//For DB2
			/*StringBuffer losBuf = new StringBuffer(SELECT_VALUATION_QUERY);
			losBuf.append("ORDER BY VALUATION_DATE DESC, LOS_VALUATION_ID ASC FETCH FIRST 1 ROWS ONLY ");*/
			//For Oracle
			StringBuffer losBuf= new StringBuffer("SELECT * FROM ( ");
			losBuf.append(SELECT_VALUATION_QUERY);
			losBuf.append("ORDER BY VALUATION_DATE DESC, LOS_VALUATION_ID ASC ");
			losBuf.append(" )FROM TEMP WHERE ROWNUM<=1");

			if (buf.length() > 0) {
				buf.append(" UNION ALL ");
			}
			buf.append("(");
			buf.append(losBuf.toString());
			buf.append(")");
			argList.add(ICMSConstant.VALUATION_SOURCE_TYPE_S);
			argList.add(new Long(collateralId));
		}

		if (!ICMSConstant.VALUATION_SOURCE_TYPE_M.equals(currentValType)) {
//			For Db2
			/*StringBuffer manualBuf = new StringBuffer(SELECT_VALUATION_QUERY);
			manualBuf.append("ORDER BY VALUATION_ID DESC FETCH FIRST 1 ROWS ONLY ");*/
//			For Oracle
			StringBuffer manualBuf = new StringBuffer("SELECT * FROM ( ");
			manualBuf.append(SELECT_VALUATION_QUERY);
			manualBuf.append("ORDER BY VALUATION_ID DESC ");
			manualBuf.append(" )FROM TEMP WHERE ROWNUM<=1");

			if (buf.length() > 0) {
				buf.append(" UNION ALL ");
			}
			buf.append("(");
			buf.append(manualBuf.toString());
			buf.append(")");
			argList.add(ICMSConstant.VALUATION_SOURCE_TYPE_M);
			argList.add(new Long(collateralId));
		}

		return (Map) getJdbcTemplate().query(buf.toString(), argList.toArray(), new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map tempMap = new HashMap();
				while (rs.next()) {
					String valType = rs.getString("SOURCE_TYPE");
					Date valDate = rs.getDate("VALUATION_DATE");
					String valCurrency = rs.getString("VALUATION_CURRENCY");
					double cmv = rs.getDouble("CMV");
					String fsvStr = rs.getString("FSV");
					double fsv = rs.getDouble("FSV");
					String valuer = rs.getString("VALUER");
					IValuationModel valModel = new GenericValuationModel();
					valModel.setCollateralId(collateralId);
					valModel.setValOMV(new Amount(cmv, valCurrency));
					if (fsvStr != null && ("").equals(fsvStr)) {
						valModel.setValFSV(new Amount(fsv, valCurrency));
					}
					valModel.setValuationMargin((cmv <= 0) ? 1 : fsv / cmv * 100);
					valModel.setValuationDate(valDate);
					valModel.setValuationType(valType);
					valModel.setValuer(valuer);
					tempMap.put(valType, valModel);
				}

				return tempMap;

			}
		});
	}

	public void persistFsvCmv(IValuationModel valModel) throws ValuationException {
		String query = "UPDATE CMS_SECURITY SET CMV = ?, CMV_CURRENCY = ?, FSV = ?, FSV_CURRENCY = ?, LAST_REMARGIN_DATE = ?, "
				+ "NEXT_REMARGIN_DATE = ?, VALUATION_TYPE = ?, VALUER = ? WHERE CMS_COLLATERAL_ID = ?";

		List argList = new ArrayList();
		argList.add(new Double(valModel.getValOMV().getAmount()));
		argList.add(valModel.getValOMV().getCurrencyCode());
		argList.add(new Double(valModel.getValFSV().getAmount()));
		argList.add(valModel.getValFSV().getCurrencyCode());
		argList.add(valModel.getValuationDate());
		argList.add(valModel.getNextValuationDate());
		argList.add(valModel.getValuationType());
		argList.add(valModel.getValuer());
		argList.add(new Long(valModel.getCollateralId()));

		getJdbcTemplate().update(query, argList.toArray());

		query = "UPDATE CMS_STAGE_SECURITY SET CMV = ?, CMV_CURRENCY = ?, FSV = ?, FSV_CURRENCY = ?, LAST_REMARGIN_DATE = ?, "
				+ "NEXT_REMARGIN_DATE = ?, VALUATION_TYPE = ?, VALUER = ? WHERE CMS_COLLATERAL_ID = (SELECT staging_reference_id "
				+ "FROM transaction WHERE reference_id = ? AND transaction_type = ?)";

		argList.add(ICMSConstant.INSTANCE_COLLATERAL);

		getJdbcTemplate().update(query, argList.toArray());
	}

	/**
	 * @deprecated was only used by PropertyValuationDAO - please use
	 *             retrievePrevValuationInfo instead.
	 * @param collateralId
	 * @param sourceType
	 * @return
	 * @throws ValuationException
	 */
	public IValuation retrieveLatestValuationByCollateralIdAndSourceType(long collateralId, String sourceType)
			throws ValuationException {
		String query = "SELECT valuation_id, valuation_date, valuation_currency, cmv, fsv, SOURCE_TYPE, source_id \n"
				+ "  FROM cms_valuation \n" + " WHERE cms_collateral_id = ? \n"
				+ "   AND valuation_date IS NOT NULL \n";

		List argList = new ArrayList();
		argList.add(new Long(collateralId));

		if (sourceType != null) {
			query += " AND source_type = ? ";
			argList.add(sourceType);
		}
		query += " ORDER BY valuation_date ";

		return (IValuation) getJdbcTemplate().query(query, argList.toArray(), new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				HashMap valMap = new HashMap();
				while (rs.next()) {
					IValuation val = new OBValuation();
					val.setValuationID(rs.getLong("valuation_id"));
					val.setValuationDate(rs.getDate("valuation_date"));
					val.setCMV(new Amount(rs.getDouble("cmv"), rs.getString("valuation_currency")));
					val.setFSV(new Amount(rs.getDouble("fsv"), rs.getString("valuation_currency")));
					val.setSourceId(rs.getString("source_id"));
					val.setSourceType(rs.getString("SOURCE_TYPE"));
					valMap.put(val.getSourceType(), val);
				}
				// System valuation has the priority first, if not found, then
				// take the valuation from source
				IValuation foundVal = (IValuation) valMap.get(ICMSConstant.VALUATION_SOURCE_TYPE_A);
				if (foundVal == null) {
					foundVal = (IValuation) valMap.get(ICMSConstant.VALUATION_SOURCE_TYPE_S);
				}
				return foundVal;
			}
		});
	}

	public void retrieveValuationInfoForCal(IValuationModel valModel) throws ValuationException {
	}

	public void persistOtherInfo(IValuationModel valModel) throws ValuationException {
	}

}
