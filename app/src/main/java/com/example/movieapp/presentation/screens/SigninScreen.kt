package com.example.movieapp.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieapp.R
import com.example.movieapp.domain.model.AuthState
import com.example.movieapp.presentation.viewmodels.SignInViewModel




@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignInScreen(navController: NavController, viewModel: SignInViewModel = hiltViewModel()) {
    val links = listOf(R.drawable.facebook, R.drawable.google, R.drawable.apple)
    val authState by viewModel.authState

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.dark)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            SignInHeader(navController)
            SignInLogo()
            SignInTitle()
            SignInInputFields(viewModel)
            SignInMessages(viewModel)
            SignInCheckbox(viewModel)
            SignInButton(viewModel)
            SignInDivider()
            SignInSocialLinks(links)
            SignInFooter(navController)
        }

        if (authState is AuthState.Loading) {
            ShowLoadingOverlay()
        }
    }
}

@Composable
fun SignInHeader(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 80.dp, start = 25.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.back),
            tint = Color.White,
            contentDescription = null,
            modifier = Modifier
                .width(30.dp)
                .height(25.dp)
                .clickable { navController.popBackStack() }
        )
    }
}

@Composable
fun SignInLogo() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.applogo),
            tint = Color.Unspecified,
            modifier = Modifier
                .size(98.dp),
            contentDescription = null
        )
    }
}

@Composable
fun SignInTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login to Your Account",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun SignInInputFields(viewModel: SignInViewModel) {
    var email by viewModel.email
    var password by viewModel.password
    var passwordVisible by viewModel.passwordVisible
    val validation by viewModel.validationState

    OutlinedTextField(
        onValueChange = { viewModel.onEmailChange(it) },
        value = email,
        label = {
            Text(
                text = "Email",
                color = colorResource(R.color.tv_grey),
                modifier = Modifier.padding(start = 25.dp)
            )
        },
        modifier = Modifier
            .padding(top = 50.dp, start = 25.dp, end = 25.dp)
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(12.dp)),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = if (validation.emailError != null) colorResource(R.color.red_container) else colorResource(
                R.color.et_dark
            ),
            unfocusedContainerColor = if (validation.emailError != null) colorResource(R.color.red_container) else colorResource(
                R.color.et_dark
            ),
            focusedIndicatorColor = if (validation.emailError != null) colorResource(R.color.red) else Color.Transparent,
            unfocusedIndicatorColor = if (validation.emailError != null) colorResource(R.color.red) else Color.Transparent,
            cursorColor = if (validation.emailError != null) colorResource(R.color.red) else Color.White
        ),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.email),
                tint = if (validation.emailError != null) colorResource(R.color.red) else Color.Unspecified,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .requiredWidth(27.dp)
                    .requiredHeight(25.dp),
                contentDescription = null
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        textStyle = TextStyle(color = Color.White, fontSize = 16.sp)
    )

    OutlinedTextField(
        value = password,
        onValueChange = { viewModel.onPasswordChange(it) },
        label = {
            Text(
                text = "Password",
                color = colorResource(R.color.tv_grey),
                modifier = Modifier.padding(start = 25.dp)
            )
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
        modifier = Modifier
            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = if (validation.passwordError != null) colorResource(R.color.red_container) else colorResource(
                R.color.et_dark
            ),
            unfocusedContainerColor = if (validation.emailError != null) colorResource(R.color.red_container) else colorResource(
                R.color.et_dark
            ),
            focusedIndicatorColor = if (validation.passwordError != null) colorResource(R.color.red) else Color.Transparent,
            unfocusedIndicatorColor = if (validation.passwordError != null) colorResource(R.color.red) else Color.Transparent,
            cursorColor = if (validation.passwordError != null) colorResource(R.color.red) else Color.White
        ),
        trailingIcon = {
            Icon(
                painter = if (passwordVisible) painterResource(R.drawable.visibilityon) else painterResource(
                    R.drawable.visibilityoff
                ),
                contentDescription = null,
                tint = if (validation.passwordError != null) colorResource(R.color.red) else Color.Unspecified,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .size(25.dp)
                    .clickable { viewModel.changePasswordVisibility() }
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.lock),
                tint = if (validation.passwordError != null) colorResource(R.color.red) else Color.Unspecified,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .requiredWidth(28.dp)
                    .requiredHeight(26.dp)
            )
        },
        singleLine = true
    )
}

@Composable
fun SignInMessages(viewModel: SignInViewModel) {
    val validation by viewModel.validationState
    val authState by viewModel.authState

    if (authState is AuthState.Error || validation.emailError != null || validation.passwordError != null) {
        var errorMessage: Int? = null
        if (authState is AuthState.Error) {
            errorMessage = (authState as AuthState.Error).message
        } else {
            errorMessage = validation.emailError ?: validation.passwordError
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 35.dp, end = 25.dp)
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(errorMessage ?: R.string.naməlum_səhv_baş_verdi),
                fontSize = 16.sp,
                color = Color.Red
            )
        }
    }
    if (authState is AuthState.Success) {
        var successMessage = (authState as AuthState.Success).message
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 35.dp, end = 25.dp)
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(successMessage ?: R.string.naməlum_səhv_baş_verdi),
                fontSize = 16.sp,
                color = Color.Green
            )
        }
    }
}

@Composable
fun SignInCheckbox(viewModel: SignInViewModel) {
    var isChecked by viewModel.isChecked

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 30.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { viewModel.onCheckChange() },
            modifier = Modifier
                .border(
                    3.dp,
                    color = colorResource(R.color.red),
                    shape = RoundedCornerShape(8.dp)
                )
                .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp))
                .size(26.dp),
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Red,
                uncheckedColor = Color.Transparent,
                checkmarkColor = Color.White
            )
        )
        Text(
            text = "Remember me",
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(start = 18.dp)
        )
    }
}

@Composable
fun SignInButton(viewModel: SignInViewModel) {
    val validation by viewModel.validationState

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp, top = 30.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { if (validation.isValid) viewModel.signIn() },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(100.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.red),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Sign in",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SignInDivider() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 30.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.line),
            contentDescription = null,
            modifier = Modifier
                .width(106.dp)
                .height(5.dp)
        )
        Text(
            text = "or continue with",
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 25.dp)
        )
        Image(
            painter = painterResource(R.drawable.line),
            contentDescription = null,
            modifier = Modifier
                .width(106.dp)
                .height(5.dp)
        )
    }
}

@Composable
fun SignInSocialLinks(links: List<Int>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 42.dp, vertical = 30.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(links) {
            Card(
                modifier = Modifier
                    .width(98.dp)
                    .height(60.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.dark)),
                border = BorderStroke(
                    width = 1.dp,
                    color = colorResource(R.color.stroke_grey)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    painter = painterResource(it),
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .size(34.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            Spacer(modifier = Modifier.width(30.dp))
        }
    }
}

@Composable
fun SignInFooter(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 50.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Don’t have an account?", fontSize = 16.sp, color = Color.White)
        Text(
            text = "Sign up",
            fontSize = 16.sp,
            color = colorResource(R.color.red),
            modifier = Modifier
                .padding(start = 8.dp)
                .clickable { navController.navigate(route = "signup") }
        )
    }
}

@Composable
private fun ShowLoadingOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = colorResource(R.color.red),
            strokeWidth = 4.dp
        )
    }
}
