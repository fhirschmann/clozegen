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
package com.github.fhirschmann.clozegen.lib.adapters;

import com.github.fhirschmann.clozegen.lib.adapters.api.GeneratorAdapter;
import com.github.fhirschmann.clozegen.lib.adapters.api.URLBasedModelAdapter;
import com.github.fhirschmann.clozegen.lib.generators.FrequencyGapGenerator;
import com.github.fhirschmann.clozegen.lib.generators.api.GapGenerator;
import com.github.fhirschmann.clozegen.lib.generators.model.MultisetModel;
import java.util.List;
import org.apache.uima.jcas.tcas.Annotation;

/**
 * An adapter for {@link FrequencyGapGenerator}.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class FrequencyAdapter
        extends URLBasedModelAdapter<MultisetModel> implements GeneratorAdapter {
    @Override
    public GapGenerator generator(
            final List<Annotation> annotationList, final int offset) {
        FrequencyGapGenerator generator = new FrequencyGapGenerator();
        generator.initialize(model);
        generator.initialize(annotationList.get(offset).getCoveredText());
        return generator;
    }
}
