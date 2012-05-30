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
package com.github.fhirschmann.clozegen.lib.annotators;

import com.github.fhirschmann.clozegen.lib.generator.GapGeneratorInterface;
import java.util.List;
import java.util.Set;
import org.apache.uima.cas.FSTypeConstraint;
import org.apache.uima.jcas.tcas.Annotation;

/**
 * Convenience methods for generating gap annotators on the fly.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class GapAnnotatorFactory {
    /**
     * Utility class; should not be called.
     */
    private GapAnnotatorFactory() {
    }

    /**
     * Creates a new gap annotator based on the parameters provided.
     *
     * @param constraint see {@link AbstractGapAnnotator#getConstraint()}
     * @param generator an instance of a gap generator
     * @param shortName see {@link AbstractGapAnnotator#getShortName()}
     * @param longName see {@link AbstractGapAnnotator#getLongName()}
     * @param description see {@link AbstractGapAnnotator#getDescription()}
     * @param supportedLanguages see {@link AbstractGapAnnotator#getSupportedLanguages()}
     * @return a new gap generator
     */
    public static GapAnnotatorInterface create(
            final FSTypeConstraint constraint,
            final GapGeneratorInterface generator,
            final String shortName,
            final String longName,
            final String description,
            final Set<String> supportedLanguages) {
        return new AbstractGapAnnotator() {

            @Override
            public FSTypeConstraint getConstraint() {
                return constraint;
            }

            @Override
            public GapGeneratorInterface generator(
                    final List<Annotation> annotationList, final int offset) {
                return generator;
            }

            @Override
            public String getShortName() {
                return shortName;
            }

            @Override
            public String getLongName() {
                return longName;
            }

            @Override
            public String getDescription() {
                return description;
            }

            @Override
            public Set<String> getSupportedLanguages() {
                return supportedLanguages;
            }
        };
    }
}
