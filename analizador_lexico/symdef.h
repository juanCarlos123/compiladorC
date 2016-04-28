#ifndef SYMDEF_H
#define SYMDEF_H

#define MAX 20 // valor máximo de tamaño del string
#define LIMIT 200 // valor máximo de palabras en un archivo de entrada

#define RW 0 /* Reserved word */
#define ID 1 /* Identifier */
#define LO 2 /* Logical Operator */
#define NU 3 /* Number */
#define ER 4 /* Error */
#define SP 5 /* Seperator */
#define AO 6 /* Arithmetic Operator */
#define RO 7 /* Relational Operator */

//Variables para almacenar caracteres validos
char *pr[8] = {"int", "float","bool",
              "if", "else",
              "true","false","return"};

char asignacion = '=';

char *logicos[3] = {"&&", "||", "!"};

char *relationalOperators[] = {"==", "<", ">", "<>", "<=", ">="};

char aritOperators[4] = {'+', '-', '*', '/'};

char signos[6] = {'(', ')', '{', '}', ',', ';'};

//Arreglos dónde se almacena la información de las palabras
char words[LIMIT][MAX]; // include identifiers, and keywords
int wordi = 0, wordj = 0; //contador de palabra y tamaño de palabra
int wordLineNums[LIMIT]; //arreglo donde se almacena el número de linea
int wordCharNums[LIMIT]; //arreglo donde se almacena el número de carácter

//Arreglos dónde se almacena la información de los identificadores
char idens[1][MAX]; // almacena identificadores
int ideni = 0; //contador de identificador

//Arreglos dónde se almacena la información de los números
char nums[1][MAX];  // almacena numeros
int numj = 0; //contador de números y tamaño de los números

//Arreglos dónde se almacena la información de los signos
char signs[MAX]; // almacena signos de puntuacion
int signi = 0; //contador de signos

//Arreglos dónde se almacena la información de los operadores aritméticos
char otherOps[MAX]; // almacena operadores aritmeticos
int otherOpi = 0; //contador de operadores artiméticos

//Arreglos dónde se almacena la información de los operadores relacionales
char relOps[1][MAX]; // almacena Operadores Relacionales
int relOpj = 0; //contador de operadores relacionales y su tamaño

//Arreglos dónde se almacena la información de los operadores lógicos
char logics[1][MAX]; // almacena logicos
int logij = 0;

#endif
