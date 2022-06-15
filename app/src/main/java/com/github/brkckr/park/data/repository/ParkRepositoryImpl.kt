package com.github.brkckr.park.data.repository

import com.github.brkckr.park.data.mapper.toPark
import com.github.brkckr.park.data.mapper.toParkDetail
import com.github.brkckr.park.data.remote.ParkApi
import com.github.brkckr.park.domain.model.Park
import com.github.brkckr.park.domain.model.ParkDetail
import com.github.brkckr.park.domain.repository.ParkRepository
import com.github.brkckr.park.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ParkRepositoryImpl @Inject constructor(
    private val api: ParkApi
) : ParkRepository {

    override suspend fun getParkList(): Resource<List<Park>> {
        return try {
            val abc = api.getParkList()
            Resource.Success(abc.map { it.toPark() })
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(message = "")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(message = "")
        }
    }

    override suspend fun getParkDetail(id: Int): Resource<ParkDetail> {
        return try {
            val result = api.getParkDetail(id)
            Resource.Success(result[0].toParkDetail())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(message = "")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(message = "")
        }
    }
}