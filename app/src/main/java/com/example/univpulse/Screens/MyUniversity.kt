import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.univpulse.R
import com.example.univpulse.ui.theme.GreenTitle
import com.example.univpulse.ui.theme.Primary
import com.example.univpulse.ui.theme.SubTitle2

@Composable
fun MyUniversity() {
    var expanded by remember { mutableStateOf(false) }
    val universityText = stringResource(R.string.university)
    val campusText = stringResource(R.string.campus)
    val selectTeacherText = stringResource(R.string.select_teacher)
    val myUniversityTitle = stringResource(R.string.my_university)

    val context = LocalContext.current
    val currentLang = LanguagesPreferences.getSavedLanguageCode(context)
    val setFontSemiBold =
        if (currentLang == "ar") R.font.cairo_semibold else R.font.poppins_semibold
    val setFontBold =
        if (currentLang == "ar") R.font.cairo_bold else R.font.poppins_bold

    var selectedBox by remember { mutableStateOf(universityText) }
    var selectedTeacher by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Text(
            text = myUniversityTitle,
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(setFontBold)),
            color = Color.Black,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // University
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (selectedBox == universityText) Primary else Color.White)
                    .border(
                        1.dp,
                        if (selectedBox == universityText) Color.Transparent else SubTitle2,
                        RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        selectedBox = universityText
                        selectedTeacher = null
                    }
            ) {
                Text(
                    text = universityText,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(setFontSemiBold)),
                    color = if (selectedBox == universityText) Color.White else SubTitle2,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }


            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (selectedBox == campusText) Primary else Color.White)
                    .border(
                        1.dp,
                        if (selectedBox == campusText) Color.Transparent else SubTitle2,
                        RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        selectedBox = campusText
                        selectedTeacher = null
                    }
            ) {
                Text(
                    text = campusText,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(setFontSemiBold)),
                    color = if (selectedBox == campusText) Color.White else SubTitle2,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }


            Box(
                modifier = Modifier
                    .height(40.dp)
                    .then(
                        if (expanded) {
                            Modifier
                        } else {
                            Modifier.border(1.dp, SubTitle2, RoundedCornerShape(8.dp))
                        }
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (expanded || selectedBox == selectTeacherText) Primary else Color.White)
                    .clickable { expanded = !expanded }
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectTeacherText,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(setFontSemiBold)),
                        color = if (expanded || selectedBox == selectTeacherText) Color.White else SubTitle2
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Image(
                        painter = painterResource(R.drawable.arrow_down_icon),
                        contentDescription = "Dropdown",
                        colorFilter = ColorFilter.tint(if (expanded || selectedBox == selectTeacherText) Color.White else SubTitle2)
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Teacher A") },
                        onClick = {
                            expanded = false
                            selectedBox = selectTeacherText
                            selectedTeacher = "Teacher A"
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Teacher B") },
                        onClick = {
                            expanded = false
                            selectedBox = selectTeacherText
                            selectedTeacher = "Teacher B"
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when {
                selectedBox == universityText -> {
                    items(5) { PostItemCard(color = GreenTitle) }
                }
                selectedBox == campusText -> {
                    item { PostItemCard(color = GreenTitle)}
                }
                selectedBox == selectTeacherText && selectedTeacher != null -> {
                    if(selectedTeacher == "Teacher A") {
                        items(5) { PostItemCard(color = GreenTitle) }
                    }else if(selectedTeacher == "Teacher B") {
                        items(2) { PostItemCard(color = GreenTitle) }
                    }

                }
            }
        }
    }
}
