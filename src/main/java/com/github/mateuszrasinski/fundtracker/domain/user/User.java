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
package com.github.mateuszrasinski.fundtracker.domain.user;

import com.github.mateuszrasinski.fundtracker.domain.fund.Fund;
import com.github.mateuszrasinski.fundtracker.sharedkernel.BaseAggregateRoot;
import com.github.mateuszrasinski.fundtracker.sharedkernel.annotation.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

@AllArgsConstructor
@AggregateRoot
public class User extends BaseAggregateRoot {

    private final UserData userData;

    @Getter
    private final Portfolio portfolio;

    public void registerFundPurchase(Fund fund, MonetaryAmount amount, ZonedDateTime date) {
        portfolio.registerFundPurchase(fund, amount, date);
    }
}
