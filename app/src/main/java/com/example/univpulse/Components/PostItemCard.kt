import android.view.Gravity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.univpulse.R
import com.example.univpulse.ui.theme.Primary
import com.example.univpulse.ui.theme.SubTitle2

@Composable
fun PostItemCard(
    modifier: Modifier = Modifier,
    color : Color
) {
    val context = LocalContext.current
    val currentLang = LanguagesPreferences.getSavedLanguageCode(context)
    val setFontMedium =
        if (currentLang == "ar") R.font.cairo_medium else R.font.poppins_medium
    val setFontRegular =
        if (currentLang == "ar") R.font.cairo_semibold else R.font.poppins_semibold
    val setFontBold =
        if (currentLang == "ar") R.font.cairo_bold else R.font.poppins_bold


    // add new reaction
    var cpt by remember { mutableStateOf(24) }
    var clickableBtn by remember { mutableStateOf(false) }
    var clickableNBtn by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(0.1f))
    ) {
        // column of content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                    Image(
                        painter = painterResource(id = R.drawable.admin_icon),
                        contentDescription = "Admin",
                        modifier = Modifier
                            .size(40.dp)
                    )


                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = stringResource(R.string.title),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(setFontBold)),
                        color = Color.Black
                    )
                    Text(
                        text =stringResource(R.string.date),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(setFontMedium)),
                        color = SubTitle2
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = stringResource(R.string.post_content),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(setFontRegular)),
                color = Color.DarkGray
            )
        }



    }
    // Row of buttons + reactions
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, start=12.dp,end =12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left: row of buttons
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.eye_icon),
                contentDescription = "Eye",
                modifier = Modifier.size(28.dp)
                    .clickable {
                        clickableBtn = !clickableBtn
                        if(clickableBtn) cpt++ else cpt--
                    }
                ,
                tint = if(clickableBtn) Primary else Color.Unspecified
            )
            Icon(
                painter = painterResource(id = R.drawable.filled_notification_icon),
                contentDescription = "Notifications",
                modifier = Modifier.size(28.dp)
                    .clickable {
                        val toast = Toast.makeText(
                            context,
                            "You will be able to get notifications about this post when it finishes",
                            Toast.LENGTH_LONG
                        )
                        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 100) // top center with offset
                        toast.show()                    }
                ,
                tint = if(clickableNBtn) Primary else SubTitle2
            )

        }

        // Right: row of reactions
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy((-4).dp)
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .border(width = 1.dp, color = Color.White, shape = CircleShape)
                            .background(SubTitle2)
                    )
                }
            }

            Text(
                text =  if(cpt > 10 ) "+" + cpt.toString() else cpt.toString(),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                color = SubTitle2,
                modifier = Modifier.padding(start = 2.dp)
            )
        }
    }

}

