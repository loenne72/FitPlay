package com.example.fitplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RunCircle
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.fitplay.ui.theme.FitPlayTheme
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitPlayTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    LauncherScreen()
                }
            }
        }
    }
}

private val defaultShortcuts = listOf(
    AppShortcut(
        title = "Läufe",
        subtitle = "Letzter: 5 km in 27:41",
        accent = Color(0xFF5AD7FF),
        icon = Icons.Filled.RunCircle
    ),
    AppShortcut(
        title = "Training",
        subtitle = "Nächstes: Kraft am Abend",
        accent = Color(0xFF7C88FF),
        icon = Icons.Filled.FitnessCenter
    ),
    AppShortcut(
        title = "Musik",
        subtitle = "Play – Focus Mix",
        accent = Color(0xFFFFB86C),
        icon = Icons.Filled.Headphones
    ),
    AppShortcut(
        title = "Coach",
        subtitle = "Neuer Tipp verfügbar",
        accent = Color(0xFF4CD964),
        icon = Icons.Filled.TipsAndUpdates
    ),
    AppShortcut(
        title = "Timer",
        subtitle = "Intervall: 45 Sekunden",
        accent = Color(0xFFFF7676),
        icon = Icons.Filled.Alarm
    ),
    AppShortcut(
        title = "Playlists",
        subtitle = "Sprint Boost",
        accent = Color(0xFF9CE0FF),
        icon = Icons.Filled.PlayArrow
    )
)

@Composable
fun LauncherScreen(
    favorites: List<AppShortcut> = defaultShortcuts,
    modifier: Modifier = Modifier
) {
    var clock by remember { mutableStateOf(LocalTime.now()) }
    var date by remember { mutableStateOf(LocalDate.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            clock = LocalTime.now()
            date = LocalDate.now()
            delay(1_000L)
        }
    }

    val timeText = remember(clock) {
        clock.format(DateTimeFormatter.ofPattern("HH:mm"))
    }
    val dateText = remember(date) {
        date.format(DateTimeFormatter.ofPattern("EEEE, dd. MMMM"))
    }
    val greeting = remember(clock) { greetingForHour(clock.hour) }

    Scaffold(
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0)
    ) { padding ->
        GlassyBackground(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                GlassPanel {
                    TimeHeader(greeting = greeting, timeText = timeText, dateText = dateText)
                }
                QuickActionsRow()
                GlassPanel(modifier = Modifier.weight(1f)) {
                    FavoriteAppsGrid(
                        favorites = favorites,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                FooterHint()
            }
        }
    }
}

@Composable
private fun GlassyBackground(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0B1021),
                        Color(0xFF101E35),
                        Color(0xFF162B4A)
                    ),
                    start = androidx.compose.ui.geometry.Offset.Zero,
                    end = androidx.compose.ui.geometry.Offset(1200f, 1600f)
                )
            )
            .drawBehind {
                val radius = size.minDimension / 3f
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(Color(0xFF5E81F4).copy(alpha = 0.6f), Color.Transparent)
                    ),
                    radius = radius,
                    center = androidx.compose.ui.geometry.Offset(size.width * 0.25f, size.height * 0.3f)
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(Color(0xFF4CD964).copy(alpha = 0.55f), Color.Transparent)
                    ),
                    radius = radius,
                    center = androidx.compose.ui.geometry.Offset(size.width * 0.75f, size.height * 0.25f)
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(Color(0xFFFFB86C).copy(alpha = 0.5f), Color.Transparent)
                    ),
                    radius = radius,
                    center = androidx.compose.ui.geometry.Offset(size.width * 0.55f, size.height * 0.8f)
                )
            }
    ) {
        BlurredGlow(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = (-60).dp, y = 20.dp)
                .size(220.dp),
            colors = listOf(Color.White.copy(alpha = 0.24f), Color.Transparent)
        )
        BlurredGlow(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 60.dp, y = 40.dp)
                .size(260.dp),
            colors = listOf(Color(0xFF7C88FF).copy(alpha = 0.4f), Color.Transparent)
        )
        content()
    }
}

