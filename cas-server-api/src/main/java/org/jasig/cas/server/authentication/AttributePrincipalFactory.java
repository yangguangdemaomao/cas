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

package org.jasig.cas.server.authentication;

/**
 * Abstracts constructing a new {@link AttributePrincipal} so that one can be created that is compliant with the backing
 * storage mechanism.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 3.5
 */
public interface AttributePrincipalFactory {

    /**
     * Constructs a new AttributePrincipal based on the supplied values.
     *
     * @param name the name of the principal.  CANNOT be NULL.
     * @return the principal.  CANNOT be NULL.
     */
    AttributePrincipal getAttributePrincipal(String name);
}