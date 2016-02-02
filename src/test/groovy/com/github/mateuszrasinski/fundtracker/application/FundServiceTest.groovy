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
package com.github.mateuszrasinski.fundtracker.application

import com.github.mateuszrasinski.fundtracker.acceptancetest.AcceptanceTestSpecification
import com.github.mateuszrasinski.fundtracker.domain.fund.Fund
import com.github.mateuszrasinski.fundtracker.domain.fund.FundRepository
import com.github.mateuszrasinski.fundtracker.domain.fund.SupportedFund
import com.github.mateuszrasinski.fundtracker.sharedkernel.UnitPrice
import org.springframework.beans.factory.annotation.Autowired

import java.time.Instant

class FundServiceTest extends AcceptanceTestSpecification {
    @Autowired
    private FundService fundService
    @Autowired
    private FundRepository fundRepository

    def "reload funds"() {
        when:
        fundService.reloadFunds()

        then:
        Fund fund = fundRepository.findAll().findAny().get()
        assert fund
        fund.name.name == SupportedFund.JAPAN.fundName()
        fund.unitPrices.sort() == [
                new UnitPrice(143.6900, Instant.ofEpochMilli(1382486400000)),
                new UnitPrice(145.1000, Instant.ofEpochMilli(1382572800000)),
                new UnitPrice(142.4000, Instant.ofEpochMilli(1382659200000))
        ].sort()
    }
}
