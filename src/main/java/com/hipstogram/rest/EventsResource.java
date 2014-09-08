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
import com.hipstogram.kafka.KafkaProducer;
import com.hipstogram.model.Event;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Post events
 * @author Andrés Sánchez
 */

@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
public class EventsResource
{
    @Context
    org.jboss.resteasy.spi.HttpResponse response;

    @POST
    public Response produceMessage(@FormParam("content") String eventArrayRaw)
    {
        Gson eventsJSON = new Gson();
        Event[] eventArray = eventsJSON.fromJson(eventArrayRaw, Event[].class);

        System.out.println(eventsJSON.toJson(eventArray));

        KafkaProducer.getInstance().produce(eventArray);

        response.getOutputHeaders().add("Access-Control-Allow-Origin", "*");
        return Response.ok().build();
    }
}
