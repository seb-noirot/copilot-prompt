package com.github.sebnoirot.copilotpromptplugin.startup

import com.github.sebnoirot.copilotpromptplugin.services.CopilotPromptService
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

/**
 * Activity that runs when a project is opened.
 * Checks if the Copilot prompt file and Git commit instructions file exist and creates them if they don't.
 */
class MyProjectActivity : ProjectActivity {
    private val log = logger<MyProjectActivity>()

    override suspend fun execute(project: Project) {
        val promptService = CopilotPromptService.getInstance(project)

        // Check if the Copilot prompt file exists
        if (!promptService.promptFileExists()) {
            log.info("Copilot prompt file does not exist, creating it...")

            // Create the prompt file
            val (success, virtualFile, _) = promptService.createPromptFile()
            if (success) {
                log.info("Copilot prompt file created successfully")

                // Show notification with link to the file
                if (virtualFile != null) {
                    showNotification(
                        project,
                        "Copilot prompt file created",
                        "The Copilot prompt file has been created.",
                        virtualFile
                    )
                }
            } else {
                log.warn("Failed to create Copilot prompt file")
            }
        } else {
            log.info("Copilot prompt file already exists")
        }

        // Check if the Git commit instructions file exists
        if (!promptService.gitCommitInstructionsFileExists()) {
            log.info("Git commit instructions file does not exist, creating it...")

            // Create the Git commit instructions file
            val (success, virtualFile, _) = promptService.createGitCommitInstructionsFile()
            if (success) {
                log.info("Git commit instructions file created successfully")

                // Show notification with link to the file
                if (virtualFile != null) {
                    showNotification(
                        project,
                        "Git commit instructions file created",
                        "The Git commit instructions file has been created.",
                        virtualFile
                    )
                }
            } else {
                log.warn("Failed to create Git commit instructions file")
            }
        } else {
            log.info("Git commit instructions file already exists")
        }
    }

    /**
     * Shows a notification with a link to open a file.
     */
    private fun showNotification(project: Project, title: String, content: String, virtualFile: com.intellij.openapi.vfs.VirtualFile) {
        val notificationGroup = NotificationGroupManager.getInstance().getNotificationGroup("Copilot Prompt")
        val notification = notificationGroup.createNotification(
            title,
            content,
            NotificationType.INFORMATION
        )

        notification.addAction(
            com.intellij.notification.NotificationAction.createSimple("Open file") {
                OpenFileDescriptor(project, virtualFile).navigate(true)
                notification.expire()
            }
        )

        notification.notify(project)
    }
}
