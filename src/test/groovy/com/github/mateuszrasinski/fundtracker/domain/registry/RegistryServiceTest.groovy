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
package com.github.mateuszrasinski.fundtracker.domain.registry

import com.github.mateuszrasinski.fundtracker.domain.fund.Fund
import com.github.mateuszrasinski.fundtracker.domain.user.User
import com.github.mateuszrasinski.fundtracker.event.EventTestSpecification

import static com.github.mateuszrasinski.fundtracker.domain.fund.FundBuilder.aFund
import static com.github.mateuszrasinski.fundtracker.domain.user.UserBuilder.aUser

class RegistryServiceTest extends EventTestSpecification {
    private User user
    private Fund fund
    private RegistryRepository registryRepository

    def setup() {
        user = aUser()
        fund = aFund()
        registryRepository = Mock(RegistryRepository)
    }

    def 'should findOrCreate find persisted registry if it exists and do not publish RegistryCreatedEvent'() {
        given:
        Registry registry = new Registry(fund)
        registryRepository.findAllByUser(user) >> [registry].stream()
        RegistryService registryService = new RegistryService(registryRepository)

        when:
        Registry foundRegistry = registryService.findOrCreate(user, fund)

        then:
        foundRegistry == registry
        0 * eventPublisherMock.publishEvent(_ as RegistryCreatedEvent)
    }

    def 'should findOrCreate create new registry if it does not exist and publish RegistryCreatedEvent'() {
        given:
        registryRepository.findAllByUser(user) >> [].stream()
        RegistryService registryService = new RegistryService(registryRepository)

        when:
        Registry foundRegistry = registryService.findOrCreate(user, fund)

        then:
        assert foundRegistry
        1 * eventPublisherMock.publishEvent(_ as RegistryCreatedEvent)
        eventPublisherMock.lastEvent() == new RegistryCreatedEvent(user.identity(), foundRegistry.identity())
    }
}
