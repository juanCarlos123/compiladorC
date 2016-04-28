void P();
void D();
void DP();
void T();
void L();
void LP();
void I();
void IP();
void S();
void SP();
void Z();
void W();
void WP();
void X();
void Y();
void O();
void C();
void CP();
void Q();
void QP();
void E();
void EP();
void F();
void FP();
void G();
int token;
int nextToken() {
    return yylex();
}
void avanza() {
    token = nextToken();
}
void error() {
    printf("Error de sintaxis\n");
}

void eat(int tok) {
    if (tok == token) {
        token = nextToken();
    } else {
        error();
    }
}

void P() {
    printf("P -> DP\n");
    D();
    S();
}

void D() {
    printf("D -> TL;D'\n");
    T();
    L();
    eat(PYC);
    DP();
}

void DP() {
    printf("D' -> TL;D'|epsylon\n");
    while(token == INT || token == FLOAT || token == DOUBLE || token == CHAR || token == BOOL){
        T();
        L();
        eat(PYC);
        DP();
    }
}

void T() {
    printf("T -> int|float|double|char|bool\n");
    switch(token){
        case INT:
            avanza();
            break;
        case FLOAT:
            avanza();
            break;
        case DOUBLE:
            avanza();
            break;
        case CHAR:
            avanza();
            break;
        case BOOL:
            avanza();
            break;
        default:
            error();
            break;
    }
}

void L() {
    printf("L -> idIL'\n");
    eat(ID);
    I();
    LP();
}

void LP() {
    printf("L' -> ,L|epsylon\n");
    while(token == CO) {
        eat(CO);
        L();
    }
}

void I() {
    printf("I -> =I'\n");
    while(token == ASIG){
        eat(ASIG);
        IP();
    }
}

void IP() {
    printf("IP -> id|numero|caracter|true|false\n");
    switch(token) {
        case ID:
            avanza();
            break;
        case NUM:
            avanza();
            break;
        case CAD:
            avanza();
            break;
        case TRUE:
            avanza();
            break;
        case FALSE:
            avanza();
            break;
        default:
            error();
            break;
    }
}

void S() {
    printf("S -> ZS'\n");
    Z();
    SP();
}

void SP() {
    printf("S' -> ZS'|epsylon\n");
    while(token == IF || token == WHI || token == ID){
        Z();
        SP();
    }
}

void Z() {
    printf("Z -> W|X|Y;\n");
    switch(token){
        case IF:
            W();
            break;
        case WHI:
            X();
            break;
        case ID:
            Y();
            eat(PYC);
            break;
        default:
            error();
            break;
    }
}

void W() {
    printf("W -> if(C){S}W'\n");
    eat(IF);
    eat(PA);
    C();
    eat(PC);
    eat(LLA);
    S();
    eat(LLC);
    WP();
}

void WP() {
    printf("W' -> else{S}|epsylon\n");
    while( token == EL) {
        eat(EL);
        eat(LLA);
        S();
        eat(LLC);
    }
}

void X() {
    printf("X -> while(C){S}\n");
    eat(WHI);
    eat(PA);
    C();
    eat(PC);
    eat(LLA);
    S();
    eat(LLC);
}

void Y() {
    printf("Y -> idOE\n");
    eat(ID);
    O();
    E();
}

void O() {
    printf("O -> +=|-=|*=|/=|%=|=\n");
    switch(token) {
        case MAS:
            avanza();
            eat(ASIG);
            break;
        case MENOS:
            avanza();
            eat(ASIG);
            break;
        case MUL:
            avanza();
            eat(ASIG);
            break;
        case DIV:
            avanza();
            eat(ASIG);
            break;
        case MOD:
             avanza();
             eat(ASIG);
             break;
        case ASIG:
            avanza();
            break;
    }
}

void C() {
    printf("C -> QC'\n");
    Q();
    CP();
}

void CP() {
    printf("CP' -> ==QC'|!=QC'|epsylon\n");
    while(token == IGUAL || token == DIF){
        avanza();
        Q();
        CP();
    }
}

void Q() {
    printf("Q -> EQ'\n");
    E();
    QP();
}

void QP() {
    printf("Q' -> >CQ'|<CQ'|>=CQT'|<=CQT'|epsylon\n");
    while(token == MAQ || token == MEQ || token == MAIQ || token == MEIQ) {
        switch(token) {
            case MAQ:
                avanza();
                C();
                QP();
                break;
            case MEQ:
                avanza();
                C();
                QP();
                break;
            case MAIQ:
                avanza();
                C();
                QP();
                break;
            case MEIQ:
                avanza();
                C();
                QP();
                break;
            default:
                error();
                break;
        }
    }
}

void E() {
    printf("E -> FE'\n");
    F();
    EP();
}

void EP() {
    printf("E' -> +FE'|-FE'|epsylon\n");
    while(token == MAS || token == MENOS){
        if(token == MAS) {
            avanza();
        } else {
            eat(MENOS);
        }
        F();
        EP();
    }
}

void F() {
    printf("F -> GF'\n");
    G();
    FP();
}

void FP() {
    printf("F' -> *GF'|/GF'|%%GF'|epsylon\n");
    while(token == MUL || token == DIV || token == MOD) {
        avanza();
        G();
        FP();
    }
}

void G() {
    printf("G -> (E)|id|numero|caracter|true|false\n");
    switch(token) {
        case PA:
            avanza();
            E();
            eat(PC);
            break;
        case ID:
            avanza();
            break;
        case NUM:
            avanza();
            break;
        case CAD:
            avanza();
            break;
        case TRUE:
            avanza();
            break;
        case FALSE:
            avanza();
            break;
        default:
            error();
            break;
    }
}
