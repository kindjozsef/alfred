# alfred

A tiny coding agent, built from scratch in five steps. No agent framework, just HTTP calls
and a couple of loops. At the end alfred can read and change the files of a project.

## Setup

1. Java 21.
2. Copy `src/main/resources/llm.properties.example` to `src/main/resources/llm.properties`
   and fill it in. The file is git-ignored.
3. For steps 4 and 5, clone the playground project next to this one.
   alfred works on `../refactorme` by default (`--workdir` changes it).
   `git reset --hard && git clean -fd` in the playground undoes whatever alfred did.
4. Check that everything builds:

```shell
./gradlew test 
```

The tests use WireMock instead of a real model, so they need no API key and no network.

## Running

```shell
# Build the new JAR (linux/macOS)
./gradlew shadowJar
# Windows
gradlew.bat shadowJar
# Run the JAR with a question
java -jar build/libs/alfred.jar "'What is a record in Java?'"
# From step 2 on running it without a question starts an interactive session:
java -jar build/libs/alfred.jar
```

## The kata

Every step has two branches:

| Branch | Content |
|---|---|
| `step-N-start` | The task: new code with `UnsupportedOperationException("Step N: ...")` and red tests |
| `step-N-resolve` | One possible solution |

Start every step from its start branch, even if you did not finish the previous one:

```shell
git switch step-1-start
```

A step is done when `./gradlew test` is green.

### Step 1: ask

Implement `LlmClient.chat`: send a `ChatRequest` to `{base-url}/chat/completions` with the
header `Authorization: Bearer {api-key}`, and return the message of the first choice.
Use the `RestClient` that is already there.

Test: `LlmClientTest`

### Step 2: outer loop

Implement `Repl.run`: read a line, ask the agent, print the answer, repeat until `exit`.

Test: `ReplTest`

### Step 3: memory

Ask alfred your name in two separate questions. It forgets it, because the API is stateless.
Keep the conversation in `Agent` and send the whole history on every call.

Test: `AgentMemoryTest`

### Step 4: read tool

The model cannot read files, it can only ask us to do it. New in this step: `Tool`,
`ListFilesTool` (a finished example), tool calls in `Message`, and tools in `LlmClient`.

1. Implement `ReadFileTool.execute`.
2. Implement the inner loop in `Agent.ask`: while the answer has tool calls, run every tool,
   add the results to the history as `tool` messages, and call the model again.

Tests: `ReadFileToolTest`, `AgentToolLoopTest`

### Step 5: write tool

Implement `WriteFileTool.execute`. Now alfred can refactor:

```shell
java -jar build/libs/alfred.jar "'Replace the magic numbers in the refactor with named constants.'"
```

Test: `WriteFileToolTest`

# The version catalog
All dependency and plugin versions live in gradle/libs.versions.toml. The build file then refers to them symbolically:

```shell
dependencies {
    implementation(libs.picocli)
}
```
## Keeping versions up to date
The version-catalog-update plugin resolves the latest available versions and rewrites the TOML file:

```shell
# update every entry, then reformat and sort the catalog
./gradlew versionCatalogUpdate
```

# Building a fat JAR
The Shadow plugin packages the application together with all of its runtime dependencies into one self-contained JAR, so it can be run anywhere a JVM is available without a classpath to assemble.

```shell
./gradlew shadowJar
```

The output lands in `build/libs/alfred.jar`:
```shell
java -jar build/libs/alfred.jar --help
```
