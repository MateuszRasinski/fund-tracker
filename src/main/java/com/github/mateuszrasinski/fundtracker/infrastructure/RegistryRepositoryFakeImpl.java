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
package com.github.mateuszrasinski.fundtracker.infrastructure;

import com.github.mateuszrasinski.fundtracker.domain.registry.Registry;
import com.github.mateuszrasinski.fundtracker.domain.registry.RegistryId;
import com.github.mateuszrasinski.fundtracker.domain.registry.RegistryRepository;
import com.github.mateuszrasinski.fundtracker.sharedkernel.BaseIdentity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class RegistryRepositoryFakeImpl implements RegistryRepository {
    Map<BaseIdentity, Registry> registries = new HashMap<>();

    @Override
    public Stream<Registry> findAll(Collection<RegistryId> registriesIds) {
        return registries
                .values()
                .stream()
                .filter(registry -> registriesIds.contains(registry.identity()));
    }

    @Override
    public void save(Registry registry) {
        registries.put(registry.identity(), registry);
    }
}
