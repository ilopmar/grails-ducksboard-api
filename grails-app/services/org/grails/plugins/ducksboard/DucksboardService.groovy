package org.grails.plugins.ducksboard

import org.grails.plugins.ducksboard.pull.DucksboardPullAPI
import org.grails.plugins.ducksboard.push.DucksboardPushAPI

class DucksboardService {

    static transactional = false

    /**
     * Get the last integer value of a widget
     * 
     * @param widgetId The if of the widget
     * 
     * @return the value of the widget
     */
    public Integer pullIntegerValue(String widgetId) {
        def ducksboardPullAPI = new DucksboardPullAPI()
        
        return ducksboardPullAPI.pullIntegerValue(widgetId)
    }
    
    /**
     * Update the widget with the new value
     * 
     * @param widgetId The id of the widget
     * @param value The new value
     * 
     * @return true if done, false otherwise
     */
    public Boolean pushIntegerValue(String widgetId, Integer value) {
        def ducksboardPushAPI = new DucksboardPushAPI()
        
        return ducksboardPushAPI.pushIntegerValue(widgetId, value)
    }
    
    /**
     * Increment/Decrement the value of a widget
     * 
     * @param widgetId The id of the widget
     * @param increment OPTIONAL The value to add or substract
     * 
     * @return true if done, false otherwise
     */
    public Boolean incrementIntegerValue(String widgetId, Integer increment = 1) {
        def actualValue = pullIntegerValue(widgetId)
        return pushIntegerValue(widgetId, actualValue + increment) 
    }
    
}
