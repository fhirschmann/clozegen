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
package com.github.fhirschmann.clozegen.lib.components;

import com.github.fhirschmann.clozegen.lib.components.api.ConstraintBasedConsumer;
import com.github.fhirschmann.clozegen.lib.multiset.WriteMultisets;
import com.github.fhirschmann.clozegen.lib.util.MiscUtils;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.uima.UimaContext;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.uimafit.descriptor.ConfigurationParameter;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * Extracts collocation and writes them to a file.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class CollocationWriter extends ConstraintBasedConsumer {
    /**
     * <em>[mandatory]</em>
     *
     * The file to which the collocation n-grams will be written to.
     */
    public static final String PARAM_OUTPUT_PATH = "OutputPath";
    @ConfigurationParameter(name = PARAM_OUTPUT_PATH, mandatory = true)
    private String path;

    /**
     * <em>[optional,default=1]</em>
     *
     * The number of neighbors on each side to include in the ngram.
     *
     * <p>
     * For example, setting this to {@code 2} will extract
     * <i>can't think of anything yet</i> from the sentence
     * <i>He can't think of anything yet.</i> (given that we are matching
     * prepositions), whereas setting{@code PARAM_N} to {@code 3} (the default)
     * will result in <i>think of anything</i> being extracted. Setting
     * this to {@code 0} will of course yield unigrams.
     * </p>
     */
    public static final String PARAM_N = "N";
    @ConfigurationParameter(name = PARAM_N, mandatory = false, defaultValue = "3")
    private int n;

    /**
     * <em>[optional,default=true]</em>
     *
     * Indicates whether or not to include the head of a word.
     *
     * <p>
     * For example, setting this to {@code false} will not include any word
     * that comes before the word matched by {@link CollocationWriter#CONSTRAINT_KEY}.
     * In this case, given {@link CollocationWriter#PARAM_N} is set to {@code 1}
     * (the default), <i>of anything</i> will be extracted from the sentence
     * <i>He can't think of anything.</i> given that we are matching prepositions.
     * </p>
     *
     * <p>
     * This should not be set to {@code false} in addition to setting
     * {@link CollocationWriter#PARAM_INCLUDE_TAIL} to {@code false}.
     * </p>
     */
    public static final String PARAM_INCLUDE_HEAD = "IncludeHead";
    @ConfigurationParameter(name = PARAM_INCLUDE_HEAD, mandatory = false, defaultValue = "true")
    private boolean includeHead;

    /**
     * <em>[optional,default=true]</em>
     *
     * Indicates whether or not to include the tail of a word.
     *
     * <p>
     * For example, setting this to {@code false} will not include any word
     * that comes after the word matched by {@link CollocationWriter#CONSTRAINT_KEY}.
     * In this case, given {@link CollocationWriter#PARAM_N} is set to {@code 1}
     * (the default), <i>think of</i> will be extracted from the sentence
     * <i>He can't think of anything.</i> given that we are matching prepositions.
     * </p>
     *
     * <p>
     * This should not be set to {@code false} in addition to setting
     * {@link CollocationWriter#PARAM_INCLUDE_HEAD} to {@code false}.
     * </p>
     */
    public static final String PARAM_INCLUDE_TAIL = "IncludeTail";
    @ConfigurationParameter(name = PARAM_INCLUDE_TAIL, mandatory = false, defaultValue = "true")
    private boolean includeTail;

    /**
     * <em>[optional,default=1]</em>
     *
     * The minimum number of occurrences an n-gram must yield in order to be included.
     */
    public static final String PARAM_MIN_FREQUENCY = "minFrequency";
    @ConfigurationParameter(name = PARAM_MIN_FREQUENCY,
            mandatory = false, defaultValue = "1")
    private int minFrequency;

    /**
     * The {@link Multiset} used to collect the frequencies.
     */
    private Multiset<String> ms;

    @Override
    public void initialize(final UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        ms = LinkedHashMultiset.create();
    }

    @Override
    public void process(final JCas jcas, final List<Annotation> annotationList,
            final int index) {
        List<String> tokens = UIMAUtils.getAdjacentTokens(POS.class,
                annotationList, index, n);

        List<String> result;
        if (!includeHead) {
            result = tokens.subList((tokens.size() - 1) / 2, tokens.size());
        } else if (!includeTail) {
            result = tokens.subList(0, (tokens.size() - 1) / 2 + 1);
        } else {
            result = tokens;
        }

        ms.add(MiscUtils.WS_JOINER.join(result));
    }

    @Override
    public void collectionProcessComplete() {
        try {
            WriteMultisets.writeSortedMultiSet(ms, minFrequency, new File(path));
        } catch (IOException ex) {
            getContext().getLogger().log(Level.SEVERE, ex.getMessage());
        }
    }
}
