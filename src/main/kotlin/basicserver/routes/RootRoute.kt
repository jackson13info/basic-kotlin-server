package basicserver.routes

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
class RootRoute {

    data class MyResponse(
        val name: String,
        val success: Boolean = true
    )

    @GET
    @Path("home")
    fun authenticateUser(): MyResponse {
        return MyResponse("Jackson")
    }
}