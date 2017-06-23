/*
  Copyright 2017, Google, Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/

package com.example.spanner;

// [START spanner_quickstart]
// Imports the Google Cloud client library
import com.google.cloud.spanner.DatabaseClient;
import com.google.cloud.spanner.DatabaseId;
import com.google.cloud.spanner.ResultSet;
import com.google.cloud.spanner.Spanner;
import com.google.cloud.spanner.SpannerOptions;
import com.google.cloud.spanner.Statement;

import java.util.concurrent.TimeUnit;

/**
 * A quick start code for Cloud Spanner. It demonstrates how to setup the Cloud Spanner client and
 * execute a simple query using it against an existing database.
 */
public class QuickstartSample {
  public static void main(String... args) throws Exception {

    if (args.length != 2) {
      System.err.println("Usage: QuickStartSample <instance_id> <database_id>");
      return;
    }
    long tp1 = System.nanoTime();
    // Instantiates a client
    SpannerOptions options = SpannerOptions.newBuilder().build();
    Spanner spanner = options.getService();
    long tp2 = System.nanoTime();
    System.out.println("--- " + new Long(TimeUnit.NANOSECONDS.toMillis(tp2-tp1)).toString() + " millisec");


    // Name of your instance & database.
    String instanceId = args[0];
    String databaseId = args[1];
    try {
      long tp3 = System.nanoTime();
      // Creates a database client
      DatabaseClient dbClient = spanner.getDatabaseClient(DatabaseId.of(
          options.getProjectId(), instanceId, databaseId));
      long tp4 = System.nanoTime();
      System.out.println("--- " + new Long(TimeUnit.NANOSECONDS.toMillis(tp4-tp3)).toString() + " millisec");

      long tp5 = System.nanoTime();
      // Queries the database
      ResultSet resultSet = dbClient.singleUse().executeQuery(Statement.of("SELECT 1"));
      long tp6 = System.nanoTime();
      System.out.println("--- " + new Long(TimeUnit.NANOSECONDS.toMillis(tp6-tp5)).toString() + " millisec");


      long tp7 = System.nanoTime();
      // Queries the database
      ResultSet resultSet2 = dbClient.singleUse().executeQuery(Statement.of("SELECT 1"));
      long tp8 = System.nanoTime();
      System.out.println("--- " + new Long(TimeUnit.NANOSECONDS.toMillis(tp8-tp7)).toString() + " millisec");


      System.out.println("\n\nResults:");
      // Prints the results
      while (resultSet.next()) {
        System.out.printf("%d\n\n", resultSet.getLong(0));
      }
    } finally {
      // Closes the client which will free up the resources used
      spanner.closeAsync();
    }
  }
}
// [END spanner_quickstart]
