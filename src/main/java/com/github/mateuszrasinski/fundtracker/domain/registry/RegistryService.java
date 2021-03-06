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
import com.github.mateuszrasinski.fundtracker.domain.user.User;
import com.github.mateuszrasinski.fundtracker.sharedkernel.DomainEventPublisher;
import com.github.mateuszrasinski.fundtracker.sharedkernel.annotation.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class RegistryService {
    private final RegistryRepository registryRepository;

    public Registry findOrCreate(User user, Fund fund) {
        return registryRepository.findAllByUser(user)
                .filter(r -> r.getFund().equals(fund))
                .findAny()
                .orElseGet(() -> createNewRegistry(user, fund));
    }

    private Registry createNewRegistry(User user, Fund fund) {
        Registry registry = new Registry(fund);
        DomainEventPublisher.publish(new RegistryCreatedEvent(user.identity(), registry.identity()));
        return registry;
    }
}
