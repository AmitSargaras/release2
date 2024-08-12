/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collateralevaluationdue/EvaluationDueDAO.java,v 1.32 2006/08/21 02:46:34 hmbao Exp $
 */

package com.integrosys.cms.app.eventmonitor.annualreview;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAOResult;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.MonitorDaoResultRetrievalFailureException;

/**
 * Describe this class. *
 * 
 * @author BaoHongMan
 * @version R1.5
 * @Purpose:
 * @Description:
 * @Tag 
 *      com.integrosys.cms.app.eventmonitor.collateralevaluationdue.EvaluationDueDAO
 *      .java
 * @since 2006-7-20
 */
public class AnnualReviewDAO extends AbstractMonitorDAO implements IMonitorDAO {

	public IMonitorDAOResult getInitialSet(IRuleParam ruleParam) throws MonitorDaoResultRetrievalFailureException {
		ResultSet rs = null;
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			AnnualReviewDAOResult result = new AnnualReviewDAOResult(getHardCodeResult());
			return result;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException(e);
		}
		finally {
			close(rs, dbUtil);
		}
	}

	private List getHardCodeResult() {
		List aList = new ArrayList();
		OBAnnualReview info = new OBAnnualReview();
		info.setFacilityID("ID_test");
		info.setFacilityDesc("Fac_Desc");
		info.setReviewDueDate(new Date());
		aList.add(info);
		return aList;
	}
}
