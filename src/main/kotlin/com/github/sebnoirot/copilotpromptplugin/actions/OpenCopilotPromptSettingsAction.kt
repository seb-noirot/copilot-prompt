package com.github.sebnoirot.copilotpromptplugin.actions

import com.github.sebnoirot.copilotpromptplugin.settings.CopilotPromptConfigurable
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.DumbAware

/**
 * Action to open the Copilot Prompt plugin settings.
 */
class OpenCopilotPromptSettingsAction : AnAction(), DumbAware {
    
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        ShowSettingsUtil.getInstance().showSettingsDialog(project, CopilotPromptConfigurable::class.java)
    }
    
    override fun update(e: AnActionEvent) {
        // This action is always enabled
        e.presentation.isEnabled = true
    }
    
    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}