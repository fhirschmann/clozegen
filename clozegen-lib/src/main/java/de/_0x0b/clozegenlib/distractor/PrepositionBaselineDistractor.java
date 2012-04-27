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
package de._0x0b.clozegenlib.distractor;

import de._0x0b.clozegenlib.api.Distractor;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class PrepositionBaselineDistractor extends Distractor {

    @Override
    public String[] generate(Sentence sentence, int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
