package pe.edu.upc.trackmyroute.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.trackmyroute.common.Resource
import pe.edu.upc.trackmyroute.data.local.RouteDao
import pe.edu.upc.trackmyroute.data.local.RouteEntity
import pe.edu.upc.trackmyroute.data.remote.RouteDto
import pe.edu.upc.trackmyroute.data.remote.RouteService
import pe.edu.upc.trackmyroute.data.remote.toRoute
import pe.edu.upc.trackmyroute.domain.Route

class RouteRepository(
    private val service: RouteService,
    private val dao: RouteDao
) {
    suspend fun insertRoute(route: Route) = withContext(Dispatchers.IO){
        dao.insert(
            RouteEntity(
                route.busName,
                route.originName,
                route.originCoord,
                route.destinationName,
                route.destinationCoord,
                route.totalDistance
            )
        )
    }

    suspend fun deleteRoute(route: Route) = withContext(Dispatchers.IO){
        dao.delete(
            RouteEntity(
                route.busName,
                route.originName,
                route.originCoord,
                route.destinationName,
                route.destinationCoord,
                route.totalDistance
            )
        )
    }


    suspend fun getRoute(): Resource<List<Route>> = withContext(Dispatchers.IO){
       val response = service.getRoute()
        if (response.isSuccessful){
            response.body()?.routes?.let { routesDto ->
                val routes = mutableListOf<Route>()
                routesDto.forEach { routeDto: RouteDto ->
                    val route = routeDto.toRoute()
                    routes += route
                }
                return@withContext Resource.Success(data = routes.toList())
            }
            return@withContext Resource.Error(message = response.body()?.error ?: "")
        } else {
            return@withContext Resource.Error(message = "Data not found")
        }
    }

    // Falta implementar
/*
    suspend fun getRoutes(): Resource<List<Route>> = withContext(Dispatchers.IO){
        val response = service.getRoute()
        if(response.isSuccessful){
            response.body()?.let {
                val entities = dao.fetchAll()
                val routes = entities.map { routeEntity: RouteEntity ->
                    Route(routeEntity.busName, routeEntity.originName,
                        routeEntity.originCoord, routeEntity.destinationName,
                        routeEntity.destinationCoord, routeEntity.totalDistance)
                }.toList()
                return@withContext Resource.Success(routes)
            }
            return@withContext Resource.Error(message = response.message())
        }
        return@withContext Resource.Error(message = response.message())
    }
    */
}