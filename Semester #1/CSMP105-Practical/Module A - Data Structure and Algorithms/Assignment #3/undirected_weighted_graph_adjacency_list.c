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
struct Node* createNode(int data, int weight)
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
	struct Node* current = graph->array[src];
	if(current == NULL)
	{
		graph->array[src] = newNode;
	}
	else
	{
		while(current->next != NULL)
		{
			current = current->next;
		}
		current->next = newNode;
	}
	
	//For Destination vertex
	newNode = createNode(src,weight);
	current = graph->array[dest];
	if(current == NULL)
	{
		graph->array[dest] = newNode;
	}
	else
	{
		while(current->next != NULL)
		{
			current = current->next;
		}
		current->next = newNode;
	}
	/*newNode->next = graph->array[src];
	graph->array[src] = newNode;
	
	newNode = createNode(src, weight);
	newNode->next = graph->array[dest];
	graph->array[dest] = newNode; */
}
void printGraph(struct Graph* graph)
{
	int i;
	for(i = 0 ; i< graph->vertices ;i++)
	{
		printf("%d: ",i);
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
	int vertices ,edges , src , dest, weight, i;
	printf("\nEnter the no. of vertices in the graph: ");
	scanf("%d",&vertices);
	struct Graph* graph = createGraph(vertices);
	
	printf("\nEnter the no. of edges in the graph: ");
	scanf("%d",&edges);
	int maxedge = vertices * (vertices - 1);
	if(edges > maxedge)
	{
		printf("\nNumber of edge exceeds the maximum possible combinations!!");
		return 0;
	}
	for(i = 0; i < edges; i++)
	{
		printf("Enter source vertex, destination vertex and weight for edge %d: ",i+1);
		scanf("%d %d %d",&src, &dest, &weight);
		addEdge(graph, src, dest, weight);
	}
/*	addEdge(graph, 0, 1, 6);
	addEdge(graph, 0, 2, 10);
	addEdge(graph, 1, 2, 1);
	addEdge(graph, 1, 3, 5);
	*/
	printf("\nAdjacency list representation of given graph: \n\n");
	printGraph(graph); 
	return 0;
}

