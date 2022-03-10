package com.drjcoding.plow.project.ast.managers

import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.issues.toPlowResult
import com.drjcoding.plow.parser.ast_nodes.QualifiedIdentifierASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.GlobalDeclarationASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.TypeDeclarationASTNode
import com.drjcoding.plow.project.ast.managers.errors.AmbiguousNameError
import com.drjcoding.plow.project.ast.managers.errors.AmbiguousTypeNameError
import com.drjcoding.plow.project.ast.managers.errors.NoMatchingNameError
import com.drjcoding.plow.project.ast.managers.errors.NoMatchingTypeNameError

class ASTManagers {
    val scopes = ScopeManager()
    val types = TypesManager()
    val globals = GlobalsManager()
    val imports = ImportsManager()

    private fun combineScopeAndQI(scope: Scope, qi: QualifiedIdentifierASTNode): Scope =
        Scope(scope.names + qi.namespaces)

    fun resolveTypeName(typeQI: QualifiedIdentifierASTNode, inScope: Scope): PlowResult<TypeDeclarationASTNode> {
        for (scope in inScope.iterateOverThisAndParents()) {
            val absoluteScope = combineScopeAndQI(scope, typeQI)
            val typesInThisScope = types.getTypesInScope(absoluteScope)
            val possibleResolutions = typesInThisScope.filter { it.name == typeQI.name }
            return when (possibleResolutions.size) {
                0 -> continue
                1 -> possibleResolutions[0].toPlowResult()
                else -> PlowResult.Error(AmbiguousTypeNameError(typeQI, possibleResolutions))
            }
        }

        return PlowResult.Error(NoMatchingTypeNameError(typeQI))
    }

    fun resolveGlobalName(nameQI: QualifiedIdentifierASTNode, inScope: Scope): PlowResult<GlobalDeclarationASTNode> {
        for (scope in inScope.iterateOverThisAndParents()) {
            val absoluteScope = combineScopeAndQI(scope, nameQI)
            val globalsInScope = globals.getGlobalsInScope(absoluteScope)
            val possibleResolutions = globalsInScope.filter { it.name == nameQI.name }
            return when (possibleResolutions.size) {
                0 -> continue
                1 -> possibleResolutions[0].toPlowResult()
                else -> PlowResult.Error(AmbiguousNameError(nameQI, possibleResolutions))
            }
        }

        return PlowResult.Error(NoMatchingNameError(nameQI))
    }
}