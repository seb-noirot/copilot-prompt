package com.github.sebnoirot.copilotpromptplugin.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.Presentation
import com.intellij.openapi.project.Project
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OpenCopilotPromptSettingsActionTest : BasePlatformTestCase() {

    /**
     * Test that the action is always enabled, even when a project is open.
     */
    fun testUpdate_WithProject() {
        // Arrange
        val action = OpenCopilotPromptSettingsAction()
        val presentation = Presentation()
        val event = createMockActionEvent(project, presentation)
        
        // Act
        action.update(event)
        
        // Assert
        assertTrue("Action should be enabled when a project is open", presentation.isEnabled)
    }
    
    /**
     * Test that the action is always enabled, even when no project is open.
     */
    fun testUpdate_WithoutProject() {
        // Arrange
        val action = OpenCopilotPromptSettingsAction()
        val presentation = Presentation()
        val event = createMockActionEvent(null, presentation)
        
        // Act
        action.update(event)
        
        // Assert
        assertTrue("Action should be enabled even when no project is open", presentation.isEnabled)
    }
    
    /**
     * Test that the action returns the correct update thread.
     */
    fun testGetActionUpdateThread() {
        // Arrange
        val action = OpenCopilotPromptSettingsAction()
        
        // Act
        val result = action.actionUpdateThread
        
        // Assert
        assertEquals("Action should use the background thread for updates", 
            com.intellij.openapi.actionSystem.ActionUpdateThread.BGT, result)
    }
    
    /**
     * Helper method to create a mock AnActionEvent.
     */
    private fun createMockActionEvent(project: Project?, presentation: Presentation): AnActionEvent {
        return AnActionEvent.createFromDataContext(
            "test",
            presentation,
            { dataId -> if (dataId == com.intellij.openapi.actionSystem.CommonDataKeys.PROJECT.name) project else null }
        )
    }
}