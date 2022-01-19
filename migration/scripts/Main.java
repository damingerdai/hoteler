
import java.io.*;
import java.net.*;
import javax.net.ssl.*;
 
public class Main {
 
   public static void main(String[] args) {
    String[] defaultArgs = {"https://github.com/bazelbuild/rules_nodejs/releases/download/4.4.1/rules_nodejs-4.4.1.tar.gz", "https://mirror.bazel.build/github.com/bazelbuild/rules_go/releases/download/v0.28.0/rules_go-v0.28.0.zip"};

    if (args.length == 0) {
      args = defaultArgs;
    }
    for (String url : args) {
      try (InputStream stream = new URL(url).openStream()) {
        System.out.println("OK: " + url);
      } catch (SSLHandshakeException ex) {
        // This is bad
        System.err.println("ERROR: " + url + ": " + ex.toString());
      } catch (IOException ex) {
        System.out.println("Warning: " + url + ": " + ex.toString());
      }
    }
  }
}