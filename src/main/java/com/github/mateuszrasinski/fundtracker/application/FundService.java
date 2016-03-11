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
package com.github.mateuszrasinski.fundtracker.application;

import com.github.mateuszrasinski.fundtracker.domain.fund.Fund;
import com.github.mateuszrasinski.fundtracker.domain.fund.FundRepository;
import com.github.mateuszrasinski.fundtracker.infrastructure.persistence.fund.FundLoader;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class FundService {
    private final FundLoader fundLoader;
    private final FundRepository fundRepository;

    public void reloadFunds() {
        Set<Fund> funds = fundLoader.loadFunds();
        fundRepository.save(funds);
    }
}
