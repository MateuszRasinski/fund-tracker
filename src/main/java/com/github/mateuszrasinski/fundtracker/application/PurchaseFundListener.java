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

import com.github.mateuszrasinski.fundtracker.domain.registry.RegistryCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.event.TransactionalEventListener;

public class PurchaseFundListener {
    @Autowired
    private PurchaseFundService purchaseFundService;

    @TransactionalEventListener(fallbackExecution = true)
    public void handle(RegistryCreatedEvent event) {
        purchaseFundService.addRegistryForUser(event.getUserId(), event.getRegistryId());
    }
}
