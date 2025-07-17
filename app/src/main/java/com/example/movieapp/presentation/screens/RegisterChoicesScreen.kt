package com.example.movieapp.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieapp.R
import com.example.movieapp.presentation.models.RegisterUi

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterChoicesScreen(navController: NavController) {

    val links = listOf(
        RegisterUi(R.drawable.facebook, "Continue with Facebook"),
        RegisterUi(R.drawable.google, "Continue with Google"),
        RegisterUi(
            R.drawable.apple, "Continue with Apple"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.dark))
    ) {
        Icon(
            painter = painterResource(id = R.drawable.back),
            tint = Color.White,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 80.dp, start = 25.dp)
                .width(30.dp)
                .height(25.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.registerimg),
            tint = Color.Unspecified,
            modifier = Modifier
                .padding(top = 50.dp)
                .width(237.dp).height(200.dp)
                .align(Alignment.CenterHorizontally),
            contentDescription = null
        )
        Text(
            text = "Let’s you in",
            modifier = Modifier
                .padding(top = 50.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 25.dp).padding(bottom = 20.dp, top = 30.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            items(links) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp).background(color = colorResource(R.color.et_dark) ,shape = RoundedCornerShape(16.dp)).border(width = 1.dp, color = colorResource(
                            R.color.stroke_grey), shape = RoundedCornerShape(16.dp)).clip(shape = RoundedCornerShape(16.dp)),
                   horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(it.img),
                        tint = Color.Unspecified,
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp).align(Alignment.CenterVertically)
                    )
                    Text(text = it.title, modifier = Modifier.padding(start =15.dp ).wrapContentWidth().wrapContentWidth().align(Alignment.CenterVertically), fontSize = 18.sp, color = Color.White)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }


        }





        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.line),
                contentDescription = null,
                modifier = Modifier
                    .padding( start = 25.dp)
                    .width(155.dp)
                    .height(5.dp)
            )
            Text(
                text = "or",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding( start = 25.dp)
            )
            Image(
                painter = painterResource(R.drawable.line),
                contentDescription = null,
                modifier = Modifier
                    .padding( start = 25.dp, end = 25.dp)
                    .width(155.dp)
                    .height(5.dp)
            )

        }
        Button(
            onClick = {navController.navigate(route = "signin") },
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
                text = "Sign in with password",
                fontSize = 18.sp,
                color = Color.White,

            )
        }


        Row(
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
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
                    .clickable {
                        navController.navigate(route = "signup")
                    })
        }


    }
}