/*
 * Copyright 2016 Mateusz Rasiński
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.mateuszrasinski.fundtracker.infrastructure.scheduling;

import com.github.mateuszrasinski.fundtracker.application.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class FundReloadingScheduler implements SchedulingConfigurer {

    @Autowired
    private FundService fundService;
    @Autowired
    private SchedulingProperties schedulingProperties;

    @Bean(destroyMethod="shutdown")
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(threadPoolTaskScheduler());
        int delayInMilliseconds = schedulingProperties.getFundRefreshInSeconds() * 1000;
        taskRegistrar.addFixedDelayTask(fundService::reloadFunds, delayInMilliseconds);
    }
}
