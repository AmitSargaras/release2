package com.integrosys.cms.batch.sibs.parameter;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.valuation.support.ValuationProfileSingletonListener;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.commoncode.bus.ICommonCodeDao;
import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryUtil;
import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.batch.factory.TransactionControlledBatchJob;
import com.integrosys.cms.batch.sibs.parameter.obj.ICommonCodeWrapper;
import com.integrosys.cms.batch.sibs.parameter.obj.ISynchronizer;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;

/**
 * Parameter Loader batch job to retrieve parameters from the host and
 * synchronize with local common code.
 * 
 * @author Cynthia Zhou
 * @author Chong Jun Yong
 * @author Andy Wong
 * @author Phoon Sai Heng
 * @since Sep 30, 2008
 */
public class ParameterBusManagerImpl implements IParameterBusManager, TransactionControlledBatchJob {

	private static final long serialVersionUID = -2911972483718217659L;

	public static final String ACTUAL_COMMON_CODE_ENTRY_WRAPPER = "actualEntryCodeWrapper";

	public static final String STAGE_COMMON_CODE_ENTRY_WRAPPER = "stageEntryCodeWrapper";

	public static final String ACTUAL_COMMON_CODE_ENTRY_WRAPPER_IGNORE_REF_ENTRY = "actualEntryCodeIgnoreRefEntryWrapper";

	public static final String STAGE_COMMON_CODE_ENTRY_WRAPPER_IGNORE_REF_ENTRY = "stageEntryCodeIgnoreRefEntryWrapper";

	public static final String ENTRY_SOURCE_SIBS = "ARBS";

    public static final String ENTRY_SOURCE_TSPR = "TSPR";

	private IParameterDao parameterDao;

	private ICommonCodeDao commonCodeDao;

	private int batchParameterFeedSize;

	private JdbcTemplate jdbcTemplate;

	private TransactionTemplate readTransactionTemplate;

	private TransactionTemplate transactionTemplate;

	private List tempTableList;

	private HashMap tempTableHashMap;

	private SingleParameterLoaderImpl singleParameterLoaderImpl;

	private ValuationProfileSingletonListener valuationProfileSingletonListener;

	// **************** Getter and Setter ******************
	public void setBatchParameterFeedSize(int batchParameterFeedSize) {
		this.batchParameterFeedSize = batchParameterFeedSize;
	}

	public int getBatchParameterFeedSize() {
		return batchParameterFeedSize;
	}

	public IParameterDao getParameterDao() {
		return parameterDao;
	}

	public void setParameterDao(IParameterDao parameterDao) {
		this.parameterDao = parameterDao;
	}

	public ICommonCodeDao getCommonCodeDao() {
		return commonCodeDao;
	}

	public void setCommonCodeDao(ICommonCodeDao commonCodeDao) {
		this.commonCodeDao = commonCodeDao;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		Validate.notNull(jdbcTemplate,
				"'jdbcTemplate' to be used for the calling on stored procedure must not be null.");
		Validate.notNull(jdbcTemplate.getDataSource(), "'dataSource' must not be null.");

		this.jdbcTemplate = jdbcTemplate;
	}

	public List getTempTableList() {
		return tempTableList;
	}

	public void setTempTableList(List tempTableList) {
		this.tempTableList = tempTableList;
	}

	public void setReadTransactionTemplate(TransactionTemplate readTransactionTemplate) {
		this.readTransactionTemplate = readTransactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void setSingleParameterLoaderImpl(SingleParameterLoaderImpl singleParameterLoaderImpl) {
		this.singleParameterLoaderImpl = singleParameterLoaderImpl;
	}

	public void setValuationProfileSingletonListener(ValuationProfileSingletonListener valuationProfileSingletonListener) {
		this.valuationProfileSingletonListener = valuationProfileSingletonListener;
	}

	// **************** BatchJob Method ******************
	public void execute(Map context) throws BatchJobException {
		tempTableHashMap = retrieveTempTableMap();
		if (context == null || context.isEmpty()) {
			updateParameter();
		}
		else {
			updateParameter(parseJobParameter(context));
		}

		try {
			DefaultLogger.debug(this, "Start refresh common code @@@@@@@@@@@@@@@@ ");
			CommonCodeEntryUtil.synchronizeCommonCode("*");
			DefaultLogger.debug(this, "Completed refresh common code @@@@@@@@@@@@@@@@ ");
			this.valuationProfileSingletonListener.reloadSingleton(new OBCollateralSubType(
					ICMSConstant.SECURITY_TYPE_ASSET, null));
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Failed to refresh all common code", e);
		}

	}

	private String[] parseJobParameter(Map jobParamMap) {
		Object obj = jobParamMap.get(IParameterBusManager.JOB_PARAM_KEY);
		if (obj instanceof String) {
			return new String[] { (String) obj };
		}
		else if (obj instanceof String[]) {
			return (String[]) obj;
		}
		else {
			return new String[0];
		}
	}

	// **************** Controller Main Methods ******************
	private Map getParameterPropertyList() {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
				IParameterBusManager.PROPERTY_FILE_NAME);
		return DomParser.parseParameterPropertyFile(inputStream);
	}

