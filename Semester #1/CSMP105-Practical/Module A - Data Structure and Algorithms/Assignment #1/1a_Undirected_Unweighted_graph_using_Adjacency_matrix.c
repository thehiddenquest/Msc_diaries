#include<stdio.h>
#include<stdlib.h>

/* Define Global Variables */
#define MAX 10
int Graph[MAX][MAX];
int nvertices = 0;
/* Function to Create Graph */
void createGraph();
/* Checking for validity vertex */
void ifValid(int*, int*);
/* Function to Add Edges */
void addedge(int, int);
/* Function to Print Graph */
void printGraph();
/* Main Function */
int main()
{
	int i, nedges, src, dest;
	printf("\nEnter the number of vertices: ");
	scanf("%d",&nvertices);
	if (nvertices >= MAX) 
	{
    	printf("\nNumber of vertices exceeds the maximum allowed value (%d)\n", MAX);
    	return -1;
	}
	createGraph();
	printf("\nEnter the number of edges: ");
	scanf("%d",&nedges);
	int maxEdge = (nvertices*(nvertices+1))/2;
	if (nedges >= maxEdge)
	{
		printf("The number of edges exceeds the maximum allowed value for %d vertices which is (%d).\n", nvertices, maxEdge);
		return -1;
	}
	for(i=0; i<nedges; i++)
	{
		printf("\nEnter the source vertex and destination vertex for edge %d: ",i+1);
		scanf("%d %d",&src, &dest);
		ifValid(&src, &dest);
		addedge(src, dest);
	}
	printf("\nAdjacency matrix representation of given graph: \n\n");
	printGraph();
	return 0;
}
void createGraph()
{
	int i,j;
	for(i=0;i<nvertices;i++)
	{
		for(j=0;j<nvertices;j++)
			Graph[i][j]=0;
	}
}
void ifValid(int *src, int *dest)
{
	while((*src) >= nvertices || (*dest) >= nvertices)
	{
		printf("\n Vertex out of graph. Enter valid vertex(numbering from 0)");
		if ((*src) >= nvertices)
		{
			printf("\nEnter source vertex: ");
			scanf("%d", &(*src));
		}
		else
		{
			printf("\nEnter destination vertex: ");
			scanf("%d", &(*dest));
		}
	}
}
void addedge(int src, int dest)
{
	Graph[src][dest] = 1;
	Graph[dest][src] = 1;
}
void printGraph() 
{
    int i, j;

    // Print column numbers
    printf("      ");
    for (j = 0; j < nvertices; j++) {
        printf("%4d", j);
    }
    printf("\n");

    for (i = 0; i < nvertices; i++) {
        // Print row number
        printf("%4d |", i );

        for (j = 0; j < nvertices; j++) {
            printf("%4d", Graph[i][j]);
        }
        printf("\n");
    }
}

