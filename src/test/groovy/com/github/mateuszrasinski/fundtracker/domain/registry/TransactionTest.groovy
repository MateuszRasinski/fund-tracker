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

import com.github.mateuszrasinski.fundtracker.domain.fund.FundId
import org.javamoney.moneta.Money
import spock.lang.Specification

import javax.money.MonetaryAmount
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId
import java.time.ZonedDateTime

class TransactionTest extends Specification {
    def "should transaction has same return true if fundId, amount and date are the same as transaction's"() {
        given:
            FundId fundId = new FundId()
            MonetaryAmount amount = Money.of(500.00, "PLN")
            ZonedDateTime date = LocalDate.of(2015, Month.APRIL, 15).atStartOfDay(ZoneId.systemDefault())
            Transaction transaction = new Transaction(fundId, amount, date)
        when:
            boolean hasSameValues = transaction.hasSame(fundId, amount, date)
        then:
            hasSameValues
    }
}
