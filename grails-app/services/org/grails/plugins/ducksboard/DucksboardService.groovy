package org.grails.plugins.ducksboard

import org.grails.plugins.ducksboard.pull.DucksboardPullAPI
import org.grails.plugins.ducksboard.push.DucksboardPushAPI
import org.grails.plugins.ducksboard.push.StatusValues

import groovy.json.JsonBuilder

class DucksboardService {

    static transactional = false

    /**
     * Update the widget with the new long value.
     * This method can be used to update the following widgets: counters, bars, boxes and pins
     * More information at: http://dev.ducksboard.com/apidoc/slot-kinds/
     *
     * @param widgetId The id of the widget
     * @param value The new value
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
     * Update the widget with the new double value. It is used for gauge widgets.
     * More information at: http://dev.ducksboard.com/apidoc/slot-kinds/#gauges
     *
     * @param widgetId The widget id
     * @param value The new value
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
     * More information at: http://dev.ducksboard.com/apidoc/slot-kinds/
     *
     * @param widgetId The id of the widget
     * @param delta OPTIONAL The value to add or subtract
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
     * More information at: http://dev.ducksboard.com/apidoc/slot-kinds/#absolute-graphs
     *
     * @param widgetId The id of the widget
     * @param list The list to update the widget. Every element of this list must be a map with the keys
     *  "timestamp" and "value"
     *
     * @return true if done, false otherwise
     */
    public Boolean pushTimestampValues(String widgetId, List list) {
        def ducksboardPushAPI = new DucksboardPushAPI()

        def builder = new JsonBuilder(list)
        return ducksboardPushAPI.pushJson(widgetId, builder.toString())
    }

    /**
     * Update a leaderboard widget with the new ranking
     * More information at: http://dev.ducksboard.com/apidoc/slot-kinds/#leaderboards
     *
     * @param widgetId The id of the widget
     * @param list The list to update the widget. Every element of this list must be a map with the keys
     *  "name" and "values", which contains a list of values. Optionally you can include the "status" property
     *  to use Status Leaderboards (http://dev.ducksboard.com/apidoc/slot-kinds/#status-leaderboards)
     *
     * @return true if done, false otherwise
     */
    public Boolean pushLeaderboardValues(String widgetId, List list) {
        def ducksboardPushAPI = new DucksboardPushAPI()

        def builder = new JsonBuilder()
        builder {
            board(list)
        }

        return ducksboardPushAPI.pushJson(widgetId, builder.toString())
    }

    /**
     * Inserts a timeline entry in a timeline widget
     * More information at: http://dev.ducksboard.com/apidoc/slot-kinds/#timelines
     *
     * @param widgetId The id of the widget
     * @param map The map to update the widget. The map must contain the keys "timestamp" and "value". The "value"
     *  must be another map with the "title", "image" and "content" keys. Optionally the key "link" can be included
     *
     * @return true if done, false otherwise
     */
    public Boolean pushTimelineValues(String widgetId, Map map) {
        def ducksboardPushAPI = new DucksboardPushAPI()

        def builder = new JsonBuilder(map)
        return ducksboardPushAPI.pushJson(widgetId, builder.toString())
    }

    /**
     * Push a new image to an image widget.
     * More information at: http://dev.ducksboard.com/apidoc/slot-kinds/#images
     *
     * @param widgetId The id of the widget
     * @param map The map to update the widget. The map must contain the keys "timestamp" and "value". The "value"
     *  must be another map with the "source" and "caption" keys.
     *  The "source" must start with "data:image/png;base64," followed with the Base64 encoded image. You can convert an image with:
     *    def f = new File('/path/to/image.png')
     *    f.bytes.encodeAsBase64()
     *
     * @return true if done, false otherwise
     */
    public Boolean pushImage(String widgetId, Map map) {
        def ducksboardPushAPI = new DucksboardPushAPI()

        def builder = new JsonBuilder(map)
        return ducksboardPushAPI.pushJson(widgetId, builder.toString())
    }

    /**
     * Push a new image to an image widget without additional info (only the image)
     * More information at: http://dev.ducksboard.com/apidoc/slot-kinds/#images
     *
     * @param widgetId The id of the widget
     * @param file The file object pointing to the image
     *
     * @return true if done, false otherwise
     */
    public Boolean pushImage(String widgetId, File file) {
        def ducksboardPushAPI = new DucksboardPushAPI()

        def builder = new JsonBuilder()
        builder {
            timestamp(new Date().time/1000)
            value {
                source("data:image/png;base64," + file.bytes.encodeAsBase64())
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
     *
     * @return true if done, false otherwise
     */
    public Boolean pushStatus(String widgetId, StatusValues status) {
        def ducksboardPushAPI = new DucksboardPushAPI()

        def builder = new JsonBuilder()
        builder {
            timestamp(new Date().time/1000)
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
     *
     * @return true if done, false otherwise
     */
    public Boolean pushText(String widgetId, String text) {
        def ducksboardPushAPI = new DucksboardPushAPI()

        def builder = new JsonBuilder()
        builder {
            timestamp(new Date().time/1000)
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
     *
     * @param widgetId The id of the widget
     * @param minimum The minimum value
     * @param maxium The maximum value
     * @param value The current value
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