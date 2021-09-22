lexer grammar PlowLexer;

WS: [ \t\n\r]+ -> channel(HIDDEN);
LINE_COMMENT: '//' ~[\n]* -> channel(HIDDEN);

// Literals
INT_LITERAL: '0'..'9' ('0..9' | '_')*;
FLOAT_LITERAL: INT_LITERAL '.' ('0..9' | '_')*;
BOOLEAN_LITERAL: 'true' | 'false';
CHAR_LITERAL: '\'' . '\'';
STRING_LITERAL: '"' (~["\\] | '\\"' | '\\')* '"';

// Puncuation
D_COLON: '::';
L_PAREN: '(';
R_PAREN: ')';
L_CURLY: '{';
R_CURLY: '}';
L_SQUARE: '[';
R_SQUARE: ']';
SEMICOLON: ';';
DOT: '.';
COMMA: ',';
PLUS: '+';
MINUS: '-';
MULTIPLY: '*';
DIVIDE: '/';
L_ARROW: '<';
R_ARROW: '>';
L_OR_EQUAL: '<=';
G_OR_EQUAL: '>=';
NOT: '!';
EQUAL: '==';
NOT_EQUAl: '!=';
AND: '&&';
OR: '||';
ASSIGN: '=';
COLON: ':';
THIN_ARROW: '->';

// Keywords
AS: 'as';
IS: 'is';
THIS: 'this';
SUPER: 'super';
LET: 'let';
VAR: 'var';
IF: 'if';
ELSE: 'else';
WHILE: 'while';
FUNC: 'func';
CLASS: 'class';
STRUCT: 'struct';
ENUM: 'enum';
IMPORT: 'import';
RETURN: 'return';

IDENTIFIER: ('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*;
