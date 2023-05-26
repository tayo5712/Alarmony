package com.slembers.alarmony.feature.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.slembers.alarmony.feature.common.ui.theme.toColor

@Composable
@ExperimentalMaterial3Api
@ExperimentalGlideComposeApi
fun CardBox(
    title : @Composable() () -> Unit = { CardTitle(title = "제목") },
    content: @Composable() () -> Unit = {},
    modifier: Modifier = DefaultModifier()
) {

    Card(
        colors = CardDefaults.cardColors(containerColor = "#FFFFFF".toColor()),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
        content = {
            Column(
                content = {
                    title()
                    content()
                }
            )
        }
    )
}

@Composable
fun DefaultModifier() : Modifier {
    return Modifier
        .fillMaxWidth()
        .padding(5.dp)
            //[CardBox 흰색 배경 제거 수정 5-17]
       /* .border(
            BorderStroke(0.dp, MaterialTheme.colorScheme.background),
            MaterialTheme.shapes.medium
        )*/
        .padding(4.dp)
//            .border(1.dp, Color.Black, MaterialTheme.shapes.medium)
        .shadow(
            elevation = 5.dp,
            ambientColor = Color.Black,
            spotColor = Color.Black,
            shape = RoundedCornerShape(20.dp)
        )
}

@Composable
fun CardDivider(
    color: Color
) {
    Divider(
        thickness = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
        color = color
    )
}