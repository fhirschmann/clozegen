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
package com.github.fhirschmann.clozegen.plugins.annotator.germanarticles;

import static org.uimafit.factory.ExternalResourceFactory.createExternalResourceDescription;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.uima.resource.ResourceInitializationException;

import com.github.fhirschmann.clozegen.lib.components.GapAnnotator;
import com.github.fhirschmann.clozegen.lib.constraints.resources.CoveredTextConstraintResource;
import com.github.fhirschmann.clozegen.lib.plugins.api.Plugin;
import com.github.fhirschmann.clozegen.lib.register.AnnotatorRegisterEntry;
import com.github.fhirschmann.clozegen.lib.register.Registers;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.ART;

/**
 * A plugin for the {@link GermanArticleGapGenerator}.
 *
 * @author Fabian Hirschmann <fabian@hirschmann.email>
 */
public class GermanArticlePlugin implements Plugin {
    @Override
    public void init() {
        AnnotatorRegisterEntry entry =
                    new AnnotatorRegisterEntry("germanarticles",
                GapAnnotator.class,
                GapAnnotator.ADAPTER_KEY,
                createExternalResourceDescription(GermanArticleAdapter.class),
                GapAnnotator.CONSTRAINT_KEY,
                createExternalResourceDescription(
                CoveredTextConstraintResource.class,
                CoveredTextConstraintResource.PARAM_TYPE, ART.class.getName(),
                CoveredTextConstraintResource.PARAM_MATCH, "der,die,das"));

        entry.setName("German nominative Article Gap Generator (der,die,das)");
        entry.setSupportedLanguage("de");
        try {
            Registers.annotator().add(entry);
        } catch (ResourceInitializationException ex) {
            Logger.getLogger(GermanArticlePlugin.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
}
