<<<<<<< HEAD
<<<<<<< ours
<<<<<<< HEAD
<<<<<<< HEAD
/*Copyright (c) 2024 Youp Hendriks <youp_hendriks@hotmail.com>
=======
=======
>>>>>>> ef38fbc2 (A bit more header clean up)
=======
>>>>>>> f08d154a (Added name to citation file)
/*
 *BridgeDb,
 *An abstraction layer for identifier mapping services, both local and online.
 *Copyright (c) 2024 Youp Hendriks<youp_hendriks@hotmail.com>
 *Copyright (c) 2024 OpenPhacts
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> e9b78be9 (Added name to citation file)
=======
=======
/*Copyright (c) 2024 Youp Hendriks <youp_hendriks@hotmail.com>
>>>>>>> 7a8142ea (A bit more header clean up)
>>>>>>> ef38fbc2 (A bit more header clean up)
=======
/*
 *Copyright (c) 2024 Youp Hendriks<youp_hendriks@hotmail.com>
>>>>>>> theirs
=======
>>>>>>> f08d154a (Added name to citation file)
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and limitations under the License.
 */
<<<<<<< HEAD
=======

>>>>>>> f08d154a (Added name to citation file)
package org.bridgedb;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Assertions;

import static org.mockito.Mockito.*;

/**
 * Tests the {@link org.bridgedb.DataSourcePatterns} class.
 * 
 * @author youphendriks
 */
public class DataSourcePatternsTest {

  @Test
  @DisplayName("Test getDataSourceMatches class, valid input")
  public void testgetDataSourceMatchesValid() {
    assertNotNull(DataSourcePatterns.getDataSourceMatches("F"));
  }

  /**
   * @Test
   *       @DisplayName("Test getDataSourceMatches class, invalid input")
   *       public void testgetDataSourceMatchesInvalid()
   *       {
   * @Mock private getDataSourceMatches
   *       Assertions.assertThrows(IllegalArgumentException.class,
   *       () -> {
   *       DataSourcePatterns.getDataSourceMatches("");
   *       });
   *       }
   */
}
