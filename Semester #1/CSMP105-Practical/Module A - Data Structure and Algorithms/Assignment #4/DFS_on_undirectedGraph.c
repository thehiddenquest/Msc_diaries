#include<stdio.h>
#include<stdlib.h>
#define MAX 100

int component = 0;
int no_vertex,i,j,visited[MAX];
int treeEdge[MAX][MAX];


//GRAPH//

struct graph
{
    int vertices[MAX];
    int edges[MAX][MAX];
}G;

void set_vertices_graph(){
    printf("Enter no. of vertices: ");
    scanf("%d",&no_vertex);
    printf("Enter vertices name: ");
    for(i = 1; i <= no_vertex; ++i)
    {
        scanf("%d",&G.vertices[i]);
    }
}

void addEdge(){
    int start,end;
    printf("Enter start vertex : ");
    scanf("%d",&start);
    printf("Enter end vertex : ");
    scanf("%d",&end);
    G.edges[start][end] = 1;
    G.edges[end][start] = 1;
}

//GRAPH//

//DFS//

void DFS(int i){
    printf("%d ", i);
    visited[i] = 1;
    for (int j = 1; j <= no_vertex; j++)
    {
        if(G.edges[i][j]==1 && !visited[j]){
            treeEdge[i][j] = 1;
            DFS(j);
        }
    }
}

void DFSTravarsal()
{
    for(i = 1; i <= no_vertex; i++)
    {
        visited[i] = 0;
    }
    for(i = 1; i <= no_vertex; i++)
    {
        if(visited[i] == 0)
        {
            component ++;
            printf("\nComponent %d : \n",component);
            DFS(i);
        }                                 
    }
}

void countTreeEdge()
{
    printf("\nTREE EDGES : ");
    for(i = 1; i <= no_vertex; ++i)
    {
        for(j = 1; j <= no_vertex; ++j)
        {
            if(treeEdge[i][j] == 1)
                printf("{ %d, %d } ",i,j);
        }
    }
}

void countForwardEdge()
{
    printf("\nFORWARD EDGE : ");
    for(i = 1; i <= no_vertex; ++i)
    {
        for(j = i; j <= no_vertex; ++j)
        {
            if(G.edges[i][j] == 1)
            {
                if(treeEdge[i][j] != 1 && treeEdge[j][i] != 1)
                printf("{ %d, %d } ",i,j);
            }
        }
    }
}

void countBackEdge()
{
    printf("\nBACK EDGE : ");
    for(i = 1; i <= no_vertex; ++i)
    {
        for(j = 1; j <= no_vertex; ++j)
        {
            if(treeEdge[i][j] == 1)
                printf("{ %d, %d } ",j,i);
        }
    }
    for(i = 1; i <= no_vertex; ++i)
    {
        for(j = i; j <= no_vertex; ++j)
        {
            if(G.edges[i][j] == 1)
            {
                if(treeEdge[i][j] != 1 && treeEdge[j][i] != 1)
                printf("{ %d, %d } ",j,i);
            }
        }
    }
}
//DFS//

int main(int argc, char const *argv[])
{
    int choice;
    printf("\nDFS ON UNDIRECTED GRAPH\n");
    printf(".......................\n");
    set_vertices_graph();
    do{
        printf("\n1. ADD EDGE\n");
        printf("2. PROCED\n");
        scanf("%d",&choice);
        switch (choice)
        {
        case 1 : addEdge();
                break;
        
        case 2 : break;

        default:
            printf("\n*Enter a valid input*\n");
            break;
        }

    }while(choice != 2);
    DFSTravarsal();
    countTreeEdge();
    countForwardEdge();
    countBackEdge();
    printf("\n........................\n");
    return 0;
}
