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
package com.github.mateuszrasinski.fundtracker.domain.fund;

import com.github.mateuszrasinski.fundtracker.sharedkernel.BaseAggregateRoot;
import com.github.mateuszrasinski.fundtracker.sharedkernel.UnitPrice;
import com.github.mateuszrasinski.fundtracker.sharedkernel.annotation.AggregateRoot;
import com.github.mateuszrasinski.fundtracker.sharedkernel.annotation.Identity;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.util.Assert;

import javax.money.CurrencyUnit;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

@AggregateRoot
@ToString
public class Fund extends BaseAggregateRoot<FundId> {
    @Identity
    private final FundId fundId;
    @Getter
    private final FundName name;
    @Getter
    private final CurrencyUnit currency;
    private final Set<UnitPrice> unitPrices;

    public Fund(@NonNull String fundName, @NonNull CurrencyUnit fundCurrency, @NonNull Set<UnitPrice> unitPrices) {
        fundId = new FundId();
        name = new FundName(fundName);
        currency = fundCurrency;
        Assert.notEmpty(unitPrices);
        this.unitPrices = unitPrices;
    }

    public Set<UnitPrice> getUnitPrices() {
        return Collections.unmodifiableSet(unitPrices);
    }

//    public UnitPrice currentUnitPrice() {
//        return unitPrices.stream()
//                .max(unitPriceDateComparator())
//                .orElseThrow(IllegalStateException::new);
//    }

    public UnitPrice unitPriceOn(ZonedDateTime date) {
        return unitPrices.stream()
                .filter(unitPrice -> !unitPrice.getDate().isAfter(date.toInstant()))
                .max(unitPriceDateComparator())
                .orElseThrow(() -> new FundNotExistedBefore(name, date));
    }

    private static Comparator<UnitPrice> unitPriceDateComparator() {
        return (up1, up2) -> up1.getDate().compareTo(up2.getDate());
    }
}
