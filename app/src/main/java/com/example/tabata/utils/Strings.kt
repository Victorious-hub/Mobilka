package com.example.tabata.utils

data class LocalizedStrings(
    val yourSequences: String,
    val createNew: String,
    val loading: String,
    val updateSequence: String,
    val deleteSequence: String,
    val startTimer: String,
    val back: String,
    val createNewSequence: String,
    val warmUpDuration: String,
    val workoutDuration: String,
    val restDuration: String,
    val cooldownDuration: String,
    val rounds: String,
    val restBetweenSets: String,
    val createSequence: String,
    val home: String,            // Added for BottomBar
    val workout: String,         // Added for BottomBar
    val settings: String,        // Added for BottomBar
    val step: String,
    val round: String,
    val application: String,
    val darkTheme: String,
    val fontSize: String,
    val language: String,
    val clearAllData: String,
    val title: String,           // Added for "Title"
    val color: String,           // Added for "Color"
    val chooseColor: String,
    val save: String,
    val cancel: String
)

fun getStrings(locale: java.util.Locale): LocalizedStrings {
    return when (locale.language) {
        "ru" -> LocalizedStrings(
            yourSequences = "Ваши последовательности",
            createNew = "Создать новую",
            loading = "Загрузка...",
            updateSequence = "Обновить последовательность",
            deleteSequence = "Удалить последовательность",
            startTimer = "Запустить таймер",
            back = "Назад",
            createNewSequence = "Создать новую последовательность",
            warmUpDuration = "Продолжительность разогрева",
            workoutDuration = "Продолжительность тренировки",
            restDuration = "Продолжительность отдыха",
            cooldownDuration = "Продолжительность заминки",
            rounds = "Раунды",
            restBetweenSets = "Отдых между сетами",
            createSequence = "Создать последовательность",
            home = "Главная",          // Russian string for Home in BottomBar
            workout = "Тренировка",    // Russian string for Workout in BottomBar
            settings = "Настройки",    // Russian string for Settings in BottomBar
            step = "Шаг",              // Russian string for Step
            round = "Раунд",
            application = "Приложение",
            darkTheme = "Темная тема",
            fontSize = "Размер шрифта",
            language = "Язык",
            clearAllData = "Очистить все данные",
            title = "Заголовок",        // Russian translation for "Title"
            color = "Цвет",             // Russian translation for "Color"
            chooseColor = "Выбрать цвет",
            save = "Сохранить",
            cancel = "Отменить"
        )
        "en" -> LocalizedStrings(
            yourSequences = "Your Sequences",
            createNew = "Create New",
            loading = "Loading...",
            updateSequence = "Update Sequence",
            deleteSequence = "Delete Sequence",
            startTimer = "Start Timer",
            back = "Back",
            createNewSequence = "Create New Sequence",
            warmUpDuration = "Warm-up Duration",
            workoutDuration = "Workout Duration",
            restDuration = "Rest Duration",
            cooldownDuration = "Cooldown Duration",
            rounds = "Rounds",
            restBetweenSets = "Rest Between Sets",
            createSequence = "Create Sequence",
            home = "Home",             // English string for Home in BottomBar
            workout = "Workout",       // English string for Workout in BottomBar
            settings = "Settings",
            step = "Step",             // English string for Step
            round = "Round",
            application = "Application",
            darkTheme = "Dark Theme",
            fontSize = "Font Size",
            language = "Language",
            clearAllData = "Clear All Data",
            title = "Title",           // English translation for "Title"
            color = "Color",           // English translation for "Color"
            chooseColor = "Choose Color",
            save = "Save",
            cancel = "Cancel"
        )
        else -> LocalizedStrings(
            yourSequences = "Your Sequences",
            createNew = "Create New",
            loading = "Loading...",
            updateSequence = "Update Sequence",
            deleteSequence = "Delete Sequence",
            startTimer = "Start Timer",
            back = "Back",
            createNewSequence = "Create New Sequence",
            warmUpDuration = "Warm-up Duration",
            workoutDuration = "Workout Duration",
            restDuration = "Rest Duration",
            cooldownDuration = "Cooldown Duration",
            rounds = "Rounds",
            restBetweenSets = "Rest Between Sets",
            createSequence = "Create Sequence",
            home = "Home",             // Default fallback for Home in BottomBar
            workout = "Workout",       // Default fallback for Workout in BottomBar
            settings = "Settings",
            step = "Step",             // Default fallback for Step
            round = "Round",
            application = "Application",
            darkTheme = "Dark Theme",
            fontSize = "Font Size",
            language = "Language",
            clearAllData = "Clear All Data",
            title = "Title",           // English translation for "Title"
            color = "Color",           // English translation for "Color"
            chooseColor = "Choose Color",
            save = "Save",
            cancel = "Cancel"
        )
    }
}

