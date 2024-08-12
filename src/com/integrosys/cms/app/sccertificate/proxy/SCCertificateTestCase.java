package com.integrosys.cms.app.sccertificate.proxy;

import java.util.HashMap;

import junit.framework.TestCase;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificateItem;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificate;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificateItem;
import com.integrosys.cms.app.sccertificate.trx.IPartialSCCertificateTrxValue;
import com.integrosys.cms.app.sccertificate.trx.ISCCertificateTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.component.bizstructure.app.bus.OBTeam;
import com.integrosys.component.user.app.bus.OBCommonUser;

public class SCCertificateTestCase extends TestCase {
	private ISCCertificateProxyManager proxyMgr = null;

	private ITrxContext trxContext = null;

	public SCCertificateTestCase(String name) {
		super(name);
	}

	public void setUp() throws Exception {
		proxyMgr = SCCertificateProxyManagerFactory.getSCCertificateProxyManager();
	}

	public void tearDown() throws Exception {
	}

	public void testSystemCloseSCC() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20031203000242L);
		proxyMgr.systemCloseSCC(limitProfile);
	}

	public void testMakerEditRejectedGeneratePartialSCC() throws Exception {
//		System.out.println("In testMakerEditRejectedGeneratePartialSCC !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getPartialSCCertificate(limitProfile, customer, "20030811000669");
		IPartialSCCertificateTrxValue trxValue = (IPartialSCCertificateTrxValue) map.get(ICMSConstant.PSCC);

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		IPartialSCCertificate sccCert = trxValue.getStagingPartialSCCertificate();
		IPartialSCCertificateItem[] itemList = sccCert.getPartialSCCItemList();
//		System.out.println("Number of Partial SCC Items: " + itemList.length);
		for (int ii = 0; ii < itemList.length; ii++) {
			Amount approvedAmt = itemList[ii].getApprovedLimitAmount();
			Amount activateAmt = new Amount(2000 + ii, approvedAmt.getCurrencyCode());
			itemList[ii].setActivatedAmount(activateAmt);
		}
		sccCert.setSCCItemList(itemList);
		sccCert.setCreditOfficerName("Credit Officer 1");
		IPartialSCCertificateTrxValue newTrxValue = proxyMgr.makerEditRejectedGeneratePartialSCC(trxContext, trxValue,
				sccCert);
//		System.out.println("TrxValue: " + newTrxValue);
	}

	public void testCheckerApproveGeneratePartialSCC() throws Exception {
//		System.out.println("In testCheckerApproveGeneratePartialSCC !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getPartialSCCertificate(limitProfile, customer, "20030811000669");
		IPartialSCCertificateTrxValue trxValue = (IPartialSCCertificateTrxValue) map.get(ICMSConstant.PSCC);

		// System.out.println("TrxValue: " + trxValue);

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);
		IPartialSCCertificateTrxValue newTrxValue = proxyMgr.checkerApproveGeneratePartialSCC(trxContext, trxValue);
