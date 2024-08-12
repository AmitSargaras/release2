package com.integrosys.cms.app.cccertificate.proxy;

import junit.framework.Test;

import com.integrosys.base.techinfra.test.IOFATestSetup;
import com.integrosys.base.techinfra.test.OFAStartupTestSetup;
import com.integrosys.base.techinfra.test.OFATestSetup;

public class CCCertificateUnitTestSetup extends OFATestSetup {
	CCCertificateUnitTestSetup(Test t) {
		super(t);
	}

	public static Test suite() {
		Test t = CCCertificateUnitTestSetup.suite();
		CCCertificateUnitTestSetup suite = new CCCertificateUnitTestSetup(t);
		return suite;
	}

	public IOFATestSetup[] getTestSetupChain() {
		// OFATestDataTestSetup dataSetup = new
		// OFATestDataTestSetup("TestCountry.sql", null);
		// return new IOFATestSetup[] {(new OFAStartupTestSetup()), dataSetup};
		return new IOFATestSetup[] { new OFAStartupTestSetup() };
	}
}