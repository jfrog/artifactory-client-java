/**
 *
 * @author jbaruch
 * @since 17/07/13
 */
executions {
    helloWorld(params: [whom: 'world']) { curlParams ->
        //TODO fallback to params once RTFACT-5867 is fixed
        message = "Hello ${curlParams?.whom}"
    }
}