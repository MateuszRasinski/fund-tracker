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
import com.github.mateuszrasinski.fundtracker.event.EventTestSpecification

import javax.money.MonetaryAmount
import java.time.ZonedDateTime

import static com.github.mateuszrasinski.fundtracker.domain.fund.FundBuilder.aFund
import static com.github.mateuszrasinski.fundtracker.domain.fund.FundBuilder.fund
import static com.github.mateuszrasinski.fundtracker.testutil.Fixtures.aDate
import static com.github.mateuszrasinski.fundtracker.testutil.Fixtures.anAmount

class RegistryTest extends EventTestSpecification {
    def 'should get new transaction after adding it'() {
        given:
        Fund fund = aFund()
        Registry registry = new Registry(fund)

        expect:
        registry.getTransactions().isEmpty()

        when:
        registry.addTransaction(anAmount(), aDate())

        then:
        Transaction transaction = registry.getTransactions().first()
        transaction.hasSame(fund.identity(), anAmount(), aDate())
    }

    def 'should publish TransactionAddedEvent while adding transaction'() {
        given:
        Fund fund = aFund()
        Registry registry = new Registry(fund)
        MonetaryAmount amount = anAmount()
        ZonedDateTime date = aDate()

        when:
        registry.addTransaction(amount, date)

        then:
        1 * eventPublisherMock.publishEvent(_ as TransactionAddedEvent)
        TransactionAddedEvent lastEvent = (TransactionAddedEvent) eventPublisherMock.lastEvent()
        lastEvent.transactionId != null
        lastEvent.fundId == fund.identity()
        lastEvent.amount == amount
        lastEvent.date == date
    }

    def 'should increase unit count while adding transaction'() {
        given:
        ZonedDateTime date = aDate()
        MonetaryAmount amount = anAmount()
        Fund fund = fund().withFirstUnitPriceDate(date).build()
        Registry registry = new Registry(fund)

        expect:
        BigDecimal initUnitCount = BigDecimal.ZERO
        registry.unitCount == initUnitCount

        when:
        registry.addTransaction(amount, date)

        then:
        registry.unitCount.compareTo(initUnitCount) > 0
        BigDecimal unitCountAfterFirstTransaction = registry.unitCount

        when:
        registry.addTransaction(amount, date.plusDays(1))

        then:
        registry.unitCount.compareTo(unitCountAfterFirstTransaction) > 0
    }
}
