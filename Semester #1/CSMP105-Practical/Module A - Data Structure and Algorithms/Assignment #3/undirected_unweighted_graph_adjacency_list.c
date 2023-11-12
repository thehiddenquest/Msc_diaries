#include<stdio.h>
#include<stdlib.h>

struct Node {
    int data;
    struct Node* next; 
};

struct Graph {
    int vertices;
    struct Node** array; // array of pointers 
};

struct Node* createNode(int data) {
    struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));
    newNode->data = data;
    newNode->next = NULL;
    return newNode;
}

struct Graph* createGraph(int vertices) {
    int i;
    struct Graph* graph = (struct Graph*)malloc(sizeof(struct Graph));
    graph->vertices = vertices;
    graph->array = (struct Node**)malloc(vertices * sizeof(struct Node*));
    for(i = 0; i < vertices; i++) {
        graph->array[i] = NULL;
    }
    return graph;
}

void addEdge(struct Graph* graph, int src, int dest) {
    struct Node* newNode = createNode(dest);

    // Traverse to the end of the list and insert at the end
    struct Node* current = graph->array[src];
    if (current == NULL) {
        graph->array[src] = newNode;
    } else {
        while (current->next != NULL) {
            current = current->next;
        }
        current->next = newNode;
    }

    // Repeat the process for the destination vertex
    newNode = createNode(src);
    current = graph->array[dest];
    if (current == NULL) {
        graph->array[dest] = newNode;
    } else {
        while (current->next != NULL) {
            current = current->next;
        }
        current->next = newNode;
    }
}

void printGraph(struct Graph* graph) {
    int i;
    for(i = 0; i < graph->vertices; i++) {
        printf("%d: ", i);
        struct Node* current = graph->array[i];
        while(current) {
            printf("%d -> ", current->data);
            current = current->next;
        }
        printf("NULL\n");
    }
}

int main() {
    int vertices, edges, src, dest, i;
    printf("\nEnter the no. of vertices in the graph: ");
    scanf("%d", &vertices);
    struct Graph* graph = createGraph(vertices);
    
    printf("\nEnter the no. of edges in the graph: ");
    scanf("%d", &edges);
    for(i = 0; i < edges; i++) {
        printf("Enter source vertex, destination vertex for edge %d: ", i + 1);
        scanf("%d %d", &src, &dest);
        addEdge(graph, src, dest);
    }

    printf("\nAdjacency list representation of the given graph: \n\n");
    printGraph(graph);
    return 0;
}

