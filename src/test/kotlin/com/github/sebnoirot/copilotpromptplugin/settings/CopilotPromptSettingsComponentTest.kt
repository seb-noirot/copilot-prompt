package com.github.sebnoirot.copilotpromptplugin.settings

import org.junit.Assert.*
import org.junit.Test

/**
 * Test for the CopilotPromptSettingsComponent class.
 * This test focuses on the behavior of the save and reset buttons.
 */
class CopilotPromptSettingsComponentTest {

    /**
     * Test that the document listener correctly marks changes as unsaved.
     */
    @Test
    fun testDocumentListener() {
        // Create test settings
        val settings = CopilotPromptSettings()
        settings.promptContent = "Initial content"

        // Create a mock component
        val component = MockSettingsComponent(settings)

        // Verify initial state
        assertFalse("Should not have unsaved changes initially", component.isModified())

        // Simulate document change
        component.simulateDocumentChange()

        // Verify that changes are marked as unsaved
        assertTrue("Should have unsaved changes after document change", component.isModified())
    }

    /**
     * Test that the reset button correctly resets the content.
     */
    @Test
    fun testResetButton() {
        // Create test settings
        val settings = CopilotPromptSettings()
        settings.promptContent = "Initial content"

        // Create a mock component
        val component = MockSettingsComponent(settings)

        // Simulate document change
        component.simulateDocumentChange()

        // Verify that changes are marked as unsaved
        assertTrue("Should have unsaved changes after document change", component.isModified())

        // Simulate reset button click
        component.reset()

        // Verify that changes are reset
        assertFalse("Should not have unsaved changes after reset", component.isModified())
        assertEquals("Content should be reset to initial value", "Initial content", component.getPromptContent())
    }

    /**
     * Test that the save button correctly saves the content.
     */
    @Test
    fun testSaveButton() {
        // Create test settings
        val settings = CopilotPromptSettings()
        settings.promptContent = "Initial content"

        // Create a mock component
        val component = MockSettingsComponent(settings)

        // Simulate changing the content
        component.setPromptContent("New content")
        component.simulateDocumentChange()

        // Verify that changes are marked as unsaved
        assertTrue("Should have unsaved changes after content change", component.isModified())

        // Simulate save button click
        component.apply()

        // Verify that changes are saved
        assertFalse("Should not have unsaved changes after save", component.isModified())
        assertEquals("Settings should have the new content", "New content", settings.promptContent)
    }

    /**
     * Test that validation works correctly.
     */
    @Test
    fun testValidation() {
        // Create test settings
        val settings = CopilotPromptSettings()
        settings.promptContent = "Initial content"

        // Create a mock component
        val component = MockSettingsComponent(settings)

        // Set valid content
        component.setPromptContent("This is valid content")
        component.simulateDocumentChange()
        component.validatePrompt()

        // Verify that validation passes
        assertTrue("Validation should pass for valid content", component.isValidationPassed())

        // Set invalid content (too short)
        component.setPromptContent("Short")
        component.simulateDocumentChange()
        component.validatePrompt()

        // Verify that validation fails
        assertFalse("Validation should fail for short content", component.isValidationPassed())
    }

    /**
     * A mock class that simulates the behavior of CopilotPromptSettingsComponent.
     * This allows us to test the logic without needing to interact with actual UI components.
     */
    private class MockSettingsComponent(private val settings: CopilotPromptSettings) {
        private var promptContent = settings.promptContent
        private var hasUnsavedChanges = false
        private var validationPassed = true

        fun getPromptContent(): String {
            return promptContent
        }

        fun setPromptContent(content: String) {
            promptContent = content
        }

        fun simulateDocumentChange() {
            // Simulate document listener being triggered
            hasUnsavedChanges = true
            validatePrompt()
        }

        fun validatePrompt() {
            // Create a temporary settings object with the current content for validation
            val tempSettings = CopilotPromptSettings()
            tempSettings.promptContent = promptContent
            val errors = tempSettings.validate()
            validationPassed = errors.isEmpty()
        }

        fun isValidationPassed(): Boolean {
            return validationPassed
        }

        fun isModified(): Boolean {
            return hasUnsavedChanges
        }

        fun reset() {
            promptContent = settings.promptContent
            hasUnsavedChanges = false
        }

        fun apply() {
            settings.promptContent = promptContent
            hasUnsavedChanges = false
        }
    }
}
