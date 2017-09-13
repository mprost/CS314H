package assignment;
import java.io.*;

public interface TextProcessor {

    /**
     * Reads text from a file for use with an analysis.
     *
     * @param inputFilename     source text file
     *
     * @throws IOException      if an I/O related exception occurs during the
     *                          reading of <code>inputFilename</code>.
     */
    void readText(String inputFilename) throws IOException;

    /**
     * Writes text generated from an analysis to a file.
     *
     * @param outputFilename    destination text file
     * @param length            length of text to generate (non-negative)
     *
     * @throws IOException      if an I/O related exception occurs during the
     *                          writing of <code>outputFilename</code>.
     */
    void writeText(String outputFilename, int length) throws IOException;
}
