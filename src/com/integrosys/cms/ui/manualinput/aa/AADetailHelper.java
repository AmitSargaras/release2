/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.excLineforstpsrm.bus.IExcLineForSTPSRMJdbc;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: Helper for AA Detail Description: Help the
 * other to pull or get data from system
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name$
 */

public class AADetailHelper {

	public static final String TIME_FREQUENCY_CODE = ICMSUIConstant.TIME_FREQ;

	static final public HashMap buildTimeFrequencyMap() {
		return buildCommonCodeMap(ICMSUIConstant.TIME_FREQ);
	}

	static final private HashMap buildCommonCodeMap(String categoryCode) {
		Collection codeCategoryLabelsCol = CommonDataSingleton.getCodeCategoryLabels(categoryCode);
		Collection codeCategoryValeusCol = CommonDataSingleton.getCodeCategoryValues(categoryCode);
		String[] codeCategoryLabels = new String[codeCategoryLabelsCol.size()];
		codeCategoryLabels = (String[]) codeCategoryLabelsCol.toArray(codeCategoryLabels);
		String[] codeCategoryValues = new String[codeCategoryValeusCol.size()];
		codeCategoryValues = (String[]) codeCategoryValeusCol.toArray(codeCategoryValues);

		HashMap map = new HashMap();
		for (int i = 0; i < codeCategoryValues.length; i++) {
			map.put(codeCategoryValues[i], codeCategoryLabels[i]);
		}
		return map;
	}
	
	public static boolean isFacilityOrLineActive(long limitProfileId) throws Exception {
		DBUtil dbUtil = null;
		ResultSet rs = null;
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String facilitySystem = "'" + bundle.getString("fcubs.systemName") + "','" + bundle.getString("ubs.systemName")
				+ "'";

		String sql = "select count(1)  from transaction where REFERENCE_ID IN (select CMS_LSP_APPR_LMTS_ID from sci_lsp_appr_lmts "
				+ " where facility_system in (" + facilitySystem + ") and cms_limit_profile_id='" + limitProfileId
				+ "') and status not in ('ACTIVE','DELETED','CLOSED')";

		try {
			dbUtil = new DBUtil();
			int isActive = 0;

			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();

			if (rs.next())
				isActive = rs.getInt(1);
			if (isActive == 0) {
				String sql1 = "  select count(1)  from transaction where STAGING_REFERENCE_ID IN (select CMS_LSP_APPR_LMTS_ID from stage_limit "
						+ " where facility_system in (" + facilitySystem + ") and cms_limit_profile_id='"
						+ limitProfileId + "') and status not in ('ACTIVE','DELETED','CLOSED')";

				int stageCount = 0;
				dbUtil.setSQL(sql1);
				rs = dbUtil.executeQuery();

				if (rs.next()) {
					stageCount = rs.getInt(1);
					if (stageCount > 0) {
						return false;
					} else {

						String sql2 = " select count(1) from SCI_LSP_SYS_XREF where status='PENDING'  and facility_system in ("
								+ facilitySystem + ")"
								+ " and CMS_LSP_SYS_XREF_ID IN (SELECT map.cms_lsp_sys_xref_id  FROM sci_lsp_lmts_xref_map map WHERE map.cms_lsp_appr_lmts_id IN ("
								+ " SELECT facility.cms_lsp_appr_lmts_id FROM sci_lsp_appr_lmts facility  WHERE facility.facility_system in ("
								+ facilitySystem
								+ ") and facility.CMS_LIMIT_STATUS='ACTIVE' and  facility.cms_limit_profile_id = '"
								+ limitProfileId + "'))";

						int pendingCount = 0;
						dbUtil.setSQL(sql2);
						rs = dbUtil.executeQuery();

						if (rs.next()) {
							pendingCount = rs.getInt(1);
							if (pendingCount > 0) {
								return false;
							} else {
								return true;
							}
						}
						return true;

					}
				}
				return true;
			} else
				return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (null != dbUtil)
				dbUtil.close();
		}
	}
	
