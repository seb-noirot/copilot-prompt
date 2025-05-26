package com.github.sebnoirot.copilotpromptplugin.services

import com.github.sebnoirot.copilotpromptplugin.settings.CopilotPromptSettings
import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import java.io.File
import java.io.IOException

/**
 * Service for managing Copilot prompt files in projects.
 */
@Service(Service.Level.PROJECT)
class CopilotPromptService(private val project: Project) {
    private val log = logger<CopilotPromptService>()
    private val settings = CopilotPromptSettings.getInstance()

    companion object {
        const val COPILOT_PROMPT_FILE_PATH = ".github/copilot-instructions.md"
        const val GIT_COMMIT_INSTRUCTIONS_FILE_PATH = ".github/git-commit-instructions.md"

        /**
         * Gets the instance of the service for the given project.
         */
        fun getInstance(project: Project): CopilotPromptService {
            return project.getService(CopilotPromptService::class.java)
        }
    }

    /**
     * Checks if the Copilot prompt file exists in the project.
     */
    fun promptFileExists(): Boolean {
        val projectBasePath = project.basePath ?: return false
        val promptFile = File(projectBasePath, COPILOT_PROMPT_FILE_PATH)
        return promptFile.exists()
    }

    /**
     * Checks if the Git commit instructions file exists in the project.
     */
    fun gitCommitInstructionsFileExists(): Boolean {
        val projectBasePath = project.basePath ?: return false
        val instructionsFile = File(projectBasePath, GIT_COMMIT_INSTRUCTIONS_FILE_PATH)
        return instructionsFile.exists()
    }

    /**
     * Creates the Copilot prompt file with content from settings.
     * @return A Triple containing:
     *   - Boolean: true if the file was created successfully, false otherwise
     *   - VirtualFile?: the virtual file if it was created successfully, null otherwise
     *   - Boolean: always true since this method creates the file
     */
    fun createPromptFile(): Triple<Boolean, VirtualFile?, Boolean> {
        val projectBasePath = project.basePath ?: return Triple(false, null, true)

        // Create the file
        val promptFilePath = File(projectBasePath, COPILOT_PROMPT_FILE_PATH)

        try {
            // Ensure the directory exists
            val githubDir = File(projectBasePath, ".github")
            if (!githubDir.exists() && !githubDir.mkdirs()) {
                log.warn("Failed to create .github directory")
                return Triple(false, null, true)
            }

            // Write the file
            promptFilePath.writeText(settings.promptContent)

            // Refresh the VFS to make the file visible in the IDE
            val virtualFile = refreshVirtualFile(promptFilePath)

            log.info("Created Copilot prompt file at ${promptFilePath.absolutePath}")
            return Triple(true, virtualFile, true)
        } catch (e: IOException) {
            log.warn("Failed to create Copilot prompt file", e)
            return Triple(false, null, true)
        }
    }

    /**
     * Creates the Git commit instructions file with content from settings.
     * @return A Triple containing:
     *   - Boolean: true if the file was created successfully, false otherwise
     *   - VirtualFile?: the virtual file if it was created successfully, null otherwise
     *   - Boolean: always true since this method creates the file
     */
    fun createGitCommitInstructionsFile(): Triple<Boolean, VirtualFile?, Boolean> {
        val projectBasePath = project.basePath ?: return Triple(false, null, true)

        // Create the file
        val instructionsFilePath = File(projectBasePath, GIT_COMMIT_INSTRUCTIONS_FILE_PATH)

        try {
            // Ensure the directory exists
            val githubDir = File(projectBasePath, ".github")
            if (!githubDir.exists() && !githubDir.mkdirs()) {
                log.warn("Failed to create .github directory")
                return Triple(false, null, true)
            }

            // Write the file
            instructionsFilePath.writeText(settings.gitCommitInstructionsContent)

            // Refresh the VFS to make the file visible in the IDE
            val virtualFile = refreshVirtualFile(instructionsFilePath)

            log.info("Created Git commit instructions file at ${instructionsFilePath.absolutePath}")
            return Triple(true, virtualFile, true)
        } catch (e: IOException) {
            log.warn("Failed to create Git commit instructions file", e)
            return Triple(false, null, true)
        }
    }

    /**
     * Applies the prompt to the project, creating or overwriting the prompt file.
     * @return A Triple containing:
     *   - Boolean: true if the file was created or updated successfully, false otherwise
     *   - VirtualFile?: the virtual file if it was created or updated successfully, null otherwise
     *   - Boolean: true if the file was created, false if it was updated
     */
    fun applyPrompt(): Triple<Boolean, VirtualFile?, Boolean> {
        val projectBasePath = project.basePath ?: return Triple(false, null, false)

        // Create or update the file
        val promptFilePath = File(projectBasePath, COPILOT_PROMPT_FILE_PATH)
        val fileExistedBefore = promptFilePath.exists()

        try {
            // Ensure the directory exists
            val githubDir = File(projectBasePath, ".github")
            if (!githubDir.exists() && !githubDir.mkdirs()) {
                log.warn("Failed to create .github directory")
                return Triple(false, null, false)
            }

            // Write the file
            promptFilePath.writeText(settings.promptContent)

            // Refresh the VFS to make the file visible in the IDE
            val virtualFile = refreshVirtualFile(promptFilePath)

            log.info("Applied Copilot prompt to ${promptFilePath.absolutePath}")
            return Triple(true, virtualFile, !fileExistedBefore)
        } catch (e: IOException) {
            log.warn("Failed to apply Copilot prompt", e)
            return Triple(false, null, false)
        }
    }

    /**
     * Applies the Git commit instructions to the project, creating or overwriting the instructions file.
     * @return A Triple containing:
     *   - Boolean: true if the file was created or updated successfully, false otherwise
     *   - VirtualFile?: the virtual file if it was created or updated successfully, null otherwise
     *   - Boolean: true if the file was created, false if it was updated
     */
    fun applyGitCommitInstructions(): Triple<Boolean, VirtualFile?, Boolean> {
        val projectBasePath = project.basePath ?: return Triple(false, null, false)

        // Create or update the file
        val instructionsFilePath = File(projectBasePath, GIT_COMMIT_INSTRUCTIONS_FILE_PATH)
        val fileExistedBefore = instructionsFilePath.exists()

        try {
            // Ensure the directory exists
            val githubDir = File(projectBasePath, ".github")
            if (!githubDir.exists() && !githubDir.mkdirs()) {
                log.warn("Failed to create .github directory")
                return Triple(false, null, false)
            }

            // Write the file
            instructionsFilePath.writeText(settings.gitCommitInstructionsContent)

            // Refresh the VFS to make the file visible in the IDE
            val virtualFile = refreshVirtualFile(instructionsFilePath)

            log.info("Applied Git commit instructions to ${instructionsFilePath.absolutePath}")
            return Triple(true, virtualFile, !fileExistedBefore)
        } catch (e: IOException) {
            log.warn("Failed to apply Git commit instructions", e)
            return Triple(false, null, false)
        }
    }

    /**
     * Refreshes the virtual file in the IDE's VFS.
     * @return The refreshed VirtualFile, or null if it couldn't be found
     */
    private fun refreshVirtualFile(file: File): VirtualFile? {
        val virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(file)
        if (virtualFile != null) {
            VfsUtil.markDirtyAndRefresh(false, false, false, virtualFile)
        }
        return virtualFile
    }
}
