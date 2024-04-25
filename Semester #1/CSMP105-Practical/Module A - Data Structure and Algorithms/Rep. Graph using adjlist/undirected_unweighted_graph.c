#include <stdio.h>
#include <stdlib.h>

/* Type declarations */

typedef struct Vertex { //Define a vertex 
    int value;
    struct Vertex* next;
} vertex;

typedef struct Graph { // Define the Graph 
    int nVertex; // Total Number of nodes
    vertex** adjacentVertices;	// Adjacency List
} graph;

/* Prototype declaration */

graph* CreateGraph(int);
void AddEdge(graph* , int , int ); 
vertex* FindMid(vertex* );
vertex* merge(vertex* , vertex* );
vertex* MergeSort(vertex*);
void sort(graph* );
void printGraph(graph*);

/* Main module */

int main()
{
	int noVertex,noEdge,temp,src,dest;
	printf("\nEnter the no. of vertices in the graph: ");
	scanf("%d",&noVertex);
	if(noVertex<1)//If Graph with zero vertex
	{
		printf("\nGraph is empty");
		return -1;
	}
	int maxnoEdge = noVertex*(noVertex-1)/2; // Maximum number of Edge in a simple graph without self-loops
	printf("\nEnter the no. of edges in the graph: ");
	scanf("%d",&noEdge);
	if(noEdge>maxnoEdge)//If not simple graph
	{
		printf("\nPlease Insert simple graph");
		return -1;
	}
	graph* ngraph = CreateGraph(noVertex); // Create graph with n vertices
	for(temp = 0;temp<noEdge;temp++)
	{
		printf("\nEnter src and destination vertex : ");
		scanf("%d %d",&src ,&dest);
		AddEdge(ngraph,src,dest);
	}
	printf("\nAdjacency list of the given graph is :\n");
	printGraph(ngraph);
	sort(ngraph);
	printf("\nSorted adjacency list of the given graph is :\n");
	printGraph(ngraph);
	return 0;
}
graph* CreateGraph(int nVertex)
{
    graph* newGraph = (graph*)malloc(sizeof(graph));
    newGraph->nVertex = nVertex;
    newGraph->adjacentVertices = (vertex**)malloc(nVertex * sizeof(vertex*));
    int i;
    for (i = 0; i < nVertex; i++)
    {
        newGraph->adjacentVertices[i] = NULL;
    }
    return newGraph;
}
void AddEdge(graph* newGraph, int source, int destination) {
    // Create a new vertex for the destination
    vertex* newVertex = (vertex*)malloc(sizeof(vertex));
    newVertex->value = destination;
    newVertex->next = NULL;

    // Add the new vertex to the adjacency list of the source vertex
    if (newGraph->adjacentVertices[source] == NULL) {
        newGraph->adjacentVertices[source] = newVertex;
    } else {
        vertex* currentVertex = newGraph->adjacentVertices[source];
        while (currentVertex->next != NULL) {
            currentVertex = currentVertex->next;
        }
        currentVertex->next = newVertex;
    }
	
	vertex* newDestVertex = (vertex*)malloc(sizeof(vertex));
    newDestVertex->value = source;
    newDestVertex->next = NULL;
    vertex* currentVertexdest = newGraph->adjacentVertices[destination];
    if(currentVertexdest == NULL)
    {
    	newGraph->adjacentVertices[destination] = newDestVertex;
	}
	else{
        while (currentVertexdest->next != NULL) {
            currentVertexdest = currentVertexdest->next;
        }
        currentVertexdest->next = newDestVertex;
	}
}

vertex* FindMid(vertex* head)
{
    vertex* slow = head;
    vertex* fast = head->next;
    while (fast != NULL) {
        fast = fast->next;
        if (fast != NULL) {
            slow = slow->next;
            fast = fast->next;
        }
    }
    return slow;
}
vertex* merge(vertex* left, vertex* right)
{
    if (left == NULL)
    {
        return right;
    }
    if (right == NULL)
    {
        return left;
    }
    vertex* dummy = (vertex*)malloc(sizeof(vertex));
    dummy->value = -1;
    dummy->next = NULL;
    vertex* temp = dummy;

    while (left != NULL && right != NULL)
    {
        if (left->value < right->value)
        {
            temp->next = left;
            left = left->next;
        }
        else
        {
            temp->next = right;
            right = right->next;
        }
        temp = temp->next;
    }
    temp->next = (left != NULL) ? left : right;

    return dummy->next;
}
vertex* MergeSort(vertex *head)
{
    if (head == NULL || head->next == NULL)
    {
        return head;
    }
    vertex* mid = FindMid(head);
    vertex* left = head;
    vertex* right = mid->next;

    mid->next = NULL;

    left = MergeSort(left);
    right = MergeSort(right);

    vertex* result = merge(left, right);

    return result;
}
void sort(graph* newGraph)
{
    int i;
    for (i = 0; i < newGraph->nVertex; i++)
    {
        vertex* head = newGraph->adjacentVertices[i];
        vertex* result = MergeSort(head);
        newGraph->adjacentVertices[i] = result;
    }

}
void printGraph(graph* newGraph)
{
    int i;
    for (i = 0; i < newGraph->nVertex; i++)
    {
        printf("%d:", i);
        vertex* current = newGraph->adjacentVertices[i];
        while (current)
        {
            printf("%d -> ", current->value);
            current = current->next;
        }
        printf("NULL\n");
    }
}
