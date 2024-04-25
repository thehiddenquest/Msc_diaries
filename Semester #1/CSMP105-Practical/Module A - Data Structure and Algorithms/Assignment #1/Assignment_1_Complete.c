#include<stdio.h>
#include<stdlib.h>

typedef struct Graph{
	int noOfVertex;
	int** adjacencyMatrix;
}Graph;

Graph* createGraph(int);
Graph* addEdgeUndirectedUnweighted(Graph*,int,int);
Graph* addEdgeUndirectedWeighted(Graph*,int,int,int);
Graph* addEdgeDirectedUnweighted(Graph*,int,int);
Graph* addEdgeDirectedWeighted(Graph*,int,int,int);
void printGraph(Graph*);

int main(){
	int noOfVertex,noOfEdge;
	int direction=0,weightage=0;
	Graph* graph;
	printf("\nEnter the number of vertices in the graph: ");
	scanf("%d",&noOfVertex);
	printf("\nEnter the number of edges in the graph: ");
	scanf("%d",&noOfEdge);
	if(noOfVertex <0 || (noOfEdge > (noOfVertex*(noOfVertex-1)/2))){
		printf("\nPlease enter a simple graph.");
		return -1;
	}
	graph = createGraph(noOfVertex);
	printf("\nEnter the graph is Directed or Undirected (0 for Undirected): ");
	scanf("%d",&direction);
	printf("\nEnter the graph is Weighted or Unweighted (0 for Unweighted): ");
	scanf("%d",&weightage);
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
		if(direction == 0){
			if(weightage == 0){
				graph = addEdgeUndirectedUnweighted(graph,endPoint1,endPoint2);
			}
			else{
				printf("\nEnter the weight of the edge: ");
				scanf("%d",&weight);
				graph = addEdgeUndirectedWeighted(graph,endPoint1,endPoint2,weight);
			}
		}
		if(direction == 1){
			if(weightage == 0){
				graph = addEdgeDirectedUnweighted(graph,endPoint1,endPoint2);
			}
			else{
				printf("\nEnter the weight of the edge: ");
				scanf("%d",&weight);
				graph = addEdgeDirectedWeighted(graph,endPoint1,endPoint2,weight);
			}
		}
		i++;
	}
	printf("\nAdjacency matrix representation of given graph is : \n");
	printGraph(graph);
	return 0;
}
Graph* createGraph(int noOfvertex){
	Graph* graph =(Graph*)malloc(sizeof(Graph));
	graph->noOfVertex = noOfvertex;
	graph->adjacencyMatrix=(int**)malloc(noOfvertex*sizeof(int*));
	int i,j;
	for(i=0;i<noOfvertex;i++){
		graph->adjacencyMatrix[i] = (int*)malloc(noOfvertex*sizeof(int));
		for(j=0;j<noOfvertex;j++)
			graph->adjacencyMatrix[i][j]=0;
	}
	return graph;
}
Graph* addEdgeUndirectedUnweighted(Graph* graph, int Apoint,int Bpoint){
	graph->adjacencyMatrix[Apoint][Bpoint] = 1;
	graph->adjacencyMatrix[Bpoint][Apoint] = 1;
	return graph;
}
Graph* addEdgeUndirectedWeighted(Graph* graph, int Apoint,int Bpoint,int weight){
	graph->adjacencyMatrix[Apoint][Bpoint] = weight;
	graph->adjacencyMatrix[Bpoint][Apoint] = weight;
	return graph;
}
Graph* addEdgeDirectedUnweighted(Graph* graph, int Apoint,int Bpoint){
	graph->adjacencyMatrix[Apoint][Bpoint] = 1;
	return graph;
}
Graph* addEdgeDirectedWeighted(Graph* graph, int Apoint,int Bpoint,int weight){
	graph->adjacencyMatrix[Apoint][Bpoint] = weight;
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
