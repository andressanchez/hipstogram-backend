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

import com.google.gson.Gson;
import com.hipstogram.context.HipstogramContext;
import com.hipstogram.context.config.sections.MongoDBSection;
import com.hipstogram.mongodb.TrackCount;
import com.mongodb.BasicDBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Histogram resource
 * @author Andrés Sánchez
 */

@Path("/histogram/{uri}")
public class HistogramMongoDBResource
{
    // MongoDB client
    private MongoClient mongo;

    // Datastore
    private Datastore ds;

    // Gson
    private Gson gson;

    /**
     * Create a new instance of this class
     */
    public HistogramMongoDBResource()
    {
        // MongoDB
        MongoDBSection mongoDBSection = HipstogramContext.getInstance().getConfiguration().getMongoSection();

        try {
            mongo = new MongoClient(new ServerAddress(mongoDBSection.host));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // Morphia
        Morphia morphia = new Morphia();
        morphia.map(TrackCount.class);
        ds = morphia.createDatastore(mongo, "test");

        // Gson
        gson = new Gson();
    }

    @Context
    org.jboss.resteasy.spi.HttpResponse response;

    @GET
    @Produces("application/json")
    public String getJson(@PathParam("uri") String uri)
    {
        Query<TrackCount> tracks = ds.find(TrackCount.class).filter("_id", uri);
        TrackCount track = tracks.get();
        response.getOutputHeaders().add("Access-Control-Allow-Origin", "*");
        if (track == null) return "[]";
        return gson.toJson(track.toCassandraTrackCount());
    }
}
