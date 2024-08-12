package com.integrosys.cms.app.collaborationtask.proxy;

import java.util.HashMap;

import junit.framework.TestCase;

import com.integrosys.cms.app.checklist.bus.CCCheckListSummary;
import com.integrosys.cms.app.checklist.bus.CollateralCheckListSummary;
import com.integrosys.cms.app.collaborationtask.bus.ICCTask;
import com.integrosys.cms.app.collaborationtask.bus.ICollateralTask;
import com.integrosys.cms.app.collaborationtask.bus.OBCCTask;
import com.integrosys.cms.app.collaborationtask.bus.OBCollateralTask;
import com.integrosys.cms.app.collaborationtask.trx.ICCTaskTrxValue;
import com.integrosys.cms.app.collaborationtask.trx.ICollateralTaskTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.component.bizstructure.app.bus.OBTeam;
import com.integrosys.component.user.app.bus.OBCommonUser;

public class CollaborationTaskTestCase extends TestCase {
	private ICollaborationTaskProxyManager proxyMgr = null;

	private ITrxContext trxContext = null;

	public CollaborationTaskTestCase(String name) {
		super(name);
	}

	public void setUp() throws Exception {
		proxyMgr = CollaborationTaskProxyManagerFactory.getProxyManager();
	}

	public void tearDown() throws Exception {
	}

	public void testCheckerApproveCCTask() throws Exception {
		ICCTaskTrxValue trxValue = proxyMgr.getCCTaskByTrxID("20030831001675");

		/*
		 * OBTeam team = new OBTeam(); team.setTeamID(98765); String[]
		 * countryList = {"SG", "MY"}; String[] orgCodeList = {"SCBLSG"} ;
		 * team.setCountryCodes(countryList);
		 * team.setOrganisationCodes(orgCodeList); OBCommonUser user = new
		 * OBCommonUser(); user.setUserID(12345);
		 */
		trxContext = new OBTrxContext();

		ICCTaskTrxValue newTrx = proxyMgr.checkerApproveCollaborationTask(trxContext, trxValue);
	}

	public void testHasPendingCCTaskTrx() throws Exception {
		boolean result = proxyMgr.hasPendingCCTaskTrx(3000002171L, ICMSConstant.CHECKLIST_PLEDGER, 7086, "HK");
		//System.out.println("Result: " + result);
	}

	public void testMakerCloseRejectedCCTask() throws Exception {
		ICCTaskTrxValue trxValue = proxyMgr.getCCTaskByTrxID("20030830001664");

		/*
		 * OBTeam team = new OBTeam(); team.setTeamID(98765); String[]
		 * countryList = {"SG", "MY"}; String[] orgCodeList = {"SCBLSG"} ;
		 * team.setCountryCodes(countryList);
		 * team.setOrganisationCodes(orgCodeList); OBCommonUser user = new
		 * OBCommonUser(); user.setUserID(12345);
		 */
		trxContext = new OBTrxContext();

		ICCTaskTrxValue newTrx = proxyMgr.makerCloseRejectedCollaborationTask(trxContext, trxValue);
	}

	public void testMakerEditRejectedCCTask() throws Exception {
		ICCTaskTrxValue trxValue = proxyMgr.getCCTaskByTrxID("20030830001664");

		/*
		 * OBTeam team = new OBTeam(); team.setTeamID(98765); String[]
		 * countryList = {"SG", "MY"}; String[] orgCodeList = {"SCBLSG"} ;
		 * team.setCountryCodes(countryList);
		 * team.setOrganisationCodes(orgCodeList); OBCommonUser user = new
		 * OBCommonUser(); user.setUserID(12345);
		 */
		trxContext = new OBTrxContext();

		ICCTask ccTask = trxValue.getStagingCCTask();
		ccTask.setRemarks("Testing editing a rejected CC task trx");

		ICCTaskTrxValue newTrx = proxyMgr.makerEditRejectedCollaborationTask(trxContext, trxValue, ccTask);
	}

