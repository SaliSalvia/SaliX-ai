@file:OptIn(ExperimentalMaterial3Api::class)

package com.example

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ui.theme.BotBubbleGradient
import com.example.ui.theme.DarkGray
import com.example.ui.theme.FrostedGlassContainer
import com.example.ui.theme.FrostedGlassWhite
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassBorderBrush
import com.example.ui.theme.GlassBorderPurple
import com.example.ui.theme.IceCyan
import com.example.ui.theme.IcyBackgroundGradient
import com.example.ui.theme.LightPurple
import com.example.ui.theme.LightPurpleContainer
import com.example.ui.theme.MediumGray
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SkyBlueGlow
import com.example.ui.theme.SkyBlueLight
import com.example.ui.theme.UserBubbleGradient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.UUID
import java.util.concurrent.TimeUnit

// ==========================================
// DATA MODELS
// ==========================================

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isUser: Boolean,
    val imageUri: Uri? = null,
    val imageBase64: String? = null,
    val isStreaming: Boolean = false,
    val isError: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

data class GeneratedImage(
    val id: String = UUID.randomUUID().toString(),
    val prompt: String,
    val imageUrl: String,
    val size: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class CodeWorkspaceMessage(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val isUser: Boolean,
    val isRunning: Boolean = false,
    val isError: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

enum class Screen {
    WELCOME,
    MAIN
}

enum class MainTab {
    CHAT,
    CODE,
    IMAGE,
    SETTINGS
}

data class AiProviderProfile(
    val id: String,
    val displayName: String,
    val baseUrl: String,
    val defaultChatModel: String,
    val defaultVisionModel: String = defaultChatModel,
    val defaultImageModel: String = "",
    val modelsPath: String = "/models",
    val balancePath: String = "",
    val notes: String
)

data class ProviderModelInfo(
    val id: String,
    val isFree: Boolean = false,
    val priceLabel: String = "Provider pricing"
)

val SupportedProviderProfiles = listOf(
    AiProviderProfile("openrouter", "OpenRouter", "https://openrouter.ai/api/v1", "openai/gpt-oss-120b", "qwen/qwen2.5-vl-72b-instruct", "", balancePath = "/credits", notes = "OpenAI-compatible chat, vision, tools and many free/paid routed models."),
    AiProviderProfile("groq", "Groq", "https://api.groq.com/openai/v1", "llama-3.3-70b-versatile", "meta-llama/llama-4-scout-17b-16e-instruct", "", notes = "Fast OpenAI-compatible inference for text, tools and selected multimodal models."),
    AiProviderProfile("nvidia", "NVIDIA NIM", "https://integrate.api.nvidia.com/v1", "meta/llama-3.1-70b-instruct", "microsoft/phi-3-vision-128k-instruct", "", notes = "OpenAI-compatible NIM endpoints for accelerated models."),
    AiProviderProfile("google", "Google Gemini", "https://generativelanguage.googleapis.com/v1beta/openai", "gemini-2.0-flash", "gemini-2.0-flash", "imagen-3.0-generate-002", notes = "Gemini OpenAI-compatible endpoint for chat, vision and tool calling."),
    AiProviderProfile("zai", "Z.ai", "https://api.z.ai/api/paas/v4", "glm-4.6", "glm-4v", "cogview-3", notes = "Native OpenAI-style chat plus CogView image generation."),
    AiProviderProfile("custom", "Custom OpenAI-compatible", "https://api.example.com/v1", "model-id", "model-id", "", notes = "Use any provider that exposes /chat/completions and /models with Bearer auth.")
)

// ==========================================
// VIEWMODEL
// ==========================================

class SalviaViewModel(context: Context) : ViewModel() {

    private val prefs = context.getSharedPreferences("salvia_aiz_prefs", Context.MODE_PRIVATE)

    private val _apiKey = MutableStateFlow(prefs.getString("api_key", "") ?: "")
    val apiKey: StateFlow<String> = _apiKey.asStateFlow()

    private val _providerId = MutableStateFlow(prefs.getString("provider_id", "openrouter") ?: "openrouter")
    val providerId: StateFlow<String> = _providerId.asStateFlow()

    private val _baseUrl = MutableStateFlow(prefs.getString("base_url", SupportedProviderProfiles.first().baseUrl) ?: SupportedProviderProfiles.first().baseUrl)
    val baseUrl: StateFlow<String> = _baseUrl.asStateFlow()

    private val _chatModel = MutableStateFlow(prefs.getString("chat_model", SupportedProviderProfiles.first().defaultChatModel) ?: SupportedProviderProfiles.first().defaultChatModel)
    val chatModel: StateFlow<String> = _chatModel.asStateFlow()

    private val _visionModel = MutableStateFlow(prefs.getString("vision_model", SupportedProviderProfiles.first().defaultVisionModel) ?: SupportedProviderProfiles.first().defaultVisionModel)
    val visionModel: StateFlow<String> = _visionModel.asStateFlow()

    private val _imageModel = MutableStateFlow(prefs.getString("image_model", SupportedProviderProfiles.first().defaultImageModel) ?: SupportedProviderProfiles.first().defaultImageModel)
    val imageModel: StateFlow<String> = _imageModel.asStateFlow()

    private val _models = MutableStateFlow<List<ProviderModelInfo>>(emptyList())
    val models: StateFlow<List<ProviderModelInfo>> = _models.asStateFlow()

    private val _providerStatus = MutableStateFlow("Connect a provider to sync models and credits.")
    val providerStatus: StateFlow<String> = _providerStatus.asStateFlow()

    private val _currentScreen = MutableStateFlow(if (_apiKey.value.isNotBlank()) Screen.MAIN else Screen.WELCOME)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _currentTab = MutableStateFlow(MainTab.CHAT)
    val currentTab: StateFlow<MainTab> = _currentTab.asStateFlow()

    // Chat State
    val chatMessages = mutableStateListOf<ChatMessage>()
    private val _isChatStreaming = MutableStateFlow(false)
    val isChatStreaming: StateFlow<Boolean> = _isChatStreaming.asStateFlow()

    private var activeChatJob: Job? = null

    // Code Workspace State
    val codeMessages = mutableStateListOf<CodeWorkspaceMessage>()
    private val _isCodeRunning = MutableStateFlow(false)
    val isCodeRunning: StateFlow<Boolean> = _isCodeRunning.asStateFlow()
    private var activeCodeJob: Job? = null

    // Image State
    private val _isImageGenerating = MutableStateFlow(false)
    val isImageGenerating: StateFlow<Boolean> = _isImageGenerating.asStateFlow()

    private val _imageError = MutableStateFlow<String?>(null)
    val imageError: StateFlow<String?> = _imageError.asStateFlow()

    private val _latestImage = MutableStateFlow<GeneratedImage?>(null)
    val latestImage: StateFlow<GeneratedImage?> = _latestImage.asStateFlow()

    val imageHistory = mutableStateListOf<GeneratedImage>()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    fun setApiKey(key: String) {
        val trimmed = key.trim()
        prefs.edit().putString("api_key", trimmed).apply()
        _apiKey.value = trimmed
        if (trimmed.isNotBlank()) {
            _currentScreen.value = Screen.MAIN
        } else {
            _currentScreen.value = Screen.WELCOME
        }
    }

    fun clearApiKey() {
        prefs.edit().remove("api_key").apply()
        _apiKey.value = ""
        _currentScreen.value = Screen.WELCOME
    }

    fun applyProvider(profile: AiProviderProfile) {
        prefs.edit()
            .putString("provider_id", profile.id)
            .putString("base_url", profile.baseUrl)
            .putString("chat_model", profile.defaultChatModel)
            .putString("vision_model", profile.defaultVisionModel)
            .putString("image_model", profile.defaultImageModel)
            .apply()
        _providerId.value = profile.id
        _baseUrl.value = profile.baseUrl
        _chatModel.value = profile.defaultChatModel
        _visionModel.value = profile.defaultVisionModel
        _imageModel.value = profile.defaultImageModel
        _providerStatus.value = "${profile.displayName} selected. Add your key, then sync models."
    }

    fun saveProviderSettings(baseUrl: String, chatModel: String, visionModel: String, imageModel: String) {
        val normalizedBase = baseUrl.trim().trimEnd('/')
        prefs.edit()
            .putString("base_url", normalizedBase)
            .putString("chat_model", chatModel.trim())
            .putString("vision_model", visionModel.trim())
            .putString("image_model", imageModel.trim())
            .apply()
        _baseUrl.value = normalizedBase
        _chatModel.value = chatModel.trim()
        _visionModel.value = visionModel.trim()
        _imageModel.value = imageModel.trim()
        _providerStatus.value = "Provider settings saved."
    }

    fun syncProviderMetadata() {
        val currentKey = _apiKey.value
        if (currentKey.isBlank()) {
            _providerStatus.value = "Add an API key before syncing provider metadata."
            return
        }
        _providerStatus.value = "Syncing models and account metadata…"
        viewModelScope.launch(Dispatchers.IO) {
            val profile = SupportedProviderProfiles.firstOrNull { it.id == _providerId.value }
            val modelResult = runCatching {
                val request = Request.Builder()
                    .url("${_baseUrl.value.trimEnd('/')}${profile?.modelsPath ?: "/models"}")
                    .addHeader("Authorization", "Bearer $currentKey")
                    .get()
                    .build()
                okHttpClient.newCall(request).execute().use { response ->
                    val body = response.body?.string().orEmpty()
                    if (!response.isSuccessful) error("Models: HTTP ${response.code}")
                    parseModels(body)
                }
            }
            val creditResult = runCatching {
                val path = profile?.balancePath.orEmpty()
                if (path.isBlank()) return@runCatching "Credit endpoint is provider-specific."
                val request = Request.Builder()
                    .url("${_baseUrl.value.trimEnd('/')}$path")
                    .addHeader("Authorization", "Bearer $currentKey")
                    .get()
                    .build()
                okHttpClient.newCall(request).execute().use { response ->
                    val body = response.body?.string().orEmpty()
                    if (!response.isSuccessful) "Credits: HTTP ${response.code}" else summarizeCredit(body)
                }
            }
            withContext(Dispatchers.Main) {
                _models.value = modelResult.getOrElse { emptyList() }
                _providerStatus.value = "Models: ${_models.value.size} found • ${creditResult.getOrDefault("Credit endpoint is provider-specific.")}"
            }
        }
    }

    fun setTab(tab: MainTab) {
        _currentTab.value = tab
    }

    fun clearChat() {
        activeChatJob?.cancel()
        _isChatStreaming.value = false
        chatMessages.clear()
    }

    fun stopChatStream() {
        activeChatJob?.cancel()
        _isChatStreaming.value = false
        if (chatMessages.isNotEmpty() && chatMessages.last().isStreaming) {
            val last = chatMessages.removeAt(chatMessages.size - 1)
            chatMessages.add(last.copy(isStreaming = false))
        }
    }

    // ==========================================
    // NETWORK: GLM-4.6 STREAMING CHAT
    // ==========================================
    fun sendChatMessage(userText: String, attachedImageUri: Uri?, imageBase64: String?) {
        if (userText.isBlank() && attachedImageUri == null) return
        val currentKey = _apiKey.value
        if (currentKey.isBlank()) {
            _currentScreen.value = Screen.WELCOME
            return
        }

        // Add user message
        val userMsg = ChatMessage(
            text = userText,
            isUser = true,
            imageUri = attachedImageUri,
            imageBase64 = imageBase64
        )
        chatMessages.add(userMsg)

        // Add placeholder bot message
        val botMsgId = UUID.randomUUID().toString()
        val botMsg = ChatMessage(
            id = botMsgId,
            text = "",
            isUser = false,
            isStreaming = true
        )
        chatMessages.add(botMsg)
        _isChatStreaming.value = true

        activeChatJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val jsonMessages = JSONArray()

                // Include conversational history
                for (msg in chatMessages.dropLast(1)) {
                    val msgObj = JSONObject()
                    msgObj.put("role", if (msg.isUser) "user" else "assistant")

                    if (msg.imageBase64 != null) {
                        val contentArray = JSONArray()
                        val textPart = JSONObject()
                        textPart.put("type", "text")
                        textPart.put("text", msg.text.ifBlank { "Please describe this image." })
                        contentArray.put(textPart)

                        val imgPart = JSONObject()
                        imgPart.put("type", "image_url")
                        val imgUrlObj = JSONObject()
                        imgUrlObj.put("url", "data:image/jpeg;base64,${msg.imageBase64}")
                        imgPart.put("image_url", imgUrlObj)
                        contentArray.put(imgPart)

                        msgObj.put("content", contentArray)
                    } else {
                        msgObj.put("content", msg.text)
                    }
                    jsonMessages.put(msgObj)
                }

                val requestJson = JSONObject()
                // Use GLM-4.6 or glm-4v if image attached
                val modelName = if (imageBase64 != null) _visionModel.value else _chatModel.value
                requestJson.put("model", modelName)
                requestJson.put("stream", true)
                requestJson.put("messages", jsonMessages)

                val mediaType = "application/json; charset=utf-8".toMediaType()
                val requestBody = requestJson.toString().toRequestBody(mediaType)

                val request = Request.Builder()
                    .url("${_baseUrl.value.trimEnd('/')}/chat/completions")
                    .addHeader("Authorization", "Bearer $currentKey")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "text/event-stream")
                    .post(requestBody)
                    .build()

                val response = okHttpClient.newCall(request).execute()

                if (!response.isSuccessful) {
                    val errorBody = response.body?.string() ?: "Unknown error"
                    val parsedError = try {
                        val errObj = JSONObject(errorBody)
                        errObj.optJSONObject("error")?.optString("message") ?: errorBody
                    } catch (e: Exception) {
                        "Error ${response.code}: $errorBody"
                    }

                    withContext(Dispatchers.Main) {
                        updateBotMessage(botMsgId, "⚠️ $parsedError", isStreaming = false, isError = true)
                        _isChatStreaming.value = false
                    }
                    return@launch
                }

                val source = response.body?.source()
                if (source == null) {
                    withContext(Dispatchers.Main) {
                        updateBotMessage(botMsgId, "⚠️ Empty response stream received.", isStreaming = false, isError = true)
                        _isChatStreaming.value = false
                    }
                    return@launch
                }

                val stringBuilder = StringBuilder()

                while (!source.exhausted()) {
                    val line = source.readUtf8Line() ?: break
                    if (line.isBlank()) continue

                    if (line.startsWith("data:")) {
                        val data = line.removePrefix("data:").trim()
                        if (data == "[DONE]") {
                            break
                        }

                        try {
                            val chunkObj = JSONObject(data)
                            val choices = chunkObj.optJSONArray("choices")
                            if (choices != null && choices.length() > 0) {
                                val choice = choices.getJSONObject(0)
                                val delta = choice.optJSONObject("delta")
                                val contentDelta = delta?.optString("content") ?: ""
                                if (contentDelta.isNotEmpty()) {
                                    stringBuilder.append(contentDelta)
                                    val currentText = stringBuilder.toString()
                                    withContext(Dispatchers.Main) {
                                        updateBotMessage(botMsgId, currentText, isStreaming = true, isError = false)
                                    }
                                }
                            }
                        } catch (_: Exception) {
                            // Non-json chunk or heartbeat
                        }
                    }
                }

                withContext(Dispatchers.Main) {
                    val finalText = if (stringBuilder.isBlank()) "No response returned." else stringBuilder.toString()
                    updateBotMessage(botMsgId, finalText, isStreaming = false, isError = false)
                    _isChatStreaming.value = false
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    updateBotMessage(botMsgId, "⚠️ Connection failed: ${e.localizedMessage ?: "Unknown network error"}", isStreaming = false, isError = true)
                    _isChatStreaming.value = false
                }
            }
        }
    }

    private fun updateBotMessage(id: String, text: String, isStreaming: Boolean, isError: Boolean) {
        val index = chatMessages.indexOfFirst { it.id == id }
        if (index != -1) {
            val old = chatMessages[index]
            chatMessages[index] = old.copy(text = text, isStreaming = isStreaming, isError = isError)
        }
    }


    fun clearCodeWorkspace() {
        activeCodeJob?.cancel()
        _isCodeRunning.value = false
        codeMessages.clear()
    }

    fun stopCodeTask() {
        activeCodeJob?.cancel()
        _isCodeRunning.value = false
        if (codeMessages.isNotEmpty() && codeMessages.last().isRunning) {
            val last = codeMessages.removeAt(codeMessages.size - 1)
            codeMessages.add(last.copy(isRunning = false))
        }
    }

    fun runCodeWorkspaceTask(repoUrl: String, branch: String, task: String, fileContext: String) {
        if (repoUrl.isBlank() && task.isBlank() && fileContext.isBlank()) return
        val currentKey = _apiKey.value
        if (currentKey.isBlank()) {
            _currentScreen.value = Screen.WELCOME
            return
        }

        val userTitle = if (repoUrl.isBlank()) "Code task" else repoUrl.trim()
        codeMessages.add(
            CodeWorkspaceMessage(
                title = userTitle,
                content = buildString {
                    if (branch.isNotBlank()) appendLine("Branch: ${branch.trim()}")
                    if (task.isNotBlank()) appendLine(task.trim())
                    if (fileContext.isNotBlank()) {
                        appendLine()
                        appendLine("Attached code/context:")
                        appendLine(fileContext.trim())
                    }
                }.trim(),
                isUser = true
            )
        )

        val replyId = UUID.randomUUID().toString()
        codeMessages.add(CodeWorkspaceMessage(id = replyId, title = "Codex-style review", content = "", isUser = false, isRunning = true))
        _isCodeRunning.value = true

        activeCodeJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val messages = JSONArray()
                messages.put(JSONObject().apply {
                    put("role", "system")
                    put("content", "You are SalviaAI Code Workspace, a Codex-style senior software engineering agent. Review GitHub repositories and pasted code safely. Produce concise plans, diagnostics, patches/diffs when possible, test commands, risk notes, and next steps. Do not claim you changed remote repositories unless an explicit tool/API integration is available.")
                })
                messages.put(JSONObject().apply {
                    put("role", "user")
                    put("content", buildString {
                        appendLine("Repository URL: ${repoUrl.ifBlank { "not provided" }}")
                        appendLine("Branch/ref: ${branch.ifBlank { "default" }}")
                        appendLine("Requested engineering task:")
                        appendLine(task.ifBlank { "Review, debug, and propose high-quality improvements." })
                        if (fileContext.isNotBlank()) {
                            appendLine()
                            appendLine("Code/files/context provided by user:")
                            appendLine(fileContext)
                        }
                    })
                })

                val requestJson = JSONObject()
                requestJson.put("model", _chatModel.value)
                requestJson.put("stream", true)
                requestJson.put("messages", messages)
                requestJson.put("tools", JSONArray().apply {
                    put(JSONObject().apply {
                        put("type", "function")
                        put("function", JSONObject().apply {
                            put("name", "propose_patch")
                            put("description", "Return a unified diff patch proposal for the connected repository.")
                            put("parameters", JSONObject().apply {
                                put("type", "object")
                                put("properties", JSONObject().apply {
                                    put("summary", JSONObject().put("type", "string"))
                                    put("diff", JSONObject().put("type", "string"))
                                    put("tests", JSONObject().put("type", "string"))
                                })
                            })
                        })
                    })
                })

                val response = okHttpClient.newCall(
                    Request.Builder()
                        .url("${_baseUrl.value.trimEnd('/')}/chat/completions")
                        .addHeader("Authorization", "Bearer $currentKey")
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "text/event-stream")
                        .post(requestJson.toString().toRequestBody("application/json; charset=utf-8".toMediaType()))
                        .build()
                ).execute()

                if (!response.isSuccessful) {
                    val errorBody = response.body?.string() ?: "Unknown error"
                    withContext(Dispatchers.Main) {
                        updateCodeMessage(replyId, "⚠️ Code workspace request failed: HTTP ${response.code}\n$errorBody", false, true)
                        _isCodeRunning.value = false
                    }
                    return@launch
                }

                val source = response.body?.source()
                val builder = StringBuilder()
                if (source != null) {
                    while (!source.exhausted()) {
                        val line = source.readUtf8Line() ?: break
                        if (!line.startsWith("data:")) continue
                        val data = line.removePrefix("data:").trim()
                        if (data == "[DONE]") break
                        runCatching {
                            val delta = JSONObject(data).optJSONArray("choices")?.optJSONObject(0)?.optJSONObject("delta")
                            val contentDelta = delta?.optString("content").orEmpty()
                            if (contentDelta.isNotEmpty()) {
                                builder.append(contentDelta)
                                withContext(Dispatchers.Main) { updateCodeMessage(replyId, builder.toString(), true, false) }
                            }
                        }
                    }
                }
                withContext(Dispatchers.Main) {
                    updateCodeMessage(replyId, builder.toString().ifBlank { "No code-workspace response returned." }, false, false)
                    _isCodeRunning.value = false
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    updateCodeMessage(replyId, "⚠️ Code workspace failed: ${e.localizedMessage ?: "Unknown error"}", false, true)
                    _isCodeRunning.value = false
                }
            }
        }
    }

    private fun updateCodeMessage(id: String, content: String, isRunning: Boolean, isError: Boolean) {
        val index = codeMessages.indexOfFirst { it.id == id }
        if (index != -1) {
            val old = codeMessages[index]
            codeMessages[index] = old.copy(content = content, isRunning = isRunning, isError = isError)
        }
    }

    // ==========================================
    // NETWORK: COGVIEW-3 IMAGE GENERATION
    // ==========================================
    fun generateImage(prompt: String, size: String) {
        if (prompt.isBlank()) return
        val currentKey = _apiKey.value
        if (currentKey.isBlank()) {
            _currentScreen.value = Screen.WELCOME
            return
        }

        _isImageGenerating.value = true
        _imageError.value = null

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val requestJson = JSONObject()
                requestJson.put("model", _imageModel.value.ifBlank { "cogview-3" })
                requestJson.put("prompt", prompt)
                requestJson.put("size", size)

                val mediaType = "application/json; charset=utf-8".toMediaType()
                val requestBody = requestJson.toString().toRequestBody(mediaType)

                val request = Request.Builder()
                    .url("${_baseUrl.value.trimEnd('/')}/images/generations")
                    .addHeader("Authorization", "Bearer $currentKey")
                    .addHeader("Content-Type", "application/json")
                    .post(requestBody)
                    .build()

                val response = okHttpClient.newCall(request).execute()
                val bodyStr = response.body?.string() ?: ""

                if (!response.isSuccessful) {
                    val parsedError = try {
                        val errObj = JSONObject(bodyStr)
                        errObj.optJSONObject("error")?.optString("message") ?: bodyStr
                    } catch (e: Exception) {
                        "Error ${response.code}: $bodyStr"
                    }

                    withContext(Dispatchers.Main) {
                        _imageError.value = parsedError
                        _isImageGenerating.value = false
                    }
                    return@launch
                }

                val jsonResponse = JSONObject(bodyStr)
                val dataArray = jsonResponse.optJSONArray("data")
                if (dataArray != null && dataArray.length() > 0) {
                    val firstItem = dataArray.getJSONObject(0)
                    val url = firstItem.optString("url")
                    if (url.isNotEmpty()) {
                        val generated = GeneratedImage(
                            prompt = prompt,
                            imageUrl = url,
                            size = size
                        )
                        withContext(Dispatchers.Main) {
                            _latestImage.value = generated
                            imageHistory.add(0, generated)
                            _isImageGenerating.value = false
                        }
                        return@launch
                    }
                }

                withContext(Dispatchers.Main) {
                    _imageError.value = "No image URL returned by the selected image model."
                    _isImageGenerating.value = false
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _imageError.value = "Generation failed: ${e.localizedMessage ?: "Network error"}"
                    _isImageGenerating.value = false
                }
            }
        }
    }
}

