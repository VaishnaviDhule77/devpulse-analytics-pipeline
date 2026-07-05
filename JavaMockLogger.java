import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JavaMockLogger {
    public static void main(String[] args) {
        try {
            // Pointing directly to your active Node.js server
            URL url = new URL("http://localhost:3000/api/logs");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Dummy payload structured precisely for our local backend engine
            String jsonInputString = "{\"minutes\": 45, \"language\": \"Java\", \"focusType\": \"Automated Backend Integration Test\"}";

            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);			
            }

            System.out.println("Response Status Code: " + conn.getResponseCode());
            System.out.println("🚀 Java Mock Engine successfully injected a code session log into Node.js!");
        } catch (Exception e) {
            System.out.println("❌ Error: Make sure your node server.js is running on port 3000 first!");
            e.printStackTrace();
        }
    }
}