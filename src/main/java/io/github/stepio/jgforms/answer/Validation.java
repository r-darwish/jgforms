/*
 * Copyright (C) 2018 - 2019 Igor Stepanov. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.stepio.jgforms.answer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Map;

import static java.lang.String.format;

/**
 * Utility methods for using within this library.
 * Do not use apache commons or other utilities as such extra dependencies may be unwanted for Android.
 *
 * @author Igor Stepanov
 */
public class Validation {

    private Validation() {
    }

    /**
     * Translate a string into {@code application/x-www-form-urlencoded} using UTF-8 encoding.
     * @param text {@link String} to be translated
     * @return the translated {@code String}.
     * @throws IllegalStateException if UTF-8 encoding is not supported
     * @see URLEncoder#encode(java.lang.String, java.lang.String)
     */
    public static String encode(String text) {
        try {
            if (isEmpty(text)) {
                return text;
            }
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("Problem constructing URL to submit a form", ex);
        }
    }

    /**
     * Check that the given {@link CharSequence} is neither {@code null} nor of length 0.
     * @param text the {@code CharSequence} to check (may be {@code null})
     * @return {@code true} if the {@code CharSequence} is not {@code null} and has length
     */
    public static boolean hasLength(CharSequence text) {
        return text != null && text.length() > 0;
    }

    /**
     * Check whether the given {@code String} is empty or null.
     * @param text the candidate String
     * @return {@code true} if the {@code CharSequence} is {@code null} or equals to empty {@code String} literal
     */
    public static boolean isEmpty(Object text) {
        return (text == null || "".equals(text));
    }

    /**
     * Returns a composed message using specified text with placeholders and params to fill those placeholders.
     * @param message {@link String} text with 0 or more placeholders
     * @param params arguments referenced by the placeholders in the message
     * @return composed message with parameters
     * @see String#format(String, Object...)
     */
    public static String message(String message, Object... params) {
        String result = message;
        if (hasLength(message)) {
            result = format(message, params);
        }
        return result;
    }

    /**
     * Assert that {@link Collection} is not empty.
     * @param collection the collection to check
     * @param message {@link String} text with 0 or more placeholders
     * @param params arguments referenced by the placeholders in the message
     * @throws IllegalArgumentException if specified map is empty, using the composed message with parameters
     */
    public static void notEmpty(Collection collection, String message, Object... params) {
        notNull(collection, message, params);
        isTrue(!collection.isEmpty(), message, params);
    }

    /**
     * Assert that {@link Map} is not empty.
     * @param map the map to check
     * @param message {@link String} text with 0 or more placeholders
     * @param params arguments referenced by the placeholders in the message
     * @throws IllegalArgumentException if specified map is empty, using the composed message with parameters
     */
    public static void notEmpty(Map map, String message, Object... params) {
        notNull(map, message, params);
        isTrue(!map.isEmpty(), message, params);
    }

    /**
     * Assert that an object is not {@code null}.
     * @param value the object to check
     * @param message {@link String} text with 0 or more placeholders
     * @param params arguments referenced by the placeholders in the message
     * @throws IllegalArgumentException if specified object is {@code null}, using the composed message with parameters
     */
    public static void notNull(Object value, String message, Object... params) {
        isTrue(value != null, message, params);
    }

    /**
     * Assert a {@code boolean} expression, throwing an {@link IllegalArgumentException} if the expression evaluates to false.
     * @param flag the expression to check
     * @param message {@link String} text with 0 or more placeholders
     * @param params arguments referenced by the placeholders in the message
     * @throws IllegalArgumentException if specified expression is {@code false}, using the composed message with parameters
     */
    public static void isTrue(boolean flag, String message, Object... params) {
        if (!flag) {
            fail(message, params);
        }
    }

    /**
     * Raise {@link IllegalArgumentException} using specified message and parameters.
     * @param message {@link String} text with 0 or more placeholders
     * @param params arguments referenced by the placeholders in the message
     * @throws IllegalArgumentException using the composed message with parameters
     */
    public static void fail(String message, Object... params) {
        throw new IllegalArgumentException(message(message, params));
    }
}
