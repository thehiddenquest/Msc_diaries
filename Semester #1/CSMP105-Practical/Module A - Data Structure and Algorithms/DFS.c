#include <stdio.h>
#include <stdlib.h>

typedef struct Graph {
    int noOfVertex;
    int** adjacencyMatrix;
} Graph;

typedef struct Vertex {
    int visited;
    int dfsNumber;
    int dfsCompletion;
} Vertex;

int stack[100];
int top = -1;

void push(int item) {
    stack[++top] = item;
}

int pop() {
    return stack[top--];
}

int peek() {
    return stack[top];
}

int isStackEmpty() {
    return top == -1;
}

Graph* createGraph(int noOfvertex) {
    Graph* graph = (Graph*)malloc(sizeof(Graph));
    graph->noOfVertex = noOfvertex;
    graph->adjacencyMatrix = (int**)malloc(noOfvertex * sizeof(int*));
    int i, j;
    for (i = 0; i < noOfvertex; i++) {
        graph->adjacencyMatrix[i] = (int*)malloc(noOfvertex * sizeof(int));
        for (j = 0; j < noOfvertex; j++)
            graph->adjacencyMatrix[i][j] = 0;
    }
    return graph;
}

Graph* addEdgeUndirectedUnweighted(Graph* graph, int Apoint, int Bpoint) {
    graph->adjacencyMatrix[Apoint][Bpoint] = 1;
    graph->adjacencyMatrix[Bpoint][Apoint] = 1;
    return graph;
}

void printGraph(Graph* graph)
{
	int i,j;
	for(i=0;i<graph->noOfVertex;i++)
	{
		for(j=0;j<graph->noOfVertex;j++)
			printf(" %d ",graph->adjacencyMatrix[i][j]);
		printf("\n");
	}
	
}
void depthFirstSearch(Graph* graph, Vertex* vertices, int startVertex) {
    printf("\nDepth First Search: ");
    push(startVertex);
    vertices[startVertex].visited = 1;
    vertices[startVertex].dfsNumber = 1;

    int dfsCounter = 2; // Starting DFS numbers from 2

    while (!isStackEmpty()) {
        int currentVertex = peek();
        int unvisitedVertex = -1;

        int i;
        for (i = 0; i < graph->noOfVertex; i++) {
            if (graph->adjacencyMatrix[currentVertex][i] == 1 && vertices[i].visited == 0) {
                unvisitedVertex = i;
                break;
            }
        }

        if (unvisitedVertex == -1) {
            vertices[currentVertex].dfsCompletion = dfsCounter++;
            pop();
        }else {
    push(unvisitedVertex);
    printf("%d (%d)", unvisitedVertex,dfsCounter+1);  // Print the vertex first
    vertices[unvisitedVertex].visited = 1;
    vertices[unvisitedVertex].dfsNumber = dfsCounter++;
    // Update the currentVertex to the unvisitedVertex
    currentVertex = unvisitedVertex;
}
    }
}

void classify_edges(Graph* graph, Vertex* vertices) {
    printf("\nClassification of Edges:\nTree edges: {");

    int i, j;
    int first_edge = 1; // Flag to check if it's the first tree edge
    for (i = 0; i < graph->noOfVertex; i++) {
        for (j = 0; j < graph->noOfVertex; j++) {
            if (graph->adjacencyMatrix[i][j] == 1) {
                // Check if the edge is a tree edge: current vertex is parent, j is child
                if (!vertices[j].visited && vertices[i].dfsNumber < vertices[j].dfsNumber) {
                    if (!first_edge) {
                        printf(", ");
                    }
                    printf("(%d, %d)", i, j);
                    first_edge = 0;
                }
            }
        }
    }
    printf("}\n");
}


int main() {
    int noOfVertex, noOfEdge;
    Graph* graph;
    printf("\nEnter the number of vertices in the graph: ");
    scanf("%d", &noOfVertex);
	printf("\nEnter the number of edges in the graph: ");
	scanf("%d",&noOfEdge);
	if(noOfVertex <0 || (noOfEdge > (noOfVertex*(noOfVertex-1)/2))){
		printf("\nPlease enter a simple graph.");
		return -1;
	}
    graph = createGraph(noOfVertex);
	int i = 0;
	while(i<noOfEdge){
		int endPoint1, endPoint2, weight;
		printf("\nEnter two endpoints of the edge: ");
		scanf("%d%d",&endPoint1,&endPoint2);
		if(endPoint1 == endPoint2){
			printf("\nSelf loop is not allowed.");
			continue;
		}
		if(graph->adjacencyMatrix[endPoint1][endPoint2] != 0 ){
			printf("\nAlready declared an edge.");
			continue;
		}

		graph = addEdgeUndirectedUnweighted(graph,endPoint1,endPoint2);
		i++;
	}
	printf("\nAdjacency matrix representation of given graph is : \n");
	printGraph(graph);

    Vertex* vertices = (Vertex*)malloc(noOfVertex * sizeof(Vertex));
    for ( i = 0; i < noOfVertex; i++) {
        vertices[i].visited = 0;
    }

    int startVertex;
    printf("\nEnter the starting vertex for DFS: ");
    scanf("%d", &startVertex);

    depthFirstSearch(graph, vertices, startVertex);
    classify_edges(graph, vertices);

    return 0;
}
