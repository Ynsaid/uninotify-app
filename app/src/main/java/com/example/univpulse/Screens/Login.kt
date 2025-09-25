import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.univpulse.R
import com.example.univpulse.ui.theme.Primary
import com.example.univpulse.ui.theme.SubTitle
import com.example.univpulse.ui.theme.SubTitle2
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    // functoion of logging
    fun loginUser(email: String, password: String, navController: NavController){
        val auth =  FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener  { task ->
                if (task.isSuccessful) {

                    navController.navigate(Route.Main){
                        popUpTo(Route.Login){ inclusive = true } // clear backstack
                    }
                } else {
                    println("error: ${task.exception?.message}")
                    showDialog = true
                }
            }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary)
    ){
        Column(
            Modifier.fillMaxWidth()
        ){
            Image(
                painter = painterResource(id = R.drawable.books),
                contentDescription = "books",
                modifier = Modifier
                    .size(120.dp)
                    .padding(start = 30.dp)
            )
            Text(
                text = "Hello,",
                fontSize = 64.sp,
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                color = Color.White,
                modifier = Modifier.padding(start = 30.dp)
            )
            Text(
                text = "Welcome to uniPulse",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                color = Color.White,
                modifier = Modifier.padding(start = 30.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.notification),
                contentDescription = "books",
                modifier = Modifier
                    .size(160.dp)
                    .align(Alignment.End)
                    .offset(y = (-150).dp, x = (50).dp)
                    .rotate(-45f)
            )
        }
        // column of inputs and btn
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                .background(Color.White)
        ){
            Text(
                text = "Login",
                fontSize = 36.sp,
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                color = Color.Black,
                modifier = Modifier.padding(start = 30.dp, top =64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                value = email,
                onValueChange = {
                    email = it
                    emailError = false
                },
                label = { Text("Email") },
                isError = emailError,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = White,
                    focusedBorderColor =  Primary,
                    unfocusedBorderColor = SubTitle,
                    focusedLabelColor = Primary,
                    unfocusedLabelColor = SubTitle,
                )
            )
            if (emailError) {
                Text(
                    text = "Email cannot be empty",
                    color =Color.Red,
                    modifier = Modifier.padding(start = 30.dp, top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                value = password,
                onValueChange = {
                    password = it
                    passwordError = false
                },
                label = { Text("Password") },
                isError = passwordError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            painter = painterResource(id = if(isPasswordVisible) R.drawable.hide else R.drawable.show),
                            contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = White,
                    focusedBorderColor =  Primary,
                    unfocusedBorderColor = SubTitle,
                    focusedLabelColor = Primary,
                    unfocusedLabelColor = SubTitle,
                )
            )
            if (passwordError) {
                Text(
                    text = "Password cannot be empty",
                    color = Color.Red,
                    modifier = Modifier.padding(start = 30.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(64.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp),
                onClick = {
                    when {
                        email.isBlank() -> emailError = true
                        password.isBlank() -> passwordError = true
                        else -> loginUser(email, password, navController)
                    }

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    contentColor = White
                ),
                shape = RoundedCornerShape(8.dp)
            ){
                Text(
                    text = "Login",
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    color = Color.White,
                )
            }


            Text(
                text = "Ministere de l’engeignement superieur et de la recherche scientifique",
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                color = SubTitle,
                textAlign =  TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, top = 120.dp)
            )
            Text(
                text = "Copyright © 2025",
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                color = SubTitle,
                textAlign =  TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, top = 10.dp)
            )
        }
    }

    if (showDialog) {
        AlertDialogCustom(
            onDismissRequest = { showDialog = false },
            onConfirmation = { showDialog = false },
            dialogTitle = "Login Failed",
            dialogText = "You are type the wrong info",
            icon = Icons.Default.Info
        )
    }

}
@Composable
fun AlertDialogCustom(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    androidx.compose.material3.AlertDialog(
        containerColor = Color.White,
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm", color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss", color = SubTitle2)
            }
        }
    )
}
