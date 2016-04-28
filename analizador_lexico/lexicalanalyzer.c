#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

#include "symdef.h"
#include "linkedlist.h"


/*********************
 *   DECLARACIONES   *
**********************/

void print(List* list);
void writeSymTable(List* list, FILE *output);

int tokenize(FILE *filePtr);

int esSignoP(char);
int esOperadorArit(char);
char* esFlotante(int);
int iniciaOperadorRelacional(char);

void divPalabras();
void printAnalisis(List* list);
void printSymTable(List* list);

char* SearchType(int type);

List* symTab;
List *symbols;


/*************
 *   MAIN    *
*************/

int main( int argc, char** argv ) {
    FILE *f;
    FILE *output;
    output = fopen("SymTable.txt", "w");

    symTab = init_list(); //Se inicializa la lista ligada
    symbols = init_list(); // se inicializa la lista la ligada para almacenar simbolos

    //switch para abrir el archivo
    switch(argc){
    case 1:
        f = stdin;
        break;
     case 2:
         if((f = fopen(argv[1],"r")) == NULL){
         printf("No se puede abrir el archivo: %s", argv[1]);
         exit(1);
         }
         break;
     default:
         printf("Nada que abrir");
         exit(0);
      }

    fseek(f, 0, SEEK_END);
    if (ftell(f) == 0){
        printf("Archivo vacio!\n");
        exit(1);
    }
    else{
        rewind(f);
    }

    rewind(f);

    // Empieza análisis de los tokens
    tokenize(f);

    printAnalisis(symbols); //impresión del análisis léxico
    printSymTable(symTab);
    writeSymTable(symTab, output);
    empty_list(symTab);

    fclose(f);
    return 0;

} /* End of main */


/*************
* Functions  *
**************/

/**
 * @param File*
 * @return TokenType
 */
 //Función que se encarga del análisis del archivo por caracteres
