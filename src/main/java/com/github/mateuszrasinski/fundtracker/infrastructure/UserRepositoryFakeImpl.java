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

import com.github.mateuszrasinski.fundtracker.domain.user.User;
import com.github.mateuszrasinski.fundtracker.domain.user.UserRepository;
import com.github.mateuszrasinski.fundtracker.publishedlanguage.Identity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryFakeImpl implements UserRepository {
    private final Map<Identity, User> users = new HashMap<>();

    @Override
    public Optional<User> find(Identity userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public User save(User user) {
        users.put(user.getIdentity(), user);
        return user;
    }
}
