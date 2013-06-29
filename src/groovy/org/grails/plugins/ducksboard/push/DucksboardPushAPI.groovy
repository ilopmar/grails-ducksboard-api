package org.grails.plugins.ducksboard.push

import org.grails.plugins.ducksboard.common.ConnectionClient
import wslite.rest.*
import org.apache.log4j.Level
import org.apache.log4j.Logger

class DucksboardPushAPI {

    def log = Logger.getLogger(getClass())

    /**
     * Common method to send a json to ducksboard to update a widget
     *
     * @param widgetId The widget id to update
     * @param json The json to send
     *
     * @return true if done or false if something was wrong
     */
    public boolean pushJson(String widgetId, String json) {
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