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
import com.github.mateuszrasinski.fundtracker.domain.registry.Registry;
import com.github.mateuszrasinski.fundtracker.domain.registry.RegistryService;
import com.github.mateuszrasinski.fundtracker.publishedlanguage.Identity;
import com.github.mateuszrasinski.fundtracker.sharedkernel.BaseEntity;
import com.github.mateuszrasinski.fundtracker.sharedkernel.annotation.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Entity
public class Portfolio extends BaseEntity {

    @Getter
    @NonNull
    private final List<Identity> registriesIds;

    //TODO: Portfolio factory
    private RegistryService registryService;

    public void registerFundPurchase(Fund fund, MonetaryAmount amount, ZonedDateTime date) {
        Registry registry = registryService.loadRegistry(registriesIds, fund).orElseGet(() -> createNewRegistry(fund));
        registry.addTransaction(amount, date);
        //TODO: remove - use events
        registryService.save(registry);
    }

    private Registry createNewRegistry(Fund fund) {
        //TODO: RegistryFactory
        Registry registry = new Registry(fund, new ArrayList<>());
        registriesIds.add(registry.getIdentity());
        return registry;
    }
}
