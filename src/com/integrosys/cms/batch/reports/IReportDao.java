package com.integrosys.cms.batch.reports;

import java.io.Serializable;
import java.util.List;

/**
 * ORM based DAO to interface with report request
 * 
 * @author Forbys Wei
 * @author Chong Jun Yong
 * 
 */
public interface IReportDao {

	public IReportRequest getReportRequestByRequestID(Serializable key);

	public List getReportRequestByStatus(String status);

	public IReportRequest createReportRequest(IReportRequest req);

	public IReportRequest updateReportRequest(IReportRequest item);

}
