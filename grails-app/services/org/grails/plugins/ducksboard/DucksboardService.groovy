package org.grails.plugins.ducksboard

import org.grails.plugins.ducksboard.pull.DucksboardPullAPI
import org.grails.plugins.ducksboard.push.DucksboardPushAPI
import org.grails.plugins.ducksboard.push.StatusValues

import groovy.json.JsonBuilder

class DucksboardService {

    static transactional = false

    /**
     * Get the last long value of a widget.
     * This method can be used to get the value of the following widgets: counters, bars, boxes and pins
     * More information at: http://dev.ducksboard.com/apidoc/slot-kinds/
     *
     * @param widgetId The if of the widget
     *
     * @return the value of the widget
     */
    public Integer pullLongValue(String widgetId) {
        def ducksboardPullAPI = new DucksboardPullAPI()

        return ducksboardPullAPI.pullLongValue(widgetId)
    }

    /**
     * Update the widget with the new long value.
     * This method can be used to update the following widgets: counters, bars, boxes and pins
     * More information at:
     * - http://dev.ducksboard.com/apidoc/slot-kinds/#counters
     * - http://dev.ducksboard.com/apidoc/slot-kinds/#bars
     * - http://dev.ducksboard.com/apidoc/slot-kinds/#boxes
     * . http://dev.ducksboard.com/apidoc/slot-kinds/#pins
     *
     * @param widgetId The id of the widget
     * @param value The new value
     *
     * {
     *     "value": 783
     * }
     *
     * @return true if done, false otherwise
     */
    public Boolean pushLongValue(String widgetId, Long value) {
        def ducksboardPushAPI = new DucksboardPushAPI()

        def builder = new JsonBuilder()
        builder {
            delegate.value(value)
        }

        return ducksboardPushAPI.pushJson(widgetId, builder.toString())
    }

    /**
     * Update the widget with the new double value. It is used for gauge widgets.
     * More information at: http://dev.ducksboard.com/apidoc/slot-kinds/#gauges
     *
     * @param widgetId The widget id
     * @param value The new value
     *
     * {
     *     "value": 0.93
     * }
     *
     * @return true if done, false otherwise
     */
    public Boolean pushDoubleValue(String widgetId, Double value) {
        def ducksboardPushAPI = new DucksboardPushAPI()

        def builder = new JsonBuilder()
        builder {
            delegate.value(value)
        }

        return ducksboardPushAPI.pushJson(widgetId, builder.toString())
    }

    /**
     * Increment/Decrement the value of a widget with a new long value.
     * This method can be used to update the following widgets: counters, bars, boxes and pins
     * More information at:
     * - http://dev.ducksboard.com/apidoc/slot-kinds/#counters
     * - http://dev.ducksboard.com/apidoc/slot-kinds/#bars
     * - http://dev.ducksboard.com/apidoc/slot-kinds/#boxes
     * . http://dev.ducksboard.com/apidoc/slot-kinds/#pins
     *
     * @param widgetId The id of the widget
     * @param delta OPTIONAL The value to add or subtract
     *
     * {
     *     "delta": 5
     * }
     *
     * @return true if done, false otherwise
     */
    public Boolean pushLongDelta(String widgetId, Long delta = 1) {
        def ducksboardPushAPI = new DucksboardPushAPI()

        def builder = new JsonBuilder()
        builder {
            delegate.delta(delta)
        }

        return ducksboardPushAPI.pushJson(widgetId, builder.toString())
    }

