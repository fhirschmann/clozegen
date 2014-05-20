/*
 * Copyright (c) 2012 Fabian Hirschmann <fabian@hirschmann.email>
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
package com.github.fhirschmann.clozegen.lib.adapters;

import java.util.List;

import org.apache.uima.jcas.tcas.Annotation;

import com.github.fhirschmann.clozegen.lib.adapters.api.GeneratorAdapter;
import com.github.fhirschmann.clozegen.lib.adapters.api.URLBasedModelAdapter;
import com.github.fhirschmann.clozegen.lib.generators.CollocationGapGenerator;
import com.github.fhirschmann.clozegen.lib.generators.api.GapGenerator;
import com.github.fhirschmann.clozegen.lib.generators.model.CollocationModel;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;

/**
 * This adapter is backed by {@link CollocationGapGenerator} and injects
 * a {@link CollocationModel} from {@link URLBasedModelAdapter#PARAM_PATH}.
 *
 * @author Fabian Hirschmann <fabian@hirschmann.email>
 */
public class CollocationAdapter
        extends URLBasedModelAdapter<CollocationModel> implements GeneratorAdapter {
    @Override
    public GapGenerator generator(
            final List<Annotation> annotationList, final int offset) {
        List<String> tokens = UIMAUtils.getAdjacentTokens(POS.class,
                annotationList, offset, 1);
        return new CollocationGapGenerator(tokens, model);
    }
}
