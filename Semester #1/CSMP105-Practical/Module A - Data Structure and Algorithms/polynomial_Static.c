#include<stdio.h>
#include<stdlib.h>

int* createPol(int nc) {
    int tc = nc * 2;
    int* polynomial = (int*)malloc(tc * sizeof(int)); // Allocate memory for the polynomial
    if (polynomial == NULL) {
        printf("Memory allocation failed.");
        exit(1);
    }

    // Initialize each element of the polynomial
    int i;
    for (i = 0; i < tc; i += 2) {
        printf("Enter coefficient for term %d: ", i / 2 + 1);
        scanf("%d", &polynomial[i]);
        printf("Enter power for term %d: ", i / 2 + 1);
        scanf("%d", &polynomial[i + 1]);
    }

    return polynomial;
}

void printPol(int* polynomial, int nc) {
    int i, j;
    int tc = nc * 2;
    for (i = 0; i < tc; i += 2) {
        j = i + 1;
        if(polynomial[i] == 0)
        	continue;
        else{
        	printf("%dX^%d ", polynomial[i], polynomial[j]);
        	if (i != tc - 2 && polynomial[i + 2] != 0)
           		printf("+ ");
		}
    }
    printf("\n");
}

int* reducePol(int* polynomial, int nc) {
    int tc = nc * 2; // Total number of elements in the polynomial array
	int i,j;
    // Iterate through the polynomial terms to reduce them
    for (i = 0; i < tc; i += 2) {
        // Skip terms that have already been zeroed out
        if (polynomial[i] == 0) {
            continue;
        }
        for (j = i + 2; j < tc; j += 2) {
            // If coefficients match, combine the powers
            if (polynomial[i+1] == polynomial[j+1]) {
                polynomial[i ] += polynomial[j ];
                polynomial[j] = 0;
                polynomial[j + 1] = 0;
            }
        }
    }

    return polynomial;
}
//int* mulPol(int* polynomial1, int nc1,int* polynomial2, int nc2){
//	
//}
int main() {
    int nc1,nc2;
    printf("\nEnter the number of elements in Polynomial A: ");
    scanf("%d", &nc1);
    printf("\nEnter the number of elements in Polynomial B: ");
    scanf("%d", &nc2);
    printf("\nEnter Polynomial A: \n");
    int* A = createPol(nc1);    
    A = reducePol(A,nc1);
    printf("\nEnter Polynomial B: \n");
    int* B = createPol(nc2);    
    A = reducePol(B,nc2);
    printf("\nPolynomial A : ");
    printPol(A, nc1);
    printf("\nPolynomial B : ");
    printPol(B, nc2);
    
//    int* C = mulPol(A,nc1,B,nc2);
//    printf("\nPolynomial C : ");
//    printPol(C, nc1*nc2);
//	
    // Don't forget to free the allocated memory
    free(A);

    return 0;
}
