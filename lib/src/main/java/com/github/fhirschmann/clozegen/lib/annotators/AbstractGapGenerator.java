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
 * You should have received validAnswers copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.github.fhirschmann.clozegen.lib.annotators;

import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import org.apache.log4j.Logger;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.NonEmptyStringList;
import org.uimafit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.tcas.Annotation;
import org.uimafit.util.FSCollectionFactory;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Base class for all gap generators.
 * <p>
 * A gap generator needs to be decorated with {@link GapGeneratorMetadata} and
 * specify the language it supports. Additionally, it can specify the POS
 * Subtag it wants to work on. For example, when generating gaps for articles,
 * one would use:
 * <pre>
 * @GapGeneratorMetadata(languageCode = "en", wantedPosSubtag = ART.class)
 * </pre>
 * </p>
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public abstract class AbstractGapGenerator extends JCasAnnotator_ImplBase {

    /** The logger (for all inheriting classes as well). */
    protected final Logger log = Logger.getLogger(this.getClass());

    /** The language code for an annotator. Needs to be set GapGeneratorMetadata. */
    private String languageCode;

    /** The POS tags (Penn TreeBank Tags) the generator is working on. */
    //protected String[] wantedPosTags = new String[] {};

    /** The POS Subtag the generator is working on. */
    private int wantedPosSubtag;;

    /**
     * Generates cloze tests item from validAnswers given subject.
     *
     * <p>
     * This method should generate a number of valid and invalid answers for a given
     * subject. For example, the subject "a" in the "articles" class might have "a" as
     * only valid answers and {"an","the"} as invalid answers.
     * </p>
     *
     * @param subject the word to generate validAnswers cloze test item for
     * @return valid and invalid answers for validAnswers gap
     */
    public abstract Gap generate(Annotation subject);

    /**
     * Constructor which initializes the {@links GapGeneratorMetadata} annotator
     * using reflection.
     *
     */
    public AbstractGapGenerator() {

        super();
        for (java.lang.annotation.Annotation an : getClass().getAnnotations()) {
            if (an instanceof GapGeneratorMetadata) {
                final GapGeneratorMetadata md = (GapGeneratorMetadata) an;
                languageCode = md.languageCode();

                // This block uses java reflection in order to allow the
                // annotation of generators.
                for (Field field : md.wantedPosSubtag().getDeclaredFields()) {
                    if (field.getName().equals("type")) {
                        try {
                            wantedPosSubtag = field.getInt(md.wantedPosSubtag());
                        } catch (IllegalArgumentException ex) {
                            wantedPosSubtag = POS.type;
                        } catch (IllegalAccessException ex) {
                            wantedPosSubtag = POS.type;
                        }
                    }
                }

            }
        }
        checkNotNull(languageCode, "languageCode cannot be null. Please decorate"
                + "your generator using GapGeneratorMetadata.");
    }

    /**
     * Process the annotator.
     *
     * <p>
     * This method will set up the annotation for words in a word class (as defined by the
     * extending classes) and call generate() in the extending class for each word in this
     * class.
     * </p>
     *
     * @param jcas the CAS to work on
     * @throws AnalysisEngineProcessException on errors during engine execution
     */
    @Override
    public final void process(final JCas jcas) throws AnalysisEngineProcessException {
        if (!languageCode.equals(jcas.getDocumentLanguage())) {
            log.error("This annotator is not made for your language!");
            return;
        }
        for (final Iterator<Annotation> i = jcas.getAnnotationIndex(
                wantedPosSubtag).iterator(); i.hasNext();) {
            final Annotation subject = i.next();
            //final POS pos = (POS) subject;

            //if (Arrays.asList(wantedTags).contains(pos.getPosValue())) {
                final GapAnnotation annotation = new GapAnnotation(jcas);

                annotation.setBegin(subject.getBegin());
                annotation.setEnd(subject.getEnd());

                final Gap gap = generate(subject);

                if (gap == null) {
                    // Skip gap annotation if no gap was generated
                    continue;
                }

                final NonEmptyStringList allAnswers = (NonEmptyStringList)
                        FSCollectionFactory.createStringList(
                        jcas, gap.getAllAnswers());
                annotation.setAllAnswers(allAnswers);

                final NonEmptyStringList validAnswers = (NonEmptyStringList)
                        FSCollectionFactory.createStringList(
                        jcas, gap.getValidAnswers());
                annotation.setValidAnswers(validAnswers);

                annotation.addToIndexes();
            //}
        }
    }

    /**
     * @return the languageCode
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * @return the wantedPosSubtag
     */
    public int getWantedPosSubtag() {
        return wantedPosSubtag;
    }
}
