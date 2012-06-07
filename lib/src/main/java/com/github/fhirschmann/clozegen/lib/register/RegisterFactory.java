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
package com.github.fhirschmann.clozegen.lib.register;

import com.github.fhirschmann.clozegen.lib.annotators.GapAnnotator;
import com.github.fhirschmann.clozegen.lib.adapter.PrepositionAdapter;
import com.google.common.collect.Sets;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;
import static org.uimafit.factory.ExternalResourceFactory.createExternalResourceDescription;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class RegisterFactory {
    /** Utility class cannot be called. */
    private RegisterFactory() {
    }

    public static Register createDefaultRegister(int answerCount)
            throws ResourceInitializationException {
        Register register = new Register();

        AnalysisEngineDescription preps = createPrimitiveDescription(GapAnnotator.class,
                GapAnnotator.PARAM_ANSWER_COUNT, answerCount,
                GapAnnotator.ADAPTER_KEY,
                createExternalResourceDescription(
                PrepositionAdapter.class,
                PrepositionAdapter.PARAM_PATH, "frequencies/en/prepositions"));

        RegisterEntry entry = new RegisterEntry("prepositions");
        entry.setDescription(preps);
        entry.setName("Preposition Gap Generator");
        entry.setSupportedLanguages(Sets.newHashSet("en"));
        register.add(entry);

        return register;
    }

    public static Register createDefaultRegister()
            throws ResourceInitializationException {
        return createDefaultRegister(4);
    }
}
