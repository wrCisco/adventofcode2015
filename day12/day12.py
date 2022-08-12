#!/usr/bin/env python


import re
import json

def sum_values(obj):
    partial = 0
    if isinstance(obj, list):
        for elem in obj:
            try:
                partial += int(elem)
            except ValueError:
                pass
            except TypeError:
                partial += sum_values(elem)
    elif isinstance(obj, dict):
        for k, v in obj.items():
            if v == 'red':
                return 0
            try:
                partial += int(v)
            except ValueError:
                pass
            except TypeError:
                partial += sum_values(v)
    return partial


def main():
    with open('data.txt') as fh:
        text = fh.read()
    print(sum(int(n) for n in re.findall(r'-?[0-9]+', text)))  # first answer
    print(sum_values(json.loads(text)))  # second answer


if __name__ == '__main__':
    main()
