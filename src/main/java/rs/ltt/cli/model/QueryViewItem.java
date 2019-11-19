/*
 * Copyright 2019 Daniel Gultsch
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package rs.ltt.cli.model;

import rs.ltt.jmap.common.entity.Email;
import rs.ltt.jmap.common.entity.EmailAddress;

import java.util.Set;

public class QueryViewItem {

    public final String threadId;

    public final int count;

    public final Set<EmailAddress> from;

    public final Email mostRecent;

    public QueryViewItem(String threadId, int count, Set<EmailAddress> from, Email mostRecent) {
        this.threadId = threadId;
        this.count = count;
        this.from = from;
        this.mostRecent = mostRecent;
    }
}
