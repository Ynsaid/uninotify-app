import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.univpulse.R
import com.example.univpulse.ui.theme.GreenTitle
import com.example.univpulse.ui.theme.Primary
import com.example.univpulse.ui.theme.SubTitle
import com.example.univpulse.ui.theme.SubTitle2
import com.google.firebase.auth.FirebaseAuth


@Composable
fun Settings(navController: NavHostController, rootNavController: NavController) {


    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    val arabic = stringResource(R.string.arabic)
    val currentLang = LanguagesPreferences.getSavedLanguageCode(context)
    var setFontSemiBold = if(currentLang == "ar") R.font.cairo_semibold else R.font.cairo_semibold
    var setFontBold = if(currentLang == "ar") R.font.cairo_bold else R.font.poppins_bold
    var setFontMedium = if(currentLang == "ar") R.font.cairo_medium else R.font.poppins_medium

    var selectedLanguage by remember {
        mutableStateOf(
            LanguagesPreferences.getSavedLanguageName(context)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    ) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(138.dp)
                .background(Primary)
                .padding(top =8.dp)
        ) {
            Text(
                text = stringResource(R.string.settings),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(setFontBold)),
                color = Color.White,
                modifier = Modifier.padding(start = 16.dp, top = 32.dp)
            )
        }


        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, start = 16.dp)
                .offset(y = (-50).dp),
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 4.dp,
            color = Color.White
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.teacher_icon),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.myname),
                        fontFamily = FontFamily(Font(setFontBold)),
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = stringResource(R.string.major),
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontFamily = FontFamily(Font(setFontMedium))
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.eye_icon),
                    contentDescription = "View",
                    tint = Color.Gray
                )
            }
        }



        SettingsSection(title = stringResource(R.string.prefences)) {
            SwitchItem(title = stringResource(R.string.dark_mode))
            SwitchItem(title = stringResource(R.string.notifications))

            LanguageItem(
                title = stringResource(R.string.languages),
                initialLanguage = selectedLanguage,
                onLanguageSelected = { lang ->

                    val langCode = when (lang) {
                        "English" -> "en"
                        "French" -> "fr"
                        arabic -> "ar"
                        "Spanish" -> "es"
                        else -> "en"
                    }

                    LanguagesPreferences.saveLanguage(context, langCode)
                    LanguagesPreferences.setLocale(context, langCode)
                    LanguagesPreferences.restartApp(context)
                    (context as? ComponentActivity)?.recreate()
                    selectedLanguage = lang
                }
            )
        }


        SettingsSection(title = stringResource(R.string.privacy_and_security)) {
            ArrowItem(title = stringResource(R.string.privacy))
            ArrowItem(title = stringResource(R.string.bug_report))
            ArrowItem(title = stringResource(R.string.app_feedback))
        }


        LogoutSection(navController = navController)
    }
}

@Composable
fun SettingsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    val context = LocalContext.current
    val currentLang = LanguagesPreferences.getSavedLanguageCode(context)
    var setFont = if(currentLang == "ar") R.font.cairo_semibold else R.font.poppins_medium
    var setFontBold = if(currentLang == "ar") R.font.cairo_bold else R.font.poppins_bold
    var setFontMedium = if(currentLang == "ar") R.font.cairo_bold else R.font.poppins_medium
    var topPadding = if(title == "Privacy and Security" || title == "الخصوصية والامان") 16.dp else 0.dp
    Text(
        text = title,
        fontSize = 16.sp,
        fontFamily = FontFamily(Font(setFontBold)),
        color = Color.Gray,
        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp ,top = topPadding)
    )
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 2.dp,
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .fillMaxHeight()
                    .background(
                        Primary,
                        RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                    )
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
fun SwitchItem(title: String) {
    var checked by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val currentLang = LanguagesPreferences.getSavedLanguageCode(context)
    var setFontMedium = if(currentLang == "ar") R.font.cairo_medium else R.font.poppins_medium
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f),
            color = Color.Gray,
            fontFamily = FontFamily(Font(setFontMedium))
        )
        Switch(
            checked = checked,
            onCheckedChange = { checked = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                uncheckedThumbColor = Color.White,
                checkedTrackColor = GreenTitle,
                uncheckedTrackColor = SubTitle2,
                uncheckedBorderColor = SubTitle2,
            )
        )
    }
}

@Composable
fun ArrowItem(title: String) {
    val context = LocalContext.current
    val currentLang = LanguagesPreferences.getSavedLanguageCode(context)
    var setFontMedium = if(currentLang == "ar") R.font.cairo_medium else R.font.poppins_medium
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f),
            color = Color.Gray,
            fontFamily = FontFamily(Font(setFontMedium))
        )
        Icon(
            painter = painterResource(R.drawable.arrow_right_icon),
            contentDescription = "Arrow",
            tint = Color.Gray
        )
    }
}

