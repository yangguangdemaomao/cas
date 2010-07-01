/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.cas.support.spnego.authentication.handler.support;

import junit.framework.TestCase;

import org.jasig.cas.TestUtils;
import org.jasig.cas.server.authentication.DefaultUserNamePasswordCredential;
import org.jasig.cas.support.spnego.MockJCSIFAuthentication;
import org.jasig.cas.support.spnego.authentication.principal.SpnegoCredentials;

import java.security.GeneralSecurityException;

/**
 * @author Marc-Antoine Garrigue
 * @author Arnaud Lesueur
 * @version $Id$
 * @since 3.1
 * 
 */
public class JCSIFSpnegoAuthenticationHandlerTests extends TestCase {
    private JCIFSSpnegoAuthenticationHandler authenticationHandler;

    protected void setUp() throws Exception {
        this.authenticationHandler = new JCIFSSpnegoAuthenticationHandler();
    }

    public void testSuccessfulAuthenticationWithDomainName() throws GeneralSecurityException {
        final SpnegoCredentials credentials = new SpnegoCredentials(new byte[] {0, 1, 2});
        this.authenticationHandler.setPrincipalWithDomainName(true);
        this.authenticationHandler.setAuthentication(new MockJCSIFAuthentication(true));
        assertTrue(this.authenticationHandler.authenticate(credentials));
        assertEquals("test", credentials.getPrincipal().getName());
        assertNotNull(credentials.getNextToken());
    }

    public void testSuccessfulAuthenticationWithoutDomainName() throws GeneralSecurityException {
        final SpnegoCredentials credentials = new SpnegoCredentials(new byte[] {0, 1, 2});
        this.authenticationHandler.setPrincipalWithDomainName(false);
        this.authenticationHandler.setAuthentication(new MockJCSIFAuthentication(true));
        assertTrue(this.authenticationHandler.authenticate(credentials));
        assertEquals("test", credentials.getPrincipal().getName());
        assertNotNull(credentials.getNextToken());
    }

    public void testUnsuccessfulAuthentication() {
        final SpnegoCredentials credentials = new SpnegoCredentials(new byte[] {0, 1, 2});
        this.authenticationHandler.setAuthentication(new MockJCSIFAuthentication(false));
        try {
            this.authenticationHandler.authenticate(credentials);
            fail("An AuthenticationException should have been thrown");
        } catch (GeneralSecurityException e) {
            assertNull(credentials.getNextToken());
            assertNull(credentials.getPrincipal());
        }
    }

    public void testSupports() {
        assertFalse(this.authenticationHandler.supports(null));
        assertTrue(this.authenticationHandler.supports(new SpnegoCredentials(new byte[] {0, 1, 2})));
        assertFalse(this.authenticationHandler.supports(new DefaultUserNamePasswordCredential()));
    }

    public void testGetSimpleCredentials() {
        String myNtlmUser = "DOMAIN\\Username";
        String myNtlmUserWithNoDomain = "Username";
        String myKerberosUser = "Username@DOMAIN.COM";

        this.authenticationHandler.setPrincipalWithDomainName(true);
        assertEquals(TestUtils.getPrincipal(myNtlmUser).getName(), this.authenticationHandler.getSimplePrincipal(myNtlmUser, true).getName());
        assertEquals(TestUtils.getPrincipal(myNtlmUserWithNoDomain).getName(), this.authenticationHandler.getSimplePrincipal(myNtlmUserWithNoDomain, false).getName());
        assertEquals(TestUtils.getPrincipal(myKerberosUser).getName(), this.authenticationHandler.getSimplePrincipal(myKerberosUser, false).getName());

        this.authenticationHandler.setPrincipalWithDomainName(false);
        assertEquals(TestUtils.getPrincipal("Username").getName(), this.authenticationHandler.getSimplePrincipal(myNtlmUser, true).getName());
        assertEquals(TestUtils.getPrincipal("Username").getName(), this.authenticationHandler.getSimplePrincipal(myNtlmUserWithNoDomain, true).getName());
        assertEquals(TestUtils.getPrincipal("Username").getName(), this.authenticationHandler.getSimplePrincipal(myKerberosUser, false).getName());
    }
}
