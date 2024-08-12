package com.integrosys.cms.app.collaborationtask.proxy;

import junit.framework.Test;

import com.integrosys.base.techinfra.test.IOFATestSetup;
import com.integrosys.base.techinfra.test.OFAStartupTestSetup;
import com.integrosys.base.techinfra.test.OFATestSetup;

public class CollaborationTaskUnitTestSetup extends OFATestSetup {
	CollaborationTaskUnitTestSetup(Test t) {
		super(t);
	}

	public static Test suite() {
		Test t = CollaborationTaskUnitTestSetup.suite();
		CollaborationTaskUnitTestSetup suite = new CollaborationTaskUnitTestSetup(t);
		return suite;
	}

	public IOFATestSetup[] getTestSetupChain() {
		// OFATestDataTestSetup dataSetup = new
		// OFATestDataTestSetup("TestCountry.sql", null);
		// return new IOFATestSetup[] {(new OFAStartupTestSetup()), dataSetup};
		return new IOFATestSetup[] { new OFAStartupTestSetup() };
	}
}