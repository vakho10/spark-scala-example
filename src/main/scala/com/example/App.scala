package com.example

import org.apache.spark.sql.SparkSession

object App {

  def main(args: Array[String]) {

    if (args.length < 2)
      System.err.println("You should pass two arguments: input and output paths!")
    else {
      // Create (or get) spark session
      val sc = SparkSession.builder
        .appName("Simple Application")
        .getOrCreate

      // First argument is the input
      val textFile = sc.read.textFile(args(0))

      // Count work occurrences...
      val counts = textFile.rdd
        .flatMap(line => line.split(" "))
        .map(word => (word, 1))
        .reduceByKey(_ + _)

      // Save result as text file
      counts.saveAsTextFile(args(1))

      // Closing the spark session
      sc.close
    }
  }

}