package basicserver.routes

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
class UniversalLinks {

    data class WebCredential(
        val apps: List<String>
    )

    data class AppleAppSiteAssociationFile(
        val webcredentials: List<WebCredential>
    )

    @GET
    @Path("apple-app-site-association")
    fun authenticateUser(): AppleAppSiteAssociationFile {
        return AppleAppSiteAssociationFile(
            listOf(
                WebCredential(
                    listOf("5GNP839YMK.com.mrktproject.Spiritz")
                )
            )
        )
    }
}