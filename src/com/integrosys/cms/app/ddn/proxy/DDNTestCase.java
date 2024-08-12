package com.integrosys.cms.app.ddn.proxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import junit.framework.TestCase;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.ddn.bus.IDDN;
import com.integrosys.cms.app.ddn.bus.IDDNCustomerDetail;
import com.integrosys.cms.app.ddn.bus.IDDNItem;
import com.integrosys.cms.app.ddn.trx.IDDNTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificate;
import com.integrosys.cms.app.sccertificate.proxy.SCCertificateProxyManagerFactory;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class DDNTestCase extends TestCase {
	private IDDNProxyManager proxyMgr = null;

	private ITrxContext trxContext = null;

	public DDNTestCase(String name) {
		super(name);
	}

	public void setUp() throws Exception {
		proxyMgr = DDNProxyManagerFactory.getDDNProxyManager();
	}

	public void tearDown() throws Exception {
	}

	public void testConvertPartialSCCToDDN() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20031129000081L);

		IPartialSCCertificate pscc = SCCertificateProxyManagerFactory.getSCCertificateProxyManager()
				.getPartialSCCertificateByLimitProfile(limitProfile);
		ITrxContext trxContext = new OBTrxContext();
		IDDNTrxValue trxValue = proxyMgr.convertPartialSCCToDDN(trxContext, limitProfile, pscc);
	}

	public void testConvertSCCToDDN() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20031001153L);

		ISCCertificate scc = SCCertificateProxyManagerFactory.getSCCertificateProxyManager()
				.getSCCertificateByLimitProfile(limitProfile);
		ITrxContext trxContext = new OBTrxContext();
		IDDNTrxValue trxValue = proxyMgr.convertSCCToDDN(trxContext, limitProfile, scc);
	}

	public void testCheckerApproveGenerateDDN() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030819000360L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getDDN(limitProfile, customer, "20030819000965");
		IDDNTrxValue trxValue = (IDDNTrxValue) map.get(ICMSConstant.DDN);
		trxContext = new OBTrxContext();
		IDDNTrxValue newTrxValue = proxyMgr.checkerApproveGenerateDDN(trxContext, trxValue);
	}

	public void testMakerCloseRejectedGenerateDDN() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030819000360L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getDDN(limitProfile, customer, "20030819000965");
		IDDNTrxValue trxValue = (IDDNTrxValue) map.get(ICMSConstant.DDN);
		trxContext = new OBTrxContext();
		IDDNTrxValue newTrxValue = proxyMgr.makerCloseRejectedGenerateDDN(trxContext, trxValue);

	}

	public void testMakerEditRejectedGenerateDDN() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030819000360L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getDDN(limitProfile, customer, "20030819000965");

		IDDNTrxValue trxValue = (IDDNTrxValue) map.get(ICMSConstant.DDN);

		IDDN ddn = trxValue.getStagingDDN();
		ddn.setDeferredToDate(new Date());
		ddn.setDaysValid(15);
		ddn.setCreditOfficerName("Credit Officer 1");
		ddn.setCreditOfficerSignNo("CreditNo1");
		IDDNItem[] itemList = trxValue.getStagingDDN().getDDNItemList();
		Amount amt = itemList[0].getDDNAmount();
		itemList[0].setDDNAmount(new Amount(200, amt.getCurrencyCode()));
		itemList[0].setIsDDNIssuedInd(true);
		ddn.setDDNItemList(itemList);
		trxContext = new OBTrxContext();
		trxContext.setRemarks("Testing edit trx for DDN !!!");
		IDDNTrxValue newTrxValue = proxyMgr.makerEditRejectedGenerateDDN(trxContext, trxValue, ddn);
	}

	public void testCheckerRejectGenerateDDN() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030819000360L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getDDN(limitProfile, customer, "20030819000965");
		IDDNTrxValue trxValue = (IDDNTrxValue) map.get(ICMSConstant.DDN);
		trxContext = new OBTrxContext();
		IDDNTrxValue newTrxValue = proxyMgr.checkerRejectGenerateDDN(trxContext, trxValue);

	}

	public void testHasPendingGenerateDDNTrx() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030819000360L);
		boolean result = proxyMgr.hasPendingGenerateDDNTrx(limitProfile);
//		System.out.println("Result: " + result);
	}

	public void testMakerGenerateDDN() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20030819000360L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20030704000012L);
		HashMap map = proxyMgr.getDDN(limitProfile, customer, new ArrayList(), new ArrayList());

		IDDNTrxValue trxValue = (IDDNTrxValue) map.get(ICMSConstant.DDN);

		IDDN ddn = trxValue.getStagingDDN();
		ddn.setDeferredToDate(new Date());
		ddn.setDaysValid(10);
		ddn.setCreditOfficerName("Credit Officer 1");
		ddn.setCreditOfficerSignNo("CreditNo1");
		IDDNItem[] itemList = trxValue.getStagingDDN().getDDNItemList();
		Amount amt = itemList[0].getDDNAmount();
		itemList[0].setDDNAmount(new Amount(1000, amt.getCurrencyCode()));
		ddn.setDDNItemList(itemList);

		trxContext = new OBTrxContext();

		IDDNTrxValue newTrxValue = proxyMgr.makerGenerateDDN(trxContext, trxValue, ddn);

	}

	public void testGetDDN() throws Exception {
		ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(20031021000591L);
		ICMSCustomer customer = CustomerProxyFactory.getProxy().getCustomer(20031021000124L);
		HashMap map = proxyMgr.getDDN(limitProfile, customer, new ArrayList(), new ArrayList());

		IDDNTrxValue trxValue = (IDDNTrxValue) map.get(ICMSConstant.DDN);

		IDDNCustomerDetail custDetail = (IDDNCustomerDetail) map.get(ICMSConstant.DDN_OWNER);
		IDDNItem[] itemList = trxValue.getDDN().getDDNItemList();
//		System.out.println("Number of item: " + itemList.length);
//		System.out.println("TRXVALUE: " + trxValue.getTransactionID());
//
//		System.out.println("TrxValue: " + itemList[0].getDDNItemID());
//		System.out.println("TrxValue: " + itemList[0].getDDNItemRef());
//		System.out.println("TrxValue: " + itemList[0].getLimitRef());
//		System.out.println("TrxValue: " + itemList[0].getProductDesc());
//		System.out.println("TrxValue: " + itemList[0].getApprovedLimitAmount());
//		System.out.println("TrxValue: " + itemList[0].getActivatedAmount());
//		System.out.println("TrxValue: " + itemList[0].getDDNAmount());
//		System.out.println("TrxValue: " + itemList[0].getIsDDNIssuedInd());
//		System.out.println("TrxValue: " + itemList[0].getIssuedDate());
//		System.out.println("TrxValue: " + itemList[0].getCheckListMap());

	}
}