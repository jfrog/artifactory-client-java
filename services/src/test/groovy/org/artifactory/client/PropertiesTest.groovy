package org.artifactory.client

import org.artifactory.client.impl.PropertiesHandlerImpl
import org.testng.annotations.Test

/**
 *
 * Date: 12/3/12
 * Time: 11:03 AM
 * @author freds
 */
class PropertiesTest {

    @Test
    public void testPropertiesEscaping() {
        def impl = new PropertiesHandlerImpl(null, null, null)
        def v1 = 'a dir|ty,va=l\\ue'
        def v2 = "a dir|ty,GString $v1 va=l\\ue"
        assert 'a dir\\|ty\\,va\\=l\\\\ue' == impl.escape(v1)
        assert 'a dir\\|ty\\,GString a dir\\|ty\\,va\\=l\\\\ue va\\=l\\\\ue' == impl.escape(v2)
        impl.addProperty("test=ex|in \\key", 'with=', 'multi,val', 'st|ring')
        impl.addProperty("test2=|\\key", v2)
        impl.addProperties(["test3=|\\key": v1, '=tes,t4': ['mu]ti', '=,|']])
        assert 'test\\=ex\\|in \\\\key=with\\=,multi\\,val,st\\|ring|test2\\=\\|\\\\key=a dir\\|ty\\,GString a dir\\|ty\\,va\\=l\\\\ue va\\=l\\\\ue|test3\\=\\|\\\\key=a dir\\|ty\\,va\\=l\\\\ue|\\=tes\\,t4=mu]ti,\\=\\,\\|' == impl.getPropList()
    }
}
