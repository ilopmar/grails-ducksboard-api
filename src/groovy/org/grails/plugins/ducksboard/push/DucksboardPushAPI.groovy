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
     * Push timeline value to the widget
     *
     * @param widgetId The widget id
     * @param json The json with the following format:
     *      {"timestamp":1312084128.67,
     *       "value":{
     *          "title":"The title",
     *          "image":"http://upload.wikimedia.org/wikipedia/commons/6/63/Wikipedia-logo.png",
     *          "content":"This is the content of the entry"
     *        }
     *      }
     *
     * @return true if done or false if something was wrong
     */
    public boolean pushTimelineValues(String widgetId, String json) {
        withClient { client ->
            def response = client.post(path:"/${widgetId}") {
                text json
            }
        }
    }

    /**
     * Push and image to the widget
     *
     * @param widgetId The widget id
     * @param json The json with the following format:
     *  {"timestamp":1342088037,
     *   "value":{
     *      "source":"data:image/png;base64,iVBORw0KG..............rkJggg==",
     *      "caption":"The caption"
     *    }
     *  }
     *
     * @return true if done or false if something was wrong
     */
    public boolean pushImage(String widgetId, String json) {
        withClient { client ->
            def response = client.post(path:"/${widgetId}") {
                text json
            }
        }
    }

    /**
     * Push a new status to a status widget
     *
     * @param widgetId The widget id
     * @param json The json with the following format:
     *      {
     *         "timestamp":1312084128,
     *         "value":1
     *      }
     *
     * @return true if done or false if something was wrong
     */
    public boolean pushStatus(String widgetId, String json) {
        withClient { client ->
            def response = client.post(path:"/${widgetId}") {
                text json
            }
        }
    }

    /**
     * Push a new text to a text widget
     *
     * @param widgetId The widget id
     * @param json The json with the following format:
     *     {
     *         "timestamp": 1310649204,
     *         "value": {
     *             "content": "Text!\nLorem ipsum dolor sit amet..."
     *          }
     *     }
     *
     * @return true if done or false if something was wrong
     */
    public boolean pushText(String widgetId, String json) {
        withClient { client ->
            def response = client.post(path:"/${widgetId}") {
                text json
            }
        }
    }

    /**
     * Push new funnel name and values
     *
     * @param widgetId The widget id
     * @param json The json with the following format:
     *
     *  {
     *    "value": {
     *      "funnel": [
     *        {
     *          "name": "STEP 1",
     *          "value": 1600
     *        },
     *        {
     *          "name": "STEP 2",
     *          "value": 1400
     *        },
     *        {
     *          "name": "STEP 3",
     *          "value": 1200
     *        },
     *        {
     *          "name": "STEP 4",
     *          "value": 900
     *        },
     *        {
     *          "name": "STEP 5",
     *          "value": 600
     *        },
     *        {
     *          "name": "STEP 6",
     *          "value": 330
     *        }
     *      ]
     *    }
     *  }
     *
     * @return true if done or false if something was wrong
     */
    public boolean pushFunnels(String widgetId, String json) {
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