package com.integrosys.cms.app.cccertificate.proxy;

import java.util.HashMap;

import junit.framework.TestCase;

import com.integrosys.cms.app.cccertificate.bus.CCCertificateSummary;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificate;
import com.integrosys.cms.app.cccertificate.trx.ICCCertificateTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.component.bizstructure.app.bus.OBTeam;
import com.integrosys.component.user.app.bus.OBCommonUser;

public class CCCertificateTestCase extends TestCase {
	private ICCCertificateProxyManager proxyMgr = null;

	private ITrxContext trxContext = null;

	public CCCertificateTestCase(String name) {
		super(name);
	}

	public void setUp() throws Exception {
		proxyMgr = CCCertificateProxyManagerFactory.getCCCertificateProxyManager();
	}

	public void tearDown() throws Exception {
	}

	public void testGetNoOfCCCGenerated() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20031016000495L);
		int numOfCCC = proxyMgr.getNoOfCCCGenerated(limitProfile);
		// System.out.println("Number of CCCs: " + numOfCCC);
	}

	public void testGetNoOfCCCRequired() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20031016000495L);
		int numOfCCC = proxyMgr.getNoOfCCCRequired(limitProfile);
		// System.out.println("Number of CCCs: " + numOfCCC);
	}

	public void testHasPendingGenerateCCCTrx() throws Exception {
		// System.out.println("In testHasPendingGenerateCCCTrx !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		CCCertificateSummary[] summaryList = proxyMgr.getCCCertificateSummaryList(null, limitProfile);
		boolean result = proxyMgr.hasPendingGenerateCCCTrx(limitProfile, summaryList[0]);
		// System.out.println("Result: " + result);
	}

	public void testMakerEditRejectedGenerateCCC() throws Exception {
		// System.out.println("In testMakerEditRejectedGenerateCCC !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getCCCertificate(limitProfile, customer, "20030805000568");
		ICCCertificateTrxValue trxValue = (ICCCertificateTrxValue) map.get(ICMSConstant.CCC);

		ICCCertificate ccCert = trxValue.getStagingCCCertificate();

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		ICCCertificateTrxValue newTrxValue = proxyMgr.makerEditRejectedGenerateCCC(trxContext, trxValue, ccCert);
		// System.out.println("TrxValue: " + newTrxValue);
	}

	public void testMakerCloseRejectedGenerateCCC() throws Exception {
		// System.out.println("In testMakerCloseRejectedGenerateCCC !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getCCCertificate(limitProfile, customer, "20030805000568");
		ICCCertificateTrxValue trxValue = (ICCCertificateTrxValue) map.get(ICMSConstant.CCC);

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		ICCCertificateTrxValue newTrxValue = proxyMgr.makerCloseRejectedGenerateCCC(trxContext, trxValue);
		// System.out.println("TrxValue: " + newTrxValue);
	}

	public void testCheckerRejectGenerateCCC() throws Exception {
		// System.out.println("In testCheckerRejectGenerateCCC !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getCCCertificate(limitProfile, customer, "20030805000568");
		ICCCertificateTrxValue trxValue = (ICCCertificateTrxValue) map.get(ICMSConstant.CCC);

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		ICCCertificateTrxValue newTrxValue = proxyMgr.checkerRejectGenerateCCC(trxContext, trxValue);
		// System.out.println("TrxValue: " + newTrxValue);
	}

	public void testCheckerApproveGenerateCCC() throws Exception {
		// System.out.println("In testCheckerApproveGenerateCCC !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getCCCertificate(limitProfile, customer, "20030805000568");
		ICCCertificateTrxValue trxValue = (ICCCertificateTrxValue) map.get(ICMSConstant.CCC);

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		ICCCertificateTrxValue newTrxValue = proxyMgr.checkerApproveGenerateCCC(trxContext, trxValue);
		// System.out.println("TrxValue: " + newTrxValue);
	}

	public void testGetCCCertificateByTrx() throws Exception {
		// System.out.println("In testGetCCCertificateByTrx !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20031213000541L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20031213000267L);
		HashMap map = proxyMgr.getCCCertificate(limitProfile, customer, "20040112006097");
		// System.out.println("HashMap: " + map);
	}

	public void testMakerGenerateCCC() throws Exception {
		// System.out.println("In testGetCCCertificate !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		CCCertificateSummary[] summaryList = proxyMgr.getCCCertificateSummaryList(null, limitProfile);
		HashMap map = proxyMgr.getCCCertificate(limitProfile, customer, summaryList[0]);
		ICCCertificateTrxValue trxValue = (ICCCertificateTrxValue) map.get(ICMSConstant.CCC);

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		ICCCertificateTrxValue newTrxValue = proxyMgr.makerGenerateCCC(trxContext, trxValue, trxValue
				.getStagingCCCertificate());
		// System.out.println("TRXVALUE: " + newTrxValue.getTrxContext());

	}

	public void testGetCCCertificate() throws Exception {
		// System.out.println("In testGetCCCertificate !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		CCCertificateSummary[] summaryList = proxyMgr.getCCCertificateSummaryList(null, limitProfile);

		HashMap map = proxyMgr.getCCCertificate(limitProfile, customer, summaryList[0]);
		// System.out.println("HashMap: " + map);
		// System.out.println("TrxValue: " + trxValue);

	}

	public void testGetCCCertificateSummaryList() throws Exception {
		// System.out.println("In testGetCCCertificateSummaryList !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		CCCertificateSummary[] summaryList = proxyMgr.getCCCertificateSummaryList(null, limitProfile);
		// System.out.println("Number of summaries: " + summaryList.length);
		for (int ii = 0; ii < summaryList.length; ii++) {
			// System.out.println("Summary :" + summaryList[ii]);
		}
	}

}