def squareRoot(n, l) :
	x = n
	count = 0
	while (1) :
		count += 1
		root = 0.5 * (x + (n / x))
		if (abs(root - x) < l) :
			break
		x = root
	return root
print("Square root of 3 :",squareRoot(3, 0.00001))
