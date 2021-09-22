parser grammar Plow;

options {
    tokenVocab = PlowLexer;
}

plowFile
    : (itemDecleration | import_)* EOF
    ;

import_
    : IMPORT qualifiedIdentifier
    ;

// Classes, Structs, & Enums -------------------------------------------------------------------------------------------

classDecleration
    : CLASS IDENTIFIER L_CURLY itemDecleration* R_CURLY
    ;

structDecleration
    : STRUCT IDENTIFIER L_CURLY itemDecleration* R_CURLY
    ;

enumDecleration
    : ENUM IDENTIFIER L_CURLY (IDENTIFIER COMMA)* IDENTIFIER itemDecleration* R_CURLY
    ;

// ---------------------------------------------------------------------------------------------------------------------


// Statements ----------------------------------------------------------------------------------------------------------

statements
    : (statement statementSeperator)* statement?
    ;

statement
    : variableDecleration
    | whileStatement
    | returnStatment
    | expression
    | itemDecleration
    ;

variableDecleration
    : (LET | VAR) IDENTIFIER COLON type ASSIGN expression
    ;

itemDecleration
    : functionDecleration
    | classDecleration
    | structDecleration
    | enumDecleration
    ;

statementSeperator
    : SEMICOLON
    ;

// ---------------------------------------------------------------------------------------------------------------------


// Control Flow --------------------------------------------------------------------------------------------------------

ifExpression
    : IF expression codeBlock (ELSE (IF expression)? codeBlock)*
    ;

whileStatement
    : WHILE expression codeBlock
    ;

returnStatment
    : RETURN expression?
    ;

codeBlock
    : L_CURLY statements R_CURLY
    ;

// ---------------------------------------------------------------------------------------------------------------------


// Expressions ---------------------------------------------------------------------------------------------------------

expression
    : primaryExpression
    | expression DOT IDENTIFIER           // object access
    | expression L_SQUARE expression R_SQUARE   // array access
    | expression L_PAREN functionArgs R_PAREN   // function call
    | expression AS type                        // cast
    | prefixOp expression                       // prefix operators
    | expression multiplicationOp expression    // multiplication level binary op
    | expression additionOp expression          // addition level binary op
    | expression comparisonOp expression        // comparison level binary op
    | expression IS type                        // typecheck
    | expression equalityOp expression          // equality level binary op
    | expression AND expression                 // and op
    | expression OR expression                  // or op
    | <assoc=right>
        expression assignmentOp expression      // assignment
    ;

prefixOp: PLUS | MINUS | NOT;
multiplicationOp: MULTIPLY | DIVIDE;
additionOp: PLUS | MINUS;
comparisonOp: L_ARROW | R_ARROW | L_OR_EQUAL | G_OR_EQUAL;
equalityOp: EQUAL | NOT_EQUAl;
assignmentOp: ASSIGN;

primaryExpression
    : L_PAREN expression R_PAREN
    | qualifiedIdentifier
    | literal
    | THIS
    | SUPER
    | ifExpression
    | tuple
    ;

tuple
    : L_PAREN (expression COMMA)* expression R_PAREN
    ;

literal
    : INT_LITERAL
    | FLOAT_LITERAL
    | BOOLEAN_LITERAL
    | CHAR_LITERAL
    | STRING_LITERAL
    ;

// ---------------------------------------------------------------------------------------------------------------------


// Functions -----------------------------------------------------------------------------------------------------------

functionDecleration
    : FUNC IDENTIFIER L_PAREN (functionParam COMMA)* functionParam? R_PAREN (COLON type)? codeBlock
    ;

functionParam
    : IDENTIFIER COLON type
    ;

functionArgs
    : (expression COMMA)* expression?
    ;

// ---------------------------------------------------------------------------------------------------------------------


// Types ---------------------------------------------------------------------------------------------------------------

type
    : qualifiedIdentifier
    | tupleType
    | functionType
    ;

tupleType
    : L_PAREN (type COMMA)* type R_PAREN
    ;

functionType
    : L_PAREN (type COMMA)* type R_PAREN THIN_ARROW type
    ;

// ---------------------------------------------------------------------------------------------------------------------


// Identifiers ---------------------------------------------------------------------------------------------------------

qualifiedIdentifier
    : (IDENTIFIER D_COLON)* IDENTIFIER
    ;

// ---------------------------------------------------------------------------------------------------------------------
