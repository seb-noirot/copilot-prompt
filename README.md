# Copilot Prompt Plugin

![Build](https://github.com/seb-noirot/copilot-prompt-plugin/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)

An IntelliJ IDEA plugin that automatically generates a `.github/copilot-instructions.md` file when a project is opened. This file is used by GitHub Copilot to provide context-aware suggestions.

<!-- Plugin description -->
The Copilot Prompt Plugin automatically generates a `.github/copilot-instructions.md` file when a project is opened in IntelliJ IDEA. This file is used by GitHub Copilot to provide context-aware suggestions.

## Features

- **Automatic Prompt Generation**: When a project is opened, the plugin checks if `.github/copilot-instructions.md` exists. If it doesn't, it creates the file using the best matching profile.
- **Multiple Prompt Profiles**: Define different prompt templates for different types of projects using regex patterns to match against project paths.
- **Profile Management**: Easily manage your prompt profiles through the settings UI.
- **Manual Application**: Apply the best matching profile at any time using the "Apply Copilot Prompt" action in the Tools menu.

## Usage

1. **Configure Profiles**: Go to Settings > Tools > Copilot Prompt to configure your prompt profiles.
2. **Automatic Generation**: When you open a project, the plugin will automatically create the `.github/copilot-instructions.md` file if it doesn't exist.
3. **Manual Application**: Use the "Apply Copilot Prompt" action from the Tools menu to manually apply the best matching profile.
<!-- Plugin description end -->

## Installation

- Using the IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "Copilot Prompt"</kbd> >
  <kbd>Install</kbd>

- Using JetBrains Marketplace:

  Go to [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID) and install it by clicking the <kbd>Install to ...</kbd> button in case your IDE is running.

  You can also download the [latest release](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID/versions) from JetBrains Marketplace and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

- Manually:

  Download the [latest release](https://github.com/seb-noirot/copilot-prompt-plugin/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

## Development

### Testing Locally

To test the plugin locally during development:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/seb-noirot/copilot-prompt-plugin.git
   cd copilot-prompt-plugin
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

### Configuration

The plugin configuration is stored in `gradle.properties`. You can modify the following properties:

- `pluginVersion`: The version of the plugin
- `pluginSinceBuild`: The minimum supported IntelliJ build
- `pluginUntilBuild`: The maximum supported IntelliJ build
- `platformVersion`: The version of IntelliJ IDEA to use for development

### Running Tests

The plugin includes unit tests for the core functionality. To run the tests:

```bash
./gradlew test
```

This will run all the tests and generate a report in `build/reports/tests/test/index.html`.

You can also run specific tests:

```bash
./gradlew test --tests "com.github.sebnoirot.copilotpromptplugin.model.PromptProfileTest"
```

The tests cover the following components:

- **PromptProfile**: Tests for the profile model, including validation and matching logic
- **CopilotPromptSettings**: Tests for the settings component, including profile management
- **ApplyCopilotPromptAction**: Tests for the action component

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation
