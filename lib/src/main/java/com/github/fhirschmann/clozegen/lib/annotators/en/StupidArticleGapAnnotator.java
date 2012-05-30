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

import com.github.fhirschmann.clozegen.lib.annotators.AbstractGapAnnotator;
import com.github.fhirschmann.clozegen.lib.generator.GapGeneratorInterface;
import com.github.fhirschmann.clozegen.lib.generator.en.StupidArticleGapGenerator;
import com.google.common.collect.Sets;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.ART;
import java.util.List;
import java.util.Set;
import org.apache.uima.cas.ConstraintFactory;
import org.apache.uima.cas.FSTypeConstraint;
import org.apache.uima.jcas.tcas.Annotation;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class StupidArticleGapAnnotator extends AbstractGapAnnotator {

    @Override
    public FSTypeConstraint getConstraint() {
        FSTypeConstraint cons = ConstraintFactory.instance().createTypeConstraint();
        cons.add(ART.class.getName());
        return cons;
    }

    @Override
    public GapGeneratorInterface generator(
            final List<Annotation> annotationList, final int offset) {
        return StupidArticleGapGenerator.create(
                annotationList.get(offset).getCoveredText());
    }

    @Override
    public String getShortName() {
        return "article_stupid";
    }

    @Override
    public String getLongName() {
        return "Stupid Article Gap Generator";
    }

    @Override
    public String getDescription() {
        return "Simplest method of generating gaps for articles one can think of.";
    }

    @Override
    public Set<String> getSupportedLanguages() {
        return Sets.newHashSet("en");
    }
}
