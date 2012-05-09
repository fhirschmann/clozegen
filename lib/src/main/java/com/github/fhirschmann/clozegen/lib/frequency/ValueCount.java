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
package com.github.fhirschmann.clozegen.lib.frequency;

/**
 * This is a generic way of mapping a frequency to a value.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class ValueCount<T> implements Comparable<ValueCount<T>> {
    /** The count (number of occurrences). */
    private Integer count;

    /** The value to assign the count to. */
    private T value;

    /**
     * Initialize the Mapping.
     *
     * @param value the value to assign the count to
     * @param count the count to assign
     */
    public ValueCount(final T value, final int count) {
        this.value = value;
        this.count = count;
    }

    /**
     * Initialize this mapping and set count to zero.
     *
     * @param value the value
     */
    public ValueCount(final T value) {
        this.value = value;
        this.count = 0;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(final int count) {
        this.count = count;
    }

    /**
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(final T value) {
        this.value = value;
    }

    @Override
    public int compareTo(final ValueCount<T> valueCount) {
        return count.compareTo(valueCount.getCount());
    }

    @Override
    public String toString() {
        return String.format("%s/%s", getValue(), getCount());
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof ValueCount)
                && getValue().equals(((ValueCount) other).getValue());
    }
}
