package com.fiserv.creditsafe.handler

import com.fiserv.creditsafe.model.ConnectAuthenticationAuthReponse
import com.fiserv.creditsafe.model.ConnectAuthenticationAuthRequest
import org.junit.jupiter.api.Test

import java.io.InputStream
import java.util.*
import kotlin.test.assertNotNull


internal class ITAuthenticationApi {

    @Test
    fun authenticate() {

        val  inputS : InputStream = ITAuthenticationApi::javaClass::class.java.getResourceAsStream("/app.properties")
        val props = Properties()
        props.load( inputS )
        val authApi  = AuthenticationApi(props.getProperty("creditsafe.connect.basepath"));
        val authRequest  = ConnectAuthenticationAuthRequest(props.getProperty("creditsafe.connect.username"), props.getProperty("creditsafe.connect.password"))
        val authResponse: ConnectAuthenticationAuthReponse =  authApi.authenticate( authRequest )

        assertNotNull( authResponse )
        assertNotNull( authResponse.token )

    }
}