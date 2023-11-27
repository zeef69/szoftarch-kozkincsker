package hu.bme.aut.szoftarch.kozkincsker.views


import android.net.Uri
import android.app.Activity

import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import dagger.hilt.android.internal.managers.ViewComponentManager
import hu.bme.aut.szoftarch.kozkincsker.R
import hu.bme.aut.szoftarch.kozkincsker.data.enums.TaskType
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.data.model.TaskSolution
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.ChangingIconButton
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.DatePicker
import hu.bme.aut.szoftarch.kozkincsker.views.helpers.VerticalReorderList
import hu.bme.aut.szoftarch.kozkincsker.views.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun Task(
    task: Task,
    session: Session,
    answerListSize: Int,
    choiceAnswerInputList: ArrayList<String>,
    checkedStateList: MutableList<Boolean>,
    orderAnswerInputList: MutableList<String>,
    onUploadImage : (uri: Uri) -> String,
    onSaveClicked: (Task, TaskSolution) -> Unit,
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val mContext = if (context is ViewComponentManager.FragmentContextWrapper) context.baseContext
                    else context
    val pattern =  '|'
    /**
     * Válaszlista és checkbox érték a választós és a sorbarendezős feladatokhoz
     * */
    //Fragmentből érkeznek
    /**
     * Számos feladathoz válasz mező érték
     * */
    var answerNumberInput by remember { mutableStateOf("0")}
    /**
     * Dátum feladathoz választó és mező érték
     * */
    var datePickerState by remember { mutableStateOf(false) }
    var dateInput by remember {
        mutableStateOf(SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date()))
    }
    /**
     * Térképen való helymeghatározás (cél terület)
     * */
    var actualRadius by remember { mutableDoubleStateOf(
        if(task.taskType == TaskType.MapAnswer) task.answers.split(pattern)[0].toDouble()
        else 10.0) }
    val originalMarkerState = rememberMarkerState(position = (if(task.taskType == TaskType.MapAnswer) LatLng(task.answers.split(pattern)[2].toDouble(), task.answers.split(pattern)[3].toDouble())
                                                            else LatLng(47.497913, 19.040236)))
    val mapZoomState by remember { mutableFloatStateOf(
        if(task.taskType == TaskType.MapAnswer)
            task.answers.split(pattern)[1].toFloat()
        else 20f)
    }
    val originalCameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            originalMarkerState.position,
            mapZoomState)
    }
    /**
     * Felhasználó helye
     * */
    val fusedLocationProviderClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var lastKnownLocation by remember {mutableStateOf<Location?>(null)}
    var deviceLatLng by remember {mutableStateOf(LatLng(0.0, 0.0))}
    val deviceCameraPositionState = rememberCameraPositionState {position = CameraPosition.fromLatLngZoom(deviceLatLng, mapZoomState)}
    //Location Update Callback
    val locationCallBack = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            for(location in locationResult.locations){
                deviceLatLng = LatLng(location!!.latitude, location.longitude)
            }
        }
    }
    //Update time
    val UPDATE_TIME : Long = 20000
    //Update locationRequest
    val locationRequest : LocationRequest = LocationRequest.create().apply{
        interval = UPDATE_TIME
        fastestInterval = UPDATE_TIME/4
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    if (ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        == PackageManager.PERMISSION_GRANTED
    ) {
        val locationResult =  fusedLocationProviderClient.lastLocation
        locationResult.addOnCompleteListener(mContext as Activity) { t ->
            if (t.isSuccessful) {
                lastKnownLocation = t.result
                deviceLatLng = LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
                deviceCameraPositionState.position = CameraPosition.fromLatLngZoom(deviceLatLng, 18f)
            } else {
                Log.d("Location", "Current location is null. Using defaults.")
                Log.e("Location", "Exception: %s", t.exception)
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, Looper.getMainLooper())
    }
    var markerVisibilityState by remember { mutableIntStateOf(0) }
    /**
     * Kép válasz
     * */
    var selectedImageUri by remember {mutableStateOf<Uri?>(null)}
    var singlePhotoPicker = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(), onResult = {selectedImageUri = it})
    var uploadedImageUriString = ""
    /**
     * Szabadszöveges válasz
     * */
    var textAnswerInput by remember { mutableStateOf("") }

    val scrollablemodifier =
        if((task.taskType == TaskType.OrderAnswer)
            || (task.taskType == TaskType.MapAnswer)) Modifier
        else Modifier.verticalScroll(rememberScrollState())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopAppBar(
            title = { Text(text = stringResource(R.string.task_title)) },
            navigationIcon = {
                IconButton(
                    content = {
                        Icon(imageVector  = Icons.Filled.ArrowBack, null)
                    },
                    onClick = onBackClick
                )
            }
        )
        Column(
            modifier = scrollablemodifier
                .fillMaxSize()
                .padding(12.dp, 12.dp, 12.dp, 25.dp)
                .weight(1f, false)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                ){
                    Text(
                        buildAnnotatedString {
                            pushStyle(SpanStyle(fontSize = 18.sp, fontStyle = FontStyle.Italic))
                            append(stringResource(R.string.task_name))
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(task.title)
                            }
                            pop()
                        },
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(0.dp, 10.dp, 0.dp, 0.dp)
                            .fillMaxWidth()
                            .weight(0.4f, false)
                    )
                    Text(
                        buildAnnotatedString {
                            pushStyle(SpanStyle(fontSize = 14.sp, fontStyle = FontStyle.Italic))
                            append(stringResource(R.string.score))
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(task.score.toString())
                            }
                            pop()
                        },
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .padding(0.dp, 10.dp, 0.dp, 0.dp)
                            .fillMaxWidth()
                            .weight(0.4f, false)
                    )
                }
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)) {
                            append(stringResource(R.string.task_type_title))
                            append(stringResource(id = task.taskType.translation))
                        }
                    },
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                        .weight(0.4f, false)
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize=14.sp)) {
                            append(task.description)
                        }
                    },
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp, 5.dp, 12.dp, 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                when(task.taskType){
                    TaskType.ListedAnswer -> {
                        Column(
                            modifier= Modifier
                                .fillMaxSize()
                        ){
                            Text(text = stringResource(R.string.answer_options),
                                modifier = Modifier
                                    .padding(0.dp, 2.dp, 0.dp, 2.dp))
                            for(i in 0..<answerListSize){
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(0.dp, 2.dp, 0.dp, 2.dp)
                                ){
                                    Checkbox(
                                        checked = checkedStateList[i],
                                        onCheckedChange = { checkedStateList[i] = it },
                                        colors = CheckboxDefaults.colors(MaterialTheme.colors.primary),
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .weight(0.1f, false)
                                    )
                                    Text(
                                        buildAnnotatedString {
                                            withStyle(style = SpanStyle(fontSize = 18.sp,fontWeight = FontWeight.Bold)) {
                                                append(choiceAnswerInputList[i])
                                            }
                                        },
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier
                                            .padding(0.dp, 10.dp, 0.dp, 0.dp)
                                            .fillMaxWidth()
                                            .weight(0.8f, false)
                                    )
                                }
                            }
                        }
                    }
                    TaskType.NumberAnswer -> {
                        Text(text = stringResource(R.string.number_answer))
                        OutlinedTextField(
                            value = answerNumberInput,
                            onValueChange = { answerNumberInput = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            placeholder = {
                                Text(
                                    text = "0",
                                    color = Gray
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 2.dp, 0.dp, 2.dp)
                        )
                    }
                    TaskType.DateAnswer -> {
                        Text(text = stringResource(R.string.date_answer))
                        DatePicker(
                            fromToday = false,
                            context = context,
                            datePickerState = datePickerState,
                            setDatePickerState = { datePickerState = it },
                            dateInput = dateInput,
                            onValueChange = { dateInput = it }
                        )
                    }
                    TaskType.MapAnswer -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .height(IntrinsicSize.Min)
                                .fillMaxWidth()
                        ) {
                            Text(text = stringResource(R.string.map_answer),
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
                                    .fillMaxWidth()
                                    .weight(0.4f, false)
                            )
                            var iconList = mutableListOf(Icons.Filled.PhoneAndroid, Icons.Filled.Flag)
                            ChangingIconButton(
                                iconList,
                                markerVisibilityState,
                                modifier= Modifier
                                    .weight(0.18f, true)
                                    .padding(2.dp, 0.dp)
                            ){markerVisibilityState = it}
                        }
                        Box(
                            modifier= Modifier
                                .fillMaxSize()
                                .heightIn(60.dp, 100.dp)
                        ){
                            GoogleMap(
                                modifier = Modifier
                                    .fillMaxSize(),
                                cameraPositionState = if(markerVisibilityState == 0) deviceCameraPositionState else originalCameraPositionState
                            ) {
                                MarkerInfoWindowContent(
                                    state = MarkerState(position = deviceLatLng),
                                    draggable = false,
                                    ){
                                    Text(stringResource(R.string.map_marker_you), color = Red)
                                }
                                MarkerInfoWindowContent(
                                    state = MarkerState(position = originalMarkerState.position),
                                    draggable = false,
                                ){
                                    Text(stringResource(R.string.map_marker_goal), color = Green)
                                }
                                Circle(
                                    center = originalMarkerState.position,
                                    radius = actualRadius,
                                    strokeColor = Red
                                )
                            }

                        }
                    }
                    TaskType.OrderAnswer -> {
                        Text(text = stringResource(R.string.order_answer))
                        VerticalReorderList(
                            listElements=orderAnswerInputList,
                            newListElements=orderAnswerInputList
                        )
                    }
                    TaskType.TextAnswer -> {
                        OutlinedTextField(
                            value = textAnswerInput,
                            onValueChange = { textAnswerInput = it },
                            singleLine = false,
                            placeholder = {
                                Text(
                                    text = stringResource(R.string.user_answer),
                                    color = Gray
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 2.dp, 0.dp, 2.dp)
                        )
                    }
                    TaskType.ImageAnswer -> {
                        Text(text = stringResource(R.string.task_type_image_answer))
                        Row(
                            horizontalArrangement  = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .height(IntrinsicSize.Min)
                                .fillMaxWidth()
                        ){
                            Button(
                                modifier = Modifier
                                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
                                    .fillMaxWidth()
                                    .weight(0.5f, true),
                                onClick = {
                                    singlePhotoPicker.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                }
                            ) {
                                Text(text = stringResource(R.string.image_selection))
                            }
                            Button(
                                modifier = Modifier
                                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
                                    .fillMaxWidth()
                                    .weight(0.5f, true),
                                onClick = {
                                    selectedImageUri?.let{
                                        uploadedImageUriString=onUploadImage(selectedImageUri!!)}
                                }
                            ){
                                Text(text = stringResource(R.string.image_upload))
                            }
                        }
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = null,
                            modifier = Modifier
                                .size(248.dp)
                        )

                       // ImagePicker(onImageSelected = )
                    }
                }
            }
        }
        Column( ) {
            Button(
                onClick = {
                    val answerStringBuilder=StringBuilder()


                    if(task.taskType==TaskType.ListedAnswer) {
                        answerStringBuilder.append(answerListSize)
                        for(i in 0..<answerListSize){
                            answerStringBuilder.append('|')
                            answerStringBuilder.append(checkedStateList[i]).append('|').append(choiceAnswerInputList[i])
                        }
                    }
                    else if(task.taskType==TaskType.OrderAnswer) {
                        answerStringBuilder.append(answerListSize)
                        for(i in 0..<answerListSize){
                            answerStringBuilder.append('|')
                            answerStringBuilder.append(orderAnswerInputList[i])
                        }
                    }
                    else if(task.taskType==TaskType.MapAnswer) {
                        answerStringBuilder
                            .append(deviceCameraPositionState.position.target.latitude.toString())
                            .append("|")
                            .append(deviceCameraPositionState.position.target.longitude.toString())
                    }
                    var userAnswer = when(task.taskType){
                        TaskType.ListedAnswer, TaskType.OrderAnswer, TaskType.MapAnswer -> answerStringBuilder.toString()
                        TaskType.NumberAnswer -> if(answerNumberInput == "") "0" else answerNumberInput
                        TaskType.DateAnswer -> dateInput.toString()
                        TaskType.TextAnswer -> textAnswerInput
                        TaskType.ImageAnswer -> uploadedImageUriString
                    }
                    var taskSolution = TaskSolution(
                        sessionId = session.id,
                        taskId = task.id,
                        userAnswer= userAnswer,
                        checked = false
                    )
                    onSaveClicked(task, taskSolution)
                },
                modifier = Modifier
                    .padding(vertical = 2.dp, horizontal = 50.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10),
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
}