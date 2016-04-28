%%

%byaccj

%{
  private Parser yyparser ;

  public Yylex(java.io.Reader r, Parser p ){
    this(r);
    linea_actual = 1 ;
    yyparser = p  ;
  }

  private int linea_actual;

  public int lineaActual(){
     return linea_actual ;
  }
%}

%%

"{"    { 
          return Parser.LLA ;
       }

"}"   {
          return Parser.LLC;
      }

"="    { 
          return Parser.IGUAL ;
       }
"=="    { 
          return Parser.DIGUAL ;
       }

"("    { 
          return Parser.PA ; 
       }
")"    { 
          return Parser.PC ; 
       }
";"    { return Parser.PYC ;
       }
"+"|"-"|"*"|"/"{  
        yyparser.yylval = new ParserVal( new Character(yytext().charAt(0)) ) ;
        return Parser.OPERADOR ;
       }

"int" {
        return INT;
      }

"float"{
        return FLOAT;
      }
"double"{
        return DOUBLE;
      }
"while"{
        return WHILE;
      } 
"if"  {
        return IFP;
      }
"else"{
        return ELSEP;
      }
       
[0-9]*"."[0-9]* {  yyparser.yylval = new ParserVal( new Double(yytext()) );
          return Parser.NUMERO ; 
       }

[a-zA-Z][a-zA-Z0-9_]*
       {  yyparser.yylval = new ParserVal( (Object) yytext() ); 
          return Parser.CADENA ;  
       }
\n     {
         linea_actual ++ ;
       }
[ \t]+ {  // accion vacia: se ignoran los espacios y tabuladores 
       }
.      {  // token desconocido: se produce un mensaje de error 
          yyparser.yyerror("el(los) carácter(es) '"+yytext()+"' no forma(n) ningún token conocido"); 
       }