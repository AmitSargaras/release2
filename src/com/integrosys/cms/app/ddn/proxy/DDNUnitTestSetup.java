package com.integrosys.cms.app.ddn.proxy;

import junit.framework.Test;

import com.integrosys.base.techinfra.test.IOFATestSetup;
import com.integrosys.base.techinfra.test.OFAStartupTestSetup;
import com.integrosys.base.techinfra.test.OFATestSetup;

public class DDNUnitTestSetup extends OFATestSetup {
	DDNUnitTestSetup(Test t) {
		super(t);
	}

	public static Test suite() {
		Test t = DDNUnitTestSetup.suite();
		DDNUnitTestSetup suite = new DDNUnitTestSetup(t);
		return suite;
	}

	public IOFATestSetup[] getTestSetupChain() {
		// OFATestDataTestSetup dataSetup = new
		// OFATestDataTestSetup("TestCountry.sql", null);
		// return new IOFATestSetup[] {(new OFAStartupTestSetup()), dataSetup};
		return new IOFATestSetup[] { new OFAStartupTestSetup() };
	}
}