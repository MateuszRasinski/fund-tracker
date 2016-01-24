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
import spock.lang.Specification

import static com.github.mateuszrasinski.fundtracker.domain.fund.FundBuilder.fund
import static com.github.mateuszrasinski.fundtracker.testutil.Fixtures.aDate
import static com.github.mateuszrasinski.fundtracker.testutil.Fixtures.anAmount

class RegistryTest extends Specification {
    def 'should get new transaction after adding it'() {
        given:
            Fund fund = fund().build()
            Registry registry = new Registry(fund)
        expect:
            registry.getTransactions().isEmpty()
        when:
            registry.addTransaction(anAmount(), aDate())
        then:
            Transaction transaction = registry.getTransactions()[0]
            transaction.hasSame(fund.identity(), anAmount(), aDate())
    }
}
