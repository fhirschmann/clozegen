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
package com.github.fhirschmann.clozegen.lib.generators;

import com.github.fhirschmann.clozegen.lib.generators.api.Gap;
import com.github.fhirschmann.clozegen.lib.generators.api.MultisetModelBasedGapGenerator;
import com.github.fhirschmann.clozegen.lib.generators.api.SingleTokenInputGapGenerator;
import com.github.fhirschmann.clozegen.lib.generators.model.MultisetModel;
import com.github.fhirschmann.clozegen.lib.util.CollectionUtils;
import com.github.fhirschmann.clozegen.lib.util.MultisetUtils;
import com.google.common.base.Optional;
import java.util.List;

/**
 * Suggests distractors with a frequency similar to that of the input word.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class FrequencyGapGenerator
        implements SingleTokenInputGapGenerator, MultisetModelBasedGapGenerator {
    /**
     * The model to use.
     */
    private MultisetModel model;

    /**
     * The word to generate a gap for.
     */
    private String token;

    @Override
    public void initialize(final MultisetModel model) {
        this.model = model;
    }

    @Override
    public void initialize(final String token) {
        this.token = token;
    }

    @Override
    public Optional<Gap> generate(final int count) {
        List<String> candidates = MultisetUtils.
                sortedElementList(model.getMultiset(), count);
        if (candidates.contains(token)) {
            int index = candidates.indexOf(token);
            return Optional.of(Gap.with(token,
                    CollectionUtils.getAdjacentTo(candidates, index, count).
                    subList(0, count).toArray(new String[0])));
        } else {
            return Optional.absent();
        }
    }
}
