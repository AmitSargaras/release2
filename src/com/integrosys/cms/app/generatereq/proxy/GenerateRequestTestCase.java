package com.integrosys.cms.app.generatereq.proxy;

import java.util.Date;

import junit.framework.TestCase;

import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.generatereq.bus.IDeferralRequest;
import com.integrosys.cms.app.generatereq.bus.IWaiverRequest;
import com.integrosys.cms.app.generatereq.trx.IDeferralRequestTrxValue;
import com.integrosys.cms.app.generatereq.trx.IWaiverRequestTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.proxy.BizStructureProxyFactory;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.proxy.CommonUserProxyFactory;
import com.integrosys.component.user.app.proxy.ICommonUserProxy;

public class GenerateRequestTestCase extends TestCase {
	private IGenerateRequestProxyManager proxyMgr = null;

	private ITrxContext trxContext = null;

	public GenerateRequestTestCase(String name) {
		super(name);
	}

	public void setUp() throws Exception {
		proxyMgr = GenerateRequestProxyManagerFactory.getProxyManager();
	}

	public void tearDown() throws Exception {
	}

	public void testRMApproveGenerateDeferralRequest() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(3000002483L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(2000002483L);
		IDeferralRequestTrxValue trxValue = proxyMgr.getDeferralRequestTrxValue(limitProfile, customer,
				"20030913067300");
		IDeferralRequest deferralReq = trxValue.getStagingDeferralRequest();
		trxContext = new OBTrxContext();
		IDeferralRequestTrxValue newTrxValue = proxyMgr.rmApproveGenerateRequest(trxContext, trxValue);
	}

	public void testRMRejectGenerateDeferralRequest() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(3000002483L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(2000002483L);
		IDeferralRequestTrxValue trxValue = proxyMgr.getDeferralRequestTrxValue(limitProfile, customer,
				"20030913067100");
		IDeferralRequest deferralReq = trxValue.getStagingDeferralRequest();
		trxContext = new OBTrxContext();
		IDeferralRequestTrxValue newTrxValue = proxyMgr.rmRejectGenerateRequest(trxContext, trxValue);
	}

	public void testCheckerApproveGenerateDeferralRequest() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(3000002483L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(2000002483L);
		IDeferralRequestTrxValue trxValue = proxyMgr.getDeferralRequestTrxValue(limitProfile, customer,
				"20030913067300");
		IDeferralRequest deferralReq = trxValue.getStagingDeferralRequest();
		trxContext = new OBTrxContext();
		IDeferralRequestTrxValue newTrxValue = proxyMgr.checkerApproveGenerateRequest(trxContext, trxValue);
	}

	public void testMakerCloseRejectedGenerateDeferralRequest() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(3000002483L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(2000002483L);
		IDeferralRequestTrxValue trxValue = proxyMgr.getDeferralRequestTrxValue(limitProfile, customer,
				"20030913067200");
		IDeferralRequest deferralReq = trxValue.getStagingDeferralRequest();
		trxContext = new OBTrxContext();
		IDeferralRequestTrxValue newTrxValue = proxyMgr.makerCloseRejectedGenerateRequest(trxContext, trxValue);
	}

	public void testMakerEditRejectedGenerateDeferralRequest() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(3000002483L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(2000002483L);
		IDeferralRequestTrxValue trxValue = proxyMgr.getDeferralRequestTrxValue(limitProfile, customer,
				"20030913067200");
		IDeferralRequest deferralReq = trxValue.getStagingDeferralRequest();
		trxContext = new OBTrxContext();
		IDeferralRequestTrxValue newTrxValue = proxyMgr.makerEditRejectedGenerateRequest(trxContext, trxValue,
				deferralReq);
	}

	public void testCheckerRejectGenerateDeferralRequest() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(3000002483L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(2000002483L);
		IDeferralRequestTrxValue trxValue = proxyMgr.getDeferralRequestTrxValue(limitProfile, customer,
				"20030913067200");
		trxContext = new OBTrxContext();
		IDeferralRequestTrxValue newTrxValue = proxyMgr.checkerRejectGenerateRequest(trxContext, trxValue);
	}

	public void testMakerGenerateDeferralRequest() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(3000002484L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(2000002484L);
		IDeferralRequestTrxValue trxValue = proxyMgr.getNewDeferralRequestTrxValue(limitProfile, customer);

		IDeferralRequest deferralReq = trxValue.getStagingDeferralRequest();
		deferralReq.setProposedByName("Proposed Name 1");
		deferralReq.setProposedByDesignation("SCO");
		deferralReq.setProposedBySignNo("12431243");
		deferralReq.setProposedByDate(new Date());

		trxContext = new OBTrxContext();

		IDeferralRequestTrxValue newTrxValue = proxyMgr.makerGenerateRequest(trxContext, trxValue, deferralReq);
//		System.out.println("Value: " + newTrxValue);
	}

	public void testGetDeferralRequestTrxValue() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(3000002484L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(2000002484L);
		IDeferralRequestTrxValue trxValue = proxyMgr.getNewDeferralRequestTrxValue(limitProfile, customer);
		System.out.println("Trx Value: " + trxValue.toString());
	}

