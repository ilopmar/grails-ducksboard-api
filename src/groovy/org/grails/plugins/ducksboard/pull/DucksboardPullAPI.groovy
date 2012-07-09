package org.grails.plugins.ducksboard.pull

import org.grails.plugins.ducksboard.common.ConnectionClient
import wslite.rest.*
import org.apache.log4j.Level
import org.apache.log4j.Logger

class DucksboardPullAPI {
    
    def log = Logger.getLogger(getClass())

    /**
     * Get the last long value of the widget
     * 
     * @param widgetId The id of the widget to get the value
     * 
     * @return the last value of the widget or null if something was wrong
     */
    public Integer pullLongValue(String widgetId) {
        def response
        withClient { client ->
            response = client.get(path:"/${widgetId}/last", query:[count:1])
        }

        def value = response.json.data[0].value
        return value
        
    }

    
    /**
     * Create the PullClient and call to Ducksboard API with the closure passed as param  
     * 
     * @param cl The closure to execute
     * 
     * @return true if done or false if something was wrong
     */
    private withClient(Closure cl) {
        try {
            RESTClient client = ConnectionClient.getPullClient()
            cl(client)
        } catch (Exception e) {
            log.error "There was an error with ducksboard request"
            log.error e
            return false
        }
        return true
    }
}
