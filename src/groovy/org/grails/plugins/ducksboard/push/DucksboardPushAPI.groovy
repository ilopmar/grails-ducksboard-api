package org.grails.plugins.ducksboard.push

import org.grails.plugins.ducksboard.common.ConnectionClient
import wslite.rest.*
import org.apache.log4j.Level
import org.apache.log4j.Logger

class DucksboardPushAPI {
    
    def log = Logger.getLogger(getClass())

    /**
     * Push the new long value to the widget
     * 
     * @param widgetId The id of the widget
     * @param value The new value
     * 
     * @return true if done or false if something was wrong
     */
    public boolean pushLongValue(String widgetId, Long value) {
        withClient { client ->
            def response = client.post(path:"/${widgetId}") {
                json value:value
            }
        }
    }
    
    /**
     * Push the new double value to the widget.
     * 
     * @param widgetId The id of the widget
     * @param value The new value
     * 
     * @return true if done or false if something was wrong
     */
    public boolean pushDoubleValue(String widgetId, Double value) {
        withClient { client ->
            def response = client.post(path:"/${widgetId}") {
                json value:value
            }
        }
    }
    
    /**
     * Push timestamp values to the widget
     * 
     * @param widgetId The widget id
     * @param json The json with the following format: [{"timestamp":1337724000, "value": 1}, {"timestamp":1337810400, "value": 5}]
     * 
     * @return true if done or false if something was wrong
     */
    public boolean pushTimestampValues(String widgetId, String json) {
        withClient { client ->
            def response = client.post(path:"/${widgetId}") {
                text json
            }
        }
    }
    
    /**
     * Push leaderboard values to the widget
     * 
     * @param widgetId The widget id
     * @param json The json with the following format: 
     *      {"value":{"board":[{"name":"Iván","values":[10,4]},{"name":"Pepe","values":[8,1]}]}}
     * 
     * @return true if done or false if something was wrong
     */
    public boolean pushLeaderboardValues(String widgetId, String json) {
        withClient { client ->
            def response = client.post(path:"/${widgetId}") {
                text json
            }
        }
    }
    
    /**
     * Create the PushClient and call to Ducksboard API with the closure passed as param  
     * 
     * @param cl The closure to execute
     * 
     * @return true if done or false if something was wrong
     */
    private withClient(Closure cl) {
        try {
            RESTClient client = ConnectionClient.getPushClient()
            cl(client)
        } catch (Exception e) {
            log.error "There was an error with ducksboard request"
            log.error e
            return false
        }
        return true
    }
}