/*
 * CustomerFusionMain.java
 *
 * Created on May 22, 2007, 10:16 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.integrosys.cms.batch.customer;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.batch.factory.TransactionControlledBatchJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Calendar;
import java.util.Map;

/**
 *
 * @author OEM
 */

/**
 * Modified by IntelliJ IDEA.
 *
 * @author Andy Wong
 * @since May 18, 2009
 * Spring conversion
 */
public class CustomerFusionMain implements TransactionControlledBatchJob {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private CustomerFusionDAO dao;
    private TransactionTemplate transactionTemplate;

    public CustomerFusionDAO getDao() {
        return dao;
    }

    public void setDao(CustomerFusionDAO dao) {
        this.dao = dao;
    }

    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public void execute(Map context) throws BatchJobException {

        try {
            getTransactionTemplate().execute(new TransactionCallback() {
                public Object doInTransaction(TransactionStatus status) {
                    OBCustomerFusion[] customers = dao.getCustomerOldIdForFusion();

                    if (customers.length == 0) {
                        DefaultLogger.debug(this, "No customer fusion.");
                    }

                    for (int loop = 0; loop < customers.length; loop++) {
                        try {
                            long cmsLeMainProfileId = getCustomerIdFromDBOrSource(customers[loop].getNewLeId(),
                                    ICustomerDAOConstants.GCIF_SOURCE);
                            long oldCmsLeMainProfileId = dao.getCmsLeMainProfileId(customers[loop].getOldLeId(),
                                    ICustomerDAOConstants.GCIF_SOURCE);

                            if (cmsLeMainProfileId != ICMSConstant.LONG_INVALID_VALUE) {
                                processCustomerFusion(customers[loop].getNewLeId(), customers[loop].getOldLeId(),
                                        ICustomerDAOConstants.GCIF_SOURCE, cmsLeMainProfileId, oldCmsLeMainProfileId);

                                customers[loop].setTimeProcessed(Calendar.getInstance().getTime());
                                customers[loop].setProcessedIndicator('Y');
                            } else {
                                // Set ProcessIndicator to N
                                // Once the new CIFID got updated . The batch will pick
                                // up same record to process the next da

                                customers[loop].setProcessedIndicator('N');

                            }
                            // Single Customer
                            OBCustomerFusion[] customer = new OBCustomerFusion[]{customers[loop]};

                            // Update Customer Fusion Table
                            dao.updateObCustomerFusion(customer);
                            
                        } catch (Exception ex) {
                            logger.error("Error in fusing cifFusionId : " + customers[loop].getCifFusionId(), ex);
                            throw new IncompleteBatchJobException("failed to perform customer fusion, proceed to next batch", ex);
                        }
                    }
                    return null;
                }
            });
        } catch (IncompleteBatchJobException e) {
            logger.warn("failed customer fusion job for this batch.", e);
        }
    }

    public long getCustomerIdFromDBOrSource(String newLeId, String source) throws Exception {
        long cmsLeMainProfileId = dao.getCmsLeMainProfileId(newLeId, source);

        if (cmsLeMainProfileId != ICMSConstant.LONG_INVALID_VALUE) {
            return cmsLeMainProfileId;
        } else {
            //Andy Wong: default main profile Id when record not found in DB
//			EAICustomerHelper.getInstance().getCustomerByCIFNumber(newLeId, source);
            return ICMSConstant.LONG_INVALID_VALUE;
        }
    }

    private void processCustomerFusion(String newLeId, String oldLeId, String source, long cmsLeMainProfileId,
                                       long oldCmsLeMainProfileId) throws Exception {
        dao.singleTransaction(newLeId, oldLeId, source, cmsLeMainProfileId, oldCmsLeMainProfileId);
    }
}