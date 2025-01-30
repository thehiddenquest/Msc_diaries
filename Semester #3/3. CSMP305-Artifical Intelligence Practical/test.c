#include<stdio.h>
#include<stdlib.h>

typedef struct node{
        int name;
        int data;
        int link;
}Node;

void addnode(Node* head, int data){
        Node* newnode = (Node*)malloc(sizeof(Node));
        newnode->data = data;
        newnode->link = NULL;
        if(*head == NULL){
                *head = newnode;
        }
        else
        {
                while(head->link == NULL){
                        head = head->link;
                }
                head->link = newnode;
        }
}
void print(Node *head)
{
    if (head == NULL)
    {
        printf("The list is empty");
    }
    else
    {
        head = head->links;
        while (head != NULL)
        {
            printf("%d\n", head->data);
            head = head->links;
        }
    }
}
int main(){
        Node* head = NULL;
        i=0;
        while(i<5):
        {
                data = 1;
                addnode(&head, data);
                i++;
        }
        print(head);
        return 0;
}