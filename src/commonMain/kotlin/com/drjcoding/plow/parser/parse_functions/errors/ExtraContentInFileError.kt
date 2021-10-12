package com.drjcoding.plow.parser.parse_functions.errors

import com.drjcoding.plow.issues.PlowError
import com.drjcoding.plow.issues.PlowIssueInfo
import com.drjcoding.plow.source_abstractions.SourceFileRange
import com.drjcoding.plow.source_abstractions.toPlowIssueTextRange

class ExtraContentInFileError(content: SourceFileRange): PlowError(
    "extra content in file",
    PlowIssueInfo(content.toPlowIssueTextRange(), "Found this extra content at the end of the file.")
)