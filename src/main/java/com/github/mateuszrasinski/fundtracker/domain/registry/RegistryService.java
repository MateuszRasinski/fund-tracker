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
import com.github.mateuszrasinski.fundtracker.publishedlanguage.Identity;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

//TODO: DomainService
@AllArgsConstructor
public class RegistryService {

    private RegistryRepository registryRepository;

    public Optional<Registry> loadRegistry(List<Identity> registriesIds, Fund fund) {
        return registryRepository.findAll(registriesIds)
                .filter(registry -> registry.getFund().equals(fund))
                .findAny();
    }

    public void save(Registry registry) {
        registryRepository.save(registry);
    }
}
