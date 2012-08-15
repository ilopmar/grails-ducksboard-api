package org.grails.plugins.ducksboard

import org.grails.plugins.ducksboard.pull.DucksboardPullAPI
import org.grails.plugins.ducksboard.push.DucksboardPushAPI

import groovy.time.TimeCategory
import grails.converters.JSON

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

        return ducksboardPushAPI.pushLongValue(widgetId, value)
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
        
        return ducksboardPushAPI.pushDoubleValue(widgetId, value)
    }

    /**
     * This method is deprecated. Use the new method pushLongDelta method
     * 
     * @param widgetId The id of the widget
     * @param increment OPTIONAL The value to add or substract
     * 
     * @return true if done, false otherwise
     */
	@Deprecated
    public Boolean incrementLongValue(String widgetId, Long increment = 1) {
        def actualValue = pullLongValue(widgetId)

        if (actualValue) {
            return pushLongValue(widgetId, actualValue + increment)
        } else {
            return false
        }
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
		
		return ducksboardPushAPI.pushLongDelta(widgetId, delta)
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

        def json = (list as JSON).toString()
        return ducksboardPushAPI.pushTimestampValues(widgetId, json)
    }
    
    /**
     * Update a leaderboard widget with the new ranking
     * More information at: http://dev.ducksboard.com/apidoc/slot-kinds/#leaderboards 
     * 
     * @param widgetId The id of the widget
     * @param list The list to update the widget. Every element of this list must be a map with the keys
     *  "name" and "values", which contains a list of values
     * 
     * @return true if done, false otherwise
     */
    public Boolean pushLeaderboardValues(String widgetId, List list) {
        def ducksboardPushAPI = new DucksboardPushAPI()

        def map = [:]
        map.value = [board:list]
        def json = (map as JSON).toString()
        
        return ducksboardPushAPI.pushLeaderboardValues(widgetId, json)
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

        def json = (map as JSON).toString()
        return ducksboardPushAPI.pushTimelineValues(widgetId, json)
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

        def json = (map as JSON).toString()
        return ducksboardPushAPI.pushImage(widgetId, json)
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
        
        def map = [:]
        map.timestamp = new Date().time/1000
        map.value = [source:"data:image/png;base64," + file.bytes.encodeAsBase64()]

        def json = (map as JSON).toString()
        return ducksboardPushAPI.pushImage(widgetId, json)
    }
}