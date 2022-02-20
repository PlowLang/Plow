package com.drjcoding.plow.project.ast.managers

import com.drjcoding.plow.issues.PlowResult
import com.drjcoding.plow.issues.toPlowResult
import com.drjcoding.plow.parser.ast_nodes.QualifiedIdentifierASTNode
import com.drjcoding.plow.parser.ast_nodes.declaration_AST_nodes.TypeDeclarationASTNode
import com.drjcoding.plow.project.ast.managers.errors.AmbiguousTypeNameError
import com.drjcoding.plow.project.ast.managers.errors.NoMatchingTypeNameError

class ASTManagers {
    val scopes = ScopeManager()
    val types = TypesManager()
    val globals = GlobalsManager()
    val imports = ImportsManager()

    fun resolveTypeName(typeQI: QualifiedIdentifierASTNode, inScope: Scope): PlowResult<TypeDeclarationASTNode> {
        fun combineScopeAndQI(scope: Scope, qi: QualifiedIdentifierASTNode): Scope = Scope(scope.names + qi.namespaces)

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
}