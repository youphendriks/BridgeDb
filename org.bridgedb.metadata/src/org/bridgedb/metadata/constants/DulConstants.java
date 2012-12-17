// BridgeDb,
// An abstraction layer for identifier mapping services, both local and online.
//
// Copyright      2012  Christian Y. A. Brenninkmeijer
// Copyright      2012  OpenPhacts
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
package org.bridgedb.metadata.constants;

import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

public class DulConstants {

	private static final String dulns = "http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#";
	
	public static final URI EXPRESSES = new URIImpl(dulns + "expresses");
    
}