fun parseModels(body: String): List<ProviderModelInfo> {
    val root = JSONObject(body)
    val data = root.optJSONArray("data") ?: return emptyList()
    return (0 until data.length()).mapNotNull { index ->
        val item = data.optJSONObject(index) ?: return@mapNotNull null
        val id = item.optString("id").ifBlank { item.optString("name") }
        if (id.isBlank()) return@mapNotNull null
        val pricing = item.optJSONObject("pricing")
        val promptPrice = pricing?.optString("prompt")?.toDoubleOrNull() ?: 1.0
        val completionPrice = pricing?.optString("completion")?.toDoubleOrNull() ?: 1.0
        ProviderModelInfo(
            id = id,
            isFree = promptPrice == 0.0 && completionPrice == 0.0,
            priceLabel = if (promptPrice == 0.0 && completionPrice == 0.0) "Free" else "Paid / metered"
        )
    }
}

fun summarizeCredit(body: String): String {
    val root = JSONObject(body)
    val data = root.optJSONObject("data") ?: root
    val total = data.opt("total_credits") ?: data.opt("total") ?: data.opt("balance") ?: data.opt("credit")
    val free = data.opt("total_granted") ?: data.opt("free") ?: data.opt("daily_free")
    return when {
        total != null && free != null -> "credits: $total total, $free free/granted"
        total != null -> "credits/balance: $total"
        else -> "credit data available from provider"
    }
}

