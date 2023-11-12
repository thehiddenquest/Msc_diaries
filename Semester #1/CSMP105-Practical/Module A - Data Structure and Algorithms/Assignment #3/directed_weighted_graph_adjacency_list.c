#include<stdio.h>
#include<stdlib.h>
struct Node
{
	int data;
	int weight;
	struct Node* next; 
};
struct Graph
{
	int vertices;
	struct Node** array; // array of pointers 
};
struct Node* createNode(int data,int weight)
{
	struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));
	newNode->data = data;
	newNode->weight = weight;
	newNode->next = NULL;
	return newNode;
};
struct Graph* createGraph(int vertices)
{
	int i;
	struct Graph* graph = (struct Graph*)malloc(sizeof(struct Graph));
	graph->vertices = vertices;
	graph->array = (struct Node**)malloc(vertices * sizeof(struct Node**));
	for(i = 0;i< vertices; i++)
	{
		graph->array[i] = NULL;
	}
	return graph;
};
void addEdge(struct Graph* graph,int src,int dest,int weight)
{
	struct Node* newNode = createNode(dest,weight);
	newNode->next = graph->array[src];
	graph->array[src] = newNode;

}
void printGraph(struct Graph* graph)
{
	int i;
	for(i = 0 ; i< graph->vertices ;i++)
	{
		printf("Adjacency list of vertex %d:\n",i);
		struct Node* current = graph->array[i];
		while(current)
		{
			printf("%d(%d) -> ", current->data, current->weight);
			current = current->next;
		}
		printf("NULL\n");
	}
}
int main()
{
	int n;
	printf("\nEnter the no. of vertices in the graph: ");
	scanf("%d",&n);
	struct Graph* graph = createGraph(n);
	addEdge(graph, 0, 1, 6);
	addEdge(graph, 2, 0, 10);
	addEdge(graph, 1, 2, 1);
	addEdge(graph, 1, 3, 5);
	printGraph(graph); 
	return 0;
}

