# Cheatsheet

## Endpoints

Both(ollama and OpenAPI) speak the same API: `POST {base-url}/chat/completions`

| | base-url | api-key |
|---|---|---|
| OpenAI | `https://api.openai.com/v1` | your key |
| Ollama | `http://localhost:11434/v1` | anything |

Header: `Authorization: Bearer {api-key}`

## The four roles

| Role | Who writes it | What it is |
|---|---|---|
| `system` | we | The rules of the agent, first message |
| `user` | the human | The question or task |
| `assistant` | the model | The answer, or a request to call tools |
| `tool` | we | The result of a tool call, linked by `tool_call_id` |

## Request

```json
{
  "model": "gpt-4.1-mini",
  "messages": [
    { "role": "system", "content": "You are alfred, a coding agent." },
    { "role": "user", "content": "What is in Hello.java?" }
  ],
  "tools": [
    {
      "type": "function",
      "function": {
        "name": "read_file",
        "description": "Read a file of the project.",
        "parameters": {
          "type": "object",
          "properties": { "path": { "type": "string" } },
          "required": ["path"]
        }
      }
    }
  ]
}
```

## Response with a tool call

The model does not run anything. It asks us to:

```json
{
  "choices": [{
    "message": {
      "role": "assistant",
      "content": null,
      "tool_calls": [{
        "id": "call_1",
        "type": "function",
        "function": { "name": "read_file", "arguments": "{\"path\":\"Hello.java\"}" }
      }]
    },
    "finish_reason": "tool_calls"
  }]
}
```

`arguments` is a JSON string, not an object.

## Sending the result back

Append the assistant message as it is, then one `tool` message per call, and call the model again:

```json
{ "role": "tool", "tool_call_id": "call_1", "content": "class Hello {}" }
```

## The two loops

```
outer loop (Repl):  read line -> agent.ask -> print -> repeat until exit
inner loop (Agent): call model -> tool calls? run them, add results, call again : return answer
```

The API is stateless: memory is just the list of messages we send every time.
