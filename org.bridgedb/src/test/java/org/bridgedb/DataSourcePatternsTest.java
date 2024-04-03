/*Copyright (c) 2024 Youp Hendriks <youp_hendriks@hotmail.com>
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and limitations under the License.
 */
package org.bridgedb;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;


/**
 * Tests the {@link org.bridgedb.DataSourcePatterns} class.
 * 
 * @author youphendriks
 */
class DataSourcePatternsTest {
  DataSourcePatterns dataSourcePatterns = new DataSourcePatterns();
  protected static DataSource key;

  @Test
  void dataSourcePatterns_validInput() {

  }
/**
 * @Test
 * void DataSourcePatterns_givenVoidReturnType_whenUsingDoThrow_thenExceptionIsThrown() {
 *     DataSourcePatterns patternMock = mock(DataSource.class);
 *     doThrow(IllegalStateException.class).when(patternMock)
 *         .registerPattern(any(), any());
 *
 *    assertThrows(IllegalStateException.class, () -> patternMock.registerPattern("notKeyButString", "notValueButString"));
 *}
*/
  @Test
  @DisplayName("Test getDataSourceMatches class, valid input")
  public void testgetDataSourceMatchesValid() {
    assertNotNull(DataSourcePatterns.getDataSourceMatches("F"));
  }

  @Test
  @DisplayName("Test getDataSourceMatches class, invalid input")
  public void testgetDataSourceMatchesInvalid() {
    MyException thrown = assertThrows(
        MyException.class,
        () -> DataSourcePatterns.getDataSourceMatches(),
        "Expected getDataSourceMatcher() to throw, but it didn't"
    );

    assertTrue(thrown.getMessage().contains("Stuff"));
  }
}
