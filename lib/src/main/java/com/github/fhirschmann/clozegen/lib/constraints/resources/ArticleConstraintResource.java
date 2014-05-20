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
package com.github.fhirschmann.clozegen.lib.constraints.resources;

import org.apache.uima.cas.ConstraintFactory;
import org.apache.uima.cas.FSMatchConstraint;
import org.apache.uima.cas.FSTypeConstraint;
import org.apache.uima.jcas.JCas;

import com.github.fhirschmann.clozegen.lib.constraints.api.ConstraintResource;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.ART;

/**
 * A constraint which matches articles.
 *
 * Please note that {@link TypeConstraintResource} could also be used
 * for this task.
 *
 * @author Fabian Hirschmann <fabian@hirschmann.email>
 */
public class ArticleConstraintResource extends ConstraintResource {
    @Override
    public FSMatchConstraint getConstraint(final JCas jcas) {
        FSTypeConstraint cons = ConstraintFactory.instance().
                createTypeConstraint();
        cons.add(ART.class.getName());
        return cons;
    }

    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        return str.toString();
    }
}
