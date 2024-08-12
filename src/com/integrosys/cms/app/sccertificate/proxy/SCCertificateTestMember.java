package com.integrosys.cms.app.sccertificate.proxy;

import junit.framework.Test;

import com.integrosys.base.techinfra.test.IOFATestSetup;
import com.integrosys.base.techinfra.test.OFATestMember;

public class SCCertificateTestMember extends OFATestMember {
	public SCCertificateTestMember(Test t) {
		super(t);
	}

	public static Test suite() {
		Test t = SCCertificateTestSuite.suite();
		SCCertificateUnitTestSetup suite = new SCCertificateUnitTestSetup(t);
		return suite;
	}

	public IOFATestSetup[] getTestSetupChain() {
		// OFATestDataTestSetup dataSetup = new
		// OFATestDataTestSetup("c:/junittest/testcountry.sql", null);
		// return new IOFATestSetup[] {dataSetup};
		return null;
	}
}