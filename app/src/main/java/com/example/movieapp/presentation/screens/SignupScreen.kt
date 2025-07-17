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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieapp.R
import com.example.movieapp.domain.model.AuthState
import com.example.movieapp.presentation.viewmodels.SignupViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignupScreen(navController: NavController, viewModel: SignupViewModel = hiltViewModel()) {
    val links = listOf(R.drawable.facebook, R.drawable.google, R.drawable.apple)
    val passwordVisible by viewModel.passwordVisible
    val password by viewModel.password
    val email by viewModel.email
    val validation by viewModel.validationState
    val authState by viewModel.authState
    val isChecked by viewModel.isChecked

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.dark))
        ) {
            SignupHeader(navController)

            EmailInput(email, validation.emailError) {
                viewModel.onEmailChange(it)
            }

            PasswordInput(
                password = password,
                passwordVisible = passwordVisible,
                passwordError = validation.passwordError,
                onPasswordChange = { viewModel.onPasswordChange(it) },
                onTogglePasswordVisibility = { viewModel.changePasswordVisibility() }
            )

            if (authState is AuthState.Error || validation.emailError != null || validation.passwordError != null) {
                val errorMessage = when {
                    authState is AuthState.Error -> (authState as AuthState.Error).message
                    validation.emailError != null -> validation.emailError
                    validation.passwordError != null -> validation.passwordError
                    else -> null
                }
                ErrorMessage(errorMessage)
            }

            if (authState is AuthState.Success) {
                SuccessMessage((authState as AuthState.Success).message)
            }

            RememberMeCheckbox(isChecked) {
                viewModel.onCheckChange()
            }

            SignupButton(onClick = { viewModel.signUp() })

            OrContinueWith()

            SocialLinksRow(links)

            SignInText {
                navController.navigate(route = "signin")
            }
        }

        if (authState is AuthState.Loading) {
            LoadingOverlay()
        }
    }
}

@Composable
fun SignupHeader(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.back),
            tint = Color.White,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 80.dp, start = 25.dp)
                .width(30.dp)
                .height(25.dp)
                .align(Alignment.Start) // bu artıq Column-da işləyir, sol tərəfə çəkir
                .clickable { navController.popBackStack() }
        )
        Icon(
            painter = painterResource(id = R.drawable.applogo),
            tint = Color.Unspecified,
            modifier = Modifier
                .padding(top = 50.dp)
                .size(98.dp),
            contentDescription = null
        )
        Text(
            text = "Create Your Account",
            modifier = Modifier.padding(top = 50.dp),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun EmailInput(email: String, emailError: Int?, onEmailChange: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
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
            focusedContainerColor = if (emailError != null) colorResource(R.color.red_container) else colorResource(R.color.et_dark),
            unfocusedContainerColor = if (emailError != null) colorResource(R.color.red_container) else colorResource(R.color.et_dark),
            focusedIndicatorColor = if (emailError != null) colorResource(R.color.red) else Color.Transparent,
            unfocusedIndicatorColor = if (emailError != null) colorResource(R.color.red) else Color.Transparent,
            cursorColor = if (emailError != null) colorResource(R.color.red) else Color.White
        ),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.email),
                tint = if (emailError != null) colorResource(R.color.red) else Color.Unspecified,
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
}

@Composable
fun PasswordInput(
    password: String,
    passwordVisible: Boolean,
    passwordError: Int?,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
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
            focusedContainerColor = if (passwordError != null) colorResource(R.color.red_container) else colorResource(R.color.et_dark),
            unfocusedContainerColor = if (passwordError != null) colorResource(R.color.red_container) else colorResource(R.color.et_dark),
            focusedIndicatorColor = if (passwordError != null) colorResource(R.color.red) else Color.Transparent,
            unfocusedIndicatorColor = if (passwordError != null) colorResource(R.color.red) else Color.Transparent,
            cursorColor = if (passwordError != null) colorResource(R.color.red) else Color.White
        ),
        trailingIcon = {
            Icon(
                painter = if (passwordVisible) painterResource(R.drawable.visibilityon) else painterResource(R.drawable.visibilityoff),
                contentDescription = null,
                tint = if (passwordError != null) colorResource(R.color.red) else Color.Unspecified,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .size(25.dp)
                    .clickable { onTogglePasswordVisibility() }
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.lock),
                tint = if (passwordError != null) colorResource(R.color.red) else Color.Unspecified,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .requiredWidth(28.dp)
                    .requiredHeight(26.dp)
            )
        },
        singleLine = true,
    )
}

@Composable
fun ErrorMessage(messageResId: Int?) {
    if (messageResId == null) return
    Text(
        text = stringResource(messageResId),
        fontSize = 16.sp,
        color = Color.Red,
        modifier = Modifier
            .padding(top = 10.dp, start = 35.dp, end = 25.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    )
}

@Composable
fun SuccessMessage(messageResId: Int?) {
    if (messageResId == null) return
    Text(
        text = stringResource(messageResId),
        fontSize = 16.sp,
        color = Color.Green,
        modifier = Modifier
            .padding(top = 10.dp, start = 35.dp, end = 25.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    )
}

@Composable
fun RememberMeCheckbox(isChecked: Boolean, onCheckedChange: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { onCheckedChange() },
            modifier = Modifier
                .padding(top = 30.dp)
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
            modifier = Modifier.padding(start = 18.dp, top = 30.dp)
        )
    }
}

@Composable
fun SignupButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp, top = 30.dp)
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(100.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.red),
            contentColor = Color.White
        )
    ) {
        Text(
            text = "Sign up",
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun OrContinueWith() {
    Row(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.line),
            contentDescription = null,
            modifier = Modifier.padding(top = 30.dp, start = 25.dp).width(106.dp).height(5.dp)
        )
        Text(
            text = "or continue with",
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 30.dp, start = 25.dp)
        )
        Image(
            painter = painterResource(R.drawable.line),
            contentDescription = null,
            modifier = Modifier.padding(top = 30.dp, start = 25.dp, end = 25.dp).width(106.dp).height(5.dp)
        )
    }
}

@Composable
fun SocialLinksRow(links: List<Int>) {
    LazyRow(
        modifier = Modifier.padding(horizontal = 42.dp).fillMaxWidth().wrapContentHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(links) { link ->
            Card(
                modifier = Modifier.padding(top = 30.dp).width(98.dp).height(60.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.dark)),
                border = BorderStroke(width = 1.dp, color = colorResource(R.color.stroke_grey)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    painter = painterResource(link),
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier.padding(top = 12.dp).size(34.dp).align(Alignment.CenterHorizontally)
                )
            }
            Spacer(modifier = Modifier.width(30.dp))
        }
    }
}

@Composable
fun SignInText(onSignInClick: () -> Unit) {
    Row(
        modifier = Modifier.padding(top = 50.dp).fillMaxWidth().wrapContentHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Already have an account?", fontSize = 16.sp, color = Color.White)
        Text(
            text = "Sign in",
            fontSize = 16.sp,
            color = colorResource(R.color.red),
            modifier = Modifier.padding(start = 8.dp).clickable { onSignInClick() }
        )
    }
}

@Composable
fun LoadingOverlay() {
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
