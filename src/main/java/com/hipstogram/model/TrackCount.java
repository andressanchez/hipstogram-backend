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

package com.hipstogram.model;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import com.google.common.base.Objects;

/**
 * Track Count Model
 * @author Andrés Sánchez
 */

@Table (keyspace = "hipstogram", name = "track_counts")
public class TrackCount
{
    @PartitionKey
    private String uri;
    private Integer time;
    private Long count;

    public TrackCount() {}

    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }

    public Integer getTime() { return time; }
    public void setTime(Integer time) { this.time = time; }

    public Long getCount() { return count; }
    public void setCount(Long count) { this.count = count; }

    @Override
    public int hashCode() {
        return Objects.hashCode(uri, time);
    }
}
