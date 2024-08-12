package com.integrosys.cms.batch.reports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.FinderException;
import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

/**
 * Description:
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.6 $
 * @since $Date: 2006/09/07 05:22:34 $ Tag: $Name: $
 */

public class SBReportRequestManagerBean implements javax.ejb.SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	public SBReportRequestManagerBean() {
	}

	/**
	 * method to create a report request
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public IReportRequest createReportRequest(IReportRequest req) throws Exception {
		try {
			if (null == req) {
				throw new Exception("IReportRequest is null!");
			}
			// create record
			EBReportRequestLocalHome reqHome = getEBHome();
			EBReportRequestLocal eb = reqHome.create(req);

			return eb.getValue();
		}

		catch (Exception e) {
			_context.setRollbackOnly();
			e.printStackTrace();
			throw new Exception("Caught Exception: " + e.toString());
		}

	}

	/**
	 * method to update the report request
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public IReportRequest updateReportRequest(IReportRequest obj) throws Exception {
		try {

			EBReportRequestLocalHome ejbHome = getEBHome();
			EBReportRequestLocal theEjb = ejbHome.findByPrimaryKey(new Long(obj.getReportRequestID()));
			theEjb.setValue(obj);
			return theEjb.getValue();
		}
		catch (FinderException e) {
			throw new Exception("Record not found " + e.toString());
		}
	}

	/**
	 * method to get the report request , given the pk
	 * @param requestID
	 * @return
	 * @throws Exception
	 */
	public IReportRequest getReportRequest(long requestID) throws Exception {
		try {
			EBReportRequestLocalHome ejbHome = getEBHome();
			EBReportRequestLocal theEjb = ejbHome.findByPrimaryKey(new Long(requestID));
			return theEjb.getValue();
		}
		catch (FinderException e) {
			throw new Exception("Finder Exception caught" + e.toString());
		}
	}

	/**
	 * method to get all the report requests, based on status
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public Collection getReportRequest(String status) throws Exception {
		try {
			EBReportRequestLocalHome ejbHome = getEBHome();
			Collection ebCol = ejbHome.findByStatus(status);

			if ((ebCol == null) || (ebCol.size() == 0)) {
				return null;
			}

			Collection obCol = new ArrayList();
			// convert eb collection to ob collection

			Iterator it = ebCol.iterator();

			while (it.hasNext()) {
				IReportRequest ob = ((EBReportRequestLocal) it.next()).getValue();
				obCol.add(ob);
			}
			return obCol;

		}
		catch (FinderException e) {
			throw new Exception("Finder Exception caught" + e.toString());
		}
	}

	/**
	 * Called by the container to create a session bean instance. Its parameters
	 * typically contain the information the client uses to customize the bean
	 * instance for its use. It requires a matching pair in the bean class and
	 * its home interface.
	 */
	public void ejbCreate() {
	}

	/**
	 * A container invokes this method before it ends the life of the session
	 * object. This happens as a result of a client's invoking a remove
	 * operation, or when a container decides to terminate the session object
	 * after a timeout. This method is called with no transaction context.
	 */
	public void ejbRemove() {
	}

	/**
	 * The activate method is called when the instance is activated from its
	 * 'passive' state. The instance should acquire any resource that it has
	 * released earlier in the ejbPassivate() method. This method is called with
	 * no transaction context.
	 */
	public void ejbActivate() {
	}

	/**
	 * The passivate method is called before the instance enters the 'passive'
	 * state. The instance should release any resources that it can re-acquire
	 * later in the ejbActivate() method. After the passivate method completes,
	 * the instance must be in a state that allows the container to use the Java
	 * Serialization protocol to externalize and store away the instance's
	 * state. This method is called with no transaction context.
	 */
	public void ejbPassivate() {
	}

	/**
	 * Set the associated session context. The container calls this method after
	 * the instance creation. The enterprise Bean instance should store the
	 * reference to the context object in an instance variable. This method is
	 * called with no transaction context.
	 */
	public void setSessionContext(javax.ejb.SessionContext sc) {
		_context = sc;
	}

	private EBReportRequestLocalHome getEBHome() throws Exception {

		EBReportRequestLocalHome home = (EBReportRequestLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_REPORT_REQUEST_LOCAL_JNDI, EBReportRequestLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new Exception("EBReportRequestLocalHome is null!");
		}
	}

	public OBReport[] getMISReportsByCountryAndDate(String countryName, String[] orgCodes, Date reportDate,
			String[] reportCategoryId,String centreCode) throws ReportException {
		ReportSchedulerDAO dao = new ReportSchedulerDAO();
		return dao.getMISReportsByCountryAndDate(countryName, orgCodes, reportDate, reportCategoryId,centreCode);
	}

	/**
	 * retrieves a report by its id and type
	 * @param reportId
	 * @return OBReport
	 */
	public OBReport getReport(long reportId, String reportType) throws ReportException {
		ReportSchedulerDAO dao = new ReportSchedulerDAO();
		return dao.getReport(reportId, reportType);
	}

	/**
	 * Retrieve concentration report list filter by country and team type
	 * @param reportDate
	 * @param reportCategoryId
	 * @param allowedCountries
	 * @param teamTypeID
	 * @return OBReport[]
	 */
	public OBReport[] getConcentrationReportList(Date reportDate, String reportCategoryId, String[] allowedCountries,
			long teamTypeID) throws ReportException {
		ReportSchedulerDAO dao = new ReportSchedulerDAO();
		return dao.getConcentrationReportList(reportDate, reportCategoryId, allowedCountries, teamTypeID);
	}

	/**
	 * Retrieves all active reports that are being used in the system
	 * @param minReportMasterId the report master id to start from (min=1, as of
	 *        R1.4.1)
	 * @param maxReportMasterId the report master id to end at (max=150, as of
	 *        R1.4.1)
	 * @return OBReport[] all active reports whose report id is >=
	 *         minReportMasterId and <= maxReportMasterId
	 */
	public OBReport[] getAllActiveReportList(int minReportMasterId, int maxReportMasterId) {
		ReportSchedulerDAO dao = new ReportSchedulerDAO();
		return dao.getAllActiveReportList(minReportMasterId, maxReportMasterId);
	}

	/**
	 * Check whether the teamTypeMembershipID has access to MIS Report
	 * @param teamTypeMembershipID
	 * @return boolean
	 */
	public boolean hasTeamAccessToMIS(long teamTypeMembershipID) throws ReportException {
		ReportSchedulerDAO dao = new ReportSchedulerDAO();
		return dao.getIsMISReportAccessibleByTeam(teamTypeMembershipID);
	}

	/**
	 * Check whether the teamTypeMembershipID has access to SYS Report
	 * @param teamTypeMembershipID
	 * @return boolean
	 */
	public boolean hasTeamAccessToSYS(long teamTypeMembershipID) throws ReportException {
		ReportSchedulerDAO dao = new ReportSchedulerDAO();
		return dao.getIsSysReportAccessibleByTeam(teamTypeMembershipID);
	}

	/**
	 * Check whether the teamTypeMembershipID has access to Concentration Report
	 * @param teamTypeMembershipID
	 * @return boolean
	 */
	public boolean hasTeamAccessToCON(long teamTypeMembershipID) throws ReportException {
		ReportSchedulerDAO dao = new ReportSchedulerDAO();
		return dao.getIsConcReportAccessibleByTeam(teamTypeMembershipID);
	}

	public long createViewReportRequest(long reportId, long userID) throws ReportException {
		ReportSchedulerDAO dao = new ReportSchedulerDAO();
		return dao.createViewReportRequest(reportId, userID);
	}

	public boolean deleteViewReportRequest(long sid) throws ReportException {
		ReportSchedulerDAO dao = new ReportSchedulerDAO();
		return dao.deleteViewReportRequest(sid);
	}

	public long getReportMasterID(String reportNumber) throws ReportException {
		ReportSchedulerDAO dao = new ReportSchedulerDAO();
		return dao.getReportMasterID(reportNumber);
	}

	/**
	 * Generate Online Report on demand
	 * @param reportMasterID
	 * @param reportDate
	 * @param limitProfileID
	 * @param exportFormat
	 * @param allowedCountries
	 * @param loginID
	 * @return String
	 */
	public String generateOnlineReport(String reportMasterID, Date reportDate, long limitProfileID,
			String exportFormat, String[] allowedCountries, String loginID) throws ReportException {
		try {
			return null;
			/*
			return OnlineReportGenerator.generateReport(reportMasterID, reportDate, limitProfileID, exportFormat,
					allowedCountries, loginID);
					*/
		}
		catch (Exception e) {
			throw new ReportException(e);
		}
	}

	/**
	 * Generate Online Report on demand
	 * @param reportMasterID
	 * @param startExpDate
	 * @param endExpDate
	 * @param teamTypeMembershipId
	 * @param allowedCountries
	 * @param exportFormat
	 * @param leID
	 * @param customerIndex
	 * @param loginID
	 * @return String
	 */
	public String generateOnlineReport(String reportMasterID, Date startExpDate, Date endExpDate,
			long teamTypeMembershipId, String[] allowedCountries, String exportFormat, long leID,
			String customerIndex, String loginID) throws ReportException {
		try {
			return null;
			/*
			return OnlineReportGenerator.generateReport(reportMasterID, startExpDate, endExpDate,
					teamTypeMembershipId, allowedCountries, exportFormat, leID, customerIndex, loginID);
					*/
		}
		catch (Exception e) {
			throw new ReportException(e);
		}
	}

	/**
	 * Generate Online Report on demand
	 * @param reportMasterID
	 * @param reportDate
	 * @param country
	 * @param exportFormat
	 * @param loginID
	 * @return String
	 */
	public String generateOnlineReport(String reportMasterID, Date reportDate, String country, String exportFormat,
			String loginID) throws ReportException {
		try {
			return null;
			//return OnlineReportGenerator.generateReport(reportMasterID, reportDate, country, exportFormat, loginID);
		}
		catch (Exception e) {
			throw new ReportException(e);
		}
	}

	/**
	 * Generate Online Report on demand - This method is used to generate
	 * Disclaimer Letter for Bridging Loan
	 * @param reportNumber
	 * @param reportDate
	 * @param country
	 * @param buildupID
	 * @param exportFormat
	 * @return String
	 */
	public String generateOnlineReport(String reportNumber, Date reportDate, String country, long buildupID,
			String exportFormat, String loginID) throws ReportException {

		try {
			return null;
			/*
			return OnlineReportGenerator.generateReport(reportNumber, reportDate, country, buildupID, exportFormat,
					loginID);
					*/
		}
		catch (Exception e) {
			throw new ReportException(e);
		}
	}

	public OBReport getReportDetailsByReportID(String reportID) throws ReportException {
		ReportSchedulerDAO dao = new ReportSchedulerDAO();
		return dao.getReportDetailsByReportID(reportID);
	}


    public String[]  getCenterCodes(String status) throws ReportException {
		ReportSchedulerDAO dao = new ReportSchedulerDAO();
		return dao.getCenterCodes(status);
	}

    /**
     * This is used to View Group Exposure Report based on country and date
     *   but country is from User country and date is today
     * @param countryName
     * @param orgCodes
     * @param reportDate
     * @param reportCategoryId
     * @return
     * @throws ReportException
     */

    public OBReport[] getGroupExposureReportsByCountryAndDate
            (String countryName, String[] orgCodes, Date reportDate, String[]  reportCategoryId) throws ReportException {
        ReportSchedulerDAO dao = new ReportSchedulerDAO();
        return dao.getGroupExposureReportsByCountryAndDate(countryName, orgCodes, reportDate, reportCategoryId);
    }    
}
