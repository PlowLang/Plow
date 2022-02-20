package com.drjcoding.plow.project.ast.managers.errors

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.project.ast.managers.Scope

class DuplicateScopesError(
    scope: Scope
) : PlowError(
    "duplicate scopes",
    PlowIssueInfo(null, "Found two scopes with the name $scope.")
)