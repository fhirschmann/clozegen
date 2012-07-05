/*
 * Copyright (c) 2012 Fabian Hirschmann <fabian@hirschm.net>
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
package com.github.fhirschmann.clozegen.lib.adapters.api;

import java.util.Map;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;
import org.uimafit.component.Resource_ImplBase;

/**
 * An extension to {@link Resource_ImplBase} which provides a more convenient
 * {@link AbstractResource#initialize()}.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class AbstractResource extends Resource_ImplBase {
    @Override
    public boolean initialize(final ResourceSpecifier aSpecifier,
            final Map<String, Object> aAdditionalParams)
            throws ResourceInitializationException {
        if (!super.initialize(aSpecifier, aAdditionalParams)) {
            return false;
        } else {
            return initialize();
        }
    }

    /**
     * Called upon resource initialization. Overwrite this method if required.
     *
     * @return true on success
     */
    public boolean initialize() {
        return true;
    }
}