@Composable
fun LanguageItem(
    title: String,
    onLanguageSelected: (String) -> Unit,
    initialLanguage: String = "English"
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf(initialLanguage) }
    val context = LocalContext.current
    val languages = listOf("English", "Arabic")
    val setLang = if (selectedLanguage == "Arabic") {
        context.getString(R.string.arabic)
    } else {
        context.getString(R.string.english)
    }

    val currentLang = LanguagesPreferences.getSavedLanguageCode(context)
    var setFontSemiBold = if(currentLang == "ar") R.font.cairo_semibold else R.font.poppins_bold
    var setFontMedium = if(currentLang == "ar") R.font.cairo_medium else R.font.poppins_medium
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { showDialog = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f),
            color = Color.Gray,
            fontFamily = FontFamily(Font(setFontMedium))
        )
        Text(
            text = if(currentLang == "ar") "العربية" else "English",
            fontSize = 14.sp,
            color = SubTitle2,
            modifier = Modifier.padding(end = 8.dp),
            fontFamily = FontFamily(Font(if(currentLang == "ar") R.font.cairo_semibold else R.font.poppins_semibold))
        )
        Icon(
            painter = painterResource(id = R.drawable.arrow_down_icon),
            contentDescription = "Dropdown",
            tint = Color.Gray
        )
    }
    // dialog to change language
    if (showDialog) {
//        val isArabic = selectedLanguage == "العربية"
//        CompositionLocalProvider(
//            LocalLayoutDirection provides if (isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr
//        ) {
//        }
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    stringResource(R.string.select_language),
                    fontFamily = FontFamily(Font(R.font.poppins_bold))
                )
            },
            text = {
                Column {
                    languages.forEach { language ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedLanguage = language }
                                .padding(4.dp)
                        ) {
                            RadioButton(
                                selected = selectedLanguage == language,
                                onClick = { selectedLanguage = language },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Primary,
                                    unselectedColor = SubTitle2
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = language,
                                fontFamily = FontFamily(Font(setFontSemiBold))
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onLanguageSelected(selectedLanguage)
                        showDialog = false
                    }
                ) { Text(stringResource(R.string.confirm), color = Primary) }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.cancel), color = SubTitle2)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(8.dp),
        )
    }
}


@Composable
fun LogoutDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    val context = LocalContext.current
    val currentLang = LanguagesPreferences.getSavedLanguageCode(context)
    var setFontSemiBold = if(currentLang == "ar") R.font.cairo_semibold else R.font.poppins_bold
    var setFontBold = if(currentLang == "ar") R.font.cairo_bold else R.font.poppins_medium
    AlertDialog(
        containerColor = Color.White,
        icon = { Icon(icon, contentDescription = "Icon") },
        title = { Text(text = dialogTitle , fontFamily = FontFamily(Font(setFontBold))) },
        text = { Text(text = dialogText) },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(stringResource(R.string.confirm), color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(stringResource(R.string.dismiss), color = SubTitle2)
            }
        }
    )
}

@Composable
fun LogoutSection(navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    val arabic = stringResource(R.string.arabic)
    val context = LocalContext.current
    val currentLang = LanguagesPreferences.getSavedLanguageCode(context)
    var setFontSemiBold = if(currentLang == "ar") R.font.cairo_semibold else R.font.cairo_semibold
    var setFontBold = if(currentLang == "ar") R.font.cairo_bold else R.font.poppins_bold
    var setFontMedium = if(currentLang == "ar") R.font.cairo_bold else R.font.poppins_medium
    if (showDialog) {
        LogoutDialog(
            onDismissRequest = { showDialog = false },
            onConfirmation = {
                showDialog = false
                FirebaseAuth.getInstance().signOut()
                navController.navigate("Login") {
                    popUpTo("Main") { inclusive = true }
                }
            },
            dialogTitle = stringResource(R.string.loggingout),
            dialogText = stringResource(R.string.confirm_logout),
            icon = Icons.Default.ExitToApp
        )
    }

    Card(
        onClick = { showDialog = true },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min), // make Box fill the height of the Row
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .width(8.dp)
                    .fillMaxHeight()
                    .background(
                        Color.Red,
                        RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                    )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Row(
                modifier = Modifier
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.log_out),
                    color = SubTitle,
                    fontFamily = FontFamily(Font(setFontBold)),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Logout",
                    tint = SubTitle
                )
            }
        }
    }
}
