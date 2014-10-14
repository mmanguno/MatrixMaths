from rudiments.basic import add

def multiply(x, y):
    additive = x
    for z in range(1, y):
        x = add.add(x, additive)
    return x
