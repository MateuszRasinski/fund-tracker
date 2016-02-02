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

import com.github.mateuszrasinski.fundtracker.domain.fund.SupportedFund;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.lang.reflect.Field;

@Data
@ConfigurationProperties(prefix="funds")
public final class FundsLocations {
    private String japan;

    String getJsonLocation(SupportedFund supportedFund) {
        try {
            String locationFieldName = supportedFund.name().toLowerCase();
            Field locationField = FundsLocations.class.getDeclaredField(locationFieldName);
            return (String) locationField.get(this);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("SupportedFund: '" + supportedFund.name() + "' has no property set. " +
                    "All supported funds must have according property set", e);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }
}
