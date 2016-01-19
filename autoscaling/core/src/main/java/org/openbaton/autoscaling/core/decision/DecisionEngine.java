package org.openbaton.autoscaling.core.decision;

import org.openbaton.autoscaling.core.execution.ExecutionManagement;
import org.openbaton.autoscaling.core.management.VnfrMonitor;
import org.openbaton.autoscaling.core.detection.task.DetectionTask;
import org.openbaton.autoscaling.utils.Utils;
import org.openbaton.catalogue.mano.common.AutoScalePolicy;
import org.openbaton.catalogue.mano.common.ScalingAction;
import org.openbaton.catalogue.mano.record.NetworkServiceRecord;
import org.openbaton.catalogue.mano.record.Status;
import org.openbaton.catalogue.mano.record.VirtualNetworkFunctionRecord;
import org.openbaton.exceptions.NotFoundException;
import org.openbaton.sdk.NFVORequestor;
import org.openbaton.sdk.api.exception.SDKException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ScheduledFuture;

/**
 * Created by mpa on 27.10.15.
 */
@Service
@Scope("singleton")
public class DecisionEngine {

    @Autowired
    private DecisionManagement decisionManagement;

    @Autowired
    private ExecutionManagement executionManagement;

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    private Properties properties;

    private NFVORequestor nfvoRequestor;

    @PostConstruct
    public void init() {
        this.properties = Utils.loadProperties();
        this.nfvoRequestor = new NFVORequestor(this.properties.getProperty("nfvo.username"), this.properties.getProperty("nfvo.password"), this.properties.getProperty("nfvo.ip"), this.properties.getProperty("nfvo.port"), "1");
    }

    public void sendDecision(String nsr_id, String vnfr_id, Set<ScalingAction> actions, long cooldown) {
        executionManagement.execute(nsr_id, vnfr_id, actions, cooldown);
        decisionManagement.finished(vnfr_id);
    }

    public boolean requestScaling(String nsr_id, String vnfr_id) {
        VirtualNetworkFunctionRecord vnfr = null;
        try {
            vnfr = nfvoRequestor.getNetworkServiceRecordAgent().getVirtualNetworkFunctionRecord(nsr_id, vnfr_id);
        } catch (SDKException e) {
            log.warn(e.getMessage(), e);
            return false;
        }
        if (vnfr.getStatus() == Status.ACTIVE) {
            return true;
        }
        return false;
    }

}