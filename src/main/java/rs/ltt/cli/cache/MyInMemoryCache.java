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

package rs.ltt.cli.cache;

import com.google.common.collect.ImmutableList;
import rs.ltt.cli.model.QueryViewItem;
import rs.ltt.jmap.common.entity.*;
import rs.ltt.jmap.common.entity.Thread;
import rs.ltt.jmap.mua.cache.InMemoryCache;
import rs.ltt.jmap.mua.cache.NotSynchronizedException;
import rs.ltt.jmap.mua.util.QueryResultItem;

import java.util.*;

public class MyInMemoryCache extends InMemoryCache {


    public List<QueryViewItem> getQueryViewItems(String query) {
        ImmutableList.Builder<QueryViewItem> listBuilder = new ImmutableList.Builder<>();
        synchronized (this.queryResults) {
            InMemoryQueryResult queryResult = this.queryResults.get(query);
            if (queryResult != null) {
                for(QueryResultItem item : queryResult.getItems()) {
                    Email email;
                    synchronized (this.emails) {
                        email = this.emails.get(item.getEmailId());
                    }
                    Thread thread;
                    synchronized (this.threads) {
                        thread = this.threads.get(item.getThreadId());
                    }
                    final Set<EmailAddress> from = new HashSet<>();
                    synchronized (this.emails) {
                        for(String id : thread.getEmailIds()) {
                            Email mail = this.emails.get(id);
                            if (mail != null && mail.getFrom() != null) {
                                from.addAll(mail.getFrom());
                            }
                        }
                    }
                    listBuilder.add(new QueryViewItem(thread.getId(), thread.getEmailIds().size(), from, email));
                }
            }

        }
        return listBuilder.build();
    }

    public Collection<Mailbox> getMailboxes() {
        try {
            return this.getSpecialMailboxes();
        } catch (NotSynchronizedException e) {
            return Collections.emptyList();
        }
    }

    public Collection<Identity> getIdentities() {
        return this.identities.values();
    }

    public Collection<Email> getEmails(String threadId) {
        ImmutableList.Builder<Email> builder = new ImmutableList.Builder<>();
        final List<String> ids;
        synchronized (this.threads) {
            Thread thread = this.threads.get(threadId);
            ids = thread.getEmailIds();
        }
        synchronized (this.emails) {
            for(String id : ids) {
                builder.add(this.emails.get(id));
            }
        }
        return builder.build();
    }
}
