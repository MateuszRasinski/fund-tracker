/*
 * Copyright 2015 Mateusz Rasiński
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
package com.github.mateuszrasinski.fundtracker.sharedkernel;

import com.github.mateuszrasinski.fundtracker.sharedkernel.annotation.ValueObject;
import lombok.Value;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.Instant;

@Value
@ValueObject
public class UnitPrice {
    private final MonetaryAmount price;
    private final Instant date;

    public UnitPrice(Number priceValue, CurrencyUnit currency, Instant date) {
        this.price = Money.of(priceValue, currency);
        this.date = date;
    }

    public BigDecimal value() {
        return price.getNumber().numberValue(BigDecimal.class);
    }
}
