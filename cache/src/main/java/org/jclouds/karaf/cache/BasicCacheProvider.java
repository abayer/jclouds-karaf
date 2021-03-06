/*
 * Copyright (C) 2011, the original authors
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.jclouds.karaf.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jclouds.karaf.cache.CacheProvider;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class BasicCacheProvider implements CacheProvider {

    private static Map<String, Multimap<String,String>> caches = new ConcurrentHashMap<String, Multimap<String,String>>();

    public synchronized  Multimap<String,String> getProviderCacheForType(String type) {
        if  (caches.containsKey(type)) {
            return caches.get(type);
        } else {

             Multimap<String,String> cache =  HashMultimap.<String,String>create();
            caches.put(type, cache);
            return cache;
        }
    }
}
