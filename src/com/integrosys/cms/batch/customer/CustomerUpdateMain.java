package com.integrosys.cms.batch.customer;

import java.util.Vector;

import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorAdapter;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;
import com.integrosys.cms.batch.common.BatchJobTrxUtil;
import com.integrosys.cms.batch.common.StartupInit;
import com.integrosys.cms.host.eai.customer.EAICustomerHelper;

/**
 * @author allen
 * 
 */
public class CustomerUpdateMain extends AbstractMonitorAdapter {
	public void start(String countryCode, SessionContext context) throws EventMonitorException {
		DefaultLogger.debug(this, "- Start Job -");

		try {
			doWork(countryCode, context);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e);

			throw new EventMonitorException(e);
		}
	}

	public CustomerUpdateMain() {
		StartupInit.init();
	}

	/**
	 * @param countryCode
	 * @param context
	 * @throws Exception
	 */
	public void doWork(String countryCode, SessionContext context) throws Exception {

		int interval = PropertyManager.getInt("customer.update.interval", 1000);

		try {

			CustomerUpdateDAO dao = new CustomerUpdateDAO();

			// Get list to be updated from DB table
			BatchJobTrxUtil trxUtil = new BatchJobTrxUtil(context);
			trxUtil.beginUserTrx();
			Vector updateVector = dao.getCustomerListForUpdate();
			trxUtil.commitUserTrx();
			trxUtil = null;

			int start = 0;
			int end = 0;

			// Further divide to smaller units .
			while (true) {

				start = end;

				end = start + 100;

				if (end > updateVector.size()) {
					end = updateVector.size();
				}
				DefaultLogger.debug(this, "----Customer Update ----------");
				DefaultLogger.debug(this, "Start :" + start + ", End :" + end);

				Vector singleVector = new Vector(updateVector.subList(start, end));

				// If empty , exit loop
				if (singleVector.size() == 0) {
					break;
				}

				try {
					updateCustomer(context, dao, singleVector);
				}
				catch (Exception ee) {
					DefaultLogger.error(this, ee.getMessage(), ee);

				}

				start = end;

				try {
					Thread.sleep(interval);
				}
				catch (Exception eee) {
					eee.printStackTrace();
				}

			}

		}
		catch (Exception e) {
			throw e;
		}

	}

	/*
	 * public void doWork(String countryCode, SessionContext context) throws
	 * Exception { BatchJobTrxUtil trxUtil = new BatchJobTrxUtil(context);
	 * 
	 * try { //trxUtil.beginUserTrx();
	 * 
	 * CustomerUpdateDAO dao = new CustomerUpdateDAO(); // Get list to be
	 * updated from DB table // Send message to host/ source Vector updateVector
	 * = dao.getCustomerListForUpdate();
	 * 
	 * if (updateVector == null || updateVector.size() == 0) {
	 * DefaultLogger.info(this, "No customers to be updated.");
	 * 
	 * trxUtil.commitUserTrx();
	 * 
	 * return; } // Update DB table to be processed. Vector updatedVector =
	 * sendCustomerEnquiryMessage(updateVector);
	 * 
	 * updateProcessedCustomers(dao, updatedVector, trxUtil);
	 * 
	 * trxUtil.commitUserTrx(); } catch (Exception e) { throw e; } }
	 */
	/**
	 * Update Single Customer Unit at a time
	 * 
	 * @param trxUtil
	 * @param dao
	 * @param updateVector
	 * @throws Exception
	 */
	private void updateCustomer(SessionContext context, CustomerUpdateDAO dao, Vector updateVector) throws Exception {

		BatchJobTrxUtil trxUtil = new BatchJobTrxUtil(context);
		try {

			trxUtil.beginUserTrx();
			Vector updatedVector = sendCustomerEnquiryMessage(updateVector);
			updateProcessedCustomers(dao, updatedVector
			// , trxUtil
			);
			trxUtil.commitUserTrx();
		}
		catch (Exception e) {
			DefaultLogger.error(this, e.getMessage(), e);
			trxUtil.rollbackUserTrx();
		}

		trxUtil = null;

	}

	/**
	 * @param updateVector
	 * @return
	 * @throws Exception
	 */
	private Vector sendCustomerEnquiryMessage(Vector updateVector) throws Exception {

		int interval = PropertyManager.getInt("customer.update.single.interval", 100);

		Vector updatedVector = new Vector();

		for (int i = 0; i < updateVector.size(); i++) {
			OBCustomerUpdate obCustomerUpdate = (OBCustomerUpdate) updateVector.get(i);

			String cifNumber = obCustomerUpdate.getCifId();
			String sourceId = obCustomerUpdate.getSourceId();

			// try {
			/*
			 * ICMSCustomer customerOB = CustomerProxyFactory.getProxy()
			 * .getCustomerByCIFSourceFromDB(cifNumber, sourceId);
			 */

			// Customer in DB
			// if (customerOB != null
			// && ICMSConstant.LONG_INVALID_VALUE != customerOB
			// .getLegalEntity().getLEID()) {
			// DefaultLogger.debug(this, "Update " + cifNumber + " : " + sourceId);

			Thread.sleep(interval); // To reduce load, can use batch window
			// period, divided by number of records
			// to be processed

			// Get Customer from CIF Source
			EAICustomerHelper.getInstance().getCustomerByCIFNumber(cifNumber, sourceId);

			// }

			updatedVector.add(obCustomerUpdate);

			/*
			 * } catch (Exception e) { DefaultLogger.debug(this,
			 * "Error updating " + cifNumber + " : " + sourceId); }
			 */

		}
		return updatedVector;
	}

	private void updateProcessedCustomers(CustomerUpdateDAO customerUpdateDAO, Vector updatedVector
	// , BatchJobTrxUtil trxUtil
	) throws Exception {
		int size = 0;

		if ((updatedVector == null) || ((size = updatedVector.size()) == 0)) {
			return;
		}

		String cifNumber = null;
		String sourceId = null;

		for (int i = 0; i < size; i++) {
			try {
				OBCustomerUpdate obCustomerUpdate = (OBCustomerUpdate) updatedVector.get(i);

				cifNumber = obCustomerUpdate.getCifId();
				sourceId = obCustomerUpdate.getSourceId();

				customerUpdateDAO.updateProcessedCustomer(obCustomerUpdate);
			}
			catch (Exception e) {
				//DefaultLogger.debug(this, "Rolling back... " + cifNumber + " : " + sourceId);
			}
		}
	}
}
