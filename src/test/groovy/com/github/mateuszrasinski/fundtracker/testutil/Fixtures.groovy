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
package com.github.mateuszrasinski.fundtracker.testutil

import org.javamoney.moneta.Money

import javax.money.CurrencyUnit
import javax.money.Monetary
import javax.money.MonetaryAmount
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId
import java.time.ZonedDateTime

class Fixtures {
    static MonetaryAmount anAmount() {
        Money.of(new BigDecimal("500"), aCurrency())
    }

    static ZonedDateTime aDate() {
        LocalDate.of(2015, Month.APRIL, 15).atStartOfDay(ZoneId.systemDefault())
    }

    static CurrencyUnit aCurrency() {
        Monetary.getCurrency("PLN")
    }
}
