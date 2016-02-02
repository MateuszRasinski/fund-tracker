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

import com.github.mateuszrasinski.fundtracker.application.FundService;
import com.github.mateuszrasinski.fundtracker.application.PurchaseFundListener;
import com.github.mateuszrasinski.fundtracker.application.PurchaseFundService;
import com.github.mateuszrasinski.fundtracker.domain.fund.FundRepository;
import com.github.mateuszrasinski.fundtracker.domain.registry.RegistryRepository;
import com.github.mateuszrasinski.fundtracker.domain.registry.RegistryService;
import com.github.mateuszrasinski.fundtracker.domain.user.UserRepository;
import com.github.mateuszrasinski.fundtracker.infrastructure.RegistryRepositoryFakeImpl;
import com.github.mateuszrasinski.fundtracker.infrastructure.UserRepositoryFakeImpl;
import com.github.mateuszrasinski.fundtracker.infrastructure.fund.FileFundLoader;
import com.github.mateuszrasinski.fundtracker.infrastructure.fund.FundLoader;
import com.github.mateuszrasinski.fundtracker.infrastructure.fund.FundRepositoryFakeImpl;
import com.github.mateuszrasinski.fundtracker.infrastructure.fund.FundsLocations;
import com.github.mateuszrasinski.fundtracker.sharedkernel.DomainEventPublisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;

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
    RegistryService registryService(RegistryRepository registryRepository) {
        return new RegistryService(registryRepository);
    }

    @Bean
    PurchaseFundService purchaseFundService() {
        return new PurchaseFundService();
    }

    @Bean
    PurchaseFundListener purchaseFundListener() {
        return new PurchaseFundListener();
    }

    @Bean
    FundService fundService(FundLoader fundLoader, FundRepository fundRepository) {
        return new FundService(fundLoader, fundRepository);
    }

    @Bean
    DomainEventPublisher domainEventPublisher() {
        return new DomainEventPublisher();
    }

    @Bean
    FundsLocations fundsLocations() {
        return new FundsLocations();
    }

    @Bean
    FundLoader fundLoader(ResourceLoader resourceLoader, JsonParser jsonParser, FundsLocations fundsLocations) {
        return new FileFundLoader(resourceLoader, jsonParser, fundsLocations);
    }

    @Bean
    public JsonParser jsonParser() {
        return JsonParserFactory.getJsonParser();
    }

    public static void main(String[] args) {
        SpringApplication.run(FundTrackerApplication.class, args);
    }
}
