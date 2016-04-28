#ifndef SYMDEF_H
#define SYMDEF_H

#define INT 1 /* token int */
#define FLOAT 2 /* token float */
#define DOUBLE 3 /* token double */
#define CHAR 4 /* token char */
#define BOOL 5 /* token bool */
#define ID 6 /* token id */
#define NUM 7 /* token numero */
#define CAD 8 /* token caracter */
#define TRUE 9 /* token true */
#define FALSE 10 /* token false */
#define IF 11 /* token if */
#define EL 12 /* token else */
#define WHI 13 /* token while */
#define PYC 14 /* token ; */
#define CO 15 /* token , */
#define ASIG 16 /* token = */
#define PA 17 /* token ( */
#define PC 18 /* token ) */
#define LLA 19 /* token { */
#define LLC 20 /* token } */
#define MAS 21 /* token + */
#define MENOS 22 /* token - */
#define MUL 23 /* token * */
#define DIV 24 /* token / */
#define MOD 25 /* token % */
#define MAQ 26 /* token > */
#define MEQ 27 /* token < */
#define DIF 28 /* token != */
#define PR 29 /* Palabra reservada */
#define OPA 30 /* Operador artimetico */
#define OPAS 31 /* Operador Asignacion */
#define OPR 32 /* Operador Relacional */
#define SIGN 33 /* signo de puntuacion */
#define ERR 34 /* error */
#define MAIQ 35 /* token >= */
#define MEIQ 36 /* token <= */
#define IGUAL 35 /* token == */

/* Se define una union para guardar todos los valores de los tokens */
typedef union VALORES VALORES;

union VALORES {
    char* sval;
    int ival;
    float fval;
    double dval;
};

printToken(int type, char* value, int charC, int lineC){
    switch(type) {
        case OPAS:
            //printf("Operador de asignación: %s\t\tcaracter: %d\t\tlinea: %d\n",value,charC,lineC);
            printf("______________________________________________________\n");
            printf("ASIG:  %s\t\t%d\t\t%d\n",value,charC,lineC);
            printf("______________________________________________________\n");
            break;
        case OPA:
            //printf("Operador aritmético: %s\t\tcaracter: %d\t\tlinea: %d\n",value,charC,lineC);
            printf("______________________________________________________\n");
            printf("ARIT:  %s\t\t%d\t\t%d\n",value,charC,lineC);
            printf("______________________________________________________\n");
            break;
        case OPR:
            //printf("Operador relacional %s\t\tcaracter: %d\t\tlinea: %d\n",value,charC,lineC);
            printf("______________________________________________________\n");
            printf("REL:   %s\t\t%d\t\t%d\n",value,charC,lineC);
            printf("______________________________________________________\n");
            break;
        case FLOAT:
            //printf("Flotante: %s\t\tcaracter: %d\t\tlinea: %d\n",value,charC,lineC);
            printf("______________________________________________________\n");
            printf("FLOAT: %s\t\t%d\t\t%d\n",value,charC,lineC);
            printf("______________________________________________________\n");
            break;
        case NUM:
            //printf("Numero Entero: %s\t\tcaracter: %d\t\tlinea: %d\n",value,charC,lineC);
            printf("______________________________________________________\n");
            printf("INT:   %s\t\t%d\t\t%d\n",value,charC,lineC);
            printf("______________________________________________________\n");
            break;
        case SIGN:
            //printf("Signo de puntuación: %s\t\tcaracter: %d\t\tlinea: %d\n",value,charC,lineC);
            printf("______________________________________________________\n");
            printf("SIGN:  %s\t\t%d\t\t%d\n",value,charC,lineC);
            printf("______________________________________________________\n");
            break;
        case ID:
            //printf("Identificador: %s\t\tcaracter: %d\t\tlinea: %d\n",value,charC,lineC);
            printf("______________________________________________________\n");
            printf("ID:     %s\t\t%d\t\t%d\n",value,charC,lineC);
            printf("______________________________________________________\n");
            break;
        case PR:
            //printf("Palabra reservada: %s\t\tcaracter: %d\t\tlinea: %d\n",value,charC,lineC);
            printf("______________________________________________________\n");
            printf("PR:     %s\t\t%d\t\t%d\n",value,charC,lineC);
            printf("______________________________________________________\n");
            break;
        case CAD:
            //printf("Constante literal: %s\t\tcaracter: %d\t\tlinea: %d\n",value,charC,lineC);
            printf("______________________________________________________\n");
            printf("LIT:    %s\t\t%d\t\t%d\n",value,charC,lineC);
            printf("______________________________________________________\n");
            break;
        default:
            //printf("Error léxico: %s\t\tcaracter: %d\t\tlinea: %d\n",value,charC,lineC);
            printf("______________________________________________________\n");
            printf("ERR:    %s\t\t%d\t\t%d\n",value,charC,lineC);
            printf("______________________________________________________\n");
            break;
    }
}

/* Se declara la variable yylval de tipo VALORES */
VALORES yylval;
# endif
