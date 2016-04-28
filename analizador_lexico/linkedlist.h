#ifndef LINKED_LIST_H
#define LINKED_LIST_H

typedef struct Data Data;
struct Data {
    char* val;
    int klass;
    int lineNumber;
    int charNumber;
    int isFloat;
};

typedef struct Node Node;
struct Node {
    Data data;
    Node* next;
};

typedef struct LinkedList List;
struct LinkedList {
    Node* root;
    int num;
};


#endif

/**
 *@return List*
 */
//Función para inicializar la lista en NULL;
List* init_list() {
    List* temp;
    temp = (List*)malloc(sizeof(List));
    temp->root = NULL;
    temp->num = 0;
    return temp;
}

/**
 * @param char*
 * @param int
 * @return Node*
 */
//Función para crear un nodo
Node* create_node(char* val, int lineNumber, int charNumber, int isFloat, int klass) {
    Node* newnode = (Node *)malloc(sizeof(Node));
    int len = strlen(val);
    newnode->data.val = malloc(len* sizeof(char));
    strcpy(newnode->data.val, val);
    newnode->data.klass = klass;
    newnode->data.lineNumber = lineNumber;
    newnode->data.charNumber = charNumber;
    newnode->data.isFloat = isFloat;
    newnode->next = NULL;
    return newnode;
}

/**
 * @param char*
 * @param List*
 * @return int
 */
//Función de busqueda en la lista ligada
int search(char* str, List* list) {
    Node* tmp = list->root;
    int pos = 0;

    while (tmp) {
        //sí el valor es igual a la cadena retorna la posición
        if (strcmp(tmp->data.val, str) == 0) {
            return pos;
        }
        tmp = tmp->next;
        pos++;
    }
    return -1;
}

/**
 * @param char*
 * @param int
 * @param List*
 * @return int
 */
int insert_end(char* val, int lineNumber, int charNumber, int isFloat, int klass, List* list) {

    Node* newtmp = create_node(val, lineNumber, charNumber, isFloat, klass); //crea el nuevo nodo
    Node* tmp = list->root;         //crea el nodo tmp y guarda el primer
                        //nodo de la lista

    if (! list->root) {             //si el primer nodo es NULL
        list->root = newtmp;            //asigna el nuevo nodo como primer nodo
    } else {
        while (tmp->next != NULL) {     //mientas que exista el siguiente nodo
            tmp = tmp->next;            //guarda el nodo siguiente en tmp
        }
        tmp->next = newtmp;         //llega al final e inserta newtmp
    }
    list->num++;                //aumenta el contador de nodos en 1
    return list->num - 1;           //devuelve el valor de la cantidad de nodos
}

/**
 * @param char*
 * @param int
 * @param List*
 * @return int
 */
//Función para insertar al inicio
int insert_begin(char* val, int lineNumber, int charNumber, int isFloat, int klass, List* list) {
    int pos = search(val, list);
    if (pos != -1) {
        return pos;
    }

    Node* newtmp = create_node(val,lineNumber, charNumber, isFloat, klass);
    Node* tmp = list->root;

    if (! list->root) {
        list->root = newtmp;            //si la lista esta vacia inserta el nodo al inicio
    } else {
    newtmp->next = tmp;         //asigna como nodo next al elemento inicial de la lista
    list->root = newtmp;            //inserta el nuevo nodo al inicio
    list->num++;
    return list->num - 1;}
}

/**
 * @param Node*
 */
//Función para eliminar un nodo de la lista ligada
void delete_node(Node* next) {
    if (next->next != NULL) {
        delete_node(next->next);
    }
    free(next);
}

/**
 * @param List*
 */
//Función para vaciar la lista ligada
void empty_list(List* list) {
    delete_node(list->root);
    list->num = 0;
}

