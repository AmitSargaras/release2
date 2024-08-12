package com.integrosys.cms.batch.reports;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.crystaldecisions.sdk.occa.infostore.ISchedulingInfo;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.factory.TransactionControlledBatchJob;

/**
 * Description: This class is to be called from the cron job. It selects all the
 * adhoc reports that are to be generated (from the reportRequest table set by
 * ui) in a loop, update the status as 'In progress' invoke the report handler
 * to generate the report update the status as 'Generated' or 'Error' as
 * returned by the handler
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.6 $
 * @since $Date: 2006/09/05 05:39:42 $ Tag: $Name: $
 */

public class AdhocReportInvoker extends ReportScheduler implements TransactionControlledBatchJob {

	private IReportRequestManager reportRequestManager;

	private TransactionTemplate transactionTemplate;

	/**
	 * @param reportRequestManager the reportRequestManager to set
	 */
	public void setReportRequestManager(IReportRequestManager reportRequestManager) {
		this.reportRequestManager = reportRequestManager;
	}

	/**
	 * @return the reportRequestManager
	 */
	public IReportRequestManager getReportRequestManager() {
		return reportRequestManager;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void execute(Map context) throws BatchJobException {
		setup();

		Collection col = getReportRequestManager().getReportRequest(ReportConstants.REPORT_STATUS_REQUESTED);
		if (col == null) {
			logger.warn("requested status reports is empty");
		}

		if (((col != null) && (col.size() != 0))) {
			Iterator it = col.iterator();

			// Create report handler
			while (it.hasNext()) {
				final OBReportRequest ob = (OBReportRequest) it.next();

				this.transactionTemplate.execute(new TransactionCallback() {

					public Object doInTransaction(TransactionStatus status) {
						ob.setStatus(ReportConstants.REPORT_STATUS_IN_PROGRESS);
						getReportRequestManager().updateReportRequest(ob);

						// Invoke the reports
						RiskProfileReportParameter param = new RiskProfileReportParameter();

						param.setReportDisplayName(ob.getReportName());
						param.setReportMasterId(Long.toString(ob.getReportID()));

						// param.setParameters(ob.getParameters());
						param.setParamMap(new HashMap());
						param.setReportDate(DateUtil.getDate());

						StringTokenizer paramSt = new StringTokenizer(ob.getParameters(), "|");
						String scope = "";
						String country = "";

						if (paramSt.hasMoreTokens()) {

							Map paramMap = prepareParameterMap(ob.getParameters());
							country = (String) paramMap.get(ReportConstants.KEY_COUNTRY);
							scope = (String) paramMap.get(ReportConstants.KEY_SCOPE);
							param.getParamMap().putAll(paramMap);

						}
						else {

							// old codes
							// remain as it is to avoid conflicts
							param.setParameters(ob.getParameters());

							StringTokenizer st = new StringTokenizer(ob.getParameters(), ",");
							scope = st.nextToken();
							country = st.nextToken();
							param.setCountry(country);
						}

						Date reportDate = DateUtil.getDate();
						setReportFolderPath(reportDate);

						OBReportConfig reportConfig = getReportConfig(param.getReportMasterId(), null);
						reportConfig.setReportDate(reportDate);
						reportConfig.setCountry(country);
						reportConfig.setScope(scope);

						ReportContext ctx = new ReportContext();
						ctx.setParam(param);
						ctx.setReportConfig(reportConfig);
						reportThread.set(new ArrayList());
						generateReport(ctx);

						try {
							while (true) {
								ctx = (ReportContext) ((ArrayList) reportThread.get()).get(0);
								String rptStatus = checkReportStatus(ctx.getOInfoObjects(), ctx.getReportStartTime());
								if (ReportConstants.RPT_STATUS_COMPLETED.equals(rptStatus)) {
									break;
								}
								else if (ReportConstants.RESCHEDULE_REQUIRED.equals(rptStatus)) {
									if (ctx.getRetryCount() < MAX_RETRY_COUNT) {
										// report that reach the max retry count
										((ArrayList) reportThread.get()).remove(0);
										// ctx.setRetryCount(0);
										generateReport(ctx);
									}
									else {
										logger.debug("Failed to generate pending report after retry " + MAX_RETRY_COUNT
												+ " times... ");
										ctx.setErrLog("Exceed maximum retry count (" + MAX_RETRY_COUNT
												+ ") for pending report generation");
										throw new ReportException(ctx.getErrLog());
									}
								}
								else if (String.valueOf(ISchedulingInfo.ScheduleStatus.FAILURE).equals(rptStatus)) {
									ctx.setErrLog(ERR_LOG);
									throw new ReportException(ctx.getErrLog());
								}
								else {
									logger.debug("~~~~~ Report : " + ctx.getReportFormat() + "\tgeneratedReportName: "
											+ ctx.getGenReportName() + " not yet finish generating with status: "
											+ status);
									Thread.sleep(SLEEP_TIME);
								}
							}
							scheduleNextReportFormat(ctx, 0);
							ob.setStatus(ReportConstants.REPORT_STATUS_GENERATED);
						}
						catch (ReportException e) {
							logger.warn("failed to generate adhoc report [" + ob + "], setting report status failed.",
									e);
							ob.setStatus(ReportConstants.REPORT_STATUS_GENERATION_ERROR);
						}
						catch (InterruptedException e) {
							logger.error("failed to interrupt current thread [" + Thread.currentThread().getName()
									+ "]", e);
						}

						ob.setReportGenTime(Calendar.getInstance().getTime());

						getReportRequestManager().updateReportRequest(ob);

						return null;
					}
				});

			}
		}
		setEnterpriseServerLogout();
	}

	/**
	 * Split the parameters which is in key value pair, eg. X=Y, and put into a
	 * Map. Map's key is the key, value is the value.
	 * 
	 * @param parameters parameter array in key value pair form, ie X=Y
	 * @return a map, which key is the parameter key, value is the parameter
	 *         value (might contain array of string). All in String format.
	 */
	protected Map prepareParameterMap(String parameters) {

		if (parameters == null) {
			return Collections.EMPTY_MAP;
		}

		String[] parameterArray = StringUtils.split(parameters, "|");

		Map parameterMap = new HashMap();
		for (int i = 0; i < parameterArray.length; i++) {
			String[] keyValue = StringUtils.split(parameterArray[i], '=');
			if (keyValue.length == 2) {
				if (keyValue[0].toUpperCase().indexOf("DATE") > 0)
					parameterMap.put(keyValue[0], CalendarHelper.convertDate(keyValue[1]));
				else
					parameterMap.put(keyValue[0], keyValue[1]);
			}
		}

		return Collections.unmodifiableMap(parameterMap);
	}
}
