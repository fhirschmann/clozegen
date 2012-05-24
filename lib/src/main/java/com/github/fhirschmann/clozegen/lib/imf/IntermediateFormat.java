/*
 * Copyright (C) 2012 Fabian Hirschmann <fabian@hirschm.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.github.fhirschmann.clozegen.lib.imf;

import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.uima.UIMAException;
import org.apache.uima.jcas.JCas;
import org.uimafit.factory.JCasFactory;
import org.uimafit.util.FSCollectionFactory;
import org.uimafit.util.JCasUtil;

/**
 * Parses and Formats the Intermediate Format.
 *
 * <p>An intermediate format is a representation of gaps with answer
 * options. For example, the following represents a gap with only
 * one valid option, "at", and three invalid options:
 * <pre>
 * He studies {at}{of,in,for,at} the university.
 * </pre>
 * Please note that it doesn't matter if "at" is among the answer options since
 * all valid answers will be automatically added to the answer options.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class IntermediateFormat {
    /** The separator for answer options. */
    public static final String SEPARATOR = ",";

    /** Joins answer options. */
    public static final Joiner JOINER = Joiner.on(SEPARATOR);

    /** Splits answer options. */
    public static final Splitter SPLITTER = Splitter.on(SEPARATOR).trimResults();

    /** The opening token for answer options. */
    public static final String OPENING_TOKEN = "{";

    /** The closing token for answer options. */
    public static final String CLOSING_TOKEN = "}";

    /** As usual, regexps are read-only; this one matches {x}{y}. */
    public static final Pattern PATTERN =
            Pattern.compile("\\{([^\\}\\{]+)\\}\\{([^\\}\\{]+)\\}");

    /** This class is not meant to be instantiated. */
    private IntermediateFormat() {
    }

    /**
     * Formats a {@link JCas}, resulting in the intermediate format.
     *
     * @param aJCas the JCas to format
     * @return a string representation (the intermediate format)
     */
    public static String format(final JCas aJCas) {

        StringBuilder sb = new StringBuilder();
        String text = aJCas.getDocumentText();
        int position = 0;
        for (GapAnnotation gap : JCasUtil.select(aJCas, GapAnnotation.class)) {
            sb.append(text.substring(position, gap.getBegin()));
            sb.append(OPENING_TOKEN);
            sb.append(JOINER.join(FSCollectionFactory.create(gap.getValidAnswers())));
            sb.append(CLOSING_TOKEN);
            sb.append(OPENING_TOKEN);
            sb.append(JOINER.join(FSCollectionFactory.create(gap.getAllAnswers())));
            sb.append(CLOSING_TOKEN);
            position = gap.getEnd();
        }
        sb.append(text.substring(position));

        return sb.toString();
    }

    /**
     * Parses the intermediate format from a string and generates
     * a {@link JCas}. For each gap, a {@link GapAnnotation} will be created.
     *
     * @param string the string to parse
     * @return an annotated JCas
     * @throws UIMAException on errors creating the JCas
     */
    public static JCas parse(final String string) throws UIMAException {
        StringBuffer buffer = new StringBuffer();
        JCas jcas = JCasFactory.createJCas();

        Matcher matcher = PATTERN.matcher(string);
        int offset = 0;
        while (matcher.find()) {
            Set<String> validAnswers = Sets.newHashSet(
                    SPLITTER.split(matcher.group(1)));
            Set<String> allAnswers = Sets.newHashSet(
                    SPLITTER.split(matcher.group(2)));

            // Select a "random" answer
            String answer = validAnswers.iterator().next();

            // Replace the current match with the selected answer
            matcher.appendReplacement(buffer, answer);

            final GapAnnotation gap = UIMAUtils.createGapAnnotation(jcas,
                    validAnswers, allAnswers);

            // the above replacement will shorten the resulting string,
            // thus we need to keep track of the offset
            gap.setBegin(matcher.start() - offset);
            gap.setEnd(matcher.start() - offset + answer.length());
            gap.addToIndexes();
            offset += (matcher.group(0).length() - answer.length());
        }
        matcher.appendTail(buffer);
        jcas.setDocumentText(buffer.toString());

        return jcas;
    }
}
