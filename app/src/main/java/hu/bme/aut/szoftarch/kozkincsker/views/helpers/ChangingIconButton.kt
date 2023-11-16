package hu.bme.aut.szoftarch.kozkincsker.views.helpers

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.Button
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChangingIconButton(iconValues: List<ImageVector>, switchState: Int, modifier: Modifier, onValueChange: (Int) -> Unit){
    Button(
        onClick = { if(switchState == 0) onValueChange(1) else onValueChange(0) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (switchState == 0)
                MaterialTheme.colors.primary
            else MaterialTheme.colors.secondary
        ),
        modifier = modifier
    ) {
        if (switchState==0 || iconValues.size==1){
            Icon(imageVector = iconValues[0], contentDescription = "")
        }
        else{
            Icon(imageVector = iconValues[1], contentDescription = "")
        }
    }
    /*
    IconButton(
        onClick = { if(switchState == 0) onValueChange(1) else onValueChange(0) }
    ) {
        if (switchState==0 || iconValues.size==1){
            Icon(imageVector = iconValues[0], contentDescription = "")
        }
        else{
            Icon(imageVector = iconValues[1], contentDescription = "")
        }
    }*/
}