// ==========================================
// MAIN ACTIVITY
// ==========================================

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel: SalviaViewModel = viewModel(factory = SalviaViewModelFactory(applicationContext))
                SalviaApp(viewModel)
            }
        }
    }
}

class SalviaViewModelFactory(private val context: Context) : androidx.lifecycle.ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SalviaViewModel(context) as T
    }
}

// ==========================================
// ICY / GLASSY REUSABLE COMPONENTS
// ==========================================

@Composable
fun IcyAtmosphereBackground(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "icy_glow")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.45f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(IcyBackgroundGradient)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Top-Right Icy Blue Aura
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        SkyBlueLight.copy(alpha = pulseAlpha),
                        IceCyan.copy(alpha = pulseAlpha * 0.5f),
                        Color.Transparent
                    ),
                    center = Offset(width * 0.85f, height * 0.15f),
                    radius = width * 0.7f
                )
            )

            // Bottom-Left Lavender Frost Aura
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        LightPurple.copy(alpha = pulseAlpha * 0.6f),
                        LightPurpleContainer.copy(alpha = pulseAlpha * 0.3f),
                        Color.Transparent
                    ),
                    center = Offset(width * 0.15f, height * 0.85f),
                    radius = width * 0.8f
                )
            )

            // Center Subtle Crystal Sheen
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.6f),
                        Color.Transparent
                    ),
                    center = Offset(width * 0.5f, height * 0.45f),
                    radius = width * 0.5f
                )
            )
        }

        content()
    }
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(20.dp),
    backgroundColor: Color = FrostedGlassWhite,
    borderBrush: Brush = GlassBorderBrush,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .shadow(
                elevation = 6.dp,
                shape = shape,
                ambientColor = SkyBlueGlow,
                spotColor = LightPurple.copy(alpha = 0.2f)
            ),
        shape = shape,
        color = backgroundColor,
        border = BorderStroke(1.2.dp, borderBrush)
    ) {
        content()
    }
}