	public static void syncExtendedNextReviewDateToLine(long limitProfileId, Date newExtendedNextReviewDate) throws Exception {
		IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
		IGeneralParamGroup generalParamGroup = generalParamDao
				.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
		IGeneralParamEntry[] generalParamEntries = generalParamGroup.getFeedEntries();

		try {
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy lmtProxy = helper.getSBMILmtProxy();
			ILimitDAO dao1 = LimitDAOFactory.getDAO();

			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String facilitySystem = "'" + bundle.getString("fcubs.systemName") + "','"
					+ bundle.getString("ubs.systemName") + "'";
			List<String> lmtIdList = dao1.getlmtId(limitProfileId, facilitySystem, null);

			Date d = DateUtil.getDate();
			String dateFormat = "yyMMdd";
			SimpleDateFormat s = new SimpleDateFormat(dateFormat);
			String date = s.format(d);

			Date appDate = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
			for (int i = 0; i < generalParamEntries.length; i++) {
				if (generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")) {
					System.out.println(
							"generalParamEntries[i].getParamValue():" + generalParamEntries[i].getParamValue());
					appDate = new Date(generalParamEntries[i].getParamValue());
				}
			}

			Map<String, String> availableActual = new HashMap<String, String>();
			Map<String, String> expiryActual = new HashMap<String, String>();
			Map<String, String> availableExpiryActual = new HashMap<String, String>();

			for (int k = 0; k < lmtIdList.size(); k++) {
				ILimitTrxValue lmtTrxObj = lmtProxy.searchLimitByLmtId(lmtIdList.get(k));
				getMapLineDetails(lmtTrxObj, dao1, date, appDate, availableActual, expiryActual, availableExpiryActual);
			}

			if (availableActual.size() > 0) {
				dao1.updateStageActualLine("Yes", "PENDING", newExtendedNextReviewDate, availableActual,
						"availableActual");
			}
			if (expiryActual.size() > 0) {
				dao1.updateStageActualLine("", "PENDING", newExtendedNextReviewDate, expiryActual, "expiryActual");
			}
			if (availableExpiryActual.size() > 0) {
				dao1.updateStageActualLine("Yes", "PENDING", newExtendedNextReviewDate, availableExpiryActual,
						"availableExpiryActual");
			}

			// for PSR limit
			Map<String, String> expiryActualForPSR = new HashMap<String, String>();
			String facilitySystemForPSR = "'" + bundle.getString("psr.systemName") + "'";
			List<String> psrlmtIdList = dao1.getPSRlmtId(limitProfileId, facilitySystemForPSR);

			for (int p = 0; p < psrlmtIdList.size(); p++) {
				ILimitTrxValue lmtTrxObj = lmtProxy.searchLimitByLmtId(psrlmtIdList.get(p));
				getMapPSRLineDetails(lmtTrxObj, dao1, date, appDate, expiryActualForPSR);
			}

			if (expiryActualForPSR.size() > 0) {
				dao1.updatePSRStageActualLine("", "PENDING", newExtendedNextReviewDate, expiryActualForPSR,
						"expiryActualForPSR");
			}
			// end PSR limit

		} catch (Exception e) {
			throw e;
		}
	}

	private static String split(String lineNumbers) {
		String[] array = lineNumbers.split(",");

		lineNumbers = " ";
		for (int i = 0; i < array.length; i++) {
			if (i == (array.length - 1)) {
				lineNumbers = lineNumbers + "facility.line_no NOT LIKE '%" + array[i] + "%'";
			} else {
				lineNumbers = lineNumbers + "facility.line_no NOT LIKE '%" + array[i] + "%' and ";
			}
		}

		return lineNumbers;
	}

