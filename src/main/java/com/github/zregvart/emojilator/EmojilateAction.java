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

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

import io.syndesis.extension.api.Step;
import io.syndesis.extension.api.annotations.Action;
import io.syndesis.extension.api.annotations.DataShape;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.model.ProcessorDefinition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import twitter4j.Status;

@Action(id = "emojilate", name = "Emojilate", description = "Convert text to emojis", tags = {"emoji", "extension"},
    inputDataShape = @DataShape(kind = "java", type = "twitter4j.Status", name = "Tweet"),
    outputDataShape = @DataShape(kind = "java", type = "twitter4j.Status", name = "Emojilated Tweet"))
public class EmojilateAction implements Step {

    private Map<String, String> wordsToEmojis;

    public EmojilateAction() throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final ObjectReader reader = mapper
            .readerFor(mapper.getTypeFactory().constructMapLikeType(HashMap.class, String.class, EmojiDefinition.class));

        try (InputStream stream = EmojilateAction.class.getResourceAsStream("/emojis.json")) {
            final Map<String, EmojiDefinition> emojiDefinitions = reader.readValue(stream);

            final Map<String, String> wordsMapped = new HashMap<>();

            emojiDefinitions.values()
                .forEach(definition -> definition.keywords.forEach(keyword -> wordsMapped.put(keyword, definition.emojChar)));
            emojiDefinitions.forEach((name, definition) -> wordsMapped.put(name, definition.emojChar));

            wordsToEmojis = Collections.unmodifiableMap(wordsMapped);
        }
    }

    @Override
    public Optional<ProcessorDefinition> configure(final CamelContext context, final ProcessorDefinition route,
        final Map<String, Object> parameters) {

        return Optional.of(route.process(this::transformTweetTextToEmojis));
    }

    public void transformTweetTextToEmojis(final Exchange exchange) {
        final Message incoming = exchange.getIn();

        final Status status = incoming.getBody(Status.class);

        if (status == null) {
            return;
        }

        final String text = status.getText();

        final String emojilatedText = emojilate(text);

        final Status mutated = new StatusMutation(status, emojilatedText);

        incoming.setBody(mutated);
    }

    String emojiFor(final String token) {
        final String search = token.toLowerCase();

        final String firstLookup = wordsToEmojis.get(search);

        if (firstLookup != null) {
            return firstLookup;
        }

        if (search.charAt(search.length() - 1) == 's') {
            final String singular = search.substring(0, search.length() - 1);

            return wordsToEmojis.get(singular);
        }

        return null;
    }

    String emojilate(final String text) {
        final StringBuilder emojilated = new StringBuilder(text.length());

        final StringTokenizer tokenizer = new StringTokenizer(text, " \t,.;:-!?\"'", true);
        while (tokenizer.hasMoreTokens()) {
            final String token = tokenizer.nextToken();

            final String emoji = emojiFor(token);

            if (emoji == null) {
                emojilated.append(token);
            } else {
                emojilated.append(emoji);
            }
        }

        return emojilated.toString();
    }
}
