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
package com.github.fhirschmann.clozegen.lib.formatters;

import org.apache.uima.jcas.JCas;
import org.uimafit.component.Resource_ImplBase;

import com.github.fhirschmann.clozegen.lib.components.api.JCasFormatter;
import com.github.fhirschmann.clozegen.lib.imf.IntermediateFormat;
import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

/**
 * Writes all {@link GapAnnotation}s to a file using the intermediate format.
 *
 * @author Fabian Hirschmann <fabian@hirschmann.email>
 */
public class IMFFormatter extends Resource_ImplBase implements JCasFormatter {
    @Override
    public String format(final JCas jcas) {
        return IntermediateFormat.format(jcas);
    }

    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        return str.toString();
    }
}
