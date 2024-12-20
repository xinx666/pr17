import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.taxi.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

val Novosibirsk = LatLng(55.024620, 82.922188)
val Krilova = LatLng(55.026424, 82.922957)
val OlgiJilinoy = LatLng(55.027170, 82.919883)
val defaultCameraPosition = CameraPosition.fromLatLngZoom(Novosibirsk, 14f)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StartScreen(navController: NavController, onMapLoaded: () -> Unit) {
    val mapUiSettings by remember { mutableStateOf(MapUiSettings(compassEnabled = false)) }
    val mapProperties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }
    val cameraPositionState = rememberCameraPositionState { position = defaultCameraPosition }

    // Состояние для бокового меню
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(onClose = { scope.launch { drawerState.close() } }, navController)
        },
        content = {
            Scaffold(
                topBar = { TopBar(onMenuClick = { scope.launch { drawerState.open() } }) },
                content = { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        GoogleMap(
                            modifier = Modifier.fillMaxSize(),
                            onMapLoaded = onMapLoaded, // Упрощение вызова onMapLoaded
                            uiSettings = mapUiSettings,
                            properties = mapProperties,
                            cameraPositionState = cameraPositionState,
                        ) {
                            // Вызываем MapMarker для каждой локации
                            MapMarker(Novosibirsk, "локация 1", R.drawable.yellow_car, 200, 0f)
                            MapMarker(Krilova, "Улица Крылова, 47", R.drawable.yellow_car, 200, 150f)
                            MapMarker(OlgiJilinoy, "ул. Октябрьская магистраль", R.drawable.yellow_car, 200, 90f)
                        }
                    }
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onMenuClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text("Карта") },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Open Menu")
            }
        }
    )
}

@Composable
fun DrawerContent(onClose: () -> Unit, navController: NavController) {
    Column(
        modifier = Modifier
            .width(250.dp)
            .fillMaxHeight()
            .background(Color(0xFF2A2E43), RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.End)
                .size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Close Menu",
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }
        Spacer(modifier = Modifier.height(60.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(40.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Ivanov Ivan\nDriver",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
        }

        MenuItem(
            imageResId = R.drawable.settings,
            text = "Настройки",
            onClick = {
                onClose()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        MenuItem(
            imageResId = R.drawable.history,
            text = "История",
            onClick = {
                navController.navigate("History")
            }
        )
    }
}

@Composable
fun MenuItem(@DrawableRes imageResId: Int, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = text,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun MapMarker(
    position: LatLng,
    title: String,
    @DrawableRes iconResourceId: Int,
    size: Int,
    rotation: Float
) {
    val context = LocalContext.current
    val icon = bitmapDescriptorFromVector(context, iconResourceId, size)
    Marker(
        state = MarkerState(position = position),
        title = title,
        icon = icon,
        rotation = rotation
    )
}

fun bitmapDescriptorFromVector(context: Context, @DrawableRes vectorResId: Int, size: Int): BitmapDescriptor? {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
    vectorDrawable?.setBounds(0, 0, size, size)
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    vectorDrawable?.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
