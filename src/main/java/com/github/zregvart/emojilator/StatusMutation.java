/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.zregvart.emojilator;

import java.util.Date;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Scopes;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;

class StatusMutation implements Status {

    private static final long serialVersionUID = 1L;

    private final Status delegate;

    private final String text;

    StatusMutation(final Status status, final String text) {
        delegate = status;
        this.text = text;
    }

    @Override
    public int compareTo(final Status other) {
        return delegate.compareTo(other);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Status)) {
            return false;
        }

        return ((Status) obj).getId() == delegate.getId();
    }

    @Override
    public int getAccessLevel() {
        return delegate.getAccessLevel();
    }

    @Override
    public long[] getContributors() {
        return delegate.getContributors();
    }

    @Override
    public Date getCreatedAt() {
        return delegate.getCreatedAt();
    }

    @Override
    public long getCurrentUserRetweetId() {
        return delegate.getCurrentUserRetweetId();
    }

    @Override
    public int getDisplayTextRangeEnd() {
        return delegate.getDisplayTextRangeEnd();
    }

    @Override
    public int getDisplayTextRangeStart() {
        return delegate.getDisplayTextRangeStart();
    }

    @Override
    public int getFavoriteCount() {
        return delegate.getFavoriteCount();
    }

    @Override
    public GeoLocation getGeoLocation() {
        return delegate.getGeoLocation();
    }

    @Override
    public HashtagEntity[] getHashtagEntities() {
        return delegate.getHashtagEntities();
    }

    @Override
    public long getId() {
        return delegate.getId();
    }

    @Override
    public String getInReplyToScreenName() {
        return delegate.getInReplyToScreenName();
    }

    @Override
    public long getInReplyToStatusId() {
        return delegate.getInReplyToStatusId();
    }

    @Override
    public long getInReplyToUserId() {
        return delegate.getInReplyToUserId();
    }

    @Override
    public String getLang() {
        return delegate.getLang();
    }

    @Override
    public MediaEntity[] getMediaEntities() {
        return delegate.getMediaEntities();
    }

    @Override
    public Place getPlace() {
        return delegate.getPlace();
    }

    @Override
    public Status getQuotedStatus() {
        return delegate.getQuotedStatus();
    }

    @Override
    public long getQuotedStatusId() {
        return delegate.getQuotedStatusId();
    }

    @Override
    public RateLimitStatus getRateLimitStatus() {
        return delegate.getRateLimitStatus();
    }

    @Override
    public int getRetweetCount() {
        return delegate.getRetweetCount();
    }

    @Override
    public Status getRetweetedStatus() {
        return delegate.getRetweetedStatus();
    }

    @Override
    public Scopes getScopes() {
        return delegate.getScopes();
    }

    @Override
    public String getSource() {
        return delegate.getSource();
    }

    @Override
    public SymbolEntity[] getSymbolEntities() {
        return delegate.getSymbolEntities();
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public URLEntity[] getURLEntities() {
        return delegate.getURLEntities();
    }

    @Override
    public User getUser() {
        return delegate.getUser();
    }

    @Override
    public UserMentionEntity[] getUserMentionEntities() {
        return delegate.getUserMentionEntities();
    }

    @Override
    public String[] getWithheldInCountries() {
        return delegate.getWithheldInCountries();
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public boolean isFavorited() {
        return delegate.isFavorited();
    }

    @Override
    public boolean isPossiblySensitive() {
        return delegate.isPossiblySensitive();
    }

    @Override
    public boolean isRetweet() {
        return delegate.isRetweet();
    }

    @Override
    public boolean isRetweeted() {
        return delegate.isRetweet();
    }

    @Override
    public boolean isRetweetedByMe() {
        return delegate.isRetweetedByMe();
    }

    @Override
    public boolean isTruncated() {
        return delegate.isTruncated();
    }

}
