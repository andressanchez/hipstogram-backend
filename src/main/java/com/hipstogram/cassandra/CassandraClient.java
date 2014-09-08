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

package com.hipstogram.cassandra;

import com.datastax.driver.core.*;

/**
 * Cassandra Client
 * @author Andrés Sánchez
 */
public class CassandraClient
{
    private Cluster cluster;
    private Session session;

    /**
     * Establish a new connection with a given node
     * @param node Cassandra node
     */
    public void connect(String node)
    {
        cluster = Cluster.builder()
            .addContactPoint(node)
            .build();
    }

    /**
     * Create a new session
     * @return A new session
     */
    public Session createSession()
    {
        return cluster.connect();
    }

    /**
     * Close connection
     */
    public void close() {
        cluster.close();
    }
}