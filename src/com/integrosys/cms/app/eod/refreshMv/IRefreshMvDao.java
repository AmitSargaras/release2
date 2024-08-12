package com.integrosys.cms.app.eod.refreshMv;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/* @author uma.khot
 * 
 */
public interface IRefreshMvDao {
	public void refreshMvForUserAdminReport() throws Exception ;
	
	public void refreshMvForAuditTrailReportDB() throws Exception ;
	
	public void refreshMvForEwsStockDeferral() throws Exception ;//EWSStockDeferral

	void refreshMvForCustomerWiseSecurityReport() throws Exception;

	public void refreshMvForStockStatementReportForRM() throws Exception;;

	void refreshMvForCustomerWiseStockDetailsReport() throws Exception;
	
	void refreshMvForSPRfreshSecMapChargeIdMV() throws Exception;

}