// ==========================================
// APP ROOT COMPOSABLE
// ==========================================

@Composable
fun SalviaApp(viewModel: SalviaViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()

    when (currentScreen) {
        Screen.WELCOME -> WelcomeScreen(
            onSaveApiKey = { key ->
                viewModel.setApiKey(key)
            }
        )
        Screen.MAIN -> MainContent(viewModel)
    }
}

// ==========================================
// A. WELCOME SCREEN (ICY / GLASSY LOOK)
// ==========================================

@Composable
fun WelcomeScreen(
    initialKey: String = "",
    onSaveApiKey: (String) -> Unit
) {
    var apiKeyText by remember { mutableStateOf(initialKey) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    IcyAtmosphereBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Glassmorphic Logo Container
            GlassCard(
                shape = RoundedCornerShape(28.dp),
                backgroundColor = FrostedGlassContainer,
                modifier = Modifier.size(110.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.ic_salvia_logo)
                            .crossfade(true)
                            .build(),
                        contentDescription = "SalviaAIZ Logo",
                        modifier = Modifier.size(82.dp),
                        error = painterResource(id = R.drawable.ic_salvia_logo),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Glass Title Pill
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = FrostedGlassWhite,
                border = BorderStroke(1.dp, GlassBorderBrush)
            ) {
                Text(
                    text = stringResource(id = R.string.welcome_title),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DarkGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.welcome_subtitle),
                fontSize = 14.sp,
                color = MediumGray,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Glass Card for API Key input
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                backgroundColor = FrostedGlassWhite
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = IceCyan,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Key,
                                    contentDescription = null,
                                    tint = SkyBlue,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = stringResource(id = R.string.api_key_label),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkGray
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = apiKeyText,
                        onValueChange = { apiKeyText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("api_key_input"),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.api_key_placeholder),
                                color = MediumGray.copy(alpha = 0.6f),
                                fontSize = 14.sp
                            )
                        },
                        singleLine = true,
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(
                                    imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = "Toggle key visibility",
                                    tint = SkyBlue
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = FrostedGlassContainer,
                            unfocusedContainerColor = FrostedGlassContainer.copy(alpha = 0.5f),
                            focusedBorderColor = SkyBlue,
                            unfocusedBorderColor = GlassBorderPurple,
                            focusedTextColor = DarkGray,
                            unfocusedTextColor = DarkGray
                        ),
                        shape = RoundedCornerShape(14.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = {
                                val text = clipboardManager.getText()?.text
                                if (!text.isNullOrBlank()) {
                                    apiKeyText = text
                                    Toast.makeText(context, "Pasted from clipboard", Toast.LENGTH_SHORT).show()
                                }
                            }
                        ) {
                            Text(
                                text = "Paste from clipboard",
                                color = SkyBlue,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            Button(
                onClick = {
                    if (apiKeyText.isNotBlank()) {
                        onSaveApiKey(apiKeyText)
                    } else {
                        Toast.makeText(context, "Please enter a valid provider API key", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .shadow(8.dp, RoundedCornerShape(18.dp), spotColor = SkyBlue.copy(alpha = 0.4f))
                    .testTag("start_button"),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SkyBlue,
                    contentColor = PureWhite
                )
            ) {
                Text(
                    text = stringResource(id = R.string.btn_start),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = FrostedGlassWhite.copy(alpha = 0.6f),
                border = BorderStroke(1.dp, GlassBorder)
            ) {
                Text(
                    text = "OpenAI-compatible providers: OpenRouter, Groq, NVIDIA, Google, Z.ai & custom",
                    fontSize = 12.sp,
                    color = MediumGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}

// ==========================================
// MAIN CONTENT & BOTTOM BAR
// ==========================================

@Composable
fun MainContent(viewModel: SalviaViewModel) {
    val currentTab by viewModel.currentTab.collectAsState()

    IcyAtmosphereBackground {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            bottomBar = {
                SalviaGlassBottomNavigation(
                    currentTab = currentTab,
                    onTabSelected = { viewModel.setTab(it) }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentTab) {
                    MainTab.CHAT -> ChatScreen(viewModel)
                    MainTab.CODE -> CodeWorkspaceScreen(viewModel)
                    MainTab.IMAGE -> ImageScreen(viewModel)
                    MainTab.SETTINGS -> SettingsScreen(viewModel)
                }
            }
        }
    }
}

// ==========================================
// B. ICY GLASS BOTTOM NAVIGATION BAR
// ==========================================

@Composable
fun SalviaGlassBottomNavigation(
    currentTab: MainTab,
    onTabSelected: (MainTab) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(12.dp, RoundedCornerShape(26.dp), spotColor = LightPurple.copy(alpha = 0.3f)),
        shape = RoundedCornerShape(26.dp),
        color = FrostedGlassWhite,
        border = BorderStroke(1.dp, GlassBorderBrush)
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            modifier = Modifier.height(68.dp)
        ) {
            NavigationBarItem(
                selected = currentTab == MainTab.CHAT,
                onClick = { onTabSelected(MainTab.CHAT) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.ChatBubble,
                        contentDescription = stringResource(id = R.string.tab_chat),
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.tab_chat),
                        fontWeight = if (currentTab == MainTab.CHAT) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 12.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = SkyBlue,
                    selectedTextColor = SkyBlue,
                    unselectedIconColor = MediumGray,
                    unselectedTextColor = MediumGray,
                    indicatorColor = IceCyan
                ),
                modifier = Modifier.testTag("tab_chat")
            )

            NavigationBarItem(
                selected = currentTab == MainTab.CODE,
                onClick = { onTabSelected(MainTab.CODE) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Terminal,
                        contentDescription = stringResource(id = R.string.tab_code),
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.tab_code),
                        fontWeight = if (currentTab == MainTab.CODE) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 12.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = SkyBlue,
                    selectedTextColor = SkyBlue,
                    unselectedIconColor = MediumGray,
                    unselectedTextColor = MediumGray,
                    indicatorColor = IceCyan
                ),
                modifier = Modifier.testTag("tab_code")
            )

            NavigationBarItem(
                selected = currentTab == MainTab.IMAGE,
                onClick = { onTabSelected(MainTab.IMAGE) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = stringResource(id = R.string.tab_image),
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.tab_image),
                        fontWeight = if (currentTab == MainTab.IMAGE) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 12.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = SkyBlue,
                    selectedTextColor = SkyBlue,
                    unselectedIconColor = MediumGray,
                    unselectedTextColor = MediumGray,
                    indicatorColor = IceCyan
                ),
                modifier = Modifier.testTag("tab_image")
            )

            NavigationBarItem(
                selected = currentTab == MainTab.SETTINGS,
                onClick = { onTabSelected(MainTab.SETTINGS) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(id = R.string.tab_settings),
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.tab_settings),
                        fontWeight = if (currentTab == MainTab.SETTINGS) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 12.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = SkyBlue,
                    selectedTextColor = SkyBlue,
                    unselectedIconColor = MediumGray,
                    unselectedTextColor = MediumGray,
                    indicatorColor = IceCyan
                ),
                modifier = Modifier.testTag("tab_settings")
            )
        }
    }
}

