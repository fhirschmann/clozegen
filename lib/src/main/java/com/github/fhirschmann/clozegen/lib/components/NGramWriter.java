/*
 * Copyright (c) 2012 Fabian Hirschmann <fabian@hirschm.net>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.fhirschmann.clozegen.lib.components;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.uimafit.descriptor.ConfigurationParameter;

import com.github.fhirschmann.clozegen.lib.components.api.ConstraintBasedConsumer;
import com.github.fhirschmann.clozegen.lib.constraints.resources.CoveredTextConstraintResource;
import com.github.fhirschmann.clozegen.lib.functions.LowerCaseFunction;
import com.github.fhirschmann.clozegen.lib.multiset.WriteMultisets;
import com.github.fhirschmann.clozegen.lib.util.MiscUtils;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.Collections2;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

/**
 * Extracts n-grams and writes them to a file.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class NGramWriter extends ConstraintBasedConsumer {
    /**
     * <em>[mandatory]</em>
     *
     * The file to which the n-grams will be written to.
     */
    public static final String PARAM_OUTPUT_PATH = "OutputPath";
    @ConfigurationParameter(name = PARAM_OUTPUT_PATH, mandatory = true)
    private String path;

    /**
     * <em>[optional,default=true]</em>
     *
     * This setting indicates if we are working on POS-Tags.
     *
     * <p>
     * When this is set to true, n-grams will be based on the text
     * covered by POS tags. You should only need to set this to false
     * if you want to work on a non-POS-tagged text, in which case
     * we will only work on tokens. This will normally be done in
     * conjunction with a {@link CoveredTextConstraintResource}.
     * </p>
     *
     */
    public static final String WORK_ON_POS_TAGS = "WorkOnPosTags";
    @ConfigurationParameter(name = WORK_ON_POS_TAGS, mandatory = false,
            defaultValue = "true")
    private boolean workOnPosTags;

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
     * that comes before the word matched by {@link NGramWriter#CONSTRAINT_KEY}.
     * In this case, given {@link NGramWriter#PARAM_N} is set to {@code 1}
     * (the default), <i>of anything</i> will be extracted from the sentence
     * <i>He can't think of anything.</i> given that we are matching prepositions.
     * </p>
     *
     * <p>
     * This should not be set to {@code false} in addition to setting
     * {@link NGramWriter#PARAM_INCLUDE_TAIL} to {@code false}.
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
     * that comes after the word matched by {@link NGramWriter#CONSTRAINT_KEY}.
     * In this case, given {@link NGramWriter#PARAM_N} is set to {@code 1}
     * (the default), <i>think of</i> will be extracted from the sentence
     * <i>He can't think of anything.</i> given that we are matching prepositions.
     * </p>
     *
     * <p>
     * This should not be set to {@code false} in addition to setting
     * {@link NGramWriter#PARAM_INCLUDE_HEAD} to {@code false}.
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
     * <em>[optional,default=false]</em>
     *
     * Transform all n-grams to lowercase.
     */
    public static final String PARAM_LOWER_CASE = "lowerCase";
    @ConfigurationParameter(name = PARAM_LOWER_CASE,
            mandatory = false, defaultValue = "false")
    private boolean lowerCase;

    /**
     * The {@link Multiset} used to collect the frequencies.
     */
    private Multiset<String> ms;

    @Override
    public void initialize(final UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        ms = LinkedHashMultiset.create();
        checkArgument(includeHead || includeTail,
                "includeHead and includeTail cannot both be false. Use N=0 instead.");
        checkArgument(n >= 0, "N must be greater than or equal to 0.");
    }

    @Override
    public void process(final JCas jcas, final List<Annotation> annotationList,
            final int index) {
        List<String> tokens = UIMAUtils.getAdjacentTokens(
                workOnPosTags ? POS.class : Token.class, annotationList, index, n);

        List<String> result;
        if (!includeHead) {
            result = tokens.subList((tokens.size() - 1) / 2, tokens.size());
        } else if (!includeTail) {
            result = tokens.subList(0, (tokens.size() - 1) / 2 + 1);
        } else {
            result = tokens;
        }


        Collection<String> lowered = lowerCase
                ? Collections2.transform(result, new LowerCaseFunction())
                : result;

        ms.add(MiscUtils.WS_JOINER.join(lowered));
    }

    @Override
    public void collectionProcessComplete() {
        try {
            WriteMultisets.writeSortedMultiSet(ms, minFrequency, new File(path));
        } catch (IOException ex) {
            getContext().getLogger().log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        str.add("path", path);
        str.add("includeHead", includeHead);
        str.add("includeTail", includeTail);
        str.add("lowerCase", lowerCase);
        str.add("minFrequency", minFrequency);
        str.add("n", n);
        return str.toString();
    }
}
