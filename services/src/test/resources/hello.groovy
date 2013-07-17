/**
 *
 * @author jbaruch
 * @since 17/07/13
 */
executions {
    helloWorld(params : [msg : 'world']) { params ->
        msg = "Hello ${params?.message}"
    }
}