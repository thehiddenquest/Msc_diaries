#include<stdio.h>
#include<stdlib.h>

/* Define a Vertex Structure */
struct Vertex
{
	int data;
	struct Vertex* next;
};
/* Define The Graph Structure */
struct Graph
{
	int nvertices;
	struct Vertex** array;
	int* dfs_number;
	int* dfs_complition_number;
	int dfs_counter;
};
/* Create Function to Create a Vertex */
struct Vertex* createVertex(int );
/* Create Function to Create the Graph */
struct Graph* createGraph(int );
/* Create Function to add edges */
void addEdge(struct Graph*,int ,int );
/* Create Function to Display the Adjacency List */
void printGraph(struct Graph* );
/* Main Function */
int main()
{
	int nvertex, nedge, src, dest, temp;
	printf("Enter the no. of vertices in the Graph: ");
	scanf("%d",&nvertex);
	printf("\nEnter the no. of edges in the Graph: ");
	scanf("%d",&nedge);
	int maxedge = nvertex * (nvertex - 1);
	if(nedge > maxedge)
	{
		printf("\nNumber of edge exceeds the maximum possible combinations!!");
		return 0;
	}
	struct Graph* graph = createGraph(nvertex);
	for(temp = 0; temp < nedge; temp++)
	{
		printf("Enter the source vertex and destination vertex for the edge %d : ",temp+1);
		scanf("%d %d", &src, &dest);
		addEdge(graph, src, dest);
	}
/*	struct Graph* graph1 = createGraph(nvertex);  For trying singleton logic*/ 
	printf("\nAdjacency list representation of given graph: \n\n");
	printGraph(graph);
	return 0;
}
struct Vertex* createVertex(int data)
{
	struct Vertex* newVertex = (struct Vertex*)malloc(sizeof(struct Vertex));
	newVertex->data = data;
	newVertex->next = NULL;
	return newVertex;
}
struct Graph* createGraph(int vertices)
{
	static int beenCreated = 0;
	if(beenCreated)
	{
		/* Implementing Singleton logic */
		printf("\nGraph already been created.Returing existing graph.\n");
		return NULL;
	}
	struct Graph* graph = (struct Graph*)malloc(sizeof(struct Graph));
	graph->nvertices = vertices;
	graph->array = (struct Vertex**)malloc(vertices * sizeof(struct Vertex*));
	int index;
	for(index = 0; index<graph->nvertices; index++)
	{
		graph->array[index]=NULL;
	}
	beenCreated = 1;
	
	return graph;
}
void addEdge(struct Graph* graph, int src, int dest)
{
    struct Vertex* newVertex = createVertex(dest);

    // Add edge from source to destination
    if (graph->array[src] == NULL) {
        graph->array[src] = newVertex;
    } else {
        struct Vertex* current = graph->array[src];
        while (current->next != NULL) {
            current = current->next;
        }
        current->next = newVertex;
    }

    // Add edge from destination to source (for undirected graph)
    newVertex = createVertex(src);
    if (graph->array[dest] == NULL) {
        graph->array[dest] = newVertex;
    } else {
        struct Vertex* current = graph->array[dest];
        while (current->next != NULL) {
            current = current->next;
        }
        current->next = newVertex;
    }
}
void printGraph(struct Graph* graph)
{
	int i;
	for(i= 0; i<graph->nvertices; i++)
	{
		printf("%d : ",i);
		struct Vertex* current = graph->array[i];
		while(current != NULL)
		{
			printf("%d -> ",current->data);
			current = current->next;
		}
		printf("NULL\n");
	}
}
