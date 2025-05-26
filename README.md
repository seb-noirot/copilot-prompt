# Copilot Prompt Plugin

![Build](https://github.com/seb-noirot/copilot-prompt/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/27460.svg)](https://plugins.jetbrains.com/plugin/27460)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/27460.svg)](https://plugins.jetbrains.com/plugin/27460)

An IntelliJ IDEA plugin that manages GitHub Copilot instruction files (`.github/copilot-instructions.md`) and Git commit instruction files (`.github/git-commit-instructions.md`) to enhance AI-assisted coding and standardize Git practices.

<!-- Plugin description -->
The Copilot Prompt Plugin simplifies the creation and management of GitHub Copilot instruction files (`.github/copilot-instructions.md`) and Git commit instruction files (`.github/git-commit-instructions.md`) in your projects. These files provide context to GitHub Copilot and standardize Git practices, resulting in more accurate code suggestions and consistent commit messages.

## Features

- **Custom Instruction Management**: Create, edit, and manage custom instructions for GitHub Copilot and Git commits directly from IntelliJ IDEA.
- **One-Click Application**: Apply your Copilot and Git commit instructions to any project with a single click from the Tools menu.
- **Persistent Settings**: Your instruction templates are saved in IDE settings and can be reused across projects.
- **Path-Based Organization**: Store your instructions in the standardized `.github/` directory for team-wide consistency.
  - Copilot instructions: `.github/copilot-instructions.md`
  - Git commit instructions: `.github/git-commit-instructions.md`

## Usage

1. **Configure Your Instructions**: Go to Settings > Tools > Copilot Prompt to customize both your Copilot and Git commit instruction templates.
2. **Apply to Project**: 
   - Use "Apply Copilot Instructions" from the Tools > Copilot Prompt menu to create or update the Copilot instructions file.
   - Use "Apply Git Commit Instructions" from the same menu to create or update the Git commit instructions file.
3. **Benefit from Better Workflows**: 
   - GitHub Copilot will use your custom instructions when generating suggestions.
   - Your team can follow standardized Git commit practices using the Git commit instructions.
<!-- Plugin description end -->

## Installation

- Using the IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "Copilot Prompt"</kbd> >
  <kbd>Install</kbd>

- Using JetBrains Marketplace:

  Go to [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/27460) and install it by clicking the <kbd>Install to ...</kbd> button in case your IDE is running.

  You can also download the [latest release](https://plugins.jetbrains.com/plugin/27460/versions) from JetBrains Marketplace and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from
