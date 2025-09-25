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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import com.example.univpulse.ui.theme.GreenTitle
import com.example.univpulse.ui.theme.Primary
import com.example.univpulse.ui.theme.RedTitle
import com.example.univpulse.ui.theme.SubTitle2
import com.example.univpulse.ui.theme.YellowTitle

@Composable
fun Home(modifier: Modifier = Modifier) {
    val dropdownItems = listOf(stringResource(R.string.select_university), stringResource(R.string.select_campus))

    // set cairo font for arabic language
    val context = LocalContext.current
    val currentLang = LanguagesPreferences.getSavedLanguageCode(context)
    var setFontSemiBold = if(currentLang == "ar") R.font.cairo_semibold else R.font.poppins_semibold
    var setFontBold = if(currentLang == "ar") R.font.cairo_bold else R.font.poppins_bold
    Column(
      modifier = Modifier.fillMaxSize()

    ){
        Text(
            text = "uniPulse",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.poppins_bold)),
            color = Color.Black,
            modifier = Modifier.padding(start = 16.dp)
            )
        Spacer(modifier = Modifier.height(16.dp))
        // row of dropmenus
        Row(
          modifier = Modifier
              .fillMaxWidth()
              .padding(start = 16.dp, end = 16.dp)
        ){
            CustomDropdownMenu(items = dropdownItems)
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Column of Posts
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ){
            val colorList = listOf(GreenTitle, RedTitle, YellowTitle)
            items(colorList.size) {index ->
                PostItemCard(color = colorList[index])
            }
        }
    }

}
// func of drop menu
@Composable
fun CustomDropdownMenu(
    items: List<String>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentLang = LanguagesPreferences.getSavedLanguageCode(context)
    var setFontSemiBold = if(currentLang == "ar") R.font.cairo_semibold else R.font.poppins_semibold
    var setFontBold = if(currentLang == "ar") R.font.cairo_bold else R.font.poppins_bold
    var expandedIndex by remember { mutableStateOf(-1) }

    Row(
        modifier = modifier
            .padding(end = 16.dp), // optional padding for the whole row
        horizontalArrangement = Arrangement.spacedBy(8.dp) // 8.dp space between dropdowns
    ) {
        items.forEachIndexed { index, item ->
            var expanded by remember { mutableStateOf(false) }

            // Detect if this dropdown is active
            val isSelected = expandedIndex == index

            Box {
                Row(
                    modifier = Modifier
                        .background(
                            color = if (isSelected) Primary else Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = if (isSelected) Primary else SubTitle2,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            expandedIndex = if (isSelected) -1 else index
                            expanded = !expanded
                        }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        fontSize = 14.sp,
                        text = item,
                        fontFamily = FontFamily(Font(setFontSemiBold)),
                        color = if (isSelected) Color.White else SubTitle2
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown",
                        tint = if (isSelected) Color.White else SubTitle2
                    )
                }

                DropdownMenu(
                    expanded = isSelected && expanded,
                    onDismissRequest = { expanded = false; expandedIndex = -1 }
                ) {
                    listOf("Option 1", "Option 2", "Option 3").forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {

                                expanded = false
                                expandedIndex = -1
                            }
                        )
                    }
                }
            }
        }
    }
}


