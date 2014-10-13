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

package com.hipstogram.mongodb;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.*;

/**
 * Track Count Model
 * @author Andrés Sánchez
 */

@Entity("tracks")
public class TrackCount
{
    @Id
    private String id;
    @Property("histogram")
    private Map<String, Integer> histogram = new TreeMap<String, Integer>();

    /**
     * Get Spotify URI
     * @return Spotify URI
     */
    public String getUri() {
        return id;
    }

    /**
     * Set Spotify URI
     * @param uri Spotify URI
     */
    public void setUri(String uri) {
        this.id = uri;
    }

    /**
     * Get associated histogram
     * @return Associated histogram
     */
    public Map<String, Integer> getHistogram() {
        return histogram;
    }

    /**
     * Set associated histogram
     * @param histogram Associated histogram
     */
    public void setHistogram(Map<String, Integer> histogram) {
        this.histogram = histogram;
    }

    /**
     * Convert MongoDB track counts to Cassandra track counts
     * @return A list of Cassandra track counts
     */
    public List<com.hipstogram.cassandra.TrackCount> toCassandraTrackCount() {
        List<com.hipstogram.cassandra.TrackCount> tracks = new LinkedList<com.hipstogram.cassandra.TrackCount>();

        List<Map.Entry<String, Integer>> list = new LinkedList(histogram.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                Integer n1 = new Integer(((Map.Entry) (o1)).getKey().toString());
                Integer n2 = new Integer(((Map.Entry) (o2)).getKey().toString());
                return ((Comparable) n1).compareTo(n2);
            }
        });

       for (Map.Entry<String, Integer> entry :  list) {
            com.hipstogram.cassandra.TrackCount track = new com.hipstogram.cassandra.TrackCount();
            track.setUri(id);
            track.setTime(Integer.parseInt(entry.getKey()));
            track.setCount(entry.getValue().longValue());
            tracks.add(track);
        }

        return tracks;
    }
}
