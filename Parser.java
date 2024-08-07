/*
    Laboratorio No. 3 - Recursive Descent Parsing
    CC4 - Compiladores

    Clase que representa el parser

    Actualizado: agosto de 2021, Luis Cu
*/

import java.util.LinkedList;
import java.util.Stack;

public class Parser {

    // Puntero next que apunta al siguiente token
    private int next;
    // Stacks para evaluar en el momento
    private Stack<Double> operandos;
    private Stack<Token> operadores;
    // LinkedList de tokens
    private LinkedList<Token> tokens;

    // Funcion que manda a llamar main para parsear la expresion
    public boolean parse(LinkedList<Token> tokens) {
        this.tokens = tokens;
        this.next = 0;
        this.operandos = new Stack<Double>();
        this.operadores = new Stack<Token>();

        // Recursive Descent Parser
        // Imprime si el input fue aceptado
        System.out.println("Aceptada? " + S());

        // Shunting Yard Algorithm
        // Imprime el resultado de operar el input
        // System.out.println("Resultado: " + this.operandos.peek());
        
        // Verifica si terminamos de consumir el input
        if(this.next != this.tokens.size()) {
            return false;
        }
        return true;
    }



    // Verifica que el id sea igual que el id del token al que apunta next
    // Si si avanza el puntero es decir lo consume.
    private boolean term(int id) {
        if(this.next < this.tokens.size() && this.tokens.get(this.next).equals(id)) {
            
            // Codigo para el Shunting Yard Algorithm
            /*
            if (id == Token.NUMBER) {
				// Encontramos un numero
				// Debemos guardarlo en el stack de operandos
				operandos.push( this.tokens.get(this.next).getVal() );

			} else if (id == Token.SEMI) {
				// Encontramos un punto y coma
				// Debemos operar todo lo que quedo pendiente
				while (!this.operadores.empty()) {
					popOp();
				}
				
			} else {
				// Encontramos algun otro token, es decir un operador
				// Lo guardamos en el stack de operadores
				// Que pushOp haga el trabajo, no quiero hacerlo yo aqui
				pushOp( this.tokens.get(this.next) );
			}
			*/

            this.next++;
            return true;
        }
        return false;
    }



    // Funcion que verifica la precedencia de un operador
    private int pre(Token op) {
        /* TODO: Su codigo aqui */

        /* El codigo de esta seccion se explicara en clase */

        switch(op.getId()) {
        	case Token.PLUS:
        		return 1;
        	case Token.MULT:
        		return 2;
        	default:
        		return -1;
        }
    }

    private void popOp() {
        Token op = this.operadores.pop();

        /* TODO: Su codigo aqui */

        /* El codigo de esta seccion se explicara en clase */

        if (op.equals(Token.PLUS)) {
        	double a = this.operandos.pop();
        	double b = this.operandos.pop();
        	// print para debug, quitarlo al terminar
        	System.out.println("suma " + a + " + " + b);
        	this.operandos.push(a + b);
        } else if (op.equals(Token.MULT)) {
        	double a = this.operandos.pop();
        	double b = this.operandos.pop();
        	// print para debug, quitarlo al terminar
        	System.out.println("mult " + a + " * " + b);
        	this.operandos.push(a * b);
        }
    }

    private void pushOp(Token op) {
        /* TODO: Su codigo aqui */

        /* Casi todo el codigo para esta seccion se vera en clase */
    	
    	// Si no hay operandos automaticamente ingresamos op al stack

    	// Si si hay operandos:
    		// Obtenemos la precedencia de op
        	// Obtenemos la precedencia de quien ya estaba en el stack
        	// Comparamos las precedencias y decidimos si hay que operar
        	// Es posible que necesitemos un ciclo aqui, una vez tengamos varios niveles de precedencia
        	// Al terminar operaciones pendientes, guardamos op en stack

    }

    // Gramtica corregida 

    /* 
    S :: = E ;											
    E :: = T + E | T
    T :: = N - T | N
    N :: = J * N | J 
    J:: = G / J | G
    G:: = A % G | A
    A:: = P ^ A | P
    P :: = ~P | M 
    M :: = ( E ) | number 
    */
    
    // Funciones para la regla de produccion inical

    private boolean S() {

        
        if((!E()) ||  (!term(Token.SEMI)) ){
            return false;}

        if(next != this.tokens.size() ) {
            return false;
        }
        return true;
       
    }




    // Funciones para la 1era regla de produccion 

    private boolean E1(){
        return T() && term(Token.PLUS) && E();}

    private boolean E2() {
        return T();}



    private boolean E() {
        int save = next;


        next = save;
        if(E1()) {return true;}

        next = save;
        if(E2()) {return true;}

        return false;
    }

   

    // Funciones para la 2da regla de produccion 

    private boolean T1(){
        return N() && term(Token.MINUS) && T();}

    private boolean T2() {
        return N();}



    private boolean T() {
        int save = next;


        next = save;
        if(T1()) {return true;}

        next = save;
        if(T2()) {return true;}

        return false;
    }

   

   
    // Funciones para la 3era regla de produccion 



    private boolean N1(){
        return J() && term(Token.MULT) && N();}

    private boolean N2() {
        return J();}



    private boolean N() {
        int save = next;


        next = save;
        if(N1()) {return true;}

        next = save;
        if(N2()) {return true;}

        return false;

    }

   
     // Funciones para la 4ta regla de produccion 


    private boolean J1(){
        return G() && term(Token.DIV) && J();}

    private boolean J2() {
        return G();}



    private boolean J() {
        
        int save = next;

        next = save;
        if(J1()) {return true;}

        next = save;
        if(J2()) {return true;}

        return false;

    }


    // Funciones para la 5ta  regla de produccion 


    private boolean G1(){
        return A() && term(Token.MOD) && G();}

    private boolean G2() {
        return A();}

    private boolean G() {
        int save = next;

        next = save;
        if(G1()) {return true;}

        next = save;
        if(G2()) {return true;}

        return false;
    }



    // Funciones para la 6ta regla de produccion 



    private boolean A1(){
        return P() && term(Token.EXP) && A();}

    private boolean A2() {
        return P();}


 
    private boolean A() {
        int save = next;

        next = save;
        if(A1()) {return true;}

        next = save;
        if(A2()) {return true;}

        return false;
    }



    // Funciones para la septima regla de produccion 



    private boolean P1(){
        return  term(Token.UNARY)  &&  P()   ;} 

    private boolean P2() {
        return M();}



    private boolean P() {
        int save = next;

        next = save;
        if(P1()) {return true;}

        next = save;
        if(P2()) {return true;}

        return false;
    }



    // Funciones para la octava regla de produccion 


    private boolean M1(){
        return  term(Token.LPAREN)  &&  E()   &&  term(Token.RPAREN);} 

    private boolean M2() {
        return term(Token.NUMBER);}



    private boolean M() {
        int save = next;

        next = save;
        if(M1()) {return true;}

        next = save;
        if(M2()) {return true;}

        return false;
    }





}
