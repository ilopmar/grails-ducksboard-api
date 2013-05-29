package org.grails.plugins.ducksboard.common

import wslite.http.auth.*
import wslite.rest.*

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class ConnectionClient {

    /**
     * Create the REST Push Client with the url, user and password
     *
     * @return the push client to use Ducksboard API
     */
    public static RESTClient getPushClient() {

        def url = CH.config.ducksboard.pushApi.url ?: "https://push.ducksboard.com/v"
        def user = CH.config.ducksboard.user
        def password = CH.config.ducksboard.password ?: "x"

        def client = new RESTClient(url)
        client.authorization = new HTTPBasicAuthorization(user, password)

        return client
    }

    /**
     * Create the REST Pull client with the url, user and password
     *
     * @return the pull client to use Ducksboard API
     */
    public static RESTClient getPullClient() {

        def url = CH.config.ducksboard.pullApi.url ?: "https://pull.ducksboard.com/values"
        def user = CH.config.ducksboard.user
        def password = CH.config.ducksboard.password ?: "x"

        def client = new RESTClient(url)
        client.authorization = new HTTPBasicAuthorization(user, password)

        return client
    }


}
