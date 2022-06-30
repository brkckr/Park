package com.github.brkckr.park.presentation.park_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.brkckr.park.R
import com.github.brkckr.park.domain.model.ParkDetail
import com.github.brkckr.park.util.MapStyle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*

@Composable
fun ParkDetailScreen(
    id: Int?,
    viewModel: ParkDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Box(
        Modifier
            .fillMaxSize()
    ) {
        if (state.parkDetail != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                state.parkDetail.let { parkDetail ->
                    if (parkDetail.areaPolygon.isNotEmpty()) {
                        GoogleMap(
                            parkDetail = parkDetail,
                            viewModel = viewModel
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        )
                        {
                            ParkAddress(parkDetail = parkDetail)

                            ParkTariff(parkDetail = parkDetail)
                        }
                    }
                }
            }
        }

        if (state.isLoading || !state.isMapLoaded) {
            AnimatedVisibility(
                modifier = Modifier
                    .matchParentSize(),
                visible = !state.isMapLoaded,
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
    }
}

@Composable
fun GoogleMap(parkDetail: ParkDetail, viewModel: ParkDetailViewModel) {
    val parkLocation = LatLng(parkDetail.lat, parkDetail.lng)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(parkLocation, 19f)
    }
    GoogleMap(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            mapStyleOptions = MapStyleOptions(
                MapStyle.json
            )
        ),
        uiSettings = MapUiSettings(compassEnabled = true),
        onMapLoaded = { viewModel.onEvent(ParkDetailEvent.OnMapLoaded) }
    ) {
        Polygon(
            points = parkDetail.areaPolygon,
            fillColor = Color.White,
            strokeColor = MaterialTheme.colors.secondary
        )
    }
}

@Composable
fun ParkAddress(parkDetail: ParkDetail) {

    Text(
        text = parkDetail.parkName,
        color = Color.White,
        fontSize = 18.sp,
        modifier = Modifier.fillMaxWidth()
    )

    Divider(
        modifier = Modifier.fillMaxWidth()
    )

    Row {
        Icon(
            imageVector = Icons.Filled.Place,
            contentDescription = "",
            tint = Color.LightGray,
            modifier = Modifier
                .width(25.dp)
                .height(25.dp)
        )
        Text(text = stringResource(id = R.string.address), fontSize = 18.sp, color = Color.White)
    }

    Text(
        text = stringResource(
            id = R.string.address_desc,
            parkDetail.address.lowercase().capitalize(Locale.current)
        ),
        color = Color.White,
        fontSize = 14.sp,
        modifier = Modifier.fillMaxWidth()
    )

    Text(
        text = stringResource(
            id = R.string.park_type,
            parkDetail.parkType.lowercase().capitalize(Locale.current)
        ),
        fontSize = 14.sp,
        color = Color.White,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
    )

    Text(
        text = stringResource(id = R.string.empty_capacity, parkDetail.emptyCapacity),
        fontSize = 14.sp,
        color = Color.White,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
    )

    Text(
        text = stringResource(id = R.string.work_hours, parkDetail.workHours),
        fontSize = 14.sp,
        color = Color.White,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ParkTariff(parkDetail: ParkDetail) {
    if (parkDetail.tariff.isNotEmpty()) {

        Divider(modifier = Modifier.fillMaxWidth())

        Row {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "",
                tint = Color.LightGray,
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = stringResource(R.string.pricing), fontSize = 18.sp, color = Color.White)
        }

        Text(
            text = stringResource(id = R.string.free_time, parkDetail.freeTime),
            fontSize = 14.sp,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(id = R.string.monthly_fee, "%.2f".format(parkDetail.monthlyFee)),
            fontSize = 14.sp,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(parkDetail.tariff) {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .height(50.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                        .wrapContentSize()
                        .padding(4.dp),
                )
            }
        }
    }
}