%{
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include "symdef.h"
#include "syntaxAnalizer.h"
int charC = 1,linesC = 1;
%}

caracter \"[a-zA-Z]+\"
id [a-zA-Z]+([0-9]+)?
numero [0-9]+
flotante [0-9]+\.[0-9]+
lines \n
spaces " "|\t|\r
%%
{lines} { linesC++; charC = 1;}
{spaces} { charC++; }
"int"|"INT" {yylval.sval = yytext; printToken(PR,yytext,charC,linesC); fprintf(yyout,"PR\t\t%d\t\t%d\t\t%s\n",charC,linesC,yytext); charC += strlen(yytext); return INT;}
"float"|"FLOAT" {yylval.sval = yytext; printToken(PR,yytext,charC,linesC); fprintf(yyout,"PR\t\t%d\t\t%d\t\t%s\n",charC,linesC,yytext); charC += strlen(yytext); return FLOAT;}
"double"|"DOUBLE" {yylval.sval = yytext; printToken(PR,yytext,charC,linesC); fprintf(yyout,"PR\t\t%d\t\t%d\t\t%s\n",charC,linesC,yytext); charC += strlen(yytext); return DOUBLE;}
"char"|"CHAR" {yylval.sval = yytext; printToken(PR,yytext,charC,linesC); fprintf(yyout,"PR\t\t%d\t\t%d\t\t%s\n",charC,linesC,yytext); charC += strlen(yytext); return CHAR;}
"bool"|"BOOL" {yylval.sval = yytext; printToken(PR,yytext,charC,linesC); fprintf(yyout,"PR\t\t%d\t\t%d\t\t%s\n",charC,linesC,yytext); charC += strlen(yytext); return BOOL;}
{flotante} {yylval.fval = atof(yytext); printToken(FLOAT,yytext,charC,linesC);charC += strlen(yytext); return NUM;}
{numero} {yylval.ival = atoi(yytext); printToken(NUM,yytext,charC,linesC); charC += strlen(yytext); return NUM;}
"true"|"TRUE" {yylval.ival = 1; printToken(PR,yytext,charC,linesC); fprintf(yyout,"PR\t\t%d\t\t%d\t\t%s\n",charC,linesC,yytext); charC += strlen(yytext); return TRUE;}
"false"|"FALSE" {yylval.ival = 0; printToken(PR,yytext,charC,linesC); fprintf(yyout,"PR\t\t%d\t\t%d\t\t%s\n",charC,linesC,yytext); charC += strlen(yytext); return FALSE;}
"if"|"IF" {yylval.sval = yytext; printToken(PR,yytext,charC,linesC); fprintf(yyout,"PR\t\t%d\t\t%d\t\t%s\n",charC,linesC,yytext); charC += strlen(yytext)+1; return IF;}
"else"|"ELSE" {yylval.sval = yytext; printToken(PR,yytext,charC,linesC); fprintf(yyout,"PR\t\t%d\t\t%d\t\t%s\n",charC,linesC,yytext); charC += strlen(yytext); return EL;}
"while"|"WHILE" {yylval.sval = yytext; printToken(PR,yytext,charC,linesC); fprintf(yyout,"PR\t\t%d\t\t%d\t\t%s\n",charC,linesC,yytext); charC += strlen(yytext); return WHI;}
"!=" {yylval.sval = yytext; printToken(OPR,yytext,charC,linesC); charC += strlen(yytext); return DIF;}
"==" {yylval.sval = yytext; printToken(OPR,yytext,charC,linesC); charC += strlen(yytext); return IGUAL;}
">=" {yylval.sval = yytext; printToken(OPR,yytext,charC,linesC); charC += strlen(yytext); return MAIQ;}
"<=" {yylval.sval = yytext; printToken(OPR,yytext,charC,linesC); charC += strlen(yytext); return MEIQ;}
";" {yylval.sval = yytext; printToken(SIGN,yytext,charC,linesC); charC += strlen(yytext); return PYC;}
"," {yylval.sval = yytext; printToken(SIGN,yytext,charC,linesC); charC += strlen(yytext); return CO;}
"=" {yylval.sval = yytext; printToken(OPAS,yytext,charC,linesC);charC += strlen(yytext); return ASIG;}
"(" {yylval.sval = yytext; printToken(SIGN,yytext,charC,linesC); charC += strlen(yytext); return PA;}
")" {yylval.sval = yytext; printToken(SIGN,yytext,charC,linesC); charC += strlen(yytext); return PC;}
"{" {yylval.sval = yytext; printToken(SIGN,yytext,charC,linesC);  charC += strlen(yytext); return LLA;}
"}" {yylval.sval = yytext; printToken(SIGN,yytext,charC,linesC); charC += strlen(yytext); return LLC;}
"+" {yylval.sval = yytext; printToken(OPA,yytext,charC,linesC);  charC += strlen(yytext); return MAS;}
"-" {yylval.sval = yytext; printToken(OPA,yytext,charC,linesC);  charC += strlen(yytext); return MENOS;}
"*" {yylval.sval = yytext; printToken(OPA,yytext,charC,linesC);  charC += strlen(yytext); return MUL;}
"/" {yylval.sval = yytext; printToken(OPA,yytext,charC,linesC);  charC += strlen(yytext); return DIV;}
"%" {yylval.sval = yytext; printToken(OPA,yytext,charC,linesC); charC += strlen(yytext); return MOD;}
">" {yylval.sval = yytext; printToken(OPR,yytext,charC,linesC); charC += strlen(yytext); return MAQ;}
"<" {yylval.sval = yytext; printToken(OPR,yytext,charC,linesC); charC += strlen(yytext); return MEQ;}
{id} {yylval.sval = yytext; printToken(ID,yytext,charC,linesC); fprintf(yyout,"ID\t\t%d\t\t%d\t\t%s\n",charC,linesC,yytext); charC += strlen(yytext); return ID;}
{caracter} {yylval.sval = yytext; printToken(CAD,yytext,charC,linesC);charC += strlen(yytext); return CAD;}
. {printToken(ERR,yytext,charC,linesC); charC +=strlen(yytext);}
%%

int main ( int argc , char ** argv ) {
    printf("TIPO\t\t\tCARACTER\tLINEA\n");
    printf("______________________________________________________\n");
    if ( argc > 1) {
        FILE * file;
        file = fopen(argv[1],"r");
        if (!file) {
            fprintf(stderr ," No se puede abrir el archivo % s \n",argv[1]);
            exit(1);
        }
        yyin = file;
    }

    FILE* outputfile = fopen("tsymbols.txt","w");
    fprintf(outputfile,"Típo\t\tCaracter\t\tLínea\t\tValor\n",charC,linesC,yytext);
    yyout = outputfile;
    token = yylex();
    P();
    fclose(yyin);
    fclose(yyout);
    return 0;
}
