/*
Algorithm SlowConvexHull
Input: A set P of points in the plane.
Output: An list L containing the vertices of CH(P) in clockwise order.

Step 1. E <- Null
Step 2. for all order pairs (p,q) includes in P*P with p not equal to q
Step 3.         do valid <- true
Step 4.         for all point r includes in P not equal to p or q
Step 5.                 do if r lies to the left of the directed line from p to q
Step 6.                         then valid <- false
Step 7.         if valid then Add the directed edge pq to E
Step 8. From the set E of edges construct a list L of vertices of CH(P), sorted in clock wise order.
*/
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

typedef struct point
{
        int x, y;
} Point;

typedef struct plane
{
        int no_point;
        Point *points; // Changed from Point ** to Point * for simplicity
} Plane;

// Function to check if a point is left of a directed line pq
bool isLeft(Point *p, Point *q, Point *r)
{
        return ((q->x - p->x) * (r->y - p->y) - (q->y - p->y) * (r->x - p->x)) > 0;
}

// Function to create a plane and allocate memory
Plane *create_plane(int no)
{
        Plane *space = (Plane *)malloc(sizeof(Plane));
        space->no_point = no;
        space->points = (Point *)malloc(no * sizeof(Point)); // Allocating array of Points
        return space;
}

// Function to print the points in the plane
void print_plane(Plane *space)
{
        printf("\nThe points in the plane are:\n");
        for (int i = 0; i < space->no_point; i++)
        {
                printf("Point %d: (%d, %d)\n", i + 1, space->points[i].x, space->points[i].y);
        }
}
void slow_convex_hull(Plane *space)
{
        int i, j, k;
        int edgeCount = 0;
        Point **E = (Point **)malloc(space->no_point * space->no_point * sizeof(Point *)); // Array of edges

        // Step 2: Check all ordered pairs (p, q)
        for (i = 0; i < space->no_point; i++)
        {
                for (j = 0; j < space->no_point; j++)
                {
                        if (i == j)
                                continue; // p ≠ q

                        bool valid = true;

                        // Step 4: Check all points r ≠ p, q
                        for (k = 0; k < space->no_point; k++)
                        {
                                if (k == i || k == j)
                                        continue;

                                if (isLeft(&space->points[i], &space->points[j], &space->points[k]))
                                {
                                        valid = false;
                                        break;
                                }
                        }

                        // Step 7: If valid, add (p, q) to edge list E
                        if (valid)
                        {
                                E[edgeCount] = (Point *)malloc(2 * sizeof(Point)); // Store edge as two points
                                E[edgeCount][0] = space->points[i];
                                E[edgeCount][1] = space->points[j];
                                edgeCount++;
                        }
                }
        }

        // Step 8: Print convex hull edges
        printf("\nConvex Hull Edges:\n");
        for (i = 0; i < edgeCount; i++)
        {
                printf("Edge: (%d, %d) -> (%d, %d)\n",
                       E[i][0].x, E[i][0].y, E[i][1].x, E[i][1].y);
                free(E[i]); // Free each edge
        }

        free(E); // Free edge array
}
int main()
{
        int no, i;
        printf("\nEnter the number of points: ");
        scanf("%d", &no);

        // Create plane
        Plane *space = create_plane(no);

        // Read points
        for (i = 0; i < no; i++)
        {
                printf("\nEnter point %d (x y): ", i + 1);
                scanf("%d %d", &space->points[i].x, &space->points[i].y);
        }

        // Print the plane points
        print_plane(space);
        slow_convex_hull(space);
        // Free allocated memory
        free(space->points);
        free(space);

        return 0;
}