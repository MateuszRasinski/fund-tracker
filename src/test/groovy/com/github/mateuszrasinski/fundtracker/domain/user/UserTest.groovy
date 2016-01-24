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
package com.github.mateuszrasinski.fundtracker.domain.user

import com.github.mateuszrasinski.fundtracker.domain.registry.RegistryId
import spock.lang.Specification

import static UserBuilder.user

class UserTest extends Specification {
    def "should get new registry after adding it"() {
        given:
            User user = user().withoutRegistries().build()
            RegistryId registryId = new RegistryId()
        when:
            user.addRegistry(registryId)
        then:
            user.registriesIds == [registryId] as Set
    }
}
