#include<stdio.h>
#include<stdlib.h>

typedef struct Graph{
	int noOfVertex;
	int** adjacencyMatrix;	
}Graph;

Graph* createGraph(int);
Graph* addEdge(Graph*,int , int);
void printGraph(Graph*);
int main()
{
	int noOfVertex, noOfEdge, noOfPossibleEdge, endPoint1, endPoint2;
	Graph* graph;
	printf("\nEnter the number of vertices in the graph: ");
	scanf("%d",&noOfVertex);
	if(noOfVertex<1)
	{
		printf("\nPlease enter a simple graph.");
		return -1;
	}
	printf("\nEnter the number of edges in the graph: ");
	scanf("%d",&noOfEdge);
	noOfPossibleEdge = noOfVertex*(noOfVertex-1)/2;
	if(noOfEdge>noOfPossibleEdge)
	{
		printf("\nPlease Enter a simple graph.");
		return -1;
	}
	graph = createGraph(noOfVertex);
	int i = 0;
	while(i<noOfEdge)
	{
		printf("\nEnter the two endpoints for a edge : ");
		scanf("%d%d",&endPoint1,&endPoint2);
		if(endPoint1 == endPoint2)
		{
			printf("\nSelf loop not allowed.");
			continue;
		}
		if(graph->adjacencyMatrix[endPoint1][endPoint2] != 0)
		{	
			printf("\nAlready defined an edge.");
			continue;
		}
		graph = addEdge(graph, endPoint1, endPoint2);
		i++;
	}
	printf("\nAdjacency matrix representation of the given graph is : \n");
	printGraph(graph);
	return 0;
}
Graph* createGraph(int noOfvertex)
{
	Graph* graph = (Graph*)malloc(sizeof(Graph));
	graph->noOfVertex = noOfvertex;
	graph->adjacencyMatrix = (int**)malloc(noOfvertex*sizeof(int*)); // heads of the matrix(rows)
	int i,j;
	for(i = 0; i<noOfvertex; i++)
	{
		graph->adjacencyMatrix[i] = (int*)malloc(noOfvertex*sizeof(int)); // contents of the matrix
	}
	  for ( i = 0; i < noOfvertex; i++) {
        for ( j = 0; j < noOfvertex; j++) {
            graph->adjacencyMatrix[i][j] = 0;
        }
    }
    return graph;
}
Graph* addEdge(Graph* graph, int APoint, int BPoint)
{
	graph->adjacencyMatrix[APoint][BPoint] = 1;
	graph->adjacencyMatrix[BPoint][APoint] = 1;
	return graph;
}
void printGraph(Graph* graph)
{
	int i,j;
	for ( i = 0; i < graph->noOfVertex; i++) {
        for ( j = 0; j < graph->noOfVertex; j++) {
            printf(" %d ",graph->adjacencyMatrix[i][j]);
        }
        printf("\n");
    }
}
