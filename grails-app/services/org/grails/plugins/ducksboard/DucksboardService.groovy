package org.grails.plugins.ducksboard

import org.grails.plugins.ducksboard.pull.DucksboardPullAPI
import org.grails.plugins.ducksboard.push.DucksboardPushAPI

import groovy.time.TimeCategory
import grails.converters.JSON

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

        if (actualValue) {
            return pushIntegerValue(widgetId, actualValue + increment)
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



    public randomList() {

        Calendar cal = Calendar.getInstance()
        cal.set(Calendar.HOUR, 0)
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)

        def dos = cal.clone()
        def uno
        use (TimeCategory) {
            //dos = uno.time - 1.month
            uno = dos.time - 1.month

        }

        Date a1 = uno
        Date b1 = dos.time

        def random = new Random()

        def list = []
        for(date in b1..a1) {
            def map = [:]
            map.timestamp = date.time/1000
            map.value = random.nextInt(50)+20

            list << map
        }

        return list

    }

}
