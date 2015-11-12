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
package com.github.mateuszrasinski.fundtracker;

import com.github.mateuszrasinski.fundtracker.application.PurchaseFundService;
import com.github.mateuszrasinski.fundtracker.domain.fund.FundRepository;
import com.github.mateuszrasinski.fundtracker.domain.registry.RegistryRepository;
import com.github.mateuszrasinski.fundtracker.domain.user.UserRepository;
import com.github.mateuszrasinski.fundtracker.infrastructure.FundRepositoryFakeImpl;
import com.github.mateuszrasinski.fundtracker.infrastructure.RegistryRepositoryFakeImpl;
import com.github.mateuszrasinski.fundtracker.infrastructure.UserRepositoryFakeImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FundTrackerApplication {

    @Bean
    UserRepository userRepository() {
        return new UserRepositoryFakeImpl();
    }

    @Bean
    FundRepository fundRepository() {
        return new FundRepositoryFakeImpl();
    }

    @Bean
    RegistryRepository registryRepository() {
        return new RegistryRepositoryFakeImpl();
    }

    @Bean
    PurchaseFundService purchaseFundService() {
        return new PurchaseFundService();
    }

    public static void main(String[] args) {
        SpringApplication.run(FundTrackerApplication.class, args);
    }
}
