package org.grails.plugins.ducksboard

import grails.plugin.spock.*
import spock.lang.*

import org.grails.plugins.ducksboard.DucksboardService
import org.grails.plugins.ducksboard.pull.DucksboardPullAPI
import org.grails.plugins.ducksboard.push.DucksboardPushAPI
import org.grails.plugins.ducksboard.push.LeaderboardStatusValues
import org.grails.plugins.ducksboard.push.StatusValues

class DucksboardServiceSpec extends Specification {

    @Shared String widgetId = "widget-id"
    def ducksboardService

    DucksboardPullAPI ducksboardPullAPI
    DucksboardPushAPI ducksboardPushAPI

    void 'get the value of a widget with the pull API'() {
        setup:
            ducksboardService = new DucksboardService()

        and: 'mock colaborator'
            ducksboardPullAPI = Mock(DucksboardPullAPI)
            ducksboardService.ducksboardPullAPI = ducksboardPullAPI

        when:
            ducksboardService.pullLongValue(widgetId)

        then:
            1 * ducksboardPullAPI.pullLongValue(widgetId)
    }

    void 'push a value to a numeric (counter, box,...) widget'() {
        setup:
            ducksboardService = new DucksboardService()

        and: 'mock colaborator'
            ducksboardPushAPI = Mock(DucksboardPushAPI)
            ducksboardService.ducksboardPushAPI = ducksboardPushAPI

        when:
            ducksboardService.pushLongValue(widgetId, value)

        then:
            1 * ducksboardPushAPI.pushJson(widgetId, json)

        where:
            value = 123
            json = """{"value":${value}}"""
    }

    void 'push a value to a gauge widget'() {
        setup:
            ducksboardService = new DucksboardService()

        and: 'mock colaborator'
            ducksboardPushAPI = Mock(DucksboardPushAPI)
            ducksboardService.ducksboardPushAPI = ducksboardPushAPI

        when:
            ducksboardService.pushDoubleValue(widgetId, value)

        then:
            1 * ducksboardPushAPI.pushJson(widgetId, json)

        where:
            value = 0.25
            json = """{"value":${value}}"""
    }

    void 'push a delta increment with default value'() {
        setup:
            ducksboardService = new DucksboardService()

        and: 'mock colaborator'
            ducksboardPushAPI = Mock(DucksboardPushAPI)
            ducksboardService.ducksboardPushAPI = ducksboardPushAPI

        when:
            ducksboardService.pushLongDelta(widgetId)

        then:
            1 * ducksboardPushAPI.pushJson(widgetId, json)

        where:
            json = '{"delta":1}'
    }

    void 'push a delta increment with a value'() {
        setup:
            ducksboardService = new DucksboardService()

        and: 'mock colaborator'
            ducksboardPushAPI = Mock(DucksboardPushAPI)
            ducksboardService.ducksboardPushAPI = ducksboardPushAPI

        when:
            ducksboardService.pushLongDelta(widgetId, value)

        then:
            1 * ducksboardPushAPI.pushJson(widgetId, json)

        where:
            value = 5
            json = """{"delta":${value}}"""
    }

    void 'push timestamps values to a graph (absolute, relative,...) widget'() {
        setup:
            ducksboardService = new DucksboardService()

        and: 'mock colaborator'
            ducksboardPushAPI = Mock(DucksboardPushAPI)
            ducksboardService.ducksboardPushAPI = ducksboardPushAPI

        when:
            ducksboardService.pushTimestampValues(widgetId, timestamps, values)

        then:
            1 * ducksboardPushAPI.pushJson(widgetId, json)

        where:
            timestamps = [1372601079, 1372501079, 1372401079]
            values = [10, 20, 30]
            json = """[{"timestamp":${timestamps[0]},"value":${values[0]}},""" +
                    """{"timestamp":${timestamps[1]},"value":${values[1]}},""" +
                    """{"timestamp":${timestamps[2]},"value":${values[2]}}]"""
    }

    void 'try to push timestamps values to graph with params list with different sizes'() {
        setup:
            ducksboardService = new DucksboardService()

        and: 'mock colaborator'
            ducksboardPushAPI = Mock(DucksboardPushAPI)
            ducksboardService.ducksboardPushAPI = ducksboardPushAPI

        when:
            def result = ducksboardService.pushTimestampValues(widgetId, timestamps, values)

        then:
            0 * ducksboardPushAPI.pushJson(widgetId, _)
            result == false

        where:
            timestamps = [1372601079]
            values = [10, 20]
    }

