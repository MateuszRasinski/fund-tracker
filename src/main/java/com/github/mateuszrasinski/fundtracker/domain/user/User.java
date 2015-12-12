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

import com.github.mateuszrasinski.fundtracker.domain.registry.RegistryId;
import com.github.mateuszrasinski.fundtracker.sharedkernel.BaseAggregateRoot;
import com.github.mateuszrasinski.fundtracker.sharedkernel.annotation.AggregateRoot;
import com.github.mateuszrasinski.fundtracker.sharedkernel.annotation.Identity;
import lombok.Getter;

import java.util.Collection;

@AggregateRoot
public class User extends BaseAggregateRoot<UserId> {

    @Identity
    private final UserId userId;

    private final UserData userData;

    @Getter
    private final Portfolio portfolio;

    public User(UserData userData, Portfolio portfolio) {
        this.userId = new UserId();
        this.userData = userData;
        this.portfolio = portfolio;
    }

    public Collection<RegistryId> getRegistriesIds() {
        return portfolio.getRegistriesIds();
    }

    public void addRegistry(RegistryId registryId) {
        portfolio.addRegistry(registryId);
    }
}
