package com.sharehub.sharehub.sharehub

class Constants {

    object UserGateway {
        val URL = API_URL + "UserGateway/"
        val Check = URL + "Check"
        val CheckKey = URL + "CheckKey"
        val New = URL + "New"
        val ProfileData = URL + "ProfileData"
        val RegisterNfc = URL + "RegisterNfc"
        val DeleteNfc = URL + "DeleteNfc"
    }

    object BoxGateway{
        val URL = API_URL + "BoxGateway/"
        val Rename = URL + "RenameBox"
        val MoveBox = URL + "MoveBox"
    }

    object CommunityGateway{
        val URL = API_URL + "CommunityGateway/"
        val GetCommunity = URL + "GetCommunity"
        val GetMessage = URL + "GetMessage"
        val RESTMessage = URL + "RESTMessage"
        val SendMessage = URL + "SendMessage"
    }

    companion object {
        val API_URL = "http://78.156.114.85:8069/api/"
    }
}
