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

import com.github.mateuszrasinski.fundtracker.domain.fund.Fund;
import com.github.mateuszrasinski.fundtracker.sharedkernel.BaseAggregateRoot;
import com.github.mateuszrasinski.fundtracker.sharedkernel.DomainEventPublisher;
import com.github.mateuszrasinski.fundtracker.sharedkernel.UnitPrice;
import com.github.mateuszrasinski.fundtracker.sharedkernel.annotation.AggregateRoot;
import com.github.mateuszrasinski.fundtracker.sharedkernel.annotation.Identity;
import lombok.Getter;
import lombok.NonNull;
import org.javamoney.moneta.spi.MoneyUtils;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@AggregateRoot
public class Registry extends BaseAggregateRoot<RegistryId> {

    @Identity
    private final RegistryId registryId;

    @Getter
    @NonNull
    private final Fund fund;

    @Getter
    @NonNull
    private BigDecimal unitCount;

    @Getter
    @NonNull
    private final Set<Transaction> transactions;

    public Registry(@NonNull Fund fund) {
        registryId = new RegistryId();
        this.fund = fund;
        unitCount = BigDecimal.ZERO;
        transactions = new HashSet<>();
    }

    public void addTransaction(MonetaryAmount amount, ZonedDateTime date) {
        MoneyUtils.checkAmountParameter(amount, fund.getCurrency());
        Transaction transaction = new Transaction(fund.identity(), amount, date);
        transactions.add(transaction);
        recalculateUnitCount(amount, date);
        DomainEventPublisher.publish(new TransactionAddedEvent(transaction.identity(), fund.identity(), amount, date));
    }

    private void recalculateUnitCount(MonetaryAmount amount, ZonedDateTime date) {
        UnitPrice unitPrice = fund.unitPriceOn(date);
        unitCount = unitCount.add(unitCountOfAmount(amount, unitPrice));
    }

    //TODO: UnitCalculator (DomainService)
    private BigDecimal unitCountOfAmount(MonetaryAmount amount, UnitPrice unitPrice) {
        BigDecimal addedAmountValue = amount.getNumber().numberValue(BigDecimal.class);
        return addedAmountValue.divide(unitPrice.value(), BigDecimal.ROUND_HALF_UP);
    }
}
