#include<stdio.h>
#include<stdlib.h>
typedef struct Node{
	int value;
	struct Node* next;
	int weight;
}Node;

typedef struct Graph{
	int V;
	Node** adjacencyList;
}Graph;

Graph* createGraph(int);
Graph* addEdge(Graph*,int,int);
Graph* addEdgeWeighted(Graph* ,int ,int ,int );
void printGraph(Graph*);
Graph* sort(Graph*);
Node* findMid(Node*);
Node* MergeSort(Node* );
Node* Merge(Node*, Node*);
int main(){
	int noOfVertex,noOfEdge;
	int direction=0,weightage=0;
	Graph* graph;
	printf("\nEnter the number of vertices in the graph: ");
	scanf("%d",&noOfVertex);
	printf("\nEnter the number of edges in the graph: ");
	scanf("%d",&noOfEdge);
	if(noOfVertex <1 || noOfEdge>(noOfVertex*(noOfVertex-1)/2)){
		printf("\nEnter a simple graph.");
		return -1;
	}
	graph = createGraph(noOfVertex);
	printf("\nEnter the graph is Directed or Undirected (0 for Undirected): ");
	scanf("%d",&direction);
	printf("\nEnter the graph is Weighted or Unweighted (0 for Unweighted): ");
	scanf("%d",&weightage);
	while(noOfEdge>0){
		int endPoint1,endPoint2,weight;
		printf("\nEnter the endpoints of a edge: ");
		scanf("%d%d",&endPoint1,&endPoint2);
		if(endPoint1 == endPoint2){
			printf("\nSelf loop is not allowed");
			continue;
		}
		if(direction == 0){
			if(weightage == 0){
				graph = addEdge(graph,endPoint1,endPoint2);
				graph = addEdge(graph,endPoint2,endPoint1);
			}
			else{
				printf("\nEnter the weight of the edge: ");
				scanf("%d",&weight);
				graph = addEdgeWeighted(graph,endPoint1,endPoint2,weight);
				graph = addEdgeWeighted(graph,endPoint2,endPoint1,weight);
			}
		}
		if(direction == 1){
			if(weightage == 0){
				graph = addEdge(graph,endPoint1,endPoint2);
			}
			else{
				printf("\nEnter the weight of the edge: ");
				scanf("%d",&weight);
				graph = addEdgeWeighted(graph,endPoint1,endPoint2,weight);
			}
		}
		noOfEdge--;
	}
	graph = sort(graph);
	printf("\nThe Adjacency list representation of the given graph is: \n");
	printGraph(graph);
	return 0;
}
Graph* createGraph(int vertex){
	int i;
	Graph* graph = (Graph*)malloc(sizeof(Graph));
	graph->V = vertex;
	graph->adjacencyList = (Node**)malloc(vertex*sizeof(Node*));
	for(i=0;i<vertex;i++){
		graph->adjacencyList[i] = (Node*)malloc(sizeof(Node));
		graph->adjacencyList[i]->value =0;
		graph->adjacencyList[i]->weight = 0;
		graph->adjacencyList[i]->next = NULL;
		
	}
	return graph;
}
Graph* addEdge(Graph* graph,int endPoint1,int endPoint2){
	Node* endPoint2Vertex = (Node*)malloc(sizeof(Node));
	endPoint2Vertex->value = endPoint2;
	endPoint2Vertex->next = NULL;
	endPoint2Vertex->weight = 0;
	if(graph->adjacencyList[endPoint1]->next == NULL){
		graph->adjacencyList[endPoint1]->next = endPoint2Vertex;	
	}
	else{
		Node* current = graph->adjacencyList[endPoint1];
		while(current->next != NULL){
			current = current->next;
		}
		current->next = endPoint2Vertex;
	}
	return graph;
}
Graph* addEdgeWeighted(Graph* graph,int endPoint1,int endPoint2,int weight){
	Node* endPoint2Vertex = (Node*)malloc(sizeof(Node));
	endPoint2Vertex->value = endPoint2;
	endPoint2Vertex->next = NULL;
	endPoint2Vertex->weight = weight;
	if(graph->adjacencyList[endPoint1]->next == NULL){
		graph->adjacencyList[endPoint1]->next = endPoint2Vertex;	
	}
	else{
		Node* current = graph->adjacencyList[endPoint1];
		while(current->next != NULL){
			current = current->next;
		}
		current->next = endPoint2Vertex;
	}
	return graph;
}
void printGraph(Graph* graph){
	int i;
	for(i=0;i<graph->V;i++){
		printf("\n%d : ",i);
		Node* current = graph->adjacencyList[i]->next;
			while(current != NULL){
				if(current->weight == 0){
					printf("%d-> ",current->value);
					current = current->next;
					}
				else{
					printf("%d(%d)-> ",current->value,current->weight);
					current = current->next;
				}
			}
		printf("NULL");
	}
}
Graph* sort(Graph* graph){
	int i;
	for(i=0;i<graph->V;i++){
		Node* head = graph->adjacencyList[i]->next;
		Node* result = MergeSort(head);
		graph->adjacencyList[i]->next = result;
	}
	return graph;
}
Node* MergeSort(Node* head){
	if(head == NULL || head->next == NULL){
		return head;
	}
	Node* mid = findMid(head);
	Node* first = head;
	Node* last = mid->next;
	mid->next = NULL;

	first = MergeSort(first);
	last = MergeSort(last);
	Node* result = Merge(first,last);
	return result;
}
Node* findMid(Node* head)
{
    Node* slow = head;
    Node* fast = head->next;
    while (fast != NULL) {
        fast = fast->next;
        if (fast != NULL) {
            slow = slow->next;
            fast = fast->next;
        }
    }
    return slow;
}
Node* Merge(Node* left, Node* right)
{
    Node* dummy = (Node*)malloc(sizeof(Node));
    Node* temp = dummy;

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

    // No need to check if left or right is not NULL, as the remaining list is already sorted
    temp->next = (left != NULL) ? left : right;

    return dummy->next;
}