	private static void getMapPSRLineDetails(ILimitTrxValue lmtTrxObj, ILimitDAO dao1, String date, Date appDate,
			Map<String, String> expiryActualForPSR) {

		ILimit limit = lmtTrxObj.getLimit();
		ILimit stagingLimit = lmtTrxObj.getStagingLimit();

		if (limit != null && limit.getLimitSysXRefs() != null) {

			ILimitSysXRef[] limitSysXRefsStage = stagingLimit.getLimitSysXRefs();
			int StageXreflength = limitSysXRefsStage.length;
			ILimitSysXRef[] limitSysXRefs = limit.getLimitSysXRefs();

			for (int i = 0; i < limitSysXRefs.length; i++) {
				ILimitSysXRef iLimitSysXRef = limitSysXRefs[i];
				ICustomerSysXRef customerSysXRef = iLimitSysXRef.getCustomerSysXRef();
				String releasedAmount = customerSysXRef.getReleasedAmount();
				String status = customerSysXRef.getStatus();

				if (null != releasedAmount && !"0".equals(releasedAmount) && null != status
						&& !"HIDE".equalsIgnoreCase(status)) {
					if (null == customerSysXRef.getCloseFlag() || "N".equals(customerSysXRef.getCloseFlag())) {
						boolean expired = false;
						if (null != customerSysXRef.getDateOfReset() && null != appDate) {
							if (customerSysXRef.getDateOfReset().compareTo(appDate) < 0
									|| customerSysXRef.getDateOfReset().compareTo(appDate) == 0)
								expired = true;
						} else if (null == customerSysXRef.getDateOfReset()) {
							expired = true;

						}

						if ("N".equals(limit.getIsAdhoc()) && (null == customerSysXRef.getAvailable()
								|| "Yes".equals(customerSysXRef.getAvailable())) && !expired) {

							String sorceRefNo = generateSourceNo(dao1, date);
							if (ICMSConstant.PSR_STATUS_REJECTED.equals(customerSysXRef.getStatus())) {
								for (int k = 0; k < StageXreflength; k++) {
									if (iLimitSysXRef.getSID() == limitSysXRefsStage[k].getSID()) {
										expiryActualForPSR.put(String.valueOf(customerSysXRef.getXRefID()),
												String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())
														+ "," + sorceRefNo + "," + customerSysXRef.getAction());
										break;
									}
								}
							} else {
								for (int k = 0; k < StageXreflength; k++) {
									if (iLimitSysXRef.getSID() == limitSysXRefsStage[k].getSID()) {
										expiryActualForPSR.put(String.valueOf(customerSysXRef.getXRefID()),
												String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())
														+ "," + sorceRefNo + "," + ICMSConstant.PSRLIMIT_ACTION_MODIFY);
										break;
									}
								}
							}
						} else if ("N".equals(limit.getIsAdhoc()) && (null == customerSysXRef.getAvailable()
								|| "Yes".equals(customerSysXRef.getAvailable())) && expired) {

							String sorceRefNo = generateSourceNo(dao1, date);
							if (ICMSConstant.PSR_STATUS_REJECTED.equals(customerSysXRef.getStatus())) {
								for (int k = 0; k < StageXreflength; k++) {
									if (iLimitSysXRef.getSID() == limitSysXRefsStage[k].getSID()) {
										expiryActualForPSR.put(String.valueOf(customerSysXRef.getXRefID()),
												String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())
														+ "," + sorceRefNo + "," + customerSysXRef.getAction());
										break;
									}
								}
							} else {
								for (int k = 0; k < StageXreflength; k++) {
									if (iLimitSysXRef.getSID() == limitSysXRefsStage[k].getSID()) {
										expiryActualForPSR.put(String.valueOf(customerSysXRef.getXRefID()),
												String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())
														+ "," + sorceRefNo + "," + ICMSConstant.PSRLIMIT_ACTION_MODIFY);
										break;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private static void getMapLineDetails(ILimitTrxValue lmtTrxObj, ILimitDAO dao1, String date, Date appDate,
			Map<String, String> availableActual, Map<String, String> expiryActual,
			Map<String, String> availableExpiryActual) {
		ILimit limit = lmtTrxObj.getLimit();
		ILimit stagingLimit = lmtTrxObj.getStagingLimit();
		if (limit != null && limit.getLimitSysXRefs() != null) {
			
			boolean isSblcSecGtZero = false;
			boolean isSblc = false;
			IExcLineForSTPSRMJdbc excLineForSTPSRMJdbc = (IExcLineForSTPSRMJdbc) BeanHouse.get("excLineForSTPSRMJdbc");
			boolean isExcluded = excLineForSTPSRMJdbc.isExcluded(limit.getLineNo(), true);
			
			if(limit.getCollateralAllocations() != null && limit.getCollateralAllocations().length > 0) {
				for(int i = 0; i <limit.getCollateralAllocations().length; i++) {
					if(isSblcSecGtZero)
						break;
					
					if(ICMSConstant.HOST_STATUS_DELETE.equals(limit.getCollateralAllocations()[i].getHostStatus())) {
						continue;
					}
					
					ICollateral collateral = limit.getCollateralAllocations()[i].getCollateral();
					String subType = collateral.getCollateralSubType().getSubTypeCode();
					if(ICMSConstant.COLTYPE_GUARANTEE_SBLC_DIFFCCY.equals(subType) || 
							ICMSConstant.COLTYPE_GUARANTEE_SBLC_SAMECCY.equals(subType)) {
						isSblc = true;
						if(collateral.getCMV() != null && collateral.getCMV().getAmountAsBigDecimal().compareTo(BigDecimal.ZERO) > 0) {
							isSblcSecGtZero = true;
						}
					}
				}
			}
			
			ILimitSysXRef[] limitSysXRefsStage = stagingLimit.getLimitSysXRefs();
			int StageXreflength = limitSysXRefsStage.length;
			ILimitSysXRef[] limitSysXRefs = limit.getLimitSysXRefs();
			for (int i = 0; i < limitSysXRefs.length; i++) {
				ILimitSysXRef iLimitSysXRef = limitSysXRefs[i];
				ICustomerSysXRef customerSysXRef = iLimitSysXRef.getCustomerSysXRef();
				String releasedAmount = customerSysXRef.getReleasedAmount();
				String status = customerSysXRef.getStatus();
				if (null != releasedAmount && !"0".equals(releasedAmount) && null != status
						&& !"HIDE".equalsIgnoreCase(status)) {
					if (null == customerSysXRef.getCloseFlag() || "N".equals(customerSysXRef.getCloseFlag())) {
						boolean expired = false;
						if (null != customerSysXRef.getDateOfReset() && null != appDate) {
							// SimpleDateFormat sdf=new SimpleDateFormat("dd/MMM/yyyy");
							// sdf.parse(DateUtil.formatDate(aLocale, customerSysXRef.getDateOfReset())
							if (customerSysXRef.getDateOfReset().compareTo(appDate) < 0
									|| customerSysXRef.getDateOfReset().compareTo(appDate) == 0)
								expired = true;
						} else if (null == customerSysXRef.getDateOfReset()) {
							expired = true;

						}
						if(isExcluded || (isSblc && isSblcSecGtZero)) {
							continue;
						}else if ("Y".equals(limit.getIsAdhoc()) && "No".equals(customerSysXRef.getAvailable())) {

							String sorceRefNo = generateSourceNo(dao1, date);
							if (ICMSConstant.FCUBS_STATUS_REJECTED.equals(customerSysXRef.getStatus())) {
								for (int k = 0; k < StageXreflength; k++) {
									if (iLimitSysXRef.getSID() == limitSysXRefsStage[k].getSID()) {
										availableActual.put(String.valueOf(customerSysXRef.getXRefID()),
												String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())
														+ "," + sorceRefNo + "," + customerSysXRef.getAction());
										break;
									}
								}
							} else {
								for (int k = 0; k < StageXreflength; k++) {
									if (iLimitSysXRef.getSID() == limitSysXRefsStage[k].getSID()) {
										availableActual.put(String.valueOf(customerSysXRef.getXRefID()),
												String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())
														+ "," + sorceRefNo + ","
														+ ICMSConstant.FCUBSLIMIT_ACTION_MODIFY);

										break;
									}
								}
							}
						} else if ("N".equals(limit.getIsAdhoc()) && "No".equals(customerSysXRef.getAvailable())
								&& expired) {

							String sorceRefNo = generateSourceNo(dao1, date);
							if (ICMSConstant.FCUBS_STATUS_REJECTED.equals(customerSysXRef.getStatus())) {
								for (int k = 0; k < StageXreflength; k++) {
									if (iLimitSysXRef.getSID() == limitSysXRefsStage[k].getSID()) {
										availableExpiryActual.put(String.valueOf(customerSysXRef.getXRefID()),
												String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())
														+ "," + sorceRefNo + "," + customerSysXRef.getAction());
										break;
									}
								}
							} else {
								for (int k = 0; k < StageXreflength; k++) {
									if (iLimitSysXRef.getSID() == limitSysXRefsStage[k].getSID()) {
										availableExpiryActual.put(String.valueOf(customerSysXRef.getXRefID()),
												String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())
														+ "," + sorceRefNo + ","
														+ ICMSConstant.FCUBSLIMIT_ACTION_MODIFY);
										break;
									}
								}
							}
						} else if ("N".equals(limit.getIsAdhoc()) && "No".equals(customerSysXRef.getAvailable())
								&& !expired) {

							String sorceRefNo = generateSourceNo(dao1, date);
							if (ICMSConstant.FCUBS_STATUS_REJECTED.equals(customerSysXRef.getStatus())) {
								for (int k = 0; k < StageXreflength; k++) {
									if (iLimitSysXRef.getSID() == limitSysXRefsStage[k].getSID()) {
										availableActual.put(String.valueOf(customerSysXRef.getXRefID()),
												String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())
														+ "," + sorceRefNo + "," + customerSysXRef.getAction());
										break;
									}
								}
							} else {
								for (int k = 0; k < StageXreflength; k++) {
									if (iLimitSysXRef.getSID() == limitSysXRefsStage[k].getSID()) {
										availableActual.put(String.valueOf(customerSysXRef.getXRefID()),
												String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())
														+ "," + sorceRefNo + ","
														+ ICMSConstant.FCUBSLIMIT_ACTION_MODIFY);
										break;
									}
								}
							}
						} else if ("N".equals(limit.getIsAdhoc()) && (null == customerSysXRef.getAvailable()
								|| "Yes".equals(customerSysXRef.getAvailable())) && !expired) {

							String sorceRefNo = generateSourceNo(dao1, date);
							if (ICMSConstant.FCUBS_STATUS_REJECTED.equals(customerSysXRef.getStatus())) {
								for (int k = 0; k < StageXreflength; k++) {
									if (iLimitSysXRef.getSID() == limitSysXRefsStage[k].getSID()) {
										expiryActual.put(String.valueOf(customerSysXRef.getXRefID()),
												String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())
														+ "," + sorceRefNo + "," + customerSysXRef.getAction());
										break;
									}
								}
							} else {
								for (int k = 0; k < StageXreflength; k++) {
									if (iLimitSysXRef.getSID() == limitSysXRefsStage[k].getSID()) {
										expiryActual.put(String.valueOf(customerSysXRef.getXRefID()),
												String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())
														+ "," + sorceRefNo + ","
														+ ICMSConstant.FCUBSLIMIT_ACTION_MODIFY);
										break;
									}
								}
							}
						} else if ("N".equals(limit.getIsAdhoc()) && (null == customerSysXRef.getAvailable()
								|| "Yes".equals(customerSysXRef.getAvailable())) && expired) {

							String sorceRefNo = generateSourceNo(dao1, date);
							if (ICMSConstant.FCUBS_STATUS_REJECTED.equals(customerSysXRef.getStatus())) {
								for (int k = 0; k < StageXreflength; k++) {
									if (iLimitSysXRef.getSID() == limitSysXRefsStage[k].getSID()) {
										expiryActual.put(String.valueOf(customerSysXRef.getXRefID()),
												String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())
														+ "," + sorceRefNo + "," + customerSysXRef.getAction());
										break;
									}
								}
							} else {
								for (int k = 0; k < StageXreflength; k++) {
									if (iLimitSysXRef.getSID() == limitSysXRefsStage[k].getSID()) {
										expiryActual.put(String.valueOf(customerSysXRef.getXRefID()),
												String.valueOf(limitSysXRefsStage[k].getCustomerSysXRef().getXRefID())
														+ "," + sorceRefNo + ","
														+ ICMSConstant.FCUBSLIMIT_ACTION_MODIFY);
										break;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private static String generateSourceNo(ILimitDAO dao1, String date) {
		String tempSourceRefNo = "";
		tempSourceRefNo = "" + dao1.generateSourceSeqNo();

		int len = tempSourceRefNo.length();
		String concatZero = "";
		if (null != tempSourceRefNo && len != 5) {

			for (int m = 5; m > len; m--) {
				concatZero = "0" + concatZero;
			}

		}
		tempSourceRefNo = concatZero + tempSourceRefNo;

		String sorceRefNo = ICMSConstant.FCUBS_CAD + date + tempSourceRefNo;
		return sorceRefNo;
	}
}
