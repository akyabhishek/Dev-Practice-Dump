def sockMerchant(n, ar):
    pair=0

    while ar !=[]:
        print(ar)
        if ar.count(ar[0])>=2:
            z=ar[0]
            pair=pair+1
            ar.remove(z)
            ar.remove(z)
        else:
            ar.remove(ar[0])
    return pair
sockMerchant(9,[1,2,1,3,2,3,4,9])