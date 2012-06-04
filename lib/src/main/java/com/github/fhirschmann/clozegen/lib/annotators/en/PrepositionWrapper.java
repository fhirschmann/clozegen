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
package com.github.fhirschmann.clozegen.lib.annotators.en;

import com.github.fhirschmann.clozegen.lib.annotators.GapAnnotatorInterface;
import com.github.fhirschmann.clozegen.lib.functions.CoveredTextFunction;
import com.github.fhirschmann.clozegen.lib.generator.GapGeneratorInterface;
import com.github.fhirschmann.clozegen.lib.generator.en.PrepositionGapGenerator;
import com.github.fhirschmann.clozegen.lib.generator.en.PrepositionGapGeneratorModel;
import com.github.fhirschmann.clozegen.lib.util.CollectionUtils;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.PP;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.uima.cas.ConstraintFactory;
import org.apache.uima.cas.FSTypeConstraint;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;

/**
 * This annotator creates annotations for prepositions.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class PrepositionWrapper implements GapAnnotatorInterface, SharedResourceObject {

    private PrepositionGapGeneratorModel model;

    @Override
    public void load(DataResource aData) throws ResourceInitializationException {
        URL baseURL = aData.getUrl();
        model = new PrepositionGapGeneratorModel();
        try {
            model.load(Resources.getResource("frequencies/en/prepositions/trigrams.txt"),
                    Resources.getResource("frequencies/en/prepositions/after.txt"),
                    Resources.getResource("frequencies/en/prepositions/before.txt"));
        } catch (IOException ex) {
            Logger.getLogger(PrepositionWrapper.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public FSTypeConstraint getConstraint() {
        FSTypeConstraint cons = ConstraintFactory.instance().createTypeConstraint();
        cons.add(PP.class.getName());
        return cons;
    }

    @Override
    public GapGeneratorInterface generator(
            final List<Annotation> annotationList, final int offset) {
        // Get a list of all POS tags
        List<POS> posList = Lists.newArrayList(
                Iterables.filter(annotationList, POS.class));

        // Get the neighbors of our POS tag
        List<POS> adjacent = CollectionUtils.getNullPaddedAdjacentTo(
                posList, posList.indexOf(annotationList.get(offset)), 1);

        // The tokens of the trigram (A, p, B)
        List<String> tokens = Lists.newArrayList(Collections2.transform(
                adjacent, new CoveredTextFunction()));

        return PrepositionGapGenerator.create(
                tokens.get(0), tokens.get(1), tokens.get(2), model);
    }
}
