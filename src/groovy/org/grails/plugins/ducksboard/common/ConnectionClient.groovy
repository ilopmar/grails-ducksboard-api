package org.grails.plugins.ducksboard.common

import wslite.http.auth.*
import wslite.rest.*

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class ConnectionClient {

    static transactional = false
    
    /**
     * Create the REST Push Client with the url, user and password
     * 
     * @return the push client to use Ducksboard API  
     */
    public static RESTClient getPushClient() {

        def url = CH.config.ducksboard.pushApi.url
        def user = CH.config.ducksboard.user
        def password = CH.config.ducksboard.password

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

        def url = CH.config.ducksboard.pullApi.url
        def user = CH.config.ducksboard.user
        def password = CH.config.ducksboard.password

        def client = new RESTClient("https://pull.ducksboard.com/values")
        client.authorization = new HTTPBasicAuthorization(user, password)

        return client
    }
    

}
