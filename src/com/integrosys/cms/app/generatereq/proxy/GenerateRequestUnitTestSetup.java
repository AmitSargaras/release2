package com.integrosys.cms.app.generatereq.proxy;

import junit.framework.Test;

import com.integrosys.base.techinfra.test.IOFATestSetup;
import com.integrosys.base.techinfra.test.OFAStartupTestSetup;
import com.integrosys.base.techinfra.test.OFATestSetup;

public class GenerateRequestUnitTestSetup extends OFATestSetup {
	GenerateRequestUnitTestSetup(Test t) {
		super(t);
	}

	public static Test suite() {
		Test t = GenerateRequestUnitTestSetup.suite();
		GenerateRequestUnitTestSetup suite = new GenerateRequestUnitTestSetup(t);
		return suite;
	}

	public IOFATestSetup[] getTestSetupChain() {
		// OFATestDataTestSetup dataSetup = new
		// OFATestDataTestSetup("TestCountry.sql", null);
		// return new IOFATestSetup[] {(new OFAStartupTestSetup()), dataSetup};
		return new IOFATestSetup[] { new OFAStartupTestSetup() };
	}
}