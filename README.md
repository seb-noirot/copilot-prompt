# Copilot Prompt Plugin

![Build](https://github.com/seb-noirot/copilot-prompt/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/27460.svg)](https://plugins.jetbrains.com/plugin/27460)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/27460.svg)](https://plugins.jetbrains.com/plugin/27460)

An IntelliJ IDEA plugin that manages GitHub Copilot instruction files (`.github/copilot-instructions.md`) to enhance AI-assisted coding with customized context.

<!-- Plugin description -->
The Copilot Prompt Plugin simplifies the creation and management of GitHub Copilot instruction files (`.github/copilot-instructions.md`) in your projects. These files provide context to GitHub Copilot, resulting in more accurate and project-specific code suggestions.

## Features

- **Custom Instruction Management**: Create, edit, and manage custom instructions for GitHub Copilot directly from IntelliJ IDEA.
- **One-Click Application**: Apply your Copilot prompt to any project with a single click from the Tools menu.
- **Persistent Settings**: Your prompt templates are saved in IDE settings and can be reused across projects.
- **Path-Based Organization**: Store your Copilot instructions in the standardized `.github/copilot-instructions.md` path for compatibility with GitHub's specifications.

## Usage

1. **Configure Your Prompt**: Go to Settings > Tools > Copilot Prompt to customize your instruction template.
2. **Apply to Project**: Use the "Apply to Project" action from the Tools > Copilot Prompt menu to create or update the `.github/copilot-instructions.md` file.
3. **Benefit from Better Suggestions**: GitHub Copilot will now use your custom instructions when generating suggestions.
<!-- Plugin description end -->

## Installation

- Using the IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "Copilot Prompt"</kbd> >
  <kbd>Install</kbd>

- Using JetBrains Marketplace:

  Go to [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/27460) and install it by clicking the <kbd>Install to ...</kbd> button in case your IDE is running.

  You can also download the [latest release](https://plugins.jetbrains.com/plugin/27460/versions) from JetBrains Marketplace and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

- Manually:

  Download the [latest release](https://github.com/seb-noirot/copilot-prompt/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

## Development

### Testing Locally

To test the plugin locally during development:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/seb-noirot/copilot-prompt.git
   cd copilot-prompt
   ```

2. **Build the plugin**:
   ```bash
   ./gradlew build
   ```

3. **Run the plugin in a development instance of IntelliJ IDEA**:
   ```bash
   ./gradlew runIde
   ```
   This will start a new instance of IntelliJ IDEA with the plugin installed.

4. **Create a plugin distribution**:
   ```bash
   ./gradlew buildPlugin
   ```
   This will create a plugin distribution in `build/distributions` that can be installed in IntelliJ IDEA.

### Adding the Plugin to Your Website

To showcase the plugin on your website, you can use the JetBrains Marketplace widget: