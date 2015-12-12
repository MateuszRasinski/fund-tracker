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
package com.github.mateuszrasinski.fundtracker.sharedkernel;

import com.github.mateuszrasinski.fundtracker.sharedkernel.annotation.Identity;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@Scope("prototype")
@Getter
@ToString
public abstract class BaseEntity<ID extends BaseIdentity> {

    @NonNull
    private final Field identityField;

    protected BaseEntity() {
        identityField = discoverIdentityField(getClass());
    }

    private static Field discoverIdentityField(Class clazz) {
        Field discoveredBaseIdentityField = null;
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Identity.class)) {
                break;
            }
            if (discoveredBaseIdentityField != null) {
                throw new IllegalStateException("Only one field can be annotated with " + Identity.class);
            }
            discoveredBaseIdentityField = field;
            discoveredBaseIdentityField.setAccessible(true);
        }
        return discoveredBaseIdentityField;
    }

    @SuppressWarnings("unchecked")
    public ID identity() {
        try {
            return (ID) identityField.get(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseEntity)) {
            return false;
        }

        BaseEntity<?> that = (BaseEntity<?>) o;

        return identity().equals(that.identity());

    }

    @Override
    public int hashCode() {
        return identity().hashCode();
    }
}
