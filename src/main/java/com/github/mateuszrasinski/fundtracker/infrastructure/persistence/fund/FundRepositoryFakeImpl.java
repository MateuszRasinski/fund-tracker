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
package com.github.mateuszrasinski.fundtracker.infrastructure.persistence.fund;

import com.github.mateuszrasinski.fundtracker.domain.fund.Fund;
import com.github.mateuszrasinski.fundtracker.domain.fund.FundId;
import com.github.mateuszrasinski.fundtracker.domain.fund.FundRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class FundRepositoryFakeImpl implements FundRepository {
    private final Set<Fund> funds = new HashSet<>();

    @Override
    public Stream<Fund> findAll() {
        return funds.stream();
    }

    @Override
    public Optional<Fund> find(FundId fundId) {
        return funds
                .stream()
                .filter(fund -> fund.identity().equals(fundId))
                .findAny();
    }

    @Override
    public void save(Iterable<Fund> funds) {
        for (Fund fund : funds) {
            this.funds.add(fund);
        }
    }
}
