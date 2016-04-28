//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 1 "Proyectic.y"

import java.io.*;
//#line 20 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 999;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short REAL=257;
public final static short DOBLE=258;
public final static short ENTERO=259;
public final static short CADENA=260;
public final static short ID=261;
public final static short CARACTER=262;
public final static short INT=263;
public final static short FLOAT=264;
public final static short CHAR=265;
public final static short DOUBLE=266;
public final static short VOID=267;
public final static short FOR=268;
public final static short WHILE=269;
public final static short ELSE=270;
public final static short CASE=271;
public final static short SWITCH=272;
public final static short BREAK=273;
public final static short DO=274;
public final static short DEFAULT=275;
public final static short SCAN=276;
public final static short PRINT=277;
public final static short RETURN=278;
public final static short STRUCT=279;
public final static short IF=280;
public final static short MASIGUAL=281;
public final static short MENOSIGUAL=282;
public final static short DIVIGUAL=283;
public final static short MULTIGUAL=284;
public final static short MODUIGUAL=285;
public final static short OR=286;
public final static short AND=287;
public final static short IGUAL=288;
public final static short DIF=289;
public final static short MENIGU=290;
public final static short MAYIGU=291;
public final static short INC=292;
public final static short DEC=293;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    3,    6,    6,    7,    7,
    5,    5,    5,    5,    5,    5,    8,    8,    8,    9,
    9,    4,   10,   10,   12,   12,   13,   13,   11,   14,
   14,   15,   15,   16,   16,   16,   16,   16,   16,   16,
   16,   16,   16,   16,   17,   17,   18,   28,   28,   19,
   20,   21,   29,   29,   31,   30,   30,   22,   23,   32,
   32,   25,   26,   24,   24,   27,   27,   34,   34,   34,
   34,   34,   34,   33,   33,   35,   35,   35,   38,   38,
   36,   36,   36,   36,   36,   36,   37,   37,   39,   39,
   40,   40,   41,   41,   41,   42,   42,   42,   42,   42,
   42,   42,   42,   42,   42,   43,   44,   44,   45,   45,
};
final static short yylen[] = {                            2,
    1,    2,    1,    1,    1,    3,    3,    1,    1,    4,
    1,    1,    1,    1,    1,    1,    5,    4,    2,    2,
    1,    6,    1,    1,    3,    1,    4,    2,    4,    2,
    0,    2,    0,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    2,    1,    6,    2,    0,    5,
    7,    8,    2,    1,    5,    4,    0,    9,    2,    2,
    2,    5,    5,    2,    3,    3,    1,    1,    1,    1,
    1,    1,    1,    1,    4,    3,    3,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    3,    1,    1,    1,
    3,    1,    1,    1,    1,    1,    1,    3,    1,    1,
    1,    1,    1,    2,    2,    4,    1,    0,    3,    1,
};
final static short yydefred[] = {                         0,
   11,   12,   14,   13,   15,    0,    0,    0,    3,    4,
    5,    0,   16,    0,    0,    2,    0,    0,    8,    0,
   21,    0,    0,    0,    0,    6,    0,    0,    0,   18,
   20,    0,    0,    0,    0,   26,    0,    7,   17,    0,
    0,    0,   10,    0,   31,   22,   25,   27,    0,   30,
    0,  100,  103,   99,  102,    0,  101,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   29,
   46,   42,   32,   34,   35,   36,   37,   38,   39,   40,
   41,   43,   44,    0,    0,    0,    0,    0,   92,   97,
    0,    0,    0,    0,    0,   59,    0,    0,    0,   64,
    0,    0,  105,  104,    0,   45,   68,   69,   70,   72,
   71,   73,    0,   80,   79,   85,   86,   83,   81,   84,
   82,    0,    0,   89,   90,    0,   93,   94,   95,    0,
  110,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   65,    0,   98,   66,   96,    0,    0,    0,   91,  106,
    0,   75,    0,    0,    0,    0,    0,    0,    0,  109,
    0,   50,    0,    0,   63,   62,    0,    0,    0,    0,
   54,    0,    0,   47,    0,    0,    0,    0,    0,   53,
   51,   48,   60,   61,    0,    0,    0,   52,   58,    0,
    0,   55,   56,
};
final static short yydgoto[] = {                          7,
    8,    9,   10,   11,   22,   18,   19,   13,   23,   34,
   72,   35,   36,   49,   51,   73,   74,   75,   76,   77,
   78,   79,   80,   81,   82,   83,   84,  174,  170,  179,
  171,  176,   85,  113,   86,  122,   87,  123,  126,   88,
  130,   89,   90,  132,  133,
};
final static short yysindex[] = {                       -47,
    0,    0,    0,    0,    0, -111,    0,  -47,    0,    0,
    0, -214,    0,  -87,  -47,    0,  -20,  -41,    0,  -47,
    0, -206, -123,   36, -183,    0, -206, -104,   -3,    0,
    0,    0, -171,   58,   65,    0,   17,    0,    0,   20,
   -7,  -47,    0,   24,    0,    0,    0,    0,  -47,    0,
   88,    0,    0,    0,    0,  -12,    0,   78,   80,   84,
   66,  169,   89,   90,   19,   91,  193,  193,  193,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   73,  -60,  -19,   39,   26,    0,    0,
  193,  193,  193,  193, -127,    0, -133, -124,  193,    0,
   79,  193,    0,    0,   98,    0,    0,    0,    0,    0,
    0,    0,  193,    0,    0,    0,    0,    0,    0,    0,
    0,  193,  193,    0,    0,  193,    0,    0,    0,  193,
    0,  108,  107,   60,   95,  114,  117,  125,  126,  127,
    0,  129,    0,    0,    0,   39,   39,   26,    0,    0,
  193,    0,  193,  169,   43,  193,  113,  118,  169,    0,
  119,    0,  -98,  135,    0,    0,  -91,  -81,  -77, -225,
    0,  128,  169,    0, -266,  144,  140,  143,   68,    0,
    0,    0,    0,    0,  169,  169,  169,    0,    0,  -74,
  -74,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,  203,    0,    0,
    0,    0,    0,  -57,    0,    0,   -6,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   -6,    0,
    0,  -30,    0,    0,  164,    0,    0,    0,    0,   62,
    0,    0,    0,    0,    0,    0,    0,    0,  112,    0,
    0,    0,    0,    0,    0,  -37,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -28,  147,   -2,   42,    0,    0,
  166,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  167,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    7,   33,   53,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  141,    0,    0,   85,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  204,   99,    0,   81,    0,  188,    0,  207,    0,
  189,    0,  187,    0,    0,   10,    0,    0,    0,    0,
    0,    0, -129,    0,    0,    0,  367,    0,    0,    0,
   64,    0,  -93,    0,    0,    0,  -15,    0,    0,  110,
    0,  109,    0,    0,    0,
};
final static int YYTABLESIZE=523;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         74,
  107,   30,   27,   74,   74,   74,   74,   74,   96,   74,
   24,   15,   96,   96,   96,   96,   96,   26,   96,   24,
   39,   74,   74,   74,   74,  183,  184,   91,  145,  145,
   96,   96,  145,   96,   78,   20,  145,    9,   78,   78,
  118,   78,  120,   76,   78,  169,   17,   76,   76,  178,
   76,   68,    9,   76,   29,   74,   78,   78,   69,   78,
  192,  193,  129,   67,   96,   76,   76,  127,   76,   77,
   25,   97,  128,   77,   77,   37,   77,  100,   92,   77,
   12,  124,   88,  125,   88,   88,   88,   25,   12,   40,
   78,   77,   77,   87,   77,   87,   87,   87,   41,   76,
   88,   88,   28,   88,   33,   28,  146,  147,   42,   43,
   44,   87,   87,   21,   87,   45,   48,   93,   21,   94,
   68,   31,   33,   95,   96,   77,   31,   69,   98,   99,
  102,  106,   67,  137,   88,  138,  139,  141,  143,    1,
    2,    3,    4,    5,   33,   87,   71,   50,  150,   14,
  151,   33,  152,  153,  154,    6,   33,  155,    1,    2,
    3,    4,    5,  162,  156,  163,  157,  158,  167,  159,
   33,  165,  169,   49,    6,  172,  166,  168,  173,  175,
   49,  177,  182,   67,  185,   49,  181,   67,   67,   67,
   67,   67,  188,   67,  189,  190,  191,  186,   61,   49,
  187,   68,    1,   19,   23,   67,  108,  107,   69,   57,
   45,   16,   70,   67,   38,    1,    2,    3,    4,    5,
  108,  109,  110,  111,  112,   68,   28,   71,   47,   46,
   15,    6,   69,  180,   33,  148,   33,   67,  149,   67,
    0,    0,    0,   74,   74,   74,   74,   74,   74,   74,
   74,   74,   74,   74,    0,    0,    0,   96,   96,   96,
   96,   96,   96,   49,    0,   49,  114,  115,  116,  117,
  119,  121,    0,    0,    0,   52,   53,   54,   55,   56,
   57,    0,    0,   78,   78,   78,   78,   78,   78,    0,
    0,   45,   76,   76,   76,   76,   76,   76,    1,    2,
    3,    4,   32,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    6,    0,    0,    0,   77,   77,
   77,   77,   77,   77,    0,    0,    0,   88,   88,   88,
   88,   88,   88,    0,    0,    0,    0,    0,   87,   87,
   87,   87,   87,   87,   52,   53,   54,   55,   56,   57,
    0,    0,    0,    0,    0,   58,   59,    0,    0,   60,
   61,   62,    0,   63,   64,   65,    0,   66,   33,   33,
   33,   33,   33,   33,    0,    0,    0,    0,    0,   33,
   33,    0,    0,   33,   33,   33,    0,   33,   33,   33,
    0,   33,    0,    0,    0,    0,    0,   49,   49,   49,
   49,   49,   49,    0,    0,    0,    0,    0,   49,   49,
    0,    0,   49,   49,   49,    0,   49,   49,   49,    0,
   49,    0,    0,    0,    0,   52,   53,   54,   55,   56,
   57,  101,    0,  103,  104,  105,   58,   59,    0,    0,
   60,   61,   62,    0,   63,   64,   65,    0,   66,   52,
   53,   54,   55,   56,   57,    0,    0,  131,  134,  135,
  136,    0,    0,    0,    0,  140,    0,    0,  142,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  144,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  160,    0,  161,
    0,    0,  164,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
   61,  125,   44,   41,   42,   43,   44,   45,   37,   47,
   41,  123,   41,   42,   43,   44,   45,   59,   47,   40,
  125,   59,   60,   61,   62,  292,  293,   40,  122,  123,
   59,   60,  126,   62,   37,  123,  130,   44,   41,   42,
   60,   44,   62,   37,   47,  271,  261,   41,   42,  275,
   44,   33,   59,   47,  261,   93,   59,   60,   40,   62,
  190,  191,   37,   45,   93,   59,   60,   42,   62,   37,
   91,   62,   47,   41,   42,  259,   44,   59,   91,   47,
    0,   43,   41,   45,   43,   44,   45,   91,    8,  261,
   93,   59,   60,   41,   62,   43,   44,   45,   41,   93,
   59,   60,   41,   62,   24,   44,  122,  123,   44,   93,
   91,   59,   60,   15,   62,  123,   93,   40,   20,   40,
   33,   23,   42,   40,   59,   93,   28,   40,   40,   40,
   40,   59,   45,  261,   93,  269,  261,   59,   41,  263,
  264,  265,  266,  267,   33,   93,   59,   49,   41,  261,
   44,   40,   93,   59,   41,  279,   45,   41,  263,  264,
  265,  266,  267,  154,   40,  123,   41,   41,  159,   41,
   59,   59,  271,   33,  279,   41,   59,   59,  270,  261,
   40,  259,  173,   37,   41,   45,   59,   41,   42,   43,
   44,   45,  125,   47,  185,  186,  187,   58,  273,   59,
   58,   33,    0,  261,   41,   59,   41,   41,   40,  125,
  123,    8,  125,   45,   27,  263,  264,  265,  266,  267,
  281,  282,  283,  284,  285,   33,   20,   59,   42,   41,
  261,  279,   40,  170,  123,  126,  125,   45,  130,   93,
   -1,   -1,   -1,  281,  282,  283,  284,  285,  286,  287,
  288,  289,  290,  291,   -1,   -1,   -1,  286,  287,  288,
  289,  290,  291,  123,   -1,  125,  286,  287,  288,  289,
  290,  291,   -1,   -1,   -1,  257,  258,  259,  260,  261,
  262,   -1,   -1,  286,  287,  288,  289,  290,  291,   -1,
   -1,  123,  286,  287,  288,  289,  290,  291,  263,  264,
  265,  266,  267,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  279,   -1,   -1,   -1,  286,  287,
  288,  289,  290,  291,   -1,   -1,   -1,  286,  287,  288,
  289,  290,  291,   -1,   -1,   -1,   -1,   -1,  286,  287,
  288,  289,  290,  291,  257,  258,  259,  260,  261,  262,
   -1,   -1,   -1,   -1,   -1,  268,  269,   -1,   -1,  272,
  273,  274,   -1,  276,  277,  278,   -1,  280,  257,  258,
  259,  260,  261,  262,   -1,   -1,   -1,   -1,   -1,  268,
  269,   -1,   -1,  272,  273,  274,   -1,  276,  277,  278,
   -1,  280,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
  260,  261,  262,   -1,   -1,   -1,   -1,   -1,  268,  269,
   -1,   -1,  272,  273,  274,   -1,  276,  277,  278,   -1,
  280,   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,
  262,   65,   -1,   67,   68,   69,  268,  269,   -1,   -1,
  272,  273,  274,   -1,  276,  277,  278,   -1,  280,  257,
  258,  259,  260,  261,  262,   -1,   -1,   91,   92,   93,
   94,   -1,   -1,   -1,   -1,   99,   -1,   -1,  102,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  113,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  151,   -1,  153,
   -1,   -1,  156,
};
}
final static short YYFINAL=7;
final static short YYMAXTOKEN=293;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"REAL","DOBLE","ENTERO","CADENA",
"ID","CARACTER","INT","FLOAT","CHAR","DOUBLE","VOID","FOR","WHILE","ELSE",
"CASE","SWITCH","BREAK","DO","DEFAULT","SCAN","PRINT","RETURN","STRUCT","IF",
"MASIGUAL","MENOSIGUAL","DIVIGUAL","MULTIGUAL","MODUIGUAL","OR","AND","IGUAL",
"DIF","MENIGU","MAYIGU","INC","DEC",
};
final static String yyrule[] = {
"$accept : programa",
"programa : lista_declaraciones",
"lista_declaraciones : lista_declaraciones declaracion",
"lista_declaraciones : declaracion",
"declaracion : declaracion_variables",
"declaracion : declaracion_funcion",
"declaracion_variables : tipo lista_variables ';'",
"lista_variables : lista_variables ',' lista",
"lista_variables : lista",
"lista : ID",
"lista : ID '[' ENTERO ']'",
"tipo : INT",
"tipo : FLOAT",
"tipo : DOUBLE",
"tipo : CHAR",
"tipo : VOID",
"tipo : tipo_struct",
"tipo_struct : STRUCT ID '{' cuerpo_struct '}'",
"tipo_struct : STRUCT '{' cuerpo_struct '}'",
"tipo_struct : STRUCT ID",
"cuerpo_struct : cuerpo_struct declaracion_variables",
"cuerpo_struct : declaracion_variables",
"declaracion_funcion : tipo ID '(' parametros ')' bloque",
"parametros : lista_parametros",
"parametros : VOID",
"lista_parametros : lista_parametros ',' parametro",
"lista_parametros : parametro",
"parametro : tipo ID '[' ']'",
"parametro : tipo ID",
"bloque : '{' declaraciones_locales lista_sentencias '}'",
"declaraciones_locales : declaraciones_locales declaracion_variables",
"declaraciones_locales :",
"lista_sentencias : lista_sentencias sentencia",
"lista_sentencias :",
"sentencia : sentencia_exp",
"sentencia : sentencia_if",
"sentencia : sentencia_while",
"sentencia : sentencia_do",
"sentencia : sentencia_switch",
"sentencia : sentencia_for",
"sentencia : sentencia_break",
"sentencia : sentencia_return",
"sentencia : bloque",
"sentencia : sentencia_imprime",
"sentencia : sentencia_lee",
"sentencia_exp : expresion ';'",
"sentencia_exp : ';'",
"sentencia_if : IF '(' expresion ')' sentencia sentencia_else",
"sentencia_else : ELSE sentencia",
"sentencia_else :",
"sentencia_while : WHILE '(' expresion ')' sentencia",
"sentencia_do : DO sentencia WHILE '(' expresion ')' ';'",
"sentencia_switch : SWITCH '(' ID ')' '{' lista_casos case_default '}'",
"lista_casos : lista_casos sentencia_case",
"lista_casos : sentencia_case",
"sentencia_case : CASE ENTERO ':' sentencia sentencia_break",
"case_default : DEFAULT ':' sentencia sentencia_break",
"case_default :",
"sentencia_for : FOR '(' expresion ';' expresion ';' sentencia_incremento ')' sentencia",
"sentencia_break : BREAK ';'",
"sentencia_incremento : ID INC",
"sentencia_incremento : ID DEC",
"sentencia_imprime : PRINT '(' expresion ')' ';'",
"sentencia_lee : SCAN '(' ID ')' ';'",
"sentencia_return : RETURN ';'",
"sentencia_return : RETURN expresion ';'",
"expresion : variable operador_asignacion expresion",
"expresion : expresion_simple",
"operador_asignacion : '='",
"operador_asignacion : MASIGUAL",
"operador_asignacion : MENOSIGUAL",
"operador_asignacion : MULTIGUAL",
"operador_asignacion : DIVIGUAL",
"operador_asignacion : MODUIGUAL",
"variable : ID",
"variable : ID '[' expresion ']'",
"expresion_simple : expresion_simple operador_relacional operando",
"expresion_simple : expresion_simple operador_logico operando",
"expresion_simple : operando",
"operador_logico : AND",
"operador_logico : OR",
"operador_relacional : MENIGU",
"operador_relacional : MAYIGU",
"operador_relacional : '<'",
"operador_relacional : '>'",
"operador_relacional : IGUAL",
"operador_relacional : DIF",
"operando : operando operador_adicion termino",
"operando : termino",
"operador_adicion : '+'",
"operador_adicion : '-'",
"termino : termino operador_mul factor",
"termino : factor",
"operador_mul : '*'",
"operador_mul : '/'",
"operador_mul : '%'",
"factor : variable",
"factor : llamada",
"factor : '(' expresion ')'",
"factor : ENTERO",
"factor : REAL",
"factor : CARACTER",
"factor : CADENA",
"factor : DOBLE",
"factor : '!' expresion",
"factor : '-' expresion",
"llamada : ID '(' argumentos ')'",
"argumentos : lista_argumentos",
"argumentos :",
"lista_argumentos : lista_argumentos ',' expresion",
"lista_argumentos : expresion",
};

//#line 95 "Proyectic.y"


/* A reference to the lexer object */
private Yylex lexer;

/* Interface to the lexer */
private int yylex(){
	int yyl_return = -1;
	try{
		yyl_return = lexer.yylex();
		//System.out.println("Token Leido " + yyl_return);
	}catch(IOException e){
		System.err.println("IO  error: " +e);
	}
	return yyl_return;
}

/* Error reporting */
public void yyerror(String error){
	System.err.println("Error: "+ error);
}


/* Lexer is created in the constructor */
public Parser(Reader r){
	lexer = new Yylex(r, this);
}


public static void main(String args[]){
	Parser yyparser = null;
	try{
		yyparser = new Parser(new FileReader(args[0]));
	}catch(Exception e){
		System.out.println("Error");
		e.printStackTrace();
	}
	//Método que realiza el proceso de analisis
	yyparser.yyparse();

}

//#line 534 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 47 "Proyectic.y"
{System.out.println("Análisis sintáctico completo");}
break;
//#line 687 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
