import random
import sys
import os

def generate_problem(n: int) -> tuple[list[int], int]:
    """Generate a problem of size n for the Subset Sum Problem (SSP).

    Every weight is randomly drawn from {1...1000} and the target C is the total weight
    of a randomly chosen subset S of items. Each item had a 0.5 selectable probability.
    """

    weights = []
    for _ in range(n):
        w = random.randint(1, 1000)
        weights.append(w)

    c = 0
    for w in weights:
        if random.random() > 0.5:
            c += w

    return weights, c

    

if __name__ == "__main__":
    n = int(sys.argv[1])
    weights, c = generate_problem(n)

    os.makedirs('problems', exist_ok=True)
    with open(f'problems/s{n}', 'w') as f:
        f.write(f"{c}\n\n")
        for w in weights:
            f.write(f"{w} ")
        