	public void testRMApproveGenerateRequest() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(3000002483L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(2000002483L);
		IWaiverRequestTrxValue trxValue = proxyMgr.getWaiverRequestTrxValue(limitProfile, customer, "20030915087900");
		IWaiverRequest waiverReq = trxValue.getStagingWaiverRequest();
		trxContext = new OBTrxContext();
		IWaiverRequestTrxValue newTrxValue = proxyMgr.rmApproveGenerateRequest(trxContext, trxValue);
	}

	public void testRMRejectGenerateRequest() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(3000002483L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(2000002483L);
		IWaiverRequestTrxValue trxValue = proxyMgr.getWaiverRequestTrxValue(limitProfile, customer, "20030915087900");
		IWaiverRequest waiverReq = trxValue.getStagingWaiverRequest();
		trxContext = new OBTrxContext();
		trxContext.setLimitProfile(limitProfile);
		trxContext.setCustomer(customer);

		ICommonUserProxy userProxy = CommonUserProxyFactory.getUserProxy();
		ICommonUser user = userProxy.getUser("FAMSG");
		trxContext.setUser(user);
		ITeam team = (ITeam) BizStructureProxyFactory.getProxy().getTeamByUserID(user.getUserID());
		trxContext.setTeam(team);
		IWaiverRequestTrxValue newTrxValue = proxyMgr.rmRejectGenerateRequest(trxContext, trxValue);
	}

	public void testCheckerApproveGenerateRequest() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(3000002483L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(2000002483L);
		IWaiverRequestTrxValue trxValue = proxyMgr.getWaiverRequestTrxValue(limitProfile, customer, "20030915087900");
		IWaiverRequest waiverReq = trxValue.getStagingWaiverRequest();
		trxContext = new OBTrxContext();
		trxContext.setLimitProfile(limitProfile);
		trxContext.setCustomer(customer);

		ICommonUserProxy userProxy = CommonUserProxyFactory.getUserProxy();
		ICommonUser user = userProxy.getUser("CPCC");
		trxContext.setUser(user);
		ITeam team = (ITeam) BizStructureProxyFactory.getProxy().getTeamByUserID(user.getUserID());
		trxContext.setTeam(team);
		IWaiverRequestTrxValue newTrxValue = proxyMgr.checkerApproveGenerateRequest(trxContext, trxValue);
	}

	public void testMakerCloseRejectedGenerateRequest() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(3000002483L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(2000002483L);
		IWaiverRequestTrxValue trxValue = proxyMgr.getWaiverRequestTrxValue(limitProfile, customer, "20030912066200");
		IWaiverRequest waiverReq = trxValue.getStagingWaiverRequest();
		trxContext = new OBTrxContext();
		IWaiverRequestTrxValue newTrxValue = proxyMgr.makerCloseRejectedGenerateRequest(trxContext, trxValue);
	}

	public void testMakerEditRejectedGenerateRequest() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(3000002483L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(2000002483L);
		IWaiverRequestTrxValue trxValue = proxyMgr.getWaiverRequestTrxValue(limitProfile, customer, "20030915087900");
		IWaiverRequest waiverReq = trxValue.getStagingWaiverRequest();
		trxContext = new OBTrxContext();
		IWaiverRequestTrxValue newTrxValue = proxyMgr.makerEditRejectedGenerateRequest(trxContext, trxValue, waiverReq);
	}

	public void testCheckerRejectGenerateRequest() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(3000002483L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(2000002483L);
		IWaiverRequestTrxValue trxValue = proxyMgr.getWaiverRequestTrxValue(limitProfile, customer, "20030912066200");
		trxContext = new OBTrxContext();
		IWaiverRequestTrxValue newTrxValue = proxyMgr.checkerRejectGenerateRequest(trxContext, trxValue);
	}

	public void testGetWaiverRequestTrxValueByTrxID() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(3000002483L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(2000002483L);
		IWaiverRequestTrxValue trxValue = proxyMgr.getWaiverRequestTrxValue(limitProfile, customer, "20030912066200");
		System.out.println("TRXVALUE: " + trxValue);
	}

	public void testMakerGenerateRequest() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(3000002484L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(2000002484L);
		IWaiverRequestTrxValue trxValue = proxyMgr.getNewWaiverRequestTrxValue(limitProfile, customer);

		IWaiverRequest waiverReq = trxValue.getStagingWaiverRequest();
		waiverReq.setProposedByName("Proposed Name 1");
		waiverReq.setProposedByDesignation("SCO");
		waiverReq.setProposedBySignNo("12431243");
		waiverReq.setProposedByDate(new Date());

		trxContext = new OBTrxContext();
		trxContext.setLimitProfile(limitProfile);
		trxContext.setCustomer(customer);

		ICommonUserProxy userProxy = CommonUserProxyFactory.getUserProxy();
		ICommonUser user = userProxy.getUser("CPCM");
		trxContext.setUser(user);
		ITeam team = (ITeam) BizStructureProxyFactory.getProxy().getTeamByUserID(user.getUserID());
		trxContext.setTeam(team);
		IWaiverRequestTrxValue newTrxValue = proxyMgr.makerGenerateRequest(trxContext, trxValue, waiverReq);
		System.out.println("Value: " + newTrxValue);
	}

	public void testGetWaiverRequestTrxValue() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(3);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(704000013L);
		IWaiverRequestTrxValue trxValue = proxyMgr.getNewWaiverRequestTrxValue(limitProfile, customer);
		System.out.println("Trx Value: " + trxValue.toString());
	}
}