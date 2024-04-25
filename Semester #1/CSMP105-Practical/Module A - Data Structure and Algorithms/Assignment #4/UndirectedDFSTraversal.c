/* Assignment 4 
	i. Consider an undirected graph, do DFS on it and compute the DFS tree.
	ii.Consider an undirected graph, do DFS on it and differentiate its edges based on your sequence of
		visiting the vertices.
*/
#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>

int DFSCounter = 1;
int DFSComplitionCounter = 1;
struct node{
	int dfsNumber;
	int dfsComplitionNumber;
	int value;
	struct node* nextNode;
	bool visited;
};
struct Graph{
	int numberOfVertices;
	struct node** adjacentNodes;
};
void addEdge(struct Graph*,int, int);
void printGraph(struct Graph*);
void DFS(struct Graph*);
int main()
{
	int noV,noE;
	return 0;
}

 

