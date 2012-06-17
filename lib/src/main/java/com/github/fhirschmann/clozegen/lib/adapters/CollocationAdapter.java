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
import com.github.fhirschmann.clozegen.lib.generators.CollocationGapGenerator;
import com.github.fhirschmann.clozegen.lib.generators.api.GapGenerator;
import com.github.fhirschmann.clozegen.lib.generators.model.CollocationModel;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import java.util.List;
import org.apache.uima.jcas.tcas.Annotation;

/**
 * This adapter is backed by {@link CollocationGapGenerator} and injects
 * a {@link CollocationModel} from {@link URLBasedModelAdapter#PARAM_PATH}.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class CollocationAdapter
        extends URLBasedModelAdapter<CollocationModel> implements GeneratorAdapter {
    @Override
    public GapGenerator generator(
            final List<Annotation> annotationList, final int offset) {
        List<String> tokens = UIMAUtils.getAdjacentTokens(POS.class,
                annotationList, offset, 1);

        GapGenerator generator = new CollocationGapGenerator(tokens, model);
        return generator;
    }
}
