package com.integrosys.cms.app.sccertificate.proxy;

import junit.framework.Test;

import com.integrosys.base.techinfra.test.IOFATestSetup;
import com.integrosys.base.techinfra.test.OFAStartupTestSetup;
import com.integrosys.base.techinfra.test.OFATestSetup;

public class SCCertificateUnitTestSetup extends OFATestSetup {
	SCCertificateUnitTestSetup(Test t) {
		super(t);
	}

	public static Test suite() {
		Test t = SCCertificateUnitTestSetup.suite();
		SCCertificateUnitTestSetup suite = new SCCertificateUnitTestSetup(t);
		return suite;
	}

	public IOFATestSetup[] getTestSetupChain() {
		// OFATestDataTestSetup dataSetup = new
		// OFATestDataTestSetup("TestCountry.sql", null);
		// return new IOFATestSetup[] {(new OFAStartupTestSetup()), dataSetup};
		return new IOFATestSetup[] { new OFAStartupTestSetup() };
	}
}