package com.fiserv.creditsafe.handler

import com.fiserv.creditsafe.model.ConnectAuthenticationAuthReponse
import com.fiserv.creditsafe.model.ConnectAuthenticationAuthRequest
import com.fiserv.creditsafe.model.CreditsafeGlobalDataCompanyStatus
import com.fiserv.creditsafe.model.CreditsafeGlobalDataCountryCode
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


import java.io.InputStream
import java.util.*
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@TestInstance (TestInstance.Lifecycle.PER_CLASS )
internal class ITCompaniesApi {

    var authToken : String? = null
    var companiesApi : CompaniesApi? = null


    @BeforeAll
    internal fun setUp() {
        print("Setting things up")
        val  inputS : InputStream = ITAuthenticationApi::javaClass::class.java.getResourceAsStream("/app.properties")
        val props = Properties()
        props.load( inputS )
        val authApi  = AuthenticationApi(props.getProperty("creditsafe.connect.basepath"));
        val authRequest  = ConnectAuthenticationAuthRequest(props.getProperty("creditsafe.connect.username"), props.getProperty("creditsafe.connect.password"))
        val authResponse: ConnectAuthenticationAuthReponse =  authApi.authenticate( authRequest )

        assertNotNull( authResponse )
        assertNotNull( authResponse.token )

        authToken = authResponse.token

        companiesApi = CompaniesApi( props.getProperty("creditsafe.connect.basepath"))
    }

    @Test
    fun activeCompanySearch() {
        val countryList = mutableListOf( CreditsafeGlobalDataCountryCode.dE )

        val companiesSearchResp = authToken?.let {
            companiesApi?.companySearch(
                it, countryList, postCode = "10115", status = CreditsafeGlobalDataCompanyStatus.active,
                page = null, pageSize = 20, language = null , id = null , safeNo = null,
                regNo = null, vatNo = null, name = null, tradeName = null, acronym = null, exact = null, address = null,
                street = null, houseNo = null, city = null,
                province = null, callRef = null, officeType = null,
                phoneNo = null,  type = null, website = null, customData = null)
        }

        assertNotNull( companiesSearchResp )
        assertNotNull( companiesSearchResp.companies )
        assertTrue {
            companiesSearchResp.companies?.size?.compareTo( 20 )!! ==  0
        }

    }

    @Test
    fun nonActiveCompanySearch() {
        val countryList = mutableListOf( CreditsafeGlobalDataCountryCode.dE )

        val companiesSearchResp = authToken?.let {
            companiesApi?.companySearch(
                it, countryList, postCode = "10115", status = CreditsafeGlobalDataCompanyStatus.nonActive,
                page = null, pageSize = null, language = null , id = null , safeNo = null,
                regNo = null, vatNo = null, name = null, tradeName = null, acronym = null, exact = null, address = null,
                street = null, houseNo = null, city = null,
                province = null, callRef = null, officeType = null,
                phoneNo = null,  type = null, website = null, customData = null)
        }

        assertNotNull( companiesSearchResp )
        assertNotNull( companiesSearchResp?.companies )
        assertTrue {
            companiesSearchResp?.companies?.size?.compareTo( 0 )!! >  0
        }

    }
}