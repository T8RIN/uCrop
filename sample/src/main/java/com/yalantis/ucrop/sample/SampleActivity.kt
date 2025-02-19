package com.yalantis.ucrop.sample

import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.yalantis.ucrop.UCrop

/**
 * Created by Oleksii Shliama (https://github.com/shliama).
 */
class SampleActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                var imageUri by rememberSaveable {
                    mutableStateOf<Uri?>(null)
                }
                var croppedUri by rememberSaveable {
                    mutableStateOf<Uri?>(null)
                }
                var croppingTrigger by remember {
                    mutableStateOf(false)
                }
                var rotationAngle by rememberSaveable {
                    mutableFloatStateOf(0f)
                }
                val launcher =
                    rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
                        it?.let {
                            imageUri = it
                        }
                    }
                UCrop(
                    imageModel = imageUri,
                    rotationAngle = rotationAngle,
                    croppingTrigger = croppingTrigger,
                    aspectRatio = null,
                    onCropped = {
                        croppingTrigger = false
                        imageUri = it
                        croppedUri = it
                        rotationAngle = 0f
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Cyan)
                )

                AsyncImage(
                    model = croppedUri,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )

                Column(Modifier.align(Alignment.BottomCenter)) {
                    Slider(
                        value = rotationAngle,
                        onValueChange = { rotationAngle = it },
                        valueRange = -180f..180f,
                        steps = 720
                    )
                    Button(onClick = {
                        launcher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }) {
                        Text("COCKCKCKCKOCOC")
                    }
                    Button(onClick = { croppingTrigger = true }) {
                        Text("CRROOOOOOOOOOP")
                    }
                }
                BackHandler {
                    imageUri = Uri.EMPTY
                }
            }
        }
    }
}
