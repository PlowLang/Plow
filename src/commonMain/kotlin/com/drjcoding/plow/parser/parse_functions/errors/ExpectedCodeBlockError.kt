package com.drjcoding.plow.parser.parse_functions.errors

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.source_abstractions.SourceFileRange
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

/**
 * Used when a block is syntactically expected but none is found. (Ex. `if foo else`)
 *
 * @param location the location after which the block is expected.
 */
class ExpectedCodeBlockError(
    location: SourceFileRange
): PlowError(
    "expected code block",
    PlowIssueInfo(location.toPlowIssueTextRange(), "expected a block after this")
)
