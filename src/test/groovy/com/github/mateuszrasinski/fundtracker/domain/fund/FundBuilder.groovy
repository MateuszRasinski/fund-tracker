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

import javax.money.CurrencyUnit
import javax.money.Monetary
import java.time.ZonedDateTime

import static com.github.mateuszrasinski.fundtracker.testutil.Fixtures.aDate

class FundBuilder {

    private CurrencyUnit currency = Monetary.getCurrency("PLN")
    private ZonedDateTime firstUnitPriceDate = aDate()
    private Set<UnitPrice> unitPrices = [new UnitPrice(2.50, currency, firstUnitPriceDate.toInstant())] as Set

    static FundBuilder fund() {
        return new FundBuilder()
    }

    Fund build() {
        return new Fund('Japonia', currency, unitPrices)
    }

    static Fund aFund() {
        return fund().build()
    }

    FundBuilder withFirstUnitPriceDate(ZonedDateTime firstUnitPriceDate) {
        this.firstUnitPriceDate = firstUnitPriceDate
        unitPrices = [new UnitPrice(2.50, currency, firstUnitPriceDate.toInstant())] as Set
        return this
    }

    FundBuilder withUnitPrices(UnitPrice... unitPrices) {
        this.unitPrices = unitPrices as Set
        return this
    }
}
