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

import com.github.mateuszrasinski.fundtracker.domain.fund.Fund;
import com.github.mateuszrasinski.fundtracker.domain.fund.FundId;
import com.github.mateuszrasinski.fundtracker.domain.fund.FundRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FundRepositoryFakeImpl implements FundRepository {
    private final List<Fund> funds = new ArrayList<>();

    @Override
    public Optional<Fund> find(FundId fundId) {
        return funds
                .stream()
                .filter(fund -> fund.identity().equals(fundId))
                .findAny();
    }

    @Override
    public Fund save(Fund fund) {
        funds.add(fund);
        return fund;
    }
}
