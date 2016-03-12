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
package com.github.mateuszrasinski.fundtracker.schedulingtest

import com.github.mateuszrasinski.fundtracker.application.FundService
import com.github.mateuszrasinski.fundtracker.infrastructure.scheduling.FundReloadingScheduler
import com.github.mateuszrasinski.fundtracker.infrastructure.scheduling.SchedulingProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import spock.lang.Specification

@SpringBootApplication
@Import(FundReloadingScheduler)
abstract class SchedulingTestSpecification extends Specification {

    @Bean
    FundService fundService(InvocationsCount invocationsCount) {
        return Mock(FundService) {
            reloadFunds() >> {
                invocationsCount.value++
            }
        }
    }

    @Bean
    SchedulingProperties schedulingProperties() {
        return new SchedulingProperties();
    }

    @Bean
    InvocationsCount invocationsCount() {
        return new InvocationsCount()
    }
}

class InvocationsCount {
    int value = 0
}
