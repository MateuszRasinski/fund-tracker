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

import com.github.mateuszrasinski.fundtracker.FundTrackerApplication
import com.github.mateuszrasinski.fundtracker.application.PurchaseFundService
import com.github.mateuszrasinski.fundtracker.domain.fund.Fund
import com.github.mateuszrasinski.fundtracker.domain.fund.FundRepository
import com.github.mateuszrasinski.fundtracker.domain.registry.Registry
import com.github.mateuszrasinski.fundtracker.domain.registry.RegistryId
import com.github.mateuszrasinski.fundtracker.domain.registry.RegistryRepository
import com.github.mateuszrasinski.fundtracker.domain.user.User
import com.github.mateuszrasinski.fundtracker.domain.user.UserId
import com.github.mateuszrasinski.fundtracker.domain.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import javax.money.MonetaryAmount
import java.time.ZonedDateTime

import static com.github.mateuszrasinski.fundtracker.domain.fund.FundBuilder.aFund
import static com.github.mateuszrasinski.fundtracker.domain.user.UserBuilder.aUser
import static com.github.mateuszrasinski.fundtracker.domain.user.UserBuilder.user
import static com.github.mateuszrasinski.fundtracker.testutil.Fixtures.aDate
import static com.github.mateuszrasinski.fundtracker.testutil.Fixtures.anAmount

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

    def "should purchasing a new fund by the user add new registry with that fund to the user's registries"() {
        given:
        User user = existsUser(user().withoutRegistries().build())
        Fund fund = existsFund(aFund())

        when:
        purchaseFundService.registerFundPurchase(user.identity(), fund.identity(), anAmount(), aDate())

        then:
        existsUserRegistry(user.identity(), fund)
    }

    def "should purchasing a new fund by the user create new transaction in user's registry"() {
        given:
        User user = existsUser(aUser())
        Fund fund = existsFund(aFund())

        when:
        purchaseFundService.registerFundPurchase(user.identity(), fund.identity(), anAmount(), aDate())

        then:
        existsTransactionInUserRegistry(user.identity(), fund, anAmount(), aDate())
    }

    def "should add registry for user"() {
        given:
        User user = existsUser(aUser())
        RegistryId registryId = new RegistryId()

        when:
        purchaseFundService.addRegistryForUser(user.identity(), registryId)

        then:
        existsUserWithRegistryId(user.identity(), registryId)
    }

    private void existsUserWithRegistryId(UserId userId, RegistryId registryId) {
        User user = userRepository.find(userId).get()
        assert user.registriesIds.contains(registryId)
    }

    private void existsUserRegistry(UserId userId, Fund fund) {
        assert findUserRegistry(userId, fund).isPresent()
    }

    private void existsTransactionInUserRegistry(UserId userId, Fund fund, MonetaryAmount amount, ZonedDateTime date) {
        Registry registry = findUserRegistry(userId, fund).get()
        assert registry.transactions.stream()
                .anyMatch { transaction -> transaction.hasSame(fund.identity(), amount, date) }
    }

    private Optional<Registry> findUserRegistry(UserId userId, Fund fund) {
        User user = userRepository.find(userId).get()
        return registryRepository.findAll(user.registriesIds)
                .filter { registry -> registry.fund.equals(fund) }
                .findAny()
    }

    private User existsUser(User user) {
        User savedUser = userRepository.save(user)
        assert savedUser
        return savedUser
    }

    private Fund existsFund(Fund fund) {
        fundRepository.save([fund])
        return fundRepository.find(fund.identity()).orElseThrow { new AssertionError("Fund doesn't exist") }
    }
}
