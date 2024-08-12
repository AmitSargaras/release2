package com.integrosys.cms.app.securityenvelope.bus;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA. User: Erene Wong Date: Jan 25, 2010 Time: 6:22:14 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractSecEnvelopeBusManager implements ISecEnvelopeBusManager {

	private ISecEnvelopeDao secEnvelopeDao;

	private ISecEnvelopeJdbc secEnvelopeJdbc;

	public ISecEnvelopeDao getSecEnvelopeDao() {
		return secEnvelopeDao;
	}

	public void setSecEnvelopeDao(ISecEnvelopeDao secEnvelopeDao) {
		this.secEnvelopeDao = secEnvelopeDao;
	}

	public ISecEnvelopeJdbc getSecEnvelopeJdbc() {
		return secEnvelopeJdbc;
	}

	public void setSecEnvelopeJdbc(ISecEnvelopeJdbc secEnvelopeJdbc) {
		this.secEnvelopeJdbc = secEnvelopeJdbc;
	}

	public ISecEnvelope createSecEnvelope(ISecEnvelope secEnvelope) {
		return getSecEnvelopeDao().createSecEnvelope(getSecEnvelopeEntityName(), secEnvelope);
	}

	public ISecEnvelope getSecEnvelope(long key) {
		return getSecEnvelopeDao().getSecEnvelope(getSecEnvelopeEntityName(), key);
	}

	public ISecEnvelope updateSecEnvelope(ISecEnvelope secEnvelope) {
		return getSecEnvelopeDao().updateSecEnvelope(getSecEnvelopeEntityName(), secEnvelope);
	}

	public List getAllSecEnvelope() {
		return getSecEnvelopeJdbc().getAllSecEnvelope();
	}

    public List getAllSecEnvelopeItemStaging(){
        return getSecEnvelopeJdbc().getAllSecEnvelopeItemStaging();
    }

    public int getNumDocItemInEnvelope(String envBarcode){
        return getSecEnvelopeJdbc().getNumDocItemInEnvelope(envBarcode);
    }

    public List getActualSecEnvelope(long lspLmtProfileId){
		return getSecEnvelopeJdbc().getActualSecEnvelope(lspLmtProfileId);
	}
    
    public List getEnvelopeItemByLimitProfileId(long lspLmtProfileId){
		return getSecEnvelopeDao().getEnvelopeItemByLimitProfileId(lspLmtProfileId);
	}

	public abstract String getSecEnvelopeEntityName();

}