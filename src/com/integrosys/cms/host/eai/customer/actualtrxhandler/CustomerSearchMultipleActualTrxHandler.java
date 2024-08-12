package com.integrosys.cms.host.eai.customer.actualtrxhandler;

import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.customer.CustomerEnquiryMessageBody;
import com.integrosys.cms.host.eai.customer.CustomerJdbcImpl;
import com.integrosys.cms.host.eai.customer.IBatchCustomerConstant;
import com.integrosys.cms.host.eai.customer.SearchDetail;
import com.integrosys.cms.host.eai.customer.SearchDetailResult;
import com.integrosys.cms.host.eai.customer.SearchHeader;
import com.integrosys.cms.host.eai.customer.bus.ICustomerDao;

/**
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.1
 */
public class CustomerSearchMultipleActualTrxHandler extends AbstractCommonActualTrxHandler implements
		IBatchCustomerConstant {

	private ICustomerDao customerDao;

	public ICustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public Message persistActualTrx(Message msg) {

		CustomerEnquiryMessageBody msgBody = ((CustomerEnquiryMessageBody) msg.getMsgBody());

		SearchHeader sh = msgBody.getSearchHeader();
		SearchDetail sb = msgBody.getSearchDetail();

		String sid = msg.getMsgHeader().getMessageRefNum();

		sh.setSID(sid);

		// Check is there any error send from source
		//String errMsg = msg.getMsgHeader().getErrMsg();

		try {

			// Update Search Header using DAO
			CustomerJdbcImpl dao = new CustomerJdbcImpl();

			String SID = sh.getSID();
			long totalFound = Long.parseLong(sh.getTotalRecord());

			/*if (StringUtils.isEmpty(errMsg)) {
				sh.setStatus(IEaiConstant.STAT_SUCCESS);
			}
			else {
				sh.setStatus(IEaiConstant.STAT_EXCEPTION);
			}
*/
			/*Remove the errMsg in message header so remove the codes that check the status*/
			/*And change the updatesearch method by removing sttus and errMsg para*/
			String status = sh.getStatus();
			String dbkey = sh.getDBKey();
			//dao.updateSearch(SID, totalFound, status, errMsg, dbkey);
			dao.updateSearch(SID, totalFound, dbkey);
		}
		catch (Exception e) {
			logger.error("encounter error when update customer search", e);
		}

		// Result Details
		try {

			java.util.Vector v = sb.getResult();

			for (int i = 0; i < v.size(); i++) {

				SearchDetailResult sdr = (SearchDetailResult) v.get(i);
				sdr.setSID(sid);

				getCustomerDao().store(sdr, SearchDetailResult.class);
			}
		}
		catch (Exception e) {
			sh.setStatus(IEaiConstant.STAT_EXCEPTION);
			logger.error("encounter error when update customer search", e);
		}

		return msg;
	}

	/**
	 * THere is no staging for SubProfile
	 */
	public Message persistStagingTrx(Message msg, Object trxValues) {
		// There are no Staging for Customer.

		return msg;
	}

}