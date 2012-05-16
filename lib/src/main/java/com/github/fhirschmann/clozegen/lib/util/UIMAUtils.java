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
package com.github.fhirschmann.clozegen.lib.util;

import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.NonEmptyStringList;
import org.uimafit.util.FSCollectionFactory;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class UIMAUtils {
    public static GapAnnotation createGapAnnotation(JCas aJCas,
            Set<String> validAnswers, Set<String> invalidAnswers) {
        final GapAnnotation annotation = new GapAnnotation(aJCas);

        final List<String> allAnswers = Lists.newArrayList(
                Sets.union(invalidAnswers, validAnswers));
        Collections.shuffle(allAnswers);

        final NonEmptyStringList all = (NonEmptyStringList)
                FSCollectionFactory.createStringList(
                aJCas, allAnswers);
        annotation.setAllAnswers(all);

        final NonEmptyStringList valid = (NonEmptyStringList)
                FSCollectionFactory.createStringList(
                aJCas, validAnswers);
        annotation.setValidAnswers(valid);

        return annotation;
    }
}
