package com.integrosys.cms.host.eai.customer.actualtrxhandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.customer.CustomerMessageBody;
import com.integrosys.cms.host.eai.customer.IBatchCustomerConstant;
import com.integrosys.cms.host.eai.customer.bus.CustomerUpdate;
import com.integrosys.cms.host.eai.customer.bus.ICustomerDao;

/**
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.1
 */
public class CustomerUpdateActualTrxHandler extends AbstractCommonActualTrxHandler implements IBatchCustomerConstant {

	private ICustomerDao customerDao;

	public ICustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public Message persistActualTrx(Message msg) {

		String cifId = ((CustomerMessageBody) msg.getMsgBody()).getCIFId();

		// Set source
		String source = msg.getMsgHeader().getSource();

		storeCustomerUpdate(cifId, source);

		return msg;
	}

	protected void storeCustomerUpdate(String cifId, String source) {

		Map parameters = new HashMap();
		parameters.put("cifId", cifId);
		parameters.put("sourceId", source);
		parameters.put("processedIndicator", new Character(NOT_PROCESSED));

		Object customerObject = getCustomerDao().retrieveObjectByParameters(parameters, CustomerUpdate.class);

		if (customerObject == null) {
			CustomerUpdate customerUpdate = new CustomerUpdate();

			// customerUpdate.setCmsId(Long.parseLong(seq));
			customerUpdate.setCifId(cifId);
			customerUpdate.setProcessedIndicator(NOT_PROCESSED);
			customerUpdate.setSourceId(source);
			customerUpdate.setTimeReceived(new Date(System.currentTimeMillis()));
			customerUpdate.setTimeProcessed(null);

			getCustomerDao().store(customerUpdate, CustomerUpdate.class);
		}

	}

	/**
	 * THere is no staging for SubProfile
	 */
	public Message persistStagingTrx(Message msg, Object trxValues) {
		// There are no Staging for Customer.

		return msg;
	}

	public String getTrxKey() {
		return IEaiConstant.CUSTOMER_KEY;
	}
}