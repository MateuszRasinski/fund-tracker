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
package com.github.mateuszrasinski.fundtracker.domain.registry;

import com.github.mateuszrasinski.fundtracker.domain.fund.FundId;
import com.github.mateuszrasinski.fundtracker.sharedkernel.BaseEntity;
import com.github.mateuszrasinski.fundtracker.sharedkernel.annotation.Entity;
import com.github.mateuszrasinski.fundtracker.sharedkernel.annotation.Identity;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

@Entity
@RequiredArgsConstructor
@ToString
public class Transaction extends BaseEntity<TransactionId> {

    @Identity
    private final TransactionId transactionId = new TransactionId();

    @NonNull
    private final FundId fundId;

    @Getter
    @NonNull
    private final MonetaryAmount amount;

    @Getter
    @NonNull
    private final ZonedDateTime date;

    public boolean hasSame(FundId fundId, MonetaryAmount amount, ZonedDateTime date) {
        return this.date.equals(date) && this.amount.equals(amount) && this.fundId.equals(fundId);
    }
}
