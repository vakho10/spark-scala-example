package com.example

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.SparkSession

object App {

  def main(args: Array[String]) {

    // Create (or get) spark session
    val sc = SparkSession.builder
      .appName("Simple Application")
      .getOrCreate

    // Get configuration from context
    val conf = sc.sparkContext.getConf

    // Read inputPath and outputPath strings from configuration file
    val inputPath = conf.get("inputPath")
    val outputPath = conf.get("outputPath")

    if (args.length < 2
      || inputPath.isEmpty
      || outputPath.isEmpty)
      System.err.println("You should pass two arguments: input and output paths!")
    else {

      // First argument is the input
      val textFile = sc.read.textFile(inputPath)

      // Count work occurrences...
      val counts = textFile.rdd
        .flatMap(line => line.split(" "))
        .map(word => (word, 1))
        .reduceByKey(_ + _)

      // Delete old output path
      val fs = FileSystem.get(sc.sparkContext.hadoopConfiguration)
      val output = new Path(outputPath)
      if (fs.exists(output))
        fs.delete(output, true)

      // Save result as text file
      counts.saveAsTextFile(outputPath)

    }

    // Closing the spark session
    sc.close
  }

}