// ==========================================
// C. CHAT SCREEN (GLM-4.6 STREAMING)
// ==========================================

@Composable
fun ChatScreen(viewModel: SalviaViewModel) {
    val messages = viewModel.chatMessages
    val isStreaming by viewModel.isChatStreaming.collectAsState()
    var inputText by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedImageBase64 by remember { mutableStateOf<String?>(null) }

    val listState = rememberLazyListState()
    val context = LocalContext.current

    // Multimodal Image Picker Launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            selectedImageBase64 = encodeImageUriToBase64(context, uri)
        }
    }

    // Auto-scroll to bottom on new token/message
    LaunchedEffect(messages.size, messages.lastOrNull()?.text) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            Surface(
                color = FrostedGlassWhite,
                border = BorderStroke(1.dp, GlassBorderBrush)
            ) {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Universal AI Chat",
                                fontWeight = FontWeight.Bold,
                                color = DarkGray,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = IceCyan,
                                border = BorderStroke(1.dp, GlassBorder)
                            ) {
                                Text(
                                    text = "Streaming + Vision",
                                    fontSize = 11.sp,
                                    color = SkyBlue,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }
                    },
                    actions = {
                        if (isStreaming) {
                            IconButton(
                                onClick = { viewModel.stopChatStream() },
                                modifier = Modifier.testTag("stop_stream_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Stop,
                                    contentDescription = "Stop generation",
                                    tint = SkyBlue
                                )
                            }
                        }
                        IconButton(
                            onClick = { viewModel.clearChat() },
                            modifier = Modifier.testTag("clear_chat_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Clear chat",
                                tint = MediumGray
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = DarkGray
                    )
                )
            }
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .imePadding()
        ) {
            // Chat history or empty state
            if (messages.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        shape = RoundedCornerShape(24.dp),
                        backgroundColor = FrostedGlassWhite
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Surface(
                                modifier = Modifier.size(64.dp),
                                shape = CircleShape,
                                color = IceCyan,
                                border = BorderStroke(1.dp, GlassBorder)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.ChatBubble,
                                        contentDescription = null,
                                        tint = SkyBlue,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(id = R.string.chat_welcome_title),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkGray,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = stringResource(id = R.string.chat_welcome_desc),
                                fontSize = 13.sp,
                                color = MediumGray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(messages, key = { it.id }) { msg ->
                        ChatBubbleItem(msg = msg)
                    }
                }
            }

            // Attached Image Thumbnail Preview above Input bar
            AnimatedVisibility(
                visible = selectedImageUri != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .shadow(4.dp, RoundedCornerShape(14.dp)),
                    shape = RoundedCornerShape(14.dp),
                    color = FrostedGlassContainer,
                    border = BorderStroke(1.dp, GlassBorderBrush)
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "Selected image preview",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "File attached for multimodal vision",
                            fontSize = 13.sp,
                            color = DarkGray,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                selectedImageUri = null
                                selectedImageBase64 = null
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Remove image",
                                tint = MediumGray
                            )
                        }
                    }
                }
            }

            // Icy Glass Input Bar
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .shadow(8.dp, RoundedCornerShape(28.dp), spotColor = SkyBlue.copy(alpha = 0.2f)),
                shape = RoundedCornerShape(28.dp),
                color = FrostedGlassWhite,
                border = BorderStroke(1.2.dp, GlassBorderBrush)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Attachment button
                    IconButton(
                        onClick = { imagePickerLauncher.launch("image/*") },
                        modifier = Modifier
                            .size(42.dp)
                            .testTag("attach_image_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Attach image",
                            tint = SkyBlue,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    // Pill-shaped text field
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("chat_input_field"),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.chat_hint),
                                color = MediumGray.copy(alpha = 0.7f),
                                fontSize = 14.sp
                            )
                        },
                        maxLines = 4,
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = FrostedGlassContainer.copy(alpha = 0.7f),
                            unfocusedContainerColor = FrostedGlassContainer.copy(alpha = 0.4f),
                            focusedBorderColor = SkyBlue,
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = DarkGray,
                            unfocusedTextColor = DarkGray
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                if (inputText.isNotBlank() || selectedImageUri != null) {
                                    val textToSend = inputText
                                    val uriToSend = selectedImageUri
                                    val b64ToSend = selectedImageBase64
                                    inputText = ""
                                    selectedImageUri = null
                                    selectedImageBase64 = null
                                    viewModel.sendChatMessage(textToSend, uriToSend, b64ToSend)
                                }
                            }
                        )
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    // Circular Sky Blue Send Button
                    FloatingActionButton(
                        onClick = {
                            if (isStreaming) {
                                viewModel.stopChatStream()
                            } else if (inputText.isNotBlank() || selectedImageUri != null) {
                                val textToSend = inputText
                                val uriToSend = selectedImageUri
                                val b64ToSend = selectedImageBase64
                                inputText = ""
                                selectedImageUri = null
                                selectedImageBase64 = null
                                viewModel.sendChatMessage(textToSend, uriToSend, b64ToSend)
                            }
                        },
                        modifier = Modifier
                            .size(46.dp)
                            .shadow(6.dp, CircleShape, spotColor = SkyBlue.copy(alpha = 0.5f))
                            .testTag("send_button"),
                        shape = CircleShape,
                        containerColor = SkyBlue,
                        contentColor = PureWhite,
                        elevation = androidx.compose.material3.FloatingActionButtonDefaults.elevation(0.dp)
                    ) {
                        Icon(
                            imageVector = if (isStreaming) Icons.Default.Stop else Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send message",
                            tint = PureWhite,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubbleItem(msg: ChatMessage) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (msg.isUser) Alignment.End else Alignment.Start
    ) {
        val bubbleShape = if (msg.isUser) {
            RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp, bottomStart = 18.dp, bottomEnd = 4.dp)
        } else {
            RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp, bottomStart = 4.dp, bottomEnd = 18.dp)
        }

        Surface(
            shape = bubbleShape,
            color = if (msg.isUser) SkyBlue else FrostedGlassContainer,
            border = BorderStroke(
                1.dp,
                if (msg.isUser) Brush.linearGradient(listOf(Color.White.copy(alpha = 0.5f), Color.Transparent))
                else GlassBorderBrush
            ),
            modifier = Modifier
                .widthIn(max = 320.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = bubbleShape,
                    spotColor = if (msg.isUser) SkyBlue.copy(alpha = 0.3f) else LightPurple.copy(alpha = 0.2f)
                )
        ) {
            Column(
                modifier = Modifier
                    .background(if (msg.isUser) UserBubbleGradient else BotBubbleGradient)
                    .padding(12.dp)
            ) {
                // Attached image inside bubble if present
                if (msg.imageUri != null) {
                    AsyncImage(
                        model = msg.imageUri,
                        contentDescription = "Attached image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (msg.text.isNotEmpty()) {
                    SelectionContainer {
                        Text(
                            text = msg.text,
                            color = if (msg.isUser) PureWhite else DarkGray,
                            fontSize = 15.sp,
                            lineHeight = 22.sp
                        )
                    }
                } else if (msg.isStreaming) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = SkyBlue,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Thinking…",
                            color = MediumGray,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Bot action footer (Copy & Status)
                if (!msg.isUser && msg.text.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(msg.text))
                                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy text",
                                tint = MediumGray,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CodeWorkspaceScreen(viewModel: SalviaViewModel) {
    val messages = viewModel.codeMessages
    val isRunning by viewModel.isCodeRunning.collectAsState()
    val chatModel by viewModel.chatModel.collectAsState()
    var repoUrl by remember { mutableStateOf("") }
    var branch by remember { mutableStateOf("main") }
    var task by remember { mutableStateOf("") }
    var fileContext by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size, messages.lastOrNull()?.content) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.size - 1)
    }

    Scaffold(
        topBar = {
            Surface(color = FrostedGlassWhite, border = BorderStroke(1.dp, GlassBorderBrush)) {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Code Workspace", fontWeight = FontWeight.Bold, color = DarkGray, fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(shape = RoundedCornerShape(10.dp), color = IceCyan, border = BorderStroke(1.dp, GlassBorder)) {
                                Text("Codex-style", fontSize = 11.sp, color = SkyBlue, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                            }
                        }
                    },
                    actions = {
                        if (isRunning) {
                            IconButton(onClick = { viewModel.stopCodeTask() }) {
                                Icon(Icons.Default.Stop, contentDescription = "Stop code task", tint = SkyBlue)
                            }
                        }
                        IconButton(onClick = { viewModel.clearCodeWorkspace() }) {
                            Icon(Icons.Default.Delete, contentDescription = "Clear code workspace", tint = MediumGray)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent, titleContentColor = DarkGray)
                )
            }
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .imePadding()
        ) {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(22.dp),
                backgroundColor = FrostedGlassWhite
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(shape = CircleShape, color = IceCyan, modifier = Modifier.size(38.dp)) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.FolderOpen, contentDescription = null, tint = SkyBlue, modifier = Modifier.size(21.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Connect GitHub repository", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = DarkGray)
                            Text("Review, debug, plan patches and generate diffs with $chatModel", fontSize = 12.sp, color = MediumGray)
                        }
                    }
                    OutlinedTextField(value = repoUrl, onValueChange = { repoUrl = it }, label = { Text("GitHub repo URL") }, placeholder = { Text("https://github.com/owner/repo") }, singleLine = true, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(value = branch, onValueChange = { branch = it }, label = { Text("Branch/ref") }, singleLine = true, modifier = Modifier.weight(1f), shape = RoundedCornerShape(14.dp))
                        Button(
                            onClick = {
                                viewModel.runCodeWorkspaceTask(repoUrl, branch, "Audit this repository and propose a safe implementation plan.", fileContext)
                            },
                            enabled = !isRunning,
                            colors = ButtonDefaults.buttonColors(containerColor = SkyBlue),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.height(56.dp)
                        ) { Icon(Icons.Default.BugReport, contentDescription = null, tint = PureWhite) }
                    }
                    OutlinedTextField(value = task, onValueChange = { task = it }, label = { Text("Task for the coding agent") }, placeholder = { Text("Find bug, refactor, add feature, write tests, produce patch…") }, minLines = 3, maxLines = 5, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp))
                    OutlinedTextField(value = fileContext, onValueChange = { fileContext = it }, label = { Text("Optional pasted files / logs / stack traces") }, minLines = 3, maxLines = 8, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp))
                    Button(
                        onClick = { viewModel.runCodeWorkspaceTask(repoUrl, branch, task, fileContext) },
                        enabled = !isRunning && (repoUrl.isNotBlank() || task.isNotBlank() || fileContext.isNotBlank()),
                        modifier = Modifier.fillMaxWidth().height(52.dp).testTag("run_code_workspace_button"),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SkyBlue, contentColor = PureWhite, disabledContainerColor = SkyBlue.copy(alpha = 0.45f))
                    ) {
                        if (isRunning) {
                            CircularProgressIndicator(modifier = Modifier.size(18.dp), color = PureWhite, strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Running code agent…", fontWeight = FontWeight.Bold)
                        } else {
                            Icon(Icons.Default.Terminal, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Run Code Agent", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            if (messages.isEmpty()) {
                GlassCard(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), shape = RoundedCornerShape(20.dp), backgroundColor = FrostedGlassWhite) {
                    Column(modifier = Modifier.padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Code, contentDescription = null, tint = SkyBlue, modifier = Modifier.size(32.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Codex-like mobile engineering desk", color = DarkGray, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                        Text("Paste GitHub links, files, logs, errors or requirements. The selected provider model will review and return debugging notes, implementation steps and patch-style output.", color = MediumGray, fontSize = 12.sp, textAlign = TextAlign.Center)
                    }
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(messages, key = { it.id }) { item -> CodeWorkspaceBubble(item) }
                }
            }
        }
    }
}

