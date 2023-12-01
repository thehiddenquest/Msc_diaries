#include <stdio.h>
#include <stdlib.h>

struct Vertex {
    int data;
    struct Vertex* next;
};

struct Graph {
    int nvertices;
    struct Vertex** array;
    int* dfs_number;
    int* dfs_completion;
    int dfs_counter;
};

struct Edge {
    int src;
    int dest;
};

struct Vertex* createVertex(int data) {
    struct Vertex* newVertex = (struct Vertex*)malloc(sizeof(struct Vertex));
    newVertex->data = data;
    newVertex->next = NULL;
    return newVertex;
}

struct Graph* createGraph(int vertices) {
    struct Graph* graph = (struct Graph*)malloc(sizeof(struct Graph));
    graph->nvertices = vertices;
    graph->array = (struct Vertex**)malloc(vertices * sizeof(struct Vertex*));
    graph->dfs_number = (int*)malloc(vertices * sizeof(int));
    graph->dfs_completion = (int*)malloc(vertices * sizeof(int));
    graph->dfs_counter = 1;

    int index;
    for (index = 0; index < graph->nvertices; index++) {
        graph->array[index] = NULL;
        graph->dfs_number[index] = 0;
        graph->dfs_completion[index] = 0;
    }

    return graph;
}

void addEdge(struct Graph* graph, int src, int dest) {
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

void dfs(struct Graph* graph, int vertex, struct Edge dfs_tree[], int* edge_index) {
    if (graph->dfs_number[vertex] == 0) {
        graph->dfs_number[vertex] = graph->dfs_counter++;

        struct Vertex* current = graph->array[vertex];
        while (current != NULL) {
            int neighbor = current->data;

            if (graph->dfs_number[neighbor] == 0) {
                dfs_tree[*edge_index].src = vertex;
                dfs_tree[*edge_index].dest = neighbor;
                (*edge_index)++;
                dfs(graph, neighbor, dfs_tree, edge_index);
            }

            current = current->next;
        }

        graph->dfs_completion[vertex] = graph->dfs_counter++;
    }
}

void printDFSInfo(struct Graph* graph) {
    int i;
	printf("\n(DFS Numbers, DFS Complition number):\n");
    for (i = 0; i < graph->nvertices; i++) {
        printf("Vertex %d: (%d ,%d)\n", i, graph->dfs_number[i],graph->dfs_completion[i]);
    }

}

void printDFSTreeGraph(struct Graph* graph, struct Edge dfs_tree[], int edges) {
    printf("\nDFS Tree as a Graph:\n");
	int i;
    for ( i = 0; i < graph->nvertices; i++) {
        printf("%d (%d,%d) : ", i,graph->dfs_number[i],graph->dfs_completion[i]);
        int j;
        for ( j = 0; j < edges; j++) {
            if (dfs_tree[j].src == i) {
                printf("%d (%d, %d) -> ", dfs_tree[j].dest,graph->dfs_number[dfs_tree[j].dest],graph->dfs_completion[dfs_tree[j].dest]);
            }
        }
        printf("NULL\n");
    }
}

void printGraph(struct Graph* graph) {
    int i;
    for (i = 0; i < graph->nvertices; i++) {
        printf("%d : ", i);
        struct Vertex* current = graph->array[i];
        while (current != NULL) {
            printf("%d -> ", current->data);
            current = current->next;
        }
        printf("NULL\n");
    }
}

int main() {
    int nvertex, nedge, src, dest, temp;
    printf("Enter the no. of vertices in the Graph: ");
    scanf("%d", &nvertex);
    printf("\nEnter the no. of edges in the Graph: ");
    scanf("%d", &nedge);
    int maxedge = nvertex * (nvertex - 1) / 2;
    if (nedge > maxedge) {
        printf("\nNumber of edges exceeds the maximum possible combinations!!");
        return 0;
    }
    struct Graph* graph = createGraph(nvertex);

    for (temp = 0; temp < nedge; temp++) {
        printf("Enter the source vertex and destination vertex for edge %d : ", temp + 1);
        scanf("%d %d", &src, &dest);
        addEdge(graph, src, dest);
    }

    printf("\nAdjacency list representation of given graph: \n");
    printGraph(graph);

    printf("\nDepth-First Search:\n");
    struct Edge* dfs_tree = (struct Edge*)malloc((nvertex - 1) * sizeof(struct Edge));
    int edge_index = 0;
    int i;
    for ( i = 0; i < graph->nvertices; i++) {
        if (graph->dfs_number[i] == 0) {
            dfs(graph, i, dfs_tree, &edge_index);
        }
    }

    printDFSInfo(graph);
    printDFSTreeGraph(graph, dfs_tree, nvertex - 1);

    // Free allocated memory
    free(dfs_tree);

    for ( i = 0; i < nvertex; ++i) {
        struct Vertex* current = graph->array[i];
        while (current != NULL) {
            struct Vertex* temp = current;
            current = current->next;
            free(temp);
        }
    }
    free(graph->array);
    free(graph->dfs_number);
    free(graph->dfs_completion);
    free(graph);

    return 0;
}

