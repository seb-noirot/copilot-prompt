package com.github.sebnoirot.copilotpromptplugin.settings

import com.intellij.util.xmlb.XmlSerializer
import org.junit.Test
import org.junit.Assert.*

class CopilotPromptSettingsTest {

    /**
     * Test that the default prompt content is set correctly.
     */
    @Test
    fun testDefaultPromptContent() {
        // Arrange
        val settings = CopilotPromptSettings()

        // Assert
        assertNotNull("Default prompt content should not be null", settings.promptContent)
        assertTrue("Default prompt content should not be empty", settings.promptContent.isNotEmpty())
    }

    /**
     * Test that the prompt content can be set and retrieved.
     */
    @Test
    fun testSetAndGetPromptContent() {
        // Arrange
        val settings = CopilotPromptSettings()
        val newContent = "This is a new prompt content"

        // Act
        settings.promptContent = newContent

        // Assert
        assertEquals("Prompt content should be updated", newContent, settings.promptContent)
    }

    /**
     * Test that validation works correctly for valid content.
     */
    @Test
    fun testValidateValidContent() {
        // Arrange
        val settings = CopilotPromptSettings()
        settings.promptContent = "This is a valid prompt content"

        // Act
        val errors = settings.validate()

        // Assert
        assertTrue("There should be no validation errors for valid content", errors.isEmpty())
    }

    /**
     * Test that validation works correctly for content that is too short.
     */
    @Test
    fun testValidateShortContent() {
        // Arrange
        val settings = CopilotPromptSettings()
        settings.promptContent = "Short"  // Less than 10 characters

        // Act
        val errors = settings.validate()

        // Assert
        assertEquals("There should be one validation error for short content", 1, errors.size)
        assertTrue("Error should mention minimum length", 
            errors[0].contains("at least 10 characters"))
    }

    /**
     * Test that the state is correctly saved and loaded.
     */
    @Test
    fun testGetAndLoadState() {
        // Arrange
        val originalSettings = CopilotPromptSettings()
        val newContent = "This is a test prompt content for serialization"
        originalSettings.promptContent = newContent

        // Act - Get state
        val state = originalSettings.getState()

        // Assert - State should be the same object
        assertSame("getState should return the same object", originalSettings, state)

        // Act - Load state into a new settings object
        val newSettings = CopilotPromptSettings()
        newSettings.loadState(originalSettings)

        // Assert - Content should be copied
        assertEquals("Prompt content should be copied during loadState", 
            newContent, newSettings.promptContent)
    }

    /**
     * Test that serialization and deserialization work correctly.
     */
    @Test
    fun testSerializationAndDeserialization() {
        // Arrange
        val originalSettings = CopilotPromptSettings()
        val testContent = "Test prompt content for serialization"
        originalSettings.promptContent = testContent

        // Act - Serialize
        val element = XmlSerializer.serialize(originalSettings)

        // Act - Deserialize
        val deserializedSettings = CopilotPromptSettings()
        XmlSerializer.deserializeInto(deserializedSettings, element)

        // Assert
        assertEquals("Prompt content should be preserved during serialization/deserialization", 
            testContent, deserializedSettings.promptContent)
    }

    /**
     * Test that empty prompt content is replaced with default content during loadState.
     */
    @Test
    fun testLoadStateWithEmptyContent() {
        // Arrange
        val settings = CopilotPromptSettings()
        val emptySettings = CopilotPromptSettings()
        emptySettings.promptContent = ""

        // Act
        settings.loadState(emptySettings)

        // Assert
        assertFalse("Prompt content should not be empty after loadState", settings.promptContent.isEmpty())
    }
}