    void 'push values to leaderboard widget'() {
        setup:
            ducksboardService = new DucksboardService()

        and: 'mock colaborator'
            ducksboardPushAPI = Mock(DucksboardPushAPI)
            ducksboardService.ducksboardPushAPI = ducksboardPushAPI

        when:
            ducksboardService.pushLeaderboardValues(widgetId, names, values)

        then:
            1 * ducksboardPushAPI.pushJson(widgetId, json)

        where:
            names = ["Abdul-Jabbar", "Karl Malone", "Michael Jordan",]
            values = [[38387, 1560, 24.6], [36928, 1476, 25], [32292, 1072, 30.1]]
            json = """{"value":{"board":[{"name":"${names[0]}","values":[38387,1560,24.6]},""" +
                                      """{"name":"${names[1]}","values":[36928,1476,25]},""" +
                                      """{"name":"${names[2]}","values":[32292,1072,30.1]}]}}"""
    }

    void 'try to push values to leaderboard widget with params list with different sizes'() {
        setup:
            ducksboardService = new DucksboardService()

        and: 'mock colaborator'
            ducksboardPushAPI = Mock(DucksboardPushAPI)
            ducksboardService.ducksboardPushAPI = ducksboardPushAPI

        when:
            def result = ducksboardService.pushLeaderboardValues(widgetId, names, values)

        then:
            0 * ducksboardPushAPI.pushJson(widgetId, _)
            result == false

        where:
            names = ["Abdul-Jabbar", "Karl Malone"]
            values = [[38387, 1560, 24.6], [36928, 1476, 25], [32292, 1072, 30.1]]
    }

    void 'push values to status leaderboard widget'() {
        setup:
            ducksboardService = new DucksboardService()

        and: 'mock colaborator'
            ducksboardPushAPI = Mock(DucksboardPushAPI)
            ducksboardService.ducksboardPushAPI = ducksboardPushAPI

        when:
            ducksboardService.pushStatusLeaderboardValues(widgetId, names, values, statuses)

        then:
            1 * ducksboardPushAPI.pushJson(widgetId, json)

        where:
            names = ["Cleveland", "New York", "Boulder", "Newport"]
            values = [[1928, "96%"], [1232, "84%"], ["--", "--"], [2740, "60%"]]
            statuses = [LeaderboardStatusValues.GREEN, LeaderboardStatusValues.YELLOW, LeaderboardStatusValues.GRAY, LeaderboardStatusValues.RED]
            json = """{"value":{"board":[{"name":"${names[0]}","values":[1928,"96%"],"status":"${statuses[0].value}"},""" +
                                      """{"name":"${names[1]}","values":[1232,"84%"],"status":"${statuses[1].value}"},""" +
                                      """{"name":"${names[2]}","values":["--","--"],"status":"${statuses[2].value}"},""" +
                                      """{"name":"${names[3]}","values":[2740,"60%"],"status":"${statuses[3].value}"}]}}"""
    }

    void 'try to push values to status leaderboard widget with params list with different sizes'() {
        setup:
            ducksboardService = new DucksboardService()

        and: 'mock colaborator'
            ducksboardPushAPI = Mock(DucksboardPushAPI)
            ducksboardService.ducksboardPushAPI = ducksboardPushAPI

        when:
            def result = ducksboardService.pushStatusLeaderboardValues(widgetId, names, values, statuses)

        then:
            0 * ducksboardPushAPI.pushJson(widgetId, _)
            result == false

        where:
            names = ["Cleveland", "New York", "Boulder", "Newport"]
            values = [[1928, "96%"], [1232, "84%"], ["--", "--"], ]
            statuses = [LeaderboardStatusValues.GREEN, LeaderboardStatusValues.YELLOW]
    }

    void 'push values to a timeline widget'() {
        setup:
            ducksboardService = new DucksboardService()

        and: 'mock colaborator'
            ducksboardPushAPI = Mock(DucksboardPushAPI)
            ducksboardService.ducksboardPushAPI = ducksboardPushAPI

        when:
            ducksboardService.pushTimelineValues(widgetId, title, content, imageUrl, timestamp)

        then:
            1 * ducksboardPushAPI.pushJson(widgetId, json)

        where:
            title = "error message"
            imageUrl = "https://app.ducksboard.com/static/img/timeline/red.gif"
            content = "Some details about my error message"
            timestamp = 1310649204
            json = """{"timestamp":${timestamp},"value":{"title":"${title}","image":"${imageUrl}","content":"${content}"}}"""
    }

