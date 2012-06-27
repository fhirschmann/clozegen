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
package com.github.fhirschmann.clozegen.lib.examples;

import com.github.fhirschmann.clozegen.lib.adapters.api.AbstractResource;
import com.github.fhirschmann.clozegen.lib.adapters.api.GeneratorAdapter;
import com.github.fhirschmann.clozegen.lib.adapters.api.GenericAdapter;
import com.github.fhirschmann.clozegen.lib.generators.api.GapGenerator;
import java.util.List;
import org.apache.uima.jcas.tcas.Annotation;

/**
 * An example adapter.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
/// START SNIPPET: stupidartad
public class StupidArticleAdapter extends AbstractResource implements GeneratorAdapter {
    @Override
    public GapGenerator generator(
            final List<Annotation> annotationList, final int offset) {
        StupidArticleGapGenerator gen = new StupidArticleGapGenerator();
        gen.initialize(annotationList.get(offset).getCoveredText());
        return gen;
    }
}
/// END SNIPPET: stupidartad
