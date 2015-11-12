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

import com.github.mateuszrasinski.fundtracker.domain.user.Email
import com.github.mateuszrasinski.fundtracker.domain.user.Portfolio
import com.github.mateuszrasinski.fundtracker.domain.user.User
import com.github.mateuszrasinski.fundtracker.domain.user.UserData

import java.time.ZonedDateTime

class UserBuilder {
    Portfolio portfolio

    static UserBuilder aUser() {
        return new UserBuilder()
    }

    User build() {
        return new User(new UserData('Jan Kowalski', new Email('jan@kowalski.com'), ZonedDateTime.now()), portfolio)
    }

    UserBuilder withPortfolio(Portfolio portfolio) {
         this.portfolio = portfolio
        return this
    }
}