    void 'push an image'() {
        setup:
            ducksboardService = new DucksboardService()

        and: 'mock colaborator'
            ducksboardPushAPI = Mock(DucksboardPushAPI)
            ducksboardService.ducksboardPushAPI = ducksboardPushAPI

        and: 'temp file'
            def file = File.createTempFile("prefix", "sufix")

        when:
            ducksboardService.pushImage(widgetId, file, caption, timestamp)

        then:
            1 * ducksboardPushAPI.pushJson(widgetId, json)

        where:
            caption = "The caption"
            timestamp = 1310649204
            json = """{"timestamp":${timestamp},"value":{"source":"data:image/png;base64,","caption":"${caption}"}}"""
    }

    void 'push a new status to a status widget'() {
        setup:
            ducksboardService = new DucksboardService()

        and: 'mock colaborator'
            ducksboardPushAPI = Mock(DucksboardPushAPI)
            ducksboardService.ducksboardPushAPI = ducksboardPushAPI

        when:
            ducksboardService.pushStatus(widgetId, status, timestamp)

        then:
            1 * ducksboardPushAPI.pushJson(widgetId, json)

        where:
            status << [StatusValues.OK, StatusValues.ERROR, StatusValues.WARNING, StatusValues.UNKNOWN]
            timestamp = 1310649204
            json = """{"timestamp":${timestamp},"value":${status.value}}"""
    }

    void 'push a text to a text widget'() {
        setup:
            ducksboardService = new DucksboardService()

        and: 'mock colaborator'
            ducksboardPushAPI = Mock(DucksboardPushAPI)
            ducksboardService.ducksboardPushAPI = ducksboardPushAPI

        when:
            ducksboardService.pushText(widgetId, text, timestamp)

        then:
            1 * ducksboardPushAPI.pushJson(widgetId, json)

        where:
            text = "Text! Lorem ipsum dolor sit amet..."
            timestamp = 1310649204
            json = """{"timestamp":${timestamp},"value":{"content":"${text}"}}"""
    }

    void 'push values to funnel widget'() {
        setup:
            ducksboardService = new DucksboardService()

        and: 'mock colaborator'
            ducksboardPushAPI = Mock(DucksboardPushAPI)
            ducksboardService.ducksboardPushAPI = ducksboardPushAPI

        when:
            ducksboardService.pushFunnel(widgetId, names, values)

        then:
            1 * ducksboardPushAPI.pushJson(widgetId, json)

        where:
            names = ["Step 1", "Step 2", "Step 3", "Step 4"]
            values = [1000, 800, 600, 200]
            json = """{"value":{"funnel":[{"name":"${names[0]}","value":${values[0]}},""" +
                                       """{"name":"${names[1]}","value":${values[1]}},""" +
                                       """{"name":"${names[2]}","value":${values[2]}},""" +
                                       """{"name":"${names[3]}","value":${values[3]}}]}}"""
    }

    void 'try to push values to funnel widget with params list with different sizes'() {
        setup:
            ducksboardService = new DucksboardService()

        and: 'mock colaborator'
            ducksboardPushAPI = Mock(DucksboardPushAPI)
            ducksboardService.ducksboardPushAPI = ducksboardPushAPI

        when:
            ducksboardService.pushFunnel(widgetId, names, values)

        then:
            0 * ducksboardPushAPI.pushJson(widgetId, _)

        where:
            names = ["Step 1", "Step 2", "Step 3", "Step 4"]
            values = [1000, 800]
    }

    void 'push values to completion widget'() {
        setup:
            ducksboardService = new DucksboardService()

        and: 'mock colaborator'
            ducksboardPushAPI = Mock(DucksboardPushAPI)
            ducksboardService.ducksboardPushAPI = ducksboardPushAPI

        when:
            ducksboardService.pushCompletion(widgetId, minimum, maximum, value)

        then:
            1 * ducksboardPushAPI.pushJson(widgetId, json)

        where:
            minimum = 100
            maximum = 15000
            value = 5600
            json = """{"value":{"min":${minimum},"max":${maximum},"current":${value}}}"""
    }

}

