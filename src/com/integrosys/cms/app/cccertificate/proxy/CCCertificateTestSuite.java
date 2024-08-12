package com.integrosys.cms.app.cccertificate.proxy;

import junit.framework.Test;
import junit.framework.TestSuite;

public class CCCertificateTestSuite extends TestSuite {
	public CCCertificateTestSuite() {
		super();
	}

	public static Test suite() {
		CCCertificateTestSuite suite = new CCCertificateTestSuite();
		// suite.addTest ( new CCCertificateTestCase
		// ("testGetCCCertificateSummaryList"));
		// suite.addTest ( new CCCertificateTestCase ("testGetCCCertificate"));
		// suite.addTest ( new CCCertificateTestCase ("testMakerGenerateCCC"));
		// suite.addTest ( new CCCertificateTestCase
		// ("testGetCCCertificateByTrx"));
		// suite.addTest ( new CCCertificateTestCase
		// ("testCheckerApproveGenerateCCC"));
		// suite.addTest ( new CCCertificateTestCase
		// ("testHasPendingGenerateCCCTrx"));
		// suite.addTest ( new CCCertificateTestCase
		// ("testCheckerRejectGenerateCCC"));
		// suite.addTest ( new CCCertificateTestCase
		// ("testMakerCloseRejectedGenerateCCC"));
		// suite.addTest ( new CCCertificateTestCase
		// ("testMakerEditRejectedGenerateCCC"));
		// suite.addTest ( new CCCertificateTestCase
		// ("testGetNoOfCCCRequired"));
		// suite.addTest ( new CCCertificateTestCase
		// ("testGetNoOfCCCGenerated"));
		suite.addTest(new CCCertificateTestCase("testSystemClose"));
		return suite;
	}
}