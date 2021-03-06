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

package org.jclouds.karaf.utils.blobstore;

import java.util.List;
import java.util.Properties;

import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.BlobStoreContextFactory;

import com.google.inject.Module;

public class BlobStoreHelper {

    /**
     * Chooses a {@link BlobStore} that matches the specified provider or api.
     * @param providerOrApi
     * @param services
     * @return
     */
    public static BlobStore getBlobStore(String providerOrApi, List<BlobStore> services) {
        if (providerOrApi != null) {
            BlobStore service = null;
            for (BlobStore svc : services) {
                if (providerOrApi.equals(svc.getContext().unwrap().getId())) {
                    service = svc;
                    break;
                }
            }
            if (service == null) {
                throw new IllegalArgumentException("No Provider Or Api named " + providerOrApi + " found");
            }
            return service;
        } else {
            if (services.size() == 0) {
                throw new IllegalArgumentException("No providers are present. Note: It takes a couple of seconds for the provider to initialize.");
            } else if (services.size() != 1) {
                StringBuilder sb = new StringBuilder();
                for (BlobStore svc : services) {
                    if (sb.length() > 0) {
                        sb.append(", ");
                    }
                    sb.append(svc.getContext().unwrap().getId());
                }
                throw new IllegalArgumentException("Multiple providers/apis are present, please select one using the --provider / --api argument in the following values: " + sb.toString());
            }
            else {
                return services.get(0);
            }
        }
    }

    public static BlobStore createBlobStore(String provider, String identity, String credential, Iterable<? extends Module> modules, Properties props) {
        BlobStoreContext context = new BlobStoreContextFactory().createContext(provider, identity, credential, modules, props);
        BlobStore blobStore = context.getBlobStore();
        return blobStore;
    }


    private BlobStoreHelper() {
        //Utility Class
    }
}
