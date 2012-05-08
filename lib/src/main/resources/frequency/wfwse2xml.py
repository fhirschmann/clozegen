#!/usr/bin/env python
#
# This script will generate a XML file from Word Frequencies
# in Written and Spoken English [1] to be used with the
# FrequencyList class in clozegen-lib.
#
# [1] http://ucrel.lancs.ac.uk/bncfreq/

import requests
from xml.etree.ElementTree import Element, SubElement, ElementTree


FREQUENCIES = [
        ('http://ucrel.lancs.ac.uk/bncfreq/lists/5_8_all_rank_preposition.txt', 'prepositions.xml')
        ]


def main(url, xml):
    req = requests.get(url)

    root = Element('frequencies')
    tree = ElementTree(root)

    for line in req.text.strip().split('\n'):
        raw = line.strip().split('\t')

        SubElement(root, 'frequency',
                attrib={'sofa': raw[0].replace('_', ' '), 'value': raw[1]})

    tree.write(xml)

if __name__ == '__main__':
    for frequency in FREQUENCIES:
        main(*frequency)