@Composable
fun CodeWorkspaceBubble(item: CodeWorkspaceMessage) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val bubbleShape = if (item.isUser) RoundedCornerShape(18.dp, 18.dp, 18.dp, 4.dp) else RoundedCornerShape(18.dp, 18.dp, 4.dp, 18.dp)
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = if (item.isUser) Alignment.End else Alignment.Start) {
        Surface(
            shape = bubbleShape,
            color = if (item.isUser) SkyBlue else FrostedGlassContainer,
            border = BorderStroke(1.dp, if (item.isUser) Brush.linearGradient(listOf(Color.White.copy(alpha = 0.5f), Color.Transparent)) else GlassBorderBrush),
            modifier = Modifier.widthIn(max = 340.dp).shadow(4.dp, bubbleShape, spotColor = SkyBlueGlow)
        ) {
            Column(modifier = Modifier.background(if (item.isUser) UserBubbleGradient else BotBubbleGradient).padding(12.dp)) {
                Text(item.title, color = if (item.isUser) PureWhite else DarkGray, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                if (item.content.isNotBlank()) {
                    SelectionContainer {
                        Text(item.content, color = if (item.isUser) PureWhite else DarkGray, fontSize = 13.sp, lineHeight = 19.sp)
                    }
                } else if (item.isRunning) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), color = SkyBlue, strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Analyzing repository…", color = MediumGray, fontSize = 12.sp)
                    }
                }
                if (!item.isUser && item.content.isNotBlank()) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        IconButton(onClick = {
                            clipboardManager.setText(AnnotatedString(item.content))
                            Toast.makeText(context, "Code output copied", Toast.LENGTH_SHORT).show()
                        }, modifier = Modifier.size(28.dp)) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy code output", tint = MediumGray, modifier = Modifier.size(15.dp))
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// D. IMAGE GENERATION SCREEN (COGVIEW-3)
// ==========================================

@Composable
fun ImageScreen(viewModel: SalviaViewModel) {
    val isGenerating by viewModel.isImageGenerating.collectAsState()
    val errorMessage by viewModel.imageError.collectAsState()
    val latestImage by viewModel.latestImage.collectAsState()
    val history = viewModel.imageHistory

    var promptText by remember { mutableStateOf("") }
    val sizeOptions = listOf("1024x1024", "768x1344", "1344x768")
    var selectedSizeIndex by remember { mutableStateOf(0) }

    var selectedPreviewImage by remember { mutableStateOf<GeneratedImage?>(null) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Surface(
                color = FrostedGlassWhite,
                border = BorderStroke(1.dp, GlassBorderBrush)
            ) {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Image Studio",
                                fontWeight = FontWeight.Bold,
                                color = DarkGray,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = IceCyan,
                                border = BorderStroke(1.dp, GlassBorder)
                            ) {
                                Text(
                                    text = "Provider model",
                                    fontSize = 11.sp,
                                    color = SkyBlue,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = DarkGray
                    )
                )
            }
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Glass Prompt Card
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                backgroundColor = FrostedGlassWhite
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = stringResource(id = R.string.image_prompt_label),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkGray
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = promptText,
                        onValueChange = { promptText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("image_prompt_input"),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.image_prompt_placeholder),
                                color = MediumGray.copy(alpha = 0.6f),
                                fontSize = 14.sp
                            )
                        },
                        minLines = 3,
                        maxLines = 5,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = FrostedGlassContainer,
                            unfocusedContainerColor = FrostedGlassContainer.copy(alpha = 0.5f),
                            focusedBorderColor = SkyBlue,
                            unfocusedBorderColor = GlassBorderPurple,
                            focusedTextColor = DarkGray,
                            unfocusedTextColor = DarkGray
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Size Selection using Custom Glass Chips
                    Text(
                        text = "Aspect Ratio / Size",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkGray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        sizeOptions.forEachIndexed { index, size ->
                            val isSelected = selectedSizeIndex == index
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = if (isSelected) SkyBlue else FrostedGlassContainer,
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) SkyBlue else GlassBorderPurple
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedSizeIndex = index }
                                    .testTag("size_chip_$size")
                            ) {
                                Text(
                                    text = if (size == "1024x1024") "Square 1:1" else if (size == "768x1344") "Tall 9:16" else "Wide 16:9",
                                    color = if (isSelected) PureWhite else DarkGray,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 10.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Generate Button with glowing elevation
                    Button(
                        onClick = {
                            if (promptText.isNotBlank()) {
                                viewModel.generateImage(promptText, sizeOptions[selectedSizeIndex])
                            } else {
                                Toast.makeText(context, "Please enter a prompt first", Toast.LENGTH_SHORT).show()
                            }
                        },
                        enabled = !isGenerating,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .shadow(6.dp, RoundedCornerShape(16.dp), spotColor = SkyBlue.copy(alpha = 0.4f))
                            .testTag("generate_image_button"),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SkyBlue,
                            contentColor = PureWhite,
                            disabledContainerColor = SkyBlue.copy(alpha = 0.5f),
                            disabledContentColor = PureWhite.copy(alpha = 0.7f)
                        )
                    ) {
                        if (isGenerating) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = PureWhite,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Generating with selected model…", fontWeight = FontWeight.Bold)
                        } else {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(id = R.string.btn_generate),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Progress Indicator
            if (isGenerating) {
                Spacer(modifier = Modifier.height(16.dp))
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = SkyBlue,
                    trackColor = LightPurpleContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.generating_image),
                    fontSize = 13.sp,
                    color = SkyBlue,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Error Message Card
            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    backgroundColor = FrostedGlassContainer
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "⚠️ $errorMessage",
                            color = DarkGray,
                            fontSize = 13.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Latest Generated Image Result
            if (latestImage != null) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Generated Result",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGray
                )
                Spacer(modifier = Modifier.height(10.dp))

                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedPreviewImage = latestImage },
                    shape = RoundedCornerShape(20.dp),
                    backgroundColor = FrostedGlassWhite
                ) {
                    Column {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(latestImage!!.imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = latestImage!!.prompt,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(260.dp)
                                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = latestImage!!.prompt,
                                fontSize = 13.sp,
                                color = DarkGray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = {
                                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                        type = "text/plain"
                                        putExtra(Intent.EXTRA_TEXT, "${latestImage!!.prompt}\n${latestImage!!.imageUrl}")
                                    }
                                    context.startActivity(Intent.createChooser(shareIntent, "Share Image"))
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Share",
                                    tint = SkyBlue
                                )
                            }
                        }
                    }
                }
            }

            // Image History Gallery
            if (history.size > 1) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Previous Generations (${history.size})",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGray
                )
                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    history.drop(1).forEach { item ->
                        GlassCard(
                            shape = RoundedCornerShape(14.dp),
                            backgroundColor = FrostedGlassWhite,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedPreviewImage = item }
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = item.imageUrl,
                                    contentDescription = item.prompt,
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(RoundedCornerShape(10.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.prompt,
                                        fontSize = 13.sp,
                                        color = DarkGray,
                                        fontWeight = FontWeight.Medium,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = item.size,
                                        fontSize = 11.sp,
                                        color = MediumGray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Full screen image preview dialog
    if (selectedPreviewImage != null) {
        Dialog(onDismissRequest = { selectedPreviewImage = null }) {
            GlassCard(
                shape = RoundedCornerShape(24.dp),
                backgroundColor = PureWhite,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    AsyncImage(
                        model = selectedPreviewImage!!.imageUrl,
                        contentDescription = selectedPreviewImage!!.prompt,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(340.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = selectedPreviewImage!!.prompt,
                        fontSize = 14.sp,
                        color = DarkGray,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, "${selectedPreviewImage!!.prompt}\n${selectedPreviewImage!!.imageUrl}")
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share Image"))
                        }) {
                            Text("Share URL", color = SkyBlue)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { selectedPreviewImage = null },
                            colors = ButtonDefaults.buttonColors(containerColor = SkyBlue)
                        ) {
                            Text("Close", color = PureWhite)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProviderChip(profile: AiProviderProfile, selected: Boolean, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = if (selected) SkyBlue else FrostedGlassContainer,
        border = BorderStroke(1.dp, if (selected) SkyBlue else GlassBorderPurple),
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = profile.displayName,
            color = if (selected) PureWhite else DarkGray,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
        )
    }
}

// ==========================================
// E. SETTINGS SCREEN
// ==========================================

@Composable
fun SettingsScreen(viewModel: SalviaViewModel) {
    val apiKey by viewModel.apiKey.collectAsState()
    val providerId by viewModel.providerId.collectAsState()
    val baseUrl by viewModel.baseUrl.collectAsState()
    val chatModel by viewModel.chatModel.collectAsState()
    val visionModel by viewModel.visionModel.collectAsState()
    val imageModel by viewModel.imageModel.collectAsState()
    val providerStatus by viewModel.providerStatus.collectAsState()
    val models by viewModel.models.collectAsState()
    var isEditingKey by remember { mutableStateOf(false) }
    var keyInput by remember { mutableStateOf(apiKey) }
    var baseUrlInput by remember(baseUrl) { mutableStateOf(baseUrl) }
    var chatModelInput by remember(chatModel) { mutableStateOf(chatModel) }
    var visionModelInput by remember(visionModel) { mutableStateOf(visionModel) }
    var imageModelInput by remember(imageModel) { mutableStateOf(imageModel) }
    var showClearDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            Surface(
                color = FrostedGlassWhite,
                border = BorderStroke(1.dp, GlassBorderBrush)
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.settings_title),
                            fontWeight = FontWeight.Bold,
                            color = DarkGray,
                            fontSize = 18.sp
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = DarkGray
                    )
                )
            }
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // API Key Card
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                backgroundColor = FrostedGlassWhite
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = IceCyan,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Key,
                                    contentDescription = null,
                                    tint = SkyBlue,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = stringResource(id = R.string.settings_api_key),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkGray
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (!isEditingKey) {
                        Text(
                            text = if (apiKey.isNotBlank()) "Key: ••••••••••••••••${apiKey.takeLast(4)}" else "No API Key configured",
                            fontSize = 14.sp,
                            color = DarkGray
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = {
                                    keyInput = apiKey
                                    isEditingKey = true
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = SkyBlue)
                            ) {
                                Text("Edit Key", fontSize = 13.sp, color = PureWhite)
                            }

                            Button(
                                onClick = { showClearDialog = true },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = FrostedGlassContainer, contentColor = DarkGray),
                                border = BorderStroke(1.dp, GlassBorderPurple)
                            ) {
                                Text(stringResource(id = R.string.btn_clear), fontSize = 13.sp, color = DarkGray)
                            }
                        }
                    } else {
                        OutlinedTextField(
                            value = keyInput,
                            onValueChange = { keyInput = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Enter provider API Key", color = MediumGray) },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = FrostedGlassContainer,
                                unfocusedContainerColor = FrostedGlassContainer.copy(alpha = 0.5f),
                                focusedBorderColor = SkyBlue,
                                unfocusedBorderColor = GlassBorderPurple,
                                focusedTextColor = DarkGray,
                                unfocusedTextColor = DarkGray
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = {
                                    viewModel.setApiKey(keyInput)
                                    isEditingKey = false
                                    Toast.makeText(context, "API Key updated", Toast.LENGTH_SHORT).show()
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = SkyBlue)
                            ) {
                                Text(stringResource(id = R.string.btn_save), color = PureWhite)
                            }

                            TextButton(onClick = { isEditingKey = false }) {
                                Text("Cancel", color = MediumGray)
                            }
                        }
                    }
                }
            }


            // Universal Provider Hub
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                backgroundColor = FrostedGlassWhite
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(shape = CircleShape, color = IceCyan, modifier = Modifier.size(34.dp)) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(imageVector = Icons.Default.CloudSync, contentDescription = null, tint = SkyBlue, modifier = Modifier.size(20.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Universal Provider Hub", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = DarkGray)
                            Text("OpenRouter, Groq, NVIDIA, Google, Z.ai or custom OpenAI-compatible API", fontSize = 12.sp, color = MediumGray)
                        }
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        SupportedProviderProfiles.take(3).forEach { profile ->
                            ProviderChip(profile, providerId == profile.id) { viewModel.applyProvider(profile) }
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        SupportedProviderProfiles.drop(3).forEach { profile ->
                            ProviderChip(profile, providerId == profile.id) { viewModel.applyProvider(profile) }
                        }
                    }

                    OutlinedTextField(value = baseUrlInput, onValueChange = { baseUrlInput = it }, label = { Text("Base URL") }, singleLine = true, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                    OutlinedTextField(value = chatModelInput, onValueChange = { chatModelInput = it }, label = { Text("Chat / agent model") }, singleLine = true, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                    OutlinedTextField(value = visionModelInput, onValueChange = { visionModelInput = it }, label = { Text("Vision / file model") }, singleLine = true, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                    OutlinedTextField(value = imageModelInput, onValueChange = { imageModelInput = it }, label = { Text("Image model") }, singleLine = true, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { viewModel.saveProviderSettings(baseUrlInput, chatModelInput, visionModelInput, imageModelInput) }, colors = ButtonDefaults.buttonColors(containerColor = SkyBlue), shape = RoundedCornerShape(12.dp)) { Text("Save Provider", color = PureWhite) }
                        Button(onClick = { viewModel.syncProviderMetadata() }, colors = ButtonDefaults.buttonColors(containerColor = FrostedGlassContainer, contentColor = DarkGray), border = BorderStroke(1.dp, GlassBorderPurple), shape = RoundedCornerShape(12.dp)) { Text("Sync Models", color = DarkGray) }
                    }

                    Text(providerStatus, fontSize = 12.sp, color = MediumGray)
                    if (models.isNotEmpty()) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            models.take(8).forEach { model ->
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(model.id, fontSize = 12.sp, color = DarkGray, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
                                    Text(if (model.isFree) "Free" else model.priceLabel, fontSize = 11.sp, color = if (model.isFree) SkyBlue else MediumGray)
                                }
                            }
                            if (models.size > 8) Text("+${models.size - 8} more models", fontSize = 11.sp, color = MediumGray)
                        }
                    }

                    HorizontalDivider(color = LightPurple.copy(alpha = 0.3f))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Code, contentDescription = null, tint = SkyBlue, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Agent-ready: streaming, vision files, tool/function-calling payloads and code-workspace prompts are preserved when the selected API supports them.", fontSize = 12.sp, color = MediumGray)
                    }
                }
            }

            // Models Card
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                backgroundColor = FrostedGlassWhite
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = stringResource(id = R.string.settings_models),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkGray
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = IceCyan,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(imageVector = Icons.Default.ChatBubble, contentDescription = null, tint = SkyBlue, modifier = Modifier.size(20.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = "GLM-4.6 / GLM-4v", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = DarkGray)
                            Text(text = "Chat & Multimodal Vision Streaming", fontSize = 12.sp, color = MediumGray)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = LightPurple.copy(alpha = 0.3f))
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = IceCyan,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = SkyBlue, modifier = Modifier.size(20.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = "CogView-3", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = DarkGray)
                            Text(text = "High-Quality Text-to-Image Generation", fontSize = 12.sp, color = MediumGray)
                        }
                    }
                }
            }

            // About App Card
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                backgroundColor = FrostedGlassWhite
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = stringResource(id = R.string.settings_about),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkGray
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "SalviaAI v1.0.0", fontSize = 13.sp, color = DarkGray, fontWeight = FontWeight.SemiBold)
                    Text(text = "Universal AI workspace for OpenAI-compatible providers, model discovery and multimodal agents.", fontSize = 12.sp, color = MediumGray)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "Violet crystal UI with fast Jetpack Compose & Material 3.", fontSize = 12.sp, color = MediumGray)
                }
            }
        }
    }

    // Confirmation dialog for clearing API key
    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = { Text("Clear API Key?", fontWeight = FontWeight.Bold, color = DarkGray) },
            text = { Text("You will be returned to the Welcome screen to enter a new key.", color = MediumGray) },
            confirmButton = {
                Button(
                    onClick = {
                        showClearDialog = false
                        viewModel.clearApiKey()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SkyBlue)
                ) {
                    Text("Clear", color = PureWhite)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) {
                    Text("Cancel", color = MediumGray)
                }
            },
            containerColor = PureWhite
        )
    }
}

// ==========================================
// UTILITIES: IMAGE TO BASE64
// ==========================================

fun encodeImageUriToBase64(context: Context, imageUri: Uri): String? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        if (bitmap == null) return null

        // Downscale image if too large (max 1024px on largest side)
        val maxDimension = 1024
        val ratio = (bitmap.width.toFloat() / bitmap.height.toFloat())
        val scaledBitmap = if (bitmap.width > maxDimension || bitmap.height > maxDimension) {
            val targetWidth: Int
            val targetHeight: Int
            if (bitmap.width > bitmap.height) {
                targetWidth = maxDimension
                targetHeight = (maxDimension / ratio).toInt()
            } else {
                targetHeight = maxDimension
                targetWidth = (maxDimension * ratio).toInt()
            }
            Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)
        } else {
            bitmap
        }

        val outputStream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream)
        val byteArray = outputStream.toByteArray()
        Base64.encodeToString(byteArray, Base64.NO_WRAP)
    } catch (e: Exception) {
        null
    }
}
