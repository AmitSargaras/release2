package com.integrosys.cms.host.stp.proxy;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.limit.proxy.IFacilityProxy;
import com.integrosys.cms.host.stp.bus.IStpMasterTrans;
import com.integrosys.cms.host.stp.bus.IStpTrans;
import com.integrosys.cms.host.stp.bus.IStpTransBusManager;
import com.integrosys.cms.host.stp.bus.OBStpMasterTrans;
import com.integrosys.cms.host.stp.chain.IStpCatalogLoader;
import com.integrosys.cms.host.stp.common.IStpConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Stp Asynchronous Message Sender to host (SIBS) for all MBASE interface in real-time
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Aug 29, 2008
 * Time: 4:16:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class StpAsyncProxyImpl extends TimerTask implements IStpAsyncProxy, IStpConstants {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private IStpTransBusManager stpTransBusManager;
    private IStpCatalogLoader stpStpCatalogLoader;
    private IFacilityProxy facilityProxy;
    private ICollateralProxy collateralProxy;
    private int taskInterval;
    private boolean isRunning = false;
    private final Object runningMonitor = new Object();
    private final List localIpList = new ArrayList();

    public IStpTransBusManager getStpTransBusManager() {
        return stpTransBusManager;
    }

    public void setStpTransBusManager(IStpTransBusManager stpTransBusManager) {
        this.stpTransBusManager = stpTransBusManager;
    }

    public IStpCatalogLoader getStpCatalogLoader() {
        return stpStpCatalogLoader;
    }

    public void setStpCatalogLoader(IStpCatalogLoader stpStpCatalogLoader) {
        this.stpStpCatalogLoader = stpStpCatalogLoader;
    }

    public IFacilityProxy getFacilityProxy() {
        return facilityProxy;
    }

    public void setFacilityProxy(IFacilityProxy facilityProxy) {
        this.facilityProxy = facilityProxy;
    }

    public ICollateralProxy getCollateralProxy() {
        return collateralProxy;
    }

    public void setCollateralProxy(ICollateralProxy collateralProxy) {
        this.collateralProxy = collateralProxy;
    }

    public int getTaskInterval() {
        return taskInterval;
    }

    public void setTaskInterval(int taskInterval) {
        this.taskInterval = taskInterval;
    }

    public StpAsyncProxyImpl() {
        //Andy Wong, 1 April 2009: retrieve all local IP, 1 machine can have multiple IP when multiple network card is installed
        try {
            Enumeration netInts = NetworkInterface.getNetworkInterfaces();
            while (netInts.hasMoreElements()) {
                NetworkInterface o = (NetworkInterface) netInts.nextElement();
                Enumeration intAddrs = o.getInetAddresses();

                while (intAddrs.hasMoreElements()) {
                    InetAddress add = (InetAddress) intAddrs.nextElement();
                    localIpList.add(add.getHostAddress());
                }
            }
        } catch (SocketException e) {
            logger.error("Socket exception in AsyncPoller.", e);
        }
    }

    /**
     * Method to submit Stp Async task from UI
     *
     * @param transactionId
     * @param referenceId
     * @param transactionType
     */
    public void submitTask(String transactionId, long referenceId, String transactionType) {
        Date todayDate = new Date();
        logger.debug("StpAsyncProxyImpl.submitTask: trx Id=" + transactionId + " ref Id=" + referenceId + " trx Type=" + transactionType);

        IStpMasterTrans masterTrans = getStpTransBusManager().getMasterTransByTransactionId(transactionId);
        if (masterTrans != null) {
            masterTrans.setStatus(MASTER_TRX_QUEUE);
            masterTrans.setLastSubmissionDate(new Timestamp(todayDate.getTime()));
            masterTrans = getStpTransBusManager().updateMasterTrans(masterTrans);
        } else {
            masterTrans = new OBStpMasterTrans();
            masterTrans.setReferenceId(new Long(referenceId));
            masterTrans.setTransactionType(transactionType);
            masterTrans.setStatus(MASTER_TRX_QUEUE);
            masterTrans.setTransactionId(transactionId);
            masterTrans.setLastSubmissionDate(new Timestamp(todayDate.getTime()));
            masterTrans = getStpTransBusManager().createMasterTrans(masterTrans);
        }
    }

    public void run() {
        //only start the poller when configured IP matches one of the local IP
        if (localIpList.contains(PropertyManager.getValue(STP_ASYNC_POLLER_ENABLED))) {
            if (this.isRunning) {
                return;
            }

            synchronized (runningMonitor) {
                this.isRunning = true;
            }

            try {
                logger.info("###############################################################################");
                logger.info("##### [StpAsyncProxyImpl] Ready to start cycle");

                List taskList = getStpTransBusManager().getMasterTransByStatus(Arrays.asList(new String[]{MASTER_TRX_QUEUE, MASTER_TRX_LOADING}));
                logger.info("##### [StpAsyncProxyImpl] Items found: " + taskList.size());

                for (Iterator iterator = taskList.iterator(); iterator.hasNext();) {
                    IStpMasterTrans masterTrans = (IStpMasterTrans) iterator.next();
                    masterTrans.setStatus(MASTER_TRX_LOADING);
                    getStpTransBusManager().updateMasterTrans(masterTrans);

                    logger.info("##### [StpAsyncProxyImpl] Process transaction: trx Id=" + masterTrans.getTransactionId() + " ref Id=" + masterTrans.getReferenceId() + " trx Type=" + masterTrans.getTransactionType());
                    try {
                        getStpCatalogLoader().executeCommand(masterTrans);
                        persistStpTransHistory(masterTrans);
                        Thread.sleep(taskInterval);
                    } catch (Exception e) {
                        logger.error("Exception caught in StpAsyncProxyImpl run.", e);
                        masterTrans.setStatus(MASTER_TRX_RESET);
                        getStpTransBusManager().updateMasterTrans(masterTrans);
                    }
                }
            } catch (Exception e) {
                logger.error("Fatal exception in AsyncPoller.", e);
            }

            synchronized (runningMonitor) {
                this.isRunning = false;
            }
        }
    }

    /**
     * Persist stp trans history into table, replicate original stp trans
     * insert when trxUID changed, update otherwise
     *
     * @param masterTrans
     */
    private void persistStpTransHistory(IStpMasterTrans masterTrans) {
        if (masterTrans.getTrxEntriesSet() != null) {
            for (Iterator iterator = masterTrans.getTrxEntriesSet().iterator(); iterator.hasNext();) {
                boolean histFound = false;
                IStpTrans ori = (IStpTrans) iterator.next();
                IStpTrans hist = getStpTransBusManager().getTransHistoryByTrxUID(ori.getTrxUID());
                if (hist != null) {
                    histFound = true;
                }

                hist = (IStpTrans) ReplicateUtils.replicateObject(ori, new String[0]);
                hist.setMasterTrxId(masterTrans.getMasterTrxId());

                if (histFound) {
                    getStpTransBusManager().updateTransHistory(hist);
                } else {
                    ori.setTrxHistoryId(getStpTransBusManager().createTransHistory(hist).getTrxHistoryId());
                }
            }
        }
        getStpTransBusManager().updateMasterTrans(masterTrans);
    }
}
