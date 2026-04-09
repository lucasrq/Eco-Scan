package br.com.fiap.ecoscan.activities



import android.content.Intent
import android.graphics.Camera
import androidx.compose.foundation.Image
import androidx.compose.material.icons.filled.*

import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.Row

import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.platform.LocalContext


import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.sp

import br.com.fiap.ecoscan.R
import br.com.fiap.ecoscan.ui.theme.EcoScanTheme
import br.com.fiap.ecoscan.ui.theme.poppinsFamily

@Composable
fun HomeActivity() {

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(400.dp)
                .background(color = MaterialTheme.colorScheme.primary)
        ) {
            Card(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .width(160.dp)
                    .height(85.dp),
                shape = RoundedCornerShape(
                    topEnd = 85.dp
                ),colors = CardDefaults.cardColors(containerColor =
                    MaterialTheme.colorScheme.secondary
                )


            ) { }

            Card(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .width(160.dp)
                    .height(85.dp),
                shape = RoundedCornerShape(
                    bottomStart = 85.dp
                ),colors = CardDefaults.cardColors(containerColor =
                    MaterialTheme.colorScheme.secondary
                )

            ) { }


            Image(
                painter = painterResource(id = R.drawable.econscanprimary),
                contentDescription = "Planta verde, crescendo",
                modifier = Modifier.size(200.dp)
                    .size(200.dp)
                    .offset(x = (190).dp)
                    .offset(y = (170.dp))
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(35.dp)
                    .align(Alignment.Center)
            ) {
                Text(
                    color = MaterialTheme.colorScheme.secondary,
                    text = "Econ \n" +
                            "  Scam",
                    fontSize = 74.sp,
                    lineHeight = 74.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium
                )


                Text(
                    color = MaterialTheme.colorScheme.secondary,
                    text = "Descubra o impacto dos\n" +
                            "produtos que você consome",
                    fontSize = 16.sp,
                    fontFamily = poppinsFamily,
                )

                val context = LocalContext.current
                Button(
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .padding(start = 30.dp)
                        .height(56.dp)
                        .fillMaxWidth(0.9f),
                    onClick = {
                        val intent = Intent(context, CameraActivity::class.java)
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 12.dp
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Câmera",
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier
                                .size(34.dp)
                                .padding(end = 8.dp)
                        )

                        Text(
                            text = "Escanear Produto",
                            color = MaterialTheme.colorScheme.tertiary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppinsFamily
                        )
                    }
                }
            }


    }
}

@Preview(showBackground = true)
@Composable
fun HomeActivityPreview() {
    EcoScanTheme {
        HomeActivity()
    }
}