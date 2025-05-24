package com.github.sebnoirot.copilotpromptplugin.actions

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.Presentation

import com.intellij.openapi.project.Project

class ApplyCopilotPromptActionTest : BasePlatformTestCase() {

    /**
     * Test that the action is enabled when a project is open.
     */
    fun testUpdate_WithProject() {
        // Arrange
        val action = ApplyCopilotPromptAction()
        val presentation = Presentation()
        val event = createMockActionEvent(project, presentation)
        
        // Act
        action.update(event)
        
        // Assert
        assertTrue("Action should be enabled when a project is open", presentation.isEnabled)
    }
    
    /**
     * Test that the action is disabled when no project is open.
     */
    fun testUpdate_WithoutProject() {
        // Arrange
        val action = ApplyCopilotPromptAction()
        val presentation = Presentation()
        val event = createMockActionEvent(null, presentation)
        
        // Act
        action.update(event)
        
        // Assert
        assertFalse("Action should be disabled when no project is open", presentation.isEnabled)
    }
    
    /**
     * Test that the action returns the correct update thread.
     */
    fun testGetActionUpdateThread() {
        // Arrange
        val action = ApplyCopilotPromptAction()
        
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