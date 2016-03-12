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

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Requires
import spock.lang.Specification

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = SchedulingTestSpecification)
class FundReloadingSchedulerTest extends Specification {

    @Autowired InvocationsCount invocationsCount

    @Requires({ System.properties['longRunningTests'] == 'true' })
    def "should reload funds once at the beginning and then every 5 seconds"() {
        expect:
        invocationsCount.value == 1

        when:
        Thread.sleep(5000)

        then:
        invocationsCount.value == 2

        when:
        Thread.sleep(5000)

        then:
        invocationsCount.value == 3
    }
}