    /**
     * Update a timestamp widget like an absolute relative graph
     * More information at:
     *  - http://dev.ducksboard.com/apidoc/slot-kinds/#absolute-graphs
     *  - http://dev.ducksboard.com/apidoc/slot-kinds/#absolute-area-graphs
     *  - http://dev.ducksboard.com/apidoc/slot-kinds/#stacked-graphs
     *  - http://dev.ducksboard.com/apidoc/slot-kinds/#relative-graphs
     *  - http://dev.ducksboard.com/apidoc/slot-kinds/#relative-area-graphs
     *
     * @param widgetId The id of the widget
     * @param timestamps The list of timestamps
     * @param values The list of the values
     *
     * [
     *     {
     *         "timestamp": 1337724000,
     *         "value": 1
     *     },
     *     {
     *         "timestamp": 1337810400,
     *         "value": 5
     *     }
     * ]
     *
     * @return true if done, false otherwise
     */
    public Boolean pushTimestampValues(String widgetId, List<Long> timestamps, List<Long> values) {
        def ducksboardPushAPI = new DucksboardPushAPI()

        Integer idx = 0
        def data = timestamps.collect { [timestamp:it, value:values[idx++]] }

        def builder = new JsonBuilder(data)

        return ducksboardPushAPI.pushJson(widgetId, builder.toString())
    }

    /**
     * Update a board widget with the new values. This method can be used to update leaderboards
     * and and trend leaderboards.
     * More information at:
     *  - http://dev.ducksboard.com/apidoc/slot-kinds/#leaderboards
     *  - http://dev.ducksboard.com/apidoc/slot-kinds/#trend-leaderboards
     *
     * @param widgetId The id of the widget
     * @param names The list of the names
     * @param values The list of the values
     *
     * {
     *     "value": {
     *         "board": [
     *             {
     *                 "name": "Abdul-Jabbar",
     *                 "values": [38387, 1560, 24.6]
     *             },
     *             {
     *                 "name": "Karl Malone",
     *                 "values": [36928, 1476, 25]
     *             },
     *             {
     *                 "name": "Michael Jordan",
     *                 "values": [32292, 1072, 30.1]
     *             }
     *         ]
     *     }
     * }
     *
     * @return true if done, false otherwise
     */
    public Boolean pushLeaderboardValues(String widgetId, List<String> names, List values) {
        def ducksboardPushAPI = new DucksboardPushAPI()

        Integer idx = 0
        def data = names.collect { [name:it, values:values[idx++]] }

        def builder = new JsonBuilder()
        builder {
            board(data)
        }

        return ducksboardPushAPI.pushJson(widgetId, builder.toString())
    }

    public Boolean pushStatusLeaderboardValues(String widgetId, List<String> names, List values) {
        //http://dev.ducksboard.com/apidoc/slot-kinds/#status-leaderboards
    }

    /**
     * Inserts a timeline entry in a timeline widget
     * More information at: http://dev.ducksboard.com/apidoc/slot-kinds/#timelines
     *
     * @param widgetId The id of the widget
     * @param title The title of the entry
     * @param content The content of the entry
     * @param imageUrl The url of the image
     * @param timestamp The timestamp in seconds of the request (optional)
     *
     * {
     *     "timestamp": 1310649204,
     *     "value": {
     *         "title": "error message",
     *         "image": "https://app.ducksboard.com/static/img/timeline/red.gif",
     *         "content": "Some details about my error message"
     *     }
     * }
     *
     * @return true if done, false otherwise
     */
    public Boolean pushTimelineValues(String widgetId, String title, String content, String imageUrl, Long timestamp = new Date().time/1000) {
        def ducksboardPushAPI = new DucksboardPushAPI()

        def builder = new JsonBuilder()
        builder {
            delegate.timestamp(timestamp)
            value {
                delegate.title(title)
                image(imageUrl)
                delegate.content(content)
            }
        }

        return ducksboardPushAPI.pushJson(widgetId, builder.toString())
    }

    /**
     * Push a new image to an image widget without additional info (only the image)
     * More information at: http://dev.ducksboard.com/apidoc/slot-kinds/#images
     *
     * @param widgetId The id of the widget
     * @param file The file object pointing to the image
     * @param caption The caption of the image (optional)
     * @param timestamp The timestamp in seconds of the request (optional)
     *
     *  {
     *      "timestamp": 1310649204,
     *      "value": {
     *          "source": "data:image/png;base64,iVB...==",
     *          "caption": "Ducksboard logo!"
     *      }
     *  }
     *
     * @return true if done, false otherwise
     */
    public Boolean pushImage(String widgetId, File file, String caption = "", Long timestamp = new Date().time/1000) {
        def ducksboardPushAPI = new DucksboardPushAPI()

        def builder = new JsonBuilder()
        builder {
            delegate.timestamp(timestamp)
            value {
                source("data:image/png;base64," + file.bytes.encodeAsBase64())
                delegate.caption(caption)
            }
        }

        return ducksboardPushAPI.pushJson(widgetId, builder.toString())
    }

