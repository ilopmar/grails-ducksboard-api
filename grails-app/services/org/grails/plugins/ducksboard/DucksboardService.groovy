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
     * More info at: http://dev.ducksboard.com/apidoc/slot-kinds/
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
     * More info at: http://dev.ducksboard.com/apidoc/slot-kinds/
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
     * http://dev.ducksboard.com/apidoc/slot-kinds/#gauges
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
     * Increment/Decrement the value of a widget with a new long value.
     * This method can be used to update the following widgets: counters, bars, boxes and pins
     * More info at: http://dev.ducksboard.com/apidoc/slot-kinds/
     * 
     * @param widgetId The id of the widget
     * @param increment OPTIONAL The value to add or substract
     * 
     * @return true if done, false otherwise
     */
    public Boolean incrementLongValue(String widgetId, Integer increment = 1) {
        def actualValue = pullLongValue(widgetId)

        if (actualValue) {
            return pushLongValue(widgetId, actualValue + increment)
        } else {
            return false
        }
    }

    /**
     * Update a timestamp widget like an absolute relative graph
     * E.g: http://dev.ducksboard.com/apidoc/slot-kinds/#absolute-graphs 
     * 
     * @param widgetId The id of the widget
     * @param list The list to update the widget
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
     * E.g: http://dev.ducksboard.com/apidoc/slot-kinds/#leaderboards 
     * 
     * @param widgetId The id of the widget
     * @param list The list to update the widget
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
}