package com.integrosys.cms.batch.recurrent;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.cms.app.recurrent.bus.IConvenant;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.RecurrentException;
import com.integrosys.cms.app.recurrent.proxy.IRecurrentProxyManager;
import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.InvalidParameterBatchJobException;
import com.integrosys.cms.batch.factory.BatchJob;

/**
 * <p>
 * Batch program to perform the computation of due date of recurrent and
 * covenant item, subsequently create sub items
 * 
 * Required parameter:
 * <ul>
 * <li>country
 * </ul>
 * 
 * @author hmbao
 * @author Chong Jun Yong
 * @since 2006/10/09 06:10:07
 */
public class RecurrentDueDateMain implements BatchJob {

	private final Logger logger = LoggerFactory.getLogger(RecurrentDueDateMain.class);

	private IRecurrentDueDateDAO recurrentDueDateDao;

	private IRecurrentProxyManager recurrentProxyManager;

	/**
	 * @param recurrentDueDateDao the recurrentDueDateDao to set
	 */
	public void setRecurrentDueDateDao(IRecurrentDueDateDAO recurrentDueDateDao) {
		this.recurrentDueDateDao = recurrentDueDateDao;
	}

	/**
	 * @return the recurrentDueDateDao
	 */
	public IRecurrentDueDateDAO getRecurrentDueDateDao() {
		return recurrentDueDateDao;
	}

	/**
	 * @param recurrentProxyManager the recurrentProxyManager to set
	 */
	public void setRecurrentProxyManager(IRecurrentProxyManager recurrentProxyManager) {
		this.recurrentProxyManager = recurrentProxyManager;
	}

	/**
	 * @return the recurrentProxyManager
	 */
	public IRecurrentProxyManager getRecurrentProxyManager() {
		return recurrentProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public RecurrentDueDateMain() {
	}

	public void execute(Map context) throws BatchJobException {
		/*Commentted for HDFC requirement */
//		String countryCode = (String) context.get("country");
		String countryCode = "IN";

		if (countryCode == null) {
			throw new InvalidParameterBatchJobException(
					"missing parameter 'country' that required for the jdbc retrieval.");
		}

		executeInternal(countryCode);
	}

	/**
	 * Process recurrent due date computation
	 */
	private void executeInternal(String countryCode) {
		int recurrentItemCreated = 0;
		IRecurrentCheckListItem[] recurrentItem = getRecurrentDueDateDao().getRecurrentCheckListItemList(countryCode);
		if ((null != recurrentItem) && (recurrentItem.length != 0)) {
			for (int i = 0; i < recurrentItem.length; i++) {
				try {
					getRecurrentProxyManager().createDueDateEntries(recurrentItem[i]);
					recurrentItemCreated++;
				}
				catch (RecurrentException e) {
					logger.warn("failed to perform due date computation of recurrent item, id ["
							+ recurrentItem[i].getCheckListItemID() + "], proceed to next item", e);
				}
			}
		}

		int covenantItemCreated = 0;
		IConvenant[] covenants = getRecurrentDueDateDao().getCovenantList(countryCode);
		if ((null != covenants) && (covenants.length != 0)) {
			for (int i = 0; i < covenants.length; i++) {
				try {
					getRecurrentProxyManager().createDueDateEntries(covenants[i]);
					covenantItemCreated++;
				}
				catch (RecurrentException e) {
					logger.warn("failed to perform due date computation of covenant item, id ["
							+ covenants[i].getConvenantID() + "], proceed to next item", e);
				}
			}
		}

		logger.info("Total recurrent item retrieved [" + (recurrentItem != null ? recurrentItem.length : 0) + "] created successfully ["
				+ recurrentItemCreated + "], and covenant item retrieved [" + (covenants != null ? covenants.length : 0)
				+ "] created successfully [" + covenantItemCreated + "]");

	}
}