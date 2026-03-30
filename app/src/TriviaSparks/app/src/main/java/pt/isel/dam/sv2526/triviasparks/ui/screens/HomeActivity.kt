package pt.isel.dam.sv2526.triviasparks.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.dam.sv2526.triviasparks.R
import pt.isel.dam.sv2526.triviasparks.ui.theme.TriviaSparksTheme
import pt.isel.dam.sv2526.triviasparks.ui.theme.dimens

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TriviaSparksTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeHeader(
                avatar = ImageVector.vectorResource(id = R.drawable.ic_avatar1),
                status = Status.ACTIVE,
                title = "Trivia Sparks",
                modifier = Modifier.fillMaxWidth())
        }) { innerPadding ->
        Greeting(
            name = "Android",
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}


enum class Status { ACTIVE, INACTIVE }

@Composable
fun AvatarWithStatus(avatar: ImageVector,
                     status: Status,
                     modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Box(modifier = Modifier
            .matchParentSize()
            .border(width = MaterialTheme.dimens.borderThick, color = MaterialTheme.colorScheme.outlineVariant, shape = CircleShape)
            .clip(CircleShape)
        ) {
            Image(imageVector = avatar,
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
        }

        Box(modifier = Modifier
            .align(Alignment.BottomEnd)
            .fillMaxSize(0.3f)
            .clip(CircleShape)
            .border(width = MaterialTheme.dimens.borderThick, MaterialTheme.colorScheme.outline, CircleShape)
            .background(if (status == Status.ACTIVE) {MaterialTheme.colorScheme.tertiaryContainer } else {MaterialTheme.colorScheme.errorContainer})
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AvatarWithStatusPreview() {
    AvatarWithStatus(
        avatar = ImageVector.vectorResource(id = R.drawable.ic_avatar1),
        status = Status.ACTIVE,
        modifier = Modifier.size(50.dp))
}

@Composable
fun HomeHeader(avatar: ImageVector,
           status: Status,
           title: String,
           modifier: Modifier = Modifier)
{
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        AvatarWithStatus(avatar = avatar, status = status, modifier = Modifier.size(MaterialTheme.dimens.avatarMD))

        Text(title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary)

        FilledIconButton (onClick = {})  {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_bell),
                contentDescription = null
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun HomeHeaderPreview() {
    HomeHeader(
        avatar = ImageVector.vectorResource(id = R.drawable.ic_avatar1),
        status = Status.ACTIVE,
        title = "Trivia Sparks",
        modifier = Modifier.fillMaxWidth())
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}