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
package com.github.mateuszrasinski.fundtracker.infrastructure.fund;

import com.github.mateuszrasinski.fundtracker.domain.fund.Fund;
import com.github.mateuszrasinski.fundtracker.domain.fund.SupportedFund;
import com.github.mateuszrasinski.fundtracker.sharedkernel.UnitPrice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParser;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

import java.io.IOError;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
public class FileFundLoader implements FundLoader {

    private final ResourceLoader resourceLoader;
    private final JsonParser jsonParser;
    private final FundsLocations fundsLocations;

    @Override
    public Set<Fund> loadFunds() {
        return Stream.of(SupportedFund.values())
                .map(this::loadFund)
                .collect(Collectors.toSet());
    }

    private Fund loadFund(SupportedFund supportedFund) {
        String jsonLocation = fundsLocations.getJsonLocation(supportedFund);
        return new Fund(supportedFund.fundName(), loadUnitPrices(jsonLocation));
    }

    private Set<UnitPrice> loadUnitPrices(String jsonLocation) {
        Set<UnitPrice> unitPrices = unitPricesJsonList(jsonLocation)
                .stream()
                .map(FileFundLoader::unitPriceFrom)
                .collect(Collectors.toSet());
        log.info("UnitPrices: {}", unitPrices);
        return unitPrices;
    }

    @SuppressWarnings("unchecked")
    private List<List<Number>> unitPricesJsonList(String jsonLocation) {
        Resource resource = resourceLoader.getResource(jsonLocation);
        String json;
        try {
            json = StreamUtils.copyToString(resource.getInputStream(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new IOError(e);
        }
        List<Object> unitPricesJsonList = jsonParser.parseList(json);
        return (List<List<Number>>) unitPricesJsonList.get(1);
    }

    private static UnitPrice unitPriceFrom(List<Number> unitPriceAsList) {
        Number priceValue = unitPriceAsList.get(1);
        long epochMilli = unitPriceAsList.get(0).longValue();
        return new UnitPrice(priceValue, Instant.ofEpochMilli(epochMilli));
    }
}
