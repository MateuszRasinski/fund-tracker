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
package com.github.mateuszrasinski.fundtracker.domain.fund

import com.github.mateuszrasinski.fundtracker.sharedkernel.UnitPrice
import spock.lang.Specification
import spock.lang.Unroll

import static com.github.mateuszrasinski.fundtracker.domain.fund.FundBuilder.fund
import static com.github.mateuszrasinski.fundtracker.testutil.Fixtures.aCurrency
import static com.github.mateuszrasinski.fundtracker.testutil.Fixtures.aDate

class FundTest extends Specification {

    @Unroll
    def "should return unit price: #value on date: #specificDate"() {
        given:
        Fund fund = fund().withUnitPrices(
                new UnitPrice(1, aCurrency(), aDate().toInstant()),
                new UnitPrice(2, aCurrency(), aDate().plusDays(1).toInstant()),
                new UnitPrice(3, aCurrency(), aDate().plusDays(2).toInstant())
        ).build()

        when:
        UnitPrice unitPriceOnSpecificDate = fund.unitPriceOn(specificDate)

        then:
        unitPriceOnSpecificDate.value() == value as BigDecimal

        where:
        specificDate                        | value
        aDate()                             | 1
        aDate().plusDays(1)                 | 2
        aDate().plusDays(2)                 | 3
        aDate().plusDays(1).minusSeconds(1) | 1
        aDate().plusDays(2).minusSeconds(1) | 2
        aDate().plusDays(3)                 | 3
        aDate().plusDays(10)                | 3
    }

    def "should throw FundNotExistedBefore if trying to find unit price on date before any unit price"() {
        given:
        Fund fund = fund().withFirstUnitPriceDate(aDate()).build()

        when:
        fund.unitPriceOn(aDate().minusDays(1))

        then:
        thrown(FundNotExistedBefore)
    }
}
