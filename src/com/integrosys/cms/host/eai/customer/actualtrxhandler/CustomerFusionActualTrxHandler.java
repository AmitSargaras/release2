package com.integrosys.cms.host.eai.customer.actualtrxhandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.customer.CustomerMessageBody;
import com.integrosys.cms.host.eai.customer.IBatchCustomerConstant;
import com.integrosys.cms.host.eai.customer.bus.CustomerFusion;
import com.integrosys.cms.host.eai.customer.bus.ICustomerDao;

/**
 * Actual Trx Handler for Customer Fusion
 * 
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.1
 */
public class CustomerFusionActualTrxHandler extends AbstractCommonActualTrxHandler implements IBatchCustomerConstant {

	private ICustomerDao customerDao;

	public ICustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public Message persistActualTrx(Message msg)  {

		String oldCifId = ((CustomerMessageBody) msg.getMsgBody()).getOldCIFId();
		String newCifId = ((CustomerMessageBody) msg.getMsgBody()).getNewCIFId();

		// Set source
		String source = msg.getMsgHeader().getSource();

		storeCustomerFusion(oldCifId, newCifId, source);

		return msg;
	}

	protected void storeCustomerFusion(String oldCifId, String newCifId, String source)  {
		// Load from table and set status to requires update

		Map parameters = new HashMap();
		parameters.put("oldCifId", oldCifId);
		parameters.put("sourceId", source);
		CustomerFusion tmpCustomerFusion = (CustomerFusion) getCustomerDao().retrieveObjectByParameters(parameters,
				CustomerFusion.class);

		if (tmpCustomerFusion == null) {
			CustomerFusion customerFusion = new CustomerFusion();
			// String seq = (new SequenceManager()).getSeqNum(ICMSConstant.
			// SEQUENCE_BATCH_CUSTOMER_FUSION, true);
			// customerFusion.setCmsId(Long.parseLong(seq));
			customerFusion.setOldCifId(oldCifId);
			customerFusion.setNewCifId(newCifId);
			customerFusion.setProcessedIndicator(NOT_PROCESSED);
			customerFusion.setSourceId(source);
			customerFusion.setTimeReceived(new Date(System.currentTimeMillis()));
			customerFusion.setTimeProcessed(null);

			getCustomerDao().store(customerFusion, CustomerFusion.class);
		}
		else {

			tmpCustomerFusion.setNewCifId(newCifId);
			tmpCustomerFusion.setProcessedIndicator(NOT_PROCESSED);
			tmpCustomerFusion.setSourceId(source);
			tmpCustomerFusion.setTimeReceived(new Date(System.currentTimeMillis()));
			tmpCustomerFusion.setTimeProcessed(null);

			getCustomerDao().update(tmpCustomerFusion, CustomerFusion.class);
		}

	}

	/**
	 * THere is no staging for SubProfile
	 */
	public Message persistStagingTrx(Message msg, Object trxValues){
		// There are no Staging for Customer.

		return msg;
	}

	public String getTrxKey() {
		return IEaiConstant.CUSTOMER_KEY;
	}
}
