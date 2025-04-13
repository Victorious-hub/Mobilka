package com.example.tabata.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.*
import com.example.tabata.presentation.theme.*
import java.util.*

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

val LocalFontScale = compositionLocalOf { 1.0f }
val LocalAppLocale = compositionLocalOf { Locale.ENGLISH }

@Composable
fun TabataTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    fontScale: Float = 1.0f,
    locale: Locale = Locale.ENGLISH,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    // Определение цветовой схемы в зависимости от текущей темы
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Обновление конфигурации локали
    SideEffect {
        val resources = context.resources
        val config = resources.configuration
        if (config.locales.get(0) != locale) {
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)
        }
    }

    // Обеспечиваем доступ к локальному масштабу шрифта и локали
    CompositionLocalProvider(
        LocalFontScale provides fontScale,
        LocalAppLocale provides locale
    ) {
        // Обновляем MaterialTheme с учетом цветовой схемы и масштаба шрифта
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography.copy(
                bodyLarge = Typography.bodyLarge.copy(fontSize = Typography.bodyLarge.fontSize * fontScale),
                bodyMedium = Typography.bodyMedium.copy(fontSize = Typography.bodyMedium.fontSize * fontScale),
                bodySmall = Typography.bodySmall.copy(fontSize = Typography.bodySmall.fontSize * fontScale),
            ),
            content = content
        )
    }
}

