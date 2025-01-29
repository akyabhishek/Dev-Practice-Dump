def fab(n):
    if n<=1:
        return n
    else:
        return fab(n-1) + fab(n-2)
