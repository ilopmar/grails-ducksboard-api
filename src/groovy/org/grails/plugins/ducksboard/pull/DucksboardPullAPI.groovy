package org.grails.plugins.ducksboard.pull

import org.grails.plugins.ducksboard.common.ConnectionClient
import wslite.rest.*
import org.apache.log4j.Level
import org.apache.log4j.Logger

class DucksboardPullAPI {
    
    def log = Logger.getLogger(getClass())

    /**
     * Get the last integer value of the widget
     * 
     * @param widgetId The id of the widget to get the value
     * 
     * @return the last value of the widget or null if something was wrong
     */
    public Integer pullIntegerValue(String widgetId) {
        
        RESTClient client = ConnectionClient.getPullClient()

        def response
        try {
            response = client.get(path:"/${widgetId}/last", query:[count:1])
        } catch (Exception e) {
            log.error "There was an error with ducksboard request"
            log.error e
            return null
        }

        def value = response.json.data[0].value
        return value
    }
}
