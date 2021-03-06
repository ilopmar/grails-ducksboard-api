class DucksboardApiGrailsPlugin {
    // the plugin version
    def version = "0.4.0"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.7 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp",
            "grails-app/domain/**",
            "grails-app/controllers/**",
            "grails-app/taglib/**",
            "grails-app/i18n/**",
            "web-app/**"
    ]

    def author = "Iván López"
    def authorEmail = "lopez.ivan@gmail.com"
    def title = "Ducksboard API"
    def description = '''\
The Ducksboard API plugin provides integration with the ducksboard.com pull and push API. Ducksboard is an online dashboard to display all your metrics in real time.
'''
    // URL to the plugin's documentation
    def documentation = "https://github.com/lmivan/grails-ducksboard-api/blob/master/README.md"

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Online location of the plugin's browseable source code.
    def scm = [ url: "https://github.com/lmivan/grails-ducksboard-api" ]


    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        def ducksboardApiConfig = application.config.ducksboard

        if (!ducksboardApiConfig.user) {
            log.error "ERROR: Ducksboard API user not found. The property ducksboard.user must be defined in Config.groovy"
        }
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
