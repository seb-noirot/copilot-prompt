package com.github.sebnoirot.copilotpromptplugin.actions

import com.github.sebnoirot.copilotpromptplugin.services.CopilotPromptService
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

/**
 * Action to manually apply the Copilot prompt to the current project.
 */
class ApplyCopilotPromptAction : AnAction(), DumbAware {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        val promptService = CopilotPromptService.getInstance(project)
        val (success, virtualFile, wasCreated) = promptService.applyPrompt()

        showNotification(project, success, virtualFile, wasCreated)
    }

    override fun update(e: AnActionEvent) {
        // Enable the action only if a project is open
        e.presentation.isEnabled = e.project != null
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    /**
     * Shows a notification to the user about the result of the action.
     * If the file was created successfully, includes a link to open it.
     * @param project The project
     * @param success Whether the operation was successful
     * @param virtualFile The virtual file that was created or updated
     * @param wasCreated Whether the file was created (true) or updated (false)
     */
    private fun showNotification(project: Project, success: Boolean, virtualFile: VirtualFile?, wasCreated: Boolean) {
        val notificationGroup = NotificationGroupManager.getInstance().getNotificationGroup("Copilot Prompt")

        if (success) {
            // Different message based on whether the file was created or updated
            val message = if (wasCreated) {
                "The Copilot prompt file has been created."
            } else {
                "The Copilot prompt file has been updated."
            }

            val notification = notificationGroup.createNotification(
                "Copilot prompt applied",
                message,
                NotificationType.INFORMATION
            )

            // Add a link to open the file if it was created successfully
            if (virtualFile != null) {
                notification.addAction(
                    com.intellij.notification.NotificationAction.createSimple("Open file") {
                        OpenFileDescriptor(project, virtualFile).navigate(true)
                        notification.expire()
                    }
                )
            }

            notification.notify(project)
        } else {
            notificationGroup.createNotification(
                "Failed to apply copilot prompt",
                "There was an error creating or updating the Copilot prompt file.",
                NotificationType.ERROR
            ).notify(project)
        }
    }
}
