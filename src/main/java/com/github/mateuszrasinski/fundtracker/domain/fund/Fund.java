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
import lombok.ToString;

import java.util.Collections;
import java.util.Set;

@AggregateRoot
@ToString
public class Fund extends BaseAggregateRoot<FundId> {
    @Identity
    private final FundId fundId;
    @Getter
    private final FundName name;
    private final Set<UnitPrice> unitPrices;

    public Fund(String fundName, Set<UnitPrice> unitPrices) {
        this.fundId = new FundId();
        this.name = new FundName(fundName);
        this.unitPrices = unitPrices;
    }

    public Set<UnitPrice> getUnitPrices() {
        return Collections.unmodifiableSet(unitPrices);
    }
}
