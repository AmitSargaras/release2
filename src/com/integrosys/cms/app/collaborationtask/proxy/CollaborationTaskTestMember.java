package com.integrosys.cms.app.collaborationtask.proxy;

import junit.framework.Test;

import com.integrosys.base.techinfra.test.IOFATestSetup;
import com.integrosys.base.techinfra.test.OFATestMember;

public class CollaborationTaskTestMember extends OFATestMember {
	public CollaborationTaskTestMember(Test t) {
		super(t);
	}

	public static Test suite() {
		Test t = CollaborationTaskTestSuite.suite();
		CollaborationTaskUnitTestSetup suite = new CollaborationTaskUnitTestSetup(t);
		return suite;
	}

	public IOFATestSetup[] getTestSetupChain() {
		// OFATestDataTestSetup dataSetup = new
		// OFATestDataTestSetup("c:/junittest/testcountry.sql", null);
		// return new IOFATestSetup[] {dataSetup};
		return null;
	}
}