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

import static com.google.common.base.Preconditions.checkNotNull;

import org.apache.uima.jcas.JCas;
import org.uimafit.component.Resource_ImplBase;
import org.uimafit.util.FSCollectionFactory;
import org.uimafit.util.JCasUtil;

import com.github.fhirschmann.clozegen.lib.components.api.JCasFormatter;
import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

/**
 * Formats a {@link JCas} as LaTeX. Please see the on-line documentation
 * for more details.
 *
 * @author Fabian Hirschmann <fabian@hirschmann.email>
 */
public class LaTeXFormatter extends Resource_ImplBase implements JCasFormatter {
    @Override
    public String format(final JCas jcas) {
        StringBuilder sb = new StringBuilder();
        String text = checkNotNull(jcas).getDocumentText();
        int position = 0;
        for (GapAnnotation gap : JCasUtil.select(jcas, GapAnnotation.class)) {
            sb.append(text.substring(position, gap.getBegin()));
            sb.append("\\clozeitem{");
            sb.append(Joiner.on(", ").join(
                    FSCollectionFactory.create(gap.getValidAnswers())));
            sb.append("}{");
            sb.append(Joiner.on(", ").join(
                    FSCollectionFactory.create(gap.getAllAnswers())));
            sb.append("}");
            position = gap.getEnd();
        }
        sb.append(text.substring(position));

        return sb.toString();
    }

    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        return str.toString();
    }
}
