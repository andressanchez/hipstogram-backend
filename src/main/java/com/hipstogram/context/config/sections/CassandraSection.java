/**
 *  Copyright 2014 Andrés Sánchez Pascual
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.hipstogram.context.config.sections;

import org.apache.commons.configuration.SubnodeConfiguration;

/**
 * CassandraSection contains the information to configure Cassandra
 * @author Andrés Sánchez
 */
public class CassandraSection
{
    public final int port;
    public final String host;

    public CassandraSection(SubnodeConfiguration cassandra)
    {
        this.port = cassandra.getInt("port");
        this.host = cassandra.getString("host");
    }
}
