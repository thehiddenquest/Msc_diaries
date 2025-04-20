// Online C compiler to run C program online
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
typedef struct node
{
        int data;
        struct node *link;
} Node;

void insertion_sort(Node *list)
{
        if (list == NULL)
                return;

        Node *current;
        Node *advance;
        int temp;

        for (current = list; current != NULL; current = current->link)
        {
                for (advance = current->link; advance != NULL; advance = advance->link)
                {
                        if (current->data > advance->data)
                        {
                                // Swap data
                                temp = current->data;
                                current->data = advance->data;
                                advance->data = temp;
                        }
                }
        }
}
int main()
{
        int i, data, y;
        Node *list = NULL;
        while (true)
        {
                printf("\nDo you want to insert data(0 to stop): ");
                scanf("%d", &y);
                if (y == 0)
                {
                        break;
                }
                printf("\nEnter data: ");
                scanf("%d", &data);

                Node *temp = (Node *)malloc(sizeof(Node));
                temp->data = data;
                temp->link = NULL;
                if (list == NULL)
                {
                        list = temp;
                }
                else
                {
                        Node *current = list;
                        while (current->link != NULL)
                        {
                                current = current->link;
                        }

                        current->link = temp;
                }
        }
        printf("\nLinked list is: \n");
        Node *printlist = list;
        while (printlist != NULL)
        {
                printf("%d -> ", printlist->data);
                printlist = printlist->link;
        }
        insertion_sort(list);
        printf("\nLinked list is: \n");
        printlist = list;
        while (printlist != NULL)
        {
                printf("%d -> ", printlist->data);
                printlist = printlist->link;
        }
        return 0;
}