@Composable
private fun BlurredGlow(modifier: Modifier = Modifier, colors: List<Color>) {
    Box(
        modifier = modifier
            .background(Brush.radialGradient(colors))
            .graphicsLayer {
                renderEffect = RenderEffect.createBlurEffect(
                    60f,
                    60f,
                    TileMode.CLAMP
                )
                alpha = 0.85f
            }
    )
}

@Composable
private fun GlassPanel(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp).copy(alpha = 0.28f),
        shape = RoundedCornerShape(26.dp),
        tonalElevation = 0.dp
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        listOf(
                            Color.White.copy(alpha = 0.28f),
                            Color.White.copy(alpha = 0.08f)
                        )
                    ),
                    shape = RoundedCornerShape(26.dp)
                )
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color.White.copy(alpha = 0.06f),
                            Color.White.copy(alpha = 0.03f)
                        )
                    )
                )
                .padding(18.dp)
        ) {
            content()
        }
    }
}

@Composable
private fun TimeHeader(greeting: String, timeText: String, dateText: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = greeting,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = timeText,
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = dateText,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun QuickActionsRow() {
    val quickActions = listOf(
        QuickAction(
            title = "Workout starten",
            subtitle = "Sofort loslegen",
            color = Brush.linearGradient(
                listOf(
                    Color.White.copy(alpha = 0.18f),
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                )
            ),
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        QuickAction(
            title = "Fokus",
            subtitle = "Benachrichtigungen still",
            color = Brush.linearGradient(
                listOf(
                    Color.White.copy(alpha = 0.18f),
                    MaterialTheme.colorScheme.tertiary.copy(alpha = 0.12f)
                )
            ),
            contentColor = MaterialTheme.colorScheme.onTertiary
        ),
        QuickAction(
            title = "Pause",
            subtitle = "Timer auf 5 Min",
            color = Brush.linearGradient(
                listOf(
                    Color.White.copy(alpha = 0.18f),
                    MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f)
                )
            ),
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        quickActions.forEach { action ->
            QuickActionCard(action, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun QuickActionCard(action: QuickAction, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .background(action.color, RoundedCornerShape(20.dp))
                .border(
                    1.dp,
                    Color.White.copy(alpha = 0.12f),
                    RoundedCornerShape(20.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = action.title,
                style = MaterialTheme.typography.titleMedium,
                color = action.contentColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = action.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = action.contentColor.copy(alpha = 0.9f)
            )
        }
    }
}

@Composable
private fun FavoriteAppsGrid(favorites: List<AppShortcut>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        columns = GridCells.Adaptive(minSize = 150.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 12.dp)
    ) {
        items(favorites) { shortcut ->
            FavoriteAppCard(shortcut)
        }
    }
}

@Composable
private fun FavoriteAppCard(shortcut: AppShortcut) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(22.dp)
    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color.White.copy(alpha = 0.12f),
                            Color.White.copy(alpha = 0.04f)
                        )
                    ),
                    RoundedCornerShape(22.dp)
                )
                .border(
                    1.dp,
                    Brush.linearGradient(
                        listOf(
                            Color.White.copy(alpha = 0.16f),
                            Color.White.copy(alpha = 0.06f)
                        )
                    ),
                    RoundedCornerShape(22.dp)
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconBadge(shortcut)
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = shortcut.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = shortcut.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun IconBadge(shortcut: AppShortcut) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
        BoxedIcon(accent = shortcut.accent) {
            Icon(
                imageVector = shortcut.icon,
                contentDescription = shortcut.title,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = "Schnellstart",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Bereit",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun BoxedIcon(
    accent: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .size(48.dp)
            .clip(RoundedCornerShape(14.dp)),
        color = accent
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}

@Composable
private fun FooterHint() {
    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )
        Text(
            text = "Lange drücken, um Apps zu verschieben oder zu verbergen.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private fun greetingForHour(hour: Int): String = when (hour) {
    in 5..11 -> "Guten Morgen"
    in 12..16 -> "Guten Tag"
    in 17..22 -> "Guten Abend"
    else -> "Willkommen zurück"
}

data class AppShortcut(
    val title: String,
    val subtitle: String,
    val accent: Color,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

data class QuickAction(
    val title: String,
    val subtitle: String,
    val color: Brush,
    val contentColor: Color
)
