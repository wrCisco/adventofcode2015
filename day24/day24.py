#!/usr/bin/env python

from itertools import combinations
from math import prod, inf

def main():
    with open('data.txt') as fh:
        packages = set(int(line) for line in fh.readlines())

    combs1 = set()
    right_weight = sum(packages) // 3
    for c in combinations(packages, 6):
        if sum(c) == right_weight:
            combs1.add(frozenset(c))

    min_quantum_entanglement = inf
    for c in combs1:
        qe = find_quantum_entanglement(packages, c, right_weight)
        if qe < min_quantum_entanglement:
            min_quantum_entanglement = qe
    print(min_quantum_entanglement)  # first answer

    combs2 = {}
    right_weight2 = sum(packages) // 4
    for i in range(4, 11):
        combs2[i] = set()
        for c in combinations(packages, i):
            if sum(c) == right_weight2:
                combs2[i].add(frozenset(c))

    min_quantum_entanglement2 = inf
    for c in combs2[4]:
        qe2 = find_quantum_entanglement2(packages, c, combs2)
        if qe2 < min_quantum_entanglement2:
            min_quantum_entanglement2 = qe2
    print(min_quantum_entanglement2)  # second answer


def find_quantum_entanglement(packages, group, right_weight):
    others = packages - group
    for i in range(6, len(others) // 2 + 1):
        for other in combinations(others, i):
            if sum(other) == right_weight:
                return prod(group)
    return inf


def find_quantum_entanglement2(packages, group, groups):
    others = packages - group
    for i in range(4, len(others) // 3 + 1):
        for other in groups[i]:
            if group & other:
                continue
            left_overs = others - other
            for j in range(i, len(left_overs) // 2 + 1):
                for left_over in groups[j]:
                    if left_over & group or left_over & other:
                        continue
                    return prod(group)
    return inf



if __name__ == '__main__':
    main()