	public void testCheckerRejectCCTask() throws Exception {
		ICCTaskTrxValue trxValue = proxyMgr.getCCTaskByTrxID("20030830001664");

		/*
		 * OBTeam team = new OBTeam(); team.setTeamID(98765); String[]
		 * countryList = {"SG", "MY"}; String[] orgCodeList = {"SCBLSG"} ;
		 * team.setCountryCodes(countryList);
		 * team.setOrganisationCodes(orgCodeList); OBCommonUser user = new
		 * OBCommonUser(); user.setUserID(12345);
		 */
		trxContext = new OBTrxContext();

		ICCTaskTrxValue newTrx = proxyMgr.checkerRejectCollaborationTask(trxContext, trxValue);

	}

	public void testMakerCreateCCTask() throws Exception {
		ICCTask ccTask = new OBCCTask();
		ccTask.setLimitProfileID(3000002171L);
		ccTask.setCustomerCategory(ICMSConstant.CHECKLIST_CO_BORROWER);
		ccTask.setCustomerID(2000002441);
		ccTask.setDomicileCountry("HK");
		ccTask.setOrgCode("SCBLCLK");
		ccTask.setRemarks("Testing the creation of cc collaboration task");

		/*
		 * OBTeam team = new OBTeam(); team.setTeamID(98765); team.set String[]
		 * countryList = {"SG", "MY"}; String[] orgCodeList = {"SCBLSG"} ;
		 * team.setCountryCodes(countryList);
		 * team.setOrganisationCodes(orgCodeList); OBCommonUser user = new
		 * OBCommonUser(); user.setUserID(12345);
		 */
		trxContext = new OBTrxContext();

		ICCTaskTrxValue trxValue = proxyMgr.makerCreateCollaborationTask(trxContext, ccTask);

	}