//		System.out.println("TrxValue: " + newTrxValue);
	}

	public void testMakerCloseRejectedGeneratePartialSCC() throws Exception {
//		System.out.println("In testMakerCloseRejectedGeneratePartialSCC !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getPartialSCCertificate(limitProfile, customer, "20030811000669");
		IPartialSCCertificateTrxValue trxValue = (IPartialSCCertificateTrxValue) map.get(ICMSConstant.PSCC);

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		IPartialSCCertificateTrxValue newTrxValue = proxyMgr.makerCloseRejectedGeneratePartialSCC(trxContext, trxValue);
//		System.out.println("TrxValue: " + newTrxValue);
	}

	public void testCheckerRejectGeneratePartialSCC() throws Exception {
//		System.out.println("In testCheckerRejectGeneratePartialSCC !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getPartialSCCertificate(limitProfile, customer, "20030811000669");
		IPartialSCCertificateTrxValue trxValue = (IPartialSCCertificateTrxValue) map.get(ICMSConstant.PSCC);

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		IPartialSCCertificateTrxValue newTrxValue = proxyMgr.checkerRejectGeneratePartialSCC(trxContext, trxValue);
//		System.out.println("TrxValue: " + newTrxValue);
	}

	public void testMakerGeneratePartialSCC() throws Exception {
//		System.out.println("In testMakerGeneratePartialSCC !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getPartialSCCertificate(limitProfile, customer);
		IPartialSCCertificateTrxValue trxValue = (IPartialSCCertificateTrxValue) map.get(ICMSConstant.PSCC);

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		IPartialSCCertificate sccCert = trxValue.getStagingPartialSCCertificate();
		IPartialSCCertificateItem[] itemList = sccCert.getPartialSCCItemList();
//		System.out.println("Number of Partial SCC Items: " + itemList.length);
		for (int ii = 0; ii < itemList.length; ii++) {
			Amount approvedAmt = itemList[ii].getApprovedLimitAmount();
			Amount activateAmt = new Amount(1000 + ii, approvedAmt.getCurrencyCode());
			itemList[ii].setActivatedAmount(activateAmt);
		}
		itemList[1].setIsPartialSCCIssued(true);
		itemList[3].setIsPartialSCCIssued(true);
		sccCert.setSCCItemList(itemList);
		IPartialSCCertificateTrxValue newTrxValue = proxyMgr.makerGeneratePartialSCC(trxContext, trxValue, sccCert);
		// System.out.println("TRXVALUE: " + newTrxValue.getTrxContext());
	}

	public void testHasPendingGeneratePartialSCCTrx() throws Exception {
		// System.out.println("In testHasPendingGeneratePartialSCCTrx !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		boolean result = proxyMgr.hasPendingGeneratePartialSCCTrx(limitProfile);
		// System.out.println("Result: " + result);
	}

	public void testGetPartialSCCertificate() throws Exception {
		// System.out.println("In testGetPartialSCCertificate !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getPartialSCCertificate(limitProfile, customer);
		IPartialSCCertificateTrxValue pscCert = (IPartialSCCertificateTrxValue) map.get(ICMSConstant.PSCC);
		// System.out.println("Partial SCC: " + pscCert);
	}

	public void testCheckerApproveGenerateSCC() throws Exception {
		// System.out.println("In testCheckerApproveGenerateSCC !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getSCCertificate(limitProfile, customer, "20030818000881");
		ISCCertificateTrxValue trxValue = (ISCCertificateTrxValue) map.get(ICMSConstant.SCC);

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		ISCCertificateTrxValue newTrxValue = proxyMgr.checkerApproveGenerateSCC(trxContext, trxValue);
		// System.out.println("TrxValue: " + newTrxValue);
	}

	public void testMakerEditRejectedGenerateSCC() throws Exception {
		// System.out.println("In testMakerEditRejectedGenerateSCC !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getSCCertificate(limitProfile, customer, "20030809000662");
		ISCCertificateTrxValue trxValue = (ISCCertificateTrxValue) map.get(ICMSConstant.SCC);

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		ISCCertificate sccCert = trxValue.getStagingSCCertificate();
		ISCCertificateItem[] itemList = sccCert.getSCCItemList();
		// System.out.println("Number of SCC Items: " + itemList.length);
		for (int ii = 0; ii < itemList.length; ii++) {
			Amount approvedAmt = itemList[ii].getApprovedLimitAmount();
			Amount activateAmt = new Amount(2000 + ii, approvedAmt.getCurrencyCode());
			itemList[ii].setActivatedAmount(activateAmt);
		}
		sccCert.setSCCItemList(itemList);
		sccCert.setCreditOfficerName("Credit Officer 1");
		ISCCertificateTrxValue newTrxValue = proxyMgr.makerEditRejectedGenerateSCC(trxContext, trxValue, sccCert);
		// System.out.println("TrxValue: " + newTrxValue);
	}

	public void testMakerCloseRejectedGenerateSCC() throws Exception {
		// System.out.println("In testMakerCloseRejectedGenerateSCC !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getSCCertificate(limitProfile, customer, "20030809000662");
		ISCCertificateTrxValue trxValue = (ISCCertificateTrxValue) map.get(ICMSConstant.SCC);

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		ISCCertificateTrxValue newTrxValue = proxyMgr.makerCloseRejectedGenerateSCC(trxContext, trxValue);
		// System.out.println("TrxValue: " + newTrxValue);
	}

	public void testCheckerRejectGenerateSCC() throws Exception {
		// System.out.println("In testCheckerRejectGenerateSCC !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getSCCertificate(limitProfile, customer, "20030809000662");
		ISCCertificateTrxValue trxValue = (ISCCertificateTrxValue) map.get(ICMSConstant.SCC);

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		ISCCertificateTrxValue newTrxValue = proxyMgr.checkerRejectGenerateSCC(trxContext, trxValue);
		// System.out.println("TrxValue: " + newTrxValue);
	}

	public void testMakerGenerateSCC() throws Exception {
		// System.out.println("In testMakerGenerateSCC !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getSCCertificate(limitProfile, customer);
		ISCCertificateTrxValue trxValue = (ISCCertificateTrxValue) map.get(ICMSConstant.SCC);

		OBTeam team = new OBTeam();
		team.setTeamID(98765);
		OBCommonUser user = new OBCommonUser();
		user.setUserID(12345);
		trxContext = new OBTrxContext(user, team);

		ISCCertificate sccCert = trxValue.getStagingSCCertificate();
		ISCCertificateItem[] itemList = sccCert.getSCCItemList();
		// System.out.println("Number of SCC Items: " + itemList.length);
		for (int ii = 0; ii < itemList.length; ii++) {
			Amount approvedAmt = itemList[ii].getApprovedLimitAmount();
			Amount activateAmt = new Amount(1000 + ii, approvedAmt.getCurrencyCode());
			itemList[ii].setActivatedAmount(activateAmt);
		}
		sccCert.setSCCItemList(itemList);
		ISCCertificateTrxValue newTrxValue = proxyMgr.makerGenerateSCC(trxContext, trxValue, sccCert);
		// System.out.println("TRXVALUE: " + newTrxValue.getTrxContext());
	}

	public void testHasPendingGenerateSCCTrx() throws Exception {
		// System.out.println("In testHasPendingGenerateSCCTrx !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		boolean result = proxyMgr.hasPendingGenerateSCCTrx(limitProfile);
		// System.out.println("Result: " + result);
	}

	public void testGetSCCertificate() throws Exception {
		// System.out.println("In testGetSCCertificate !!!");
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030715000023L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getSCCertificate(limitProfile, customer);
		// System.out.println("HashMap: " + map);
	}
}