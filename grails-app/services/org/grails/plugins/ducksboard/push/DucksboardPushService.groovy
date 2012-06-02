package org.grails.plugins.ducksboard.push

@Grab(group='com.github.groovy-wslite', module='groovy-wslite', version='0.7.0')
import wslite.http.auth.*
import wslite.rest.*

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class DucksboardPushService {

    static transactional = false

    /**
     * Push the new integer value to the widget
     * 
     * @param widgetId The id of the widget
     * @param value The new value
     * 
     * @return true if done
     */
    public boolean pushIntegerValue(String widgetId, Integer value) {
        
        RESTClient client = initializeClient()

        try {        
            def response = client.post(path:"/${widgetId}") {
                json value2:value
            }
        } catch (Exception e) {
            log.error "There was an error with ducksboar request"
            log.error e
            return false
        }
        
        return true
    }
    
    /**
     * Create the RESTClient with the url, user and password
     * 
     * @return the client to use Ducksboard API  
     */
    private RESTClient initializeClient() {
        def url = CH.config.ducksboard.pushApi.url
        def user = CH.config.ducksboard.user
        def password = CH.config.ducksboard.password
        
        def client = new RESTClient(url)
        client.authorization = new HTTPBasicAuthorization(user, password)
        
        return client
    }
}