	public void testGetCCSummaryList() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(3000002171L);
		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		String[] countryList = { "SG", "MY" };
		String[] orgCodeList = { "SCBLSG" };
		team.setCountryCodes(countryList);
		team.setOrganisationCodes(orgCodeList);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		HashMap map = proxyMgr.getCCSummaryList(trxContext, limitProfile);
		CCCheckListSummary[] summaryList = (CCCheckListSummary[]) map.get(ICMSConstant.SORTED_TASK_LIST);
		for (int jj = 0; jj < summaryList.length; jj++) {

			CCCheckListSummary summary = summaryList[jj];
//			System.out.println(jj);
//			System.out.println("CustCat: " + summary.getCustCategory());
//			System.out.println("LegalID: " + summary.getLegalID());
//			System.out.println("LegalName: " + summary.getLegalName());
//			System.out.println("CustomerID: " + summary.getSubProfileID());
//			System.out.println("Domicile Country: " + summary.getDomicileCtry());
//			System.out.println("OrgCode: " + summary.getOrgCode());
//			System.out.println("GovernLaw: " + summary.getGovernLaw());
//			System.out.println("Status: " + summary.getCheckListStatus());
			String canCreateInd = (String) map.get(summary);
//			System.out.println("Can Create Ind: " + canCreateInd);
		}
	}

	public void testSystemCloseCollateralCollaborationTaskTrx() throws Exception {
		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		String[] countryList = { "SG", "MY" };
		String[] orgCodeList = { "SCBLSG" };
		team.setCountryCodes(countryList);
		team.setOrganisationCodes(orgCodeList);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		proxyMgr.systemCloseCollateralCollaborationTaskTrx(trxContext, 20030804000799L);
	}

	public void testHasPendingCollateralTaskTrx() throws Exception {
		boolean result = proxyMgr.hasPendingCollateralTaskTrx(20030715000023L, 20030811001060L, "SG");
//		System.out.println("Result: " + result);
	}

	public void testGetCollaborationTask() throws Exception {
		ICollateralTaskTrxValue trxValue = proxyMgr.getCollateralTaskByTrxID("20030816000838");
		ICollateralTask task = trxValue.getCollateralTask();
//		System.out.println("Task: " + task);
	}

	public void testCheckerApproveCollaborationTask() throws Exception {
		ICollateralTaskTrxValue trxValue = proxyMgr.getCollateralTaskByTrxID("20030816000838");

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		String[] countryList = { "SG", "MY" };
		String[] orgCodeList = { "SCBLSG" };
		team.setCountryCodes(countryList);
		team.setOrganisationCodes(orgCodeList);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		ICollateralTaskTrxValue newTrx = proxyMgr.checkerApproveCollaborationTask(trxContext, trxValue);
	}

	public void testMakerCloseRejectedCollaborationTask() throws Exception {
		ICollateralTaskTrxValue trxValue = proxyMgr.getCollateralTaskByTrxID("20030816000837");

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		String[] countryList = { "SG", "MY" };
		String[] orgCodeList = { "SCBLSG" };
		team.setCountryCodes(countryList);
		team.setOrganisationCodes(orgCodeList);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		ICollateralTaskTrxValue newTrx = proxyMgr.makerCloseRejectedCollaborationTask(trxContext, trxValue);
	}

	public void testMakerEditRejectedCollaborationTask() throws Exception {
		ICollateralTaskTrxValue trxValue = proxyMgr.getCollateralTaskByTrxID("20030816000837");

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		String[] countryList = { "SG", "MY" };
		String[] orgCodeList = { "SCBLSG" };
		team.setCountryCodes(countryList);
		team.setOrganisationCodes(orgCodeList);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		ICollateralTask colTask = trxValue.getStagingCollateralTask();
		colTask.setRemarks("Testing editing a rejected collateral task trx");

		ICollateralTaskTrxValue newTrx = proxyMgr.makerEditRejectedCollaborationTask(trxContext, trxValue, colTask);
	}

	public void testCheckerRejectCollaborationTask() throws Exception {
		ICollateralTaskTrxValue trxValue = proxyMgr.getCollateralTaskByTrxID("20030816000837");

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		String[] countryList = { "SG", "MY" };
		String[] orgCodeList = { "SCBLSG" };
		team.setCountryCodes(countryList);
		team.setOrganisationCodes(orgCodeList);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		ICollateralTaskTrxValue newTrx = proxyMgr.checkerRejectCollaborationTask(trxContext, trxValue);

	}

	public void testMakerCreateCollaborationTask() throws Exception {
		ICollateralTask colTask = new OBCollateralTask();
		colTask.setLimitProfileID(20030715000023L);
		// colTask.setLimitID(20030710000016L);
		colTask.setCollateralID(20030811001060L);
		colTask.setCollateralLocation("SG");
		colTask.setRemarks("Testing the creation of collateral collaboration taskf");

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		String[] countryList = { "SG", "MY" };
		String[] orgCodeList = { "SCBLSG" };
		team.setCountryCodes(countryList);
		team.setOrganisationCodes(orgCodeList);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		ICollateralTaskTrxValue trxValue = proxyMgr.makerCreateCollaborationTask(trxContext, colTask);

	}

	public void testGetCollateralSummaryList() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		String[] countryList = { "SG", "MY" };
		String[] orgCodeList = { "SCBLSG" };
		team.setCountryCodes(countryList);
		team.setOrganisationCodes(orgCodeList);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		HashMap map = proxyMgr.getCollateralSummaryList(trxContext, limitProfile);
		// Iterator iter = map.keySet().iterator();
		// while (iter.hasNext())

		CollateralCheckListSummary[] summaryList = (CollateralCheckListSummary[]) map.get("Sorted");
		for (int jj = 0; jj < summaryList.length; jj++) {
			// CollateralCheckListSummary summary =
			// (CollateralCheckListSummary)iter.next();
			CollateralCheckListSummary summary = summaryList[jj];
			ILimit[] limitList = summary.getLimitList();
			for (int ii = 0; ii < limitList.length; ii++) {
//				System.out.println("LimitID: " + limitList[ii].getLimitID());
//				System.out.println("LimitRef: " + limitList[ii].getLimitRef());
			}
//			System.out.println("CollateralID: " + summary.getCollateralID());
//			System.out.println("CollateralRef: " + summary.getCollateralRef());
//			System.out.println("Col Type: " + summary.getCollateralType());
//			System.out.println("Col SubType: " + summary.getCollateralSubType());
//			System.out.println("Col Location: " + summary.getCollateralLocation());
//			System.out.println("Status: " + summary.getCheckListStatus());
			String canCreateInd = (String) map.get(summary);
//			System.out.println("Can Create Ind: " + canCreateInd);
		}
	}

}