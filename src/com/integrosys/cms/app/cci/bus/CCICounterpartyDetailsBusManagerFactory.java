package com.integrosys.cms.app.cci.bus;

public class CCICounterpartyDetailsBusManagerFactory{
    
    /**
     * Default Constructor
     */
    public CCICounterpartyDetailsBusManagerFactory()
    {}

    /**
     * Create the Counterparty Details business manager.
     *
     * @return Counterparty Details business manager
     */
    public static ICCICounterpartyDetailsBusManager getActualCCICounterpartyDetailsBusManager() {
        return new CCICounterpartyDetailsBusManagerImpl();
    }

    /**
     * Create the stage ICCICounterpartyDetailsBusManager
     *
     * @return stage CCI CounterpartyDetails Bus Manager
     */
    public static ICCICounterpartyDetailsBusManager getStagingCCICounterpartyDetailsBusManager() {
        return new CCICounterpartyDetailsBusManagerStagingImpl();
    }


}
