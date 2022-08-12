#!/usr/bin/env python

import re

with open('data.txt') as fh:
    lines = fh.read().splitlines()

result1 = 0
result2 = 0
for line in lines:
    unescaped = re.sub(r'\\(?:x[0-9a-f]{2}|\\|")', '_', line[1:-1])
    result1 += len(line) - len(unescaped)
    result2 += line.count('"') + line.count('\\') + 2
print(result1)
print(result2)
