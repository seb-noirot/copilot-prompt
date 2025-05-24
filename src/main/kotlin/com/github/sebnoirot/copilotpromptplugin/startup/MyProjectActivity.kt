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
 * Checks if the Copilot prompt file exists and creates it if it doesn't.
 */
class MyProjectActivity : ProjectActivity {
    private val log = logger<MyProjectActivity>()

    override suspend fun execute(project: Project) {
        val promptService = CopilotPromptService.getInstance(project)

        // Check if the prompt file exists
        if (!promptService.promptFileExists()) {
            log.info("Copilot prompt file does not exist, creating it...")

            // Create the prompt file
            val (success, virtualFile, _) = promptService.createPromptFile()
            if (success) {
                log.info("Copilot prompt file created successfully")

                // Show notification with link to the file
                if (virtualFile != null) {
                    val notificationGroup = NotificationGroupManager.getInstance().getNotificationGroup("Copilot Prompt")
                    val notification = notificationGroup.createNotification(
                        "Copilot prompt file created",
                        "The Copilot prompt file has been created.",
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
            } else {
                log.warn("Failed to create Copilot prompt file")
            }
        } else {
            log.info("Copilot prompt file already exists")
        }
    }
}
