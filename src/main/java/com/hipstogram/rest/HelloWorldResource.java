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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * Test resource
 * @author Andrés Sánchez
 */

@Path("/hello")
public class HelloWorldResource
{
    @Context
    org.jboss.resteasy.spi.HttpResponse response;

    @GET
    @Produces("application/json")
    public String getJson()
    {
        response.getOutputHeaders().add("Access-Control-Allow-Origin", "*");
        return "{ \"msg\": \"Hello, mate!\" }";
    }
}
