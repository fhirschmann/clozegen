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
package com.github.fhirschmann.clozegen.lib.generators.api;

import com.google.common.base.Optional;

/**
 * This is the basic interface which Gap Generators must implement.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public interface GapGenerator {
    /**
     * Called for each word for which a gap should be generated.
     *
     * <p>
     * This method should return an {@link Optional} Gap. {@link Optional} is a way
     * of replacing a nullable Gap reference with a non-null value in order to
     * prevent the ambiguity that comes with null.
     * </p>
     *
     * @param count the number of answers to generate
     * @return a new gap
     */
    Optional<Gap> generate(int count);
}
