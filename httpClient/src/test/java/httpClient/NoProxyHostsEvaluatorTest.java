package httpClient;

import org.jfrog.artifactory.client.httpClient.http.NoProxyHostsEvaluator;
import org.testng.Assert;
import org.testng.annotations.Test;


public class NoProxyHostsEvaluatorTest {

    @Test
    public void testIPV4() {
        NoProxyHostsEvaluator noProxyHostsEvaluator = new NoProxyHostsEvaluator("10.0.0.1");
        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("10.0.0.1"));
    }

    @Test
    public void testWrongIPV4() {
        NoProxyHostsEvaluator noProxyHostsEvaluator = new NoProxyHostsEvaluator("127.0.0.1");
        Assert.assertFalse(noProxyHostsEvaluator.shouldBypassProxy("10.0.0.1"));
    }

    @Test
    public void testIPV6() {
        NoProxyHostsEvaluator noProxyHostsEvaluator =
                new NoProxyHostsEvaluator("2001:0db8:85a3:0000:0000:8a2e:0370:7334");
        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("2001:0db8:85a3:0000:0000:8a2e:0370:7334"));
    }

    @Test
    public void testWrongIPV6() {
        NoProxyHostsEvaluator noProxyHostsEvaluator = new NoProxyHostsEvaluator("0:0:0:0:0:0:0:1");
        Assert.assertFalse(noProxyHostsEvaluator.shouldBypassProxy("2001:0db8:85a3:0000:0000:8a2e:0370:7334"));
    }

    @Test
    public void testFQDN() {
        NoProxyHostsEvaluator noProxyHostsEvaluator = new NoProxyHostsEvaluator("foobar.com");

        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("foobar.com"));
        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("sub.foobar.com"));
        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("sub.sub.foobar.com"));

        Assert.assertFalse(noProxyHostsEvaluator.shouldBypassProxy("foobar.org"));
        Assert.assertFalse(noProxyHostsEvaluator.shouldBypassProxy("jfrog.com"));
    }

    @Test
    public void testSubFQDN() {
        NoProxyHostsEvaluator noProxyHostsEvaluator = new NoProxyHostsEvaluator("sub.foobar.com");

        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("sub.foobar.com"));
        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("sub.sub.foobar.com"));

        Assert.assertFalse(noProxyHostsEvaluator.shouldBypassProxy("foobar.com"));
    }

    @Test
    public void testFQDNWithDot() {
        NoProxyHostsEvaluator noProxyHostsEvaluator = new NoProxyHostsEvaluator(".foobar.com");

        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("foobar.com"));
        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("sub.foobar.com"));
        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("sub.sub.foobar.com"));

        Assert.assertFalse(noProxyHostsEvaluator.shouldBypassProxy("foobar.org"));
        Assert.assertFalse(noProxyHostsEvaluator.shouldBypassProxy("jfrog.com"));
    }

    @Test
    public void testSubFQDNWithDot() {
        NoProxyHostsEvaluator noProxyHostsEvaluator = new NoProxyHostsEvaluator(".sub.foobar.com");

        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("sub.foobar.com"));
        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("sub.sub.foobar.com"));

        Assert.assertFalse(noProxyHostsEvaluator.shouldBypassProxy("foobar.com"));
    }

    @Test
    public void testNoProxyWithInvalidChars() {
        NoProxyHostsEvaluator noProxyHostsEvaluator = new NoProxyHostsEvaluator("foo+.co=m");

        Assert.assertFalse(noProxyHostsEvaluator.shouldBypassProxy("foo+.co=m"));
    }

    @Test
    public void testNoProxyWithInvalidChar() {
        NoProxyHostsEvaluator noProxyHostsEvaluator = new NoProxyHostsEvaluator("foo.co=m");

        Assert.assertFalse(noProxyHostsEvaluator.shouldBypassProxy("foo.co=m"));
    }

    @Test
    public void testNoProxyWithInvalidCharInMinimalSuffix() {
        NoProxyHostsEvaluator noProxyHostsEvaluator = new NoProxyHostsEvaluator(".sv=c");

        Assert.assertFalse(noProxyHostsEvaluator.shouldBypassProxy("foo.sv=c"));
    }

    @Test
    public void testMixed() {
        NoProxyHostsEvaluator noProxyHostsEvaluator =
                new NoProxyHostsEvaluator(" 127.0.0.1,  0:0:0:0:0:0:0:1,\tfoobar.com, .jfrog.com");

        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("127.0.0.1"));
        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("0:0:0:0:0:0:0:1"));
        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("foobar.com"));
        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("sub.foobar.com"));
        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("sub.jfrog.com"));

        Assert.assertFalse(noProxyHostsEvaluator.shouldBypassProxy("foobar.org"));
        Assert.assertFalse(noProxyHostsEvaluator.shouldBypassProxy("jfrog.org"));
        Assert.assertFalse(noProxyHostsEvaluator.shouldBypassProxy("sub.foobar.org"));
        Assert.assertFalse(noProxyHostsEvaluator.shouldBypassProxy("sub.jfrog.org"));
    }

    @Test
    public void testHostsWithPorts() {
        NoProxyHostsEvaluator noProxyHostsEvaluator =
                new NoProxyHostsEvaluator("*.jfrog.com,.not.important.host:443,196.10.5.44,196.10.5.33:20");

        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("jfrog.com:80"));
        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("jfrog.com"));
        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("not.important.host:443"));
        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("bla.not.important.host:443"));
        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("196.10.5.44"));
        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("196.10.5.44:20"));
        Assert.assertTrue(noProxyHostsEvaluator.shouldBypassProxy("196.10.5.33:20"));

        Assert.assertFalse(noProxyHostsEvaluator.shouldBypassProxy("196.10.5.33"));
        Assert.assertFalse(noProxyHostsEvaluator.shouldBypassProxy("196.10.5.33:40"));
        Assert.assertFalse(noProxyHostsEvaluator.shouldBypassProxy("bla.not.important.host"));
        Assert.assertFalse(noProxyHostsEvaluator.shouldBypassProxy("bla.not.important.host:8080"));
    }

}