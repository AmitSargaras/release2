package com.integrosys.cms.app.cccertificate.proxy;

import junit.framework.Test;

import com.integrosys.base.techinfra.test.IOFATestSetup;
import com.integrosys.base.techinfra.test.OFATestMember;

public class CCCertificateTestMember extends OFATestMember {
	public CCCertificateTestMember(Test t) {
		super(t);
	}

	public static Test suite() {
		Test t = CCCertificateTestSuite.suite();
		CCCertificateUnitTestSetup suite = new CCCertificateUnitTestSetup(t);
		return suite;
	}

	public IOFATestSetup[] getTestSetupChain() {
		// OFATestDataTestSetup dataSetup = new
		// OFATestDataTestSetup("c:/junittest/testcountry.sql", null);
		// return new IOFATestSetup[] {dataSetup};
		return null;
	}
}