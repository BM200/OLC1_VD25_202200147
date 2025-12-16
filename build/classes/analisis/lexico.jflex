package analisis;

import java_cup.runtime.Symbol;
import java.util.LinkedList;
import excepciones.Errores;
%%
// codigo de usuario
%{
   public LinkedList<Errores> listaErrores = new LinkedList<>();
%}

%init{
    yyline = 1;
    yycolumn = 1;
    listaErrores = new LinkedList<>();
%init}


%cup
%class scanner
%public 
%line
%char
%column
%full
%ignorecase //JavaUsac es Case Insensitive


//simbolos del sistema
PAR1 = "("
PAR2 = ")"
MAS = "+"
MENOS = "-"
MULT = "*"
DIV = "/"
MOD = "%"
POT = "**"
IGUAL = "="
EQUALS = "=="
DIFERENTE = "!="
_llaveizq = "{"
_llaveder = "}"
_menorq = "<"
_menorigual = "<="
_mayorq = ">"
_mayorigual = ">="
DOSPUNTOS = ":"
PUNTO = "."
TRUE="true"
FALSE="false"
FINCADENA = ";"

// Operadores Logicos
AND = "&&"
OR = "||"
NOT = "!"
XOR = "^"


BLANCOS = [\ \r\t\f\n]+
ENTERO = [0-9]+
DECIMAL = [0-9]+"."[0-9]+
// Char acepta cualquier caracter entre comillas simples o secuencias de escape
CHAR = \'([^\'\\]|\\.)\'
// Cadena acepta todo entre comillas dobles
CADENA = [\"]([^\"\\]|\\.)*[\"]
ID = [a-zA-Z_][a-zA-Z0-9_]*

// Comentarios (El parser los va a ignorar)
COMENTARIO_LINEA = "//".*
COMENTARIO_MULTI = "/*" ~"*/"

// palabras reservadas
_for = "for"
_break ="break"
_continue = "continue"
_while = "while"
_do = "do"
_if = "if"
_else = "else"
_switch = "switch"
_case = "case"
_default = "default"
PRINT = "print"
VAR = "var"
INT = "int"
DOUBLE = "double"
STRING = "string"
BOOL = "bool"
CHAR_TYPE= "char"
%%
// Ignorar blancos y comentarios
<YYINITIAL> {BLANCOS} { }
<YYINITIAL> {COMENTARIO_LINEA} { }
<YYINITIAL> {COMENTARIO_MULTI} { }

// Palabras Reservadas del Sistema
<YYINITIAL> {PRINT} {return new Symbol(sym.PRINT, yyline, yycolumn, yytext());}
<YYINITIAL> {TRUE} {return new Symbol(sym.TRUE, yyline, yycolumn,yytext());}
<YYINITIAL> {FALSE} {return new Symbol(sym.FALSE, yyline, yycolumn,yytext());}

//Sentencias de Control 
<YYINITIAL> {_if} {return new Symbol(sym._if, yyline, yycolumn,yytext());}
<YYINITIAL> {_else} {return new Symbol(sym._else, yyline, yycolumn,yytext());}
<YYINITIAL> {_while} {return new Symbol(sym._while, yyline, yycolumn,yytext());}
<YYINITIAL> {_do}   {return new Symbol(sym._do, yyline, yycolumn, yytext()); }
<YYINITIAL> {_for} {return new Symbol(sym._for, yyline, yycolumn,yytext());}
<YYINITIAL> {_break} {return new Symbol(sym._break, yyline, yycolumn,yytext());}
<YYINITIAL> {_continue} {return new Symbol(sym._continue, yyline, yycolumn, yytext());}
<YYINITIAL> {_switch} {return new Symbol(sym._switch, yyline, yycolumn, yytext());}
<YYINITIAL> {_case} {return new Symbol(sym._case, yyline, yycolumn, yytext());}
<YYINITIAL> {_default} {return new Symbol(sym._default, yyline, yycolumn, yytext());}
//Tipos de Datos
<YYINITIAL> {VAR} {return new Symbol(sym.VAR, yyline, yycolumn,yytext());}
<YYINITIAL> {INT} {return new Symbol(sym.INT, yyline, yycolumn,yytext());}
<YYINITIAL> {STRING} {return new Symbol(sym.STRING, yyline, yycolumn,yytext());}
<YYINITIAL> {DOUBLE} {return new Symbol(sym.DOUBLE, yyline, yycolumn,yytext());}
<YYINITIAL> {BOOL} {return new Symbol(sym.BOOL, yyline, yycolumn,yytext());}
<YYINITIAL> {CHAR_TYPE} {return new Symbol(sym.CHAR_TYPE, yyline, yycolumn,yytext());}

<YYINITIAL> {ID} {return new Symbol(sym.ID, yyline, yycolumn,yytext());}
<YYINITIAL> {DECIMAL} {return new Symbol(sym.DECIMAL, yyline, yycolumn, yytext());}
<YYINITIAL> {ENTERO} {return new Symbol(sym.ENTERO, yyline, yycolumn, yytext());} 

<YYINITIAL> {CHAR} {
    String texto = yytext();       // 'A'
    char contenido = texto.charAt(1);  // A
    return new Symbol(sym.CHAR, yyline, yycolumn, contenido);
}
<YYINITIAL> {CADENA} {
    String cadena = yytext();
    cadena = cadena.substring(1, cadena.length()-1);
    return new Symbol(sym.CADENA, yyline, yycolumn,cadena);
    }

<YYINITIAL> {FINCADENA} {return new Symbol(sym.FINCADENA, yyline, yycolumn, yytext());}
<YYINITIAL> {DOSPUNTOS} {return new Symbol(sym.DOSPUNTOS, yyline, yycolumn,yytext());}
<YYINITIAL> {PAR1} {return new Symbol(sym.PAR1, yyline, yycolumn, yytext());}
<YYINITIAL> {PAR2} {return new Symbol(sym.PAR2, yyline, yycolumn, yytext());}
<YYINITIAL> {_llaveizq} {return new Symbol(sym._llaveizq, yyline, yycolumn, yytext());}
<YYINITIAL> {_llaveder} {return new Symbol(sym._llaveder, yyline, yycolumn, yytext());}

//Operadores Relacionales
<YYINITIAL> {_menorq} {return new Symbol(sym._menorq, yyline, yycolumn, yytext());}
<YYINITIAL> {_menorigual} {return new Symbol(sym._menorigual, yyline, yycolumn, yytext());}
<YYINITIAL> {_mayorq} {return new Symbol(sym._mayorq, yyline, yycolumn, yytext());}
<YYINITIAL> {_mayorigual} {return new Symbol(sym._mayorigual, yyline, yycolumn, yytext());}
<YYINITIAL> {EQUALS} {return new Symbol(sym.EQUALS, yyline, yycolumn, yytext());}
<YYINITIAL> {DIFERENTE} {return new Symbol(sym.DIFERENTE, yyline, yycolumn, yytext());}     
<YYINITIAL> {IGUAL} {return new Symbol(sym.IGUAL, yyline, yycolumn, yytext());}

//Operadores Aritmeticos
<YYINITIAL> {POT} {return new Symbol(sym.POTENCIA, yyline, yycolumn, yytext());} 
<YYINITIAL> {MAS} {return new Symbol(sym.MAS, yyline, yycolumn, yytext());}
<YYINITIAL> {MENOS} {return new Symbol(sym.MENOS, yyline, yycolumn, yytext());}
<YYINITIAL> {MULT}  {return new Symbol(sym.MULTIPLICACION, yyline, yycolumn, yytext());}
<YYINITIAL> {DIV}   {return new Symbol(sym.DIVISION, yyline, yycolumn, yytext());}
<YYINITIAL> {MOD}   {return new Symbol(sym.MODULO, yyline, yycolumn, yytext());}

//Operadores Logicos
<YYINITIAL> {AND}   {return new Symbol(sym.AND, yyline, yycolumn, yytext());}
<YYINITIAL> {OR}    {return new Symbol(sym.OR, yyline, yycolumn, yytext());}
<YYINITIAL> {NOT}   {return new Symbol(sym.NOT, yyline, yycolumn, yytext());}
<YYINITIAL> {XOR}   {return new Symbol(sym.XOR, yyline, yycolumn, yytext());}

<YYINITIAL> . {
            listaErrores.add(new Errores("LEXICO", "El carcater "+yytext()+" No pertenece al lenguaje", yyline, yycolumn));

}