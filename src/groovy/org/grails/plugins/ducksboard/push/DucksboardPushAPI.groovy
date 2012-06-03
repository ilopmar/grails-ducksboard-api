package org.grails.plugins.ducksboard.push

import org.grails.plugins.ducksboard.common.ConnectionClient
import wslite.rest.*
import org.apache.log4j.Level
import org.apache.log4j.Logger

class DucksboardPushAPI {
    
    def log = Logger.getLogger(getClass())

    /**
     * Push the new integer value to the widget
     * 
     * @param widgetId The id of the widget
     * @param value The new value
     * 
     * @return true if done or false if something was wrong
     */
    public boolean pushIntegerValue(String widgetId, Integer value) {
        
        RESTClient client = ConnectionClient.getPushClient()

        try {        
            def response = client.post(path:"/${widgetId}") {
                json value:value
            }
        } catch (Exception e) {
            log.error "There was an error with ducksboard request"
            log.error e
            return false
        }
        
        return true
    }
}
