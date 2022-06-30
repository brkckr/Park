package com.github.brkckr.park.presentation.park_map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.github.brkckr.park.R
import com.github.brkckr.park.domain.model.Park
import com.github.brkckr.park.ui.navigation.Screen
import com.github.brkckr.park.util.MapStyle
import com.github.brkckr.park.util.SegmentedControl
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun ParkMapScreen(
    navController: NavController,
    viewModel: ParkMapViewModel = hiltViewModel()
) {
    val state = viewModel.state

    val scope = rememberCoroutineScope()

    val listState = rememberLazyListState()

    val cameraPositionState = CameraPositionState(
        position = CameraPosition.fromLatLngZoom(
            LatLng(
                state.currentLocation.latitude,
                state.currentLocation.longitude
            ), 16f
        )
    )

    Box(
        Modifier.fillMaxSize()
    ) {
        if (!state.isLoading) {

            if (!state.isListVisible)
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(mapStyleOptions = MapStyleOptions(MapStyle.json)),
                    uiSettings = MapUiSettings(compassEnabled = true),
                    onMapLoaded = { viewModel.onEvent(ParkMapEvent.OnMapLoaded) }
                ) {
                    val context = LocalContext.current
                    var clusterManager by remember {
                        mutableStateOf<ClusterManager<ParkClusterItem>?>(
                            null
                        )
                    }

                    MapEffect(state.parkClusterList) { map ->
                        if (clusterManager == null) {
                            clusterManager = ClusterManager<ParkClusterItem>(context, map)
                        }

                        clusterManager?.addItems(state.parkClusterList)

                        clusterManager!!.setOnClusterItemInfoWindowClickListener { (itemPosition, itemTitle, itemSnippet, itemId) ->
                            scope.launch {
                                navController.navigate(Screen.ParkDetailScreen.withArgs(itemId))
                            }
                        }
                    }

                    LaunchedEffect(key1 = cameraPositionState.isMoving) {
                        if (!cameraPositionState.isMoving) {
                            clusterManager?.onCameraIdle()
                        }
                    }

                    Marker(
                        state = MarkerState(position = state.currentLocation),
                        onInfoWindowClick = {},
                        onClick = { false },
                        icon = BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_ORANGE
                        )
                    )
                }
            else {
                ParkList(navController, listState, state.parkList)
            }

            SegmentedControl(viewModel = viewModel)
        }

        if (state.isLoading || !state.isMapLoaded) {
            Loading()
        }
    }
}

@Composable
private fun ParkList(navController: NavController, listState: LazyListState, parkList: List<Park>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, top = 110.dp),
        state = listState,
    ) {
        itemsIndexed(parkList) { index, park ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .padding(8.dp)
                    .clickable(
                        onClick = {
                            navController.navigate(Screen.ParkDetailScreen.withArgs(park.parkId))
                        }
                    )
            ) {
                Text(
                    text = park.parkName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Place,
                        contentDescription = "",
                        tint = Color.LightGray,
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                    )
                    Text(
                        text = stringResource(
                            id = R.string.distance,
                            park.distanceDescription
                        ),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.LightGray,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(
                            id = R.string.empty_capacity_description,
                            park.emptyCapacity
                        ),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.LightGray,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colors.secondary)
                    )
                    Text(
                        text = if (park.isOpen) stringResource(id = R.string.open)
                        else stringResource(id = R.string.close)
                                + " " + park.workHours,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.LightGray,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (index < parkList.lastIndex)
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.DarkGray
                    )
            }
        }
    }
}

@Composable
private fun SegmentedControl(viewModel: ParkMapViewModel) {
    val map = stringResource(id = R.string.map)
    val list = stringResource(id = R.string.list)

    val twoSegments = remember { listOf(map, list) }
    var selectedTwoSegment by remember { mutableStateOf(twoSegments.first()) }
    SegmentedControl(
        twoSegments,
        selectedTwoSegment,
        onSegmentSelected = {
            selectedTwoSegment = it
            if (it == twoSegments[0]) viewModel.onEvent(ParkMapEvent.OnMapSegmented)
            else viewModel.onEvent(ParkMapEvent.OnListSegmented)
        },
        modifier = Modifier
            .padding(start = 40.dp, end = 40.dp, top = 50.dp)
            .height(45.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxSize(),
            text = it,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = Color.LightGray
        )
    }
}

@Composable
private fun Loading() {
    AnimatedVisibility(
        modifier = Modifier.fillMaxSize(),
        visible = true,
        enter = EnterTransition.None,
        exit = fadeOut()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .wrapContentSize(),
            color = MaterialTheme.colors.secondary
        )
    }
}