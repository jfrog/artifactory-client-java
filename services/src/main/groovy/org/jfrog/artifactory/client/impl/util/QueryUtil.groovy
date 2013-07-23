package org.jfrog.artifactory.client.impl.util

import org.apache.commons.lang.StringUtils

/**
 *
 * @author jbaruch
 * @since 11/12/12
 */
class QueryUtil {


    static getQueryList(Map<String, Iterable<?>> props) {
        def propList = new StringBuilder()
        props.eachWithIndex { entry, pi ->
            if (pi != 0) propList.append('|')
            propList.append(escape(entry.key)).append('=')
            entry.value.eachWithIndex { val, vi ->
                if (vi != 0) propList.append(',')
                propList.append(escape(val))
            }
        }
        propList.toString()
    }

    static escape(def o) {
        StringUtils.replaceEach(o?.toString(), [',', '\\', '|', '='] as String[], ['\\,', '\\\\', '\\|', '\\='] as String[])
    }
}
