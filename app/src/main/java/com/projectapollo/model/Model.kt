package com.projectapollo.model

import com.adamratzman.spotify.auth.SpotifyDefaultCredentialStore
import com.projectapollo.ApolloApplication
import com.projectapollo.BuildConfig

object Model {
    val credentialStore by lazy {
        SpotifyDefaultCredentialStore(
            clientId = BuildConfig.spotifyapiclientId,
            redirectUri = BuildConfig.spotifyapiredirectUri,
            applicationContext = ApolloApplication.context
        )
    }
}