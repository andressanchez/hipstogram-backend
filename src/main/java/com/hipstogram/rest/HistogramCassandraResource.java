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

package com.hipstogram.rest;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.google.gson.Gson;
import com.hipstogram.cassandra.CassandraClient;
import com.hipstogram.context.HipstogramContext;
import com.hipstogram.context.config.sections.CassandraSection;
import com.hipstogram.cassandra.DataAccessor;
import com.hipstogram.cassandra.TrackCount;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * Histogram resource
 * @author Andrés Sánchez
 */

@Path("/histogram/{uri}")
public class HistogramCassandraResource
{
    // Cassandra stuff
    private CassandraClient client;
    private Session session;
    private MappingManager manager;
    private DataAccessor dataAccessor;

    // Gson
    private Gson gson;

    /**
     * Create a new instance of this class
     */
    public HistogramCassandraResource()
    {
        // Cassandra
        CassandraSection cassandraSection = HipstogramContext.getInstance().getConfiguration().getCassandraSection();
        client = new CassandraClient();
        client.connect(cassandraSection.host);
        session = client.createSession();
        manager = new MappingManager(session);
        dataAccessor = manager.createAccessor(DataAccessor.class);

        // Gson
        gson = new Gson();
    }

    @Context
    org.jboss.resteasy.spi.HttpResponse response;

    @GET
    @Produces("application/json")
    public String getJson(@PathParam("uri") String uri)
    {
        Result<TrackCount> histogram = dataAccessor.getCountsByURI(uri);
        response.getOutputHeaders().add("Access-Control-Allow-Origin", "*");
        return gson.toJson(histogram.all());
    }
}
