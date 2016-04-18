/*
 *
 *  * Copyright (c) 2015 Technische Universität Berlin
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *         http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *
 */

package org.openbaton.vnfm;

import org.openbaton.autoscaling.core.decision.DecisionEngine;
import org.openbaton.autoscaling.core.decision.DecisionManagement;
import org.openbaton.autoscaling.core.detection.DetectionEngine;
import org.openbaton.autoscaling.core.detection.DetectionManagement;
import org.openbaton.autoscaling.core.execution.ExecutionEngine;
import org.openbaton.autoscaling.core.execution.ExecutionManagement;
import org.openbaton.autoscaling.core.management.ElasticityManagement;
import org.openbaton.vnfm.configuration.MediaServerProperties;
import org.openbaton.vnfm.core.HistoryManagement;
import org.openbaton.vnfm.core.MediaServerResourceManagement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by mpa on 02.02.16.
 */
@Configuration
@EnableAsync
@EnableScheduling
public class MSBeanConfiguration {

    @Bean
    public MediaServerResourceManagement mediaServerResourceManagement() {
        return new MediaServerResourceManagement();
    }

    @Bean
    public ElasticityManagement elasticityManagement() {
        return new ElasticityManagement();
    }

    @Bean
    public HistoryManagement historyManagement() {
        return new HistoryManagement();
    }

}

