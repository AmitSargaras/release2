package com.integrosys.cms.app.custgrpi.bus;

public class CustGrpIdentifierBusManagerFactory{

    /**
     * Default Constructor
     */
    public CustGrpIdentifierBusManagerFactory()
    {}

    /**
     * Create the Counterparty Details business manager.
     *
     * @return Counterparty Details business manager
     */
    public static ICustGrpIdentifierBusManager getActualCustGrpIdentifierBusManager() {
        return new CustGrpIdentifierBusManagerImpl();
    }

    /**
     * Create the stage CustGrpIdentifierBusManager
     *
     * @return stage CustGrpIdentifierBusManager Bus Manager
     */
    public static ICustGrpIdentifierBusManager getStagingCustGrpIdentifierBusManager() {
        return new CustGrpIdentifierBusManagerStagingImpl();
    }


}
