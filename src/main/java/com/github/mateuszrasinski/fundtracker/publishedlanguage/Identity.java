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
package com.github.mateuszrasinski.fundtracker.publishedlanguage;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class Identity {

    @NonNull
    private final String identity;

    public Identity(String identity) {
        this.identity = identity;
    }

    public static Identity generate() {
        return new Identity(UUID.randomUUID().toString());
    }
}
