/*
 * Copyright (c) 2017 Nike, Inc.
 *
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
 */

package com.nike.cerberus.archaius.client;

import javax.annotation.Nullable;

import okhttp3.HttpUrl;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.config.ConfigurationManager;
import com.nike.vault.client.UrlResolver;

/**
 * Class for resolving the Cerberus URL via Archaius.
 */
public class ArchaiusCerberusUrlResolver implements UrlResolver {

    public static final String CERBERUS_ADDR_ENV_PROPERTY = "CERBERUS_ADDR";

    public static final String CERBERUS_ADDR_SYS_PROPERTY = "cerberus.addr";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Attempts to acquire the Vault URL from Archaius.
     *
     * @return Vault URL
     */
    @Nullable
    @Override
    public  String resolve() {
        final AbstractConfiguration configuration = ConfigurationManager.getConfigInstance();
        final String envUrl = configuration.getString(CERBERUS_ADDR_ENV_PROPERTY);
        final String sysUrl = configuration.getString(CERBERUS_ADDR_SYS_PROPERTY);

        if (StringUtils.isNotBlank(envUrl) && HttpUrl.parse(envUrl) != null) {
            return envUrl;
        } else if (StringUtils.isNotBlank(sysUrl) && HttpUrl.parse(sysUrl) != null) {
            return sysUrl;
        }

        logger.warn("Unable to resolve the Cerberus URL.");

        return null;
    }
}