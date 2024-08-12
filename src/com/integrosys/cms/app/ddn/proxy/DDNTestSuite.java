package com.integrosys.cms.app.ddn.proxy;

import junit.framework.Test;
import junit.framework.TestSuite;

public class DDNTestSuite extends TestSuite {
	public DDNTestSuite() {
		super();
	}

	public static Test suite() {
		DDNTestSuite suite = new DDNTestSuite();
		// suite.addTest ( new DDNTestCase ("testGetDDN"));
		// suite.addTest(new DDNTestCase("testMakerGenerateDDN"));
		// suite.addTest(new DDNTestCase("testHasPendingGenerateDDNTrx"));
		// suite.addTest(new DDNTestCase("testCheckerRejectGenerateDDN"));
		// suite.addTest(new DDNTestCase("testMakerEditRejectedGenerateDDN"));
		// suite.addTest(new DDNTestCase("testMakerCloseRejectedGenerateDDN"));
		// suite.addTest(new DDNTestCase("testCheckerApproveGenerateDDN"));
		// suite.addTest(new DDNTestCase("testConvertSCCToDDN"));
		return suite;
	}
}