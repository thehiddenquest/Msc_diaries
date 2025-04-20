#include <stdio.h>
#include <stdlib.h>

typedef struct point
{
        int x;
        int y;
} Point;

typedef struct graph
{
        int npoint;
        Point **array;
} Graph;
void sort_x(Graph *graph)
{
        int i, j;
        Point *temp;
        int swapped;
        for (i = 0; i < graph->npoint; i++)
        {
                swapped = 0;
                for (j = 0; j < graph->npoint - i - 1; j++)
                {
                        if (graph->array[j]->x > graph->array[j + 1]->x)
                        {
                                temp = graph->array[j];
                                graph->array[j] = graph->array[j + 1];
                                graph->array[j + 1] = temp;
                                swapped = 1;
                        }
                        if (graph->array[j]->x == graph->array[j + 1]->x)
                        {
                                if (graph->array[j]->y > graph->array[j + 1]->y)
                                {
                                        temp = graph->array[j];
                                        graph->array[j] = graph->array[j + 1];
                                        graph->array[j + 1] = temp;
                                        swapped = 1;
                                }
                        }
                }
                if (swapped == 0)
                {
                        break;
                }
        }
}
int main()
{
        int npoint, i;
        printf("\nHow many points in the set P? ");
        scanf("%d", &npoint);

        Graph *graph = (Graph *)malloc(sizeof(Graph));
        graph->array = (Point **)malloc(npoint * sizeof(Point *));
        graph->npoint = npoint;
        for (i = 0; i < npoint; i++)
        {
                graph->array[i] = (Point *)malloc(sizeof(Point)); // Allocate memory for each Point
                printf("\nEnter point x y: ");
                scanf("%d %d", &graph->array[i]->x, &graph->array[i]->y);
        }

        printf("\nPoints in the graph:\n");
        for (i = 0; i < npoint; i++)
        {
                printf("(%d, %d)\n", graph->array[i]->x, graph->array[i]->y);
        }
        sort_x(graph);
        printf("After Sorting graph: \n");
        for (i = 0; i < npoint; i++)
        {
                printf("(%d, %d)\n", graph->array[i]->x, graph->array[i]->y);
        }

        // Free allocated memory
        for (i = 0; i < npoint; i++)
        {
                free(graph->array[i]);
        }
        free(graph->array);
        free(graph);

        return 0;
}