package com.integrosys.cms.app.generatereq.proxy;

import junit.framework.Test;

import com.integrosys.base.techinfra.test.IOFATestSetup;
import com.integrosys.base.techinfra.test.OFATestMember;

public class GenerateRequestTestMember extends OFATestMember {
	public GenerateRequestTestMember(Test t) {
		super(t);
	}

	public static Test suite() {
		Test t = GenerateRequestTestSuite.suite();
		GenerateRequestUnitTestSetup suite = new GenerateRequestUnitTestSetup(t);
		return suite;
	}

	public IOFATestSetup[] getTestSetupChain() {
		// OFATestDataTestSetup dataSetup = new
		// OFATestDataTestSetup("c:/junittest/testcountry.sql", null);
		// return new IOFATestSetup[] {dataSetup};
		return null;
	}
}