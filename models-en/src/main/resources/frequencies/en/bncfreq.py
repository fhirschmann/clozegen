#!/usr/bin/env python

import requests

FREQUENCIES = [
        ('http://ucrel.lancs.ac.uk/bncfreq/lists/5_8_all_rank_preposition.txt', 'prepositions/unigrams.txt'),
        ]


def main(url, output):
    req = requests.get(url)

    output_file = open(output, 'w')

    for line in req.text.strip().split('\n'):
        output_file.write(line.strip().replace('_', ' ') + '\n')

if __name__ == '__main__':
    for frequency in FREQUENCIES:
        main(*frequency)