    /**
     * Push a new status to an status widget.
     * More information at: http://dev.ducksboard.com/apidoc/slot-kinds/#status
     *
     * @param widgetId The id of the widget
     * @param status The new status of the widget
     * @param timestamp The timestamp in seconds of the request (optional)
     *
     *{
     *    "timestamp": 1310649204,
     *    "value": 0
     *}
     *
     * @return true if done, false otherwise
     */
    public Boolean pushStatus(String widgetId, StatusValues status, Long timestamp = new Date().time/1000) {
        def ducksboardPushAPI = new DucksboardPushAPI()

        def builder = new JsonBuilder()
        builder {
            delegate.timestamp(timestamp)
            value(status.value)
        }

        return ducksboardPushAPI.pushJson(widgetId, builder.toString())
    }

    /**
     * Push a new text to a text widget.
     * More information at: http://dev.ducksboard.com/apidoc/slot-kinds/#texts
     *
     * @param widgetId The id of the widget
     * @param text The new text to send to the widget
     * @param timestamp The timestamp in seconds of the request (optional)
     *
     * {
     *     "timestamp": 1310649204,
     *     "value": {
     *         "content": "Text!\nLorem ipsum dolor sit amet..."
     *     }
     * }
     *
     * @return true if done, false otherwise
     */
    public Boolean pushText(String widgetId, String text, Long timestamp = new Date().time/1000) {
        def ducksboardPushAPI = new DucksboardPushAPI()

        def builder = new JsonBuilder()
        builder {
            delegate.timestamp(timestamp)
            value {
                content(text)
            }
        }

        return ducksboardPushAPI.pushJson(widgetId, builder.toString())
    }

    /**
     * Push new funnel values to a funnel widget
     * More information at: http://dev.ducksboard.com/apidoc/slot-kinds/#funnels
     *
     * @param widgetId The id of the widget
     * @param names A list of string with the names of every step
     * @param values A list of long with the values of every step
     *
     * {
     *     "value": {
     *         "funnel": [
     *             {
     *                 "name": "STEP 1",
     *                 "value": 1600
     *             },
     *             {
     *                 "name": "STEP 2",
     *                 "value": 1400
     *             },
     *             {
     *                 "name": "STEP 3",
     *                 "value": 1200
     *             },
     *             {
     *                 "name": "STEP 4",
     *                 "value": 900
     *             },
     *             {
     *                 "name": "STEP 5",
     *                 "value": 600
     *             },
     *             {
     *                 "name": "STEP 6",
     *                 "value": 330
     *             }
     *         ]
     *     }
     * }
     *
     * @return true if done, false otherwise
     */
    public Boolean pushFunnel(String widgetId, List<String> names, List<Long> values) {
        def ducksboardPushAPI = new DucksboardPushAPI()

        Integer idx = 0
        def data = names.collect { [name:it, value:values[idx++]] }

        def builder = new JsonBuilder()
        builder.value {
            funnel(data)
        }

        return ducksboardPushAPI.pushJson(widgetId, builder.toString())
    }


    /**
     * Push values to a completion widget
     * More information at: http://dev.ducksboard.com/apidoc/slot-kinds/#completion
     *
     * @param widgetId The id of the widget
     * @param minimum The minimum value
     * @param maxium The maximum value
     * @param value The current value
     *
     * {
     *     "value": {
     *         "current": 19790,
     *         "min": 0,
     *         "max": 25000
     *     }
     * }
     *
     * @return true if done, false otherwise
     */
    public Boolean pushCompletion(String widgetId, Long minimum, Long maximum, Long value) {
        def ducksboardPushAPI = new DucksboardPushAPI()

        def builder = new JsonBuilder()
        builder.value {
            min(minimum)
            max(maximum)
            current(value)
        }

        return ducksboardPushAPI.pushJson(widgetId, builder.toString())
    }
}