int tokenize(FILE *filePtr) {
    int lineNum = 1; //Contador de Lineas
    int charNum = 1; //Contador de Caracteres
    char ch; //Char para caracter leido

    while ((ch = fgetc(filePtr)) != EOF) {
        if (ch == '\n') {
            lineNum++;
            charNum=0;
        }

        //signo puntuación
        if (ispunct(ch)) {
            if (esSignoP(ch)) {
                signs[0] = ch;
                signs[1] = '\0';
                insert_end(signs, lineNum, charNum, 0, SP, symbols);
            }
            //operador aritmetico
            else if (esOperadorArit(ch)) {
                otherOps[0] = ch;
                otherOps[1] = '\0';
                insert_end(otherOps, lineNum, charNum, 0, AO, symbols);
            }
            //operador relacional
            else if (iniciaOperadorRelacional(ch)) {
                if (ch == '<' || ch == '>') {
                    relOps[0][relOpj++] = ch;
                    if ((ch = fgetc(filePtr)) == '=') {
                        relOps[0][relOpj++] = ch;
                    } else {
                        fseek(filePtr, -1, SEEK_CUR);
                    }
                    relOps[0][relOpj] = '\0';
                    insert_end(relOps[0], lineNum, charNum, 0, RO, symbols);
                    charNum += relOpj-1;
                    relOpj = 0;
                }
                else if (ch == '=') {
                    if ((ch = fgetc(filePtr)) == '=') {
                        relOps[0][relOpj++] = '=';
                        relOps[0][relOpj++] =   ch;
                        relOps[0][relOpj] = '\0';
                        insert_end(relOps[0], lineNum, charNum, 0, RO, symbols);
                        charNum += relOpj-1;
                        relOpj = 0;
                    } else {
                        relOps[0][relOpj++] = '=';
                        relOps[0][relOpj] = '\0';
                        insert_end(relOps[0], lineNum, charNum, 0, RO, symbols);
                        charNum += relOpj;
                        relOpj = 0;
                    }
                }   else if (ch == '!') {
                        if ((ch = fgetc(filePtr)) == '=') {
                            relOps[0][relOpj++] = '!';
                            relOps[0][relOpj++] = ch;
                            relOps[0][relOpj] = '\0';
                            insert_end(relOps[0], lineNum, charNum, 0, RO, symbols);
                            charNum += relOpj-1;
                            relOpj = 0;
                        } else {
                            logics[0][logij++] = '!';
                            logics[0][logij] = '\0';
                            insert_end(logics[0], lineNum, charNum, 0, LO, symbols);
                            charNum += logij-1;
                            logij = 0;
                            fseek(filePtr, -1, SEEK_CUR);
                        }
                } else {
                    fseek(filePtr, -1, SEEK_CUR);
                }
            }
        }

         // Ignora los comentarios que comienzan con --
        if (ch == '-') {
            if (fgetc(filePtr) == '-') {
                while ((ch = fgetc(filePtr)) != '\n') {}
                lineNum++;
                charNum = 0;
                printf("lineNum %d\n",lineNum);
            } else
                fseek(filePtr, -1, SEEK_CUR);
        }

        // Identifica indentificadores
        if (ch == '@') {
            words[wordi][wordj++] = ch;
            if (isalpha(ch = fgetc(filePtr))) {
                words[wordi][wordj++] = ch;
                while (isalpha(ch = fgetc(filePtr)) || isdigit(ch)) {
                    words[wordi][wordj++] = ch;
                }
                words[wordi][wordj] = '\0';
                wordLineNums[wordi] = lineNum;
                wordCharNums[wordi] = charNum;
                /* Validación si el identifica ya existe en la tabla de símbolos */
                if (search(words[wordi], symTab) == -1) {
                  insert_begin(words[wordi], lineNum, charNum, 0, ID, symTab);
                  insert_end(words[wordi], lineNum, charNum, 0, ID, symbols);
                }
                charNum += wordj-1;
                wordi++; wordj = 0;
                fseek(filePtr, -1, SEEK_CUR);
            } else {
                fseek(filePtr, -1, SEEK_CUR);
            }
        }
        //Identifica &&
        if ( ch == '&') {
            if ((ch = fgetc(filePtr)) == '&' ) {
                logics[0][logij++] = '&';
                logics[0][logij++] = ch;
                logics[0][logij] = '\0';
                insert_end(logics[0], lineNum, charNum, 0, LO, symbols);
                charNum += logij-1;
                logij = 0;
            } else {
                printf("Error en linea: %d char: %d Simbolo no reconocido\n", lineNum, charNum);
                fseek(filePtr, -1, SEEK_CUR);
            }
        }
        //identifica ||
        if ( ch == '|') {
            if ((ch = fgetc(filePtr)) == '|' ) {
                logics[0][logij++] = '|';
                logics[0][logij++] = ch;
                logics[0][logij] = '\0';
                insert_end(logics[0], lineNum, charNum, 0, LO, symbols);
                charNum += logij-1;
                logij = 0;
            } else {
                fseek(filePtr, -1, SEEK_CUR);
            }
        }

        //almacena alfanumericos
        if (isalpha(ch)) {
            words[wordi][wordj++] = ch;
            while (isalpha(ch = fgetc(filePtr)) || isdigit(ch)) {
                words[wordi][wordj++] = ch;
            }
            words[wordi][wordj] = '\0';
            wordLineNums[wordi] = lineNum;
            wordCharNums[wordi] = charNum;
            charNum +=wordj-1;
            wordi++; wordj = 0;
            fseek(filePtr, -1, SEEK_CUR);
        }
        // numeros
        if (isdigit(ch)) {
            int i = 0, isfloat = 0, punto = 0;
            nums[0][numj++] = ch;
            while (isdigit(ch = fgetc(filePtr))) {
                nums[0][numj++] = ch;
            }
            if ( ch == '.') {
                punto = 1;
                nums[0][numj++] = ch;
                while (isdigit(ch = fgetc(filePtr))) {
                    nums[0][numj++] = ch;
                    isfloat = 1;
                }
            }
            if( (punto && isfloat) || punto == 0) {
                nums[0][numj] = '\0';
                insert_end(nums[0], lineNum, charNum, isfloat, NU, symbols);
                charNum += numj;
                numj = 0;
            } else{
                printf("Error en linea %d, char %d: ", lineNum, charNum);
                printf("Número no reconocido\n");
                charNum += numj-1;
                numj = 0;
            }
            fseek(filePtr, -1, SEEK_CUR);
        }

        charNum++;
    } // end while

    divPalabras(); //Funciones que identifica las palabras

    return 0;
}
/*--------------------/End tokenize()--------------------*/

/**
 * @param char
 * @return int
 */
//Función que define si un caracter es operador relacional
int iniciaOperadorRelacional(char c) {
    int i;
    int result = 0; // false
    if (c == '=' || c == '<' || c == '>' || c == '!') {
        result = 1;
    }
    return result;
}

/**
 * @param char
 * @return int
 */
//Función que define si un caracter es operador Aritmético
int esOperadorArit(char c) {
     int i;
     int result = 0; // false
     for (i = 0; i < 4; i++) {
        if (aritOperators[i] == c)
            result = 1;
     }
     return result;
}

/**
 * @param char
 * @return int
 */
//Función que define si un caracter es signo de puntuación.
int esSignoP(char c) {
     int i;
     int result = 0; // false
     for (i = 0; i < 6; i++) {
        if (signos[i] == c)
            result = 1;
     }
     return result;
}

/**
 *@param char*
 * @return int
 */
