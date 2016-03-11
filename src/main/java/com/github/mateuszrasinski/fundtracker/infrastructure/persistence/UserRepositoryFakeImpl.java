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
package com.github.mateuszrasinski.fundtracker.infrastructure.persistence;

import com.github.mateuszrasinski.fundtracker.domain.user.User;
import com.github.mateuszrasinski.fundtracker.domain.user.UserId;
import com.github.mateuszrasinski.fundtracker.domain.user.UserRepository;
import com.github.mateuszrasinski.fundtracker.sharedkernel.BaseIdentity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryFakeImpl implements UserRepository {
    private final Map<BaseIdentity, User> users = new HashMap<>();

    @Override
    public Optional<User> find(UserId userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public User save(User user) {
        users.put(user.identity(), user);
        return user;
    }
}
