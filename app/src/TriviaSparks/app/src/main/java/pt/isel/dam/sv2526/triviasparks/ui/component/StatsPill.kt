package pt.isel.dam.sv2526.triviasparks.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.dam.sv2526.triviasparks.R

@Composable
fun StatsPill(icon: Int,
              description: String,
              backgroundColor: Color,
              tintColor: Color,
              modifier: Modifier = Modifier) {

    Row(horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(5.dp)) {
        Icon(painter            = painterResource(icon),
            contentDescription = null,
            tint               = tintColor)

        Spacer(modifier = Modifier.width(10.dp))
        Text(text = description, color = tintColor)
    }
}

@Preview
@Composable
fun StatsPillPreview() {
    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
        StatsPill(
            icon = R.drawable.ic_crown,
            description = "Teste1",
            backgroundColor = Color.Red,
            tintColor = Color.Yellow,
            modifier = Modifier.weight(1f))

        StatsPill(
            icon = R.drawable.ic_trophy,
            description = "Teste2",
            backgroundColor = Color.Cyan,
            tintColor = Color.DarkGray,
            modifier = Modifier.weight(1f))
    }

}










/*
Row(
modifier              = modifier
.background(color = pillColor, shape = ChipShape)
.padding(horizontal = Spacing.lg, vertical = Spacing.md),
verticalAlignment     = Alignment.CenterVertically,
horizontalArrangement = Arrangement.Center
) {
    Icon(
        painter            = painterResource(iconRes),
        contentDescription = null,
        tint               = iconTint,
        modifier           = Modifier.size(IconSize.sm)
    )
    Spacer(modifier = Modifier.width(Spacing.sm))
    Text(
        text       = label,
        style      = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color      = MaterialTheme.colorScheme.onBackground
    )
}*/