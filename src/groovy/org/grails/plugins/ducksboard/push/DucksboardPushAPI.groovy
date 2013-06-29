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
     * Push the new long delta to the widget. This is used to update the value of the widget adding or subtracting an increment
     *
     * @param widgetId The id of the widget
     * @param delta The value to add or subtract. It can be positive or negative
     *
     * @return true if done or false if something was wrong
     */
    public boolean pushLongDelta(String widgetId, Long delta) {
        withClient { client ->
            def response = client.post(path:"/${widgetId}") {
                json delta:delta
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