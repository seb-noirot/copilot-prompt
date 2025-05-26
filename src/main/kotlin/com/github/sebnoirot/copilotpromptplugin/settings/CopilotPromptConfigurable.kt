package com.github.sebnoirot.copilotpromptplugin.settings

import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.options.Configurable
import com.intellij.ui.EditorTextField
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBScrollPane
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.*
import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridLayoutManager

/**
 * Configurable component for the Copilot Prompt settings UI.
 */
class CopilotPromptConfigurable : Configurable {
    private var settingsComponent: CopilotPromptSettingsComponent? = null
    private val settings = CopilotPromptSettings.getInstance()

    override fun getDisplayName(): String = "Copilot Prompt"

    override fun createComponent(): JComponent {
        settingsComponent = CopilotPromptSettingsComponent(settings)
        return settingsComponent!!.panel
    }

    override fun isModified(): Boolean {
        return settingsComponent?.isModified() ?: false
    }

    override fun apply() {
        settingsComponent?.apply()
    }

    override fun reset() {
        settingsComponent?.reset()
    }

    override fun disposeUIResources() {
        settingsComponent = null
    }
}

class CopilotPromptSettingsComponent(private val settings: CopilotPromptSettings) {
    private val promptContentArea = EditorTextField("", null, FileTypeManager.getInstance().getFileTypeByExtension("md")).apply {
        setOneLineMode(false)
        setPreferredWidth(JBUI.scale(400))
        setMinimumSize(Dimension(JBUI.scale(400), JBUI.scale(200)))
        addSettingsProvider { editor: EditorEx ->
            editor.setVerticalScrollbarVisible(true)
            editor.setHorizontalScrollbarVisible(true)
            editor.settings.isLineNumbersShown = true
            editor.settings.isLineMarkerAreaShown = true
        }
    }

    private val gitCommitInstructionsArea = EditorTextField("", null, FileTypeManager.getInstance().getFileTypeByExtension("md")).apply {
        setOneLineMode(false)
        setPreferredWidth(JBUI.scale(400))
        setMinimumSize(Dimension(JBUI.scale(400), JBUI.scale(200)))
        addSettingsProvider { editor: EditorEx ->
            editor.setVerticalScrollbarVisible(true)
            editor.setHorizontalScrollbarVisible(true)
            editor.settings.isLineNumbersShown = true
            editor.settings.isLineMarkerAreaShown = true
        }
    }

    private val validationLabel = JBLabel()
    private val gitCommitValidationLabel = JBLabel()
    private var hasUnsavedChanges = false
    private val saveButton = JButton("Save").apply {
        addActionListener {
            apply()
        }
    }
    private val resetButton = JButton("Reset").apply {
        addActionListener {
            reset()
        }
    }

    val panel: JComponent = JPanel(BorderLayout()).apply {
        // Create the editor panel with proper layout and spacing
        val editorPanel = JPanel().apply {
            layout = GridLayoutManager(6, 1, JBUI.insets(10), JBUI.scale(5), JBUI.scale(5))

            // Prompt Content label
            add(JBLabel("Copilot prompt content:"),
                GridConstraints(0, 0, 1, 1,
                    GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null, 0, false))

            // Prompt Content editor
            add(JBScrollPane(promptContentArea),
                GridConstraints(1, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null, 0, false))

            // Validation area for prompt
            add(validationLabel,
                GridConstraints(2, 0, 1, 1,
                    GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null, 0, false))

            // Git Commit Instructions label
            add(JBLabel("Git commit instructions content:"),
                GridConstraints(3, 0, 1, 1,
                    GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null, 0, false))

            // Git Commit Instructions editor
            add(JBScrollPane(gitCommitInstructionsArea),
                GridConstraints(4, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null, 0, false))

            // Validation area for git commit instructions
            add(gitCommitValidationLabel,
                GridConstraints(5, 0, 1, 1,
                    GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null, 0, false))
        }

        // Create button panel
        val buttonPanel = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            add(Box.createHorizontalGlue())
            add(resetButton)
            add(Box.createHorizontalStrut(JBUI.scale(5)))
            add(saveButton)
        }

        // Add panels to main panel
        add(editorPanel, BorderLayout.CENTER)
        add(buttonPanel, BorderLayout.SOUTH)

        // Add document listener to the EditorTextField for prompt content
        promptContentArea.addDocumentListener(object : com.intellij.openapi.editor.event.DocumentListener {
            override fun documentChanged(event: com.intellij.openapi.editor.event.DocumentEvent) {
                // Mark that there are unsaved changes
                hasUnsavedChanges = true

                // Validate the prompt
                validatePrompt()
            }
        })

        // Add document listener to the EditorTextField for git commit instructions
        gitCommitInstructionsArea.addDocumentListener(object : com.intellij.openapi.editor.event.DocumentListener {
            override fun documentChanged(event: com.intellij.openapi.editor.event.DocumentEvent) {
                // Mark that there are unsaved changes
                hasUnsavedChanges = true

                // Validate the git commit instructions
                validateGitCommitInstructions()
            }
        })

        // Initialize with current settings
        reset()
    }

    /**
     * Validates the current prompt and shows validation errors.
     */
    private fun validatePrompt() {
        val errors = settings.validate()
        if (errors.isNotEmpty()) {
            validationLabel.text = "<html><font color='red'>Errors: ${errors.joinToString(", ")}</font></html>"
        } else {
            validationLabel.text = ""
        }
    }

    /**
     * Validates the current git commit instructions and shows validation errors.
     */
    private fun validateGitCommitInstructions() {
        val errors = settings.validateGitCommitInstructions()
        if (errors.isNotEmpty()) {
            gitCommitValidationLabel.text = "<html><font color='red'>Errors: ${errors.joinToString(", ")}</font></html>"
        } else {
            gitCommitValidationLabel.text = ""
        }
    }

    fun reset() {
        promptContentArea.text = settings.promptContent
        gitCommitInstructionsArea.text = settings.gitCommitInstructionsContent
        validatePrompt()
        validateGitCommitInstructions()
        hasUnsavedChanges = false
    }

    fun isModified(): Boolean {
        return hasUnsavedChanges ||
               promptContentArea.text != settings.promptContent ||
               gitCommitInstructionsArea.text != settings.gitCommitInstructionsContent
    }

    fun apply() {
        settings.promptContent = promptContentArea.text
        settings.gitCommitInstructionsContent = gitCommitInstructionsArea.text
        validatePrompt()
        validateGitCommitInstructions()
        hasUnsavedChanges = false
    }
}
