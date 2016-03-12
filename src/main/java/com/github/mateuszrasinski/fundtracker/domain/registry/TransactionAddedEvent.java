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
package com.github.mateuszrasinski.fundtracker.domain.registry;

import com.github.mateuszrasinski.fundtracker.domain.fund.FundId;
import com.github.mateuszrasinski.fundtracker.sharedkernel.DomainEvent;
import lombok.*;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TransactionAddedEvent extends DomainEvent {
    private final @NonNull TransactionId transactionId;
    private final @NonNull FundId fundId;
    private final @NonNull MonetaryAmount amount;
    private final @NonNull ZonedDateTime date;
}
