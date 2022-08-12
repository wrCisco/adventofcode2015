#!/usr/bin/env python


import re

def main():
    with open('data.txt') as fh:
        lines = [l.strip() for l in fh.readlines() if l.strip()]
    a = compute(lines)
    print(a)
    for i, line in enumerate(lines):
        if line.endswith(' -> b'):
            lines[i] = f'{a} -> b'
    print(compute(lines))

def compute(lines):
    wires = {}
    for line in lines:
        expr, dest = line.split(' -> ')
        try:
            wires[dest] = int(expr)
        except ValueError:
            wires[dest] = -1

    while wires['a'] == -1:
        for line in lines:
            expr, dest = line.split(' -> ')
            if wires[dest] == -1:
                if "NOT" in expr:
                    w = expr[4:]
                    if wires.get(w) != -1:
                        allf = 0xFFFF
                        wires[dest] = ~wires[w] & allf
                elif re.match(r'^[a-z]+$', expr):
                    w = expr
                    if wires.get(w) != -1:
                        wires[dest] = wires.get(w)
                else:
                    try:
                        w1, w2 = re.split(r' (?:AND|OR|LSHIFT|RSHIFT) ', expr)
                    except ValueError:
                        print(expr, "Error")
                    if wires.get(w1) != -1 and wires.get(w2) != -1:
                        try:
                            v1 = int(w1)
                        except ValueError:
                            v1 = wires[w1]
                        try:
                            v2 = int(w2)
                        except ValueError:
                            v2 = wires[w2]
                        if "AND" in expr:
                            wires[dest] = v1 & v2
                        elif "OR" in expr:
                            wires[dest] = v1 | v2
                        elif "LSHIFT" in expr:
                            wires[dest] = v1 << v2
                        elif "RSHIFT" in expr:
                            wires[dest] = v1 >> v2
    return wires['a']

if __name__ == '__main__':
    main()
