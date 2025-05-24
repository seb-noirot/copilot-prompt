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
         * Gets the instance of the settings.
         */
        fun getInstance(): CopilotPromptSettings {
            return service<CopilotPromptSettings>()
        }
    }

    @Tag("promptContent")
    var promptContent: String = DEFAULT_PROMPT

    override fun getState(): CopilotPromptSettings = this

    override fun loadState(state: CopilotPromptSettings) {
        XmlSerializerUtil.copyBean(state, this)

        // Ensure prompt content is not empty
        if (promptContent.isEmpty()) {
            promptContent = DEFAULT_PROMPT
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

}