	private List getParameterPropertyRemoteEntityNameList() {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
				IParameterBusManager.PROPERTY_FILE_NAME);
		return DomParser.parseParameterFileForRemoteEntityName(inputStream);
	}

	public void updateParameter() {
		List remoteEntityNameList = getParameterPropertyRemoteEntityNameList();
		Map paramPropertyMap = getParameterPropertyList();

		if (remoteEntityNameList == null || remoteEntityNameList.isEmpty() || paramPropertyMap == null
				|| paramPropertyMap.isEmpty()) {
			DefaultLogger.error(this, "Parameter Property Config Is Not Correct!!!");
			return;
		}

		for (int i = 0; i < remoteEntityNameList.size(); i++) {
			String remoteEntity = (String) remoteEntityNameList.get(i);
			IParameterProperty parameterProperty = (IParameterProperty) paramPropertyMap.get(remoteEntity);

			if (parameterProperty != null) {
				if (this.singleParameterLoaderImpl != null
						&& this.singleParameterLoaderImpl.getRemoteEntityNameSingleParameterLoaderMap() != null
						&& this.singleParameterLoaderImpl.getRemoteEntityNameSingleParameterLoaderMap().containsKey(
								remoteEntity)
						&& this.singleParameterLoaderImpl.getRemoteEntityNameSingleParameterLoaderMap().get(
								remoteEntity) != null) {
					DefaultLogger.debug(this, "[Start] Remote Entity Name: [" + parameterProperty.getRemoteEntityName()
							+ "]\t Parameter Type: [" + parameterProperty.getType() + "]\t Local (Entity) Name: "
							+ parameterProperty.getLocalName());
					DefaultLogger.debug(this, " ^^^^^^^^ Inside Single Parameter Loader ^^^^^^^^ ");

					try {
						this.singleParameterLoaderImpl.setValue((Map) this.singleParameterLoaderImpl
								.getRemoteEntityNameSingleParameterLoaderMap().get(remoteEntity));
						this.singleParameterLoaderImpl.run();
					}
					catch (Exception e) {
						DefaultLogger.error(this, "**************************************** ");
						DefaultLogger.error(this, "Failed to update parameter for, param local ["
								+ parameterProperty.getLocalName() + "] remote ["
								+ parameterProperty.getRemoteEntityName() + "] ", e);
						continue;
					}
					finally {
						DefaultLogger.debug(this, "[End] Remote Entity Name: ["
								+ parameterProperty.getRemoteEntityName() + "]\t Parameter Type: ["
								+ parameterProperty.getType() + "]\t Local (Entity) Name: "
								+ parameterProperty.getLocalName());
					}
				}
				else {
					updateParameter(parameterProperty);

				}

				// Process external temp table
				processTempTable(parameterProperty);
			}
		}
	}

	public void updateParameter(String[] remoteEntityNameList) {
		Map paramPropertyMap = getParameterPropertyList();

		if (paramPropertyMap == null || paramPropertyMap.isEmpty()) {
			DefaultLogger.error(this, "Parameter Property Config Is Not Correct!!!");
			return;
		}

		for (int i = 0; i < remoteEntityNameList.length; i++) {
			IParameterProperty parameterProperty = (IParameterProperty) paramPropertyMap.get(remoteEntityNameList[i]);

			if (parameterProperty != null) {
				if (this.singleParameterLoaderImpl != null
						&& this.singleParameterLoaderImpl.getRemoteEntityNameSingleParameterLoaderMap() != null
						&& this.singleParameterLoaderImpl.getRemoteEntityNameSingleParameterLoaderMap().containsKey(
								remoteEntityNameList[i])
						&& this.singleParameterLoaderImpl.getRemoteEntityNameSingleParameterLoaderMap().get(
								remoteEntityNameList[i]) != null) {
					DefaultLogger.debug(this, "[Start] Remote Entity Name: [" + parameterProperty.getRemoteEntityName()
							+ "]\t Parameter Type: [" + parameterProperty.getType() + "]\t Local (Entity) Name: "
							+ parameterProperty.getLocalName());
					DefaultLogger.debug(this, " ^^^^^^^^ Inside Single Parameter Loader ^^^^^^^^ ");

					try {
						this.singleParameterLoaderImpl.setValue((Map) this.singleParameterLoaderImpl
								.getRemoteEntityNameSingleParameterLoaderMap().get(remoteEntityNameList[i]));
						this.singleParameterLoaderImpl.run();
					}
					catch (Exception e) {
						DefaultLogger.error(this, "**************************************** ");
						DefaultLogger.error(this, "Failed to update parameter for, param local ["
								+ parameterProperty.getLocalName() + "] remote ["
								+ parameterProperty.getRemoteEntityName() + "] ", e);
						continue;
					}
					finally {
						DefaultLogger.debug(this, "[End] Remote Entity Name: ["
								+ parameterProperty.getRemoteEntityName() + "]\t Parameter Type: ["
								+ parameterProperty.getType() + "]\t Local (Entity) Name: "
								+ parameterProperty.getLocalName());
					}
				}
				else {
					updateParameter(parameterProperty);
				}

				// Process external temp table
				processTempTable(parameterProperty);
			}
		}
	}

	public Collection updateParameter(final IParameterProperty paramProperty) {
		List remoteParameterList = null;
		List localCodeList = null;

		try {
			DefaultLogger.debug(this, "[Start] Remote Entity Name: [" + paramProperty.getRemoteEntityName()
					+ "]\t Parameter Type: [" + paramProperty.getType() + "]\t Local (Entity) Name: "
					+ paramProperty.getLocalName());

			remoteParameterList = (List) this.readTransactionTemplate.execute(new TransactionCallback() {

				public Object doInTransaction(TransactionStatus status) {
					return getParameterEntryByEntityName(paramProperty.getRemoteEntityName());
				}

			});

			if (remoteParameterList == null || remoteParameterList.isEmpty()) {
				DefaultLogger.warn(this, "***** Remote List is empty, stop processing ***** ");
				return null;
			}

			localCodeList = (List) this.readTransactionTemplate.execute(new TransactionCallback() {

				public Object doInTransaction(TransactionStatus status) {
					return (IParameterProperty.TYPE_COMMON_CODE.equals(paramProperty.getType())) ? getCommonCodeEntryByCategoryCode(
							paramProperty.getLocalName(), paramProperty.getRemoteEntityName())
							: getEntityEntryByEntityName(paramProperty.getLocalName());
				}

			});

			retrieveSetupInformation(paramProperty);

			if (!paramProperty.getIsDependencyUpdate()) { // normal handling
				handleDistinctListRequirements(paramProperty.getSpecialHandlingMap(), localCodeList);
				return updateParameter(paramProperty, remoteParameterList, localCodeList);
			}
			else {
				// dependency updating
				return updateDependency(paramProperty, remoteParameterList, localCodeList);
			}

		}
		catch (Exception e) {
			DefaultLogger.error(this, "**************************************** ");
			DefaultLogger.error(this, "Failed to update parameter for, param local [" + paramProperty.getLocalName()
					+ "] remote [" + paramProperty.getRemoteEntityName() + "], returning null", e);
			return null;
		}
		finally {
			DefaultLogger.debug(this, "[End] Remote Entity Name: [" + paramProperty.getRemoteEntityName()
					+ "]\t Parameter Type: [" + paramProperty.getType() + "]\t Local (Entity) Name: "
					+ paramProperty.getLocalName() + "\t Number of Remote Codes ["
					+ ((remoteParameterList != null) ? remoteParameterList.size() : 0) + "]\t Number of Local Codes ["
					+ ((localCodeList != null) ? localCodeList.size() : 0) + "]");

			if (remoteParameterList != null) {
				remoteParameterList.clear();
			}

			if (localCodeList != null) {
				localCodeList.clear();
			}
		}
	}

	// **************** Actual Methods doing the update ******************
	private Collection updateParameter(IParameterProperty paramProperty, List remoteList, List localList) {
		boolean fromLocalList = true;
		if ((localList == null || localList.isEmpty()) && (!(remoteList == null || remoteList.isEmpty()))) {
			fromLocalList = false;
		}

		DefaultLogger.debug(this, "The Class: ["
				+ ((fromLocalList) ? localList.get(0).getClass() : remoteList.get(0).getClass())
				+ "], is From Local List ? [" + fromLocalList + "]");

		String[] matchingProperties = (fromLocalList) ? ((ISynchronizer) localList.get(0)).getMatchingProperties()
				: ((ISynchronizer) remoteList.get(0)).getMatchingProperties();

		String[] ignoredProperties = (fromLocalList) ? ((ISynchronizer) localList.get(0)).getIgnoreProperties()
				: ((ISynchronizer) remoteList.get(0)).getIgnoreProperties();

		Collection createUpdateSet = EntityAssociationUtils.synchronizeCollectionsByProperties(localList, remoteList,
				matchingProperties, ignoredProperties);

		// perform any updates before saving
		Iterator it = createUpdateSet.iterator();

		while (it.hasNext()) {
			((ISynchronizer) it.next()).updatePropertiesForCreateUpdate(paramProperty);
		}

		Map specialHandleMap = paramProperty.getSpecialHandlingMap();
		if ((specialHandleMap == null)
				|| (specialHandleMap != null && !specialHandleMap
						.containsKey(IParameterBusManager.SPECIAL_HANDLE_NO_DELETE))) {
			Collection deleteSet = EntityAssociationUtils.retrieveRemovedObjectsCollection(localList, remoteList,
					matchingProperties);

			it = deleteSet.iterator();
			while (it.hasNext()) {
				((ISynchronizer) it.next()).updatePropertiesForDelete(paramProperty);
			}
			createUpdateSet.addAll(deleteSet);
		}

		return updateToDB(paramProperty, createUpdateSet);

	}

	private Collection updateDependency(IParameterProperty paramProperty, List remoteList, List localList) {
		boolean fromLocalList = true;
		if ((localList == null || localList.isEmpty()) && (!(remoteList == null || remoteList.isEmpty()))) {
			fromLocalList = false;
		}

		String[] matchingProperties = (fromLocalList) ? ((ISynchronizer) localList.get(0)).getMatchingProperties()
				: ((ISynchronizer) remoteList.get(0)).getMatchingProperties();

		String[] ignoredProperties = (fromLocalList) ? ((ISynchronizer) localList.get(0)).getIgnoreProperties()
				: ((ISynchronizer) remoteList.get(0)).getIgnoreProperties();

		// false: do not retrieve create set
		Collection updateSet = EntityAssociationUtils.synchronizeCollectionsByProperties(localList, remoteList,
				matchingProperties, ignoredProperties, false);

		// as of current requirements, there is no need to perform
		// "updatePropertiesForCreateUpdate" nor "updatePropertiesForDelete" -
		// should the need arise, can collaspe this method with updateParameter
		// method

		// perform any updates before updating
		Iterator it = updateSet.iterator();

		while (it.hasNext()) {
			((ISynchronizer) it.next()).updatePropertiesForCreateUpdate(paramProperty);
		}

		return updateToDB(paramProperty, updateSet);
	}

	// **************** Helper Methods (utility methods) ******************
	private List handleDistinctListRequirements(Map specialHandleMap, List parameterList) {

		DefaultLogger.debug(this, "specialHandleMap != null : " + (specialHandleMap != null));
		if (specialHandleMap != null && specialHandleMap.containsKey(IParameterBusManager.SPECIAL_HANDLE_DISTINCT)) {

			if (parameterList != null) {
				DefaultLogger.debug(this, "parameterList.size() BEGIN : " + parameterList.size());
				for (int i = parameterList.size() - 1; i >= 0; i--) {

					for (int j = i - 1; j >= 0; j--) {
						if (parameterList.get(i).equals(parameterList.get(j))) {
							DefaultLogger.debug(this, "parameterList.get(j) : " + parameterList.get(j));
							parameterList.remove(j);
						}
					}
				}

				DefaultLogger.debug(this, "parameterList.size() END : " + parameterList.size());
			}

		}
		return parameterList;
	}

	private void retrieveSetupInformation(IParameterProperty paramProperty) {
		if (IParameterProperty.TYPE_COMMON_CODE.equals(paramProperty.getType())) {
			long categoryID = getCommonCodeDao().getCategoryCodeId(ICommonCodeDao.ACTUAL_COMMON_CODE_CATEGORY,
					paramProperty.getLocalName());

			Map setupDetails = (paramProperty.getSetupDetailMap() == null) ? new HashMap() : paramProperty
					.getSetupDetailMap();
			setupDetails.put(ICommonCodeWrapper.KEY_CATEGORY_ID, new Long(categoryID));
			paramProperty.setSetupDetailMap(setupDetails);
		}
	}

	// **************** Helper Methods (relating to DAO) ******************
	private List getParameterEntryByEntityName(String entityName) {
		return getParameterDao().getParameterEntryByEntityName(entityName);
	}

	private List getEntityEntryByEntityName(String entityName) {
		return getCommonCodeDao().getEntityEntryByEntityName(entityName);
	}

	private List getCommonCodeEntryByCategoryCode(String categoryCode, String remoteEntityName) {
		String actualEntityName = ACTUAL_COMMON_CODE_ENTRY_WRAPPER;
		if (ICMSConstant.CATEGORY_CODE_ORG_GROUP.equals(categoryCode)
				|| CategoryCodeConstant.OFFICER_TYPE_LOS_MAP.equals(categoryCode)
				|| ICMSConstant.ORG_CODE.equals(categoryCode)
                || CategoryCodeConstant.RC_BRANCH.equals(categoryCode)
				|| CategoryCodeConstant.INSURANCE_TYPE.equals(categoryCode)) {
			// to use wrapper that ignore ther ref_entry_code
			actualEntityName = ACTUAL_COMMON_CODE_ENTRY_WRAPPER_IGNORE_REF_ENTRY;
		}

        String source = ENTRY_SOURCE_SIBS;

        if (remoteEntityName.equals("paramTSPRBaseRateType") ||
                remoteEntityName.equals("paramTSPRFacilityID") ||
                remoteEntityName.equals("paramTSPRLSMCode") ||
                remoteEntityName.equals("paramTSPRRBSPurposeCode") ||
                remoteEntityName.equals("paramTSPRLimitDescription") ||
                remoteEntityName.equals("paramTSPRProductType")) {
            source = ENTRY_SOURCE_TSPR;
        }

		return getCommonCodeDao().getCommonCodeEntryByCategorySource(actualEntityName, categoryCode, source,
				remoteEntityName);
	}

	private Collection updateToDB(final IParameterProperty paramProperty, Collection updateList) {
		final List returnList = new ArrayList();
		final List persistentList = new ArrayList(updateList);

		int fromIndex = 0;
		int toIndex = getBatchParameterFeedSize();

		if (IParameterProperty.TYPE_COMMON_CODE.equals(paramProperty.getType())) {

			final String actualEntityName = ACTUAL_COMMON_CODE_ENTRY_WRAPPER;

			DefaultLogger.debug(this, "****** Common Code ****** " + paramProperty.getLocalName());

			while (toIndex <= persistentList.size()) {
				final List persistentSubList = persistentList.subList(fromIndex, toIndex);
				List resultList = (List) this.transactionTemplate.execute(new TransactionCallback() {

					public Object doInTransaction(TransactionStatus status) {
						return getCommonCodeDao().update(actualEntityName, persistentSubList);
					}
				});

				returnList.add(resultList);

				fromIndex = toIndex;
				toIndex += getBatchParameterFeedSize();
			}

			if (fromIndex < persistentList.size()) {
				final List persistentSubList = persistentList.subList(fromIndex, persistentList.size());

				List resultList = (List) this.transactionTemplate.execute(new TransactionCallback() {

					public Object doInTransaction(TransactionStatus status) {
						return getCommonCodeDao().update(actualEntityName, persistentSubList);
					}
				});

				returnList.add(resultList);
			}

			return returnList;
		}
		else {
			DefaultLogger.debug(this, "****** Non Common Code ****** " + paramProperty.getLocalName());

			while (toIndex <= persistentList.size()) {
				final List persistentSubList = persistentList.subList(fromIndex, toIndex);

				this.transactionTemplate.execute(new TransactionCallback() {
					public Object doInTransaction(TransactionStatus status) {
						returnList.addAll(getCommonCodeDao().updateTable(paramProperty.getLocalName(),
								persistentSubList));
						return null;
					}
				});

				fromIndex = toIndex;
				toIndex += getBatchParameterFeedSize();
			}

			if (fromIndex < persistentList.size()) {
				final List persistentSubList = persistentList.subList(fromIndex, persistentList.size());
				this.transactionTemplate.execute(new TransactionCallback() {
					public Object doInTransaction(TransactionStatus status) {
						returnList.addAll(getCommonCodeDao().updateTable(paramProperty.getLocalName(),
								persistentSubList));
						return null;
					}
				});
			}

			return returnList;
		}
	}

	private HashMap retrieveTempTableMap() {
		HashMap tempTableMap = null;
		if (tempTableList != null && !tempTableList.isEmpty()) {
			// DefaultLogger.debug(this, "tempTableList.size() : " +
			// tempTableList.size());

			tempTableMap = new HashMap();
			for (int i = 0; i < tempTableList.size(); i++) {
				String currentTempStr = (String) tempTableList.get(i);
				DefaultLogger.debug(this, "currentTempStr : " + currentTempStr);

				String[] tempArray = StringUtils.split(currentTempStr, "|");
				// DefaultLogger.debug(this, "tempArray : " + tempArray[1]);

				tempTableMap.put((String) tempArray[0], (String[]) tempArray);
			}
		}
		// DefaultLogger.debug(this, "tempTableMap.size() : " +
		// tempTableMap.size());

		return tempTableMap;
	}

	private void processTempTable(IParameterProperty parameterProperty) {
		try {
			if (tempTableHashMap != null && tempTableHashMap.containsKey(parameterProperty.getLocalName())) {
				String[] strArray = (String[]) tempTableHashMap.get(parameterProperty.getLocalName());
				// DefaultLogger.debug(this, "strArray[0] : " + strArray[0]);
				// DefaultLogger.debug(this, "strArray[1] : " + strArray[1]);
				DefaultLogger.debug(this, "Procedure Name : " + strArray[2]);

				DefaultLogger.debug(this, "Process external temp table [ " + strArray[1] + " ]");
				doRunStoredProcedureForTempTable(strArray[1], strArray[2]);
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "**************************************** ");
			DefaultLogger.error(this, "Failed to update parameter for, param local ["
					+ parameterProperty.getLocalName() + "] remote [" + parameterProperty.getRemoteEntityName()
					+ "], returning null", e);
		}
	}

	protected void doRunStoredProcedureForTempTable(String tempTableName, String runProcedureName)
			throws IncompleteBatchJobException {
		if (tempTableName == null) {
			DefaultLogger.debug(this, "There is no temp table configured for this batch job, "
					+ "batch job will be skipped. ");
			return;
		}

		if (("NO_TEMP_TABLE").equals(tempTableName)) {
			DefaultLogger.debug(this, "*** Run Stored Procedure without temp table ***");

			try {
				getJdbcTemplate().execute("{call " + runProcedureName + "()}", new CallableStatementCallback() {

					public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
						cs.executeUpdate();
						return null;
					}

				});
			}
			catch (DataAccessException ex) {
				throw new IncompleteBatchJobException("failed to process actual run procedure [" + runProcedureName
						+ "]", ex);
			}
		}
		else {
			DefaultLogger.debug(this, "*** Run Stored Procedure with temp table ***");

			int count = getJdbcTemplate().queryForInt("select count(*) from " + tempTableName);
			DefaultLogger.debug(this, "Count for " + tempTableName + " is : " + count);

			if (count > 0) {

				try {
					getJdbcTemplate().execute("{call " + runProcedureName + "()}", new CallableStatementCallback() {

						public Object doInCallableStatement(CallableStatement cs) throws SQLException,
								DataAccessException {
							cs.executeUpdate();
							return null;
						}

					});
				}
				catch (DataAccessException ex) {
					throw new IncompleteBatchJobException("failed to process actual run procedure [" + runProcedureName
							+ "]", ex);
				}

				try {
					getJdbcTemplate().execute("{call UTIL_TRUNCATE_TABLE ('" + tempTableName + "')}",
							new CallableStatementCallback() {
								public Object doInCallableStatement(CallableStatement cs) throws SQLException,
										DataAccessException {
									cs.executeUpdate();
									return null;
								}

							});
				}
				catch (DataAccessException ex) {
					throw new IncompleteBatchJobException("failed to process UTIL_TRUNCATE_TABLE procedure "
							+ "for table [" + tempTableName + "]", ex);
				}
			}
			else {
				DefaultLogger.debug(this, "there is no item in the table [" + tempTableName + "], skip this batch job");
			}
		}
	}

}
