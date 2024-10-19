package com.shift.notelist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shift.notelist.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun EmptyScreen(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column {
            Text(
                text = stringResource(R.string.empty_screen) + " 🥲",
                textAlign = TextAlign.Center

            )
            Spacer(Modifier.padding(16.dp))
            /*Icon(
                modifier = Modifier.size(150.dp),
                imageVector = ImageVector.vectorResource(R.drawable.sad_face),
                contentDescription =  stringResource(R.string.empty_screen),
                tint = Color.Gray
            )*/
        }
    }
}