//Función que define si una cadena es reservada.
int esPR(char *str) {
    int i;
    int result = 0; // false
    for (i = 0; i < 8; i++) {
        if (!strcasecmp(pr[i], str))
            result = 1;
    }
    return result;
}

/**
 * @param char*
 * @return int
 */
//Función que define si una cadena es un identificador
int esId(char *str) {
    int result = 0;
    if (strlen(str) > 1 && str[0] == '@'){
        result = 1;
    }
    return result;
}

//Función que verifica sí una palabra (dividida por espacios) es Palabra Reservada, Id o un Error
void divPalabras() {
    int i;
    for (i = 0; i < wordi; i++) {
        if (esPR(words[i])) {
            /* Validación si entrada ya existe en la tabla SymTabl */
            if (search(words[i], symTab) == -1) {
                insert_end(words[i], wordLineNums[i], wordCharNums[i], 0, RW, symTab);
                insert_end(words[i], wordLineNums[i], wordCharNums[i], 0, RW, symbols);
            }
        } else if (esId(words[i])) {
            /* Validación si entrada ya existe en la tabla SymTabl */
            //if (search(words[wordi], symTab) == -1) {
            //    insert_end(words[i], wordLineNums[i], wordCharNums[i], 0, ID, symTab);
            //}
        } else {
            printf("Error en línea %d, char %d: ", wordLineNums[i], wordCharNums[i]);
            printf("%s no es Palabra Reservada\n", words[i]);
        }
    }
}

/**
 * @param int
 * @return char*
 */
//Devuelve sí es flotante o Entero
char* esFlotante(int flotante) {
    char *result;
    if (flotante) {
        return " Flotante ";
    } else {
        return " Entero ";
    }
}

void writeSymTable(List* list, FILE *outputfile) {
    Node* tmp = list->root;
    int counter = 0;

    fprintf(outputfile, "  SYMBOL TABLE\n");
    fprintf(outputfile, "================\n");
    fprintf(outputfile, "ID  Class  Value\n");
    fprintf(outputfile, "================\n");
    while (tmp != NULL) {
        fprintf(outputfile, "%i   %d      %s\n", counter++, tmp->data.klass, tmp->data.val);
        tmp = tmp->next;
    }
}

/**
 * @param int
 * @return char*
 */
//Devuelve sí es flotante o Entero
char* SearchType(int type) {
    switch(type) {
        case RW:
            return "Palabra Reservada";
            break;
        case ID:
            return "Identificador\t";
            break;
        case LO:
            return "Operador Lógico\t";
            break;
        case NU:
            return "Número";
            break;
        case ER:
            return "Error\t\t";
            break;
        case SP:
            return "Signo de Puntuación";
            break;
        case AO:
            return "Operador Aritmético";
            break;
        case RO:
            return "Operador Relacional";
            break;
        default:
            return "Undefined";
            break;
    }
}

//Función para imprimir la lista ligada
void print(List* list) {
    Node* tmp = list->root;
    while (tmp != NULL) {
        printf("%d\t%s\t%s\n", tmp->data.klass, SearchType(tmp->data.klass), tmp->data.val);
        tmp = tmp->next;
    }
}


int countList(List* list) {
    Node* tmp = list->root;
    int counter = 0;
    while (tmp) {
        tmp = tmp->next;
        counter++;
    }
    return counter;
}

//Función que imprime el análisis realizado
void printAnalisis(List* list) {
    printf("\n----------------------ANALIZADOR LÉXICO-----------------------------\n\n");
    printf("Se encontraron un total de: %d tokens: \n", countList(symbols));
    printf("======================================================================\n");
    printf("ID\tClase\t\t\tFila\tCol\tValor\t\tTipo\n");
    printf("----------------------------------------------------------------------\n");
    Node* tmp = list->root;
    while (tmp != NULL) {
        if (tmp->data.klass == NU) {
            printf("%d\t%s\t\t\t%d\t%d\t%s\t\t%s\n",tmp->data.klass, \
            SearchType(tmp->data.klass), tmp->data.lineNumber, tmp->data.charNumber,\
            tmp->data.val, esFlotante(tmp->data.isFloat));
        } else {
            printf("%d\t%s\t%d\t%d\t%s\n", tmp->data.klass, SearchType(tmp->data.klass),\
            tmp->data.lineNumber, tmp->data.charNumber, tmp->data.val);
        }
        tmp = tmp->next;
    }
    printf("\n--------------------------------------------------------------------\n\n");
}

void printSymTable(List* list) {
    //Imprime Tabla de simbolos
    printf("\n----------------------TABLA DE SÍMBOLOS-----------------------------\n");
    printf("Clase\tTipo\t\t\tValor\n");
    printf("--------------------------------------------------------------------\n");
    print(symTab);
    printf("\n--------------------------------------------------------------------\n\n");
}
