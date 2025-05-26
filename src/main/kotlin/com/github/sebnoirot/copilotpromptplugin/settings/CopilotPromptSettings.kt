package com.github.sebnoirot.copilotpromptplugin.settings

import com.intellij.openapi.components.*
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.util.xmlb.annotations.Tag

/**
 * Persistent settings component for storing Copilot prompt.
 */
@State(
    name = "com.github.sebnoirot.copilotpromptplugin.settings.CopilotPromptSettings",
    storages = [Storage("CopilotPromptSettings.xml")]
)
class CopilotPromptSettings : PersistentStateComponent<CopilotPromptSettings> {

    companion object {
        /**
         * Default prompt content.
         */
        private val DEFAULT_PROMPT = """
            # GitHub Copilot Context

            This is the default Copilot prompt for this project.

            ## Project Description

            Please describe your project here.

            ## Guidelines

            - Guideline 1
            - Guideline 2
            - Guideline 3
        """.trimIndent()

        /**
         * Default Git commit instructions content.
         */
        private val DEFAULT_GIT_COMMIT_INSTRUCTIONS = """
            ## Commit message rules
            - Use the conventional commit format: `<type>(<scope>): <description>`
            - Types: feat, fix, docs, style, refactor, test, chore, perf
            - Keep the description concise (under 50 characters)
            - Use imperative mood (e.g., "add" not "added" or "adds")
            - Don't end with a period
            - Use lowercase for the first word unless it's a proper noun
            - Provide more details in the commit body if needed, separated by a blank line

            ## Branch naming conventions
            - Use kebab-case (lowercase with hyphens)
            - Follow the pattern: `<type>/<issue-number>-<short-description>`
            - Types: feature, bugfix, hotfix, release, support
            - Example: `feature/123-add-dark-mode`

            ## Pull request guidelines
            - Link related issues using keywords (Fixes #123, Closes #456)
            - Provide a clear description of changes
            - Add screenshots for UI changes
            - Ensure all CI checks pass before requesting review
            - Keep PRs focused and small when possible
        """.trimIndent()

        /**
         * Gets the instance of the settings.
         */
        fun getInstance(): CopilotPromptSettings {
            return service<CopilotPromptSettings>()
        }
    }

    @Tag("promptContent")
    var promptContent: String = DEFAULT_PROMPT

    @Tag("gitCommitInstructionsContent")
    var gitCommitInstructionsContent: String = DEFAULT_GIT_COMMIT_INSTRUCTIONS

    override fun getState(): CopilotPromptSettings = this

    override fun loadState(state: CopilotPromptSettings) {
        XmlSerializerUtil.copyBean(state, this)

        // Ensure prompt content is not empty
        if (promptContent.isEmpty()) {
            promptContent = DEFAULT_PROMPT
        }

        // Ensure git commit instructions content is not empty
        if (gitCommitInstructionsContent.isEmpty()) {
            gitCommitInstructionsContent = DEFAULT_GIT_COMMIT_INSTRUCTIONS
        }
    }

    /**
     * Validates that the prompt content is valid.
     * Returns a list of validation errors, or an empty list if valid.
     */
    fun validate(): List<String> {
        val errors = mutableListOf<String>()

        if (promptContent.length < 10) {
            errors.add("Prompt content must be at least 10 characters")
        }

        return errors
    }

    /**
     * Validates that the git commit instructions content is valid.
     * Returns a list of validation errors, or an empty list if valid.
     */
    fun validateGitCommitInstructions(): List<String> {
        val errors = mutableListOf<String>()

        if (gitCommitInstructionsContent.length < 10) {
            errors.add("Git commit instructions content must be at least 10 characters")
        }

        return errors
    }
}
