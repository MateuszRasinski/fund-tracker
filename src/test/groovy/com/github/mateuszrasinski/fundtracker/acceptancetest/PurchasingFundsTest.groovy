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
package com.github.mateuszrasinski.fundtracker.acceptancetest
import com.github.mateuszrasinski.fundtracker.AcceptanceTestSpecification
import com.github.mateuszrasinski.fundtracker.FundTrackerApplication
import com.github.mateuszrasinski.fundtracker.application.PurchaseFundService
import com.github.mateuszrasinski.fundtracker.domain.fund.Fund
import com.github.mateuszrasinski.fundtracker.domain.fund.FundName
import com.github.mateuszrasinski.fundtracker.domain.fund.FundRepository
import com.github.mateuszrasinski.fundtracker.domain.registry.RegistryRepository
import com.github.mateuszrasinski.fundtracker.domain.user.Portfolio
import com.github.mateuszrasinski.fundtracker.domain.user.User
import com.github.mateuszrasinski.fundtracker.domain.user.UserRepository
import com.github.mateuszrasinski.fundtracker.sharedkernel.UnitPrice
import org.javamoney.moneta.Money
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import javax.money.MonetaryAmount
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId
import java.time.ZonedDateTime

import static com.github.mateuszrasinski.fundtracker.acceptancetest.UserBuilder.aUser

@ContextConfiguration(classes = FundTrackerApplication)
class PurchasingFundsTest extends AcceptanceTestSpecification {

    @Autowired
    PurchaseFundService purchaseFundService

    @Autowired
    UserRepository userRepository

    @Autowired
    FundRepository fundRepository

    @Autowired
    RegistryRepository registryRepository

    def "should purchasing a new fund by the user add that fund to the user's portfolio"() {
        given:
            Portfolio portfolioWithoutFunds = new Portfolio([] as Set)
            User user = existsUser(aUser().withPortfolio(portfolioWithoutFunds).build())
            Fund fund = existsFund(aFund())
            MonetaryAmount amount = Money.of(500.00, "PLN")
            ZonedDateTime date = LocalDate.of(2015, Month.APRIL, 15).atStartOfDay(ZoneId.systemDefault())
        when:
            purchaseFundService.registerFundPurchase(user.identity, fund.identity, amount, date)
        then:
            usersPortfolioHasFund(user, fund)
    }

    void usersPortfolioHasFund(User user, Fund fund) {
        User updatedUser = userRepository.find(user.identity).get()
        assert registryRepository.findAll(updatedUser.portfolio.registriesIds)
                .anyMatch { registry -> registry.fund.equals(fund) }
    }

    User existsUser(User user) {
        return userRepository.save(user)
    }

    Fund existsFund(Fund fund) {
        return fundRepository.save(fund)
    }

    Fund aFund() {
        return new Fund(new FundName('JAPAN'), [new UnitPrice(Money.of(2.50, 'PLN'), ZonedDateTime.now())])
    }
}
