import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.univpulse.R
import com.example.univpulse.ui.theme.Primary
import com.example.univpulse.ui.theme.SubTitle
import com.example.univpulse.ui.theme.SubTitle2

data class NotificationData(
    val userImage: Painter,
    val userName: String,
    val actionText: String,
    val timeAgo: String,
    val isUnread: Boolean
)

@Composable
fun Notifications(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val currentLang = LanguagesPreferences.getSavedLanguageCode(context)
    val setFontBold = if (currentLang == "ar") R.font.cairo_bold else R.font.poppins_bold
    val setFontRegular = if (currentLang == "ar") R.font.cairo_bold else R.font.poppins_bold
    var expended by remember { mutableStateOf(false) }
    val isExpanded  = if(expended) Color.Unspecified else SubTitle

    val todayNotifications = List(5) {
        NotificationData(
            userImage = painterResource(R.drawable.pic),
            userName = stringResource(R.string.teacher),
            actionText = stringResource(R.string.new_ntfs),
            timeAgo = stringResource(R.string.time),
            isUnread = true
        )
    }

    val earlierNotifications = List(5) {
        NotificationData(
            userImage = painterResource(R.drawable.pic),
            userName = stringResource(R.string.teacher),
            actionText = stringResource(R.string.new_ntfs),
            timeAgo = stringResource(R.string.time),
            isUnread = false
        )
    }

    val sections = listOf(
        stringResource(R.string.today) to todayNotifications,
        stringResource(R.string.earlier) to earlierNotifications
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.notifications_center),
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(setFontBold)),
                    color = Color.Black,
                )

                Icon(
                    painter = painterResource(R.drawable.check_icon),
                    contentDescription = "Check",
                    tint = isExpanded,
                    modifier = Modifier.clickable{
                        expended = !expended

                    }
                )

            }
        }

        sections.forEach { (title, notifications) ->
            item {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(setFontBold)),
                    color = Color.Black,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }

            items(notifications) { notification ->
                NotificationCard(notification = notification)
            }
        }
    }
}

@Composable
fun NotificationCard(notification: NotificationData) {
    val context = LocalContext.current
    val currentLang = LanguagesPreferences.getSavedLanguageCode(context)
    val setFontBold = if (currentLang == "ar") R.font.cairo_bold else R.font.poppins_bold
    val setFontRegular = if (currentLang == "ar") R.font.cairo_regular else R.font.poppins_regular

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (notification.isUnread) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(Color.Red, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
        } else {
            Spacer(modifier = Modifier.width(18.dp))
        }

        // User Image
        Image(
            painter = notification.userImage,
            contentDescription = "User Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(55.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = if (notification.isUnread) Color.Black else SubTitle,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(setFontBold))
                        )
                    ) {
                        append(notification.userName)
                    }
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            color = if (notification.isUnread) Color.Black else SubTitle2,
                            fontFamily = FontFamily(Font(setFontRegular))
                        )
                    ) {
                        append(notification.actionText)
                    }
                },
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = notification.timeAgo,
                fontFamily = FontFamily(Font(setFontRegular)),
                color = SubTitle2,
                fontSize = 12.sp
            )
        }
    }
}
